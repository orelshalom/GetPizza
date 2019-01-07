package com.example.reut.getpizza;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import Holders.EditPizzaDataHolder;
import Holders.OrderDataHolder;
import PizzaApp.Order;


public class OrderActivity extends AppCompatActivity implements View.OnClickListener{

    private Order order;
    EditPizzaDataHolder editPizza;
    private Button to_payment_details;
    MyAdapter adapter1;
    ImageButton newPizza;
//    MyAdapter mClass = new MyAdapter(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


        order= OrderDataHolder.getOrderDataHolder().getOrder();
        editPizza= EditPizzaDataHolder.getEditPizzaDataHolder();
        to_payment_details=findViewById(R.id.to_payment_details);
        to_payment_details.setOnClickListener(this);
        newPizza=findViewById(R.id.newPizza);
        newPizza.setOnClickListener(this);


        RecyclerView recyclerView = findViewById(R.id.ListPizza);
        adapter1 = new MyAdapter(this, order.getPizzas(), this,order);
        recyclerView.setAdapter(adapter1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));





    }


    @Override
    public void onClick(View v) {
        if (v == to_payment_details) {
            finish();
            startActivity(new Intent(getApplicationContext(),paymentDetails.class));
        }
        else if(v==newPizza){
            startActivity(new Intent(getApplicationContext(),ChoosingSizeActivity.class));
        }
    }




}
