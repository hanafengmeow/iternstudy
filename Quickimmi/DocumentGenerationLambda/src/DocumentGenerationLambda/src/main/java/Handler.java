import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.google.gson.Gson;
import com.quick.immi.ai.database.DocumentGenerationManager;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.handler.FormHandler;
import com.quick.immi.ai.handler.HandlerFactory;
import com.quick.immi.ai.utils.S3Utils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import static com.quick.immi.ai.constant.Constants.REGION_ENV_MAP;

public class Handler implements RequestHandler<SQSEvent, Void> {
    private SqlSessionFactory sqlSessionFactory;

    public Handler() {
        String region = S3Utils.getCurrentRegion();
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
        long start = System.currentTimeMillis();
        String currentRegion = S3Utils.getCurrentRegion();
        try {
            for (SQSEvent.SQSMessage msg : event.getRecords()) {
                logger.log(String.format("Received message: %s, with region %s", msg.getBody(), currentRegion));
                FormGenerationTask formGenerationTask = new Gson().fromJson(msg.getBody(), FormGenerationTask.class);
                DocumentGenerationManager documentGenerationManager = new DocumentGenerationManager(sqlSessionFactory);
                FormHandler handler = HandlerFactory.getHandler(documentGenerationManager, formGenerationTask, formGenerationTask.getFormName());
                handler.handle();
                long end = System.currentTimeMillis();
                logger.log(String.format("generate table %s successfully, cost=%s seconds", formGenerationTask.getFormName(), (end - start) / 1000));
            }

        } catch (Exception e) {
            throw new RuntimeException("fail to process event: " + new Gson().toJson(event), e);
        }
        return null;
    }
}