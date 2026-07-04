package dao;

import java.sql.*;
import java.util.*;

import javax.sql.DataSource;

import model.*;

public class AbbinamentoDaoImpl implements AbbinamentoDao {

	private static final String TABLE_NAME = "Prodotto";
    private final DataSource ds;

     public AbbinamentoDaoImpl (){
        this.ds = DBConnection.getDataSource();
    }
     public synchronized void doSave(int idProdotto1, int idProdotto2) throws SQLException {

         String sql = "INSERT INTO Abbinamento (id_prodotto1, id_prodotto2) VALUES (?, ?)";

         try (Connection connection = ds.getConnection();
              PreparedStatement ps = connection.prepareStatement(sql)) {
        	 
        	 if (idProdotto1 > idProdotto2) {
                 int tmp = idProdotto1;
                 idProdotto1 = idProdotto2;
                 idProdotto2 = tmp;
             }

             ps.setInt(1, idProdotto1);
             ps.setInt(2, idProdotto2);

             ps.executeUpdate();
         }
     }
     public synchronized void doDelete(int idProdotto1, int idProdotto2) throws SQLException {

         String sql = "DELETE FROM Abbinamento WHERE id_prodotto1 = ? AND id_prodotto2 = ?";

         try (Connection connection = ds.getConnection();
              PreparedStatement ps = connection.prepareStatement(sql)) {

             ps.setInt(1, idProdotto1);
             ps.setInt(2, idProdotto2);

             ps.executeUpdate();
         }
     }
    public Collection<AbbinamentoBean> doRetrieveByProdotto(int idProdotto) throws SQLException {	
    	/*
        String sql = """
            SELECT a.id_abbinamento,
                   p1.id_prodotto AS p1_id, p1.nome AS p1_nome,
                   p2.id_prodotto AS p2_id, p2.nome AS p2_nome
            FROM Abbinamento a
            JOIN Prodotto p1 ON a.id_prodotto1 = p1.id_prodotto
            JOIN Prodotto p2 ON a.id_prodotto2 = p2.id_prodotto
            WHERE a.id_prodotto1 = ? OR a.id_prodotto2 = ?
        """;
        */
    	String sql = """
		    			SELECT *
		    			FROM Abbinamento
		    			WHERE id_prodotto1 = ? OR id_prodotto2 = ?
	    			""";

        List<AbbinamentoBean> list = new ArrayList<>();

        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
        	
        	/*
            ps.setInt(1, idProdotto);
            ps.setInt(2, idProdotto);
            */
        	
        	ps.setInt(1, idProdotto);
        	ps.setInt(2, idProdotto);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
            	
            	ProdottoDao prod = new ProdottoDaoImpl();
            	
                AbbinamentoBean a = new AbbinamentoBean();
                a.setIdAbbinamento(rs.getInt("id_abbinamento"));
                /*
                ProdottoBean p1 = new ProdottoBean();
                p1.setIdProdotto(rs.getInt("id_prodotto1"));
                */
                ProdottoBean p1 = prod.doRetrieveByKey(rs.getInt("id_prodotto1"));
                /*
                ProdottoBean p2 = new ProdottoBean();
                p2.setIdProdotto(rs.getInt("id_prodotto2"));
                */
                

                ProdottoBean p2 = prod.doRetrieveByKey(rs.getInt("id_prodotto2"));
                a.setProdotto1(p1);
                a.setProdotto2(p2);

                list.add(a);
            }
        }

        return list;
    }
    
    public Collection<ProdottoBean> doRetrieveNonAbbinati(int idProdotto) throws SQLException {

        String sql = """
            SELECT p.*
            FROM Prodotto p
            WHERE p.id_prodotto <> ?
            AND p.attivo = TRUE
            AND NOT EXISTS (
                SELECT 1
                FROM Abbinamento a
                WHERE (a.id_prodotto1 = ? AND a.id_prodotto2 = p.id_prodotto)
                   OR (a.id_prodotto2 = ? AND a.id_prodotto1 = p.id_prodotto)
            )
        """;

        List<ProdottoBean> list = new ArrayList<>();

        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idProdotto);
            ps.setInt(2, idProdotto);
            ps.setInt(3, idProdotto);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                ProdottoBean p = new ProdottoBean();
                p.setIdProdotto(rs.getInt("id_prodotto"));
                p.setNome(rs.getString("nome"));
                p.setDescrizione(rs.getString("descrizione"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setCategoria(rs.getString("categoria"));
                p.setImmagine(rs.getString("immagine"));
                p.setQuantitaMagazzino(rs.getInt("quantita_magazzino"));
                p.setAttivo(rs.getBoolean("attivo"));

                list.add(p);
            }
        }

        return list;
    }
}