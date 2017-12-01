package de.predic8.workshop.checkout.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.predic8.workshop.checkout.dto.Basket;
import de.predic8.workshop.checkout.dto.BasketIdentifier;
import de.predic8.workshop.checkout.dto.Stock;
import de.predic8.workshop.checkout.event.Operation;
import de.predic8.workshop.checkout.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CheckoutRestController {
	private final CheckoutService checkoutService;
	private final KafkaTemplate<String, Operation> kafkaTemplate;
	private final ObjectMapper objectMapper;
	private final Map<String, BigDecimal> prices;

	@PostMapping("/checkouts")
	public ResponseEntity<?> save(@RequestBody Basket basket) {
		if (!checkoutService.areArticlesAvailable(basket)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

		log.info("All articles available");

		String uuid = UUID.randomUUID().toString();
		basket.setUuid(uuid);
		basket.getItems().forEach(i -> i.setPrice(prices.get(i.getArticle())));

		kafkaTemplate.send("shop", new Operation("basket", "create", objectMapper.valueToTree(basket)));

		return ResponseEntity
			.accepted()
			.body(new BasketIdentifier(uuid));
	}
}