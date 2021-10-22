package hu.ak_akademia.symptom;

import java.util.Scanner;
import java.util.regex.Pattern;

import hu.ak_akademia.dal.dao.SymptomDAO;
import hu.ak_akademia.property.PropertyFileReader;

public class SymptomInputManagement {

	private Scanner scanner;
	private SymptomDAO symptomDAO = new SymptomDAO();
	private static final String SYMPTOM_INPUT_MESSAGE = "Please enter the symptom id: ";

	public SymptomInputManagement(Scanner scanner) {
		this.scanner = scanner;
	}

	public PatientSymptom getPatientSymptoms() {
		return addPatientSymtoms();
	}

	private PatientSymptom addPatientSymtoms() {
		PatientSymptom patientSymptom = new PatientSymptom();
		Integer limit = Integer.parseInt(new PropertyFileReader().readPropertyValue("numberofsymptomslimitenteredbyuser"));
		while (patientSymptom.numberOfPatientSymptoms() < limit) {
			int symptomID = getSymptomID();
			patientSymptom.add(new Symptom(symptomID, symptomDAO.getName(symptomID)));
		}
		return patientSymptom;
	}

	private int getSymptomID() {
		String userGiven;
		do {
			System.out.println(SYMPTOM_INPUT_MESSAGE);
			userGiven = scanner.nextLine();
		} while (!isNumber(userGiven) || !isValidInput(Integer.valueOf(userGiven)));
		return Integer.valueOf(userGiven);
	}

	private boolean isValidInput(int userGiven) {
		return symptomDAO.isIDExist(userGiven);
	}

	private boolean isNumber(String userGiven) {
		return Pattern.compile("-?\\d+(\\.\\d+)?")
				.matcher(userGiven)
				.matches();
	}
}
