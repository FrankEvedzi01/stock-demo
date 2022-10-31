package com.stock.api.controller;


import com.stock.api.model.StatusResponse;
import com.stock.api.model.StockDetails;
import com.stock.api.model.StockDetailsBulkRequest;
import com.stock.api.model.StockDetailsResponse;
import com.stock.api.service.StockService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/stock-data")
public class StockController {
	
	private final StockService stockService;

	public StockController(StockService stockService) {
		this.stockService = stockService;
	}

	@GetMapping(value = "/{stock}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public StockDetailsResponse getStockData(@PathVariable("stock") String stock) {
		
		return stockService.getStockData(stock) ;
	}  
	
	@PostMapping(value="/bulk-insert", produces = {MediaType.APPLICATION_JSON_VALUE})
	public StatusResponse  bulkUpload(@RequestBody StockDetailsBulkRequest request) {
		StatusResponse  response = new StatusResponse();
		if (null == request || request.getDetails().isEmpty() || request.getDetails().size()==0 ) {
			response.setErrorMessage("Incoming Data is empty, please check your data.");
			response.setStatus(HttpStatus.BAD_REQUEST+"");
			
			return response;
		}
		return stockService.bulkUpload(request);

	}
	@PostMapping(value="/bulk-insert/bulk/{missingLoad}" , produces = {MediaType.APPLICATION_JSON_VALUE})
	public StatusResponse  bulkUploadJson(@PathVariable String  missingLoad) {
		StatusResponse  response = new StatusResponse();
		if (null == missingLoad ) {
			response.setErrorMessage("Incoming data is invalid, please check your data.");
			response.setStatus(HttpStatus.BAD_REQUEST+"");
			return response;
		}
		return stockService.bulkUploadJson(missingLoad);

	}
	@PostMapping(value="/" , produces = {MediaType.APPLICATION_JSON_VALUE})
	public  StockDetailsResponse  addStockData(@RequestBody StockDetails data) {
		return stockService.addStockData(data);
	}


}
