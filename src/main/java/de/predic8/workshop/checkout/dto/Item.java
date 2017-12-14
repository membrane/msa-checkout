package de.predic8.workshop.checkout.dto;

import java.math.BigDecimal;

/**
 * Created by thomas on 08.12.17.
 */
public class Item {

    private String articleId;
    private int quantity;
    private BigDecimal price;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "articleId='" + articleId + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
