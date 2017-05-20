package interfata.ip.notifier;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
<<<<<<< HEAD
import android.database.sqlite.SQLiteDatabase;
=======
import android.os.SystemClock;
import android.support.annotation.Nullable;
>>>>>>> a00611dc6a61ed4b56922285af5d3a8b7cf31d92
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import interfata.ip.notifier.Database.DbOperator;
import interfata.ip.notifier.Database.NotificationTableData;
import interfata.ip.notifier.Database.NotificationsDB;
import interfata.ip.notifier.logic.BackgroundTask;
import interfata.ip.notifier.notifications.AlarmNotificationReceiver;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("ShaPreferences", getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        boolean  firstTime=sharedPreferences.getBoolean("first", true);
        if(firstTime) {
            editor.putBoolean("first",false);
            //For commit the changes, Use either editor.commit(); or  editor.apply();.
            editor.commit();  ;
            Intent register = new Intent(getApplicationContext(), Register.class);
            // startService(new Intent(, BackgroundTask.class));
            startActivity(register);
        } else {
            Intent menu = new Intent(getApplicationContext(), Meniu.class);
            startActivity(menu);
        }

        Button b = (Button) findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
            }
        });

        startService(new Intent(this, BackgroundTask.class));

        /*Read and write from file test */
       /* System.out.println("Test");
        FileIO file= new FileIO("test.file",getApplicationContext());
        try {
            file.saveInfo("Informatii exemplu");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            file.loadInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(file.getInfo());
*/
        //Test baze de date
       NotificationTableData data = new NotificationTableData();


        data.setText("informatii");
        data.setImg("3");
        data.setTime("312312");
        //data.setId(1);
        DbOperator db= new DbOperator(getApplicationContext());

        NotificationsDB db2=new NotificationsDB(getApplicationContext());
        System.out.println("inainte de insert");
        //db2.addNotification(data);


        List<NotificationTableData> dates = new ArrayList<NotificationTableData>();

        dates=db2.getFirstTableDataList();
        for (NotificationTableData date : dates)
        {
            System.out.println(date.getImg());
            System.out.println(date.getText());
            System.out.println(date.getTime());
        }

    }
}
