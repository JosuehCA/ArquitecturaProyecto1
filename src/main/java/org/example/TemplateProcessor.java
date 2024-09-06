package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateProcessor {

    private String templateContent;
    private ArrayList<String> identifiers =  new ArrayList<String>();

    public void loadTemplate(String filePath) throws IOException, TemplateExceptions {
        templateContent = new String(Files.readAllBytes(Paths.get(filePath)), "UTF-8");
        //System.out.println(templateContent);
        validateTemplate(templateContent);
    }

    public void detectIdentifiers() throws CSVExceptions {
        Pattern pattern = Pattern.compile("(?<!<)<\\s*[a-zA-Z0-9_]+\\s*>(?!>)");
        Matcher matcher = pattern.matcher(templateContent);

        while(matcher.find()) {
            String token = matcher.group();
            token = token.substring(1, token.length()-1).trim();

            identifiers.add(token);
        }


        for(String token : identifiers) {
            System.out.println("Token: " + token + '\n');
        }

        if(identifiers.isEmpty() || identifiers.size() == 1) {
            throw new CSVExceptions("No se encontraron identificadores suficientes en el template");
        }
    }

    // validar contenido de template
    private void validateTemplate(String content) throws TemplateExceptions {
        // encuentra identificadores con múltiples < >
        Pattern multipleBracketsPattern = Pattern.compile("<{2,}|>{2,}");
        Matcher multipleBracketsMatcher = multipleBracketsPattern.matcher(content);

        // si hay identificador con varios < >
        if (multipleBracketsMatcher.find()) {
            throw new TemplateExceptions("El template contiene múltiples '<' '>'");
        }
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

    public ArrayList<String> getIdentifiers() {
        return identifiers;
    }
}