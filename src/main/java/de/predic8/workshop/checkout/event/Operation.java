package de.predic8.workshop.checkout.event;

import com.fasterxml.jackson.databind.JsonNode;

public class Operation {
	private String type;
	private String action;
	private JsonNode object;

	public Operation(String type, String action, JsonNode object) {
		this.type = type;
		this.action = action;
		this.object = object;
	}

	public String getType() {
		return this.type;
	}

	public String getAction() {
		return this.action;
	}

	public JsonNode getObject() {
		return this.object;
	}

	public String toString() {
		return "Operation(type=" + this.getType() + ", action=" + this.getAction() + ", object=" + this.getObject() + ")";
	}
}