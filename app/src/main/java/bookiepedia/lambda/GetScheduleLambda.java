package bookiepedia.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetScheduleLambda implements RequestHandler<LambdaRequest<GetScheduleLambda>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetScheduleLambda> input, Context context) {
        return LambdaResponse.success();
    }
}
