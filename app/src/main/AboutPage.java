package com.example.trysomething;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class AboutPage extends AppCompatActivity {

    Button checking;

    FirebaseAuth authProfile;

    FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);

        checking = findViewById(R.id.checkingButton);

        authProfile = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();


        String userId = authProfile.getCurrentUser().getUid();
        DocumentReference df = fstore.collection("Users").document(userId);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess" + documentSnapshot.getData());
                checking.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(documentSnapshot.getString("Admid")!=null){
                            Intent i = new Intent(getApplicationContext(),ShopOwnerRegistration.class);
                            startActivity(i);
                        }else{
                            Intent i = new Intent(getApplicationContext(),CustomerRegistrationActivity.class);
                            startActivity(i);
                        }
                    }
                });
            }
        });


    }
}