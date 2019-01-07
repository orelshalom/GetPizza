package com.example.reut.getpizza;


import android.content.Intent;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.data.DataHolder;
import com.google.firebase.auth.FirebaseAuth;

import Holders.OrderDataHolder;
import PizzaApp.Order;
import PizzaApp.Pizza;
import PizzaApp.Size;


public class ChoosingSizeActivity extends AppCompatActivity implements View.OnClickListener{

    private ProgressDialog pd;
    private FirebaseAuth auth;
    private Button logout, continue_to_toppings;;
    private RadioButton big, family, personal;
    private RadioGroup sizes;
    private Pizza pizza;
    private Order order;
    private ImageView pizza_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing_size);

        auth = FirebaseAuth.getInstance();
        //if the user is already logged in - finish the activity and open choosing size activity.
        if(auth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }


        pizza= new Pizza();
        order= OrderDataHolder.getOrderDataHolder().getOrder();



        sizes = findViewById(R.id.sizes);
        logout = findViewById(R.id.logout);
        continue_to_toppings= findViewById(R.id.continue_to_toppings);
        pd = new ProgressDialog(this);
        logout.setOnClickListener(this);
        continue_to_toppings.setOnClickListener(this);
        pizza_pic= findViewById(R.id.pizza_pic);
        big = findViewById(R.id.big_size);
        family = findViewById(R.id.family_size);
        personal = findViewById(R.id.personal_size);

        RadioGroup radioGroup = findViewById(R.id.sizes);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==big.getId()){
                    pizza.setSize(Size.Big);
                    pizza_pic.setLayoutParams(new LinearLayout.LayoutParams(500,500));
                }
                else if(checkedId==family.getId()){
                    pizza.setSize(Size.Family);
                    pizza_pic.setLayoutParams(new LinearLayout.LayoutParams(400,400));
                }
                else if(checkedId==personal.getId()){
                    pizza.setSize(Size.Personal);
                    pizza_pic.setLayoutParams(new LinearLayout.LayoutParams(300,300));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == continue_to_toppings){
            if(pizza.getSize()==Size.None.toString()){
                Toast.makeText(ChoosingSizeActivity.this, "Please Choose Size.", Toast.LENGTH_SHORT).show();
            }
            else {
                order.addPizza(pizza);
                startActivity(new Intent(getApplicationContext(),ToppingsActivity.class));
            }
        }
        else if (v == logout) {
            auth.signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }





}

