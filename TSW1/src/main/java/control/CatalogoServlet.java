package control;

import java.io.IOException;
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

@WebServlet("/catalogo")
public class CatalogoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public CatalogoServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

        try {

            ProdottoDao dao = new ProdottoDaoImpl();// istanziato per poter usarne la proprietà doRetrieve

            Collection<ProdottoBean> prodotti = dao.doRetrieveByFiltri(nome, categoria, ordine);

            request.setAttribute("prodotti", prodotti);

            request.getRequestDispatcher("/WEB-INF/view/catalogo.jsp").forward(request, response);

        } catch (SQLException e) {

            throw new ServletException("Errore nel caricamento del catalogo", e);

        }
    }
}