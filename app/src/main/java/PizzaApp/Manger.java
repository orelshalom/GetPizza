package PizzaApp;


public class Manger {

	String name;
	String password;


	public void setName(String name) { this.name = name; }

	public void setPassword(String password) { this.password = password; }

	public void set_supply(Topping t, int sup) {}// on the DB

	public double show_income_week () { return 0.0; }// from the DB
	public double show_income_day () {
		return 0.0;
	}// from the DB
	public double show_income_month () {
		return 0.0;
	}// from the DB
	public double show_income_year () {
		return 0.0;
	}// from the DB


	
}
