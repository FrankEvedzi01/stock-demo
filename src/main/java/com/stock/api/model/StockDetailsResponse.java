package com.stock.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockDetailsResponse {
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("errorMessage")
	private String errorMessage;
	
	@JsonProperty("details")
	private List<StockDetails> details;

	public StockDetailsResponse() {
		
	}
	

	public StockDetailsResponse(String status, String errorMessage, List<StockDetails> details) {
		super();
		this.status = status;
		this.errorMessage = errorMessage;
		this.details = details;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErroMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<StockDetails> getDetails() {
		return details;
	}

	public void setDetails(List<StockDetails> details) {
		this.details = details;
	}


	@Override
	public String toString() {
		return "StockDetailsResponse [status=" + status + ", errorMessage=" + errorMessage + ", details=" + details + "]";
	}

	
}
