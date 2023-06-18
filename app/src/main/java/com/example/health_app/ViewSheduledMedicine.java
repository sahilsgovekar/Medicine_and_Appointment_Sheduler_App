 package com.example.health_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.health_app.database.MyDbHelper;
import com.example.health_app.model.Medecine;

import java.util.List;

 public class ViewSheduledMedicine extends AppCompatActivity {

    Button btn;
    MyDbHelper db;

    private LinearLayout parentContainer;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sheduled_medicine);
        db = new MyDbHelper(this);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

//        btn = findViewById(R.id.button);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                List<Medecine> shedmed = db.getallmedecine(username);
//                for(Medecine med: shedmed){
//                    Log.d("dbparse", "\nname: " + med.getName() + "\n" +
//                            "MName: " + med.getMname() + "\n" );
//
//
//                }
//            }
//        });

         List<Medecine> shedmed = db.getallmedecine(username);

        parentContainer = findViewById(R.id.parent_container); // Get reference to the parent container

        // Add containers dynamically
        int numberOfContainers = 5; // Set the desired number of containers

         for(Medecine med: shedmed) {
            View container = getLayoutInflater().inflate(R.layout.container_xml, null);
            TextView textView = container.findViewById(R.id.text_view);
            textView.setText(med.getMname());

            parentContainer.addView(container); // Add the inflated container to the parent container
        }
    }
}