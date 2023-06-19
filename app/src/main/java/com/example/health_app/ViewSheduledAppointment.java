package com.example.health_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.health_app.database.MyDbHelper;
import com.example.health_app.model.Appointment;
import com.example.health_app.model.Medecine;

import java.util.List;

public class ViewSheduledAppointment extends AppCompatActivity {

    Button btn;
    MyDbHelper db;

    private LinearLayout parentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sheduled_appointment);

        db = new MyDbHelper(this);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        List<Appointment> shedmed = db.getappointmentlist(username);

        parentContainer = findViewById(R.id.parent_container); // Get reference to the parent container

        // Add containers dynamically
        int numberOfContainers = 5; // Set the desired number of containers

        for(Appointment med: shedmed) {
//            View container = getLayoutInflater().inflate(R.layout.container_xml,  null);
            View container = getLayoutInflater().inflate(R.layout.container_xml, parentContainer, false);

            TextView textView = container.findViewById(R.id.text_view);
            String content = "doctor :"+med.getDhname()+" \n"+med.getDesc()+"\ndate : "+med.getDate();
            textView.setText(content);

            parentContainer.addView(container); // Add the inflated container to the parent container
        }


    }
}