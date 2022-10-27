package com.stock.api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.stock.api.model.StockDetails;
import com.stock.api.model.StockDetailsResponse;
import com.stock.api.utilities.JsonHelper;

@Service
public class StockService {

	@Value("${csv-out-put-dirctory-path}")
	private String CSV_PATH;

	@Value("${csv-out-put-dirctory-path-existing}")
	private String CSV_PATH_EXISTING;
	
	/**
	 * A service call to get filtered stock data.
	 * 
	 * @param stock
	 * @return
	 */

	public StockDetailsResponse getStockData(String stock) {
		StockDetailsResponse response = new StockDetailsResponse();
		List<StockDetails> list =JsonHelper.processCSVFilter(CSV_PATH_EXISTING, stock);
		
		if(list.isEmpty()) {
			response.setStatus(HttpStatus.NO_CONTENT+"");
			return response;
		}
		response.setDetails(list);
		response.setStatus(HttpStatus.FOUND+"");

		return response;
	}

	/**
	 * Bulk upload to a file and return file location.
	 * 
	 * @param details
	 * @return
	 */
	public String bulkUpload(List<StockDetails> details) {
		try {
			String jsonStr = JsonHelper.toJSONList(details);
			String path = JsonHelper.writeToCSVFileFromJson(jsonStr, false, CSV_PATH, "csv");
			return HttpStatus.CREATED + " : File is successfull uploaded to ==> folder " + path;
		} catch (IOException e) {
			return HttpStatus.INTERNAL_SERVER_ERROR + "";
		}
	}

	/**
	 * This service call add data to existing file.
	 * 
	 * @param data
	 * @return
	 */
	public StockDetailsResponse addStockData(StockDetails data) {
		StockDetailsResponse response = new StockDetailsResponse();
		List<StockDetails> dtList = new ArrayList<>();
		dtList.add(data);
		response.setDetails(dtList);
		try {
			String jsonStr = JsonHelper.toJSONList(dtList);
			JsonHelper.getJsonDataToCSVSTRING(jsonStr, CSV_PATH_EXISTING);
			JsonHelper.writeAndAppendToCSV("originalCVSFile", "newString");

			response.setStatus(HttpStatus.CREATED + "");

		} catch (IOException e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR + "");

		}
		return response;
	}

}
