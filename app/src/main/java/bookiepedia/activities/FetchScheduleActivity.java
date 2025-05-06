package bookiepedia.activities;

import bookiepedia.activities.requests.FetchScheduleRequest;
import bookiepedia.activities.results.FetchScheduleResult;
import bookiepedia.dynamodb.EspnDAO.EspnDAO;
import bookiepedia.dynamodb.EspnDAO.constants.EspnRequestConstants;
import bookiepedia.dynamodb.models.Event;
import bookiepedia.dynamodb.models.Schedule;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class FetchScheduleActivity {

    private final DynamoDBMapper dynamoDBMapper;
    private final EspnDAO espnDAO;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    public FetchScheduleActivity(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.espnDAO = new EspnDAO(dynamoDBMapper);
    }

    public FetchScheduleResult handleRequest(final FetchScheduleRequest request) {
        try {

            String apiURL = String.format("https://site.api.espn.com/apis/site/v2/sports/%s/%s/scoreboard",
                    request.getSportName(), request.getLeagueName().toLowerCase());

            System.out.println(apiURL);
            
            JSONObject response = espnDAO.requestQuery(apiURL);

            processResponse(response);

            return FetchScheduleResult.builder()
                    .withMessage(EspnRequestConstants.TIMESTAMP)
                    .build();

        } catch (IOException ioe) {
            System.err.println("Error occurred while processing ESPN API response: " + ioe.getMessage());
            throw new RuntimeException("Failled to process ESPN API response", ioe);
        }
    }

    private void processResponse(JSONObject response) throws JsonProcessingException {
        List<Object> itemsToSave = new ArrayList<>();

        String scheduleJson = espnDAO.extractSchedule(response);
        Schedule schedule = objectMapper.readValue(scheduleJson, Schedule.class);
        itemsToSave.add(schedule);

        List<String> eventListJson = espnDAO.extractEvents(response);
        for (String eventJson : eventListJson) {
            Event event = objectMapper.readValue(eventJson, Event.class);
            itemsToSave.add(event);
        }

        dynamoDBMapper.batchSave(itemsToSave);
    }
}
