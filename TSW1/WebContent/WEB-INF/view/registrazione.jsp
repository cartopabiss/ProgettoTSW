<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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

    <form class="form-login"
          action="${pageContext.request.contextPath}/registrazione"
          method="post">

        <h2>Registrazione</h2>

        <div class="campo-form">

            <label for="nome">Nome</label>

            <input type="text" id="nome" name="nome" required>

        </div>

        <div class="campo-form">

            <label for="cognome">Cognome</label>

            <input type="text" id="cognome" name="cognome" required>

        </div>

        <div class="campo-form">

            <label for="email">Email</label>

            <input type="email" id="email" name="email" required>

        </div>

        <div class="campo-form">

            <label for="password">Password</label>

            <input type="password" id="password" name="password" required>

        </div>

        <div class="campo-form">

            <label for="indirizzo">Indirizzo</label>

            <input type="text" id="indirizzo" name="indirizzo" required>

        </div>

        <div class="campo-form">

            <label for="citta">Città</label>

            <input type="text" id="citta" name="citta" required>

        </div>

        <div class="campo-form">

            <label for="cap">CAP</label>

            <input type="text" id="cap" name="cap" required>

        </div>

        <button type="submit">Registrati</button>

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

</body>
</html>