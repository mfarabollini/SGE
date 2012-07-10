/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.vista;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import sge.entidades.Cliente;
import sge.entidades.Envio;
import sge.entidades.LineaEnvio;
import sge.exception.ConectividadException;
import sge.servicio.GestionEnvioServicios;

/**
 *
 * @author Marcos
 */
public class JMuestraEnviosPresenter {
    private JMuestraEnviosViewer vista;
    private Cliente aCliente;
    private Envio envio;

    
private BuscarEnvioHandler buscarEnvioHandler= new BuscarEnvioHandler();
private SelecEnvioHandler selecEnvioHandler= new SelecEnvioHandler();

    
   public JMuestraEnviosPresenter(JMuestraEnviosViewer form) {
        this.vista = form;

    }
    public BuscarEnvioHandler getBuscarEnvioHandler() {
            return buscarEnvioHandler;
    }

    public SelecEnvioHandler getSelecEnvioHandler() {
        return selecEnvioHandler;
    }

    public Envio getEnvio() {
        return envio;
    }

    public void setEnvio(Envio envio) {
        this.envio = envio;
    }
    
class BuscarEnvioHandler implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent e) {
            List<Envio> envios = null;
            
            try {
                aCliente = GestionEnvioServicios.buscarClientePorCodigo(vista.getCodCliente(),false);
                envios = GestionEnvioServicios.buscarEnviosCliente(aCliente);
                
            } catch (ConectividadException ex) {
                 vista.notificarMensaje(ex.getMensaje(),JOptionPane.ERROR_MESSAGE);
            }          
            
            if(envios.isEmpty()){
                vista.setFind(false);
            }else{
                vista.setFind(true);
            }
            
            Map aItem = null;           
            GenericoTableModel aModel =(GenericoTableModel) vista.getTblEnvios().getModel();
            Iterator iter = envios.iterator();
            
            SimpleDateFormat formatea = new SimpleDateFormat("dd/MM/yyyy");        
            
            while(iter.hasNext()){
                Envio aEnvio = (Envio) iter.next();

                    List<LineaEnvio>lineas = aEnvio.getLineaEnvioList();
                    for(int i=0; i<lineas.size();i++){              
                        if(lineas.get(i).getCliente().equals(aCliente)){
                            if((lineas.get(i).getFechaEntrega()==null)&&
                                (aEnvio.getFechaSalida()!=null)){
                                aItem = new HashMap();
                                aItem.put("0", aEnvio.getIdenvio());
                                aItem.put("1", lineas.get(i).getId());  
                                aItem.put("2", formatea.format(aEnvio.getFechaCreacion()));
                                aItem.put("3", aEnvio.getIdmedio().getRazonSocial());
                                aItem.put("4", lineas.get(i).getNroFactura());
                                aItem.put("5", lineas.get(i).getCantBultos());
                                aItem.put("6", lineas.get(i).getCodigoDeBarra()+lineas.get(i).getDigitoVerificador());                        
                                aModel.agregarLinea(aItem);
                            }
                        }else{
                            lineas.remove(i);
                        }                       
                    }
                
            }
        }
}

class SelecEnvioHandler implements ChangeListener{

            @Override
            public void stateChanged(ChangeEvent e) {
                int fila = vista.getTblEnvios().getSelectedRow();
                int idPedido = Integer.parseInt(vista.getTblEnvios().getValueAt(fila, 0).toString());

                try {
                    setEnvio(GestionEnvioServicios.buscarEnvioPorCodigo(idPedido));
                } catch (ConectividadException ex) {
                    //vista.notificarMensaje(ex.getMensaje(),JOptionPane.ERROR_MESSAGE);
                }
                vista.setVisible(false);         
                vista.dispose();  
            }
            

        }


}


