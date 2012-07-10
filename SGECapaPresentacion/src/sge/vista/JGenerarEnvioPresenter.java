/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.vista;


import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import sge.entidades.Cliente;
import sge.entidades.Envio;
import sge.entidades.LineaEnvio;
import sge.entidades.MedioEnvio;
import sge.entidades.Parametro;
import sge.exception.ConectividadException;
import sge.servicio.GestionEnvioServicios;

/**
 *
 * @author mboni
 */
public class JGenerarEnvioPresenter {
    private Envio aEnvio;
    private MedioEnvio aMedio;
    private Cliente aCliente;
    private JGenerarEnvioViewer vista;
    private List lineasDeEnvioMap;
    private TransporteChangeHandler handler= new TransporteChangeHandler();
    private ClienteChangeHandler handlerCliente= new ClienteChangeHandler();
    private AgregarChangeHandler agregarLinea = new AgregarChangeHandler();
    private CancelarHandler cancelarHandler = new CancelarHandler();
    private EnviarHandler enviarHandler = new EnviarHandler();
    
    
    public JGenerarEnvioPresenter(JGenerarEnvioViewer vista) {
        aEnvio = new Envio();
        lineasDeEnvioMap = new ArrayList();
        this.vista = vista;
        Parametro aParametro= GestionEnvioServicios.getParametro("LNMULT");
        
        vista.setMultiplesLineas(Boolean.valueOf( aParametro.getDetalle().get(0).getValor()));
    }
    public TransporteChangeHandler getHandler() {
        return handler;
    }
    public ClienteChangeHandler getClienteHandler() {
        return handlerCliente;
    }

    public AgregarChangeHandler getAgregarHandler() {
        return agregarLinea;
    }

    /**
     * @return the cancelarHandler
     */
    public CancelarHandler getCancelarHandler() {
        return cancelarHandler;
    }

    /**
     * @return the enviarHandler
     */
    public EnviarHandler getEnviarHandler() {
        return enviarHandler;
    }
    public class TransporteChangeHandler  implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent ce) {   
            JBuscarEntidadPresenter buscarEntPresenter;
            MedioEnvioTableModel aTableModel;
            List<MedioEnvio> medios = null;
            boolean esNumero = true;
            String codigo = vista.getTxtCodTransporte().getText();
             
            for (int i=0;i<codigo.length();i++){
                if (!Character.isDigit(codigo.charAt(i))){
                    esNumero=false;
                }
            }
            if(esNumero){
                Integer codigoT = Integer.valueOf(codigo);
                try {
                    aMedio = GestionEnvioServicios.buscarMedioEnvioPorCodigo(codigoT,false);
                } catch (Exception ex) {
                    notificarException (ex);
                }
            }else{
                try {
                    medios = GestionEnvioServicios.buscarMediosEnvioPorRazonSocial(codigo,false);
                } catch (Exception ex) {
                    notificarException (ex);
                }
                if(medios.size()== 1){
                    aMedio = medios.get(0);                   
                }else{
                    buscarEntPresenter = new JBuscarEntidadPresenter();
                    aTableModel = new MedioEnvioTableModel(medios);
                    buscarEntPresenter.setClase(MedioEnvio.class);
                    buscarEntPresenter.setResultado(medios);
                    buscarEntPresenter.setHabilitado(false);
                    buscarEntPresenter.setModel(aTableModel);
                    buscarEntPresenter.mostrar();   
                    aMedio = (MedioEnvio) buscarEntPresenter.getEntidad();                     
                }                    
            }  
            if(aMedio!=null){                
                vista.getTxtCodTransporte().setText(aMedio.getId().toString());
                vista.getTxtRazonSocial().setText(aMedio.getRazonSocial());
                vista.getTxtCodCliente().requestFocus();
            }else{              
                vista.notificarMensaje("El Transporte/comisionista no se há encontrado o a cancelado la busqueda" ,JOptionPane.INFORMATION_MESSAGE);
            }
        }
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
                vista.getTxtNroFactura().requestFocus();
            }else{                
                vista.notificarMensaje("No se encontro el Cliente o a cancelado la busqueda" ,JOptionPane.INFORMATION_MESSAGE);
            }    
        }
    }
    public class AgregarChangeHandler  implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent ce) { 
            
            if(existeNumeroFactura(vista.getTxtNroFactura().getText().trim())){
                return;
            }
            Map item = new HashMap();
            item.put("CLI", aCliente);
            item.put("FAC", vista.getTxtNroFactura().getText().trim());
            item.put("BUL", vista.getTxtCantBultos().getText().trim());
                        
            lineasDeEnvioMap.add(item);
          
                    
            Map itemModel = new HashMap();
            itemModel.put("0", lineasDeEnvioMap.size());
            itemModel.put("1", aCliente.getRazonSocial());
            itemModel.put("2", vista.getTxtNroFactura().getText().trim());
            itemModel.put("3", Integer.parseInt(vista.getTxtCantBultos().getText().trim()));
            LineaEnvioTableModel modelTabla= (LineaEnvioTableModel) vista.getjTable1().getModel();
            modelTabla.agregarLinea(itemModel);
            vista.getjTable1().setModel(modelTabla);
            vista.getjTable1().addNotify();
            
        }        
      
    }
   public class CancelarHandler  implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent ce) {
            aEnvio= null;
            aMedio= null;
            aCliente= null;       
            lineasDeEnvioMap = null;
            
            aEnvio = new Envio();
            lineasDeEnvioMap = new ArrayList();
        }
       
   }
   public class EnviarHandler implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent ce) {
            boolean resultado=true;
            
            aEnvio.setFechaCreacion(GregorianCalendar.getInstance().getTime());
            aEnvio.setIdmedio(aMedio);
            for (Iterator it = lineasDeEnvioMap.iterator(); it.hasNext();) {
                Map aItem = (Map) it.next();                
                aEnvio.agregarLinea(aItem);
            }      
            
            
            try {
                GestionEnvioServicios.guardarEnvio(aEnvio);
            } catch (Exception ex) {
                notificarException (ex);
            }
            reinciciarTransaccion();
            vista.notificarEnvio(resultado);
        }    
    }
   private void notificarException(Exception ex){
       vista.notificarException(ex);
   }
   
   public boolean existeNumeroFactura(String nroFactura){
        boolean resultado = false;
        Envio aEnvioLoc=null;
        Parametro aParam = GestionEnvioServicios.getParametro("VALFAC");
        boolean permite =Boolean.parseBoolean(aParam.getDetalle().get(0).getValor());
        try{        
            aEnvioLoc = GestionEnvioServicios.buscarEnvioPorFactura(nroFactura);
        }catch(ConectividadException ex){
            notificarException (ex);
        }        
        if(aEnvioLoc != null && !permite){
            for (Iterator it = aEnvioLoc.getLineaEnvioList().iterator(); it.hasNext();) {
                LineaEnvio linea = (LineaEnvio) it.next();
                if(linea.getNroFactura().equals(vista.getTxtNroFactura().getText().trim())){
                    vista.notificarMensaje("El número de factura ya fue registrado en envío nro: " + linea.getCodigoDeBarra()  ,JOptionPane.ERROR_MESSAGE);
                    resultado=true;
                    break;
                }
            }
        }
        return resultado;
    } 
    private void reinciciarTransaccion(){
        this.getCancelarHandler().stateChanged(new ChangeEvent(this));
    }
}

