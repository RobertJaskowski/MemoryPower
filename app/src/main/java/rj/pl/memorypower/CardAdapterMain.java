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
import android.widget.Toast;



class CardAdapterMain extends RecyclerView.Adapter<CardAdapterMain.ViewHolder> {

    private String[] captions;
    private Drawable[] imageIds;

    CardAdapterMain(String[] captions, Drawable[] imageIds) {
        this.captions = captions;
        this.imageIds = imageIds;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_menu, parent, false);

        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.info_image);
        imageView.setImageDrawable(imageIds[position]);
        imageView.setContentDescription(captions[position]);
        TextView textView = cardView.findViewById(R.id.info_text);
        textView.setText(captions[position]);


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                switch (holder.getAdapterPosition()) {
                    case 0:
                        intent = new Intent(view.getContext(), NumbersIntro.class);
                        break;
                    case 1:
                        intent = new Intent(view.getContext(), WordsIntro.class);
                        break;
                    case 2:
                        intent = new Intent(view.getContext(), CardsIntro.class);
                        break;
                    case 3:
                        intent = new Intent(view.getContext(), NamesIntro.class);
                        break;
                    case 4:
                        intent = new Intent(view.getContext(), NumbersIntro.class);
                        break;
                    case 5:
                        intent = new Intent(view.getContext(), NumbersIntro.class);
                        break;

                }

                if (intent!=null){
                    view.getContext().startActivity(intent);
                }else{
                    Toast.makeText(view.getContext().getApplicationContext(),"Error choose again",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return captions.length;
    }


}
