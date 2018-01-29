package rj.pl.memorypower;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GetPremium extends Activity implements BillingProcessor.IBillingHandler {

    @BindView(R.id.img3)
    ImageView ad3;

    @BindView(R.id.img4)
    ImageView ad4;

    @BindView(R.id.youOwnPremiumText)
    TextView youOwnPremiumText;

    BillingProcessor bp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_premium);

        bp = new BillingProcessor(this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsXXPL2kfbqkEFINcdqrvm3G40yVDcbhC/+kJeOk3rJ1WhrdyFv6Fzn6PI63Fp0n67sVInGpgTOF2B0yU9ejyOYsY/IX9a/GxtCSidawRZ7vgU25WUacEDxM4HnKmHXtyaVmcZ9JLkxT5CpQ/LWqCXkprzuNoATDGrL/klnP+1BJcHhV7phjsvzQZZSTjNBLOg1syWglQxwIWwN+MOMlGL8E3Rimk+3qggRT1XyeRSvSUi1BnzXpOlcLxCJaQZVv7V7JI583OK4GMzCyCRNr0hQshnUbD2jIxrAkqDO/6Glh3ZN/4icmeYnBFoZKhJfdNVu4wLQhY71N17PLbAs2V0wIDAQAB", this);


        ButterKnife.bind(this);


        Picasso
                .with(this)
                .load(R.drawable.ad1)
                .into(ad3);

        Picasso
                .with(this)
                .load(R.drawable.ad2)
                .into(ad4);


        checkForPremium();

    }

    private void checkForPremium() {
            SharedPreferences sharedPreferences = getSharedPreferences("MySettings", MODE_PRIVATE);
            boolean saved = sharedPreferences.getBoolean("PR", false);
            if (!saved) {
                boolean isPurchased = bp.isPurchased("memorypower_premium");

                if (isPurchased){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("PR",true);
                    editor.apply();

                    Toast.makeText(this,"Access granted",Toast.LENGTH_SHORT).show();

                    youOwnPremiumText.setVisibility(View.VISIBLE);
                }
            }

    }

    public void buyPremium(View view) {
        bp.purchase(this, "memorypower_premium");


    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        Toast.makeText(this, "Thank you for supporting this application!", Toast.LENGTH_LONG).show();

        SharedPreferences sharedPreferences = getSharedPreferences("MySettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("PR", true);

        editor.apply();

    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();



    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {

        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }
}
