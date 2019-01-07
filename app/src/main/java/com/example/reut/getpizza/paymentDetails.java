package com.example.reut.getpizza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Holders.OrderDataHolder;
import PizzaApp.Order;
import PizzaApp.Pay_Credit;

public class paymentDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener ,View.OnClickListener{
    private Spinner cardSpinner;
    private Spinner monthSpinner;
    private Spinner yearSpinner;
    private ArrayAdapter<CharSequence> adapter;
    private ArrayAdapter<CharSequence> adapter1;
    private ArrayAdapter<CharSequence> adapter2;
    private EditText card_number, identity_card, security_code;
    private Button confirm;
    private Order order;
    private Pay_Credit pay_details;
    private DatabaseReference dbrOrders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        card_number = findViewById(R.id.card_number);
        identity_card = findViewById(R.id.identity_card);
        security_code = findViewById(R.id.security_code);
        confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(this);
        pay_details= new Pay_Credit();

        order= OrderDataHolder.getOrderDataHolder().getOrder();

        dbrOrders = FirebaseDatabase.getInstance().getReference("Orders");

        cardSpinner = findViewById(R.id.card);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.cards_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardSpinner.setAdapter(adapter);
        cardSpinner.setOnItemSelectedListener(this);

        monthSpinner = findViewById(R.id.month);
        adapter1 = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter1);
        monthSpinner.setOnItemSelectedListener(this);


        yearSpinner = findViewById(R.id.year);
        adapter2 = ArrayAdapter.createFromResource(this,
                R.array.years_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(adapter2);
        yearSpinner.setOnItemSelectedListener(this);




    }

    public void getPaymentDetails(){
        int cvc = Short.parseShort(security_code.getText().toString().trim());
        int id= Integer.parseInt(identity_card.getText().toString().trim());
        int creditId= Integer.parseInt(card_number.getText().toString().trim());
        int month_validity= Integer.parseInt(monthSpinner.getSelectedItem().toString().trim());
        int year_validity=Integer.parseInt(yearSpinner.getSelectedItem().toString().trim());
        double price=order.getPrice();
        pay_details.setCvc(cvc);
        pay_details.setId(id);
        pay_details.setCreditId(creditId);
        pay_details.setMonthValidity(month_validity);
        pay_details.setYearValidity(year_validity);
        order.setPay(pay_details);
    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position>0) {
            String text = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v == confirm) {
            getPaymentDetails();
            dbrOrders.child(order.getId()).setValue(order);
            startActivity(new Intent(getApplicationContext(),Goodbye.class));
        }
    }




}
