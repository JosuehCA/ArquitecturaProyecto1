package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class TemplateProcessor {

    private String templateContent;

    public void loadTemplate(String filePath) throws IOException {
        templateContent = new String(Files.readAllBytes(Paths.get(filePath)), "UTF-8");
    }

    // reemplazar los identificadores en el template
    public String replaceIdentifiers(Map<String, String> replacements) {
        String processedContent = templateContent;

        // Reemplazar cada identificador con valores del CSV
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            processedContent = processedContent.replace("<" + entry.getKey() + ">", entry.getValue());
        }

        return processedContent;
    }
}

