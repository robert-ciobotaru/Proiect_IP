package interfata.ip.notifier;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;


public class NotificationSend extends AppCompatActivity {

    private Button button;
    private EditText editText;

    public EditText getEditText() {
        return editText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_send);

        editText = (EditText) this.findViewById(R.id.editText);
        button = (Button) this.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if(text.length()==0){
                    Snackbar snackbar = Snackbar.make(v, "Introduceti un text", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else {
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getBaseContext());
                    mBuilder.setSmallIcon(R.drawable.icon);
                    mBuilder.setContentText(text);
                    mBuilder.setContentTitle("Notification");

                    Intent resultIntent = new Intent(getBaseContext(), NotificationView.class);
                    resultIntent.putExtra("text", text);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(getBaseContext());
                    stackBuilder.addParentStack(NotificationView.class);
                    stackBuilder.addNextIntent(resultIntent);
                    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                    mBuilder.setContentIntent(resultPendingIntent);
                    mBuilder.setAutoCancel(true);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    Random rand = new Random();
                    notificationManager.notify(rand.nextInt(130000), mBuilder.build());
                }
            }
        });
    }
}
