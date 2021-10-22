package hu.ak_akademia.dal.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import hu.ak_akademia.file.FileData;

public class SymptomDAO extends AbstractDAO {

	private FileData fileData;
	private static final String INSERT_SYMPTOM_QUERY = " INSERT INTO symptom (name) VALUES(?)";
	private static final String GET_ALL_TABLE_DATA_QUERY = "SELECT * FROM symptom";
	private static final String GET_ID_QUERY = "SELECT id FROM symptom WHERE name = ?";
	private static final String GET_NAME_QUERY = "SELECT name FROM symptom WHERE id = ?";
	private static final String GET_ALL_NAMES_QUERY = "SELECT name FROM symptom";
	private static final String CHECK_ID_EXISTS_QUERY = "SELECT * FROM symptom WHERE id = ?";
	private static final int ID_COLUMN_NUMBER = 1;

	public SymptomDAO(FileData fileData) {
		this.fileData = fileData;
	}

	public SymptomDAO() {
	}

	public int getID(String name) {
		return findID(name, GET_ID_QUERY);
	}

	public String getName(int id) {
		return findStringTypeColumnBasedOnGivenID(id, GET_NAME_QUERY, ID_COLUMN_NUMBER);
	}

	@Override
	public void insert() {
		try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SYMPTOM_QUERY)) {
			for (String symptom : fileData.getColumnOneDataWithoutDuplicates()) {
				preparedStatement.setString(1, symptom);
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
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
		return getResultSet(GET_ALL_TABLE_DATA_QUERY);
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
