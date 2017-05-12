package interfata.ip.notifier.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vlad on 12.05.2017.
 */

public class NotificationsDB extends DbOperator {
    public NotificationsDB(Context context) {
        super(context);
    }

    private static final String COL_ID = "_id";
    private static final String COL1 = "notif";
    //private static final String COL2 = "COL2";

    public void deleteFirstTableDataList(List<NotificationTableData>notificationTableDataList) {
        for (NotificationTableData data : notificationTableDataList) {
            deleteFirstTableDetailData(data);
        }
    }
    public void deleteFirstTableDetailData(NotificationTableData item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FIRST_TABLE_NAME, item.getId() + "=" + COL_ID, null);
        db.close();
    }
    /**this method retrieves all the records from table and returns them as list of
     FirstTableData types. Now you use this list to display detail on your screen as per your
     requirements.
     */
    public List< NotificationTableData > getFirstTableDataList() {
        List< NotificationTableData > firstTableDataList = new ArrayList< NotificationTableData >();
        String refQuery = "Select * From " + FIRST_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(refQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    NotificationTableData itemData = new NotificationTableData();

                    itemData.setId(cursor.getInt(0));

                    itemData.setInfo(cursor.getString(1));

                    firstTableDataList.add(itemData);
                } while (cursor.moveToNext());
            }
        } finally {

            db.close();
        }

        //Collections.sort(firstTableDataList);
        return firstTableDataList;
    }

    public int  addFirstTableData(NotificationTableData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL1, data.getInfo());
        //values.put(COL2, data.getCol2());

        long x=db.insert(FIRST_TABLE_NAME, null, values);
        db.close();
        return (int)x;
    }

    public void updateItemDetailData(NotificationTableData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL1, data.getInfo());



        db.update(FIRST_TABLE_NAME, values, COL_ID + "=" + data.getId(),    null);
        db.close();
    }
}
