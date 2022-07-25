<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="es.thefactory.javaee8app.util.web.WebActions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Java EE 8 Application</title>
    </head>
    <body>
        <h1>Employee</h1>
        <table>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Last Name</th>
                <th>Position</th>
                <th>Gross Salary</th>
                <th>&nbsp;</th>
            </tr>
            <c:forEach items="${employeeDtoList}" var="employeeDto">
                <c:set value="employee?action=${WebActions.EDIT}&employeeId=${employeeDto.employeeId}" var="url"/>
                <tr>
                    <td>
                        <a href="${url}">${employeeDto.employeeId}</a>
                    </td>
                    <td>${employeeDto.name}</td>
                    <td>${employeeDto.lastName}</td>
                    <td>${employeeDto.position}</td>
                    <td>${employeeDto.grossSalary}</td>
                    <td>
                        <c:set value="employee?action=${WebActions.DELETE}&employeeId=${employeeDto.employeeId}" var="url"/>
                        <a href="${url}" onclick="return confirm('Are you sure?');">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="6" style="text-align: right;">
                    <a href="employee?action=${WebActions.ADD}">Add</a>
                </td>
            </tr>
        </table>
        <a href="index.jsp">Back</a>
    </body>
</html>
