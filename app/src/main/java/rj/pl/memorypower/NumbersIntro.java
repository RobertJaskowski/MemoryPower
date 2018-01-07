package rj.pl.memorypower;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.NumberPicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NumbersIntro extends Activity {

    @BindView(R.id.NNumber_picker)
    NumberPicker numberPicker;
    private int numberPickerValue;



    /**
     * for number picker
     */
    private String[] values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers_intro);

        ButterKnife.bind(this);


        values = new String[24];

        int n = 4;
        for (int i = 0; i < 24; i++) {
            values[i] = Integer.toString(n);
            n += 2;

        }

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(values.length - 1);
        numberPicker.setWrapSelectorWheel(false);



        numberPicker.setDisplayedValues(values);


    }

    @OnClick(R.id.startNumberSession)
    public void startNumberSession(){
        numberPickerValue = numberPicker.getValue() * 2 + 4;

        Intent intent = new Intent(this,NumbersSession.class);
        intent.putExtra("picker",numberPickerValue);
        startActivity(intent);
    }
}
