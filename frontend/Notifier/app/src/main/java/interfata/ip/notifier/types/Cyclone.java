package interfata.ip.notifier.types;

/**
 * Created by cschifirnet on 14-May-17.
 */

public class Cyclone extends Flood {
    public Cyclone(String alertLevel, String country, String time, String url, String title, String description) {
        super(alertLevel, country, time, url, title, description);
    }
}
