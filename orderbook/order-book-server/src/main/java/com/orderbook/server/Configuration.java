package com.orderbook.server;

import com.orderbook.core.InMemoryOrdersRepository;
import com.orderbook.core.OrderBookRepositoryFactory;
import com.orderbook.core.OrderBooks;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

	@Bean
	OrderBookRepositoryFactory orderBookRepositoryFactory() {
		return new InMemoryOrdersRepository();
	}

	@Bean
	OrderBooks orderBooks(OrderBookRepositoryFactory orderBookRepositoryFactory) {
		return new OrderBooks(orderBookRepositoryFactory);
	}
}
