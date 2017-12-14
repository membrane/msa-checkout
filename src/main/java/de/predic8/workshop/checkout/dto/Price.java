package de.predic8.workshop.checkout.dto;

import java.math.BigDecimal;

public class Price {

	private String uuid;
	private BigDecimal price;

	public Price() {
	}

	public Price(String uuid, long quantity, BigDecimal price) {
		this.uuid = uuid;
		this.price = price;
	}

	public String getUuid() {
		return this.uuid;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String toString() {
		return "Price(uuid=" + uuid + ", price=" + price + ")";
	}
}