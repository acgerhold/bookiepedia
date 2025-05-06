package bookiepedia.lambda;

import bookiepedia.activities.requests.FetchLeaguesRequest;
import bookiepedia.activities.results.FetchLeaguesResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class FetchLeaguesLambda
        extends LambdaActivityRunner<FetchLeaguesRequest, FetchLeaguesResult>
        implements RequestHandler<LambdaRequest<FetchLeaguesRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(LambdaRequest<FetchLeaguesRequest> input, Context context) {
        return super.runActivity(
                () -> input.fromQuery(query ->
                        FetchLeaguesRequest.builder()
                                .withSportName(query.get("sportName"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideFetchLeaguesActivity().handleRequest(request)
        );
    }
}
