package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class Yield extends AstNode {
    private AstNode value;

    public Yield() {
        this.type = 73;
    }

    public AstNode getValue() {
        return this.value;
    }

    public void setValue(AstNode astNode) {
        this.value = astNode;
        if (astNode != null) {
            astNode.setParent(this);
        }
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        if (this.value == null) {
            return "yield";
        }
        return "yield " + this.value.toSource(0);
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        AstNode astNode;
        if (!nodeVisitor.visit(this) || (astNode = this.value) == null) {
            return;
        }
        astNode.visit(nodeVisitor);
    }

    public Yield(int i2) {
        super(i2);
        this.type = 73;
    }

    public Yield(int i2, int i3) {
        super(i2, i3);
        this.type = 73;
    }

    public Yield(int i2, int i3, AstNode astNode) {
        super(i2, i3);
        this.type = 73;
        setValue(astNode);
    }
}
