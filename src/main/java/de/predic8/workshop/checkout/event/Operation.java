package de.predic8.workshop.checkout.event;

import com.fasterxml.jackson.databind.JsonNode;

public class Operation {
	private String bo;
	private String action;
	private JsonNode object;

	public Operation() {
	}

	public Operation(String type, String action, JsonNode object) {
		this.bo = type;
		this.action = action;
		this.object = object;
	}

	public String getBo() {
		return this.bo;
	}

	public String getAction() {
		return this.action;
	}

	public JsonNode getObject() {
		return this.object;
	}

	public String toString() {
		return "Operation<<< bo=" + bo + " action=" + action + " object=" + object + ">>>";
	}
}