<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Da Capo a Piede</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>

    <header class="header">
        <h1 class="logo">Da Capo a Piede</h1>

        <nav class="menu">
            <!-- <a href="${pageContext.request.contextPath}/home">Home</a> -->
            <a href="${pageContext.request.contextPath}/catalogo">Catalogo</a>
            <a href="${pageContext.request.contextPath}/login">Login</a>
            <a href="${pageContext.request.contextPath}/carrello">Carrello</a>
        </nav>
    </header>

    <main class="main">

        <section class="presentazione">
            <p class="testo-intro">
                //presentazione
            </p>

            <div class="categoria">
                <h3>Cappelli</h3>
                <p>In lana cotone e nylon.</p>
            </div>

            <div class="categoria">
                <h3>Calzini</h3>
                <p>In cotone e lino</p>
            </div>

            <div class="categoria">
                <h3>Bundle</h3>
                <p>Pack con prodotti già abbinati.</p>
            </div>
        </section>

        <aside class="presentazioneDX">
            <h2 class="titolo-laterale">Catalogo</h2>

            <p class="testo-laterale">
                Vai al catalogo per vedere tutti i prodotti disponibili e gli abbinamenti.
            </p>

            <a class="bottone" href="${pageContext.request.contextPath}/catalogo">
                Vai al catalogo
            </a>
        </aside>

    </main>

    <footer class="footer">
        ......................
    </footer>

</body>
</html>