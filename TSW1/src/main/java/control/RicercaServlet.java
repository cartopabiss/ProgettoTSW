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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");

        try {
            ProdottoDao dao = new ProdottoDaoImpl();
            Collection<ProdottoBean> prodotti = dao.doRetrieveByNome(nome);

            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();

            if (prodotti == null || prodotti.isEmpty()) {
                out.println("<p class='nessun-risultato'>Nessun prodotto trovato.</p>");
                return;
            }

            for (ProdottoBean prodotto : prodotti) {
                out.println("<div class='prodotto'>");

                out.println("<img src='" + request.getContextPath() + "/images/" + escapeHtml(prodotto.getImmagine()) + "' alt='" + escapeHtml(prodotto.getNome()) + "'>");

                out.println("<h3>" + escapeHtml(prodotto.getNome()) + "</h3>");

                out.println("<p>&euro; " + String.format("%.2f", prodotto.getPrezzo()) + "</p>");

                out.println("<a class='bottone-prodotto' href='" + request.getContextPath() + "/prodotto?id=" + prodotto.getIdProdotto() + "'>Dettagli</a>");

                out.println("</div>");
            }

        } catch (SQLException e) {
            throw new ServletException("Errore nella ricerca prodotti", e);
        }
    }

    private String escapeHtml(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
}