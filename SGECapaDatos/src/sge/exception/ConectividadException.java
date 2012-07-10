/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.exception;

/**
 *
 * @author Propietario
 */
public class ConectividadException extends Exception {
    private Exception e;
    private String mensaje;
    
    public ConectividadException(Exception e, String msg){
        this.mensaje= msg;
    }
    public String getCausa(){
        return this.e.getCause().toString();
    }
    public String getMensaje(){
        return mensaje;
    }
    public String getMensajeTecnico(){
        return e.getMessage();
    }
    @Override
    public String toString(){
        return mensaje;
    }
}
