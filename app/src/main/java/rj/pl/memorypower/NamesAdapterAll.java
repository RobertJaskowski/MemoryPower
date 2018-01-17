package rj.pl.memorypower;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Robert on 07.01.2018 - 18:35.
 */

class NamesAdapterAll extends BaseAdapter{
    private Context context;
    private int ile;


    private ArrayList<Names_item> list;

    ArrayList<Names_item> getList() {
        return list;
    }

    private ArrayList<Names_item> listCopiaStart;

    private boolean canContinue = false;


    private int wchichOneId;


    private int scoreE = 0;

    public int getScoreE() {
        return scoreE;
    }



    public NamesAdapterAll(Context c, int ile) {
        this.context = c;
        this.ile = ile;



        list = new ArrayList<>();
        listCopiaStart = new ArrayList<>();


        for (int i = 0; i < ile; i++) {
//            Log.e("a", String.valueOf(new Random().nextInt(9- 1 + 1) +1));
            Names_item temp = new Names_item(i + 1, new Random().nextInt(20 - 1 + 1) + 1);
            list.add(temp);
            listCopiaStart.add(temp);
        }

        EventBus.getDefault().register(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setInListEvent(MessageEventWords event) {
        if (!list.get(wchichOneId ).word.equals(" ")){
            addToKeypadEvent();
        }
        Names_item tempNumber = new Names_item(wchichOneId + 1, event.getText());
        list.set(wchichOneId, tempNumber);
        notifyDataSetChanged();
        if (wchichOneId < ile - 1) {
            wchichOneId++;
        }
    }
    private void addToKeypadEvent(){
        EventBus.getDefault().post(new MessageEventWordsInsertToKeypad(list.get(wchichOneId).word));
    }

    private void countScores(){
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).word.equals(listCopiaStart.get(i).word)){
                scoreE++;
                Log.d("score:","");
            }
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
        NamesAdapterAll.ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_word_all, viewGroup, false);
            holder = new NamesAdapterAll.ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (NamesAdapterAll.ViewHolder) row.getTag();
        }

        if (!canContinue) {
            Names_item temp = list.get(i);
            holder.textView1.setText(String.valueOf(temp.word));
            holder.textView2.setText(String.valueOf(temp.id));
        } else {
            Names_item temp = list.get(i);
            Names_item tempStart = listCopiaStart.get(i);
            if (temp.word.equals(tempStart.word)) {
                view.setBackgroundResource(R.color.lightGreen);

            } else {
                view.setBackgroundResource(R.color.lightDark);
                holder.textView2.setText(tempStart.word);
                holder.textView2.setTextColor(view.getResources().getColor(R.color.lightGreen));

            }
        }

        return row;
    }

    public void setToNone() {
        for (int i = 0; i < ile; i++) {
            Names_item tempNumber = new Names_item(i + 1, 0);
            list.set(i, tempNumber);

        }
        notifyDataSetChanged();
    }


//    public void setInList(String i) {
//        Words_item tempNumber = new Words_item(wchichOneId + 1, i);
//        list.set(wchichOneId, tempNumber);
//        notifyDataSetChanged();
//        if (wchichOneId < ile - 1) {
//            wchichOneId++;
//        }
//    }

    public void skonczonePokaz() {
        canContinue = true;
        countScores();
        notifyDataSetChanged();
    }

    public void setWchichOneId(int wchichOneId) {
        this.wchichOneId = wchichOneId;
        Names_item tempNumber = new Names_item(wchichOneId + 1, 0);
        list.set(wchichOneId, tempNumber);

        ///jesli po kliknięciu endInput nie odswiezaj
        if (!canContinue) {
            notifyDataSetChanged();
        }
    }
}
