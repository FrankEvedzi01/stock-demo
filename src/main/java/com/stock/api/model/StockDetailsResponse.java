package com.stock.api.model;

import java.util.List;

public class StockDetailsResponse {
	
	private String status;
	
	private List<StockDetails> details;

	public StockDetailsResponse() {
		
	}

	public StockDetailsResponse(String status, List<StockDetails> details) {
		
		this.status = status;
		this.details = details;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<StockDetails> getDetails() {
		return details;
	}

	public void setDetails(List<StockDetails> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "StockDetailsResponse [status=" + status + ", details=" + details + "]";
	}
	
	

}
