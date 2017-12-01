package de.predic8.workshop.checkout.service;

import de.predic8.workshop.checkout.dto.Basket;
import de.predic8.workshop.checkout.dto.Stock;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CheckoutService {
	private final RestTemplate restTemplate;

	public CheckoutService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public boolean areArticlesAvailable(Basket basket) {
		return basket.getItems().stream().allMatch(item -> {
				Stock stock = restTemplate.getForObject("http://localhost:8081/stocks/{uuid}", Stock.class, item.getArticle());

				return stock.getQuantity() >= item.getQuantity();
			}
		);
	}

	public boolean areArticlesAvailableFallback(Basket basket) {
		return false;
	}
}
