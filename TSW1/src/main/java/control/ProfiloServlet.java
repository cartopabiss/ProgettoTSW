package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import dao.OrdineDao;
import dao.OrdineDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.OrdineBean;
import model.UtenteBean;

@WebServlet("/profilo")
public class ProfiloServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        UtenteBean utente = (UtenteBean) session.getAttribute("utente");

        try {

            OrdineDao ordineDao = new OrdineDaoImpl();

            Collection<OrdineBean> ordini = ordineDao.doRetrieveByUtente(utente.getIdUtente());

            request.setAttribute("ordini", ordini);

            request.getRequestDispatcher("/WEB-INF/view/profilo.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Errore nel recupero degli ordini", e);
        }
    }
}