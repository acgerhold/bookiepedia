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

public class FetchScheduleActivity {

    private final DynamoDBMapper dynamoDBMapper;
    private final EspnDAO espnDAO;

    @Inject
    public FetchScheduleActivity(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.espnDAO = new EspnDAO(dynamoDBMapper);
    }

    public FetchScheduleResult handleRequest(final FetchScheduleRequest request) {
        try {
            String startDate = EspnRequestConstants.getStartDate();
            String endDate = EspnRequestConstants.getEndDate();

            String nbaURL = String.format("https://site.api.espn.com/apis/site/v2/sports/%s/scoreboard?dates=%s-%s",
                    EspnRequestConstants.NBA, startDate, endDate);
            String nhlURL = String.format("https://site.api.espn.com/apis/site/v2/sports/%s/scoreboard?dates=%s-%s",
                    EspnRequestConstants.NHL, startDate, endDate);

            System.out.println(startDate + "-" + endDate);

            JSONObject nbaResponse = espnDAO.requestQuery(nbaURL);
            processResponse(nbaResponse);

            JSONObject nhlResponse = espnDAO.requestQuery(nhlURL);
            processResponse(nhlResponse);

            return FetchScheduleResult.builder()
                    .withMessage("Schedules for : " + startDate + " - Successfully created for NBA & NHL")
                    .build();

        } catch (IOException ioe) {
            throw new RuntimeException("Error occurred attempting to fetch NBA & NHL schedules");
        }
    }

    private void processResponse(JSONObject response) throws JsonProcessingException {
        String scheduleJson = espnDAO.extractSchedule(response);
        Schedule schedule = new ObjectMapper().readValue(scheduleJson, Schedule.class);
        dynamoDBMapper.save(schedule);

        List<String> eventListJson = espnDAO.extractEvents(response);
        for (String eventJson : eventListJson) {
            Event event = new ObjectMapper().readValue(eventJson, Event.class);
            dynamoDBMapper.save(event);
        }

    }
}
