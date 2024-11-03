package org.example.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.*;

import org.config.DatabaseConnection;
import org.example.dao.ProductDao;
import org.example.dao.impl.ProductDaoImpl;
import org.example.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/addProduct")
public class AddProductServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(AddProductServlet.class);
    private static final String PARAM_NAME = "name";
    private static final String PARAM_DESCRIPTION = "description";
    private static final String PARAM_COLOR = "color";
    private static final String PARAM_MATERIAL = "material";
    private static final String PARAM_PRICE = "price";
    private static final String PARAM_SEASON = "season";
    private static final String PARAM_CLOTHING_URL = "clothingURL";
    private static final String PARAM_IMAGE_URL = "imageURL";
    private static final String PARAM_OCCASIONS = "occasions";
    private static final String PARAM_STOCK = "stock[%s]";


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection con = DatabaseConnection.getConnection()) {
            ProductDao productDao = new ProductDaoImpl(con);
            String name = req.getParameter(PARAM_NAME);
            String description = req.getParameter(PARAM_DESCRIPTION);
            ItemColor color = ItemColor.valueOf(req.getParameter(PARAM_COLOR));

            Map<Size, Integer> stockBySize = getStockBySize(req);
            String material = req.getParameter(PARAM_MATERIAL);
            String priceParam = req.getParameter(PARAM_PRICE);

            if (priceParam == null || priceParam.isEmpty()) {
                throw new NumberFormatException("Price cannot be null or empty");
            }

            float price = Float.parseFloat(priceParam);
            Season season = Season.valueOf(req.getParameter(PARAM_SEASON));
            List<Occasion> occasions = getSelectedOccasions(req);
            String url = req.getParameter(PARAM_CLOTHING_URL);
            String imageUrl = req.getParameter(PARAM_IMAGE_URL);


            Product newProduct = new Product(name, description, color, stockBySize, material, season, price, occasions, url, imageUrl);
            logger.info("Attempting to add product: {}", newProduct);
            productDao.addProduct(con, newProduct);

            resp.sendRedirect(req.getContextPath() + "/listProducts");

        } catch (NumberFormatException e) {
            logger.error("Invalid price format: {}", e.getMessage(), e); // Log the error with stack trace
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid price format: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage(), e); // Log the error with stack trace
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input: " + e.getMessage());
        } catch (IOException e) {
            logger.error("IO error occurred while processing the request: {}", e.getMessage(), e); // Log the error with stack trace
            // Send an internal server error response without propagating IOException
            try {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An IO error occurred while processing your request.");
            } catch (IOException sendErrorException) {
                logger.error("Failed to send error response: {}", sendErrorException.getMessage(), sendErrorException);
            }
        } catch (
                Exception e) {
            logger.error("An error occurred while adding the product.", e); // Log the error with stack trace
            try {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while adding the product.");
            } catch (IOException sendErrorException) {
                logger.error("Failed to send error response: {}", sendErrorException.getMessage(), sendErrorException);
            }
        }
    }


    private Map<Size, Integer> getStockBySize(HttpServletRequest req) {
        Map<Size, Integer> stockBySize = new HashMap<>();
        for (Size size : Size.values()) {
            stockBySize.put(size, getStock(req, String.format(PARAM_STOCK, size)));
        }
        return stockBySize;
    }


    private int getStock(HttpServletRequest req, String parameterName) {
        String paramValue = req.getParameter(parameterName);
        return (paramValue != null && !paramValue.isEmpty()) ? Integer.parseInt(paramValue) : 0;
    }

    private List<Occasion> getSelectedOccasions(HttpServletRequest req) {
        List<Occasion> occasions = new ArrayList<>();
        String[] selectedOccasions = req.getParameterValues(PARAM_OCCASIONS);
        if (selectedOccasions != null) {
            for (String occasion : selectedOccasions) {
                occasions.add(Occasion.valueOf(occasion));
            }
        }
        return occasions;
    }
}
