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
import android.widget.TextView;

import java.util.Random;

public class Meniu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlayout);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            String name = extras.getString("name");
            String time = extras.getString("time");
            String content = extras.getString("content");
            TextView title = (TextView) findViewById(R.id.textView5);
            title.setText(name);
            TextView body = (TextView) findViewById(R.id.textView6);
            body.setText(content+" "+time);
            ImageView img= (ImageView) findViewById(R.id.imageView2);
            img.setVisibility(View.VISIBLE);
        }



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
            super.onBackPressed();
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
