package org.mozilla.javascript.ast;

import anetwork.channel.util.RequestConstant;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;

/* loaded from: classes5.dex */
public class KeywordLiteral extends AstNode {
    public KeywordLiteral() {
    }

    public boolean isBooleanLiteral() {
        int i2 = this.type;
        return i2 == 45 || i2 == 44;
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(i2));
        int type = getType();
        if (type != 161) {
            switch (type) {
                case 42:
                    sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
                    break;
                case 43:
                    sb.append("this");
                    break;
                case 44:
                    sb.append(RequestConstant.FALSE);
                    break;
                case 45:
                    sb.append("true");
                    break;
                default:
                    throw new IllegalStateException("Invalid keyword literal type: " + getType());
            }
        } else {
            sb.append("debugger;\n");
        }
        return sb.toString();
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        nodeVisitor.visit(this);
    }

    public KeywordLiteral(int i2) {
        super(i2);
    }

    @Override // org.mozilla.javascript.Node
    public KeywordLiteral setType(int i2) {
        if (i2 == 43 || i2 == 42 || i2 == 45 || i2 == 44 || i2 == 161) {
            this.type = i2;
            return this;
        }
        throw new IllegalArgumentException("Invalid node type: " + i2);
    }

    public KeywordLiteral(int i2, int i3) {
        super(i2, i3);
    }

    public KeywordLiteral(int i2, int i3, int i4) {
        super(i2, i3);
        setType(i4);
    }
}
