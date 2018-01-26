package rj.pl.memorypower;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import butterknife.BindColor;
import butterknife.BindDrawable;

/**
 * Created  by Robert on 12.01.2018 - 16:25.
 */

class ButtonAfterSession implements View.OnClickListener {

    private Context context;
    private int typeSession;
    private int scoreE;
    private int scoreM;
    private int timeSpent;

    private int day;
    private int month;
    private int year;


    private MemoryDatabaseAdapter helper;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @BindColor(R.color.lightDark)
    int lightDark;

    @BindColor(R.color.primaryTextColor)
    int primaryTextColor;


    @Override
    public String toString() {
        return "ButtonAfterSession{" +
                "context=" + context +
                ", typeSession=" + typeSession +
                ", scoreE=" + scoreE +
                ", scoreM=" + scoreM +
                ", timeSpent=" + timeSpent +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", helper=" + helper +
                '}';
    }

    public ButtonAfterSession(Context context, int typeSession, int scoreE, int scoreM, int timeSpent) {
        this.context = context;
        this.typeSession = typeSession;
        this.scoreE = scoreE;
        this.scoreM = scoreM;
        this.timeSpent = timeSpent;


        helper = new MemoryDatabaseAdapter(context);

        if (scoreE>3) { //todo add setting for always saving and setting for always same name to ;skip; dialog
            if (isNetworkAvailable()) {
                showAlert();
            } else {
                Toast.makeText(context, "fail allert", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void showAlert() {

        final AlertDialog.Builder alert = new AlertDialog.Builder(context,R.style.AlertDialog);

        int maxLenght=20;

        final EditText dialogText = new EditText(context);
        dialogText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLenght)});
        alert.setMessage("Who achived this?");

        alert.setView(dialogText);


        alert.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String text = dialogText.getText().toString();
                addScoreToFirebase(text);

            }
        });

        alert.setNegativeButton("Don't send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });


        AlertDialog alertDialog = alert.create();
        alertDialog.show();



    }

    void addScoreToFirebase(String messageWritten) {


        String convertedType = null;

        switch (typeSession){
            case 0:
                convertedType = "numbers";
                break;
            case 1:
                convertedType = "words";
                break;
            case 2:
                convertedType = "cards";
                break;
            case 3:
                convertedType = "names";
                break;
        }


        database = FirebaseDatabase.getInstance();
        myRef = database.getReferenceFromUrl("https://memorypower-3ada5.firebaseio.com/");

        String key = myRef.push().getKey();

        User testObj = new User(messageWritten, scoreE, timeSpent);
        myRef.child(convertedType).child(String.valueOf(month)).child(key).setValue(testObj);

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.przyciskOneInKeypad)://no - place back to main here

                String messageTemp = helper.getAllData();



                Toast.makeText(context, messageTemp, Toast.LENGTH_LONG).show();

                break;
            case R.id.przyciskTwoInKeypad://Save - place saving here

                getCalendar();


//                new InsertRecordTask().execute();

                insertRecord();

                break;

            case (R.id.przyciskOneInKeypadWord)://no - place back to main here


                String message = helper.getAllData();


                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                break;
            case R.id.przyciskTwoInKeypadWord://Save - place saving here

                getCalendar();

                insertRecord();

                break;
        }
    }

    private void getCalendar() {
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
    }

    private void insertRecord() {


        long it = helper.checkIfExist(typeSession, day);
        if (it < 1) {

            long id = helper.insertRecord(typeSession, scoreE, scoreM, day, month, year, timeSpent);


            if (id < 0) {
                Toast.makeText(context, "failure addin", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "success addin", Toast.LENGTH_SHORT).show();
            }
        } else {
            long id = helper.updateRecord(typeSession,scoreE,scoreM,day,month,year,timeSpent);
            if (id < 0) {
                Toast.makeText(context, "failure updatin", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "success updatin", Toast.LENGTH_SHORT).show();
            }

        }


    }



}
