/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.vista;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import sge.entidades.MedioEnvio;

/**
 *
 * @author mboni
 */
public class MedioEnvioTableModel implements TableModel{
    private List lineas;
    private LinkedList suscriptores = new LinkedList();
    
    public MedioEnvioTableModel() {
         lineas = new ArrayList();
    }
    public MedioEnvioTableModel(List lineas) {
        this.lineas = lineas;
    }
    public void agregarLinea (Object aItem){
        getLineas().add(aItem);
    }
    
    @Override
    public int getRowCount() {
        return getLineas().size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int i) {
        String nombre="";
        switch(i){
                case 0:nombre= "Codigo";
                       break;
                case 1:nombre= "Razón social";
                       break;
                case 2:nombre= "Cuit";
                       break;
                case 3:nombre= "Dirección";
                       break;
                case 4:nombre= "Tel";
                       break;    
                case 5:nombre= "Email";
                       break;    
        }
        return nombre;
    }

    @Override
    public Class<?> getColumnClass(int i) {
         return String.class;
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        return false;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        Object object = null;
        MedioEnvio aMedio = (MedioEnvio) getLineas().get(i);
        switch(i1){
                case 0:object= aMedio.getId().toString();
                       break;
                case 1:object= aMedio.getRazonSocial();
                       break;
                case 2:object= aMedio.getCuit();
                       break;
             /*   case 3:object= aCliente.get;
                       break;
                case 4:object= "Tel";
                       break;    
                case 5:object= "Email";
                       break;    */
        }
        
        return object;
    }

    @Override
    public void setValueAt(Object o, int i, int i1) {
        
        MedioEnvio aMedio= (MedioEnvio) getLineas().get(i);
         switch(i1){
                case 0:aMedio.setIdmedio(Integer.getInteger(o.toString()));
                       break;
                case 1: aMedio.setRazonSocial(o.toString());
                       break;
                case 2:aMedio.setCuit(o.toString());
                       break;
         }
    }

    @Override
    public void addTableModelListener(TableModelListener tl) {
        suscriptores.add(tl);
    }

    @Override
    public void removeTableModelListener(TableModelListener tl) {
        suscriptores.remove(tl);
    }

    /**
     * @return the lineas
     */
    public List getLineas() {
        return lineas;
    }

    /**
     * @param lineas the lineas to set
     */
    public void setLineas(List lineas) {
        this.lineas = lineas;
    }
    
}
