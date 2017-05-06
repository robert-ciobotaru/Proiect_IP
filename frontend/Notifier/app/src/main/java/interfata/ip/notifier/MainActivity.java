package interfata.ip.notifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText("Salut");

        Button button = (Button) findViewById(R.id.button);
        Button button2= (Button) findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(getApplicationContext(), Login.class);
                startActivity(login);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(getApplicationContext(), Register.class);
                startActivity(register);
            }
        });


        Messenger m = new GetTriggeredNotifications("104.198.253.69", 8080, "v1", 2);
        NetworkTask t = new NetworkTask();
        t.execute(m);
        try {
            System.out.println(t.get());
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("EXCEPTIONNN");
            e.printStackTrace();
        }
    }
}
