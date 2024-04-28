package ServiceImpl;

import Service.DDMSService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a service implementation for interacting with a database management system.
 */
public class DDMSServiceImpl implements DDMSService {
    private final Repository.DDBMSRepository ddbmsRepository;

    /**
     * Constructs a new DDMSServiceImpl with the specified DDBMSRepository.
     *
     * @param ddbmsRepository the DDBMSRepository used for database operations
     */
    public DDMSServiceImpl(Repository.DDBMSRepository ddbmsRepository) {
        this.ddbmsRepository = ddbmsRepository;
    }

    /**
     * Executes the specified SQL query and prints the results if it is a SELECT statement,
     * or prints the number of rows affected if it is an INSERT, UPDATE, or DELETE statement.
     *
     * @param query the SQL query to execute
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void executeQuery(String query) throws SQLException {
        Connection connection = ddbmsRepository.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            boolean isResultSet = preparedStatement.execute();
            if (isResultSet) {
                ResultSet resultSet = preparedStatement.getResultSet();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Print column names with proper formatting
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    System.out.printf("%-15s", columnName); // Adjust the width as needed
                }
                System.out.println();

                // Print rows with proper formatting
                while (resultSet.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        String columnValue = resultSet.getString(i);
                        System.out.printf("%-15s", columnValue); // Adjust the width as needed
                    }
                    System.out.println();
                }
            } else {
                // Print the number of rows affected
                int rowsAffected = preparedStatement.getUpdateCount();
                System.out.println("Rows affected: " + rowsAffected);
            }
        }
    }
}
