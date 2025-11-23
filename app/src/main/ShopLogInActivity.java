package com.example.trysomething;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShopLogInActivity extends AppCompatActivity {
    Button logbtn;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;

   // DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://trysomething-7a998-default-rtdb.firebaseio.com");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_log_in);
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        logbtn = findViewById(R.id.logBtnShop);
        TextView mobile =(TextView) findViewById(R.id.mobileShop);
        TextView password =(TextView) findViewById(R.id.passwordShop);
        TextView forgot = (TextView) findViewById(R.id.forgotShop);
        TextView signBtnShop = findViewById(R.id.loginAccBtnShop);

        String text = "SignUp";
        String text2 = "Forgot Password ?";

        SpannableString ss = new SpannableString(text);
        SpannableString ss2 = new SpannableString(text2);


        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailtxt = String.valueOf(mobile.getText());
                String passtxt = String.valueOf(password.getText());
                Log.d("TAG","onClick"+emailtxt);

                if (emailtxt.isEmpty()) {
                    mobile.setError("Enter proper Number");
                } else if (passtxt.isEmpty() || passtxt.length() < 6) {
                    password.setError("Enter proper password");
                }
                else{


                    mAuth.signInWithEmailAndPassword(emailtxt,passtxt).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(ShopLogInActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                            checkInformation1(authResult.getUser().getUid()) ;

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });



                  /*databaseReference.child("ShopOwner").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(mobiletxt)){
                                String getPassword = snapshot.child(mobiletxt).child("pass").getValue(String.class);

                                if(getPassword.equals(passtxt)){
                                    Toast.makeText(ShopLogInActivity.this, "Successfully logged In", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(),HomePage.class);
                                    startActivity(i);
                                }
                                else{
                                    Toast.makeText(ShopLogInActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(ShopLogInActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });*/
                }
            }
        });

        ClickableSpan clickspan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent i = new Intent(getApplicationContext(),ShopOwnerRegistration.class);
                startActivity(i);
            }
        };

        ss.setSpan(clickspan,0,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signBtnShop.setText(ss);
        signBtnShop.setMovementMethod(LinkMovementMethod.getInstance());

        ClickableSpan clickspan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent i = new Intent(getApplicationContext(),ShopForget.class);
                startActivity(i);
            }
        };

        ss2.setSpan(clickspan2,0,16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        forgot.setText(ss2);
        forgot.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void checkInformation1(String uid) {
        DocumentReference df = fstore.collection("ShopOwner").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess" + documentSnapshot.getData());

                Intent i = new Intent(getApplicationContext(),ShopFirstPage.class);
                startActivity(i);
            }
        });
    }
}