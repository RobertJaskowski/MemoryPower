package rj.pl.memorypower;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.annotation.Annotation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class Settings extends Activity {

    @BindView(R.id.leadSw1)
    CheckBox dontPostMyScores;

    @BindView(R.id.leadSw2)
    CheckBox alwaysSaveTolead;


    @BindView(R.id.textEditTextName1)
    EditText editTextName;

    @BindView(R.id.statSw1)
    CheckBox displayStatsAfterSeleted;

    @BindView(R.id.sessionTimerEditText)
    EditText sessionTimer;

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
        if (name==null){
            //todo
        }


    }
}
