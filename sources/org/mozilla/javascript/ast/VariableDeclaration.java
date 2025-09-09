package org.mozilla.javascript.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.mozilla.javascript.Node;
import org.mozilla.javascript.Token;

/* loaded from: classes5.dex */
public class VariableDeclaration extends AstNode {
    private boolean isStatement;
    private List<VariableInitializer> variables;

    public VariableDeclaration() {
        this.variables = new ArrayList();
        this.type = 123;
    }

    private String declTypeName() {
        return Token.typeToName(this.type).toLowerCase();
    }

    public void addVariable(VariableInitializer variableInitializer) {
        assertNotNull(variableInitializer);
        this.variables.add(variableInitializer);
        variableInitializer.setParent(this);
    }

    public List<VariableInitializer> getVariables() {
        return this.variables;
    }

    public boolean isConst() {
        return this.type == 155;
    }

    public boolean isLet() {
        return this.type == 154;
    }

    public boolean isStatement() {
        return this.isStatement;
    }

    public boolean isVar() {
        return this.type == 123;
    }

    public void setIsStatement(boolean z2) {
        this.isStatement = z2;
    }

    @Override // org.mozilla.javascript.Node
    public Node setType(int i2) {
        if (i2 == 123 || i2 == 155 || i2 == 154) {
            return super.setType(i2);
        }
        throw new IllegalArgumentException("invalid decl type: " + i2);
    }

    public void setVariables(List<VariableInitializer> list) {
        assertNotNull(list);
        this.variables.clear();
        Iterator<VariableInitializer> it = list.iterator();
        while (it.hasNext()) {
            addVariable(it.next());
        }
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(i2));
        sb.append(declTypeName());
        sb.append(" ");
        printList(this.variables, sb);
        if (isStatement()) {
            sb.append(";\n");
        }
        return sb.toString();
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            Iterator<VariableInitializer> it = this.variables.iterator();
            while (it.hasNext()) {
                it.next().visit(nodeVisitor);
            }
        }
    }

    public VariableDeclaration(int i2) {
        super(i2);
        this.variables = new ArrayList();
        this.type = 123;
    }

    public VariableDeclaration(int i2, int i3) {
        super(i2, i3);
        this.variables = new ArrayList();
        this.type = 123;
    }
}
