package bookiepedia.lambda;

import bookiepedia.activities.requests.RemoveBetFromHistoryRequest;
import bookiepedia.activities.results.RemoveBetFromHistoryResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RemoveBetFromHistoryLambda
    extends LambdaActivityRunner<RemoveBetFromHistoryRequest, RemoveBetFromHistoryResult>
    implements RequestHandler<LambdaRequest<RemoveBetFromHistoryRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(LambdaRequest<RemoveBetFromHistoryRequest> input, Context context) {
        return super.runActivity(
                () -> input.fromBody(RemoveBetFromHistoryRequest.class),
                (request, serviceComponent) -> {
                    try {
                        return serviceComponent.provideRemoveBetFromHistoryActivity().handleRequest(request);
                    } catch (Exception e) {
                        context.getLogger().log("Error parsing input: " + e.getMessage());
                        throw new RuntimeException("Error! Cause: " + e.getCause() + " - " + e.getClass());
                    }
                });
    }

}
