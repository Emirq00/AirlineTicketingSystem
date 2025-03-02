package SistemaDeMenus;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

import Tickets.FormatoTickets.Vuelo;

/**
 * Plantilla general de los diferentes tipos de menú que compartiran las 2 variables de clase tanto para ingresar datos como para manejar
 * excepciones dentro de los métodos.
 */
public class Menu {
    protected static Scanner scanner = new Scanner(System.in);
    protected static boolean incorrectEntry;
    protected static HashMap<Integer, Vuelo> vuelosDisponibles = new HashMap<>();
    
    /**
     * Método con el que vamos a recuperar todos los objetos de tipo vuelo que hemos almacenado en el archivo "Vuelos". la estructura 
     * de cómo se almacenan los vuelos en el archivo es mediante un hashmap, donde la llave es un entero y el valor es un objeto de tipo vuelo
     * <p>
     * HASHMAP '<'NUMERO DEL VUELO SELECCIOANDO EN EL MENÚ, objeto de tipo vuelo asociado a ese número del menú'>'
     */
    public static void consultarTodosLosVuelosArchivo(){
        ObjectInputStream archivoObjetos = null;
        try {
            archivoObjetos = new ObjectInputStream(new FileInputStream("Vuelos"));
            int i = 1;
            while (true) {
                try {
                    vuelosDisponibles.put(i, (Vuelo)archivoObjetos.readObject());
                    i++;
                } catch (EOFException e) {
                    // Romper ciclo cuando se alcanza el final del archivo
                    break;
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (archivoObjetos != null) {
                    archivoObjetos.close();
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

}
