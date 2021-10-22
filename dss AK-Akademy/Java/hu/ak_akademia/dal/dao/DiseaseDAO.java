package hu.ak_akademia.dal.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hu.ak_akademia.file.FileData;
import hu.ak_akademia.web.GoogleSearchScraper;

public class DiseaseDAO extends AbstractDAO {

	private FileData fileData;
	private static final String INSERT_DISEASE_QUERY = "INSERT INTO disease (name, treatment) VALUES(?,?)";
	protected static final String GET_ALL_TABLE_DATA_QUERY = "SELECT * FROM disease";
	protected static final String GET_ID_QUERY = "SELECT id FROM disease WHERE name = ?";
	private static final String GET_ALL_NAMES_QUERY = "SELECT name FROM disease";
	private static final String CHECK_ID_EXISTS_QUERY = "SELECT * FROM disease WHERE id = ?";
	private static final String CHECK_NAME_EXISTS_QUERY = "SELECT * FROM disease WHERE name = ?";
	private static final String GET_NAME_QUERY = "SELECT name FROM disease WHERE id = ?";
	private static final String GET_TREATMENT_QUERY = "SELECT treatment FROM disease WHERE id = ?";
	private static final String GET_ALL_IDS_QUERY = "SELECT id FROM disease";
	private static final String TREATMENT_SEARCH_POSTFIX = " treament";

	public DiseaseDAO(FileData fileData) {
		this.fileData = fileData;
	}

	public DiseaseDAO() {
	}

	public int getID(String name) {
		return findID(name, GET_ID_QUERY);
	}

	public String getName(int id) {
		return findStringTypeColumnBasedOnGivenID(id, GET_NAME_QUERY, 1);
	}

	public Set<Integer> getIDs() {
		Set<Integer> ids = new HashSet<>();
		try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_IDS_QUERY)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				ids.add(resultSet.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}

	public boolean isNameExist(String name) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_NAME_EXISTS_QUERY)) {
			preparedStatement.setString(1, name);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getTreatment(int id) {
		return findStringTypeColumnBasedOnGivenID(id, GET_TREATMENT_QUERY, 1);
	}

	@Override
	public void insert() {
		try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DISEASE_QUERY)) {
			GoogleSearchScraper google = new GoogleSearchScraper();
			for (String disease : fileData.getColumnTwoDataWithoutDuplicates()) {
				if (!isNameExist(disease)) {
					preparedStatement.setString(1, disease);
					preparedStatement.setString(2, google.getSearchedResult(disease + TREATMENT_SEARCH_POSTFIX));
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
