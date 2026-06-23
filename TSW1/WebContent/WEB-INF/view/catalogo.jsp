<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.UtenteBean" %>

<%
	UtenteBean utente = (UtenteBean) session.getAttribute("utente");
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

    <form class="ricerca">

        <input type="text" placeholder="Cerca prodotto...">

    </form>

    <nav class="menu">

        <a href="${pageContext.request.contextPath}/home">Home</a>
    
    	<% if(utente == null) { %>

    		<a href="${pageContext.request.contextPath}/login">
            	Login
        	</a>

    	<% } else { %>

        	<a href="${pageContext.request.contextPath}/profilo">
            	Profilo
        	</a>
        	<a href="${pageContext.request.contextPath}/carrello">
        		Carrello
    		</a>

    	<% } %>

    </nav>

</header>


<main class="catalogo">

    <div class="prodotto">

        <img src="${pageContext.request.contextPath}/images/cappello1.jpg"
             alt="Cappello">

        <h3>Cappello Nero</h3>

        <p>24.99 €</p>

        <a class="bottone-prodotto" href="#">
            Dettagli
        </a>

    </div>


    <div class="prodotto">

        <img src="${pageContext.request.contextPath}/images/calzino1.jpg"
             alt="Calzino">

        <h3>Calzino Rosso</h3>

        <p>9.99 €</p>

        <a class="bottone-prodotto" href="#">
            Dettagli
        </a>

    </div>


    <div class="prodotto">

        <img src="${pageContext.request.contextPath}/images/bundle1.jpg"
             alt="Bundle">

        <h3>Bundle Estate</h3>

        <p>39.99 €</p>

        <a class="bottone-prodotto" href="#">
            Dettagli
        </a>

    </div>


    <div class="prodotto">

        <img src="${pageContext.request.contextPath}/images/cappello2.jpg"
             alt="Cappello">

        <h3>Cappello Blu</h3>

        <p>29.99 €</p>

        <a class="bottone-prodotto" href="#">
            Dettagli
        </a>

    </div>


    <div class="prodotto">

        <img src="${pageContext.request.contextPath}/images/calzino2.jpg"
             alt="Calzino">

        <h3>Calzino Verde</h3>

        <p>11.99 €</p>

        <a class="bottone-prodotto" href="#">
            Dettagli
        </a>

    </div>


    <div class="prodotto">

        <img src="${pageContext.request.contextPath}/images/bundle2.jpg"
             alt="Bundle">

        <h3>Bundle Inverno</h3>

        <p>44.99 €</p>

        <a class="bottone-prodotto" href="#">
            Dettagli
        </a>

    </div>

</main>


<footer class="footer">

    © Da Capo a Piede

</footer>

</body>
</html>