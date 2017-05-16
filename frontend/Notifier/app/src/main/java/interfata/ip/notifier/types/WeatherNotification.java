package interfata.ip.notifier.types;

/**
 * Created by cschifirnet on 14-May-17.
 */

public class WeatherNotification {

    private Location location;
    private String text;

    public WeatherNotification(Location location, String text) {
        this.location = location;
        this.text = text;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "WeatherNotification{" +
                "location=" + location +
                ", text='" + text + '\'' +
                '}';
    }
}
