# Venda-de-cursos
Sistema que organiza venda de cursos para clientes e seus pagamentos, usando Java

###Bibliotecas utilizadas
-Jstl


![Huzzah! Your post has been shared to the community. It will be available in the gallery momentarily. Pagina principal do organizador de venda de cursos](https://i.imgur.com/Ks5rgdo.png "Huzzah! Your post has been shared to the community. It will be available in the gallery momentarily. Pagina principal do organizador de venda de cursos")

```java
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../util/topo.jsp"></jsp:include>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="../lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="../lib/js/bootstrap.min.js"></script>
    <link href="../lib/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="../lib/css/bootstrap.css" rel="stylesheet" type="text/css">
    <link href="../lib/css/padrao.css" rel="stylesheet" type="text/css">
    <title>Página de Sucesso</title>
  </head><body>
    <div class="section section-danger text-justify">
      <div class="container">
        <div class="row text-center">
          <div class="col-md-12 text-center">
            <h1 class="text-center">Sistema de Gerenciamento de Clientes</h1>
          </div>
        </div>
      </div>
    </div>
      <div class="section">
      <div class="container">
        <div class="row">
          <div class="col-md-12 text-center">
            <h3 class="tt_menu">&gt;&gt; CLIENTES - CONSULTAR TODOS OS CLIENTES &lt;&lt;</h3>
          </div>
        </div>
     </div>
   </div> 
   <div class="section section-danger text-justify">
      <div class="container">
        <div class="row text-center">
          <div class="col-md-12 text-center">
            <h1 class="text-center">${mensagem}</h1>
           <table align="center" border="1">
              <tr>
                 <th>CPF</th>
                 <th>Nome</th>
                 <th>Email</th>
              </tr>
             <c:forEach var="row" items="${clientes}">
                <tr>
                   <td>${row.cpf}</td>
                   <td>${row.nome}</td>
                   <td>${row.email}</td>
               </tr>
             </c:forEach>
           </table>
          </div>
        </div>
      </div>
    </div>
    <div class="section">
      <div class="container">
        <div class="row">
          <div class="col-md-12 text-center corrigir">
            <a class="btn btn-default" href="index.jsp">Retornar ao Menu Cliente </a>
          </div>
        </div>
      </div>
    </div>
    <footer>
      <div class="navbar navbar-fixed-bottom bgred">
        <div class="container">
          <div class="row">
            <div class="col-sm-12 text-center" style="top:13px;color:#fff;">Â© ABCTreinamentos - Curso de Java 8 para Web</div>
          </div>
        </div>
      </div>
    </footer>
  
```
