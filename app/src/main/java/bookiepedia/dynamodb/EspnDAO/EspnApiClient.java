package bookiepedia.dynamodb.EspnDAO;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import org.json.JSONObject;

public class EspnApiClient {
    
    private final OkHttpClient client = new OkHttpClient().newBuilder()
        .retryOnConnectionFailure(true)
        .build();

    public JSONObject fetchData(String url) throws IOException {
        Request request = new Request.Builder()
            .url(url)
            .header("User-Agent", "Mozilla/5.0")
            .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return new JSONObject(response.body().string());
        }
    }
}
