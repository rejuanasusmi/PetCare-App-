package com.example.trysomething;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class dogInterface extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_interface);


        CardView food=findViewById(R.id.dogFoodInfo);
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),foodPdfShow.class);
                intent.putExtra("pdf_url","https://nap.nationalacademies.org/resource/10668/dog_nutrition_final_fix.pdf");
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