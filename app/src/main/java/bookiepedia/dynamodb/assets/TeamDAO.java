package bookiepedia.dynamodb.assets;

import bookiepedia.dynamodb.models.assets.Team;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class TeamDAO {

    private final DynamoDBMapper dynamoDBMapper;

    public TeamDAO(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public void saveTeam(Team team) {
        this.dynamoDBMapper.save(team);
    }
}
