package org.mozilla.javascript;

import androidx.webkit.ProxyConfig;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.ArrayComprehension;
import org.mozilla.javascript.ast.ArrayComprehensionLoop;
import org.mozilla.javascript.ast.ArrayLiteral;
import org.mozilla.javascript.ast.Assignment;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.AstRoot;
import org.mozilla.javascript.ast.Block;
import org.mozilla.javascript.ast.BreakStatement;
import org.mozilla.javascript.ast.CatchClause;
import org.mozilla.javascript.ast.ConditionalExpression;
import org.mozilla.javascript.ast.ContinueStatement;
import org.mozilla.javascript.ast.DestructuringForm;
import org.mozilla.javascript.ast.DoLoop;
import org.mozilla.javascript.ast.ElementGet;
import org.mozilla.javascript.ast.EmptyExpression;
import org.mozilla.javascript.ast.ExpressionStatement;
import org.mozilla.javascript.ast.ForInLoop;
import org.mozilla.javascript.ast.ForLoop;
import org.mozilla.javascript.ast.FunctionCall;
import org.mozilla.javascript.ast.FunctionNode;
import org.mozilla.javascript.ast.GeneratorExpression;
import org.mozilla.javascript.ast.GeneratorExpressionLoop;
import org.mozilla.javascript.ast.IfStatement;
import org.mozilla.javascript.ast.InfixExpression;
import org.mozilla.javascript.ast.Jump;
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
import org.mozilla.javascript.ast.XmlFragment;
import org.mozilla.javascript.ast.XmlLiteral;
import org.mozilla.javascript.ast.XmlMemberGet;
import org.mozilla.javascript.ast.XmlPropRef;
import org.mozilla.javascript.ast.XmlRef;
import org.mozilla.javascript.ast.XmlString;
import org.mozilla.javascript.ast.Yield;

/* loaded from: classes5.dex */
public final class IRFactory extends Parser {
    private static final int ALWAYS_FALSE_BOOLEAN = -1;
    private static final int ALWAYS_TRUE_BOOLEAN = 1;
    private static final int LOOP_DO_WHILE = 0;
    private static final int LOOP_FOR = 2;
    private static final int LOOP_WHILE = 1;
    private Decompiler decompiler;

    public IRFactory() {
        this.decompiler = new Decompiler();
    }

    private void addSwitchCase(Node node, Node node2, Node node3) throws RuntimeException {
        if (node.getType() != 130) {
            throw Kit.codeBug();
        }
        Jump jump = (Jump) node.getFirstChild();
        if (jump.getType() != 115) {
            throw Kit.codeBug();
        }
        Node nodeNewTarget = Node.newTarget();
        if (node2 != null) {
            Jump jump2 = new Jump(116, node2);
            jump2.target = nodeNewTarget;
            jump.addChildToBack(jump2);
        } else {
            jump.setDefault(nodeNewTarget);
        }
        node.addChildToBack(nodeNewTarget);
        node.addChildToBack(node3);
    }

    private Node arrayCompTransformHelper(ArrayComprehension arrayComprehension, String str) throws Throwable {
        ArrayComprehensionLoop arrayComprehensionLoop;
        Scope scopeCreateLoopNode;
        int i2;
        String string;
        this.decompiler.addToken(84);
        int lineno = arrayComprehension.getLineno();
        Node nodeTransform = transform(arrayComprehension.getResult());
        List<ArrayComprehensionLoop> loops = arrayComprehension.getLoops();
        int size = loops.size();
        Node[] nodeArr = new Node[size];
        Node[] nodeArr2 = new Node[size];
        Node nodeCreateBinary = nodeTransform;
        for (int i3 = 0; i3 < size; i3++) {
            ArrayComprehensionLoop arrayComprehensionLoop2 = loops.get(i3);
            this.decompiler.addName(" ");
            this.decompiler.addToken(120);
            if (arrayComprehensionLoop2.isForEach()) {
                this.decompiler.addName("each ");
            }
            this.decompiler.addToken(88);
            AstNode iterator = arrayComprehensionLoop2.getIterator();
            if (iterator.getType() == 39) {
                string = iterator.getString();
                this.decompiler.addName(string);
            } else {
                decompile(iterator);
                String nextTempName = this.currentScriptOrFn.getNextTempName();
                defineSymbol(88, nextTempName, false);
                nodeCreateBinary = createBinary(90, createAssignment(91, iterator, createName(nextTempName)), nodeCreateBinary);
                string = nextTempName;
            }
            Node nodeCreateName = createName(string);
            defineSymbol(154, string, false);
            nodeArr[i3] = nodeCreateName;
            if (arrayComprehensionLoop2.isForOf()) {
                this.decompiler.addName("of ");
            } else {
                this.decompiler.addToken(52);
            }
            nodeArr2[i3] = transform(arrayComprehensionLoop2.getIteratedObject());
            this.decompiler.addToken(89);
        }
        Node nodeCreateCallOrNew = createCallOrNew(38, createPropertyGet(createName(str), null, "push", 0));
        Node node = new Node(134, nodeCreateCallOrNew, lineno);
        if (arrayComprehension.getFilter() != null) {
            this.decompiler.addName(" ");
            this.decompiler.addToken(113);
            this.decompiler.addToken(88);
            node = createIf(transform(arrayComprehension.getFilter()), node, null, lineno);
            this.decompiler.addToken(89);
        }
        Node nodeCreateForIn = node;
        int i4 = size - 1;
        int i5 = 0;
        while (i4 >= 0) {
            try {
                arrayComprehensionLoop = loops.get(i4);
                scopeCreateLoopNode = createLoopNode(null, arrayComprehensionLoop.getLineno());
                pushScope(scopeCreateLoopNode);
                i2 = i5 + 1;
            } catch (Throwable th) {
                th = th;
            }
            try {
                Node node2 = nodeCreateCallOrNew;
                nodeCreateForIn = createForIn(154, scopeCreateLoopNode, nodeArr[i4], nodeArr2[i4], nodeCreateForIn, arrayComprehensionLoop.isForEach(), arrayComprehensionLoop.isForOf());
                i4--;
                nodeCreateCallOrNew = node2;
                i5 = i2;
            } catch (Throwable th2) {
                th = th2;
                i5 = i2;
                for (int i6 = 0; i6 < i5; i6++) {
                    popScope();
                }
                throw th;
            }
        }
        Node node3 = nodeCreateCallOrNew;
        for (int i7 = 0; i7 < i5; i7++) {
            popScope();
        }
        this.decompiler.addToken(85);
        node3.addChildToBack(nodeCreateBinary);
        return nodeCreateForIn;
    }

    private void closeSwitch(Node node) throws RuntimeException {
        if (node.getType() != 130) {
            throw Kit.codeBug();
        }
        Jump jump = (Jump) node.getFirstChild();
        if (jump.getType() != 115) {
            throw Kit.codeBug();
        }
        Node nodeNewTarget = Node.newTarget();
        jump.target = nodeNewTarget;
        Node node2 = jump.getDefault();
        if (node2 == null) {
            node2 = nodeNewTarget;
        }
        node.addChildAfter(makeJump(5, node2), jump);
        node.addChildToBack(nodeNewTarget);
    }

    private Node createAssignment(int i2, Node node, Node node2) {
        int i3;
        Node nodeMakeReference = makeReference(node);
        if (nodeMakeReference == null) {
            if (node.getType() != 66 && node.getType() != 67) {
                reportError("msg.bad.assign.left");
                return node2;
            }
            if (i2 == 91) {
                return createDestructuringAssignment(-1, node, node2);
            }
            reportError("msg.bad.destruct.op");
            return node2;
        }
        switch (i2) {
            case 91:
                return simpleAssignment(nodeMakeReference, node2);
            case 92:
                i3 = 9;
                break;
            case 93:
                i3 = 10;
                break;
            case 94:
                i3 = 11;
                break;
            case 95:
                i3 = 18;
                break;
            case 96:
                i3 = 19;
                break;
            case 97:
                i3 = 20;
                break;
            case 98:
                i3 = 21;
                break;
            case 99:
                i3 = 22;
                break;
            case 100:
                i3 = 23;
                break;
            case 101:
                i3 = 24;
                break;
            case 102:
                i3 = 25;
                break;
            default:
                throw Kit.codeBug();
        }
        int type = nodeMakeReference.getType();
        if (type == 33 || type == 36) {
            return new Node(type == 33 ? 140 : 141, nodeMakeReference.getFirstChild(), nodeMakeReference.getLastChild(), new Node(i3, new Node(139), node2));
        }
        if (type == 39) {
            return new Node(8, Node.newString(49, nodeMakeReference.getString()), new Node(i3, nodeMakeReference, node2));
        }
        if (type != 68) {
            throw Kit.codeBug();
        }
        Node firstChild = nodeMakeReference.getFirstChild();
        checkMutableReference(firstChild);
        return new Node(143, firstChild, new Node(i3, new Node(139), node2));
    }

