package dao;

import java.sql.SQLException;
import model.ProdottoBean;
import model.AbbinamentoBean;
import java.util.Collection;

public interface AbbinamentoDao {

    void doSave(int id1, int id2) throws SQLException;

    void doDelete(int id1, int id2) throws SQLException;

    Collection<AbbinamentoBean> doRetrieveByProdotto(int idProdotto) throws SQLException;
    
    Collection<ProdottoBean> doRetrieveNonAbbinati(int idProdotto) throws SQLException;
}