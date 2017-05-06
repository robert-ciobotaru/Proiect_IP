package interfata.ip.notifier.messenger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by edufcknd on 06/05/2017.
 */

public class DeleteNotification extends Messenger {

    private int userId;
    private int notificationId;

    private static final String specificURL = "users/{userId}/notifications/{notificationId}";

    public DeleteNotification(String host, int port, String version, int userId, int notificationId) {

        super(host, port, version);
        this.userId = userId;
        this.notificationId = notificationId;
        this.requestURL = this.baseServerURL
                        + specificURL
                        .replace("{userId}", String.valueOf(userId))
                        .replace("{notificationId}", String.valueOf(notificationId));
    }

    @Override
    public JSONObject makeRequest() throws IOException, JSONException {

        int res;

        URL url = new URL(requestURL);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        urlConnection.setRequestMethod("DELETE");
        urlConnection.setRequestProperty("charset", "utf-8");
        urlConnection.setUseCaches (false);
        urlConnection.setDoOutput(true);

        res = urlConnection.getResponseCode();
        System.out.println(requestURL);
        System.out.println(res);

        StringBuilder sb = new StringBuilder();


        if (422 == res) {
            sb.append("{"+"error: 'Invalid notification id / Invalid Notification user'"+"}");
            return new JSONObject(sb.toString());
        }
        if (500 == res) {
            sb.append("{"+"error: 'Internal server error'"+"}");
            return new JSONObject(sb.toString());
        }
        if (503 == res) {
            sb.append("{"+"error: 'The server is currently unavailable'"+"}");
            return new JSONObject(sb.toString());
        }
        if (200 == res) {
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));

            String line = null;


            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();

            System.out.print(sb);
            return new JSONObject(sb.toString());
        }
        sb.append("{"+"error: 'Unknown error'"+"}");
        return new JSONObject(sb.toString());
    }
}