package org.mozilla.javascript.tools.debugger;

import java.util.Arrays;
import java.util.Comparator;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;
import org.mozilla.javascript.tools.debugger.treetable.TreeTableModel;

/* loaded from: classes5.dex */
class VariableModel implements TreeTableModel {
    private Dim debugger;
    private VariableNode root;
    private static final String[] cNames = {" Name", " Value"};
    private static final Class<?>[] cTypes = {TreeTableModel.class, String.class};
    private static final VariableNode[] CHILDLESS = new VariableNode[0];

    private static class VariableNode {
        private VariableNode[] children;
        private Object id;
        private Object object;

        public VariableNode(Object obj, Object obj2) {
            this.object = obj;
            this.id = obj2;
        }

        public String toString() {
            Object obj = this.id;
            if (obj instanceof String) {
                return (String) obj;
            }
            return "[" + ((Integer) this.id).intValue() + "]";
        }
    }

    public VariableModel() {
    }

    private VariableNode[] children(VariableNode variableNode) {
        VariableNode[] variableNodeArr;
        if (variableNode.children != null) {
            return variableNode.children;
        }
        Object value = getValue(variableNode);
        Object[] objectIds = this.debugger.getObjectIds(value);
        if (objectIds == null || objectIds.length == 0) {
            variableNodeArr = CHILDLESS;
        } else {
            Arrays.sort(objectIds, new Comparator<Object>() { // from class: org.mozilla.javascript.tools.debugger.VariableModel.1
                @Override // java.util.Comparator
                public int compare(Object obj, Object obj2) {
                    if (obj instanceof String) {
                        if (obj2 instanceof Integer) {
                            return -1;
                        }
                        return ((String) obj).compareToIgnoreCase((String) obj2);
                    }
                    if (obj2 instanceof String) {
                        return 1;
                    }
                    return ((Integer) obj).intValue() - ((Integer) obj2).intValue();
                }
            });
            variableNodeArr = new VariableNode[objectIds.length];
            for (int i2 = 0; i2 != objectIds.length; i2++) {
                variableNodeArr[i2] = new VariableNode(value, objectIds[i2]);
            }
        }
        variableNode.children = variableNodeArr;
        return variableNodeArr;
    }

    public void addTreeModelListener(TreeModelListener treeModelListener) {
    }

    public Object getChild(Object obj, int i2) {
        if (this.debugger == null) {
            return null;
        }
        return children((VariableNode) obj)[i2];
    }

    public int getChildCount(Object obj) {
        if (this.debugger == null) {
            return 0;
        }
        return children((VariableNode) obj).length;
    }

    @Override // org.mozilla.javascript.tools.debugger.treetable.TreeTableModel
    public Class<?> getColumnClass(int i2) {
        return cTypes[i2];
    }

    @Override // org.mozilla.javascript.tools.debugger.treetable.TreeTableModel
    public int getColumnCount() {
        return cNames.length;
    }

    @Override // org.mozilla.javascript.tools.debugger.treetable.TreeTableModel
    public String getColumnName(int i2) {
        return cNames[i2];
    }

    public int getIndexOfChild(Object obj, Object obj2) {
        if (this.debugger == null) {
            return -1;
        }
        VariableNode variableNode = (VariableNode) obj2;
        VariableNode[] variableNodeArrChildren = children((VariableNode) obj);
        for (int i2 = 0; i2 != variableNodeArrChildren.length; i2++) {
            if (variableNodeArrChildren[i2] == variableNode) {
                return i2;
            }
        }
        return -1;
    }

    public Object getRoot() {
        if (this.debugger == null) {
            return null;
        }
        return this.root;
    }

    public Object getValue(VariableNode variableNode) {
        try {
            return this.debugger.getObjectProperty(variableNode.object, variableNode.id);
        } catch (Exception unused) {
            return "undefined";
        }
    }

    @Override // org.mozilla.javascript.tools.debugger.treetable.TreeTableModel
    public Object getValueAt(Object obj, int i2) {
        String message;
        Dim dim = this.debugger;
        if (dim == null) {
            return null;
        }
        VariableNode variableNode = (VariableNode) obj;
        if (i2 == 0) {
            return variableNode.toString();
        }
        if (i2 != 1) {
            return null;
        }
        try {
            message = dim.objectToString(getValue(variableNode));
        } catch (RuntimeException e2) {
            message = e2.getMessage();
        }
        StringBuilder sb = new StringBuilder();
        int length = message.length();
        for (int i3 = 0; i3 < length; i3++) {
            char cCharAt = message.charAt(i3);
            if (Character.isISOControl(cCharAt)) {
                cCharAt = ' ';
            }
            sb.append(cCharAt);
        }
        return sb.toString();
    }

    @Override // org.mozilla.javascript.tools.debugger.treetable.TreeTableModel
    public boolean isCellEditable(Object obj, int i2) {
        return i2 == 0;
    }

    public boolean isLeaf(Object obj) {
        return this.debugger == null || children((VariableNode) obj).length == 0;
    }

    public void removeTreeModelListener(TreeModelListener treeModelListener) {
    }

    @Override // org.mozilla.javascript.tools.debugger.treetable.TreeTableModel
    public void setValueAt(Object obj, Object obj2, int i2) {
    }

    public void valueForPathChanged(TreePath treePath, Object obj) {
    }

    public VariableModel(Dim dim, Object obj) {
        this.debugger = dim;
        this.root = new VariableNode(obj, "this");
    }
}
