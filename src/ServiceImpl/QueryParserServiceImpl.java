package ServiceImpl;

import Service.QueryParserService;

/**
 * Represents a service implementation for parsing SQL queries to extract table names.
 */
public class QueryParserServiceImpl implements QueryParserService {

    /**
     * Finds and returns the table name mentioned in the provided SQL query.
     *
     * @param query the SQL query
     * @return the table name extracted from the query, or null if not found
     */
    @Override
    public String findTableInQuery(String query) {
        String[] tokens = query.split("\\s+");
        String tableName = null;

        // Identify the type of SQL statement
        String sqlType = tokens[0].toUpperCase(); // Get the first token and convert to uppercase

        switch (sqlType) {
            case "SELECT":
                // For SELECT statement, find the table name after the "FROM" keyword
                for (int i = 0; i < tokens.length; i++) {
                    if (tokens[i].equalsIgnoreCase("FROM") && i + 1 < tokens.length) {
                        tableName = tokens[i + 1].replaceAll("[;,]+$", "");
                        if (tableName.startsWith("`") || tableName.startsWith("'")) {
                            tableName = tableName.substring(1);
                        }
                        if (tableName.endsWith("`") || tableName.endsWith("'")) {
                            tableName = tableName.substring(0, tableName.length() - 1);
                        }
                        break;
                    }
                }
                break;
            case "INSERT":
                // For INSERT statement, the table name is immediately after "INTO"
                for (int i = 0; i < tokens.length; i++) {
                    if (tokens[i].equalsIgnoreCase("INTO") && i + 1 < tokens.length) {
                        tableName = tokens[i + 1].replaceAll("[;,]+$", "");
                        break;
                    }
                }
                break;
            case "UPDATE":
                // For UPDATE statement, the table name is immediately after "UPDATE"
                tableName = tokens[1].replaceAll("[;,]+$", "");
                break;
            case "DELETE":
                // For DELETE statement, the table name is immediately after "FROM"
                tableName = tokens[2].replaceAll("[;,]+$", "");
                break;
            default:
                // Unsupported SQL statement type
                System.out.println("Unsupported SQL statement type.");
        }

        return tableName;
    }
}
