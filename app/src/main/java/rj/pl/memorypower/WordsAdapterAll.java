package rj.pl.memorypower;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Robert on 03.01.2018 - 09:56.
 */

public class WordsAdapterAll extends BaseAdapter {
    private Context context;
    private int ile;


    private ArrayList<Words_item> list;

    public ArrayList<Words_item> getList() {
        return list;
    }

    private ArrayList<Words_item> listCopiaStart;

    private boolean canContinue = false;


    private int wchichOneId;


    private int scoreE = 0;

    public int getScoreE() {
        return scoreE;
    }

    private int scoreM = 0;

    public WordsAdapterAll(Context c,int ile) {
        this.context = c;
        this.ile = ile;

        scoreM = ile;

        list = new ArrayList<>();
        listCopiaStart = new ArrayList<>();


        for (int i = 0; i < ile; i++) {
//            Log.e("a", String.valueOf(new Random().nextInt(9- 1 + 1) +1));
            Words_item temp = new Words_item(i+1,new Random().nextInt(9 - 1 +1 )+1);
            list.add(temp);
            listCopiaStart.add(temp);
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
    private class ViewHolder {


        TextView textView1;

        TextView textView2;
        ViewHolder(View v) {
            textView1 = v.findViewById(R.id.single_word_all_textView1);
            textView2 = v.findViewById(R.id.single_word_all_textView2);
        }

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = view;
        WordsAdapterAll.ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_word_all, viewGroup, false);
            holder = new WordsAdapterAll.ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (WordsAdapterAll.ViewHolder) row.getTag();
        }

        if (!canContinue) {
            Words_item temp = list.get(i);
            holder.textView1.setText(String.valueOf(temp.word));
            holder.textView2.setText(String.valueOf(temp.id));
        } else {
            Words_item temp = list.get(i);
            Words_item tempStart = listCopiaStart.get(i);
            if (temp.word.equals(tempStart.word)){
                view.setBackgroundResource(R.color.lightGreen);
                scoreE++;
            }else {
                view.setBackgroundResource(R.color.lightDark);
                holder.textView2.setText(tempStart.word);
                holder.textView2.setTextColor(view.getResources().getColor(R.color.lightGreen));

            }
        }

        return row;
    }

    public void setToNone() {
        for (int i = 0; i < ile; i++) {
            Words_item tempNumber = new Words_item(i + 1, 0);
            list.set(i, tempNumber);

        }
        notifyDataSetChanged();
    }

    public void setInList(String i) {
        Words_item tempNumber = new Words_item(wchichOneId + 1, i);
        list.set(wchichOneId, tempNumber);
        notifyDataSetChanged();
        if (wchichOneId < ile - 1) {
            wchichOneId++;
        }
    }

    public void skonczonePokaz() {
        canContinue = true;
        notifyDataSetChanged();
    }

    public void setWchichOneId(int wchichOneId) {
        this.wchichOneId = wchichOneId;
        Words_item tempNumber = new Words_item(wchichOneId + 1, 0);
        list.set(wchichOneId, tempNumber);

        ///jesli po kliknięciu endInput nie odswiezaj
        if (!canContinue) {
            notifyDataSetChanged();
        }
    }
}
