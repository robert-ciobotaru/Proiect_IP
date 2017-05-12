package interfata.ip.notifier;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import interfata.ip.notifier.Database.DbOperator;
import interfata.ip.notifier.Database.NotificationTableData;
import interfata.ip.notifier.Database.NotificationsDB;
import interfata.ip.notifier.InternalFile.FileIO;
import interfata.ip.notifier.messenger.DeleteUser;
import interfata.ip.notifier.messenger.Messenger;
import interfata.ip.notifier.messenger.NetworkTask;


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
            startActivity(register);
        }else {
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
        NotificationTableData data= new NotificationTableData();
        data.setInfo("informatii");
        data.setId(1);
        DbOperator db= new DbOperator(getApplicationContext());
        NotificationsDB db2=new NotificationsDB(getApplicationContext());
        db2.addFirstTableData(data);
        List<NotificationTableData> dates = new ArrayList<NotificationTableData>();

        dates=db2.getFirstTableDataList();
        System.out.println(dates.get(1).getInfo());
        System.out.println(dates);

    }
}
