package bookiepedia.dynamodb;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Maybe use this as an interface later
public class DataQualityScanner {

    private final String model;
    private float dataQualityPercentage;
    private final List<String> nullAttributes;

    public DataQualityScanner(String model) {
        this.model = model;
        this.nullAttributes = new ArrayList<>();
    }

    public void scan() {
        // Counts number of null attributes for model JSONs
        Map<String, Object> map = new JSONObject(model).toMap();
        int counter = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() == null) {
                nullAttributes.add(entry.getKey());
                counter++;
            }
        }

        this.dataQualityPercentage = 100 - ((float) counter / map.keySet().size() * 100);

        printQualityPercentage();
    }

    public void printQualityPercentage() {
        System.out.println(dataQualityPercentage);
    }

}
