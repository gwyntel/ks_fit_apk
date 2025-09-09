package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class PropertyGet extends InfixExpression {
    public PropertyGet() {
        this.type = 33;
    }

    public Name getProperty() {
        return (Name) getRight();
    }

    public AstNode getTarget() {
        return getLeft();
    }

    public void setProperty(Name name) {
        setRight(name);
    }

    public void setTarget(AstNode astNode) {
        setLeft(astNode);
    }

    @Override // org.mozilla.javascript.ast.InfixExpression, org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        return makeIndent(i2) + getLeft().toSource(0) + "." + getRight().toSource(0);
    }

    @Override // org.mozilla.javascript.ast.InfixExpression, org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            getTarget().visit(nodeVisitor);
            getProperty().visit(nodeVisitor);
        }
    }

    public PropertyGet(int i2) {
        super(i2);
        this.type = 33;
    }

    public PropertyGet(int i2, int i3) {
        super(i2, i3);
        this.type = 33;
    }

    public PropertyGet(int i2, int i3, AstNode astNode, Name name) {
        super(i2, i3, astNode, name);
        this.type = 33;
    }

    public PropertyGet(AstNode astNode, Name name) {
        super(astNode, name);
        this.type = 33;
    }

    public PropertyGet(AstNode astNode, Name name, int i2) {
        super(33, astNode, name, i2);
        this.type = 33;
    }
}
