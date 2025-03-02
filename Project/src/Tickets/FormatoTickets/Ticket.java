package Tickets.FormatoTickets;

import java.io.Serializable;
import Cuentas.*;

/**
 *  Clase abstracta en la que definimos la estructura principal de los diferentes tickets de compra que podrán ser comprados al momento 
 *  de reservar los vuelos. La clase permite generar tickets standard, premium y vip.
 */
public abstract class Ticket implements Serializable{

    private Usuario propietario;
    protected String tipoTicket;
    //Bridge
    protected Vuelo vuelo;
    protected String asiento;
    protected double precioVueloTicket;

    public Usuario getPropietario() {
        return propietario;
    }

    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }

    /**
     * Constructor en el que vamos a generar el puente entre los tickets y los diferentes vuelos que podemos generar en el programa, este 
     * constructor nos permite asociar cualquier ticket con cualquier vuelo.
     * @param vuelo Variable de tipo vuelo en el que asociaremos el vuelo al ticket.
     */
    public Ticket(Vuelo vuelo){
        this.vuelo=vuelo;
    }

    /**
     * Método en el que se recupera el vuelo asociado al ticket que se va comprar.
     * @return Variable con toda la información del vuelo asociado al ticket.
     */
    public Vuelo getVuelo() {
        return vuelo;
    }

    /**
     * Método en el que se recupera el asiento asociado al ticket que se va comprar.
     * @return Variable con el asiento asociado al ticket.
     */
    public String getAsiento() {
        return asiento;
    }

    /**
     * Método en el que se recupera el tipo de ticket que se va a comprar.
     * @return Variable con el tipo de ticket que se va a comprar.
     */
    public String getTipoTicket() {
        return tipoTicket;
    }

    /**
     * Método en el que se asigna el asiento al ticket que se va a comprar.
     * @param asiento Variable con el asiento que se va a asignar al ticket.
     */
    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    /**
     * Método en el que se asigna el precio al ticket que se va a comprar.
     * @param precioVueloTicket Variable con el precio que se va a asignar al ticket.
     */
    public void setPrecioVueloTicket(double precioVueloTicket) {
        this.precioVueloTicket = precioVueloTicket;
    }

    /**
     * Método en el que se recupera el precio del ticket que se va a comprar.
     * @return Variable con el precio del ticket que se va a comprar.
     */
    public double getPrecioVueloTicket() {
        return precioVueloTicket;
    }

    /**
     * Método general en el que se definirá cómo se "imprimirán" los tickets dentro de nuestra computadora, ya que cada ticket contendrá
     * su forma de generar la impresión junto con información específica dependiendo la calidad del vuelo y el recorrido que realize. 
     */
    public abstract void imprimirTicket(Cliente cliente);
    
}
