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

public class Register extends AppCompatActivity {

    String username;
    String password;
    String password_c;

    EditText uname, pwd, c_pwd;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
        uname = findViewById(R.id.Email);
        pwd = findViewById(R.id.Password);
        c_pwd = findViewById(R.id.Confirm);
    }

    public void addUser(View v){
        username = uname.getText().toString();
        password = pwd.getText().toString();
        password_c = c_pwd.getText().toString();

        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password_c)){
            Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT).show();
            return;
        }else if(!TextUtils.equals(password, password_c)){
            Toast.makeText(this, "Passwords doesn't match", Toast.LENGTH_SHORT).show();
        }else{
            auth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(
                    this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(Register.this, "Unable to register", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Register.this, "User registered", Toast.LENGTH_SHORT).show();
                                launch();
                            }
                        }

                    }
            );
        }
    }


    void launch(){
        startActivity(new Intent(this, MainActivity.class));
    }
}
