package org.example;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

public class CreatePdf {

    private PDDocument document;

    public CreatePdf() {
        document = new PDDocument();
    }

    // agregar página al PDF
    public void addPage(String content) throws IOException {
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.beginText();

        File fontFile = new File("src/main/resources/arial.ttf");
        PDType0Font font = PDType0Font.load(document, fontFile);

        contentStream.setFont(font, 12);
        contentStream.newLineAtOffset(25, 700);

        //filtra caracteres invisibles
        content = removeInvisibleChars(content);


        String[] lines = content.split("\n");

        for (String line : lines) {
            contentStream.showText(line.trim());
            contentStream.newLineAtOffset(0, -15);
        }

        contentStream.endText();
        contentStream.close();
    }

    //guardar el PDF
    public void savePdf(String outputPath) throws IOException {
        document.save(outputPath);
        System.out.println("PDF created at: " + outputPath);
        document.close();
    }

    //eliminar caracteres de control menos '\n'
    private String removeInvisibleChars(String content) {
        return content.replaceAll("[\\p{C}&&[^\\n]]", "");
    }
}

