package interfata.ip.notifier.messenger;


/**
 * Created by edufcknd on 06/05/2017.
 */

public class DeleteUser extends DeleteRequest {

    private static final String specificURL = "users/{userId}";

    public DeleteUser(int userId) {
        this.requestURL = baseServerURL + specificURL
                .replace("{userId}", String.valueOf(userId));
    }
}