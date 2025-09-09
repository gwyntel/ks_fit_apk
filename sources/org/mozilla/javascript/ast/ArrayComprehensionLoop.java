package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class ArrayComprehensionLoop extends ForInLoop {
    public ArrayComprehensionLoop() {
    }

    @Override // org.mozilla.javascript.ast.Loop
    public AstNode getBody() {
        return null;
    }

    @Override // org.mozilla.javascript.ast.Loop
    public void setBody(AstNode astNode) {
        throw new UnsupportedOperationException("this node type has no body");
    }

    @Override // org.mozilla.javascript.ast.ForInLoop, org.mozilla.javascript.ast.Scope, org.mozilla.javascript.ast.Jump, org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(i2));
        sb.append(" for ");
        sb.append(isForEach() ? "each " : "");
        sb.append("(");
        sb.append(this.iterator.toSource(0));
        sb.append(isForOf() ? " of " : " in ");
        sb.append(this.iteratedObject.toSource(0));
        sb.append(")");
        return sb.toString();
    }

    @Override // org.mozilla.javascript.ast.ForInLoop, org.mozilla.javascript.ast.Scope, org.mozilla.javascript.ast.Jump, org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            this.iterator.visit(nodeVisitor);
            this.iteratedObject.visit(nodeVisitor);
        }
    }

    public ArrayComprehensionLoop(int i2) {
        super(i2);
    }

    public ArrayComprehensionLoop(int i2, int i3) {
        super(i2, i3);
    }
}
