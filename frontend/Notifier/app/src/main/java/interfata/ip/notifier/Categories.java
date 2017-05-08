package interfata.ip.notifier;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

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
                    Intent notificationSend = new Intent(getApplicationContext(), Meniu.class);
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
