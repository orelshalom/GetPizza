package com.example.reut.getpizza;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import Holders.OrderDataHolder;
import Holders.WorkerDataHolder;
import PizzaApp.Order;
import PizzaApp.Worker;


public class WorkerActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference dbrOrders;
    private FirebaseAuth auth;
    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayList<Order> ordersList;
    private ArrayAdapter<String> adapter;
    private Button logout;
    private Worker worker;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);

        auth = FirebaseAuth.getInstance();
        dbrOrders = FirebaseDatabase.getInstance().getReference("Orders");
        if(auth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        arrayList = new ArrayList<>();
        ordersList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView = findViewById(R.id.orders_list);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);

        Query query = dbrOrders.orderByChild("date");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Order order = ds.getValue(Order.class);
                        ordersList.add(order);
                        arrayList.add("Order ID:  " + order.getId() + "\nOrder Date:  " + order.getDate());
                    }
                    listView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(WorkerActivity.this, databaseError.toException().toString(), Toast.LENGTH_LONG).show();
            }
        });

        listView = findViewById(R.id.orders_list);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                worker = WorkerDataHolder.getWorkerDataHolder().getWorker();
                Order order = ordersList.get(position);
                order.setWorkerName(worker.getName());
                OrderDataHolder.getOrderDataHolder().getOrder().setAll(order);
                startActivity(new Intent(getApplicationContext(), SettingOrderDetailsActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == logout) {
            auth.signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}