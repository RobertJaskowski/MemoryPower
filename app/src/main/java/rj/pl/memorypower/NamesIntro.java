package rj.pl.memorypower;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.NumberPicker;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NamesIntro extends Activity {

    @BindView(R.id.NamesNumber_picker)
    NumberPicker numberPicker;
    private int numberPickerValue;

    /**
     * for number picker
     */
    private String[] values;

    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("MySettings",MODE_PRIVATE);
        boolean pr = sharedPreferences.getBoolean("PR",false);
        if (!pr) {
            MobileAds.initialize(this, "ca-app-pub-8569563470793396~4105033645");//changed

            adView = findViewById(R.id.adViewNamesIntro);
//        AdRequest adRequest = new AdRequest.Builder().addTestDevice("EB1F0516010726D6D702F296F58A1DD4").build();//test purpose
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        }

        setContentView(R.layout.activity_names_intro);
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
    @OnClick(R.id.StartNamesSession)
    void startWordsSession() {

        numberPickerValue = numberPicker.getValue() * 2 + 4;

        Intent intent = new Intent(this, NamesSession.class);
        intent.putExtra("picker", numberPickerValue);
        startActivity(intent);
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
