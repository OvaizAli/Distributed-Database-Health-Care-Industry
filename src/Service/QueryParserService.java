package Service;

/**
 * Represents a service for parsing SQL queries to extract table names.
 */
public interface QueryParserService {
    /**
     * Finds the table name mentioned in the provided SQL query.
     *
     * @param query the SQL query
     * @return the name of the table referenced in the query
     */
    String findTableInQuery(String query);
}
