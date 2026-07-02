package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;
import model.BundleProdottoBean;
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
    
    public synchronized Collection<BundleProdottoBean> doRetrieveProdottiBundle(int idBundle) throws SQLException {

        Collection<BundleProdottoBean> prodottiBundle = new ArrayList<>();

        String sql ="SELECT p.*, bp.quantita " +
            "FROM BundleProdotto bp " +
            "JOIN Prodotto p ON bp.id_prodotto = p.id_prodotto " +
            "WHERE bp.id_bundle = ? AND p.attivo = TRUE";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idBundle);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    ProdottoBean prodotto = mapRow(rs);

                    BundleProdottoBean bundleSingolo = new BundleProdottoBean();
                    bundleSingolo.setProdotto(prodotto);
                    bundleSingolo.setQuantita(rs.getInt("quantita"));

                    prodottiBundle.add(bundleSingolo);
                }
            }
        }

        return prodottiBundle;
    }
    
    public synchronized Collection<ProdottoBean> doRetrieveAbbinati(int idProdotto) throws SQLException {

        Collection<ProdottoBean> prodotti = new ArrayList<>();

        String sql = "SELECT p.* " +
            "FROM " + TABLE_NAME + " p " +
            "JOIN Abbinamento a " +
            "ON ((a.id_prodotto1 = ? AND p.id_prodotto = a.id_prodotto2) " +
            " OR (a.id_prodotto2 = ? AND p.id_prodotto = a.id_prodotto1)) " +
            "WHERE p.attivo = TRUE";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idProdotto);
            ps.setInt(2, idProdotto);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    prodotti.add(mapRow(rs));
                }
            }
        }

        return prodotti;
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
    
    //METODI PER L'ADMIN
    public synchronized void doSave(ProdottoBean prodotto) throws SQLException {

        String sql = "INSERT INTO " + TABLE_NAME +
                " (nome, descrizione, prezzo, categoria, immagine, quantita_magazzino, attivo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, prodotto.getNome());
            ps.setString(2, prodotto.getDescrizione());
            ps.setDouble(3, prodotto.getPrezzo());
            ps.setString(4, prodotto.getCategoria());
            ps.setString(5, prodotto.getImmagine());
            ps.setInt(6, prodotto.getQuantitaMagazzino());
            ps.setBoolean(7, true);

            ps.executeUpdate();
        }
    }
    public synchronized void doUpdate(ProdottoBean prodotto) throws SQLException {

        String sql = "UPDATE " + TABLE_NAME +
                " SET nome = ?, descrizione = ?, prezzo = ?, categoria = ?, " +
                "immagine = ?, quantita_magazzino = ? " +
                "WHERE id_prodotto = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, prodotto.getNome());
            ps.setString(2, prodotto.getDescrizione());
            ps.setDouble(3, prodotto.getPrezzo());
            ps.setString(4, prodotto.getCategoria());
            ps.setString(5, prodotto.getImmagine());
            ps.setInt(6, prodotto.getQuantitaMagazzino());

            ps.setInt(7, prodotto.getIdProdotto());

            ps.executeUpdate();
        }
    }
    public synchronized void doDelete(int idProdotto) throws SQLException {

        String sql = "UPDATE " + TABLE_NAME +
                     " SET attivo = FALSE " +
                     "WHERE id_prodotto = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idProdotto);

            ps.executeUpdate();
        }
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