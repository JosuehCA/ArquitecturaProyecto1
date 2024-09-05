package org.example;
public class CSVExceptions extends Exception {
    // CSVHeaderMissingException   Faltan encabezados en el CSV
    // CSVNoDataException           CSV solo tiene los encabezados

    public CSVExceptions(String message) {
        super(message);
    }
}
