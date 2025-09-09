package org.mozilla.javascript.ast;

import org.mozilla.javascript.ScriptRuntime;

/* loaded from: classes5.dex */
public class StringLiteral extends AstNode {
    private char quoteChar;
    private String value;

    public StringLiteral() {
        this.type = 41;
    }

    public char getQuoteCharacter() {
        return this.quoteChar;
    }

    public String getValue() {
        return this.value;
    }

    public void setQuoteCharacter(char c2) {
        this.quoteChar = c2;
    }

    public void setValue(String str) {
        assertNotNull(str);
        this.value = str;
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        return makeIndent(i2) + this.quoteChar + ScriptRuntime.escapeString(this.value, this.quoteChar) + this.quoteChar;
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        nodeVisitor.visit(this);
    }

    public String getValue(boolean z2) {
        if (!z2) {
            return this.value;
        }
        return this.quoteChar + this.value + this.quoteChar;
    }

    public StringLiteral(int i2) {
        super(i2);
        this.type = 41;
    }

    public StringLiteral(int i2, int i3) {
        super(i2, i3);
        this.type = 41;
    }
}
