package ServiceImpl;

import Service.FileReaderService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Represents a service implementation for reading and parsing GDC files.
 */
public class FileReaderServiceImpl implements FileReaderService {

    /**
     * Parses the GDC file located at the specified file path and returns a map containing
     * tables grouped by VM names.
     *
     * @param filePath the path to the GDC file
     * @return a map containing tables grouped by VM names
     * @throws IOException if an I/O error occurs while reading the file
     */
    @Override
    public Map<String, List<String>> parseGDCFile(String filePath) throws IOException {
        Map<String, List<String>> tablesByVM = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\$");

                // Extract VM name and table names
                String vmName = parts[1];
                String[] tableNames = parts[4].split("\\|");

                // Add table names to the list for the corresponding VM
                List<String> tables = tablesByVM.getOrDefault(vmName, new ArrayList<>());
                for (String tableName : tableNames) {
                    tables.add(tableName.trim());
                }
                tablesByVM.put(vmName, tables);
            }
        }

        return tablesByVM;
    }
}
