package org.mozilla.javascript.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class SwitchCase extends AstNode {
    private AstNode expression;
    private List<AstNode> statements;

    public SwitchCase() {
        this.type = 116;
    }

    public void addStatement(AstNode astNode) {
        assertNotNull(astNode);
        if (this.statements == null) {
            this.statements = new ArrayList();
        }
        setLength((astNode.getPosition() + astNode.getLength()) - getPosition());
        this.statements.add(astNode);
        astNode.setParent(this);
    }

    public AstNode getExpression() {
        return this.expression;
    }

    public List<AstNode> getStatements() {
        return this.statements;
    }

    public boolean isDefault() {
        return this.expression == null;
    }

    public void setExpression(AstNode astNode) {
        this.expression = astNode;
        if (astNode != null) {
            astNode.setParent(this);
        }
    }

    public void setStatements(List<AstNode> list) {
        List<AstNode> list2 = this.statements;
        if (list2 != null) {
            list2.clear();
        }
        Iterator<AstNode> it = list.iterator();
        while (it.hasNext()) {
            addStatement(it.next());
        }
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(i2));
        if (this.expression == null) {
            sb.append("default:\n");
        } else {
            sb.append("case ");
            sb.append(this.expression.toSource(0));
            sb.append(":\n");
        }
        List<AstNode> list = this.statements;
        if (list != null) {
            Iterator<AstNode> it = list.iterator();
            while (it.hasNext()) {
                sb.append(it.next().toSource(i2 + 1));
            }
        }
        return sb.toString();
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            AstNode astNode = this.expression;
            if (astNode != null) {
                astNode.visit(nodeVisitor);
            }
            List<AstNode> list = this.statements;
            if (list != null) {
                Iterator<AstNode> it = list.iterator();
                while (it.hasNext()) {
                    it.next().visit(nodeVisitor);
                }
            }
        }
    }

    public SwitchCase(int i2) {
        super(i2);
        this.type = 116;
    }

    public SwitchCase(int i2, int i3) {
        super(i2, i3);
        this.type = 116;
    }
}
