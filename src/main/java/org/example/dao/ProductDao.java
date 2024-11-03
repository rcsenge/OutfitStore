package org.example.dao;
import org.example.model.Product;

import java.sql.Connection;
import java.util.List;

public interface ProductDao {
    void addProduct(Connection con, Product product);

    Product getProductById(Connection con, long id);

    long getMaxProductId(Connection con);

    Product getProductByName(Connection con, String name);

    void updateProduct(Connection con, long id, Product updatedProduct, Product oldProduct);

    void deleteProduct(Connection con, long id);
    List<Product> getAllProducts(Connection con);
}
