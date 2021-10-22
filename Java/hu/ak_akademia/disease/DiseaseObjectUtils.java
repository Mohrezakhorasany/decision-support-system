package hu.ak_akademia.disease;

import java.util.Set;

import hu.ak_akademia.dal.constructor.DiseaseSetFactory;
import hu.ak_akademia.dal.dao.DiseaseDAO;

public class DiseaseObjectUtils {

	public Set<Disease> getDiseaseObjects() {
		return new DiseaseSetFactory().construct(new DiseaseDAO().getAllColumns());
	}
}
