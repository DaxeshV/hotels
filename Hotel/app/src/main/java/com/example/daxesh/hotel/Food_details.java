package com.example.daxesh.hotel;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.daxesh.hotel.Model.Food;
import com.example.daxesh.hotel.Model.Order;
import com.example.daxesh.hotel.database.Database;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Food_details extends AppCompatActivity {


    TextView food_name, food_price, food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btncart;
    ElegantNumberButton numberButton;

    String foodId = "";

    FirebaseDatabase database;
    DatabaseReference foods;

    Food currentfood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);


        //firebase

        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Foods");
        numberButton = (ElegantNumberButton) findViewById(R.id.number_butoon);

        btncart = (FloatingActionButton) findViewById(R.id.btncart);

        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  new Database(getBaseContext()).addtocart(new Order(
                        foodId,
                        currentfood.getName(),
                        numberButton.getNumber(),
                        currentfood.getPrice(),
                        currentfood.getDiscount()



                ));

                Toast.makeText(Food_details.this, "Added to cart ", Toast.LENGTH_SHORT).show();

            }
        });


        food_description = (TextView) findViewById(R.id.food_description);
        food_price = (TextView) findViewById(R.id.food_price);
        food_name = (TextView) findViewById(R.id.food_name);
        food_image = (ImageView) findViewById(R.id.img_food);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collepsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.Expandappbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.Collapsdappbar);


        //get food id from intent

        if (getIntent() != null)
            foodId = getIntent().getStringExtra("FoodId");
        if (!foodId.isEmpty()) {
            getDetailfood(foodId);
        }
    }

    private void getDetailfood(final String foodId) {


        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentfood = dataSnapshot.getValue(Food.class);
                Picasso.with(getBaseContext()).load(currentfood.getImage())
                        .into(food_image);
                collapsingToolbarLayout.setTitle(currentfood.getName());
                food_price.setText(currentfood.getPrice());
                food_name.setText(currentfood.getName());
                food_description.setText(currentfood.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
