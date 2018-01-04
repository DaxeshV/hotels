package com.example.daxesh.hotel.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daxesh.hotel.Interface.ItemClickListner;
import com.example.daxesh.hotel.R;

/**
 * Created by Daxesh on 10/29/2017.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView foodname;
    public ImageView foodimage;


    private ItemClickListner itemclicklistner;

    public FoodViewHolder(View itemView) {
        super(itemView);

        foodname =(TextView)itemView.findViewById(R.id.food_name);
        foodimage=(ImageView)itemView.findViewById(R.id.food_image);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        itemclicklistner.onclick(view,getAdapterPosition(),false );

    }

    public void setItemclicklistner(ItemClickListner itemclicklistner) {
        this.itemclicklistner = itemclicklistner;
    }
}
