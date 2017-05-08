package interfata.ip.notifier;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

import interfata.ip.notifier.messenger.GetNotifications;
import interfata.ip.notifier.messenger.GetTriggeredNotifications;
import interfata.ip.notifier.messenger.Messenger;
import interfata.ip.notifier.messenger.NetworkTask;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("ShaPreferences", getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        boolean  firstTime=sharedPreferences.getBoolean("first", true);
        if(firstTime) {
            editor.putBoolean("first",false);
            //For commit the changes, Use either editor.commit(); or  editor.apply();.
            editor.commit();  ;
            Intent register = new Intent(getApplicationContext(), Register.class);
            startActivity(register);
        }else {
            Intent menu = new Intent(getApplicationContext(), Meniu.class);
            startActivity(menu);
        }






        /*Messenger m = new GetTriggeredNotifications("104.198.253.69", 8080, "v1", 2);
        NetworkTask t = new NetworkTask();
        t.execute(m);
        try {
            System.out.println(t.get());
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("EXCEPTIONNN");
            e.printStackTrace();
        }*/
    }
}
