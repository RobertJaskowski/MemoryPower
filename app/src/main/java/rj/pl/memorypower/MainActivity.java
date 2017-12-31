package rj.pl.memorypower;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.main_recycler);


        //todo change
        int temp = R.drawable.ic_accessible_white_48dp;

        String[] menuNames = new String[]{"Numbers", "Words", "Cards", "Names", "5test", "Stats"};
        int[] menuImages = new int[]{temp,temp,temp,temp,temp,temp};

        CardAdapterMain adapter = new CardAdapterMain(menuNames, menuImages);

        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);


    }

}
