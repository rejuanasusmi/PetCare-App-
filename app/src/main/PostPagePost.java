package com.example.trysomething;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PostPagePost extends AppCompatActivity {

    Button selectpic,postbtn;
    private Uri imageUri;
    private Bitmap bitmap;
    private ImageView putimage;
    FirebaseAuth mAuth;
    FirebaseStorage firebaseStorage;
    FirebaseFirestore fstore;

    StorageReference storageReference;

    CollectionReference mPostAdCollection;
    private String photourl;
    EditText petType,Petage,price,contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_page_post);

        firebaseStorage = FirebaseStorage.getInstance();
        fstore= FirebaseFirestore.getInstance();
        storageReference = firebaseStorage.getReference();


        mAuth = FirebaseAuth.getInstance();
        // currentUserId = mAuth.getCurrentUser().getUid();

        mPostAdCollection = fstore.collection("ADPost");

        selectpic =findViewById(R.id.selectbutton);
        putimage = findViewById(R.id.imageView_profile_dp);


        petType = findViewById(R.id.enterpet);
        Petage = findViewById(R.id.petage);
        price = findViewById(R.id.enterpetprice);
        contact = findViewById(R.id.enternumber);
        postbtn = findViewById(R.id.postbuttonpost);

        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();
               // petInformation();
            }
        });

        selectpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery();
            }
        });
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(intent);

    }
    ActivityResultLauncher<Intent> launcher
            =registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode()== Activity.RESULT_OK){
                    Intent data = result.getData();
                    if(data!= null && data.getData()!=null){
                        imageUri=data.getData();
                        try {
                            bitmap= MediaStore.Images.Media.getBitmap(
                                    getApplication().getContentResolver(),
                                    imageUri
                            );
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if(imageUri!=null){
                        putimage.setImageBitmap(bitmap);
                    }
                }
            }

    );

    public void UploadImage(){

        final StorageReference myref = storageReference.child("photo/"+ UUID.randomUUID().toString());
        myref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                myref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String susmi = uri.toString();
                        // Toast.makeText(getApplicationContext(), "Download Successfully", Toast.LENGTH_SHORT).show();
                        petInformation(susmi);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Download failed", Toast.LENGTH_SHORT).show();
                    }
                });
                // Toast.makeText(getApplicationContext(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                //photourl = image.toString();
            }
        });
    }

    public void petInformation(String susmi){
        String petCategory = petType.getText().toString().trim();
        String petboyosh = Petage.getText().toString().trim();
        String petprice = price.getText().toString().trim();
        String petnumber = contact.getText().toString().trim();
        //photourl = imageUri.toString();

        if(TextUtils.isEmpty(petCategory) && TextUtils.isEmpty(petboyosh)  &&  TextUtils.isEmpty(petprice)  &&  TextUtils.isEmpty(petnumber)){
            Toast.makeText(getApplicationContext(), "Please fill all the blanks", Toast.LENGTH_SHORT).show();
        }else {
            FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
            String emailtxt = currentUser.getEmail();
            if (currentUser == null) {
                Intent intent = new Intent(getApplicationContext(), AboutPage.class);
                startActivity(intent);
            }
            String userId = currentUser.getUid();
            String id = mPostAdCollection.document().getId();
            PostDetails postDetails = new PostDetails(petCategory, petboyosh, susmi, petprice, petnumber,emailtxt);
            mPostAdCollection.document(userId).collection("UserPosts").document(id).set(postDetails)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Successfully posted the AD", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ShopPostAd.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed to post the job", Toast.LENGTH_SHORT).show();
                        }
                    });



        }
    }
}