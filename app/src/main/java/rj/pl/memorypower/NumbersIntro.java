package rj.pl.memorypower;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

public class NumbersIntro extends Activity {

    NumberPicker numberPicker;
    int numberPickerValue;

    /**
     * for number picker
     */
    String[] values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers_intro);


        values = new String[24];

        int n = 4;
        for (int i = 0; i < 24; i++) {
            values[i] = Integer.toString(n);
            n += 2;

        }
        numberPicker = findViewById(R.id.Number_picker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(values.length - 1);
        numberPicker.setWrapSelectorWheel(false);



        numberPicker.setDisplayedValues(values);


        Button btn = findViewById(R.id.StartSession);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPickerValue = numberPicker.getValue() * 2 + 4;

                Intent intent = new Intent(view.getContext(),NumbersSession.class);
                intent.putExtra("picker",numberPickerValue);
                startActivity(intent);

//                Bundle bundle = new Bundle();
//                bundle.putInt("numberPickerValue", numberPickerValue);




//                fragmentManager = getActivity().getFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//
//
//
//
//                NumbersSession numbersSession = new NumbersSession();
//                numbersSession.setArguments(bundle);
//
//                fragmentTransaction.replace(R.id.main_container, numbersSession);
//                fragmentTransaction.commit();
            }
        });
    }
}
