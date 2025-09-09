package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

/* loaded from: classes5.dex */
public class Comment extends AstNode {
    private Token.CommentType commentType;
    private String value;

    public Comment(int i2, int i3, Token.CommentType commentType, String str) {
        super(i2, i3);
        this.type = 162;
        this.commentType = commentType;
        this.value = str;
    }

    public Token.CommentType getCommentType() {
        return this.commentType;
    }

    public String getValue() {
        return this.value;
    }

    public void setCommentType(Token.CommentType commentType) {
        this.commentType = commentType;
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        StringBuilder sb = new StringBuilder(getLength() + 10);
        sb.append(makeIndent(i2));
        sb.append(this.value);
        return sb.toString();
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        nodeVisitor.visit(this);
    }
}
