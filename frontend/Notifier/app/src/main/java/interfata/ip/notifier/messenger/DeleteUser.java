package interfata.ip.notifier.messenger;


/**
 * Created by edufcknd on 06/05/2017.
 */

public class DeleteUser extends DeleteRequest {

    private static final String specificURL = "users/{userId}";

    public DeleteUser(String host, int port, String version, int userId) {
        super(host, port, version);
        this.requestURL = baseServerURL + specificURL
                .replace("{userId}", String.valueOf(userId));
    }
}