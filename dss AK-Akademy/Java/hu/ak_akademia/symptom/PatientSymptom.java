package hu.ak_akademia.symptom;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

public class PatientSymptom {

	private Set<Symptom> patientSymptomSet = new HashSet<>();
	private static final String SHOWING_SYMPTOMS_MESSAGE = "Patient described symptoms: ";

	public PatientSymptom() {
		patientSymptomSet = new HashSet<>();
	}

	public boolean add(Symptom symptom) {
		return patientSymptomSet.add(symptom);
	}

	public boolean addAll(PatientSymptom symptoms) {
		return patientSymptomSet.addAll(symptoms.patientSymptomSet);
	}

	public int numberOfPatientSymptoms() {
		return patientSymptomSet.size();
	}

	public Set<Symptom> getPatientSymptoms() {
		return Collections.unmodifiableSet(patientSymptomSet);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(SHOWING_SYMPTOMS_MESSAGE);
		StringJoiner sj = new StringJoiner(", ");
		for (Symptom symptom : patientSymptomSet) {
			sj.add(symptom.getName());
		}
		return sb.append(sj)
				.toString();
	}

	public int numberOfSymptoms() {
		return patientSymptomSet.size();
	}
}
