package com.stock.api.utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Builder;
import com.stock.api.model.StockDetails;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class JsonHelper {

	/**
	 * This method take jsonInput file data and converts to delimited csv file.
	 * 
	 * @param jsonArrayString jsonArrayString
	 * @param isJsonFilePath isJsonFilePath
	 * @param csvOutputFilePath csvOutputFilePath
	 * @param ext ext
	 * @return String Object
	 * @throws IOException IOException
	 */
	public static String  writeToCSVFileFromJson(String jsonArrayString, boolean isJsonFilePath, String csvOutputFilePath, String ext)
			throws IOException {
		JsonNode jsonTree = isJsonFilePath ? new ObjectMapper().readTree(new File(jsonArrayString)):
							new ObjectMapper().readTree(jsonArrayString);

		Builder csvSchemaBuilder = CsvSchema.builder();
		JsonNode firstObject = jsonTree.elements().next();
		firstObject.fieldNames().forEachRemaining(csvSchemaBuilder::addColumn);
		CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
		CsvMapper csvMapper = new CsvMapper();
		String createdFilePath = isFilePathExist(csvOutputFilePath, ext);
		csvMapper.writerFor(JsonNode.class).with(csvSchema).writeValue(new File(createdFilePath),
				jsonTree);
		
		return createdFilePath;

	}

	/**
	 * This method get StockDetails object list from json string.
	 *
	 * @param jsonArrayString Json Object
	 * @return String Object
	 * @throws IOException IOException
	 */
	public static List<StockDetails>  jsonToPojo(String jsonArrayString)
			throws IOException {
		List<StockDetails> list = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
		StockDetails details = objectMapper.readValue(jsonArrayString, StockDetails.class);
       list.add(details);
		return list;

	}

	/**
	 * This method is used to create a new file path.
	 * 
	 * @param directoryOutputPath directoryOutputPath
	 * @param ext ext
	 * @return String
	 * @throws IOException IoException
	 */

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public static String isFilePathExist(String directoryOutputPath, String ext) throws IOException {
		UUID uuid = UUID.randomUUID();
		ClassPathResource cp;
		cp = new ClassPathResource(directoryOutputPath);
		String directoryName = cp.getPath();
		String fileName = uuid + "." + ext;

		File directory = new File(directoryName);
		if (!directory.exists()) directory.mkdir();
		File file = new File(directoryName + "/" + fileName);
		if (!file.exists()) {
			file.createNewFile();
		}

		return directoryOutputPath + "/" + fileName;
	}

	/**
	 * This method reads incoming bulk data from user and converts to json string.
	 * 
	 * @return String object
	 */
	public static String toJSONList(List<StockDetails> list) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		return objectMapper.writeValueAsString(list);
	}


	/**
	 * This method appends to the existing csv file
	 * 
	 * @param originalCVSFile originalCVSFile
	 * @param newString testing
	 * @throws  IOException IOException
	 */
	public static void writeAndAppendToCSV(String originalCVSFile, String newString) throws IOException {
		FileWriter fw = new FileWriter(originalCVSFile, true);
	    BufferedWriter bw = new BufferedWriter(fw);
	    bw.write(newString);
	    bw.newLine();
	    bw.close();
	}
	
	/**
	 * This method obtains delimited string from json node
	 * 
	 * @param jsonArrayString jsonArrayString
	 * @param csvOutputFilePath csvOutputFilePath
	 * @return String Object
	 * @throws IOException IOException
	 */
	public static String getJsonDataToCSVString(String jsonArrayString, String csvOutputFilePath)
			throws IOException {
		
		JsonNode jsonTree = new ObjectMapper().readTree(jsonArrayString);
		
		Builder csvSchemaBuilder = CsvSchema.builder();
		JsonNode firstObject = jsonTree.elements().next();
	
		firstObject.fieldNames().forEachRemaining(csvSchemaBuilder::addColumn);
		
		CsvSchema csvSchema  = csvSchemaBuilder.build();
		
		try {
			if (!isAFile(csvOutputFilePath)){
				 csvSchema.withHeader();
			}
		} catch (IOException ignored) {}
		
		CsvMapper csvMapper = new CsvMapper();
		
		return csvMapper.writerFor(JsonNode.class).with(csvSchema).writeValueAsString(jsonTree);

	}

	/**
	 * Check if file exist or empty, if it doesn't create it.
	 * @param fileFullPath fileFullPath
	 * @return boolean
	 * @throws IOException IOException
	 */
	
	public static boolean isAFile(String fileFullPath) throws IOException {
		boolean result = false;
		ClassPathResource cp;
		cp = new ClassPathResource(fileFullPath);
		File f = new File(cp.getPath());
		if (!f.exists()) {
			f.createNewFile();
		} else {
			result = f.length() != 0;
		}

		return result;
	}
	
	
	/**
	 * 
	 * This method filters the record for value passed.
	 * 
	 * @param filePath filePath
	 * @param stockValue stockValue
	 * @return List Object
	 */
	
	public static List<StockDetails> processCSVFilter(String filePath, String stockValue) {
		Path path;
		File f;
		ClassPathResource cp = new ClassPathResource(filePath);
			f = new File(cp.getPath());
			path = Path.of(cp.getPath());
		if (!f.exists()) {
			throw new IllegalStateException("FSystem Error-file could not be processed: " + path);
		}

		List<String> lines;
		try {
			lines = Files.lines(path, StandardCharsets.UTF_8).toList();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		lines = lines.subList(1, lines.size()); // Skip header first line.

		List<StockDetails> stocks = new ArrayList<>();
		for (String line : lines) {
			if (line.trim().length() !=0 ) {
				String[] index = line.split(",");
				if (index[0].trim().length() == 0) {
					continue;
				}
				StockDetails detail = new StockDetails(
						index[0], index[1], index[2], index[3], index[4], index[5], index[6],
						index[7], index[8], index[9], index[10], index[11], index[12], index[13],
						index[14], index[15]);

				stocks.add(detail);
			}
		}
		return stocks.stream().filter(det -> Objects.nonNull(det) && det.getStock().equalsIgnoreCase(stockValue)).toList();
	}
}
