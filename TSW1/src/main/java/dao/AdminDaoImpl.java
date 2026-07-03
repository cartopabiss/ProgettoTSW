package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import model.AdminBean;

public class AdminDaoImpl implements AdminDao {

    private static final String TABLE_NAME = "Admin";
    private final DataSource ds;

    public AdminDaoImpl() {
        this.ds = DBConnection.getDataSource();
    }

    public synchronized AdminBean doRetrieveByEmail(String email) throws SQLException {

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE email = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    AdminBean admin = new AdminBean();

                    admin.setIdAdmin(rs.getInt("id_admin"));
                    admin.setEmail(rs.getString("email"));
                    admin.setPasswordHash(rs.getString("password_hash"));

                    return admin;
                }
            }
        }

        return null;
    }
}