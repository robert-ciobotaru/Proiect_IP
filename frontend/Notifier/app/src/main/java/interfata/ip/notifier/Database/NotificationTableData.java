package interfata.ip.notifier.Database;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by Vlad on 12.05.2017.
 */

public class NotificationTableData {
    private int id;
    private String text;
    private String time;
    private String img;

    public NotificationTableData() {
    }

    public NotificationTableData(String text, String time, String img) {
        this.id = id;
        this.text = text;
        this.time = time;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void addText(String tmp) {
        this.text += tmp;
    }

    public NotificationTableData construct(JSONObject notification) throws JSONException {
        this.setText("");
        parseObject(notification);
        Log.d("test", this.getText());
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        this.setTime(ts);
        Log.d("test", this.getTime());
        return this;
    }

    private void parseObject(JSONObject obj) throws JSONException {
        for(int j = 0; j< obj.names().length(); j++){
            if (obj.get(obj.names().getString(j)) instanceof JSONObject){
                parseObject( (JSONObject) obj.get(obj.names().getString(j)) );
            }
            this.addText(obj.names().getString(j).toUpperCase() + ": " + obj.get(obj.names().getString(j)) + '\n');
        }
    }
}
