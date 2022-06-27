package com.stocks.estockmarketstock.service;

import java.util.List;

import com.estockmarket.stocks.document.Stock;
import com.estockmarket.stocks.document.StockDetails;
import com.estockmarket.stocks.dto.StockDto;

public interface StockService {

	StockDto getStock(Integer companyCode);

	void deleteStock(StockDto stock);

	List<StockDto> getAllStock();

	void deleteAllStocks();
	
	void deleteAllCustomSequence();
	
	String addCompanyNewStock(Integer companyCode, Stock stock);
}
