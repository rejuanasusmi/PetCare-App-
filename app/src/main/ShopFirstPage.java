package com.example.trysomething;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class ShopFirstPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    CardView homecard,profilecard,postcard;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_first_page);

        homecard = findViewById(R.id.shophome);
        profilecard = findViewById(R.id.shopprofile);
        postcard = findViewById(R.id.postadshop);

        drawerLayout = findViewById(R.id.drawer_layout1);
        navigationView=findViewById(R.id.nev_view);
        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        homecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),HomePage.class);
                startActivity(i);
            }
        });

        profilecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ShopProfile.class);
                startActivity(i);
            }
        });

        postcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ShopPostAd.class);
                startActivity(i);
            }
        });

    }

    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        if(itemId == R.id.home){
            Intent intent = new Intent(ShopFirstPage.this,HomePage.class);
            startActivity(intent);


        }
        if (itemId == R.id.post){
            Intent intent = new Intent(ShopFirstPage.this,ShopProfile.class);
            startActivity(intent);

        }

        if(itemId == R.id.logout){
            Intent intent = new Intent(ShopFirstPage.this,CustomerLogInActivity.class);
            startActivity(intent);
            finish();
        }
        return false;
    }
}