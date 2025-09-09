package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class ConditionalExpression extends AstNode {
    private int colonPosition;
    private AstNode falseExpression;
    private int questionMarkPosition;
    private AstNode testExpression;
    private AstNode trueExpression;

    public ConditionalExpression() {
        this.questionMarkPosition = -1;
        this.colonPosition = -1;
        this.type = 103;
    }

    public int getColonPosition() {
        return this.colonPosition;
    }

    public AstNode getFalseExpression() {
        return this.falseExpression;
    }

    public int getQuestionMarkPosition() {
        return this.questionMarkPosition;
    }

    public AstNode getTestExpression() {
        return this.testExpression;
    }

    public AstNode getTrueExpression() {
        return this.trueExpression;
    }

    @Override // org.mozilla.javascript.ast.AstNode, org.mozilla.javascript.Node
    public boolean hasSideEffects() throws RuntimeException {
        if (this.testExpression == null || this.trueExpression == null || this.falseExpression == null) {
            AstNode.codeBug();
        }
        return this.trueExpression.hasSideEffects() && this.falseExpression.hasSideEffects();
    }

    public void setColonPosition(int i2) {
        this.colonPosition = i2;
    }

    public void setFalseExpression(AstNode astNode) {
        assertNotNull(astNode);
        this.falseExpression = astNode;
        astNode.setParent(this);
    }

    public void setQuestionMarkPosition(int i2) {
        this.questionMarkPosition = i2;
    }

    public void setTestExpression(AstNode astNode) {
        assertNotNull(astNode);
        this.testExpression = astNode;
        astNode.setParent(this);
    }

    public void setTrueExpression(AstNode astNode) {
        assertNotNull(astNode);
        this.trueExpression = astNode;
        astNode.setParent(this);
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        return makeIndent(i2) + this.testExpression.toSource(i2) + " ? " + this.trueExpression.toSource(0) + " : " + this.falseExpression.toSource(0);
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            this.testExpression.visit(nodeVisitor);
            this.trueExpression.visit(nodeVisitor);
            this.falseExpression.visit(nodeVisitor);
        }
    }

    public ConditionalExpression(int i2) {
        super(i2);
        this.questionMarkPosition = -1;
        this.colonPosition = -1;
        this.type = 103;
    }

    public ConditionalExpression(int i2, int i3) {
        super(i2, i3);
        this.questionMarkPosition = -1;
        this.colonPosition = -1;
        this.type = 103;
    }
}
