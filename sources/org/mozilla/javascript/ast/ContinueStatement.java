package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class ContinueStatement extends Jump {
    private Name label;
    private Loop target;

    public ContinueStatement() {
        this.type = 122;
    }

    public Name getLabel() {
        return this.label;
    }

    public Loop getTarget() {
        return this.target;
    }

    public void setLabel(Name name) {
        this.label = name;
        if (name != null) {
            name.setParent(this);
        }
    }

    public void setTarget(Loop loop) {
        assertNotNull(loop);
        this.target = loop;
        setJumpStatement(loop);
    }

    @Override // org.mozilla.javascript.ast.Jump, org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(i2));
        sb.append("continue");
        if (this.label != null) {
            sb.append(" ");
            sb.append(this.label.toSource(0));
        }
        sb.append(";\n");
        return sb.toString();
    }

    @Override // org.mozilla.javascript.ast.Jump, org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        Name name;
        if (!nodeVisitor.visit(this) || (name = this.label) == null) {
            return;
        }
        name.visit(nodeVisitor);
    }

    public ContinueStatement(int i2) {
        this(i2, -1);
    }

    public ContinueStatement(int i2, int i3) {
        this.type = 122;
        this.position = i2;
        this.length = i3;
    }

    public ContinueStatement(Name name) {
        this.type = 122;
        setLabel(name);
    }

    public ContinueStatement(int i2, Name name) {
        this(i2);
        setLabel(name);
    }

    public ContinueStatement(int i2, int i3, Name name) {
        this(i2, i3);
        setLabel(name);
    }
}
