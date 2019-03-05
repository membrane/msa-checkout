package de.predic8.checkout.service;

import de.predic8.checkout.model.Item;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.ExpectedCount.manyTimes;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class CheckoutServiceTest {


    CheckoutService cs;
    MockRestServiceServer ms;

    @Before
    public void setUp() {

        RestTemplate rest = new RestTemplate();
        ms = MockRestServiceServer.bindTo(rest).build();

        ms.expect( manyTimes(),
                requestTo("http://localhost:8081/stocks/42")).andExpect(method(GET))
                .andRespond( withSuccess("{ \"uuid\" : \"42\", \"quantity\" : 20}", APPLICATION_JSON));

        cs = new CheckoutService(rest);
    }

    @Test
    public void enough() {

        Item i = new Item();
        i.setQuantity(10);
        i.setArticleId("42");

        assertTrue(cs.enough(i),"Compute enough stock");

        ms.verify();
    }
}