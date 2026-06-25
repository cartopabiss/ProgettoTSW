<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrazione</title>

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
        <a href="${pageContext.request.contextPath}/">
            Home
        </a>

        <a href="${pageContext.request.contextPath}/catalogo">
            Catalogo
        </a>

        <a href="${pageContext.request.contextPath}/login">
            Login
        </a>
    </nav>

</header>

<main class="pagina-login">

    <form class="form-login" id="registrazioneForm" action="${pageContext.request.contextPath}/registrazione" method="post">

        <h2>Registrazione</h2>

        <div class="campo-form">
            <label for="nome">Nome</label>
            <input type="text" id="nome" name="nome">
            <div id="erroreNome" class="errore"></div>
        </div>

        <div class="campo-form">
            <label for="cognome">Cognome</label>
            <input type="text" id="cognome" name="cognome">
            <div id="erroreCognome" class="errore"></div>
        </div>

        <div class="campo-form">
            <label for="email">Email</label>
            <input type="email" id="email" name="email">
            <div id="erroreEmail" class="errore"></div>
        </div>

        <div class="campo-form">
            <label for="password">Password</label>
            <input type="password" id="password" name="password">
            <div id="errorePassword" class="errore"></div>
        </div>

        <div class="campo-form">
            <label for="indirizzo">Indirizzo</label>
            <input type="text" id="indirizzo" name="indirizzo">
            <div id="erroreIndirizzo" class="errore"></div>
        </div>

        <div class="campo-form">
            <label for="citta">Città</label>
            <input type="text" id="citta" name="citta">
            <div id="erroreCitta" class="errore"></div>
        </div>

        <div class="campo-form">
            <label for="cap">CAP</label>
            <input type="text" id="cap" name="cap">
            <div id="erroreCap" class="errore"></div>
        </div>

        <%
            String erroreServer = (String) request.getAttribute("errore");
            if (erroreServer != null) {
        %>
            <div class="errore errore-server">
                <%= erroreServer %>
            </div>
        <%
            }
        %>

        <button type="submit">
            Registrati
        </button>

        <p class="testo-registrazione">
            Hai già un account?

            <a href="${pageContext.request.contextPath}/login">
                Accedi
            </a>
        </p>

    </form>

</main>

<footer class="footer">
    ....................
</footer>

<script>
    const form = document.getElementById("registrazioneForm");

    const nome = document.getElementById("nome");
    const cognome = document.getElementById("cognome");
    const email = document.getElementById("email");
    const password = document.getElementById("password");
    const indirizzo = document.getElementById("indirizzo");
    const citta = document.getElementById("citta");
    const cap = document.getElementById("cap");

    nome.addEventListener("change", validaNome);
    cognome.addEventListener("change", validaCognome);
    email.addEventListener("change", validaEmail);
    password.addEventListener("change", validaPassword);
    indirizzo.addEventListener("change", validaIndirizzo);
    citta.addEventListener("change", validaCitta);
    cap.addEventListener("change", validaCap);

    form.addEventListener("submit", function(event) {
        let valido = true;

        if (!validaNome()) valido = false;
        if (!validaCognome()) valido = false;
        if (!validaEmail()) valido = false;
        if (!validaPassword()) valido = false;
        if (!validaIndirizzo()) valido = false;
        if (!validaCitta()) valido = false;
        if (!validaCap()) valido = false;

        if (!valido) {
            event.preventDefault();
        }
    });

    function validaNome() {
        const regex = /^[A-Za-zÀ-ÖØ-öø-ÿ' ]+$/;
        const errore = document.getElementById("erroreNome");

        if (nome.value.trim() === "") {
            errore.textContent = "Inserisci il nome";
            return false;
        }

        if (!regex.test(nome.value.trim())) {
            errore.textContent = "Nome non valido";
            return false;
        }

        errore.textContent = "";
        return true;
    }

    function validaCognome() {
        const regex = /^[A-Za-zÀ-ÖØ-öø-ÿ' ]+$/;
        const errore = document.getElementById("erroreCognome");

        if (cognome.value.trim() === "") {
            errore.textContent = "Inserisci il cognome";
            return false;
        }

        if (!regex.test(cognome.value.trim())) {
            errore.textContent = "Cognome non valido";
            return false;
        }

        errore.textContent = "";
        return true;
    }

    function validaEmail() {
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        const errore = document.getElementById("erroreEmail");

        if (email.value.trim() === "") {
            errore.textContent = "Inserisci l'email";
            return false;
        }

        if (!regex.test(email.value.trim())) {
            errore.textContent = "Formato email non valido";
            return false;
        }

        errore.textContent = "";
        return true;
    }

    function validaPassword() {
        const errore = document.getElementById("errorePassword");

        if (password.value.trim() === "") {
            errore.textContent = "Inserisci la password";
            return false;
        }

        if (password.value.length < 4) {
            errore.textContent = "La password deve contenere almeno 4 caratteri";
            return false;
        }

        errore.textContent = "";
        return true;
    }

    function validaIndirizzo() {
        const errore = document.getElementById("erroreIndirizzo");

        if (indirizzo.value.trim() === "") {
            errore.textContent = "Inserisci l'indirizzo";
            return false;
        }

        errore.textContent = "";
        return true;
    }

    function validaCitta() {
        const regex = /^[A-Za-zÀ-ÖØ-öø-ÿ' ]+$/;
        const errore = document.getElementById("erroreCitta");

        if (citta.value.trim() === "") {
            errore.textContent = "Inserisci la città";
            return false;
        }

        if (!regex.test(citta.value.trim())) {
            errore.textContent = "Città non valida";
            return false;
        }

        errore.textContent = "";
        return true;
    }

    function validaCap() {
        const regex = /^[0-9]{5}$/;
        const errore = document.getElementById("erroreCap");

        if (cap.value.trim() === "") {
            errore.textContent = "Inserisci il CAP";
            return false;
        }

        if (!regex.test(cap.value.trim())) {
            errore.textContent = "CAP non valido";
            return false;
        }

        errore.textContent = "";
        return true;
    }
</script>

</body>
</html>