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
        <a href="${pageContext.request.contextPath}/">Da Capo a Piede</a>
    </h1>

    <form class="ricerca" id="formRicerca">

        <input type="text"
               id="testoRicerca"
               placeholder="Cerca prodotto...">

        <div id="erroreRicerca" class="errore"></div>

    </form>

    <nav class="menu">

        <a href="${pageContext.request.contextPath}/home">Home</a>

        <% if(utente == null) { %>

            <a href="${pageContext.request.contextPath}/login">Login</a>

        <% } else { %>

            <a href="${pageContext.request.contextPath}/profilo">Profilo</a>
            <a href="${pageContext.request.contextPath}/carrello">Carrello</a>

        <% } %>

    </nav>

</header>

<main class="catalogo" id="listaProdotti">

    <div class="griglia-prodotti">

    <%
    if (prodotti != null) {
        for (ProdottoBean prodotto : prodotti) {
    %>

        <div class="prodotto">

            <img src="${pageContext.request.contextPath}/images/<%= prodotto.getImmagine() %>"
                 alt="<%= prodotto.getNome() %>">

            <h3>
                <%= prodotto.getNome() %>
            </h3>

            <p>
                &euro; <%= String.format("%.2f", prodotto.getPrezzo()) %>
            </p>

            <a class="bottone-prodotto"
               href="${pageContext.request.contextPath}/prodotto?id=<%= prodotto.getIdProdotto() %>">
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

const formRicerca = document.getElementById("formRicerca");
const testoRicerca = document.getElementById("testoRicerca");
const erroreRicerca = document.getElementById("erroreRicerca");
const listaProdotti = document.getElementById("listaProdotti");

formRicerca.addEventListener("submit", function(event) {
    event.preventDefault();

    if (validaRicerca()) {
        cercaProdotti();
    }
});

testoRicerca.addEventListener("input", function() {

    if(validaRicerca()) {
        cercaProdotti();
    }

});

function validaRicerca() {
    const testo = testoRicerca.value.trim();

    if (testo.length === 0) {
        erroreRicerca.textContent = "Inserisci un testo da cercare";
        return false;
    }

    if (testo.length > 30) {
        erroreRicerca.textContent = "Massimo 30 caratteri";
        return false;
    }

    erroreRicerca.textContent = "";
    return true;
}


function cercaProdotti() {

    let nome = testoRicerca.value.trim();
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {

        if(xhr.readyState == 4 && xhr.status == 200) {
            listaProdotti.innerHTML = "<div class='griglia-prodotti'>" + xhr.responseText + "</div>";
        }
    };

    xhr.open("GET", "${pageContext.request.contextPath}/ricerca?nome=" + encodeURIComponent(nome), true
    );

    xhr.send();
}

</script>

</body>
</html>