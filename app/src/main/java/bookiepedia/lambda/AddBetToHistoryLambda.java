package bookiepedia.lambda;

import bookiepedia.activities.requests.AddBetToHistoryRequest;
import bookiepedia.activities.results.AddBetToHistoryResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.math.BigDecimal;

public class AddBetToHistoryLambda
        extends LambdaActivityRunner<AddBetToHistoryRequest, AddBetToHistoryResult>
        implements RequestHandler<LambdaRequest<AddBetToHistoryRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(LambdaRequest<AddBetToHistoryRequest> input, Context context) {
        return super.runActivity(
                () -> input.fromBody(AddBetToHistoryRequest.class),
                (request, serviceComponent) -> {
                    try {
                        return serviceComponent.provideAddBetToHistoryActivity().handleRequest(request);
                    } catch (Exception e) {
                        context.getLogger().log("Error parsing input: " + e.getMessage());
                        throw new RuntimeException("Error! Cause: " + e.getCause() + " - " + e.getClass() + "-" + e.getMessage());
                    }
                });
    }
}

