/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.vista;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import sge.exception.ConectividadException;
import sge.servicio.GestionEnvioServicios;

/**
 *
 * @author Propietario
 */
public class JMainPresenter {
    private JMainViewer vista;
    private GenerarEnvíoHandler generarEnvioHandler = new GenerarEnvíoHandler();
    private ImprimirEtiquetasHandler imprimirEtiquetasHanlder = new ImprimirEtiquetasHandler();
    private SalidaEnvioHandler RegistroSalidaEnvioHandler = new SalidaEnvioHandler();
    private AltaClienteHandler altaClienteHandler = new AltaClienteHandler();
    private ConfirmacionEnvioHandler confirmacionEnvioHandler = new ConfirmacionEnvioHandler();
    private BajaClienteHandler bajaClienteHandler = new BajaClienteHandler();
    private ModifClienteHandler modifClienteHandler = new ModifClienteHandler();
    private AltaTransporteHandler altaTransporteHandler = new AltaTransporteHandler();
    private BajaTransporteHandler bajaTransporteHandler = new BajaTransporteHandler();
    private ModifTransporteHandler modifTransporteHandler = new ModifTransporteHandler();
    private LanzarReportesHandler lanzarReportesHandler = new LanzarReportesHandler();

    
    public ConfirmacionEnvioHandler getConfirmacionEnvioHandler() {
        return confirmacionEnvioHandler;
    }
            
    public AltaClienteHandler getAltaClienteHandler() {
        return altaClienteHandler;
    }
    
    JMainPresenter(JMainViewer form) {
        
        try {
            GestionEnvioServicios.iniciarParametros();
        } catch (ConectividadException ex) {
            Logger.getLogger(JMainPresenter.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.vista = form;
    }
    
    public GenerarEnvíoHandler getGenerarEnvioHandler() {
        return generarEnvioHandler;
    }

    public ImprimirEtiquetasHandler getImprimirEtiquetasHanlder() {
        return imprimirEtiquetasHanlder;
    }

    
    public SalidaEnvioHandler getSalidaEnvioHandler() {
        return RegistroSalidaEnvioHandler;
    }
    
    public BajaClienteHandler getBajaClienteHandler() {
        return bajaClienteHandler;
    }

    public ModifClienteHandler getModifClienteHandler() {
        return modifClienteHandler;
    }

    public AltaTransporteHandler getAltaTransporteHandler() {
        return altaTransporteHandler;
    }

    public BajaTransporteHandler getBajaTransporteHandler() {
        return bajaTransporteHandler;
    }

    public ModifTransporteHandler getModifTransporteHandler() {
        return modifTransporteHandler;
    }

    /**
     * @return the lanzarReportesHandler
     */
    public LanzarReportesHandler getLanzarReportesHandler() {
        return lanzarReportesHandler;
    }

   

    
    class GenerarEnvíoHandler  implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent ce) {
            if(ventanaAbierta("GENENV")){
                return;
            }
            JGenerarEnvioViewer form = new JGenerarEnvioViewer();
            //vista.getPanelEscritorio().add("GENENV",form);
            vista.getPanelEscritorio().add( form, this, 1);
            form.setVisible(true);
            
        }
         
     }
    class ImprimirEtiquetasHandler  implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent ce) {
            if(ventanaAbierta("IMPETI")){
                return;
            }
            JEtiquetasViewer form = new JEtiquetasViewer();
            vista.getPanelEscritorio().add(form);
            form.setVisible(true);
        }
         
     }
    class SalidaEnvioHandler  implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent ce) {
            if(ventanaAbierta("REGDES")){
                return;
            }
            JRegistroSalidaViewer form = new JRegistroSalidaViewer();
            vista.getPanelEscritorio().add(form);
            form.setVisible(true);
        }
         
     }
    class ConfirmacionEnvioHandler  implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent ce) {
            if(ventanaAbierta("CONENV")){
                return;
            }
            JConfirmacionEntregaViewer form = new JConfirmacionEntregaViewer();
            vista.getPanelEscritorio().add(form);
            form.setVisible(true);
        }
         
     }
    
    class AltaClienteHandler  implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent ce) {
            if(ventanaAbierta("ABMCLI1")){
                return;
            }
            
            JABMClienteViewer form = new JABMClienteViewer(1);
            form.setName(form.getName()+"1");
            vista.getPanelEscritorio().add(form);
            
            form.setModo(new Integer(1));
            form.setVisible(true);
        }
         
     }     
     
    class BajaClienteHandler  implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent ce) {
            if(ventanaAbierta("ABMCLI3")){
                return;
            }
            JABMClienteViewer form = new JABMClienteViewer(3);
            form.setName(form.getName()+"3");
            vista.getPanelEscritorio().add(form);
            form.setVisible(true);
        }
    }
     class ModifClienteHandler  implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent ce) {
            if(ventanaAbierta("ABMCLI2")){
                return;
            }
            JABMClienteViewer form = new JABMClienteViewer(2);
            form.setName(form.getName()+"2");
            vista.getPanelEscritorio().add(form);
            form.setVisible(true);
        }
         
        
     }  
     class AltaTransporteHandler  implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent ce) {
            if(ventanaAbierta("ABMTRA1")){
                return;
            }
            JABMTransporteViewer form = new JABMTransporteViewer(1);
            form.setName(form.getName()+"1");
            vista.getPanelEscritorio().add(form);
            form.setVisible(true);
        }
         
     }
     
    class BajaTransporteHandler  implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent ce) {
            if(ventanaAbierta("ABMTRA3")){
                return;
            }
            JABMTransporteViewer form = new JABMTransporteViewer(3);
            form.setName(form.getName()+"3");
            vista.getPanelEscritorio().add(form);
            form.setVisible(true);
        }
    }
     class ModifTransporteHandler  implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent ce) {
            if(ventanaAbierta("ABMTRA2")){
                return;
            }
            JABMTransporteViewer form = new JABMTransporteViewer(2);
            form.setName(form.getName()+"2");
            vista.getPanelEscritorio().add(form);
            form.setVisible(true);
        }
         
        
     }  
     
     
     class LanzarReportesHandler  implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent ce) {
            if(ventanaAbierta("LANREP")){
                return;
            }
            JLanzadorReporteViewer form = new JLanzadorReporteViewer();            
            vista.getPanelEscritorio().add(form);
            form.setVisible(true);
        }
         
        
     }  
     
     private boolean ventanaAbierta(String nombre){
        boolean existe=false;
        JInternalFrame frames[] =vista.getPanelEscritorio().getAllFrames();
        if(frames.length>0){
            for (int i = 0; i < frames.length; i++) {
                JInternalFrame jInternalFrame = frames[i];
                if(jInternalFrame.getName().equals(nombre)){
                    existe=true;
                    break;
                }
            }
        }
        return existe;
     }
            
}

