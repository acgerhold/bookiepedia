package bookiepedia.dynamodb;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import bookiepedia.dynamodb.models.assets.League;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

public class LeagueDAO {
    
    private final DynamoDBMapper dynamoDBMapper;

    @Inject
    public LeagueDAO(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    // This is just an optional GSI in case I want to query by sport on back-end
    public List<League> getLeagues(String sportName) {
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":sportName", new AttributeValue().withS(sportName));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
            .withFilterExpression("sportName = :sportName")
            .withExpressionAttributeValues(valueMap);

        PaginatedScanList<League> leagueList = dynamoDBMapper.scan(League.class, scanExpression);
        
        return leagueList.stream()
            .sorted(Comparator.comparing(League::getSeasonStatusId))
            .collect(Collectors.toList());
    }
}
