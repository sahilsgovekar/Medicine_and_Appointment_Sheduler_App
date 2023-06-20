package com.example.health_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.health_app.database.MyDbHelper;

public class SignupPerMedDet extends AppCompatActivity {

    EditText patientname, bg, hc, height, weight;
    Button sub;

    MyDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_per_med_det);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        db = new MyDbHelper(this);

        patientname = findViewById(R.id.patientname);
        bg = findViewById(R.id.bg);
        hc = findViewById(R.id.hc);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);


        sub = findViewById(R.id.sub);


        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pname = patientname.getText().toString();
                String bloodgroup = bg.getText().toString();
                String healthcondition = hc.getText().toString();
                String ht = height.getText().toString();
                String wt = weight.getText().toString();

                boolean instatus = insertIntoDatabase(pname, bloodgroup, healthcondition, ht, wt, username);

                if(instatus) {
                    Toast.makeText(SignupPerMedDet.this, "record saved", Toast.LENGTH_SHORT).show();
                    Log.d("t1", "afcll: ");
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignupPerMedDet.this, "error in db, retry", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean insertIntoDatabase(String pname, String bloodgroup, String healthcondition, String ht, String wt, String username) {


        if(TextUtils.isEmpty(pname) || TextUtils.isEmpty(bloodgroup) || TextUtils.isEmpty(healthcondition) || TextUtils.isEmpty(ht) || TextUtils.isEmpty(wt)  )    {
            Toast.makeText(SignupPerMedDet.this, "all feilds required", Toast.LENGTH_SHORT).show();
        }
        else {
            Boolean insert = db.insertrecord(username, pname, bloodgroup, healthcondition, ht, wt);


            if(insert) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}