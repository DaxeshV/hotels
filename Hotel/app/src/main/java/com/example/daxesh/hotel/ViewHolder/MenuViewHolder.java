package com.example.daxesh.hotel.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daxesh.hotel.Interface.ItemClickListner;
import com.example.daxesh.hotel.R;

/**
 * Created by Daxesh on 10/28/2017.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView textView;
    public ImageView imgview;


    private  ItemClickListner itemclicklistner;


    public MenuViewHolder(View itemView) {


        super(itemView);


        textView =(TextView)itemView.findViewById(R.id.menu_name);
        imgview=(ImageView)itemView.findViewById(R.id.menu_image);
        itemView.setOnClickListener(this);


    }

    public ItemClickListner getItemclicklistner() {
        return itemclicklistner;
    }

    public void setItemclicklistner(ItemClickListner itemclicklistner) {
        this.itemclicklistner = itemclicklistner;
    }

    @Override
    public void onClick(View view) {
        itemclicklistner.onclick(view,getAdapterPosition(),false);



    }
}

