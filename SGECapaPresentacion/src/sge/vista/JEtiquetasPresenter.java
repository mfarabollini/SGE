/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.vista;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import sge.clases.ReporteException;
import sge.entidades.Cliente;
import sge.entidades.Envio;
import sge.entidades.LineaEnvio;
import sge.exception.ConectividadException;
import sge.servicio.GestionEnvioServicios;

/**
 *
 * @author mboni
 */
public class JEtiquetasPresenter {
     private JEtiquetasViewer vista;
     private Cliente aCliente;
     private List<Envio> envios;
     private BuscarClienteHandler buscarCLienteHandler= new BuscarClienteHandler();
     private BuscarEnvioHandler buscarEnvioHandler = new BuscarEnvioHandler();
     private CancelarImpresionHandler cancelarImpresionHandler= new CancelarImpresionHandler();
     private EnviarImpresionHandler enviarImpresionHandler= new EnviarImpresionHandler();

    public BuscarEnvioHandler getBuscarEnvioHandler() {
        return buscarEnvioHandler;
    }

    public JEtiquetasPresenter(JEtiquetasViewer form) {
        this.vista = form;
    }

    public BuscarClienteHandler getBuscarClienteHandler() {
        
        return buscarCLienteHandler;
    }

    public CancelarImpresionHandler getCancelarImpresionHandler() {
        return cancelarImpresionHandler;
    }

    public EnviarImpresionHandler getEnviarImpresionHandler() {
        return enviarImpresionHandler;
    }
     
