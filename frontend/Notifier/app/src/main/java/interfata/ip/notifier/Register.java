package interfata.ip.notifier;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText username = (EditText) findViewById(R.id.editText);
        final EditText password = (EditText) findViewById(R.id.editText2);
        final EditText r_password = (EditText) findViewById(R.id.editText3);
        final EditText email = (EditText) findViewById(R.id.editText4);
        final Button register = (Button) findViewById(R.id.button);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().length()>3 && password.getText().length()>3 && password.getText().toString().equals(r_password.getText().toString()) && email.getText().length()>3) {
                    Intent categories = new Intent(getApplicationContext(), Categories.class);
                    startActivity(categories);
                }
                else if(username.getText().length()<=3){
                    Snackbar snackbar = Snackbar.make(v, "Username-ul trebuie sa aiba cel putin 4 caractere!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    username.requestFocus();
                }
                else if(!password.getText().toString().equals(r_password.getText().toString())){
                    Snackbar snackbar = Snackbar.make(v, "Parolele nu sunt la fel!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    r_password.requestFocus();
                }
                else if(password.getText().length()<=3){
                    Snackbar snackbar = Snackbar.make(v, "Username-ul trebuie sa aiba cel putin 4 caractere!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    password.requestFocus();
                }
                else if(email.getText().length()<=3){
                    Snackbar snackbar = Snackbar.make(v, "Email-ul trebuie sa aiba cel putin 4 caractere!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    email.requestFocus();
                }

            }
        });
    }
}
