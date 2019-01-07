package com.example.reut.getpizza;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import Holders.EditPizzaDataHolder;
import Holders.OrderDataHolder;
import PizzaApp.Cheese;
import PizzaApp.Eggplant;
import PizzaApp.Mushrooms;
import PizzaApp.Onion;
import PizzaApp.Order;
import PizzaApp.Partition;
import PizzaApp.Pizza;
import PizzaApp.Sweet_Potato;
import PizzaApp.Tomato;
import PizzaApp.Topping;



public class ToppingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    private CheckBox onion, extra_cheese, eggplant, tomato, sweet_potato, mushroom;
    Spinner onion_spinner, extra_cheese_spinner, eggplant_spinner, tomato_spinner, sweet_potato_spinner, mushroom_spinner;
    Button orderButton;
    Pizza pizza;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toppings);
        this.onion = findViewById(R.id.Onion);
        this.eggplant = findViewById(R.id.Eggplant);
        this.extra_cheese = findViewById(R.id.Extra_Cheese);
        this.sweet_potato = findViewById(R.id.Sweet_Potato);
        this.tomato = findViewById(R.id.Tomato);
        this.mushroom = findViewById(R.id.Mushroom);
        this.orderButton = findViewById(R.id.Order) ;
        onion.setOnClickListener(this);
        eggplant.setOnClickListener(this);
        extra_cheese.setOnClickListener(this);
        sweet_potato.setOnClickListener(this);
        tomato.setOnClickListener(this);
        mushroom.setOnClickListener(this);
        orderButton.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Choose, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        order= OrderDataHolder.getOrderDataHolder().getOrder();
        boolean edit_mode=EditPizzaDataHolder.getEditPizzaDataHolder().getEditMode();
        Toast.makeText(ToppingsActivity.this, edit_mode+"", Toast.LENGTH_LONG).show();




        //Enter the size of the pizza from the previous Activity.
        this.onion_spinner= findViewById(R.id.Onion_spinner);
        this.onion_spinner.setAdapter(adapter);
        this.onion_spinner.setOnItemSelectedListener(this);

        this.eggplant_spinner= findViewById(R.id.Eggplant_spinner);
        this.eggplant_spinner.setAdapter(adapter);
        this.eggplant_spinner.setOnItemSelectedListener(this);

        this.sweet_potato_spinner= findViewById(R.id.Sweet_Potato_spinner);
        this.sweet_potato_spinner.setAdapter(adapter);
        this.sweet_potato_spinner.setOnItemSelectedListener(this);

        this.extra_cheese_spinner= findViewById(R.id.Extra_Cheese_spinner);
        this.extra_cheese_spinner.setAdapter(adapter);
        this.extra_cheese_spinner.setOnItemSelectedListener(this);

        this.tomato_spinner= findViewById(R.id.Tomato_spinner);
        this.tomato_spinner.setAdapter(adapter);
        this.tomato_spinner.setOnItemSelectedListener(this);

        this.mushroom_spinner= findViewById(R.id.mushroom_spinner);
        this.mushroom_spinner.setAdapter(adapter);
        this.mushroom_spinner.setOnItemSelectedListener(this);


        if(edit_mode){//if it is edit mode-got pizza to edit
            pizza= EditPizzaDataHolder.getEditPizzaDataHolder().getPizza();
            EditPizzaDataHolder.getEditPizzaDataHolder().setEditMode(false);
            checkChecked();
            pizza.toppings.clear();
        }
        else{//new pizza
            pizza= new Pizza();
        }

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){}

    @Override
    public void onClick(View v) {
        //TODO change the partiotion back to all
        if(v == tomato) {
            if(tomato.isChecked())
                tomato_spinner.setVisibility(View.VISIBLE);
            else
                tomato_spinner.setVisibility(View.INVISIBLE);
        }
        else if(v == onion) {
            if(onion.isChecked())
                onion_spinner.setVisibility(View.VISIBLE);
            else
                onion_spinner.setVisibility(View.INVISIBLE);
        }
        else if(v == extra_cheese){
            if(extra_cheese.isChecked())
                extra_cheese_spinner.setVisibility(View.VISIBLE);
            else
                extra_cheese_spinner.setVisibility(View.INVISIBLE);
        }
        else if(v == eggplant) {
            if(eggplant.isChecked())
                eggplant_spinner.setVisibility(View.VISIBLE);
            else
                eggplant_spinner.setVisibility(View.INVISIBLE);
        }
        else if(v == sweet_potato) {
            if(sweet_potato.isChecked())
                sweet_potato_spinner.setVisibility(View.VISIBLE);
            else
                sweet_potato_spinner.setVisibility(View.INVISIBLE);
        }
        else if(v == mushroom) {
            if(mushroom.isChecked())
                mushroom_spinner.setVisibility(View.VISIBLE);
            else
                mushroom_spinner.setVisibility(View.INVISIBLE);
        }
        else if(v == orderButton){
            getChecked();
            order.addPizza(pizza);
            startActivity(new Intent(getApplicationContext(),OrderActivity.class));
        }
    }

    public void getChecked(){
        if(onion.isChecked()){
            Topping onion = new Onion();
            if(onion_spinner.getSelectedItem().toString().equals("All"))
                onion.setPartition(Partition.All);
            else if(onion_spinner.getSelectedItem().toString().equals("Right Half"))
                onion.setPartition(Partition.HalfRight);
            else if(onion_spinner.getSelectedItem().toString().equals("Left Half"))
                onion.setPartition(Partition.HalfLeft);
            pizza.addTopping(onion);
        }

        if(extra_cheese.isChecked()){
            Topping cheese= new Cheese();
            if(extra_cheese_spinner.getSelectedItem().toString().equals("All"))
                cheese.setPartition(Partition.All);
            else if(extra_cheese_spinner.getSelectedItem().toString().equals("Right Half"))
                cheese.setPartition(Partition.HalfRight);
            else if(extra_cheese_spinner.getSelectedItem().toString().equals("Left Half"))
                cheese.setPartition(Partition.HalfLeft);
            pizza.addTopping(cheese);
        }

        if(eggplant.isChecked()){
            Topping eggplant= new Eggplant();
            if(eggplant_spinner.getSelectedItem().toString().equals("All"))
                eggplant.setPartition(Partition.All);
            else if(eggplant_spinner.getSelectedItem().toString().equals("Right Half"))
                eggplant.setPartition(Partition.HalfRight);
            else if(eggplant_spinner.getSelectedItem().toString().equals("Left Half"))
                eggplant.setPartition(Partition.HalfLeft);
            pizza.addTopping(eggplant);
        }

        if(sweet_potato.isChecked()){
            Topping sweetPotato= new Sweet_Potato();
            if(sweet_potato_spinner.getSelectedItem().toString().equals("All"))
                sweetPotato.setPartition(Partition.All);
            else if(sweet_potato_spinner.getSelectedItem().toString().equals("Right Half"))
                sweetPotato.setPartition(Partition.HalfRight);
            else if(sweet_potato_spinner.getSelectedItem().toString().equals("Left Half"))
                sweetPotato.setPartition(Partition.HalfLeft);
            pizza.addTopping(sweetPotato);
        }

        if(tomato.isChecked()){
            Topping _tomato= new Tomato();
            if(tomato_spinner.getSelectedItem().toString().equals("All"))
                _tomato.setPartition(Partition.All);
            else if(tomato_spinner.getSelectedItem().toString().equals("Right Half"))
                _tomato.setPartition(Partition.HalfRight);
            else if(tomato_spinner.getSelectedItem().toString().equals("Left Half"))
                _tomato.setPartition(Partition.HalfLeft);
            pizza.addTopping(_tomato);
        }
        if(mushroom.isChecked()){
            Topping mushroomTopping= new Mushrooms();
            if(mushroom_spinner.getSelectedItem().toString().equals("All"))
                mushroomTopping.setPartition(Partition.All);
            else if(mushroom_spinner.getSelectedItem().toString().equals("Right Half") )
                mushroomTopping.setPartition(Partition.HalfRight);
            else if(mushroom_spinner.getSelectedItem().toString().equals("Left Half"))
                mushroomTopping.setPartition(Partition.HalfLeft);
            pizza.addTopping(mushroomTopping);
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


    public void checkChecked(){
        for(int i=0; i<pizza.toppings.size(); i++){
            Toast.makeText(ToppingsActivity.this, pizza.toppings.get(i).name, Toast.LENGTH_LONG).show();
            Toast.makeText(ToppingsActivity.this, pizza.toppings.get(i).getPartition()+"", Toast.LENGTH_LONG).show();

            if(pizza.toppings.get(i).name.equals("Tomato")){
                tomato.setChecked(true);
                tomato_spinner.setVisibility(View.VISIBLE);
                tomato_spinner.setSelection(pizza.toppings.get(i).getPartition().ordinal());
            }
            else if(pizza.toppings.get(i).name.equals("Eggplant")){
                eggplant.setChecked(true);
                eggplant_spinner.setVisibility(View.VISIBLE);
                eggplant_spinner.setSelection(pizza.toppings.get(i).getPartition().ordinal());
            }
            else if(pizza.toppings.get(i).name.equals("Mushrooms")){
                mushroom.setChecked(true);
                mushroom_spinner.setVisibility(View.VISIBLE);
                mushroom_spinner.setSelection(pizza.toppings.get(i).getPartition().ordinal());
            }
            else if(pizza.toppings.get(i).name.equals("Onion")){
                onion.setChecked(true);
                onion_spinner.setVisibility(View.VISIBLE);
                onion_spinner.setSelection(pizza.toppings.get(i).getPartition().ordinal());
            }
            else if(pizza.toppings.get(i).name.equals("Cheese")){
                extra_cheese.setChecked(true);
                extra_cheese_spinner.setVisibility(View.VISIBLE);
                extra_cheese_spinner.setSelection(pizza.toppings.get(i).getPartition().ordinal());
            }
            else if(pizza.toppings.get(i).name.equals("Sweet_Potato")){
                sweet_potato.setChecked(true);
                sweet_potato_spinner.setVisibility(View.VISIBLE);
                sweet_potato_spinner.setSelection(pizza.toppings.get(i).getPartition().ordinal());
            }
        }
    }



}
