<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Login</title>

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

    </nav>

</header>


<main class="pagina-login">

    <form class="form-login"
          id="loginForm"
          action="${pageContext.request.contextPath}/login"
          method="post">

        <h2>Login</h2>


        <label for="email">Email</label>

        <input type="email"
               id="email"
               name="email">

        <div id="erroreEmail" class="errore"></div>


        <label for="password">Password</label>

        <input type="password"
               id="password"
               name="password">

        <div id="errorePassword" class="errore"></div>


        <button type="submit">
            Accedi
        </button>


        <%
            String errore = (String) request.getAttribute("errore");

            if (errore != null) {
        %>

            <div class="errore">
                <p><%= errore %></p>
            </div>

        <%
            }
        %>


        <p class="testo-registrazione">

            Non hai un account?

            <a href="${pageContext.request.contextPath}/registrazione">
                Registrati
            </a>

        </p>

    </form>

</main>


<footer class="footer">

    ......................

</footer>


<script>

const form = document.getElementById("loginForm");

const email = document.getElementById("email");
const password = document.getElementById("password");

const erroreEmail = document.getElementById("erroreEmail");
const errorePassword = document.getElementById("errorePassword");


email.addEventListener("change", validaEmail);
password.addEventListener("change", validaPassword);


form.addEventListener("submit", function(event) {

    let valido = true;

    if (!validaEmail()) {
        valido = false;
    }

    if (!validaPassword()) {
        valido = false;
    }

    if (!valido) {
        event.preventDefault();
    }
});


function validaEmail() {

    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (email.value.trim() === "") {

        erroreEmail.textContent =
            "Inserisci l'email";

        return false;
    }

    if (!regex.test(email.value)) {

        erroreEmail.textContent =
            "Formato email non valido";

        return false;
    }

    erroreEmail.textContent = "";

    return true;
}


function validaPassword() {

    if (password.value.trim() === "") {

        errorePassword.textContent = "Inserisci la password";

        return false;
    }
    errorePassword.textContent = "";

    return true;
}

</script>

</body>

</html>