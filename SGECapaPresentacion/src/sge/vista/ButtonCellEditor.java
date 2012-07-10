/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.vista;

/**
 *
 * @author mboni
 */
import java.awt.Component;
import java.awt.event.ActionEvent;
 
import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
 
public class ButtonCellEditor extends AbstractCellEditor implements TableCellEditor {
 
 /** Componente que estamos editando. */
 private Component currentValue;
 private JLanzadorReportePresenter presenter;
 public ButtonCellEditor(){
     
 }
 public ButtonCellEditor(JLanzadorReportePresenter presenter){
     this.presenter = presenter;
 }
 @Override
 public Component getTableCellEditorComponent(final JTable table, Object value, boolean isSelected, final int row, int column) {
    JButton button = null;
    
    if (value instanceof JButton) {
        button = (JButton) value;
        button.setAction(new AbstractAction("Buscar") {        
            @Override
            public void actionPerformed(ActionEvent e) {
                table.setRowSelectionInterval(row, row);
                presenter.getBuscarEntidadHandler().stateChanged(new ChangeEvent(this)); 
            }
        });
    } 
    currentValue = button;
    return button;
 }
 
 @Override
 public Object getCellEditorValue() {
  return currentValue;
 }
 
}