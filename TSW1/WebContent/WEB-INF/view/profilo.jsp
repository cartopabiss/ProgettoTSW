<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.UtenteBean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="model.OrdineBean" %>

<%
Collection<OrdineBean> ordini = (Collection<OrdineBean>) request.getAttribute("ordini");
UtenteBean utente = (UtenteBean) session.getAttribute("utente");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<title>Registrazione</title> 
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
			    
				
				<hr>
				
				<h2>I miei ordini</h2>
				
				<% if (ordini == null || ordini.isEmpty()) { %>
				    <p>Non hai ancora effettuato acquisti.</p>
				<% } else { %>
				<table class="tabella-admin">
				
				    <tr>
				        <th>Ordine</th>
				        <th>Data</th>
				        <th>Totale</th>
				        <th>Stato</th>
				        <th></th>
				    </tr>
				
				<% for (OrdineBean ordine : ordini) { %>
				
				<tr>
				
				    <td><%= ordine.getIdOrdine() %></td>
				
				    <td><%= ordine.getDataOrdine() %></td>
				
				    <td>
				        &euro; <%= String.format("%.2f", ordine.getTotale()) %>
				    </td>
				
				    <td>
				        <%= ordine.getStato() %>
				    </td>
				
				    <td>
				        <a href="${pageContext.request.contextPath}/ordine?id=<%= ordine.getIdOrdine() %>">
				            Visualizza
				        </a>
				
				    </td>
				
				</tr>
				
				<% } %>
				
				</table>
				
				<% } %>
							
			</main>
		<footer class="footer">
		
		    ....................
		
		</footer>
	
	</body>
</html>

