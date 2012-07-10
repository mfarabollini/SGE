/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.vista;

import java.util.List;
import java.util.Map;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author mboni
 */
public class ReporteTreeModel implements TreeModel {
    protected EventListenerList listeners = new EventListenerList();

    private Map map;

    private Object root;
    
    public ReporteTreeModel(Map datos, Object root){
        map = datos;
        this.root= root;
    }
    
    @Override
    public Object getRoot() {
         return root;
    }

    @Override
    public Object getChild(Object o, int i) {
         return ((List)map.get(o)).get(i);
    }

    @Override
    public int getChildCount(Object o) {
        List children = (List)map.get(o); 
        return children == null ? 0 : children.size();
    }

    @Override
    public boolean isLeaf(Object o) {
        return !map.containsKey(o);
    }

    @Override
    public void valueForPathChanged(TreePath tp, Object o) {
       
    }

    @Override
    public int getIndexOfChild(Object o, Object o1) {
        return ((List)map.get(o)).indexOf(o1);
    }

    @Override
    public void addTreeModelListener(TreeModelListener tl) {
       listeners.add(TreeModelListener.class, tl);

    }

    @Override
    public void removeTreeModelListener(TreeModelListener tl) {
        listeners.remove(TreeModelListener.class, tl);
    }
    
}
