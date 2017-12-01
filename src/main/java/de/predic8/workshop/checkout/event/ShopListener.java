package de.predic8.workshop.checkout.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@Service
public class ShopListener {
	private final Map<String, BigDecimal> prices;

	public ShopListener(Map<String, BigDecimal> prices) {
		this.prices = prices;
	}

	@KafkaListener(topics = "shop")
	public void listen(Operation operation) {
		if (!operation.getType().equals("article")) {
			return;
		}

		if (!operation.getAction().equals("create")) {
			return;
		}

		prices.put(operation.getObject().get("uuid").asText(), operation.getObject().get("price").decimalValue());
	}
}