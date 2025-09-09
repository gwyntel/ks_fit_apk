package org.mozilla.javascript.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class ArrayLiteral extends AstNode implements DestructuringForm {
    private static final List<AstNode> NO_ELEMS = Collections.unmodifiableList(new ArrayList());
    private int destructuringLength;
    private List<AstNode> elements;
    private boolean isDestructuring;
    private int skipCount;

    public ArrayLiteral() {
        this.type = 66;
    }

    public void addElement(AstNode astNode) {
        assertNotNull(astNode);
        if (this.elements == null) {
            this.elements = new ArrayList();
        }
        this.elements.add(astNode);
        astNode.setParent(this);
    }

    public int getDestructuringLength() {
        return this.destructuringLength;
    }

    public AstNode getElement(int i2) {
        List<AstNode> list = this.elements;
        if (list != null) {
            return list.get(i2);
        }
        throw new IndexOutOfBoundsException("no elements");
    }

    public List<AstNode> getElements() {
        List<AstNode> list = this.elements;
        return list != null ? list : NO_ELEMS;
    }

    public int getSize() {
        List<AstNode> list = this.elements;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public int getSkipCount() {
        return this.skipCount;
    }

    @Override // org.mozilla.javascript.ast.DestructuringForm
    public boolean isDestructuring() {
        return this.isDestructuring;
    }

    public void setDestructuringLength(int i2) {
        this.destructuringLength = i2;
    }

    public void setElements(List<AstNode> list) {
        if (list == null) {
            this.elements = null;
            return;
        }
        List<AstNode> list2 = this.elements;
        if (list2 != null) {
            list2.clear();
        }
        Iterator<AstNode> it = list.iterator();
        while (it.hasNext()) {
            addElement(it.next());
        }
    }

    @Override // org.mozilla.javascript.ast.DestructuringForm
    public void setIsDestructuring(boolean z2) {
        this.isDestructuring = z2;
    }

    public void setSkipCount(int i2) {
        this.skipCount = i2;
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(i2));
        sb.append("[");
        List<AstNode> list = this.elements;
        if (list != null) {
            printList(list, sb);
        }
        sb.append("]");
        return sb.toString();
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            Iterator<AstNode> it = getElements().iterator();
            while (it.hasNext()) {
                it.next().visit(nodeVisitor);
            }
        }
    }

    public ArrayLiteral(int i2) {
        super(i2);
        this.type = 66;
    }

    public ArrayLiteral(int i2, int i3) {
        super(i2, i3);
        this.type = 66;
    }
}
