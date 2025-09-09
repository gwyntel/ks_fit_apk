package org.mozilla.javascript.optimizer;

import androidx.core.app.NotificationCompat;
import com.umeng.analytics.pro.bg;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.mozilla.classfile.ClassFileWriter;
import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Kit;
import org.mozilla.javascript.Node;
import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.FunctionNode;
import org.mozilla.javascript.ast.Jump;
import org.mozilla.javascript.ast.ScriptNode;

/* loaded from: classes5.dex */
class BodyCodegen {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ECMAERROR_EXCEPTION = 2;
    private static final int EVALUATOR_EXCEPTION = 1;
    private static final int EXCEPTION_MAX = 5;
    private static final int FINALLY_EXCEPTION = 4;
    static final int GENERATOR_START = 0;
    static final int GENERATOR_TERMINATE = -1;
    static final int GENERATOR_YIELD_START = 1;
    private static final int JAVASCRIPT_EXCEPTION = 0;
    private static final int MAX_LOCALS = 1024;
    private static final int THROWABLE_EXCEPTION = 3;
    private short argsLocal;
    ClassFileWriter cfw;
    Codegen codegen;
    CompilerEnvirons compilerEnv;
    private short contextLocal;
    private int enterAreaStartLabel;
    private int epilogueLabel;
    private Map<Node, FinallyReturnPoint> finallys;
    private short firstFreeLocal;
    private OptFunctionNode fnCurrent;
    private short funObjLocal;
    private short generatorStateLocal;
    private int generatorSwitch;
    private boolean hasVarsInRegs;
    private boolean inDirectCallFunction;
    private boolean inLocalBlock;
    private boolean isGenerator;
    private boolean itsForcedObjectParameters;
    private int itsLineNumber;
    private short itsOneArgArray;
    private short itsZeroArgArray;
    private List<Node> literals;
    private int[] locals;
    private short localsMax;
    private short operationLocal;
    private short popvLocal;
    private int savedCodeOffset;
    ScriptNode scriptOrFn;
    public int scriptOrFnIndex;
    private short thisObjLocal;
    private short[] varRegisters;
    private short variableObjectLocal;
    private ExceptionManager exceptionManager = new ExceptionManager();
    private int maxLocals = 0;
    private int maxStack = 0;

    private class ExceptionManager {
        private LinkedList<ExceptionInfo> exceptionInfo = new LinkedList<>();

        private class ExceptionInfo {
            Node finallyBlock;
            Jump node;
            int[] handlerLabels = new int[5];
            int[] exceptionStarts = new int[5];
            Node currentFinally = null;

            ExceptionInfo(Jump jump, Node node) {
                this.node = jump;
                this.finallyBlock = node;
            }
        }

        ExceptionManager() {
        }

        private void endCatch(ExceptionInfo exceptionInfo, int i2, int i3) {
            int i4 = exceptionInfo.exceptionStarts[i2];
            if (i4 == 0) {
                throw new IllegalStateException("bad exception start");
            }
            if (BodyCodegen.this.cfw.getLabelPC(i4) != BodyCodegen.this.cfw.getLabelPC(i3)) {
                BodyCodegen bodyCodegen = BodyCodegen.this;
                bodyCodegen.cfw.addExceptionHandler(exceptionInfo.exceptionStarts[i2], i3, exceptionInfo.handlerLabels[i2], bodyCodegen.exceptionTypeToName(i2));
            }
        }

        private ExceptionInfo getTop() {
            return this.exceptionInfo.getLast();
        }

        void addHandler(int i2, int i3, int i4) {
            ExceptionInfo top2 = getTop();
            top2.handlerLabels[i2] = i3;
            top2.exceptionStarts[i2] = i4;
        }

        void markInlineFinallyEnd(Node node, int i2) {
            LinkedList<ExceptionInfo> linkedList = this.exceptionInfo;
            ListIterator<ExceptionInfo> listIterator = linkedList.listIterator(linkedList.size());
            while (listIterator.hasPrevious()) {
                ExceptionInfo exceptionInfoPrevious = listIterator.previous();
                for (int i3 = 0; i3 < 5; i3++) {
                    if (exceptionInfoPrevious.handlerLabels[i3] != 0 && exceptionInfoPrevious.currentFinally == node) {
                        exceptionInfoPrevious.exceptionStarts[i3] = i2;
                        exceptionInfoPrevious.currentFinally = null;
                    }
                }
                if (exceptionInfoPrevious.finallyBlock == node) {
                    return;
                }
            }
        }

        void markInlineFinallyStart(Node node, int i2) {
            LinkedList<ExceptionInfo> linkedList = this.exceptionInfo;
            ListIterator<ExceptionInfo> listIterator = linkedList.listIterator(linkedList.size());
            while (listIterator.hasPrevious()) {
                ExceptionInfo exceptionInfoPrevious = listIterator.previous();
                for (int i3 = 0; i3 < 5; i3++) {
                    if (exceptionInfoPrevious.handlerLabels[i3] != 0 && exceptionInfoPrevious.currentFinally == null) {
                        endCatch(exceptionInfoPrevious, i3, i2);
                        exceptionInfoPrevious.exceptionStarts[i3] = 0;
                        exceptionInfoPrevious.currentFinally = node;
                    }
                }
                if (exceptionInfoPrevious.finallyBlock == node) {
                    return;
                }
            }
        }

        void popExceptionInfo() {
            this.exceptionInfo.removeLast();
        }

        void pushExceptionInfo(Jump jump) {
            this.exceptionInfo.add(new ExceptionInfo(jump, BodyCodegen.this.getFinallyAtTarget(jump.getFinally())));
        }

        int removeHandler(int i2, int i3) {
            ExceptionInfo top2 = getTop();
            int i4 = top2.handlerLabels[i2];
            if (i4 == 0) {
                return 0;
            }
            endCatch(top2, i2, i3);
            top2.handlerLabels[i2] = 0;
            return i4;
        }

        void setHandlers(int[] iArr, int i2) {
            getTop();
            for (int i3 = 0; i3 < iArr.length; i3++) {
                int i4 = iArr[i3];
                if (i4 != 0) {
                    addHandler(i3, i4, i2);
                }
            }
        }
    }

    static class FinallyReturnPoint {
        public List<Integer> jsrPoints = new ArrayList();
        public int tableLabel = 0;

        FinallyReturnPoint() {
        }
    }

    BodyCodegen() {
    }

    private void addDoubleWrap() {
        addOptRuntimeInvoke("wrapDouble", "(D)Ljava/lang/Double;");
    }

    private void addGoto(Node node, int i2) throws RuntimeException {
        this.cfw.add(i2, getTargetLabel(node));
    }

    private void addGotoWithReturn(Node node) throws RuntimeException {
        FinallyReturnPoint finallyReturnPoint = this.finallys.get(node);
        this.cfw.addLoadConstant(finallyReturnPoint.jsrPoints.size());
        addGoto(node, 167);
        int iAcquireLabel = this.cfw.acquireLabel();
        this.cfw.markLabel(iAcquireLabel);
        finallyReturnPoint.jsrPoints.add(Integer.valueOf(iAcquireLabel));
    }

    private void addInstructionCount() throws RuntimeException {
        addInstructionCount(Math.max(this.cfw.getCurrentCodeOffset() - this.savedCodeOffset, 1));
    }

    private void addJumpedBooleanWrap(int i2, int i3) throws RuntimeException {
        this.cfw.markLabel(i3);
        int iAcquireLabel = this.cfw.acquireLabel();
        this.cfw.add(178, "java/lang/Boolean", "FALSE", "Ljava/lang/Boolean;");
        this.cfw.add(167, iAcquireLabel);
        this.cfw.markLabel(i2);
        this.cfw.add(178, "java/lang/Boolean", "TRUE", "Ljava/lang/Boolean;");
        this.cfw.markLabel(iAcquireLabel);
        this.cfw.adjustStackTop(-1);
    }

    private void addLoadPropertyIds(Object[] objArr, int i2) throws RuntimeException {
        addNewObjectArray(i2);
        for (int i3 = 0; i3 != i2; i3++) {
            this.cfw.add(89);
            this.cfw.addPush(i3);
            Object obj = objArr[i3];
            if (obj instanceof String) {
                this.cfw.addPush((String) obj);
            } else {
                this.cfw.addPush(((Integer) obj).intValue());
                addScriptRuntimeInvoke("wrapInt", "(I)Ljava/lang/Integer;");
            }
            this.cfw.add(83);
        }
    }

    private void addLoadPropertyValues(Node node, Node node2, int i2) throws RuntimeException {
        int i3 = 0;
        if (!this.isGenerator) {
            addNewObjectArray(i2);
            while (i3 != i2) {
                this.cfw.add(89);
                this.cfw.addPush(i3);
                int type = node2.getType();
                if (type == 152 || type == 153 || type == 164) {
                    generateExpression(node2.getFirstChild(), node);
                } else {
                    generateExpression(node2, node);
                }
                this.cfw.add(83);
                node2 = node2.getNext();
                i3++;
            }
            return;
        }
        for (int i4 = 0; i4 != i2; i4++) {
            int type2 = node2.getType();
            if (type2 == 152 || type2 == 153 || type2 == 164) {
                generateExpression(node2.getFirstChild(), node);
            } else {
                generateExpression(node2, node);
            }
            node2 = node2.getNext();
        }
        addNewObjectArray(i2);
        while (i3 != i2) {
            this.cfw.add(90);
            this.cfw.add(95);
            this.cfw.addPush((i2 - i3) - 1);
            this.cfw.add(95);
            this.cfw.add(83);
            i3++;
        }
    }

    private void addNewObjectArray(int i2) throws RuntimeException {
        if (i2 != 0) {
            this.cfw.addPush(i2);
            this.cfw.add(189, "java/lang/Object");
            return;
        }
        short s2 = this.itsZeroArgArray;
        if (s2 >= 0) {
            this.cfw.addALoad(s2);
        } else {
            this.cfw.add(178, "org/mozilla/javascript/ScriptRuntime", "emptyArgs", "[Ljava/lang/Object;");
        }
    }

    private void addObjectToDouble() {
        addScriptRuntimeInvoke("toNumber", "(Ljava/lang/Object;)D");
    }

    private void addOptRuntimeInvoke(String str, String str2) {
        this.cfw.addInvoke(184, "org/mozilla/javascript/optimizer/OptRuntime", str, str2);
    }

    private void addScriptRuntimeInvoke(String str, String str2) {
        this.cfw.addInvoke(184, "org.mozilla.javascript.ScriptRuntime", str, str2);
    }

