package rj.pl.memorypower;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

public class WordsSession extends Activity {

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
        setContentView(R.layout.activity_words_session);

        pickerValue = (int) getIntent().getExtras().get(EXTRA_PICKER);


        final ExpandableHeightGridView gridView = findViewById(R.id.gridViewWords);
        gridView.setExpanded(true);

        final WordsAdapterAll wordsAdapterAll = new WordsAdapterAll(this, pickerValue);

        final ArrayList<Words_item> list = wordsAdapterAll.getList();


        //setting Text Switcher

        textSwitcher = findViewById(R.id.text_switcher_words);
        nextButton = findViewById(R.id.sessionButton_TextSwitcher_words);
        progressBar = findViewById(R.id.progressBar_words_session);


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
                textView = new TextView(WordsSession.this);
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





        gridView.setAdapter(wordsAdapterAll);



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                wordsAdapterAll.setWchichOneId(i); TODO add this


            }
        });


        final Button buttonEndSessionInput = findViewById(R.id.endSessionInputWords);
        buttonEndSessionInput.setVisibility(View.INVISIBLE);


        final Button endButton = findViewById(R.id.endSessionWords);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextButton.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                textSwitcher.setVisibility(View.GONE);
                endButton.setVisibility(View.GONE);


//                wordsAdapterAll.setToNone(); TODO enable this

                final RelativeLayout layout = findViewById(R.id.words_session_relativeMain);
                final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//                final View viewKeys = inflater.inflate(R.layout.session_number_inflation_keypad, (ViewGroup) findViewById(R.id.numbers_session_relativeMain));


                final View viewKeys = inflater.inflate(R.layout.session_number_inflation_keypad,layout);




                final Button przyciskOneInkeypad = findViewById(R.id.przyciskOneInKeypad);
                final Button przyciskTwoInKeypad = findViewById(R.id.przyciskTwoInKeypad);
                final TextView textViewInKeypad = findViewById(R.id.textViewInKeypad);
                final TextView textViewSaveScoreInKeypad = findViewById(R.id.textViewSaveScoreInKeypad);

//                przyciskOneInkeypad.setVisibility(View.GONE);



                String[] numbers = new String[]{
                        "0" ,"1", "2", "3", "4", "5", "6", "7", "8", "9"
                };

                final GridView gridViewKeypad = findViewById(R.id.gridViewKeypad);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(WordsSession.this, R.layout.keypad_singleitem, numbers);
//                gridViewKeypad.setBackgroundColor(getResources().getColor(R.color.lightGreen));


                gridViewKeypad.setAdapter(adapter);
                gridViewKeypad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        itemClicked(i);

                    }
                });


                buttonEndSessionInput.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sesjaSkonczonaPokaz();

                        gridView.setClickable(false);

                        buttonEndSessionInput.setVisibility(View.GONE);

                        gridViewKeypad.setVisibility(View.GONE);


                        przyciskOneInkeypad.setVisibility(View.VISIBLE);
                        przyciskTwoInKeypad.setVisibility(View.VISIBLE);
                        textViewInKeypad.setVisibility(View.VISIBLE);
                        textViewSaveScoreInKeypad.setVisibility(View.VISIBLE);

                        Handler handlerr = new Handler();
                        handlerr.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                int scoreE = wordsAdapterAll.getScoreE()-4;
                                int scoreM = pickerValue;

                                if (scoreE <= 0){
                                    textViewInKeypad.setText(0+"/"+scoreM);
                                }else{
                                    textViewInKeypad.setText(scoreE+"/"+scoreM);
                                }


                            }
                        },1000);



//                        layout.removeView(viewKeys);

                        //viewKeys.setVisibility(View.INVISIBLE);
//                        ViewGroup parent = (ViewGroup) viewKeys.getParent();
//                        parent.removeView(viewKeys);

                    }

                });

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        buttonEndSessionInput.setVisibility(View.VISIBLE);
                    }
                },3000);


            }

            private void sesjaSkonczonaPokaz() {
                wordsAdapterAll.skonczonePokaz();
            }


            //TODO
            private void itemClicked(int i) {
                wordsAdapterAll.setInList(i);
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

