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
    		<a href="${pageContext.request.contextPath}/">Da Capo a Piede</a>
		</h1>

        <nav class="menu">
            <a href="${pageContext.request.contextPath}/home">Home</a>
    		<a href="${pageContext.request.contextPath}/catalogo">
        		Catalogo
   			</a>
        </nav>
    </header>


<main class="pagina-login">
	<form class="form-login" action="${pageContext.request.contextPath}/login" method="post">
    
        <h2>Login</h2>

        <label>Email</label>

        <input type="email"
               name="email"
               required>

        <label>Password</label>

        <input type="password"
               name="password"
               required>

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

    </form>

</main>


<footer class="footer">

    © Da Capo a Piede

</footer>

</body>
</html>