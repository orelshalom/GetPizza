package com.example.reut.getpizza;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PizzaViewHolder extends RecyclerView.ViewHolder{
   public TextView pizzaSize,rightToppings,leftToppings,allToppings,price;
   ImageButton edit, remove;
    RelativeLayout parentLayout;

    public PizzaViewHolder(@NonNull View itemView) {
        super(itemView);
        this.pizzaSize= itemView.findViewById(R.id.txtPizzaSize);
        this.rightToppings= itemView.findViewById(R.id.txtToppingsRight);
        this.leftToppings= itemView.findViewById(R.id.txtToppingsLeft);
        this.allToppings= itemView.findViewById(R.id.txtToppingsAll);
        this.price=itemView.findViewById(R.id.txtprice);
        this.remove=itemView.findViewById(R.id.remove);
        this.edit=itemView.findViewById(R.id.edit);
        parentLayout = itemView.findViewById(R.id.ListPizza);

    }
    public void bindData(final PizzaViewModle viewModel) {
        this.pizzaSize.setText(viewModel.getPizzaSize());
        this.rightToppings.setText(viewModel.getRightToopings());
        this.leftToppings.setText(viewModel.getLeftToppings());
        this.allToppings.setText(viewModel.getAllToppings());
        this.price.setText(viewModel.getPrice());

    }

    public TextView getPizzaSize() {
        return pizzaSize;
    }

    public void setPizzaSize(TextView pizzaSize) {
        this.pizzaSize = pizzaSize;
    }

    public TextView getRightToppings() {
        return rightToppings;
    }

    public void setRightToppings(TextView rightToppings) {
        this.rightToppings = rightToppings;
    }

    public TextView getLeftToppings() {
        return leftToppings;
    }

    public void setLeftToppings(TextView leftToppings) {
        this.leftToppings = leftToppings;
    }

    public TextView getAllToppings() {
        return allToppings;
    }

    public void setAllToppings(TextView allToppings) {
        this.allToppings = allToppings;
    }

}
