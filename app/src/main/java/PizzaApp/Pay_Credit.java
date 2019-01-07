package PizzaApp;


public class Pay_Credit   {

    int id;
	int creditId;
	int month_validity;
    int year_validity;
	int cvc;
	double price;

	public Pay_Credit(){
        this.id = 0;
        this.creditId = 0;
        this.month_validity = 0;
        this.year_validity = 0;
        this.cvc = 0;
        this.price = 0.0;
    }

    public void setId(int id) { this.id = id; }

    public void setCreditId(int creditId) { this.creditId = creditId; }

    public void setMonthValidity(int month_validity) { this.month_validity = month_validity; }

    public void setYearValidity(int year_validity) { this.year_validity = year_validity; }

    public void setCvc(int cvc) { this.cvc = cvc; }

    public void setPrice(double price) { this.price = price; }

    //Sending the payment details to the credit card company and get approval.
	public boolean checkPayment() {
		return true;
	}

}
