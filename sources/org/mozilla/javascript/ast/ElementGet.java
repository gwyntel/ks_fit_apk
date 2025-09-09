package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class ElementGet extends AstNode {
    private AstNode element;
    private int lb;
    private int rb;
    private AstNode target;

    public ElementGet() {
        this.lb = -1;
        this.rb = -1;
        this.type = 36;
    }

    public AstNode getElement() {
        return this.element;
    }

    public int getLb() {
        return this.lb;
    }

    public int getRb() {
        return this.rb;
    }

    public AstNode getTarget() {
        return this.target;
    }

    public void setElement(AstNode astNode) {
        assertNotNull(astNode);
        this.element = astNode;
        astNode.setParent(this);
    }

    public void setLb(int i2) {
        this.lb = i2;
    }

    public void setParens(int i2, int i3) {
        this.lb = i2;
        this.rb = i3;
    }

    public void setRb(int i2) {
        this.rb = i2;
    }

    public void setTarget(AstNode astNode) {
        assertNotNull(astNode);
        this.target = astNode;
        astNode.setParent(this);
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        return makeIndent(i2) + this.target.toSource(0) + "[" + this.element.toSource(0) + "]";
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            this.target.visit(nodeVisitor);
            this.element.visit(nodeVisitor);
        }
    }

    public ElementGet(int i2) {
        super(i2);
        this.lb = -1;
        this.rb = -1;
        this.type = 36;
    }

    public ElementGet(int i2, int i3) {
        super(i2, i3);
        this.lb = -1;
        this.rb = -1;
        this.type = 36;
    }

    public ElementGet(AstNode astNode, AstNode astNode2) {
        this.lb = -1;
        this.rb = -1;
        this.type = 36;
        setTarget(astNode);
        setElement(astNode2);
    }
}
