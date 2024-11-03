<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.model.Product" %>

<!DOCTYPE html>
<html>
<head>
    <title>Outfit List</title>
    <link rel="stylesheet" href="style.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap"
          rel="stylesheet"></link>
</head>
<body>

<h1>Outfit List</h1>

<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    System.out.println(products);
    if (products == null) {
%>
<p>No outfits found.</p>
<%
} else {
%>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Color</th>
        <th>Description</th>
        <th>Price</th>
        <th>URL</th>
        <th>Picture URL</th>
        <th>Material</th>
        <th>Season</th>
        <th>Sizes</th>
        <th>Stock</th>
        <th>Occasions</th>


    </tr>
    <% for (Product product : products) { %>
    <tr>
        <td><%= product.getId() %>
        </td>
        <td><%= product.getName() %>
        </td>
        <td><%= product.getItemColor() %>
        </td>
        <td><%= product.getDescription() %>
        </td>
        <td><%= product.getPrice() %>
        </td>
        <td><%= product.getURL() %>
        </td>
        <td><%= product.getPictureURL() %>
        </td>
        <td><%= product.getMaterial() %>
        </td>
        <td><%= product.getSeason() %>
        </td>
    </tr>
    <% } %>
</table>
<%
    }
%>

</body>
</html>
