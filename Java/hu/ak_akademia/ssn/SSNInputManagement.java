package hu.ak_akademia.ssn;

import java.util.Scanner;
import java.util.regex.Pattern;

import hu.ak_akademia.dal.dao.PatientDAO;

public class SSNInputManagement {

	private final Scanner scanner;
	private static final String REGEX_SSN_NUMBER_VALIDATION = "^[0-9]{9}$";

	public SSNInputManagement(Scanner scanner) {
		this.scanner = scanner;
	}

	public SSN getSSN() {
		PatientDAO patientDAO = new PatientDAO();
		String ssn;
		int counter = 0;
		do {
			if (counter++ > 0) {
				System.out.println("The entered social security number is not valid, please try again!");
			}
			System.out.print("Enter SSN (ie. 123456789): ");
			ssn = scanner.nextLine();
		} while (!isValidFormat(ssn, REGEX_SSN_NUMBER_VALIDATION));
		return new SSN(ssn, patientDAO.isDataExist(ssn));

	}

	private boolean isValidFormat(String userGiven, String regex) {
		return Pattern.compile(regex)
				.matcher(userGiven)
				.matches();
	}
}
