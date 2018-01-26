package de.predic8.workshop.checkout.dto;

public class BasketIdentifier {
	public BasketIdentifier(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	private String uuid;
}
