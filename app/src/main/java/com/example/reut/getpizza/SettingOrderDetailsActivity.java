package com.example.reut.getpizza;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import Holders.OrderDataHolder;
import Holders.WorkerDataHolder;
import PizzaApp.Order;
import PizzaApp.Pizza;
import PizzaApp.Status;
import PizzaApp.Worker;



public class SettingOrderDetailsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private DatabaseReference dbrOrders, dbrUsers, dbrWorkers, dbrNotifications;
    private FirebaseAuth auth;
    private LinearLayout linearLayout, empty;
    private TextView setting_name, setting_phone, setting_address;
    private Spinner spinner;
    private Button client_details;
    private ListView listViewPizza;
    private List<String> arraylist;
    private ArrayAdapter<String> pizzaAdapter;
    private ArrayAdapter<CharSequence> adapter;
    private Order order;
    private Worker worker;
    private String title, message;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_order_details);

        auth = FirebaseAuth.getInstance();
        dbrOrders = FirebaseDatabase.getInstance().getReference("Orders");
        dbrUsers = FirebaseDatabase.getInstance().getReference("Users");
        dbrWorkers = FirebaseDatabase.getInstance().getReference("Workers");
        dbrNotifications = FirebaseDatabase.getInstance().getReference("Notifications");

        worker = WorkerDataHolder.getWorkerDataHolder().getWorker();
        order = OrderDataHolder.getOrderDataHolder().getOrder();

        empty = findViewById(R.id.empty_layout);
        linearLayout = findViewById(R.id.client_details_layout);
        linearLayout.setVisibility(View.INVISIBLE);
        setting_name = findViewById(R.id.setting_name);
        setting_phone = findViewById(R.id.setting_phone);
        setting_address = findViewById(R.id.setting_address);
        empty.setOnClickListener(this);

        spinner = findViewById(R.id.spinner_set_status);
        listViewPizza = findViewById(R.id.pizzas_list);
        client_details = findViewById(R.id.show_client_details);
        client_details.setOnClickListener(this);

        adapter = ArrayAdapter.createFromResource(this, R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        arraylist = new ArrayList<>();
        pizzaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arraylist);
        listViewPizza.setAdapter(pizzaAdapter);

        // Reading Pizzas from DB and show them on screen.
        Query query = dbrOrders.child("pizzas").orderByChild("size");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arraylist.clear();
                int count = 0;
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        count++;
                        Pizza pizza = ds.child("pizzas").getValue(Pizza.class);
                        order.getPizzas().add(pizza);
                        arraylist.add("#" + count + " Pizza\n" +
                                "Size: " + pizza.getSize().toString() +
                                "\nToppings: " + pizza.toString());
                    }
                    listViewPizza.setAdapter(pizzaAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SettingOrderDetailsActivity.this, databaseError.toException().toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v == client_details){
            dbrUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    setting_name.setText(dataSnapshot.child(order.getClientId()).child("name").getValue(String.class));
                    setting_phone.setText(dataSnapshot.child(order.getClientId()).child("phone_number").getValue(String.class));
                    setting_address.setText(dataSnapshot.child(order.getClientId()).child("address").getValue(String.class));
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });
            linearLayout.setVisibility(View.VISIBLE);
        }
        else if(v == empty) linearLayout.setVisibility(View.INVISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position != 0) {
            if(position == 1){
                dbrOrders.child(order.getId()).child("status").setValue(Status.Delivered);
                order.setStatus(Status.Delivered);
            }
            else if (position == 2) {
                dbrOrders.child(order.getId()).child("status").setValue(Status.In_Progress);
                order.setStatus(Status.In_Progress);
            }
            else {
                dbrOrders.child(order.getId()).child("status").setValue(Status.On_The_Way);
                order.setStatus(Status.On_The_Way);
            }



        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        finish();
    }
}