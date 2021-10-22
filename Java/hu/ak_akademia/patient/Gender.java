package hu.ak_akademia.patient;

public enum Gender {

	MALE("M", "Male"), FEMALE("F", "Female");

	private String code;
	private String text;

	Gender(String code, String text) {
		this.code = code;
		this.text = text;
	}

	public static Gender getGenderByCode(String code) {
		for (Gender gender : Gender.values()) {
			if (gender.code.equals(code)) {
				return gender;
			}
		}
		return null;
	}

	public static boolean isGenderExist(String code) {
		for (Gender gender : Gender.values()) {
			if (gender.code.equals(code)) {
				return true;
			}
		}
		return false;
	}

	public static String getText(String code) {
		String text = "";
		for (Gender gender : Gender.values()) {
			if (gender.code.equals(code)) {
				text = gender.text;
			}
		}
		return text;
	}

	public String getCode() {
		return code;
	}
}
