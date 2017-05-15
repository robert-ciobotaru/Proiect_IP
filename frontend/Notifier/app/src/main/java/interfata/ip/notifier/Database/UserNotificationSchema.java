package interfata.ip.notifier.Database;

import java.sql.Date;

/**
 * Created by edufcknd on 14/05/2017.
 */

public class UserNotificationSchema {
    private int id;
    private String text;
    private Date timestamp;
    private boolean repeatable;
    private int interval;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public int getInterval() {
        return interval;
    }

    public boolean isRepeatable() {

        return repeatable;
    }

    public Date getTimestamp() {

        return timestamp;
    }

    public void setText(String text) {

        this.text = text;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}