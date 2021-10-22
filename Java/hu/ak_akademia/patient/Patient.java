package hu.ak_akademia.patient;

import java.sql.Date;

import hu.ak_akademia.ssn.SSN;

public class Patient {

	private final SSN ssn;
	private final String firstName;
	private final String lastName;
	private final Gender gender;
	private final String email;
	private final Date dateOfBirth;
	private final String phoneNumber;
	private final String address;

	private Patient(Builder builder) {
		this.ssn = builder.ssn;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.gender = builder.gender;
		this.email = builder.email;
		this.dateOfBirth = builder.dateOfBirth;
		this.phoneNumber = builder.phoneNumber;
		this.address = builder.address;
	}

	public static class Builder {

		private final SSN ssn;
		private String firstName;
		private String lastName;
		private Gender gender;
		private String email;
		private Date dateOfBirth;
		private String phoneNumber;
		private String address;

		public Builder(SSN ssn) {
			this.ssn = ssn;
		}

		public Builder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public Builder gender(Gender gender) {
			this.gender = gender;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder date(Date dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
			return this;
		}

		public Builder phoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}

		public Builder address(String address) {
			this.address = address;
			return this;
		}

		public Patient build() {
			return new Patient(this);
		}

	}

	public SSN getSsn() {
		return ssn;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public String getEmail() {
		return email;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return String.format("%nSocial security number: %s%nFirst name: %s%nLast name: %s%nGender: %s%nE-mail: %s" + "%nDate of birth: %s%nPhone number %s%nAddress: %s%n", ssn.getSocialSecurityNumber(), firstName, lastName,
				Gender.getText(gender.getCode()), email, dateOfBirth, phoneNumber, address);
	}
}
