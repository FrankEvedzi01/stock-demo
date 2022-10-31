package com.stock.api.service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.stock.api.model.StatusResponse;
import com.stock.api.model.StockDetails;
import com.stock.api.model.StockDetailsBulkRequest;
import com.stock.api.model.StockDetailsResponse;
import com.stock.api.utilities.JsonHelper;

import static com.stock.api.utilities.JsonHelper.processCSVFilter;

@Service
public class StockService {

	@Value("${csv-out-put-directory-path}")
	private String CSV_PATH_OUT;

	@Value("${csv-out-put-directory-path-existing}")
	private String CSV_PATH_EXISTING;

	@Value("${json-in-put-directory-path}")
	private String CSV_PATH_INPUT;
	
	/**
	 * A service call to get filtered stock data.
	 * 
	 * @param stock
	 * @return
	 */

	public StockDetailsResponse getStockData(String stock) {
		StockDetailsResponse response = new StockDetailsResponse();
		List<StockDetails> list = processCSVFilter(CSV_PATH_EXISTING, stock);
		
		if(list.isEmpty()) {
			response.setStatus(HttpStatus.NO_CONTENT+"");
			return response;
		}
		response.setDetails(list);
		response.setStatus(HttpStatus.FOUND+"");

		return response;
	}

	/**
	 *  Bulk upload to a file and return file location.
	 * @param req
	 * @return
	 */
	public StatusResponse  bulkUpload(StockDetailsBulkRequest req) {
		try {
			String jsonStr = JsonHelper.toJSONList(req.getDetails());
			String path = JsonHelper.writeToCSVFileFromJson(jsonStr, false, CSV_PATH_OUT, "csv");
			
			return new StatusResponse(HttpStatus.CREATED + "", "File is successful uploaded to ==> folder " + path);
		} catch (IOException e) {
			return new StatusResponse(HttpStatus.INTERNAL_SERVER_ERROR + "", e.getMessage());
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
			String csv = JsonHelper.getJsonDataToCSVString(jsonStr, CSV_PATH_EXISTING).toUpperCase();
			JsonHelper.writeAndAppendToCSV(CSV_PATH_EXISTING, csv);

			response.setStatus(HttpStatus.CREATED + "");

		} catch (IOException e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR + "");

		}
		return response;
	}

	/**
	 * This method takes json object as input or takes input filename to local the file in the system to process.
	 *
	 * @param missingLoad Json File path or bulk json string
	 * @return status response
	 */
	public StatusResponse  bulkUploadJson(String  missingLoad) {
		try {
			if(missingLoad.indexOf(".") != 0) {
				ClassPathResource cp;
				cp = new ClassPathResource(CSV_PATH_INPUT + "/" + missingLoad);
				File inputFile = new File(cp.getPath());
				if (inputFile.exists()) {
					String json= String.valueOf(new ObjectMapper().readTree(inputFile));
					List<StockDetails> list = JsonHelper.jsonToPojo(json);
					String jsonStr = JsonHelper.toJSONList(list);
					String csv = JsonHelper.getJsonDataToCSVString(jsonStr, CSV_PATH_EXISTING).toUpperCase();
					JsonHelper.writeAndAppendToCSV(CSV_PATH_EXISTING, csv);
					return new StatusResponse(HttpStatus.CREATED + "", "Data is successful " +
							"uploaded to ==> folder " + CSV_PATH_EXISTING);
				} else {
					return new StatusResponse(HttpStatus.BAD_REQUEST + "", "File is not available," +
							" please check the filename");
				}
			} else{
				return new StatusResponse(HttpStatus.BAD_REQUEST + "",
						"The request parameter is not a file or file directory" );
			}
			} catch (IOException e) {
			return new StatusResponse(HttpStatus.INTERNAL_SERVER_ERROR + "", e.getMessage());
		}
	}
}
