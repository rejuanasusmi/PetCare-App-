package com.example.trysomething;

//import static com.example.trysomething.R.id.backButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Timer;
import java.util.TimerTask;

public class CustomerLogInActivity extends AppCompatActivity {

   // Button BackButton = findViewById(R.id.backButton);

    //TextView signUpBtn;
    FirebaseAuth mAuth;
    ProgressBar pb;
    FirebaseFirestore fstore;
    int cnt = 0;

    boolean visibity;



    boolean passwordVisible;
  // DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://trysomething-adfb0-default-rtdb.firebaseio.com");
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_log_in);

        pb= (ProgressBar) findViewById(R.id.pb);
        pb.onVisibilityAggregated(false);

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        TextView username =(TextView) findViewById(R.id.username);
        EditText password =(EditText) findViewById(R.id.password);
        TextView signBtn = (TextView) findViewById(R.id.signUpAccBtn);
        TextView forgot = (TextView) findViewById(R.id.forgotBtn);

        String text = "SignUp";
        String text2 = "Forgot Password ?";

        SpannableString ss = new SpannableString(text);
        SpannableString ss2 = new SpannableString(text2);


        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right=2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=password.getRight()-password.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=password.getSelectionEnd();

                        if(passwordVisible){
                            //set drawable img here
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_info_24,0);
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_off_24,0);
                            //for hide pass
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible=false;


                        }
                        else{
                            //set drawable img here
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_24,0);
                            //for show pass
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });


        MaterialButton loginbtn=(MaterialButton) findViewById(R. id.loginbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailtxt = String.valueOf(username.getText());
                String passtxt = String.valueOf(password.getText());
                Log.d("TAG","onClick"+emailtxt);

                if (emailtxt.isEmpty()) {
                    username.setError("Enter proper Email");
                } else if (passtxt.isEmpty() || passtxt.length() < 6) {
                    password.setError("Enter proper password");
                }
                else{

                        prog();


                   mAuth.signInWithEmailAndPassword(emailtxt,passtxt).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                       @Override
                       public void onSuccess(AuthResult authResult) {
                           Toast.makeText(CustomerLogInActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                          checkInformation(authResult.getUser().getUid()) ;

                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {

                       }
                   });

                 /*   databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(mobiletxt)){
                                String getPassword = snapshot.child(mobiletxt).child("pass").getValue(String.class);

                                if(getPassword.equals(passtxt)){
                                    Toast.makeText(CustomerLogInActivity.this, "Successfully logged In", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(),HomePage.class);
                                    startActivity(i);
                                }
                                else{
                                    Toast.makeText(CustomerLogInActivity.this, "password doesn't match", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(CustomerLogInActivity.this, "password doesn't match", Toast.LENGTH_SHORT).show();
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
                Intent i = new Intent(getApplicationContext(),SplashActivity.class);
                startActivity(i);
            }
        };

        ss.setSpan(clickspan,0,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signBtn.setText(ss);
        signBtn.setMovementMethod(LinkMovementMethod.getInstance());


        ClickableSpan clickspan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent i = new Intent(getApplicationContext(),CustomerForget.class);
                startActivity(i);
            }
        };

        ss2.setSpan(clickspan2,0,16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        forgot.setText(ss2);
        forgot.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void checkInformation(String uid) {
        DocumentReference df = fstore.collection("Users").document(uid);

        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess" + documentSnapshot.getData());
                if(documentSnapshot.getString("Admid")!=null){
                    Intent i = new Intent(getApplicationContext(),ShopFirstPage.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Intent i = new Intent(getApplicationContext(),FirstPage.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    public void prog(){
        pb.onVisibilityAggregated(true);
        Timer t=new Timer();
        TimerTask tt= new TimerTask(){
            @Override
            public void run(){
                cnt++;
                pb.setProgress(cnt);
                if(cnt==100)
                    t.cancel();
            }

        };
        t.schedule(tt,0,100);

}


}