package com.stocks.estockmarketstock.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.estockmarket.stocks.document.Stock;
import com.estockmarket.stocks.document.StockDetails;
import com.stocks.estockmarketstock.repository.StockDetailsRepository;

@Service
public class StockDetailsServiceImpl implements StockDetailsService {
	
	@Autowired
	StockDetailsRepository stockDetailsRepository;
	
	@Autowired
	MongoTemplate mongotemplate;

	@Override
	public List<StockDetails> findByStockPriceDttm(Integer companyCode, Date startDate, Date endDate) {
			StockDetails stockdetails = new StockDetails();
			Criteria criteria = Criteria.where("addedTime").gte(startDate)
					.andOperator(Criteria.where("addedTime").lte(endDate));
			criteria = criteria.and("companyCode").is(companyCode);
			Query query = new Query(criteria);
			List<Stock> stockList = mongotemplate.find(query, Stock.class);
			if (!stockList.isEmpty()){				
				Optional<Stock> minStockPrice = stockList.stream().min(Comparator.comparing(Stock::getPrice));
				Optional<Stock> maxStockPrice = stockList.stream().max(Comparator.comparing(Stock::getPrice));
				double avg = stockList.stream().mapToDouble(stock -> stock.getPrice().doubleValue()).average()
						.getAsDouble();
				stockdetails.setAvgPrice(avg);
				if (minStockPrice.isPresent()) {
					stockdetails.setMinPrice(minStockPrice.get().getPrice());
				}
				if (maxStockPrice.isPresent()) {
					stockdetails.setMaxPrice(maxStockPrice.get().getPrice());
				}
	  }else {
		  return List.of();
	  }
			stockdetails.setStockData(stockList);
			return Arrays.asList(stockdetails);
		}
	
}
