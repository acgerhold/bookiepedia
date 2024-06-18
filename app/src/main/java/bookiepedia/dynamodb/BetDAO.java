package bookiepedia.dynamodb;

import bookiepedia.dynamodb.models.Bet;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;


import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class BetDAO {

    private final DynamoDBMapper dynamoDBMapper;

    @Inject
    public BetDAO(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public Bet getBet(String betId) {
        return this.dynamoDBMapper.load(Bet.class, betId);
    }

    public List<Bet> getBetsFromHistory(String weeklyHistoryId) {

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":weeklyHistoryId", new AttributeValue().withS(weeklyHistoryId));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("weeklyHistoryId = :weeklyHistoryId")
                .withExpressionAttributeValues(valueMap);

        PaginatedScanList<Bet> betList = dynamoDBMapper.scan(Bet.class, scanExpression);

        return betList.stream()
                .sorted(Comparator.comparing(Bet::getDatePlaced))
                .collect(Collectors.toList());
    }

    public void saveBet(Bet bet) {
        this.dynamoDBMapper.save(bet);
    }

}
