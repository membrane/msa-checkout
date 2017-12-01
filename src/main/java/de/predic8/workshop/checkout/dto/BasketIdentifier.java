package de.predic8.workshop.checkout.dto;

public class BasketIdentifier {
	private String basket;

	public BasketIdentifier() {
	}

	public BasketIdentifier(String basket) {
		this.basket = basket;
	}

	public String getBasket() {
		return this.basket;
	}

	public String toString() {
		return "BasketIdentifier(basket=" + basket + ")";
	}
}
