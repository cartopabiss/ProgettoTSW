package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collection;

import dao.ProdottoDao;
import dao.ProdottoDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ProdottoBean;

@WebServlet("/ricerca")
public class RicercaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String categoria = request.getParameter("categoria");
        String ordine = request.getParameter("ordine");

        if(nome == null) {
        	nome = "";
        }
        if(categoria == null) {
        	categoria = "TUTTI";
        }
        if(ordine == null) {
        	ordine = "nomeASC";
        }
	
	        response.setContentType("text/html;charset=UTF-8");

        try {

            ProdottoDao dao = new ProdottoDaoImpl();
            Collection<ProdottoBean> prodotti = dao.doRetrieveByFiltri(nome, categoria, ordine);

            PrintWriter out = response.getWriter();

            if (prodotti.isEmpty()) {
            	out.println("<div class='nessun-risultato'>la ricerca non ha portato nessun risultato</div>");
            }

            for(ProdottoBean prodotto : prodotti) {

                out.println("<div class='prodotto'>");

                out.println("<img src='" + request.getContextPath() + "/images/" + prodotto.getImmagine() + "' alt='" + prodotto.getNome() + "'>");

                out.println("<h3>" + prodotto.getNome() + "</h3>" );

                out.println("<p>&euro; " + String.format("%.2f", prodotto.getPrezzo()) + "</p>" );

                out.println("<a class='bottone-prodotto' href='" + request.getContextPath() + "/prodotto?id=" + prodotto.getIdProdotto() + "'>Dettagli</a>");

                out.println("</div>");
            }

        } catch(SQLException e) {

            throw new ServletException( "Errore nella ricerca prodotti", e );
        }
    }
}