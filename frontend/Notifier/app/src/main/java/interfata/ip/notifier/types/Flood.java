package interfata.ip.notifier.types;

/**
 * Created by cschifirnet on 14-May-17.
 */

public class Flood {
    String alertLevel;
    String country;
    String time;
    String url;
    String title;
    String description;

    public Flood(String alertLevel, String country, String time, String url, String title, String description) {
        this.alertLevel = alertLevel;
        this.country = country;
        this.time = time;
        this.url = url;
        this.title = title;
        this.description = description;
    }

    public String getAlertLevel() {
        return alertLevel;
    }

    public void setAlertLevel(String alertLevel) {
        this.alertLevel = alertLevel;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
