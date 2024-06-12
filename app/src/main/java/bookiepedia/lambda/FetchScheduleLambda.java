package bookiepedia.lambda;

import bookiepedia.activities.requests.FetchScheduleRequest;
import bookiepedia.activities.results.FetchScheduleResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class FetchScheduleLambda
        extends LambdaActivityRunner<FetchScheduleRequest, FetchScheduleResult>
        implements RequestHandler<LambdaRequest<FetchScheduleRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(LambdaRequest<FetchScheduleRequest> input, Context context) {
        return super.runActivity(
                () -> input.fromPath(path ->
                        FetchScheduleRequest.builder()
                                .withId(path.get("id"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideFetchScheduleActivity().handleRequest(request)
        );
    }

}
