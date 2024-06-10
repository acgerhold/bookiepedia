package bookiepedia.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetEventsForScheduleLambda implements RequestHandler<LambdaRequest<GetEventsForScheduleLambda>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetEventsForScheduleLambda> input, Context context) {
        return LambdaResponse.success();
    }
}
