package com.example.trysomething;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class catInterface extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_interface);

        CardView food=findViewById(R.id.catFoodInfo);
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),foodPdfShow.class);
                intent.putExtra("pdf_url","https://www.franklinanimalclinic.vet/sites/site-2382/documents/Cat_Canned_Pouch_Foods1.pdf");
                startActivity(intent);
            }
        });
        CardView hospital=findViewById(R.id.hospitalInfo);
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), hospitalInfo.class);
                intent.putExtra("hos_url","https://petzonebd.com/veterinarian-pet-doctor-dhaka");
                startActivity(intent);
            }
        });
    }
}