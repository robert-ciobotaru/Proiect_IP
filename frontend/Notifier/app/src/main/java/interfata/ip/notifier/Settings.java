package interfata.ip.notifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button button1 = (Button) findViewById(R.id.button1);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ChangeCategories = new Intent(getApplicationContext(), SettingsCategories.class);
                startActivity(ChangeCategories);
            }
        });


    }
}
