package com.saurav.wishshop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.saurav.wishshop.Detail2Activity;
import com.saurav.wishshop.Main2Modal;
import com.saurav.wishshop.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends  RecyclerView.Adapter<AdapterViewHolder>  {

    private Context mContext;
    private List<Main2Modal> main2modaldata;
    private int lastPosition=-1;

    public MyAdapter(Context mContext, List<Main2Modal> main2modaldata) {
        this.mContext = mContext;
        this.main2modaldata = main2modaldata;

    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_item,parent,false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        Glide.with(mContext)
                .load(main2modaldata.get(position).getItemImage())
                .into(holder.imageView);
        // holder.imageView.setImageResource(main2modaldata.get(position).getItemImage());
        holder.mTitle.setText(main2modaldata.get(position).getItemName());
        holder.mDescription.setText(main2modaldata.get(position).getItemDescription());
        holder.mPrice.setText(main2modaldata.get(position).getItemPrice());

        holder.mcardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Detail2Activity.class);
                intent.putExtra("Image",main2modaldata.get(holder.getAdapterPosition()).getItemImage());
                intent.putExtra("Description",main2modaldata.get(holder.getAdapterPosition()).getItemDescription());
                mContext.startActivity(intent);
            }
        });
        setAnimation(holder.itemView,position);

    }
    public void setAnimation(View viewToAnimate,int position){
        if (position> lastPosition){
            ScaleAnimation animation= new ScaleAnimation(0.0f,1.0f,0.0f,1.0f,
                    Animation.RELATIVE_TO_SELF,0.5f,
                    Animation.RELATIVE_TO_SELF,0.05f);
            animation.setDuration(1500);
            viewToAnimate.startAnimation(animation);
            lastPosition=position;
        }


    }

    @Override
    public int getItemCount() {
        return main2modaldata.size();
    }


    public void filteredList(ArrayList<Main2Modal> filterList) {
        main2modaldata=filterList;
        notifyDataSetChanged();

    }
}
class AdapterViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView mTitle,mDescription,mPrice;
    CardView mcardView;
    public AdapterViewHolder(View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.ivImage);
        mTitle=itemView.findViewById(R.id.tvTitle);
        mDescription=itemView.findViewById(R.id.tvDescription);
        mPrice=itemView.findViewById(R.id.tvPrice);

        mcardView = itemView.findViewById(R.id.myCardView);
    }
}
