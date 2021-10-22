package hu.ak_akademia.dal.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import hu.ak_akademia.file.FileData;

public class DiseaseSymptomDAO extends AbstractDAO {

	private final DiseaseDAO diseaseDAO = new DiseaseDAO();
	private final SymptomDAO symptomDAO = new SymptomDAO();
	private FileData fileData;
	private static final String INSERT_DISEASE_SYMPTOM_QUERY = " INSERT INTO disease_symptom (disease_id, symptom_id, score) VALUES(?,?,?)";
	private static final String GET_ALL_NAMES_QUERY = "SELECT name FROM disease_symptom";
	private static final String GET_ALL_TABLE_DATA_EXCEPT_ID_QUERY = "SELECT disease_id, symptom_id, score FROM disease_symptom";
	private static final String CHECK_ID_EXISTS_QUERY = "SELECT * FROM disease_symptom WHERE id = ?";

	public DiseaseSymptomDAO(FileData fileData) {
		this.fileData = fileData;
	}

	public DiseaseSymptomDAO() {
	}

	@Override
	public void insert() {
		try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DISEASE_SYMPTOM_QUERY)) {
			ResultSet resultSetDiseaseDAO = diseaseDAO.getAllColumns();
			while (resultSetDiseaseDAO.next()) {
				String diseaseName = resultSetDiseaseDAO.getString("name");
				for (Map.Entry<String, Double> symptomKScoreV : fileData.getValue(diseaseName)
						.entrySet()) {
					preparedStatement.setInt(1, diseaseDAO.getID(diseaseName));
					preparedStatement.setInt(2, symptomDAO.getID(symptomKScoreV.getKey()));
					preparedStatement.setDouble(3, symptomKScoreV.getValue());
					preparedStatement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isIDExist(int id) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_ID_EXISTS_QUERY)) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ResultSet getAllColumns() {
		return getResultSet(GET_ALL_TABLE_DATA_EXCEPT_ID_QUERY);
	}

	@Override
	public List<String> getAllNames() {
		return getListOfNames(GET_ALL_NAMES_QUERY);
	}

	@Override
	public List<Integer> getIDs(ResultSet resultSet) {
		return createListOfIDsFromResultSet(resultSet, 1);
	}
}
