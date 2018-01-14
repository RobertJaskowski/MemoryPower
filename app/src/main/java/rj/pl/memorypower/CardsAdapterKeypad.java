package rj.pl.memorypower;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropTransformation;

/**
 * Created  by Robert on 09.01.2018 - 19:57.
 */

public class CardsAdapterKeypad extends RecyclerView.Adapter<CardsAdapterKeypad.MyViewHolder> {
    Context context;
    ArrayList<Integer> cards;

    LayoutInflater infalter;

    public CardsAdapterKeypad(Context context, ArrayList<Integer> cards) {
        this.context = context;
        this.cards = cards;

        infalter = LayoutInflater.from(context);

        EventBus.getDefault().register(this);

        for (Integer l : cards) {
            Log.e("test", l.toString());

        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addFromMain(MessageEventIntInsertToKeypad event) {
        Log.e("kepadadapterAddFrSub", String.valueOf(event.getId()));
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
//        myViewHolder.textView.setText(cards.get(position));
        MyTag myTag = new MyTag("asd", cards.get(position), "asd");

        Picasso
                .with(context)
                .load(cards.get(position))
                .transform(new CropTransformation(150, 120, CropTransformation.GravityHorizontal.LEFT, CropTransformation.GravityVertical.TOP))
                .into(myViewHolder.imageView);

        myViewHolder.imageView.setTag(myTag);

    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //        TextView textView;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.CardKeypadImg1);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

//            EventBus.getDefault().post(new MessageEventInt(Integer.parseInt(textView.getText().toString())));
//            EventBus.getDefault().post(new MessageEventInt(imageView.getTag());//


            if (getLayoutPosition() >= 0) {
                MyTag myTag = (MyTag) view.getTag();
                EventBus.getDefault().post(new MessageEventInt(myTag.image));
                Log.e("kepadonclickid", String.valueOf(view.getId()));//this bad, not used
                Log.e("kepadonclick", String.valueOf(myTag.image));
                delete(getLayoutPosition());
            }else{
                Log.e("minus1", String.valueOf(getLayoutPosition()));
            }


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
