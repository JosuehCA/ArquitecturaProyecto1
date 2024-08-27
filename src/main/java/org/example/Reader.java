package org.example;

import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
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

    public static void leerMatriz(String[][] arreglo) {
        for (int i = 0; i < arreglo.length; i++) {
            System.out.println("");
            System.out.println("Registro " + (i+1) + "\n");
            for (int j = 0; j < arreglo[i].length; j++) {
                System.out.println(arreglo[i][j]);;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //String[][] arregloleido = leerArchivo("src/main/resources/elementos.csv");
        String[][] arregloleido = leerArchivo("src/main/resources/elementos.csv");
        leerMatriz(arregloleido);
    }
}