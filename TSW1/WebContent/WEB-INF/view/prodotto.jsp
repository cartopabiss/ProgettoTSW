<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.UtenteBean" %>
<%@ page import="model.ProdottoBean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="model.BundleProdottoBean" %>

<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utente");
    ProdottoBean prodotto = (ProdottoBean) request.getAttribute("prodotto");
    Collection<ProdottoBean> correlati = (Collection<ProdottoBean>) request.getAttribute("correlati");
    Collection<BundleProdottoBean> componentiBundle = (Collection<BundleProdottoBean>) request.getAttribute("componentiBundle");
%>

<!DOCTYPE html>
<html>
	
	<head>
	
	    <meta charset="UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	    <title><%= prodotto.getNome() %></title>
	
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
		
		        <a href="${pageContext.request.contextPath}/home">
		            Home
		        </a>
		
		        <a href="${pageContext.request.contextPath}/catalogo">
		            Catalogo
		        </a>
		
		        <% if (utente == null) { %>
		
		            <a href="${pageContext.request.contextPath}/login">
		                Login
		            </a>
		
		        <% } else { %>
		
		            <a href="${pageContext.request.contextPath}/profilo">
		                Profilo
		            </a>
		        <% } %>
		
		     	<a href="${pageContext.request.contextPath}/carrello">
		        	Carrello
		     	</a>
		    </nav>
		
		</header>
		
		<main class="prodotto-dettaglio">
		
		    <div class="prodotto-dettaglio-immagine">
		
		        <img src="${pageContext.request.contextPath}/images/<%= prodotto.getImmagine() %>"
		             alt="<%= prodotto.getNome() %>">
		
		    </div>
		
		    <div class="prodotto-dettaglio-testo">
		
		        <h2><%= prodotto.getNome() %></h2>
		
		        <p>
		            <%= prodotto.getDescrizione() %>
		        </p>
		
		        <p>
		            <strong>Categoria:</strong>
		            <%= prodotto.getCategoria() %>
		        </p>
		        
		        <% if (componentiBundle != null && !componentiBundle.isEmpty()) { %>

				    <div class="componenti-bundle">
				
				        <h3>Il bundle comprende</h3>
				
				        <ul>
				
				            <% for (BundleProdottoBean riga : componentiBundle) { %>
				
				                <li>
				                    <img src="${pageContext.request.contextPath}/images/<%= riga.getProdotto().getImmagine() %>"
				                         alt="<%= riga.getProdotto().getNome() %>" width="60">
				
				                    <%= riga.getProdotto().getNome() %>
				
				                    (x<%= riga.getQuantita() %>)
				
				                    <a href="${pageContext.request.contextPath}/prodotto?id=<%= riga.getProdotto().getIdProdotto() %>">
				                        Dettagli
				                    </a>
				
				                </li>
				            <% } %>
				        </ul>
				    </div>
				<% } %>
		        <p>
		            <strong>Prezzo:</strong>
		            &euro; <%= String.format("%.2f", prodotto.getPrezzo()) %>
		        </p>
		
		        <p>
		            <strong>Disponibilità:</strong>
		            <%= prodotto.getQuantitaMagazzino() %>
		        </p>
		
		        <% if (prodotto.getQuantitaMagazzino() > 0) { %>
		
		            <form action="${pageContext.request.contextPath}/carrello" method="post">
		
		                <input type="hidden" name="azione" value="aggiungi">
		
		                <input type="hidden" name="id" value="<%= prodotto.getIdProdotto() %>">
		
		                <label for="quantita">
		                    Quantità
		                </label>
		
		                <input type="number" id="quantita" name="quantita" value="1" min="1"
		                       max="<%= prodotto.getQuantitaMagazzino() %>">
		
		                <br><br>
		
		                <button type="submit" class="bottone-prodotto">
		                    Aggiungi al carrello
		                </button>
		
		            </form>
		
		        <% } else { %>
		
		            <p class="errore">
		                Prodotto non disponibile.
		            </p>
		
		        <% } %>
		
		    </div>
		
		</main>
		
		<% if(correlati != null && !correlati.isEmpty()) { %>
		
		<section class="catalogo">
		
		    <h2>Potrebbe interessarti anche</h2>
		
		    <div class="griglia-prodotti">
		
		        <% for(ProdottoBean p : correlati) { %>
		
		            <div class="prodotto">
		
		                <img src="${pageContext.request.contextPath}/images/<%= p.getImmagine() %>"
		                     alt="<%= p.getNome() %>">
		
		                <h3>
		                    <%= p.getNome() %>
		                </h3>
		
		                <p>
		                    &euro; <%= String.format("%.2f", p.getPrezzo()) %>
		                </p>
		
		                <a class="bottone-prodotto"
		                   href="${pageContext.request.contextPath}/prodotto?id=<%= p.getIdProdotto() %>">
		                    Dettagli
		                </a>
		
		            </div>
		
		        <% } %>
		
		    </div>
		
		</section>
		
		<% } %>
		
		<footer class="footer">
		
		    ......................
		
		</footer>
	
	</body>

</html>