package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class XmlString extends XmlFragment {
    private String xml;

    public XmlString() {
    }

    public String getXml() {
        return this.xml;
    }

    public void setXml(String str) {
        assertNotNull(str);
        this.xml = str;
        setLength(str.length());
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        return makeIndent(i2) + this.xml;
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        nodeVisitor.visit(this);
    }

    public XmlString(int i2) {
        super(i2);
    }

    public XmlString(int i2, String str) {
        super(i2);
        setXml(str);
    }
}
