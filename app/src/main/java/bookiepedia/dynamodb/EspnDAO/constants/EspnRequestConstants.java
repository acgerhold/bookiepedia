package bookiepedia.dynamodb.EspnDAO.constants;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class EspnRequestConstants {

    // Specify league with 'v2'/sports/{sport}/{league}/scoreboard'
    // Specify date ranges with '?dates=YYYYMMDD-YYYYMMDD'
    // Ex. "https://site.api.espn.com/apis/site/v2/sports/basketball/nba/scoreboard?dates=20240501-20240531"

    // Leagues
    public static final String NBA = "basketball/nba";
    public static final String NHL = "hockey/nhl";

    // Date Formatting
    public static final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter yyyy_MM_dd = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Starting Date
    public static final ZoneId ZONE_ID = ZoneId.of("UTC-05:00");
    public static final LocalDateTime NOW = LocalDateTime.now(ZoneId.of("UTC-05:00"));
    public static final String START_DATE = NOW.format(yyyyMMdd);
    public static final String START_DATE_SPLIT = NOW.format(yyyy_MM_dd);

    // Ending Date
    public static final long RANGE_DAYS = 10;
    // On average, for most regular season games across major sports leagues, lines are released
    // for games 1-7 days in advance depending on the game's importance
    // Regular Season NBA/NHL - 1 to 2 days before game
    // Post Season NBA/NHL & special events - 3-7+

    public static String getStartDate() {
        LocalDateTime now = LocalDateTime.now(ZONE_ID);
        return now.format(yyyyMMdd);
    }

    public static String getEndDate() {
        LocalDateTime now = LocalDateTime.now(ZONE_ID);
        return now.plusDays(RANGE_DAYS).format(yyyyMMdd);
    }

}
