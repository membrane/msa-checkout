package de.predic8.workshop.checkout.dto;

public class BasketIdentifier {
	private final String basket;

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
