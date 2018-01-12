package rj.pl.memorypower;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created  by Robert on 12.01.2018 - 16:25.
 */

public class ButtonAfterSession implements View.OnClickListener {

    Context context;
    private String typeSession;
    private int scoreE;
    private int scoreM;
    private int whenAchived;
    private int timeSpent;

    private int day;
    private int month;
    private int year;


    private MemoryDatabaseAdapter helper;

    @Override
    public String toString() {
        return "ButtonAfterSession{" +
                "typeSession='" + typeSession + '\'' +
                ", scoreE=" + scoreE +
                ", scoreM=" + scoreM +
                ", whenAchived=" + whenAchived +
                ", timeSpent=" + timeSpent +
                '}';
    }

    public ButtonAfterSession(Context context, String typeSession, int scoreE, int scoreM, int timeSpent) {
        this.context = context;
        this.typeSession = typeSession;
        this.scoreE = scoreE;
        this.scoreM = scoreM;
        this.timeSpent = timeSpent;


        helper = new MemoryDatabaseAdapter(context);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.przyciskOneInKeypad)://no - place back to main here
                Log.e("button one clicked", "clk");
//                Toast.makeText(context, toString(), Toast.LENGTH_LONG).show();

                String message = helper.getAllData();

                Log.e("tag", message);

                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                break;
            case R.id.przyciskTwoInKeypad://Save - place saving here

                Calendar calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DATE);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);


                Log.e("button two clicked", "clk");


//                new InsertRecordTask().execute();

                insertRecord();

                break;
        }
    }

    private void insertRecord() {

        long id = helper.insertRecord(typeSession, scoreE, scoreM, day, month, year, timeSpent);
        if (id < 0) {
            Toast.makeText(context, "failure", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
        }

    }


//     class InsertRecordTask extends AsyncTask<Void, Void, Boolean> {
//
//
//
//
//        @Override
//        protected Boolean doInBackground(Void... voids) {
//
//            long id = helper.insertRecord("numbers", 6, 9, 13, 0, 2018, 6);
//
//            if (id < 0) {
//                return false;
//            } else {
//                return true;
//            }
//
//
//        }
//
//        @Override
//        protected void onPostExecute(Boolean abool) {
//            if (abool) {
//                Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(context, "failure", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}
