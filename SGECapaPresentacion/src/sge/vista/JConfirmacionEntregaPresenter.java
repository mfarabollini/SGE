/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.vista;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import sge.entidades.*;
import sge.exception.ConectividadException;
import sge.servicio.GestionEnvioServicios;


/**
 *
 * @author Propietario
 */
public class JConfirmacionEntregaPresenter {
    private JConfirmacionEntregaViewer vista;
    private Envio aEnvio;
    private Cliente aCliente;
    private int posLinea;
    private CargarMotivosHandler cargarMotivos = new CargarMotivosHandler();   
    private BuscarEnvioHandler buscarEnvioHandler = new BuscarEnvioHandler();
    private CancelarEnvioHandler cancelarEnvioHandler = new CancelarEnvioHandler();
    private  RegistrarConfirmacionHandler registrarConfirmacionHandler = new RegistrarConfirmacionHandler();
    private ClienteChangeHandler handlerCliente= new ClienteChangeHandler();
    private EnvioSelHandler envioSelHandler= new EnvioSelHandler();
    
    public JConfirmacionEntregaPresenter(JConfirmacionEntregaViewer vista){
         this.vista = vista;
         posLinea = -1;
    }
    public CargarMotivosHandler getCargarMotivos() {
        return cargarMotivos;
    }
    
    public BuscarEnvioHandler getBuscarEnvioHandler() {
        return buscarEnvioHandler;
    }

    public CancelarEnvioHandler getCancelarEnvioHandler() {
        return cancelarEnvioHandler;
    }

    public RegistrarConfirmacionHandler getRegistrarConfirmacionHandler() {
        return registrarConfirmacionHandler;
    }

    public EnvioSelHandler getEnvioSelHandler() {
        return envioSelHandler;
    }

