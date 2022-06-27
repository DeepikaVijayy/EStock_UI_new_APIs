package com.stocks.estockmarketstock.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.estockmarket.company.dto.CompanyDto;
import com.estockmarket.stocks.document.Stock;
import com.estockmarket.stocks.document.StockDetails;
import com.estockmarket.stocks.dto.StockDto;
import com.estockmarket.utils.Constants;
import com.stocks.estockmarketstock.repository.CustomSequencesRepo;
import com.stocks.estockmarketstock.repository.StockRepository;

@Service
public class SrockServiceImpl implements StockService {

	@Autowired
	StockRepository stockRepository;
	
	@Autowired
	CustomSequencesRepo customSequencesRepo;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${company.url}")
	private String companyUrl;
	
	@Autowired
	NextSequenceService nextSequenceService;
	
	@Override
	public String addCompanyNewStock(Integer companyCode, Stock stock) {
		stock.setCompanyCode(companyCode);
	    stockRepository.save(stock);
		return null;
	}


	@Override
	public StockDto getStock(Integer companyCode) {
		StockDto stockDto=new StockDto();
		Stock stock=stockRepository.findByCompanyCode(companyCode);
		if(stock != null) {
			BeanUtils.copyProperties(stock, stockDto);
		}
		return stockDto;
	}

	@Override
	public void deleteStock(StockDto stock) {
		stockRepository.deleteById(stock.getId());
	}

	@Override
	public List<StockDto> getAllStock() {
		List<Stock> stockList=stockRepository.findAll();
		List<StockDto> stockDtoList=new ArrayList<StockDto>();
		if(!stockList.isEmpty()) {
			stockList.stream().forEach(s -> {
				StockDto stockDto=new StockDto();
				BeanUtils.copyProperties(s, stockDto);
				stockDtoList.add(stockDto);
			});
		}
	
		return stockDtoList;
	}

	@Override
	public void deleteAllStocks() {
		stockRepository.deleteAll();
	}

	@Override
	public void deleteAllCustomSequence() {
		customSequencesRepo.deleteAll();
	}


}
