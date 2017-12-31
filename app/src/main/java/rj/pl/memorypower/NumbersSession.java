package rj.pl.memorypower;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

public class NumbersSession extends Activity {

    public static final String EXTRA_PICKER = "picker";

    int pickerValue;
    private TextSwitcher textSwitcher;
    private Button nextButton;
    private ProgressBar progressBar;
    private int stringIndex = 0;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers_session);

        pickerValue = (int) getIntent().getExtras().get(EXTRA_PICKER);
        Log.d("asda", String.valueOf(pickerValue));

        ExpandableHeightGridView gridView = findViewById(R.id.gridView);
        gridView.setExpanded(true);

        final NumbersAdapterAll numbersAdapterAll = new NumbersAdapterAll(this, pickerValue);

        final ArrayList<Number_item> list = numbersAdapterAll.getList();



        //setting Text Switcher

        textSwitcher = findViewById(R.id.text_switcher);
        nextButton = findViewById(R.id.sessionButton_TextSwitcher);
        progressBar = findViewById(R.id.progressBar_number_session);






//        //TODO temp button
//        Button endbutton = view.findViewById(R.id.endSession);
//
//        endbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Bundle bundleArray = new Bundle();
//                bundleArray.putParcelableArrayList("bundleArray",list);
//
//                fragmentManager = getActivity().getFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//
//
//
//                NumbersSessionEnd numbersSessionEnd = new NumbersSessionEnd();
//
//                numbersSessionEnd.setArguments(bundleArray);
//
//                fragmentTransaction.replace(R.id.main_container, numbersSessionEnd);
//                fragmentTransaction.commit();
//
//
//            }
//        });



        progressBar.setMax(list.size());
        progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#f05545"), PorterDuff.Mode.SRC_IN);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //comment used when want to start over textswitcher
//                if (stringIndex == list.size()) {
//                    Log.d("DUPA", String.valueOf(list.size()));
//
//                    stringIndex = 0;
//                    textSwitcher.setText(String.valueOf(list.get(stringIndex).number) + "" + String.valueOf(list.get(stringIndex + 1).number));
//                    stringIndex += 2;
//                    progressBar.setProgress(stringIndex);
//                }
//                 else if (stringIndex == list.size() - 2) {
//
//                    Log.d("kupa", String.valueOf(list.size()));
//
//                    textSwitcher.setText(String.valueOf(list.get(stringIndex).number) + "" + String.valueOf(list.get(stringIndex + 1).number) + ".");
//                    stringIndex += 2;
//
//
//                }
//                else
                {

                    if(stringIndex == list.size()-2){

                        if(progressBar.getProgress() != progressBar.getMax()){
                            textSwitcher.setText(String.valueOf(list.get(stringIndex).number) + "" + String.valueOf(list.get(stringIndex + 1).number));
                        }
                        progressBar.setProgress(stringIndex+2);

                    }else {
                        textSwitcher.setText(String.valueOf(list.get(stringIndex).number) + "" + String.valueOf(list.get(stringIndex + 1).number));
                        stringIndex += 2;
                        progressBar.setProgress(stringIndex);

                    }
                }

            }
        });

        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                textView = new TextView(NumbersSession.this);
                textView.setBackgroundColor(Color.parseColor("#f05545"));
                textView.setTextColor(Color.WHITE);
                textView.setTextSize(40);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);

                return textView;
            }
        });

        textSwitcher.setText(String.valueOf(list.get(stringIndex).number) + "" + String.valueOf(list.get(stringIndex + 1).number));
        stringIndex += 2;
        progressBar.setProgress(stringIndex);






        gridView.setAdapter(numbersAdapterAll);


        Button endButton = findViewById(R.id.endSession);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextButton.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                textSwitcher.setVisibility(View.GONE);

                numbersAdapterAll.setToNone();
            }
        });

    }
}
