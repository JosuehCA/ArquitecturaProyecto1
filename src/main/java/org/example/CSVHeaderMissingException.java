package org.example;

// faltan encabezados en el CSV
public class CSVHeaderMissingException extends Exception {
    public CSVHeaderMissingException(String message) {
        super(message);
    }
}
