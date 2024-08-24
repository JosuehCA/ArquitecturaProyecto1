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
        while((fila = csvReader.readNext()) != null) {}
        int registros = (int)csvReader.getLinesRead();
        csvReader.close();
        String[][] arreglo = new String[registros][2];
        csvReader = new CSVReader(new FileReader(archivo));
        int i = 0;
        while((fila = csvReader.readNext()) != null) {
            for(int j = 0; j<2; j++) {
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
        String[][] arregloleido = leerArchivo("src/main/resources/elementos.csv");
        leerMatriz(arregloleido);
    }
}