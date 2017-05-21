package interfata.ip.notifier.messenger;


/**
 * Created by Vlad on 06.05.2017.
 */

public class GetReminders extends GetRequest {

    private static final String specificURL = "users/{userId}/reminders";

    public GetReminders(int userId) {
        this.requestURL = baseServerURL + specificURL.replace("{userId}", String.valueOf(userId));
    }
}
