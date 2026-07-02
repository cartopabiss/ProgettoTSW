package dao;

import java.sql.SQLException;
import java.util.Collection;

import model.BundleProdottoBean;
import model.ProdottoBean;

public interface ProdottoDao {

    Collection<ProdottoBean> doRetrieveAll() throws SQLException;

    ProdottoBean doRetrieveByKey(int idProdotto) throws SQLException;

    //usato nella prima implementazione (senza filtri)
    Collection<ProdottoBean> doRetrieveByNome(String nome) throws SQLException;

    Collection<ProdottoBean> doRetrieveByFiltri(String nome, String categoria, String ordine) throws SQLException;

    Collection<ProdottoBean> doRetrieveAbbinati(int idProdotto) throws SQLException;
    
    //usato per recuperare i prodotti del bundle
    Collection<BundleProdottoBean> doRetrieveProdottiBundle(int idBundle) throws SQLException;
    
    //metodi per l'admin
    void doSave(ProdottoBean prodotto) throws SQLException;

    void doUpdate(ProdottoBean prodotto) throws SQLException;

    void doDelete(int idProdotto) throws SQLException;
    
}