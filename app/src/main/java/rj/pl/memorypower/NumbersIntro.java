package rj.pl.memorypower;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.NumberPicker;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NumbersIntro extends Activity {

    @BindView(R.id.NNumber_picker)
    NumberPicker numberPicker;


    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers_intro);

        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");//todo change

        adView = findViewById(R.id.adViewNumbersIntro);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("EB1F0516010726D6D702F296F58A1DD4").build();//todo change
        adView.loadAd(adRequest);


        ButterKnife.bind(this);


        /*
      for number picker
     */
        String[] values = new String[24];

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
        int numberPickerValue = numberPicker.getValue() * 2 + 4;

        Intent intent = new Intent(this,NumbersSession.class);
        intent.putExtra("picker", numberPickerValue);
        startActivity(intent);
    }
}
