package hu.ak_akademia.menu.menuoptions;

import java.sql.SQLException;

import hu.ak_akademia.patient.Patient;

public interface PatientMenuOption {

	Patient getPatient() throws SQLException;
}
