package interfata.ip.notifier.messenger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public abstract class Messenger {

    private static final int port = 8080;
    private static final String host = "104.198.253.69";
    private static final String version = "v1";
    private static final String urlPattern = "http://{host}:{port}/{version}/";

    protected String baseServerURL;
    protected String requestURL;


    public Messenger() {
        this.baseServerURL = urlPattern
                .replace("{host}", host)
                .replace("{port}", String.valueOf(port))
                .replace("{version}", version);
    }

    String getHost() {
        return host;
    }

    String getVersion() {
        return version;
    }

    int getPort() {
        return port;
    }

    public abstract JSONObject makeRequest() throws IOException, JSONException;

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }
}
