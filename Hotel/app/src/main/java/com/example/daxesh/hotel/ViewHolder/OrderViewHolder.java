package com.example.daxesh.hotel.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.daxesh.hotel.Interface.ItemClickListner;
import com.example.daxesh.hotel.R;

/**
 * Created by Daxesh on 11/13/2017.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtorderid,txtorderstatus,txtorderphone,txtorderaddress;
    private ItemClickListner itemClickListner;
    public OrderViewHolder(View itemView) {
        super(itemView);

        txtorderaddress=(TextView)itemView.findViewById(R.id.order_address);
        txtorderid=(TextView)itemView.findViewById(R.id.order_id);
        txtorderstatus=(TextView)itemView.findViewById(R.id.order_status);
        txtorderphone=(TextView)itemView.findViewById(R.id.order_phone);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    @Override
    public void onClick(View v) {
        itemClickListner.onclick(v,getAdapterPosition(),false);


    }
}
