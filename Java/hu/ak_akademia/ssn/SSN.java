package hu.ak_akademia.ssn;

import java.util.Objects;

public class SSN {

	private String ssn;
	private boolean existingSsn;

	public SSN(String ssn, boolean existingSsn) {
		this.ssn = ssn;
		this.existingSsn = existingSsn;
	}

	public String getSocialSecurityNumber() {
		return ssn;
	}

	public boolean isExistingSsn() {
		return existingSsn;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		SSN ssn1 = (SSN) o;
		return (existingSsn == ssn1.existingSsn) && Objects.equals(ssn, ssn1.ssn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ssn, existingSsn);
	}
}