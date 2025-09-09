package org.mozilla.javascript.tools.debugger;

import java.awt.Dimension;
import java.util.EventObject;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import org.mozilla.javascript.tools.debugger.treetable.JTreeTable;
import org.mozilla.javascript.tools.debugger.treetable.TreeTableModel;
import org.mozilla.javascript.tools.debugger.treetable.TreeTableModelAdapter;

/* loaded from: classes5.dex */
class MyTreeTable extends JTreeTable {
    private static final long serialVersionUID = 3457265548184453049L;

    public MyTreeTable(VariableModel variableModel) {
        super(variableModel);
    }

    public boolean isCellEditable(EventObject eventObject) {
        return eventObject == null;
    }

    public JTree resetTree(TreeTableModel treeTableModel) {
        this.tree = new JTreeTable.TreeTableCellRenderer(treeTableModel);
        super.setModel(new TreeTableModelAdapter(treeTableModel, this.tree));
        TreeSelectionModel listToTreeSelectionModelWrapper = new JTreeTable.ListToTreeSelectionModelWrapper();
        this.tree.setSelectionModel(listToTreeSelectionModelWrapper);
        setSelectionModel(listToTreeSelectionModelWrapper.getListSelectionModel());
        if (this.tree.getRowHeight() < 1) {
            setRowHeight(18);
        }
        setDefaultRenderer(TreeTableModel.class, this.tree);
        setDefaultEditor(TreeTableModel.class, new JTreeTable.TreeTableCellEditor());
        setShowGrid(true);
        setIntercellSpacing(new Dimension(1, 1));
        this.tree.setRootVisible(false);
        this.tree.setShowsRootHandles(true);
        DefaultTreeCellRenderer cellRenderer = this.tree.getCellRenderer();
        cellRenderer.setOpenIcon((Icon) null);
        cellRenderer.setClosedIcon((Icon) null);
        cellRenderer.setLeafIcon((Icon) null);
        return this.tree;
    }
}
