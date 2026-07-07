package control;
	
	import java.io.IOException;
	import java.sql.SQLException;
	
	import dao.AdminDao;
	import dao.AdminDaoImpl;
	import dao.UtenteDao;
	import dao.UtenteDaoImpl;
	
	import jakarta.servlet.ServletException;
	import jakarta.servlet.annotation.WebServlet;
	import jakarta.servlet.http.HttpServlet;
	import jakarta.servlet.http.HttpServletRequest;
	import jakarta.servlet.http.HttpServletResponse;
	import jakarta.servlet.http.HttpSession;
	
	import model.AdminBean;
	import model.UtenteBean;
	
	import utility.PasswordUtil;
	
	@WebServlet("/login")
	public class LoginServlet extends HttpServlet {
	
	    private static final long serialVersionUID = 1L;
	
	    public LoginServlet() {
	        super();
	    }
	    
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
	    }
	 
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	        String email = request.getParameter("email");
	        String password = request.getParameter("password");
	
	        if (email == null || password == null || email.isBlank() || password.isBlank()) {
	
	            request.setAttribute("errore", "Inserisci email e password.");
	            doGet(request, response);//redirect alla pagina di login
	            return;
	        }
	
	        try {
	
	            String hashInserita = PasswordUtil.sha256(password);
	            
	            //login per l'utente
	            UtenteDao utenteDao = new UtenteDaoImpl();
	            UtenteBean utente = utenteDao.doRetrieveByEmail(email.trim());
	
	            if (utente != null && utente.getPasswordHash().equals(hashInserita)) {
	
	                HttpSession session = request.getSession();
	                session.setAttribute("utente", utente);
	                response.sendRedirect(request.getContextPath() + "/profilo");
	                return;
	            }
	
	            //se non è un utente verifico che sia un admin
	            AdminDao adminDao = new AdminDaoImpl();
	            AdminBean admin = adminDao.doRetrieveByEmail(email.trim());
	
	            if (admin != null && admin.getPasswordHash().equals(hashInserita)) {
	
	                HttpSession session = request.getSession();
	                session.setAttribute("admin", admin);
	                response.sendRedirect(request.getContextPath() + "/admin");
	                return;
	            }
	
	            request.setAttribute( "errore", "Utente non trovato, o password errata");
	            doGet(request, response);
	
	        } catch (SQLException e) {
	        	e.printStackTrace();
	            throw new ServletException( "Errore durante il login", e);
	            
	        }
	    }
	}