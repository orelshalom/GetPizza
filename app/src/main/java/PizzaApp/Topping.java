package PizzaApp;


import java.io.Serializable;

public class Topping implements Serializable{

	double price;
	Partition part;
	public String name;

	public Topping() {
		this.price = 0;
		this.part = Partition.None;
		this.name = "";
	}

	public void setPartition(Partition part) {
		this.part = part;
	}

	public double getPrice() {
		if(part == Partition.HalfLeft || part == Partition.HalfRight) return this.price/2;
		return this.price;
	}

	public Partition getPartition() {
		return this.part;
	}

}
