package interfata.ip.notifier.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 12.05.2017.
 */

public class NotificationsDB extends DbOperator {
    public NotificationsDB(Context context) {
        super(context);
    }

    private static final String COL_ID = "_id";
    //private static final String COL1 = "notif";
    //private static final String COL2 = "COL2";

    public void deleteFirstTableDataList(List<NotificationTableData>notificationTableDataList) {
        for (NotificationTableData data : notificationTableDataList) {
            deleteFirstTableDetailData(data);
        }
    }
    public void deleteFirstTableDetailData(NotificationTableData item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USER_NOTIFICATION_TABLE, item.getId() + "=" + COL_ID, null);
        db.close();
    }
    /**this method retrieves all the records from table and returns them as list of
     FirstTableData types. Now you use this list to display detail on your screen as per your
     requirements.
     */

    public List< NotificationTableData > getFirstTableDataList() {
        List< NotificationTableData > firstTableDataList = new ArrayList< NotificationTableData >();
        String refQuery = "Select * From " + USER_NOTIFICATION_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(refQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    NotificationTableData itemData = new NotificationTableData();
                    itemData.setId(cursor.getInt(0));
                    itemData.setText(cursor.getString(1));


                    itemData.setTime(cursor.getString(2));

                    itemData.setImg(cursor.getString(3));


                    firstTableDataList.add(itemData);
                } while (cursor.moveToNext());
            }
        } finally {
            db.close();
        }

        //Collections.sort(firstTableDataList);
        return firstTableDataList;
    }






    public int addNotification(NotificationTableData notification){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("text", notification.getText());
        values.put("time", notification.getTime());
        values.put("image", notification.getImg());

        long status = db.insert(USER_NOTIFICATION_TABLE, null, values);
        db.close();
        return (int)status;
    }

    public void updateItemDetailData(NotificationTableData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("text", data.getText());
        values.put("time", data.getTime());
        values.put("image", data.getImg());




        db.update(USER_NOTIFICATION_TABLE, values, COL_ID + "=" + data.getId(),    null);

        db.update(FIRST_TABLE_NAME, values, COL_ID + "=" + data.getId(),    null);

        db.close();
    }
}
