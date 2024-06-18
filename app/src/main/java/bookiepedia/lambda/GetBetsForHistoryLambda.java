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
        return super.runActivity(
                () -> input.fromPath(path ->
                        GetBetsForHistoryRequest.builder()
                                .withId(path.get("weeklyHistoryId"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetBetsForHistoryActivity().handleRequest(request)
        );
    }

}
