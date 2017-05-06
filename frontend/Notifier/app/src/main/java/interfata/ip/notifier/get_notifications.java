package interfata.ip.notifier;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Codrin on 5/5/2017.
 */

public class get_notifications {
    public String make_request(Integer userID) throws IOException {
        String response = "Success";
        Integer.toString(userID);
        String newURL = "http://104.198.253.69:8080/v1/users/{userID}/notifications";
        newURL = newURL.replace("{userID}", String.valueOf(userID));

        URL url = null;
        try {
            url = new URL(newURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection client = null;
        try {
            client = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            client.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        client.setDoOutput(true);

        OutputStream outputPost = new BufferedOutputStream(client.getOutputStream());
        writeStream(outputPost);

        outputPost.flush();
        outputPost.close();

        return response;
    }

    private void writeStream(OutputStream outputPost) {
        System.out.println(outputPost.toString());
    }
}
