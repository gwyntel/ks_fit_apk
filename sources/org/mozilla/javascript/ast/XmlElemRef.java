package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class XmlElemRef extends XmlRef {
    private AstNode indexExpr;
    private int lb;
    private int rb;

    public XmlElemRef() {
        this.lb = -1;
        this.rb = -1;
        this.type = 78;
    }

    public AstNode getExpression() {
        return this.indexExpr;
    }

    public int getLb() {
        return this.lb;
    }

    public int getRb() {
        return this.rb;
    }

    public void setBrackets(int i2, int i3) {
        this.lb = i2;
        this.rb = i3;
    }

    public void setExpression(AstNode astNode) {
        assertNotNull(astNode);
        this.indexExpr = astNode;
        astNode.setParent(this);
    }

    public void setLb(int i2) {
        this.lb = i2;
    }

    public void setRb(int i2) {
        this.rb = i2;
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(i2));
        if (isAttributeAccess()) {
            sb.append("@");
        }
        Name name = this.namespace;
        if (name != null) {
            sb.append(name.toSource(0));
            sb.append("::");
        }
        sb.append("[");
        sb.append(this.indexExpr.toSource(0));
        sb.append("]");
        return sb.toString();
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            Name name = this.namespace;
            if (name != null) {
                name.visit(nodeVisitor);
            }
            this.indexExpr.visit(nodeVisitor);
        }
    }

    public XmlElemRef(int i2) {
        super(i2);
        this.lb = -1;
        this.rb = -1;
        this.type = 78;
    }

    public XmlElemRef(int i2, int i3) {
        super(i2, i3);
        this.lb = -1;
        this.rb = -1;
        this.type = 78;
    }
}
