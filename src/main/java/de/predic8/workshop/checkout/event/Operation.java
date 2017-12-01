package de.predic8.workshop.checkout.event;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Operation {
	private String type;
	private String action;
	private JsonNode object;
}