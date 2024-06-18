package bookiepedia.activities;

import bookiepedia.activities.requests.AddBetToHistoryRequest;
import bookiepedia.activities.results.AddBetToHistoryResult;
import bookiepedia.converters.ModelConverter;
import bookiepedia.dynamodb.BetDAO;
import bookiepedia.dynamodb.bettinghistory.WeeklyHistoryDAO;
import bookiepedia.dynamodb.models.Bet;
import bookiepedia.dynamodb.models.bettinghistory.WeeklyHistory;
import bookiepedia.models.BetModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddBetToHistoryActivityTest {

    @Mock
    private BetDAO mockBetDAO;

    @Mock
    private WeeklyHistoryDAO mockWeeklyHistoryDAO;

    @InjectMocks
    private AddBetToHistoryActivity addBetToHistoryActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testHandleRequest() {
        // Mock input request
        AddBetToHistoryRequest request = createSampleRequest();

        // Mock data for WeeklyHistory
        WeeklyHistory mockWeeklyHistory = createMockWeeklyHistory();

        // Mock behavior of DAOs
        when(mockWeeklyHistoryDAO.getWeeklyHistory(any())).thenReturn(mockWeeklyHistory);
        doNothing().when(mockWeeklyHistoryDAO).saveWeeklyHistory(any());

        // Execute the method under test
        AddBetToHistoryResult result = addBetToHistoryActivity.handleRequest(request);

        // Verify DAO interactions
        verify(mockWeeklyHistoryDAO, times(1)).getWeeklyHistory(any());
        verify(mockWeeklyHistoryDAO, times(1)).saveWeeklyHistory(any());

        // Verify interactions with BetDAO
        verify(mockBetDAO, times(1)).saveBet(any(Bet.class));

        // Capture the argument passed to saveBet method
        ArgumentCaptor<Bet> betCaptor = ArgumentCaptor.forClass(Bet.class);
        verify(mockBetDAO).saveBet(betCaptor.capture());

        // Assert the captured Bet object matches the expected data from request
        Bet capturedBet = betCaptor.getValue();
        assertEquals(request.getWeeklyHistoryId(), capturedBet.getWeeklyHistoryId());
        assertEquals(request.getBetId(), capturedBet.getBetId());
        assertEquals(request.getUserId(), capturedBet.getUserId());
        assertEquals(request.getEventId(), capturedBet.getEventId());
        assertEquals(request.getAmountWagered(), capturedBet.getAmountWagered());
        assertEquals(request.getOdds(), capturedBet.getOdds());
        assertEquals(request.getTeamBetOn(), capturedBet.getTeamBetOn());
        assertEquals(request.getProjection(), capturedBet.getProjection());
        assertEquals(request.getBettingMarket(), capturedBet.getBettingMarket());
        assertEquals(request.getBookmakerId(), capturedBet.getBookmakerId());
        assertEquals(request.getDatePlaced(), capturedBet.getDatePlaced());
        assertEquals(request.getGainOrLoss(), capturedBet.getGainOrLoss());
        assertEquals(request.getTeamHome(), capturedBet.getTeamHome());
        assertEquals(request.getScoreHome(), capturedBet.getScoreHome());
        assertEquals(request.getTeamHomeLogo(), capturedBet.getTeamHomeLogo());
        assertEquals(request.getTeamAway(), capturedBet.getTeamAway());
        assertEquals(request.getScoreAway(), capturedBet.getScoreAway());
        assertEquals(request.getTeamAwayLogo(), capturedBet.getTeamAwayLogo());
        assertEquals(request.getTeamWinner(), capturedBet.getTeamWinner());
        assertEquals(request.getScoreTotal(), capturedBet.getScoreTotal());
        assertEquals(request.getEventName(), capturedBet.getEventName());
        assertEquals(request.getEventHeadline(), capturedBet.getEventHeadline());
        assertEquals(request.getEventDate(), capturedBet.getEventDate());
        assertEquals(request.getEventStatus(), capturedBet.getEventStatus());

        // Additional assertions can be added based on your requirements
        // Assert the result returned by handleRequest if needed
        List<BetModel> expectedBetModels = new ModelConverter().toBetModelList(mockWeeklyHistory.getWeeklyBetList());
        assertEquals(expectedBetModels.size(), result.getBetList().size());
    }

    private AddBetToHistoryRequest createSampleRequest() {
        return AddBetToHistoryRequest.builder()
                .withWeeklyHistoryId("weeklyHistory123")
                .withBetId("bet123")
                .withUserId("user123")
                .withEventId("event123")
                .withAmountWagered(100.00)
                .withOdds(2.50)
                .withTeamBetOn("Team A")
                .withProjection(250.00)
                .withBettingMarket("Market A")
                .withBookmakerId("bookmaker123")
                .withDatePlaced("2023-01-01")
                .withGainOrLoss(50.00)
                .withTeamHome("Team A")
                .withScoreHome(2)
                .withTeamHomeLogo("teamA_logo.png")
                .withTeamAway("Team B")
                .withScoreAway(1)
                .withTeamAwayLogo("teamB_logo.png")
                .withTeamWinner("Team A")
                .withScoreTotal(3)
                .withEventName("Event Name")
                .withEventHeadline("Event Headline")
                .withEventDate("2023-01-01")
                .withEventStatus("Scheduled")
                .build();
    }

    private WeeklyHistory createMockWeeklyHistory() {
        WeeklyHistory weeklyHistory = new WeeklyHistory();
        weeklyHistory.setWeeklyHistoryId("weeklyHistory123");
        weeklyHistory.setWeeklyBetList(new ArrayList<>()); // Initialize an empty list
        return weeklyHistory;
    }
}


