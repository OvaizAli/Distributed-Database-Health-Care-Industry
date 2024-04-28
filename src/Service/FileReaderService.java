package Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Represents a service for reading and parsing a GDC (Global Data Catalog) file.
 */
public interface FileReaderService {
    /**
     * Parses the GDC file located at the specified file path.
     *
     * @param filePath the path to the GDC file
     * @return a map containing VM names as keys and lists of table names as values
     * @throws IOException if an I/O error occurs while reading the file
     */
    Map<String, List<String>> parseGDCFile(String filePath) throws IOException;
}
