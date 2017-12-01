package de.predic8.workshop.checkout.dto;

import java.math.BigDecimal;

public class Article {
	private String article;
	private long quantity;
	private BigDecimal price;

	public Article(String article, long quantity, BigDecimal price) {
		this.article = article;
		this.quantity = quantity;
		this.price = price;
	}

	public String getArticle() {
		return this.article;
	}

	public long getQuantity() {
		return this.quantity;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String toString() {
		return "Article(article=" + article + ", quantity=" + quantity + ", price=" + price + ")";
	}
}