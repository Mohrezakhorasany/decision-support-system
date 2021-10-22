package hu.ak_akademia.dal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hu.ak_akademia.dal.connection.ConnectionFactory;

public abstract class AbstractDAO implements DAO {

	protected static Connection connection = ConnectionFactory.getInstance()
			.connect();

	protected int findID(String name, String query) {
		int id = 0;
		try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
			prepareStatement.setString(1, name);
			ResultSet resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				id = resultSet.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	protected String findStringTypeColumnBasedOnGivenID(int id, String query, int columnID) {
		try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
			prepareStatement.setInt(1, id);
			ResultSet resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString(columnID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "No data have been found!";
	}

	protected List<String> getListOfNames(String query) {
		List<String> names = new ArrayList<>();
		try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
			ResultSet resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				names.add(resultSet.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return names;
	}

	protected ResultSet getResultSet(String query) {
		ResultSet resultSet = null;
		try (PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	protected ResultSet getResultSet(String query, String parameter) {
		ResultSet resultSet = null;
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, parameter);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	protected List<Integer> createListOfIDsFromResultSet(ResultSet resultSet, int column) {
		List<Integer> ids = new ArrayList<>();
		try {
			while (resultSet.next()) {
				ids.add(resultSet.getInt(column));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}
}
