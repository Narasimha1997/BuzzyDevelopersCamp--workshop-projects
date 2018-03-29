package com.example.narasimha.orderapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity implements NumberPicker.OnValueChangeListener{

    NumberPicker costPicker;
    TextView cost;
    EditText address;

    float tot;
    int quantity = 0;

    final float cost_per_item = 20.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        costPicker = (NumberPicker)findViewById(R.id.PickCost);
        cost = (TextView)findViewById(R.id.CostView);
        address = (EditText) findViewById(R.id.address);

        costPicker.setMinValue(1);
        costPicker.setMaxValue(30);

        costPicker.setOnValueChangedListener(this);
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        tot = i1*cost_per_item;
        quantity = i1;
        cost.setText(Float.toString(tot)+" Rs");
    }

    public void submitOrder(View v) {
        String mail_text;
        if (address.getText().toString().equals("Enter your address") || address.getText().toString().equals("")){
            Toast.makeText(this, "Enter address", Toast.LENGTH_SHORT).show();
        }else{
            String body = "Quantity: "+quantity+"\nTotal Cost: "+tot+"\nAddress: "
                    +address.getText().toString();
            Intent email = new Intent(Intent.ACTION_SEND);
            email.setType("message/rfc822");
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"prasannahn1997@gmail.com"});
            email.putExtra(Intent.EXTRA_SUBJECT, "Order");
            email.putExtra(Intent.EXTRA_TEXT, body);

            startActivity(Intent.createChooser(email, "Send order using:"));
        }
    }
}
