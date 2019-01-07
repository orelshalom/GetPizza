package PizzaApp;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;


public class Order implements Serializable {

	String id;
	String clientId;

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	String workerName;
	List<Pizza> pizzas;
	String date;
	Status status;
	Pay_Credit pay;
	double price;
	boolean isInTreatment;


	public Order(){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("Israel"));
		this.id = "";
		this.clientId = "";
		this.workerName = "";
		this.pizzas = new ArrayList<>();
		this.date = format.format(cal.getTime());
		this.status = Status.None;
		this.pay = null;
		this.price = 0.0;
		this.isInTreatment = false;
	}

//	public Order(String id, String clientId, List<Pizza> pizzas, String date, Status status, Pay_Credit pay, double price) {
//	    this.id = id;
//		this.clientId = clientId;
//		this.pizzas = pizzas;
//		this.date = date;
//		this.status = status;
//		this.pay = pay;
//		this.price = price;
//    }

	//Adding Pizza to the order.
	public void addPizza(Pizza p) {
		pizzas.add(p);
	}

	//Calculates the total price of the order.
	public void totalPrice() {
		double sum = 0;
		//Calculation
		for(int i = 0; i<pizzas.size(); i++){
			sum += pizzas.get(i).price;
		}
		this.price = sum;
	}

	/** Getters & Setters **/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientId() { return clientId; }

	public void setClientId(String clientId) { this.clientId = clientId; }

	public void setDate(String date) { this.date = date; }

	public String getDate() { return date; }

	public List<Pizza> getPizzas() {
		return pizzas;
	}

	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Pay_Credit getPay() {
		return pay;
	}

	public void setPay(Pay_Credit pay) {
		this.pay = pay;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setAll(Order order){
		this.id = order.id;
		this.clientId = order.clientId;
		this.workerName = order.workerName;
		this.pizzas.clear();
		this.pizzas.addAll(order.pizzas);
		this.date = order.date;
		this.status = order.status;
		this.pay = order.pay;
		this.price = order.price;
	}
}
