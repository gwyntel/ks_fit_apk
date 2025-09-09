package org.mozilla.javascript.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.mozilla.javascript.Node;

/* loaded from: classes5.dex */
public class Scope extends Jump {
    private List<Scope> childScopes;
    protected Scope parentScope;
    protected Map<String, Symbol> symbolTable;

    /* renamed from: top, reason: collision with root package name */
    protected ScriptNode f26762top;

    public Scope() {
        this.type = 130;
    }

    private Map<String, Symbol> ensureSymbolTable() {
        if (this.symbolTable == null) {
            this.symbolTable = new LinkedHashMap(5);
        }
        return this.symbolTable;
    }

    public static void joinScopes(Scope scope, Scope scope2) throws RuntimeException {
        Map<String, Symbol> mapEnsureSymbolTable = scope.ensureSymbolTable();
        Map<String, Symbol> mapEnsureSymbolTable2 = scope2.ensureSymbolTable();
        if (!Collections.disjoint(mapEnsureSymbolTable.keySet(), mapEnsureSymbolTable2.keySet())) {
            AstNode.codeBug();
        }
        for (Map.Entry<String, Symbol> entry : mapEnsureSymbolTable.entrySet()) {
            Symbol value = entry.getValue();
            value.setContainingTable(scope2);
            mapEnsureSymbolTable2.put(entry.getKey(), value);
        }
    }

    public static Scope splitScope(Scope scope) {
        Scope scope2 = new Scope(scope.getType());
        scope2.symbolTable = scope.symbolTable;
        scope.symbolTable = null;
        scope2.parent = scope.parent;
        scope2.setParentScope(scope.getParentScope());
        scope2.setParentScope(scope2);
        scope.parent = scope2;
        scope2.f26762top = scope.f26762top;
        return scope2;
    }

    public void addChildScope(Scope scope) {
        if (this.childScopes == null) {
            this.childScopes = new ArrayList();
        }
        this.childScopes.add(scope);
        scope.setParentScope(this);
    }

    public void clearParentScope() {
        this.parentScope = null;
    }

    public List<Scope> getChildScopes() {
        return this.childScopes;
    }

    public Scope getDefiningScope(String str) {
        for (Scope scope = this; scope != null; scope = scope.parentScope) {
            Map<String, Symbol> symbolTable = scope.getSymbolTable();
            if (symbolTable != null && symbolTable.containsKey(str)) {
                return scope;
            }
        }
        return null;
    }

    public Scope getParentScope() {
        return this.parentScope;
    }

    public List<AstNode> getStatements() {
        ArrayList arrayList = new ArrayList();
        for (Node firstChild = getFirstChild(); firstChild != null; firstChild = firstChild.getNext()) {
            arrayList.add((AstNode) firstChild);
        }
        return arrayList;
    }

    public Symbol getSymbol(String str) {
        Map<String, Symbol> map = this.symbolTable;
        if (map == null) {
            return null;
        }
        return map.get(str);
    }

    public Map<String, Symbol> getSymbolTable() {
        return this.symbolTable;
    }

    public ScriptNode getTop() {
        return this.f26762top;
    }

    public void putSymbol(Symbol symbol) {
        if (symbol.getName() == null) {
            throw new IllegalArgumentException("null symbol name");
        }
        ensureSymbolTable();
        this.symbolTable.put(symbol.getName(), symbol);
        symbol.setContainingTable(this);
        this.f26762top.addSymbol(symbol);
    }

    public void replaceWith(Scope scope) throws RuntimeException {
        List<Scope> list = this.childScopes;
        if (list != null) {
            Iterator<Scope> it = list.iterator();
            while (it.hasNext()) {
                scope.addChildScope(it.next());
            }
            this.childScopes.clear();
            this.childScopes = null;
        }
        Map<String, Symbol> map = this.symbolTable;
        if (map == null || map.isEmpty()) {
            return;
        }
        joinScopes(this, scope);
    }

    public void setParentScope(Scope scope) {
        this.parentScope = scope;
        this.f26762top = scope == null ? (ScriptNode) this : scope.f26762top;
    }

    public void setSymbolTable(Map<String, Symbol> map) {
        this.symbolTable = map;
    }

    public void setTop(ScriptNode scriptNode) {
        this.f26762top = scriptNode;
    }

    @Override // org.mozilla.javascript.ast.Jump, org.mozilla.javascript.ast.AstNode
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

    @Override // org.mozilla.javascript.ast.Jump, org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            Iterator<Node> it = iterator();
            while (it.hasNext()) {
                ((AstNode) it.next()).visit(nodeVisitor);
            }
        }
    }

    public Scope(int i2) {
        this.type = 130;
        this.position = i2;
    }

    public Scope(int i2, int i3) {
        this(i2);
        this.length = i3;
    }
}
