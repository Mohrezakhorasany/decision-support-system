package hu.ak_akademia.dal.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import hu.ak_akademia.dal.connection.ConnectionFactory;
import hu.ak_akademia.disease.Disease;
import hu.ak_akademia.ssn.SSN;

public class HistoryDAO {

	private Connection connection = ConnectionFactory.getInstance()
			.connect();
	private static final String INSERT_HISTORY_QUERY = "INSERT INTO history VALUES(?,?,?)";
	private static final String GET_HISTORY_QUERY = "SELECT * FROM history WHERE ssn = ?";

	public void insert(SSN ssn, Disease disease) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_HISTORY_QUERY)) {
			preparedStatement.setString(1, ssn.getSocialSecurityNumber());
			preparedStatement.setString(2, disease.getName());
			preparedStatement.setDate(3, getCurrentDate());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Date getCurrentDate() {
		return Date.valueOf(LocalDate.now());
	}

	public ResultSet get(SSN ssn) {
		ResultSet resultSet = null;
		try (PreparedStatement preparedStatement = connection.prepareStatement(GET_HISTORY_QUERY)) {
			preparedStatement.setString(1, ssn.getSocialSecurityNumber());
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return resultSet;
	}
}
