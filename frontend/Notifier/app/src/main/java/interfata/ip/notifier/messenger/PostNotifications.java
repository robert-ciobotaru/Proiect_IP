package interfata.ip.notifier.messenger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by cschifirnet on 07-May-17.
 */

public class PostNotifications extends Messenger {

    private static final String specificURL = "users/{userId}/notifications";

    private String text;
    private int time;
    private boolean repeatable;
    private int interval;

    private JSONObject obj;

    public PostNotifications(int userId, String text, int time, boolean repeatable, int interval) {
        this.text = text;
        this.time = time;
        this.repeatable = repeatable;
        this.interval = interval;

        this.requestURL = baseServerURL + specificURL.replace("{userId}", String.valueOf(userId));
        try {
            this.obj = makeJSON();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject makeRequest() throws IOException, JSONException {
        URL url = new URL(requestURL);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        urlConn.setRequestProperty("Content-Type", "application/json");
        urlConn.setRequestProperty("charset", "utf-8");
        urlConn.setRequestMethod("POST");
        urlConn.setUseCaches (false);
        urlConn.setDoOutput(true);

        OutputStreamWriter osw = new OutputStreamWriter(urlConn.getOutputStream());
        osw.write(obj.toString());
        osw.flush();
        osw.close();

        int res = urlConn.getResponseCode();

        if (400 == res) {
            return new JSONObject("{error: 'Data for creating new user is invalid'}");
        }
        if (500 == res) {
            return new JSONObject("{error: 'Internal server error'}");
        }
        if (503 == res) {
            return new JSONObject("{error: 'The server is currently unavailable'}");
        }
        if (200 == res) {
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "utf-8"));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();

            return new JSONObject(sb.toString());
        }
        return new JSONObject("{error: 'Unknown error'}");
    }

    private JSONObject makeJSON() throws JSONException {
        String result = "{" +
                "\"text\":\"{text}\"," +
                "\"time\":{time}," +
                "\"repeatable\":{repeatable}," +
                "\"interval\":{interval}" +
                "}";
        return new JSONObject(
                result.replace("{text}", text)
                      .replace("{time}", String.valueOf(time))
                      .replace("{repeatable}", String.valueOf(repeatable))
                      .replace("{interval}", String.valueOf(interval))
        );
    }
}
