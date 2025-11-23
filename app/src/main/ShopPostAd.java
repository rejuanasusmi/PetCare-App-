package com.example.trysomething;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ShopPostAd extends AppCompatActivity implements SelectListner{

    ImageView post;

    ImageView push;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private CollectionReference PostCollection;
    private CollectionReference publicPostCollection;

    private ArrayList<PostDetails> dataList;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_post_ad);

        post = findViewById(R.id.postadplus);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        String uId = mUser.getUid();

        db = FirebaseFirestore.getInstance();
        PostCollection = db.collection("ADPost").document(uId).collection("UserPosts");
        publicPostCollection = db.collection("PublicADPostings");

        dataList = new ArrayList<>();
        adapter = new MyAdapter(dataList,this);

        recyclerView = findViewById(R.id.recycleshop);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),PostPagePost.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        PostCollection
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    dataList.clear();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        PostDetails data = documentSnapshot.toObject(PostDetails.class);
                        dataList.add(data);
                        saveToPublicCollection(data);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to fetch job data.", Toast.LENGTH_SHORT).show();
                });
    }
    private void saveToPublicCollection(PostDetails Data) {
        publicPostCollection.whereEqualTo("contact", Data.getContact())
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (querySnapshot.isEmpty()) {
                        publicPostCollection.add(Data);
                    }
                })
                .addOnFailureListener(e -> {

                });
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(getApplicationContext(),ShopShowDetails.class);

        intent.putExtra("PetType", dataList.get(position).getPetType());
        intent.putExtra("PetImage",dataList.get(position).getPetImage());
        intent.putExtra("PetAge", dataList.get(position).getPetAge());
        intent.putExtra("PetPrice", dataList.get(position).getPrice());
        intent.putExtra("Contact", dataList.get(position).getContact());
        intent.putExtra("Email", dataList.get(position).getEmail());
        startActivity(intent);
    }

    @Override
    public void onItemClicked1(int pos) {

    }
}