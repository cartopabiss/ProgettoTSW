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
		
		                    <input type="number" name="quantita" min="1"
		                           max="<%= riga.getProdotto().getQuantitaMagazzino() %>"
		                           value="<%= riga.getQuantita() %>">
		
		                    <button type="submit">Aggiorna</button>
		
		                </form>
		
		            </td>
		
		            <td>
		                &euro; <%= String.format("%.2f", riga.getSubtotale()) %>
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
		            Totale: &euro; <%= String.format("%.2f", carrello.getTotale()) %>
		        </h3>
		
		        <form action="${pageContext.request.contextPath}/carrello" method="post">
		            <input type="hidden" name="azione" value="svuota">
		            <button type="submit">Svuota carrello</button>
		        </form>
		
		    </div>
		    
		    <hr>
		
		    <% if (utente != null) { %>
		
		        <form id="checkoutForm" action="${pageContext.request.contextPath}/checkout" method="post">
			
				    <label>Indirizzo spedizione</label>
				    <input type="text" name="indirizzo" id="indirizzo" required>
				
				    <label>Metodo pagamento</label>
				    <select name="metodoPagamento" id="metodoPagamento" required>
					    <option value="" disabled selected hidden>seleziona</option>
					
					    <option value="carta">Carta di credito</option>
					    <option value="paypal" selected>PayPal</option>
					</select>
				
				    <div id="carta" style="display:none; margin-top:10px;">
				        <label>Numero carta</label>
				        <input type="text" id="numeroCarta" maxlength="16">
						
						<label>scadenza</label>
				        <input type="text" id="scadenzaCarta" placeholder="MM/AAAA" maxlength="7">
				
				        <label>CVV</label>
				        <input type="text" id="cvv" maxlength="3">
				    </div>
				
				    <button type="submit">Procedi all'acquisto</button>
			
				</form>
		
		    <% } else { %>
		
		        <p>
		            Devi effettuare il login per acquistare.
		        </p>
		
		        <a href="${pageContext.request.contextPath}/login">
		            <button>Vai al login</button>
		        </a>
		
		    <% } %>
		    
		    <hr>
		
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
		
	<script>
	
	    const metodo = document.getElementById("metodoPagamento");
	    const cartaBox = document.getElementById("carta");
	    const form = document.getElementById("checkoutForm");
	
	    const indirizzo = document.getElementById("indirizzo");
	    const numeroCarta = document.getElementById("numeroCarta");
	    const cvv = document.getElementById("cvv");
	    const scad = document.getElementById("scad");
	    
	    document.addEventListener("DOMContentLoaded", function () {
	        document.getElementById("metodoPagamento").selectedIndex = 0;
	    });
	
	    // mostra/nasconde campi carta
	    metodo.addEventListener("change", function () {
	
	        if (this.value === "carta") {
	            cartaBox.style.display = "block";
	        } else {
	            cartaBox.style.display = "none";
	        }
	
	    });
	
	    // validazione submit
	    form.addEventListener("submit", function (e) {
	
	        let errors = [];
	
	        // indirizzo
	        if (indirizzo.value.trim().length < 5) {
	            errors.push("Indirizzo troppo corto");
	        }
	
	        // carta obbligatoria solo se selezionata
	        if (metodo.value === "carta") {
	
	            if (!/^\d{16}$/.test(numeroCarta.value)) {
	                
	            }
	
	            if (!/^\d{3}$/.test(cvv.value)) {
	                errors.push("CVV non valido (3 cifre)");
	            }
	            if (/^(0[1-9]|1[0-2])\/\d{4}$/.test(scad.value)){
	            	errors.push("Numero carta non valido (16 cifre)");
	            }
	        }
	
	        if (metodo.value == "") {
	            errors.push("Seleziona un metodo di pagamento");
	        }
	
	        if (errors.length > 0) {
	            e.preventDefault();
	            alert(errors.join("\n"));
	        }
	    });
	</script>
	
	</body>
</html>