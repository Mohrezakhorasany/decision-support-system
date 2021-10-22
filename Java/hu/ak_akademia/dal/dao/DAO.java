package hu.ak_akademia.dal.dao;

import java.sql.ResultSet;
import java.util.List;

public interface DAO {

	void insert();

	boolean isIDExist(int id);

	ResultSet getAllColumns();

	List<String> getAllNames();

	List<Integer> getIDs(ResultSet resultSet);
}
