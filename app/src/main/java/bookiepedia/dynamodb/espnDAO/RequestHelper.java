package bookiepedia.dynamodb.espnDAO;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class RequestHelper {

    private static final LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC-05:00"));
    private static final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final DateTimeFormatter mm_dd_yy = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final long RANGE_DAYS = 14;
    private static final String START_DATE = now.format(yyyyMMdd);
    private static final String END_DATE = now.plusDays(RANGE_DAYS).format(yyyyMMdd);
    private static final String INVALID_ATTRIBUTE_REPLACER = "Unavailable";

}
