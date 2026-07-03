	<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.ProdottoBean"%>
<%@ page import="java.util.Collection"%>
<%@ page import="model.BundleProdottoBean"%>
<%
    ProdottoBean prodotto = (ProdottoBean) request.getAttribute("prodotto");
    String formAction = (String) request.getAttribute("formAction");
    //prodotti aggiungibili (diversi dal prodotto in considerazione)
    Collection<ProdottoBean> disponibili = (Collection<ProdottoBean>) request.getAttribute("disponibili");
    //componenti del bundle
    Collection<BundleProdottoBean> componenti = (Collection<BundleProdottoBean>) request.getAttribute("componenti");
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title> <%= formAction.equals("aggiungi") ? "Nuovo prodotto" : "Modifica prodotto" %> </title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
	</head>

		<body>
			<header class="header">
			    <h1 class="logo">
			        <a href="${pageContext.request.contextPath}/home"> Da Capo a Piede </a>
			    </h1>
			
			    <nav class="menu">
			        <a href="${pageContext.request.contextPath}/admin"> Pannello Admin </a>
			        <a href="${pageContext.request.contextPath}/logout"> Logout </a>
			    </nav>
			
			</header>
		
			<main class="contenitore-admin">
				<div class="formSX">
				<h3>
					<%if (formAction.equals("aggiungi")) {%>
						Aggiungi prodotto
					<%} else {%>
						Modifica prodotto
					<%} %>
				</h3>
				
				<form action="${pageContext.request.contextPath}/admin" method="post" class="form-admin">
				
				    <input type="hidden" name="azione" value="<%= formAction %>">
				
				    <% if(formAction.equals("modifica")) { %>
				
				        <input type="hidden" name="id" value="<%= prodotto.getIdProdotto() %>">
				
				    <% } %>
				
				    <label>Nome</label>
				
				    <input type="text" name="nome"
				    		value="<%= prodotto.getNome() == null ? "" : prodotto.getNome() %>" required>
				
				    <label>Descrizione</label>
				
				    <textarea name="descrizione" rows="5"><%= prodotto.getDescrizione() == null ? "" : prodotto.getDescrizione() %></textarea>
				
				    <label>Prezzo</label>
				
				    <input type="number" step="0.01" min="0" name="prezzo" value="<%= prodotto.getPrezzo() %>" required>
				
				    <label>Categoria</label>
				
				    <select name="categoria">
					
					<%if (prodotto.getCategoria().equals("BUNDLE")){ %>
						<option value="BUNDLE" <%= "BUNDLE".equals(prodotto.getCategoria()) ? "selected" : "" %>>
				            BUNDLE
				        </option>
					<% } else { %>
				        <option value="CAPPELLO" <%= "CAPPELLO".equals(prodotto.getCategoria()) ? "selected" : "" %>>
				            CAPPELLO
				        </option>
				
				        <option value="CALZINO" <%= "CALZINO".equals(prodotto.getCategoria()) ? "selected" : "" %>>
				            CALZINO
				        </option>
				    <% } %>
				
				    </select>
				
				    <label>Immagine</label>
				
				    <input type="text" name="immagine"
				           value="<%= prodotto.getImmagine() == null ? "" : prodotto.getImmagine() %>" required>
				
				    <label>Quantità in magazzino</label>
				
				    <input type="number" min="0" name="quantitaMagazzino"
				           value="<%= prodotto.getQuantitaMagazzino() %>" required>
				
				
				    <button type="submit" class="bottone-admin">
				
				        <%= formAction.equals("aggiungi") ?
				                "Aggiungi prodotto" :
				                "Salva modifiche" %>
				
				    </button>
				</form>
				</div>
				
				<div class="formDX">
				<% if (formAction.equals("modifica") && "BUNDLE".equals(prodotto.getCategoria())) { %>	
				    
				    	<h3>Componenti del bundle</h3>
				    	
						<form action="${pageContext.request.contextPath}/admin" method="post">
						
						    <input type="hidden" name="azione" value="aggiungiBundle">
						    <input type="hidden" name="idBundle" value="<%= prodotto.getIdProdotto() %>">
						    <label>Prodotto</label>
						
						    <select name="idProdotto">
						
						        <% for (ProdottoBean p : disponibili) { %>
						
						            <option value="<%= p.getIdProdotto() %>">
						                <%= p.getNome() %>
						            </option>
						
						        <% } %>
						
						    </select>
						
						    <label>Quantità</label>
						
						    <input type="number" name="quantita" value="1" min="1">
						
						    <button type="submit"> Aggiungi </button>
						
						</form>
						
						<br>
				
				    <h3>Componenti del bundle</h3>	
	
					<% if (componenti == null || componenti.isEmpty()) { %>
					
					    <p>Nessun prodotto presente nel bundle.</p>
					
					<% } else { %>
					
					    <table class="tabella-admin">
					        <tr>
					            <th>Prodotto</th>
					            <th>Quantità</th>
	    						<th>Azione</th>
					        </tr>
					
					        <% for(BundleProdottoBean componente : componenti) { %>
					
					            <tr>
					                <td>
					                    <%= componente.getProdotto().getNome() %>
					                </td>
					                <td>
					                    <%= componente.getQuantita() %>
					                </td>
					                <td>
					                	 <form action="${pageContext.request.contextPath}/admin" method="post">
								
								            <input type="hidden" name="azione" value="rimuoviBundle">
								            <input type="hidden" name="idBundle" value="<%= prodotto.getIdProdotto() %>">
								            <input type="hidden" name="idProdotto" value="<%= componente.getProdotto().getIdProdotto() %>">
								
								            <button type="submit">
								                Rimuovi
								         	</button>
	           							</form>
					                </td>
					            </tr>
					        <% } %>
					    </table>
					<% } %>
				<% } %>
			</div>
			
		</main>
		
		<footer class="footer">
		
		..................
		
		</footer>
	
	</body>
</html>