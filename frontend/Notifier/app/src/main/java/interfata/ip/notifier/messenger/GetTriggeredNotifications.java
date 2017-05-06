package interfata.ip.notifier.messenger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Vlad on 06.05.2017.
 */

public class GetTriggeredNotifications extends Messenger {

    private int userId;
    private static final String specificURL = "users/{userId}/notifications/triggered-notifications/";

    public GetTriggeredNotifications(String host, int port, String version, int userId) {
        super(host, port, version);
        this.userId = userId;
        this.requestURL = this.baseServerURL + specificURL.replace("{userId}", String.valueOf(userId));
    }

    @Override
    public JSONObject makeRequest() throws IOException, JSONException {
        URL url = new URL(requestURL);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader( urlConnection.getInputStream(),"utf-8"));

        String line = null;
        StringBuilder sb = new StringBuilder();

        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();

        return new JSONObject(sb.toString());
    }
}
