package hu.ak_akademia.menu;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

import hu.ak_akademia.diagnosis.Lab;
import hu.ak_akademia.disease.Disease;
import hu.ak_akademia.menu.menuoptions.DiagnosisMenuOption;
import hu.ak_akademia.symptom.PatientSymptom;

public class DiagnosisMenu implements DiagnosisMenuOption {

	private Scanner scanner;
	private Set<Disease> diseases;

	public DiagnosisMenu(Scanner scanner, Set<Disease> diseases) {
		this.scanner = scanner;
		this.diseases = diseases;
	}

	@Override
	public List<Disease> getDiagnosis() {
		SymptomMenu symptomMenu = new SymptomMenu(scanner);
		symptomMenu.display();
		PatientSymptom symptoms = symptomMenu.getSymptoms();
		System.out.println(symptoms);
		return new Lab(diseases).getDiagnosis(symptoms);
	}
}
