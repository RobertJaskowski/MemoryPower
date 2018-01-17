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

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
@SuppressWarnings("WeakerAccess")
public class NumbersSession extends Activity {


    public static final String EXTRA_PICKER = "picker";


    private int pickerValue;

    @BindView(R.id.text_switcher)
    TextSwitcher textSwitcher;

    @BindView(R.id.sessionButton_TextSwitcher)
    Button nextButton;
    @BindView(R.id.progressBar_number_session)
    ProgressBar progressBar;

    @BindView(R.id.gridView)
    ExpandableHeightGridView gridView;

    @BindColor(R.color.colorAccent)
    int colorAccent;

    @BindView(R.id.endSessionInput)
    Button buttonEndSessionInput;


    @BindView(R.id.endSession)
    Button endSession;


    class laterInflationViewsPack {
        @BindView(R.id.przyciskOneInKeypad)
        Button przyciskOneInkeypad;
        @BindView(R.id.przyciskTwoInKeypad)
        Button przyciskTwoInKeypad;


        @BindView(R.id.textViewInKeypad)
        TextView textViewInKeypad;

        @BindView(R.id.textViewSaveScoreInKeypad)
        TextView textViewSaveScoreInKeypad;

        @BindView(R.id.gridViewKeypad)
        GridView gridViewKeypad;
    }

    laterInflationViewsPack laterInflationViews;
    View viewInflated;


    @BindView(R.id.numbers_session_relativeMain)
    RelativeLayout layout;

    @BindString(R.string.slash)
    String slash;

    private NumbersAdapterAll numbersAdapterAll;

    private ArrayList<Number_item> list;

    /**
     * dynamically generated TextView
     */
    private TextView textView;

    private int stringIndex = 0;

    private CountDownTimer timer;

    private int timerAchived = 0;
    private TextView timerText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers_session);

        ButterKnife.bind(this);


        //noinspection ConstantConditions
        pickerValue = (int) getIntent().getExtras().get(EXTRA_PICKER);


        gridView.setExpanded(true);

        gridView.setEnabled(false);

        numbersAdapterAll = new NumbersAdapterAll(this, pickerValue);

        list = numbersAdapterAll.getList();


        progressBar.setMax(list.size());
        progressBar.getProgressDrawable().setColorFilter(colorAccent, PorterDuff.Mode.SRC_IN);


        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                textView = new TextView(NumbersSession.this);
                textView.setBackgroundColor(colorAccent);
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

//todo
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                numbersAdapterAll.setWchichOneId(i);


            }
        });


        buttonEndSessionInput.setVisibility(View.INVISIBLE);
        laterInflationViews = new laterInflationViewsPack();

    }

    @OnClick(R.id.sessionButton_TextSwitcher)
    public void onClick() {


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

    @OnClick(R.id.endSession)
    void endSession() {

        nextButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        textSwitcher.setVisibility(View.GONE);
        endSession.setVisibility(View.GONE);

        gridView.setEnabled(true);


        numbersAdapterAll.setToNone();


        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        if (inflater != null)
            viewInflated = inflater.inflate(R.layout.session_number_inflation_keypad, layout);


        timer.cancel();
        timerText.setVisibility(View.GONE);
        timerText.setText("");


        String[] numbers = new String[]{
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
        };


        ArrayAdapter<String> adapter = new ArrayAdapter<>(NumbersSession.this, R.layout.keypad_singleitem, numbers);


        ButterKnife.bind(laterInflationViews, viewInflated);

        laterInflationViews.gridViewKeypad.setAdapter(adapter);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                buttonEndSessionInput.setVisibility(View.VISIBLE);
            }
        }, 3000);


        bindClickEvents();


    }

    /**
     * Binds clicks for keypad after binding butterknife
     */
    private void bindClickEvents() {
        laterInflationViews.gridViewKeypad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemClicked(i);

            }
        });
    }

    @OnClick(R.id.endSessionInput)
    public void endSessionInput() {
        sesjaSkonczonaPokaz();

        gridView.setClickable(false);

        buttonEndSessionInput.setVisibility(View.GONE);

        laterInflationViews.gridViewKeypad.setVisibility(View.GONE);



        int scoreE = numbersAdapterAll.getScoreE();
        int scoreM = pickerValue;


        laterInflationViews.przyciskOneInkeypad.setVisibility(View.VISIBLE);
        if (scoreE>3)//dont allow if less than 3 database is not a trashcan, todo in other classes and fields timer to add
        laterInflationViews.przyciskTwoInKeypad.setVisibility(View.VISIBLE);

        laterInflationViews.textViewInKeypad.setVisibility(View.VISIBLE);
        laterInflationViews.textViewSaveScoreInKeypad.setVisibility(View.VISIBLE);




        if (scoreE <= 0) {
            laterInflationViews.textViewInKeypad.setText(String.format("0" + slash + "%d", scoreM));

        } else {
            laterInflationViews.textViewInKeypad.setText(String.format(scoreE + slash + "%d", scoreM));
        }


//        Handler handlerr = new Handler();  //handler not needed anymore , count scores always is first
//        handlerr.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                int scoreE = numbersAdapterAll.getScoreE();
//                int scoreM = pickerValue;
//
//                if (scoreE <= 0) {
////                    laterInflationViews.textViewInKeypad.setText(0 + slash + scoreM);
//                    laterInflationViews.textViewInKeypad.setText(String.format("0" + slash + "%d", scoreM));
//
//                } else {
//                    laterInflationViews.textViewInKeypad.setText(String.format(scoreE + slash + "%d", scoreM));
//                }
//
//
//            }
//        }, 1000);

        ButtonAfterSession buttonAfterSession = new ButtonAfterSession(this, 0, scoreE, scoreM, timerAchived);


        laterInflationViews.przyciskOneInkeypad.setOnClickListener(buttonAfterSession);
        laterInflationViews.przyciskTwoInKeypad.setOnClickListener(buttonAfterSession);

    }


    private void itemClicked(int i) {
        numbersAdapterAll.setInList(i);
    }

    private void sesjaSkonczonaPokaz() {
        numbersAdapterAll.skonczonePokaz();
    }


    private void startTimer() {
        timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                timerText.setText(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(l)));
                timerAchived++; //for sending how much time has passed in when clicked
            }

            @Override
            public void onFinish() {
                timerText.setVisibility(View.GONE);
                timerText.setText("");

                endSession.performClick();
            }
        };
        timer.start();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);


        MenuItem timerItem = menu.findItem(R.id.timer_session);
        timerText = (TextView) timerItem.getActionView();
        timerText.setTextSize(30);

        startTimer();


        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(numbersAdapterAll);
    }
}
