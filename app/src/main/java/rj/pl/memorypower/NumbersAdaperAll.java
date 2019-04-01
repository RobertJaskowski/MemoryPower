package rj.pl.memorypower;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  Created by Robert on 31.12.2017.
 */

class NumbersAdapterAll extends BaseAdapter {



    /**
     * lista w NumbersAdapterAll
     */
    private ArrayList<Number_item> list;

    private ArrayList<Number_item> listaCopiaStart;

    private boolean canContinue = false;


    ArrayList<Number_item> getList() {
        return list;
    }

    private Context context;
    private int ile;
    private int wchichOneId;


    private int scoreE = 0;

    int getScoreE() {
        return scoreE;
    }

    //    private Random random = new Random();

    NumbersAdapterAll(Context c, int ile) {
        this.context = c;
        this.ile = ile;
        list = new ArrayList<>();
        listaCopiaStart = new ArrayList<>();



        for (int i = 0; i < ile; i++) {
            Number_item tempNumber = new Number_item(i + 1, String.valueOf(new Random().nextInt(10)));
            list.add(tempNumber);
            listaCopiaStart.add(tempNumber);
        }



    }

    private void countScores(){
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).number.equals(listaCopiaStart.get(i).number)){
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

     class ViewHolder {

        @BindView(R.id.single_number_all_textView1)
        TextView textView1;
        @BindView(R.id.single_number_all_textView2)
        TextView textView2;
        ViewHolder(View v) {
            ButterKnife.bind(this,v);
        }

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater!=null)
            row = inflater.inflate(R.layout.single_number_all, viewGroup, false);
            holder = new ViewHolder(row);
            if (row!=null)
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        if (!canContinue) {
            Number_item temp = list.get(i);
            holder.textView1.setText(String.valueOf(temp.number));
            holder.textView2.setText(String.valueOf(temp.id));
        } else {
            Number_item temp = list.get(i);
            Number_item tempStart = listaCopiaStart.get(i);
            if (temp.number.equals(tempStart.number)){
                if(view!=null)
                    view.setBackgroundResource(R.color.lightGreen);
//                scoreE++;

            }else {
                if(view!=null)
                view.setBackgroundResource(R.color.lightDark);
                holder.textView2.setText(tempStart.number);
                holder.textView2.setTextColor(view.getResources().getColor(R.color.lightGreen));

            }
        }

        return row;
    }


    void setToNone() {
        for (int i = 0; i < ile; i++) {
            Number_item tempNumber = new Number_item(i + 1, " ");
            list.set(i, tempNumber);

        }
        notifyDataSetChanged();
    }

    void setInList(int i) {

        Number_item tempNumber = new Number_item(wchichOneId + 1, String.valueOf(i));
        list.set(wchichOneId, tempNumber);
        notifyDataSetChanged();
        if (wchichOneId < ile - 1) {
            wchichOneId++;
        }
    }

    void skonczonePokaz() {
        canContinue = true;
        countScores();
        notifyDataSetChanged();
    }

    void setWchichOneId(int wchichOneId) {
        this.wchichOneId = wchichOneId;
        Number_item tempNumber = new Number_item(wchichOneId + 1, " ");
        list.set(wchichOneId, tempNumber);

        ///jesli po kliknięciu endInput nie odswiezaj
        if (!canContinue) {
            notifyDataSetChanged();
        }
    }

}