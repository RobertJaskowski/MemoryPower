package rj.pl.memorypower;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GetPremium extends Activity implements BillingProcessor.IBillingHandler{

    @BindView(R.id.img3)
    ImageView ad3;

    @BindView(R.id.img4)
    ImageView ad4;

    BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_premium);

        bp = new BillingProcessor(this,null,this);//todo


        ButterKnife.bind(this);


        Picasso
                .with(this)
                .load(R.drawable.ad1)
                .into(ad3);

        Picasso
                .with(this)
                .load(R.drawable.ad2)
                .into(ad4);
    }

    public void buyPremium(View view) {
        bp.purchase(this,"android.test.purchased");


    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        Toast.makeText(this,"Thank you for supporting this application!",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode,resultCode,data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {

        if (bp !=null){
            bp.release();
        }
        super.onDestroy();
    }
}
