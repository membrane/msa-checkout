package de.predic8.workshop.checkout.dto;

import java.util.List;

public class Basket {

	private String uuid;
	private String customer;
	private List<Item> items;

	public Basket() {
	}

	public String getUuid() {
		return this.uuid;
	}

	public String getCustomer() {
		return this.customer;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String toString() {
		return "Basket(uuid=" + uuid + ", customer=" + customer + ", items=" + items + ")";
	}
}