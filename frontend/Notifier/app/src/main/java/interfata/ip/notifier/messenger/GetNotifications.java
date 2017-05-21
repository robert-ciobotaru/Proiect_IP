package interfata.ip.notifier.messenger;


public class GetNotifications extends GetRequest {

    private static final String specificURL = "users/{userId}/notifications";

    public GetNotifications(int userId) {
        this.requestURL = baseServerURL + specificURL.replace("{userId}", String.valueOf(userId));
    }

}
