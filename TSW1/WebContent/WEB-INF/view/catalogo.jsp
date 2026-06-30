<%@ page import="java.util.Collection" %>
<%@ page import="model.ProdottoBean" %>
<%@ page import="model.UtenteBean" %>

<%
UtenteBean utente = (UtenteBean) session.getAttribute("utente");
Collection<ProdottoBean> prodotti = (Collection<ProdottoBean>) request.getAttribute("prodotti");
%>

<!DOCTYPE html>
<html>
	
	<head>
		
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<title>Catalogo</title>
		
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
	    
	    <form class="ricerca" id="formRicerca">
       		
       		<input type="text" id="testoRicerca" placeholder="Cerca prodotto...">
       		<button class="filtri" id="cerca" type="submit"> Cerca </button>
	
	        <select class="filtri" id="categoria">
	            <option value="TUTTI"> Tutte le categorie </option>
	            <option value="CAPPELLO"> Cappelli </option>
	            <option value="CALZINO"> Calzini </option>
	            <option value="BUNDLE"> Bundle </option>
	
	        </select>
	
	        <select class="filtri" id="ordine">
	            <option value="nomeASC"> Nome A-Z </option>
	            <option value="nomeDESC"> Nome Z-A </option>
	            <option value="prezzoASC"> Prezzo crescente </option>
	            <option value="prezzoDESC"> Prezzo decrescente </option>
	        </select>
    	</form>
    	
	    <nav class="menu">
	
	        <a href="${pageContext.request.contextPath}/home">
	            Home
	        </a>
	
	        <% if(utente == null){ %>
	
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
	
	<main class="catalogo">

	    <div id="erroreRicerca" class="errore"></div>
	    
	    <div id="listaProdotti" class="griglia-prodotti">
	
	    <%
	    if(prodotti != null){
	
	        for(ProdottoBean prodotto : prodotti){
	    %>
	
	        <div class="prodotto">
	
	            <img
	            src="${pageContext.request.contextPath}/images/<%= prodotto.getImmagine() %>"
	            alt="<%= prodotto.getNome() %>">
	
	            <h3>
	                <%= prodotto.getNome() %>
	            </h3>
	
	            <p>
	                &euro; <%= String.format("%.2f", prodotto.getPrezzo()) %>
	            </p>
	
	            <a class="bottone-prodotto" href="${pageContext.request.contextPath}/prodotto?id=<%= prodotto.getIdProdotto() %>">
	                Dettagli
	            </a>
	
	        </div>
	
	    <%
	        }
	    }
	    %>
	
	    </div>
	
	</main>

	<footer class="footer">
	
	......................
	
	</footer>
	<script>
		
		const ricerca = document.getElementById("testoRicerca");
		const categoria = document.getElementById("categoria");
		const ordine = document.getElementById("ordine");
		const formRicerca = document.getElementById("formRicerca");
		
		const lista = document.getElementById("listaProdotti");
		const errore = document.getElementById("erroreRicerca");
		
		ricerca.addEventListener("input", aggiornaCatalogo);
		categoria.addEventListener("change", aggiornaCatalogo);
		ordine.addEventListener("change", aggiornaCatalogo);
		
		formRicerca.addEventListener("submit", function(event) {
		    event.preventDefault();   // impedisce il ricaricamento della pagina
		    aggiornaCatalogo();
	
		});
		
		function aggiornaCatalogo() {
		
		    let nome = ricerca.value.trim();
		
		    if(nome.length > 10){
		        errore.innerrHtml = "Massimo 10 caratteri";
		        return;
		    }
		
		    errore.textContent = "";
		    let xhr = new XMLHttpRequest();
		    xhr.onreadystatechange = function(){
		
		        if(xhr.readyState == 4 && xhr.status == 200){
		       		lista.innerHTML = xhr.responseText; //qui si riceve direttamente la riposta del server formattata
		        }
		
		    };
		
		    let url = "${pageContext.request.contextPath}/ricerca"
		        + "?nome=" + encodeURIComponent(nome)
		        + "&categoria=" + categoria.value
		        + "&ordine=" + ordine.value;
		
		    xhr.open("GET", url, true);
		    xhr.send();
		
		}
	
	</script>

</body>
</html>