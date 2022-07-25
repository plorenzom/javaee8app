<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="es.thefactory.javaee8app.util.web.WebActions" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Java EE 8 Application</title>
    </head>
    <body>
        <h1>Employee</h1>
        <form action="employee" method="post">
            <input name="employeeId" type="hidden" value="${employeeDto.employeeId}"/>
            <table>
                <tr>
                    <td>Name:</td>
                    <td><input name="name" required type="text" value="${employeeDto.name}"/></td>
                </tr>
                <tr>
                    <td>Last Name:</td>
                    <td><input name="lastName" required type="text" value="${employeeDto.lastName}"/></td>
                </tr>
                <tr>
                    <td>Position:</td>
                    <td><input name="position" required type="text" value="${employeeDto.position}"/></td>
                </tr>
                <tr>
                    <td>Gross Salary:</td>
                    <td><input min="0" name="grossSalary" required type="number" value="${employeeDto.grossSalary}"/></td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: right;">
                        <a href="#" onclick="document.forms[0].requestSubmit();">OK</a>
                        <a href="employee?action=${WebActions.LIST}">Cancel</a>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
