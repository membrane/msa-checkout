package de.predic8.workshop.checkout.dto;

import lombok.Data;

import java.util.List;

@Data
public class Basket {
	private String uuid;
	private String customer;
	private List<Article> items;
}