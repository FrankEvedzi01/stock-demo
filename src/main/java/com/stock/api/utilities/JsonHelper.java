package com.stock.api.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Builder;
import com.stock.api.model.StockDetails;

public class JsonHelper {
	
	/**
	 *This method reads from the flat file and create Map Objects.
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static List<Map<?, ?>> readObjectDataFromCsv(File file) throws IOException {
		CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();
		CsvMapper csvMapper = new CsvMapper();
		try (MappingIterator<Map<?, ?>> mappingIterator = csvMapper.readerFor(Map.class).with(csvSchema)
				.readValues(file)) {
			return mappingIterator.readAll();
		}
	}

	/**
	 *This method takes map objects as input converts to json and write to system file.
	 *
	 * @param data
	 * @param file
	 * @throws IOException
	 */
	public static void writeDataObjectJson(List<Map<?, ?>> data, File file) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(file, data);
	}

	/**
	 * This method receives to file paths (input and out and write process.
	 * @param inputFilePath
	 * @param outputFilePath
	 * @throws IOException
	 */
	public static void processToJson(String inputFilePath, String outputFilePath) throws IOException {
		File input = new File(inputFilePath);
		File output = new File(outputFilePath);
		List<Map<?, ?>> data = readObjectDataFromCsv(input);
		writeDataObjectJson(data, output);
	}

	/**
	 * This method take jsonnInput file data and converts to delimited csv file.
	 * 
	 * @param jsonArrayString
	 * @param isJsonFilePath
	 * @param csvOutputFilePath
	 * @param ext
	 * @return
	 * @throws IOException
	 */
	public static String  writeToCSVFileFromJson(String jsonArrayString, boolean isJsonFilePath, String csvOutputFilePath, String ext)
			throws IOException {
		JsonNode jsonTree = isJsonFilePath ? new ObjectMapper().readTree(new File(jsonArrayString)):
							new ObjectMapper().readTree(jsonArrayString);
		
		Builder csvSchemaBuilder = CsvSchema.builder();
		JsonNode firstObject = jsonTree.elements().next();
		firstObject.fieldNames().forEachRemaining(fieldName -> {
			csvSchemaBuilder.addColumn(fieldName);
		});
		CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
		CsvMapper csvMapper = new CsvMapper();
		String createdFilePath = isFilePathExist(csvOutputFilePath, ext);
		csvMapper.writerFor(JsonNode.class).with(csvSchema).writeValue(new File(createdFilePath),
				jsonTree);
		
		return createdFilePath;

	}

	/**
	 * This method take csv input  file data and converts to json file.
	 * 
	 * @param csvInputFilePath
	 * @param jsonOutputFilePath
	 * @throws IOException
	 */
	public static void writeToJSONFromDelimitedFlatFile(String csvInputFilePath, String jsonOutputFilePath)
			throws IOException {
		CsvSchema stockSchema = CsvSchema.emptySchema().withHeader();
		CsvMapper csvMapper = new CsvMapper();
		MappingIterator<StockDetails> stockList = csvMapper.readerFor(StockDetails.class).with(stockSchema)
				.readValues(new File(csvInputFilePath));

		new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true).writeValue(new File(jsonOutputFilePath),
				stockList.readAll());
	}

	/**
	 * This method is used to create a new file path if doesn't exist. A get a new file name for
	 * data bulk upload.
	 * 
	 * @param directoryOutputPath
	 * @param ext
	 * @return
	 * @throws IOException 
	 */
	public static String isFilePathExist(String directoryOutputPath, String ext) throws IOException {
		UUID uuid = UUID.randomUUID();
		String directoryName = directoryOutputPath;
		String fileName = uuid + "." + ext;

		File directory = new File(directoryName);
		if (!directory.exists()) {
			directory.mkdir();
		}

		File file = new File(directoryName + "/" + fileName);
		if (!file.exists()) {
			file.createNewFile();
		}

		return directoryOutputPath + "/" + fileName;
	}

	/**
	 * This method reads incoming bulk data from user and converts to json string.
	 * 
	 * @param list
	 * @return
	 * @throws IOException
	 */
	public static String toJSONList(List<StockDetails> list) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		return objectMapper.writeValueAsString(list);
	}

	/**
	 * This method reads incoming json data to obtain POJO Object list. 
	 * 
	 * @param arrayToJson
	 * @return
	 * @throws IOException
	 */
	public static List<StockDetails> toStockListObject(String arrayToJson) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		TypeReference<List<StockDetails>> mapType = new TypeReference<List<StockDetails>>() {
		};
		return objectMapper.readValue(arrayToJson, mapType);
	}
	/**
	 * This method appends to the existing csv file
	 * 
	 * @param originalCVSFile
	 * @param newString
	 * @throws IOException
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
	 * @param jsonArrayString
	 * @param csvOutputFilePath
	 * @return
	 * @throws IOException
	 */
	public static String  getJsonDataToCSVSTRING(String jsonArrayString, String csvOutputFilePath)
			throws IOException {
		
		JsonNode jsonTree = new ObjectMapper().readTree(jsonArrayString);
		
		Builder csvSchemaBuilder = CsvSchema.builder();
		JsonNode firstObject = jsonTree.elements().next();
	
		firstObject.fieldNames().forEachRemaining(fieldName -> {
			  csvSchemaBuilder.addColumn(fieldName);
		});
		
		CsvSchema csvSchema  = csvSchemaBuilder.build();
		
		try {
			if (!isAFile(csvOutputFilePath)){
				 csvSchema.withHeader();
			}
		} catch (IOException e) {}
		
		CsvMapper csvMapper = new CsvMapper();
		
		return csvMapper.writerFor(JsonNode.class).with(csvSchema).writeValueAsString(jsonTree);

	}
	
	/**
	 * Check if file exist or empty, if doesn't create it.
	 * 
	 * @param fileFullPath
	 * @return
	 * @throws IOException
	 */
	
	public static boolean isAFile(String fileFullPath) throws IOException {
		File f = new File(fileFullPath);
		if (!f.exists()) {
			f.createNewFile();
			return false;
		}

		return f.length() == 0 ? false : true;
	}
	
	
	/**
	 * 
	 * This method filters the record for value passed.
	 * 
	 * @param filePath
	 * @param stockValue
	 * @return
	 */
	
	public static List<StockDetails> processCSVFilter(String filePath, String stockValue) {
		Path path = Paths.get(filePath);
		if (Files.notExists(path)) {
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
