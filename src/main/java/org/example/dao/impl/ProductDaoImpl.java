package org.example.dao.impl;

import org.example.dao.ProductDao;
import org.example.model.*;


import java.sql.*;
import java.util.*;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    Connection con;

    public ProductDaoImpl(Connection con) {
        this.con = con;
    }

    @Override
    public void addProduct(Connection con, Product product) {

        int colorId = product.getItemColor().getDbValue();

        Map<Size, Integer> stockBySize = product.getStockBySize();
        Set<Size> sizes = stockBySize.keySet();
        List<Occasion> occasions = product.getOccasions();
        System.out.println(product);

        String insertIntoProductsQuery = """
                INSERT INTO OutfitStore.products (name, description, price, url, picture_url, material, season)
                VALUES(?, ?, ?, ?, ?, ?, ?);
                """;
        String insertIntoSizesQuery = """
                INSERT INTO OutfitStore.productSizes (product_id, color_id, size_id, stock)
                VALUES(?, ?, ?, ?);
                """;
        String insertIntoOccasionsQuery = """
                INSERT INTO OutfitStore.productOccasions (product_id, occasion_id)
                VALUES(?, ?);
                """;


        long productId = getMaxProductId(con) + 1;
        System.out.println("New product ID: " + productId);

        product.setId(productId);

        try (PreparedStatement pstmt1 = con.prepareStatement(insertIntoProductsQuery);
             PreparedStatement pstmt2 = con.prepareStatement(insertIntoSizesQuery);
             PreparedStatement pstmt3 = con.prepareStatement(insertIntoOccasionsQuery)) {


            pstmt1.setString(1, product.getName());
            pstmt1.setString(2, product.getDescription());
            pstmt1.setFloat(3, product.getPrice());
            pstmt1.setString(4, product.getURL());
            pstmt1.setString(5, product.getPictureURL());
            pstmt1.setString(6, product.getMaterial());
            pstmt1.setString(7, String.valueOf(product.getSeason()));
            pstmt1.executeUpdate();
            System.out.println("Product inserted.");


            for (Size size : sizes) {
                pstmt2.setLong(1, productId);
                pstmt2.setInt(2, colorId);
                pstmt2.setInt(3, size.getDbValue());
                pstmt2.setInt(4, stockBySize.get(size));
                pstmt2.addBatch();
            }
            pstmt2.executeBatch();
            System.out.println("Sizes inserted.");

            for (Occasion occasion : occasions) {
                pstmt3.setLong(1, productId);
                pstmt3.setInt(2, occasion.getDbValue());
                pstmt3.addBatch();
            }
            pstmt3.executeBatch();
            System.out.println("Occasions inserted.");

            System.out.println("Transaction committed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add product", e);
        }
    }


    @Override
    public Product getProductById(Connection con, long id) {
        Product p = new Product();

        String productQuery = """
                SELECT * 
                FROM OutfitStore.products
                WHERE id = ?
                """;

        String productSizesQuery = """
                SELECT * 
                FROM OutfitStore.productSizes
                WHERE product_id = ?
                """;

        String productOccasionsQuery = """
                SELECT * 
                FROM OutfitStore.productOccasions
                WHERE product_id = ?
                """;


        try (PreparedStatement pstmt1 = con.prepareStatement(productQuery);
             PreparedStatement pstmt2 = con.prepareStatement(productSizesQuery);
             PreparedStatement pstmt3 = con.prepareStatement(productOccasionsQuery)) {

            pstmt1.setLong(1, id);

            ResultSet rs1 = pstmt1.executeQuery();

            while (rs1.next()) {
                p.setId(rs1.getLong(1));
                p.setName(rs1.getString(2));
                p.setDescription(rs1.getString(3));
                p.setMaterial(rs1.getString(4));
                p.setSeason(Season.valueOf(rs1.getString(5)));
                p.setPrice(rs1.getFloat(6));
                p.setURL(rs1.getString(7));
                p.setPictureURL(rs1.getString(8));
            }
            rs1.close();


            Map<Size, Integer> stockBySize = new HashMap<>();

            pstmt2.setLong(1, id);
            ResultSet rs2 = pstmt2.executeQuery();

            while (rs2.next()) {
                p.setItemColor(ItemColor.fromDbValue(rs2.getInt(2)));
                Size size = Size.fromDbValue(rs2.getInt(3));
                int quantity = rs2.getInt(4);
                stockBySize.put(size, quantity);
            }

            p.setStockBySize(stockBySize);
            rs2.close();


            List<Occasion> occasions = new ArrayList<>();

            pstmt3.setLong(1, id);
            ResultSet rs3 = pstmt3.executeQuery();

            while (rs3.next()) {
                occasions.add(Occasion.fromDbValue(rs3.getInt(2)));
            }
            rs3.close();

            p.setOccasions(occasions);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return p;
    }


    @Override
    public Product getProductByName(Connection con, String name) {
        String productQuery = """
                SELECT * 
                FROM OutfitStore.product
                WHERE name = ?
                """;

        Product p;
        try (PreparedStatement pstmt = con.prepareStatement(productQuery)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            long id = 0;

            while (rs.next()) {
                id = rs.getLong("id");
            }

            p = getProductById(con, id);
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return p;
    }


    @Override
    public void updateProduct(Connection con, long id, Product updatedProduct, Product oldProduct) {
        Map<Size, Integer> newStockBySize = updatedProduct.getStockBySize();
        Set<Size> newSizes = newStockBySize.keySet();
        List<Occasion> newOccasions = updatedProduct.getOccasions();

        Map<Size, Integer> oldStockBySize = updatedProduct.getStockBySize();
        List<Occasion> oldOccasions = updatedProduct.getOccasions();

        String productQuery = """
                UPDATE OutfitStore.products
                SET name = ?, description = ?, price = ?, url = ?, picture_url = ?, material = ?, season = ?
                WHERE id = ?;
                """;

        String productSizesQuery = """
                UPDATE OutfitStore.productSizes
                SET = color_id = ?, size_id = ?, stock  = ?
                WHERE product_id = ?;  
                """;

        String productOccasionsQuery = """
                UPDATE OutfitStore.productOccasions
                SET occasion_id = ?
                WHERE product_id = ?;
                """;


        try (PreparedStatement pstmt1 = con.prepareStatement(productQuery);
             PreparedStatement pstmt2 = con.prepareStatement(productSizesQuery);
             PreparedStatement pstmt3 = con.prepareStatement(productOccasionsQuery)) {

            if (!updatedProduct.getName().equals(oldProduct.getName()) ||
                    !updatedProduct.getDescription().equals(oldProduct.getDescription()) ||
                    updatedProduct.getPrice() != oldProduct.getPrice() ||
                    !updatedProduct.getURL().equals(oldProduct.getURL()) ||
                    !updatedProduct.getPictureURL().equals(oldProduct.getPictureURL()) ||
                    !updatedProduct.getMaterial().equals(oldProduct.getMaterial()) ||
                    !updatedProduct.getSeason().equals(oldProduct.getSeason())
            ) {
                pstmt1.setString(1, updatedProduct.getName());
                pstmt1.setString(2, updatedProduct.getDescription());
                pstmt1.setDouble(3, updatedProduct.getPrice());
                pstmt1.setString(4, updatedProduct.getURL());
                pstmt1.setString(5, updatedProduct.getPictureURL());
                pstmt1.setString(6, updatedProduct.getMaterial());
                pstmt1.setString(7, String.valueOf(updatedProduct.getSeason()));
                pstmt1.setLong(8, id);

                pstmt1.executeUpdate();
            }

            for (Size size : newSizes) {
                Integer newStock = newStockBySize.get(size);
                Integer oldStock = oldStockBySize.get(size);
                if (oldStock == null || !oldStock.equals(newStock)) {
                    pstmt2.setInt(1, updatedProduct.getItemColor().getDbValue());
                    pstmt2.setInt(2, size.getDbValue());
                    pstmt2.setInt(3, newStock);
                    pstmt2.setLong(4, id);
                    pstmt2.setInt(5, size.getDbValue());
                    pstmt2.executeUpdate();
                }
            }

            for (Occasion occasion : newOccasions) {
                if (!oldOccasions.contains(occasion)) {
                    pstmt3.setInt(1, occasion.getDbValue());
                    pstmt3.setLong(2, id);
                    pstmt3.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update the product: " + e.getMessage(), e);
        }

    }


    @Override
    public void deleteProduct(Connection con, long id) {
        String productsQuery = """
                DELETE FROM OutfitStore.products
                WHERE id = ? 
                """;
        String productSizesQuery = """
                DELETE FROM OutfitStore.productSizes
                WHERE product_id = ? 
                """;
        String productOccasionsQuery = """
                DELETE FROM OutfitStore.productOccasions
                WHERE product_id  = ? 
                """;


        try (PreparedStatement pstmt1 = con.prepareStatement(productsQuery);
             PreparedStatement pstmt2 = con.prepareStatement(productSizesQuery);
             PreparedStatement pstmt3 = con.prepareStatement(productOccasionsQuery)) {

            pstmt1.setLong(1, id);
            pstmt1.executeUpdate();

            pstmt2.setLong(1, id);
            pstmt2.executeUpdate();

            pstmt3.setLong(1, id);
            pstmt3.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("The product could not be deleted: " + e.getMessage(), e);
        }
    }


    @Override
    public long getMaxProductId(Connection con) {
        long id = 0;
        String query = """ 
                SELECT id
                FROM OutfitStore.products
                ORDER BY id DESC
                LIMIT 1;
                """;


        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                id = rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve max product id: " + e.getMessage(), e);
        }
        return id;
    }

    @Override
    public List<Product> getAllProducts(Connection con) {
        List<Product> products = new ArrayList<>();

        String productsQuery = """
                SELECT *
                FROM OutfitStore.products
                """;

        String productSizesQuery =
                """
                        SELECT * 
                        FROM OutfitStore.productSizes
                        WHERE product_id = ?
                        """;
        String productOccasionsQuery =
                """
                        SELECT * 
                        FROM OutfitStore.productOccasions
                        WHERE product_id = ?
                        """;


        try (Statement stmt1 = con.createStatement()) {
            ResultSet rs1 = stmt1.executeQuery(productsQuery);

            while (rs1.next()) {
                long id = rs1.getLong("id");
                String name = rs1.getString("name");
                String description = rs1.getString("description");
                String material = rs1.getString("material");
                Season season = Season.valueOf(rs1.getString("season"));
                float price = rs1.getFloat("price");
                String url = rs1.getString("url");
                String pictureUrl = rs1.getString("picture_url");

                Product p = new Product();
                p.setId(id);
                p.setName(name);
                p.setDescription(description);
                p.setMaterial(material);
                p.setSeason(season);
                p.setPrice(price);
                p.setURL(url);
                p.setPictureURL(pictureUrl);


                try (PreparedStatement pstmt2 = con.prepareStatement(productSizesQuery)) {
                    pstmt2.setLong(1, id);
                    ResultSet rs2 = pstmt2.executeQuery();

                    Map<Size, Integer> stockBySize = new HashMap<>();
                    while (rs2.next()) {
                        ItemColor color = ItemColor.fromDbValue(rs2.getInt("color_id"));
                        Size size = Size.fromDbValue(rs2.getInt("size_id"));
                        int quantity = rs2.getInt("stock");

                        p.setItemColor(color);
                        stockBySize.put(size, quantity);
                    }
                    p.setStockBySize(stockBySize);
                    rs2.close();
                }

                try (PreparedStatement pstmt3 = con.prepareStatement(productOccasionsQuery)) {
                    pstmt3.setLong(1, id);
                    ResultSet rs3 = pstmt3.executeQuery();

                    List<Occasion> occasions = new ArrayList<>();
                    while (rs3.next()) {
                        Occasion occasion = Occasion.fromDbValue(rs3.getInt("occasion_id"));
                        occasions.add(occasion);
                    }
                    p.setOccasions(occasions);
                    rs3.close();
                }

                products.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }
}
