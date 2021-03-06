package rj.pl.memorypower;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.annotation.Annotation;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class Settings extends Activity {

    @BindView(R.id.leadSw1)
    CheckBox dontPostMyScores;

    @BindView(R.id.setLead2)
    TextView saveToLeadTextView;

    @BindView(R.id.leadSw2)
    CheckBox alwaysSaveTolead;

    @BindView(R.id.textLead1)
    TextView saveAss;


    @BindView(R.id.textEditTextName1)
    EditText editTextName;

    @BindView(R.id.statSw1)
    CheckBox displayStatsAfterSeleted;

    @BindView(R.id.sessionTimerEditText)
    EditText sessionTimer;

    @BindColor(R.color.lightDark)
    int lightDark;

    @BindColor(R.color.primaryTextColor)
    int primaryTextColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);


        checkPrefsForChecked();


    }

    public void clearUserStatistics(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialog);
        builder.setMessage("You are deleting database \n Are you sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MemoryDatabaseAdapter helper = new MemoryDatabaseAdapter(Settings.this);
                helper.dropDatabase();

                Toast.makeText(Settings.this, "Database has been deleted :( ", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });


        builder.create().show();


    }

    private void checkPrefsForChecked() {

        SharedPreferences sharedPreferences = getSharedPreferences("MySettings",Context.MODE_PRIVATE);


        if (sharedPreferences.getBoolean("leadSw1",false)){
            dontPostMyScores.setChecked(true);
            if (dontPostMyScores.isChecked()){
                alwaysSaveTolead.setEnabled(false);
                editTextName.setEnabled(false);
                saveToLeadTextView.setTextColor(lightDark);
                saveAss.setTextColor(lightDark);
            }
        }

        if (sharedPreferences.getBoolean("leadSw2",false)){
            alwaysSaveTolead.setChecked(true);
        }


        editTextName.setText(sharedPreferences.getString("name","Anonymous"));




        if (sharedPreferences.getBoolean("statsSw1",false)){
            displayStatsAfterSeleted.setChecked(true);
        }


        sessionTimer.setText(sharedPreferences.getString("sessionTimer","60"));



        setCheckedListeners();



    }

    private void setCheckedListeners() {

        dontPostMyScores.setOnCheckedChangeListener(new checkedChangeListner());
        alwaysSaveTolead.setOnCheckedChangeListener(new checkedChangeListner());
        displayStatsAfterSeleted.setOnCheckedChangeListener(new checkedChangeListner());

    }


    class checkedChangeListner implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            switch (compoundButton.getId()){
                case R.id.leadSw1:
                    if (b){
                        alwaysSaveTolead.setEnabled(false);
                        editTextName.setEnabled(false);
                        saveToLeadTextView.setTextColor(lightDark);
                        saveAss.setTextColor(lightDark);
                    }else {
                        alwaysSaveTolead.setEnabled(true);
                        editTextName.setEnabled(true);
                        saveToLeadTextView.setTextColor(primaryTextColor);
                        saveAss.setTextColor(primaryTextColor);
                    }
                    break;
            }
        }
    }


    public void checkedChanged(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        SharedPreferences sharedPreferences = getSharedPreferences("MySettings", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();


        switch (view.getId()) {
            case R.id.leadSw1:
                if (checked)
                    editor.putBoolean("leadSw1", true);
                else
                    editor.putBoolean("leadSw1", false);
                break;
            case R.id.leadSw2:
                if (checked)
                    editor.putBoolean("leadSw2", true);
                else
                    editor.putBoolean("leadSw2", false);
                break;
            case R.id.statSw1:
                if (checked)
                    editor.putBoolean("statsSw1", true);
                else
                    editor.putBoolean("statsSw1", false);
                break;


        }

        editor.apply();


    }

    @Override
    protected void onPause() {
        super.onPause();

        String name = String.valueOf(editTextName.getText());
        Log.e("name",name);
        if (name.equals("")){
            name = "Anonymous";
        }

        Log.e("name",name);


        String timer = String.valueOf(sessionTimer.getText());
        Log.e("timer", String.valueOf(timer));

        if (timer.equals("")){
            timer = "60";
        }else if (Integer.parseInt(timer)>60){
            timer = "60";
        }

        Log.e("timer", String.valueOf(timer));


        SharedPreferences sharedPreferences = getSharedPreferences("MySettings", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("name",name);
        editor.putString("sessionTimer",timer);

        editor.apply();


        Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();

    }
}
