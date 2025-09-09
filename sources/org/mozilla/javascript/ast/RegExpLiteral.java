package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class RegExpLiteral extends AstNode {
    private String flags;
    private String value;

    public RegExpLiteral() {
        this.type = 48;
    }

    public String getFlags() {
        return this.flags;
    }

    public String getValue() {
        return this.value;
    }

    public void setFlags(String str) {
        this.flags = str;
    }

    public void setValue(String str) {
        assertNotNull(str);
        this.value = str;
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(i2));
        sb.append("/");
        sb.append(this.value);
        sb.append("/");
        String str = this.flags;
        if (str == null) {
            str = "";
        }
        sb.append(str);
        return sb.toString();
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        nodeVisitor.visit(this);
    }

    public RegExpLiteral(int i2) {
        super(i2);
        this.type = 48;
    }

    public RegExpLiteral(int i2, int i3) {
        super(i2, i3);
        this.type = 48;
    }
}
