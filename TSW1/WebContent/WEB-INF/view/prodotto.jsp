<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.UtenteBean" %>
<%@ page import="model.ProdottoBean" %>

<%
UtenteBean utente = (UtenteBean) session.getAttribute("utente");
ProdottoBean prodotto = (ProdottoBean) request.getAttribute("prodotto");
%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title><%= prodotto.getNome() %></title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">

</head>

<body>

<header class="header">

    <h1 class="logo">
        <a href="${pageContext.request.contextPath}/">Da Capo a Piede</a>
    </h1>

    <nav class="menu">

        <a href="${pageContext.request.contextPath}/home">Home</a>
        <a href="${pageContext.request.contextPath}/catalogo">Catalogo</a>

        <% if (utente == null) { %>

            <a href="${pageContext.request.contextPath}/login">Login</a>

        <% } else { %>

            <a href="${pageContext.request.contextPath}/profilo">Profilo</a>
            <a href="${pageContext.request.contextPath}/carrello">Carrello</a>

        <% } %>

    </nav>

</header>

<main class="prodotto-dettaglio">

    <div class="prodotto-dettaglio-immagine">
        <img src="${pageContext.request.contextPath}/images/<%= prodotto.getImmagine() %>"
             alt="<%= prodotto.getNome() %>">
    </div>

    <div class="prodotto-dettaglio-testo">
        <h2><%= prodotto.getNome() %></h2>

        <p><%= prodotto.getDescrizione() %></p>

        <p><strong>Categoria:</strong> <%= prodotto.getCategoria() %></p>

        <p><strong>Prezzo:</strong> € <%= String.format("%.2f", prodotto.getPrezzo()) %></p>

        <p><strong>Disponibilità:</strong> <%= prodotto.getQuantitaMagazzino() %></p>

        <a class="bottone-prodotto"
           href="#">
            bottone per acquistare (da definire)
        </a>
    </div>

</main>

<footer class="footer">
   ......................
</footer>

</body>
</html>