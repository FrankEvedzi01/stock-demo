package com.stock.api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stock.api.model.StockDetails;
import com.stock.api.model.StockDetailsResponse;
import com.stock.api.service.StockService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;



@RestController
@RequestMapping("/api/stock-data")
public class StockController {
	
	@Autowired
	private StockService stockService;

	@GetMapping("/{stock}")
	public StockDetailsResponse getStockData(@PathVariable("stock") String stock) {
		
		return stockService.getStockData(stock) ;
	}  
	
	@PostMapping("/bulk-insert")
	public String bulkUpload(@RequestBody @JsonFormat(with=JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY) List<StockDetails> details) {	
		return stockService.bulkUpload(details);

	}
	
	@PostMapping("/")
	public  StockDetailsResponse  addStockData(@RequestBody StockDetails data) {
		return stockService.addStockData(data);
	}


}
