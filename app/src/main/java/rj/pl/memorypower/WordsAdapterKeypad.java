package rj.pl.memorypower;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by PC on 05.01.2018.
 */

public class WordsAdapterKeypad extends RecyclerView.Adapter<WordsAdapterKeypad.MyViewHolder>{

    Context context;
    ArrayList<String> words;

    LayoutInflater infalter;


    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private Listener listener;

    public static interface Listener{
        public void OnClick(String text);
    }

    public WordsAdapterKeypad(Context context, ArrayList<String> words) {
        this.context = context;
        this.words = words;

        infalter = LayoutInflater.from(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        View view = infalter.inflate(R.layout.keypad_word_item,parent,false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        myViewHolder.textView.setText(words.get(position));
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);

            textView = (TextView)itemView.findViewById(R.id.WordKeypadText1);
            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(listener!=null) {
                listener.OnClick((String) textView.getText());
                delete(getLayoutPosition());
            }
        }
    }

    public void delete(int position){
        words.remove(position);
        notifyItemRemoved(position);
    }
    public void insert(String text){
        words.add(text);
        notifyItemInserted(words.size()-1);
    }
}
