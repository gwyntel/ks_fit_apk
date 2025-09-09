package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class EmptyExpression extends AstNode {
    public EmptyExpression() {
        this.type = 129;
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        return makeIndent(i2);
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        nodeVisitor.visit(this);
    }

    public EmptyExpression(int i2) {
        super(i2);
        this.type = 129;
    }

    public EmptyExpression(int i2, int i3) {
        super(i2, i3);
        this.type = 129;
    }
}
