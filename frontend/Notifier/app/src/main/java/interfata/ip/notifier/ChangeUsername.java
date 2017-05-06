package interfata.ip.notifier;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChangeUsername extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);



        final EditText username  =(EditText) findViewById(R.id.editText);
        final String text = username.getText().toString();
        final Button button =(Button) findViewById(R.id.button);




        Button fab = (Button) findViewById(R.id.button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.length()>3 ) {
                    Intent categories = new Intent(getApplicationContext(), Settings.class);
                    startActivity(categories);
                }
                else{
                    Snackbar snackbar = Snackbar.make(view, "Username incorect!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                // DE ADAUGAT UN IF CARE SA VERIFICE DACA USERNAME-UL EXISTENT ESTE LA FEL CA CEL NOU
            }
        });

    }
}
