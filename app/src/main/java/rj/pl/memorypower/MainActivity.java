package rj.pl.memorypower;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @BindView(R.id.main_recycler)
    RecyclerView recyclerView;
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

}
