<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="es.thefactory.javaee8app.util.web.WebActions" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Java EE 8 Application</title>
    </head>
    <body>
        <h1>Index</h1>
        Click <a href="helloworld">here</a> to get a greeting from the 'Hello World!' servlet.
        <br/>
        Click <a href="employee?action=${WebActions.LIST}">here</a> to get a list of employees.
    </body>
</html>
