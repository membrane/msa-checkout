package de.predic8.workshop.checkout.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.predic8.workshop.checkout.dto.Basket;
import de.predic8.workshop.checkout.dto.BasketIdentifier;
import de.predic8.workshop.checkout.event.Operation;
import de.predic8.workshop.checkout.service.CheckoutService;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RestController
public class CheckoutRestController {
	private final CheckoutService checkoutService;
	private final KafkaTemplate<String, Operation> kafkaTemplate;
	private final ObjectMapper objectMapper;
	private final Map<String, BigDecimal> prices;

	public CheckoutRestController(CheckoutService checkoutService, KafkaTemplate<String, Operation> kafkaTemplate, ObjectMapper objectMapper, Map<String, BigDecimal> prices) {
		this.checkoutService = checkoutService;
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
		this.prices = prices;
	}

	@PostMapping("/checkouts")
	public ResponseEntity<?> save(@RequestBody Basket basket) {
		if (!checkoutService.areArticlesAvailable(basket)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

		String uuid = UUID.randomUUID().toString();
		basket.setUuid(uuid);
		basket.getItems().forEach(i -> i.setPrice(prices.get(i.getArticle())));

		kafkaTemplate.send("shop", new Operation("basket", "create", objectMapper.valueToTree(basket)));

		return ResponseEntity
			.accepted()
			.body(new BasketIdentifier(uuid));
	}
}