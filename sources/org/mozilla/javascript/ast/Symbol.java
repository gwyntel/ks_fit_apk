package org.mozilla.javascript.ast;

import org.mozilla.javascript.Node;
import org.mozilla.javascript.Token;

/* loaded from: classes5.dex */
public class Symbol {
    private Scope containingTable;
    private int declType;
    private int index = -1;
    private String name;
    private Node node;

    public Symbol() {
    }

    public Scope getContainingTable() {
        return this.containingTable;
    }

    public int getDeclType() {
        return this.declType;
    }

    public String getDeclTypeName() {
        return Token.typeToName(this.declType);
    }

    public int getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

    public Node getNode() {
        return this.node;
    }

    public void setContainingTable(Scope scope) {
        this.containingTable = scope;
    }

    public void setDeclType(int i2) {
        if (i2 == 110 || i2 == 88 || i2 == 123 || i2 == 154 || i2 == 155) {
            this.declType = i2;
            return;
        }
        throw new IllegalArgumentException("Invalid declType: " + i2);
    }

    public void setIndex(int i2) {
        this.index = i2;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Symbol (");
        sb.append(getDeclTypeName());
        sb.append(") name=");
        sb.append(this.name);
        if (this.node != null) {
            sb.append(" line=");
            sb.append(this.node.getLineno());
        }
        return sb.toString();
    }

    public Symbol(int i2, String str) {
        setName(str);
        setDeclType(i2);
    }
}