    public ClienteChangeHandler getClienteHandler() {
        return handlerCliente;
    }
    class CargarMotivosHandler implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent e) {
            Parametro aParametro= GestionEnvioServicios.getParametro("MOTDEV");
            List<DetalleParametro> detalle = aParametro.getDetalle();
            for (Iterator<DetalleParametro> it = detalle.iterator(); it.hasNext();) {
                DetalleParametro detalleParametro = it.next();
                vista.getCmbMotivo().addItem(detalleParametro.getDescripcion());                
            }   
        }
    }
    class EnvioSelHandler  implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent e) {
            
        }
    }
    
    class BuscarEnvioHandler  implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent e) {

            Integer codigoCli = Integer.valueOf(vista.getTxtCodCliente().getText().trim());
            JMuestraEnviosViewer dialog = new JMuestraEnviosViewer(new javax.swing.JFrame(), true, codigoCli);
            
            if (dialog.getFind()){
                aEnvio = dialog.showDialog();
                if (aEnvio!=null){
                    List<LineaEnvio> lineas = aEnvio.getLineaEnvioList();
                    SimpleDateFormat formatea = new SimpleDateFormat("dd/MM/yyyy");  
                    for (Iterator<LineaEnvio> it = lineas.iterator(); it.hasNext();) {
                            LineaEnvio lineaEnvio = it.next();
                            posLinea++;
                                vista.getTxtFechaViaje().setText(formatea.format(aEnvio.getFechaCreacion()));
                                vista.getTxtRazonSocial().setText(aEnvio.getIdmedio().getRazonSocial());
                                vista.getTxtRazonSocialCliente().setText(lineaEnvio.getCliente().getRazonSocial());        
                                vista.getTxtNroFactura().setText(lineaEnvio.getNroFactura());
                                vista.getTxtCantBultos().setText( String.valueOf(lineaEnvio.getCantBultos())); 
                                break;
                    }
                }else{
                    vista.getTxtCodCliente().setText("");
                    vista.getTxtRazonSocialCli().setText("");
                    vista.getTxtFechaViaje().setText("");
                    vista.getTxtFechaEntrega().setText("");
                    vista.getTxtRazonSocial().setText("");
                    vista.getTxtCodCliente().setText("");
                    vista.getTxtNroFactura().setText("");        
                    vista.getTxtCantBultos().setText("");
                    vista.getTxtNroGuia().setText("");
                    vista.getChkConDevoluciones().setSelected(false);
                    vista.getTxtCantDevuelta().setText("");      
                    vista.getTxtObservaciones().setText("");
                    vista.getCmbMotivo().setEnabled(false);   
                    vista.getTxtCodCliente().requestFocus(); 
                }
            }else{
                vista.notificarMensaje("No se encontraron envíos para el cliente",JOptionPane.ERROR_MESSAGE);
                vista.getTxtCodCliente().setText("");
                vista.getTxtRazonSocialCli().setText("");
                vista.getTxtFechaViaje().setText("");
                vista.getTxtFechaEntrega().setText("");
                vista.getTxtRazonSocial().setText("");
                vista.getTxtCodCliente().setText("");
                vista.getTxtNroFactura().setText("");        
                vista.getTxtCantBultos().setText("");
                vista.getTxtNroGuia().setText("");
                vista.getChkConDevoluciones().setSelected(false);
                vista.getTxtCantDevuelta().setText("");      
                vista.getTxtObservaciones().setText("");
                vista.getCmbMotivo().setEnabled(false);   
                vista.getTxtCodCliente().requestFocus(); 
            } 
        }
    }
    class CancelarEnvioHandler  implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent e) {
            aEnvio = null;
            posLinea = -1;
        }

    }  
    class RegistrarConfirmacionHandler  implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent e) {
            int cantDev = 0;
            Date fecha= null;
            
            if(vista.getChkConDevoluciones().isSelected()){
                cantDev = Integer.parseInt(vista.getTxtCantDevuelta().getText());
                String motivo = vista.getCmbMotivo().getSelectedItem().toString();
                aEnvio.getLineaEnvioList().get(posLinea).setCantDevuelta(cantDev);
                aEnvio.getLineaEnvioList().get(posLinea).setMotivoDevolucion(vista.getCmbMotivo().getSelectedItem().toString());
                aEnvio.getLineaEnvioList().get(posLinea).setObservaciones(vista.getTxtObservaciones().getText().trim());
            }
            SimpleDateFormat formatea = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            try {
                fecha = formatea.parse(vista.getTxtFechaEntrega().getText().trim());           
            
            }catch(Exception ev){
                 vista.notificarMensaje(ev.getMessage(),JOptionPane.ERROR_MESSAGE);
            }
            aEnvio.getLineaEnvioList().get(posLinea).setFechaEntrega(fecha);
            aEnvio.getLineaEnvioList().get(posLinea).setFechaConfirmacion(GregorianCalendar.getInstance().getTime());
            aEnvio.getLineaEnvioList().get(posLinea).setNroGuiaTransporte(vista.getTxtNroGuia().getText().trim());
            
            try {
                if(existeNumeroGuia()){
                    vista.setResultado(false);
                    return; 
                }
                boolean result =GestionEnvioServicios.registrarConfirmacion(aEnvio,aEnvio.getLineaEnvioList().get(posLinea).getCodigoDeBarra());
                    vista.setResultado(result);
                if(result){
                    vista.notificarMensaje("Recepción registrada",JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (ConectividadException ex) {
                vista.notificarMensaje(ex.getMensaje(),JOptionPane.ERROR_MESSAGE);
            }
        }

    }  
    public boolean existeNumeroGuia(){
        boolean resultado = false;
        Envio aEnvioLoc=null;
        Parametro aParam = GestionEnvioServicios.getParametro("VALGUI");
        boolean permite =Boolean.parseBoolean(aParam.getDetalle().get(0).getValor());
        try{        
            aEnvioLoc = GestionEnvioServicios.buscarEnvioPorNroGuia(vista.getTxtNroGuia().getText().trim());
        }catch(ConectividadException ex){
            
        }        
        if(aEnvioLoc != null && !permite){
            for (Iterator it = aEnvioLoc.getLineaEnvioList().iterator(); it.hasNext();) {
                LineaEnvio linea = (LineaEnvio) it.next();
                if(linea.getNroGuiaTransporte().equals(vista.getTxtNroGuia().getText().trim())){
                    vista.notificarMensaje("El número de guía ya fue registrado en envío nro: " + linea.getCodigoDeBarra()  ,JOptionPane.ERROR_MESSAGE);
                    resultado=true;
                    break;
                }
            }
        }
        return resultado;
    }

  

 public class ClienteChangeHandler  implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent ce) {     
            boolean esNumero = true;
            Integer codigoC  ;
            List<Cliente> clientes = null;
            JBuscarEntidadPresenter buscarEntPresenter;
            ClienteTableModel aTableModel;
            
            String codigo = vista.getTxtCodCliente().getText();
                        
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
                    notificarException (ex);
                }
            }else{
                try {
                    clientes = GestionEnvioServicios.buscarClientesPorRazonSocial(codigo,false);
                } catch (Exception ex) {
                    notificarException (ex);
                }
                if(clientes.size()== 1){
                   aCliente = clientes.get(0);                   
                }else{
                    buscarEntPresenter = new JBuscarEntidadPresenter();
                    aTableModel = new ClienteTableModel(clientes);
                    buscarEntPresenter.setClase(Cliente.class);
                    buscarEntPresenter.setResultado(clientes);
                    buscarEntPresenter.setHabilitado(false);
                    buscarEntPresenter.setModel(aTableModel);
                    buscarEntPresenter.mostrar();   
                    aCliente = (Cliente) buscarEntPresenter.getEntidad();  
                }                    
            } 
            if(aCliente!= null){
                vista.getTxtCodCliente().setText(aCliente.getId().toString().trim());
                vista.getTxtRazonSocialCli().setText(aCliente.getRazonSocial().trim());
                vista.getCmdBuscar().requestFocus();
            }else{
                vista.notificarMensaje("No se encontro el Cliente" ,JOptionPane.ERROR_MESSAGE);
            }    
        }
    }
    private void notificarException(Exception ex){
       vista.notificarException(ex);
   }
}