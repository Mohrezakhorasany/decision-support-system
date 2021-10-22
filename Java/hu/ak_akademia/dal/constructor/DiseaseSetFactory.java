package hu.ak_akademia.dal.constructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import hu.ak_akademia.disease.Disease;

public class DiseaseSetFactory implements Constructional<Disease, ResultSet> {

	private final SymptomSetFactory symptomSetFactory = new SymptomSetFactory();

	@Override
	public Set<Disease> construct(ResultSet resultSet) {
		Set<Disease> diseases = new HashSet<>();
		try {
			while (resultSet.next()) {
				diseases.add(createDisease(resultSet.getInt(1), resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return diseases;
	}

	private Disease createDisease(int id, ResultSet resultSet) throws SQLException {
		return new Disease(resultSet.getString(2), symptomSetFactory.construct(id), resultSet.getString(3), id);
	}
}
