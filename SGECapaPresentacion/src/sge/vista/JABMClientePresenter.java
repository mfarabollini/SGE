/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.vista;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import sge.entidades.Cliente;
import sge.entidades.Envio;
import sge.entidades.Localidad;
import sge.entidades.Provincia;
import sge.exception.ConectividadException;
import sge.servicio.GestionEnvioServicios;

/**
 *
 * @author Marcos
 */
public class JABMClientePresenter {
    private Cliente aCliente;
    List<Provincia> provinciaList;
    private Provincia aProvincia;
    List<Localidad> localidadList;
    
    public JABMClientePresenter(JABMClienteViewer vista) {
        aCliente = new Cliente();
        this.vista = vista;
    }
    
    
    private JABMClienteViewer vista;
    
    private BotonClickHandler pres = new BotonClickHandler();
    private BuscarProvinciaHandler provinciaHandler = new BuscarProvinciaHandler();
    private BuscarClienteHandler clienteHandler = new BuscarClienteHandler();
    private BuscarLocalidadHandler localidadHandler = new BuscarLocalidadHandler();
    
    public BotonClickHandler getPres() {
        return pres;
    }

    public BuscarProvinciaHandler getProvinciaHandler() {
        return provinciaHandler;
    }
    
    public BuscarClienteHandler getClienteHandler() {
        return clienteHandler;
    }

    public BuscarLocalidadHandler getLocalidadHandler() {
        return localidadHandler;
    }
    


    
    class BotonClickHandler  implements ChangeListener{
        
        @Override
        public void stateChanged(ChangeEvent ce) {    
            ///Seteo los campos del Clinete
            aCliente.setRazonSocial(vista.getTxtRazonSocial().getText());
            aCliente.setCuit(vista.getTxtCuit().getText());
            aCliente.setDireccion(vista.getTxtDir().getText());
            aCliente.setTelefono(vista.getTxtTel().getText());
            aCliente.setTelefonoAlternativo(vista.getTxtTel2().getText());
            aCliente.setEmail(vista.getTxtMail().getText());
            
            ///Seteo la Provincia y Localidad
            try {
                aProvincia = GestionEnvioServicios.buscarProvinciaNombre((String) vista.getCboProvincia().getSelectedItem());
            } catch (ConectividadException ex) {
                notificarException(ex);
            }
            localidadList = aProvincia.getLocalidades();
            if(localidadList!=null){
                for (Iterator<Localidad> it = localidadList.iterator(); it.hasNext();) {
                    Localidad localidad = it.next();
                    if (localidad.getNombre() == vista.getCboLocalidad().getSelectedItem()){
                        aCliente.setLocalidad(localidad);
                        break;
                    }
                }
            }else{
                aCliente.setLocalidad(null);
            }
            
            Integer modo = vista.getModo();
            boolean resultado = false;
            if (modo == 1){
                ///Guardo el Cliente
                aCliente.setHabilitado(true);
                try {
                    resultado = GestionEnvioServicios.guardarCliente(aCliente);
                    if (!resultado){
                        vista.notificarMensaje("El cliente ya existe en los registros",JOptionPane.ERROR_MESSAGE);
                        resultado=false;
                    }
                } catch (ConectividadException ex) {
                    notificarException(ex);
                }
            }else if (modo == 2){
                ///Modifico el Cliente
                aCliente.setIdCliente(Integer.valueOf(vista.getTxtId().getText()));                
                aCliente.setHabilitado(true);
                try {
                    resultado = GestionEnvioServicios.actualizarCliente(aCliente);
                } catch (ConectividadException ex) {
                    notificarException(ex);
                }
            }else if (modo == 3){
                ///Seteo el Cliente como deshabilitado    
                aCliente.setIdCliente(Integer.valueOf(vista.getTxtId().getText()));
                aCliente.setHabilitado(false);
                try {
                    resultado = GestionEnvioServicios.actualizarCliente(aCliente);
                } catch (ConectividadException ex) {
                    notificarException(ex);
                }
            }
            if(resultado){
                ///Limpio los campos de texto
                vista.getTxtId().setText("");
                vista.getTxtCuit().setText("");
                vista.getTxtRazonSocial().setText("");
                vista.getTxtDir().setText("");
                vista.getTxtTel().setText("");
                vista.getTxtTel2().setText("");
                vista.getTxtMail().setText("");

                ///Reinicializo los combos
                vista.getCboProvincia().setSelectedIndex(0);
                vista.getCboLocalidad().setSelectedIndex(0);
            }
            ///Notifico el resultado
            vista.notificar(resultado);

        }
    }
    class BuscarProvinciaHandler  implements ChangeListener{
        
