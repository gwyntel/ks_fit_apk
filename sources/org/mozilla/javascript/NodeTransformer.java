package org.mozilla.javascript;

import java.util.ArrayList;
import java.util.List;
import org.mozilla.javascript.ast.FunctionNode;
import org.mozilla.javascript.ast.Scope;
import org.mozilla.javascript.ast.ScriptNode;

/* loaded from: classes5.dex */
public class NodeTransformer {
    private boolean hasFinally;
    private ObjArray loopEnds;
    private ObjArray loops;

    private static Node addBeforeCurrent(Node node, Node node2, Node node3, Node node4) throws RuntimeException {
        if (node2 == null) {
            if (node3 != node.getFirstChild()) {
                Kit.codeBug();
            }
            node.addChildToFront(node4);
        } else {
            if (node3 != node2.getNext()) {
                Kit.codeBug();
            }
            node.addChildAfter(node4, node2);
        }
        return node4;
    }

    private static Node replaceCurrent(Node node, Node node2, Node node3, Node node4) throws RuntimeException {
        if (node2 == null) {
            if (node3 != node.getFirstChild()) {
                Kit.codeBug();
            }
            node.replaceChild(node3, node4);
        } else if (node2.next == node3) {
            node.replaceChildAfter(node2, node4);
        } else {
            node.replaceChild(node3, node4);
        }
        return node4;
    }

