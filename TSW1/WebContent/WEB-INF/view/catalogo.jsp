<%@ page import="java.util.Collection" %>
<%@ page import="model.ProdottoBean" %>
<%@ page import="model.UtenteBean" %>

<%
UtenteBean utente = (UtenteBean) session.getAttribute("utente");

Collection<ProdottoBean> prodotti = (Collection<ProdottoBean>) request.getAttribute("prodotti");
%>
<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Catalogo</title>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/styles/style.css">

</head>

<body>
	
	<header class="header">
	
	    <h1 class="logo">
	    	<a href="${pageContext.request.contextPath}/">Da Capo a Piede</a>
		</h1>
	
	    <form class="ricerca">
	
	        <input type="text" placeholder="Cerca prodotto...">
	
	    </form>
	
	    <nav class="menu">
	
	        <a href="${pageContext.request.contextPath}/home">Home</a>
	    
	    	<% if(utente == null) { %>
	
	    		<a href="${pageContext.request.contextPath}/login">
	            	Login
	        	</a>
	
	    	<% } else { %>
	
	        	<a href="${pageContext.request.contextPath}/profilo">
	            	Profilo
	        	</a>
	        	<a href="${pageContext.request.contextPath}/carrello">
	        		Carrello
	    		</a>
	
	    	<% } %>
	
	    </nav>
	
	</header>
	
	<main class="catalogo">
	
		<%
		if(prodotti != null) {
		
		    for(ProdottoBean prodotto : prodotti) {
		%>
		
		    <div class="prodotto">
		
		        <img src="${pageContext.request.contextPath}/images/<%= prodotto.getImmagine() %>"
		            alt="<%= prodotto.getNome() %>">
		
		        <h3>
		            <%= prodotto.getNome() %>
		        </h3>
		
		        <p>
		            &euro; <%= prodotto.getPrezzo() %>
		        </p>
		
		        <a class="bottone-prodotto" href="${pageContext.request.contextPath}/prodotto?id=<%= prodotto.getIdProdotto() %>">Dettagli</a>
		
		    </div>
		
		<%
		    }
		}
		%>
	
	</main>
	
	
	<footer class="footer">
	
	   ......................
	
	</footer>
	
</body>
</html>