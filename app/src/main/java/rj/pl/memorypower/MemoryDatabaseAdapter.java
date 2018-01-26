package rj.pl.memorypower;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created  by Robert on 12.01.2018 - 17:21.
 */

@SuppressWarnings("ALL")
class MemoryDatabaseAdapter {


    private MemoryDatabaseHelper helper;
    private Context context;

    public MemoryDatabaseAdapter(Context context) {
        helper = new MemoryDatabaseHelper(context);
        this.context = context;


    }

    public void testRecords() {
        insertRecord(0,6,6,13,0,2018,4);
        insertRecord(0,40,41,14,0,2018,5);
        insertRecord(0,24,35,16,0,2018,5);
        insertRecord(1,2,5,12,0,2018,5);
        insertRecord(1,34,46,17,0,2018,2);
        insertRecord(1,13,45,25,0,2018,1);
        insertRecord(2,3,7,13,0,2018,3);
        insertRecord(2,50,50,23,0,2018,5);
        insertRecord(2,39,39,30,0,2018,6);
        insertRecord(3,3,5,13,0,2018,6);
        insertRecord(3,15,24,21,0,2018,3);
        insertRecord(3,6,8,22,0,2018,2);
        insertRecord(0,5,9,18,0,2018,7);
        insertRecord(0,5,6,1,3,2018,9);
        insertRecord(0,30,35,30,3,2018,8);
        insertRecord(1,5,40,3,3,2018,5);
        insertRecord(1,6,50,31,3,2018,5);
        insertRecord(1,5,8,5,11,2018,7);
        insertRecord(1,6,7,7,11,2018,8);
        insertRecord(2,5,10,5,10,2018,5);
        insertRecord(2,45,50,20,10,2018,5);
    }

    public long insertRecord(int type, int scoreE, int scoreM, int day, int month, int year, int time) {
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

    public long updateRecord(int type, int scoreE, int scoreM, int day, int month, int year, int time) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues recordValues = new ContentValues();
        recordValues.put("TYPE", type);// type should be stored MEMDATHELPER.TYPE as final private
        recordValues.put("SCOREE", scoreE);
        recordValues.put("SCOREM", scoreM);
        recordValues.put("DAY", day);
        recordValues.put("MONTH", month);
        recordValues.put("YEAR", year);
        recordValues.put("TIME", time);
        long id = db.update(MemoryDatabaseHelper.USERSTATS, recordValues, "TYPE= ? AND DAY = ?", new String[]{String.valueOf(type), String.valueOf(day)});
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
            int type = cursor.getInt(1);
            int e = cursor.getInt(2);
            int m = cursor.getInt(3);
            int da = cursor.getInt(4);
            int mo = cursor.getInt(5);
            int ye = cursor.getInt(6);
            int ti = cursor.getInt(7);

            buffer.append("id" + cid + " type" + type + " ea" + e + " ma" + m + " da" + da + " mo" + mo + " ye" + ye + " ti" + ti + " \n");
//            buffer.append(type+ " ");

        }
        db.close();
        cursor.close();

        return buffer.toString();
    }

    public String getAllDataByType(int whatType, int month) {
        SQLiteDatabase db = helper.getWritableDatabase();


        Cursor cursor = db.query(MemoryDatabaseHelper.USERSTATS, new String[]{"_id", "TYPE", "SCOREE", "SCOREM", "DAY", "MONTH", "YEAR", "TIME"}, "TYPE = ? AND MONTH = ?", new String[]{Integer.toString(whatType), String.valueOf(month)}, null, null, null);

        if (cursor.getCount() <= 0) {
            return "null";
        } else {
            StringBuilder buffer = new StringBuilder();
            while (cursor.moveToNext()) {
//            int index1 = cursor.getColumnIndex("_id");
//            cursor.getInt(index1);                //the right way

                int cid = cursor.getInt(0);
                int type = cursor.getInt(1);
                int e = cursor.getInt(2);
                int m = cursor.getInt(3);
                int da = cursor.getInt(4);
                int mo = cursor.getInt(5);
                int ye = cursor.getInt(6);
                int ti = cursor.getInt(7);

                buffer.append("id" + cid + " type" + type + " ea" + e + " ma" + m + " da" + da + " mo" + mo + " ye" + ye + " ti" + ti + " \n");
//            buffer.append(type+ " ");

            }
            db.close();
            cursor.close();

            return buffer.toString();
        }
    }


    public void dropDatabase(){
        context.deleteDatabase(MemoryDatabaseHelper.DB_NAME);
    }

    public long checkIfExist(int whatType, int day) {//transfer month and year here too
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] selectionArgs = {String.valueOf(whatType), String.valueOf(day)};

        Cursor cursor = db.query(MemoryDatabaseHelper.USERSTATS, new String[]{"_id", "SCOREE"}, "TYPE = ? AND DAY = ?", selectionArgs, null, null, null);

        if (cursor.getCount() <= 0) {
            db.close();
            cursor.close();//doesnt exist do insert method

            return 0;

        } else {

            db.close();
            cursor.close();
            return 1;
        }


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
                db.execSQL("CREATE TABLE " + USERSTATS + " (_id INTEGER PRIMARY KEY AUTOINCREMENT," + "TYPE INTEGER," + "SCOREE INTEGER," + "SCOREM INTEGER," + "DAY INTEGER," + "MONTH INTEGER," + "YEAR INTEGER," + "TIME INTEGER);");
            }
        }


    }
}
