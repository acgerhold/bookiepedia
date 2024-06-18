package bookiepedia.dynamodb;

import bookiepedia.dynamodb.models.Bet;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

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

    public void saveBet(Bet bet) {
        this.dynamoDBMapper.save(bet);
    }

}
