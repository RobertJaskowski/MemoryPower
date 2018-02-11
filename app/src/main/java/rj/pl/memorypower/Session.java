package rj.pl.memorypower;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Session extends Activity {

    private int pickerValue;
    private int selected;

    @BindView(R.id.sessionRecyclerTop)
    RecyclerView sessionRecyclerTop;

    @BindView(R.id.sessionRecyclerBottom)
    RecyclerView sessionRecyclerBottom;

    @BindColor(R.color.colorAccent)
    int colorAccent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        ButterKnife.bind(this);

        getIntentsExtras();

        SessionAdapter sessionAdapter = new SessionAdapter(this, pickerValue, selected);




    }

    private void getIntentsExtras() {
        if (getIntent().getExtras() != null) {
            pickerValue = getIntent().getExtras().getInt("picker");
            selected = getIntent().getExtras().getInt("selected");
        }
    }
}
