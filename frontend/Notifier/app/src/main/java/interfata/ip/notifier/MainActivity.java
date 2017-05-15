package interfata.ip.notifier;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import interfata.ip.notifier.Database.DbOperator;
import interfata.ip.notifier.Database.NotificationTableData;
import interfata.ip.notifier.Database.NotificationsDB;


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
        NotificationTableData data = new NotificationTableData();

        data.setText("informatii");
        data.setId(1);
        DbOperator db= new DbOperator(getApplicationContext());
        NotificationsDB db2=new NotificationsDB(getApplicationContext());
        db2.addFirstTableData(data);

        db2.addNotification(data.construct());

        List<NotificationTableData> dates = new ArrayList<NotificationTableData>();

        dates=db2.getFirstTableDataList();
        System.out.println(dates.get(1).getText());
        System.out.println(dates);

    }
}
