package rj.pl.memorypower;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

import static java.lang.String.format;

/**
 * Created  by Robert on 21.01.2018 - 01:01.
 */

public class customRankingAdapter extends BaseAdapter {

    private Context context;
    private List<User> userList;

    customRankingAdapter(Context c, List<User> userList) {
        this.userList = userList;
        this.context = c;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {

        TextView textViewPosition, textViewScore,textViewTime, textViewText;

        ViewHolder(View v) {
            textViewPosition = v.findViewById(R.id.firebase_adapter_position);
            textViewScore = v.findViewById(R.id.firebase_adapter_score);
            textViewTime = v.findViewById(R.id.firebase_adapter_time);
            textViewText = v.findViewById(R.id.firebase_adapter_text);
        }
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View row = view;
        customRankingAdapter.ViewHolder holder;

        if (row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //noinspection ConstantConditions
            row = inflater.inflate(R.layout.ranking_firebase_adapter,viewGroup,false);
            holder = new customRankingAdapter.ViewHolder(row);
            row.setTag(holder);

        }else{
            holder = (customRankingAdapter.ViewHolder) row.getTag();
        }

        User temp = userList.get(i);
        holder.textViewPosition.setText(format("%d:", i + 1));
        holder.textViewScore.setText(format("%dP", temp.score));
        holder.textViewTime.setText(format("%ds", temp.time));
        holder.textViewText.setText(temp.name);


        return row;
    }
}
