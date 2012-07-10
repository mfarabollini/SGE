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
import org.apache.commons.lang.StringUtils;
import sge.entidades.Envio;
import sge.entidades.LineaEnvio;
import sge.exception.ConectividadException;
import sge.servicio.GestionEnvioServicios;

/**
 *
 * @author Propietario
 */
public class JRegistroSalidaPresenter {
     private JRegistroSalidaViewer vista;
     private Envio aEnvio;
     private BuscarEnvioHandler buscarEnvioHandler = new BuscarEnvioHandler();
     private CancelarEnvioHandler cancelarEnvioHandler = new CancelarEnvioHandler();
     
     private RegistrarDespachoHandler registrarDespachoHandler = new RegistrarDespachoHandler();

    public CancelarEnvioHandler getCancelarEnvioHandler() {
        return cancelarEnvioHandler;
    }

    public RegistrarDespachoHandler getRegistrarDespachoHandler() {
        return registrarDespachoHandler;
    }
     
    public BuscarEnvioHandler getBuscarEnvioHandler() {
        return buscarEnvioHandler;
    }

    public void setBuscarEnvioHandler(BuscarEnvioHandler buscarEnvioHandler) {
        this.buscarEnvioHandler = buscarEnvioHandler;
    }

    public JRegistroSalidaPresenter(JRegistroSalidaViewer vista) {
        this.vista = vista;
    }
    
    class BuscarEnvioHandler  implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent ce) {
            Map aItem= null;
            
            String codigo = vista.getTxtNumeroEnvio().getText().trim();
            codigo = "0000000000000"+ codigo;
            codigo = codigo.substring(codigo.length()-13, codigo.length()-1);
            
            try {
                aEnvio = GestionEnvioServicios.buscarEnvioPorCodigoBarra(codigo);
            } catch (ConectividadException ex) {
                notificarException(ex);
            }
            
            if(aEnvio!=null){
                if(aEnvio.getFechaSalida() != null){
                    vista.notificarMensaje("El envío encontrado ya fue despachado",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                SimpleDateFormat formatea = new SimpleDateFormat("dd/MM/yyyy");
        
                vista.getTxtFechaViaje().setText(formatea.format(aEnvio.getFechaCreacion()));
                vista.getTxtRazonSocial().setText(aEnvio.getIdmedio().getRazonSocial());
                
                List<LineaEnvio> lineas = aEnvio.getLineaEnvioList();
                GenericoTableModel aModel =(GenericoTableModel) vista.getTblEnvio().getModel();

                for (Iterator<LineaEnvio> it = lineas.iterator(); it.hasNext();) {
                    LineaEnvio lineaEnvio = it.next();
                    aItem = new HashMap();
                    aItem.put("0", lineaEnvio.getId());
                    aItem.put("1", lineaEnvio.getNumeroLinea());  
                    aItem.put("2", lineaEnvio.getCliente().getRazonSocial());
                    aItem.put("3", lineaEnvio.getCliente().getDireccion());
                    aItem.put("4", lineaEnvio.getNroFactura());
                    aItem.put("5", lineaEnvio.getCantBultos());
                    aModel.agregarLinea(aItem);  
                }
                vista.getTblEnvio().addNotify();     
            }else{
                
                vista.notificarMensaje("No se há encontrado un envío para el codigo informado",JOptionPane.ERROR_MESSAGE);
            }
        }

        private void notificarException(ConectividadException ex) {
            vista.notificarException(ex);
        }
         
     }
     class CancelarEnvioHandler  implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent e) {
            aEnvio = null;            
        }
         
     }
   
     class RegistrarDespachoHandler  implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent e) {
            
            try {                
                GestionEnvioServicios.registrarDespacho(aEnvio);
                vista.notificarMensaje("Despacho registrado",JOptionPane.INFORMATION_MESSAGE);
                
            } catch (ConectividadException ex) {
                
            }
        }
         
     }
     private void notificarException(Exception ex){
       vista.notificarMensaje(ex.toString(),JOptionPane.ERROR);
   }
}
