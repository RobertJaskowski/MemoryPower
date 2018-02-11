package rj.pl.memorypower;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Robert on 10.02.2018 - 22:52.
 */

public class SessionAdapter extends BaseAdapter {

    static ArrayList<Object> list;

    private ArrayList<Object> listCopyStart;

    private boolean canContinue = false;

    private Context context;
    private int ile;
    private int wchichOneId;
    private int selected;

    public SessionAdapter(Context c, int ile, int selected) {
        this.context = c;
        this.ile = ile;
        this.selected = selected;

        list = new ArrayList<>();
        listCopyStart = new ArrayList<>();


        if (selected == 0) {

            for (int i = 0; i < ile; i++) {
                Number_item tempNumber = new Number_item(i + 1, String.valueOf(new Random().nextInt(10)));
                list.add(tempNumber);
                listCopyStart.add(tempNumber);
            }

        } else {
//todo
        }

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
