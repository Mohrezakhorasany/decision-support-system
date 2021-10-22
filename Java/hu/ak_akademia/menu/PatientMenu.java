package hu.ak_akademia.menu;

import java.util.Scanner;

import hu.ak_akademia.dal.dao.PatientDAO;
import hu.ak_akademia.menu.menuoptions.PatientMenuOption;
import hu.ak_akademia.patient.Patient;
import hu.ak_akademia.patient.PatientFactory;
import hu.ak_akademia.ssn.SSN;

public class PatientMenu implements PatientMenuOption {

	private SSN ssn;
	private Scanner scanner;
	private static final String VALID_PATIENT_DATA_CONFIRMATION = "Would you like to modify the patient data (y/n)? ";

	public PatientMenu(Scanner scanner, SSN ssn) {
		this.scanner = scanner;
		this.ssn = ssn;
	}

	@Override
	public Patient getPatient() {
		Patient patient = null;
		String confirmation = "";
		PatientDAO patientDAO = new PatientDAO();
		PatientFactory patientFactory = new PatientFactory(scanner, ssn);
		do {
			patient = patientFactory.createPatient(patient);
			confirmation = confirmPatient(patient);
			if (isValid(confirmation)) {
				if (patient.getSsn()
						.isExistingSsn()) {
					patientDAO.update(patient);
				} else {
					patientDAO.insert(patient);
				}
			}
		} while (!isValid(confirmation));
		return patient;
	}

	private boolean isValid(String answer) {
		return !answer.isBlank() && answer.substring(0, 1)
				.equalsIgnoreCase("n");
	}

	private String confirmPatient(Patient patient) {
		System.out.println(patient);
		System.out.print(VALID_PATIENT_DATA_CONFIRMATION);
		return scanner.nextLine();
	}

	@Override
	public String toString() {
		return "-------- Patient menu --------";
	}
}
