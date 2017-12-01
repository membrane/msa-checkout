package de.predic8.workshop.checkout.dto;

public class Stock {
	private String uuid;
    private long quantity;

	public Stock() {
	}

	public String getUuid() {
		return this.uuid;
	}

	public long getQuantity() {
		return this.quantity;
	}

	public String toString() {
		return "Stock(uuid=" + uuid + ", quantity=" + quantity + ")";
	}
}