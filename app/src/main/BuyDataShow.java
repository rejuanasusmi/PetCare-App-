package com.example.trysomething;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class BuyDataShow extends AppCompatActivity {

    TextView pettype,petage,petprice,contactnum,typremail;

    private ArrayList<PostDetails> dataList;
    private MyAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_data_show);

        String type = getIntent().getStringExtra("PetType");
        String typeAge = getIntent().getStringExtra("PetAge");
        String image = getIntent().getStringExtra("PetImage");
        String typeprice = getIntent().getStringExtra("PetPrice");
        String typecontact = getIntent().getStringExtra("Contact");
        String typeemail = getIntent().getStringExtra("Email");


        pettype = findViewById(R.id.pettypecard);
        petage = findViewById(R.id.petagecard);
        petprice = findViewById(R.id.petpricecard);
        contactnum = findViewById(R.id.contactcard);
        typremail = findViewById(R.id.emailcard);

        pettype.setText(type);
        petage.setText(typeAge);


        ImageView imageView = findViewById(R.id.imagecard); // Make sure you use the correct ID

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.petimagepost1) // Placeholder image
                .error(R.drawable.facebook); // Error image if loading fails

        Glide.with(this)
                .load(image)
                .apply(requestOptions)
                .into(imageView);

        petprice.setText(typeprice);
        contactnum.setText(typecontact);
        typremail.setText(typeemail);
    }
}