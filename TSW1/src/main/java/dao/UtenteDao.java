package dao;

import java.sql.SQLException;
import java.util.Collection;

import model.UtenteBean;

public interface UtenteDao {

    public void doSave(UtenteBean utente) throws SQLException;

    public boolean doDelete(int idUtente) throws SQLException;

    public UtenteBean doRetrieveByKey(int idUtente) throws SQLException;

    public UtenteBean doRetrieveByEmail(String email) throws SQLException;

    public Collection<UtenteBean> doRetrieveAll(String order) throws SQLException;
}