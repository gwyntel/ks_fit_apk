package org.mozilla.javascript.tools.debugger.treetable;

import javax.swing.tree.TreeModel;

/* loaded from: classes5.dex */
public interface TreeTableModel extends TreeModel {
    Class<?> getColumnClass(int i2);

    int getColumnCount();

    String getColumnName(int i2);

    Object getValueAt(Object obj, int i2);

    boolean isCellEditable(Object obj, int i2);

    void setValueAt(Object obj, Object obj2, int i2);
}