    private Node createBinary(int i2, Node node, Node node2) throws RuntimeException {
        String strNumberToString;
        if (i2 == 105) {
            int iIsAlwaysDefinedBoolean = isAlwaysDefinedBoolean(node);
            if (iIsAlwaysDefinedBoolean == 1) {
                return node;
            }
            if (iIsAlwaysDefinedBoolean == -1) {
                return node2;
            }
        } else if (i2 != 106) {
            switch (i2) {
                case 21:
                    int i3 = node.type;
                    if (i3 == 41) {
                        int i4 = node2.type;
                        if (i4 == 41) {
                            strNumberToString = node2.getString();
                        } else if (i4 == 40) {
                            strNumberToString = ScriptRuntime.numberToString(node2.getDouble(), 10);
                        }
                        node.setString(node.getString().concat(strNumberToString));
                        return node;
                    }
                    if (i3 == 40) {
                        int i5 = node2.type;
                        if (i5 == 40) {
                            node.setDouble(node.getDouble() + node2.getDouble());
                            return node;
                        }
                        if (i5 == 41) {
                            node2.setString(ScriptRuntime.numberToString(node.getDouble(), 10).concat(node2.getString()));
                            return node2;
                        }
                    }
                    break;
                case 22:
                    if (node.type == 40) {
                        double d2 = node.getDouble();
                        if (node2.type == 40) {
                            node.setDouble(d2 - node2.getDouble());
                            return node;
                        }
                        if (d2 == 0.0d) {
                            return new Node(29, node2);
                        }
                    } else if (node2.type == 40 && node2.getDouble() == 0.0d) {
                        return new Node(28, node);
                    }
                    break;
                case 23:
                    if (node.type == 40) {
                        double d3 = node.getDouble();
                        if (node2.type == 40) {
                            node.setDouble(d3 * node2.getDouble());
                            return node;
                        }
                        if (d3 == 1.0d) {
                            return new Node(28, node2);
                        }
                    } else if (node2.type == 40 && node2.getDouble() == 1.0d) {
                        return new Node(28, node);
                    }
                    break;
                case 24:
                    if (node2.type == 40) {
                        double d4 = node2.getDouble();
                        if (node.type == 40) {
                            node.setDouble(node.getDouble() / d4);
                            return node;
                        }
                        if (d4 == 1.0d) {
                            return new Node(28, node);
                        }
                    }
                    break;
            }
        } else {
            int iIsAlwaysDefinedBoolean2 = isAlwaysDefinedBoolean(node);
            if (iIsAlwaysDefinedBoolean2 == -1) {
                return node;
            }
            if (iIsAlwaysDefinedBoolean2 == 1) {
                return node2;
            }
        }
        return new Node(i2, node, node2);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0038  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private org.mozilla.javascript.Node createCallOrNew(int r6, org.mozilla.javascript.Node r7) {
        /*
            r5 = this;
            int r0 = r7.getType()
            r1 = 39
            r2 = 1
            java.lang.String r3 = "eval"
            r4 = 0
            if (r0 != r1) goto L21
            java.lang.String r0 = r7.getString()
            boolean r1 = r0.equals(r3)
            if (r1 == 0) goto L17
            goto L39
        L17:
            java.lang.String r1 = "With"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L38
            r2 = 2
            goto L39
        L21:
            int r0 = r7.getType()
            r1 = 33
            if (r0 != r1) goto L38
            org.mozilla.javascript.Node r0 = r7.getLastChild()
            java.lang.String r0 = r0.getString()
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L38
            goto L39
        L38:
            r2 = r4
        L39:
            org.mozilla.javascript.Node r0 = new org.mozilla.javascript.Node
            r0.<init>(r6, r7)
            if (r2 == 0) goto L48
            r5.setRequiresActivation()
            r6 = 10
            r0.putIntProp(r6, r2)
        L48:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.IRFactory.createCallOrNew(int, org.mozilla.javascript.Node):org.mozilla.javascript.Node");
    }

    private Node createCatch(String str, Node node, Node node2, int i2) {
        if (node == null) {
            node = new Node(129);
        }
        return new Node(125, createName(str), node, node2, i2);
    }

    private Node createCondExpr(Node node, Node node2, Node node3) {
        int iIsAlwaysDefinedBoolean = isAlwaysDefinedBoolean(node);
        return iIsAlwaysDefinedBoolean == 1 ? node2 : iIsAlwaysDefinedBoolean == -1 ? node3 : new Node(103, node, node2, node3);
    }

    private Node createElementGet(Node node, String str, Node node2, int i2) {
        if (str != null || i2 != 0) {
            return createMemberRefGet(node, str, node2, i2);
        }
        if (node != null) {
            return new Node(36, node, node2);
        }
        throw Kit.codeBug();
    }

    private Node createExprStatementNoReturn(Node node, int i2) {
        return new Node(134, node, i2);
    }

    private Node createFor(Scope scope, Node node, Node node2, Node node3, Node node4) {
        if (node.getType() != 154) {
            return createLoop(scope, 2, node4, node2, node, node3);
        }
        Scope scopeSplitScope = Scope.splitScope(scope);
        scopeSplitScope.setType(154);
        scopeSplitScope.addChildrenToBack(node);
        scopeSplitScope.addChildToBack(createLoop(scope, 2, node4, node2, new Node(129), node3));
        return scopeSplitScope;
    }

    private Node createForIn(int i2, Node node, Node node2, Node node3, Node node4, boolean z2, boolean z3) throws RuntimeException {
        int type;
        Node nodeNewString;
        Node nodeSimpleAssignment;
        int type2 = node2.getType();
        if (type2 == 123 || type2 == 154) {
            Node lastChild = node2.getLastChild();
            type = lastChild.getType();
            if (type == 66 || type == 67) {
                destructuringLength = lastChild instanceof ArrayLiteral ? ((ArrayLiteral) lastChild).getDestructuringLength() : 0;
                nodeNewString = lastChild;
                type2 = type;
            } else {
                if (type != 39) {
                    reportError("msg.bad.for.in.lhs");
                    return null;
                }
                type = type2;
                nodeNewString = Node.newString(39, lastChild.getString());
                type2 = -1;
            }
        } else if (type2 == 66 || type2 == 67) {
            destructuringLength = node2 instanceof ArrayLiteral ? ((ArrayLiteral) node2).getDestructuringLength() : 0;
            type = type2;
            nodeNewString = node2;
        } else {
            nodeNewString = makeReference(node2);
            if (nodeNewString == null) {
                reportError("msg.bad.for.in.lhs");
                return null;
            }
            type = type2;
            type2 = -1;
        }
        Node node5 = new Node(142);
        Node node6 = new Node(z2 ? 59 : z3 ? 61 : type2 != -1 ? 60 : 58, node3);
        node6.putProp(3, node5);
        Node node7 = new Node(62);
        node7.putProp(3, node5);
        Node node8 = new Node(63);
        node8.putProp(3, node5);
        Node node9 = new Node(130);
        if (type2 != -1) {
            nodeSimpleAssignment = createDestructuringAssignment(i2, nodeNewString, node8);
            if (!z2 && !z3 && (type2 == 67 || destructuringLength != 2)) {
                reportError("msg.bad.for.in.destruct");
            }
        } else {
            nodeSimpleAssignment = simpleAssignment(nodeNewString, node8);
        }
        node9.addChildToBack(new Node(134, nodeSimpleAssignment));
        node9.addChildToBack(node4);
        Node nodeCreateLoop = createLoop((Jump) node, 1, node9, node7, null, null);
        nodeCreateLoop.addChildToFront(node6);
        if (type == 123 || type == 154) {
            nodeCreateLoop.addChildToFront(node2);
        }
        node5.addChildToBack(nodeCreateLoop);
        return node5;
    }

    private Node createIf(Node node, Node node2, Node node3, int i2) {
        int iIsAlwaysDefinedBoolean = isAlwaysDefinedBoolean(node);
        if (iIsAlwaysDefinedBoolean == 1) {
            return node2;
        }
        if (iIsAlwaysDefinedBoolean == -1) {
            return node3 != null ? node3 : new Node(130, i2);
        }
        Node node4 = new Node(130, i2);
        Node nodeNewTarget = Node.newTarget();
        Jump jump = new Jump(7, node);
        jump.target = nodeNewTarget;
        node4.addChildToBack(jump);
        node4.addChildrenToBack(node2);
        if (node3 != null) {
            Node nodeNewTarget2 = Node.newTarget();
            node4.addChildToBack(makeJump(5, nodeNewTarget2));
            node4.addChildToBack(nodeNewTarget);
            node4.addChildrenToBack(node3);
            node4.addChildToBack(nodeNewTarget2);
        } else {
            node4.addChildToBack(nodeNewTarget);
        }
        return node4;
    }

    private Node createIncDec(int i2, boolean z2, Node node) {
        Node nodeMakeReference = makeReference(node);
        int type = nodeMakeReference.getType();
        if (type != 33 && type != 36 && type != 39 && type != 68) {
            throw Kit.codeBug();
        }
        Node node2 = new Node(i2, nodeMakeReference);
        int i3 = i2 == 108 ? 1 : 0;
        if (z2) {
            i3 |= 2;
        }
        node2.putIntProp(13, i3);
        return node2;
    }

    private Node createLoop(Jump jump, int i2, Node node, Node node2, Node node3, Node node4) throws RuntimeException {
        Node nodeNewTarget = Node.newTarget();
        Node nodeNewTarget2 = Node.newTarget();
        if (i2 == 2 && node2.getType() == 129) {
            node2 = new Node(45);
        }
        Jump jump2 = new Jump(6, node2);
        jump2.target = nodeNewTarget;
        Node nodeNewTarget3 = Node.newTarget();
        jump.addChildToBack(nodeNewTarget);
        jump.addChildrenToBack(node);
        if (i2 == 1 || i2 == 2) {
            jump.addChildrenToBack(new Node(129, jump.getLineno()));
        }
        jump.addChildToBack(nodeNewTarget2);
        jump.addChildToBack(jump2);
        jump.addChildToBack(nodeNewTarget3);
        jump.target = nodeNewTarget3;
        if (i2 == 1 || i2 == 2) {
            jump.addChildToFront(makeJump(5, nodeNewTarget2));
            if (i2 == 2) {
                int type = node3.getType();
                if (type != 129) {
                    if (type != 123 && type != 154) {
                        node3 = new Node(134, node3);
                    }
                    jump.addChildToFront(node3);
                }
                nodeNewTarget2 = Node.newTarget();
                jump.addChildAfter(nodeNewTarget2, node);
                if (node4.getType() != 129) {
                    jump.addChildAfter(new Node(134, node4), nodeNewTarget2);
                }
            }
        }
        jump.setContinue(nodeNewTarget2);
        return jump;
    }

    private Scope createLoopNode(Node node, int i2) throws RuntimeException {
        Scope scopeCreateScopeNode = createScopeNode(133, i2);
        if (node != null) {
            ((Jump) node).setLoop(scopeCreateScopeNode);
        }
        return scopeCreateScopeNode;
    }

    private Node createMemberRefGet(Node node, String str, Node node2, int i2) {
        Node node3;
        Node node4 = str != null ? str.equals(ProxyConfig.MATCH_ALL_SCHEMES) ? new Node(42) : createName(str) : null;
        if (node == null) {
            node3 = str == null ? new Node(80, node2) : new Node(81, node4, node2);
        } else {
            node3 = str == null ? new Node(78, node, node2) : new Node(79, node, node4, node2);
        }
        if (i2 != 0) {
            node3.putIntProp(16, i2);
        }
        return new Node(68, node3);
    }

    private Node createPropertyGet(Node node, String str, String str2, int i2) {
        if (str != null || i2 != 0) {
            return createMemberRefGet(node, str, Node.newString(str2), i2 | 1);
        }
        if (node == null) {
            return createName(str2);
        }
        checkActivationName(str2, 33);
        if (!ScriptRuntime.isSpecialProperty(str2)) {
            return new Node(33, node, Node.newString(str2));
        }
        Node node2 = new Node(72, node);
        node2.putProp(17, str2);
        return new Node(68, node2);
    }

    private Node createString(String str) {
        return Node.newString(str);
    }

    private Node createTryCatchFinally(Node node, Node node2, Node node3, int i2) throws RuntimeException {
        boolean z2 = false;
        boolean z3 = node3 != null && (node3.getType() != 130 || node3.hasChildren());
        if (node.getType() == 130 && !node.hasChildren() && !z3) {
            return node;
        }
        boolean zHasChildren = node2.hasChildren();
        if (!z3 && !zHasChildren) {
            return node;
        }
        Node node4 = new Node(142);
        Jump jump = new Jump(82, node, i2);
        int i3 = 3;
        jump.putProp(3, node4);
        if (zHasChildren) {
            Node nodeNewTarget = Node.newTarget();
            jump.addChildToBack(makeJump(5, nodeNewTarget));
            Node nodeNewTarget2 = Node.newTarget();
            jump.target = nodeNewTarget2;
            jump.addChildToBack(nodeNewTarget2);
            Node node5 = new Node(142);
            Node firstChild = node2.getFirstChild();
            int i4 = 0;
            while (firstChild != null) {
                int lineno = firstChild.getLineno();
                Node firstChild2 = firstChild.getFirstChild();
                Node next = firstChild2.getNext();
                Node next2 = next.getNext();
                firstChild.removeChild(firstChild2);
                firstChild.removeChild(next);
                firstChild.removeChild(next2);
                next2.addChildToBack(new Node(i3));
                next2.addChildToBack(makeJump(5, nodeNewTarget));
                if (next.getType() == 129) {
                    z2 = true;
                } else {
                    next2 = createIf(next, next2, null, lineno);
                }
                Node node6 = new Node(57, firstChild2, createUseLocal(node4));
                node6.putProp(3, node5);
                node6.putIntProp(14, i4);
                node5.addChildToBack(node6);
                node5.addChildToBack(createWith(createUseLocal(node5), next2, lineno));
                firstChild = firstChild.getNext();
                i4++;
                i3 = 3;
            }
            jump.addChildToBack(node5);
            if (!z2) {
                Node node7 = new Node(51);
                node7.putProp(3, node4);
                jump.addChildToBack(node7);
            }
            jump.addChildToBack(nodeNewTarget);
        }
        if (z3) {
            Node nodeNewTarget3 = Node.newTarget();
            jump.setFinally(nodeNewTarget3);
            jump.addChildToBack(makeJump(136, nodeNewTarget3));
            Node nodeNewTarget4 = Node.newTarget();
            jump.addChildToBack(makeJump(5, nodeNewTarget4));
            jump.addChildToBack(nodeNewTarget3);
            Node node8 = new Node(126, node3);
            node8.putProp(3, node4);
            jump.addChildToBack(node8);
            jump.addChildToBack(nodeNewTarget4);
        }
        node4.addChildToBack(jump);
        return node4;
    }

    private Node createUnary(int i2, Node node) {
        int type = node.getType();
        switch (i2) {
            case 26:
                int iIsAlwaysDefinedBoolean = isAlwaysDefinedBoolean(node);
                if (iIsAlwaysDefinedBoolean != 0) {
                    int i3 = iIsAlwaysDefinedBoolean == 1 ? 44 : 45;
                    if (type != 45 && type != 44) {
                        return new Node(i3);
                    }
                    node.setType(i3);
                    return node;
                }
                break;
            case 27:
                if (type == 40) {
                    node.setDouble(~ScriptRuntime.toInt32(node.getDouble()));
                    return node;
                }
                break;
            case 29:
                if (type == 40) {
                    node.setDouble(-node.getDouble());
                    return node;
                }
                break;
            case 31:
                if (type == 39) {
                    node.setType(49);
                    return new Node(i2, node, Node.newString(node.getString()));
                }
                if (type == 33 || type == 36) {
                    Node firstChild = node.getFirstChild();
                    Node lastChild = node.getLastChild();
                    node.removeChild(firstChild);
                    node.removeChild(lastChild);
                    return new Node(i2, firstChild, lastChild);
                }
                if (type != 68) {
                    return new Node(i2, new Node(45), node);
                }
                Node firstChild2 = node.getFirstChild();
                node.removeChild(firstChild2);
                return new Node(70, firstChild2);
            case 32:
                if (type == 39) {
                    node.setType(138);
                    return node;
                }
                break;
        }
        return new Node(i2, node);
    }

    private Node createUseLocal(Node node) {
        if (142 != node.getType()) {
            throw Kit.codeBug();
        }
        Node node2 = new Node(54);
        node2.putProp(3, node);
        return node2;
    }

    private Node createWith(Node node, Node node2, int i2) {
        setRequiresActivation();
        Node node3 = new Node(130, i2);
        node3.addChildToBack(new Node(2, node));
        node3.addChildrenToBack(new Node(124, node2, i2));
        node3.addChildToBack(new Node(3));
        return node3;
    }

    private Node genExprTransformHelper(GeneratorExpression generatorExpression) throws Throwable {
        GeneratorExpressionLoop generatorExpressionLoop;
        Scope scopeCreateLoopNode;
        int i2;
        String string;
        this.decompiler.addToken(88);
        int lineno = generatorExpression.getLineno();
        Node nodeTransform = transform(generatorExpression.getResult());
        List<GeneratorExpressionLoop> loops = generatorExpression.getLoops();
        int size = loops.size();
        Node[] nodeArr = new Node[size];
        Node[] nodeArr2 = new Node[size];
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            GeneratorExpressionLoop generatorExpressionLoop2 = loops.get(i4);
            this.decompiler.addName(" ");
            this.decompiler.addToken(120);
            this.decompiler.addToken(88);
            AstNode iterator = generatorExpressionLoop2.getIterator();
            if (iterator.getType() == 39) {
                string = iterator.getString();
                this.decompiler.addName(string);
            } else {
                decompile(iterator);
                String nextTempName = this.currentScriptOrFn.getNextTempName();
                defineSymbol(88, nextTempName, false);
                nodeTransform = createBinary(90, createAssignment(91, iterator, createName(nextTempName)), nodeTransform);
                string = nextTempName;
            }
            Node nodeCreateName = createName(string);
            defineSymbol(154, string, false);
            nodeArr[i4] = nodeCreateName;
            if (generatorExpressionLoop2.isForOf()) {
                this.decompiler.addName("of ");
            } else {
                this.decompiler.addToken(52);
            }
            nodeArr2[i4] = transform(generatorExpressionLoop2.getIteratedObject());
            this.decompiler.addToken(89);
        }
        Node node = new Node(134, new Node(73, nodeTransform, generatorExpression.getLineno()), lineno);
        if (generatorExpression.getFilter() != null) {
            this.decompiler.addName(" ");
            this.decompiler.addToken(113);
            this.decompiler.addToken(88);
            node = createIf(transform(generatorExpression.getFilter()), node, null, lineno);
            this.decompiler.addToken(89);
        }
        Node nodeCreateForIn = node;
        int i5 = size - 1;
        int i6 = 0;
        while (i5 >= 0) {
            try {
                generatorExpressionLoop = loops.get(i5);
                scopeCreateLoopNode = createLoopNode(null, generatorExpressionLoop.getLineno());
                pushScope(scopeCreateLoopNode);
                i2 = i6 + 1;
            } catch (Throwable th) {
                th = th;
            }
            try {
                nodeCreateForIn = createForIn(154, scopeCreateLoopNode, nodeArr[i5], nodeArr2[i5], nodeCreateForIn, generatorExpressionLoop.isForEach(), generatorExpressionLoop.isForOf());
                i5--;
                i6 = i2;
            } catch (Throwable th2) {
                th = th2;
                i6 = i2;
                while (i3 < i6) {
                    popScope();
                    i3++;
                }
                throw th;
            }
        }
        while (i3 < i6) {
            popScope();
            i3++;
        }
        this.decompiler.addToken(89);
        return nodeCreateForIn;
    }

    private Object getPropKey(Node node) throws RuntimeException {
        if (node instanceof Name) {
            String identifier = ((Name) node).getIdentifier();
            this.decompiler.addName(identifier);
            return ScriptRuntime.getIndexObject(identifier);
        }
        if (node instanceof StringLiteral) {
            String value = ((StringLiteral) node).getValue();
            this.decompiler.addString(value);
            return ScriptRuntime.getIndexObject(value);
        }
        if (!(node instanceof NumberLiteral)) {
            throw Kit.codeBug();
        }
        double number = ((NumberLiteral) node).getNumber();
        this.decompiler.addNumber(number);
        return ScriptRuntime.getIndexObject(number);
    }

    private Node initFunction(FunctionNode functionNode, int i2, Node node, int i3) {
        Name functionName;
        functionNode.setFunctionType(i3);
        functionNode.addChildToBack(node);
        if (functionNode.getFunctionCount() != 0) {
            functionNode.setRequiresActivation();
        }
        if (i3 == 2 && (functionName = functionNode.getFunctionName()) != null && functionName.length() != 0 && functionNode.getSymbol(functionName.getIdentifier()) == null) {
            functionNode.putSymbol(new org.mozilla.javascript.ast.Symbol(110, functionName.getIdentifier()));
            node.addChildrenToFront(new Node(134, new Node(8, Node.newString(49, functionName.getIdentifier()), new Node(64))));
        }
        Node lastChild = node.getLastChild();
        if (lastChild == null || lastChild.getType() != 4) {
            node.addChildToBack(new Node(4));
        }
        Node nodeNewString = Node.newString(110, functionNode.getName());
        nodeNewString.putIntProp(1, i2);
        return nodeNewString;
    }

    private static int isAlwaysDefinedBoolean(Node node) {
        int type = node.getType();
        if (type == 40) {
            double d2 = node.getDouble();
            return (d2 != d2 || d2 == 0.0d) ? -1 : 1;
        }
        if (type == 42 || type == 44) {
            return -1;
        }
        return type != 45 ? 0 : 1;
    }

    private Jump makeJump(int i2, Node node) {
        Jump jump = new Jump(i2);
        jump.target = node;
        return jump;
    }

    private Node makeReference(Node node) {
        int type = node.getType();
        if (type != 33 && type != 36 && type != 68) {
            if (type == 38) {
                node.setType(71);
                return new Node(68, node);
            }
            if (type != 39) {
                return null;
            }
        }
        return node;
    }

    private Node transformArrayComp(ArrayComprehension arrayComprehension) {
        int lineno = arrayComprehension.getLineno();
        Scope scopeCreateScopeNode = createScopeNode(158, lineno);
        String nextTempName = this.currentScriptOrFn.getNextTempName();
        pushScope(scopeCreateScopeNode);
        try {
            defineSymbol(154, nextTempName, false);
            Node node = new Node(130, lineno);
            node.addChildToBack(new Node(134, createAssignment(91, createName(nextTempName), createCallOrNew(30, createName("Array"))), lineno));
            node.addChildToBack(arrayCompTransformHelper(arrayComprehension, nextTempName));
            scopeCreateScopeNode.addChildToBack(node);
            scopeCreateScopeNode.addChildToBack(createName(nextTempName));
            return scopeCreateScopeNode;
        } finally {
            popScope();
        }
    }

    private Node transformArrayLiteral(ArrayLiteral arrayLiteral) throws RuntimeException {
        if (arrayLiteral.isDestructuring()) {
            return arrayLiteral;
        }
        this.decompiler.addToken(84);
        List<AstNode> elements = arrayLiteral.getElements();
        Node node = new Node(66);
        ArrayList arrayList = null;
        for (int i2 = 0; i2 < elements.size(); i2++) {
            AstNode astNode = elements.get(i2);
            if (astNode.getType() != 129) {
                node.addChildToBack(transform(astNode));
            } else {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(Integer.valueOf(i2));
            }
            if (i2 < elements.size() - 1) {
                this.decompiler.addToken(90);
            }
        }
        this.decompiler.addToken(85);
        node.putIntProp(21, arrayLiteral.getDestructuringLength());
        if (arrayList != null) {
            int[] iArr = new int[arrayList.size()];
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                iArr[i3] = ((Integer) arrayList.get(i3)).intValue();
            }
            node.putProp(11, iArr);
        }
        return node;
    }

    private Node transformAssignment(Assignment assignment) throws RuntimeException {
        AstNode astNodeTransform;
        AstNode astNodeRemoveParens = removeParens(assignment.getLeft());
        if (isDestructuring(astNodeRemoveParens)) {
            decompile(astNodeRemoveParens);
            astNodeTransform = astNodeRemoveParens;
        } else {
            astNodeTransform = transform(astNodeRemoveParens);
        }
        this.decompiler.addToken(assignment.getType());
        return createAssignment(assignment.getType(), astNodeTransform, transform(assignment.getRight()));
    }

    private Node transformBlock(AstNode astNode) {
        boolean z2 = astNode instanceof Scope;
        if (z2) {
            pushScope((Scope) astNode);
        }
        try {
            ArrayList arrayList = new ArrayList();
            Iterator<Node> it = astNode.iterator();
            while (it.hasNext()) {
                arrayList.add(transform((AstNode) it.next()));
            }
            astNode.removeChildren();
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                astNode.addChildToBack((Node) it2.next());
            }
            if (astNode instanceof Scope) {
                popScope();
            }
            return astNode;
        } catch (Throwable th) {
            if (z2) {
                popScope();
            }
            throw th;
        }
    }

    private Node transformBreak(BreakStatement breakStatement) throws RuntimeException {
        this.decompiler.addToken(121);
        if (breakStatement.getBreakLabel() != null) {
            this.decompiler.addName(breakStatement.getBreakLabel().getIdentifier());
        }
        this.decompiler.addEOL(83);
        return breakStatement;
    }

    private Node transformCondExpr(ConditionalExpression conditionalExpression) throws RuntimeException {
        Node nodeTransform = transform(conditionalExpression.getTestExpression());
        this.decompiler.addToken(103);
        Node nodeTransform2 = transform(conditionalExpression.getTrueExpression());
        this.decompiler.addToken(104);
        return createCondExpr(nodeTransform, nodeTransform2, transform(conditionalExpression.getFalseExpression()));
    }

    private Node transformContinue(ContinueStatement continueStatement) throws RuntimeException {
        this.decompiler.addToken(122);
        if (continueStatement.getLabel() != null) {
            this.decompiler.addName(continueStatement.getLabel().getIdentifier());
        }
        this.decompiler.addEOL(83);
        return continueStatement;
    }

    private Node transformDefaultXmlNamepace(UnaryExpression unaryExpression) throws RuntimeException {
        this.decompiler.addToken(117);
        this.decompiler.addName(" xml");
        this.decompiler.addName(" namespace");
        this.decompiler.addToken(91);
        return createUnary(75, transform(unaryExpression.getOperand()));
    }

    private Node transformDoLoop(DoLoop doLoop) {
        doLoop.setType(133);
        pushScope(doLoop);
        try {
            this.decompiler.addToken(119);
            this.decompiler.addEOL(86);
            Node nodeTransform = transform(doLoop.getBody());
            this.decompiler.addToken(87);
            this.decompiler.addToken(118);
            this.decompiler.addToken(88);
            Node nodeTransform2 = transform(doLoop.getCondition());
            this.decompiler.addToken(89);
            this.decompiler.addEOL(83);
            return createLoop(doLoop, 0, nodeTransform, nodeTransform2, null, null);
        } finally {
            popScope();
        }
    }

    private Node transformElementGet(ElementGet elementGet) throws RuntimeException {
        Node nodeTransform = transform(elementGet.getTarget());
        this.decompiler.addToken(84);
        Node nodeTransform2 = transform(elementGet.getElement());
        this.decompiler.addToken(85);
        return new Node(36, nodeTransform, nodeTransform2);
    }

    private Node transformExprStmt(ExpressionStatement expressionStatement) throws RuntimeException {
        Node nodeTransform = transform(expressionStatement.getExpression());
        this.decompiler.addEOL(83);
        return new Node(expressionStatement.getType(), nodeTransform, expressionStatement.getLineno());
    }

    private Node transformForInLoop(ForInLoop forInLoop) throws RuntimeException {
        this.decompiler.addToken(120);
        if (forInLoop.isForEach()) {
            this.decompiler.addName("each ");
        }
        this.decompiler.addToken(88);
        forInLoop.setType(133);
        pushScope(forInLoop);
        try {
            AstNode iterator = forInLoop.getIterator();
            int type = iterator instanceof VariableDeclaration ? ((VariableDeclaration) iterator).getType() : -1;
            Node nodeTransform = transform(iterator);
            if (forInLoop.isForOf()) {
                this.decompiler.addName("of ");
            } else {
                this.decompiler.addToken(52);
            }
            Node nodeTransform2 = transform(forInLoop.getIteratedObject());
            this.decompiler.addToken(89);
            this.decompiler.addEOL(86);
            Node nodeTransform3 = transform(forInLoop.getBody());
            this.decompiler.addEOL(87);
            Node nodeCreateForIn = createForIn(type, forInLoop, nodeTransform, nodeTransform2, nodeTransform3, forInLoop.isForEach(), forInLoop.isForOf());
            popScope();
            return nodeCreateForIn;
        } catch (Throwable th) {
            popScope();
            throw th;
        }
    }

    private Node transformForLoop(ForLoop forLoop) throws RuntimeException {
        this.decompiler.addToken(120);
        this.decompiler.addToken(88);
        forLoop.setType(133);
        Scope scope = this.currentScope;
        this.currentScope = forLoop;
        try {
            Node nodeTransform = transform(forLoop.getInitializer());
            this.decompiler.addToken(83);
            Node nodeTransform2 = transform(forLoop.getCondition());
            this.decompiler.addToken(83);
            Node nodeTransform3 = transform(forLoop.getIncrement());
            this.decompiler.addToken(89);
            this.decompiler.addEOL(86);
            Node nodeTransform4 = transform(forLoop.getBody());
            this.decompiler.addEOL(87);
            return createFor(forLoop, nodeTransform, nodeTransform2, nodeTransform3, nodeTransform4);
        } finally {
            this.currentScope = scope;
        }
    }

    private Node transformFunction(FunctionNode functionNode) throws RuntimeException {
        int functionType = functionNode.getFunctionType();
        int iMarkFunctionStart = this.decompiler.markFunctionStart(functionType);
        Node nodeDecompileFunctionHeader = decompileFunctionHeader(functionNode);
        int iAddFunction = this.currentScriptOrFn.addFunction(functionNode);
        Parser.PerFunctionVariables perFunctionVariables = new Parser.PerFunctionVariables(functionNode);
        try {
            Node node = (Node) functionNode.getProp(23);
            functionNode.removeProp(23);
            int lineno = functionNode.getBody().getLineno();
            this.nestingOfFunction++;
            Node nodeTransform = transform(functionNode.getBody());
            if (!functionNode.isExpressionClosure()) {
                this.decompiler.addToken(87);
            }
            functionNode.setEncodedSourceBounds(iMarkFunctionStart, this.decompiler.markFunctionEnd(iMarkFunctionStart));
            if (functionType != 2 && !functionNode.isExpressionClosure()) {
                this.decompiler.addToken(1);
            }
            if (node != null) {
                nodeTransform.addChildToFront(new Node(134, node, lineno));
            }
            int functionType2 = functionNode.getFunctionType();
            Node nodeInitFunction = initFunction(functionNode, iAddFunction, nodeTransform, functionType2);
            if (nodeDecompileFunctionHeader != null) {
                nodeInitFunction = createAssignment(91, nodeDecompileFunctionHeader, nodeInitFunction);
                if (functionType2 != 2) {
                    nodeInitFunction = createExprStatementNoReturn(nodeInitFunction, functionNode.getLineno());
                }
            }
            this.nestingOfFunction--;
            perFunctionVariables.restore();
            return nodeInitFunction;
        } catch (Throwable th) {
            this.nestingOfFunction--;
            perFunctionVariables.restore();
            throw th;
        }
    }

    private Node transformFunctionCall(FunctionCall functionCall) throws RuntimeException {
        Node nodeCreateCallOrNew = createCallOrNew(38, transform(functionCall.getTarget()));
        nodeCreateCallOrNew.setLineno(functionCall.getLineno());
        this.decompiler.addToken(88);
        List<AstNode> arguments = functionCall.getArguments();
        for (int i2 = 0; i2 < arguments.size(); i2++) {
            nodeCreateCallOrNew.addChildToBack(transform(arguments.get(i2)));
            if (i2 < arguments.size() - 1) {
                this.decompiler.addToken(90);
            }
        }
        this.decompiler.addToken(89);
        return nodeCreateCallOrNew;
    }

    private Node transformGenExpr(GeneratorExpression generatorExpression) throws RuntimeException {
        FunctionNode functionNode = new FunctionNode();
        functionNode.setSourceName(this.currentScriptOrFn.getNextTempName());
        functionNode.setIsGenerator();
        functionNode.setFunctionType(2);
        functionNode.setRequiresActivation();
        int functionType = functionNode.getFunctionType();
        int iMarkFunctionStart = this.decompiler.markFunctionStart(functionType);
        Node nodeDecompileFunctionHeader = decompileFunctionHeader(functionNode);
        int iAddFunction = this.currentScriptOrFn.addFunction(functionNode);
        Parser.PerFunctionVariables perFunctionVariables = new Parser.PerFunctionVariables(functionNode);
        try {
            Node node = (Node) functionNode.getProp(23);
            functionNode.removeProp(23);
            int i2 = generatorExpression.lineno;
            this.nestingOfFunction++;
            Node nodeGenExprTransformHelper = genExprTransformHelper(generatorExpression);
            if (!functionNode.isExpressionClosure()) {
                this.decompiler.addToken(87);
            }
            functionNode.setEncodedSourceBounds(iMarkFunctionStart, this.decompiler.markFunctionEnd(iMarkFunctionStart));
            if (functionType != 2 && !functionNode.isExpressionClosure()) {
                this.decompiler.addToken(1);
            }
            if (node != null) {
                nodeGenExprTransformHelper.addChildToFront(new Node(134, node, i2));
            }
            int functionType2 = functionNode.getFunctionType();
            Node nodeInitFunction = initFunction(functionNode, iAddFunction, nodeGenExprTransformHelper, functionType2);
            if (nodeDecompileFunctionHeader != null) {
                nodeInitFunction = createAssignment(91, nodeDecompileFunctionHeader, nodeInitFunction);
                if (functionType2 != 2) {
                    nodeInitFunction = createExprStatementNoReturn(nodeInitFunction, functionNode.getLineno());
                }
            }
            this.nestingOfFunction--;
            perFunctionVariables.restore();
            Node nodeCreateCallOrNew = createCallOrNew(38, nodeInitFunction);
            nodeCreateCallOrNew.setLineno(generatorExpression.getLineno());
            this.decompiler.addToken(88);
            this.decompiler.addToken(89);
            return nodeCreateCallOrNew;
        } catch (Throwable th) {
            this.nestingOfFunction--;
            perFunctionVariables.restore();
            throw th;
        }
    }

    private Node transformIf(IfStatement ifStatement) throws RuntimeException {
        Node nodeTransform;
        this.decompiler.addToken(113);
        this.decompiler.addToken(88);
        Node nodeTransform2 = transform(ifStatement.getCondition());
        this.decompiler.addToken(89);
        this.decompiler.addEOL(86);
        Node nodeTransform3 = transform(ifStatement.getThenPart());
        if (ifStatement.getElsePart() != null) {
            this.decompiler.addToken(87);
            this.decompiler.addToken(114);
            this.decompiler.addEOL(86);
            nodeTransform = transform(ifStatement.getElsePart());
        } else {
            nodeTransform = null;
        }
        this.decompiler.addEOL(87);
        return createIf(nodeTransform2, nodeTransform3, nodeTransform, ifStatement.getLineno());
    }

    private Node transformInfix(InfixExpression infixExpression) throws RuntimeException {
        Node nodeTransform = transform(infixExpression.getLeft());
        this.decompiler.addToken(infixExpression.getType());
        Node nodeTransform2 = transform(infixExpression.getRight());
        if (infixExpression instanceof XmlDotQuery) {
            this.decompiler.addToken(89);
        }
        return createBinary(infixExpression.getType(), nodeTransform, nodeTransform2);
    }

    private Node transformLabeledStatement(LabeledStatement labeledStatement) throws RuntimeException {
        Label firstLabel = labeledStatement.getFirstLabel();
        List<Label> labels = labeledStatement.getLabels();
        this.decompiler.addName(firstLabel.getName());
        if (labels.size() > 1) {
            for (Label label : labels.subList(1, labels.size())) {
                this.decompiler.addEOL(104);
                this.decompiler.addName(label.getName());
            }
        }
        if (labeledStatement.getStatement().getType() == 130) {
            this.decompiler.addToken(67);
            this.decompiler.addEOL(86);
        } else {
            this.decompiler.addEOL(104);
        }
        Node nodeTransform = transform(labeledStatement.getStatement());
        if (labeledStatement.getStatement().getType() == 130) {
            this.decompiler.addEOL(87);
        }
        Node nodeNewTarget = Node.newTarget();
        Node node = new Node(130, firstLabel, nodeTransform, nodeNewTarget);
        firstLabel.target = nodeNewTarget;
        return node;
    }

    private Node transformLetNode(LetNode letNode) {
        pushScope(letNode);
        try {
            this.decompiler.addToken(154);
            this.decompiler.addToken(88);
            Node nodeTransformVariableInitializers = transformVariableInitializers(letNode.getVariables());
            this.decompiler.addToken(89);
            letNode.addChildToBack(nodeTransformVariableInitializers);
            boolean z2 = letNode.getType() == 159;
            if (letNode.getBody() != null) {
                if (z2) {
                    this.decompiler.addName(" ");
                } else {
                    this.decompiler.addEOL(86);
                }
                letNode.addChildToBack(transform(letNode.getBody()));
                if (!z2) {
                    this.decompiler.addEOL(87);
                }
            }
            popScope();
            return letNode;
        } catch (Throwable th) {
            popScope();
            throw th;
        }
    }

    private Node transformLiteral(AstNode astNode) throws RuntimeException {
        this.decompiler.addToken(astNode.getType());
        return astNode;
    }

    private Node transformName(Name name) throws RuntimeException {
        this.decompiler.addName(name.getIdentifier());
        return name;
    }

    private Node transformNewExpr(NewExpression newExpression) throws RuntimeException {
        this.decompiler.addToken(30);
        Node nodeCreateCallOrNew = createCallOrNew(30, transform(newExpression.getTarget()));
        nodeCreateCallOrNew.setLineno(newExpression.getLineno());
        List<AstNode> arguments = newExpression.getArguments();
        this.decompiler.addToken(88);
        for (int i2 = 0; i2 < arguments.size(); i2++) {
            nodeCreateCallOrNew.addChildToBack(transform(arguments.get(i2)));
            if (i2 < arguments.size() - 1) {
                this.decompiler.addToken(90);
            }
        }
        this.decompiler.addToken(89);
        if (newExpression.getInitializer() != null) {
            nodeCreateCallOrNew.addChildToBack(transformObjectLiteral(newExpression.getInitializer()));
        }
        return nodeCreateCallOrNew;
    }

    private Node transformNumber(NumberLiteral numberLiteral) throws RuntimeException {
        this.decompiler.addNumber(numberLiteral.getNumber());
        return numberLiteral;
    }

    private Node transformObjectLiteral(ObjectLiteral objectLiteral) throws RuntimeException {
        Object[] objArr;
        if (objectLiteral.isDestructuring()) {
            return objectLiteral;
        }
        this.decompiler.addToken(86);
        List<ObjectProperty> elements = objectLiteral.getElements();
        Node node = new Node(67);
        if (elements.isEmpty()) {
            objArr = ScriptRuntime.emptyArgs;
        } else {
            int size = elements.size();
            Object[] objArr2 = new Object[size];
            int i2 = 0;
            for (ObjectProperty objectProperty : elements) {
                if (objectProperty.isGetterMethod()) {
                    this.decompiler.addToken(152);
                } else if (objectProperty.isSetterMethod()) {
                    this.decompiler.addToken(153);
                } else if (objectProperty.isNormalMethod()) {
                    this.decompiler.addToken(164);
                }
                int i3 = i2 + 1;
                objArr2[i2] = getPropKey(objectProperty.getLeft());
                if (!objectProperty.isMethod()) {
                    this.decompiler.addToken(67);
                }
                Node nodeTransform = transform(objectProperty.getRight());
                if (objectProperty.isGetterMethod()) {
                    nodeTransform = createUnary(152, nodeTransform);
                } else if (objectProperty.isSetterMethod()) {
                    nodeTransform = createUnary(153, nodeTransform);
                } else if (objectProperty.isNormalMethod()) {
                    nodeTransform = createUnary(164, nodeTransform);
                }
                node.addChildToBack(nodeTransform);
                if (i3 < size) {
                    this.decompiler.addToken(90);
                }
                i2 = i3;
            }
            objArr = objArr2;
        }
        this.decompiler.addToken(87);
        node.putProp(12, objArr);
        return node;
    }

    private Node transformParenExpr(ParenthesizedExpression parenthesizedExpression) throws RuntimeException {
        AstNode expression = parenthesizedExpression.getExpression();
        this.decompiler.addToken(88);
        int i2 = 1;
        while (expression instanceof ParenthesizedExpression) {
            this.decompiler.addToken(88);
            i2++;
            expression = ((ParenthesizedExpression) expression).getExpression();
        }
        Node nodeTransform = transform(expression);
        for (int i3 = 0; i3 < i2; i3++) {
            this.decompiler.addToken(89);
        }
        nodeTransform.putProp(19, Boolean.TRUE);
        return nodeTransform;
    }

    private Node transformPropertyGet(PropertyGet propertyGet) throws RuntimeException {
        Node nodeTransform = transform(propertyGet.getTarget());
        String identifier = propertyGet.getProperty().getIdentifier();
        this.decompiler.addToken(109);
        this.decompiler.addName(identifier);
        return createPropertyGet(nodeTransform, null, identifier, 0);
    }

    private Node transformRegExp(RegExpLiteral regExpLiteral) throws RuntimeException {
        this.decompiler.addRegexp(regExpLiteral.getValue(), regExpLiteral.getFlags());
        this.currentScriptOrFn.addRegExp(regExpLiteral);
        return regExpLiteral;
    }

    private Node transformReturn(ReturnStatement returnStatement) throws RuntimeException {
        Boolean bool = Boolean.TRUE;
        boolean zEquals = bool.equals(returnStatement.getProp(25));
        boolean zEquals2 = bool.equals(returnStatement.getProp(27));
        if (!zEquals) {
            this.decompiler.addToken(4);
        } else if (!zEquals2) {
            this.decompiler.addName(" ");
        }
        AstNode returnValue = returnStatement.getReturnValue();
        Node nodeTransform = returnValue == null ? null : transform(returnValue);
        if (!zEquals) {
            this.decompiler.addEOL(83);
        }
        return returnValue == null ? new Node(4, returnStatement.getLineno()) : new Node(4, nodeTransform, returnStatement.getLineno());
    }

    private Node transformScript(ScriptNode scriptNode) throws RuntimeException {
        this.decompiler.addToken(137);
        if (this.currentScope != null) {
            Kit.codeBug();
        }
        this.currentScope = scriptNode;
        Node node = new Node(130);
        Iterator<Node> it = scriptNode.iterator();
        while (it.hasNext()) {
            node.addChildToBack(transform((AstNode) it.next()));
        }
        scriptNode.removeChildren();
        Node firstChild = node.getFirstChild();
        if (firstChild != null) {
            scriptNode.addChildrenToBack(firstChild);
        }
        return scriptNode;
    }

    private Node transformString(StringLiteral stringLiteral) throws RuntimeException {
        this.decompiler.addString(stringLiteral.getValue());
        return Node.newString(stringLiteral.getValue());
    }

    private Node transformSwitch(SwitchStatement switchStatement) throws RuntimeException {
        Node nodeTransform;
        this.decompiler.addToken(115);
        this.decompiler.addToken(88);
        Node nodeTransform2 = transform(switchStatement.getExpression());
        this.decompiler.addToken(89);
        switchStatement.addChildToBack(nodeTransform2);
        Node node = new Node(130, switchStatement, switchStatement.getLineno());
        this.decompiler.addEOL(86);
        for (SwitchCase switchCase : switchStatement.getCases()) {
            AstNode expression = switchCase.getExpression();
            if (expression != null) {
                this.decompiler.addToken(116);
                nodeTransform = transform(expression);
            } else {
                this.decompiler.addToken(117);
                nodeTransform = null;
            }
            this.decompiler.addEOL(104);
            List<AstNode> statements = switchCase.getStatements();
            Block block = new Block();
            if (statements != null) {
                Iterator<AstNode> it = statements.iterator();
                while (it.hasNext()) {
                    block.addChildToBack(transform(it.next()));
                }
            }
            addSwitchCase(node, nodeTransform, block);
        }
        this.decompiler.addEOL(87);
        closeSwitch(node);
        return node;
    }

    private Node transformThrow(ThrowStatement throwStatement) throws RuntimeException {
        this.decompiler.addToken(50);
        Node nodeTransform = transform(throwStatement.getExpression());
        this.decompiler.addEOL(83);
        return new Node(50, nodeTransform, throwStatement.getLineno());
    }

    private Node transformTry(TryStatement tryStatement) throws RuntimeException {
        Node nodeTransform;
        Node emptyExpression;
        this.decompiler.addToken(82);
        this.decompiler.addEOL(86);
        Node nodeTransform2 = transform(tryStatement.getTryBlock());
        this.decompiler.addEOL(87);
        Block block = new Block();
        for (CatchClause catchClause : tryStatement.getCatchClauses()) {
            this.decompiler.addToken(125);
            this.decompiler.addToken(88);
            String identifier = catchClause.getVarName().getIdentifier();
            this.decompiler.addName(identifier);
            AstNode catchCondition = catchClause.getCatchCondition();
            if (catchCondition != null) {
                this.decompiler.addName(" ");
                this.decompiler.addToken(113);
                emptyExpression = transform(catchCondition);
            } else {
                emptyExpression = new EmptyExpression();
            }
            this.decompiler.addToken(89);
            this.decompiler.addEOL(86);
            Node nodeTransform3 = transform(catchClause.getBody());
            this.decompiler.addEOL(87);
            block.addChildToBack(createCatch(identifier, emptyExpression, nodeTransform3, catchClause.getLineno()));
        }
        if (tryStatement.getFinallyBlock() != null) {
            this.decompiler.addToken(126);
            this.decompiler.addEOL(86);
            nodeTransform = transform(tryStatement.getFinallyBlock());
            this.decompiler.addEOL(87);
        } else {
            nodeTransform = null;
        }
        return createTryCatchFinally(nodeTransform2, block, nodeTransform, tryStatement.getLineno());
    }

    private Node transformUnary(UnaryExpression unaryExpression) throws RuntimeException {
        int type = unaryExpression.getType();
        if (type == 75) {
            return transformDefaultXmlNamepace(unaryExpression);
        }
        if (unaryExpression.isPrefix()) {
            this.decompiler.addToken(type);
        }
        Node nodeTransform = transform(unaryExpression.getOperand());
        if (unaryExpression.isPostfix()) {
            this.decompiler.addToken(type);
        }
        return (type == 107 || type == 108) ? createIncDec(type, unaryExpression.isPostfix(), nodeTransform) : createUnary(type, nodeTransform);
    }

    private Node transformVariableInitializers(VariableDeclaration variableDeclaration) throws RuntimeException {
        Node nodeTransform;
        Node nodeTransform2;
        List<VariableInitializer> variables = variableDeclaration.getVariables();
        int size = variables.size();
        int i2 = 0;
        for (VariableInitializer variableInitializer : variables) {
            AstNode target = variableInitializer.getTarget();
            AstNode initializer = variableInitializer.getInitializer();
            if (variableInitializer.isDestructuring()) {
                decompile(target);
                nodeTransform = target;
            } else {
                nodeTransform = transform(target);
            }
            if (initializer != null) {
                this.decompiler.addToken(91);
                nodeTransform2 = transform(initializer);
            } else {
                nodeTransform2 = null;
            }
            if (!variableInitializer.isDestructuring()) {
                if (nodeTransform2 != null) {
                    nodeTransform.addChildToBack(nodeTransform2);
                }
                variableDeclaration.addChildToBack(nodeTransform);
            } else if (nodeTransform2 == null) {
                variableDeclaration.addChildToBack(nodeTransform);
            } else {
                variableDeclaration.addChildToBack(createDestructuringAssignment(variableDeclaration.getType(), nodeTransform, nodeTransform2));
            }
            int i3 = i2 + 1;
            if (i2 < size - 1) {
                this.decompiler.addToken(90);
            }
            i2 = i3;
        }
        return variableDeclaration;
    }

    private Node transformVariables(VariableDeclaration variableDeclaration) throws RuntimeException {
        this.decompiler.addToken(variableDeclaration.getType());
        transformVariableInitializers(variableDeclaration);
        AstNode parent = variableDeclaration.getParent();
        if (!(parent instanceof Loop) && !(parent instanceof LetNode)) {
            this.decompiler.addEOL(83);
        }
        return variableDeclaration;
    }

    private Node transformWhileLoop(WhileLoop whileLoop) throws RuntimeException {
        this.decompiler.addToken(118);
        whileLoop.setType(133);
        pushScope(whileLoop);
        try {
            this.decompiler.addToken(88);
            Node nodeTransform = transform(whileLoop.getCondition());
            this.decompiler.addToken(89);
            this.decompiler.addEOL(86);
            Node nodeTransform2 = transform(whileLoop.getBody());
            this.decompiler.addEOL(87);
            return createLoop(whileLoop, 1, nodeTransform2, nodeTransform, null, null);
        } finally {
            popScope();
        }
    }

    private Node transformWith(WithStatement withStatement) throws RuntimeException {
        this.decompiler.addToken(124);
        this.decompiler.addToken(88);
        Node nodeTransform = transform(withStatement.getExpression());
        this.decompiler.addToken(89);
        this.decompiler.addEOL(86);
        Node nodeTransform2 = transform(withStatement.getStatement());
        this.decompiler.addEOL(87);
        return createWith(nodeTransform, nodeTransform2, withStatement.getLineno());
    }

    private Node transformXmlLiteral(XmlLiteral xmlLiteral) throws RuntimeException {
        Node node = new Node(30, xmlLiteral.getLineno());
        List<XmlFragment> fragments = xmlLiteral.getFragments();
        node.addChildToBack(createName(((XmlString) fragments.get(0)).getXml().trim().startsWith("<>") ? "XMLList" : "XML"));
        Node nodeCreateString = null;
        for (XmlFragment xmlFragment : fragments) {
            if (xmlFragment instanceof XmlString) {
                String xml = ((XmlString) xmlFragment).getXml();
                this.decompiler.addName(xml);
                nodeCreateString = nodeCreateString == null ? createString(xml) : createBinary(21, nodeCreateString, createString(xml));
            } else {
                XmlExpression xmlExpression = (XmlExpression) xmlFragment;
                boolean zIsXmlAttribute = xmlExpression.isXmlAttribute();
                this.decompiler.addToken(86);
                Node nodeCreateString2 = xmlExpression.getExpression() instanceof EmptyExpression ? createString("") : transform(xmlExpression.getExpression());
                this.decompiler.addToken(87);
                nodeCreateString = createBinary(21, nodeCreateString, zIsXmlAttribute ? createBinary(21, createBinary(21, createString("\""), createUnary(76, nodeCreateString2)), createString("\"")) : createUnary(77, nodeCreateString2));
            }
        }
        node.addChildToBack(nodeCreateString);
        return node;
    }

    private Node transformXmlMemberGet(XmlMemberGet xmlMemberGet) throws RuntimeException {
        XmlRef memberRef = xmlMemberGet.getMemberRef();
        Node nodeTransform = transform(xmlMemberGet.getLeft());
        int i2 = memberRef.isAttributeAccess() ? 2 : 0;
        if (xmlMemberGet.getType() == 144) {
            i2 |= 4;
            this.decompiler.addToken(144);
        } else {
            this.decompiler.addToken(109);
        }
        return transformXmlRef(nodeTransform, memberRef, i2);
    }

    private Node transformXmlRef(XmlRef xmlRef) {
        return transformXmlRef(null, xmlRef, xmlRef.isAttributeAccess() ? 2 : 0);
    }

    private Node transformYield(Yield yield) throws RuntimeException {
        this.decompiler.addToken(73);
        Node nodeTransform = yield.getValue() == null ? null : transform(yield.getValue());
        return nodeTransform != null ? new Node(73, nodeTransform, yield.getLineno()) : new Node(73, yield.getLineno());
    }

    void decompile(AstNode astNode) throws RuntimeException {
        int type = astNode.getType();
        if (type == 33) {
            decompilePropertyGet((PropertyGet) astNode);
        }
        if (type == 36) {
            decompileElementGet((ElementGet) astNode);
            return;
        }
        if (type == 43) {
            this.decompiler.addToken(astNode.getType());
            return;
        }
        if (type != 129) {
            if (type == 66) {
                decompileArrayLiteral((ArrayLiteral) astNode);
                return;
            }
            if (type == 67) {
                decompileObjectLiteral((ObjectLiteral) astNode);
                return;
            }
            switch (type) {
                case 39:
                    this.decompiler.addName(((Name) astNode).getIdentifier());
                    break;
                case 40:
                    this.decompiler.addNumber(((NumberLiteral) astNode).getNumber());
                    break;
                case 41:
                    this.decompiler.addString(((StringLiteral) astNode).getValue());
                    break;
                default:
                    Kit.codeBug("unexpected token: " + Token.typeToName(astNode.getType()));
                    break;
            }
        }
    }

    void decompileArrayLiteral(ArrayLiteral arrayLiteral) throws RuntimeException {
        this.decompiler.addToken(84);
        List<AstNode> elements = arrayLiteral.getElements();
        int size = elements.size();
        for (int i2 = 0; i2 < size; i2++) {
            decompile(elements.get(i2));
            if (i2 < size - 1) {
                this.decompiler.addToken(90);
            }
        }
        this.decompiler.addToken(85);
    }

    void decompileElementGet(ElementGet elementGet) throws RuntimeException {
        decompile(elementGet.getTarget());
        this.decompiler.addToken(84);
        decompile(elementGet.getElement());
        this.decompiler.addToken(85);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0029  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x007d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    org.mozilla.javascript.Node decompileFunctionHeader(org.mozilla.javascript.ast.FunctionNode r9) throws java.lang.RuntimeException {
        /*
            r8 = this;
            org.mozilla.javascript.ast.Name r0 = r9.getFunctionName()
            if (r0 == 0) goto L10
            org.mozilla.javascript.Decompiler r0 = r8.decompiler
            java.lang.String r1 = r9.getName()
            r0.addName(r1)
            goto L1f
        L10:
            org.mozilla.javascript.ast.AstNode r0 = r9.getMemberExprNode()
            if (r0 == 0) goto L1f
            org.mozilla.javascript.ast.AstNode r0 = r9.getMemberExprNode()
            org.mozilla.javascript.Node r0 = r8.transform(r0)
            goto L20
        L1f:
            r0 = 0
        L20:
            int r1 = r9.getFunctionType()
            r2 = 4
            r3 = 0
            r4 = 1
            if (r1 != r2) goto L2b
            r1 = r4
            goto L2c
        L2b:
            r1 = r3
        L2c:
            if (r1 == 0) goto L37
            int r2 = r9.getLp()
            r5 = -1
            if (r2 != r5) goto L37
            r2 = r4
            goto L38
        L37:
            r2 = r3
        L38:
            if (r2 != 0) goto L41
            org.mozilla.javascript.Decompiler r5 = r8.decompiler
            r6 = 88
            r5.addToken(r6)
        L41:
            java.util.List r5 = r9.getParams()
        L45:
            int r6 = r5.size()
            if (r3 >= r6) goto L65
            java.lang.Object r6 = r5.get(r3)
            org.mozilla.javascript.ast.AstNode r6 = (org.mozilla.javascript.ast.AstNode) r6
            r8.decompile(r6)
            int r6 = r5.size()
            int r6 = r6 - r4
            if (r3 >= r6) goto L62
            org.mozilla.javascript.Decompiler r6 = r8.decompiler
            r7 = 90
            r6.addToken(r7)
        L62:
            int r3 = r3 + 1
            goto L45
        L65:
            if (r2 != 0) goto L6e
            org.mozilla.javascript.Decompiler r2 = r8.decompiler
            r3 = 89
            r2.addToken(r3)
        L6e:
            if (r1 == 0) goto L77
            org.mozilla.javascript.Decompiler r1 = r8.decompiler
            r2 = 165(0xa5, float:2.31E-43)
            r1.addToken(r2)
        L77:
            boolean r9 = r9.isExpressionClosure()
            if (r9 != 0) goto L84
            org.mozilla.javascript.Decompiler r9 = r8.decompiler
            r1 = 86
            r9.addEOL(r1)
        L84:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.IRFactory.decompileFunctionHeader(org.mozilla.javascript.ast.FunctionNode):org.mozilla.javascript.Node");
    }

    void decompileObjectLiteral(ObjectLiteral objectLiteral) throws RuntimeException {
        this.decompiler.addToken(86);
        List<ObjectProperty> elements = objectLiteral.getElements();
        int size = elements.size();
        for (int i2 = 0; i2 < size; i2++) {
            ObjectProperty objectProperty = elements.get(i2);
            boolean zEquals = Boolean.TRUE.equals(objectProperty.getProp(26));
            decompile(objectProperty.getLeft());
            if (!zEquals) {
                this.decompiler.addToken(104);
                decompile(objectProperty.getRight());
            }
            if (i2 < size - 1) {
                this.decompiler.addToken(90);
            }
        }
        this.decompiler.addToken(87);
    }

    void decompilePropertyGet(PropertyGet propertyGet) throws RuntimeException {
        decompile(propertyGet.getTarget());
        this.decompiler.addToken(109);
        decompile(propertyGet.getProperty());
    }

    /* JADX WARN: Multi-variable type inference failed */
    boolean isDestructuring(Node node) {
        return (node instanceof DestructuringForm) && ((DestructuringForm) node).isDestructuring();
    }

    public Node transform(AstNode astNode) {
        int type = astNode.getType();
        if (type == 66) {
            return transformArrayLiteral((ArrayLiteral) astNode);
        }
        if (type == 67) {
            return transformObjectLiteral((ObjectLiteral) astNode);
        }
        if (type == 129) {
            return astNode;
        }
        if (type == 130) {
            return transformBlock(astNode);
        }
        switch (type) {
            case 4:
                return transformReturn((ReturnStatement) astNode);
            case 30:
                return transformNewExpr((NewExpression) astNode);
            case 33:
                return transformPropertyGet((PropertyGet) astNode);
            case 36:
                return transformElementGet((ElementGet) astNode);
            case 48:
                return transformRegExp((RegExpLiteral) astNode);
            case 50:
                return transformThrow((ThrowStatement) astNode);
            case 73:
                return transformYield((Yield) astNode);
            case 82:
                return transformTry((TryStatement) astNode);
            case 103:
                return transformCondExpr((ConditionalExpression) astNode);
            case 110:
                return transformFunction((FunctionNode) astNode);
            case 113:
                return transformIf((IfStatement) astNode);
            case 115:
                return transformSwitch((SwitchStatement) astNode);
            case 124:
                return transformWith((WithStatement) astNode);
            case 137:
                return transformScript((ScriptNode) astNode);
            case 158:
                return transformArrayComp((ArrayComprehension) astNode);
            case 161:
                break;
            case 163:
                return transformGenExpr((GeneratorExpression) astNode);
            default:
                switch (type) {
                    case 38:
                        return transformFunctionCall((FunctionCall) astNode);
                    case 39:
                        return transformName((Name) astNode);
                    case 40:
                        return transformNumber((NumberLiteral) astNode);
                    case 41:
                        return transformString((StringLiteral) astNode);
                    case 42:
                    case 43:
                    case 44:
                    case 45:
                        break;
                    default:
                        switch (type) {
                            case 118:
                                return transformWhileLoop((WhileLoop) astNode);
                            case 119:
                                return transformDoLoop((DoLoop) astNode);
                            case 120:
                                return astNode instanceof ForInLoop ? transformForInLoop((ForInLoop) astNode) : transformForLoop((ForLoop) astNode);
                            case 121:
                                return transformBreak((BreakStatement) astNode);
                            case 122:
                                return transformContinue((ContinueStatement) astNode);
                            default:
                                if (astNode instanceof ExpressionStatement) {
                                    return transformExprStmt((ExpressionStatement) astNode);
                                }
                                if (astNode instanceof Assignment) {
                                    return transformAssignment((Assignment) astNode);
                                }
                                if (astNode instanceof UnaryExpression) {
                                    return transformUnary((UnaryExpression) astNode);
                                }
                                if (astNode instanceof XmlMemberGet) {
                                    return transformXmlMemberGet((XmlMemberGet) astNode);
                                }
                                if (astNode instanceof InfixExpression) {
                                    return transformInfix((InfixExpression) astNode);
                                }
                                if (astNode instanceof VariableDeclaration) {
                                    return transformVariables((VariableDeclaration) astNode);
                                }
                                if (astNode instanceof ParenthesizedExpression) {
                                    return transformParenExpr((ParenthesizedExpression) astNode);
                                }
                                if (astNode instanceof LabeledStatement) {
                                    return transformLabeledStatement((LabeledStatement) astNode);
                                }
                                if (astNode instanceof LetNode) {
                                    return transformLetNode((LetNode) astNode);
                                }
                                if (astNode instanceof XmlRef) {
                                    return transformXmlRef((XmlRef) astNode);
                                }
                                if (astNode instanceof XmlLiteral) {
                                    return transformXmlLiteral((XmlLiteral) astNode);
                                }
                                throw new IllegalArgumentException("Can't transform: " + astNode);
                        }
                }
        }
        return transformLiteral(astNode);
    }

    public ScriptNode transformTree(AstRoot astRoot) {
        this.currentScriptOrFn = astRoot;
        this.inUseStrictDirective = astRoot.isInStrictMode();
        int currentOffset = this.decompiler.getCurrentOffset();
        ScriptNode scriptNode = (ScriptNode) transform(astRoot);
        scriptNode.setEncodedSourceBounds(currentOffset, this.decompiler.getCurrentOffset());
        if (this.compilerEnv.isGeneratingSource()) {
            scriptNode.setEncodedSource(this.decompiler.getEncodedSource());
        }
        this.decompiler = null;
        return scriptNode;
    }

    public IRFactory(CompilerEnvirons compilerEnvirons) {
        this(compilerEnvirons, compilerEnvirons.getErrorReporter());
    }

    private Node transformXmlRef(Node node, XmlRef xmlRef, int i2) throws RuntimeException {
        if ((i2 & 2) != 0) {
            this.decompiler.addToken(148);
        }
        Name namespace = xmlRef.getNamespace();
        String identifier = namespace != null ? namespace.getIdentifier() : null;
        if (identifier != null) {
            this.decompiler.addName(identifier);
            this.decompiler.addToken(145);
        }
        if (xmlRef instanceof XmlPropRef) {
            String identifier2 = ((XmlPropRef) xmlRef).getPropName().getIdentifier();
            this.decompiler.addName(identifier2);
            return createPropertyGet(node, identifier, identifier2, i2);
        }
        this.decompiler.addToken(84);
        Node nodeTransform = transform(((XmlElemRef) xmlRef).getExpression());
        this.decompiler.addToken(85);
        return createElementGet(node, identifier, nodeTransform, i2);
    }

    public IRFactory(CompilerEnvirons compilerEnvirons, ErrorReporter errorReporter) {
        super(compilerEnvirons, errorReporter);
        this.decompiler = new Decompiler();
    }
}
