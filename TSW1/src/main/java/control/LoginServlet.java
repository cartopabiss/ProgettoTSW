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

import utility.PasswordUtil;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                        "/WEB-INF/view/login.jsp");

        dispatcher.forward(
                request,
                response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null || email.isBlank() || password.isBlank()) {

            request.setAttribute(
                    "errore",
                    "Inserisci email e password.");

            doGet(request, response);
            return;
        }

        try {

            UtenteDao dao = new UtenteDaoImpl();

            UtenteBean utente = dao.doRetrieveByEmail(email.trim());

            if (utente == null) {

                request.setAttribute( 
                        "errore",
                        "Utente non trovato, o password errata");

                doGet(request, response);
                return;
            }

            String hashInserita = PasswordUtil.sha256(password);

            if (utente.getPasswordHash().equals(hashInserita)) {

                HttpSession session = request.getSession();

                session.setAttribute(
                        "utente",
                        utente);

                response.sendRedirect(request.getContextPath() + "/home");

            } else {

                request.setAttribute(
                        "errore",
                        "Utente non trovato, o password errata");

                doGet(request, response);
            }

        } catch (SQLException e) {

            throw new ServletException(
                    "Errore durante il login",
                    e);
        }
    }
}