package com.example.reut.getpizza;

import android.app.Notification;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Random;
import Holders.OrderDataHolder;
import PizzaApp.NotificationHelper;
import PizzaApp.Order;
import PizzaApp.Status;


public class Goodbye extends AppCompatActivity {
    private NotificationHelper helper;
    private DatabaseReference dbrOrders;
    private Order order;
    private String title;
    private String body;
    private Status status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodbye);
        order = OrderDataHolder.getOrderDataHolder().getOrder();
        dbrOrders = FirebaseDatabase.getInstance().getReference("Orders");
        helper= new NotificationHelper(this);
        title = "Your order status was updated";
        dbrOrders.child(order.getId()).child("status").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    status = dataSnapshot.getValue(Status.class);
                    body = status + "";
                    Notification.Builder builder = helper.getChannelNotification(title,body);
                    helper.getManager().notify(new Random().nextInt(),builder.build());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });







    }
}
