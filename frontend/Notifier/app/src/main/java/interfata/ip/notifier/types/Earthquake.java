package interfata.ip.notifier.types;

/**
 * Created by cschifirnet on 14-May-17.
 */

public class Earthquake {
    int magnitude;
    String place;
    String time;
    String url;
    String title;

    public Earthquake(int magnitude, String place, String time, String url, String title) {
        this.magnitude = magnitude;
        this.place = place;
        this.time = time;
        this.url = url;
        this.title = title;
    }

    public int getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(int magnitude) {
        this.magnitude = magnitude;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
