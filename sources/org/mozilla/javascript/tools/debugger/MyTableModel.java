package org.mozilla.javascript.tools.debugger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/* loaded from: classes5.dex */
class MyTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 2971618907207577000L;
    private SwingGui debugGui;
    private List<String> expressions = Collections.synchronizedList(new ArrayList());
    private List<String> values = Collections.synchronizedList(new ArrayList());

    public MyTableModel(SwingGui swingGui) {
        this.debugGui = swingGui;
        this.expressions.add("");
        this.values.add("");
    }

    public int getColumnCount() {
        return 2;
    }

    public String getColumnName(int i2) {
        if (i2 == 0) {
            return "Expression";
        }
        if (i2 != 1) {
            return null;
        }
        return "Value";
    }

    public int getRowCount() {
        return this.expressions.size();
    }

    public Object getValueAt(int i2, int i3) {
        return i3 != 0 ? i3 != 1 ? "" : this.values.get(i2) : this.expressions.get(i2);
    }

    public boolean isCellEditable(int i2, int i3) {
        return true;
    }

    public void setValueAt(Object obj, int i2, int i3) {
        String strEval;
        if (i3 != 0) {
            if (i3 != 1) {
                return;
            }
            fireTableDataChanged();
            return;
        }
        String string = obj.toString();
        this.expressions.set(i2, string);
        if (string.length() <= 0 || (strEval = this.debugGui.dim.eval(string)) == null) {
            strEval = "";
        }
        this.values.set(i2, strEval);
        updateModel();
        int i4 = i2 + 1;
        if (i4 == this.expressions.size()) {
            this.expressions.add("");
            this.values.add("");
            fireTableRowsInserted(i4, i4);
        }
    }

    void updateModel() {
        String strEval;
        for (int i2 = 0; i2 < this.expressions.size(); i2++) {
            String str = this.expressions.get(i2);
            String str2 = "";
            if (str.length() > 0 && (strEval = this.debugGui.dim.eval(str)) != null) {
                str2 = strEval;
            }
            this.values.set(i2, str2.replace('\n', ' '));
        }
        fireTableDataChanged();
    }
}
