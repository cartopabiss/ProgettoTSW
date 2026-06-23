package control;

import java.io.IOException;

import dao.UtenteDao;
import dao.UtenteDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.UtenteBean;
import utility.PasswordUtil;

@WebServlet("/registrazione")
public class RegistrazioneServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/view/registrazione.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            UtenteBean utente = new UtenteBean();

            utente.setNome(request.getParameter("nome"));

            utente.setCognome(request.getParameter("cognome"));

            utente.setEmail(request.getParameter("email"));

            utente.setPasswordHash(PasswordUtil.sha256(request.getParameter("password")));

            utente.setIndirizzo(request.getParameter("indirizzo"));

            utente.setCitta(request.getParameter("citta"));

            utente.setCap(request.getParameter("cap"));

            UtenteDao dao = new UtenteDaoImpl();

            dao.doSave(utente);

            response.sendRedirect(request.getContextPath() + "/login");

        } catch (Exception e) {

            throw new ServletException(e);
        }
    }
}