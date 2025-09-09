package org.mozilla.javascript.ast;

import com.alipay.sdk.m.u.i;

/* loaded from: classes5.dex */
public class XmlExpression extends XmlFragment {
    private AstNode expression;
    private boolean isXmlAttribute;

    public XmlExpression() {
    }

    public AstNode getExpression() {
        return this.expression;
    }

    public boolean isXmlAttribute() {
        return this.isXmlAttribute;
    }

    public void setExpression(AstNode astNode) {
        assertNotNull(astNode);
        this.expression = astNode;
        astNode.setParent(this);
    }

    public void setIsXmlAttribute(boolean z2) {
        this.isXmlAttribute = z2;
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        return makeIndent(i2) + "{" + this.expression.toSource(i2) + i.f9804d;
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            this.expression.visit(nodeVisitor);
        }
    }

    public XmlExpression(int i2) {
        super(i2);
    }

    public XmlExpression(int i2, int i3) {
        super(i2, i3);
    }

    public XmlExpression(int i2, AstNode astNode) {
        super(i2);
        setExpression(astNode);
    }
}
