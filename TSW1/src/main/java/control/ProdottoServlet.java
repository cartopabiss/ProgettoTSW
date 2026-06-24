package control;

import java.io.IOException;
import java.sql.SQLException;

import dao.ProdottoDao;
import dao.ProdottoDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ProdottoBean;

@WebServlet("/prodotto")
public class ProdottoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ProdottoServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idString = request.getParameter("id");

        if (idString == null || idString.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/catalogo");
            return;
        }

        try {
            int id = Integer.parseInt(idString);

            ProdottoDao dao = new ProdottoDaoImpl();
            ProdottoBean prodotto = dao.doRetrieveByKey(id);

            if (prodotto == null) {
                response.sendRedirect(request.getContextPath() + "/catalogo");
                return;
            }

            request.setAttribute("prodotto", prodotto);
            request.getRequestDispatcher("/WEB-INF/view/prodotto.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/catalogo");
        } catch (SQLException e) {
            throw new ServletException("Errore nel caricamento del prodotto", e);
        }
    }
}