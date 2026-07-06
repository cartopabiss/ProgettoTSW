package control;

import java.io.IOException;
import java.sql.SQLException;

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

@WebServlet("/ordine")
public class OrdineServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        String idString = request.getParameter("id");

        if (idString == null || idString.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/profilo");
            return;
        }

        try {

            int idOrdine = Integer.parseInt(idString);

            OrdineDao dao = new OrdineDaoImpl();

            OrdineBean ordine = dao.doRetrieveByKey(idOrdine);

            if (ordine == null) {
                response.sendRedirect(request.getContextPath() + "/profilo");
                return;
            }

            UtenteBean utente = (UtenteBean) session.getAttribute("utente");
            
            System.out.println("Ordine utente = " + ordine.getIdUtente());
            System.out.println("Utente loggato = " + utente.getIdUtente());
            // L'utente può vedere solo i propri ordini
            if (ordine.getIdUtente() != utente.getIdUtente()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            request.setAttribute("ordine", ordine);

            request.getRequestDispatcher("/WEB-INF/view/ordine.jsp").forward(request, response);

        } catch (NumberFormatException e) {

            response.sendRedirect(request.getContextPath() + "/profilo");

        } catch (SQLException e) {

            throw new ServletException(e);

        }
    }
}