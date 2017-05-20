package interfata.ip.notifier.Database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONObject;


/**
 * Created by Vlad on 12.05.2017.
 */

public class DbOperator extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DB_IP";
    protected static final String FIRST_TABLE_NAME = "NOTIFICATIONS";
    protected static final String USER_NOTIFICATION_TABLE = "UserNotifications";

    public static final String CREATE_NOTIFICATIONS = "create table if not exists "
            + FIRST_TABLE_NAME
            + " ( _id integer primary key autoincrement, notif  VARCHAR(300) NOT NULL);";

    public static final String CREATE_USER_NOTIFICATIONS = "create table if not exists "
            + USER_NOTIFICATION_TABLE
            + " ( _id integer primary key autoincrement, title VARCHAR(300) NOT NULL, content VARCHAR(300), time DATE NOT NULL, repeatable BOOLEAN NOT NULL);";


    public DbOperator(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(CREATE_NOTIFICATIONS);
       db.execSQL(CREATE_USER_NOTIFICATIONS);
       db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //THIS WILL BE EXECUTED WHEN YOU UPDATED VERSION OF DATABASE_VERSION
        //YOUR DROP AND CREATE QUERIES

    }

}
