package handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import constant.Constant;
import model.RequestMetadata;
import model.Response;
import model.Task;
import utils.SQSUtils;
import utils.TaskDynamoDBUtils;

import static com.google.common.base.Preconditions.checkNotNull;
import static constant.Constant.*;


public class Handler implements RequestHandler<RequestMetadata, Response> {

    /**
     * {
     * "rootUrls": ["https://uci.edu/"],
     * "source": "uscis",
     * "depth": 2
     * }
     */
    @Override
    public Response handleRequest(RequestMetadata request, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("received message + " + request.toJson());

        checkNotNull(request.getSource(), "source field is empty");
        checkNotNull(request.getDepth(), "depth field is empty");
        checkNotNull(request.getRootUrls(), "root urls are empty");

        if(!request.getSource().startsWith(USCIS)){
            throw new RuntimeException(String.format("source should start with %s, or %s, or %s, or %s, or %s", USCIS));
        }

        try {
            Task task = createTask(request);
            logger.log("generate task metadata: " + task.toJson());
            TaskDynamoDBUtils.createTask(task);
            logger.log(String.format("create crawler task {} successfully", task.getId()));
            new SQSUtils(logger).sendUnvisitedUrlMessage(task.getId(), request.getDepth(), request.getRootUrls(), request.getSource());
            logger.log(String.format("{} send SQS successfully !!!", task.getId()));
            return new Response(task.getId(), "200", "SUCCESS!!!");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.fillInStackTrace());
        }
    }

    private Task createTask(RequestMetadata request) {
        Long currentTimestamp = System.currentTimeMillis();
        return new Task()
                .setId(request.getSource() + "-" + currentTimestamp)
                .setStartAt(currentTimestamp)
                .setSource(request.getSource())
                .setRequest(request.toJson());
    }
}
