package rj.pl.memorypower;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CardsSession extends Activity {

    public static final String EXTRA_PICKER = "picker";

    @BindString(R.string.slash)
    String slash;

    @BindColor(R.color.colorAccent)
    int colorAccent;

    @BindView(R.id.gridViewCards)
    ExpandableHeightGridView gridView;

//    @BindView(R.id.text_switcher_cards)
//    TextSwitcher textSwitcher;//todo

//    @BindView(R.id.imageSwitcher_cards)
//    ImageSwitcher imageSwitcher;


//    @BindView(R.id.sessionButton_TextSwitcher_cards)
//    Button nextButton;

//    @BindView(R.id.progressBar_cards_session)
//    ProgressBar progressBar;

    @BindView(R.id.endSessionInputCards)
    Button buttonEndSessionInput;

    @BindView(R.id.endSessionCards)
    Button endSessionCards;

    class laterInflationViewsPack {
        @BindView(R.id.przyciskOneInKeypadWord)
        Button przyciskOneInkeypad;
        @BindView(R.id.przyciskTwoInKeypadWord)
        Button przyciskTwoInKeypad;

        @BindView(R.id.textViewInKeypadWord)
        TextView textViewInKeypad;

        @BindView(R.id.wordKeypadRecycler)
        RecyclerView recyclerViewKeypad;

        @BindView(R.id.textViewSaveScoreInKeypadWord)
        TextView textViewSaveScoreInKeypad;
    }

    CardsSession.laterInflationViewsPack laterInflationViews;
    View viewInflated;

    int pickerValue;

//    private int stringIndex = 0;
//    private TextView textView;


    CardsAdapterAll cardsAdapterAll;
    ArrayList<Cards_item> list;
    ArrayList<Integer> words;


    MyTagTwo tag;

    CardsAdapterKeypad cardsAdapterKeypad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_session);
        ButterKnife.bind(this);

        //noinspection ConstantConditions
        pickerValue = (int) getIntent().getExtras().get(EXTRA_PICKER);


//        final ExpandableHeightGridView gridView = findViewById(R.id.gridViewWords);
        gridView.setExpanded(true);

        gridView.setEnabled(false);

        cardsAdapterAll = new CardsAdapterAll(this, pickerValue);

        list = cardsAdapterAll.getList();

//        progressBar.setMax(list.size());
//        progressBar.getProgressDrawable().setColorFilter(colorAccent, PorterDuff.Mode.SRC_IN);


//        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
//            @Override
//            public View makeView() {
//                textView = new TextView(CardsSession.this);
//                textView.setBackgroundColor(colorAccent);
//                textView.setTextColor(Color.WHITE);
//                textView.setTextSize(40);
//                textView.setGravity(Gravity.CENTER_HORIZONTAL);
//
//                return textView;
//            }
//        });
//
//        textSwitcher.setText(String.valueOf(list.get(stringIndex).card));


