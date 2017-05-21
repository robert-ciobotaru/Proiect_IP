package interfata.ip.notifier;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import interfata.ip.notifier.Database.DbOperator;
import interfata.ip.notifier.Database.NotificationTableData;
import interfata.ip.notifier.Database.NotificationsDB;
import interfata.ip.notifier.messenger.GetReminders;
import interfata.ip.notifier.messenger.NetworkTask;

public class Meniu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String[] titles=new String[50];
    int[] pics= new int[50];
    String[] contents=new String[50];


    List<Notificare> rowItems;
    ListView mylistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rowItems = new ArrayList<Notificare>();
        DbOperator db= new DbOperator(getApplicationContext());
        NotificationsDB db2=new NotificationsDB(getApplicationContext());
        List<NotificationTableData> dates = new ArrayList<NotificationTableData>();

        dates=db2.getFirstTableDataList();
        int j=0;


        GetReminders gr = new GetReminders(2);
        NetworkTask t= new NetworkTask();
        try {
            JSONObject jsonObject=t.execute(gr).get();
            JSONArray jsonArray = jsonObject.getJSONArray("remindersList");
            JSONObject reminder;
            for(int k = 0; k<jsonArray.length();k++){
                reminder=jsonArray.getJSONObject(k);
                titles[j]="Reminder";
                contents[j]=reminder.getString("text")+ " " + reminder.getString("time");
                j++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlayout);

        for (int i = 0; i < titles.length && titles[i]!=null; i++ ) {
            Notificare item2 = new Notificare(titles[i], contents[i], pics[i]);
            rowItems.add(item2);
        }

        mylistview = (ListView) findViewById(R.id.listview);
        NotificationAdapter adapter = new NotificationAdapter(this, rowItems);
        mylistview.setAdapter(adapter);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getBaseContext());
                mBuilder.setSmallIcon(R.drawable.icon);
                mBuilder.setContentTitle("NotificatioN");

                Intent resultIntent = new Intent(getBaseContext(), NotificationView.class);
                resultIntent.putExtra("text", "test");
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getBaseContext());
                stackBuilder.addParentStack(NotificationView.class);
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(resultPendingIntent);
                mBuilder.setAutoCancel(true);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Random rand = new Random();
                notificationManager.notify(rand.nextInt(130000), mBuilder.build());*/
                Intent createNotification = new Intent(getApplicationContext(),CreateNotification.class);
                startActivity(createNotification);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.closeDrawer(GravityCompat.START);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent setting =new Intent(getApplicationContext(),Settings.class);
            startActivity(setting);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        View view=new View(getApplicationContext());
        int id = item.getItemId();

        if (id == R.id.profile) {
            // Handle the camera action
            Intent history = new Intent(getApplicationContext(),History.class);
            startActivity(history);
        } else if (id == R.id.logout) {
            Intent register = new Intent(getApplicationContext(),Register.class);
            startActivity(register);
        } else if (id == R.id.setting) {
            Intent setting =new Intent(getApplicationContext(),Settings.class);
            startActivity(setting);
        } else if (id == R.id.about) {
            return true;
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