    private void transformCompilationUnit(ScriptNode scriptNode, boolean z2) throws RuntimeException {
        this.loops = new ObjArray();
        this.loopEnds = new ObjArray();
        this.hasFinally = false;
        boolean z3 = scriptNode.getType() != 110 || ((FunctionNode) scriptNode).requiresActivation();
        scriptNode.flattenSymbolTable(!z3);
        transformCompilationUnit_r(scriptNode, scriptNode, scriptNode, z3, z2);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:109:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0275  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x037a  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0161  */
    /* JADX WARN: Type inference failed for: r10v0 */
    /* JADX WARN: Type inference failed for: r10v1, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r10v3 */
    /* JADX WARN: Type inference failed for: r13v7, types: [org.mozilla.javascript.ast.Scope] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void transformCompilationUnit_r(org.mozilla.javascript.ast.ScriptNode r19, org.mozilla.javascript.Node r20, org.mozilla.javascript.ast.Scope r21, boolean r22, boolean r23) throws java.lang.RuntimeException {
        /*
            Method dump skipped, instructions count: 984
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NodeTransformer.transformCompilationUnit_r(org.mozilla.javascript.ast.ScriptNode, org.mozilla.javascript.Node, org.mozilla.javascript.ast.Scope, boolean, boolean):void");
    }

    public final void transform(ScriptNode scriptNode, CompilerEnvirons compilerEnvirons) {
        transform(scriptNode, false, compilerEnvirons);
    }

    protected void visitCall(Node node, ScriptNode scriptNode) {
    }

    protected Node visitLet(boolean z2, Node node, Node node2, Node node3) throws RuntimeException {
        Node nodeReplaceCurrent;
        Node node4;
        Node firstChild;
        Node node5;
        Node firstChild2;
        Node firstChild3 = node3.getFirstChild();
        Node next = firstChild3.getNext();
        node3.removeChild(firstChild3);
        node3.removeChild(next);
        int i2 = 159;
        boolean z3 = node3.getType() == 159;
        int i3 = 154;
        int i4 = 90;
        if (z2) {
            nodeReplaceCurrent = replaceCurrent(node, node2, node3, new Node(z3 ? 160 : 130));
            ArrayList arrayList = new ArrayList();
            Node node6 = new Node(67);
            Node firstChild4 = firstChild3.getFirstChild();
            while (firstChild4 != null) {
                if (firstChild4.getType() == i2) {
                    List list = (List) firstChild4.getProp(22);
                    Node firstChild5 = firstChild4.getFirstChild();
                    if (firstChild5.getType() != i3) {
                        throw Kit.codeBug();
                    }
                    node5 = z3 ? new Node(i4, firstChild5.getNext(), next) : new Node(130, new Node(134, firstChild5.getNext()), next);
                    if (list != null) {
                        arrayList.addAll(list);
                        for (int i5 = 0; i5 < list.size(); i5++) {
                            node6.addChildToBack(new Node(127, Node.newNumber(0.0d)));
                        }
                    }
                    firstChild2 = firstChild5.getFirstChild();
                } else {
                    node5 = next;
                    firstChild2 = firstChild4;
                }
                if (firstChild2.getType() != 39) {
                    throw Kit.codeBug();
                }
                arrayList.add(ScriptRuntime.getIndexObject(firstChild2.getString()));
                Node firstChild6 = firstChild2.getFirstChild();
                if (firstChild6 == null) {
                    firstChild6 = new Node(127, Node.newNumber(0.0d));
                }
                node6.addChildToBack(firstChild6);
                firstChild4 = firstChild4.getNext();
                next = node5;
                i2 = 159;
                i3 = 154;
                i4 = 90;
            }
            node6.putProp(12, arrayList.toArray());
            nodeReplaceCurrent.addChildToBack(new Node(2, node6));
            nodeReplaceCurrent.addChildToBack(new Node(124, next));
            nodeReplaceCurrent.addChildToBack(new Node(3));
        } else {
            nodeReplaceCurrent = replaceCurrent(node, node2, node3, new Node(z3 ? 90 : 130));
            Node node7 = new Node(90);
            Node firstChild7 = firstChild3.getFirstChild();
            while (firstChild7 != null) {
                if (firstChild7.getType() == 159) {
                    Node firstChild8 = firstChild7.getFirstChild();
                    if (firstChild8.getType() != 154) {
                        throw Kit.codeBug();
                    }
                    node4 = z3 ? new Node(90, firstChild8.getNext(), next) : new Node(130, new Node(134, firstChild8.getNext()), next);
                    Scope.joinScopes((Scope) firstChild7, (Scope) node3);
                    firstChild = firstChild8.getFirstChild();
                } else {
                    node4 = next;
                    firstChild = firstChild7;
                }
                if (firstChild.getType() != 39) {
                    throw Kit.codeBug();
                }
                Node nodeNewString = Node.newString(firstChild.getString());
                nodeNewString.setScope((Scope) node3);
                Node firstChild9 = firstChild.getFirstChild();
                if (firstChild9 == null) {
                    firstChild9 = new Node(127, Node.newNumber(0.0d));
                }
                node7.addChildToBack(new Node(56, nodeNewString, firstChild9));
                firstChild7 = firstChild7.getNext();
                next = node4;
            }
            if (z3) {
                nodeReplaceCurrent.addChildToBack(node7);
                node3.setType(90);
                nodeReplaceCurrent.addChildToBack(node3);
                node3.addChildToBack(next);
                if (next instanceof Scope) {
                    Scope scope = (Scope) next;
                    Scope parentScope = scope.getParentScope();
                    Scope scope2 = (Scope) node3;
                    scope.setParentScope(scope2);
                    scope2.setParentScope(parentScope);
                }
            } else {
                nodeReplaceCurrent.addChildToBack(new Node(134, node7));
                node3.setType(130);
                nodeReplaceCurrent.addChildToBack(node3);
                node3.addChildrenToBack(next);
                if (next instanceof Scope) {
                    Scope scope3 = (Scope) next;
                    Scope parentScope2 = scope3.getParentScope();
                    Scope scope4 = (Scope) node3;
                    scope3.setParentScope(scope4);
                    scope4.setParentScope(parentScope2);
                }
            }
        }
        return nodeReplaceCurrent;
    }

    protected void visitNew(Node node, ScriptNode scriptNode) {
    }

    public final void transform(ScriptNode scriptNode, boolean z2, CompilerEnvirons compilerEnvirons) throws RuntimeException {
        if (compilerEnvirons.getLanguageVersion() >= 200 && scriptNode.isInStrictMode()) {
            z2 = true;
        }
        transformCompilationUnit(scriptNode, z2);
        for (int i2 = 0; i2 != scriptNode.getFunctionCount(); i2++) {
            transform(scriptNode.getFunctionNode(i2), z2, compilerEnvirons);
        }
    }
}
