package bookiepedia.lambda;

import bookiepedia.activities.requests.GetBetsForHistoryRequest;
import bookiepedia.activities.requests.GetEventsForScheduleRequest;
import bookiepedia.activities.results.GetBetsForHistoryResult;
import bookiepedia.activities.results.GetEventsForScheduleResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetBetsForHistoryLambda
        extends LambdaActivityRunner<GetBetsForHistoryRequest, GetBetsForHistoryResult>
        implements RequestHandler<LambdaRequest<GetBetsForHistoryRequest>, LambdaResponse> {


    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetBetsForHistoryRequest> input, Context context) {
        context.getLogger().log("Received input: " + input);
        return super.runActivity(
                () -> input.fromPath(path ->
                        GetBetsForHistoryRequest.builder()
                                .withWeeklyHistoryId(path.get("weeklyHistoryId"))
                                .build()),
                (request, serviceComponent) -> {
                    try {
                        return serviceComponent.provideGetBetsForHistoryActivity().handleRequest(request);
                    } catch (Exception e) {
                        context.getLogger().log("Error parsing input: " + e.getMessage());
                        throw new RuntimeException("Error! Cause: " + e.getCause() + " - " + e.getClass() + "-" + e.getMessage());
                    }
                });
    }

}
