package com.stock.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatusResponse {
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("errorMessage")
	private String errorMessage;

	public StatusResponse() {
		
	}

	public StatusResponse(String status, String errorMessage) {
		
		this.status = status;
		this.errorMessage = errorMessage;
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

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "StatusResponse [status=" + status + ", errorMessage=" + errorMessage + "]";
	}

}
