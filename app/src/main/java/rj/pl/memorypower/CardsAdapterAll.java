package rj.pl.memorypower;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Random;

import jp.wasabeef.picasso.transformations.CropTransformation;

/**
 * Created  by Robert on 09.01.2018 - 18:30.
 */

class CardsAdapterAll extends BaseAdapter {
    private Context context;
    private int ile;

    private MyTagTwo myTagImage;





    private ArrayList<Cards_item> list;

    ArrayList<Cards_item> getList() {
        return list;
    }

    private ArrayList<Cards_item> listCopiaStart;

    private boolean canContinue = false;


    private int wchichOneId;


    private int scoreE = 0;

    public int getScoreE() {
        return scoreE;
    }



    public CardsAdapterAll(Context c, int ile) {
        this.context = c;
        this.ile = ile;



        list = new ArrayList<>();
        listCopiaStart = new ArrayList<>();


        for (int i = 0; i < ile; i++) {
//            Log.e("a", String.valueOf(new Random().nextInt(9- 1 + 1) +1));
            Cards_item temp = new Cards_item(i + 1, new Random().nextInt(52 - 1 + 1) + 1);// here was 20 bound - 52 max is good
            list.add(temp);
            listCopiaStart.add(temp);
        }

        EventBus.getDefault().register(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setInListEvent(MessageEventInt event) {
        Log.e("adapterAll", String.valueOf(list.get(wchichOneId).card)+" " + R.drawable.card0+" " + event.getId());
        if (list.get(wchichOneId ).card != R.drawable.card0){
            Log.e("dodaje do adapteraall","do adaptera");
            addToKeypadEvent();
        }
        Cards_item tempNumber = new Cards_item(wchichOneId + 1, event.getId(),0);
        list.set(wchichOneId, tempNumber);
        notifyDataSetChanged();
        if (wchichOneId < ile - 1) {
            wchichOneId++;
        }
    }

    private void addToKeypadEvent(){
        EventBus.getDefault().post(new MessageEventIntInsertToKeypad(list.get(wchichOneId).card));
    }

    private void countScores(){
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).card==listCopiaStart.get(i).card){
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

    private class ViewHolder  {


        ImageView imageView;

        TextView textView2;

        ViewHolder(View v) {
            imageView = v.findViewById(R.id.single_card_all_img1);
            textView2 = v.findViewById(R.id.single_card_all_textView2);
        }

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = view;
        CardsAdapterAll.ViewHolder holder;



        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_card_all, viewGroup, false);
            holder = new CardsAdapterAll.ViewHolder(row);
            row.setTag(R.id.Tag_holder,holder);
        } else {
            holder = (CardsAdapterAll.ViewHolder) row.getTag(R.id.Tag_holder);
        }

        if (!canContinue) {
            Cards_item temp = list.get(i);
            Picasso
                    .with(context)
                    .load(temp.card)
//                    .transform(new CropTransformation(5,CropTransformation.GravityHorizontal.CENTER,CropTransformation.GravityVertical.TOP))
                    .into(holder.imageView);

            myTagImage = new MyTagTwo("asd", temp.card ,"asd");
            Log.e("allListgetICard", String.valueOf(temp.card));
//            holder.imageView.setTag(R.id.TAG_ONLINE,myTagImage);

            row.setTag(R.id.TAG_ONLINE,myTagImage);

            holder.textView2.setText(String.valueOf(temp.id));
        } else {
            Cards_item temp = list.get(i);
            Cards_item tempStart = listCopiaStart.get(i);
            if (temp.card==tempStart.card) {
                view.setBackgroundResource(R.color.lightGreen);

            } else {
                view.setBackgroundResource(R.color.lightDark);
//                holder.textViewScore.setText(tempStart.card);todo
                holder.textView2.setTextColor(view.getResources().getColor(R.color.lightGreen));

            }
        }



//        row.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myTagImage = (MyTagTwo) view.getTag(R.id.TAG_ONLINE);
//                Log.e("adapteralltag", String.valueOf(myTagImage));
//                EventBus.getDefault().post(new MessageEventIntInsertToKeypad(myTagImage.image));
//            }
//        });




//        if (!canContinue) {
//            Cards_item temp = list.get(i);
//            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),temp.card);
//            Bitmap btm = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight()/5);
//            holder.imageView.setImageBitmap(btm);
////            holder.textViewScore.setText(String.valueOf(temp.id));todo
//        } else {
//            Cards_item temp = list.get(i);
//            Cards_item tempStart = listCopiaStart.get(i);
//            if (temp.card==tempStart.card) {
//                view.setBackgroundResource(R.color.lightGreen);
//
//            } else {
//                view.setBackgroundResource(R.color.lightDark);
////                holder.textViewScore.setText(tempStart.card);todo
//                holder.textViewScore.setTextColor(view.getResources().getColor(R.color.lightGreen));
//
//            }
//        }

        return row;
    }

    public void setToNone() {
        for (int i = 0; i < ile; i++) {
            Cards_item tempNumber = new Cards_item(i + 1, 0);
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
        Cards_item tempNumber = new Cards_item(wchichOneId + 1, 0);
        list.set(wchichOneId, tempNumber);

        ///jesli po kliknięciu endInput nie odswiezaj
        if (!canContinue) {
            notifyDataSetChanged();
        }
    }
}

