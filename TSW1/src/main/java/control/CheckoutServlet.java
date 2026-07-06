package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CarrelloBean;
import model.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;

import dao.OrdineDao;
import dao.OrdineDaoImpl;


@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private OrdineDao ordineDao = new OrdineDaoImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        UtenteBean utente = (UtenteBean) session.getAttribute("utente");
        CarrelloBean carrello = (CarrelloBean) session.getAttribute("carrello");

        String indirizzo = request.getParameter("indirizzo");
        String metodoPagamento = request.getParameter("metodoPagamento");

        try {

            int idOrdine = ordineDao.doSave( utente.getIdUtente(), indirizzo, metodoPagamento, carrello);

            // svuota carrello dopo ordine OK
            carrello.svuota();

            // da definire pagina di conferma: uso il redirect
            request.setAttribute("idOrdine", idOrdine);

            request.setAttribute("ordine", ordineDao.doRetrieveByKey(idOrdine));

            request.getRequestDispatcher( "/WEB-INF/view/ordineConfermato.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Errore creazione ordine", e);
        }
    }
}