package org.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.example.TemplateExceptions;
import static org.example.Reader.validateCSV;

public class Main {

    public static void main(String[] args) {
        try {
            String templatePath = "src/main/resources/template.txt";
            String csvPath = "src/main/resources/elementos.csv";

            TemplateProcessor templateProcessor = new TemplateProcessor();
            templateProcessor.loadTemplate(templatePath);

            // leer CSV
            String[][] csvData = Reader.leerArchivo(csvPath);

            // Detectar identificadores en el template
            templateProcessor.detectIdentifiers();

            // verificar que el CSV tenga los encabezados y datos
            validateCSV(csvData, templateProcessor.getIdentifiers());

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
        } catch (TemplateExceptions e){
            System.err.println("Error: " + e.getMessage());
        } catch (CSVExceptions e) {
            throw new RuntimeException(e);
        }
    }
}