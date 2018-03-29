package com.example.narasimha.googlesignin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    EditText username;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
            Toast.makeText(this, "Already logged in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Hello.class));
        }
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    public void login(View v){
        String uname = username.getText().toString();
        String passd = password.getText().toString();
        Toast.makeText(this, "Name: "+uname+" Pwd:"+passd, Toast.LENGTH_SHORT).show();
        if(TextUtils.isEmpty(uname) || TextUtils.isEmpty(passd))
            Toast.makeText(this, "Invalid Username and password", Toast.LENGTH_SHORT).show();
        else{
            auth.signInWithEmailAndPassword(uname, passd).addOnCompleteListener(
                    this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                                Toast.makeText(MainActivity.this, "Password not found", Toast.LENGTH_SHORT).show();
                            else{
                                Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                launch();
                            }
                        }
                    }
            );
        }
    }

    public void register(View v) {
        startActivity(new Intent(
                this, Register.class
        ));
    }

    void launch(){
        startActivity(new Intent(this, Hello.class));
    }
}
