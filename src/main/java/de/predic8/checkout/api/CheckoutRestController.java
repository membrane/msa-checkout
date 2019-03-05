package de.predic8.checkout.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.predic8.checkout.model.Basket;
import de.predic8.checkout.error.NoPriceException;
import de.predic8.checkout.event.Operation;
import de.predic8.checkout.model.BasketIdentifier;
import de.predic8.checkout.service.CheckoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static java.util.UUID.randomUUID;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.ResponseEntity.accepted;
import static org.springframework.http.ResponseEntity.status;

@RestController
public class CheckoutRestController {

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
	public ResponseEntity<BasketIdentifier> save(@RequestBody Basket basket) throws Exception {

		if (!checkoutService.areArticlesAvailable(basket)) {
			return status( CONFLICT).build();
		}

		String uuid = randomUUID().toString();

		basket.setUuid(uuid);

		basket.getItems().forEach(i -> {
			BigDecimal price = prices.get(i.getArticleId());
			if (price == null) throw new NoPriceException();
			i.setPrice(price);
		});

		Operation op = new Operation("basket", "create", mapper.valueToTree(basket));

		op.logSend();

		kafka.send("shop", op).get(100, TimeUnit.MILLISECONDS);

		return accepted()
			.body(new BasketIdentifier(uuid));
	}
}