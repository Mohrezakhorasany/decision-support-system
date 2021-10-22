package hu.ak_akademia.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileReader {

	private static final int COLUMN_ONE = 0;
	private static final int COLUMN_TWO = 1;
	private static final int COLUMN_THREE = 2;
	private static final int SHEET_NUMBER = 0;

	private static final String FILE_PATH = "resources/disease_symptom list.xlsx";

	public Set<String> readOneColumn(int columnNumber) {
		Set<String> columnEntries = new LinkedHashSet<>();
		try (FileInputStream file = new FileInputStream(new File(FILE_PATH)); XSSFWorkbook workbooknew = new XSSFWorkbook(file)) {
			XSSFSheet sheet = workbooknew.getSheetAt(SHEET_NUMBER);
			for (Row row : sheet) {
				columnEntries.add(getCellAsString(row.getCell(columnNumber)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return columnEntries;
	}

	public Map<String, Double> readColumnsBasedOnGivenValue(String columnValue) {
		Map<String, Double> colkeycolVal = new HashedMap<>();
		try (FileInputStream file = new FileInputStream(new File(FILE_PATH)); XSSFWorkbook workbooknew = new XSSFWorkbook(file)) {
			XSSFSheet sheet = workbooknew.getSheetAt(SHEET_NUMBER);
			for (Row row : sheet) {
				if (getCellAsString(row.getCell(COLUMN_TWO)).equals(columnValue)) {
					colkeycolVal.put(getCellAsString(row.getCell(COLUMN_ONE)), getCellAsNumber(row.getCell(COLUMN_THREE)));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return colkeycolVal;
	}

	public FileData readAllData() {
		Map<String, Map<String, Double>> allData = new HashMap<>();
		try (FileInputStream file = new FileInputStream(new File(FILE_PATH)); XSSFWorkbook workbooknew = new XSSFWorkbook(file)) {
			XSSFSheet sheet = workbooknew.getSheetAt(SHEET_NUMBER);
			for (Row row : sheet) {
				if (allData.containsKey(getCellAsString(row.getCell(COLUMN_TWO)))) {
					allData.get(getCellAsString(row.getCell(COLUMN_TWO)))
							.put(getCellAsString(row.getCell(COLUMN_ONE)), getCellAsNumber(row.getCell(COLUMN_THREE)));
				} else {
					allData = putWhenAbsent(allData, row);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new FileData(allData);
	}

	private Map<String, Map<String, Double>> putWhenAbsent(Map<String, Map<String, Double>> allData, Row row) {
		Map<String, Double> symptomsScoreMap = new HashMap<>();
		symptomsScoreMap.put(getCellAsString(row.getCell(COLUMN_ONE)), getCellAsNumber(row.getCell(COLUMN_THREE)));
		allData.put(getCellAsString(row.getCell(COLUMN_TWO)), symptomsScoreMap);
		return allData;
	}

	private double getCellAsNumber(Cell cell) {
		return cell.getNumericCellValue();
	}

	private String getCellAsString(Cell cell) {
		return cell.getStringCellValue();
	}
}
