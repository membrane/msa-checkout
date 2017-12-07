package de.predic8.workshop.checkout.event;

import de.predic8.workshop.checkout.rest.CheckoutRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class ShopListener {

	private final Logger log = LoggerFactory.getLogger(CheckoutRestController.class);

	private final Map<String, BigDecimal> prices;

	public ShopListener(Map<String, BigDecimal> prices) {
		this.prices = prices;
	}

	@KafkaListener(topics = "shop")
	public void listen(Operation op) {

		if (!op.getBo().equals("article"))
			return;

		log.info("Receiving: " + op);

		switch (op.getAction()) {
			case "create":
			case "update":
				prices.put(op.getObject().get("uuid").asText(), op.getObject().get("price").decimalValue());
				break;
			case "delete":
		}


	}
}