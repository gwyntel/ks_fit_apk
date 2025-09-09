package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class ParenthesizedExpression extends AstNode {
    private AstNode expression;

    public ParenthesizedExpression() {
        this.type = 88;
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
        return makeIndent(i2) + "(" + this.expression.toSource(0) + ")";
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            this.expression.visit(nodeVisitor);
        }
    }

    public ParenthesizedExpression(int i2) {
        super(i2);
        this.type = 88;
    }

    public ParenthesizedExpression(int i2, int i3) {
        super(i2, i3);
        this.type = 88;
    }

    public ParenthesizedExpression(AstNode astNode) {
        this(astNode != null ? astNode.getPosition() : 0, astNode != null ? astNode.getLength() : 1, astNode);
    }

    public ParenthesizedExpression(int i2, int i3, AstNode astNode) {
        super(i2, i3);
        this.type = 88;
        setExpression(astNode);
    }
}
