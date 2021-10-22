package hu.ak_akademia.menu;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import hu.ak_akademia.dal.dao.HistoryDAO;
import hu.ak_akademia.disease.Disease;
import hu.ak_akademia.menu.menuoptions.HistoryMenuOption;
import hu.ak_akademia.patient.Patient;

public class HistoryMenu implements HistoryMenuOption {

	private List<Disease> diseaseList;
	private Scanner scanner;
	private Patient patient;

	public HistoryMenu(List<Disease> diagnosis, Scanner scanner, Patient patient) {
		this.diseaseList = diagnosis;
		this.scanner = scanner;
		this.patient = patient;
	}

	@Override
	public void askUserForValidation() {
		if (diseaseList.isEmpty()) {
			System.out.println("No disease have been found.");
			return;
		}
		int userGivenDiseaseId = getUserGivenDiseaseId();
		if (userGivenDiseaseId != 0) {
			HistoryDAO historyDAO = new HistoryDAO();
			historyDAO.insert(patient.getSsn(), diseaseList.get(userGivenDiseaseId - 1));
			System.out.println("Disease with id " + userGivenDiseaseId + " has been recorded.");
		} else {
			System.out.println("No disease will be recorded.");
		}
	}

	private int getUserGivenDiseaseId() {
		String diseaseID;
		do {
			for (Disease disease : diseaseList) {
				System.out.println(diseaseList.indexOf(disease) + 1 + " - " + disease);
			}
			System.out.print("""
					Which disease id you would like to add to the patient medical history? (e.g. 1,2,..)\s
					Note: By entering 0 no disease will be added to the patient medical history.\s
					Your choice: \s""");
			diseaseID = scanner.next();
			scanner.nextLine();
		} while (!isValid(diseaseID));
		return Integer.parseInt(diseaseID);
	}

	private boolean isValid(String answer) {
		return !answer.isBlank() && Pattern.compile("-?\\d+(\\.\\d+)?")
				.matcher(answer)
				.matches() && (Integer.parseInt(answer) <= diseaseList.size()) && (Integer.parseInt(answer) >= 0);
	}
}
