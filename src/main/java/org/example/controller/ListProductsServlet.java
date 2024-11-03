package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.config.DatabaseConnection;
import org.example.dao.ProductDao;
import org.example.dao.impl.ProductDaoImpl;
import org.example.model.Product;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/listProducts")
public class ListProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection con = DatabaseConnection.getConnection()) { // Connection példányosítása
            ProductDao productDao = new ProductDaoImpl(con); // Connection átadása a DAO-nak
            List<Product> products = productDao.getAllProducts(con); // Implementáld ezt a metódust a DAO-ban

            req.setAttribute("products", products);
            req.getRequestDispatcher("/WEB-INF/views/listProducts.jsp").forward(req, resp);
        } catch (Exception e) {
            // Kezelj bárminemű kivételt, amely a kapcsolat során felléphet
            throw new ServletException("Error while listing products", e);
        }
    }
}
