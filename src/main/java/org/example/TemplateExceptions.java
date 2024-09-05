package org.example;

public class TemplateExceptions {
    public static class MultipleBracketsException extends Exception {
        public MultipleBracketsException(String message) {
            super(message);
        }
    }
}
