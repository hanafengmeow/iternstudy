import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.google.gson.Gson;
import com.quick.immi.ai.database.DocumentGenerationManager;
import com.quick.immi.ai.entity.Document;
import com.quick.immi.ai.entity.FormGenerationTask;
import com.quick.immi.ai.entity.TaskStatus;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import static com.quick.immi.ai.entity.Constants.MAIN_FROM_SET_WITH_SUPPLEMENT;
import static com.quick.immi.ai.entity.Constants.REGION_ENV_MAP;

public class Handler implements RequestHandler<SQSEvent, Void> {
    private SqlSessionFactory sqlSessionFactory;

    public Handler() {
        String region = System.getenv("AWS_REGION");
        String dbConfigPath = REGION_ENV_MAP.get(region).getDbConfigPath();
        try (Reader reader = new InputStreamReader(
                DocumentGenerationManager.class.getClassLoader().getResourceAsStream(dbConfigPath),
                StandardCharsets.UTF_8)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("receive event: " + new Gson().toJson(event));
        try {
            for (SQSEvent.SQSMessage msg : event.getRecords()) {
                FormGenerationTask formGenerationTask = new Gson().fromJson(msg.getBody(), FormGenerationTask.class);
                DocumentGenerationManager documentGenerationManager = new DocumentGenerationManager(sqlSessionFactory);
                updateFormGenerationTask(formGenerationTask, documentGenerationManager);

                Document document = documentGenerationManager.getDocument(formGenerationTask.getDocumentId());
                updateDocument(document, documentGenerationManager);

                if (MAIN_FROM_SET_WITH_SUPPLEMENT.contains(formGenerationTask.getFormName())) {
                    String metadata = formGenerationTask.getMetadata();
                    if (metadata != null) {
                        FormGenerationTask supplementFormGenerationTask = new Gson().fromJson(metadata, FormGenerationTask.class);
                        updateFormGenerationTask(supplementFormGenerationTask, documentGenerationManager);

                        Document supplementDocument = documentGenerationManager.getDocument(supplementFormGenerationTask.getDocumentId());
                        updateDocument(supplementDocument, documentGenerationManager);
                    }
                }
            }
            logger.log("update document successfully....");
        } catch (Exception e) {
            throw new RuntimeException("fail to process event: " + new Gson().toJson(event), e);
        }
        return null;
    }

    private void updateFormGenerationTask(FormGenerationTask formGenerationTask, DocumentGenerationManager documentGenerationManager) {
        formGenerationTask.setStatus(TaskStatus.FAILED.getValue());
        formGenerationTask.setUpdatedAt(System.currentTimeMillis());
        documentGenerationManager.updateFormGenerationTask(formGenerationTask);
    }

    private void updateDocument(Document document, DocumentGenerationManager documentGenerationManager) {
        document.setStatus(TaskStatus.FAILED.getValue());
        document.setUpdatedAt(System.currentTimeMillis());
        documentGenerationManager.updateDocument(document);
    }
}