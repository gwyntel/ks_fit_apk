package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class XmlMemberGet extends InfixExpression {
    public XmlMemberGet() {
        this.type = 144;
    }

    public XmlRef getMemberRef() {
        return (XmlRef) getRight();
    }

    public AstNode getTarget() {
        return getLeft();
    }

    public void setProperty(XmlRef xmlRef) {
        setRight(xmlRef);
    }

    public void setTarget(AstNode astNode) {
        setLeft(astNode);
    }

    @Override // org.mozilla.javascript.ast.InfixExpression, org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        return makeIndent(i2) + getLeft().toSource(0) + AstNode.operatorToString(getType()) + getRight().toSource(0);
    }

    public XmlMemberGet(int i2) {
        super(i2);
        this.type = 144;
    }

    public XmlMemberGet(int i2, int i3) {
        super(i2, i3);
        this.type = 144;
    }

    public XmlMemberGet(int i2, int i3, AstNode astNode, XmlRef xmlRef) {
        super(i2, i3, astNode, xmlRef);
        this.type = 144;
    }

    public XmlMemberGet(AstNode astNode, XmlRef xmlRef) {
        super(astNode, xmlRef);
        this.type = 144;
    }

    public XmlMemberGet(AstNode astNode, XmlRef xmlRef, int i2) {
        super(144, astNode, xmlRef, i2);
        this.type = 144;
    }
}
