package org.mozilla.javascript.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class ArrayComprehension extends Scope {
    private AstNode filter;
    private int ifPosition;
    private List<ArrayComprehensionLoop> loops;
    private int lp;
    private AstNode result;
    private int rp;

    public ArrayComprehension() {
        this.loops = new ArrayList();
        this.ifPosition = -1;
        this.lp = -1;
        this.rp = -1;
        this.type = 158;
    }

    public void addLoop(ArrayComprehensionLoop arrayComprehensionLoop) {
        assertNotNull(arrayComprehensionLoop);
        this.loops.add(arrayComprehensionLoop);
        arrayComprehensionLoop.setParent(this);
    }

    public AstNode getFilter() {
        return this.filter;
    }

    public int getFilterLp() {
        return this.lp;
    }

    public int getFilterRp() {
        return this.rp;
    }

    public int getIfPosition() {
        return this.ifPosition;
    }

    public List<ArrayComprehensionLoop> getLoops() {
        return this.loops;
    }

    public AstNode getResult() {
        return this.result;
    }

    public void setFilter(AstNode astNode) {
        this.filter = astNode;
        if (astNode != null) {
            astNode.setParent(this);
        }
    }

    public void setFilterLp(int i2) {
        this.lp = i2;
    }

    public void setFilterRp(int i2) {
        this.rp = i2;
    }

    public void setIfPosition(int i2) {
        this.ifPosition = i2;
    }

    public void setLoops(List<ArrayComprehensionLoop> list) {
        assertNotNull(list);
        this.loops.clear();
        Iterator<ArrayComprehensionLoop> it = list.iterator();
        while (it.hasNext()) {
            addLoop(it.next());
        }
    }

    public void setResult(AstNode astNode) {
        assertNotNull(astNode);
        this.result = astNode;
        astNode.setParent(this);
    }

    @Override // org.mozilla.javascript.ast.Scope, org.mozilla.javascript.ast.Jump, org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        StringBuilder sb = new StringBuilder(250);
        sb.append("[");
        sb.append(this.result.toSource(0));
        Iterator<ArrayComprehensionLoop> it = this.loops.iterator();
        while (it.hasNext()) {
            sb.append(it.next().toSource(0));
        }
        if (this.filter != null) {
            sb.append(" if (");
            sb.append(this.filter.toSource(0));
            sb.append(")");
        }
        sb.append("]");
        return sb.toString();
    }

    @Override // org.mozilla.javascript.ast.Scope, org.mozilla.javascript.ast.Jump, org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            this.result.visit(nodeVisitor);
            Iterator<ArrayComprehensionLoop> it = this.loops.iterator();
            while (it.hasNext()) {
                it.next().visit(nodeVisitor);
            }
            AstNode astNode = this.filter;
            if (astNode != null) {
                astNode.visit(nodeVisitor);
            }
        }
    }

    public ArrayComprehension(int i2) {
        super(i2);
        this.loops = new ArrayList();
        this.ifPosition = -1;
        this.lp = -1;
        this.rp = -1;
        this.type = 158;
    }

    public ArrayComprehension(int i2, int i3) {
        super(i2, i3);
        this.loops = new ArrayList();
        this.ifPosition = -1;
        this.lp = -1;
        this.rp = -1;
        this.type = 158;
    }
}
