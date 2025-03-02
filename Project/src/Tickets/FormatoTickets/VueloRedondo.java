package Tickets.FormatoTickets;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Clase en la que se asocia toda la información referente a los vuelos redondos, este tipo de vuelos se caracterizan por tener tanto un vuelo 
 * de ida al destino como un vuelo de regreso al origen separados por un periodo de tiempo definido por el administrador, el precio de estos
 * vuelos se recomienda ser mayor al de un vuelo simple debido a que se contemplan dos vuelos en lugar de uno solo.
 */
public class VueloRedondo extends Vuelo implements Serializable {

    public VueloRedondo(String origen, String destino, LocalDateTime fecha, double precio, int tiempoDias){
        this.tipoDeVuelo="Redondo";
        this.origen=origen;
        this.destino=destino;
        this.fecha=fecha;
        this.precio=precio;
        this.tiempoDias=tiempoDias;
        //Inicializado de los asientos y sus rangos
        String [] letras={"A","B","C","D","E","F","G","H","I","J"};
        for(int i=0;i<9;i++){
            for(int j=0;j<20;j++){
                if(j<5){
                    asientosDisponibles.put(letras[i]+j, 3);
                } else if (j<10){
                    asientosDisponibles.put(letras[i]+j, 2);
                } else{
                    asientosDisponibles.put(letras[i]+j, 1);
                }
            }
        }
    }

    

    /**
     * Método en el que retorna toda la información de una compra de un ticket mediante un formato específico por lo que solamente se 
     * recomienda utilizarse cuando la compra del ticket esté por finalizar. La información retornada incluye:
     * <p>
     *   - Origen
     * <p>
     *   - Destino 
     * <p>
     *   - Fecha del vuelo
     * <p>
     *   - Precio
     * <p>
     *   - Tiempo en días entre los dos vuelos (ida y regreso).
     */
    @Override
    public String mostrarInformacionCompra(String asiento, String tipoTicket){
        double multiplicador=1;
        if(tipoTicket.equals("Standard")){
            multiplicador=1;
        } else if(tipoTicket.equals("Premium")){
            multiplicador=1.4;
        } else if(tipoTicket.equals("VIP")){
            multiplicador=1.9;
        }
        return "==========================================\n"+
        "   Resumen de su compra:\n"+
        "   Tipo de vuelo: "+tipoDeVuelo+"\n"+
        "   Ticket: "+tipoTicket+"\n"+
        "   Origen: "+origen+"\n"+
        "   Destino: "+destino+"\n"+
        "   Asiento: "+ asiento +"\n"+
        "   Fecha de vuelo: "+getFecha()+"\n"+
        "   Precio: "+(precio*multiplicador)+"\n"+
        "   Días entre vuelos: "+tiempoDias+" días\n"+
        "==========================================\n";
    }


    
}
