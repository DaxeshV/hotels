package com.example.daxesh.hotel;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.daxesh.hotel.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class Signup extends AppCompatActivity {
    MaterialEditText editphone,editname,editpassword;
    Button btnsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editname=(MaterialEditText)findViewById(R.id.ename);

        editphone=(MaterialEditText)findViewById(R.id.editphon);
        editpassword=(MaterialEditText)findViewById(R.id.editpassword);

        btnsignup=(Button)findViewById(R.id.btnsignup);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tbl_user = database.getReference("User");

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mdilog  =  new ProgressDialog(Signup.this);
                mdilog.setMessage("Please Wait");
                mdilog.show();


                tbl_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(editphone.getText().toString()).exists()){

                            mdilog.dismiss();
                            Toast.makeText(Signup.this, "Phone no aluready ragister", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            mdilog.dismiss();
                            User user =  new User(editname.getText().toString(),editpassword.getText().toString());
                            tbl_user.child(editphone.getText().toString()).setValue(user);
                            Toast.makeText(Signup.this, "signup sucess", Toast.LENGTH_SHORT).show();
                            finish();
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
