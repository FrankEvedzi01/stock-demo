package com.stock.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockDetailsBulkRequest {
	
	@JsonProperty("details")
	private List<StockDetails> details;
	

	public StockDetailsBulkRequest() {
		
	}

	public List<StockDetails> getDetails() {
		return details;
	}

	public void setDetails(List<StockDetails> details) {
		this.details = details;
	}

	public StockDetailsBulkRequest(List<StockDetails> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "StockDetailsBulkRequest [details=" + details + "]";
	}
	
	

}
