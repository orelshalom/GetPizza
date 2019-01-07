package com.example.reut.getpizza;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import Holders.EditPizzaDataHolder;
import Holders.OrderDataHolder;
import PizzaApp.Order;
import PizzaApp.Pizza;
//
public class MyAdapter extends RecyclerView.Adapter<PizzaViewHolder> {
    List<PizzaViewModle> pizzaList;
    List<Pizza> pizzas;
    EditPizzaDataHolder editPizza;
    private Context context;
    private Order order;

    public MyAdapter(OrderActivity order_activity, List<Pizza> pizzas, Context context, Order order) {
        this.pizzas = new ArrayList<>();
        this.pizzas.addAll(pizzas);
        this.pizzaList = new ArrayList<PizzaViewModle>();
        this.context= context;
        this.order=order;

        for (int i = 0; i < pizzas.size(); ++i) {
            PizzaViewModle temp = new PizzaViewModle(pizzas.get(i));
            this.pizzaList.add(temp);
        }
//        Toast.makeText(context, pizzaList.size()+"", Toast.LENGTH_LONG).show();


    }

    public List<Pizza> getPizzas() {
        return this.pizzas;
    }

    @NonNull
    public PizzaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_pizza_view_holder, parent, false);
        PizzaViewHolder holder = new PizzaViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PizzaViewHolder pizzaViewHolder, final int i) {
        pizzaViewHolder.bindData(this.pizzaList.get(i));
        pizzaViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, pizzaList.get(i).getPizzaSize(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, pizzaList.get(i).getRightToopings(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, pizzaList.get(i).getLeftToppings(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, pizzaList.get(i).getAllToppings(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, pizzaList.get(i).getPrice(), Toast.LENGTH_SHORT).show();
            }
        });
        pizzaViewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String size = (String) pizzaViewHolder.getPizzaSize().getText();
                String right = (String) pizzaViewHolder.getRightToppings().getText();
                String left = (String) pizzaViewHolder.getLeftToppings().getText();
                String all = (String) pizzaViewHolder.getAllToppings().getText();
                int pizzaIndex=findPizza(size, right, left, all);
                remove(pizzaIndex);

            }

        });
        pizzaViewHolder.edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String size = (String) pizzaViewHolder.getPizzaSize().getText();
                String right = (String) pizzaViewHolder.getRightToppings().getText();
                String left = (String) pizzaViewHolder.getLeftToppings().getText();
                String all = (String) pizzaViewHolder.getAllToppings().getText();

                int pizzaIndex=findPizza(size, right, left, all);
                Pizza p=pizzas.get(pizzaIndex);
                remove(pizzaIndex);
                editPizza= EditPizzaDataHolder.getEditPizzaDataHolder();
                editPizza.getPizza().copy(p);
                editPizza.setEditMode(true);
                Intent intent = new Intent(context,ToppingsActivity.class);
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return pizzaList.size();
    }

    public int getItemViewType(final int position) {
        return R.layout.activity_pizza_view_holder;
    }

    public void remove(int i) {
        pizzaList.remove(i);
        pizzas.remove(i);
        order.getPizzas().remove(i);
        notifyItemRemoved(i);
    }


    public int findPizza(String size, String right, String left, String all){
        boolean flag = true;
        int index=0;
        for (int i = 0; i < pizzaList.size() && flag; ++i) {
            if (size.equals(pizzaList.get(i).getPizzaSize()) && right.equals(pizzaList.get(i).getRightToopings()) && left.equals(pizzaList.get(i).getLeftToppings()) && all.equals(pizzaList.get(i).getAllToppings())) {
                flag = false;
                index=i;
            }
        }
        return index;
    }

    public Pizza edit(String size, String right, String left, String all) {
        int i;

        for ( i = 0; i < pizzaList.size(); ++i) {
            if (size.equals(pizzaList.get(i).getPizzaSize()) && right.equals(pizzaList.get(i).getRightToopings()) && left.equals(pizzaList.get(i).getLeftToppings()) && all.equals(pizzaList.get(i).getAllToppings()))
                break;
            }
            Pizza temp;
            temp=pizzas.get(i);
            pizzas.remove(i);
            return temp;
    }






}



