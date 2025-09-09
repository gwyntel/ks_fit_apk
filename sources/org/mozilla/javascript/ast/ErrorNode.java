package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class ErrorNode extends AstNode {
    private String message;

    public ErrorNode() {
        this.type = -1;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        return "";
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        nodeVisitor.visit(this);
    }

    public ErrorNode(int i2) {
        super(i2);
        this.type = -1;
    }

    public ErrorNode(int i2, int i3) {
        super(i2, i3);
        this.type = -1;
    }
}
