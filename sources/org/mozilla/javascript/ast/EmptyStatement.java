package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class EmptyStatement extends AstNode {
    public EmptyStatement() {
        this.type = 129;
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        return makeIndent(i2) + ";\n";
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        nodeVisitor.visit(this);
    }

    public EmptyStatement(int i2) {
        super(i2);
        this.type = 129;
    }

    public EmptyStatement(int i2, int i3) {
        super(i2, i3);
        this.type = 129;
    }
}
