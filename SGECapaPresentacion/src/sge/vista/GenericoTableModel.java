/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.vista;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author mboni
 */
public class GenericoTableModel implements TableModel{
   private List lineas;
   private String cabeceras[];
   private boolean editables[];

    
   private LinkedList suscriptores = new LinkedList();

    public void setLineas(List lineas) {
        this.lineas = lineas;
    }
   
    
    public GenericoTableModel(){
        lineas = new ArrayList();
    }
    
    public void agregarLinea (Object aItem){
        lineas.add(aItem);
    }
    
    public boolean[] getEditables() {
        return editables;
    }

    public void setEditables(boolean[] editables) {
        this.editables = editables;
    }
    @Override
    public int getRowCount() {
        return lineas.size();
    }

    @Override
    public int getColumnCount() {
        return getCabeceras().length;
    }

    @Override
    public String getColumnName(int i) {
       
        return getCabeceras()[i];
        
    }

    @Override
    public Class<?> getColumnClass(int i) {
        Class clase = Object.class;
 
        Object aux = getValueAt(0, i);
        if (aux != null) {
        clase = aux.getClass();
        }  
        return clase;
        
        
       //return String.class;
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        
        return editables[i1];
        
    }

    @Override
    public Object getValueAt(int i, int i1) {
        Map aLinea = (Map) lineas.get(i);
        String ind = String.valueOf(i1);
        return aLinea.get(ind);
        
    }

    @Override
    public void setValueAt(Object o, int i, int i1) {
        Map aLinea = (Map) lineas.get(i);
        String ind = String.valueOf(i1);
        aLinea.remove(ind);
        aLinea.put(ind, o);
       
        TableModelEvent evento = new TableModelEvent (this,  i, i,i1, TableModelEvent.UPDATE);      
        ((TableModelListener)suscriptores.get(0)).tableChanged( evento);
        
    }

    @Override
    public void addTableModelListener(TableModelListener tl) {
         suscriptores.add(tl);
    }

    @Override
    public void removeTableModelListener(TableModelListener tl) {
        suscriptores.remove(tl);
    }

    void limpiar() {
        lineas.clear();
   
    }

    /**
     * @return the cabeceras
     */
    public String[] getCabeceras() {
        return cabeceras;
    }

    /**
     * @param cabeceras the cabeceras to set
     */
    public void setCabeceras(String[] cabeceras) {
        this.cabeceras = cabeceras;
    }
    
    
   
}
