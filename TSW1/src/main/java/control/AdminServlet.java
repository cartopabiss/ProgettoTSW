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
import jakarta.servlet.http.HttpSession;
import model.AdminBean;
import model.ProdottoBean;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public AdminServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        String azione = request.getParameter("azione");

        try {
            ProdottoDao dao = new ProdottoDaoImpl();

            if ("nuovo".equals(azione)) {
                request.setAttribute("prodotto", new ProdottoBean());
                request.setAttribute("formAction", "aggiungi");
                request.getRequestDispatcher("/WEB-INF/view/adminForm.jsp").forward(request, response);
                return;
            }

            if ("modifica".equals(azione)) {

                String idString = request.getParameter("id");

                if (idString == null || idString.isBlank()) {
                    response.sendRedirect(request.getContextPath() + "/admin");
                    return;
                }

                int id = Integer.parseInt(idString);
                ProdottoBean prodotto = dao.doRetrieveByKey(id);

                if (prodotto == null) {
                    response.sendRedirect(request.getContextPath() + "/admin");
                    return;
                }

                request.setAttribute("prodotto", prodotto);
                request.setAttribute("formAction", "modifica");

                request.getRequestDispatcher("/WEB-INF/view/adminForm.jsp").forward(request, response);
                return;
            }
            
            // se non c'è l'azione da svolgere specificata nella get
            Collection<ProdottoBean> prodotti = dao.doRetrieveAll();
            request.setAttribute("prodotti", prodotti);

            request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin");
        } catch (SQLException e) {
            throw new ServletException("Errore nel pannello admin", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String azione = request.getParameter("azione");

        if (azione == null || azione.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/admin");
            return;
        }

        try {
            ProdottoDao dao = new ProdottoDaoImpl();

            switch (azione) {

                case "aggiungi": {

                    String nome = request.getParameter("nome");
                    String descrizione = request.getParameter("descrizione");
                    String prezzoString = request.getParameter("prezzo");
                    String categoria = request.getParameter("categoria");
                    String immagine = request.getParameter("immagine");
                    String quantitaString = request.getParameter("quantitaMagazzino");

                    if (nome == null || nome.isBlank() ||
                        prezzoString == null || prezzoString.isBlank() ||
                        categoria == null || categoria.isBlank() ||
                        immagine == null || immagine.isBlank() ||
                        quantitaString == null || quantitaString.isBlank()) {
                    	
                        response.sendRedirect(request.getContextPath() + "/admin?azione=nuovo");
                        return;
                    }

                    ProdottoBean prodotto = new ProdottoBean();
                    prodotto.setNome(nome.trim());
                    prodotto.setDescrizione(descrizione);
                    prodotto.setPrezzo(Double.parseDouble(prezzoString));
                    prodotto.setCategoria(categoria);
                    prodotto.setImmagine(immagine.trim());
                    prodotto.setQuantitaMagazzino(Integer.parseInt(quantitaString));
                    prodotto.setAttivo(true);

                    dao.doSave(prodotto);
                    response.sendRedirect(request.getContextPath() + "/admin");
                    return;
                }

                case "modifica": {

                    String idString = request.getParameter("id");
                    String nome = request.getParameter("nome");
                    String descrizione = request.getParameter("descrizione");
                    String prezzoString = request.getParameter("prezzo");
                    String categoria = request.getParameter("categoria");
                    String immagine = request.getParameter("immagine");
                    String quantitaString = request.getParameter("quantitaMagazzino");

                    if (idString == null || idString.isBlank() ||
                        nome == null || nome.isBlank() ||
                        prezzoString == null || prezzoString.isBlank() ||
                        categoria == null || categoria.isBlank() ||
                        immagine == null || immagine.isBlank() ||
                        quantitaString == null || quantitaString.isBlank()) {
                        response.sendRedirect(request.getContextPath() + "/admin");
                        return;
                    }

                    ProdottoBean prodotto = new ProdottoBean();
                    prodotto.setIdProdotto(Integer.parseInt(idString));
                    prodotto.setNome(nome.trim());
                    prodotto.setDescrizione(descrizione);
                    prodotto.setPrezzo(Double.parseDouble(prezzoString));
                    prodotto.setCategoria(categoria);
                    prodotto.setImmagine(immagine.trim());
                    prodotto.setQuantitaMagazzino(Integer.parseInt(quantitaString));

                    dao.doUpdate(prodotto);
                    response.sendRedirect(request.getContextPath() + "/admin");
                    return;
                }

                case "elimina": {

                    String idString = request.getParameter("id");

                    if (idString == null || idString.isBlank()) {
                        response.sendRedirect(request.getContextPath() + "/admin");
                        return;
                    }

                    dao.doDelete(Integer.parseInt(idString));
                    response.sendRedirect(request.getContextPath() + "/admin");
                    return;
                }

                default:
                    response.sendRedirect(request.getContextPath() + "/admin");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin");
        } catch (SQLException e) {
            throw new ServletException("Errore durante la gestione admin", e);
        }
    }
}