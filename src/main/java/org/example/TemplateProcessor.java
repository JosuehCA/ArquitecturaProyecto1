package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateProcessor {

    private String templateContent;

    public void loadTemplate(String filePath) throws IOException {
        templateContent = new String(Files.readAllBytes(Paths.get(filePath)), "UTF-8");
    }

    // reemplazar los identificadores en el template
    public String replaceIdentifiers(Map<String, String> replacements) {
        String processedContent = templateContent;

        // reemplazar cada identificador con valores del CSV /ignora espacios en blanco
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            String identifier = entry.getKey();
            String value = entry.getValue();

            //identificadores que pueden tener espacios en blanco
            String regex = "<\\s*" + Pattern.quote(identifier) + "\\s*>";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(processedContent);
            //reemplaza lo que coincida con el patrón
            processedContent = matcher.replaceAll(value);
        }

        return processedContent;
    }
}