        @Override
        public void stateChanged(ChangeEvent ce) { 
            try {
                provinciaList = GestionEnvioServicios.buscarProvincia();
            } catch (ConectividadException ex) {
                notificarException(ex);
            }
           for (Iterator<Provincia> it = provinciaList.iterator(); it.hasNext();) {
                Provincia provincia = it.next();             
                vista.getCboProvincia().addItem(provincia.getNombre());
           }
            try {
                aProvincia = GestionEnvioServicios.buscarProvinciaNombre((String) vista.getCboProvincia().getSelectedItem());
            } catch (ConectividadException ex) {
                notificarException(ex);
            }
           localidadList = aProvincia.getLocalidades();
           for (Iterator<Localidad> it = localidadList.iterator(); it.hasNext();) {
                Localidad localidad = it.next();
                vista.getCboLocalidad().addItem(localidad.getNombre());
           }
        }       
    }
    
    class BuscarLocalidadHandler  implements ChangeListener{
        
        @Override
        public void stateChanged(ChangeEvent ce) { 
           vista.getCboLocalidad().removeAllItems();
            try {
                aProvincia = GestionEnvioServicios.buscarProvinciaNombre((String) vista.getCboProvincia().getSelectedItem());
            } catch (ConectividadException ex) {
                notificarException(ex);
            }
           localidadList = aProvincia.getLocalidades();
           for (Iterator<Localidad> it = localidadList.iterator(); it.hasNext();) {
                Localidad localidad = it.next();
                vista.getCboLocalidad().addItem(localidad.getNombre());
           }
        }       
    }
    
    class BuscarClienteHandler  implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent ce) {     
            boolean esNumero = true;
            Integer codigoC  ;
            List<Cliente> clientes = null;
            JBuscarEntidadPresenter buscarEntPresenter;
            ClienteTableModel aTableModel;
            
            String codigo = vista.getTxtClienteBusq().getText();
                        
            for (int i=0;i<codigo.length();i++){
                if (!Character.isDigit(codigo.charAt(i))){
                    esNumero=false;
                }
            }
            
            if(esNumero){
                codigoC = Integer.valueOf(codigo);
                try {
                    aCliente = GestionEnvioServicios.buscarClientePorCodigo(codigoC,true);
                } catch (ConectividadException ex) {
                    notificarException(ex);
                }
            }else{
                try {
                    clientes = GestionEnvioServicios.buscarClientesPorRazonSocial(codigo,true);
                } catch (ConectividadException ex) {
                    notificarException(ex);
                }
                if(clientes.size()== 1){
                   aCliente = clientes.get(0);                   
                }else{
                    buscarEntPresenter = new JBuscarEntidadPresenter();
                    aTableModel = new ClienteTableModel(clientes);
                    buscarEntPresenter.setClase(Cliente.class);
                    buscarEntPresenter.setResultado(clientes);
                    buscarEntPresenter.setHabilitado(true);
                    buscarEntPresenter.setModel(aTableModel);
                    buscarEntPresenter.mostrar();   
                    aCliente = (Cliente) buscarEntPresenter.getEntidad();  
                }                    
            } 
            if(aCliente!= null){
                vista.getTxtClienteBusq().setText("");
                vista.getTxtId().setText(aCliente.getId().toString());
                vista.getTxtRazonSocial().setText(aCliente.getRazonSocial());
                vista.getTxtCuit().setText(aCliente.getCuit());
                vista.getTxtDir().setText(aCliente.getDireccion());
                
                vista.getCboProvincia().setSelectedItem(aCliente.getLocalidad().getProvincia().getNombre());     
                vista.getCboLocalidad().setSelectedItem(aCliente.getLocalidad().getNombre());

                vista.getTxtTel().setText(aCliente.getTelefono());
                vista.getTxtTel2().setText(aCliente.getTelefonoAlternativo());
                vista.getTxtMail().setText(aCliente.getEmail());
            }  
        }
       
    }
    private void notificarException(Exception ex){
        vista.notificarMensaje(ex.toString(),JOptionPane.ERROR);
    }
    
    
}

