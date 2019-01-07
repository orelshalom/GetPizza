package Holders;

import PizzaApp.Pizza;

public class EditPizzaDataHolder {

    private Pizza pizza = null;
    private static final EditPizzaDataHolder editPizza = new EditPizzaDataHolder();
    private boolean editMode;

    private EditPizzaDataHolder(){
        pizza = new Pizza();
        editMode=false;
    }

    public static EditPizzaDataHolder getEditPizzaDataHolder(){
        return editPizza;
    }

    public Pizza getPizza(){
        return pizza;
    }

    public boolean getEditMode(){
        return editMode;
    }

    public void setEditMode(boolean mode){
        editMode=mode;
    }

}
