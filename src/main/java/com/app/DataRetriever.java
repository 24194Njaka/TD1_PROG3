package com.app;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class DataRetriever {

    private final Bdconnection db = new Bdconnection();

    // 1. Récupérer toutes les catégories
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();

        String sql = "SELECT id, name FROM product_category";
        try (Connection con = db.getDBConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                list.add(new Category(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    // 2. Pagination
    public List<Product> getProductList(int page, int size) {
        List<Product> list = new ArrayList<>();

        int offset = (page - 1) * size;

        String sql = "SELECT id, name, price, creation_datetime "
                + "FROM product ORDER BY id LIMIT ? OFFSET ?";

        try (Connection con = db.getDBConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, size);
            st.setInt(2, offset);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getTimestamp("creation_datetime").toInstant()
                ));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    // 3. Filtre multi-critères
    public List<Product> getProductsByCriteria(
            String productName,
            String categoryName,
            Instant creationMin,
            Instant creationMax
    ) {
        String base = """
                SELECT p.id, p.name, p.price, p.creation_datetime
                FROM product p
                LEFT JOIN product_category c ON c.product_id = p.id
                WHERE 1=1
                """;

        List<Object> params = new ArrayList<>();

        if (productName != null) {
            base += " AND p.name ILIKE ?";
            params.add("%" + productName + "%");
        }
        if (categoryName != null) {
            base += " AND c.name ILIKE ?";
            params.add("%" + categoryName + "%");
        }
        if (creationMin != null) {
            base += " AND p.creation_datetime >= ?";
            params.add(Timestamp.from(creationMin));
        }
        if (creationMax != null) {
            base += " AND p.creation_datetime <= ?";
            params.add(Timestamp.from(creationMax));
        }

        List<Product> list = new ArrayList<>();

        try (Connection con = db.getDBConnection();
             PreparedStatement st = con.prepareStatement(base)) {

            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getTimestamp("creation_datetime").toInstant()
                ));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return list;
    }
}

