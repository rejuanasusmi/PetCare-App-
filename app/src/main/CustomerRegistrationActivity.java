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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;


public class CustomerRegistrationActivity extends AppCompatActivity {

    Button regisBtn;
    EditText emaili,pass,conpass,fullname,mobile;
    TextView signBtn;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
   // DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://trysomething-adfb0-default-rtdb.firebaseio.com");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        regisBtn= findViewById(R. id.signUpBtn);
        signBtn = findViewById(R.id.signInBtn);
        emaili = findViewById(R.id.emailET);
        pass = findViewById(R.id.passwordET);
        conpass = findViewById(R.id.conpasswordET);
        fullname = findViewById(R.id.fullNameET);
        mobile = findViewById(R.id.mobileET);




        String text = "SignIn";
        SpannableString ss = new SpannableString(text);



        regisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checking();
            }
        });

        ClickableSpan clickspan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent i = new Intent(getApplicationContext(),CustomerLogInActivity.class);
                startActivity(i);
            }
        };

        ss.setSpan(clickspan,0,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signBtn.setText(ss);
        signBtn.setMovementMethod(LinkMovementMethod.getInstance());
    }
    private void checking() {
        String emailtxt = String.valueOf(emaili.getText());
        String passtxt = String.valueOf(pass.getText());
        String conpasstxt = String.valueOf(conpass.getText());
        String mobiletxt = String.valueOf(mobile.getText());
        String fullnametxt = String.valueOf(fullname.getText());

        if(!emailtxt.matches(emailPattern)){
            emaili.setError("Enter proper Email");
        }
        else if(passtxt.isEmpty() || passtxt.length()<6){
            pass.setError("Enter proper password");
        }
        else if(emailtxt.isEmpty() || fullnametxt.isEmpty() || mobiletxt.isEmpty() || passtxt.isEmpty()){
            Toast.makeText(CustomerRegistrationActivity.this, "Plaese fill all the blanks", Toast.LENGTH_SHORT).show();
        }
        else if(!passtxt.equals(conpasstxt)){
           conpass.setError("Password doesn't match");
        }
        else{

            mAuth.createUserWithEmailAndPassword(emailtxt,passtxt).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    Toast.makeText(CustomerRegistrationActivity.this, "Account Created",Toast.LENGTH_SHORT).show();
                    DocumentReference df = fstore.collection("Users").document(firebaseUser.getUid());
                    Map<String,Object> userinfo = new HashMap<>();
                    userinfo.put("Fullname",fullnametxt);
                    userinfo.put("Phone",mobiletxt);
                    userinfo.put("Email",emailtxt);


                    df.set(userinfo);
                    Intent intent = new Intent(getApplicationContext(),CustomerLogInActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CustomerRegistrationActivity.this, "Account Create Failed",Toast.LENGTH_SHORT).show();
                }
            });



           /* mAuth.createUserWithEmailAndPassword(emailtxt, passtxt)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                Toast.makeText(CustomerRegistrationActivity.this, "Account Created",
                                        Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(),CustomerLogInActivity.class);
                                startActivity(intent);
                                finish();


                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(CustomerRegistrationActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });*/



           /* databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(mobiletxt)){
                        Toast.makeText(CustomerRegistrationActivity.this, "Already registered phone number", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        databaseReference.child("users").child(mobiletxt).child("fullname").setValue(fullnametxt);
                        databaseReference.child("users").child(mobiletxt).child("emaili").setValue(emailtxt);
                        databaseReference.child("users").child(mobiletxt).child("pass").setValue(passtxt);
                        Toast.makeText(CustomerRegistrationActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(),CustomerLogInActivity.class);
                        startActivity(i);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });*/
        }
    }
}