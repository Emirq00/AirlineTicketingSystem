package SistemaDeMenus;

import java.io.*;

import Cuentas.Cliente;
import Tickets.CreacionTickets.CompraTicket;
import Tickets.FormatoTickets.*;
import java.util.*;

/**
 * Clase donde mostraremos un menú general con las diferentes operaciones que podemos hacer posterior al inicio de sesion, 
 * entre los que estan la consulta de los diferntes vuelos, salir al menu de inicio de sesión, descargar los boletos 
 * los menús, para las implementaciones de los diferentes árboles se manda a llamar un método con un menú secundario.
 */
public class MenuPrincipalCliente extends Menu{

    /**
     * Método main en el que mostramos el menú general con las diferentes opciones. El menú se repite indefinidamente
     * permitiendo al usuario realizar cualquier operación del sistema de manera ininterrumpida, para finalizar el programa el
     * usuario deberá ingresar la opción señalada en el menú como "Salir", permitiendo así romper el bucle while.
     */
    public static void menuUsuario(Cliente cliente) {
        int decision=0;
        //Comenzamos siempre recuperando los vuelos disponibles del archivo de vuelos
        CompraTicket.iniciarVuelosPrueba();
        consultarTodosLosVuelosArchivo();

        do{

            System.out.println("\n======== Bienvenido a nuestra página " +cliente.getNombre()+" ========");
            System.out.println("1.- Mostrar vuelos disponibles");
            System.out.println("2.- Descargar boletos");
            System.out.println("3.- Agregar metodos de pago");
            System.out.println("4.- Ver metodos de pago y saldos");
            System.out.println("5.- Salir");
            do{
                System.out.print("Ingresa tu entrada: ");
                incorrectEntry=false;
                try {
                    decision = scanner.nextInt();
                    if(decision<1 || decision>5){
                        System.out.println("* Ingrese una entrada válida");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("* Ingrese una entrada numérica");
                    scanner.nextLine();
                    incorrectEntry=true;
                }
            } while (incorrectEntry || decision<1 || decision>5);

            switch (decision) {
                case 1->consultaVuelos(cliente);
                case 2->descargarBoletos(cliente);
                case 3->cliente.addMetodoPago(scanner);
                case 4->cliente.verMetodosPago();
                case 5->{
                    System.out.println("Regresando...");
                    return;
                }
                default->System.out.println("* Ingrese una opción válida");
            }
        } while (decision!=5);

        System.err.println("Saliendo...");
    }

    /**
     * Método que muestra todos los vuelos, permitiendo al usuario observar los vuelos disponibles, para ello accederemos a un archivo
     * de objetos en el que anteriormente el administrador habrá guardado los vuelos disponibles, junto con 5 vuelos de ejemplo.
     */
    private static void consultaVuelos(Cliente cliente){

        boolean notRecognizedEntry=false;
        String decision;
        int numeroDeVuelosDisponibles=0;
        scanner.nextLine();     //Limpieza buffer numérico del menú principal
        do {
            numeroDeVuelosDisponibles=vuelosDisponibles();
            do{
                System.out.print("Ingrese su entrada: ");
                decision = scanner.nextLine();
                notRecognizedEntry=filtroPalabras(decision, numeroDeVuelosDisponibles, cliente);
                
            } while (notRecognizedEntry);

        } while (!decision.equals("SALIR"));

        
    }

    /**
     * Método con el que vamos a mostrar los vuelos disponibles, para ello accederemos a un archivo de objetos en el que anteriormente
     * el administrador habrá guardado de los vuelos disponibles, junto con los 5 vuelos de ejemplo para el programa.
     * @return Retorna el número de vuelos almacenados dentro del archivo de objetos.
     */
    private static int vuelosDisponibles(){
        int numeroDeVuelosDisponibles=0;

        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("   Vuelos disponibles: \n");

        for(int i: vuelosDisponibles.keySet()){
            System.out.println(i+".- "+vuelosDisponibles.get(i).mostrarInformacionVuelo());
            numeroDeVuelosDisponibles++;
        }
        System.out.println("\nPara mayor información sobre los vuelos, por favor ingrese la palabra ''HELP''");
        System.out.println("Si desea reservar uno de los vuelos disponible ingrese la palabra ''RESERVAR'' seguido del número del vuelo (ejemplo: RESERVAR 4)");
        System.out.println("Si por el contrario desea regresar a menú principal, ingrese la palabra ''SALIR''");
        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------");

        return numeroDeVuelosDisponibles;
    }

    /**
     * Método con el que filtramos las distintas entradas que puede ingresar el usuario al momento de consultar los vuelos, el programa
     * se encarga de filtrar si la palabra fue reconocida o no, en casos afirmativos se realizará la acción correspondiente, en caso contrario
     * se le pedirá al usuario que ingrese una nueva entrada y se mostrarpá un mensaje de error, ya sea de no reconocer el tipo de vuelo o
     * no reconocer la entrada
     * @param decision Palabra a filtrar
     * @param numeroDeVuelosDisponibles Numero de vuelos disponibles recuperados del archivo de objetos
     * @return Devuelve true en caso de que se haya ingresado una entrada inválida, devuelve false en caso de que se haya ingresado una entrada válida.
     * Esta entrada válida puede ser "HELP", "SALIR" o "RESERVAR" seguido del número del vuelo y romperán el ciclo while en el método consultaVuelos.
     */
    private static boolean filtroPalabras(String decision, int numeroDeVuelosDisponibles, Cliente cliente){
        if(decision.equals("HELP")){
            mostrarInformacionHELPVuelos();
            return false;

        } else if(decision.equals("SALIR")){
            System.out.println("Regresando al menú principal...");
            return false;

        } else if(decision.equals("RESERVAR") || decision.equals("RESERVAR ")){
            System.out.println("* No se ha ingresado un número de vuelo, favor de especificarlo");
            return true;

        } else if (decision.length()>=10){
            
            //Validamos que la palabra ingresada sea "RESERVAR" seguido de un número
            if(decision.substring(0, 9).equals("RESERVAR ")){
                int i;
                for(i=1; i<=numeroDeVuelosDisponibles; i++){
                    if(decision.charAt(decision.length()-1)==Integer.toString(i).charAt(0)){
                        CompraTicket.comprarTicket(vuelosDisponibles.get(i), cliente);
                        return false;
                    }
                }
                if(i==numeroDeVuelosDisponibles+1){
                    System.out.println("* No se ha encontrado el vuelo ingresado, por favor intente de nuevo");
                    return true;
                }
            }
        } else {
            System.out.println("* No se ha reconocido la entrada, por favor intente de nuevo");
        }
        return true;
    }

    /**
     * Método con el que mostraremos toda la información particular de cada uno de los conceptos que se manejan en el menú, en este se 
     * desarrollan cada uno de los conceptos junto con una pequeña definición, este método únicamente se manda a llamar cuando
     * el usuario ingresa la palabra HELP dentro del menú de consulta de vuelos.
     */
    private static void mostrarInformacionHELPVuelos(){
        System.out.println("   Información general de los vuelos:");
        System.out.println("   \n  -- Tipo de vuelo --");
        System.out.println("   Se refiere a los diferentes tipos de vuelos que se tienen disponibles, estos pueden ser de tipo sencillo o redondo.");
        System.out.println("   * Los vuelos sencillos son aquellos que solo tienen un destino y un origen.");
        System.out.println("   * Los vuelos redondos son aquellos que tienen un destino y un origen, pero además tienen un segundo vuelo de regreso.");
        System.out.println("     (NOTA: El tiempo entre el vuelo de ida y el vuelo de regreso está sujeto al vuelo seleccionado y puede variar).");
        System.out.println("   \n  -- Fecha --");
        System.out.println("   Se refiere a la fecha especificada en la que el vuelo partira del origen hacia el destino, es recomendable");
        System.out.println("   que el usuario tome en cuenta la fecha de salida para evitar confusiones.");
        System.out.println("   \n  -- Precio --");
        System.out.println("   Se indica un precio base desde el cual comienzan los costos de los distintos tipos de vuelos, es importante");
        System.out.println("   señalar que la aerolínea se reserva el derecho de cambiar los precios sin previo aviso. Los precios de los");
        System.out.println("   Tickets premium y VIP son diferentes a los precios base.");
        System.out.println("   \n  -- Origen --");
        System.out.println("   Se refiere al lugar de donde parte el vuelo, es importante que el usuario tome en cuenta el lugar de origen");
        System.out.println("   para evitar confusiones al momento de comprar el boleto.");
        System.out.println("   \n  -- Destino --");
        System.out.println("   Se refiere al lugar al que llegará el vuelo, es importante que el usuario tome en cuenta el lugar de destino");
        System.out.println("   para evitar confusiones al momento de comprar el boleto.");
        System.out.println("   \n  -- Tiempo en días --");
        System.out.println("   Se refiere al tiempo que transcurre entre el vuelo de ida y el vuelo de regreso, este tiempo puede variar y solamente ");
        System.out.println("   aplica para los vuelos redondos. La fecha entre ambos vuelos está unicamente disponible al momento de la compra.\n");
    }



    /**
     * Método con el que descargaremos los boletos de los vuelos que el usuario haya comprado, para ello se accederá a un archivo de texto
     * en el que se almacenarán los boletos de los vuelos comprados, estos boletos se podrán consultar en cualquier momento y lugar.
     */
    private static void descargarBoletos(Cliente cliente){
        System.out.println("Descargando boletos...");
        ObjectInputStream archivoObjetos = null;
        try {
            archivoObjetos = new ObjectInputStream(new FileInputStream("src/Tickets/TicketsComprados/"+cliente.getNombre()));
            Ticket TicketImprimir=(Ticket)archivoObjetos.readObject();
            TicketImprimir.imprimirTicket(cliente);
            
        } catch (ClassNotFoundException e) {
            System.out.println("Error al descargar los boletos");
        } catch (IOException e) {
            System.out.println("Error al descargar los boletos");
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



