package org.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            String templatePath = "src/main/resources/template.txt";
            String csvPath = "src/main/resources/elementos.csv";

            TemplateProcessor templateProcessor = new TemplateProcessor();
            templateProcessor.loadTemplate(templatePath);

            // leer CSV
            String[][] csvData = Reader.leerArchivo(csvPath);

            // verificar que el CSV tenga los encabezados y datos
            validateCSV(csvData);

            // instancia de CreatePdf
            CreatePdf pdfCreator = new CreatePdf();

            // procesa cada fila del CSV y agrega una página al PDF
            for (int i = 1; i < csvData.length; i++) {  // se omiten encabezados
                Map<String, String> replacements = new HashMap<>();

                // se usa la primera fila del CSV como nombres de identificadores
                for (int j = 0; j < csvData[i].length; j++) {
                    String identifier = csvData[0][j]; // identifica si es destinatario/remitente
                    replacements.put(identifier, csvData[i][j]);  //reemplazo (clave/valor)
                }

                // reemplazar identificadores en el template
                String processedContent = templateProcessor.replaceIdentifiers(replacements);

                // agregar página al PDF con el contenido procesado
                pdfCreator.addPage(processedContent);
            }

            // guardar el PDF con todas las páginas agregadas
            String outputPath = "src/main/resources/prueba.pdf";
            pdfCreator.savePdf(outputPath);

            //manejo de excepciones
        } catch (IOException e) {
            System.err.println("Error al leer archivos: " + e.getMessage());
        } catch (CSVHeaderMissingException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (CSVNoDataException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // validar que el CSV tiene encabezados y fila de datos
    private static void validateCSV(String[][] csvData) throws CSVHeaderMissingException, CSVNoDataException {
        if (csvData.length == 0) {
            throw new CSVHeaderMissingException("El archivo CSV está vacío.");
        }

        String[] headers = csvData[0];
        boolean hasDestinatario = false;
        boolean hasRemitente = false;

        // verificar que existan los encabezados
        for (String header : headers) {
            if (header.equalsIgnoreCase("destinatario")) {
                hasDestinatario = true;
            }
            if (header.equalsIgnoreCase("remitente")) {
                hasRemitente = true;
            }
        }

        if (!hasDestinatario || !hasRemitente) {
            throw new CSVHeaderMissingException("El archivo CSV debe contener los encabezados (destinatario/remitente)");
        }

        // verificar quue tenga más filas además de los encabezados
        if (csvData.length == 1) {
            throw new CSVNoDataException("El archivo CSV solo contiene los encabezados");
        }
    }
}
