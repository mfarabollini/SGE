/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.vista;

import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import sge.servicio.GestionEnvioServicios;

/**
 *
 * @author mboni
 */
public class JBuscarEntidadPresenter {
    private JBuscarEntidadViewer vista;
    private TableModel model; 
    private List resultado;
    private Object entidad;
    private Class aClase;
    private boolean habilitado;
    private ClienteSeleccionadoHandler handlerCliente= new ClienteSeleccionadoHandler(this);
    private BuscarEntidadHandler buscarEntidadHandler = new BuscarEntidadHandler();
    private CancelarBusquedaHandler cancelarBusquedaHandler = new CancelarBusquedaHandler();
    
    public JBuscarEntidadPresenter(){
        this.vista = new JBuscarEntidadViewer(null,true);
        this.vista.setPresenter(this);
    }
    public JBuscarEntidadPresenter(JBuscarEntidadViewer vista) {
        this.vista = vista;
    }

    /**
     * @return the vista
     */
    public JBuscarEntidadViewer getVista() {
        return vista;
    }

    /**
     * @param vista the vista to set
     */
    public void setVista(JBuscarEntidadViewer vista) {
        this.vista = vista;
    }

    /**
     * @return the model
     */
    public TableModel getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(TableModel model) {
        this.model = model;
        this.vista.setModel(model);
        this.vista.getTblResultado().addNotify();
    }

    /**
     * @return the resultado
     */
    public List getResultado() {
        return resultado;
    }

    /**
     * @param resultado the resultado to set
     */
    public void setResultado(List resultado) {
        this.resultado = resultado;       
    }
    
    public void mostrar(){
        this.vista.setVisible(true);
    }
    public JBuscarEntidadPresenter.ClienteSeleccionadoHandler getClienteSeleccionadoHandler() {
        return handlerCliente;
    }

    /**
     * @return the entidad
     */
    public Object getEntidad() {      
        if(vista.getTblResultado().getSelectedRowCount()>0){
            if(resultado.size()> 0){
                entidad = resultado.get(vista.getEntidadSeleccionada());
            }
        }
        return entidad;
    }

    /**
     * @param entidad the entidad to set
     */
    public void setEntidad(Object entidad) {
        this.entidad = entidad;
    }

    /**
     * @return the buscarEntidadHandler
     */
    public BuscarEntidadHandler getBuscarEntidadHandler() {
        return buscarEntidadHandler;
    }

    void setClase(Class<?> clase) {
        this.aClase= clase;
    }

    void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    /**
     * @return the cancelarBusquedaHandler
     */
    public CancelarBusquedaHandler getCancelarBusquedaHandler() {
        return cancelarBusquedaHandler;
    }
    
    class ClienteSeleccionadoHandler  implements ChangeListener{
        private JBuscarEntidadPresenter presenter;
        public ClienteSeleccionadoHandler(){
             
        }
        public ClienteSeleccionadoHandler(JBuscarEntidadPresenter presenter){
            this.presenter = presenter;
        }
        @Override
        public void stateChanged(ChangeEvent ce) {         
          presenter.vista.setVisible(false);         
          presenter.vista.dispose();          
        }
    }
    class BuscarEntidadHandler implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent ce) {
            try {
                String rs = vista.getTxtRazonSocial().getText();
                resultado = GestionEnvioServicios.buscarEntidadPorRazonSocial(aClase.getSimpleName(),rs,habilitado );
                setResultado(resultado);
                Class<?> clase = Class.forName(vista.getTblResultado().getModel().getClass().getName());
             
                Method  method = clase.getDeclaredMethod ("setLineas", List.class);
                method.invoke(vista.getTblResultado().getModel(), resultado); 
                vista.getTblResultado().addNotify();
                
            } catch (Exception ex) {
                Logger.getLogger(JBuscarEntidadPresenter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    
    
     class CancelarBusquedaHandler implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent ce) {
          vista.getTblResultado().clearSelection();
        }
         
     }   
            
}
