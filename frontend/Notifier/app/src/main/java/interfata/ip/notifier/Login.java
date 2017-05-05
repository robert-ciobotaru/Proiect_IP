package interfata.ip.notifier;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText editText  =(EditText) findViewById(R.id.editText);
        final String text = editText.getText().toString();
        final EditText editText2 =(EditText) findViewById(R.id.editText2);
        final String text2=editText2.getText().toString();



        Button fab = (Button) findViewById(R.id.button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.length()>3 && editText2.length()>3) {
                    Intent categories = new Intent(Login.this, Categories.class);
                    startActivity(categories);
                }
                else{

                    Snackbar snackbar = Snackbar
                            .make(view, "Username sau parola sunt incorecte!", Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
            }
        });
    }
}
