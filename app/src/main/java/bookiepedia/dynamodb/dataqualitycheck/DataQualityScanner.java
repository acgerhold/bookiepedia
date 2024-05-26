package bookiepedia.dynamodb.dataqualitycheck;

import bookiepedia.dynamodb.dataqualitycheck.exceptions.DataQualityException;
import bookiepedia.dynamodb.dataqualitycheck.exceptions.modelexceptions.EventDataQualityException;
import bookiepedia.dynamodb.dataqualitycheck.exceptions.modelexceptions.ScheduleDataQualityException;
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
    private final List<String> nullAttributes;
    private static final String INVALID_ATTRIBUTE = "Unavailable";

    public DataQualityScanner(String model, double threshold) {
        this.model = model;
        this.threshold = threshold;
        this.nullAttributes = new ArrayList<>();
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
            if (entry.getValue() == null) {
                // entry.getValue().equals(INVALID_ATTRIBUTE) works to count "Unavailable" instead of null (DataQualityScannerTest)
                // Need to find way
                nullAttributes.add(entry.getKey());
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
            }
            throw new DataQualityException(String.valueOf(dataQualityPercentage));
        }
        // Print any null/invalid attributes regardless of data quality %
        if (!nullAttributes.isEmpty()) {
            System.out.println("Invalid Attributes: " + nullAttributes);
        }

        printQualityPercentage();
    }

    public void printQualityPercentage() {
        System.out.println(dataQualityPercentage);
    }

}
