package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import model.UtenteBean;

public class UtenteDaoImpl implements UtenteDao {

    private static final String TABLE_NAME = "Utente";
    private final DataSource ds;

    public UtenteDaoImpl() {
        this.ds = DBConnection.getDataSource();
    }

    @Override
    public synchronized void doSave(UtenteBean utente) throws SQLException {

        String sql = "INSERT INTO " + TABLE_NAME +
                " (nome, cognome, email, password_hash, indirizzo, citta, cap) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getCognome());
            ps.setString(3, utente.getEmail());
            ps.setString(4, utente.getPasswordHash());
            ps.setString(5, utente.getIndirizzo());
            ps.setString(6, utente.getCitta());
            ps.setString(7, utente.getCap());

            ps.executeUpdate();
        }
    }

    @Override
    public synchronized boolean doDelete(int idUtente) throws SQLException {

        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id_utente = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idUtente);
            return ps.executeUpdate() != 0;
        }
    }

    @Override
    public synchronized UtenteBean doRetrieveByKey(int idUtente) throws SQLException {

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id_utente = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idUtente);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }

        return null;
    }

    @Override
    public synchronized UtenteBean doRetrieveByEmail(String email) throws SQLException {

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE email = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }

        return null;
    }

    @Override
    public synchronized Collection<UtenteBean> doRetrieveAll(String order) throws SQLException {

        Collection<UtenteBean> utenti = new LinkedList<>();

        String sql = "SELECT * FROM " + TABLE_NAME;

        if (order != null && !order.isBlank()) {
            sql += " ORDER BY " + order;
        }

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                utenti.add(mapRow(rs));
            }
        }

        return utenti;
    }

    private UtenteBean mapRow(ResultSet rs) throws SQLException {

        UtenteBean utente = new UtenteBean();

        utente.setIdUtente(rs.getInt("id_utente"));
        utente.setNome(rs.getString("nome"));
        utente.setCognome(rs.getString("cognome"));
        utente.setEmail(rs.getString("email"));
        utente.setPasswordHash(rs.getString("password_hash"));
        utente.setIndirizzo(rs.getString("indirizzo"));
        utente.setCitta(rs.getString("citta"));
        utente.setCap(rs.getString("cap"));

        return utente;
    }
}