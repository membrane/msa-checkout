package de.predic8.workshop.checkout.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.predic8.workshop.checkout.dto.Basket;
import de.predic8.workshop.checkout.dto.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static net.logstash.logback.marker.Markers.appendEntries;

@Service
public class CheckoutService {

	private final Logger log = LoggerFactory.getLogger(CheckoutService.class);


	private final RestTemplate rest;

	public CheckoutService(RestTemplate rest) {
		this.rest = rest;
	}

	@HystrixCommand(fallbackMethod = "areArticlesAvailableFallback")
	public boolean areArticlesAvailable(Basket basket) {

		return basket.getItems().stream().allMatch(item -> {
				Stock stock = rest.getForObject("http://stock/stocks/{uuid}", Stock.class, item.getArticleId());
				return stock.getQuantity() >= item.getQuantity();
			}
		);
	}

	public boolean areArticlesAvailableFallback(Basket basket) {

		Map<String,Object> entries = new HashMap();
		entries.put("fallback", "Sending Basket: " + basket);

		log.error(appendEntries(entries),"");

		return true;
	}
}
