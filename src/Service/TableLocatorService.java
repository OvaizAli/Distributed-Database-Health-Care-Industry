package Service;

import java.util.List;
import java.util.Map;

/**
 * Represents a service for locating the virtual machine (VM) hosting a given table.
 */
public interface TableLocatorService {
    /**
     * Determines the virtual machine (VM) hosting the specified table.
     *
     * @param tablesByVM a mapping of VM names to lists of tables hosted on each VM
     * @param tableName   the name of the table to locate
     * @return the name of the VM hosting the table, or null if the table is not found
     */
    String determineVMForTable(Map<String, List<String>> tablesByVM, String tableName);
}
