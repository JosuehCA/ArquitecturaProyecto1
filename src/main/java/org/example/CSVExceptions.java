package org.example;

public class CSVExceptions {

    // faltan encabezados en el CSV
    public static class CSVHeaderMissingException extends Exception {
        public CSVHeaderMissingException(String message) {
            super(message);

        }
    }

    // CSV solo tiene los encabezados
    public static class CSVNoDataException extends Exception {
        public CSVNoDataException(String message) {
            super(message);
        }
    }

    public static class CSVEmptyLineException extends Exception {
        public CSVEmptyLineException(String message) {
            super(message);
        }
    }
}
