package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class IfStatement extends AstNode {
    private AstNode condition;
    private AstNode elsePart;
    private int elsePosition;
    private int lp;
    private int rp;
    private AstNode thenPart;

    public IfStatement() {
        this.elsePosition = -1;
        this.lp = -1;
        this.rp = -1;
        this.type = 113;
    }

    public AstNode getCondition() {
        return this.condition;
    }

    public AstNode getElsePart() {
        return this.elsePart;
    }

    public int getElsePosition() {
        return this.elsePosition;
    }

    public int getLp() {
        return this.lp;
    }

    public int getRp() {
        return this.rp;
    }

    public AstNode getThenPart() {
        return this.thenPart;
    }

    public void setCondition(AstNode astNode) {
        assertNotNull(astNode);
        this.condition = astNode;
        astNode.setParent(this);
    }

    public void setElsePart(AstNode astNode) {
        this.elsePart = astNode;
        if (astNode != null) {
            astNode.setParent(this);
        }
    }

    public void setElsePosition(int i2) {
        this.elsePosition = i2;
    }

    public void setLp(int i2) {
        this.lp = i2;
    }

    public void setParens(int i2, int i3) {
        this.lp = i2;
        this.rp = i3;
    }

    public void setRp(int i2) {
        this.rp = i2;
    }

    public void setThenPart(AstNode astNode) {
        assertNotNull(astNode);
        this.thenPart = astNode;
        astNode.setParent(this);
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        String strMakeIndent = makeIndent(i2);
        StringBuilder sb = new StringBuilder(32);
        sb.append(strMakeIndent);
        sb.append("if (");
        sb.append(this.condition.toSource(0));
        sb.append(") ");
        if (this.thenPart.getType() != 130) {
            sb.append("\n");
            sb.append(makeIndent(i2 + 1));
        }
        sb.append(this.thenPart.toSource(i2).trim());
        if (this.elsePart != null) {
            if (this.thenPart.getType() != 130) {
                sb.append("\n");
                sb.append(strMakeIndent);
                sb.append("else ");
            } else {
                sb.append(" else ");
            }
            if (this.elsePart.getType() != 130 && this.elsePart.getType() != 113) {
                sb.append("\n");
                sb.append(makeIndent(i2 + 1));
            }
            sb.append(this.elsePart.toSource(i2).trim());
        }
        sb.append("\n");
        return sb.toString();
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            this.condition.visit(nodeVisitor);
            this.thenPart.visit(nodeVisitor);
            AstNode astNode = this.elsePart;
            if (astNode != null) {
                astNode.visit(nodeVisitor);
            }
        }
    }

    public IfStatement(int i2) {
        super(i2);
        this.elsePosition = -1;
        this.lp = -1;
        this.rp = -1;
        this.type = 113;
    }

    public IfStatement(int i2, int i3) {
        super(i2, i3);
        this.elsePosition = -1;
        this.lp = -1;
        this.rp = -1;
        this.type = 113;
    }
}
