package dao;

import java.sql.SQLException;
import java.util.Collection;

import model.ProdottoBean;

public interface ProdottoDao {

    Collection<ProdottoBean> doRetrieveAll() throws SQLException;

    ProdottoBean doRetrieveByKey(int idProdotto) throws SQLException;
}