package dao;

import java.sql.SQLException;
import java.util.Collection;

import model.CarrelloBean;
import model.OrdineBean;

public interface OrdineDao {

    int doSave(int idUtente, String indirizzo, String metodoPagamento, CarrelloBean carrello) throws SQLException;

    Collection<OrdineBean> doRetrieveByUtente(int idUtente) throws SQLException;

    OrdineBean doRetrieveByKey(int idOrdine) throws SQLException;

}