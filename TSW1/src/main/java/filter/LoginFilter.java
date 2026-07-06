package filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/login")
public class LoginFilter extends HttpFilter implements Filter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpSession session = request.getSession(false);

        if (session != null) {

            if (session.getAttribute("utente") != null) {
                response.sendRedirect(request.getContextPath() + "/profilo");
                return;
            }

            if (session.getAttribute("admin") != null) {
                response.sendRedirect(request.getContextPath() + "/admin");
                return;
            }
        }

        // se non loggato vai alla servlet login
        chain.doFilter(request, response);
    }
}