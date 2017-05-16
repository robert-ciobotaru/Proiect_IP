package interfata.ip.notifier.types;


public class UserNotification {
    private boolean repeatable;

    private int id;
    private int time;
    private int interval;

    private String text;

    public UserNotification(boolean repeatable, int id, int time, int interval, String text) {
        this.repeatable = repeatable;
        this.id = id;
        this.time = time;
        this.interval = interval;
        this.text = text;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
