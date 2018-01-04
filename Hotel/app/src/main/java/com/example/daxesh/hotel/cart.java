package com.example.daxesh.hotel;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daxesh.hotel.Common.Common;
import com.example.daxesh.hotel.Model.Order;
import com.example.daxesh.hotel.Model.Request;
import com.example.daxesh.hotel.ViewHolder.CartAdapter;
import com.example.daxesh.hotel.database.Database;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;


    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txttotalprice;
    FButton btnPlace;




    List<Order>carts = new ArrayList<>();
    CartAdapter adapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        database = FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");

        recyclerView =(RecyclerView)findViewById(R.id.listcart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txttotalprice =  (TextView)findViewById(R.id.total);
        btnPlace =(FButton)findViewById(R.id.btnplaceorder);


        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create new request

                showAlertDialog();

            }
        });

        loadListfood();



    }

    private void showAlertDialog() {
        AlertDialog.Builder alertdialog =  new AlertDialog.Builder(cart.this);
        alertdialog.setTitle("One more step!");
        alertdialog.setMessage("Enter your Address : ");

        final EditText editaddress=  new EditText(cart.this);
        LinearLayout.LayoutParams lp  =  new LinearLayout.LayoutParams(

                LinearLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        editaddress.setLayoutParams(lp);
        alertdialog.setView(editaddress); //add edit text to  alert dialog
        alertdialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);


        alertdialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Request request  =  new Request(
                        Common.currentuser.getPhone(),
                        Common.currentuser.getName(),
                        editaddress.getText().toString(),
                        txttotalprice.getText().toString(),
                        carts
                );
//submit to firebase
                //we will use  System.cureent.mill
requests.child(String.valueOf(System.currentTimeMillis()))
        .setValue(request);
                //delete cart

                new  Database(getBaseContext()).cleanCart();

                Toast.makeText(cart.this, "Thank You , Order  Place ", Toast.LENGTH_SHORT).show();
                finish();


            }
        });
        alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertdialog.show();
    }

    private void loadListfood() {



carts = new Database(this).getCarts();
        adapter =  new CartAdapter(carts,this);
        recyclerView.setAdapter(adapter);


        int total = 0;
        for(Order order:carts)
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));


        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txttotalprice.setText(fmt.format(total));



    }
}
