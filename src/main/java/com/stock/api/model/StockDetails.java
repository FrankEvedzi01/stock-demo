package com.stock.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockDetails {
	
	@JsonProperty("quarter")
	private String quarter;
	
	@JsonProperty("stock")
	private String stock;
	
	@JsonProperty("date")
	private String date;
	
	@JsonProperty("open")
	private String open;
	
	@JsonProperty("high")
	private String high;
	
	@JsonProperty("low")
	private String low;
	
	@JsonProperty("close")
	private String close;
	
	@JsonProperty("volume")
	private String volume;
	
	@JsonProperty("percent_change_price")
	private String percentChangePrice;
	
	@JsonProperty("percent_change_volume_over_last_wk")
	private String percentChangeVolumeOverLastWk;
	
	@JsonProperty("previous_weeks_volume")
	private String previousWeeksVolume;	
	
	@JsonProperty("next_weeks_open")
	private String nextWeeksOpen;
	
	@JsonProperty("next_weeks_close")
	private String nextWeeksClose;
	
	@JsonProperty("percent_change_next_weeks_price")
	private String percentChangeNextWeeksPrice;
	
	@JsonProperty("days_to_next_dividend")
	private String daysToNextDividend;	
	
	@JsonProperty("percent_return_next_dividend")
	private String percentReturnNextDividend;
	
	

	public StockDetails(String quarter, String stock, String date, String open, String high, String low, String close,
			String volume, String percentChangePrice, String percentChangeVolumeOverLastWk, String previousWeeksVolume,
			String nextWeeksOpen, String nextWeeksClose, String percentChangeNextWeeksPrice, String daysToNextDividend,
			String percentReturnNextDividend) {
		this.quarter = quarter; //index[0]
		this.stock = stock; //index[1]
		this.date = date; //index[2]
		this.open = open; //index[3]
		this.high = high; //index[4]
		this.low = low; //index[5]
		this.close = close; //index[6]
		this.volume = volume;  //index[7]
		this.percentChangePrice = percentChangePrice; //index[8]
		this.percentChangeVolumeOverLastWk = percentChangeVolumeOverLastWk; //index[9]
		this.previousWeeksVolume = previousWeeksVolume; //index[10]
		this.nextWeeksOpen = nextWeeksOpen; //index[11]
		this.nextWeeksClose = nextWeeksClose; //index[12]
		this.percentChangeNextWeeksPrice = percentChangeNextWeeksPrice; //index[13]
		this.daysToNextDividend = daysToNextDividend; //index[14]
		this.percentReturnNextDividend = percentReturnNextDividend; //index[5]
	}

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getClose() {
		return close;
	}

	public void setClose(String close) {
		this.close = close;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getPercentChangePrice() {
		return percentChangePrice;
	}

	public void setPercentChangePrice(String percentChangePrice) {
		this.percentChangePrice = percentChangePrice;
	}

	public String getPercentChangeVolumeOverLastWk() {
		return percentChangeVolumeOverLastWk;
	}

	public void setPercentChangeVolumeOverLastWk(String percentChangeVolumeOverLastWk) {
		this.percentChangeVolumeOverLastWk = percentChangeVolumeOverLastWk;
	}

	public String getPreviousWeeksVolume() {
		return previousWeeksVolume;
	}

	public void setPreviousWeeksVolume(String previousWeeksVolume) {
		this.previousWeeksVolume = previousWeeksVolume;
	}

	public String getNextWeeksOpen() {
		return nextWeeksOpen;
	}

	public void setNextWeeksOpen(String nextWeeksOpen) {
		this.nextWeeksOpen = nextWeeksOpen;
	}

	public String getNextWeeksClose() {
		return nextWeeksClose;
	}

	public void setNextWeeksClose(String nextWeeksClose) {
		this.nextWeeksClose = nextWeeksClose;
	}

	public String getPercentChangeNextWeeksPrice() {
		return percentChangeNextWeeksPrice;
	}

	public void setPercentChangeNextWeeksPrice(String percentChangeNextWeeksPrice) {
		this.percentChangeNextWeeksPrice = percentChangeNextWeeksPrice;
	}

	public String getDaysToNextDividend() {
		return daysToNextDividend;
	}

	public void setDaysToNextDividend(String daysToNextDividend) {
		this.daysToNextDividend = daysToNextDividend;
	}

	public String getPercentReturnNextDividend() {
		return percentReturnNextDividend;
	}

	public void setPercentReturnNextDividend(String percentReturnNextDividend) {
		this.percentReturnNextDividend = percentReturnNextDividend;
	}

	@Override
	public String toString() {
		return "StockDetails [quarter=" + quarter + ", stock=" + stock + ", date=" + date + ", open=" + open + ", high="
				+ high + ", low=" + low + ", close=" + close + ", volume=" + volume + ", percentChangePrice="
				+ percentChangePrice + ", percentChangeVolumeOverLastWk=" + percentChangeVolumeOverLastWk
				+ ", previousWeeksVolume=" + previousWeeksVolume + ", nextWeeksOpen=" + nextWeeksOpen
				+ ", nextWeeksClose=" + nextWeeksClose + ", percentChangeNextWeeksPrice=" + percentChangeNextWeeksPrice
				+ ", daysToNextDividend=" + daysToNextDividend + ", percentReturnNextDividend="
				+ percentReturnNextDividend + "]";
	}

	
}
