<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.UtenteBean" %>
<%@ page import="model.AdminBean"%>

<%
	UtenteBean utente = (UtenteBean) session.getAttribute("utente");
	AdminBean admin = (AdminBean) session.getAttribute("admin");
%>
<!DOCTYPE html>
	<html>
	<head>
	    <meta charset="UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <title>Da Capo a Piede</title>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
	</head>
	<body>
	
	    <header class="header">
	    	<h1 class="logo">
	    		<a href="${pageContext.request.contextPath}/">Da Capo a Piede</a>
			</h1>
	        <nav class="menu">
	               
	   			<% if (utente == null && admin == null) { %>
	   				<a href="${pageContext.request.contextPath}/catalogo">
		                Catalogo
		            </a>
				    <a href="${pageContext.request.contextPath}/carrello">
		                Carrello
		            </a>
		            <a href="${pageContext.request.contextPath}/login">
				        Login
				    </a>
				<% } %>
				
				<% if (utente != null) { %>
					<a href="${pageContext.request.contextPath}/catalogo">
		                Catalogo
		            </a>
				    <a href="${pageContext.request.contextPath}/carrello">
		                Carrello
		            </a>
				    <a href="${pageContext.request.contextPath}/profilo">
				        Profilo
				    </a>
				<% } %>
				
				<% if (admin != null) { %>
				    <a href="${pageContext.request.contextPath}/admin">
				        Pannello controllo
				    </a>
					<a href="${pageContext.request.contextPath}/logout"> 
						Logout 
					</a>
				<% } %>
				
	        </nav>
	    </header>
	
	    <main class="main">
	
	        <section class="presentazione">
	            <p class="testo-intro">
	                presentazione del sito
	            </p>
	
	            <div class="categoria">
	                <h3>Cappelli</h3>
	                <p>In cotone, lino e poliestere per l'estate<br>In lana per l'inverno</p>
	            </div>
	
	            <div class="categoria">
	                <h3>Calzini</h3>
	                <p>In cotone e lino per l'estate<br>In lana e lana merino per l'inverno</p>
	            </div>
	
	            <div class="categoria">
	                <h3>Bundle</h3>
	                <p>Bundle di cappelli e calzini già abbinati</p>
	            </div>
	        </section>
	
	        <aside class="presentazioneDX">
	        	<%if (admin == null) {%>
		            <h2 class="titolo-laterale">Catalogo</h2>
					
		            <p class="testo-laterale">
		                Vai al catalogo per vedere tutti i prodotti disponibili e gli abbinamenti.
		            </p>
		
		            <a class="bottone" href="${pageContext.request.contextPath}/catalogo">
		                Vai al catalogo
		            </a>
	            <%} else {%>
	            	<h2 class="titolo-laterale">Pannello di controllo</h2>
					
		            <p class="testo-laterale">
		                Accedi al pannello per modificare il catalogo
		            </p>
		
		            <a class="bottone" href="${pageContext.request.contextPath}/admin">
		                Vai al pannello
		            </a>
		      	<% } %>
	        </aside>
	    </main>
	
	    <footer class="footer">
	        ......................
	    </footer>
	
	</body>
</html>