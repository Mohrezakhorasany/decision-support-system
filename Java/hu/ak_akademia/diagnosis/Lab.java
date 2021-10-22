package hu.ak_akademia.diagnosis;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import hu.ak_akademia.disease.Disease;
import hu.ak_akademia.disease.DiseaseService;
import hu.ak_akademia.symptom.PatientSymptom;

public class Lab {

	private final Set<Disease> diseases;

	public Lab(Set<Disease> diseases) {
		this.diseases = diseases;
	}

	private List<Disease> runDiagnosis(PatientSymptom patientSymptom) {
		DiseaseService diseaseService = new DiseaseService(patientSymptom, diseases);
		if (diseaseService.noDiseaseFound()) {
			return Collections.emptyList();
		}
		return diseaseService.getFilteredDiseases();
	}

	public List<Disease> getDiagnosis(PatientSymptom patientSymptom) {
		return runDiagnosis(patientSymptom);
	}
}
