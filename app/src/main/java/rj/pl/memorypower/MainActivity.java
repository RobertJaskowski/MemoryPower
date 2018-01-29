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






    @BindDrawable(R.drawable.ic_filter_7_white_48dp)
    Drawable numbersIcon;

    @BindDrawable(R.drawable.ic_rate_review_white_48dp)
    Drawable wordsIcon;

    @BindDrawable(R.drawable.ic_layers_white_48dp)
    Drawable cardsIcon;

    @BindDrawable(R.drawable.ic_portrait_white_48dp)
    Drawable namesIcon;

    @BindDrawable(R.drawable.ic_filter_list_white_48dp)
    Drawable rankingIcon;

    @BindDrawable(R.drawable.ic_timeline_white_48dp)
    Drawable statsIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);




        String[] menuNames = new String[]{"Numbers", "Words", "Cards", "Names", "Ranking", "Stats"};
        Drawable[] menuImages = new Drawable[]{numbersIcon,wordsIcon,cardsIcon,namesIcon,rankingIcon,statsIcon};

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
            case R.id.get_premium:
                startActivity(new Intent(this,GetPremium.class));
                break;

                default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}
