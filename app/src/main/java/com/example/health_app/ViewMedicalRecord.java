package com.example.health_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.health_app.database.MyDbHelper;
import com.example.health_app.model.Appointment;
import com.example.health_app.model.Record;

import java.util.List;

public class ViewMedicalRecord extends AppCompatActivity {

    TextView patientname, bg, hc, height, weight;
    MyDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medical_record);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        db = new MyDbHelper(this);



        patientname = findViewById(R.id.patientname);
        bg = findViewById(R.id.bg);
        hc = findViewById(R.id.hc);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);

        List<Record> records = db.getrecords(username);

        for(Record rc : records) {
            patientname.setText("patient name : "+rc.getPname());
            bg.setText("Blood Group : "+rc.getBloodgroup());
            hc.setText("Condition : "+rc.getHealthcondition());
            height.setText("Height : "+rc.getHt());
            weight.setText("Weight : "+rc.getWt());
        }



    }
}