     class BuscarClienteHandler  implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent ce) {
            boolean esNumero = true;
            aCliente = null;
            Integer codigoC  ;
            List<Cliente> clientes = null;
            JBuscarEntidadPresenter buscarEntPresenter;
            ClienteTableModel aTableModel;
            
            String codigo = vista.getTxtCliente().getText();
                        
            for (int i=0;i<codigo.length();i++){
                if (!Character.isDigit(codigo.charAt(i))){
                    esNumero=false;
                }
            }
            
            if(esNumero){
                codigoC = Integer.valueOf(codigo);
                try {
                    aCliente = GestionEnvioServicios.buscarClientePorCodigo(codigoC,false);
                } catch (Exception ex) {
                    notificarException(ex);
                }
            }else{
                try {
                    clientes = GestionEnvioServicios.buscarClientesPorRazonSocial(codigo,false);
                } catch (Exception ex) {
                    notificarException(ex);
                }
                if(clientes.size()== 1){
                   aCliente = clientes.get(0);                   
                }else{
                    buscarEntPresenter = new JBuscarEntidadPresenter();
                    aTableModel = new ClienteTableModel(clientes);
                    buscarEntPresenter.setClase(Cliente.class);
                    buscarEntPresenter.setResultado(clientes);
                    buscarEntPresenter.setModel(aTableModel);
                    buscarEntPresenter.mostrar();   
                    aCliente = (Cliente) buscarEntPresenter.getEntidad();  
                }                    
            } 
            if(aCliente!= null){
                vista.getTxtCliente().setText(aCliente.getId().toString());
                vista.getTxtRazonSocial().setText(aCliente.getRazonSocial());
                
            }
            
        }
         
     }
     class BuscarEnvioHandler implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent e) {
            
            Date fecha = vista.getFechaBusqueda(); 
            try {
                envios = GestionEnvioServicios.buscarEnvio(fecha  , aCliente);
            } catch (Exception ex) {
                notificarException(ex);
            }
            
            if(envios.size() > 0){
                if(aCliente!=null){
                    mostrarSoloLineasCliente();
                }else{
                    mostrarTodos();
                }
                vista.getTblPedidos().addNotify();     
            }else{
                vista.notificarFinBusqueda();
            }
        }

        private void mostrarSoloLineasCliente() {
            Map aItem = null;           
            GenericoTableModel aModel =(GenericoTableModel) vista.getTblPedidos().getModel();
            Iterator iter = envios.iterator();
            
            SimpleDateFormat formatea = new SimpleDateFormat("dd/MM/yyyy");        
            
            while(iter.hasNext()){
                Envio aEnvio = (Envio) iter.next();
                List<LineaEnvio>lineas = aEnvio.getLineaEnvioList();
                for(int i=0; i<lineas.size();i++){              
                    if(lineas.get(i).getCliente().equals(aCliente)){
                        aItem = new HashMap();
                        aItem.put("0", aEnvio.getIdenvio());
                        aItem.put("1", lineas.get(i).getId());  
                        aItem.put("2", formatea.format(aEnvio.getFechaCreacion()));
                        aItem.put("3", lineas.get(i).getCliente().getRazonSocial());
                        aItem.put("4", lineas.get(i).getCliente().getDireccion());
                        aItem.put("5", aEnvio.getIdmedio().getRazonSocial());
                        aItem.put("6", lineas.get(i).getNroFactura());
                        aItem.put("7", lineas.get(i).getCantBultos());
                        aItem.put("8", lineas.get(i).getCodigoDeBarra()+lineas.get(i).getDigitoVerificador());                        
                        aModel.agregarLinea(aItem);                                
                    }else{
                        lineas.remove(i);
                    }                       
                }       
            }            
        }

        private void mostrarTodos() {
            Map aItem = null;           
            GenericoTableModel aModel =(GenericoTableModel) vista.getTblPedidos().getModel();
            Iterator iter = envios.iterator();
            
            SimpleDateFormat formatea = new SimpleDateFormat("dd/MM/yyyy");         
            while(iter.hasNext()){                
                Envio aEnvio = (Envio) iter.next();
                List<LineaEnvio>lineas = aEnvio.getLineaEnvioList();
                for(int i=0; i<lineas.size();i++){              
                    aItem = new HashMap();
                    aItem.put("0", aEnvio.getIdenvio());
                    aItem.put("1", lineas.get(i).getId());                    
                    aItem.put("2", formatea.format(aEnvio.getFechaCreacion()));
                    aItem.put("3", lineas.get(i).getCliente().getRazonSocial());
                    aItem.put("4", lineas.get(i).getCliente().getDireccion());
                    aItem.put("5", aEnvio.getIdmedio().getRazonSocial());
                    aItem.put("6", lineas.get(i).getNroFactura());
                    aItem.put("7", lineas.get(i).getCantBultos());
                    aItem.put("8", lineas.get(i).getCodigoDeBarra()+lineas.get(i).getDigitoVerificador());
                    //aItem.put("8", lineas.get(i).getCodigoDeBarra());
                    aModel.agregarLinea(aItem);                                
                }                    
            }            
        }
            
               
     }
     class CancelarImpresionHandler  implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent ce) {
            aCliente=null;
            envios = null;
        }
         
     }
     
     class EnviarImpresionHandler  implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent ce) {
            List<LineaEnvio> lineasSeleccionados = null;
            int[] filas = vista.getTblPedidos().getSelectedRows();
            if(filas.length>0){
                lineasSeleccionados = new ArrayList();
            
                for(int i=0; i<filas.length; i++){
                    int idPedido = Integer.parseInt(vista.getTblPedidos().getValueAt(filas[i], 0).toString());
                    int idLinea = Integer.parseInt(vista.getTblPedidos().getValueAt(filas[i], 1).toString());
                    Iterator iter = envios.iterator();
                    while(iter.hasNext()){  
                        Envio aEnvio = (Envio) iter.next();
                        if(aEnvio.getIdenvio().intValue()==idPedido ){
                            for(int j=0;j<aEnvio.getLineaEnvioList().size();j++){
                                if(aEnvio.getLineaEnvioList().get(j).getId().intValue()==idLinea ){
                                     lineasSeleccionados.add(aEnvio.getLineaEnvioList().get(j));
                                     break;
                                }
                            }                         
                            break;
                        }
                    }
                   
                }
                try {
                    GestionEnvioServicios.imprimirEtiquetas(lineasSeleccionados);
                } catch (ConectividadException ex) {
                    notificarException(ex);
                }
                catch (ReporteException ex) {
                    notificarException(ex);
                }
            }
        }        
     }
   private void notificarException(Exception ex){
       vista.notificarException(ex);
   }
}
