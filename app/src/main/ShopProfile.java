package com.example.trysomething;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.protobuf.Value;

public class ShopProfile extends AppCompatActivity {

    TextView textViewWelcome, textViewFullname,textViewemail,textViewMobile,textViewShopname;
    ProgressBar progressBar;
    private String fullname, email, mobile;
    ImageView imageView;
    FirebaseAuth authProfile;
    ImageView edit;
    FirebaseFirestore fstore;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_profile);

        textViewWelcome = findViewById(R.id.name);
        textViewFullname = findViewById(R.id.username);
        textViewemail = findViewById(R.id.emailshoppp);
        textViewShopname = findViewById(R.id.shopnamepp);
        textViewMobile = findViewById(R.id.phonenumber);
        edit = findViewById(R.id.editpro);

        authProfile = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ShopEditProfile.class);
                startActivity(i);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        String userId = authProfile.getCurrentUser().getUid();
        DocumentReference databaseProfile = fstore.collection("Users").document(userId);
        databaseProfile.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                textViewWelcome.setText("WELCOME, "+value.getString("Fullname")+"!");
                textViewFullname.setText(value.getString("Fullname"));
                textViewemail.setText(value.getString("Email"));
                textViewMobile.setText(value.getString("Phone"));
                textViewShopname.setText(value.getString("Shop Name"));
            }
        });
    }
}