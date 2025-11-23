package com.example.trysomething;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity implements SelectListner  {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private FirebaseFirestore db;

    private ArrayList<PostDetails> dataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);



            recyclerView = findViewById(R.id.homerecycle);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            db = FirebaseFirestore.getInstance();

       /* dataList1 = new ArrayList<>();
        adapter = new MyAdapter(dataList1,this);*/

           dataList = new ArrayList<>();

            CollectionReference publicJobPostCollection = db.collection("PublicADPostings");

            Query query = publicJobPostCollection.orderBy("petType", Query.Direction.DESCENDING);

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        PostDetails data = document.toObject(PostDetails.class);
                        dataList.add(data);
                    }
                    adapter = new MyAdapter(dataList,this);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.e("allAD", "Error getting documents: " + task.getException());
                }
            });
        }

        @Override
        protected void onStart() {
            super.onStart();

            // Check if a new job was posted from postJob activity
            if (getIntent().hasExtra("postedJob")) {
                PostDetails postedJob = getIntent().getParcelableExtra("postedJob");
                if (postedJob != null) {
                    // Add the posted job to the list and update the adapter
                    adapter.addData(postedJob);
                }
            }
        }

    @Override
    public void onItemClicked(int position) {

        Intent intent = new Intent(getApplicationContext(),BuyDataShow.class);

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



