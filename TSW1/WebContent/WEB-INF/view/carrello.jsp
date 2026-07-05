<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.CarrelloBean" %>
<%@ page import="model.RigaCarrelloBean" %>
<%@ page import="model.UtenteBean" %>

<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utente");
    CarrelloBean carrello = (CarrelloBean) session.getAttribute("carrello");
%>

<!DOCTYPE html>
<html>

	<head>
	    <meta charset="UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <title>Carrello</title>
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
		
		        <% if (utente == null) { %>
		            <a href="${pageContext.request.contextPath}/login">Login</a>
		        <% } else { %>
		            <a href="${pageContext.request.contextPath}/profilo">Profilo</a>
		        <% } %>
		
		    </nav>
		
		</header>
		
		<main class="pagina-login">
		
		<h2>Il tuo carrello</h2>
		
		<%
		    if (carrello == null || carrello.isEmpty()) {
		%>
		
		    <p>Il carrello è vuoto.</p>
		
		    <div class="continua-shopping">
		        <a class="bottone-link" href="${pageContext.request.contextPath}/catalogo">
		            Vai al catalogo
		        </a>
		    </div>
		
		<%
		    } else {
		%>
		
		    <table class="tabella-carrello">
		
		        <thead>
		        <tr>
		            <th>Prodotto</th>
		            <th>Prezzo</th>
		            <th>Quantità</th>
		            <th>Subtotale</th>
		            <th>Azioni</th>
		        </tr>
		        </thead>
		
		        <tbody>
		
		        <%
		            for (RigaCarrelloBean riga : carrello.getRighe()) {
		        %>
		
		        <tr>
		
		            <td class="cella-prodotto">
		
		                <img src="${pageContext.request.contextPath}/images/<%= riga.getProdotto().getImmagine() %>"
		                     alt="<%= riga.getProdotto().getNome() %>" width="80">
		
		                <div>
		                    <strong><%= riga.getProdotto().getNome() %></strong>
		                </div>
		
		            </td>
		
		            <td>
		                € <%= String.format("%.2f", riga.getProdotto().getPrezzo()) %>
		            </td>
		
		            <td>
		
		                <form action="${pageContext.request.contextPath}/carrello" method="post">
		
		                    <input type="hidden" name="azione" value="aggiorna">
		                    <input type="hidden" name="id" value="<%= riga.getProdotto().getIdProdotto() %>">
		
		                    <input type="number" name="quantita"
		                           min="1"
		                           max="<%= riga.getProdotto().getQuantitaMagazzino() %>"
		                           value="<%= riga.getQuantita() %>">
		
		                    <button type="submit">Aggiorna</button>
		
		                </form>
		
		            </td>
		
		            <td>
		                € <%= String.format("%.2f", riga.getSubtotale()) %>
		            </td>
		
		            <td>
		
		                <form action="${pageContext.request.contextPath}/carrello" method="post">
		
		                    <input type="hidden" name="azione" value="rimuovi">
		                    <input type="hidden" name="id" value="<%= riga.getProdotto().getIdProdotto() %>">
		
		                    <button type="submit">Rimuovi</button>
		
		                </form>
		
		            </td>
		
		        </tr>
		
		        <%
		            }
		        %>
		
		        </tbody>
		
		    </table>
		
		    <div class="carrello-riepilogo">
		
		        <h3>
		            Totale: € <%= String.format("%.2f", carrello.getTotale()) %>
		        </h3>
		
		        <form action="${pageContext.request.contextPath}/carrello" method="post">
		            <input type="hidden" name="azione" value="svuota">
		            <button type="submit">Svuota carrello</button>
		        </form>
		
		    </div>
		
		    <% if (utente != null) { %>
		
		        <div class="checkout-box">
					<hr>
		            <h3>Completa l'acquisto</h3>
		
		            <form action="${pageContext.request.contextPath}/checkout" method="post">
		
		                <label>Indirizzo spedizione</label>
		                <input type="text" name="indirizzo" required>
		
		                <label>Metodo pagamento</label>
		                <select name="metodoPagamento" required>
		                    <option value="carta">Carta di credito</option>
		                    <option value="paypal">PayPal</option>
		                </select>
		
		                <button type="submit">
		                    Procedi all'acquisto
		                </button>
		
		            </form>
					<hr>
		        </div>
		
		    <% } else { %>
		
		        <p>
		            Devi effettuare il login per acquistare.
		        </p>
		
		        <a href="${pageContext.request.contextPath}/login">
		            <button>Vai al login</button>
		        </a>
		
		    <% } %>
		
		    <div class="continua-shopping">
		        <a href="${pageContext.request.contextPath}/catalogo">
		            <button>Continua lo shopping</button>
		        </a>
		    </div>
		
		<%
		    }
		%>
		
		</main>
		
		<footer class="footer">
		    ......................
		</footer>
	
	</body>
</html>