package PizzaApp;

import java.io.Serializable;

public class Worker implements Serializable {

	String name;
	String id;

	public Worker() {
		this.name = "";
		this.id = "";
	}

	public void setAll(Worker worker) {
		this.name = worker.name;
		this.id = worker.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}