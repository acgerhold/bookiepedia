package bookiepedia.dynamodb;

import bookiepedia.dynamodb.EspnDAO.constants.EspnRequestConstants;
import bookiepedia.dynamodb.models.Schedule;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;

public class ScheduleDAO {

    private final DynamoDBMapper dynamoDBMapper;

    @Inject
    public ScheduleDAO(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public Schedule getSchedule(String leagueId) {
        return this.dynamoDBMapper.load(Schedule.class, leagueId, EspnRequestConstants.START_DATE_SPLIT);
    }

    public void saveSchedule(Schedule schedule) {
        this.dynamoDBMapper.save(schedule);
    }
}
