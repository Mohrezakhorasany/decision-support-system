package hu.ak_akademia.dal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hu.ak_akademia.dal.connection.ConnectionFactory;
import hu.ak_akademia.patient.Gender;
import hu.ak_akademia.patient.Patient;

public class PatientDAO {

	private Connection connection = ConnectionFactory.getInstance()
			.connect();
	private static final String SELECT_QUERY = "SELECT * FROM patient WHERE ssn = ?";
	private static final String INSERT_QUERY = "INSERT INTO patient VALUES(?,?,?,?,?,?,?,?)";
	private static final String DATA_EXISTS_MESSAGE = "Data already exists in the database";
	private static final String DELETE_QUERY = "DELETE FROM patient WHERE ssn = ?";
	private static final String UPDATE_INQURY = "UPDATE patient SET first_Name = ?, last_Name = ?, gender = ?, email = ?, date_of_birth = ?, phone_number = ?, address = ?  WHERE ssn = ?";

	public boolean isDataExist(String data) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {
			preparedStatement.setString(1, data);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				System.out.println(DATA_EXISTS_MESSAGE);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public ResultSet get(String data) {
		ResultSet resultSet = null;
		try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY);) {
			preparedStatement.setString(1, data);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	public void insert(Patient patient) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
			preparedStatement.setString(1, patient.getSsn()
					.getSocialSecurityNumber());
			preparedStatement.setString(2, patient.getFirstName());
			preparedStatement.setString(3, patient.getLastName());
			preparedStatement.setString(4, Gender.getText(patient.getGender()
					.getCode()));
			preparedStatement.setString(5, patient.getEmail());
			preparedStatement.setDate(6, patient.getDateOfBirth());
			preparedStatement.setString(7, patient.getPhoneNumber());
			preparedStatement.setString(8, patient.getAddress());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void remove(String data) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
			preparedStatement.setString(1, data);
			preparedStatement.executeQuery();
			System.out.println("Patient with " + data + " SSN removed successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(Patient patient) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_INQURY)) {
			preparedStatement.setString(8, patient.getSsn()
					.getSocialSecurityNumber());
			preparedStatement.setString(1, patient.getFirstName());
			preparedStatement.setString(2, patient.getLastName());
			preparedStatement.setString(3, Gender.getText(patient.getGender()
					.getCode()));
			preparedStatement.setString(4, patient.getEmail());
			preparedStatement.setDate(5, patient.getDateOfBirth());
			preparedStatement.setString(6, patient.getPhoneNumber());
			preparedStatement.setString(7, patient.getAddress());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
