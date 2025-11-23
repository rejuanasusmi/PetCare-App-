package com.example.trysomething;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ShopEditProfile extends AppCompatActivity {


    Button regisBtn;
    EditText shop,fullname,mobile;

    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_edit_profile);

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        regisBtn= findViewById(R. id.signUpBtnp);
        fullname = findViewById(R.id.fullNameETp);
        mobile = findViewById(R.id.mobileETp);
        shop = findViewById(R.id.shoppETp);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail();
        } else {
            // No user is signed in
        }

        regisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobiletxt = String.valueOf(mobile.getText());
                String fullnametxt = String.valueOf(fullname.getText());
                String shopname = String.valueOf(shop.getText());

                FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();
                String emailtxt = firebaseUser.getEmail();
                DocumentReference df = fstore.collection("Users").document(firebaseUser.getUid());
                Map<String,Object> userinfo = new HashMap<>();
                userinfo.put("Fullname",fullnametxt);
                userinfo.put("Phone",mobiletxt);
                userinfo.put("Shop Name",shopname);
                userinfo.put("Email",emailtxt);
                userinfo.put("Admid","1");

                df.set(userinfo);
                Toast.makeText(ShopEditProfile.this, "Profile Edited Successfully",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),ShopProfile.class);
                startActivity(i);
                finish();
            }
        });
    }
}