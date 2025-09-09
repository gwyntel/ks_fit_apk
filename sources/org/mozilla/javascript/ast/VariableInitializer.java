package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class VariableInitializer extends AstNode {
    private AstNode initializer;
    private AstNode target;

    public VariableInitializer() {
        this.type = 123;
    }

    public AstNode getInitializer() {
        return this.initializer;
    }

    public AstNode getTarget() {
        return this.target;
    }

    public boolean isDestructuring() {
        return !(this.target instanceof Name);
    }

    public void setInitializer(AstNode astNode) {
        this.initializer = astNode;
        if (astNode != null) {
            astNode.setParent(this);
        }
    }

    public void setNodeType(int i2) {
        if (i2 != 123 && i2 != 155 && i2 != 154) {
            throw new IllegalArgumentException("invalid node type");
        }
        setType(i2);
    }

    public void setTarget(AstNode astNode) {
        if (astNode == null) {
            throw new IllegalArgumentException("invalid target arg");
        }
        this.target = astNode;
        astNode.setParent(this);
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(i2));
        sb.append(this.target.toSource(0));
        if (this.initializer != null) {
            sb.append(" = ");
            sb.append(this.initializer.toSource(0));
        }
        return sb.toString();
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            this.target.visit(nodeVisitor);
            AstNode astNode = this.initializer;
            if (astNode != null) {
                astNode.visit(nodeVisitor);
            }
        }
    }

    public VariableInitializer(int i2) {
        super(i2);
        this.type = 123;
    }

    public VariableInitializer(int i2, int i3) {
        super(i2, i3);
        this.type = 123;
    }
}
