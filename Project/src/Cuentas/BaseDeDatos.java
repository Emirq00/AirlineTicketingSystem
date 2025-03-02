package Cuentas;

import java.io.*;
import java.util.HashMap;

public class BaseDeDatos implements Serializable {
    private static final long serialVersionUID = 1L;
    private HashMap<String, Cliente> baseDeDatosUsuarios = new HashMap<>();
    private HashMap<String, Administrador> baseDeDatosAdministradores = new HashMap<>();
    private Cliente usuario;
        
    public BaseDeDatos() {
        cargarDeArchivo();
        if (baseDeDatosAdministradores.isEmpty()) {
            Administrador admin = new Administrador("Admin", 30, "admin123", "admin@aeroviajes.com");
            agregarAdministrador(admin);
        }
    }

    public Cliente obtenerUsuario(){
        return usuario;
    }

    public void agregarUsuario(Cliente usuario) {
        baseDeDatosUsuarios.put(usuario.getEmail(), usuario);
        guardarEnArchivo();
    }

    public void agregarAdministrador(Administrador administrador) {
        baseDeDatosAdministradores.put(administrador.getEmail(), administrador);
        guardarEnArchivo();
    }

    public void eliminarUsuario(String email) {
        baseDeDatosUsuarios.remove(email);
        guardarEnArchivo();
    }

    public void eliminarAdministrador(String email) {
        baseDeDatosAdministradores.remove(email);
        guardarEnArchivo();
    }

    public Cliente obtenerUsuario(String email) {
        return baseDeDatosUsuarios.get(email);
    }

    public Administrador obtenerAdministrador(String email) {
        return baseDeDatosAdministradores.get(email);
    }

    public boolean validarUsuario(String email, String password) {
        Cliente usuario = obtenerUsuario(email);
        if(usuario == null){
            return false;
        }
        return usuario.getPassword().equals(password);
    }


    public boolean validarAdministrador(String email, String password) {
        Administrador administrador = obtenerAdministrador(email);
        if(administrador == null){
            return false;
        }
        return administrador.getPassword().equals(password);
    }


    public String obtenerRol(String email) {
        if (baseDeDatosAdministradores.containsKey(email)) {
            return "Administrador";
        } else if (baseDeDatosUsuarios.containsKey(email)) {
            return "Usuario";
        } else {
            return "No encontrado";
        }
    }

    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("baseDeDatos"))) {
            oos.writeObject(baseDeDatosUsuarios);
            oos.writeObject(baseDeDatosAdministradores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarDeArchivo() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("baseDeDatos"))) {
            baseDeDatosUsuarios = (HashMap<String, Cliente>) ois.readObject();
            baseDeDatosAdministradores = (HashMap<String, Administrador>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar la base de datos: " + e.getMessage());
            baseDeDatosUsuarios = new HashMap<>();
            baseDeDatosAdministradores = new HashMap<>();
        }
    }


    public HashMap<String, Cliente> getBaseDeDatosUsuarios() {
        return baseDeDatosUsuarios;
    }

    public HashMap<String, Administrador> getBaseDeDatosAdministradores() {
        return baseDeDatosAdministradores;
    }
}
