package interfata.ip.notifier.messenger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cschifirnet on 07-May-17.
 */

public class PostUsers extends Messenger {

    private static final String specificURL = "users";

    private String city;
    private String country;
    private String email;
    private boolean news;
    private boolean hazards;
    private boolean weather;

    private JSONObject obj;


    public PostUsers(String city, String country, String email, boolean news, boolean hazards, boolean weather) {
        this.city = city;
        this.country = country;
        this.email = email;
        this.news = news;
        this.hazards = hazards;
        this.weather = weather;
        this.requestURL = baseServerURL + specificURL;
        try {
            this.obj = makeJSON();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject makeRequest() throws IOException, JSONException {
        URL url = new URL(requestURL);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        urlConn.setRequestProperty("Content-Type", "application/json");
        urlConn.setRequestProperty("charset", "utf-8");
        urlConn.setRequestMethod("POST");
        urlConn.setUseCaches (false);
        urlConn.setDoOutput(true);

        OutputStreamWriter osw = new OutputStreamWriter(urlConn.getOutputStream());
        osw.write(obj.toString());
        osw.flush();
        osw.close();

        int res = urlConn.getResponseCode();

        if (400 == res) {
            return new JSONObject("{error: 'Data for creating new user is invalid'}");
        }
        if (500 == res) {
            return new JSONObject("{error: 'Internal server error'}");
        }
        if (503 == res) {
            return new JSONObject("{error: 'The server is currently unavailable'}");
        }
        if (200 == res) {
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "utf-8"));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();

            return new JSONObject(sb.toString());
        }
        return new JSONObject("{error: 'Unknown error'}");
    }

    private JSONObject makeJSON() throws JSONException {
        String result = "{" +
                "\"country\":\"{country}\"," +
                "\"city\":\"{city}\"," +
                "\"email\":\"{email}\"," +
                "\"newsCrawler\":{news}," +
                "\"hazardCrawler\":{hazards}," +
                "\"weatherCrawler\":{weather}" +
                "}";
        return new JSONObject(
                result.replace("{country}", country)
                      .replace("{city}", city)
                      .replace("{email}", email)
                      .replace("{news}", String.valueOf(news))
                      .replace("{hazards}", String.valueOf(hazards))
                      .replace("{weather}", String.valueOf(weather))
        );
    }
}
