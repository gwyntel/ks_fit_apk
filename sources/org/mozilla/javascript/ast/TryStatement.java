package org.mozilla.javascript.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class TryStatement extends AstNode {
    private static final List<CatchClause> NO_CATCHES = Collections.unmodifiableList(new ArrayList());
    private List<CatchClause> catchClauses;
    private AstNode finallyBlock;
    private int finallyPosition;
    private AstNode tryBlock;

    public TryStatement() {
        this.finallyPosition = -1;
        this.type = 82;
    }

    public void addCatchClause(CatchClause catchClause) {
        assertNotNull(catchClause);
        if (this.catchClauses == null) {
            this.catchClauses = new ArrayList();
        }
        this.catchClauses.add(catchClause);
        catchClause.setParent(this);
    }

    public List<CatchClause> getCatchClauses() {
        List<CatchClause> list = this.catchClauses;
        return list != null ? list : NO_CATCHES;
    }

    public AstNode getFinallyBlock() {
        return this.finallyBlock;
    }

    public int getFinallyPosition() {
        return this.finallyPosition;
    }

    public AstNode getTryBlock() {
        return this.tryBlock;
    }

    public void setCatchClauses(List<CatchClause> list) {
        if (list == null) {
            this.catchClauses = null;
            return;
        }
        List<CatchClause> list2 = this.catchClauses;
        if (list2 != null) {
            list2.clear();
        }
        Iterator<CatchClause> it = list.iterator();
        while (it.hasNext()) {
            addCatchClause(it.next());
        }
    }

    public void setFinallyBlock(AstNode astNode) {
        this.finallyBlock = astNode;
        if (astNode != null) {
            astNode.setParent(this);
        }
    }

    public void setFinallyPosition(int i2) {
        this.finallyPosition = i2;
    }

    public void setTryBlock(AstNode astNode) {
        assertNotNull(astNode);
        this.tryBlock = astNode;
        astNode.setParent(this);
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        StringBuilder sb = new StringBuilder(250);
        sb.append(makeIndent(i2));
        sb.append("try ");
        sb.append(this.tryBlock.toSource(i2).trim());
        Iterator<CatchClause> it = getCatchClauses().iterator();
        while (it.hasNext()) {
            sb.append(it.next().toSource(i2));
        }
        if (this.finallyBlock != null) {
            sb.append(" finally ");
            sb.append(this.finallyBlock.toSource(i2));
        }
        return sb.toString();
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            this.tryBlock.visit(nodeVisitor);
            Iterator<CatchClause> it = getCatchClauses().iterator();
            while (it.hasNext()) {
                it.next().visit(nodeVisitor);
            }
            AstNode astNode = this.finallyBlock;
            if (astNode != null) {
                astNode.visit(nodeVisitor);
            }
        }
    }

    public TryStatement(int i2) {
        super(i2);
        this.finallyPosition = -1;
        this.type = 82;
    }

    public TryStatement(int i2, int i3) {
        super(i2, i3);
        this.finallyPosition = -1;
        this.type = 82;
    }
}
