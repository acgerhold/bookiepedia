package bookiepedia.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.json.JSONArray;
import org.json.JSONObject;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bookiepedia.activities.requests.FetchLeaguesRequest;
import bookiepedia.activities.results.FetchLeaguesResult;
import bookiepedia.converters.ModelConverter;
import bookiepedia.dynamodb.EspnDAO.EspnDAO;
import bookiepedia.dynamodb.models.assets.League;
import bookiepedia.models.assets.LeagueModel;

public class FetchLeaguesActivity {
    
    private final DynamoDBMapper dynamoDBMapper;
    private final EspnDAO espnDAO;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    public FetchLeaguesActivity(DynamoDBMapper dynamoDBMapper, EspnDAO espnDAO) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.espnDAO = espnDAO;
    }

    // This method builds the call to the ESPN API to display available leagues for the sport the user selected
    // It first passes the build HTTP request to the ESPN API using espnDAO.requestQuery()
    // Then hands off the response to be processed by dynamoDB and return league models to the user
    public FetchLeaguesResult handleRequest(final FetchLeaguesRequest request) {
        try {
            String url = String.format(
                "https://site.api.espn.com/apis/site/v2/leagues/dropdown?lang=en&region=us&calendartype=whitelist&limit=100&sport=%s",
                request.getSportName()
            );

            List<LeagueModel> leagueModels = processResponse(espnDAO.requestQuery(url), request.getSportName());

            return FetchLeaguesResult.builder()
                 .withMessage(String.format("Leagues for '%s' retrieved", request.getSportName()))
                 .withLeagueModels(leagueModels)
                 .build();

        } catch (IOException ioe) {
            System.err.println("Error occurred while processing ESPN API response: " + ioe.getMessage());
            throw new RuntimeException("Failled to process ESPN API response", ioe);
        }
    }

    // This method accepts the ESPN API response for available leagues for a selected sport
    // If a league ID doesn't exist in DynamoDB, a new League object is created and loaded
    // After this check, leagues are converted to models and returned
    private List<LeagueModel> processResponse(JSONObject response, String sportName) throws JsonProcessingException {
        // Pass blank List<League>, 'leagueKeys', to extractLeagues() to fill in primary keys for batchLoad()
        List<League> leagueKeys = new ArrayList<>();
        List<League> extractedLeagues = espnDAO.extractLeagues(response, leagueKeys, sportName);

        // Use batchLoad() to check for existing leagues in DynamoDB
        Map<String, List<Object>> existingLeaguesMap = dynamoDBMapper.batchLoad(leagueKeys);
        List<Object> existingLeagues = existingLeaguesMap.get("League");

        // Filter out leagues that already exist by comparing directly with existingLeagues
        List<League> newLeagues = extractedLeagues.stream()
            .filter(league -> existingLeagues.stream()
                .noneMatch(existing -> ((League) existing).getLeagueId().equals(league.getLeagueId())))
            .collect(Collectors.toList());

        // Save new leagues to DynamoDB
        if (!newLeagues.isEmpty()) {
            dynamoDBMapper.batchSave(newLeagues);
            System.out.println("New leagues saved to DynamoDB: " + newLeagues.size());
        }

        // Convert League entities to LeagueModel objects for abstraction
        ModelConverter modelConverter = new ModelConverter();
        return modelConverter.mapToLeagueModels(extractedLeagues);
    }
}
