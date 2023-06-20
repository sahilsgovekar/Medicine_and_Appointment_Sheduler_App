package com.example.health_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    TextView headname;
    CardView shedMedBtn, bookapbtn, viewshedmed, viewshedapp, vr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        Toast.makeText(this, "Hello "+username, Toast.LENGTH_SHORT).show();

        headname = findViewById(R.id.title_view);
        shedMedBtn = findViewById(R.id.shedMedBtn);
        bookapbtn = findViewById(R.id.bookapbtn);
        viewshedmed = findViewById(R.id.viewshedmed);
        viewshedapp = findViewById(R.id.viewshedapp);
        vr = findViewById(R.id.vr);

        headname.setText("@"+username);

        shedMedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SheduleMedecine.class);
                intent.putExtra("username", username);
                startActivity(intent);

            }
        });

        bookapbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SheduleAppointment.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        viewshedmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewSheduledMedicine.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        viewshedapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewSheduledAppointment.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        vr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewMedicalRecord.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });


    }
}