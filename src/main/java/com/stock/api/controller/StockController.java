package com.stock.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stock.api.model.StatusResponse;
import com.stock.api.model.StockDetails;
import com.stock.api.model.StockDetailsBulkRequest;
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
	public StatusResponse  bulkUpload(@RequestBody StockDetailsBulkRequest request) {
		StatusResponse  response = new StatusResponse();
		if (null == request || request.getDetails().isEmpty() || request.getDetails().size()==0 ) {
			response.setErrorMessage("Incoming Data is empty, please check your data.");
			response.setStatus(HttpStatus.BAD_REQUEST+"");
			
			return response;
		}
		return stockService.bulkUpload(request);

	}
	@PostMapping("/bulk-insert/bulk/{missingLoad}")
	public StatusResponse  bulkUploadJson(@PathVariable String  missingLoad) {
		StatusResponse  response = new StatusResponse();
		if (null == missingLoad ) {
			response.setErrorMessage("Incoming data is invalid, please check your data.");
			response.setStatus(HttpStatus.BAD_REQUEST+"");
			return response;
		}
		return stockService.bulkUploadJson(missingLoad);

	}
	@PostMapping("/")
	public  StockDetailsResponse  addStockData(@RequestBody StockDetails data) {
		return stockService.addStockData(data);
	}


}
