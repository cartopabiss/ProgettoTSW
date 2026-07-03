package dao;

import java.sql.SQLException;

import model.AdminBean;

public interface AdminDao {

    AdminBean doRetrieveByEmail(String email) throws SQLException;

}