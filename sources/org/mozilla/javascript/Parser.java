package org.mozilla.javascript;

import androidx.media3.exoplayer.rtsp.SessionDescription;
import androidx.webkit.ProxyConfig;
import com.tekartik.sqflite.Constant;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.ArrayComprehension;
import org.mozilla.javascript.ast.ArrayLiteral;
import org.mozilla.javascript.ast.Assignment;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.AstRoot;
import org.mozilla.javascript.ast.Block;
import org.mozilla.javascript.ast.BreakStatement;
import org.mozilla.javascript.ast.CatchClause;
import org.mozilla.javascript.ast.Comment;
import org.mozilla.javascript.ast.ConditionalExpression;
import org.mozilla.javascript.ast.ContinueStatement;
import org.mozilla.javascript.ast.DestructuringForm;
import org.mozilla.javascript.ast.DoLoop;
import org.mozilla.javascript.ast.ElementGet;
import org.mozilla.javascript.ast.EmptyExpression;
import org.mozilla.javascript.ast.EmptyStatement;
import org.mozilla.javascript.ast.ErrorNode;
import org.mozilla.javascript.ast.ExpressionStatement;
import org.mozilla.javascript.ast.ForInLoop;
import org.mozilla.javascript.ast.ForLoop;
import org.mozilla.javascript.ast.FunctionCall;
import org.mozilla.javascript.ast.FunctionNode;
import org.mozilla.javascript.ast.GeneratorExpression;
import org.mozilla.javascript.ast.GeneratorExpressionLoop;
import org.mozilla.javascript.ast.IdeErrorReporter;
import org.mozilla.javascript.ast.IfStatement;
import org.mozilla.javascript.ast.InfixExpression;
import org.mozilla.javascript.ast.Jump;
import org.mozilla.javascript.ast.KeywordLiteral;
import org.mozilla.javascript.ast.Label;
import org.mozilla.javascript.ast.LabeledStatement;
import org.mozilla.javascript.ast.LetNode;
import org.mozilla.javascript.ast.Loop;
import org.mozilla.javascript.ast.Name;
import org.mozilla.javascript.ast.NewExpression;
import org.mozilla.javascript.ast.NumberLiteral;
import org.mozilla.javascript.ast.ObjectLiteral;
import org.mozilla.javascript.ast.ObjectProperty;
import org.mozilla.javascript.ast.ParenthesizedExpression;
import org.mozilla.javascript.ast.PropertyGet;
import org.mozilla.javascript.ast.RegExpLiteral;
import org.mozilla.javascript.ast.ReturnStatement;
import org.mozilla.javascript.ast.Scope;
import org.mozilla.javascript.ast.ScriptNode;
import org.mozilla.javascript.ast.StringLiteral;
import org.mozilla.javascript.ast.SwitchCase;
import org.mozilla.javascript.ast.SwitchStatement;
import org.mozilla.javascript.ast.ThrowStatement;
import org.mozilla.javascript.ast.TryStatement;
import org.mozilla.javascript.ast.UnaryExpression;
import org.mozilla.javascript.ast.VariableDeclaration;
import org.mozilla.javascript.ast.VariableInitializer;
import org.mozilla.javascript.ast.WhileLoop;
import org.mozilla.javascript.ast.WithStatement;
import org.mozilla.javascript.ast.XmlDotQuery;
import org.mozilla.javascript.ast.XmlElemRef;
import org.mozilla.javascript.ast.XmlExpression;
import org.mozilla.javascript.ast.XmlLiteral;
import org.mozilla.javascript.ast.XmlMemberGet;
import org.mozilla.javascript.ast.XmlPropRef;
import org.mozilla.javascript.ast.XmlRef;
import org.mozilla.javascript.ast.XmlString;
import org.mozilla.javascript.ast.Yield;

/* loaded from: classes5.dex */
public class Parser {
    public static final int ARGC_LIMIT = 65536;
    static final int CLEAR_TI_MASK = 65535;
    private static final int GET_ENTRY = 2;
    private static final int METHOD_ENTRY = 8;
    private static final int PROP_ENTRY = 1;
    private static final int SET_ENTRY = 4;
    static final int TI_AFTER_EOL = 65536;
    static final int TI_CHECK_LABEL = 131072;
    boolean calledByCompileFunction;
    CompilerEnvirons compilerEnv;
    private int currentFlaggedToken;
    private Comment currentJsDocComment;
    private LabeledStatement currentLabel;
    Scope currentScope;
    ScriptNode currentScriptOrFn;
    private int currentToken;
    private boolean defaultUseStrictDirective;
    private int endFlags;
    private IdeErrorReporter errorCollector;
    private ErrorReporter errorReporter;
    private boolean inDestructuringAssignment;
    private boolean inForInit;
    protected boolean inUseStrictDirective;
    private Map<String, LabeledStatement> labelSet;
    private List<Jump> loopAndSwitchSet;
    private List<Loop> loopSet;
    protected int nestingOfFunction;
    private boolean parseFinished;
    private int prevNameTokenLineno;
    private int prevNameTokenStart;
    private String prevNameTokenString;
    private List<Comment> scannedComments;
    private char[] sourceChars;
    private String sourceURI;
    private int syntaxErrorCount;
    private TokenStream ts;

    private static class ConditionData {
        AstNode condition;
        int lp;
        int rp;

        private ConditionData() {
            this.lp = -1;
            this.rp = -1;
        }
    }

    private static class ParserException extends RuntimeException {
        static final long serialVersionUID = 5882582646773765630L;

        private ParserException() {
        }
    }

    protected class PerFunctionVariables {
        private Scope savedCurrentScope;
        private ScriptNode savedCurrentScriptOrFn;
        private int savedEndFlags;
        private boolean savedInForInit;
        private Map<String, LabeledStatement> savedLabelSet;
        private List<Jump> savedLoopAndSwitchSet;
        private List<Loop> savedLoopSet;

        PerFunctionVariables(FunctionNode functionNode) {
            this.savedCurrentScriptOrFn = Parser.this.currentScriptOrFn;
            Parser.this.currentScriptOrFn = functionNode;
            this.savedCurrentScope = Parser.this.currentScope;
            Parser.this.currentScope = functionNode;
            this.savedLabelSet = Parser.this.labelSet;
            Parser.this.labelSet = null;
            this.savedLoopSet = Parser.this.loopSet;
            Parser.this.loopSet = null;
            this.savedLoopAndSwitchSet = Parser.this.loopAndSwitchSet;
            Parser.this.loopAndSwitchSet = null;
            this.savedEndFlags = Parser.this.endFlags;
            Parser.this.endFlags = 0;
            this.savedInForInit = Parser.this.inForInit;
            Parser.this.inForInit = false;
        }

        void restore() {
            Parser parser = Parser.this;
            parser.currentScriptOrFn = this.savedCurrentScriptOrFn;
            parser.currentScope = this.savedCurrentScope;
            parser.labelSet = this.savedLabelSet;
            Parser.this.loopSet = this.savedLoopSet;
            Parser.this.loopAndSwitchSet = this.savedLoopAndSwitchSet;
            Parser.this.endFlags = this.savedEndFlags;
            Parser.this.inForInit = this.savedInForInit;
        }
    }

    public Parser() {
        this(new CompilerEnvirons());
    }

    private AstNode addExpr() throws IOException, RuntimeException {
        AstNode astNodeMulExpr = mulExpr();
        while (true) {
            int iPeekToken = peekToken();
            int i2 = this.ts.tokenBeg;
            if (iPeekToken != 21 && iPeekToken != 22) {
                return astNodeMulExpr;
            }
            consumeToken();
            astNodeMulExpr = new InfixExpression(iPeekToken, astNodeMulExpr, mulExpr(), i2);
        }
    }

    private AstNode andExpr() throws IOException {
        AstNode astNodeBitOrExpr = bitOrExpr();
        if (!matchToken(106)) {
            return astNodeBitOrExpr;
        }
        return new InfixExpression(106, astNodeBitOrExpr, andExpr(), this.ts.tokenBeg);
    }

