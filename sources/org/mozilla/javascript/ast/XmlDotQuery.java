package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class XmlDotQuery extends InfixExpression {
    private int rp;

    public XmlDotQuery() {
        this.rp = -1;
        this.type = 147;
    }

    public int getRp() {
        return this.rp;
    }

    public void setRp(int i2) {
        this.rp = i2;
    }

    @Override // org.mozilla.javascript.ast.InfixExpression, org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        return makeIndent(i2) + getLeft().toSource(0) + ".(" + getRight().toSource(0) + ")";
    }

    public XmlDotQuery(int i2) {
        super(i2);
        this.rp = -1;
        this.type = 147;
    }

    public XmlDotQuery(int i2, int i3) {
        super(i2, i3);
        this.rp = -1;
        this.type = 147;
    }
}
