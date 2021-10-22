package hu.ak_akademia.disease;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import hu.ak_akademia.property.PropertyFileReader;
import hu.ak_akademia.symptom.PatientSymptom;
import hu.ak_akademia.symptom.Symptom;

public class Disease {

	private String name;
	private Set<Symptom> symptoms = new HashSet<>();
	private String treatment;
	private int id;
	private PropertyFileReader propertyFileReader = new PropertyFileReader();

	public Disease(String name, Set<Symptom> symptoms, String treatment, int id) {
		this.name = name;
		this.symptoms = symptoms;
		this.treatment = treatment;
		this.id = id;
	}

	public Disease() {

	}

	public boolean containsAll(PatientSymptom patientSymptom) {
		if (Integer.parseInt(propertyFileReader.readPropertyValue("symptomselectionlimitfromdb")) > patientSymptom.getPatientSymptoms()
				.size()) {
			return patientSymptom.getPatientSymptoms()
					.stream()
					.allMatch(patinetSymptom -> limitSymptomsBasedOnConfig().anyMatch(diseaseSymptom -> diseaseSymptom.equals(patinetSymptom)));
		}
		return limitSymptomsBasedOnConfig().allMatch(diseaseSymptom -> patientSymptom.getPatientSymptoms()
				.stream()
				.anyMatch(patinetSymptom -> diseaseSymptom.equals(patinetSymptom)));
	}

	public String getName() {
		return name;
	}

	public String getTreatment() {
		return treatment;
	}

	public Set<Symptom> getSymptoms() {
		return Collections.unmodifiableSet(symptoms);
	}

	public int getID() {
		return id;
	}

	private Stream<Symptom> limitSymptomsBasedOnConfig() {
		return symptoms.stream()
				.sorted(Comparator.comparing(Symptom::getTfidfScore)
						.reversed())
				.limit(Integer.parseInt(propertyFileReader.readPropertyValue("symptomselectionlimitfromdb")));
	}

	private String getSymptomsNames() {
		return limitSymptomsBasedOnConfig().map(Symptom::getName)
				.toList()
				.toString();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("You are diagnosed with: " + name);
		builder.append("\n");
		builder.append("You may experience some or all these symptoms: ");
		builder.append(getSymptomsNames());
		builder.append("\n");
		builder.append("Following treatment has been advised: " + treatment);
		builder.append("\n");
		return builder.toString();
	}
}