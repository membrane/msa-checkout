package de.predic8.workshop.checkout.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.predic8.workshop.checkout.dto.Basket;
import de.predic8.workshop.checkout.event.Operation;
import de.predic8.workshop.checkout.service.CheckoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
public class CheckoutRestController {

	private final Logger log = LoggerFactory.getLogger(CheckoutRestController.class);

	private final CheckoutService checkoutService;
	private final KafkaTemplate<String, Operation> kafka;
	private final ObjectMapper mapper;
	private final Map<String, BigDecimal> prices;

	public CheckoutRestController(CheckoutService checkoutService, KafkaTemplate<String, Operation> kafkaTemplate, ObjectMapper objectMapper, Map<String, BigDecimal> prices) {
		this.checkoutService = checkoutService;
		this.kafka = kafkaTemplate;
		this.mapper = objectMapper;
		this.prices = prices;
	}

	@PostMapping("/checkouts")
	public ResponseEntity<?> save(@RequestBody Basket basket) throws InterruptedException, ExecutionException, TimeoutException {

		if (!checkoutService.areArticlesAvailable(basket)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

		String uuid = UUID.randomUUID().toString();
		basket.setUuid(uuid);
		basket.getItems().forEach(i -> i.setPrice(prices.get(i.getArticle())));

		Operation op = new Operation("basket", "create", mapper.valueToTree(basket));

		log.info("Sending: " + op);

		kafka.send("shop", op).get(100, TimeUnit.MILLISECONDS);

		return ResponseEntity
			.accepted()
			.body(basket);
	}
}