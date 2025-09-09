package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class BreakStatement extends Jump {
    private Name breakLabel;
    private AstNode target;

    public BreakStatement() {
        this.type = 121;
    }

    public Name getBreakLabel() {
        return this.breakLabel;
    }

    public AstNode getBreakTarget() {
        return this.target;
    }

    public void setBreakLabel(Name name) {
        this.breakLabel = name;
        if (name != null) {
            name.setParent(this);
        }
    }

    public void setBreakTarget(Jump jump) throws RuntimeException {
        assertNotNull(jump);
        this.target = jump;
        setJumpStatement(jump);
    }

    @Override // org.mozilla.javascript.ast.Jump, org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(i2));
        sb.append("break");
        if (this.breakLabel != null) {
            sb.append(" ");
            sb.append(this.breakLabel.toSource(0));
        }
        sb.append(";\n");
        return sb.toString();
    }

    @Override // org.mozilla.javascript.ast.Jump, org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        Name name;
        if (!nodeVisitor.visit(this) || (name = this.breakLabel) == null) {
            return;
        }
        name.visit(nodeVisitor);
    }

    public BreakStatement(int i2) {
        this.type = 121;
        this.position = i2;
    }

    public BreakStatement(int i2, int i3) {
        this.type = 121;
        this.position = i2;
        this.length = i3;
    }
}
