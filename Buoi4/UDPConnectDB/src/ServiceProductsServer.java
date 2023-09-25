import java.sql.*;

public class ServiceProductsServer {
    private Connection connection;
    private Statement statement;

    public ServiceProducts() throws SQLException {
        connectToDatabase();
    }

    private void connectToDatabase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/product_db";
        String username = "root";
        String password = "123123";

        connection = DriverManager.getConnection(url, username, password);
        statement = connection.createStatement();
    }

    public void addProduct(String productName, String productCode, double productPrice) throws SQLException {
        String query = String.format("INSERT INTO products (product_name, product_code, product_price) VALUES ('%s', '%s', %f)",
                productName, productCode, productPrice);
        statement.executeUpdate(query);
    }

    public void updateProduct(int productId, String productName, String productCode, double productPrice) throws SQLException {
        String query = String.format("UPDATE products SET product_name = '%s', product_code = '%s', product_price = %f WHERE id = %d",
                productName, productCode, productPrice, productId);
        statement.executeUpdate(query);
    }

    public void deleteProduct(int productId) throws SQLException {
        String query = "DELETE FROM products WHERE id = " + productId;
        statement.executeUpdate(query);
    }

    public String searchProductById(int productId) throws SQLException {
        String query = "SELECT * FROM products WHERE id = " + productId;
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String productName = resultSet.getString("product_name");
            String productCode = resultSet.getString("product_code");
            double productPrice = resultSet.getDouble("product_price");

            return String.format("Product ID: %d, Name: %s, Code: %s, Price: %f", id, productName, productCode, productPrice);
        } else {
            return "Product not found for the given ID.";
        }
    }
}
