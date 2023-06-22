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

import java.util.regex.Pattern;

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
                        if(isValidPassword(pass)) {
                            Boolean checkuser = db.checkusername(user);
                            if(checkuser == false) {
                                Boolean insert = db.insertData(user, pass);
                                if(insert==true) {
                                    Toast.makeText(SignUp.this, "regrestration sucessfull", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), SignupPerMedDet.class);
                                    intent.putExtra("username", user);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignUp.this, "Regrestration failes, try again", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(SignUp.this, "user already exist", Toast.LENGTH_SHORT).show();
                            }
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


    private Boolean isValidPassword(String password) {
        Pattern lowercase = Pattern.compile("^.*[a-z].*$");
        Pattern uppercase = Pattern.compile("^.*[A-Z].*$");
        Pattern number = Pattern.compile("^.*[0-9].*$");
        Pattern specialCharacter = Pattern.compile("^.*[^a-zA-Z0-9].*$");
        if (password.length() < 8) {
            Toast.makeText(this, "password should be minimum of 8 character", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!lowercase.matcher(password).matches()) {
            Toast.makeText(this, "password should contains one lower case", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!uppercase.matcher(password).matches()) {
            Toast.makeText(this, "password should contains atleast one upper case", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!number.matcher(password).matches()) {
            Toast.makeText(this, "Toast should contain atleast one number", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!specialCharacter.matcher(password).matches()) {
            Toast.makeText(this, "Password should contain atleast one special character", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}