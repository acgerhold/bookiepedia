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
    public static final DateTimeFormatter mm_dd_yy = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    // Starting Date
    public static final LocalDateTime NOW = LocalDateTime.now(ZoneId.of("UTC-05:00"));
    public static final String START_DATE = "20240606";
    // NOW.format(yyyyMMdd)

    // Ending Date
    public static final long RANGE_DAYS = 14;
    public static final String END_DATE = NOW.plusDays(RANGE_DAYS).format(yyyyMMdd);

}
