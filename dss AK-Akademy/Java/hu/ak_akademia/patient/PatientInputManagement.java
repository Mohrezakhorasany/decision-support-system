package hu.ak_akademia.patient;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.regex.Pattern;

public class PatientInputManagement {

	private final Scanner input;

	private static final String REGEX_EMAIL_VALIDATION = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
	private static final String REGEX_NAME_VALIDATION = "[a-zíéáűőúöüóA-ZÍÉÁŰŐÚÖÜÓ\\-. ]{0,40}";
	private static final String REGEX_PHONE_NUMBER_VALIDATION = "[0-9]{1,2}[-][0-9]{6,7}";

	public PatientInputManagement(Scanner input) {
		this.input = input;
	}

	public String getName(String msg) {
		String name;
		do {
			System.out.print(msg);
			name = input.nextLine();
		} while (!isValidFormat(name, REGEX_NAME_VALIDATION));
		StringBuilder sb = getFormattedName(name);
		return sb.toString()
				.trim();
	}

	public String getEmail(String msg) {
		String email;
		int counter = 0;
		do {
			if (counter++ > 0) {
				System.out.println("Not valid email, enter again!");
			}
			System.out.print(msg);
			email = input.nextLine();
		} while (!isValidFormat(email, REGEX_EMAIL_VALIDATION));
		return email;
	}

	public Gender getGender(String msg) {
		String gender;
		int counter = 0;
		do {
			if (counter++ > 0) {
				System.out.println("Not valid gender, must be M or F");
			}
			System.out.print(msg);
			gender = input.nextLine();
		} while (!Gender.isGenderExist(gender));
		return Gender.getGenderByCode(gender);
	}

	public Date getDateOfBirth(String msg) {
		String date;
		int counter = 0;
		do {
			if (counter++ > 0) {
				System.out.println("Not valid date format, must be yyyy-MM-dd (e.g. 1980-02-29), enter again!");
			}
			System.out.print(msg);
			date = input.nextLine();
		} while (!isValidDate(date));
		return Date.valueOf(date);
	}

	private boolean isValidDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			sdf.parse(date);
			sdf.setLenient(false);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public String getPhoneNumber(String msg) {
		String phoneNumber;
		int counter = 0;
		do {
			if (counter++ > 0) {
				System.out.println("Not valid phone number, must be [20-1234567], enter again!");
			}
			System.out.print(msg);
			phoneNumber = input.nextLine();
		} while (!isValidFormat(phoneNumber, REGEX_PHONE_NUMBER_VALIDATION));
		return phoneNumber;
	}

	public String getAddress(String msg) {
		String address;
		do {
			System.out.print(msg);
			address = input.nextLine();
		} while (address.isEmpty());
		return address;
	}

	private boolean isValidFormat(String userGiven, String regex) {
		return Pattern.compile(regex)
				.matcher(userGiven)
				.matches();
	}

	private StringBuilder getFormattedName(String name) {
		StringBuilder sb = new StringBuilder();
		for (String str : name.replaceAll("\\s{2,}", " ")
				.split(" ")) {
			sb.append(str.substring(0, 1)
					.toUpperCase()
					+ str.substring(1)
							.toLowerCase())
					.append(" ");
		}
		return sb;
	}
}
