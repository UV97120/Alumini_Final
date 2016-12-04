package vishal.alumini_final.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import vishal.alumini_final.Guest;
import vishal.alumini_final.R;
import vishal.alumini_final.model.DataModel;

public class Card_custom_adapter extends RecyclerView.Adapter<Card_custom_adapter.MyViewHolder>{
    private ArrayList<DataModel> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewVersion;
        CardView cardView;
        //ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            //this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
            this.cardView = (CardView)itemView.findViewById(R.id.card_view);
        }
    }

    public Card_custom_adapter(ArrayList<DataModel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        //ImageView imageView = holder.imageViewIcon;

        textViewName.setText(dataSet.get(listPosition).getName());
        textViewVersion.setText(dataSet.get(listPosition).getVersion());
       // imageView.setImageResource(dataSet.get(listPosition).getImage());
        holder.cardView.setOnClickListener(Guest.myOnClickListener);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}