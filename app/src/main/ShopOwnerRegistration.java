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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ShopOwnerRegistration extends AppCompatActivity {

    Button regisBtn;
    EditText emaili,pass,conpass,fullname,mobile,shopname;
    TextView signBtn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;

   // DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://trysomething-7a998-default-rtdb.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_owner_registration);

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        regisBtn= findViewById(R.id.signUpBtnShop);
        signBtn = findViewById(R.id.signInBtnShop);
        emaili = findViewById(R.id.emailShop);
        pass = findViewById(R.id.passwordShop);
        conpass = findViewById(R.id.conpasswordShop);
        fullname = findViewById(R.id.fullNameShop);
        mobile = findViewById(R.id.mobileShop);
        shopname = findViewById(R.id.shopnameShop);

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
        String shopnametxt = String.valueOf(shopname.getText());

        if(!emailtxt.matches(emailPattern)){
            emaili.setError("Enter proper Email");
        }
        else if(passtxt.isEmpty() || passtxt.length()<6){
            pass.setError("Enter proper password");
        }
        else if(!passtxt.equals(conpasstxt)){
            conpass.setError("Password doesn't match");
        }
        else if(emailtxt.isEmpty() || fullnametxt.isEmpty() || mobiletxt.isEmpty() || passtxt.isEmpty() || shopnametxt.isEmpty()){
            Toast.makeText(ShopOwnerRegistration.this, "Please fill all the blanks", Toast.LENGTH_SHORT).show();
        }
        else{


            mAuth.createUserWithEmailAndPassword(emailtxt,passtxt).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    Toast.makeText(ShopOwnerRegistration.this, "Account Created",Toast.LENGTH_SHORT).show();
                    DocumentReference df = fstore.collection("Users").document(firebaseUser.getUid());
                    Map<String,Object> userinfo = new HashMap<>();

                    userinfo.put("Fullname",fullnametxt);
                    userinfo.put("Phone",mobiletxt);
                    userinfo.put("Email",emailtxt);
                    userinfo.put("Shop Name",shopnametxt);
                    userinfo.put("Admid","1");

                    df.set(userinfo);
                    Intent intent = new Intent(getApplicationContext(),CustomerLogInActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ShopOwnerRegistration.this, "Account Create Failed (already have an account)",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}