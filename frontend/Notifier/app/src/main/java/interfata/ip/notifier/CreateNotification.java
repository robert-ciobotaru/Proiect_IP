package interfata.ip.notifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class CreateNotification extends AppCompatActivity {

    private EditText name;
    private EditText time;
    private EditText content;
    private CheckBox repetable;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notification);

        name = (EditText) findViewById(R.id.editText);
        time = (EditText) findViewById(R.id.editText2);
        content = (EditText) findViewById(R.id.editText3);
        submit = (Button) findViewById(R.id.button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(getApplicationContext(),Meniu.class);
                menu.putExtra("name", name.getText().toString());
                menu.putExtra("time", time.getText().toString());
                menu.putExtra("content", content.getText().toString());
                startActivity(menu);
            }
        });


    }
}
