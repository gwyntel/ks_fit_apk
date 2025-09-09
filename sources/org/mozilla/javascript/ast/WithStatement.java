package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class WithStatement extends AstNode {
    private AstNode expression;
    private int lp;
    private int rp;
    private AstNode statement;

    public WithStatement() {
        this.lp = -1;
        this.rp = -1;
        this.type = 124;
    }

    public AstNode getExpression() {
        return this.expression;
    }

    public int getLp() {
        return this.lp;
    }

    public int getRp() {
        return this.rp;
    }

    public AstNode getStatement() {
        return this.statement;
    }

    public void setExpression(AstNode astNode) {
        assertNotNull(astNode);
        this.expression = astNode;
        astNode.setParent(this);
    }

    public void setLp(int i2) {
        this.lp = i2;
    }

    public void setParens(int i2, int i3) {
        this.lp = i2;
        this.rp = i3;
    }

    public void setRp(int i2) {
        this.rp = i2;
    }

    public void setStatement(AstNode astNode) {
        assertNotNull(astNode);
        this.statement = astNode;
        astNode.setParent(this);
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(i2));
        sb.append("with (");
        sb.append(this.expression.toSource(0));
        sb.append(") ");
        if (this.statement.getType() == 130) {
            sb.append(this.statement.toSource(i2).trim());
            sb.append("\n");
        } else {
            sb.append("\n");
            sb.append(this.statement.toSource(i2 + 1));
        }
        return sb.toString();
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            this.expression.visit(nodeVisitor);
            this.statement.visit(nodeVisitor);
        }
    }

    public WithStatement(int i2) {
        super(i2);
        this.lp = -1;
        this.rp = -1;
        this.type = 124;
    }

    public WithStatement(int i2, int i3) {
        super(i2, i3);
        this.lp = -1;
        this.rp = -1;
        this.type = 124;
    }
}
