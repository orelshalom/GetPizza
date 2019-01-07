package com.example.reut.getpizza;
import PizzaApp.Pizza;

public class PizzaViewModle {
    private String PizzaSize;
    private String RightToopings;
    private String LeftToppings;
    private String AllToppings;
    private String price;

    @Override
    public String toString() {
        return "PizzaViewModle{" +
                "PizzaSize='" + PizzaSize + '\'' +
                ", RightToopings='" + RightToopings + '\'' +
                ", LeftToppings='" + LeftToppings + '\'' +
                ", AllToppings='" + AllToppings + '\'' +
                '}';
    }

    public PizzaViewModle(Pizza pizza){
        this.PizzaSize=pizza.getSize();
        this.RightToopings=pizza.getRightToppings();
        this.LeftToppings=pizza.getLeftToppings();
        this.AllToppings=pizza.getAllToppings();
        this.price=String.valueOf(pizza.PriceOfPizza());
    }

    public String getPrice() {
        return price+"\u20AA";
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPizzaSize(String pizzaSize) {
        PizzaSize = pizzaSize;
    }

    public void setRightToopings(String rightToopings) {
        RightToopings = rightToopings;
    }

    public void setLeftToppings(String leftToppings) {
        LeftToppings = leftToppings;
    }

    public void setAllToppings(String allToppings) {
        AllToppings = allToppings;
    }

    public String getPizzaSize() {
        return PizzaSize;
    }

    public String getRightToopings() {
        return RightToopings;
    }

    public String getLeftToppings() {
        return LeftToppings;
    }

    public String getAllToppings() {
        return AllToppings;
    }
}
