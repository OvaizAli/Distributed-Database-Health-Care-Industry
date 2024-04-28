package Repository;

import java.sql.*;

/**
 * Represents a repository for interacting with a database management system.
 */
public class DDBMSRepository {
    private final Connection connection;

    /**
     * Constructs a new DDBMSRepository with the specified JDBC URL, username, and password.
     *
     * @param jdbcUrl  the JDBC URL of the database
     * @param username the username for authentication
     * @param password the password for authentication
     * @throws SQLException if a database access error occurs
     */
    public DDBMSRepository(String jdbcUrl, String username, String password) throws SQLException {
        try {
            this.connection = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            System.err.println("Failed to establish database connection: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Gets the connection associated with this repository.
     *
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Closes the connection associated with this repository.
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to close database connection: " + e.getMessage());
        }
    }
}
