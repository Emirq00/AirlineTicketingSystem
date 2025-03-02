package Tickets.FormatoTickets;

import java.io.*;
import Cuentas.*;
import java.util.Random;

//import java.lang.*;

/**
 * Clase en la que se crearán todos los tickets de tipo premium (mejores beneficios). Estos tickets tienen la particularidad de ser más
 * caros que los simples pero no los más caros entre los 3 tipos de tickets. Dentro de esta clase se tendrá el método para mandar a 
 * imprimir el ticket mediante un archivo de texto que se almacenará para consultas fuera del programa.
 */
public class PremiumTicket extends Ticket implements Serializable {

    /**
     * Método constructor en el que asociaremos el puente entre el ticket y el tipo de vuelo a realizar
     * @param vuelo Tipo de vuelo al que se va a asociar este ticket.
     */
    public PremiumTicket(Vuelo vuelo){
        super(vuelo);
        this.tipoTicket="Premium";
        setPrecioVueloTicket(vuelo.precio+(vuelo.precio*1.4));
    }

    /**
     * Método en el que guardaremos toda la información de la compra del usuario mediante un archivo, dentro de este archivo se tendrá
     * tanto la información del vuelo comprado como información extra como el nombre de la persona, el número de recibo y la fecha de 
     * vuelo. Al tratarse de un ticket premium se dejará explicitamente en el ticket que el cliente puede ingresar antes
     * que los que compraron ticket standard.
     */
    @Override
    public void imprimirTicket(Cliente cliente) {
        PrintWriter archivoImprimirBoleto;
        boolean QRTypeLine;     //Si true, entonces línea, caso contrario espacio
        Random random = new Random();
		try{
            archivoImprimirBoleto = new PrintWriter(new FileWriter("src/Tickets/TicketsComprados/boletosImpresos.txt"));
            archivoImprimirBoleto.println("***************************************************************");
            archivoImprimirBoleto.println("                       AEROVUELOS MÉXICO                   ");
            archivoImprimirBoleto.println("                                                                               ");
            archivoImprimirBoleto.println("      Boleto premium - Vuelo "+vuelo.getTipoDeVuelo()                                   );
            archivoImprimirBoleto.println("      Pasajero: "+ cliente.getNombre()                                             );
            archivoImprimirBoleto.println("      Fecha de vuelo: "+vuelo.getFecha()                                           );
            archivoImprimirBoleto.println("      Destino: "+vuelo.getOrigen()+"-"+vuelo.getDestino()                          );
            archivoImprimirBoleto.println("      Asiento: "+ getAsiento()                                                     );
            archivoImprimirBoleto.println(" ");
            archivoImprimirBoleto.println("   === Puede ingresar antes que los pasajeros standard ===");
            archivoImprimirBoleto.println(" ");
            archivoImprimirBoleto.println(" ");
            archivoImprimirBoleto.print("           ");

            int numeroDeLineasConsecutivas=0;
            int numeroDeEspaciosConsecutivos=0;
            String QRCodeDouble="";
            for(int i=0;i<40;i++){
                QRTypeLine=random.nextBoolean();
                if((QRTypeLine && numeroDeLineasConsecutivas<2) || numeroDeEspaciosConsecutivos>1){
                    QRCodeDouble+="█";
                    archivoImprimirBoleto.print("█");
                    numeroDeLineasConsecutivas++;
                    numeroDeEspaciosConsecutivos=0;
                }
                else{
                    QRCodeDouble+=" ";
                    archivoImprimirBoleto.print(" ");
                    numeroDeEspaciosConsecutivos++;
                    numeroDeLineasConsecutivas=0;
                }
            }
            archivoImprimirBoleto.println("");
            archivoImprimirBoleto.println("           "+QRCodeDouble);
            archivoImprimirBoleto.println("                        "+((int)(Math.random()*10000000))+((int)(Math.random()*1000)));
            archivoImprimirBoleto.println(" ");
            archivoImprimirBoleto.println("***************************************************************");
			archivoImprimirBoleto.close();
		}
		catch (IOException e){
			System.out.println("Error: " + e.getMessage());
		}
    }
}
