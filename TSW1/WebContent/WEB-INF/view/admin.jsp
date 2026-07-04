<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Collection"%>
<%@ page import="model.ProdottoBean"%>

<%
Collection<ProdottoBean> prodotti = (Collection<ProdottoBean>) request.getAttribute("prodotti");
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>admin</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
	</head>
	
	<body>
		
		<header class="header">
		
		    <h1 class="logo">
		        <a href="${pageContext.request.contextPath}/home"> Da Capo a Piede </a>
		    </h1>
		
		    <nav class="menu">
		        <a href="${pageContext.request.contextPath}/home"> Home</a>	
		        <a href="${pageContext.request.contextPath}/logout"> Logout </a>
		    </nav>
		
		</header>
		
		<main class="contenitore-admin">
		
		    <h2>Pannello Amministratore</h2>
		
		    <p>
		
		        <a class="bottone-admin" href="${pageContext.request.contextPath}/admin?azione=nuovo">
		            + Aggiungi prodotto
		        </a>
		
		    </p>
		
		    <table class="tabella-admin">
		        <tr>
		            <th>Immagine</th>
		            <th>Nome</th>
		            <th>Categoria</th>
		            <th>Prezzo</th>
		            <th>Disponibilità</th>
		            <th>Azioni</th>
		        </tr>
		
		        <% for (ProdottoBean prodotto : prodotti) { %>
		
		        <tr>
		
		            <td>
		                <img src="${pageContext.request.contextPath}/images/<%= prodotto.getImmagine() %>"
		                width="70">
		            </td>
		
		            <td>
		                <%= prodotto.getNome() %>
		            </td>
		
		            <td>
		                <%= prodotto.getCategoria() %>
		            </td>
		
		            <td>
		               &euro; <%= String.format("%.2f", prodotto.getPrezzo()) %>
		            </td>
		
		            <td>
		                <%= prodotto.getQuantitaMagazzino() %>
		            </td>
		
		            <td>
		                <a class="bottone-admin" href="${pageContext.request.contextPath}/admin?azione=modifica&id=<%= prodotto.getIdProdotto() %>">
		                    Modifica
		                </a>
		
		                <form action="${pageContext.request.contextPath}/admin" method="post" style="display:inline;">
		
		                    <input type="hidden" name="azione" value="elimina">
		                    <input type="hidden" name="id" value="<%= prodotto.getIdProdotto() %>">
		                    <button type="submit" class="bottone-admin">Elimina </button>
		
		                </form>
		
		            </td>
		        </tr>
		        <% } %>
		    </table>
		</main>
		
		<footer class="footer">
		
		    ................
		
		</footer>
	
	</body>

</html>