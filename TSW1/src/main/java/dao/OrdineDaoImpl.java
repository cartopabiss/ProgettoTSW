package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;


import model.CarrelloBean;
import model.OrdineBean;
import model.RigaCarrelloBean;
import model.RigaOrdineBean;

public class OrdineDaoImpl implements OrdineDao{

    private final DataSource ds;

    public OrdineDaoImpl() {
        ds = DBConnection.getDataSource();
    }

    public synchronized int doSave(int idUtente, String indirizzo, String metodoPagamento, CarrelloBean carrello) throws SQLException {

        String insertOrdine = "INSERT INTO Ordine (id_utente, totale, indirizzo_spedizione, metodo_pagamento) " +
                "VALUES (?, ?, ?, ?)";

        String insertRiga = "INSERT INTO RigaOrdine " +
                "(id_ordine, id_prodotto, nome_prodotto, prezzo_unitario, quantita, subtotale) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        String updateMagazzino = "UPDATE Prodotto " +
                "SET quantita_magazzino = quantita_magazzino - ? " +
                "WHERE id_prodotto = ? AND quantita_magazzino >= ?";

        Connection connection = null;

        try {
            connection = ds.getConnection(); 
            connection.setAutoCommit(false);

            int idOrdine;
            														// ritorna la chiave della riga ordine appena generata
            try (PreparedStatement ps = connection.prepareStatement(insertOrdine, Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, idUtente);
                ps.setDouble(2, carrello.getTotale());
                ps.setString(3, indirizzo);
                ps.setString(4, metodoPagamento);

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (!rs.next()) {
                        throw new SQLException("Errore recupero ID ordine");
                    }
                    idOrdine = rs.getInt(1);
                }
            }

            try (PreparedStatement ps = connection.prepareStatement(insertRiga)) {

                for (RigaCarrelloBean riga : carrello.getRighe()) {

                    ps.setInt(1, idOrdine);
                    ps.setInt(2, riga.getProdotto().getIdProdotto());
                    ps.setString(3, riga.getProdotto().getNome());
                    ps.setDouble(4, riga.getProdotto().getPrezzo());
                    ps.setInt(5, riga.getQuantita());
                    ps.setDouble(6, riga.getSubtotale());

                    ps.addBatch();
                }
                ps.executeBatch();
            }

            try (PreparedStatement ps = connection.prepareStatement(updateMagazzino)) {

                for (RigaCarrelloBean riga : carrello.getRighe()) {

                    ps.setInt(1, riga.getQuantita());
                    ps.setInt(2, riga.getProdotto().getIdProdotto());
                    ps.setInt(3, riga.getQuantita());

                    int righeAggiornate = ps.executeUpdate();

                    if (righeAggiornate == 0) {
                        throw new SQLException("Magazzino insufficiente per prodotto ID " + riga.getProdotto().getIdProdotto());
                    }
                }
            }

            connection.commit();
            return idOrdine;

        } catch (SQLException e) {

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;

        } finally { // eseguito sempre

            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Collection<OrdineBean> doRetrieveByUtente(int idUtente) throws SQLException {

        String sqlOrdini = "SELECT * FROM Ordine WHERE id_utente = ? ORDER BY data_ordine DESC";

        List<OrdineBean> ordini = new ArrayList<>();

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlOrdini)) {

            ps.setInt(1, idUtente);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    OrdineBean ordine = new OrdineBean();

                    ordine.setIdOrdine(rs.getInt("id_ordine"));
                    ordine.setTotale(rs.getDouble("totale"));
                    ordine.setDataOrdine(rs.getTimestamp("data_ordine"));
                    ordine.setIndirizzoSpedizione(rs.getString("indirizzo_spedizione"));
                    ordine.setMetodoPagamento(rs.getString("metodo_pagamento"));
                    ordine.setStato(rs.getString("stato"));

                    ordini.add(ordine);
                }
            }
        }

        return ordini;
    }

    public OrdineBean doRetrieveByKey(int idOrdine) throws SQLException {

        String sqlOrdine = "SELECT * FROM Ordine WHERE id_ordine = ?";
        String sqlRighe = "SELECT * FROM RigaOrdine WHERE id_ordine = ?";

        OrdineBean ordine = null;

        try (Connection connection = ds.getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(sqlOrdine)) {
                ps.setInt(1, idOrdine);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {

                        ordine = new OrdineBean();

                        ordine.setIdOrdine(rs.getInt("id_ordine"));
                        ordine.setIdUtente(rs.getInt("id_utente"));
                        ordine.setTotale(rs.getDouble("totale"));
                        ordine.setDataOrdine(rs.getTimestamp("data_ordine"));
                        ordine.setIndirizzoSpedizione(rs.getString("indirizzo_spedizione"));
                        ordine.setMetodoPagamento(rs.getString("metodo_pagamento"));
                        ordine.setStato(rs.getString("stato"));
                    }
                }
            }

            if (ordine == null) return null;

            try (PreparedStatement ps = connection.prepareStatement(sqlRighe)) {

                ps.setInt(1, idOrdine);

                try (ResultSet rs = ps.executeQuery()) {

                    List<RigaOrdineBean> righe = new ArrayList<>();

                    while (rs.next()) {

                        RigaOrdineBean riga = new RigaOrdineBean();

                        riga.setIdRiga(rs.getInt("id_riga"));
                        riga.setNomeProdotto(rs.getString("nome_prodotto"));
                        riga.setPrezzoUnitario(rs.getDouble("prezzo_unitario"));
                        riga.setQuantita(rs.getInt("quantita"));
                        riga.setSubtotale(rs.getDouble("subtotale"));

                        righe.add(riga);
                    }
                    ordine.setRighe(righe);
                }
            }
        }

        return ordine;
    }

}