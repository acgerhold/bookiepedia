package bookiepedia.lambda;

import bookiepedia.activities.GetEventsForScheduleActivity;
import bookiepedia.activities.requests.GetEventsForScheduleRequest;
import bookiepedia.activities.results.GetEventsForScheduleResult;
import bookiepedia.dynamodb.EventDAO;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class GetEventsForScheduleLambda
        extends LambdaActivityRunner<GetEventsForScheduleRequest, GetEventsForScheduleResult>
        implements RequestHandler<LambdaRequest<GetEventsForScheduleRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetEventsForScheduleRequest> input, Context context) {
        return super.runActivity(
                () -> input.fromPath(path ->
                            GetEventsForScheduleRequest.builder()
                                    .withId(path.get("scheduleId"))
                                    .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetEventsForScheduleActivity().handleRequest(request)
        );
    }
}
