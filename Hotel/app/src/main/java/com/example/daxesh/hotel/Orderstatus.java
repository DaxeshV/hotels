package com.example.daxesh.hotel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.daxesh.hotel.Common.Common;
import com.example.daxesh.hotel.Model.Request;
import com.example.daxesh.hotel.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Orderstatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference requeste;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderstatus);

        database = FirebaseDatabase.getInstance();
        requeste = database.getReference("Requests");

        recyclerView = (RecyclerView) findViewById(R.id.listorders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadorder(Common.currentuser.getPhone());


    }

    private void loadorder(String phone) {


        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requeste.orderByChild("phone")
                        .equalTo(phone)) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
                viewHolder.txtorderid.setText(adapter.getRef(position).getKey());
                viewHolder.txtorderstatus.setText(convertCodeToStatus(model.getStatus()));
                viewHolder.txtorderaddress.setText(model.getAddress());
                viewHolder.txtorderphone.setText(model.getPhone());
            }
        };

        recyclerView.setAdapter(adapter);
    }

    private String convertCodeToStatus(String status) {
        if (status.equals("0"))
            return "placed";
        else if (status.equals("1"))
            return "on the way";
        else
            return "shipped";

    }
}
