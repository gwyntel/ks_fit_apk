package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class ThrowStatement extends AstNode {
    private AstNode expression;

    public ThrowStatement() {
        this.type = 50;
    }

    public AstNode getExpression() {
        return this.expression;
    }

    public void setExpression(AstNode astNode) {
        assertNotNull(astNode);
        this.expression = astNode;
        astNode.setParent(this);
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        return makeIndent(i2) + "throw " + this.expression.toSource(0) + ";\n";
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            this.expression.visit(nodeVisitor);
        }
    }

    public ThrowStatement(int i2) {
        super(i2);
        this.type = 50;
    }

    public ThrowStatement(int i2, int i3) {
        super(i2, i3);
        this.type = 50;
    }

    public ThrowStatement(AstNode astNode) {
        this.type = 50;
        setExpression(astNode);
    }

    public ThrowStatement(int i2, AstNode astNode) {
        super(i2, astNode.getLength());
        this.type = 50;
        setExpression(astNode);
    }

    public ThrowStatement(int i2, int i3, AstNode astNode) {
        super(i2, i3);
        this.type = 50;
        setExpression(astNode);
    }
}
