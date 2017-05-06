package interfata.ip.notifier.messenger;

import android.os.AsyncTask;

import java.io.IOException;


public class NetworkTask extends AsyncTask<Messenger, Void, String> {

    @Override
    protected String doInBackground(Messenger... params) {
        try {
            String s = params[0].makeRequest();
            System.out.println(s);
            return s;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
