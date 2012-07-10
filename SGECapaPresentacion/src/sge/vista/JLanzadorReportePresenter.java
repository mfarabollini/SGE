/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.vista;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import sge.clases.ReporteException;
import sge.interfaces.IReporte;
import sge.metadata.Grupo;
import sge.metadata.ParamReporte;
import sge.metadata.ReporteMetadata;
import sge.servicio.GestionEnvioServicios;
/**
 *
 * @author mboni
 */
public class JLanzadorReportePresenter {
    private JLanzadorReporteViewer vista;
    private List<Grupo> grupos;
    private ReporteMetadata reporteSeleccionado;
    private BuscarMetatdataChangeHandler buscarMetatdataChangeHandler = new BuscarMetatdataChangeHandler();
    private ReporteSeleccionadoHandler reporteSeleccionadoHandler = new ReporteSeleccionadoHandler();
    private LanzarReporteHandler lanzarReporteHandler = new LanzarReporteHandler();
    private CancelarReporteHandler cancelarReporteHandler = new CancelarReporteHandler();
    private BuscarEntidadHandler buscarEntidadHandler = new BuscarEntidadHandler();
            
    public JLanzadorReportePresenter(JLanzadorReporteViewer vista){
         this.vista = vista;
         grupos = null;
         reporteSeleccionado = null;
    }

    /**
     * @return the buscarMetatdataChangeHandler
     */
    public BuscarMetatdataChangeHandler getBuscarMetatdataChangeHandler() {
        return buscarMetatdataChangeHandler;
    }

    /**
     * @return the reporteSeleccionadoHandler
     */
    public ReporteSeleccionadoHandler getReporteSeleccionadoHandler() {
        return reporteSeleccionadoHandler;
    }

    /**
     * @return the lanzarReporteHandler
     */
    public LanzarReporteHandler getLanzarReporteHandler() {
        return lanzarReporteHandler;
    }

    /**
     * @return the cancelarReporteHandler
     */
    public CancelarReporteHandler getCancelarReporteHandler() {
        return cancelarReporteHandler;
    }

    /**
     * @return the buscarEntidadHandler
     */
    public BuscarEntidadHandler getBuscarEntidadHandler() {
        return buscarEntidadHandler;
    }
    
