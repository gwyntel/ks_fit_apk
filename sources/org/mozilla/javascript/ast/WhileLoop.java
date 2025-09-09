package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class WhileLoop extends Loop {
    private AstNode condition;

    public WhileLoop() {
        this.type = 118;
    }

    public AstNode getCondition() {
        return this.condition;
    }

    public void setCondition(AstNode astNode) {
        assertNotNull(astNode);
        this.condition = astNode;
        astNode.setParent(this);
    }

    @Override // org.mozilla.javascript.ast.Scope, org.mozilla.javascript.ast.Jump, org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(i2));
        sb.append("while (");
        sb.append(this.condition.toSource(0));
        sb.append(") ");
        if (this.body.getType() == 130) {
            sb.append(this.body.toSource(i2).trim());
            sb.append("\n");
        } else {
            sb.append("\n");
            sb.append(this.body.toSource(i2 + 1));
        }
        return sb.toString();
    }

    @Override // org.mozilla.javascript.ast.Scope, org.mozilla.javascript.ast.Jump, org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            this.condition.visit(nodeVisitor);
            this.body.visit(nodeVisitor);
        }
    }

    public WhileLoop(int i2) {
        super(i2);
        this.type = 118;
    }

    public WhileLoop(int i2, int i3) {
        super(i2, i3);
        this.type = 118;
    }
}
