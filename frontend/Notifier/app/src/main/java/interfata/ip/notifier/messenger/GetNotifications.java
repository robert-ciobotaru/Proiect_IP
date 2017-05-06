package interfata.ip.notifier.messenger;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetNotifications extends Messenger {

    private int userId;
    private static final String getNotificationsEnd = "users/{userId}/notifications";

    public GetNotifications(String host, int port, String version, int userId) {
        super(host, port, version);
        this.userId = userId;
    }

    @Override
    public String makeRequest() throws IOException {
        String requestURL = this.baseServerURL + getNotificationsEnd.replace("{userId}", String.valueOf(userId));
        URL url = new URL(requestURL);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader( urlConnection.getInputStream(),"utf-8"));

        String line = null;
        StringBuilder sb = new StringBuilder();

        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();

        return sb.toString();
    }
}
