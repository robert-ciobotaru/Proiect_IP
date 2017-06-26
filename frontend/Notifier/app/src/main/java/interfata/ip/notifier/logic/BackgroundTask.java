package interfata.ip.notifier.logic;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcel;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

import interfata.ip.notifier.Database.NotificationTableData;
import interfata.ip.notifier.History;
import interfata.ip.notifier.R;
import interfata.ip.notifier.messenger.GetNotifications;
import interfata.ip.notifier.notifications.AlarmNotificationReceiver;


public class BackgroundTask extends IntentService {

    static String exampleRes = "{\n" +
            "    \"userNotificationsList\" : [\n" +
            "        {\n" +
            "            \"id\": 23,\n" +
            "            \"text\": \"Wake me up\",\n" +
            "            \"time\" : 1231245,\n" +
            "            \"repeatable\" : true,\n" +
            "            \"interval\" : 300\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 24,\n" +
            "            \"text\": \"Get the kid\",\n" +
            "            \"time\" : 123245,\n" +
            "            \"repeatable\" : false,\n" +
            "            \"interval\" : 400\n" +
            "        }\n" +
            "    ],\n" +
            "    \"weatherNotificationsList\" : [\n" +
            "        {\n" +
            "            \"location\" : {\n" +
            "                       \"city\" : \"Iasi\",\n" +
            "                       \"country\" : \"Romania\"\n" +
            "                      },\n" +
            "           \"text\": \"The weather is bad\"\n" +
            "        },\n" +
            "         {\n" +
            "            \"location\" : {\n" +
            "                       \"city\" : \"Iasi\",\n" +
            "                       \"country\" : \"Romania\"\n" +
            "                      },\n" +
            "           \"text\": \"The weather is bad\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"hazzardNotifications\" : {\n" +
            "        \"earthquakesList\" : [\n" +
            "            {\n" +
            "                \"magnitude\" : 2,\n" +
            "                \"place\": \"Iasi, Romania\",\n" +
            "                \"time\" : \"2017-05-06T00:21:40\",\n" +
            "                \"url\" :  \"http://link.ro\",\n" +
            "                \"title\" : \"The amaizing earthquake\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"hazzard\" : {\n" +
            "\n" +
            "            \"floodsList\" : [\n" +
            "                {\n" +
            "                    \"alert-level\" : \"BIG\",\n" +
            "                    \"country\": \"Romania\",\n" +
            "                    \"time\" : \"2017-05-06T00:21:40\",\n" +
            "                    \"url\" :  \"http://link.ro\",\n" +
            "                    \"title\" : \"The amaizing earthquake\",\n" +
            "                    \"description\" : \"Description for the flood\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"cyclonesList\" : [\n" +
            "                 {\n" +
            "                    \"alert-level\" : \"BIG\",\n" +
            "                    \"country\": \"Romania\",\n" +
            "                    \"time\" : \"2017-05-06T00:21:40\",\n" +
            "                    \"url\" :  \"http://link.ro\",\n" +
            "                    \"title\" : \"The amaizing earthquake\",\n" +
            "                    \"description\" : \"Description for the cyclone\"\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    },\n" +
            "    \"newsNotificationsList\" : [\n" +
            "        {\n" +
            "            \"author\": \"Some guy\",\n" +
            "            \"title\" : \"The weather is bad\",\n" +
            "            \"description\" : \"The weaher is really bad\",\n" +
            "            \"url\" : \"http://link.ro\",\n" +
            "            \"urlToImage\" : \"http://...\",\n" +
            "            \"publishedAt\" : \"some location\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"author\": \"Some guy\",\n" +
            "            \"title\" : \"The weather is bad\",\n" +
            "            \"description\" : \"The weaher is really bad\",\n" +
            "            \"url\" : \"http://link.ro\",\n" +
            "            \"urlToImage\" : \"http://...\",\n" +
            "            \"publishedAt\" : \"some location\"\n" +
            "        }\n" +
            "    ]\n" +
            "}\n";


    public BackgroundTask() {
        super("defaultName");
    }


    private String parseObject(JSONObject obj) throws JSONException {
        StringBuilder result = new StringBuilder();
        for(int j = 0; j< obj.names().length(); j++){
            if (obj.get(obj.names().getString(j)) instanceof JSONObject){
                result.append(
                        obj.names().getString(j))
                        .append(": ")
                        .append(parseObject((JSONObject)obj.get(obj.names().getString(j))))
                        .append(", ");
            }
            else {
                result.append(
                        obj.names().getString(j).toUpperCase())
                        .append(": ")
                        .append(obj.get(obj.names().getString(j)))
                        .append(", ");
            }
        }
        return result.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void showNotification(JSONObject notification, String title) throws JSONException {
        // show notifications and store them into the database
        String text;
        String time;

        try {
            text = parseObject(notification);
        } catch (JSONException e) {
            text = notification.toString();
        }
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle(title)
                        .setContentText(text);
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, History.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(History.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(new Random().nextInt(), mBuilder.build());
        // add the notification to the database

        try {
            time = notification.getString("time");
        } catch (JSONException e) {
            time = "Now";
        }

        NotificationTableData ntd = new NotificationTableData(text, time, title);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    void showNotificationsList(JSONArray notificationsList, String category) throws JSONException {
        // shows every notification in the list
        JSONObject notification;
        for(int i = 0; i < notificationsList.length(); i++) {
            notification = notificationsList.getJSONObject(i);
            showNotification(notification, category);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // Show the notifications then add them to the database
        JSONObject obj;
        String reqResult;
        GetNotifications notificationGetter = new GetNotifications(2);

        while(true) {
            try {
                obj = notificationGetter.makeRequest();
                System.out.println(obj);
                obj = new JSONObject(exampleRes);
                showNotificationsList(obj.getJSONArray("weatherNotificationsList"), "Weather");
                showNotificationsList(obj.getJSONObject("hazzardNotifications").getJSONArray("earthquakesList"), "Earthquake");
                showNotificationsList(obj.getJSONObject("hazzardNotifications").getJSONObject("hazzard").getJSONArray("floodsList"), "Flood");
                showNotificationsList(obj.getJSONObject("hazzardNotifications").getJSONObject("hazzard").getJSONArray("cyclonesList"), "Cyclone");
                Thread.sleep(60000);
            } catch (JSONException | InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}