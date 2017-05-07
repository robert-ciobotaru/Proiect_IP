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

public class DeleteNotification extends DeleteRequest {

    private static final String specificURL = "users/{userId}/notifications/{notificationId}";

    public DeleteNotification(String host, int port, String version, int userId, int notificationId) {
        super(host, port, version);
        this.requestURL = baseServerURL + specificURL
                        .replace("{userId}", String.valueOf(userId))
                        .replace("{notificationId}", String.valueOf(notificationId));
    }
}