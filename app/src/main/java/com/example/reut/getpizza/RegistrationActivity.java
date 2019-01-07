package com.example.reut.getpizza;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import PizzaApp.Client;
import PizzaApp.Order;



public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText user_name, user_email, user_password, user_phone, user_address;
    private Button sign_up;
    private ProgressDialog pd;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference dbrUsers;
    private DatabaseReference dbrOrders;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), ChoosingSizeActivity.class));
            finish();
        }

        dbrUsers = FirebaseDatabase.getInstance().getReference("Users");
        dbrOrders = FirebaseDatabase.getInstance().getReference("Orders");

        user_name = findViewById(R.id.user_name);
        user_email = findViewById(R.id.user_email);
        user_password = findViewById(R.id.user_password);
        user_phone = findViewById(R.id.user_phone);
        user_address = findViewById(R.id.user_address);
        sign_up = findViewById(R.id.sign_up2);

        user = auth.getCurrentUser();
        pd = new ProgressDialog(this);
        sign_up.setOnClickListener(this);
    }

    private void userRegistration() {
        name = user_name.getText().toString().trim();
        email = user_email.getText().toString().trim();
        password = user_password.getText().toString().trim();
        phone = user_phone.getText().toString().trim();
        address = user_address.getText().toString().trim();

        //Check if anything is empty.
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please Enter Name.", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please Enter Email.", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter Password.", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please Enter Phone Number.", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Please Enter Address.", Toast.LENGTH_LONG).show();
            return;
        }

        pd.setMessage("Loading Please Wait..");
        pd.show();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String Uid = auth.getCurrentUser().getUid();
                    Client client = new Client();
                    client.setId(Uid);
                    client.setName(name);
                    client.setPhone_number(phone);
                    client.setAddress(address);
                    String Oid = dbrOrders.push().getKey();
                    Order order = new Order();
                    order.setId(Oid);
                    order.setClientId(Uid);

                    dbrUsers.child(Uid).setValue(client);
                    dbrOrders.child(Oid).setValue(order);

                    Intent intent= new Intent(getApplicationContext(),ChoosingSizeActivity.class);
//                    intent.putExtra("client", client);
                    intent.putExtra("order", order);
                    Toast.makeText(RegistrationActivity.this, "Registration Successfully Ended.", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(intent);
                    //TODO Pass the order and the client.
//                    finish();
//                    startActivity(new Intent(getApplicationContext(), ChoosingSizeActivity.class));
                } else {
                    Toast.makeText(RegistrationActivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                }
                pd.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == sign_up) {
            userRegistration();
        }
    }

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
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}


