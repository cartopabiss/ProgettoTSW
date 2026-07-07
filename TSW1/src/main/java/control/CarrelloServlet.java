package control;

import java.io.IOException;

import dao.ProdottoDao;
import dao.ProdottoDaoImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.CarrelloBean;
import model.ProdottoBean;
import model.RigaCarrelloBean;

@WebServlet("/carrello")
public class CarrelloServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public CarrelloServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(); 
        CarrelloBean carrello = (CarrelloBean) session.getAttribute("carrello");

        if(carrello == null) {
            carrello = new CarrelloBean();
            session.setAttribute("carrello", carrello);
        }

        request.getRequestDispatcher("/WEB-INF/view/carrello.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        CarrelloBean carrello = (CarrelloBean) session.getAttribute("carrello");

        if(carrello == null) {
            carrello = new CarrelloBean();
            session.setAttribute("carrello", carrello);
        }

        String azione = request.getParameter("azione");

        try {

        	if(azione.equals("aggiungi")) {

        	    int id = Integer.parseInt(request.getParameter("id"));
        	    int quantita = Integer.parseInt(request.getParameter("quantita"));

        	    ProdottoDao dao = new ProdottoDaoImpl();
        	    ProdottoBean prodotto = dao.doRetrieveByKey(id);

        	    if(prodotto != null) {

        	    	RigaCarrelloBean riga = carrello.getRigaByIdProdotto(id);

            	    int quantitaGiaNelCarrello = 0;

            	    if (riga != null) {
            	        quantitaGiaNelCarrello = riga.getQuantita();
            	    }

            	    if (quantitaGiaNelCarrello + quantita <= prodotto.getQuantitaMagazzino()) {
            	        carrello.aggiungiProdotto(prodotto, quantita);
            	    }
        	    }
        	    

        	}

            else if(azione.equals("rimuovi")) {

                int id = Integer.parseInt(request.getParameter("id"));
                carrello.rimuoviProdotto(id);

            }

            else if(azione.equals("aggiorna")) {
                int id = Integer.parseInt(request.getParameter("id"));
                int quantita = Integer.parseInt(request.getParameter("quantita"));
                carrello.aggiornaQuantita(id, quantita);

            }

            else if(azione.equals("svuota")) {
                carrello.svuota();
            }
        }

        catch(Exception e) {
            throw new ServletException(e);
        }

        response.sendRedirect(request.getContextPath() + "/carrello");

    }

}