package de.predic8.workshop.checkout.service;

import de.predic8.workshop.checkout.dto.Basket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CheckoutService {
	private static final Logger log = LoggerFactory.getLogger(CheckoutService.class);

	private final RestTemplate restTemplate;

	public CheckoutService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public boolean areArticlesAvailable(Basket basket) {
		return true;
	}
}
