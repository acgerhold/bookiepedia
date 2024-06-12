package bookiepedia.dynamodb.assets;

import bookiepedia.dynamodb.models.assets.Team;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;

public class TeamDAO {

    private final DynamoDBMapper dynamoDBMapper;

    @Inject
    public TeamDAO(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public Team getTeam(String teamId) {
        return this.dynamoDBMapper.load(Team.class, teamId);
    }
    public void saveTeam(Team team) {
        this.dynamoDBMapper.save(team);
    }
}
