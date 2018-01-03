package rj.pl.memorypower;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by PC on 30.12.2017.
 */

class CardAdapterMain extends RecyclerView.Adapter<CardAdapterMain.ViewHolder> {

    private  String[] captions;
    private int[] imageIds;

    public CardAdapterMain(String[] captions, int[] imageIds) {
        this.captions = captions;
        this.imageIds = imageIds;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_menu, parent,false );

        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.info_image);
        Drawable drawable = cardView.getResources().getDrawable(imageIds[position]);//TODO DEPRECIATION
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(captions[position]);
        TextView textView = cardView.findViewById(R.id.info_text);
        textView.setText(captions[position]);



        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                if(position==0){
                    intent = new Intent(view.getContext(),NumbersIntro.class);
                }
                if(position==1){
                    intent = new Intent(view.getContext(),WordsIntro.class);
                }
                if(position==2){
//                    intent = new Intent(view.getContext(),NumbersIntro.class);
                }
                if(position==3){
                    intent = new Intent(view.getContext(),NumbersIntro.class);
                }
                if(position==4){
//                    intent = new Intent(view.getContext(),NumbersIntro.class);
                }
                if(position==5){
//                    intent = new Intent(view.getContext(),NumbersIntro.class);
                }
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return captions.length;
    }


}
