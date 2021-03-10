package com.orderbook.server;

import com.orderbook.model.OrderBookEntry;
import com.orderbook.model.OrderBookSnapshot;
import com.orderbook.model.OrderSide;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookApplicationTest {

	@LocalServerPort
	private int                  serverPort;
	@Autowired
	private OrderBookApplication orderBookApplication;
	@Autowired
	private TestRestTemplate     restTemplate;

	@Test
	public void whenAddingRecordCanGetSnapShot() {
		OrderBookEntry orderBookEntry = OrderBookEntry.builder()
				.exchange("exchange")
				.symbol("symbol")
				.price(10d)
				.quantity(11d)
				.side(OrderSide.BUY)
				.build();

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + serverPort + "/orderbook/update", orderBookEntry, String.class);
		assertThat(response.getStatusCode().value()).isEqualTo(200);

		ResponseEntity<OrderBookSnapshot> getResponse = restTemplate
				.getForEntity("http://localhost:" + serverPort + "/orderbook/fetch?exchange=exchange&symbol=symbol", OrderBookSnapshot.class);

		assertThat(getResponse.getStatusCode().value()).isEqualTo(200);
		assertThat(getResponse.getBody()).isNotNull();
		assertThat(getResponse.getBody().getBids().size()).isEqualTo(1);
		assertThat(getResponse.getBody().getBids().get(0).getPrice()).isEqualTo(10);
		assertThat(getResponse.getBody().getBids().get(0).getQuantity()).isEqualTo(11);

	}

	@Test
	public void whenNoSnapshotReturn404() {

		ResponseEntity<OrderBookSnapshot> getResponse = restTemplate
				.getForEntity("http://localhost:" + serverPort + "/orderbook/fetch?exchange=1&symbol=1", OrderBookSnapshot.class);

		assertThat(getResponse.getStatusCode().value()).isEqualTo(404);

	}

}
