import Repository.DDBMSRepository;
import Service.DDMSService;
import Service.FileReaderService;
import Service.QueryParserService;
import Service.TableLocatorService;
import ServiceImpl.DDMSServiceImpl;
import ServiceImpl.FileReaderServiceImpl;
import ServiceImpl.QueryParserServiceImpl;
import ServiceImpl.TableLocatorServiceImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * This class represents the main entry point of the application.
 */
public class Main {

    /**
     * The main method of the application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        // File paths and configurations
        String config1Path = "config1.properties";
        String config2Path = "config2.properties";
        String gdcFilePath = "GDC.txt";

        try {
            // Initialize FileReaderService to parse the GDC file
            FileReaderService fileReaderService = new FileReaderServiceImpl();
            // Parse the GDC file to get tables by VM
            Map<String, List<String>> tablesByVM = fileReaderService.parseGDCFile(gdcFilePath);

            // Initialize Scanner to get user input
            Scanner scanner = new Scanner(System.in);

            // Loop to continuously prompt for SQL queries until user exits
            while (true) {
                System.out.println("Enter the SQL query (type 'exit' to end):");
                String query = scanner.nextLine();

                if (query.trim().equalsIgnoreCase("exit")) {
                    System.out.println("Exiting...");
                    break;
                }

                // Initialize QueryParserService to find the table name in the query
                QueryParserService queryParserService = new QueryParserServiceImpl();
                String tableName = queryParserService.findTableInQuery(query);

                // Initialize TableLocatorService to determine the VM for the table
                TableLocatorService tableLocatorService = new TableLocatorServiceImpl();
                String vmForTable = tableLocatorService.determineVMForTable(tablesByVM, tableName);

                if (vmForTable != null) {
                    // Establish database connection and execute the query
                    System.out.println("The table '" + tableName + "' exists in VM: " + vmForTable);
                    establishConnection(config1Path, config2Path, vmForTable, query);
                } else {
                    // Table not found in the GDC file
                    System.out.println("Table '" + tableName + "' not found in the GDC file.");
                }

                System.out.println("\n");
            }
        } catch (IOException e) {
            // Failed to parse the GDC file
            System.err.println("Failed to parse the GDC file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Establishes a database connection and executes the SQL query.
     *
     * @param config1Path The path to the first configuration file.
     * @param config2Path The path to the second configuration file.
     * @param vm          The VM to connect to.
     * @param query       The SQL query to execute.
     */
    private static void establishConnection(String config1Path, String config2Path, String vm, String query) {
        Properties properties = new Properties();
        // Determine the configuration file path based on the VM
        String configPath = vm.equals("vmysql1") ? config1Path : config2Path;
        try {
            // Load JDBC properties from the configuration file
            properties.load(new FileInputStream(configPath));
            String jdbcUrl = properties.getProperty("jdbc.url");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");
            // Initialize DDBMSRepository with JDBC properties
            DDBMSRepository repository = new DDBMSRepository(jdbcUrl, username, password);
            // Initialize DDMSService to execute the query
            DDMSService ddmsService = new DDMSServiceImpl(repository);
            ddmsService.executeQuery(query);
        } catch (IOException | SQLException e) {
            // Failed to establish database connection
            System.err.println("Failed to establish database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
