package org.example;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.example.CSVExceptions;
import org.example.CSVExceptions;

public class Reader {
    public static String[][] leerArchivo(String path) throws IOException, CSVExceptions {
        String archivo = path;
        CSVReader csvReader = new CSVReader(new FileReader(archivo));
        String[] fila = null;
        boolean hasEmptyLines = false;
        int dataLines = 0;

        // contar líneas y verificar si hay líneas vacías
        while ((fila = csvReader.readNext()) != null) {
            if (isEmptyRow(fila)) {
                hasEmptyLines = true;
            } else {
                dataLines++;
            }
        }
        csvReader.close();

        //si esta vacío
        if (dataLines == 0) {
            throw new CSVExceptions("El archivo CSV está vacío.");
        }

        //si hay líneas vacías
        if (hasEmptyLines) {
            throw new CSVExceptions("El archivo CSV contiene filas vacías.");
        }


        String[][] arreglo = new String[dataLines][2];
        csvReader = new CSVReader(new FileReader(archivo));
        int i = 0;
        while ((fila = csvReader.readNext()) != null) {
            for (int j = 0; j < 2; j++) {
                if (j < fila.length) {
                    arreglo[i][j] = fila[j];
                }
            }
            i++;
        }
        csvReader.close();
        return arreglo;
    }

    // verificar si una fila está vacía
    private static boolean isEmptyRow(String[] fila) {
        if (fila == null) {
            return true;
        }
        for (String item : fila) {
            if (item != null && !item.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    // validar que el CSV tiene encabezados y fila de datos
    public static void validateCSV(String[][] csvData, ArrayList<String> identifiers) throws CSVExceptions {

        String[] headers = csvData[0];
        boolean hasDestinatario = false;
        boolean hasRemitente = false;

        // verificar que existan los encabezados
        for (String header : headers) {
            if (header.equalsIgnoreCase(identifiers.get(0))) {
                hasDestinatario = true;
            }
            if (header.equalsIgnoreCase(identifiers.get(1))) {
                hasRemitente = true;
            }
        }

        if (!hasDestinatario || !hasRemitente) {
            throw new CSVExceptions("El archivo CSV debe contener los encabezados (destinatario/remitente)");
        }

        // verificar quue tenga más filas además de los encabezados
        if (csvData.length == 1) {
            throw new CSVExceptions("El archivo CSV solo contiene los encabezados");
        }
    }
}