//        stringIndex += 1;
//        progressBar.setProgress(stringIndex);


        gridView.setAdapter(cardsAdapterAll);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cardsAdapterAll.setWchichOneId(i);
                View viewItem = adapterView.getChildAt(i);
                if (viewItem != null) {
//                    TextView textV = viewItem.findViewById(R.id.single_word_all_textView1);
//                    String text = (String) textV.getText();
//                    Integer integer = Integer.parseInt((String) textV.getText());
//                    ImageView img = viewItem.findViewById(R.id.single_card_all_img1);
//                    MyTag myTag = (MyTag) view.getTag();
//                    cardsAdapterKeypad.insert(myTag.image);
                    tag = (MyTagTwo) viewItem.getTag(R.id.TAG_ONLINE);
                    Log.e("cardssesGrOnclick", String.valueOf(tag.image));
                    if (tag.image != R.drawable.card0) {
                        EventBus.getDefault().post(new MessageEventIntInsertToKeypad(tag.image));
                    }

                }


            }
        });


        buttonEndSessionInput.setVisibility(View.INVISIBLE);

        words = new ArrayList<Integer>(list.size());
        for (int i = 0; i < list.size(); i++) {
            words.add(list.get(i).card);
            Log.e("dodajeDoWords", String.valueOf(list.get(i).card));
        }
        Collections.shuffle(words);

        laterInflationViews = new CardsSession.laterInflationViewsPack();

    }

    @OnClick(R.id.endSessionCards)
    void endSession() {
//        nextButton.setVisibility(View.GONE);
//        progressBar.setVisibility(View.GONE);
//        textSwitcher.setVisibility(View.GONE);
        endSessionCards.setVisibility(View.GONE);

        gridView.setEnabled(true);

        cardsAdapterAll.setToNone();

        final RelativeLayout layout = findViewById(R.id.cards_session_relativeMain);
        final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//                final View viewKeys = inflater.inflate(R.layout.session_number_inflation_keypad, (ViewGroup) findViewById(R.id.numbers_session_relativeMain));


        if (inflater != null)
            viewInflated = inflater.inflate(R.layout.session_word_inflation_keypad, layout);

        timer.cancel();
        timerText.setVisibility(View.GONE);
        timerText.setText("");

//                przyciskOneInkeypad.setVisibility(View.GONE);

        ButterKnife.bind(laterInflationViews, viewInflated);


//        laterInflationViews.recyclerViewKeypad = findViewById(R.id.wordKeypadRecycler);

        cardsAdapterKeypad = new CardsAdapterKeypad(CardsSession.this, words);


        laterInflationViews.recyclerViewKeypad.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL));
        laterInflationViews.recyclerViewKeypad.setAdapter(cardsAdapterKeypad);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                buttonEndSessionInput.setVisibility(View.VISIBLE);
            }
        }, 3000);
    }

    @OnClick(R.id.endSessionInputCards)
    void endSessionInput() {
        sesjaSkonczonaPokaz();

        gridView.setClickable(false);

        buttonEndSessionInput.setVisibility(View.INVISIBLE);


        laterInflationViews.recyclerViewKeypad.setVisibility(View.GONE);


        laterInflationViews.przyciskOneInkeypad.setVisibility(View.VISIBLE);
        laterInflationViews.przyciskTwoInKeypad.setVisibility(View.VISIBLE);
        laterInflationViews.textViewInKeypad.setVisibility(View.VISIBLE);
        laterInflationViews.textViewSaveScoreInKeypad.setVisibility(View.VISIBLE);

        Handler handlerr = new Handler();
        handlerr.postDelayed(new Runnable() {
            @Override
            public void run() {
                int scoreE = cardsAdapterAll.getScoreE();
                int scoreM = pickerValue;

                if (scoreE <= 0) {
                    laterInflationViews.textViewInKeypad.setText(String.format("0" + slash + "%d", scoreM));
                } else {
                    //TODO null couse not binded view
                    laterInflationViews.textViewInKeypad.setText(String.format(scoreE + slash + "%d", scoreM));
                }


            }
        }, 1000);
    }

    private void sesjaSkonczonaPokaz() {
        cardsAdapterAll.skonczonePokaz();
    }

//    @OnClick(R.id.sessionButton_TextSwitcher_cards)
//    void nextButton() {
//        if (stringIndex == list.size() - 1) {
//
//            if (progressBar.getProgress() != progressBar.getMax()) {
//                textSwitcher.setText(String.valueOf(list.get(stringIndex).card));
//            }
//            progressBar.setProgress(stringIndex + 1);
//
//        } else {
//            textSwitcher.setText(String.valueOf(list.get(stringIndex).card));
//            stringIndex += 1;
//            progressBar.setProgress(stringIndex);
//
//        }
//    }

    CountDownTimer timer;

    private void startTimer() {
        timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {

                timerText.setText(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(l)));
            }

            @Override
            public void onFinish() {
                timerText.setVisibility(View.GONE);
                timerText.setText("");

                endSessionCards.performClick();
            }
        };
        timer.start();
    }

    private TextView timerText;


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
        EventBus.getDefault().unregister(cardsAdapterAll);
        EventBus.getDefault().unregister(cardsAdapterKeypad);
    }
}

