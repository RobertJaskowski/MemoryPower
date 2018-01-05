package rj.pl.memorypower;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class WordsSession extends Activity {

    public static final String EXTRA_PICKER = "picker";

    int pickerValue;
    private TextSwitcher textSwitcher;
    private Button nextButton;
    private ProgressBar progressBar;
    private int stringIndex = 0;
    private TextView textView;

    Button endButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_session);

        pickerValue = (int) getIntent().getExtras().get(EXTRA_PICKER);


        final ExpandableHeightGridView gridView = findViewById(R.id.gridViewWords);
        gridView.setExpanded(true);

        gridView.setEnabled(false);

        final WordsAdapterAll wordsAdapterAll = new WordsAdapterAll(this, pickerValue);

        final ArrayList<Words_item> list = wordsAdapterAll.getList();


        //setting Text Switcher

        textSwitcher = findViewById(R.id.text_switcher_words);
        nextButton = findViewById(R.id.sessionButton_TextSwitcher_words);
        progressBar = findViewById(R.id.progressBar_words_session);





        progressBar.setMax(list.size());
        progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#f05545"), PorterDuff.Mode.SRC_IN);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {

                    if (stringIndex == list.size() - 1) {

                        if (progressBar.getProgress() != progressBar.getMax()) {
                            textSwitcher.setText(String.valueOf(list.get(stringIndex).word));
                        }
                        progressBar.setProgress(stringIndex + 1);

                    } else {
                        textSwitcher.setText(String.valueOf(list.get(stringIndex).word));
                        stringIndex += 1;
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

        textSwitcher.setText(String.valueOf(list.get(stringIndex).word));
        stringIndex += 1;
        progressBar.setProgress(stringIndex);


        gridView.setAdapter(wordsAdapterAll);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                wordsAdapterAll.setWchichOneId(i);


            }
        });


        final Button buttonEndSessionInput = findViewById(R.id.endSessionInputWords);
        buttonEndSessionInput.setVisibility(View.INVISIBLE);

        final ArrayList<String> words = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            words.add(list.get(i).word);
        }
        Collections.shuffle(words);



        endButton = findViewById(R.id.endSessionWords);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextButton.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                textSwitcher.setVisibility(View.GONE);
                endButton.setVisibility(View.GONE);

                gridView.setEnabled(true);

                wordsAdapterAll.setToNone();

                final RelativeLayout layout = findViewById(R.id.words_session_relativeMain);
                final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//                final View viewKeys = inflater.inflate(R.layout.session_number_inflation_keypad, (ViewGroup) findViewById(R.id.numbers_session_relativeMain));


                inflater.inflate(R.layout.session_number_inflation_keypad, layout);


                final Button przyciskOneInkeypad = findViewById(R.id.przyciskOneInKeypad);
                final Button przyciskTwoInKeypad = findViewById(R.id.przyciskTwoInKeypad);
                final TextView textViewInKeypad = findViewById(R.id.textViewInKeypad);
                final TextView textViewSaveScoreInKeypad = findViewById(R.id.textViewSaveScoreInKeypad);

//                przyciskOneInkeypad.setVisibility(View.GONE);


                final GridView gridViewKeypad = findViewById(R.id.gridViewKeypad);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(WordsSession.this, R.layout.keypad_singleitem, words);
//                gridViewKeypad.setBackgroundColor(getResources().getColor(R.color.lightGreen));


                gridViewKeypad.setAdapter(adapter);
                gridViewKeypad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        TextView tv = view.findViewById(R.id.text1);
                        itemClicked(((String) tv.getText()));

                    }
                });


                buttonEndSessionInput.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sesjaSkonczonaPokaz();

                        gridView.setClickable(false);

                        buttonEndSessionInput.setVisibility(View.INVISIBLE);


                        gridViewKeypad.setVisibility(View.GONE);


                        przyciskOneInkeypad.setVisibility(View.VISIBLE);
                        przyciskTwoInKeypad.setVisibility(View.VISIBLE);
                        textViewInKeypad.setVisibility(View.VISIBLE);
                        textViewSaveScoreInKeypad.setVisibility(View.VISIBLE);

                        Handler handlerr = new Handler();
                        handlerr.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                int scoreE = wordsAdapterAll.getScoreE() - 4;
                                int scoreM = pickerValue;

                                if (scoreE <= 0) {
                                    textViewInKeypad.setText(0 + "/" + scoreM);
                                } else {
                                    textViewInKeypad.setText(scoreE + "/" + scoreM);
                                }


                            }
                        }, 1000);


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
                }, 3000);


            }

            private void sesjaSkonczonaPokaz() {
                wordsAdapterAll.skonczonePokaz();
            }



            private void itemClicked(String i) {
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

    private void startTimer(long duration, long interval){
        CountDownTimer timer = new CountDownTimer(duration,interval) {
            @Override
            public void onTick(long l) {

                timerText.setText(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(l)));

            }

            @Override
            public void onFinish() {
                timerText.setVisibility(View.GONE);
                timerText.setText("");

                endButton.performClick();
            }
        };
        timer.start();
    }

    private TextView timerText;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        MenuItem timerItem = menu.findItem(R.id.timer_session);
        timerText = (TextView) timerItem.getActionView();
        timerText.setTextSize(30);

        startTimer(10000,1000);


        return true;
    }

}

