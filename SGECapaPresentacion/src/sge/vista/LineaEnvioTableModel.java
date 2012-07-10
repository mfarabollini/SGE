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
public class LineaEnvioTableModel implements TableModel {
    private List lineas;
    private LinkedList suscriptores = new LinkedList();
    
    public LineaEnvioTableModel(){
        lineas = new ArrayList();
    }  
    
    public LineaEnvioTableModel(List lineas){
       this.lineas = lineas;
    }    
    
    public void agregarLinea (Object aItem){
        lineas.add(aItem);
    }

    @Override
    public int getRowCount() {
        return lineas.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int i) {
        String nombre="";
        switch(i){
                case 0:nombre= "Pos";
                       break;
                case 1:nombre= "Cliente";
                       break;
                case 2:nombre= "N° Factura";
                       break;
                case 3:nombre= "Cant. bultos";
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
    
    
    
}
