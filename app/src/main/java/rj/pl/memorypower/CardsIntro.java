package rj.pl.memorypower;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.NumberPicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CardsIntro extends Activity {

    @BindView(R.id.CNumber_picker)
    NumberPicker numberPicker;
    private int numberPickerValue;

    /**
     * for number picker
     */
    private String[] values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_intro);




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

    @OnClick(R.id.StartCardsSession)
    void startWordsSession() {

        numberPickerValue = numberPicker.getValue() * 2 + 4;

        Intent intent = new Intent(this, CardsSession.class);
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
