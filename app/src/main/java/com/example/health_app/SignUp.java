package com.example.health_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.health_app.database.MyDbHelper;

public class SignUp extends AppCompatActivity {

    EditText username, password, repassword;
    Button signup, signin;

    MyDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        signup = findViewById(R.id.signup);
        signin = findViewById(R.id.signin);

        db = new MyDbHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass)) {
                    Toast.makeText(SignUp.this, "all feilds required", Toast.LENGTH_SHORT).show();
                }
                else {
                    if( pass.equals(repass)) {
                        Boolean checkuser = db.checkusername(user);
                        if(checkuser == false) {
                            Boolean insert = db.insertData(user, pass);
                            if(insert==true) {
                                Toast.makeText(SignUp.this, "regrestration sucessfull", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), SignupPerMedDet.class);
                                intent.putExtra("username", user);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignUp.this, "Regrestration failes, try again", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignUp.this, "user already exist", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUp.this, "password not matching", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}