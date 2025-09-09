package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class NumberLiteral extends AstNode {
    private double number;
    private String value;

    public NumberLiteral() {
        this.type = 40;
    }

    public double getNumber() {
        return this.number;
    }

    public String getValue() {
        return this.value;
    }

    public void setNumber(double d2) {
        this.number = d2;
    }

    public void setValue(String str) {
        assertNotNull(str);
        this.value = str;
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(i2));
        String str = this.value;
        if (str == null) {
            str = "<null>";
        }
        sb.append(str);
        return sb.toString();
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        nodeVisitor.visit(this);
    }

    public NumberLiteral(int i2) {
        super(i2);
        this.type = 40;
    }

    public NumberLiteral(int i2, int i3) {
        super(i2, i3);
        this.type = 40;
    }

    public NumberLiteral(int i2, String str) {
        super(i2);
        this.type = 40;
        setValue(str);
        setLength(str.length());
    }

    public NumberLiteral(int i2, String str, double d2) {
        this(i2, str);
        setDouble(d2);
    }

    public NumberLiteral(double d2) {
        this.type = 40;
        setDouble(d2);
        setValue(Double.toString(d2));
    }
}
