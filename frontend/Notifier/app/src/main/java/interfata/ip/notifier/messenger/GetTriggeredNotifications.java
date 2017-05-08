package interfata.ip.notifier.messenger;


/**
 * Created by Vlad on 06.05.2017.
 */

public class GetTriggeredNotifications extends GetRequest {

    private static final String specificURL = "users/{userId}/notifications/triggered-notifications/";

    public GetTriggeredNotifications(int userId) {
        this.requestURL = baseServerURL + specificURL.replace("{userId}", String.valueOf(userId));
    }
}
