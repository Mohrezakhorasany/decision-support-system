package hu.ak_akademia.menu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import hu.ak_akademia.dal.dao.SymptomDAO;
import hu.ak_akademia.menu.menuoptions.SymptomMenuOption;
import hu.ak_akademia.symptom.PatientSymptom;
import hu.ak_akademia.symptom.SymptomInputManagement;

public class SymptomMenu implements SymptomMenuOption {

	private Scanner scanner;

	public SymptomMenu(Scanner scanner) {
		this.scanner = scanner;
	}

	@Override
	public PatientSymptom getSymptoms() {
		return new SymptomInputManagement(scanner).getPatientSymptoms();
	}

	@Override
	public void display() {
		SymptomDAO symptomDAO = new SymptomDAO();
		ResultSet resultSet = symptomDAO.getAllColumns();
		Map<String, String> symptomIdMap = createAlpahabaizedOrderedMapFromResultSet(resultSet);
		for (Map.Entry<String, String> symptomKeyIdPair : symptomIdMap.entrySet()) {
			System.out.println(symptomKeyIdPair.getValue() + " - " + symptomKeyIdPair.getKey());
		}
	}

	private Map<String, String> createAlpahabaizedOrderedMapFromResultSet(ResultSet resultSet) {
		Map<String, String> idSymptomPair = new TreeMap<>();
		try {
			while (resultSet.next()) {
				idSymptomPair.put(resultSet.getString("name"), resultSet.getString("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idSymptomPair;
	}
}
