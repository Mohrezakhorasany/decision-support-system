package hu.ak_akademia.menu;

import java.util.Scanner;
import java.util.Set;

import hu.ak_akademia.disease.Disease;
import hu.ak_akademia.disease.DiseaseObjectUtils;
import hu.ak_akademia.patient.Patient;
import hu.ak_akademia.ssn.SSNInputManagement;

public class Menu {

	private Scanner scanner;

	public Menu(Scanner scanner) {
		this.scanner = scanner;
	}

	public void run() {
		Set<Disease> diseases = new DiseaseObjectUtils().getDiseaseObjects();
		PatientMenu patientMenu = new PatientMenu(scanner, new SSNInputManagement(scanner).getSSN());
		System.out.println(patientMenu);
		Patient patient = patientMenu.getPatient();
		new HistoryMenu(new DiagnosisMenu(scanner, diseases).getDiagnosis(), scanner, patient).askUserForValidation();
	}
}
