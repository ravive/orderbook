package com.orderbook.core;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.orderbook.model.OrderBookEntry;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class OrderBookResult {

	public static void main(String[] args) throws IOException {
		Resource resource = new ClassPathResource("orderBookEntries.csv");
		try (Reader reader = new InputStreamReader(resource.getInputStream())) {
			OrderBook orderBook = new OrderBook(new InMemoryOrdersRepository());

			new TransactionCsvReader(reader)
					.read()
					.forEach(orderBookEntry -> orderBook.execute(Order.from(orderBookEntry)));

			System.out.println(orderBook.toString());
		}
	}

	private static class TransactionCsvReader {

		private Reader reader;

		public TransactionCsvReader(Reader reader) {
			this.reader = reader;
		}

		public List<OrderBookEntry> read()  {

			CsvToBean<OrderBookEntry> csvToBean = new CsvToBeanBuilder(reader)
					.withType(OrderBookEntry.class)
					.withIgnoreLeadingWhiteSpace(true)
					.build();

			return TransactionCsvReader.StreamUtils.asStream(csvToBean.iterator())
					.collect(Collectors.toList());

		}

		public static class StreamUtils {

			public static <T> Stream<T> asStream(Iterator<T> sourceIterator) {
				return asStream(sourceIterator, false);
			}

			public static <T> Stream<T> asStream(Iterator<T> sourceIterator, boolean parallel) {
				Iterable<T> iterable = () -> sourceIterator;
				return StreamSupport.stream(iterable.spliterator(), parallel);
			}
		}
	}
}
