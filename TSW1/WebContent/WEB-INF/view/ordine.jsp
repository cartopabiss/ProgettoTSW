<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.OrdineBean"%>
<%@ page import="model.RigaOrdineBean"%>
<%@ page import="model.UtenteBean"%>

<%
UtenteBean utente = (UtenteBean) session.getAttribute("utente");
OrdineBean ordine = (OrdineBean) request.getAttribute("ordine");
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<title>Ordine <%= ordine.getIdOrdine() %></title> 
		<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
	</head>
	
	<body>
	
		<header class="header">
		
		    <h1 class="logo">
		        <a href="${pageContext.request.contextPath}/">
		            Da Capo a Piede
		        </a>
		    </h1>
		
		    <nav class="menu">
		
		        <a href="${pageContext.request.contextPath}/home">Home</a>
		        <a href="${pageContext.request.contextPath}/catalogo">Catalogo</a>
		        <a href="${pageContext.request.contextPath}/profilo">Profilo</a>
		
		    </nav>
		
		</header>
		
		<main class="profilo">
		
		    <h2>Ordine #<%= ordine.getIdOrdine() %></h2>
		
		    <p>
		        <strong>Data:</strong>
		        <%= ordine.getDataOrdine() %>
		    </p>
		
		    <p>
		        <strong>Stato:</strong>
		        <%= ordine.getStato() %>
		    </p>
		
		    <p>
		        <strong>Metodo pagamento:</strong>
		        <%= ordine.getMetodoPagamento() %>
		    </p>
		
		    <p>
		        <strong>Indirizzo spedizione:</strong>
		        <%= ordine.getIndirizzoSpedizione() %>
		    </p>
		
		    <br>
		
		    <table class="tabella-carrello">
		        <thead>
		            <tr>
		                <th>Prodotto</th>
		                <th>Prezzo</th>
		                <th>Quantità</th>
		                <th>Subtotale</th>
		            </tr>
		        </thead>
		
		        <tbody>
		
		        <% for (RigaOrdineBean riga : ordine.getRighe()) { %>
		
		            <tr>
		                <td>
		                    <%= riga.getNomeProdotto() %>
		                </td>
		
		                <td>
		                    &euro; <%= String.format("%.2f", riga.getPrezzoUnitario()) %>
		                </td>
		
		                <td>
		                    <%= riga.getQuantita() %>
		                </td>
		
		                <td>
		                    &euro; <%= String.format("%.2f", riga.getSubtotale()) %>
		                </td>
		            </tr>
		        <% } %>
		        </tbody>
		    </table>
		
		    <br>
		
		    <h3>
		        Totale ordine: &euro; <%= String.format("%.2f", ordine.getTotale()) %>
		
		    </h3>
		
		    <br>
		
		    <a href="${pageContext.request.contextPath}/profilo"> 
		        <button>Torna al profilo </button>
		    </a>
		
		</main>
		
		<footer class="footer">
		
		....................
		
		</footer>
	
	</body>

</html>