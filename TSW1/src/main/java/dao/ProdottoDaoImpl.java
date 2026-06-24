package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import model.ProdottoBean;
import dao.DBConnection;

public class ProdottoDaoImpl implements ProdottoDao {

    private static final DataSource ds = DBConnection.getDataSource();
    private static final String TABLE_NAME = "Prodotto";

    @Override
    public Collection<ProdottoBean> doRetrieveAll() throws SQLException {

        Collection<ProdottoBean> prodotti = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE attivo = TRUE";

        try (
            Connection connection = ds.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                prodotti.add(mapRow(rs));
            }
        }

        return prodotti;
    }

    @Override
    public ProdottoBean doRetrieveByKey(int idProdotto) throws SQLException {

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id_prodotto = ?";

        try (
            Connection connection = ds.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, idProdotto);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }

        return null;
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