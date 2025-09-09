package org.mozilla.javascript.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.mozilla.javascript.Node;

/* loaded from: classes5.dex */
public class ScriptNode extends Scope {
    private List<FunctionNode> EMPTY_LIST;
    private Object compilerData;
    private String encodedSource;
    private int encodedSourceEnd;
    private int encodedSourceStart;
    private int endLineno;
    private List<FunctionNode> functions;
    private boolean inStrictMode;
    private boolean[] isConsts;
    private int paramCount;
    private List<RegExpLiteral> regexps;
    private String sourceName;
    private List<Symbol> symbols;
    private int tempNumber;
    private String[] variableNames;

    public ScriptNode() {
        this.encodedSourceStart = -1;
        this.encodedSourceEnd = -1;
        this.endLineno = -1;
        this.EMPTY_LIST = Collections.emptyList();
        this.symbols = new ArrayList(4);
        this.paramCount = 0;
        this.tempNumber = 0;
        this.f26762top = this;
        this.type = 137;
    }

    public int addFunction(FunctionNode functionNode) {
        if (functionNode == null) {
            AstNode.codeBug();
        }
        if (this.functions == null) {
            this.functions = new ArrayList();
        }
        this.functions.add(functionNode);
        return this.functions.size() - 1;
    }

    public void addRegExp(RegExpLiteral regExpLiteral) throws RuntimeException {
        if (regExpLiteral == null) {
            AstNode.codeBug();
        }
        if (this.regexps == null) {
            this.regexps = new ArrayList();
        }
        this.regexps.add(regExpLiteral);
        regExpLiteral.putIntProp(4, this.regexps.size() - 1);
    }

    void addSymbol(Symbol symbol) {
        if (this.variableNames != null) {
            AstNode.codeBug();
        }
        if (symbol.getDeclType() == 88) {
            this.paramCount++;
        }
        this.symbols.add(symbol);
    }

    public void flattenSymbolTable(boolean z2) {
        if (!z2) {
            ArrayList arrayList = new ArrayList();
            if (this.symbolTable != null) {
                for (int i2 = 0; i2 < this.symbols.size(); i2++) {
                    Symbol symbol = this.symbols.get(i2);
                    if (symbol.getContainingTable() == this) {
                        arrayList.add(symbol);
                    }
                }
            }
            this.symbols = arrayList;
        }
        this.variableNames = new String[this.symbols.size()];
        this.isConsts = new boolean[this.symbols.size()];
        for (int i3 = 0; i3 < this.symbols.size(); i3++) {
            Symbol symbol2 = this.symbols.get(i3);
            this.variableNames[i3] = symbol2.getName();
            this.isConsts[i3] = symbol2.getDeclType() == 155;
            symbol2.setIndex(i3);
        }
    }

    public int getBaseLineno() {
        return this.lineno;
    }

    public Object getCompilerData() {
        return this.compilerData;
    }

    public String getEncodedSource() {
        return this.encodedSource;
    }

    public int getEncodedSourceEnd() {
        return this.encodedSourceEnd;
    }

    public int getEncodedSourceStart() {
        return this.encodedSourceStart;
    }

    public int getEndLineno() {
        return this.endLineno;
    }

    public int getFunctionCount() {
        List<FunctionNode> list = this.functions;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public FunctionNode getFunctionNode(int i2) {
        return this.functions.get(i2);
    }

    public List<FunctionNode> getFunctions() {
        List<FunctionNode> list = this.functions;
        return list == null ? this.EMPTY_LIST : list;
    }

    public int getIndexForNameNode(Node node) throws RuntimeException {
        if (this.variableNames == null) {
            AstNode.codeBug();
        }
        Scope scope = node.getScope();
        Symbol symbol = scope == null ? null : scope.getSymbol(((Name) node).getIdentifier());
        if (symbol == null) {
            return -1;
        }
        return symbol.getIndex();
    }

    public String getNextTempName() {
        StringBuilder sb = new StringBuilder();
        sb.append("$");
        int i2 = this.tempNumber;
        this.tempNumber = i2 + 1;
        sb.append(i2);
        return sb.toString();
    }

    public boolean[] getParamAndVarConst() throws RuntimeException {
        if (this.variableNames == null) {
            AstNode.codeBug();
        }
        return this.isConsts;
    }

    public int getParamAndVarCount() throws RuntimeException {
        if (this.variableNames == null) {
            AstNode.codeBug();
        }
        return this.symbols.size();
    }

    public String[] getParamAndVarNames() throws RuntimeException {
        if (this.variableNames == null) {
            AstNode.codeBug();
        }
        return this.variableNames;
    }

    public int getParamCount() {
        return this.paramCount;
    }

    public String getParamOrVarName(int i2) throws RuntimeException {
        if (this.variableNames == null) {
            AstNode.codeBug();
        }
        return this.variableNames[i2];
    }

    public int getRegexpCount() {
        List<RegExpLiteral> list = this.regexps;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public String getRegexpFlags(int i2) {
        return this.regexps.get(i2).getFlags();
    }

    public String getRegexpString(int i2) {
        return this.regexps.get(i2).getValue();
    }

    public String getSourceName() {
        return this.sourceName;
    }

    public List<Symbol> getSymbols() {
        return this.symbols;
    }

    public boolean isInStrictMode() {
        return this.inStrictMode;
    }

    public void setBaseLineno(int i2) throws RuntimeException {
        if (i2 < 0 || this.lineno >= 0) {
            AstNode.codeBug();
        }
        this.lineno = i2;
    }

    public void setCompilerData(Object obj) {
        assertNotNull(obj);
        if (this.compilerData != null) {
            throw new IllegalStateException();
        }
        this.compilerData = obj;
    }

    public void setEncodedSource(String str) {
        this.encodedSource = str;
    }

    public void setEncodedSourceBounds(int i2, int i3) {
        this.encodedSourceStart = i2;
        this.encodedSourceEnd = i3;
    }

    public void setEncodedSourceEnd(int i2) {
        this.encodedSourceEnd = i2;
    }

    public void setEncodedSourceStart(int i2) {
        this.encodedSourceStart = i2;
    }

    public void setEndLineno(int i2) throws RuntimeException {
        if (i2 < 0 || this.endLineno >= 0) {
            AstNode.codeBug();
        }
        this.endLineno = i2;
    }

    public void setInStrictMode(boolean z2) {
        this.inStrictMode = z2;
    }

    public void setSourceName(String str) {
        this.sourceName = str;
    }

    public void setSymbols(List<Symbol> list) {
        this.symbols = list;
    }

    @Override // org.mozilla.javascript.ast.Scope, org.mozilla.javascript.ast.Jump, org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            Iterator<Node> it = iterator();
            while (it.hasNext()) {
                ((AstNode) it.next()).visit(nodeVisitor);
            }
        }
    }

    public ScriptNode(int i2) {
        super(i2);
        this.encodedSourceStart = -1;
        this.encodedSourceEnd = -1;
        this.endLineno = -1;
        this.EMPTY_LIST = Collections.emptyList();
        this.symbols = new ArrayList(4);
        this.paramCount = 0;
        this.tempNumber = 0;
        this.f26762top = this;
        this.type = 137;
    }
}
