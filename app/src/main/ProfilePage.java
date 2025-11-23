package com.example.trysomething;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfilePage extends AppCompatActivity {

    TextView textViewWelcome, textViewFullname,textViewemail,textViewMobile;
    ProgressBar progressBar;
    private String fullname, email, mobile;
    ImageView imageView;
    FirebaseAuth authProfile;

    FirebaseFirestore fstore;
    ImageView edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        textViewWelcome = findViewById(R.id.textVie_show_welcome);
        textViewFullname = findViewById(R.id.show_fullname);
        textViewemail = findViewById(R.id.show_email);
        textViewMobile = findViewById(R.id.show_mobile);

        edit = findViewById(R.id.editpro);

        authProfile = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),EditProfile.class);
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
            }
        });
    }

}


