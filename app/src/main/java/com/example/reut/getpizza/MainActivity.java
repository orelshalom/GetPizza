package com.example.reut.getpizza;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Holders.OrderDataHolder;
import Holders.WorkerDataHolder;
import PizzaApp.Order;
import PizzaApp.Worker;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog pd;
    private DatabaseReference dbrWorkers, dbrUsers, dbrOrders;
    private FirebaseAuth auth;
    private EditText user_email, user_password;
    private Button login;
    private TextView sign_up;
    private String prefix_email, workerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            String prefix_email = auth.getCurrentUser().getEmail().substring(0, 6);
            if (prefix_email.equals("worker")) {
                finish();
                startActivity(new Intent(getApplicationContext(), WorkerActivity.class));
            } else {
                finish();
                startActivity(new Intent(getApplicationContext(), ChoosingSizeActivity.class));
            }
        }

        dbrWorkers = FirebaseDatabase.getInstance().getReference("Workers");
        dbrUsers = FirebaseDatabase.getInstance().getReference("Users");
        dbrOrders = FirebaseDatabase.getInstance().getReference("Orders");




        user_email = findViewById(R.id.user_email);
        user_password = findViewById(R.id.user_password);
        login = findViewById(R.id.login);
        sign_up = findViewById(R.id.sign_up2);
        login.setOnClickListener(this);
        sign_up.setOnClickListener(this);
        pd = new ProgressDialog(this);

    }


    private void userLogin() {

        String email = user_email.getText().toString().trim();
        String password = user_password.getText().toString().trim();

        //Checking if email or password are empty.
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please Enter Email.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter Password.", Toast.LENGTH_LONG).show();
            return;
        }

        if (email.length() >= 9) {
            prefix_email = email.substring(0, 9);
            workerId = dbrWorkers.child(email.substring(6, 9)).getKey();
        }
        pd.setMessage("Login Please Wait..");
        pd.show();

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // We assume that the Manager assigns the worker a new user with password and email address
                            // that starts with "worker" + some id.
                            // if the worker doesn't work in the Pizzeria anymore the user is deleted by the Manager.
                            // Since we don't implement the Manager's activities, we added a user to Firebase Authentication.
                            String currentId = auth.getCurrentUser().getUid();
                            if (prefix_email.equals("worker" + workerId)) {
                                dbrWorkers.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            Worker worker = dataSnapshot.child(workerId).getValue(Worker.class);
                                            worker.setId(workerId);
                                            WorkerDataHolder.getWorkerDataHolder().getWorker().setAll(worker);
                                            finish();
                                            startActivity(new Intent(getApplicationContext(), WorkerActivity.class));
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(MainActivity.this, databaseError.toException().toString(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            } else {
                                dbrUsers.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
//                                    Client client = dataSnapshot.child(auth.getCurrentUser().getUid()).getValue(Client.class);
                                            Order order = new Order();
                                            order.setClientId(auth.getCurrentUser().getUid());
                                            order.setId(dbrOrders.push().getKey());
                                            OrderDataHolder.getOrderDataHolder().getOrder().setAll(order);
                                            startActivity(new Intent(getApplicationContext(), ChoosingSizeActivity.class));
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(MainActivity.this, databaseError.toException().toString(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Email or Password Incorrect", Toast.LENGTH_LONG).show();
                        }
                        pd.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v == login) {
            userLogin();
        }
        if (v == sign_up) {
            finish();
            startActivity(new Intent(this, RegistrationActivity.class));
        }
    }



}
// TODO An option to change the client details.







