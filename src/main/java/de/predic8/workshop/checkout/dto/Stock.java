package de.predic8.workshop.checkout.dto;

import lombok.Data;

@Data
public class Stock {
	private String uuid;
    private long quantity;
}