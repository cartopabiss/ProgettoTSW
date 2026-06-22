package control;

import java.io.IOException;
import java.sql.SQLException;

import dao.UtenteDao;
import dao.UtenteDaoImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UtenteBean;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/view/login.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String passwordInserita = request.getParameter("password");

        if (email == null || passwordInserita == null ||
            email.isBlank() || passwordInserita.isBlank()) {
            request.setAttribute("errore", "Inserisci email e password.");
            doGet(request, response);
            return;
        }

        UtenteDao utenteDao = new UtenteDaoImpl();

        try {
            UtenteBean utente = utenteDao.doRetrieveByEmail(email.trim());

            if (utente != null && utente.getPasswordHash().equals(passwordInserita)) {

                HttpSession session = request.getSession(true);
                session.setAttribute("utente", utente);

                response.sendRedirect(request.getContextPath() + "/home");
            } else {
                request.setAttribute("errore", "Email o password non valide.");
                doGet(request, response);
            }

        } catch (SQLException e) {
            throw new ServletException("Errore durante il login", e);
        }
    }
}