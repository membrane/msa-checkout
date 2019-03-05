package de.predic8.checkout.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.predic8.checkout.model.Basket;
import de.predic8.checkout.model.Item;
import de.predic8.checkout.model.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CheckoutService {

	private static final Logger log = LoggerFactory.getLogger(CheckoutService.class);

	private final RestTemplate rest;

	public CheckoutService(RestTemplate rest) {
		this.rest = rest;
	}

	@HystrixCommand(fallbackMethod = "getDefault")
	public boolean areArticlesAvailable(Basket basket) {

		return basket.getItems().stream().allMatch( this::enough);
	}

	protected boolean enough(Item item) {
		return getStock(item).getQuantity() >= item.getQuantity();
	}

	private Stock getStock(Item item) {
		return rest.getForObject(
				"http://stock/stocks/" + item.getArticleId(), Stock.class);
	}

	private boolean getDefault(Basket basket, Throwable t) {
		return true;
	}
}
