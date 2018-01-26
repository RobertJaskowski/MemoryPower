package rj.pl.memorypower;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.main_recycler)
    RecyclerView recyclerView;



    @SuppressWarnings("WeakerAccess")
    @BindDrawable(R.drawable.ic_accessible_white_48dp)
    Drawable temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        //todo change icon
//        int temp = R.drawable.ic_accessible_white_48dp;

        String[] menuNames = new String[]{"Numbers", "Words", "Cards", "Names", "Ranking", "Stats"};
        Drawable[] menuImages = new Drawable[]{temp,temp,temp,temp,temp,temp};

        CardAdapterMain adapter = new CardAdapterMain(menuNames, menuImages);

        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);


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

                default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}
