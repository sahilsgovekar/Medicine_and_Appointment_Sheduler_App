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

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button signin, createacc;
    MyDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username1);
        password = findViewById(R.id.password1);
        signin = findViewById(R.id.signin);
        createacc = findViewById(R.id.createacc);
        db = new MyDbHelper(this);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(LoginActivity.this, "all feilds required", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean checkuserpass = db.checkusernamepassword(user, pass);
                    if(checkuserpass == true) {
                        Toast.makeText(LoginActivity.this, "login sucess", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("username", user);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "login faild", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        createacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });
    }
}