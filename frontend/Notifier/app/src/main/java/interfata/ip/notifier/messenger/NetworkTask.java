package interfata.ip.notifier.messenger;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class NetworkTask extends AsyncTask<Messenger, Void, JSONObject> {

    @Override
    protected JSONObject doInBackground(Messenger... params) {
        try {
            return params[0].makeRequest();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
