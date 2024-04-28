package Service;

import java.sql.SQLException;

/**
 * Represents a service for executing queries on a database management system.
 */
public interface DDMSService {
    /**
     * Executes the specified SQL query.
     *
     * @param query the SQL query to execute
     * @throws SQLException if a database access error occurs
     */
    void executeQuery(String query) throws SQLException;
}
