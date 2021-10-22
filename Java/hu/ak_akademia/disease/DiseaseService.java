package hu.ak_akademia.disease;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import hu.ak_akademia.symptom.PatientSymptom;

public class DiseaseService {

	private final PatientSymptom patientSymptom;
	private List<Disease> diseases;

	public DiseaseService(PatientSymptom patientSymptom, Set<Disease> diseases) {
		this.patientSymptom = patientSymptom;
		this.diseases = retainDiseasesContainsAllSymptoms(diseases);
	}

	private List<Disease> retainDiseasesContainsAllSymptoms(Set<Disease> diseases) {
		return diseases.stream()
				.filter(disease -> disease.containsAll(patientSymptom))
				.collect(Collectors.toUnmodifiableList());
	}

	public List<Disease> getFilteredDiseases() {
		return Collections.unmodifiableList(diseases);
	}

	public boolean noDiseaseFound() {
		return diseases.isEmpty();
	}
}
