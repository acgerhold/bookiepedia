package bookiepedia.lambda;

import bookiepedia.activities.requests.GetScheduleRequest;
import bookiepedia.activities.results.GetScheduleResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetScheduleLambda
        extends LambdaActivityRunner<GetScheduleRequest, GetScheduleResult>
        implements RequestHandler<LambdaRequest<GetScheduleRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetScheduleRequest> input, Context context) {
        return super.runActivity(
                () -> input.fromPath(path ->
                        GetScheduleRequest.builder()
                                .withId(path.get("id"))
                                .build()),
                (request, serviceComponent) ->
                    serviceComponent.provideGetScheduleActivity().handleRequest(request)
        );
    }
}
