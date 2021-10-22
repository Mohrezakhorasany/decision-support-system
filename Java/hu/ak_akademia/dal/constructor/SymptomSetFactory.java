package hu.ak_akademia.dal.constructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import hu.ak_akademia.dal.dao.DiseaseSymptomDAO;
import hu.ak_akademia.dal.dao.SymptomDAO;
import hu.ak_akademia.symptom.Symptom;

public class SymptomSetFactory implements Constructional<Symptom, Integer> {

	private final ResultSet symptomDAOResultSet = new SymptomDAO().getAllColumns();
	private final ResultSet diseaseSymptomDAOResultSet = new DiseaseSymptomDAO().getAllColumns();
	private int row = 1;

	@Override
	public Set<Symptom> construct(Integer desiredDiseaseID) {
		Set<Symptom> symptoms = new HashSet<>();
		try {
			diseaseSymptomDAOResultSet.absolute(row);
			while (diseaseSymptomDAOResultSet.next()) {
				if (diseaseSymptomDAOResultSet.getInt(1) == desiredDiseaseID) {
					while (symptomDAOResultSet.next()) {
						if (symptomDAOResultSet.getInt(1) == diseaseSymptomDAOResultSet.getInt(2)) {
							symptoms.add(new Symptom(symptomDAOResultSet.getString(2), diseaseSymptomDAOResultSet.getDouble(3), diseaseSymptomDAOResultSet.getInt(2)));
							symptomDAOResultSet.beforeFirst();
							break;
						}
					}
					row++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return symptoms;
	}
}
