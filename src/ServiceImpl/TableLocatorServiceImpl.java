package ServiceImpl;

import Service.TableLocatorService;

import java.util.List;
import java.util.Map;

/**
 * Represents a service implementation for locating the virtual machine (VM) associated with a given table.
 */
public class TableLocatorServiceImpl implements TableLocatorService {

    /**
     * Determines the virtual machine (VM) associated with the specified table.
     *
     * @param tablesByVM a mapping of VM names to lists of tables
     * @param tableName  the name of the table to locate
     * @return the name of the VM associated with the table, or null if not found
     */
    @Override
    public String determineVMForTable(Map<String, List<String>> tablesByVM, String tableName) {
        for (Map.Entry<String, List<String>> entry : tablesByVM.entrySet()) {
            String vm = entry.getKey();
            List<String> tables = entry.getValue();
            if (tables.contains(tableName)) {
                return vm;
            }
        }
        return null;
    }
}
