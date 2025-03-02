package Organizacion;

import java.io.*;
import java.util.HashMap;
import java.time.LocalDateTime;
import Tickets.FormatoTickets.Vuelo;

public class BaseDeDatosVuelos implements Serializable {
    private static final long serialVersionUID = 1L;
    private HashMap<Integer, Vuelo> baseDeDatosVuelos = new HashMap<>();
    
    public BaseDeDatosVuelos() {
        cargarDeArchivo();
    }
    
    public void agregarVuelo(Vuelo vuelo) {
        int nuevoNumeroVuelo = baseDeDatosVuelos.size() + 1;
        baseDeDatosVuelos.put(nuevoNumeroVuelo, vuelo);
        guardarEnArchivo();
    }

    public void eliminarVuelo(int numeroVuelo) {
        baseDeDatosVuelos.remove(numeroVuelo);
        guardarEnArchivo();
    }

    public Vuelo obtenerVuelo(int numeroVuelo) {
        return baseDeDatosVuelos.get(numeroVuelo);
    }

    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("baseDeDatosVuelos"))) {
            oos.writeObject(baseDeDatosVuelos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarDeArchivo() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("baseDeDatosVuelos"))) {
            baseDeDatosVuelos = (HashMap<Integer, Vuelo>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            baseDeDatosVuelos = new HashMap<>();
        }
    }

    public HashMap<Integer, Vuelo> getBaseDeDatosVuelos() {
        return baseDeDatosVuelos;
    }
}
