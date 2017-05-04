package com.example.pethoalpar.androidnotificationexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NotificationView extends AppCompatActivity {
    TextView myAwesomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_view);
        myAwesomeTextView = (TextView) findViewById(R.id.textView);
        Bundle bundle = getIntent().getExtras();
        String output = bundle.getString("text");
        myAwesomeTextView.setText(output);


    }
}
