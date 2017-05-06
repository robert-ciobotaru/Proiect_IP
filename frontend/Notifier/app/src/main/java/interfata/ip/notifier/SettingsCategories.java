package interfata.ip.notifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class SettingsCategories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_categories);
        CheckBox categorie_1 = (CheckBox) findViewById(R.id.checkBox);
        CheckBox categorie_2 = (CheckBox) findViewById(R.id.checkBox2);
        CheckBox categorie_3 = (CheckBox) findViewById(R.id.checkBox3);
        CheckBox categorie_4 = (CheckBox) findViewById(R.id.checkBox4);
        CheckBox categorie_5 = (CheckBox) findViewById(R.id.checkBox5);

        Button applyChanges = (Button) findViewById(R.id.button);



        applyChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Settings = new Intent(getApplicationContext(), Settings.class);
                startActivity(Settings);
            }
        });
    }
}
