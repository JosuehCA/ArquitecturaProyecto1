package org.example;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
    public static String[][] leerArchivo(String path) throws IOException {
        String archivo = path;
        CSVReader csvReader = new CSVReader(new FileReader(archivo));
        String[] fila = null;

        // lista para filas no vacías
        int registros = 0;
        while ((fila = csvReader.readNext()) != null) {
            // saltar filas vacías
            if (fila.length == 0 || (fila.length == 1 && fila[0].trim().isEmpty())) {
                continue;
            }
            registros++;
        }
        csvReader.close();

        String[][] arreglo = new String[registros][2];
        csvReader = new CSVReader(new FileReader(archivo));
        int i = 0;
        while ((fila = csvReader.readNext()) != null) {
            // saltar filas vacías
            if (fila.length == 0 || (fila.length == 1 && fila[0].trim().isEmpty())) {
                continue;
            }
            for (int j = 0; j < 2; j++) {
                arreglo[i][j] = fila[j];
            }
            i++;
        }
        csvReader.close();
        return arreglo;
    }

    // validar que el CSV tiene encabezados y fila de datos
    public static void validateCSV(String[][] csvData) throws CSVExceptions {
        if (csvData.length == 0) {
            throw new CSVExceptions("El archivo CSV está vacío.");
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
            throw new CSVExceptions("El archivo CSV debe contener los encabezados (destinatario/remitente)");
        }

        // verificar quue tenga más filas además de los encabezados
        if (csvData.length == 1) {
            throw new CSVExceptions("El archivo CSV solo contiene los encabezados");
        }
    }
}