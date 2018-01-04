package com.example.daxesh.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.example.daxesh.hotel.Interface.ItemClickListner;
import com.example.daxesh.hotel.Model.Food;
import com.example.daxesh.hotel.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodlist;

    String categoryId = "";

    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    //search food

    FirebaseRecyclerAdapter<Food, FoodViewHolder> searchAdapter;
    List<String> suggestlist = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);


        database = FirebaseDatabase.getInstance();
        foodlist = database.getReference("Foods");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        //get intent here


        if (getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty() && categoryId != null) {
            loadListfood(categoryId);
        }
//search

        materialSearchBar = (MaterialSearchBar) findViewById(R.id.searchbar);
        materialSearchBar.setHint("Enter food name");
        loadsuggest();
        materialSearchBar.setLastSuggestions(suggestlist);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<String>();
                for (String search : suggestlist) {
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //when searchbar closed
                //restore orignal sugeestion

                if (!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

    }

    private void startSearch(CharSequence text) {

        searchAdapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodlist.orderByChild("Name").equalTo(text.toString())
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.foodname.setText(model.getName());
                Log.d("TAG", model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.foodimage);
                final Food local = model;
                viewHolder.setItemclicklistner(new ItemClickListner() {
                    @Override
                    public void onclick(View view, int position, boolean isLongclick) {
                        //Toast.makeText(FoodList.this, ""+local.getName(), Toast.LENGTH_SHORT).show();

                        Intent fooddetail = new Intent(FoodList.this, Food_details.class);
                        fooddetail.putExtra("FoodId", searchAdapter.getRef(position).getKey());
                        startActivity(fooddetail);
                    }
                });
            }
        };
        recyclerView.setAdapter(searchAdapter);

    }

    private void loadsuggest() {

        foodlist.orderByChild("MenuId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {
                            Food item = postsnapshot.getValue(Food.class);
                            suggestlist.add(item.getName());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void loadListfood(String categoryId) {


        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_item, FoodViewHolder.class, foodlist.orderByChild("MenuId").equalTo(categoryId))
                //Like select * from  foods where  MenuId=
        {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {

                viewHolder.foodname.setText(model.getName());
                Log.d("TAG", model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.foodimage);
                final Food local = model;
                viewHolder.setItemclicklistner(new ItemClickListner() {
                    @Override
                    public void onclick(View view, int position, boolean isLongclick) {
                        //Toast.makeText(FoodList.this, ""+local.getName(), Toast.LENGTH_SHORT).show();

                        Intent fooddetail = new Intent(FoodList.this, Food_details.class);
                        fooddetail.putExtra("FoodId", adapter.getRef(position).getKey());
                        startActivity(fooddetail);
                    }
                });

            }

        };
//set adapter
        recyclerView.setAdapter(adapter);


    }
}
