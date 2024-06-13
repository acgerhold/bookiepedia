package bookiepedia.dynamodb;

import bookiepedia.dynamodb.models.Event;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EventDAO {

    private final DynamoDBMapper dynamoDBMapper;

    @Inject
    public EventDAO(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public List<Event> getEvents(String scheduleId) {

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":scheduleId", new AttributeValue().withS(scheduleId));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("scheduleId = :scheduleId")
                .withExpressionAttributeValues(valueMap);

        PaginatedScanList<Event> eventList = dynamoDBMapper.scan(Event.class, scanExpression);

        return eventList;
    }
}
