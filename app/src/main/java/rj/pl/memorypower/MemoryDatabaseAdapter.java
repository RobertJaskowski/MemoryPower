package rj.pl.memorypower;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created  by Robert on 12.01.2018 - 17:21.
 */

public class MemoryDatabaseAdapter {


    MemoryDatabaseHelper helper;

    public MemoryDatabaseAdapter(Context context) {
        helper = new MemoryDatabaseHelper(context);


    }

    public long insertRecord(String type, int scoreE, int scoreM, int day, int month, int year, int time) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues recordValues = new ContentValues();
        recordValues.put("TYPE", type);// type should be stored MEMDATHELPER.TYPE as final private
        recordValues.put("SCOREE", scoreE);
        recordValues.put("SCOREM", scoreM);
        recordValues.put("DAY", day);
        recordValues.put("MONTH", month);
        recordValues.put("YEAR", year);
        recordValues.put("TIME", time);
        long id = db.insert(MemoryDatabaseHelper.USERSTATS, null, recordValues);
        return id;
    }

    public String getAllData() {
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor cursor = db.query(MemoryDatabaseHelper.USERSTATS, new String[]{"_id", "TYPE", "SCOREE", "SCOREM", "DAY", "MONTH", "YEAR", "TIME"}, null, null, null, null, null);

        StringBuilder buffer = new StringBuilder();
        while (cursor.moveToNext()) {
//            int index1 = cursor.getColumnIndex("_id");
//            cursor.getInt(index1);                //the right way

            int cid = cursor.getInt(0);
            String type = cursor.getString(1);
            int e = cursor.getInt(2);
            int m = cursor.getInt(3);
            int da = cursor.getInt(4);
            int mo = cursor.getInt(5);
            int ye = cursor.getInt(6);
            int ti = cursor.getInt(7);

            buffer.append(cid + " " + type + " " + e + " " + m + " " + da + " " + mo + " " + ye + " "+ ti + "\n");
//            buffer.append(type+ " ");

        }
        db.close();
        cursor.close();

        return buffer.toString();
    }


    static class MemoryDatabaseHelper extends SQLiteOpenHelper {
        private static final String DB_NAME = "memorypower";
        private static final int DB_VERSION = 1;
        private static final String USERSTATS = "USERSTATS";

        MemoryDatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            updateMyDatabase(db, 0, DB_VERSION);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            updateMyDatabase(db, oldVersion, newVersion);
        }

        private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion < 1) {
                db.execSQL("CREATE TABLE " + USERSTATS + " (_id INTEGER PRIMARY KEY AUTOINCREMENT," + "TYPE TEXT," + "SCOREE INTEGER," + "SCOREM INTEGER," + "DAY INTEGER," + "MONTH INTEGER," + "YEAR INTEGER," + "TIME INTEGER);");
            }
        }


    }
}