    private List<AstNode> argumentList() throws IOException {
        if (matchToken(89)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        boolean z2 = this.inForInit;
        this.inForInit = false;
        do {
            try {
                if (peekToken() == 73) {
                    reportError("msg.yield.parenthesized");
                }
                AstNode astNodeAssignExpr = assignExpr();
                if (peekToken() == 120) {
                    try {
                        arrayList.add(generatorExpression(astNodeAssignExpr, 0, true));
                    } catch (IOException unused) {
                    }
                } else {
                    arrayList.add(astNodeAssignExpr);
                }
            } catch (Throwable th) {
                this.inForInit = z2;
                throw th;
            }
        } while (matchToken(90));
        this.inForInit = z2;
        mustMatchToken(89, "msg.no.paren.arg");
        return arrayList;
    }

    private AstNode arrayComprehension(AstNode astNode, int i2) throws IOException {
        int i3;
        ConditionData conditionDataCondition;
        ArrayList arrayList = new ArrayList();
        while (peekToken() == 120) {
            arrayList.add(arrayComprehensionLoop());
        }
        if (peekToken() == 113) {
            consumeToken();
            i3 = this.ts.tokenBeg - i2;
            conditionDataCondition = condition();
        } else {
            i3 = -1;
            conditionDataCondition = null;
        }
        mustMatchToken(85, "msg.no.bracket.arg");
        ArrayComprehension arrayComprehension = new ArrayComprehension(i2, this.ts.tokenEnd - i2);
        arrayComprehension.setResult(astNode);
        arrayComprehension.setLoops(arrayList);
        if (conditionDataCondition != null) {
            arrayComprehension.setIfPosition(i3);
            arrayComprehension.setFilter(conditionDataCondition.condition);
            arrayComprehension.setFilterLp(conditionDataCondition.lp - i2);
            arrayComprehension.setFilterRp(conditionDataCondition.rp - i2);
        }
        return arrayComprehension;
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x00c6 A[Catch: all -> 0x0036, TryCatch #0 {all -> 0x0036, blocks: (B:6:0x0019, B:9:0x0022, B:11:0x0030, B:16:0x003d, B:18:0x0045, B:20:0x004c, B:26:0x005a, B:29:0x0070, B:31:0x0077, B:32:0x0082, B:44:0x00b1, B:45:0x00b8, B:47:0x00c6, B:49:0x00cd, B:53:0x00e5, B:37:0x008e, B:39:0x0095, B:42:0x00a5, B:43:0x00aa, B:27:0x0061, B:28:0x0069, B:14:0x0039), top: B:58:0x0019 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00e4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private org.mozilla.javascript.ast.ArrayComprehensionLoop arrayComprehensionLoop() throws java.io.IOException, java.lang.RuntimeException {
        /*
            Method dump skipped, instructions count: 246
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.Parser.arrayComprehensionLoop():org.mozilla.javascript.ast.ArrayComprehensionLoop");
    }

    private AstNode arrayLiteral() throws IOException, RuntimeException {
        if (this.currentToken != 84) {
            codeBug();
        }
        TokenStream tokenStream = this.ts;
        int i2 = tokenStream.tokenBeg;
        int i3 = tokenStream.tokenEnd;
        ArrayList arrayList = new ArrayList();
        ArrayLiteral arrayLiteral = new ArrayLiteral(i2);
        int i4 = 0;
        int i5 = -1;
        loop0: while (true) {
            int i6 = 1;
            while (true) {
                int iPeekToken = peekToken();
                if (iPeekToken == 90) {
                    consumeToken();
                    i5 = this.ts.tokenEnd;
                    if (i6 == 0) {
                        break;
                    }
                    arrayList.add(new EmptyExpression(this.ts.tokenBeg, 1));
                    i4++;
                } else if (iPeekToken == 85) {
                    consumeToken();
                    i3 = this.ts.tokenEnd;
                    arrayLiteral.setDestructuringLength(arrayList.size() + i6);
                    arrayLiteral.setSkipCount(i4);
                    if (i5 != -1) {
                        warnTrailingComma(i2, arrayList, i5);
                    }
                } else {
                    if (iPeekToken == 120 && i6 == 0 && arrayList.size() == 1) {
                        return arrayComprehension((AstNode) arrayList.get(0), i2);
                    }
                    if (iPeekToken == 0) {
                        reportError("msg.no.bracket.arg");
                        break loop0;
                    }
                    if (i6 == 0) {
                        reportError("msg.no.bracket.arg");
                    }
                    arrayList.add(assignExpr());
                    i6 = 0;
                    i5 = -1;
                }
            }
        }
        Iterator<?> it = arrayList.iterator();
        while (it.hasNext()) {
            arrayLiteral.addElement((AstNode) it.next());
        }
        arrayLiteral.setLength(i3 - i2);
        return arrayLiteral;
    }

    private AstNode arrowFunction(AstNode astNode) throws IOException, RuntimeException {
        int i2 = this.ts.lineno;
        int position = astNode != null ? astNode.getPosition() : -1;
        FunctionNode functionNode = new FunctionNode(position);
        functionNode.setFunctionType(4);
        functionNode.setJsDocNode(getAndResetJsDoc());
        Map<String, Node> map = new HashMap<>();
        Set<String> hashSet = new HashSet<>();
        PerFunctionVariables perFunctionVariables = new PerFunctionVariables(functionNode);
        try {
            if (astNode instanceof ParenthesizedExpression) {
                functionNode.setParens(0, astNode.getLength());
                AstNode expression = ((ParenthesizedExpression) astNode).getExpression();
                if (!(expression instanceof EmptyExpression)) {
                    arrowFunctionParams(functionNode, expression, map, hashSet);
                }
            } else {
                arrowFunctionParams(functionNode, astNode, map, hashSet);
            }
            if (!map.isEmpty()) {
                Node node = new Node(90);
                for (Map.Entry<String, Node> entry : map.entrySet()) {
                    node.addChildToBack(createDestructuringAssignment(123, entry.getValue(), createName(entry.getKey())));
                }
                functionNode.putProp(23, node);
            }
            functionNode.setBody(parseFunctionBody(4, functionNode));
            functionNode.setEncodedSourceBounds(position, this.ts.tokenEnd);
            functionNode.setLength(this.ts.tokenEnd - position);
            perFunctionVariables.restore();
            if (functionNode.isGenerator()) {
                reportError("msg.arrowfunction.generator");
                return makeErrorNode();
            }
            functionNode.setSourceName(this.sourceURI);
            functionNode.setBaseLineno(i2);
            functionNode.setEndLineno(this.ts.lineno);
            return functionNode;
        } catch (Throwable th) {
            perFunctionVariables.restore();
            throw th;
        }
    }

    private void arrowFunctionParams(FunctionNode functionNode, AstNode astNode, Map<String, Node> map, Set<String> set) {
        if ((astNode instanceof ArrayLiteral) || (astNode instanceof ObjectLiteral)) {
            markDestructuring(astNode);
            functionNode.addParam(astNode);
            String nextTempName = this.currentScriptOrFn.getNextTempName();
            defineSymbol(88, nextTempName, false);
            map.put(nextTempName, astNode);
            return;
        }
        if ((astNode instanceof InfixExpression) && astNode.getType() == 90) {
            InfixExpression infixExpression = (InfixExpression) astNode;
            arrowFunctionParams(functionNode, infixExpression.getLeft(), map, set);
            arrowFunctionParams(functionNode, infixExpression.getRight(), map, set);
            return;
        }
        if (!(astNode instanceof Name)) {
            reportError("msg.no.parm", astNode.getPosition(), astNode.getLength());
            functionNode.addParam(makeErrorNode());
            return;
        }
        functionNode.addParam(astNode);
        String identifier = ((Name) astNode).getIdentifier();
        defineSymbol(88, identifier);
        if (this.inUseStrictDirective) {
            if ("eval".equals(identifier) || Constant.PARAM_SQL_ARGUMENTS.equals(identifier)) {
                reportError("msg.bad.id.strict", identifier);
            }
            if (set.contains(identifier)) {
                addError("msg.dup.param.strict", identifier);
            }
            set.add(identifier);
        }
    }

    private AstNode assignExpr() throws IOException {
        int iPeekToken = peekToken();
        boolean z2 = true;
        if (iPeekToken == 73) {
            return returnOrYield(iPeekToken, true);
        }
        AstNode astNodeCondExpr = condExpr();
        int iPeekTokenOrEOL = peekTokenOrEOL();
        if (iPeekTokenOrEOL == 1) {
            iPeekTokenOrEOL = peekToken();
        } else {
            z2 = false;
        }
        if (91 > iPeekTokenOrEOL || iPeekTokenOrEOL > 102) {
            if (iPeekTokenOrEOL == 83) {
                if (this.currentJsDocComment == null) {
                    return astNodeCondExpr;
                }
                astNodeCondExpr.setJsDocNode(getAndResetJsDoc());
                return astNodeCondExpr;
            }
            if (z2 || iPeekTokenOrEOL != 165) {
                return astNodeCondExpr;
            }
            consumeToken();
            return arrowFunction(astNodeCondExpr);
        }
        if (this.inDestructuringAssignment) {
            reportError("msg.destruct.default.vals");
        }
        consumeToken();
        Comment andResetJsDoc = getAndResetJsDoc();
        markDestructuring(astNodeCondExpr);
        Assignment assignment = new Assignment(iPeekTokenOrEOL, astNodeCondExpr, assignExpr(), this.ts.tokenBeg);
        if (andResetJsDoc != null) {
            assignment.setJsDocNode(andResetJsDoc);
        }
        return assignment;
    }

    private AstNode attributeAccess() throws IOException {
        int iNextToken = nextToken();
        TokenStream tokenStream = this.ts;
        int i2 = tokenStream.tokenBeg;
        if (iNextToken == 23) {
            saveNameTokenData(i2, ProxyConfig.MATCH_ALL_SCHEMES, tokenStream.lineno);
            return propertyName(i2, ProxyConfig.MATCH_ALL_SCHEMES, 0);
        }
        if (iNextToken == 39) {
            return propertyName(i2, tokenStream.getString(), 0);
        }
        if (iNextToken == 84) {
            return xmlElemRef(i2, null, -1);
        }
        reportError("msg.no.name.after.xmlAttr");
        return makeErrorNode();
    }

    private void autoInsertSemicolon(AstNode astNode) throws IOException {
        int iPeekFlaggedToken = peekFlaggedToken();
        int position = astNode.getPosition();
        int i2 = 65535 & iPeekFlaggedToken;
        if (i2 != -1 && i2 != 0) {
            if (i2 == 83) {
                consumeToken();
                astNode.setLength(this.ts.tokenEnd - position);
                return;
            } else if (i2 != 87) {
                if ((iPeekFlaggedToken & 65536) == 0) {
                    reportError("msg.no.semi.stmt");
                    return;
                } else {
                    warnMissingSemi(position, nodeEnd(astNode));
                    return;
                }
            }
        }
        warnMissingSemi(position, nodeEnd(astNode));
    }

    private AstNode bitAndExpr() throws IOException {
        AstNode astNodeEqExpr = eqExpr();
        while (matchToken(11)) {
            astNodeEqExpr = new InfixExpression(11, astNodeEqExpr, eqExpr(), this.ts.tokenBeg);
        }
        return astNodeEqExpr;
    }

    private AstNode bitOrExpr() throws IOException {
        AstNode astNodeBitXorExpr = bitXorExpr();
        while (matchToken(9)) {
            astNodeBitXorExpr = new InfixExpression(9, astNodeBitXorExpr, bitXorExpr(), this.ts.tokenBeg);
        }
        return astNodeBitXorExpr;
    }

    private AstNode bitXorExpr() throws IOException {
        AstNode astNodeBitAndExpr = bitAndExpr();
        while (matchToken(10)) {
            astNodeBitAndExpr = new InfixExpression(10, astNodeBitAndExpr, bitAndExpr(), this.ts.tokenBeg);
        }
        return astNodeBitAndExpr;
    }

    private AstNode block() throws IOException, RuntimeException {
        if (this.currentToken != 86) {
            codeBug();
        }
        consumeToken();
        int i2 = this.ts.tokenBeg;
        Scope scope = new Scope(i2);
        scope.setLineno(this.ts.lineno);
        pushScope(scope);
        try {
            statements(scope);
            mustMatchToken(87, "msg.no.brace.block");
            scope.setLength(this.ts.tokenEnd - i2);
            return scope;
        } finally {
            popScope();
        }
    }

    private BreakStatement breakStatement() throws IOException, RuntimeException {
        int nodeEnd;
        Name nameCreateNameNode;
        if (this.currentToken != 121) {
            codeBug();
        }
        consumeToken();
        TokenStream tokenStream = this.ts;
        int i2 = tokenStream.lineno;
        int i3 = tokenStream.tokenBeg;
        int i4 = tokenStream.tokenEnd;
        if (peekTokenOrEOL() == 39) {
            nameCreateNameNode = createNameNode();
            nodeEnd = getNodeEnd(nameCreateNameNode);
        } else {
            nodeEnd = i4;
            nameCreateNameNode = null;
        }
        LabeledStatement labeledStatementMatchJumpLabelName = matchJumpLabelName();
        Jump firstLabel = labeledStatementMatchJumpLabelName != null ? labeledStatementMatchJumpLabelName.getFirstLabel() : null;
        if (firstLabel == null && nameCreateNameNode == null) {
            List<Jump> list = this.loopAndSwitchSet;
            if (list != null && list.size() != 0) {
                firstLabel = this.loopAndSwitchSet.get(r4.size() - 1);
            } else if (nameCreateNameNode == null) {
                reportError("msg.bad.break", i3, nodeEnd - i3);
            }
        }
        BreakStatement breakStatement = new BreakStatement(i3, nodeEnd - i3);
        breakStatement.setBreakLabel(nameCreateNameNode);
        if (firstLabel != null) {
            breakStatement.setBreakTarget(firstLabel);
        }
        breakStatement.setLineno(i2);
        return breakStatement;
    }

    private void checkBadIncDec(UnaryExpression unaryExpression) {
        int type = removeParens(unaryExpression.getOperand()).getType();
        if (type == 39 || type == 33 || type == 36 || type == 68 || type == 38) {
            return;
        }
        reportError(unaryExpression.getType() == 107 ? "msg.bad.incr" : "msg.bad.decr");
    }

    private void checkCallRequiresActivation(AstNode astNode) {
        if ((astNode.getType() == 39 && "eval".equals(((Name) astNode).getIdentifier())) || (astNode.getType() == 33 && "eval".equals(((PropertyGet) astNode).getProperty().getIdentifier()))) {
            setRequiresActivation();
        }
    }

    private RuntimeException codeBug() throws RuntimeException {
        throw Kit.codeBug("ts.cursor=" + this.ts.cursor + ", ts.tokenBeg=" + this.ts.tokenBeg + ", currentToken=" + this.currentToken);
    }

    private AstNode condExpr() throws IOException {
        AstNode astNodeOrExpr = orExpr();
        if (!matchToken(103)) {
            return astNodeOrExpr;
        }
        TokenStream tokenStream = this.ts;
        int i2 = tokenStream.lineno;
        int i3 = tokenStream.tokenBeg;
        boolean z2 = this.inForInit;
        this.inForInit = false;
        try {
            AstNode astNodeAssignExpr = assignExpr();
            this.inForInit = z2;
            int i4 = mustMatchToken(104, "msg.no.colon.cond") ? this.ts.tokenBeg : -1;
            AstNode astNodeAssignExpr2 = assignExpr();
            int position = astNodeOrExpr.getPosition();
            ConditionalExpression conditionalExpression = new ConditionalExpression(position, getNodeEnd(astNodeAssignExpr2) - position);
            conditionalExpression.setLineno(i2);
            conditionalExpression.setTestExpression(astNodeOrExpr);
            conditionalExpression.setTrueExpression(astNodeAssignExpr);
            conditionalExpression.setFalseExpression(astNodeAssignExpr2);
            conditionalExpression.setQuestionMarkPosition(i3 - position);
            conditionalExpression.setColonPosition(i4 - position);
            return conditionalExpression;
        } catch (Throwable th) {
            this.inForInit = z2;
            throw th;
        }
    }

    private ConditionData condition() throws IOException {
        ConditionData conditionData = new ConditionData();
        if (mustMatchToken(88, "msg.no.paren.cond")) {
            conditionData.lp = this.ts.tokenBeg;
        }
        conditionData.condition = expr();
        if (mustMatchToken(89, "msg.no.paren.after.cond")) {
            conditionData.rp = this.ts.tokenBeg;
        }
        AstNode astNode = conditionData.condition;
        if (astNode instanceof Assignment) {
            addStrictWarning("msg.equal.as.assign", "", astNode.getPosition(), conditionData.condition.getLength());
        }
        return conditionData;
    }

    private void consumeToken() {
        this.currentFlaggedToken = 0;
    }

    private ContinueStatement continueStatement() throws IOException, RuntimeException {
        int nodeEnd;
        Name nameCreateNameNode;
        if (this.currentToken != 122) {
            codeBug();
        }
        consumeToken();
        TokenStream tokenStream = this.ts;
        int i2 = tokenStream.lineno;
        int i3 = tokenStream.tokenBeg;
        int i4 = tokenStream.tokenEnd;
        Loop loop = null;
        if (peekTokenOrEOL() == 39) {
            nameCreateNameNode = createNameNode();
            nodeEnd = getNodeEnd(nameCreateNameNode);
        } else {
            nodeEnd = i4;
            nameCreateNameNode = null;
        }
        LabeledStatement labeledStatementMatchJumpLabelName = matchJumpLabelName();
        if (labeledStatementMatchJumpLabelName == null && nameCreateNameNode == null) {
            List<Loop> list = this.loopSet;
            if (list == null || list.size() == 0) {
                reportError("msg.continue.outside");
            } else {
                loop = this.loopSet.get(r4.size() - 1);
            }
        } else {
            if (labeledStatementMatchJumpLabelName == null || !(labeledStatementMatchJumpLabelName.getStatement() instanceof Loop)) {
                reportError("msg.continue.nonloop", i3, nodeEnd - i3);
            }
            if (labeledStatementMatchJumpLabelName != null) {
                loop = (Loop) labeledStatementMatchJumpLabelName.getStatement();
            }
        }
        ContinueStatement continueStatement = new ContinueStatement(i3, nodeEnd - i3);
        if (loop != null) {
            continueStatement.setTarget(loop);
        }
        continueStatement.setLabel(nameCreateNameNode);
        continueStatement.setLineno(i2);
        return continueStatement;
    }

    private Name createNameNode() {
        return createNameNode(false, 39);
    }

    private StringLiteral createStringLiteral() {
        TokenStream tokenStream = this.ts;
        int i2 = tokenStream.tokenBeg;
        StringLiteral stringLiteral = new StringLiteral(i2, tokenStream.tokenEnd - i2);
        stringLiteral.setLineno(this.ts.lineno);
        stringLiteral.setValue(this.ts.getString());
        stringLiteral.setQuoteCharacter(this.ts.getQuoteChar());
        return stringLiteral;
    }

    private AstNode defaultXmlNamespace() throws IOException, RuntimeException {
        if (this.currentToken != 117) {
            codeBug();
        }
        consumeToken();
        mustHaveXML();
        setRequiresActivation();
        TokenStream tokenStream = this.ts;
        int i2 = tokenStream.lineno;
        int i3 = tokenStream.tokenBeg;
        if (!matchToken(39) || !"xml".equals(this.ts.getString())) {
            reportError("msg.bad.namespace");
        }
        if (!matchToken(39) || !"namespace".equals(this.ts.getString())) {
            reportError("msg.bad.namespace");
        }
        if (!matchToken(91)) {
            reportError("msg.bad.namespace");
        }
        AstNode astNodeExpr = expr();
        UnaryExpression unaryExpression = new UnaryExpression(i3, getNodeEnd(astNodeExpr) - i3);
        unaryExpression.setOperator(75);
        unaryExpression.setOperand(astNodeExpr);
        unaryExpression.setLineno(i2);
        return new ExpressionStatement((AstNode) unaryExpression, true);
    }

    private AstNode destructuringPrimaryExpr() throws ParserException, IOException {
        try {
            this.inDestructuringAssignment = true;
            return primaryExpr();
        } finally {
            this.inDestructuringAssignment = false;
        }
    }

    private DoLoop doLoop() throws IOException, RuntimeException {
        if (this.currentToken != 119) {
            codeBug();
        }
        consumeToken();
        int i2 = this.ts.tokenBeg;
        DoLoop doLoop = new DoLoop(i2);
        doLoop.setLineno(this.ts.lineno);
        enterLoop(doLoop);
        try {
            AstNode astNodeStatement = statement();
            mustMatchToken(118, "msg.no.while.do");
            doLoop.setWhilePosition(this.ts.tokenBeg - i2);
            ConditionData conditionDataCondition = condition();
            doLoop.setCondition(conditionDataCondition.condition);
            doLoop.setParens(conditionDataCondition.lp - i2, conditionDataCondition.rp - i2);
            int nodeEnd = getNodeEnd(astNodeStatement);
            doLoop.setBody(astNodeStatement);
            exitLoop();
            if (matchToken(83)) {
                nodeEnd = this.ts.tokenEnd;
            }
            doLoop.setLength(nodeEnd - i2);
            return doLoop;
        } catch (Throwable th) {
            exitLoop();
            throw th;
        }
    }

    private void enterLoop(Loop loop) throws RuntimeException {
        if (this.loopSet == null) {
            this.loopSet = new ArrayList();
        }
        this.loopSet.add(loop);
        if (this.loopAndSwitchSet == null) {
            this.loopAndSwitchSet = new ArrayList();
        }
        this.loopAndSwitchSet.add(loop);
        pushScope(loop);
        LabeledStatement labeledStatement = this.currentLabel;
        if (labeledStatement != null) {
            labeledStatement.setStatement(loop);
            this.currentLabel.getFirstLabel().setLoop(loop);
            loop.setRelative(-this.currentLabel.getPosition());
        }
    }

    private void enterSwitch(SwitchStatement switchStatement) {
        if (this.loopAndSwitchSet == null) {
            this.loopAndSwitchSet = new ArrayList();
        }
        this.loopAndSwitchSet.add(switchStatement);
    }

    private AstNode eqExpr() throws IOException {
        AstNode astNodeRelExpr = relExpr();
        while (true) {
            int iPeekToken = peekToken();
            int i2 = this.ts.tokenBeg;
            if (iPeekToken != 12 && iPeekToken != 13 && iPeekToken != 46 && iPeekToken != 47) {
                return astNodeRelExpr;
            }
            consumeToken();
            if (this.compilerEnv.getLanguageVersion() == 120) {
                if (iPeekToken == 12) {
                    iPeekToken = 46;
                } else if (iPeekToken == 13) {
                    iPeekToken = 47;
                }
            }
            astNodeRelExpr = new InfixExpression(iPeekToken, astNodeRelExpr, relExpr(), i2);
        }
    }

    private void exitLoop() {
        Loop loopRemove = this.loopSet.remove(r0.size() - 1);
        this.loopAndSwitchSet.remove(r1.size() - 1);
        if (loopRemove.getParent() != null) {
            loopRemove.setRelative(loopRemove.getParent().getPosition());
        }
        popScope();
    }

    private void exitSwitch() {
        this.loopAndSwitchSet.remove(r0.size() - 1);
    }

    private AstNode expr() throws IOException {
        AstNode astNodeAssignExpr = assignExpr();
        int position = astNodeAssignExpr.getPosition();
        while (matchToken(90)) {
            int i2 = this.ts.tokenBeg;
            if (this.compilerEnv.isStrictMode() && !astNodeAssignExpr.hasSideEffects()) {
                addStrictWarning("msg.no.side.effects", "", position, nodeEnd(astNodeAssignExpr) - position);
            }
            if (peekToken() == 73) {
                reportError("msg.yield.parenthesized");
            }
            astNodeAssignExpr = new InfixExpression(90, astNodeAssignExpr, assignExpr(), i2);
        }
        return astNodeAssignExpr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Loop forLoop() throws IOException, RuntimeException {
        boolean z2;
        int i2;
        AstNode astNodeExpr;
        AstNode astNodeExpr2;
        boolean z3;
        AstNode astNode;
        AstNode astNodeExpr3;
        int i3;
        ForInLoop forInLoop;
        if (this.currentToken != 120) {
            codeBug();
        }
        consumeToken();
        TokenStream tokenStream = this.ts;
        int i4 = tokenStream.tokenBeg;
        int i5 = tokenStream.lineno;
        Scope scope = new Scope();
        pushScope(scope);
        try {
            boolean z4 = false;
            if (!matchToken(39)) {
                z2 = false;
                i2 = -1;
            } else if ("each".equals(this.ts.getString())) {
                i2 = this.ts.tokenBeg - i4;
                z2 = true;
            } else {
                reportError("msg.no.paren.for");
                z2 = false;
                i2 = -1;
            }
            int i6 = mustMatchToken(88, "msg.no.paren.for") ? this.ts.tokenBeg - i4 : -1;
            AstNode astNodeForLoopInit = forLoopInit(peekToken());
            if (matchToken(52)) {
                i3 = this.ts.tokenBeg - i4;
                astNode = null;
                astNodeExpr3 = expr();
                z3 = false;
                z4 = true;
            } else if (this.compilerEnv.getLanguageVersion() >= 200 && matchToken(39) && "of".equals(this.ts.getString())) {
                i3 = this.ts.tokenBeg - i4;
                astNode = null;
                astNodeExpr3 = expr();
                z3 = true;
            } else {
                mustMatchToken(83, "msg.no.semi.for");
                if (peekToken() == 83) {
                    astNodeExpr = new EmptyExpression(this.ts.tokenBeg, 1);
                    astNodeExpr.setLineno(this.ts.lineno);
                } else {
                    astNodeExpr = expr();
                }
                mustMatchToken(83, "msg.no.semi.for.cond");
                int i7 = this.ts.tokenEnd;
                if (peekToken() == 89) {
                    astNodeExpr2 = new EmptyExpression(i7, 1);
                    astNodeExpr2.setLineno(this.ts.lineno);
                } else {
                    astNodeExpr2 = expr();
                }
                z3 = false;
                astNode = astNodeExpr2;
                astNodeExpr3 = astNodeExpr;
                i3 = -1;
            }
            int i8 = mustMatchToken(89, "msg.no.paren.for.ctrl") ? this.ts.tokenBeg - i4 : -1;
            if (z4 || z3) {
                ForInLoop forInLoop2 = new ForInLoop(i4);
                if ((astNodeForLoopInit instanceof VariableDeclaration) && ((VariableDeclaration) astNodeForLoopInit).getVariables().size() > 1) {
                    reportError("msg.mult.index");
                }
                if (z3 && z2) {
                    reportError("msg.invalid.for.each");
                }
                forInLoop2.setIterator(astNodeForLoopInit);
                forInLoop2.setIteratedObject(astNodeExpr3);
                forInLoop2.setInPosition(i3);
                forInLoop2.setIsForEach(z2);
                forInLoop2.setEachPosition(i2);
                forInLoop2.setIsForOf(z3);
                forInLoop = forInLoop2;
            } else {
                ForLoop forLoop = new ForLoop(i4);
                forLoop.setInitializer(astNodeForLoopInit);
                forLoop.setCondition(astNodeExpr3);
                forLoop.setIncrement(astNode);
                forInLoop = forLoop;
            }
            this.currentScope.replaceWith(forInLoop);
            popScope();
            enterLoop(forInLoop);
            try {
                AstNode astNodeStatement = statement();
                forInLoop.setLength(getNodeEnd(astNodeStatement) - i4);
                forInLoop.setBody(astNodeStatement);
                if (this.currentScope == scope) {
                    popScope();
                }
                forInLoop.setParens(i6, i8);
                forInLoop.setLineno(i5);
                return forInLoop;
            } finally {
                exitLoop();
            }
        } catch (Throwable th) {
            if (this.currentScope == scope) {
                popScope();
            }
            throw th;
        }
    }

    private AstNode forLoopInit(int i2) throws IOException {
        AstNode astNodeVariables;
        try {
            this.inForInit = true;
            if (i2 == 83) {
                astNodeVariables = new EmptyExpression(this.ts.tokenBeg, 1);
                astNodeVariables.setLineno(this.ts.lineno);
            } else if (i2 == 123 || i2 == 154) {
                consumeToken();
                astNodeVariables = variables(i2, this.ts.tokenBeg, false);
            } else {
                astNodeVariables = expr();
                markDestructuring(astNodeVariables);
            }
            this.inForInit = false;
            return astNodeVariables;
        } catch (Throwable th) {
            this.inForInit = false;
            throw th;
        }
    }

    private FunctionNode function(int i2) throws IOException, RuntimeException {
        Name nameCreateNameNode;
        TokenStream tokenStream = this.ts;
        int i3 = tokenStream.lineno;
        int i4 = tokenStream.tokenBeg;
        AstNode astNodeMemberExprTail = null;
        if (matchToken(39)) {
            nameCreateNameNode = createNameNode(true, 39);
            if (this.inUseStrictDirective) {
                String identifier = nameCreateNameNode.getIdentifier();
                if ("eval".equals(identifier) || Constant.PARAM_SQL_ARGUMENTS.equals(identifier)) {
                    reportError("msg.bad.id.strict", identifier);
                }
            }
            if (!matchToken(88)) {
                if (this.compilerEnv.isAllowMemberExprAsFunctionName()) {
                    astNodeMemberExprTail = memberExprTail(false, nameCreateNameNode);
                    nameCreateNameNode = null;
                }
                mustMatchToken(88, "msg.no.paren.parms");
            }
        } else if (matchToken(88)) {
            nameCreateNameNode = null;
        } else {
            AstNode astNodeMemberExpr = this.compilerEnv.isAllowMemberExprAsFunctionName() ? memberExpr(false) : null;
            mustMatchToken(88, "msg.no.paren.parms");
            astNodeMemberExprTail = astNodeMemberExpr;
            nameCreateNameNode = null;
        }
        int i5 = this.currentToken == 88 ? this.ts.tokenBeg : -1;
        if ((astNodeMemberExprTail != null ? 2 : i2) != 2 && nameCreateNameNode != null && nameCreateNameNode.length() > 0) {
            defineSymbol(110, nameCreateNameNode.getIdentifier());
        }
        FunctionNode functionNode = new FunctionNode(i4, nameCreateNameNode);
        functionNode.setFunctionType(i2);
        if (i5 != -1) {
            functionNode.setLp(i5 - i4);
        }
        functionNode.setJsDocNode(getAndResetJsDoc());
        PerFunctionVariables perFunctionVariables = new PerFunctionVariables(functionNode);
        try {
            parseFunctionParams(functionNode);
            functionNode.setBody(parseFunctionBody(i2, functionNode));
            functionNode.setEncodedSourceBounds(i4, this.ts.tokenEnd);
            functionNode.setLength(this.ts.tokenEnd - i4);
            if (this.compilerEnv.isStrictMode() && !functionNode.getBody().hasConsistentReturnUsage()) {
                addStrictWarning((nameCreateNameNode == null || nameCreateNameNode.length() <= 0) ? "msg.anon.no.return.value" : "msg.no.return.value", nameCreateNameNode == null ? "" : nameCreateNameNode.getIdentifier());
            }
            perFunctionVariables.restore();
            if (astNodeMemberExprTail != null) {
                Kit.codeBug();
                functionNode.setMemberExprNode(astNodeMemberExprTail);
            }
            functionNode.setSourceName(this.sourceURI);
            functionNode.setBaseLineno(i3);
            functionNode.setEndLineno(this.ts.lineno);
            if (this.compilerEnv.isIdeMode()) {
                functionNode.setParentScope(this.currentScope);
            }
            return functionNode;
        } catch (Throwable th) {
            perFunctionVariables.restore();
            throw th;
        }
    }

    private AstNode generatorExpression(AstNode astNode, int i2) throws IOException {
        return generatorExpression(astNode, i2, false);
    }

    private GeneratorExpressionLoop generatorExpressionLoop() throws IOException, RuntimeException {
        AstNode astNodeCreateNameNode;
        if (nextToken() != 120) {
            codeBug();
        }
        int i2 = this.ts.tokenBeg;
        GeneratorExpressionLoop generatorExpressionLoop = new GeneratorExpressionLoop(i2);
        pushScope(generatorExpressionLoop);
        try {
            int i3 = mustMatchToken(88, "msg.no.paren.for") ? this.ts.tokenBeg - i2 : -1;
            int iPeekToken = peekToken();
            if (iPeekToken == 39) {
                consumeToken();
                astNodeCreateNameNode = createNameNode();
            } else if (iPeekToken == 84 || iPeekToken == 86) {
                astNodeCreateNameNode = destructuringPrimaryExpr();
                markDestructuring(astNodeCreateNameNode);
            } else {
                reportError("msg.bad.var");
                astNodeCreateNameNode = null;
            }
            if (astNodeCreateNameNode.getType() == 39) {
                defineSymbol(154, this.ts.getString(), true);
            }
            int i4 = mustMatchToken(52, "msg.in.after.for.name") ? this.ts.tokenBeg - i2 : -1;
            AstNode astNodeExpr = expr();
            int i5 = mustMatchToken(89, "msg.no.paren.for.ctrl") ? this.ts.tokenBeg - i2 : -1;
            generatorExpressionLoop.setLength(this.ts.tokenEnd - i2);
            generatorExpressionLoop.setIterator(astNodeCreateNameNode);
            generatorExpressionLoop.setIteratedObject(astNodeExpr);
            generatorExpressionLoop.setInPosition(i4);
            generatorExpressionLoop.setParens(i3, i5);
            popScope();
            return generatorExpressionLoop;
        } catch (Throwable th) {
            popScope();
            throw th;
        }
    }

    private Comment getAndResetJsDoc() {
        Comment comment = this.currentJsDocComment;
        this.currentJsDocComment = null;
        return comment;
    }

    private String getDirective(AstNode astNode) {
        if (!(astNode instanceof ExpressionStatement)) {
            return null;
        }
        AstNode expression = ((ExpressionStatement) astNode).getExpression();
        if (expression instanceof StringLiteral) {
            return ((StringLiteral) expression).getValue();
        }
        return null;
    }

    private int getNodeEnd(AstNode astNode) {
        return astNode.getPosition() + astNode.getLength();
    }

    private int getNumberOfEols(String str) {
        int i2 = 0;
        for (int length = str.length() - 1; length >= 0; length--) {
            if (str.charAt(length) == '\n') {
                i2++;
            }
        }
        return i2;
    }

    private IfStatement ifStatement() throws IOException, RuntimeException {
        int i2;
        AstNode astNodeStatement;
        if (this.currentToken != 113) {
            codeBug();
        }
        consumeToken();
        TokenStream tokenStream = this.ts;
        int i3 = tokenStream.tokenBeg;
        int i4 = tokenStream.lineno;
        ConditionData conditionDataCondition = condition();
        AstNode astNodeStatement2 = statement();
        if (matchToken(114)) {
            i2 = this.ts.tokenBeg - i3;
            astNodeStatement = statement();
        } else {
            i2 = -1;
            astNodeStatement = null;
        }
        IfStatement ifStatement = new IfStatement(i3, getNodeEnd(astNodeStatement != null ? astNodeStatement : astNodeStatement2) - i3);
        ifStatement.setCondition(conditionDataCondition.condition);
        ifStatement.setParens(conditionDataCondition.lp - i3, conditionDataCondition.rp - i3);
        ifStatement.setThenPart(astNodeStatement2);
        ifStatement.setElsePart(astNodeStatement);
        ifStatement.setElsePosition(i2);
        ifStatement.setLineno(i4);
        return ifStatement;
    }

    private AstNode let(boolean z2, int i2) throws IOException {
        LetNode letNode = new LetNode(i2);
        letNode.setLineno(this.ts.lineno);
        if (mustMatchToken(88, "msg.no.paren.after.let")) {
            letNode.setLp(this.ts.tokenBeg - i2);
        }
        pushScope(letNode);
        try {
            letNode.setVariables(variables(154, this.ts.tokenBeg, z2));
            if (mustMatchToken(89, "msg.no.paren.let")) {
                letNode.setRp(this.ts.tokenBeg - i2);
            }
            if (z2 && peekToken() == 86) {
                consumeToken();
                int i3 = this.ts.tokenBeg;
                AstNode astNodeStatements = statements();
                mustMatchToken(87, "msg.no.curly.let");
                astNodeStatements.setLength(this.ts.tokenEnd - i3);
                letNode.setLength(this.ts.tokenEnd - i2);
                letNode.setBody(astNodeStatements);
                letNode.setType(154);
            } else {
                AstNode astNodeExpr = expr();
                letNode.setLength(getNodeEnd(astNodeExpr) - i2);
                letNode.setBody(astNodeExpr);
                if (z2) {
                    ExpressionStatement expressionStatement = new ExpressionStatement(letNode, !insideFunction());
                    expressionStatement.setLineno(letNode.getLineno());
                    popScope();
                    return expressionStatement;
                }
            }
            popScope();
            return letNode;
        } catch (Throwable th) {
            popScope();
            throw th;
        }
    }

    private AstNode letStatement() throws IOException, RuntimeException {
        if (this.currentToken != 154) {
            codeBug();
        }
        consumeToken();
        TokenStream tokenStream = this.ts;
        int i2 = tokenStream.lineno;
        int i3 = tokenStream.tokenBeg;
        AstNode astNodeLet = peekToken() == 88 ? let(true, i3) : variables(154, i3, true);
        astNodeLet.setLineno(i2);
        return astNodeLet;
    }

    private int lineBeginningFor(int i2) {
        char[] cArr = this.sourceChars;
        if (cArr == null) {
            return -1;
        }
        if (i2 <= 0) {
            return 0;
        }
        if (i2 >= cArr.length) {
            i2 = cArr.length - 1;
        }
        while (true) {
            int i3 = i2 - 1;
            if (i3 < 0) {
                return 0;
            }
            if (ScriptRuntime.isJSLineTerminator(cArr[i3])) {
                return i2;
            }
            i2 = i3;
        }
    }

    private ErrorNode makeErrorNode() {
        TokenStream tokenStream = this.ts;
        int i2 = tokenStream.tokenBeg;
        ErrorNode errorNode = new ErrorNode(i2, tokenStream.tokenEnd - i2);
        errorNode.setLineno(this.ts.lineno);
        return errorNode;
    }

    private LabeledStatement matchJumpLabelName() throws IOException {
        if (peekTokenOrEOL() == 39) {
            consumeToken();
            Map<String, LabeledStatement> map = this.labelSet;
            labeledStatement = map != null ? map.get(this.ts.getString()) : null;
            if (labeledStatement == null) {
                reportError("msg.undef.label");
            }
        }
        return labeledStatement;
    }

    private boolean matchToken(int i2) throws IOException {
        if (peekToken() != i2) {
            return false;
        }
        consumeToken();
        return true;
    }

    private AstNode memberExpr(boolean z2) throws IOException, RuntimeException {
        AstNode astNodePrimaryExpr;
        int iPeekToken = peekToken();
        int i2 = this.ts.lineno;
        if (iPeekToken != 30) {
            astNodePrimaryExpr = primaryExpr();
        } else {
            consumeToken();
            int i3 = this.ts.tokenBeg;
            NewExpression newExpression = new NewExpression(i3);
            AstNode astNodeMemberExpr = memberExpr(false);
            int nodeEnd = getNodeEnd(astNodeMemberExpr);
            newExpression.setTarget(astNodeMemberExpr);
            if (matchToken(88)) {
                int i4 = this.ts.tokenBeg;
                List<AstNode> listArgumentList = argumentList();
                if (listArgumentList != null && listArgumentList.size() > 65536) {
                    reportError("msg.too.many.constructor.args");
                }
                TokenStream tokenStream = this.ts;
                int i5 = tokenStream.tokenBeg;
                int i6 = tokenStream.tokenEnd;
                if (listArgumentList != null) {
                    newExpression.setArguments(listArgumentList);
                }
                newExpression.setParens(i4 - i3, i5 - i3);
                nodeEnd = i6;
            }
            if (matchToken(86)) {
                ObjectLiteral objectLiteral = objectLiteral();
                nodeEnd = getNodeEnd(objectLiteral);
                newExpression.setInitializer(objectLiteral);
            }
            newExpression.setLength(nodeEnd - i3);
            astNodePrimaryExpr = newExpression;
        }
        astNodePrimaryExpr.setLineno(i2);
        return memberExprTail(z2, astNodePrimaryExpr);
    }

    private AstNode memberExprTail(boolean z2, AstNode astNode) throws IOException, RuntimeException {
        AstNode astNode2;
        if (astNode == null) {
            codeBug();
        }
        int position = astNode.getPosition();
        while (true) {
            int iPeekToken = peekToken();
            int i2 = -1;
            if (iPeekToken == 84) {
                consumeToken();
                TokenStream tokenStream = this.ts;
                int i3 = tokenStream.tokenBeg;
                int i4 = tokenStream.lineno;
                AstNode astNodeExpr = expr();
                int nodeEnd = getNodeEnd(astNodeExpr);
                if (mustMatchToken(85, "msg.no.bracket.index")) {
                    TokenStream tokenStream2 = this.ts;
                    int i5 = tokenStream2.tokenBeg;
                    nodeEnd = tokenStream2.tokenEnd;
                    i2 = i5;
                }
                ElementGet elementGet = new ElementGet(position, nodeEnd - position);
                elementGet.setTarget(astNode);
                elementGet.setElement(astNodeExpr);
                elementGet.setParens(i3, i2);
                elementGet.setLineno(i4);
                astNode2 = elementGet;
            } else if (iPeekToken != 88) {
                if (iPeekToken == 109 || iPeekToken == 144) {
                    int i6 = this.ts.lineno;
                    astNode = propertyAccess(iPeekToken, astNode);
                    astNode.setLineno(i6);
                } else {
                    if (iPeekToken != 147) {
                        break;
                    }
                    consumeToken();
                    TokenStream tokenStream3 = this.ts;
                    int i7 = tokenStream3.tokenBeg;
                    int i8 = tokenStream3.lineno;
                    mustHaveXML();
                    setRequiresActivation();
                    AstNode astNodeExpr2 = expr();
                    int nodeEnd2 = getNodeEnd(astNodeExpr2);
                    if (mustMatchToken(89, "msg.no.paren")) {
                        TokenStream tokenStream4 = this.ts;
                        int i9 = tokenStream4.tokenBeg;
                        nodeEnd2 = tokenStream4.tokenEnd;
                        i2 = i9;
                    }
                    XmlDotQuery xmlDotQuery = new XmlDotQuery(position, nodeEnd2 - position);
                    xmlDotQuery.setLeft(astNode);
                    xmlDotQuery.setRight(astNodeExpr2);
                    xmlDotQuery.setOperatorPosition(i7);
                    xmlDotQuery.setRp(i2 - position);
                    xmlDotQuery.setLineno(i8);
                    astNode2 = xmlDotQuery;
                }
            } else {
                if (!z2) {
                    break;
                }
                int i10 = this.ts.lineno;
                consumeToken();
                checkCallRequiresActivation(astNode);
                FunctionCall functionCall = new FunctionCall(position);
                functionCall.setTarget(astNode);
                functionCall.setLineno(i10);
                functionCall.setLp(this.ts.tokenBeg - position);
                List<AstNode> listArgumentList = argumentList();
                if (listArgumentList != null && listArgumentList.size() > 65536) {
                    reportError("msg.too.many.function.args");
                }
                functionCall.setArguments(listArgumentList);
                functionCall.setRp(this.ts.tokenBeg - position);
                functionCall.setLength(this.ts.tokenEnd - position);
                astNode = functionCall;
            }
            astNode = astNode2;
        }
        return astNode;
    }

    private ObjectProperty methodDefinition(int i2, AstNode astNode, int i3) throws IOException, RuntimeException {
        FunctionNode functionNodeFunction = function(2);
        Name functionName = functionNodeFunction.getFunctionName();
        if (functionName != null && functionName.length() != 0) {
            reportError("msg.bad.prop");
        }
        ObjectProperty objectProperty = new ObjectProperty(i2);
        if (i3 == 2) {
            objectProperty.setIsGetterMethod();
            functionNodeFunction.setFunctionIsGetterMethod();
        } else if (i3 == 4) {
            objectProperty.setIsSetterMethod();
            functionNodeFunction.setFunctionIsSetterMethod();
        } else if (i3 == 8) {
            objectProperty.setIsNormalMethod();
            functionNodeFunction.setFunctionIsNormalMethod();
        }
        int nodeEnd = getNodeEnd(functionNodeFunction);
        objectProperty.setLeft(astNode);
        objectProperty.setRight(functionNodeFunction);
        objectProperty.setLength(nodeEnd - i2);
        return objectProperty;
    }

    private AstNode mulExpr() throws IOException, RuntimeException {
        AstNode astNodeUnaryExpr = unaryExpr();
        while (true) {
            int iPeekToken = peekToken();
            int i2 = this.ts.tokenBeg;
            switch (iPeekToken) {
                case 23:
                case 24:
                case 25:
                    consumeToken();
                    astNodeUnaryExpr = new InfixExpression(iPeekToken, astNodeUnaryExpr, unaryExpr(), i2);
                default:
                    return astNodeUnaryExpr;
            }
        }
    }

    private void mustHaveXML() {
        if (this.compilerEnv.isXmlAvailable()) {
            return;
        }
        reportError("msg.XML.not.available");
    }

    private boolean mustMatchToken(int i2, String str) throws IOException {
        TokenStream tokenStream = this.ts;
        int i3 = tokenStream.tokenBeg;
        return mustMatchToken(i2, str, i3, tokenStream.tokenEnd - i3);
    }

    private AstNode name(int i2, int i3) throws IOException {
        String string = this.ts.getString();
        TokenStream tokenStream = this.ts;
        int i4 = tokenStream.tokenBeg;
        int i5 = tokenStream.lineno;
        if ((i2 & 131072) == 0 || peekToken() != 104) {
            saveNameTokenData(i4, string, i5);
            return this.compilerEnv.isXmlAvailable() ? propertyName(-1, string, 0) : createNameNode(true, 39);
        }
        Label label = new Label(i4, this.ts.tokenEnd - i4);
        label.setName(string);
        label.setLineno(this.ts.lineno);
        return label;
    }

    private AstNode nameOrLabel() throws IOException, RuntimeException {
        AstNode astNodeStatementHelper;
        if (this.currentToken != 39) {
            throw codeBug();
        }
        int i2 = this.ts.tokenBeg;
        this.currentFlaggedToken |= 131072;
        AstNode astNodeExpr = expr();
        if (astNodeExpr.getType() != 131) {
            ExpressionStatement expressionStatement = new ExpressionStatement(astNodeExpr, !insideFunction());
            expressionStatement.lineno = astNodeExpr.lineno;
            return expressionStatement;
        }
        LabeledStatement labeledStatement = new LabeledStatement(i2);
        recordLabel((Label) astNodeExpr, labeledStatement);
        labeledStatement.setLineno(this.ts.lineno);
        while (true) {
            if (peekToken() != 39) {
                astNodeStatementHelper = null;
                break;
            }
            this.currentFlaggedToken |= 131072;
            AstNode astNodeExpr2 = expr();
            if (astNodeExpr2.getType() != 131) {
                astNodeStatementHelper = new ExpressionStatement(astNodeExpr2, !insideFunction());
                autoInsertSemicolon(astNodeStatementHelper);
                break;
            }
            recordLabel((Label) astNodeExpr2, labeledStatement);
        }
        try {
            this.currentLabel = labeledStatement;
            if (astNodeStatementHelper == null) {
                astNodeStatementHelper = statementHelper();
            }
            labeledStatement.setLength(astNodeStatementHelper.getParent() == null ? getNodeEnd(astNodeStatementHelper) - i2 : getNodeEnd(astNodeStatementHelper));
            labeledStatement.setStatement(astNodeStatementHelper);
            return labeledStatement;
        } finally {
            this.currentLabel = null;
            Iterator<Label> it = labeledStatement.getLabels().iterator();
            while (it.hasNext()) {
                this.labelSet.remove(it.next().getName());
            }
        }
    }

    private int nextFlaggedToken() throws IOException {
        peekToken();
        int i2 = this.currentFlaggedToken;
        consumeToken();
        return i2;
    }

    private int nextToken() throws IOException {
        int iPeekToken = peekToken();
        consumeToken();
        return iPeekToken;
    }

    private int nodeEnd(AstNode astNode) {
        return astNode.getPosition() + astNode.getLength();
    }

    private static final boolean nowAllSet(int i2, int i3, int i4) {
        return (i2 & i4) != i4 && (i3 & i4) == i4;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x010a A[LOOP:0: B:7:0x0024->B:71:0x010a, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0111 A[EDGE_INSN: B:78:0x0111->B:72:0x0111 BREAK  A[LOOP:0: B:7:0x0024->B:71:0x010a], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private org.mozilla.javascript.ast.ObjectLiteral objectLiteral() throws java.io.IOException, java.lang.RuntimeException {
        /*
            Method dump skipped, instructions count: 300
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.Parser.objectLiteral():org.mozilla.javascript.ast.ObjectLiteral");
    }

    private AstNode objliteralProperty() throws IOException {
        switch (peekToken()) {
            case 39:
                return createNameNode();
            case 40:
                TokenStream tokenStream = this.ts;
                return new NumberLiteral(tokenStream.tokenBeg, tokenStream.getString(), this.ts.getNumber());
            case 41:
                return createStringLiteral();
            default:
                if (this.compilerEnv.isReservedKeywordAsIdentifier() && TokenStream.isKeyword(this.ts.getString(), this.compilerEnv.getLanguageVersion(), this.inUseStrictDirective)) {
                    return createNameNode();
                }
                return null;
        }
    }

    private AstNode orExpr() throws IOException {
        AstNode astNodeAndExpr = andExpr();
        if (!matchToken(105)) {
            return astNodeAndExpr;
        }
        return new InfixExpression(105, astNodeAndExpr, orExpr(), this.ts.tokenBeg);
    }

    private AstNode parenExpr() throws IOException {
        boolean z2 = this.inForInit;
        this.inForInit = false;
        try {
            Comment andResetJsDoc = getAndResetJsDoc();
            TokenStream tokenStream = this.ts;
            int i2 = tokenStream.lineno;
            int i3 = tokenStream.tokenBeg;
            AstNode emptyExpression = peekToken() == 89 ? new EmptyExpression(i3) : expr();
            if (peekToken() == 120) {
                AstNode astNodeGeneratorExpression = generatorExpression(emptyExpression, i3);
                this.inForInit = z2;
                return astNodeGeneratorExpression;
            }
            ParenthesizedExpression parenthesizedExpression = new ParenthesizedExpression(emptyExpression);
            if (andResetJsDoc == null) {
                andResetJsDoc = getAndResetJsDoc();
            }
            if (andResetJsDoc != null) {
                parenthesizedExpression.setJsDocNode(andResetJsDoc);
            }
            mustMatchToken(89, "msg.no.paren");
            if (emptyExpression.getType() != 129 || peekToken() == 165) {
                parenthesizedExpression.setLength(this.ts.tokenEnd - parenthesizedExpression.getPosition());
                parenthesizedExpression.setLineno(i2);
                this.inForInit = z2;
                return parenthesizedExpression;
            }
            reportError("msg.syntax");
            ErrorNode errorNodeMakeErrorNode = makeErrorNode();
            this.inForInit = z2;
            return errorNodeMakeErrorNode;
        } catch (Throwable th) {
            this.inForInit = z2;
            throw th;
        }
    }

    private AstNode parseFunctionBody(int i2, FunctionNode functionNode) throws IOException {
        boolean z2;
        AstNode astNodeFunction;
        if (matchToken(86)) {
            z2 = false;
        } else if (this.compilerEnv.getLanguageVersion() >= 180 || i2 == 4) {
            z2 = true;
        } else {
            reportError("msg.no.brace.body");
            z2 = false;
        }
        boolean z3 = i2 == 4;
        this.nestingOfFunction++;
        int i3 = this.ts.tokenBeg;
        Block block = new Block(i3);
        boolean z4 = this.inUseStrictDirective;
        block.setLineno(this.ts.lineno);
        try {
            if (z2) {
                AstNode astNodeAssignExpr = assignExpr();
                ReturnStatement returnStatement = new ReturnStatement(astNodeAssignExpr.getPosition(), astNodeAssignExpr.getLength(), astNodeAssignExpr);
                Boolean bool = Boolean.TRUE;
                returnStatement.putProp(25, bool);
                block.putProp(25, bool);
                if (z3) {
                    returnStatement.putProp(27, bool);
                }
                block.addStatement(returnStatement);
            } else {
                boolean z5 = true;
                while (true) {
                    int iPeekToken = peekToken();
                    if (iPeekToken == -1 || iPeekToken == 0 || iPeekToken == 87) {
                        break;
                    }
                    if (iPeekToken != 110) {
                        astNodeFunction = statement();
                        if (z5) {
                            String directive = getDirective(astNodeFunction);
                            if (directive == null) {
                                z5 = false;
                            } else if (directive.equals("use strict")) {
                                this.inUseStrictDirective = true;
                                functionNode.setInStrictMode(true);
                                if (!z4) {
                                    setRequiresActivation();
                                }
                            }
                        }
                    } else {
                        consumeToken();
                        astNodeFunction = function(1);
                    }
                    block.addStatement(astNodeFunction);
                }
            }
        } catch (ParserException unused) {
        } catch (Throwable th) {
            this.nestingOfFunction--;
            this.inUseStrictDirective = z4;
            throw th;
        }
        this.nestingOfFunction--;
        this.inUseStrictDirective = z4;
        int i4 = this.ts.tokenEnd;
        getAndResetJsDoc();
        if (!z2 && mustMatchToken(87, "msg.no.brace.after.body")) {
            i4 = this.ts.tokenEnd;
        }
        block.setLength(i4 - i3);
        return block;
    }

    private void parseFunctionParams(FunctionNode functionNode) throws ParserException, IOException {
        if (matchToken(89)) {
            functionNode.setRp(this.ts.tokenBeg - functionNode.getPosition());
            return;
        }
        HashSet hashSet = new HashSet();
        HashMap map = null;
        do {
            int iPeekToken = peekToken();
            if (iPeekToken == 84 || iPeekToken == 86) {
                AstNode astNodeDestructuringPrimaryExpr = destructuringPrimaryExpr();
                markDestructuring(astNodeDestructuringPrimaryExpr);
                functionNode.addParam(astNodeDestructuringPrimaryExpr);
                if (map == null) {
                    map = new HashMap();
                }
                String nextTempName = this.currentScriptOrFn.getNextTempName();
                defineSymbol(88, nextTempName, false);
                map.put(nextTempName, astNodeDestructuringPrimaryExpr);
            } else if (mustMatchToken(39, "msg.no.parm")) {
                AstNode astNodeCreateNameNode = createNameNode();
                Comment andResetJsDoc = getAndResetJsDoc();
                if (andResetJsDoc != null) {
                    astNodeCreateNameNode.setJsDocNode(andResetJsDoc);
                }
                functionNode.addParam(astNodeCreateNameNode);
                String string = this.ts.getString();
                defineSymbol(88, string);
                if (this.inUseStrictDirective) {
                    if ("eval".equals(string) || Constant.PARAM_SQL_ARGUMENTS.equals(string)) {
                        reportError("msg.bad.id.strict", string);
                    }
                    if (hashSet.contains(string)) {
                        addError("msg.dup.param.strict", string);
                    }
                    hashSet.add(string);
                }
            } else {
                functionNode.addParam(makeErrorNode());
            }
        } while (matchToken(90));
        if (map != null) {
            Node node = new Node(90);
            for (Map.Entry entry : map.entrySet()) {
                node.addChildToBack(createDestructuringAssignment(123, (Node) entry.getValue(), createName((String) entry.getKey())));
            }
            functionNode.putProp(23, node);
        }
        if (mustMatchToken(89, "msg.no.paren.after.parms")) {
            functionNode.setRp(this.ts.tokenBeg - functionNode.getPosition());
        }
    }

    private int peekFlaggedToken() throws IOException {
        peekToken();
        return this.currentFlaggedToken;
    }

    private int peekToken() throws IOException {
        if (this.currentFlaggedToken != 0) {
            return this.currentToken;
        }
        int lineno = this.ts.getLineno();
        int token = this.ts.getToken();
        boolean z2 = false;
        while (true) {
            if (token != 1 && token != 162) {
                break;
            }
            if (token == 1) {
                lineno++;
                z2 = true;
            } else if (this.compilerEnv.isRecordingComments()) {
                String andResetCurrentComment = this.ts.getAndResetCurrentComment();
                recordComment(lineno, andResetCurrentComment);
                lineno += getNumberOfEols(andResetCurrentComment);
            }
            token = this.ts.getToken();
        }
        this.currentToken = token;
        this.currentFlaggedToken = token | (z2 ? 65536 : 0);
        return token;
    }

    private int peekTokenOrEOL() throws IOException {
        int iPeekToken = peekToken();
        if ((this.currentFlaggedToken & 65536) != 0) {
            return 1;
        }
        return iPeekToken;
    }

    private ObjectProperty plainProperty(AstNode astNode, int i2) throws IOException {
        int iPeekToken = peekToken();
        if ((iPeekToken != 90 && iPeekToken != 87) || i2 != 39 || this.compilerEnv.getLanguageVersion() < 180) {
            mustMatchToken(104, "msg.no.colon.prop");
            ObjectProperty objectProperty = new ObjectProperty();
            objectProperty.setOperatorPosition(this.ts.tokenBeg);
            objectProperty.setLeftAndRight(astNode, assignExpr());
            return objectProperty;
        }
        if (!this.inDestructuringAssignment) {
            reportError("msg.bad.object.init");
        }
        Name name = new Name(astNode.getPosition(), astNode.getString());
        ObjectProperty objectProperty2 = new ObjectProperty();
        objectProperty2.putProp(26, Boolean.TRUE);
        objectProperty2.setLeftAndRight(astNode, name);
        return objectProperty2;
    }

    private AstNode primaryExpr() throws IOException {
        int iPeekFlaggedToken = peekFlaggedToken();
        int i2 = 65535 & iPeekFlaggedToken;
        if (i2 == -1) {
            consumeToken();
        } else {
            if (i2 != 0) {
                if (i2 != 24) {
                    if (i2 == 84) {
                        consumeToken();
                        return arrayLiteral();
                    }
                    if (i2 == 86) {
                        consumeToken();
                        return objectLiteral();
                    }
                    if (i2 == 88) {
                        consumeToken();
                        return parenExpr();
                    }
                    if (i2 != 101) {
                        if (i2 == 110) {
                            consumeToken();
                            return function(2);
                        }
                        if (i2 == 128) {
                            consumeToken();
                            reportError("msg.reserved.id", this.ts.getString());
                        } else {
                            if (i2 == 148) {
                                consumeToken();
                                mustHaveXML();
                                return attributeAccess();
                            }
                            if (i2 == 154) {
                                consumeToken();
                                return let(false, this.ts.tokenBeg);
                            }
                            switch (i2) {
                                case 39:
                                    consumeToken();
                                    return name(iPeekFlaggedToken, i2);
                                case 40:
                                    consumeToken();
                                    String string = this.ts.getString();
                                    if (this.inUseStrictDirective && this.ts.isNumberOldOctal()) {
                                        reportError("msg.no.old.octal.strict");
                                    }
                                    if (this.ts.isNumberBinary()) {
                                        string = "0b" + string;
                                    }
                                    if (this.ts.isNumberOldOctal()) {
                                        string = "0" + string;
                                    }
                                    if (this.ts.isNumberOctal()) {
                                        string = "0o" + string;
                                    }
                                    if (this.ts.isNumberHex()) {
                                        string = "0x" + string;
                                    }
                                    TokenStream tokenStream = this.ts;
                                    return new NumberLiteral(tokenStream.tokenBeg, string, tokenStream.getNumber());
                                case 41:
                                    consumeToken();
                                    return createStringLiteral();
                                case 42:
                                case 43:
                                case 44:
                                case 45:
                                    consumeToken();
                                    TokenStream tokenStream2 = this.ts;
                                    int i3 = tokenStream2.tokenBeg;
                                    return new KeywordLiteral(i3, tokenStream2.tokenEnd - i3, i2);
                                default:
                                    consumeToken();
                                    reportError("msg.syntax");
                                    break;
                            }
                        }
                    }
                }
                consumeToken();
                this.ts.readRegExp(i2);
                TokenStream tokenStream3 = this.ts;
                int i4 = tokenStream3.tokenBeg;
                RegExpLiteral regExpLiteral = new RegExpLiteral(i4, tokenStream3.tokenEnd - i4);
                regExpLiteral.setValue(this.ts.getString());
                regExpLiteral.setFlags(this.ts.readAndClearRegExpFlags());
                return regExpLiteral;
            }
            consumeToken();
            reportError("msg.unexpected.eof");
        }
        consumeToken();
        return makeErrorNode();
    }

    private AstNode propertyAccess(int i2, AstNode astNode) throws IOException, RuntimeException {
        int i3;
        AstNode astNodePropertyName;
        String strKeywordToName;
        if (astNode == null) {
            codeBug();
        }
        TokenStream tokenStream = this.ts;
        int i4 = tokenStream.lineno;
        int i5 = tokenStream.tokenBeg;
        consumeToken();
        if (i2 == 144) {
            mustHaveXML();
            i3 = 4;
        } else {
            i3 = 0;
        }
        if (!this.compilerEnv.isXmlAvailable()) {
            if (nextToken() != 39 && (!this.compilerEnv.isReservedKeywordAsIdentifier() || !TokenStream.isKeyword(this.ts.getString(), this.compilerEnv.getLanguageVersion(), this.inUseStrictDirective))) {
                reportError("msg.no.name.after.dot");
            }
            PropertyGet propertyGet = new PropertyGet(astNode, createNameNode(true, 33), i5);
            propertyGet.setLineno(i4);
            return propertyGet;
        }
        int iNextToken = nextToken();
        if (iNextToken == 23) {
            TokenStream tokenStream2 = this.ts;
            saveNameTokenData(tokenStream2.tokenBeg, ProxyConfig.MATCH_ALL_SCHEMES, tokenStream2.lineno);
            astNodePropertyName = propertyName(-1, ProxyConfig.MATCH_ALL_SCHEMES, i3);
        } else if (iNextToken == 39) {
            astNodePropertyName = propertyName(-1, this.ts.getString(), i3);
        } else if (iNextToken == 50) {
            TokenStream tokenStream3 = this.ts;
            saveNameTokenData(tokenStream3.tokenBeg, "throw", tokenStream3.lineno);
            astNodePropertyName = propertyName(-1, "throw", i3);
        } else if (iNextToken == 128) {
            String string = this.ts.getString();
            TokenStream tokenStream4 = this.ts;
            saveNameTokenData(tokenStream4.tokenBeg, string, tokenStream4.lineno);
            astNodePropertyName = propertyName(-1, string, i3);
        } else if (iNextToken == 148) {
            astNodePropertyName = attributeAccess();
        } else {
            if (!this.compilerEnv.isReservedKeywordAsIdentifier() || (strKeywordToName = Token.keywordToName(iNextToken)) == null) {
                reportError("msg.no.name.after.dot");
                return makeErrorNode();
            }
            TokenStream tokenStream5 = this.ts;
            saveNameTokenData(tokenStream5.tokenBeg, strKeywordToName, tokenStream5.lineno);
            astNodePropertyName = propertyName(-1, strKeywordToName, i3);
        }
        boolean z2 = astNodePropertyName instanceof XmlRef;
        InfixExpression xmlMemberGet = z2 ? new XmlMemberGet() : new PropertyGet();
        if (z2 && i2 == 109) {
            xmlMemberGet.setType(109);
        }
        int position = astNode.getPosition();
        xmlMemberGet.setPosition(position);
        xmlMemberGet.setLength(getNodeEnd(astNodePropertyName) - position);
        xmlMemberGet.setOperatorPosition(i5 - position);
        xmlMemberGet.setLineno(astNode.getLineno());
        xmlMemberGet.setLeft(astNode);
        xmlMemberGet.setRight(astNodePropertyName);
        return xmlMemberGet;
    }

    private AstNode propertyName(int i2, String str, int i3) throws IOException, RuntimeException {
        Name nameCreateNameNode;
        int i4;
        int i5 = i2 != -1 ? i2 : this.ts.tokenBeg;
        int i6 = this.ts.lineno;
        Name nameCreateNameNode2 = createNameNode(true, this.currentToken);
        if (matchToken(145)) {
            i4 = this.ts.tokenBeg;
            int iNextToken = nextToken();
            if (iNextToken == 23) {
                TokenStream tokenStream = this.ts;
                saveNameTokenData(tokenStream.tokenBeg, ProxyConfig.MATCH_ALL_SCHEMES, tokenStream.lineno);
                nameCreateNameNode = createNameNode(false, -1);
            } else {
                if (iNextToken != 39) {
                    if (iNextToken == 84) {
                        return xmlElemRef(i2, nameCreateNameNode2, i4);
                    }
                    reportError("msg.no.name.after.coloncolon");
                    return makeErrorNode();
                }
                nameCreateNameNode = createNameNode();
            }
        } else {
            nameCreateNameNode = nameCreateNameNode2;
            nameCreateNameNode2 = null;
            i4 = -1;
        }
        if (nameCreateNameNode2 == null && i3 == 0 && i2 == -1) {
            return nameCreateNameNode;
        }
        XmlPropRef xmlPropRef = new XmlPropRef(i5, getNodeEnd(nameCreateNameNode) - i5);
        xmlPropRef.setAtPos(i2);
        xmlPropRef.setNamespace(nameCreateNameNode2);
        xmlPropRef.setColonPos(i4);
        xmlPropRef.setPropName(nameCreateNameNode);
        xmlPropRef.setLineno(i6);
        return xmlPropRef;
    }

    private String readFully(Reader reader) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            char[] cArr = new char[1024];
            StringBuilder sb = new StringBuilder(1024);
            while (true) {
                int i2 = bufferedReader.read(cArr, 0, 1024);
                if (i2 == -1) {
                    String string = sb.toString();
                    bufferedReader.close();
                    return string;
                }
                sb.append(cArr, 0, i2);
            }
        } catch (Throwable th) {
            bufferedReader.close();
            throw th;
        }
    }

    private void recordComment(int i2, String str) {
        if (this.scannedComments == null) {
            this.scannedComments = new ArrayList();
        }
        TokenStream tokenStream = this.ts;
        Comment comment = new Comment(tokenStream.tokenBeg, tokenStream.getTokenLength(), this.ts.commentType, str);
        if (this.ts.commentType == Token.CommentType.JSDOC && this.compilerEnv.isRecordingLocalJsDocComments()) {
            this.currentJsDocComment = comment;
        }
        comment.setLineno(i2);
        this.scannedComments.add(comment);
    }

    private void recordLabel(Label label, LabeledStatement labeledStatement) throws IOException, RuntimeException {
        if (peekToken() != 104) {
            codeBug();
        }
        consumeToken();
        String name = label.getName();
        Map<String, LabeledStatement> map = this.labelSet;
        if (map == null) {
            this.labelSet = new HashMap();
        } else {
            LabeledStatement labeledStatement2 = map.get(name);
            if (labeledStatement2 != null) {
                if (this.compilerEnv.isIdeMode()) {
                    Label labelByName = labeledStatement2.getLabelByName(name);
                    reportError("msg.dup.label", labelByName.getAbsolutePosition(), labelByName.getLength());
                }
                reportError("msg.dup.label", label.getPosition(), label.getLength());
            }
        }
        labeledStatement.addLabel(label);
        this.labelSet.put(name, labeledStatement);
    }

    private AstNode relExpr() throws IOException {
        AstNode astNodeShiftExpr = shiftExpr();
        while (true) {
            int iPeekToken = peekToken();
            int i2 = this.ts.tokenBeg;
            if (iPeekToken != 52) {
                if (iPeekToken != 53) {
                    switch (iPeekToken) {
                    }
                } else {
                    continue;
                }
                consumeToken();
                astNodeShiftExpr = new InfixExpression(iPeekToken, astNodeShiftExpr, shiftExpr(), i2);
            } else if (!this.inForInit) {
                consumeToken();
                astNodeShiftExpr = new InfixExpression(iPeekToken, astNodeShiftExpr, shiftExpr(), i2);
            }
        }
        return astNodeShiftExpr;
    }

    private AstNode returnOrYield(int i2, boolean z2) throws IOException {
        int nodeEnd;
        AstNode astNodeExpr;
        AstNode yield;
        if (!insideFunction()) {
            reportError(i2 == 4 ? "msg.bad.return" : "msg.bad.yield");
        }
        consumeToken();
        TokenStream tokenStream = this.ts;
        int i3 = tokenStream.lineno;
        int i4 = tokenStream.tokenBeg;
        int i5 = tokenStream.tokenEnd;
        int iPeekTokenOrEOL = peekTokenOrEOL();
        if (iPeekTokenOrEOL == -1 || iPeekTokenOrEOL == 0 || iPeekTokenOrEOL == 1 || iPeekTokenOrEOL == 73 || iPeekTokenOrEOL == 83 || iPeekTokenOrEOL == 85 || iPeekTokenOrEOL == 87 || iPeekTokenOrEOL == 89) {
            nodeEnd = i5;
            astNodeExpr = null;
        } else {
            astNodeExpr = expr();
            nodeEnd = getNodeEnd(astNodeExpr);
        }
        int i6 = this.endFlags;
        if (i2 == 4) {
            this.endFlags = i6 | (astNodeExpr == null ? 2 : 4);
            int i7 = nodeEnd - i4;
            yield = new ReturnStatement(i4, i7, astNodeExpr);
            if (nowAllSet(i6, this.endFlags, 6)) {
                addStrictWarning("msg.return.inconsistent", "", i4, i7);
            }
        } else {
            if (!insideFunction()) {
                reportError("msg.bad.yield");
            }
            this.endFlags |= 8;
            yield = new Yield(i4, nodeEnd - i4, astNodeExpr);
            setRequiresActivation();
            setIsGenerator();
            if (!z2) {
                yield = new ExpressionStatement(yield);
            }
        }
        if (insideFunction() && nowAllSet(i6, this.endFlags, 12)) {
            Name functionName = ((FunctionNode) this.currentScriptOrFn).getFunctionName();
            if (functionName == null || functionName.length() == 0) {
                addError("msg.anon.generator.returns", "");
            } else {
                addError("msg.generator.returns", functionName.getIdentifier());
            }
        }
        yield.setLineno(i3);
        return yield;
    }

    private void saveNameTokenData(int i2, String str, int i3) {
        this.prevNameTokenStart = i2;
        this.prevNameTokenString = str;
        this.prevNameTokenLineno = i3;
    }

    private AstNode shiftExpr() throws IOException {
        AstNode astNodeAddExpr = addExpr();
        while (true) {
            int iPeekToken = peekToken();
            int i2 = this.ts.tokenBeg;
            switch (iPeekToken) {
                case 18:
                case 19:
                case 20:
                    consumeToken();
                    astNodeAddExpr = new InfixExpression(iPeekToken, astNodeAddExpr, addExpr(), i2);
                default:
                    return astNodeAddExpr;
            }
        }
    }

    private AstNode statement() throws IOException, RuntimeException {
        int iPeekTokenOrEOL;
        int i2 = this.ts.tokenBeg;
        try {
            AstNode astNodeStatementHelper = statementHelper();
            if (astNodeStatementHelper != null) {
                if (this.compilerEnv.isStrictMode() && !astNodeStatementHelper.hasSideEffects()) {
                    int position = astNodeStatementHelper.getPosition();
                    int iMax = Math.max(position, lineBeginningFor(position));
                    addStrictWarning(astNodeStatementHelper instanceof EmptyStatement ? "msg.extra.trailing.semi" : "msg.no.side.effects", "", iMax, nodeEnd(astNodeStatementHelper) - iMax);
                }
                return astNodeStatementHelper;
            }
        } catch (ParserException unused) {
        }
        do {
            iPeekTokenOrEOL = peekTokenOrEOL();
            consumeToken();
            if (iPeekTokenOrEOL == -1 || iPeekTokenOrEOL == 0 || iPeekTokenOrEOL == 1) {
                break;
            }
        } while (iPeekTokenOrEOL != 83);
        return new EmptyStatement(i2, this.ts.tokenBeg - i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0113  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private org.mozilla.javascript.ast.AstNode statementHelper() throws java.io.IOException, java.lang.RuntimeException {
        /*
            Method dump skipped, instructions count: 312
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.Parser.statementHelper():org.mozilla.javascript.ast.AstNode");
    }

    private AstNode statements(AstNode astNode) throws IOException, RuntimeException {
        if (this.currentToken != 86 && !this.compilerEnv.isIdeMode()) {
            codeBug();
        }
        int i2 = this.ts.tokenBeg;
        if (astNode == null) {
            astNode = new Block(i2);
        }
        astNode.setLineno(this.ts.lineno);
        while (true) {
            int iPeekToken = peekToken();
            if (iPeekToken <= 0 || iPeekToken == 87) {
                break;
            }
            astNode.addChild(statement());
        }
        astNode.setLength(this.ts.tokenBeg - i2);
        return astNode;
    }

    private SwitchStatement switchStatement() throws IOException, RuntimeException {
        AstNode astNodeExpr;
        if (this.currentToken != 115) {
            codeBug();
        }
        consumeToken();
        int i2 = this.ts.tokenBeg;
        SwitchStatement switchStatement = new SwitchStatement(i2);
        if (mustMatchToken(88, "msg.no.paren.switch")) {
            switchStatement.setLp(this.ts.tokenBeg - i2);
        }
        switchStatement.setLineno(this.ts.lineno);
        switchStatement.setExpression(expr());
        enterSwitch(switchStatement);
        try {
            if (mustMatchToken(89, "msg.no.paren.after.switch")) {
                switchStatement.setRp(this.ts.tokenBeg - i2);
            }
            mustMatchToken(86, "msg.no.brace.switch");
            boolean z2 = false;
            while (true) {
                int iNextToken = nextToken();
                TokenStream tokenStream = this.ts;
                int i3 = tokenStream.tokenBeg;
                int i4 = tokenStream.lineno;
                if (iNextToken == 87) {
                    switchStatement.setLength(tokenStream.tokenEnd - i2);
                    break;
                }
                if (iNextToken == 116) {
                    astNodeExpr = expr();
                    mustMatchToken(104, "msg.no.colon.case");
                } else {
                    if (iNextToken != 117) {
                        reportError("msg.bad.switch");
                        break;
                    }
                    if (z2) {
                        reportError("msg.double.switch.default");
                    }
                    mustMatchToken(104, "msg.no.colon.case");
                    z2 = true;
                    astNodeExpr = null;
                }
                SwitchCase switchCase = new SwitchCase(i3);
                switchCase.setExpression(astNodeExpr);
                switchCase.setLength(this.ts.tokenEnd - i2);
                switchCase.setLineno(i4);
                while (true) {
                    int iPeekToken = peekToken();
                    if (iPeekToken == 87 || iPeekToken == 116 || iPeekToken == 117 || iPeekToken == 0) {
                        break;
                    }
                    switchCase.addStatement(statement());
                }
                switchStatement.addCase(switchCase);
            }
            exitSwitch();
            return switchStatement;
        } catch (Throwable th) {
            exitSwitch();
            throw th;
        }
    }

    private ThrowStatement throwStatement() throws IOException, RuntimeException {
        if (this.currentToken != 50) {
            codeBug();
        }
        consumeToken();
        TokenStream tokenStream = this.ts;
        int i2 = tokenStream.tokenBeg;
        int i3 = tokenStream.lineno;
        if (peekTokenOrEOL() == 1) {
            reportError("msg.bad.throw.eol");
        }
        AstNode astNodeExpr = expr();
        ThrowStatement throwStatement = new ThrowStatement(i2, getNodeEnd(astNodeExpr), astNodeExpr);
        throwStatement.setLineno(i3);
        return throwStatement;
    }

    private TryStatement tryStatement() throws IOException, RuntimeException {
        int i2;
        ArrayList arrayList;
        int i3;
        AstNode astNode;
        int i4;
        AstNode astNodeExpr;
        if (this.currentToken != 82) {
            codeBug();
        }
        consumeToken();
        Comment andResetJsDoc = getAndResetJsDoc();
        TokenStream tokenStream = this.ts;
        int i5 = tokenStream.tokenBeg;
        int i6 = tokenStream.lineno;
        int i7 = 86;
        if (peekToken() != 86) {
            reportError("msg.no.brace.try");
        }
        AstNode astNodeStatement = statement();
        int nodeEnd = getNodeEnd(astNodeStatement);
        int iPeekToken = peekToken();
        if (iPeekToken == 125) {
            boolean z2 = false;
            arrayList = null;
            for (int i8 = 125; matchToken(i8); i8 = 125) {
                int i9 = this.ts.lineno;
                if (z2) {
                    reportError("msg.catch.unreachable");
                }
                int i10 = this.ts.tokenBeg;
                int i11 = mustMatchToken(88, "msg.no.paren.catch") ? this.ts.tokenBeg : -1;
                mustMatchToken(39, "msg.bad.catchcond");
                Name nameCreateNameNode = createNameNode();
                Comment andResetJsDoc2 = getAndResetJsDoc();
                if (andResetJsDoc2 != null) {
                    nameCreateNameNode.setJsDocNode(andResetJsDoc2);
                }
                String identifier = nameCreateNameNode.getIdentifier();
                if (this.inUseStrictDirective && ("eval".equals(identifier) || Constant.PARAM_SQL_ARGUMENTS.equals(identifier))) {
                    reportError("msg.bad.id.strict", identifier);
                }
                if (matchToken(113)) {
                    i4 = this.ts.tokenBeg;
                    astNodeExpr = expr();
                } else {
                    z2 = true;
                    i4 = -1;
                    astNodeExpr = null;
                }
                int i12 = mustMatchToken(89, "msg.bad.catchcond") ? this.ts.tokenBeg : -1;
                mustMatchToken(i7, "msg.no.brace.catchblock");
                Block block = (Block) statements();
                int nodeEnd2 = getNodeEnd(block);
                CatchClause catchClause = new CatchClause(i10);
                catchClause.setVarName(nameCreateNameNode);
                catchClause.setCatchCondition(astNodeExpr);
                catchClause.setBody(block);
                if (i4 != -1) {
                    catchClause.setIfPosition(i4 - i10);
                }
                catchClause.setParens(i11, i12);
                catchClause.setLineno(i9);
                nodeEnd = mustMatchToken(87, "msg.no.brace.after.body") ? this.ts.tokenEnd : nodeEnd2;
                catchClause.setLength(nodeEnd - i10);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(catchClause);
                i7 = 86;
            }
            i2 = 126;
        } else {
            i2 = 126;
            if (iPeekToken != 126) {
                mustMatchToken(126, "msg.try.no.catchfinally");
            }
            arrayList = null;
        }
        if (matchToken(i2)) {
            int i13 = this.ts.tokenBeg;
            AstNode astNodeStatement2 = statement();
            nodeEnd = getNodeEnd(astNodeStatement2);
            astNode = astNodeStatement2;
            i3 = i13;
        } else {
            i3 = -1;
            astNode = null;
        }
        TryStatement tryStatement = new TryStatement(i5, nodeEnd - i5);
        tryStatement.setTryBlock(astNodeStatement);
        tryStatement.setCatchClauses(arrayList);
        tryStatement.setFinallyBlock(astNode);
        if (i3 != -1) {
            tryStatement.setFinallyPosition(i3 - i5);
        }
        tryStatement.setLineno(i6);
        if (andResetJsDoc != null) {
            tryStatement.setJsDocNode(andResetJsDoc);
        }
        return tryStatement;
    }

    private AstNode unaryExpr() throws IOException, RuntimeException {
        int iPeekToken = peekToken();
        int i2 = this.ts.lineno;
        if (iPeekToken == -1) {
            consumeToken();
            return makeErrorNode();
        }
        if (iPeekToken != 14) {
            if (iPeekToken != 127) {
                if (iPeekToken == 21) {
                    consumeToken();
                    UnaryExpression unaryExpression = new UnaryExpression(28, this.ts.tokenBeg, unaryExpr());
                    unaryExpression.setLineno(i2);
                    return unaryExpression;
                }
                if (iPeekToken == 22) {
                    consumeToken();
                    UnaryExpression unaryExpression2 = new UnaryExpression(29, this.ts.tokenBeg, unaryExpr());
                    unaryExpression2.setLineno(i2);
                    return unaryExpression2;
                }
                if (iPeekToken != 26 && iPeekToken != 27) {
                    if (iPeekToken == 31) {
                        consumeToken();
                        UnaryExpression unaryExpression3 = new UnaryExpression(iPeekToken, this.ts.tokenBeg, unaryExpr());
                        unaryExpression3.setLineno(i2);
                        return unaryExpression3;
                    }
                    if (iPeekToken != 32) {
                        if (iPeekToken == 107 || iPeekToken == 108) {
                            consumeToken();
                            UnaryExpression unaryExpression4 = new UnaryExpression(iPeekToken, this.ts.tokenBeg, memberExpr(true));
                            unaryExpression4.setLineno(i2);
                            checkBadIncDec(unaryExpression4);
                            return unaryExpression4;
                        }
                    }
                }
            }
            consumeToken();
            UnaryExpression unaryExpression5 = new UnaryExpression(iPeekToken, this.ts.tokenBeg, unaryExpr());
            unaryExpression5.setLineno(i2);
            return unaryExpression5;
        }
        if (this.compilerEnv.isXmlAvailable()) {
            consumeToken();
            return memberExprTail(true, xmlInitializer());
        }
        AstNode astNodeMemberExpr = memberExpr(true);
        int iPeekTokenOrEOL = peekTokenOrEOL();
        if (iPeekTokenOrEOL != 107 && iPeekTokenOrEOL != 108) {
            return astNodeMemberExpr;
        }
        consumeToken();
        UnaryExpression unaryExpression6 = new UnaryExpression(iPeekTokenOrEOL, this.ts.tokenBeg, astNodeMemberExpr, true);
        unaryExpression6.setLineno(i2);
        checkBadIncDec(unaryExpression6);
        return unaryExpression6;
    }

    private VariableDeclaration variables(int i2, int i3, boolean z2) throws ParserException, IOException {
        AstNode astNodeDestructuringPrimaryExpr;
        int nodeEnd;
        Name name;
        VariableDeclaration variableDeclaration = new VariableDeclaration(i3);
        variableDeclaration.setType(i2);
        variableDeclaration.setLineno(this.ts.lineno);
        Comment andResetJsDoc = getAndResetJsDoc();
        if (andResetJsDoc != null) {
            variableDeclaration.setJsDocNode(andResetJsDoc);
        }
        do {
            int iPeekToken = peekToken();
            TokenStream tokenStream = this.ts;
            int i4 = tokenStream.tokenBeg;
            int i5 = tokenStream.tokenEnd;
            AstNode astNodeAssignExpr = null;
            if (iPeekToken == 84 || iPeekToken == 86) {
                astNodeDestructuringPrimaryExpr = destructuringPrimaryExpr();
                int nodeEnd2 = getNodeEnd(astNodeDestructuringPrimaryExpr);
                if (!(astNodeDestructuringPrimaryExpr instanceof DestructuringForm)) {
                    reportError("msg.bad.assign.left", i4, nodeEnd2 - i4);
                }
                markDestructuring(astNodeDestructuringPrimaryExpr);
                nodeEnd = nodeEnd2;
                name = null;
            } else {
                mustMatchToken(39, "msg.bad.var");
                Name nameCreateNameNode = createNameNode();
                nameCreateNameNode.setLineno(this.ts.getLineno());
                if (this.inUseStrictDirective) {
                    String string = this.ts.getString();
                    if ("eval".equals(string) || Constant.PARAM_SQL_ARGUMENTS.equals(this.ts.getString())) {
                        reportError("msg.bad.id.strict", string);
                    }
                }
                defineSymbol(i2, this.ts.getString(), this.inForInit);
                nodeEnd = i5;
                name = nameCreateNameNode;
                astNodeDestructuringPrimaryExpr = null;
            }
            int i6 = this.ts.lineno;
            Comment andResetJsDoc2 = getAndResetJsDoc();
            if (matchToken(91)) {
                astNodeAssignExpr = assignExpr();
                nodeEnd = getNodeEnd(astNodeAssignExpr);
            }
            VariableInitializer variableInitializer = new VariableInitializer(i4, nodeEnd - i4);
            if (astNodeDestructuringPrimaryExpr != null) {
                if (astNodeAssignExpr == null && !this.inForInit) {
                    reportError("msg.destruct.assign.no.init");
                }
                variableInitializer.setTarget(astNodeDestructuringPrimaryExpr);
            } else {
                variableInitializer.setTarget(name);
            }
            variableInitializer.setInitializer(astNodeAssignExpr);
            variableInitializer.setType(i2);
            variableInitializer.setJsDocNode(andResetJsDoc2);
            variableInitializer.setLineno(i6);
            variableDeclaration.addVariable(variableInitializer);
        } while (matchToken(90));
        variableDeclaration.setLength(nodeEnd - i3);
        variableDeclaration.setIsStatement(z2);
        return variableDeclaration;
    }

    private void warnMissingSemi(int i2, int i3) {
        if (this.compilerEnv.isStrictMode()) {
            int[] iArr = new int[2];
            String line = this.ts.getLine(i3, iArr);
            if (this.compilerEnv.isIdeMode()) {
                i2 = Math.max(i2, i3 - iArr[1]);
            }
            int i4 = i2;
            if (line != null) {
                addStrictWarning("msg.missing.semi", "", i4, i3 - i4, iArr[0], line, iArr[1]);
            } else {
                addStrictWarning("msg.missing.semi", "", i4, i3 - i4);
            }
        }
    }

    private void warnTrailingComma(int i2, List<?> list, int i3) {
        if (this.compilerEnv.getWarnTrailingComma()) {
            if (!list.isEmpty()) {
                i2 = ((AstNode) list.get(0)).getPosition();
            }
            int iMax = Math.max(i2, lineBeginningFor(i3));
            addWarning("msg.extra.trailing.comma", iMax, i3 - iMax);
        }
    }

    private WhileLoop whileLoop() throws IOException, RuntimeException {
        if (this.currentToken != 118) {
            codeBug();
        }
        consumeToken();
        int i2 = this.ts.tokenBeg;
        WhileLoop whileLoop = new WhileLoop(i2);
        whileLoop.setLineno(this.ts.lineno);
        enterLoop(whileLoop);
        try {
            ConditionData conditionDataCondition = condition();
            whileLoop.setCondition(conditionDataCondition.condition);
            whileLoop.setParens(conditionDataCondition.lp - i2, conditionDataCondition.rp - i2);
            AstNode astNodeStatement = statement();
            whileLoop.setLength(getNodeEnd(astNodeStatement) - i2);
            whileLoop.setBody(astNodeStatement);
            return whileLoop;
        } finally {
            exitLoop();
        }
    }

    private WithStatement withStatement() throws IOException, RuntimeException {
        if (this.currentToken != 124) {
            codeBug();
        }
        consumeToken();
        Comment andResetJsDoc = getAndResetJsDoc();
        TokenStream tokenStream = this.ts;
        int i2 = tokenStream.lineno;
        int i3 = tokenStream.tokenBeg;
        int i4 = mustMatchToken(88, "msg.no.paren.with") ? this.ts.tokenBeg : -1;
        AstNode astNodeExpr = expr();
        int i5 = mustMatchToken(89, "msg.no.paren.after.with") ? this.ts.tokenBeg : -1;
        AstNode astNodeStatement = statement();
        WithStatement withStatement = new WithStatement(i3, getNodeEnd(astNodeStatement) - i3);
        withStatement.setJsDocNode(andResetJsDoc);
        withStatement.setExpression(astNodeExpr);
        withStatement.setStatement(astNodeStatement);
        withStatement.setParens(i4, i5);
        withStatement.setLineno(i2);
        return withStatement;
    }

    private XmlElemRef xmlElemRef(int i2, Name name, int i3) throws IOException {
        int i4 = this.ts.tokenBeg;
        int i5 = -1;
        int i6 = i2 != -1 ? i2 : i4;
        AstNode astNodeExpr = expr();
        int nodeEnd = getNodeEnd(astNodeExpr);
        if (mustMatchToken(85, "msg.no.bracket.index")) {
            TokenStream tokenStream = this.ts;
            int i7 = tokenStream.tokenBeg;
            nodeEnd = tokenStream.tokenEnd;
            i5 = i7;
        }
        XmlElemRef xmlElemRef = new XmlElemRef(i6, nodeEnd - i6);
        xmlElemRef.setNamespace(name);
        xmlElemRef.setColonPos(i3);
        xmlElemRef.setAtPos(i2);
        xmlElemRef.setExpression(astNodeExpr);
        xmlElemRef.setBrackets(i4, i5);
        return xmlElemRef;
    }

    private AstNode xmlInitializer() throws IOException, RuntimeException {
        if (this.currentToken != 14) {
            codeBug();
        }
        TokenStream tokenStream = this.ts;
        int i2 = tokenStream.tokenBeg;
        int firstXMLToken = tokenStream.getFirstXMLToken();
        if (firstXMLToken != 146 && firstXMLToken != 149) {
            reportError("msg.syntax");
            return makeErrorNode();
        }
        XmlLiteral xmlLiteral = new XmlLiteral(i2);
        xmlLiteral.setLineno(this.ts.lineno);
        while (firstXMLToken == 146) {
            TokenStream tokenStream2 = this.ts;
            xmlLiteral.addFragment(new XmlString(tokenStream2.tokenBeg, tokenStream2.getString()));
            mustMatchToken(86, "msg.syntax");
            int i3 = this.ts.tokenBeg;
            AstNode emptyExpression = peekToken() == 87 ? new EmptyExpression(i3, this.ts.tokenEnd - i3) : expr();
            mustMatchToken(87, "msg.syntax");
            XmlExpression xmlExpression = new XmlExpression(i3, emptyExpression);
            xmlExpression.setIsXmlAttribute(this.ts.isXMLAttribute());
            xmlExpression.setLength(this.ts.tokenEnd - i3);
            xmlLiteral.addFragment(xmlExpression);
            firstXMLToken = this.ts.getNextXMLToken();
        }
        if (firstXMLToken != 149) {
            reportError("msg.syntax");
            return makeErrorNode();
        }
        TokenStream tokenStream3 = this.ts;
        xmlLiteral.addFragment(new XmlString(tokenStream3.tokenBeg, tokenStream3.getString()));
        return xmlLiteral;
    }

    void addError(String str) {
        TokenStream tokenStream = this.ts;
        int i2 = tokenStream.tokenBeg;
        addError(str, i2, tokenStream.tokenEnd - i2);
    }

    void addStrictWarning(String str, String str2) {
        int i2;
        int i3;
        TokenStream tokenStream = this.ts;
        if (tokenStream != null) {
            i2 = tokenStream.tokenBeg;
            i3 = tokenStream.tokenEnd - i2;
        } else {
            i2 = -1;
            i3 = -1;
        }
        addStrictWarning(str, str2, i2, i3);
    }

    void addWarning(String str, String str2) {
        int i2;
        int i3;
        TokenStream tokenStream = this.ts;
        if (tokenStream != null) {
            i2 = tokenStream.tokenBeg;
            i3 = tokenStream.tokenEnd - i2;
        } else {
            i2 = -1;
            i3 = -1;
        }
        addWarning(str, str2, i2, i3);
    }

    protected void checkActivationName(String str, int i2) {
        if (insideFunction()) {
            if ((!Constant.PARAM_SQL_ARGUMENTS.equals(str) || ((FunctionNode) this.currentScriptOrFn).getFunctionType() == 4) && ((this.compilerEnv.getActivationNames() == null || !this.compilerEnv.getActivationNames().contains(str)) && !(SessionDescription.ATTR_LENGTH.equals(str) && i2 == 33 && this.compilerEnv.getLanguageVersion() == 120))) {
                return;
            }
            setRequiresActivation();
        }
    }

    protected void checkMutableReference(Node node) {
        if ((node.getIntProp(16, 0) & 4) != 0) {
            reportError("msg.bad.assign.left");
        }
    }

    Node createDestructuringAssignment(int i2, Node node, Node node2) {
        String nextTempName = this.currentScriptOrFn.getNextTempName();
        Node nodeDestructuringAssignmentHelper = destructuringAssignmentHelper(i2, node, node2, nextTempName);
        nodeDestructuringAssignmentHelper.getLastChild().addChildToBack(createName(nextTempName));
        return nodeDestructuringAssignmentHelper;
    }

    protected Node createName(String str) {
        checkActivationName(str, 39);
        return Node.newString(39, str);
    }

    protected Node createNumber(double d2) {
        return Node.newNumber(d2);
    }

    protected Scope createScopeNode(int i2, int i3) {
        Scope scope = new Scope();
        scope.setType(i2);
        scope.setLineno(i3);
        return scope;
    }

    void defineSymbol(int i2, String str) {
        defineSymbol(i2, str, false);
    }

    boolean destructuringArray(ArrayLiteral arrayLiteral, int i2, String str, Node node, List<String> list) {
        int i3 = i2 == 155 ? 156 : 8;
        int i4 = 0;
        boolean z2 = true;
        for (AstNode astNode : arrayLiteral.getElements()) {
            if (astNode.getType() == 129) {
                i4++;
            } else {
                Node node2 = new Node(36, createName(str), createNumber(i4));
                if (astNode.getType() == 39) {
                    String string = astNode.getString();
                    node.addChildToBack(new Node(i3, createName(49, string, null), node2));
                    if (i2 != -1) {
                        defineSymbol(i2, string, true);
                        list.add(string);
                    }
                } else {
                    node.addChildToBack(destructuringAssignmentHelper(i2, astNode, node2, this.currentScriptOrFn.getNextTempName()));
                }
                i4++;
                z2 = false;
            }
        }
        return z2;
    }

    Node destructuringAssignmentHelper(int i2, Node node, Node node2, String str) {
        Scope scopeCreateScopeNode = createScopeNode(159, node.getLineno());
        scopeCreateScopeNode.addChildToFront(new Node(154, createName(39, str, node2)));
        try {
            pushScope(scopeCreateScopeNode);
            boolean zDestructuringArray = true;
            defineSymbol(154, str, true);
            popScope();
            Node node3 = new Node(90);
            scopeCreateScopeNode.addChildToBack(node3);
            List<String> arrayList = new ArrayList<>();
            int type = node.getType();
            if (type == 33 || type == 36) {
                if (i2 == 123 || i2 == 154 || i2 == 155) {
                    reportError("msg.bad.assign.left");
                }
                node3.addChildToBack(simpleAssignment(node, createName(str)));
            } else if (type == 66) {
                zDestructuringArray = destructuringArray((ArrayLiteral) node, i2, str, node3, arrayList);
            } else if (type != 67) {
                reportError("msg.bad.assign.left");
            } else {
                zDestructuringArray = destructuringObject((ObjectLiteral) node, i2, str, node3, arrayList);
            }
            if (zDestructuringArray) {
                node3.addChildToBack(createNumber(0.0d));
            }
            scopeCreateScopeNode.putProp(22, arrayList);
            return scopeCreateScopeNode;
        } catch (Throwable th) {
            popScope();
            throw th;
        }
    }

    boolean destructuringObject(ObjectLiteral objectLiteral, int i2, String str, Node node, List<String> list) {
        Node node2;
        int i3 = i2 == 155 ? 156 : 8;
        boolean z2 = true;
        for (ObjectProperty objectProperty : objectLiteral.getElements()) {
            TokenStream tokenStream = this.ts;
            int i4 = tokenStream != null ? tokenStream.lineno : 0;
            AstNode left = objectProperty.getLeft();
            if (left instanceof Name) {
                node2 = new Node(33, createName(str), Node.newString(((Name) left).getIdentifier()));
            } else if (left instanceof StringLiteral) {
                node2 = new Node(33, createName(str), Node.newString(((StringLiteral) left).getValue()));
            } else {
                if (!(left instanceof NumberLiteral)) {
                    throw codeBug();
                }
                node2 = new Node(36, createName(str), createNumber((int) ((NumberLiteral) left).getNumber()));
            }
            node2.setLineno(i4);
            AstNode right = objectProperty.getRight();
            if (right.getType() == 39) {
                String identifier = ((Name) right).getIdentifier();
                node.addChildToBack(new Node(i3, createName(49, identifier, null), node2));
                if (i2 != -1) {
                    defineSymbol(i2, identifier, true);
                    list.add(identifier);
                }
            } else {
                node.addChildToBack(destructuringAssignmentHelper(i2, right, node2, this.currentScriptOrFn.getNextTempName()));
            }
            z2 = false;
        }
        return z2;
    }

    public boolean eof() {
        return this.ts.eof();
    }

    public boolean inUseStrictDirective() {
        return this.inUseStrictDirective;
    }

    boolean insideFunction() {
        return this.nestingOfFunction != 0;
    }

    String lookupMessage(String str) {
        return lookupMessage(str, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    void markDestructuring(AstNode astNode) {
        if (astNode instanceof DestructuringForm) {
            ((DestructuringForm) astNode).setIsDestructuring(true);
        } else if (astNode instanceof ParenthesizedExpression) {
            markDestructuring(((ParenthesizedExpression) astNode).getExpression());
        }
    }

    public AstRoot parse(String str, String str2, int i2) {
        if (this.parseFinished) {
            throw new IllegalStateException("parser reused");
        }
        this.sourceURI = str2;
        if (this.compilerEnv.isIdeMode()) {
            this.sourceChars = str.toCharArray();
        }
        this.ts = new TokenStream(this, null, str, i2);
        try {
            try {
                return parse();
            } catch (IOException unused) {
                throw new IllegalStateException();
            }
        } finally {
            this.parseFinished = true;
        }
    }

    void popScope() {
        this.currentScope = this.currentScope.getParentScope();
    }

    void pushScope(Scope scope) {
        Scope parentScope = scope.getParentScope();
        if (parentScope == null) {
            this.currentScope.addChildScope(scope);
        } else if (parentScope != this.currentScope) {
            codeBug();
        }
        this.currentScope = scope;
    }

    protected AstNode removeParens(AstNode astNode) {
        while (astNode instanceof ParenthesizedExpression) {
            astNode = ((ParenthesizedExpression) astNode).getExpression();
        }
        return astNode;
    }

    void reportError(String str) {
        reportError(str, null);
    }

    public void setDefaultUseStrictDirective(boolean z2) {
        this.defaultUseStrictDirective = z2;
    }

    protected void setIsGenerator() {
        if (insideFunction()) {
            ((FunctionNode) this.currentScriptOrFn).setIsGenerator();
        }
    }

    protected void setRequiresActivation() {
        if (insideFunction()) {
            ((FunctionNode) this.currentScriptOrFn).setRequiresActivation();
        }
    }

    protected Node simpleAssignment(Node node, Node node2) {
        Node firstChild;
        Node lastChild;
        int i2;
        int type = node.getType();
        if (type != 33 && type != 36) {
            if (type != 39) {
                if (type != 68) {
                    throw codeBug();
                }
                Node firstChild2 = node.getFirstChild();
                checkMutableReference(firstChild2);
                return new Node(69, firstChild2, node2);
            }
            String identifier = ((Name) node).getIdentifier();
            if (this.inUseStrictDirective && ("eval".equals(identifier) || Constant.PARAM_SQL_ARGUMENTS.equals(identifier))) {
                reportError("msg.bad.id.strict", identifier);
            }
            node.setType(49);
            return new Node(8, node, node2);
        }
        if (node instanceof PropertyGet) {
            PropertyGet propertyGet = (PropertyGet) node;
            firstChild = propertyGet.getTarget();
            lastChild = propertyGet.getProperty();
        } else if (node instanceof ElementGet) {
            ElementGet elementGet = (ElementGet) node;
            firstChild = elementGet.getTarget();
            lastChild = elementGet.getElement();
        } else {
            firstChild = node.getFirstChild();
            lastChild = node.getLastChild();
        }
        if (type == 33) {
            lastChild.setType(41);
            i2 = 35;
        } else {
            i2 = 37;
        }
        return new Node(i2, firstChild, lastChild, node2);
    }

    public Parser(CompilerEnvirons compilerEnvirons) {
        this(compilerEnvirons, compilerEnvirons.getErrorReporter());
    }

    private Name createNameNode(boolean z2, int i2) throws RuntimeException {
        TokenStream tokenStream = this.ts;
        int i3 = tokenStream.tokenBeg;
        String string = tokenStream.getString();
        int i4 = this.ts.lineno;
        String str = "";
        if (!"".equals(this.prevNameTokenString)) {
            i3 = this.prevNameTokenStart;
            string = this.prevNameTokenString;
            i4 = this.prevNameTokenLineno;
            this.prevNameTokenStart = 0;
            this.prevNameTokenString = "";
            this.prevNameTokenLineno = 0;
        }
        if (string != null) {
            str = string;
        } else if (!this.compilerEnv.isIdeMode()) {
            codeBug();
            str = string;
        }
        Name name = new Name(i3, str);
        name.setLineno(i4);
        if (z2) {
            checkActivationName(str, i2);
        }
        return name;
    }

    private AstNode generatorExpression(AstNode astNode, int i2, boolean z2) throws IOException {
        int i3;
        ConditionData conditionDataCondition;
        ArrayList arrayList = new ArrayList();
        while (peekToken() == 120) {
            arrayList.add(generatorExpressionLoop());
        }
        if (peekToken() == 113) {
            consumeToken();
            i3 = this.ts.tokenBeg - i2;
            conditionDataCondition = condition();
        } else {
            i3 = -1;
            conditionDataCondition = null;
        }
        if (!z2) {
            mustMatchToken(89, "msg.no.paren.let");
        }
        GeneratorExpression generatorExpression = new GeneratorExpression(i2, this.ts.tokenEnd - i2);
        generatorExpression.setResult(astNode);
        generatorExpression.setLoops(arrayList);
        if (conditionDataCondition != null) {
            generatorExpression.setIfPosition(i3);
            generatorExpression.setFilter(conditionDataCondition.condition);
            generatorExpression.setFilterLp(conditionDataCondition.lp - i2);
            generatorExpression.setFilterRp(conditionDataCondition.rp - i2);
        }
        return generatorExpression;
    }

    private boolean mustMatchToken(int i2, String str, int i3, int i4) throws IOException {
        if (matchToken(i2)) {
            return true;
        }
        reportError(str, i3, i4);
        return false;
    }

    void addError(String str, int i2, int i3) {
        addError(str, null, i2, i3);
    }

    void defineSymbol(int i2, String str, boolean z2) {
        if (str == null) {
            if (this.compilerEnv.isIdeMode()) {
                return;
            } else {
                codeBug();
            }
        }
        Scope definingScope = this.currentScope.getDefiningScope(str);
        org.mozilla.javascript.ast.Symbol symbol = definingScope != null ? definingScope.getSymbol(str) : null;
        int declType = symbol != null ? symbol.getDeclType() : -1;
        String str2 = "msg.var.redecl";
        if (symbol != null && (declType == 155 || i2 == 155 || (definingScope == this.currentScope && declType == 154))) {
            if (declType == 155) {
                str2 = "msg.const.redecl";
            } else if (declType == 154) {
                str2 = "msg.let.redecl";
            } else if (declType != 123) {
                str2 = declType == 110 ? "msg.fn.redecl" : "msg.parm.redecl";
            }
            addError(str2, str);
            return;
        }
        if (i2 == 88) {
            if (symbol != null) {
                addWarning("msg.dup.parms", str);
            }
            this.currentScriptOrFn.putSymbol(new org.mozilla.javascript.ast.Symbol(i2, str));
            return;
        }
        if (i2 != 110 && i2 != 123) {
            if (i2 == 154) {
                if (z2 || !(this.currentScope.getType() == 113 || (this.currentScope instanceof Loop))) {
                    this.currentScope.putSymbol(new org.mozilla.javascript.ast.Symbol(i2, str));
                    return;
                } else {
                    addError("msg.let.decl.not.in.block");
                    return;
                }
            }
            if (i2 != 155) {
                throw codeBug();
            }
        }
        if (symbol == null) {
            this.currentScriptOrFn.putSymbol(new org.mozilla.javascript.ast.Symbol(i2, str));
        } else if (declType == 123) {
            addStrictWarning("msg.var.redecl", str);
        } else if (declType == 88) {
            addStrictWarning("msg.var.hides.arg", str);
        }
    }

    String lookupMessage(String str, String str2) {
        return str2 == null ? ScriptRuntime.getMessage0(str) : ScriptRuntime.getMessage1(str, str2);
    }

    void reportError(String str, String str2) {
        TokenStream tokenStream = this.ts;
        if (tokenStream == null) {
            reportError(str, str2, 1, 1);
        } else {
            int i2 = tokenStream.tokenBeg;
            reportError(str, str2, i2, tokenStream.tokenEnd - i2);
        }
    }

    public Parser(CompilerEnvirons compilerEnvirons, ErrorReporter errorReporter) {
        this.currentFlaggedToken = 0;
        this.prevNameTokenString = "";
        this.compilerEnv = compilerEnvirons;
        this.errorReporter = errorReporter;
        if (errorReporter instanceof IdeErrorReporter) {
            this.errorCollector = (IdeErrorReporter) errorReporter;
        }
    }

    void addError(String str, String str2) {
        TokenStream tokenStream = this.ts;
        int i2 = tokenStream.tokenBeg;
        addError(str, str2, i2, tokenStream.tokenEnd - i2);
    }

    protected Node createName(int i2, String str, Node node) {
        Node nodeCreateName = createName(str);
        nodeCreateName.setType(i2);
        if (node != null) {
            nodeCreateName.addChildToBack(node);
        }
        return nodeCreateName;
    }

    void addError(String str, int i2) {
        String string = Character.toString((char) i2);
        TokenStream tokenStream = this.ts;
        int i3 = tokenStream.tokenBeg;
        addError(str, string, i3, tokenStream.tokenEnd - i3);
    }

    void addStrictWarning(String str, String str2, int i2, int i3) {
        if (this.compilerEnv.isStrictMode()) {
            addWarning(str, str2, i2, i3);
        }
    }

    void addWarning(String str, int i2, int i3) {
        addWarning(str, null, i2, i3);
    }

    void reportError(String str, int i2, int i3) {
        reportError(str, null, i2, i3);
    }

    void addError(String str, String str2, int i2, int i3) {
        String line;
        int i4;
        int offset;
        this.syntaxErrorCount++;
        String strLookupMessage = lookupMessage(str, str2);
        IdeErrorReporter ideErrorReporter = this.errorCollector;
        if (ideErrorReporter != null) {
            ideErrorReporter.error(strLookupMessage, this.sourceURI, i2, i3);
            return;
        }
        TokenStream tokenStream = this.ts;
        if (tokenStream != null) {
            int lineno = tokenStream.getLineno();
            line = this.ts.getLine();
            offset = this.ts.getOffset();
            i4 = lineno;
        } else {
            line = "";
            i4 = 1;
            offset = 1;
        }
        this.errorReporter.error(strLookupMessage, this.sourceURI, i4, line, offset);
    }

    void addWarning(String str, String str2, int i2, int i3) {
        String strLookupMessage = lookupMessage(str, str2);
        if (this.compilerEnv.reportWarningAsError()) {
            addError(str, str2, i2, i3);
            return;
        }
        IdeErrorReporter ideErrorReporter = this.errorCollector;
        if (ideErrorReporter != null) {
            ideErrorReporter.warning(strLookupMessage, this.sourceURI, i2, i3);
        } else {
            this.errorReporter.warning(strLookupMessage, this.sourceURI, this.ts.getLineno(), this.ts.getLine(), this.ts.getOffset());
        }
    }

    void reportError(String str, String str2, int i2, int i3) {
        addError(str, str2, i2, i3);
        if (!this.compilerEnv.recoverFromErrors()) {
            throw new ParserException();
        }
    }

    private void addStrictWarning(String str, String str2, int i2, int i3, int i4, String str3, int i5) {
        if (this.compilerEnv.isStrictMode()) {
            addWarning(str, str2, i2, i3, i4, str3, i5);
        }
    }

    private AstNode statements() throws IOException {
        return statements(null);
    }

    public AstRoot parse(Reader reader, String str, int i2) throws IOException {
        if (!this.parseFinished) {
            if (this.compilerEnv.isIdeMode()) {
                return parse(readFully(reader), str, i2);
            }
            try {
                this.sourceURI = str;
                this.ts = new TokenStream(this, reader, null, i2);
                return parse();
            } finally {
                this.parseFinished = true;
            }
        }
        throw new IllegalStateException("parser reused");
    }

    private void addWarning(String str, String str2, int i2, int i3, int i4, String str3, int i5) {
        String strLookupMessage = lookupMessage(str, str2);
        if (this.compilerEnv.reportWarningAsError()) {
            addError(str, str2, i2, i3, i4, str3, i5);
            return;
        }
        IdeErrorReporter ideErrorReporter = this.errorCollector;
        if (ideErrorReporter != null) {
            ideErrorReporter.warning(strLookupMessage, this.sourceURI, i2, i3);
        } else {
            this.errorReporter.warning(strLookupMessage, this.sourceURI, i4, str3, i5);
        }
    }

    private void addError(String str, String str2, int i2, int i3, int i4, String str3, int i5) {
        this.syntaxErrorCount++;
        String strLookupMessage = lookupMessage(str, str2);
        IdeErrorReporter ideErrorReporter = this.errorCollector;
        if (ideErrorReporter != null) {
            ideErrorReporter.error(strLookupMessage, this.sourceURI, i2, i3);
        } else {
            this.errorReporter.error(strLookupMessage, this.sourceURI, i4, str3, i5);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0098  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private org.mozilla.javascript.ast.AstRoot parse() throws java.io.IOException, java.lang.RuntimeException {
        /*
            Method dump skipped, instructions count: 229
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.Parser.parse():org.mozilla.javascript.ast.AstRoot");
    }
}
