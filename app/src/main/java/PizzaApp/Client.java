package PizzaApp;


public class Client {

	String id;
    String name;
    String phone_number;
	String address;

	public Client(){
		this.id = "";
		this.phone_number = "";
		this.address = "";
		this.name = "";
	}

	public Client(String id, String name, String phone_number, String address){
		this.id = id;
		this.name = name;
		this.phone_number = phone_number;
		this.address = address;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
