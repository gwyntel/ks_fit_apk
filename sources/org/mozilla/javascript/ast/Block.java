package org.mozilla.javascript.ast;

import java.util.Iterator;
import org.mozilla.javascript.Node;

/* loaded from: classes5.dex */
public class Block extends AstNode {
    public Block() {
        this.type = 130;
    }

    public void addStatement(AstNode astNode) {
        addChild(astNode);
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(i2));
        sb.append("{\n");
        Iterator<Node> it = iterator();
        while (it.hasNext()) {
            sb.append(((AstNode) it.next()).toSource(i2 + 1));
        }
        sb.append(makeIndent(i2));
        sb.append("}\n");
        return sb.toString();
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            Iterator<Node> it = iterator();
            while (it.hasNext()) {
                ((AstNode) it.next()).visit(nodeVisitor);
            }
        }
    }

    public Block(int i2) {
        super(i2);
        this.type = 130;
    }

    public Block(int i2, int i3) {
        super(i2, i3);
        this.type = 130;
    }
}