    public class BuscarMetatdataChangeHandler  implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent ce) {
            List rptList= null;
            Map datosTree = new HashMap();
            ArrayList arr = new ArrayList();
            ReporteTreeModel model= null;
            grupos = GestionEnvioServicios.buscarGruposReporte();
            for (Iterator<Grupo> it = grupos.iterator(); it.hasNext();) {
                rptList = new ArrayList();
                
                Grupo grupo = it.next();                
                arr.add(grupo.getDescripcion());
                List<ReporteMetadata> reportes = grupo.getReporteMetadataList();
                for (Iterator<ReporteMetadata> it1 = reportes.iterator(); it1.hasNext();) {
                    ReporteMetadata reporteMetadata = it1.next();
                    rptList.add(reporteMetadata.getNombrereporte());
                }
                datosTree.put(grupo.getDescripcion(), rptList);
            
            }
            datosTree.put("Reportes", arr);
            model = new ReporteTreeModel(datosTree,"Reportes");
            vista.getTreeReportes().setModel(model);
      
        }
        
    }
    
    public class ReporteSeleccionadoHandler  implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent ce) {
            Grupo aGrupo = null;
             List<ReporteMetadata>reportes = null;
            String nombre = vista.getTreeReportes().getSelectionPath().getPathComponent(1).toString().toUpperCase();
            Object nodo = vista.getTreeReportes().getLastSelectedPathComponent();
            
            for (Iterator<Grupo> it = grupos.iterator(); it.hasNext();) {
                Grupo grupo = it.next();
                if(grupo.getDescripcion().toUpperCase().equals(nombre)){
                    aGrupo = grupo;
                    break;
                }
            }
            reportes = aGrupo.getReporteMetadataList();
            nombre = vista.getTreeReportes().getSelectionPath().getPathComponent(2).toString().toUpperCase();
            for (Iterator<ReporteMetadata> it = reportes.iterator(); it.hasNext();) {
                ReporteMetadata reporteMetadata = it.next();
                if(reporteMetadata.getNombrereporte().toUpperCase().equals(nombre)){
                    reporteSeleccionado = reporteMetadata;
                    break;
                }
            }
           /*Pinto los parametros*/          
           mostrarParametros();             
        }        
    }
    
    public class LanzarReporteHandler  implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent ce) {
            int filas = vista.getTblParametros().getRowCount();
            String valor= "";
        
            if(!validarParametros()){
                vista.notificarMensaje("No se valorizaron uno o mas parametros, por favor informe los datos",JOptionPane.ERROR_MESSAGE);
                return;
            }                
                     
            IReporte aReporte = GestionEnvioServicios.buscarReporte(reporteSeleccionado.getKeyreporte());
            if(aReporte==null){
                vista.notificarMensaje("No se econtró el reporte, por favor notifique a sistemas",JOptionPane.ERROR_MESSAGE);
            }
            for (int i = 0; i < filas; i++) {
                valor = vista.getTblParametros().getModel().getValueAt(i, 1).toString().trim();
                aReporte.addParametro(valor);
            }
            try {
                aReporte.enviar(IReporte.DestinoReporte.PANTALLA);
            } catch (ReporteException ex) {
                vista.notificarMensaje(ex.getMensaje(),JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }  
    
    public class CancelarReporteHandler  implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent e) {
            reporteSeleccionado = null;            
        }
        
    }   
    
    public class BuscarEntidadHandler   implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent e) {
            Object aObject = null;
            Object aObject2 = null;
            Object aEntidad= null;
            Class<?> clase;
            Class<?> claseEntidad = null;
            String nombreClaseEntidad = null;
            String claseModel = null;
            
            int fila = vista.getTblParametros().getSelectedRow();
            
            Boolean esEntidad = (Boolean) vista.getTblParametros().getModel().getValueAt(fila, 4);
            
            if(!esEntidad){
                return;
            }
            nombreClaseEntidad =   vista.getTblParametros().getModel().getValueAt(fila, 3).toString();
            
            claseModel  = "sge.vista." + nombreClaseEntidad + "TableModel";
      
            try {
                clase = Class.forName(claseModel);   
                claseEntidad = Class.forName("sge.entidades." + nombreClaseEntidad);   
                aObject = clase.newInstance();  
                aObject2= claseEntidad.newInstance();  
                
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
                  
         
            JBuscarEntidadPresenter buscarEntPresenter = new JBuscarEntidadPresenter();
            buscarEntPresenter.setClase(aObject2.getClass());
            buscarEntPresenter.setResultado(new ArrayList());
            buscarEntPresenter.setHabilitado(false);
            buscarEntPresenter.setModel((TableModel) aObject);
            buscarEntPresenter.mostrar();   
            aEntidad = buscarEntPresenter.getEntidad(); 
            if(aEntidad!=null){
               try{
                Method  method = claseEntidad.getDeclaredMethod ("getId", null);
                    Object codigo = method.invoke(aEntidad, null);
                 
                    vista.getTblParametros().getModel().setValueAt(codigo.toString(), fila, 1)  ;
                    vista.getTblParametros().addNotify();
                } catch (Exception ex) {
                     System.out.println(ex.toString());
                }
            }
            
        }
    }
    
    private void mostrarParametros(){
        Map aItem = null;           
        GenericoTableModel aModel =(GenericoTableModel) vista.getTblParametros().getModel();
        List<ParamReporte> parametros = reporteSeleccionado.getParamReporteList();
        
        for (Iterator<ParamReporte> it = parametros.iterator(); it.hasNext();) {
            ParamReporte paramReporte = it.next();
            aItem = new HashMap();
            aItem.put("0", paramReporte.getDescripcion());
            aItem.put("1", new String());
            if(paramReporte.getEntidad()){
                aItem.put("2", new JButton("Buscar"));
            }else{
                aItem.put("2", "");
            }   
            aItem.put("3", paramReporte.getClase());
            aItem.put("4", paramReporte.getEntidad());
            aItem.put("5", paramReporte.getObligatorio());
            
            
            
            aModel.agregarLinea(aItem);  
        }
         vista.getTblParametros().addNotify();   
        
    }
    private boolean validarParametros(){
        boolean resultado = true;
        Boolean obligatorio = true;
        int filas = vista.getTblParametros().getRowCount();
        String valor= "";
        for (int i = 0; i < filas; i++) {         
            obligatorio = (Boolean) vista.getTblParametros().getModel().getValueAt(i, 5);
            if(obligatorio){
                valor = vista.getTblParametros().getModel().getValueAt(i, 1).toString().trim();
                if(valor.length()==0){
                   resultado = false;
                   break;
                }
            }
        }            
        return resultado;
    }
    
}
