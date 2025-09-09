package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class Label extends Jump {
    private String name;

    public Label() {
        this.type = 131;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        String strTrim = str == null ? null : str.trim();
        if (strTrim == null || "".equals(strTrim)) {
            throw new IllegalArgumentException("invalid label name");
        }
        this.name = strTrim;
    }

    @Override // org.mozilla.javascript.ast.Jump, org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        return makeIndent(i2) + this.name + ":\n";
    }

    @Override // org.mozilla.javascript.ast.Jump, org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        nodeVisitor.visit(this);
    }

    public Label(int i2) {
        this(i2, -1);
    }

    public Label(int i2, int i3) {
        this.type = 131;
        this.position = i2;
        this.length = i3;
    }

    public Label(int i2, int i3, String str) {
        this(i2, i3);
        setName(str);
    }
}
