/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.vista;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import sge.entidades.MedioEnvio;
import sge.exception.ConectividadException;
import sge.servicio.GestionEnvioServicios;

/**
 *
 * @author Marcos
 */
public class JABMTransportePresenter {
    private MedioEnvio aMedioEnvio;
    
    public JABMTransportePresenter(JABMTransporteViewer vista) {
        aMedioEnvio = new MedioEnvio();
        this.vista = vista;
    }
    
    private JABMTransporteViewer vista;
    private GuardarMedioEnvioHandler guardarMedioEnvio = new GuardarMedioEnvioHandler();
    private BuscarMedioEnvioHandler buscarMedioEnvioHandler = new BuscarMedioEnvioHandler();
    
    public GuardarMedioEnvioHandler getGuardarMedioEnvio() {
        return guardarMedioEnvio;
    }

    public BuscarMedioEnvioHandler getBuscarMedioEnvioHandler() {
        return buscarMedioEnvioHandler;
    }
    

    class GuardarMedioEnvioHandler  implements ChangeListener{
        
        @Override
        public void stateChanged(ChangeEvent ce) {    
            
            if(vista.getCboTipo().getSelectedItem().equals("Transportista")){
                aMedioEnvio.setClase('T');
            }else{
                aMedioEnvio.setClase('S');
            }
            aMedioEnvio.setCuit(vista.getTxtCuit().getText().trim());
            aMedioEnvio.setRazonSocial(vista.getTxtRazonSocial().getText().trim());
            aMedioEnvio.setDireccion(vista.getTxtDir().getText().trim());
            aMedioEnvio.setTel(vista.getTxtTel().getText().trim());
            
             Integer modo = vista.getModo();
            boolean resultado = false;
            if (modo == 1){
                ///Guardo el Cliente
                
                aMedioEnvio.setHabilitado(true);
                try {
                     resultado = GestionEnvioServicios.guardarTransporte(aMedioEnvio);
                    if (!resultado){
                        vista.notificarMensaje("El Transporte o comisionista ya existe en los registros",JOptionPane.ERROR_MESSAGE);
                        resultado=false;
                    }
                
                } catch (ConectividadException ex) {
                    Logger.getLogger(JABMClientePresenter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else if (modo == 2){
                ///Modifico el Cliente
                aMedioEnvio.setIdmedio(Integer.valueOf(vista.getTxtId().getText()));
                aMedioEnvio.setHabilitado(true);
                try {
                    resultado = GestionEnvioServicios.actualizarTransporte(aMedioEnvio);
                } catch (ConectividadException ex) {
                    Logger.getLogger(JABMClientePresenter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else if (modo == 3){
                ///Seteo el Cliente como deshabilitado    
                aMedioEnvio.setIdmedio(Integer.valueOf(vista.getTxtId().getText()));
                aMedioEnvio.setHabilitado(false);
                try {
                    resultado = GestionEnvioServicios.actualizarTransporte(aMedioEnvio);
                } catch (ConectividadException ex) {
                    Logger.getLogger(JABMClientePresenter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(resultado){
                ///Limpio los campos de texto
                vista.getTxtId().setText("");
                vista.getTxtCuit().setText("");
                vista.getTxtRazonSocial().setText("");
                vista.getTxtDir().setText("");
                vista.getTxtTel().setText("");

                ///Reinicializo los combos
                vista.getCboTipo().setSelectedIndex(0);

            }
            ///Notifico el resultado
            vista.notificar(resultado);

        }
     }
    
    class BuscarMedioEnvioHandler  implements ChangeListener{
        
        @Override
        public void stateChanged(ChangeEvent ce) {    
            JBuscarEntidadPresenter buscarEntPresenter;
            MedioEnvioTableModel aTableModel;
            List<MedioEnvio> medios = null;
            boolean esNumero = true;
            String codigo = vista.getTxtTransporteBusq().getText();
             
            for (int i=0;i<codigo.length();i++){
                if (!Character.isDigit(codigo.charAt(i))){
                    esNumero=false;
                }
            }
            if(esNumero){
                Integer codigoT = Integer.valueOf(codigo);
                try {
                    aMedioEnvio = GestionEnvioServicios.buscarMedioEnvioPorCodigo(codigoT,true);
                } catch (Exception ex) {
                    notificarException (ex);
                }
            }else{
                try {
                    medios = GestionEnvioServicios.buscarMediosEnvioPorRazonSocial(codigo,true);
                } catch (Exception ex) {
                    notificarException (ex);
                }
                if(medios.size()== 1){
                    aMedioEnvio = medios.get(0);                   
                }else{
                    buscarEntPresenter = new JBuscarEntidadPresenter();
                    aTableModel = new MedioEnvioTableModel(medios);
                    buscarEntPresenter.setClase(MedioEnvio.class);
                    buscarEntPresenter.setResultado(medios);
                    buscarEntPresenter.setModel(aTableModel);
                    buscarEntPresenter.mostrar();   
                    aMedioEnvio = (MedioEnvio) buscarEntPresenter.getEntidad();                     
                }                    
            }  
            if(aMedioEnvio!=null){                
                vista.getTxtId().setText(aMedioEnvio.getId().toString());
                
                if (aMedioEnvio.getClase() == 'T') {
                    vista.getCboTipo().setSelectedItem("Transportista");
                }else{    
                    vista.getCboTipo().setSelectedItem("Comisionista");
                }

                vista.getTxtRazonSocial().setText(aMedioEnvio.getRazonSocial());
                vista.getTxtCuit().setText(aMedioEnvio.getCuit());
                vista.getTxtDir().setText(aMedioEnvio.getDireccion());
                vista.getTxtTel().setText(aMedioEnvio.getTel());
                   
            }else{
                
            }
            
        }
    }

          private void notificarException(Exception ex){
            vista.notificarException(ex);
   
     }
    
}
