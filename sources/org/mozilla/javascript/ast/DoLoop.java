package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class DoLoop extends Loop {
    private AstNode condition;
    private int whilePosition;

    public DoLoop() {
        this.whilePosition = -1;
        this.type = 119;
    }

    public AstNode getCondition() {
        return this.condition;
    }

    public int getWhilePosition() {
        return this.whilePosition;
    }

    public void setCondition(AstNode astNode) {
        assertNotNull(astNode);
        this.condition = astNode;
        astNode.setParent(this);
    }

    public void setWhilePosition(int i2) {
        this.whilePosition = i2;
    }

    @Override // org.mozilla.javascript.ast.Scope, org.mozilla.javascript.ast.Jump, org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        return makeIndent(i2) + "do " + this.body.toSource(i2).trim() + " while (" + this.condition.toSource(0) + ");\n";
    }

    @Override // org.mozilla.javascript.ast.Scope, org.mozilla.javascript.ast.Jump, org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            this.body.visit(nodeVisitor);
            this.condition.visit(nodeVisitor);
        }
    }

    public DoLoop(int i2) {
        super(i2);
        this.whilePosition = -1;
        this.type = 119;
    }

    public DoLoop(int i2, int i3) {
        super(i2, i3);
        this.whilePosition = -1;
        this.type = 119;
    }
}
