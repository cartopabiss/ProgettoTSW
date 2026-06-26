package dao;

import java.sql.SQLException;
import java.util.Collection;

import model.ProdottoBean;

public interface ProdottoDao {

    Collection<ProdottoBean> doRetrieveAll() throws SQLException;

    ProdottoBean doRetrieveByKey(int idProdotto) throws SQLException;
   
    //usato in implementazione iniziale senza filtri, non più usato
    Collection<ProdottoBean> doRetrieveByNome(String nome) throws SQLException;

    Collection<ProdottoBean> doRetrieveByFiltri(String nome, String categoria, String ordine) throws SQLException;
}