package bookiepedia.lambda;

import bookiepedia.activities.requests.FetchScheduleRequest;
import bookiepedia.activities.results.FetchScheduleResult;
import bookiepedia.dynamodb.EspnDAO.EspnDAO;
import bookiepedia.dynamodb.EspnDAO.constants.EspnRequestConstants;
import bookiepedia.dynamodb.models.Event;
import bookiepedia.dynamodb.models.Schedule;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FetchScheduleLambda implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final DynamoDBMapper dynamoDBMapper;
    private final EspnDAO espnDAO;
    private final int defaultUpdateFrequency;
    private final AtomicInteger updateFrequency;

    @Inject
    public FetchScheduleLambda(DynamoDBMapper dynamoDBMapper, EspnDAO espnDAO) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.espnDAO = espnDAO;
        this.defaultUpdateFrequency = 60;
        this.updateFrequency = new AtomicInteger(defaultUpdateFrequency);
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {

        int updateFrequencyInMin = getUpdateFrequency();

        try {
            String startDate = EspnRequestConstants.getStartDate();
            String endDate = EspnRequestConstants.getEndDate();

            String nbaURL = String.format("https//site.api.espn.com/apis/site/v2/sports/%s/scoreboard?dates=%s-%s",
                    EspnRequestConstants.NBA, startDate, endDate);
            String nhlURL = String.format("https//site.api.espn.com/apis/site/v2/sports/%s/scoreboard?dates=%s-%s",
                    EspnRequestConstants.NHL, startDate, endDate);

            JSONObject nbaResponse = espnDAO.requestQuery(nbaURL);
            processResponse(nbaResponse);

            JSONObject nhlResponse = espnDAO.requestQuery(nhlURL);
            processResponse(nhlResponse);

            return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(
                    "Schedules for : " + startDate + " - Successfully created for NBA & NHL");

        } catch (IOException ioe) {
            return new APIGatewayProxyResponseEvent().withStatusCode(500).withBody("Error creating Schedule: " + ioe.getMessage());
        }
    }

    private void processResponse(JSONObject response) throws JsonProcessingException {
        String scheduleJson = espnDAO.extractSchedule(response);
        Schedule schedule = new ObjectMapper().readValue(scheduleJson, Schedule.class);
        dynamoDBMapper.save(schedule);

        boolean eventInProgress = false;
        List<String> eventListJson = espnDAO.extractEvents(response);
        for (String eventJson : eventListJson) {
            Event event = new ObjectMapper().readValue(eventJson, Event.class);
            if (event.getEventStatusId().equals("3")) {
                eventInProgress = true;
            }

            dynamoDBMapper.save(event);
        }

        if (eventInProgress) {
            updateFrequency.set(1);
        } else {
            updateFrequency.set(defaultUpdateFrequency);
        }
    }

    private int getUpdateFrequency() {
        return updateFrequency.get();
    }




//    @Override
//    public LambdaResponse handleRequest(LambdaRequest<FetchScheduleRequest> input, Context context) {
//        return super.runActivity(
//                () -> FetchScheduleRequest.builder().build(),
//                (request, serviceComponent) ->
//                        serviceComponent.provideFetchScheduleActivity().handleRequest(request)
//        );
//    }

}
