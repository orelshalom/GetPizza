package PizzaApp;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;




public class Pizza implements Serializable {

	Size size;
	public List <Topping> toppings;
	double price;

	public Pizza() {
		this.size = size.None;
		this.toppings = new ArrayList<Topping>();
		this.price = 0;
	}

	public Pizza(Pizza p){
		this.size = p.size;
		this.price = p.price;
		this.toppings.clear();
		for(int i=0; i < p.toppings.size(); i++){
			this.toppings.add(p.toppings.get(i));
		}
	}



	//Adding topping to the pizza.
	public void addTopping(Topping top) {
		this.toppings.add(top);
	}


	@Override
	public String toString(){
		String s = "";
		for(int i=0; i<toppings.size(); i++){
			if(i==0) s = s + "" + toppings.get(i).name;
			else s = s + " , " + toppings.get(i).name;
		}
		return s;
	}

//	public Size getSize() {
//		return size;
//	}


	public List<Topping> getToppings() {
		return toppings;
	}

	public void setToppings(List<Topping> toppings) {
		this.toppings = toppings;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void copy(Pizza p){
		this.size = p.size;
		this.price = p.price;
		this.toppings.clear();
		for(int i=0; i < p.toppings.size(); i++){
			this.toppings.add(p.toppings.get(i));
		}
	}
	public String getSize(){
		if(size==Size.Big)
			return "Big";
		else if(size==Size.Family)
			return "Family";
		else if(size==Size.Personal)
			return "Personal";
		else
			return "None";
	}
	//Adding topping to the pizza.
	public void addToppings(Topping top) {
		this.toppings.add(top);
	}

	//Calculate the pizza's price.
	public double PriceOfPizza() {
		double temp = 0;
		for(int i=0; i<toppings.size();i++){
			if(toppings.get(i).part==Partition.All)
				price+=toppings.get(i).price;
			else
				price+=toppings.get(i).price*0.5;
		}
		return price+20;

	}
	public void setSize(Size s)
	{this.size=s;}
	public String getRightToppings(){
		String s="Right: ";
		int counter=0;
		for(int i=0; i<this.toppings.size(); ++i){
			if(this.toppings.get(i).part==Partition.HalfRight){
				if(counter==0)
					s=s+this.toppings.get(i).name;
				else
					s=s+" , "+this.toppings.get(i).name;
				counter++;
			}
		}
		return s;
	}

	public String getLeftToppings(){
		String s="Left: ";
		int counter=0;
		for(int i=0; i<this.toppings.size(); ++i){
			if(this.toppings.get(i).part==Partition.HalfLeft){
				if(counter==0)
					s=s+this.toppings.get(i).name;
				else
					s=s+" , "+this.toppings.get(i).name;
				counter++;
			}
		}
		return s;
	}
	public String getAllToppings(){
		String s="All: ";
		int counter=0;
		for(int i=0; i<this.toppings.size(); ++i){
			if(this.toppings.get(i).part==Partition.All){
				if(counter==0)
					s=s+this.toppings.get(i).name;
				else
					s=s+" , "+this.toppings.get(i).name;
				counter++;
			}
		}
		return s;
	}


}