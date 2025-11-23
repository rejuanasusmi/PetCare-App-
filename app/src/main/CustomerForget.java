package com.example.trysomething;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;



public class CustomerForget extends AppCompatActivity {

    Button changepass;
    TextView signInForgot;
    EditText emailpass;
    FirebaseAuth mAuth;
    //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://trysomething-adfb0-default-rtdb.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_forget);

        signInForgot = findViewById(R.id.signInBtnForgot);
        emailpass = findViewById(R.id.emailpassShop);
        changepass = findViewById(R.id.changepassforgot);
        mAuth = FirebaseAuth.getInstance();

        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });


        String text = "Sign In";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickspan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent i = new Intent(getApplicationContext(),CustomerLogInActivity.class);
                startActivity(i);
            }
        };

        ss.setSpan(clickspan,0,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signInForgot.setText(ss);
        signInForgot.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void validate() {
        String emailR = String.valueOf(emailpass.getText());
        if(emailR.isEmpty()){
            emailpass.setError("Fill Email");
        }else{
            mAuth.sendPasswordResetEmail(emailR).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(CustomerForget.this, "Check Your Email", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(),CustomerLogInActivity.class);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(CustomerForget.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}