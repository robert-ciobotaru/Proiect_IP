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

            try{
                client = (HttpURLConnection) url.openConnection();
                client.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
                client.setRequestProperty("Accept","*/*");
                client.setRequestMethod("POST");
                System.out.println("pas1");
                client.setDoOutput(true);
                client.setChunkedStreamingMode(0);
                OutputStream outputPost = new BufferedOutputStream(client.getOutputStream());
                System.out.println("pas2");
                writeStream(outputPost);
                readStream(client);
//                outputPost.flush();
  //              outputPost.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Salut");

        }

    private void writeStream(OutputStream outputPost) throws IOException {
        final JSONObject root = new JSONObject();
        String result;
        try{
            root.put("country",this.country);
            root.put("city",this.city);
            root.put("newsCrawler",this.newsCrawler);
            root.put("hazzardCrawler",this.hazzardCrawler);
            root.put("wheaterCrawler",this.wheaterCrawler);
            root.put("e-mail",this.mail);

        }
         catch (JSONException e) {
           Log.d("JWP","Can't format JSON" );
        }
        result=root.toString();


        outputPost.write(result.getBytes());

        outputPost.flush();
        outputPost.close();


    }

    private void readStream(HttpURLConnection client) throws IOException {

        System.out.println("Suntem in afisare");
        BufferedReader br = new BufferedReader(new InputStreamReader( client.getInputStream(),"utf-8"));
        String line = null;
        StringBuilder sb = new StringBuilder();

        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();
        System.out.println(""+sb.toString());
        client.disconnect();


    }





}