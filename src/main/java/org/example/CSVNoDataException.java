package org.example;

// CSV solo tiene los encabezados
public class CSVNoDataException extends Exception {
    public CSVNoDataException(String message) {
        super(message);
    }
}
