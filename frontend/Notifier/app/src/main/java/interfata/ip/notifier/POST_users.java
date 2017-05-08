package interfata.ip.notifier;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Vlad on 05.05.2017.
 */

public class POST_users {
    String id;
    String country;
    String city;
    String newsCrawler;
    String hazzardCrawler;
    String wheaterCrawler;
    String mail;


    public POST_users(String country, String city, String newsCrawler, String hazzardCrawler, String wheaterCrawler, String mail) throws MalformedURLException {
        this.country = country;
        this.city = city;
        this.newsCrawler = newsCrawler;
        this.hazzardCrawler = hazzardCrawler;
        this.wheaterCrawler = wheaterCrawler;
        this.mail = mail;
        Post();
    }

    public void Post() throws MalformedURLException {

        URL url = new URL("http://104.198.253.69:8080/v1/users/");
        HttpURLConnection client = null;

        try {
            client = (HttpURLConnection) url.openConnection();
            client.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
            client.setRequestProperty("Accept", "*/*");
            client.setRequestMethod("POST");
            System.out.println("pas1");
            client.setDoOutput(true);
            client.setChunkedStreamingMode(0);
            OutputStream outputPost = new BufferedOutputStream(client.getOutputStream());
            System.out.println("pas2");
            writeStream(outputPost);
           JSONObject cd = readStream(client);
            System.out.println(cd.toString());
//                outputPost.flush();
            //              outputPost.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("Salut");

    }

    private void writeStream(OutputStream outputPost) throws IOException {
        final JSONObject root = new JSONObject();
        String result;
        try {
            root.put("country", this.country);
            root.put("city", this.city);
            root.put("newsCrawler", this.newsCrawler);
            root.put("hazzardCrawler", this.hazzardCrawler);
            root.put("wheaterCrawler", this.wheaterCrawler);
            root.put("e-mail", this.mail);

        } catch (JSONException e) {
            Log.d("JWP", "Can't format JSON");
        }
        result = root.toString();


        outputPost.write(result.getBytes());

        outputPost.flush();
        outputPost.close();


    }

    private JSONObject readStream(HttpURLConnection client) throws IOException, JSONException {
        int res;
        res = client.getResponseCode();
       // System.out.println(requestURL);
        System.out.println(res);

        StringBuilder sb = new StringBuilder();


        if (422 == res) {
            sb.append("{" + "error: 'Invalid User'" + "}");
            return new JSONObject(sb.toString());
        }
        if (500 == res) {
            sb.append("{" + "error: 'Internal server error'" + "}");
            return new JSONObject(sb.toString());
        }
        if (503 == res) {
            sb.append("{" + "error: 'The server is currently unavailable'" + "}");
            return new JSONObject(sb.toString());
        }
        if (200 == res) {
            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream(), "utf-8"));

            String line = null;


            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();

            System.out.print(sb);
            return new JSONObject(sb.toString());
        }
        sb.append("{" + "error: 'Unknown error'" + "}");
        return new JSONObject(sb.toString());


        }


    }
