package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class ExpressionStatement extends AstNode {
    private AstNode expr;

    public ExpressionStatement() {
        this.type = 134;
    }

    public AstNode getExpression() {
        return this.expr;
    }

    @Override // org.mozilla.javascript.ast.AstNode, org.mozilla.javascript.Node
    public boolean hasSideEffects() {
        return this.type == 135 || this.expr.hasSideEffects();
    }

    public void setExpression(AstNode astNode) {
        assertNotNull(astNode);
        this.expr = astNode;
        astNode.setParent(this);
        setLineno(astNode.getLineno());
    }

    public void setHasResult() {
        this.type = 135;
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        return this.expr.toSource(i2) + ";\n";
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            this.expr.visit(nodeVisitor);
        }
    }

    public ExpressionStatement(AstNode astNode, boolean z2) {
        this(astNode);
        if (z2) {
            setHasResult();
        }
    }

    public ExpressionStatement(AstNode astNode) {
        this(astNode.getPosition(), astNode.getLength(), astNode);
    }

    public ExpressionStatement(int i2, int i3) {
        super(i2, i3);
        this.type = 134;
    }

    public ExpressionStatement(int i2, int i3, AstNode astNode) {
        super(i2, i3);
        this.type = 134;
        setExpression(astNode);
    }
}
