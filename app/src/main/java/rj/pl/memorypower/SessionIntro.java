package rj.pl.memorypower;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.NumberPicker;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SessionIntro extends Activity {

    @BindView(R.id.Number_picker)
    NumberPicker numberPicker;

    private int numberPickerValue;

    private String[] values;

    private AdView adView;

    private int positionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_intro);

        ButterKnife.bind(this);


        getPositionAdapter();

//        setUpAds();

        setUpNumberPicker();

    }

    private void getPositionAdapter() {
        if (getIntent().getExtras()!=null)
            positionAdapter = getIntent().getExtras().getInt("selected");
    }

    @OnClick(R.id.StartSession)
    void startSession() {
        numberPickerValue = numberPicker.getValue() * 2 + 4;

        Intent intent = new Intent(this, Session.class);
        intent.putExtra("picker", numberPickerValue);
        intent.putExtra("selected",positionAdapter);
        startActivity(intent);
    }

    private void setUpNumberPicker() {



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

    private void setUpAds() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySettings", MODE_PRIVATE);
        boolean pr = sharedPreferences.getBoolean("PR", false);
        if (!pr) {
            MobileAds.initialize(this, "ca-app-pub-8569563470793396~4105033645");

            adView = findViewById(R.id.adViewNamesIntro);
            AdRequest adRequest = new AdRequest.Builder().build();

            adView.loadAd(adRequest);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.setting:
                startActivity(new Intent(this,Settings.class));
                break;

            case R.id.rate_app:

                try {
                    Uri uri1 = Uri.parse("market://details?id=" + getPackageName());
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri1);
                    startActivity(goToMarket);
                }catch (Exception e ){
                    Uri uri1 = Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName());
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri1);
                    startActivity(goToMarket);
                }

                break;


            case R.id.get_premium:
                startActivity(new Intent(this,GetPremium.class));
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}
