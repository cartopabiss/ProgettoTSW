<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.OrdineBean" %>
<%@ page import="model.UtenteBean" %>

<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utente");
    OrdineBean ordine = (OrdineBean) request.getAttribute("ordine");
    Integer idOrdine = (Integer) request.getAttribute("idOrdine");
%>

<!DOCTYPE html>
<html>
	
	<head>
	    <meta charset="UTF-8">
	    <title>Ordine confermato</title>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
	</head>
	
	<body>
		
		<header class="header">
		
		    <h1 class="logo">
		        <a href="${pageContext.request.contextPath}/home">
		            Da Capo a Piede
		        </a>
		    </h1>
		
		    <nav class="menu">
		
		        <a href="${pageContext.request.contextPath}/home">Home</a>
		        <a href="${pageContext.request.contextPath}/catalogo">Catalogo</a>
		
		        <% if (utente != null) { %>
		            <a href="${pageContext.request.contextPath}/profilo">Profilo</a>
		        <% } else { %>
		            <a href="${pageContext.request.contextPath}/login">Login</a>
		        <% } %>
		
		    </nav>
		
		</header>
		
		<main class="pagina-login">
		
		    <h2>Ordine confermato</h2>
		
		    <% if (ordine != null) { %>
		
		        <div class="box-ordine">
		
		            <h3>Dettagli ordine</h3>
		
		            <p><strong>Numero ordine:</strong> <%= ordine.getIdOrdine() %></p>
		            <p><strong>Totale:</strong> € <%= String.format("%.2f", ordine.getTotale()) %></p>
		            <p><strong>Indirizzo spedizione:</strong> <%= ordine.getIndirizzoSpedizione() %></p>
		            <p><strong>Metodo pagamento:</strong> <%= ordine.getMetodoPagamento() %></p>
		            <p><strong>Stato:</strong> <%= ordine.getStato() %></p>
		
		        </div>
		
		    <% } else if (idOrdine != null) { %>
		
		        <div class="box-ordine">
		
		            <h3>Numero ordine</h3>
		
		            <p><strong>ID ordine:</strong> <%= idOrdine %></p>
		
		            <p>Il tuo ordine è stato registrato correttamente.</p>
		
		        </div>
		
		    <% } %>
		
		    <div class="azioni-ordine">
		
		        <a href="${pageContext.request.contextPath}/catalogo">
		            <button>Continua lo shopping</button>
		        </a>
		
		        <a href="${pageContext.request.contextPath}/profilo">
		            <button>Vai al profilo</button>
		        </a>
		
		    </div>
		
		</main>
		
		<footer class="footer">
		    ......................
		</footer>
	
	</body>
</html>