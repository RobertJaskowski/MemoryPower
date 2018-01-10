package rj.pl.memorypower;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created  by Robert on 09.01.2018 - 19:57.
 */

public class CardsAdapterKeypad extends RecyclerView.Adapter<CardsAdapterKeypad.MyViewHolder>{
    Context context;
    ArrayList<Integer> cards;

    LayoutInflater infalter;
    public CardsAdapterKeypad(Context context, ArrayList<Integer> cards) {
        this.context = context;
        this.cards = cards;

        infalter = LayoutInflater.from(context);

        EventBus.getDefault().register(this);


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addFromMain(MessageEventIntInsertToKeypad event) {
        insert(event.getId());
    }


    @Override
    public CardsAdapterKeypad.MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        View view = infalter.inflate(R.layout.keypad_card_item, parent, false);

        CardsAdapterKeypad.MyViewHolder holder = new CardsAdapterKeypad.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(CardsAdapterKeypad.MyViewHolder myViewHolder, int position) {
        myViewHolder.textView.setText(cards.get(position));
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.CardKeypadText1);
            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            EventBus.getDefault().post(new MessageEventInt(Integer.parseInt(textView.getText().toString())));
            delete(getLayoutPosition());

        }
    }

    public void delete(int position) {
        cards.remove(position);
        notifyItemRemoved(position);
    }

    public void insert(Integer integer) {
        cards.add(integer);
        notifyItemInserted(cards.size() - 1);
    }
}
