package com.example.narasimha.orderapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    final String username = "Prasanna";
    final String password = "Prasanna1997";

    int Login_count = 0;

    EditText u_name;
    EditText password_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        u_name = (EditText)findViewById(R.id.Username);
        password_ = (EditText)findViewById(R.id.password);
    }

    public void tryLogin(View V) {
        String username = u_name.getText().toString();
        String password = password_.getText().toString();
        if(Login_count == 3) {
            Toast.makeText(this, "Too many attempts", Toast.LENGTH_SHORT).show();
            u_name.setEnabled(false);
            password_.setEnabled(false);
        }

        if(username.equals(this.username) && password.equals(this.password)) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Main2Activity.class));
        }else {
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
            Login_count++;
        }
    }
}
