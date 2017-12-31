package rj.pl.memorypower;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
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

        final ExpandableHeightGridView gridView = findViewById(R.id.gridView);
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

                    if (stringIndex == list.size() - 2) {

                        if (progressBar.getProgress() != progressBar.getMax()) {
                            textSwitcher.setText(String.valueOf(list.get(stringIndex).number) + "" + String.valueOf(list.get(stringIndex + 1).number));
                        }
                        progressBar.setProgress(stringIndex + 2);

                    } else {
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


        ///przy kliknieciu wywołuje metode ktora przenosi id kliknietego przycisku tutaj a potem do adaptera wywołujac w nim metode ktora zmienia numer w liscie + notifydata..


        gridView.setAdapter(numbersAdapterAll);



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                numbersAdapterAll.setWchichOneId(i);


            }
        });


        Button endButton = findViewById(R.id.endSession);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextButton.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                textSwitcher.setVisibility(View.GONE);

                numbersAdapterAll.setToNone();

//////////////////////////////////
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                inflater.inflate(R.layout.session_number_inflation_keypad, (ViewGroup) findViewById(R.id.numbers_session_relativeMain));

                String[] numbers = new String[]{
                        "0" ,"1", "2", "3", "4", "5", "6", "7", "8", "9"
                };

                GridView gridViewKeypad = findViewById(R.id.gridViewKeypad);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(NumbersSession.this, R.layout.keypad_singleitem, numbers);
//                gridViewKeypad.setBackgroundColor(getResources().getColor(R.color.lightGreen));


                gridViewKeypad.setAdapter(adapter);
                gridViewKeypad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        itemClicked(i);



                    }
                });


            }


            //TODO
            private void itemClicked(int i) {
                numbersAdapterAll.setInList(i);
//                int start = gridView.getFirstVisiblePosition();
//                for (int k = start, j = gridView.getLastVisiblePosition(); k <= j; k++) {
//                    if (i == gridView.getItemIdAtPosition(k)) {
//                        View view = gridView.getChildAt(k - start);
//                        gridView.getAdapter().getView(k, view, gridView);
//                        break;
//                    }
//                }
            }


        });


    }


}
