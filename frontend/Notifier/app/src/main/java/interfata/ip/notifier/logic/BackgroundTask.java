package interfata.ip.notifier.logic;

import android.app.Service;
import android.os.IBinder;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import interfata.ip.notifier.messenger.GetNotifications;
import interfata.ip.notifier.types.WeatherNotification;


public class BackgroundTask extends Service {

    String exampleRes = "{\n" +
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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful

        JSONObject obj, weatherNotification, earthquakeNotification, floodNotification, cycloneNotification;
        JSONArray weatherNotificationsList, earthquakesNotificationsList, floodsNotificationsList, cyclonesNotificationsList;
        String reqResult;
        GetNotifications notificationGetter = new GetNotifications(2);

        while(true) {
            try {
                obj = new JSONObject(exampleRes);
                weatherNotificationsList = obj.getJSONArray("weatherNotificationsList");
                for(int i = 0; i < weatherNotificationsList.length(); i++) {
                    weatherNotification = weatherNotificationsList.getJSONObject(i);
                    System.out.println(weatherNotification.toString());
                }

                earthquakesNotificationsList = obj.getJSONObject("hazzardNotifications").getJSONArray("earthquakesList");
                for(int i = 0; i < earthquakesNotificationsList.length(); i++) {
                    earthquakeNotification = earthquakesNotificationsList.getJSONObject(i);
                    System.out.println(earthquakeNotification.toString());
                }

                floodsNotificationsList = obj.getJSONObject("hazzardNotifications").getJSONObject("hazzard").getJSONArray("floodsList");
                for(int i = 0; i < floodsNotificationsList.length(); i++) {
                    floodNotification = floodsNotificationsList.getJSONObject(i);
                    System.out.println(floodNotification);
                }

                cyclonesNotificationsList = obj.getJSONObject("hazzardNotifications").getJSONObject("hazzard").getJSONArray("cyclonesList");
                for(int i = 0; i < cyclonesNotificationsList.length(); i++) {
                    cycloneNotification = cyclonesNotificationsList.getJSONObject(i);
                    System.out.println(cycloneNotification);
                }

                Thread.sleep(3000);
            } catch (JSONException | InterruptedException e) {
                return Service.START_NOT_STICKY;
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }

}