<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.UtenteBean" %>

<%
	UtenteBean utente = (UtenteBean) session.getAttribute("utente");
%>
<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Registrazione</title>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/styles/style.css">

</head>

<body>
	
	<header class="header">
	
	    <h1 class="logo">
	        <a href="${pageContext.request.contextPath}/">
	            Da Capo a Piede
	        </a>
	    </h1>
	
	    <nav class="menu">
	
	        <a href="${pageContext.request.contextPath}/">
	            Home
	        </a>
	
	        <a href="${pageContext.request.contextPath}/catalogo">
	            Catalogo
	        </a>
	        
	        <a href="${pageContext.request.contextPath}/carrello">
                Carrello
            </a>
	
	
	    </nav>
	
	</header>
	<main class="profilo">
	
	    <div>
	    	<h2>Profilo</h2>
	    
	
		    <p>Nome: <%= utente.getNome() %></p>
		
		    <p>Cognome: <%= utente.getCognome() %></p>
		
		    <p>Email: <%= utente.getEmail() %></p>
		
		    <p>Indirizzo: <%= utente.getIndirizzo() %></p>
		
		    <p>Città: <%= utente.getCitta() %></p>
		
		    <p>CAP: <%= utente.getCap() %></p>
		
		    <a class="logout" href="${pageContext.request.contextPath}/logout">
		        Logout
		    </a>
	    </div>
	
	</main>
<footer class="footer">

    ....................

</footer>

</body>
</html>

