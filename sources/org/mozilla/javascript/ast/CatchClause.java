package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class CatchClause extends AstNode {
    private Block body;
    private AstNode catchCondition;
    private int ifPosition;
    private int lp;
    private int rp;
    private Name varName;

    public CatchClause() {
        this.ifPosition = -1;
        this.lp = -1;
        this.rp = -1;
        this.type = 125;
    }

    public Block getBody() {
        return this.body;
    }

    public AstNode getCatchCondition() {
        return this.catchCondition;
    }

    public int getIfPosition() {
        return this.ifPosition;
    }

    public int getLp() {
        return this.lp;
    }

    public int getRp() {
        return this.rp;
    }

    public Name getVarName() {
        return this.varName;
    }

    public void setBody(Block block) {
        assertNotNull(block);
        this.body = block;
        block.setParent(this);
    }

    public void setCatchCondition(AstNode astNode) {
        this.catchCondition = astNode;
        if (astNode != null) {
            astNode.setParent(this);
        }
    }

    public void setIfPosition(int i2) {
        this.ifPosition = i2;
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

    public void setVarName(Name name) {
        assertNotNull(name);
        this.varName = name;
        name.setParent(this);
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(i2));
        sb.append("catch (");
        sb.append(this.varName.toSource(0));
        if (this.catchCondition != null) {
            sb.append(" if ");
            sb.append(this.catchCondition.toSource(0));
        }
        sb.append(") ");
        sb.append(this.body.toSource(0));
        return sb.toString();
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            this.varName.visit(nodeVisitor);
            AstNode astNode = this.catchCondition;
            if (astNode != null) {
                astNode.visit(nodeVisitor);
            }
            this.body.visit(nodeVisitor);
        }
    }

    public CatchClause(int i2) {
        super(i2);
        this.ifPosition = -1;
        this.lp = -1;
        this.rp = -1;
        this.type = 125;
    }

    public CatchClause(int i2, int i3) {
        super(i2, i3);
        this.ifPosition = -1;
        this.lp = -1;
        this.rp = -1;
        this.type = 125;
    }
}
