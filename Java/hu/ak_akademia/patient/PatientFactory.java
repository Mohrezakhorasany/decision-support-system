package hu.ak_akademia.patient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import hu.ak_akademia.dal.dao.PatientDAO;
import hu.ak_akademia.ssn.SSN;

public class PatientFactory {

	private Scanner scanner;
	private SSN ssn;

	public PatientFactory(Scanner scanner, SSN ssn) {
		this.scanner = scanner;
		this.ssn = ssn;
	}

	public Patient createPatient(Patient patient) {
		if (ssn.isExistingSsn() && (patient == null)) {
			return createPatientFromDB();
		} else {
			return createPatientFromInput();
		}
	}

	private Patient createPatientFromDB() {
		Patient patient = null;
		ResultSet resultSet = new PatientDAO().get(ssn.getSocialSecurityNumber());
		try {
			while (resultSet.next()) {
				patient = new Patient.Builder(new SSN(resultSet.getString("ssn"), true)).firstName(resultSet.getString("first_name"))
						.lastName(resultSet.getString("last_name"))
						.gender(Gender.getGenderByCode(String.valueOf(resultSet.getString("gender")
								.charAt(0))))
						.email(resultSet.getString("email"))
						.date(resultSet.getDate("date_of_birth"))
						.phoneNumber(resultSet.getString("phone_number"))
						.address(resultSet.getString("address"))
						.build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return patient;
	}

	private Patient createPatientFromInput() {
		PatientInputManagement input = new PatientInputManagement(scanner);
		return new Patient.Builder(ssn).firstName(input.getName("First name: "))
				.lastName(input.getName("Last name: "))
				.gender(input.getGender("Gender (M/F): "))
				.email(input.getEmail("E-mail: "))
				.date(input.getDateOfBirth("Date of birth (e.g. 2020-02-29): "))
				.phoneNumber(input.getPhoneNumber("Phone number [20-1234567]: "))
				.address(input.getAddress("Address: "))
				.build();
	}
}
