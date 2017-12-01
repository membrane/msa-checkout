package de.predic8.workshop.checkout.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.predic8.workshop.checkout.dto.Basket;
import de.predic8.workshop.checkout.dto.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class CheckoutService {
	private final RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "areArticlesAvailableFallback")
	public boolean areArticlesAvailable(Basket basket) {
		return basket.getItems().stream().allMatch(item -> {
				Stock stock = restTemplate.getForObject("http://stock-service/stocks/{uuid}", Stock.class, item.getArticle());

				return stock.getQuantity() >= item.getQuantity();
			}
		);
	}

	public boolean areArticlesAvailableFallback(Basket basket) {
		return false;
	}
}
