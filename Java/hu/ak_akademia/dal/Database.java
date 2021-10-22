package hu.ak_akademia.dal;

import java.sql.SQLException;
import java.util.List;

import hu.ak_akademia.dal.dao.DAO;
import hu.ak_akademia.dal.dao.DiseaseSymptomDAO;
import hu.ak_akademia.file.FileData;
import hu.ak_akademia.file.FileReader;

public class Database {

	private final FileData fileData = new FileReader().readAllData();

	public void fillUpDatabase() throws SQLException {
		List<DAO> daos = List.of(new DiseaseSymptomDAO(fileData));
		for (DAO dao : daos) {
			dao.insert();
		}
	}
}
