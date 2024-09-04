package org.example;

public class CSVExceptions extends Exception {
    // CSVHeaderMissingException   Faltan encabezados en el CSV
    // CSVNoDataException           CSV solo tiene los encabezados
    // CSVEmptyLineException        Línea vacía

    public CSVExceptions(String message) {
        super(message);
    }
}
