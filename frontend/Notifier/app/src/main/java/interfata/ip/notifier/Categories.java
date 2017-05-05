package interfata.ip.notifier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

public class Categories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        CheckBox categorie_1 = (CheckBox) findViewById(R.id.checkBox);
        CheckBox categorie_2 = (CheckBox) findViewById(R.id.checkBox2);
        CheckBox categorie_3 = (CheckBox) findViewById(R.id.checkBox3);
        CheckBox categorie_4 = (CheckBox) findViewById(R.id.checkBox4);
        CheckBox categorie_5 = (CheckBox) findViewById(R.id.checkBox5);

        Button urmatorul = (Button) findViewById(R.id.button);
    }
}
