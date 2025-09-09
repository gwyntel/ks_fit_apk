package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class Name extends AstNode {
    private String identifier;
    private Scope scope;

    public Name() {
        this.type = 39;
    }

    public Scope getDefiningScope() {
        Scope enclosingScope = getEnclosingScope();
        String identifier = getIdentifier();
        if (enclosingScope == null) {
            return null;
        }
        return enclosingScope.getDefiningScope(identifier);
    }

    public String getIdentifier() {
        return this.identifier;
    }

    @Override // org.mozilla.javascript.Node
    public Scope getScope() {
        return this.scope;
    }

    public boolean isLocalName() {
        Scope definingScope = getDefiningScope();
        return (definingScope == null || definingScope.getParentScope() == null) ? false : true;
    }

    public int length() {
        String str = this.identifier;
        if (str == null) {
            return 0;
        }
        return str.length();
    }

    public void setIdentifier(String str) {
        assertNotNull(str);
        this.identifier = str;
        setLength(str.length());
    }

    @Override // org.mozilla.javascript.Node
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(i2));
        String str = this.identifier;
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

    public Name(int i2) {
        super(i2);
        this.type = 39;
    }

    public Name(int i2, int i3) {
        super(i2, i3);
        this.type = 39;
    }

    public Name(int i2, int i3, String str) {
        super(i2, i3);
        this.type = 39;
        setIdentifier(str);
    }

    public Name(int i2, String str) {
        super(i2);
        this.type = 39;
        setIdentifier(str);
        setLength(str.length());
    }
}
