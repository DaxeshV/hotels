package com.example.daxesh.hotel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.daxesh.hotel.Common.Common;
import com.example.daxesh.hotel.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class  Signin extends AppCompatActivity {

    MaterialEditText edtphone, edtpassword;
    Button signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);



        edtphone = (MaterialEditText) findViewById(R.id.editphon);
        edtpassword = (MaterialEditText) findViewById(R.id.editpassword);
        signin = (Button) findViewById(R.id.btnsignin);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference tbl_user = database.getReference("User");


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mdilog = new ProgressDialog(Signin.this);
                mdilog.setMessage("Please Wait");
                mdilog.show();

                    tbl_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            if (dataSnapshot.child(edtphone.getText().toString()).exists()) {
                                mdilog.dismiss();
                                User user = dataSnapshot.child(edtphone.getText().toString()).getValue(User.class);
                                user.setPhone(edtphone.getText().toString());//set phone
                                if (user.getPassword().equals(edtpassword.getText().toString())) {



                                    Intent i = new Intent(Signin.this,Home.class);
                                    Common.currentuser = user;
                                    startActivity(i);
                                    finish();


                                } else {

                                    Toast.makeText(Signin.this, "Wrong password!!!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                mdilog.dismiss();
                                Toast.makeText(Signin.this, "User Not Exist in database ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

            }
        });

    }
}
