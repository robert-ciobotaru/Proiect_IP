package interfata.ip.notifier;

import android.content.Intent;
import android.os.Messenger;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import interfata.ip.notifier.InternalFile.FileIO;
import interfata.ip.notifier.messenger.NetworkTask;
import interfata.ip.notifier.messenger.PostUsers;

public class Categories extends AppCompatActivity {


    private String first;
    private String second;
    private String country;
    private String city;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);



        Bundle bundle=getIntent().getExtras();
        first= bundle.getString("first");
        second= bundle.getString("second");
        country= bundle.getString("country");
        city= bundle.getString("city");
        email= bundle.getString("email");

        final CheckBox newsCrawler = (CheckBox) findViewById(R.id.checkBox);
        final CheckBox hazzardCrawler = (CheckBox) findViewById(R.id.checkBox2);
        final CheckBox wheaterCrawler = (CheckBox) findViewById(R.id.checkBox3);

        Button urmatorul = (Button) findViewById(R.id.button);



        urmatorul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(newsCrawler.isChecked()==true || hazzardCrawler.isChecked()==true || wheaterCrawler.isChecked()==true) {
                // aici se face requestul
               // System.out.println("register");
               // PostUsers m = new PostUsers(city, country, email, newsCrawler.isChecked(), hazzardCrawler.isChecked(), wheaterCrawler.isChecked());
               // NetworkTask t = new NetworkTask();
               // t.execute(m);
                //System.out.println("dupa execute");
               // System.out.println("in try block");
                //   System.out.println("t.get: " + t.get());

                Intent notificationSend = new Intent(getApplicationContext(), Menu.class);
                startActivity(notificationSend);
            }
            else{
                Snackbar snackbar=Snackbar.make(v,"Please check at least one!",Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            }
        });

    }
}
