package interfata.ip.notifier.messenger;

import java.io.IOException;


public abstract class Messenger {

    private static final String urlPattern = "http://{host}:{port}/{version}/";

    protected String baseServerURL;
    protected String host;
    protected String version;
    protected int port;


    public Messenger(String host, int port, String version) {
        this.host = host;
        this.port = port;
        this.version = version;
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

    public abstract String makeRequest() throws IOException;
}
