package de.predic8.workshop.checkout.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class Article {
	private String article;
	private long quantity;
	private BigDecimal price;
}