package interfata.ip.notifier;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText first = (EditText) findViewById(R.id.editText);
        final EditText second = (EditText) findViewById(R.id.editText2);
        final EditText country = (EditText) findViewById(R.id.editText3);
        final EditText city = (EditText) findViewById(R.id.editText4);
        final EditText email = (EditText) findViewById(R.id.editText5);
        final Button register = (Button) findViewById(R.id.button);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(first.getText().length()>3 && second.getText().length()>3 && email.getText().length()>3 && city.getText().length()>2) {
                    Intent categories = new Intent(getApplicationContext(), Categories.class);
                    categories.putExtra("first", first.getText().toString());
                    categories.putExtra("second", second.getText().toString());
                    categories.putExtra("country", country.getText().toString());
                    categories.putExtra("email", email.getText().toString());
                    startActivity(categories);
                }
                else if(first.getText().length()<=3){
                    Snackbar snackbar = Snackbar.make(v, "First name must have at least 4 characters!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    first.requestFocus();
                }
                else if(second.getText().length()<=3){
                    Snackbar snackbar = Snackbar.make(v, "Last name must have at least 4 characters!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    second.requestFocus();
                }
                else if(city.getText().length()<=2){
                    Snackbar snackbar = Snackbar.make(v, "City does not exist!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    email.requestFocus();
                }
                else if(email.getText().length()<=3){
                    Snackbar snackbar = Snackbar.make(v, "Email must have at least 4 characters", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    email.requestFocus();
                }


                String filename = "user.txt";
                FileOutputStream outputStream;

                try {
                    outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                    outputStream.write(first.getText().toString().getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

       /* TextView tv;
        String selectedfile = "user.txt";
        tv = (TextView)findViewById(R.id.textView2);
        try {
            FileReader fr=new FileReader(selectedfile);
            BufferedReader br=new BufferedReader(fr);
            String line = null;
            try {
                while(br.readLine()!=null)
                {
                    line =br.readLine();
                    tv.append(line);
                    tv.append("\n");
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        */
    }
}