    private void dcpLoadAsNumber(int i2) throws RuntimeException {
        this.cfw.addALoad(i2);
        this.cfw.add(178, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
        int iAcquireLabel = this.cfw.acquireLabel();
        this.cfw.add(165, iAcquireLabel);
        short stackTop = this.cfw.getStackTop();
        this.cfw.addALoad(i2);
        addObjectToDouble();
        int iAcquireLabel2 = this.cfw.acquireLabel();
        this.cfw.add(167, iAcquireLabel2);
        this.cfw.markLabel(iAcquireLabel, stackTop);
        this.cfw.addDLoad(i2 + 1);
        this.cfw.markLabel(iAcquireLabel2);
    }

    private void dcpLoadAsObject(int i2) throws RuntimeException {
        this.cfw.addALoad(i2);
        this.cfw.add(178, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
        int iAcquireLabel = this.cfw.acquireLabel();
        this.cfw.add(165, iAcquireLabel);
        short stackTop = this.cfw.getStackTop();
        this.cfw.addALoad(i2);
        int iAcquireLabel2 = this.cfw.acquireLabel();
        this.cfw.add(167, iAcquireLabel2);
        this.cfw.markLabel(iAcquireLabel, stackTop);
        this.cfw.addDLoad(i2 + 1);
        addDoubleWrap();
        this.cfw.markLabel(iAcquireLabel2);
    }

    private void decReferenceWordLocal(short s2) {
        this.locals[s2] = r0[s2] - 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String exceptionTypeToName(int i2) {
        if (i2 == 0) {
            return "org/mozilla/javascript/JavaScriptException";
        }
        if (i2 == 1) {
            return "org/mozilla/javascript/EvaluatorException";
        }
        if (i2 == 2) {
            return "org/mozilla/javascript/EcmaError";
        }
        if (i2 == 3) {
            return "java/lang/Throwable";
        }
        if (i2 == 4) {
            return null;
        }
        throw Kit.codeBug();
    }

    private void genSimpleCompare(int i2, int i3, int i4) throws RuntimeException {
        if (i3 == -1) {
            throw Codegen.badTree();
        }
        switch (i2) {
            case 14:
                this.cfw.add(152);
                this.cfw.add(155, i3);
                break;
            case 15:
                this.cfw.add(152);
                this.cfw.add(158, i3);
                break;
            case 16:
                this.cfw.add(151);
                this.cfw.add(157, i3);
                break;
            case 17:
                this.cfw.add(151);
                this.cfw.add(156, i3);
                break;
            default:
                throw Codegen.badTree();
        }
        if (i4 != -1) {
            this.cfw.add(167, i4);
        }
    }

    private void generateActivationExit() throws RuntimeException {
        if (this.fnCurrent == null || this.hasVarsInRegs) {
            throw Kit.codeBug();
        }
        this.cfw.addALoad(this.contextLocal);
        addScriptRuntimeInvoke("exitActivationFunction", "(Lorg/mozilla/javascript/Context;)V");
    }

    private void generateArrayLiteralFactory(Node node, int i2) throws RuntimeException {
        String str = this.codegen.getBodyMethodName(this.scriptOrFn) + "_literal" + i2;
        initBodyGeneration();
        short s2 = this.firstFreeLocal;
        short s3 = (short) (s2 + 1);
        this.firstFreeLocal = s3;
        this.argsLocal = s2;
        this.localsMax = s3;
        this.cfw.startMethod(str, "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)Lorg/mozilla/javascript/Scriptable;", (short) 2);
        visitArrayLiteral(node, node.getFirstChild(), true);
        this.cfw.add(176);
        this.cfw.stopMethod((short) (this.localsMax + 1));
    }

    private void generateCallArgArray(Node node, Node node2, boolean z2) throws RuntimeException {
        short s2;
        int i2 = 0;
        for (Node next = node2; next != null; next = next.getNext()) {
            i2++;
        }
        if (i2 != 1 || (s2 = this.itsOneArgArray) < 0) {
            addNewObjectArray(i2);
        } else {
            this.cfw.addALoad(s2);
        }
        for (int i3 = 0; i3 != i2; i3++) {
            if (!this.isGenerator) {
                this.cfw.add(89);
                this.cfw.addPush(i3);
            }
            if (z2) {
                int iNodeIsDirectCallParameter = nodeIsDirectCallParameter(node2);
                if (iNodeIsDirectCallParameter >= 0) {
                    dcpLoadAsObject(iNodeIsDirectCallParameter);
                } else {
                    generateExpression(node2, node);
                    if (node2.getIntProp(8, -1) == 0) {
                        addDoubleWrap();
                    }
                }
            } else {
                generateExpression(node2, node);
            }
            if (this.isGenerator) {
                short newWordLocal = getNewWordLocal();
                this.cfw.addAStore(newWordLocal);
                this.cfw.add(192, "[Ljava/lang/Object;");
                this.cfw.add(89);
                this.cfw.addPush(i3);
                this.cfw.addALoad(newWordLocal);
                releaseWordLocal(newWordLocal);
            }
            this.cfw.add(83);
            node2 = node2.getNext();
        }
    }

    private void generateCatchBlock(int i2, short s2, int i3, int i4, int i5) throws RuntimeException {
        if (i5 == 0) {
            i5 = this.cfw.acquireLabel();
        }
        this.cfw.markHandler(i5);
        this.cfw.addAStore(i4);
        this.cfw.addALoad(s2);
        this.cfw.addAStore(this.variableObjectLocal);
        exceptionTypeToName(i2);
        this.cfw.add(167, i3);
    }

    private void generateCheckForThrowOrClose(int i2, boolean z2, int i3) throws RuntimeException {
        int iAcquireLabel = this.cfw.acquireLabel();
        int iAcquireLabel2 = this.cfw.acquireLabel();
        this.cfw.markLabel(iAcquireLabel);
        this.cfw.addALoad(this.argsLocal);
        generateThrowJavaScriptException();
        this.cfw.markLabel(iAcquireLabel2);
        this.cfw.addALoad(this.argsLocal);
        this.cfw.add(192, "java/lang/Throwable");
        this.cfw.add(191);
        if (i2 != -1) {
            this.cfw.markLabel(i2);
        }
        if (!z2) {
            this.cfw.markTableSwitchCase(this.generatorSwitch, i3);
        }
        this.cfw.addILoad(this.operationLocal);
        this.cfw.addLoadConstant(2);
        this.cfw.add(159, iAcquireLabel2);
        this.cfw.addILoad(this.operationLocal);
        this.cfw.addLoadConstant(1);
        this.cfw.add(159, iAcquireLabel);
    }

    private void generateEpilogue() throws RuntimeException {
        if (this.compilerEnv.isGenerateObserverCount()) {
            addInstructionCount();
        }
        if (this.isGenerator) {
            Map<Node, int[]> liveLocals = ((FunctionNode) this.scriptOrFn).getLiveLocals();
            if (liveLocals != null) {
                List<Node> resumptionPoints = ((FunctionNode) this.scriptOrFn).getResumptionPoints();
                for (int i2 = 0; i2 < resumptionPoints.size(); i2++) {
                    Node node = resumptionPoints.get(i2);
                    int[] iArr = liveLocals.get(node);
                    if (iArr != null) {
                        this.cfw.markTableSwitchCase(this.generatorSwitch, getNextGeneratorState(node));
                        generateGetGeneratorLocalsState();
                        for (int i3 = 0; i3 < iArr.length; i3++) {
                            this.cfw.add(89);
                            this.cfw.addLoadConstant(i3);
                            this.cfw.add(50);
                            this.cfw.addAStore(iArr[i3]);
                        }
                        this.cfw.add(87);
                        this.cfw.add(167, getTargetLabel(node));
                    }
                }
            }
            Map<Node, FinallyReturnPoint> map = this.finallys;
            if (map != null) {
                for (Node node2 : map.keySet()) {
                    if (node2.getType() == 126) {
                        FinallyReturnPoint finallyReturnPoint = this.finallys.get(node2);
                        this.cfw.markLabel(finallyReturnPoint.tableLabel, (short) 1);
                        int iAddTableSwitch = this.cfw.addTableSwitch(0, finallyReturnPoint.jsrPoints.size() - 1);
                        this.cfw.markTableSwitchDefault(iAddTableSwitch);
                        int i4 = 0;
                        for (int i5 = 0; i5 < finallyReturnPoint.jsrPoints.size(); i5++) {
                            this.cfw.markTableSwitchCase(iAddTableSwitch, i4);
                            this.cfw.add(167, finallyReturnPoint.jsrPoints.get(i5).intValue());
                            i4++;
                        }
                    }
                }
            }
        }
        int i6 = this.epilogueLabel;
        if (i6 != -1) {
            this.cfw.markLabel(i6);
        }
        if (this.hasVarsInRegs) {
            this.cfw.add(176);
            return;
        }
        if (this.isGenerator) {
            if (((FunctionNode) this.scriptOrFn).getResumptionPoints() != null) {
                this.cfw.markTableSwitchDefault(this.generatorSwitch);
            }
            generateSetGeneratorResumptionPoint(-1);
            this.cfw.addALoad(this.variableObjectLocal);
            addOptRuntimeInvoke("throwStopIteration", "(Ljava/lang/Object;)V");
            Codegen.pushUndefined(this.cfw);
            this.cfw.add(176);
            return;
        }
        if (this.fnCurrent == null) {
            this.cfw.addALoad(this.popvLocal);
            this.cfw.add(176);
            return;
        }
        generateActivationExit();
        this.cfw.add(176);
        int iAcquireLabel = this.cfw.acquireLabel();
        this.cfw.markHandler(iAcquireLabel);
        short newWordLocal = getNewWordLocal();
        this.cfw.addAStore(newWordLocal);
        generateActivationExit();
        this.cfw.addALoad(newWordLocal);
        releaseWordLocal(newWordLocal);
        this.cfw.add(191);
        this.cfw.addExceptionHandler(this.enterAreaStartLabel, this.epilogueLabel, iAcquireLabel, null);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0468  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x02ce  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0306  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void generateExpression(org.mozilla.javascript.Node r18, org.mozilla.javascript.Node r19) throws java.lang.RuntimeException {
        /*
            Method dump skipped, instructions count: 1654
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.optimizer.BodyCodegen.generateExpression(org.mozilla.javascript.Node, org.mozilla.javascript.Node):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void generateFunctionAndThisObj(org.mozilla.javascript.Node r5, org.mozilla.javascript.Node r6) throws java.lang.RuntimeException {
        /*
            r4 = this;
            int r0 = r5.getType()
            int r1 = r5.getType()
            r2 = 33
            if (r1 == r2) goto L4e
            r3 = 34
            if (r1 == r3) goto L49
            r3 = 36
            if (r1 == r3) goto L4e
            r0 = 39
            if (r1 == r0) goto L2a
            r4.generateExpression(r5, r6)
            org.mozilla.classfile.ClassFileWriter r5 = r4.cfw
            short r6 = r4.contextLocal
            r5.addALoad(r6)
            java.lang.String r5 = "getValueFunctionAndThis"
            java.lang.String r6 = "(Ljava/lang/Object;Lorg/mozilla/javascript/Context;)Lorg/mozilla/javascript/Callable;"
            r4.addScriptRuntimeInvoke(r5, r6)
            goto L9e
        L2a:
            java.lang.String r5 = r5.getString()
            org.mozilla.classfile.ClassFileWriter r6 = r4.cfw
            r6.addPush(r5)
            org.mozilla.classfile.ClassFileWriter r5 = r4.cfw
            short r6 = r4.contextLocal
            r5.addALoad(r6)
            org.mozilla.classfile.ClassFileWriter r5 = r4.cfw
            short r6 = r4.variableObjectLocal
            r5.addALoad(r6)
            java.lang.String r5 = "getNameFunctionAndThis"
            java.lang.String r6 = "(Ljava/lang/String;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Callable;"
            r4.addScriptRuntimeInvoke(r5, r6)
            goto L9e
        L49:
            java.lang.RuntimeException r5 = org.mozilla.javascript.Kit.codeBug()
            throw r5
        L4e:
            org.mozilla.javascript.Node r6 = r5.getFirstChild()
            r4.generateExpression(r6, r5)
            org.mozilla.javascript.Node r6 = r6.getNext()
            if (r0 != r2) goto L7a
            java.lang.String r5 = r6.getString()
            org.mozilla.classfile.ClassFileWriter r6 = r4.cfw
            r6.addPush(r5)
            org.mozilla.classfile.ClassFileWriter r5 = r4.cfw
            short r6 = r4.contextLocal
            r5.addALoad(r6)
            org.mozilla.classfile.ClassFileWriter r5 = r4.cfw
            short r6 = r4.variableObjectLocal
            r5.addALoad(r6)
            java.lang.String r5 = "getPropFunctionAndThis"
            java.lang.String r6 = "(Ljava/lang/Object;Ljava/lang/String;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Callable;"
            r4.addScriptRuntimeInvoke(r5, r6)
            goto L9e
        L7a:
            r4.generateExpression(r6, r5)
            r6 = 8
            r0 = -1
            int r5 = r5.getIntProp(r6, r0)
            if (r5 == r0) goto L89
            r4.addDoubleWrap()
        L89:
            org.mozilla.classfile.ClassFileWriter r5 = r4.cfw
            short r6 = r4.contextLocal
            r5.addALoad(r6)
            org.mozilla.classfile.ClassFileWriter r5 = r4.cfw
            short r6 = r4.variableObjectLocal
            r5.addALoad(r6)
            java.lang.String r5 = "getElemFunctionAndThis"
            java.lang.String r6 = "(Ljava/lang/Object;Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Callable;"
            r4.addScriptRuntimeInvoke(r5, r6)
        L9e:
            org.mozilla.classfile.ClassFileWriter r5 = r4.cfw
            short r6 = r4.contextLocal
            r5.addALoad(r6)
            java.lang.String r5 = "lastStoredScriptable"
            java.lang.String r6 = "(Lorg/mozilla/javascript/Context;)Lorg/mozilla/javascript/Scriptable;"
            r4.addScriptRuntimeInvoke(r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.optimizer.BodyCodegen.generateFunctionAndThisObj(org.mozilla.javascript.Node, org.mozilla.javascript.Node):void");
    }

    private void generateGenerator() throws RuntimeException {
        this.cfw.startMethod(this.codegen.getBodyMethodName(this.scriptOrFn), this.codegen.getBodyMethodSignature(this.scriptOrFn), (short) 10);
        initBodyGeneration();
        short s2 = this.firstFreeLocal;
        short s3 = (short) (s2 + 1);
        this.firstFreeLocal = s3;
        this.argsLocal = s2;
        this.localsMax = s3;
        if (this.fnCurrent != null) {
            this.cfw.addALoad(this.funObjLocal);
            this.cfw.addInvoke(185, "org/mozilla/javascript/Scriptable", "getParentScope", "()Lorg/mozilla/javascript/Scriptable;");
            this.cfw.addAStore(this.variableObjectLocal);
        }
        this.cfw.addALoad(this.funObjLocal);
        this.cfw.addALoad(this.variableObjectLocal);
        this.cfw.addALoad(this.argsLocal);
        this.cfw.addPush(this.scriptOrFn.isInStrictMode());
        addScriptRuntimeInvoke("createFunctionActivation", "(Lorg/mozilla/javascript/NativeFunction;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;Z)Lorg/mozilla/javascript/Scriptable;");
        this.cfw.addAStore(this.variableObjectLocal);
        this.cfw.add(187, this.codegen.mainClassName);
        this.cfw.add(89);
        this.cfw.addALoad(this.variableObjectLocal);
        this.cfw.addALoad(this.contextLocal);
        this.cfw.addPush(this.scriptOrFnIndex);
        this.cfw.addInvoke(183, this.codegen.mainClassName, "<init>", "(Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Context;I)V");
        generateNestedFunctionInits();
        this.cfw.addALoad(this.variableObjectLocal);
        this.cfw.addALoad(this.thisObjLocal);
        this.cfw.addLoadConstant(this.maxLocals);
        this.cfw.addLoadConstant(this.maxStack);
        addOptRuntimeInvoke("createNativeGenerator", "(Lorg/mozilla/javascript/NativeFunction;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;II)Lorg/mozilla/javascript/Scriptable;");
        this.cfw.add(176);
        this.cfw.stopMethod((short) (this.localsMax + 1));
    }

    private void generateGetGeneratorLocalsState() throws RuntimeException {
        this.cfw.addALoad(this.generatorStateLocal);
        addOptRuntimeInvoke("getGeneratorLocalsState", "(Ljava/lang/Object;)[Ljava/lang/Object;");
    }

    private void generateGetGeneratorResumptionPoint() throws RuntimeException {
        this.cfw.addALoad(this.generatorStateLocal);
        this.cfw.add(180, "org/mozilla/javascript/optimizer/OptRuntime$GeneratorState", "resumptionPoint", "I");
    }

    private void generateGetGeneratorStackState() throws RuntimeException {
        this.cfw.addALoad(this.generatorStateLocal);
        addOptRuntimeInvoke("getGeneratorStackState", "(Ljava/lang/Object;)[Ljava/lang/Object;");
    }

    private void generateIfJump(Node node, Node node2, int i2, int i3) throws RuntimeException {
        int type = node.getType();
        Node firstChild = node.getFirstChild();
        if (type == 26) {
            generateIfJump(firstChild, node, i3, i2);
            return;
        }
        if (type != 46 && type != 47) {
            if (type != 52 && type != 53) {
                if (type == 105 || type == 106) {
                    int iAcquireLabel = this.cfw.acquireLabel();
                    if (type == 106) {
                        generateIfJump(firstChild, node, iAcquireLabel, i3);
                    } else {
                        generateIfJump(firstChild, node, i2, iAcquireLabel);
                    }
                    this.cfw.markLabel(iAcquireLabel);
                    generateIfJump(firstChild.getNext(), node, i2, i3);
                    return;
                }
                switch (type) {
                    case 12:
                    case 13:
                        break;
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                        break;
                    default:
                        generateExpression(node, node2);
                        addScriptRuntimeInvoke("toBoolean", "(Ljava/lang/Object;)Z");
                        this.cfw.add(154, i2);
                        this.cfw.add(167, i3);
                        break;
                }
                return;
            }
            visitIfJumpRelOp(node, firstChild, i2, i3);
            return;
        }
        visitIfJumpEqOp(node, firstChild, i2, i3);
    }

    private void generateIntegerUnwrap() {
        this.cfw.addInvoke(182, "java/lang/Integer", "intValue", "()I");
    }

    private void generateIntegerWrap() {
        this.cfw.addInvoke(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
    }

    private void generateNestedFunctionInits() throws RuntimeException {
        int functionCount = this.scriptOrFn.getFunctionCount();
        for (int i2 = 0; i2 != functionCount; i2++) {
            OptFunctionNode optFunctionNode = OptFunctionNode.get(this.scriptOrFn, i2);
            if (optFunctionNode.fnode.getFunctionType() == 1) {
                visitFunction(optFunctionNode, 1);
            }
        }
    }

    private void generateObjectLiteralFactory(Node node, int i2) throws RuntimeException {
        String str = this.codegen.getBodyMethodName(this.scriptOrFn) + "_literal" + i2;
        initBodyGeneration();
        short s2 = this.firstFreeLocal;
        short s3 = (short) (s2 + 1);
        this.firstFreeLocal = s3;
        this.argsLocal = s2;
        this.localsMax = s3;
        this.cfw.startMethod(str, "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)Lorg/mozilla/javascript/Scriptable;", (short) 2);
        visitObjectLiteral(node, node.getFirstChild(), true);
        this.cfw.add(176);
        this.cfw.stopMethod((short) (this.localsMax + 1));
    }

    private void generatePrologue() throws RuntimeException {
        String str;
        short newWordLocal;
        if (this.inDirectCallFunction) {
            int paramCount = this.scriptOrFn.getParamCount();
            if (this.firstFreeLocal != 4) {
                Kit.codeBug();
            }
            for (int i2 = 0; i2 != paramCount; i2++) {
                short[] sArr = this.varRegisters;
                short s2 = this.firstFreeLocal;
                sArr[i2] = s2;
                this.firstFreeLocal = (short) (s2 + 3);
            }
            if (!this.fnCurrent.getParameterNumberContext()) {
                this.itsForcedObjectParameters = true;
                for (int i3 = 0; i3 != paramCount; i3++) {
                    short s3 = this.varRegisters[i3];
                    this.cfw.addALoad(s3);
                    this.cfw.add(178, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
                    int iAcquireLabel = this.cfw.acquireLabel();
                    this.cfw.add(166, iAcquireLabel);
                    this.cfw.addDLoad(s3 + 1);
                    addDoubleWrap();
                    this.cfw.addAStore(s3);
                    this.cfw.markLabel(iAcquireLabel);
                }
            }
        }
        if (this.fnCurrent != null) {
            this.cfw.addALoad(this.funObjLocal);
            this.cfw.addInvoke(185, "org/mozilla/javascript/Scriptable", "getParentScope", "()Lorg/mozilla/javascript/Scriptable;");
            this.cfw.addAStore(this.variableObjectLocal);
        }
        short s4 = this.firstFreeLocal;
        short s5 = (short) (s4 + 1);
        this.firstFreeLocal = s5;
        this.argsLocal = s4;
        this.localsMax = s5;
        if (this.isGenerator) {
            short s6 = (short) (s5 + 1);
            this.firstFreeLocal = s6;
            this.operationLocal = s5;
            this.localsMax = s6;
            this.cfw.addALoad(this.thisObjLocal);
            short s7 = this.firstFreeLocal;
            short s8 = (short) (s7 + 1);
            this.firstFreeLocal = s8;
            this.generatorStateLocal = s7;
            this.localsMax = s8;
            this.cfw.add(192, "org/mozilla/javascript/optimizer/OptRuntime$GeneratorState");
            this.cfw.add(89);
            this.cfw.addAStore(this.generatorStateLocal);
            this.cfw.add(180, "org/mozilla/javascript/optimizer/OptRuntime$GeneratorState", "thisObj", "Lorg/mozilla/javascript/Scriptable;");
            this.cfw.addAStore(this.thisObjLocal);
            if (this.epilogueLabel == -1) {
                this.epilogueLabel = this.cfw.acquireLabel();
            }
            List<Node> resumptionPoints = ((FunctionNode) this.scriptOrFn).getResumptionPoints();
            if (resumptionPoints != null) {
                generateGetGeneratorResumptionPoint();
                this.generatorSwitch = this.cfw.addTableSwitch(0, resumptionPoints.size());
                generateCheckForThrowOrClose(-1, false, 0);
            }
        }
        if (this.fnCurrent == null && this.scriptOrFn.getRegexpCount() != 0) {
            this.cfw.addALoad(this.contextLocal);
            this.cfw.addInvoke(184, this.codegen.mainClassName, "_reInit", "(Lorg/mozilla/javascript/Context;)V");
        }
        if (this.compilerEnv.isGenerateObserverCount()) {
            saveCurrentCodeOffset();
        }
        if (this.hasVarsInRegs) {
            int paramCount2 = this.scriptOrFn.getParamCount();
            if (paramCount2 > 0 && !this.inDirectCallFunction) {
                this.cfw.addALoad(this.argsLocal);
                this.cfw.add(190);
                this.cfw.addPush(paramCount2);
                int iAcquireLabel2 = this.cfw.acquireLabel();
                this.cfw.add(162, iAcquireLabel2);
                this.cfw.addALoad(this.argsLocal);
                this.cfw.addPush(paramCount2);
                addScriptRuntimeInvoke("padArguments", "([Ljava/lang/Object;I)[Ljava/lang/Object;");
                this.cfw.addAStore(this.argsLocal);
                this.cfw.markLabel(iAcquireLabel2);
            }
            int paramCount3 = this.fnCurrent.fnode.getParamCount();
            int paramAndVarCount = this.fnCurrent.fnode.getParamAndVarCount();
            boolean[] paramAndVarConst = this.fnCurrent.fnode.getParamAndVarConst();
            short s9 = -1;
            for (int i4 = 0; i4 != paramAndVarCount; i4++) {
                if (i4 < paramCount3) {
                    if (this.inDirectCallFunction) {
                        newWordLocal = -1;
                    } else {
                        newWordLocal = getNewWordLocal();
                        this.cfw.addALoad(this.argsLocal);
                        this.cfw.addPush(i4);
                        this.cfw.add(50);
                        this.cfw.addAStore(newWordLocal);
                    }
                } else if (this.fnCurrent.isNumberVar(i4)) {
                    newWordLocal = getNewWordPairLocal(paramAndVarConst[i4]);
                    this.cfw.addPush(0.0d);
                    this.cfw.addDStore(newWordLocal);
                } else {
                    newWordLocal = getNewWordLocal(paramAndVarConst[i4]);
                    if (s9 == -1) {
                        Codegen.pushUndefined(this.cfw);
                        s9 = newWordLocal;
                    } else {
                        this.cfw.addALoad(s9);
                    }
                    this.cfw.addAStore(newWordLocal);
                }
                if (newWordLocal >= 0) {
                    if (paramAndVarConst[i4]) {
                        this.cfw.addPush(0);
                        this.cfw.addIStore((this.fnCurrent.isNumberVar(i4) ? (short) 2 : (short) 1) + newWordLocal);
                    }
                    this.varRegisters[i4] = newWordLocal;
                }
                if (this.compilerEnv.isGenerateDebugInfo()) {
                    String paramOrVarName = this.fnCurrent.fnode.getParamOrVarName(i4);
                    String str2 = this.fnCurrent.isNumberVar(i4) ? "D" : "Ljava/lang/Object;";
                    int currentCodeOffset = this.cfw.getCurrentCodeOffset();
                    if (newWordLocal < 0) {
                        newWordLocal = this.varRegisters[i4];
                    }
                    this.cfw.addVariableDescriptor(paramOrVarName, str2, currentCodeOffset, newWordLocal);
                }
            }
            return;
        }
        if (this.isGenerator) {
            return;
        }
        ScriptNode scriptNode = this.scriptOrFn;
        boolean z2 = (scriptNode instanceof FunctionNode) && ((FunctionNode) scriptNode).getFunctionType() == 4;
        if (this.fnCurrent != null) {
            this.cfw.addALoad(this.funObjLocal);
            this.cfw.addALoad(this.variableObjectLocal);
            this.cfw.addALoad(this.argsLocal);
            String str3 = z2 ? "createArrowFunctionActivation" : "createFunctionActivation";
            this.cfw.addPush(this.scriptOrFn.isInStrictMode());
            addScriptRuntimeInvoke(str3, "(Lorg/mozilla/javascript/NativeFunction;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;Z)Lorg/mozilla/javascript/Scriptable;");
            this.cfw.addAStore(this.variableObjectLocal);
            this.cfw.addALoad(this.contextLocal);
            this.cfw.addALoad(this.variableObjectLocal);
            addScriptRuntimeInvoke("enterActivationFunction", "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)V");
            str = "activation";
        } else {
            this.cfw.addALoad(this.funObjLocal);
            this.cfw.addALoad(this.thisObjLocal);
            this.cfw.addALoad(this.contextLocal);
            this.cfw.addALoad(this.variableObjectLocal);
            this.cfw.addPush(0);
            addScriptRuntimeInvoke("initScript", "(Lorg/mozilla/javascript/NativeFunction;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Z)V");
            str = "global";
        }
        this.enterAreaStartLabel = this.cfw.acquireLabel();
        this.epilogueLabel = this.cfw.acquireLabel();
        this.cfw.markLabel(this.enterAreaStartLabel);
        generateNestedFunctionInits();
        if (this.compilerEnv.isGenerateDebugInfo()) {
            ClassFileWriter classFileWriter = this.cfw;
            classFileWriter.addVariableDescriptor(str, "Lorg/mozilla/javascript/Scriptable;", classFileWriter.getCurrentCodeOffset(), this.variableObjectLocal);
        }
        OptFunctionNode optFunctionNode = this.fnCurrent;
        if (optFunctionNode == null) {
            this.popvLocal = getNewWordLocal();
            Codegen.pushUndefined(this.cfw);
            this.cfw.addAStore(this.popvLocal);
            int endLineno = this.scriptOrFn.getEndLineno();
            if (endLineno != -1) {
                this.cfw.addLineNumberEntry((short) endLineno);
                return;
            }
            return;
        }
        if (optFunctionNode.itsContainsCalls0) {
            this.itsZeroArgArray = getNewWordLocal();
            this.cfw.add(178, "org/mozilla/javascript/ScriptRuntime", "emptyArgs", "[Ljava/lang/Object;");
            this.cfw.addAStore(this.itsZeroArgArray);
        }
        if (this.fnCurrent.itsContainsCalls1) {
            this.itsOneArgArray = getNewWordLocal();
            this.cfw.addPush(1);
            this.cfw.add(189, "java/lang/Object");
            this.cfw.addAStore(this.itsOneArgArray);
        }
    }

    private boolean generateSaveLocals(Node node) throws RuntimeException {
        int i2 = 0;
        for (int i3 = 0; i3 < this.firstFreeLocal; i3++) {
            if (this.locals[i3] != 0) {
                i2++;
            }
        }
        if (i2 == 0) {
            ((FunctionNode) this.scriptOrFn).addLiveLocals(node, null);
            return false;
        }
        int i4 = this.maxLocals;
        if (i4 <= i2) {
            i4 = i2;
        }
        this.maxLocals = i4;
        int[] iArr = new int[i2];
        int i5 = 0;
        for (int i6 = 0; i6 < this.firstFreeLocal; i6++) {
            if (this.locals[i6] != 0) {
                iArr[i5] = i6;
                i5++;
            }
        }
        ((FunctionNode) this.scriptOrFn).addLiveLocals(node, iArr);
        generateGetGeneratorLocalsState();
        for (int i7 = 0; i7 < i2; i7++) {
            this.cfw.add(89);
            this.cfw.addLoadConstant(i7);
            this.cfw.addALoad(iArr[i7]);
            this.cfw.add(83);
        }
        this.cfw.add(87);
        return true;
    }

    private void generateSetGeneratorResumptionPoint(int i2) throws RuntimeException {
        this.cfw.addALoad(this.generatorStateLocal);
        this.cfw.addLoadConstant(i2);
        this.cfw.add(181, "org/mozilla/javascript/optimizer/OptRuntime$GeneratorState", "resumptionPoint", "I");
    }

    private void generateStatement(Node node) throws RuntimeException {
        updateLineNumber(node);
        int type = node.getType();
        Node firstChild = node.getFirstChild();
        if (type == 50) {
            generateExpression(firstChild, node);
            if (this.compilerEnv.isGenerateObserverCount()) {
                addInstructionCount();
            }
            generateThrowJavaScriptException();
            return;
        }
        if (type == 51) {
            if (this.compilerEnv.isGenerateObserverCount()) {
                addInstructionCount();
            }
            this.cfw.addALoad(getLocalBlockRegister(node));
            this.cfw.add(191);
            return;
        }
        if (type != 65) {
            if (type == 82) {
                visitTryCatchFinally((Jump) node, firstChild);
                return;
            }
            int i2 = 1;
            if (type == 110) {
                OptFunctionNode optFunctionNode = OptFunctionNode.get(this.scriptOrFn, node.getExistingIntProp(1));
                int functionType = optFunctionNode.fnode.getFunctionType();
                if (functionType == 3) {
                    visitFunction(optFunctionNode, functionType);
                    return;
                } else {
                    if (functionType != 1) {
                        throw Codegen.badTree();
                    }
                    return;
                }
            }
            if (type == 115) {
                if (this.compilerEnv.isGenerateObserverCount()) {
                    addInstructionCount();
                }
                visitSwitch((Jump) node, firstChild);
                return;
            }
            if (type != 124) {
                if (type == 126) {
                    if (this.isGenerator) {
                        if (this.compilerEnv.isGenerateObserverCount()) {
                            saveCurrentCodeOffset();
                        }
                        this.cfw.setStackTop((short) 1);
                        short newWordLocal = getNewWordLocal();
                        int iAcquireLabel = this.cfw.acquireLabel();
                        int iAcquireLabel2 = this.cfw.acquireLabel();
                        this.cfw.markLabel(iAcquireLabel);
                        generateIntegerWrap();
                        this.cfw.addAStore(newWordLocal);
                        while (firstChild != null) {
                            generateStatement(firstChild);
                            firstChild = firstChild.getNext();
                        }
                        this.cfw.addALoad(newWordLocal);
                        this.cfw.add(192, "java/lang/Integer");
                        generateIntegerUnwrap();
                        FinallyReturnPoint finallyReturnPoint = this.finallys.get(node);
                        int iAcquireLabel3 = this.cfw.acquireLabel();
                        finallyReturnPoint.tableLabel = iAcquireLabel3;
                        this.cfw.add(167, iAcquireLabel3);
                        releaseWordLocal(newWordLocal);
                        this.cfw.markLabel(iAcquireLabel2);
                        return;
                    }
                    return;
                }
                if (type == 142) {
                    boolean z2 = this.inLocalBlock;
                    this.inLocalBlock = true;
                    short newWordLocal2 = getNewWordLocal();
                    if (this.isGenerator) {
                        this.cfw.add(1);
                        this.cfw.addAStore(newWordLocal2);
                    }
                    node.putIntProp(2, newWordLocal2);
                    while (firstChild != null) {
                        generateStatement(firstChild);
                        firstChild = firstChild.getNext();
                    }
                    releaseWordLocal(newWordLocal2);
                    node.removeProp(2);
                    this.inLocalBlock = z2;
                    return;
                }
                if (type != 161) {
                    switch (type) {
                        case 2:
                            generateExpression(firstChild, node);
                            this.cfw.addALoad(this.contextLocal);
                            this.cfw.addALoad(this.variableObjectLocal);
                            addScriptRuntimeInvoke("enterWith", "(Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Scriptable;");
                            this.cfw.addAStore(this.variableObjectLocal);
                            incReferenceWordLocal(this.variableObjectLocal);
                            return;
                        case 3:
                            this.cfw.addALoad(this.variableObjectLocal);
                            addScriptRuntimeInvoke("leaveWith", "(Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Scriptable;");
                            this.cfw.addAStore(this.variableObjectLocal);
                            decReferenceWordLocal(this.variableObjectLocal);
                            return;
                        case 4:
                            break;
                        default:
                            switch (type) {
                                case 57:
                                    this.cfw.setStackTop((short) 0);
                                    int localBlockRegister = getLocalBlockRegister(node);
                                    int existingIntProp = node.getExistingIntProp(14);
                                    String string = firstChild.getString();
                                    generateExpression(firstChild.getNext(), node);
                                    if (existingIntProp == 0) {
                                        this.cfw.add(1);
                                    } else {
                                        this.cfw.addALoad(localBlockRegister);
                                    }
                                    this.cfw.addPush(string);
                                    this.cfw.addALoad(this.contextLocal);
                                    this.cfw.addALoad(this.variableObjectLocal);
                                    addScriptRuntimeInvoke("newCatchScope", "(Ljava/lang/Throwable;Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Scriptable;");
                                    this.cfw.addAStore(localBlockRegister);
                                    return;
                                case 58:
                                case 59:
                                case 60:
                                case 61:
                                    generateExpression(firstChild, node);
                                    this.cfw.addALoad(this.contextLocal);
                                    this.cfw.addALoad(this.variableObjectLocal);
                                    if (type == 58) {
                                        i2 = 0;
                                    } else if (type != 59) {
                                        i2 = type == 61 ? 6 : 2;
                                    }
                                    this.cfw.addPush(i2);
                                    addScriptRuntimeInvoke("enumInit", "(Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;I)Ljava/lang/Object;");
                                    this.cfw.addAStore(getLocalBlockRegister(node));
                                    return;
                                default:
                                    switch (type) {
                                        case 129:
                                        case 130:
                                        case 131:
                                        case 133:
                                        case 137:
                                            break;
                                        case 132:
                                            if (this.compilerEnv.isGenerateObserverCount()) {
                                                addInstructionCount();
                                            }
                                            this.cfw.markLabel(getTargetLabel(node));
                                            if (this.compilerEnv.isGenerateObserverCount()) {
                                                saveCurrentCodeOffset();
                                                return;
                                            }
                                            return;
                                        case 134:
                                            if (firstChild.getType() == 56) {
                                                visitSetVar(firstChild, firstChild.getFirstChild(), false);
                                                return;
                                            }
                                            if (firstChild.getType() == 157) {
                                                visitSetConstVar(firstChild, firstChild.getFirstChild(), false);
                                                return;
                                            }
                                            if (firstChild.getType() == 73) {
                                                generateYieldPoint(firstChild, false);
                                                return;
                                            }
                                            generateExpression(firstChild, node);
                                            if (node.getIntProp(8, -1) != -1) {
                                                this.cfw.add(88);
                                                return;
                                            } else {
                                                this.cfw.add(87);
                                                return;
                                            }
                                        case 135:
                                            generateExpression(firstChild, node);
                                            if (this.popvLocal < 0) {
                                                this.popvLocal = getNewWordLocal();
                                            }
                                            this.cfw.addAStore(this.popvLocal);
                                            return;
                                        case 136:
                                            break;
                                        default:
                                            throw Codegen.badTree();
                                    }
                            }
                        case 5:
                        case 6:
                        case 7:
                            if (this.compilerEnv.isGenerateObserverCount()) {
                                addInstructionCount();
                            }
                            visitGoto((Jump) node, type, firstChild);
                            return;
                    }
                } else {
                    return;
                }
            }
            if (this.compilerEnv.isGenerateObserverCount()) {
                addInstructionCount(1);
            }
            while (firstChild != null) {
                generateStatement(firstChild);
                firstChild = firstChild.getNext();
            }
            return;
        }
        if (!this.isGenerator) {
            if (firstChild != null) {
                generateExpression(firstChild, node);
            } else if (type == 4) {
                Codegen.pushUndefined(this.cfw);
            } else {
                short s2 = this.popvLocal;
                if (s2 < 0) {
                    throw Codegen.badTree();
                }
                this.cfw.addALoad(s2);
            }
        }
        if (this.compilerEnv.isGenerateObserverCount()) {
            addInstructionCount();
        }
        if (this.epilogueLabel == -1) {
            if (!this.hasVarsInRegs) {
                throw Codegen.badTree();
            }
            this.epilogueLabel = this.cfw.acquireLabel();
        }
        this.cfw.add(167, this.epilogueLabel);
    }

    private void generateThrowJavaScriptException() throws RuntimeException {
        this.cfw.add(187, "org/mozilla/javascript/JavaScriptException");
        this.cfw.add(90);
        this.cfw.add(95);
        this.cfw.addPush(this.scriptOrFn.getSourceName());
        this.cfw.addPush(this.itsLineNumber);
        this.cfw.addInvoke(183, "org/mozilla/javascript/JavaScriptException", "<init>", "(Ljava/lang/Object;Ljava/lang/String;I)V");
        this.cfw.add(191);
    }

    private void generateYieldPoint(Node node, boolean z2) throws RuntimeException {
        short stackTop = this.cfw.getStackTop();
        int i2 = this.maxStack;
        if (i2 <= stackTop) {
            i2 = stackTop;
        }
        this.maxStack = i2;
        if (this.cfw.getStackTop() != 0) {
            generateGetGeneratorStackState();
            for (int i3 = 0; i3 < stackTop; i3++) {
                this.cfw.add(90);
                this.cfw.add(95);
                this.cfw.addLoadConstant(i3);
                this.cfw.add(95);
                this.cfw.add(83);
            }
            this.cfw.add(87);
        }
        Node firstChild = node.getFirstChild();
        if (firstChild != null) {
            generateExpression(firstChild, node);
        } else {
            Codegen.pushUndefined(this.cfw);
        }
        int nextGeneratorState = getNextGeneratorState(node);
        generateSetGeneratorResumptionPoint(nextGeneratorState);
        boolean zGenerateSaveLocals = generateSaveLocals(node);
        this.cfw.add(176);
        generateCheckForThrowOrClose(getTargetLabel(node), zGenerateSaveLocals, nextGeneratorState);
        if (stackTop != 0) {
            generateGetGeneratorStackState();
            for (int i4 = 0; i4 < stackTop; i4++) {
                this.cfw.add(89);
                this.cfw.addLoadConstant((stackTop - i4) - 1);
                this.cfw.add(50);
                this.cfw.add(95);
            }
            this.cfw.add(87);
        }
        if (z2) {
            this.cfw.addALoad(this.argsLocal);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Node getFinallyAtTarget(Node node) {
        Node next;
        if (node == null) {
            return null;
        }
        if (node.getType() == 126) {
            return node;
        }
        if (node.getType() == 132 && (next = node.getNext()) != null && next.getType() == 126) {
            return next;
        }
        throw Kit.codeBug("bad finally target");
    }

    private int getLocalBlockRegister(Node node) {
        return ((Node) node.getProp(3)).getExistingIntProp(2);
    }

    private short getNewWordIntern(int i2) {
        int i3;
        int i4;
        int[] iArr = this.locals;
        if (i2 > 1) {
            i3 = this.firstFreeLocal;
            loop0: while (true) {
                if (i3 + i2 > 1024) {
                    i3 = -1;
                    break;
                }
                i4 = 0;
                while (i4 < i2) {
                    if (iArr[i3 + i4] != 0) {
                        break;
                    }
                    i4++;
                }
                break loop0;
                i3 += i4 + 1;
            }
        } else {
            i3 = this.firstFreeLocal;
        }
        if (i3 != -1) {
            iArr[i3] = 1;
            if (i2 > 1) {
                iArr[i3 + 1] = 1;
            }
            if (i2 > 2) {
                iArr[i3 + 2] = 1;
            }
            if (i3 != this.firstFreeLocal) {
                return (short) i3;
            }
            for (int i5 = i2 + i3; i5 < 1024; i5++) {
                if (iArr[i5] == 0) {
                    short s2 = (short) i5;
                    this.firstFreeLocal = s2;
                    if (this.localsMax < s2) {
                        this.localsMax = s2;
                    }
                    return (short) i3;
                }
            }
        }
        throw Context.reportRuntimeError("Program too complex (out of locals)");
    }

    private short getNewWordLocal(boolean z2) {
        return getNewWordIntern(z2 ? 2 : 1);
    }

    private short getNewWordPairLocal(boolean z2) {
        return getNewWordIntern(z2 ? 3 : 2);
    }

    private int getNextGeneratorState(Node node) {
        return ((FunctionNode) this.scriptOrFn).getResumptionPoints().indexOf(node) + 1;
    }

    private int getTargetLabel(Node node) throws RuntimeException {
        int iLabelId = node.labelId();
        if (iLabelId != -1) {
            return iLabelId;
        }
        int iAcquireLabel = this.cfw.acquireLabel();
        node.labelId(iAcquireLabel);
        return iAcquireLabel;
    }

    private void incReferenceWordLocal(short s2) {
        int[] iArr = this.locals;
        iArr[s2] = iArr[s2] + 1;
    }

    private void initBodyGeneration() {
        int paramAndVarCount;
        this.varRegisters = null;
        if (this.scriptOrFn.getType() == 110) {
            OptFunctionNode optFunctionNode = OptFunctionNode.get(this.scriptOrFn);
            this.fnCurrent = optFunctionNode;
            boolean z2 = !optFunctionNode.fnode.requiresActivation();
            this.hasVarsInRegs = z2;
            if (z2 && (paramAndVarCount = this.fnCurrent.fnode.getParamAndVarCount()) != 0) {
                this.varRegisters = new short[paramAndVarCount];
            }
            boolean zIsTargetOfDirectCall = this.fnCurrent.isTargetOfDirectCall();
            this.inDirectCallFunction = zIsTargetOfDirectCall;
            if (zIsTargetOfDirectCall && !this.hasVarsInRegs) {
                Codegen.badTree();
            }
        } else {
            this.fnCurrent = null;
            this.hasVarsInRegs = false;
            this.inDirectCallFunction = false;
        }
        this.locals = new int[1024];
        this.funObjLocal = (short) 0;
        this.contextLocal = (short) 1;
        this.variableObjectLocal = (short) 2;
        this.thisObjLocal = (short) 3;
        this.localsMax = (short) 4;
        this.firstFreeLocal = (short) 4;
        this.popvLocal = (short) -1;
        this.argsLocal = (short) -1;
        this.itsZeroArgArray = (short) -1;
        this.itsOneArgArray = (short) -1;
        this.epilogueLabel = -1;
        this.enterAreaStartLabel = -1;
        this.generatorStateLocal = (short) -1;
    }

    private void inlineFinally(Node node, int i2, int i3) throws RuntimeException {
        Node finallyAtTarget = getFinallyAtTarget(node);
        finallyAtTarget.resetTargets();
        this.exceptionManager.markInlineFinallyStart(finallyAtTarget, i2);
        for (Node firstChild = finallyAtTarget.getFirstChild(); firstChild != null; firstChild = firstChild.getNext()) {
            generateStatement(firstChild);
        }
        this.exceptionManager.markInlineFinallyEnd(finallyAtTarget, i3);
    }

    private static boolean isArithmeticNode(Node node) {
        int type = node.getType();
        return type == 22 || type == 25 || type == 24 || type == 23;
    }

    private int nodeIsDirectCallParameter(Node node) throws RuntimeException {
        if (node.getType() != 55 || !this.inDirectCallFunction || this.itsForcedObjectParameters) {
            return -1;
        }
        int varIndex = this.fnCurrent.getVarIndex(node);
        if (this.fnCurrent.isParameter(varIndex)) {
            return this.varRegisters[varIndex];
        }
        return -1;
    }

    private void releaseWordLocal(short s2) {
        if (s2 < this.firstFreeLocal) {
            this.firstFreeLocal = s2;
        }
        this.locals[s2] = 0;
    }

    private void saveCurrentCodeOffset() {
        this.savedCodeOffset = this.cfw.getCurrentCodeOffset();
    }

    private void updateLineNumber(Node node) {
        int lineno = node.getLineno();
        this.itsLineNumber = lineno;
        if (lineno == -1) {
            return;
        }
        this.cfw.addLineNumberEntry((short) lineno);
    }

    private boolean varIsDirectCallParameter(int i2) {
        return this.fnCurrent.isParameter(i2) && this.inDirectCallFunction && !this.itsForcedObjectParameters;
    }

    private void visitArithmetic(Node node, int i2, Node node2, Node node3) throws RuntimeException {
        if (node.getIntProp(8, -1) != -1) {
            generateExpression(node2, node);
            generateExpression(node2.getNext(), node);
            this.cfw.add(i2);
            return;
        }
        boolean zIsArithmeticNode = isArithmeticNode(node3);
        generateExpression(node2, node);
        if (!isArithmeticNode(node2)) {
            addObjectToDouble();
        }
        generateExpression(node2.getNext(), node);
        if (!isArithmeticNode(node2.getNext())) {
            addObjectToDouble();
        }
        this.cfw.add(i2);
        if (zIsArithmeticNode) {
            return;
        }
        addDoubleWrap();
    }

    private void visitArrayLiteral(Node node, Node node2, boolean z2) throws RuntimeException {
        int i2 = 0;
        int i3 = 0;
        for (Node next = node2; next != null; next = next.getNext()) {
            i3++;
        }
        if (!z2 && ((i3 > 10 || this.cfw.getCurrentCodeOffset() > 30000) && !this.hasVarsInRegs && !this.isGenerator && !this.inLocalBlock)) {
            if (this.literals == null) {
                this.literals = new LinkedList();
            }
            this.literals.add(node);
            String str = this.codegen.getBodyMethodName(this.scriptOrFn) + "_literal" + this.literals.size();
            this.cfw.addALoad(this.funObjLocal);
            this.cfw.addALoad(this.contextLocal);
            this.cfw.addALoad(this.variableObjectLocal);
            this.cfw.addALoad(this.thisObjLocal);
            this.cfw.addALoad(this.argsLocal);
            this.cfw.addInvoke(182, this.codegen.mainClassName, str, "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)Lorg/mozilla/javascript/Scriptable;");
            return;
        }
        if (this.isGenerator) {
            for (int i4 = 0; i4 != i3; i4++) {
                generateExpression(node2, node);
                node2 = node2.getNext();
            }
            addNewObjectArray(i3);
            while (i2 != i3) {
                this.cfw.add(90);
                this.cfw.add(95);
                this.cfw.addPush((i3 - i2) - 1);
                this.cfw.add(95);
                this.cfw.add(83);
                i2++;
            }
        } else {
            addNewObjectArray(i3);
            while (i2 != i3) {
                this.cfw.add(89);
                this.cfw.addPush(i2);
                generateExpression(node2, node);
                this.cfw.add(83);
                node2 = node2.getNext();
                i2++;
            }
        }
        int[] iArr = (int[]) node.getProp(11);
        if (iArr == null) {
            this.cfw.add(1);
            this.cfw.add(3);
        } else {
            this.cfw.addPush(OptRuntime.encodeIntArray(iArr));
            this.cfw.addPush(iArr.length);
        }
        this.cfw.addALoad(this.contextLocal);
        this.cfw.addALoad(this.variableObjectLocal);
        addOptRuntimeInvoke("newArrayLiteral", "([Ljava/lang/Object;Ljava/lang/String;ILorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Scriptable;");
    }

    private void visitBitOp(Node node, int i2, Node node2) throws RuntimeException {
        int intProp = node.getIntProp(8, -1);
        generateExpression(node2, node);
        if (i2 == 20) {
            addScriptRuntimeInvoke("toUint32", "(Ljava/lang/Object;)J");
            generateExpression(node2.getNext(), node);
            addScriptRuntimeInvoke("toInt32", "(Ljava/lang/Object;)I");
            this.cfw.addPush(31);
            this.cfw.add(126);
            this.cfw.add(125);
            this.cfw.add(138);
            addDoubleWrap();
            return;
        }
        if (intProp == -1) {
            addScriptRuntimeInvoke("toInt32", "(Ljava/lang/Object;)I");
            generateExpression(node2.getNext(), node);
            addScriptRuntimeInvoke("toInt32", "(Ljava/lang/Object;)I");
        } else {
            addScriptRuntimeInvoke("toInt32", "(D)I");
            generateExpression(node2.getNext(), node);
            addScriptRuntimeInvoke("toInt32", "(D)I");
        }
        if (i2 == 18) {
            this.cfw.add(120);
        } else if (i2 != 19) {
            switch (i2) {
                case 9:
                    this.cfw.add(128);
                    break;
                case 10:
                    this.cfw.add(130);
                    break;
                case 11:
                    this.cfw.add(126);
                    break;
                default:
                    throw Codegen.badTree();
            }
        } else {
            this.cfw.add(122);
        }
        this.cfw.add(135);
        if (intProp == -1) {
            addDoubleWrap();
        }
    }

    private void visitDotQuery(Node node, Node node2) throws RuntimeException {
        updateLineNumber(node);
        generateExpression(node2, node);
        this.cfw.addALoad(this.variableObjectLocal);
        addScriptRuntimeInvoke("enterDotQuery", "(Ljava/lang/Object;Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Scriptable;");
        this.cfw.addAStore(this.variableObjectLocal);
        this.cfw.add(1);
        int iAcquireLabel = this.cfw.acquireLabel();
        this.cfw.markLabel(iAcquireLabel);
        this.cfw.add(87);
        generateExpression(node2.getNext(), node);
        addScriptRuntimeInvoke("toBoolean", "(Ljava/lang/Object;)Z");
        this.cfw.addALoad(this.variableObjectLocal);
        addScriptRuntimeInvoke("updateDotQuery", "(ZLorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;");
        this.cfw.add(89);
        this.cfw.add(198, iAcquireLabel);
        this.cfw.addALoad(this.variableObjectLocal);
        addScriptRuntimeInvoke("leaveDotQuery", "(Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Scriptable;");
        this.cfw.addAStore(this.variableObjectLocal);
    }

    private void visitFunction(OptFunctionNode optFunctionNode, int i2) throws RuntimeException {
        int index = this.codegen.getIndex(optFunctionNode.fnode);
        this.cfw.add(187, this.codegen.mainClassName);
        this.cfw.add(89);
        this.cfw.addALoad(this.variableObjectLocal);
        this.cfw.addALoad(this.contextLocal);
        this.cfw.addPush(index);
        this.cfw.addInvoke(183, this.codegen.mainClassName, "<init>", "(Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Context;I)V");
        if (i2 == 4) {
            this.cfw.addALoad(this.contextLocal);
            this.cfw.addALoad(this.variableObjectLocal);
            this.cfw.addALoad(this.thisObjLocal);
            addOptRuntimeInvoke("bindThis", "(Lorg/mozilla/javascript/NativeFunction;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Function;");
        }
        if (i2 == 2 || i2 == 4) {
            return;
        }
        this.cfw.addPush(i2);
        this.cfw.addALoad(this.variableObjectLocal);
        this.cfw.addALoad(this.contextLocal);
        addOptRuntimeInvoke("initFunction", "(Lorg/mozilla/javascript/NativeFunction;ILorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Context;)V");
    }

    private void visitGetProp(Node node, Node node2) throws RuntimeException {
        generateExpression(node2, node);
        Node next = node2.getNext();
        generateExpression(next, node);
        if (node.getType() == 34) {
            this.cfw.addALoad(this.contextLocal);
            this.cfw.addALoad(this.variableObjectLocal);
            addScriptRuntimeInvoke("getObjectPropNoWarn", "(Ljava/lang/Object;Ljava/lang/String;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;");
        } else if (node2.getType() == 43 && next.getType() == 41) {
            this.cfw.addALoad(this.contextLocal);
            addScriptRuntimeInvoke("getObjectProp", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;Lorg/mozilla/javascript/Context;)Ljava/lang/Object;");
        } else {
            this.cfw.addALoad(this.contextLocal);
            this.cfw.addALoad(this.variableObjectLocal);
            addScriptRuntimeInvoke("getObjectProp", "(Ljava/lang/Object;Ljava/lang/String;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;");
        }
    }

    private void visitGetVar(Node node) throws RuntimeException {
        if (!this.hasVarsInRegs) {
            Kit.codeBug();
        }
        int varIndex = this.fnCurrent.getVarIndex(node);
        short s2 = this.varRegisters[varIndex];
        if (varIsDirectCallParameter(varIndex)) {
            if (node.getIntProp(8, -1) != -1) {
                dcpLoadAsNumber(s2);
                return;
            } else {
                dcpLoadAsObject(s2);
                return;
            }
        }
        if (this.fnCurrent.isNumberVar(varIndex)) {
            this.cfw.addDLoad(s2);
        } else {
            this.cfw.addALoad(s2);
        }
    }

    private void visitGoto(Jump jump, int i2, Node node) throws RuntimeException {
        Node node2 = jump.target;
        if (i2 != 6 && i2 != 7) {
            if (i2 != 136) {
                addGoto(node2, 167);
                return;
            } else if (this.isGenerator) {
                addGotoWithReturn(node2);
                return;
            } else {
                inlineFinally(node2);
                return;
            }
        }
        if (node == null) {
            throw Codegen.badTree();
        }
        int targetLabel = getTargetLabel(node2);
        int iAcquireLabel = this.cfw.acquireLabel();
        if (i2 == 6) {
            generateIfJump(node, jump, targetLabel, iAcquireLabel);
        } else {
            generateIfJump(node, jump, iAcquireLabel, targetLabel);
        }
        this.cfw.markLabel(iAcquireLabel);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00c4 A[PHI: r1
      0x00c4: PHI (r1v15 java.lang.String) = (r1v14 java.lang.String), (r1v20 java.lang.String) binds: [B:23:0x00ad, B:27:0x00b7] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x013a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void visitIfJumpEqOp(org.mozilla.javascript.Node r18, org.mozilla.javascript.Node r19, int r20, int r21) throws java.lang.RuntimeException {
        /*
            Method dump skipped, instructions count: 347
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.optimizer.BodyCodegen.visitIfJumpEqOp(org.mozilla.javascript.Node, org.mozilla.javascript.Node, int, int):void");
    }

    private void visitIfJumpRelOp(Node node, Node node2, int i2, int i3) throws RuntimeException {
        if (i2 == -1 || i3 == -1) {
            throw Codegen.badTree();
        }
        int type = node.getType();
        Node next = node2.getNext();
        if (type == 53 || type == 52) {
            generateExpression(node2, node);
            generateExpression(next, node);
            this.cfw.addALoad(this.contextLocal);
            addScriptRuntimeInvoke(type == 53 ? "instanceOf" : "in", "(Ljava/lang/Object;Ljava/lang/Object;Lorg/mozilla/javascript/Context;)Z");
            this.cfw.add(154, i2);
            this.cfw.add(167, i3);
            return;
        }
        int intProp = node.getIntProp(8, -1);
        int iNodeIsDirectCallParameter = nodeIsDirectCallParameter(node2);
        int iNodeIsDirectCallParameter2 = nodeIsDirectCallParameter(next);
        if (intProp != -1) {
            if (intProp != 2) {
                generateExpression(node2, node);
            } else if (iNodeIsDirectCallParameter != -1) {
                dcpLoadAsNumber(iNodeIsDirectCallParameter);
            } else {
                generateExpression(node2, node);
                addObjectToDouble();
            }
            if (intProp != 1) {
                generateExpression(next, node);
            } else if (iNodeIsDirectCallParameter2 != -1) {
                dcpLoadAsNumber(iNodeIsDirectCallParameter2);
            } else {
                generateExpression(next, node);
                addObjectToDouble();
            }
            genSimpleCompare(type, i2, i3);
            return;
        }
        if (iNodeIsDirectCallParameter == -1 || iNodeIsDirectCallParameter2 == -1) {
            generateExpression(node2, node);
            generateExpression(next, node);
        } else {
            short stackTop = this.cfw.getStackTop();
            int iAcquireLabel = this.cfw.acquireLabel();
            this.cfw.addALoad(iNodeIsDirectCallParameter);
            this.cfw.add(178, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
            this.cfw.add(166, iAcquireLabel);
            this.cfw.addDLoad(iNodeIsDirectCallParameter + 1);
            dcpLoadAsNumber(iNodeIsDirectCallParameter2);
            genSimpleCompare(type, i2, i3);
            if (stackTop != this.cfw.getStackTop()) {
                throw Codegen.badTree();
            }
            this.cfw.markLabel(iAcquireLabel);
            int iAcquireLabel2 = this.cfw.acquireLabel();
            this.cfw.addALoad(iNodeIsDirectCallParameter2);
            this.cfw.add(178, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
            this.cfw.add(166, iAcquireLabel2);
            this.cfw.addALoad(iNodeIsDirectCallParameter);
            addObjectToDouble();
            this.cfw.addDLoad(iNodeIsDirectCallParameter2 + 1);
            genSimpleCompare(type, i2, i3);
            if (stackTop != this.cfw.getStackTop()) {
                throw Codegen.badTree();
            }
            this.cfw.markLabel(iAcquireLabel2);
            this.cfw.addALoad(iNodeIsDirectCallParameter);
            this.cfw.addALoad(iNodeIsDirectCallParameter2);
        }
        if (type == 17 || type == 16) {
            this.cfw.add(95);
        }
        addScriptRuntimeInvoke((type == 14 || type == 16) ? "cmp_LT" : "cmp_LE", "(Ljava/lang/Object;Ljava/lang/Object;)Z");
        this.cfw.add(154, i2);
        this.cfw.add(167, i3);
    }

    private void visitIncDec(Node node) throws RuntimeException {
        int existingIntProp = node.getExistingIntProp(13);
        Node firstChild = node.getFirstChild();
        int type = firstChild.getType();
        if (type == 33) {
            Node firstChild2 = firstChild.getFirstChild();
            generateExpression(firstChild2, node);
            generateExpression(firstChild2.getNext(), node);
            this.cfw.addALoad(this.contextLocal);
            this.cfw.addALoad(this.variableObjectLocal);
            this.cfw.addPush(existingIntProp);
            addScriptRuntimeInvoke("propIncrDecr", "(Ljava/lang/Object;Ljava/lang/String;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;I)Ljava/lang/Object;");
            return;
        }
        if (type == 34) {
            throw Kit.codeBug();
        }
        if (type == 36) {
            Node firstChild3 = firstChild.getFirstChild();
            generateExpression(firstChild3, node);
            generateExpression(firstChild3.getNext(), node);
            this.cfw.addALoad(this.contextLocal);
            this.cfw.addALoad(this.variableObjectLocal);
            this.cfw.addPush(existingIntProp);
            if (firstChild3.getNext().getIntProp(8, -1) != -1) {
                addOptRuntimeInvoke("elemIncrDecr", "(Ljava/lang/Object;DLorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;I)Ljava/lang/Object;");
                return;
            } else {
                addScriptRuntimeInvoke("elemIncrDecr", "(Ljava/lang/Object;Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;I)Ljava/lang/Object;");
                return;
            }
        }
        if (type == 39) {
            this.cfw.addALoad(this.variableObjectLocal);
            this.cfw.addPush(firstChild.getString());
            this.cfw.addALoad(this.contextLocal);
            this.cfw.addPush(existingIntProp);
            addScriptRuntimeInvoke("nameIncrDecr", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;Lorg/mozilla/javascript/Context;I)Ljava/lang/Object;");
            return;
        }
        if (type != 55) {
            if (type != 68) {
                Codegen.badTree();
                return;
            }
            generateExpression(firstChild.getFirstChild(), node);
            this.cfw.addALoad(this.contextLocal);
            this.cfw.addALoad(this.variableObjectLocal);
            this.cfw.addPush(existingIntProp);
            addScriptRuntimeInvoke("refIncrDecr", "(Lorg/mozilla/javascript/Ref;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;I)Ljava/lang/Object;");
            return;
        }
        if (!this.hasVarsInRegs) {
            Kit.codeBug();
        }
        boolean z2 = (existingIntProp & 2) != 0;
        int varIndex = this.fnCurrent.getVarIndex(firstChild);
        short s2 = this.varRegisters[varIndex];
        if (this.fnCurrent.fnode.getParamAndVarConst()[varIndex]) {
            if (node.getIntProp(8, -1) != -1) {
                this.cfw.addDLoad(s2 + (varIsDirectCallParameter(varIndex) ? 1 : 0));
                if (z2) {
                    return;
                }
                this.cfw.addPush(1.0d);
                if ((existingIntProp & 1) == 0) {
                    this.cfw.add(99);
                    return;
                } else {
                    this.cfw.add(103);
                    return;
                }
            }
            if (varIsDirectCallParameter(varIndex)) {
                dcpLoadAsObject(s2);
            } else {
                this.cfw.addALoad(s2);
            }
            if (z2) {
                this.cfw.add(89);
                addObjectToDouble();
                this.cfw.add(88);
                return;
            } else {
                addObjectToDouble();
                this.cfw.addPush(1.0d);
                if ((existingIntProp & 1) == 0) {
                    this.cfw.add(99);
                } else {
                    this.cfw.add(103);
                }
                addDoubleWrap();
                return;
            }
        }
        if (node.getIntProp(8, -1) != -1) {
            boolean zVarIsDirectCallParameter = varIsDirectCallParameter(varIndex);
            ClassFileWriter classFileWriter = this.cfw;
            int i2 = s2 + (zVarIsDirectCallParameter ? 1 : 0);
            classFileWriter.addDLoad(i2);
            if (z2) {
                this.cfw.add(92);
            }
            this.cfw.addPush(1.0d);
            if ((existingIntProp & 1) == 0) {
                this.cfw.add(99);
            } else {
                this.cfw.add(103);
            }
            if (!z2) {
                this.cfw.add(92);
            }
            this.cfw.addDStore(i2);
            return;
        }
        if (varIsDirectCallParameter(varIndex)) {
            dcpLoadAsObject(s2);
        } else {
            this.cfw.addALoad(s2);
        }
        addObjectToDouble();
        if (z2) {
            this.cfw.add(92);
        }
        this.cfw.addPush(1.0d);
        if ((existingIntProp & 1) == 0) {
            this.cfw.add(99);
        } else {
            this.cfw.add(103);
        }
        addDoubleWrap();
        if (!z2) {
            this.cfw.add(89);
        }
        this.cfw.addAStore(s2);
        if (z2) {
            addDoubleWrap();
        }
    }

    private void visitObjectLiteral(Node node, Node node2, boolean z2) throws RuntimeException {
        Object[] objArr = (Object[]) node.getProp(12);
        int length = objArr.length;
        if (!z2 && ((length > 10 || this.cfw.getCurrentCodeOffset() > 30000) && !this.hasVarsInRegs && !this.isGenerator && !this.inLocalBlock)) {
            if (this.literals == null) {
                this.literals = new LinkedList();
            }
            this.literals.add(node);
            String str = this.codegen.getBodyMethodName(this.scriptOrFn) + "_literal" + this.literals.size();
            this.cfw.addALoad(this.funObjLocal);
            this.cfw.addALoad(this.contextLocal);
            this.cfw.addALoad(this.variableObjectLocal);
            this.cfw.addALoad(this.thisObjLocal);
            this.cfw.addALoad(this.argsLocal);
            this.cfw.addInvoke(182, this.codegen.mainClassName, str, "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)Lorg/mozilla/javascript/Scriptable;");
            return;
        }
        if (this.isGenerator) {
            addLoadPropertyValues(node, node2, length);
            addLoadPropertyIds(objArr, length);
            this.cfw.add(95);
        } else {
            addLoadPropertyIds(objArr, length);
            addLoadPropertyValues(node, node2, length);
        }
        Node next = node2;
        for (int i2 = 0; i2 != length; i2++) {
            int type = next.getType();
            if (type == 152 || type == 153) {
                this.cfw.addPush(length);
                this.cfw.add(188, 10);
                for (int i3 = 0; i3 != length; i3++) {
                    this.cfw.add(89);
                    this.cfw.addPush(i3);
                    int type2 = node2.getType();
                    if (type2 == 152) {
                        this.cfw.add(2);
                    } else if (type2 == 153) {
                        this.cfw.add(4);
                    } else {
                        this.cfw.add(3);
                    }
                    this.cfw.add(79);
                    node2 = node2.getNext();
                }
                this.cfw.addALoad(this.contextLocal);
                this.cfw.addALoad(this.variableObjectLocal);
                addScriptRuntimeInvoke("newObjectLiteral", "([Ljava/lang/Object;[Ljava/lang/Object;[ILorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Scriptable;");
            }
            next = next.getNext();
        }
        this.cfw.add(1);
        this.cfw.addALoad(this.contextLocal);
        this.cfw.addALoad(this.variableObjectLocal);
        addScriptRuntimeInvoke("newObjectLiteral", "([Ljava/lang/Object;[Ljava/lang/Object;[ILorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Scriptable;");
    }

    private void visitOptimizedCall(Node node, OptFunctionNode optFunctionNode, int i2, Node node2) throws RuntimeException {
        short newWordLocal;
        Node next = node2.getNext();
        String str = this.codegen.mainClassName;
        if (i2 == 30) {
            generateExpression(node2, node);
            newWordLocal = 0;
        } else {
            generateFunctionAndThisObj(node2, node);
            newWordLocal = getNewWordLocal();
            this.cfw.addAStore(newWordLocal);
        }
        int iAcquireLabel = this.cfw.acquireLabel();
        int iAcquireLabel2 = this.cfw.acquireLabel();
        this.cfw.add(89);
        this.cfw.add(193, str);
        this.cfw.add(153, iAcquireLabel2);
        this.cfw.add(192, str);
        this.cfw.add(89);
        this.cfw.add(180, str, bg.f21483d, "I");
        this.cfw.addPush(this.codegen.getIndex(optFunctionNode.fnode));
        this.cfw.add(160, iAcquireLabel2);
        this.cfw.addALoad(this.contextLocal);
        this.cfw.addALoad(this.variableObjectLocal);
        if (i2 == 30) {
            this.cfw.add(1);
        } else {
            this.cfw.addALoad(newWordLocal);
        }
        for (Node next2 = next; next2 != null; next2 = next2.getNext()) {
            int iNodeIsDirectCallParameter = nodeIsDirectCallParameter(next2);
            if (iNodeIsDirectCallParameter >= 0) {
                this.cfw.addALoad(iNodeIsDirectCallParameter);
                this.cfw.addDLoad(iNodeIsDirectCallParameter + 1);
            } else if (next2.getIntProp(8, -1) == 0) {
                this.cfw.add(178, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
                generateExpression(next2, node);
            } else {
                generateExpression(next2, node);
                this.cfw.addPush(0.0d);
            }
        }
        this.cfw.add(178, "org/mozilla/javascript/ScriptRuntime", "emptyArgs", "[Ljava/lang/Object;");
        ClassFileWriter classFileWriter = this.cfw;
        Codegen codegen = this.codegen;
        classFileWriter.addInvoke(184, codegen.mainClassName, i2 == 30 ? codegen.getDirectCtorName(optFunctionNode.fnode) : codegen.getBodyMethodName(optFunctionNode.fnode), this.codegen.getBodyMethodSignature(optFunctionNode.fnode));
        this.cfw.add(167, iAcquireLabel);
        this.cfw.markLabel(iAcquireLabel2);
        this.cfw.addALoad(this.contextLocal);
        this.cfw.addALoad(this.variableObjectLocal);
        if (i2 != 30) {
            this.cfw.addALoad(newWordLocal);
            releaseWordLocal(newWordLocal);
        }
        generateCallArgArray(node, next, true);
        if (i2 == 30) {
            addScriptRuntimeInvoke("newObject", "(Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)Lorg/mozilla/javascript/Scriptable;");
        } else {
            this.cfw.addInvoke(185, "org/mozilla/javascript/Callable", NotificationCompat.CATEGORY_CALL, "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)Ljava/lang/Object;");
        }
        this.cfw.markLabel(iAcquireLabel);
    }

    private void visitSetConst(Node node, Node node2) throws RuntimeException {
        String string = node.getFirstChild().getString();
        while (node2 != null) {
            generateExpression(node2, node);
            node2 = node2.getNext();
        }
        this.cfw.addALoad(this.contextLocal);
        this.cfw.addPush(string);
        addScriptRuntimeInvoke("setConst", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/Object;Lorg/mozilla/javascript/Context;Ljava/lang/String;)Ljava/lang/Object;");
    }

    private void visitSetConstVar(Node node, Node node2, boolean z2) throws RuntimeException {
        if (!this.hasVarsInRegs) {
            Kit.codeBug();
        }
        int varIndex = this.fnCurrent.getVarIndex(node);
        generateExpression(node2.getNext(), node);
        boolean z3 = node.getIntProp(8, -1) != -1;
        short s2 = this.varRegisters[varIndex];
        int iAcquireLabel = this.cfw.acquireLabel();
        int iAcquireLabel2 = this.cfw.acquireLabel();
        if (z3) {
            int i2 = s2 + 2;
            this.cfw.addILoad(i2);
            this.cfw.add(154, iAcquireLabel2);
            short stackTop = this.cfw.getStackTop();
            this.cfw.addPush(1);
            this.cfw.addIStore(i2);
            this.cfw.addDStore(s2);
            if (z2) {
                this.cfw.addDLoad(s2);
                this.cfw.markLabel(iAcquireLabel2, stackTop);
            } else {
                this.cfw.add(167, iAcquireLabel);
                this.cfw.markLabel(iAcquireLabel2, stackTop);
                this.cfw.add(88);
            }
        } else {
            int i3 = s2 + 1;
            this.cfw.addILoad(i3);
            this.cfw.add(154, iAcquireLabel2);
            short stackTop2 = this.cfw.getStackTop();
            this.cfw.addPush(1);
            this.cfw.addIStore(i3);
            this.cfw.addAStore(s2);
            if (z2) {
                this.cfw.addALoad(s2);
                this.cfw.markLabel(iAcquireLabel2, stackTop2);
            } else {
                this.cfw.add(167, iAcquireLabel);
                this.cfw.markLabel(iAcquireLabel2, stackTop2);
                this.cfw.add(87);
            }
        }
        this.cfw.markLabel(iAcquireLabel);
    }

    private void visitSetElem(int i2, Node node, Node node2) throws RuntimeException {
        generateExpression(node2, node);
        Node next = node2.getNext();
        if (i2 == 141) {
            this.cfw.add(89);
        }
        generateExpression(next, node);
        Node next2 = next.getNext();
        boolean z2 = node.getIntProp(8, -1) != -1;
        if (i2 == 141) {
            if (z2) {
                this.cfw.add(93);
                this.cfw.addALoad(this.contextLocal);
                this.cfw.addALoad(this.variableObjectLocal);
                addScriptRuntimeInvoke("getObjectIndex", "(Ljava/lang/Object;DLorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;");
            } else {
                this.cfw.add(90);
                this.cfw.addALoad(this.contextLocal);
                this.cfw.addALoad(this.variableObjectLocal);
                addScriptRuntimeInvoke("getObjectElem", "(Ljava/lang/Object;Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;");
            }
        }
        generateExpression(next2, node);
        this.cfw.addALoad(this.contextLocal);
        this.cfw.addALoad(this.variableObjectLocal);
        if (z2) {
            addScriptRuntimeInvoke("setObjectIndex", "(Ljava/lang/Object;DLjava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;");
        } else {
            addScriptRuntimeInvoke("setObjectElem", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;");
        }
    }

    private void visitSetName(Node node, Node node2) throws RuntimeException {
        String string = node.getFirstChild().getString();
        while (node2 != null) {
            generateExpression(node2, node);
            node2 = node2.getNext();
        }
        this.cfw.addALoad(this.contextLocal);
        this.cfw.addALoad(this.variableObjectLocal);
        this.cfw.addPush(string);
        addScriptRuntimeInvoke("setName", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;)Ljava/lang/Object;");
    }

    private void visitSetProp(int i2, Node node, Node node2) throws RuntimeException {
        generateExpression(node2, node);
        Node next = node2.getNext();
        if (i2 == 140) {
            this.cfw.add(89);
        }
        generateExpression(next, node);
        Node next2 = next.getNext();
        if (i2 == 140) {
            this.cfw.add(90);
            if (node2.getType() == 43 && next.getType() == 41) {
                this.cfw.addALoad(this.contextLocal);
                addScriptRuntimeInvoke("getObjectProp", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;Lorg/mozilla/javascript/Context;)Ljava/lang/Object;");
            } else {
                this.cfw.addALoad(this.contextLocal);
                this.cfw.addALoad(this.variableObjectLocal);
                addScriptRuntimeInvoke("getObjectProp", "(Ljava/lang/Object;Ljava/lang/String;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;");
            }
        }
        generateExpression(next2, node);
        this.cfw.addALoad(this.contextLocal);
        this.cfw.addALoad(this.variableObjectLocal);
        addScriptRuntimeInvoke("setObjectProp", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;");
    }

    private void visitSetVar(Node node, Node node2, boolean z2) throws RuntimeException {
        if (!this.hasVarsInRegs) {
            Kit.codeBug();
        }
        int varIndex = this.fnCurrent.getVarIndex(node);
        generateExpression(node2.getNext(), node);
        boolean z3 = node.getIntProp(8, -1) != -1;
        short s2 = this.varRegisters[varIndex];
        if (this.fnCurrent.fnode.getParamAndVarConst()[varIndex]) {
            if (z2) {
                return;
            }
            if (z3) {
                this.cfw.add(88);
                return;
            } else {
                this.cfw.add(87);
                return;
            }
        }
        if (varIsDirectCallParameter(varIndex)) {
            if (!z3) {
                if (z2) {
                    this.cfw.add(89);
                }
                this.cfw.addAStore(s2);
                return;
            }
            if (z2) {
                this.cfw.add(92);
            }
            this.cfw.addALoad(s2);
            this.cfw.add(178, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
            int iAcquireLabel = this.cfw.acquireLabel();
            int iAcquireLabel2 = this.cfw.acquireLabel();
            this.cfw.add(165, iAcquireLabel);
            short stackTop = this.cfw.getStackTop();
            addDoubleWrap();
            this.cfw.addAStore(s2);
            this.cfw.add(167, iAcquireLabel2);
            this.cfw.markLabel(iAcquireLabel, stackTop);
            this.cfw.addDStore(s2 + 1);
            this.cfw.markLabel(iAcquireLabel2);
            return;
        }
        boolean zIsNumberVar = this.fnCurrent.isNumberVar(varIndex);
        if (!z3) {
            if (zIsNumberVar) {
                Kit.codeBug();
            }
            this.cfw.addAStore(s2);
            if (z2) {
                this.cfw.addALoad(s2);
                return;
            }
            return;
        }
        if (zIsNumberVar) {
            this.cfw.addDStore(s2);
            if (z2) {
                this.cfw.addDLoad(s2);
                return;
            }
            return;
        }
        if (z2) {
            this.cfw.add(92);
        }
        addDoubleWrap();
        this.cfw.addAStore(s2);
    }

    private void visitSpecialCall(Node node, int i2, int i3, Node node2) throws RuntimeException {
        String str;
        String str2;
        this.cfw.addALoad(this.contextLocal);
        if (i2 == 30) {
            generateExpression(node2, node);
        } else {
            generateFunctionAndThisObj(node2, node);
        }
        generateCallArgArray(node, node2.getNext(), false);
        if (i2 == 30) {
            this.cfw.addALoad(this.variableObjectLocal);
            this.cfw.addALoad(this.thisObjLocal);
            this.cfw.addPush(i3);
            str = "newObjectSpecial";
            str2 = "(Lorg/mozilla/javascript/Context;Ljava/lang/Object;[Ljava/lang/Object;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;I)Ljava/lang/Object;";
        } else {
            this.cfw.addALoad(this.variableObjectLocal);
            this.cfw.addALoad(this.thisObjLocal);
            this.cfw.addPush(i3);
            String sourceName = this.scriptOrFn.getSourceName();
            ClassFileWriter classFileWriter = this.cfw;
            if (sourceName == null) {
                sourceName = "";
            }
            classFileWriter.addPush(sourceName);
            this.cfw.addPush(this.itsLineNumber);
            str = "callSpecial";
            str2 = "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Callable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;ILjava/lang/String;I)Ljava/lang/Object;";
        }
        addOptRuntimeInvoke(str, str2);
    }

    private void visitStandardCall(Node node, Node node2) throws RuntimeException {
        String str;
        String str2;
        if (node.getType() != 38) {
            throw Codegen.badTree();
        }
        Node next = node2.getNext();
        int type = node2.getType();
        if (next == null) {
            if (type == 39) {
                this.cfw.addPush(node2.getString());
                str = "callName0";
                str2 = "(Ljava/lang/String;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;";
            } else if (type == 33) {
                Node firstChild = node2.getFirstChild();
                generateExpression(firstChild, node);
                this.cfw.addPush(firstChild.getNext().getString());
                str = "callProp0";
                str2 = "(Ljava/lang/Object;Ljava/lang/String;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;";
            } else {
                if (type == 34) {
                    throw Kit.codeBug();
                }
                generateFunctionAndThisObj(node2, node);
                str = "call0";
                str2 = "(Lorg/mozilla/javascript/Callable;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;";
            }
        } else if (type == 39) {
            String string = node2.getString();
            generateCallArgArray(node, next, false);
            this.cfw.addPush(string);
            str = "callName";
            str2 = "([Ljava/lang/Object;Ljava/lang/String;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;";
        } else {
            int i2 = 0;
            for (Node next2 = next; next2 != null; next2 = next2.getNext()) {
                i2++;
            }
            generateFunctionAndThisObj(node2, node);
            if (i2 == 1) {
                generateExpression(next, node);
                str = "call1";
                str2 = "(Lorg/mozilla/javascript/Callable;Lorg/mozilla/javascript/Scriptable;Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;";
            } else if (i2 == 2) {
                generateExpression(next, node);
                generateExpression(next.getNext(), node);
                str = "call2";
                str2 = "(Lorg/mozilla/javascript/Callable;Lorg/mozilla/javascript/Scriptable;Ljava/lang/Object;Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;";
            } else {
                generateCallArgArray(node, next, false);
                str = "callN";
                str2 = "(Lorg/mozilla/javascript/Callable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;";
            }
        }
        this.cfw.addALoad(this.contextLocal);
        this.cfw.addALoad(this.variableObjectLocal);
        addOptRuntimeInvoke(str, str2);
    }

    private void visitStandardNew(Node node, Node node2) throws RuntimeException {
        if (node.getType() != 30) {
            throw Codegen.badTree();
        }
        Node next = node2.getNext();
        generateExpression(node2, node);
        this.cfw.addALoad(this.contextLocal);
        this.cfw.addALoad(this.variableObjectLocal);
        generateCallArgArray(node, next, false);
        addScriptRuntimeInvoke("newObject", "(Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)Lorg/mozilla/javascript/Scriptable;");
    }

    private void visitStrictSetName(Node node, Node node2) throws RuntimeException {
        String string = node.getFirstChild().getString();
        while (node2 != null) {
            generateExpression(node2, node);
            node2 = node2.getNext();
        }
        this.cfw.addALoad(this.contextLocal);
        this.cfw.addALoad(this.variableObjectLocal);
        this.cfw.addPush(string);
        addScriptRuntimeInvoke("strictSetName", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;)Ljava/lang/Object;");
    }

    private void visitSwitch(Jump jump, Node node) throws RuntimeException {
        generateExpression(node, jump);
        short newWordLocal = getNewWordLocal();
        this.cfw.addAStore(newWordLocal);
        for (Jump jump2 = (Jump) node.getNext(); jump2 != null; jump2 = (Jump) jump2.getNext()) {
            if (jump2.getType() != 116) {
                throw Codegen.badTree();
            }
            generateExpression(jump2.getFirstChild(), jump2);
            this.cfw.addALoad(newWordLocal);
            addScriptRuntimeInvoke("shallowEq", "(Ljava/lang/Object;Ljava/lang/Object;)Z");
            addGoto(jump2.target, 154);
        }
        releaseWordLocal(newWordLocal);
    }

    private void visitTryCatchFinally(Jump jump, Node node) throws RuntimeException {
        int i2;
        int i3;
        short newWordLocal = getNewWordLocal();
        this.cfw.addALoad(this.variableObjectLocal);
        this.cfw.addAStore(newWordLocal);
        int iAcquireLabel = this.cfw.acquireLabel();
        this.cfw.markLabel(iAcquireLabel, (short) 0);
        Node node2 = jump.target;
        Node node3 = jump.getFinally();
        int[] iArr = new int[5];
        this.exceptionManager.pushExceptionInfo(jump);
        if (node2 != null) {
            iArr[0] = this.cfw.acquireLabel();
            iArr[1] = this.cfw.acquireLabel();
            iArr[2] = this.cfw.acquireLabel();
            Context currentContext = Context.getCurrentContext();
            if (currentContext != null && currentContext.hasFeature(13)) {
                iArr[3] = this.cfw.acquireLabel();
            }
        }
        if (node3 != null) {
            iArr[4] = this.cfw.acquireLabel();
        }
        this.exceptionManager.setHandlers(iArr, iAcquireLabel);
        if (this.isGenerator && node3 != null) {
            FinallyReturnPoint finallyReturnPoint = new FinallyReturnPoint();
            if (this.finallys == null) {
                this.finallys = new HashMap();
            }
            this.finallys.put(node3, finallyReturnPoint);
            this.finallys.put(node3.getNext(), finallyReturnPoint);
        }
        for (Node next = node; next != null; next = next.getNext()) {
            if (next == node2) {
                int targetLabel = getTargetLabel(node2);
                this.exceptionManager.removeHandler(0, targetLabel);
                this.exceptionManager.removeHandler(1, targetLabel);
                this.exceptionManager.removeHandler(2, targetLabel);
                this.exceptionManager.removeHandler(3, targetLabel);
            }
            generateStatement(next);
        }
        int iAcquireLabel2 = this.cfw.acquireLabel();
        this.cfw.add(167, iAcquireLabel2);
        int localBlockRegister = getLocalBlockRegister(jump);
        if (node2 != null) {
            int iLabelId = node2.labelId();
            i2 = localBlockRegister;
            i3 = iAcquireLabel2;
            generateCatchBlock(0, newWordLocal, iLabelId, localBlockRegister, iArr[0]);
            generateCatchBlock(1, newWordLocal, iLabelId, localBlockRegister, iArr[1]);
            generateCatchBlock(2, newWordLocal, iLabelId, localBlockRegister, iArr[2]);
            Context currentContext2 = Context.getCurrentContext();
            if (currentContext2 != null && currentContext2.hasFeature(13)) {
                generateCatchBlock(3, newWordLocal, iLabelId, i2, iArr[3]);
            }
        } else {
            i2 = localBlockRegister;
            i3 = iAcquireLabel2;
        }
        if (node3 != null) {
            int iAcquireLabel3 = this.cfw.acquireLabel();
            int iAcquireLabel4 = this.cfw.acquireLabel();
            this.cfw.markHandler(iAcquireLabel3);
            if (!this.isGenerator) {
                this.cfw.markLabel(iArr[4]);
            }
            int i4 = i2;
            this.cfw.addAStore(i4);
            this.cfw.addALoad(newWordLocal);
            this.cfw.addAStore(this.variableObjectLocal);
            int iLabelId2 = node3.labelId();
            if (this.isGenerator) {
                addGotoWithReturn(node3);
            } else {
                inlineFinally(node3, iArr[4], iAcquireLabel4);
            }
            this.cfw.addALoad(i4);
            if (this.isGenerator) {
                this.cfw.add(192, "java/lang/Throwable");
            }
            this.cfw.add(191);
            this.cfw.markLabel(iAcquireLabel4);
            if (this.isGenerator) {
                this.cfw.addExceptionHandler(iAcquireLabel, iLabelId2, iAcquireLabel3, null);
            }
        }
        releaseWordLocal(newWordLocal);
        this.cfw.markLabel(i3);
        if (this.isGenerator) {
            return;
        }
        this.exceptionManager.popExceptionInfo();
    }

    private void visitTypeofname(Node node) throws RuntimeException {
        int indexForNameNode;
        if (!this.hasVarsInRegs || (indexForNameNode = this.fnCurrent.fnode.getIndexForNameNode(node)) < 0) {
            this.cfw.addALoad(this.variableObjectLocal);
            this.cfw.addPush(node.getString());
            addScriptRuntimeInvoke("typeofName", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;)Ljava/lang/String;");
            return;
        }
        if (this.fnCurrent.isNumberVar(indexForNameNode)) {
            this.cfw.addPush("number");
            return;
        }
        if (!varIsDirectCallParameter(indexForNameNode)) {
            this.cfw.addALoad(this.varRegisters[indexForNameNode]);
            addScriptRuntimeInvoke("typeof", "(Ljava/lang/Object;)Ljava/lang/String;");
            return;
        }
        short s2 = this.varRegisters[indexForNameNode];
        this.cfw.addALoad(s2);
        this.cfw.add(178, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
        int iAcquireLabel = this.cfw.acquireLabel();
        this.cfw.add(165, iAcquireLabel);
        short stackTop = this.cfw.getStackTop();
        this.cfw.addALoad(s2);
        addScriptRuntimeInvoke("typeof", "(Ljava/lang/Object;)Ljava/lang/String;");
        int iAcquireLabel2 = this.cfw.acquireLabel();
        this.cfw.add(167, iAcquireLabel2);
        this.cfw.markLabel(iAcquireLabel, stackTop);
        this.cfw.addPush("number");
        this.cfw.markLabel(iAcquireLabel2);
    }

    void generateBodyCode() {
        this.isGenerator = Codegen.isGenerator(this.scriptOrFn);
        initBodyGeneration();
        if (this.isGenerator) {
            String str = "(" + this.codegen.mainClassSignature + "Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Ljava/lang/Object;Ljava/lang/Object;I)Ljava/lang/Object;";
            this.cfw.startMethod(this.codegen.getBodyMethodName(this.scriptOrFn) + "_gen", str, (short) 10);
        } else {
            this.cfw.startMethod(this.codegen.getBodyMethodName(this.scriptOrFn), this.codegen.getBodyMethodSignature(this.scriptOrFn), (short) 10);
        }
        generatePrologue();
        generateStatement(this.fnCurrent != null ? this.scriptOrFn.getLastChild() : this.scriptOrFn);
        generateEpilogue();
        this.cfw.stopMethod((short) (this.localsMax + 1));
        if (this.isGenerator) {
            generateGenerator();
        }
        if (this.literals != null) {
            for (int i2 = 0; i2 < this.literals.size(); i2++) {
                Node node = this.literals.get(i2);
                int type = node.getType();
                if (type == 66) {
                    generateArrayLiteralFactory(node, i2 + 1);
                } else if (type != 67) {
                    Kit.codeBug(Token.typeToName(type));
                } else {
                    generateObjectLiteralFactory(node, i2 + 1);
                }
            }
        }
    }

    private short getNewWordLocal() {
        return getNewWordIntern(1);
    }

    private void addInstructionCount(int i2) throws RuntimeException {
        this.cfw.addALoad(this.contextLocal);
        this.cfw.addPush(i2);
        addScriptRuntimeInvoke("addInstructionCount", "(Lorg/mozilla/javascript/Context;I)V");
    }

    private void inlineFinally(Node node) throws RuntimeException {
        int iAcquireLabel = this.cfw.acquireLabel();
        int iAcquireLabel2 = this.cfw.acquireLabel();
        this.cfw.markLabel(iAcquireLabel);
        inlineFinally(node, iAcquireLabel, iAcquireLabel2);
        this.cfw.markLabel(iAcquireLabel2);
    }
}
