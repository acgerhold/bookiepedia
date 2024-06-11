package bookiepedia.dynamodb;

import bookiepedia.dynamodb.models.Schedule;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;

public class ScheduleDAO {

    private final DynamoDBMapper dynamoDBMapper;

    @Inject
    public ScheduleDAO(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public Schedule getSchedule(String scheduleId) {
        return this.dynamoDBMapper.load(Schedule.class, scheduleId);
    }

    public void saveSchedule(Schedule schedule) {
        this.dynamoDBMapper.save(schedule);
    }
}
