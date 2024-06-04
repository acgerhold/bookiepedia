package bookiepedia.dynamodb.dataqualitycheck;

import bookiepedia.exceptions.dataqualityexception.DataQualityException;
import bookiepedia.exceptions.dataqualityexception.modelexceptions.EventDataQualityException;
import bookiepedia.exceptions.dataqualityexception.modelexceptions.LeagueDataQualityException;
import bookiepedia.exceptions.dataqualityexception.modelexceptions.ScheduleDataQualityException;
import bookiepedia.exceptions.dataqualityexception.modelexceptions.TeamDataQualityException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// Maybe use this as an interface later
public class DataQualityScanner {

    private double dataQualityPercentage;
    private final String model;
    private final double threshold;
    private final List<String> invalidAttributes;
    private static final String INVALID_ATTRIBUTE_REPLACER = "Unavailable";

    public DataQualityScanner(String model, double threshold) {
        this.model = model;
        this.threshold = threshold;
        this.invalidAttributes = new ArrayList<>();
    }

    public void scan() {

        // Use StackWalker to determine which method from ESPNdao called this method
        StackWalker stackWalker = StackWalker.getInstance();
        String method = "";
        Optional<String> caller = stackWalker.walk(frames -> frames
                .skip(1)
                .findFirst()
                .map(StackWalker.StackFrame::getMethodName));
        if (caller.isPresent()) {
            method = caller.get();
        }

        // Count each null/invalid attribute in model and add attribute name to 'nullAttributes' list
        Map<String, Object> map = new JSONObject(model).toMap();
        int counter = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue().equals(INVALID_ATTRIBUTE_REPLACER) || entry.getValue().equals(-1)) {
                invalidAttributes.add(entry.getKey());
                counter++;
            }
        }

        // Set this instance's data quality %
        this.dataQualityPercentage = 100 - ((double) counter / map.keySet().size() * 100);

        // If data quality % is below threshold throw 'DataQualityException' based on calling method
        if (dataQualityPercentage < threshold) {
            switch (method) {
                case "extractSchedule":
                    throw new ScheduleDataQualityException(String.valueOf(dataQualityPercentage));
                case "extractEvents":
                    throw new EventDataQualityException(String.valueOf(dataQualityPercentage));
                case "extractTeam":
                    throw new TeamDataQualityException(String.valueOf(dataQualityPercentage));
                case "extractLeague":
                    throw new LeagueDataQualityException(String.valueOf(dataQualityPercentage));
            }

            throw new DataQualityException(String.valueOf(dataQualityPercentage));
        }

        // Print any null/invalid attributes regardless of data quality %
        if (!invalidAttributes.isEmpty()) {
            System.out.println("Invalid Attributes: " + invalidAttributes);
        }

        printQualityPercentage();
    }

    public void printQualityPercentage() {
        System.out.println("Data Quality - " + dataQualityPercentage + "%");
    }

    public double getQualityPercentage() {
        return this.dataQualityPercentage;
    }
}
