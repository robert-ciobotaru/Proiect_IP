package interfata.ip.notifier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< Updated upstream

        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ChangeCategories = new Intent(getApplicationContext(), SettingsCategories.class);
                startActivity(ChangeCategories);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ChangeCategories = new Intent(getApplicationContext(), ChangeUsername.class);
                startActivity(ChangeCategories);
            }
        });


=======
        setContentView(R.layout.activity_settings);
>>>>>>> Stashed changes
    }
}
