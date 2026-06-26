package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import model.ProdottoBean;

public class ProdottoDaoImpl implements ProdottoDao {

    private static final String TABLE_NAME = "Prodotto";
    private final DataSource ds;

    public ProdottoDaoImpl() {
        this.ds = DBConnection.getDataSource();
    }

    public synchronized Collection<ProdottoBean> doRetrieveAll() throws SQLException {

        return doRetrieveByFiltri("", "", "nomeASC"); // di default l'ordine è in base al nome
    }

    public synchronized ProdottoBean doRetrieveByKey(int idProdotto) throws SQLException {

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id_prodotto = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idProdotto);

            try(ResultSet rs = ps.executeQuery()) {

                if(rs.next()) {
                    return mapRow(rs);
                }
            }
        }

        return null;
    }

    public synchronized Collection<ProdottoBean> doRetrieveByNome(String nome)
            throws SQLException {

        return doRetrieveByFiltri(nome, "", "nomeASC");
    }

    @Override
    public synchronized Collection<ProdottoBean> doRetrieveByFiltri(String nome, String categoria, String ordine)
            throws SQLException {

        Collection<ProdottoBean> prodotti = new ArrayList<>();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM ").append(TABLE_NAME).append(" WHERE attivo = TRUE");

        if(nome != null && !nome.isBlank()) {
            sql.append(" AND LOWER(nome) LIKE LOWER(?)");
        }

        if(categoria != null && !categoria.isBlank() && !categoria.equals("TUTTI")) {

            sql.append(" AND categoria = ?");
        }

        switch(ordine) {

            case "nomeDESC":
                sql.append(" ORDER BY nome DESC");
                break;

            case "prezzoASC":
                sql.append(" ORDER BY prezzo ASC");
                break;

            case "prezzoDESC":
                sql.append(" ORDER BY prezzo DESC");
                break;

            default:
                sql.append(" ORDER BY nome ASC");
        }

        try(Connection connection = ds.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql.toString())) {

            int indice = 1;

            if(nome != null && !nome.isBlank()) {
                ps.setString(indice++, "%" + nome.trim() + "%");
            }

            if(categoria != null && !categoria.isBlank() && !categoria.equals("TUTTI")) {

                ps.setString(indice++, categoria);
            }

            try(ResultSet rs = ps.executeQuery()) {

                while(rs.next()) {

                    prodotti.add(mapRow(rs));
                }
            }
        }

        return prodotti;
    }

    private ProdottoBean mapRow(ResultSet rs) throws SQLException {

        ProdottoBean prodotto = new ProdottoBean();

        prodotto.setIdProdotto(rs.getInt("id_prodotto"));
        prodotto.setNome(rs.getString("nome"));
        prodotto.setDescrizione(rs.getString("descrizione"));
        prodotto.setPrezzo(rs.getDouble("prezzo"));
        prodotto.setCategoria(rs.getString("categoria"));
        prodotto.setImmagine(rs.getString("immagine"));
        prodotto.setQuantitaMagazzino(rs.getInt("quantita_magazzino"));
        prodotto.setAttivo(rs.getBoolean("attivo"));

        return prodotto;
    }
}