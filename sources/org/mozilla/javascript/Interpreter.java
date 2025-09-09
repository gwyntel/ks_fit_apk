package org.mozilla.javascript;

import com.alibaba.ailabs.iot.mesh.StatusCode;
import com.google.common.base.Ascii;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.ast.ScriptNode;
import org.mozilla.javascript.debug.DebugFrame;

/* loaded from: classes5.dex */
public final class Interpreter extends Icode implements Evaluator {
    static final int EXCEPTION_HANDLER_SLOT = 2;
    static final int EXCEPTION_LOCAL_SLOT = 4;
    static final int EXCEPTION_SCOPE_SLOT = 5;
    static final int EXCEPTION_SLOT_SIZE = 6;
    static final int EXCEPTION_TRY_END_SLOT = 1;
    static final int EXCEPTION_TRY_START_SLOT = 0;
    static final int EXCEPTION_TYPE_SLOT = 3;
    InterpreterData itsData;

    private static class CallFrame implements Cloneable, Serializable {
        static final long serialVersionUID = -2843792508994958978L;
        DebugFrame debuggerFrame;
        int emptyStackTop;
        InterpretedFunction fnOrScript;
        int frameIndex;
        boolean frozen;
        InterpreterData idata;
        boolean isContinuationsTopFrame;
        int localShift;
        CallFrame parentFrame;
        int pc;
        int pcPrevBranch;
        int pcSourceLineStart;
        Object result;
        double resultDbl;
        double[] sDbl;
        int savedCallOp;
        int savedStackTop;
        Scriptable scope;
        Object[] stack;
        int[] stackAttributes;
        Scriptable thisObj;
        Object throwable;
        boolean useActivation;
        CallFrame varSource;

        private CallFrame() {
        }

        CallFrame cloneFrozen() throws RuntimeException {
            if (!this.frozen) {
                Kit.codeBug();
            }
            try {
                CallFrame callFrame = (CallFrame) clone();
                callFrame.stack = (Object[]) this.stack.clone();
                callFrame.stackAttributes = (int[]) this.stackAttributes.clone();
                callFrame.sDbl = (double[]) this.sDbl.clone();
                callFrame.frozen = false;
                return callFrame;
            } catch (CloneNotSupportedException unused) {
                throw new IllegalStateException();
            }
        }
    }

    private static final class ContinuationJump implements Serializable {
        static final long serialVersionUID = 7687739156004308247L;
        CallFrame branchFrame;
        CallFrame capturedFrame;
        Object result;
        double resultDbl;

        ContinuationJump(NativeContinuation nativeContinuation, CallFrame callFrame) throws RuntimeException {
            CallFrame callFrame2 = (CallFrame) nativeContinuation.getImplementation();
            this.capturedFrame = callFrame2;
            if (callFrame2 == null || callFrame == null) {
                this.branchFrame = null;
                return;
            }
            int i2 = callFrame2.frameIndex - callFrame.frameIndex;
            if (i2 != 0) {
                if (i2 < 0) {
                    i2 = -i2;
                } else {
                    callFrame = callFrame2;
                    callFrame2 = callFrame;
                }
                do {
                    callFrame = callFrame.parentFrame;
                    i2--;
                } while (i2 != 0);
                if (callFrame.frameIndex != callFrame2.frameIndex) {
                    Kit.codeBug();
                }
                callFrame = callFrame2;
                callFrame2 = callFrame;
            }
            while (callFrame2 != callFrame && callFrame2 != null) {
                callFrame2 = callFrame2.parentFrame;
                callFrame = callFrame.parentFrame;
            }
            this.branchFrame = callFrame2;
            if (callFrame2 == null || callFrame2.frozen) {
                return;
            }
            Kit.codeBug();
        }
    }

    static class GeneratorState {
        int operation;
        RuntimeException returnedException;
        Object value;

        GeneratorState(int i2, Object obj) {
            this.operation = i2;
            this.value = obj;
        }
    }

    private static void addInstructionCount(Context context, CallFrame callFrame, int i2) {
        int i3 = context.instructionCount + (callFrame.pc - callFrame.pcPrevBranch) + i2;
        context.instructionCount = i3;
        if (i3 > context.instructionThreshold) {
            context.observeInstructionCount(i3);
            context.instructionCount = 0;
        }
    }

    private static int bytecodeSpan(int i2) {
        if (i2 != -54 && i2 != -23) {
            if (i2 == -21) {
                return 5;
            }
            if (i2 != 50) {
                if (i2 != 57) {
                    if (i2 != 73 && i2 != 5 && i2 != 6 && i2 != 7) {
                        switch (i2) {
                            case -63:
                            case -62:
                                break;
                            default:
                                switch (i2) {
                                    case -49:
                                    case -48:
                                        break;
                                    case -47:
                                        return 5;
                                    case -46:
                                        return 3;
                                    case -45:
                                        return 2;
                                    default:
                                        switch (i2) {
                                            case StatusCode.ERROR_CONFIG_MODEL_SUBSCRIPTION_ADD /* -40 */:
                                                return 5;
                                            case -39:
                                                return 3;
                                            case -38:
                                                return 2;
                                            default:
                                                switch (i2) {
                                                    case StatusCode.ERROR_WAKEUP_FAIL /* -28 */:
                                                        return 5;
                                                    case StatusCode.ERROR_LOCAL_TRANSLATION_NOT_SUPPORT /* -27 */:
                                                    case StatusCode.ERROR_LOCAL_CONTROL_FAILED /* -26 */:
                                                        return 3;
                                                    default:
                                                        switch (i2) {
                                                            case -11:
                                                            case -10:
                                                            case -9:
                                                            case -8:
                                                            case -7:
                                                                return 2;
                                                            case -6:
                                                                break;
                                                            default:
                                                                if (Icode.validBytecode(i2)) {
                                                                    return 1;
                                                                }
                                                                throw Kit.codeBug();
                                                        }
                                                }
                                        }
                                }
                            case -61:
                                return 2;
                        }
                    }
                }
                return 2;
            }
            return 3;
        }
        return 3;
    }

    public static NativeContinuation captureContinuation(Context context) {
        Object obj = context.lastInterpreterFrame;
        if (obj == null || !(obj instanceof CallFrame)) {
            throw new IllegalStateException("Interpreter frames not found");
        }
        return captureContinuation(context, (CallFrame) obj, true);
    }

    private static CallFrame captureFrameForGenerator(CallFrame callFrame) throws RuntimeException {
        callFrame.frozen = true;
        CallFrame callFrameCloneFrozen = callFrame.cloneFrozen();
        callFrame.frozen = false;
        callFrameCloneFrozen.parentFrame = null;
        callFrameCloneFrozen.frameIndex = 0;
        return callFrameCloneFrozen;
    }

    private static void doAdd(Object[] objArr, double[] dArr, int i2, Context context) {
        boolean z2;
        double d2;
        int i3 = i2 + 1;
        Object obj = objArr[i3];
        Object obj2 = objArr[i2];
        UniqueTag uniqueTag = UniqueTag.DOUBLE_MARK;
        if (obj == uniqueTag) {
            d2 = dArr[i3];
            if (obj2 == uniqueTag) {
                dArr[i2] = dArr[i2] + d2;
                return;
            }
            z2 = true;
        } else {
            if (obj2 != uniqueTag) {
                if ((obj2 instanceof Scriptable) || (obj instanceof Scriptable)) {
                    objArr[i2] = ScriptRuntime.add(obj2, obj, context);
                    return;
                }
                if ((obj2 instanceof CharSequence) || (obj instanceof CharSequence)) {
                    objArr[i2] = new ConsString(ScriptRuntime.toCharSequence(obj2), ScriptRuntime.toCharSequence(obj));
                    return;
                }
                double dDoubleValue = obj2 instanceof Number ? ((Number) obj2).doubleValue() : ScriptRuntime.toNumber(obj2);
                double dDoubleValue2 = obj instanceof Number ? ((Number) obj).doubleValue() : ScriptRuntime.toNumber(obj);
                objArr[i2] = uniqueTag;
                dArr[i2] = dDoubleValue + dDoubleValue2;
                return;
            }
            obj2 = obj;
            z2 = false;
            d2 = dArr[i2];
        }
        if (obj2 instanceof Scriptable) {
            Object objWrapNumber = ScriptRuntime.wrapNumber(d2);
            if (!z2) {
                Object obj3 = obj2;
                obj2 = objWrapNumber;
                objWrapNumber = obj3;
            }
            objArr[i2] = ScriptRuntime.add(obj2, objWrapNumber, context);
            return;
        }
        if (!(obj2 instanceof CharSequence)) {
            double dDoubleValue3 = obj2 instanceof Number ? ((Number) obj2).doubleValue() : ScriptRuntime.toNumber(obj2);
            objArr[i2] = uniqueTag;
            dArr[i2] = dDoubleValue3 + d2;
        } else {
            CharSequence charSequence = (CharSequence) obj2;
            CharSequence charSequence2 = ScriptRuntime.toCharSequence(Double.valueOf(d2));
            if (z2) {
                objArr[i2] = new ConsString(charSequence, charSequence2);
            } else {
                objArr[i2] = new ConsString(charSequence2, charSequence);
            }
        }
    }

    private static int doArithmetic(CallFrame callFrame, int i2, Object[] objArr, double[] dArr, int i3) {
        double dStack_double = stack_double(callFrame, i3);
        int i4 = i3 - 1;
        double dStack_double2 = stack_double(callFrame, i4);
        objArr[i4] = UniqueTag.DOUBLE_MARK;
        switch (i2) {
            case 22:
                dStack_double2 -= dStack_double;
                break;
            case 23:
                dStack_double2 *= dStack_double;
                break;
            case 24:
                dStack_double2 /= dStack_double;
                break;
            case 25:
                dStack_double2 %= dStack_double;
                break;
        }
        dArr[i4] = dStack_double2;
        return i4;
    }

    private static int doBitOp(CallFrame callFrame, int i2, Object[] objArr, double[] dArr, int i3) {
        int iStack_int32 = stack_int32(callFrame, i3 - 1);
        int iStack_int322 = stack_int32(callFrame, i3);
        int i4 = i3 - 1;
        objArr[i4] = UniqueTag.DOUBLE_MARK;
        if (i2 == 18) {
            iStack_int32 <<= iStack_int322;
        } else if (i2 != 19) {
            switch (i2) {
                case 9:
                    iStack_int32 |= iStack_int322;
                    break;
                case 10:
                    iStack_int32 ^= iStack_int322;
                    break;
                case 11:
                    iStack_int32 &= iStack_int322;
                    break;
            }
        } else {
            iStack_int32 >>= iStack_int322;
        }
        dArr[i4] = iStack_int32;
        return i4;
    }

    private static int doCallSpecial(Context context, CallFrame callFrame, Object[] objArr, double[] dArr, int i2, byte[] bArr, int i3) {
        int i4;
        int i5 = callFrame.pc;
        int i6 = bArr[i5] & 255;
        boolean z2 = bArr[i5 + 1] != 0;
        int index = getIndex(bArr, i5 + 2);
        if (z2) {
            i4 = i2 - i3;
            Object objWrapNumber = objArr[i4];
            if (objWrapNumber == UniqueTag.DOUBLE_MARK) {
                objWrapNumber = ScriptRuntime.wrapNumber(dArr[i4]);
            }
            objArr[i4] = ScriptRuntime.newSpecial(context, objWrapNumber, getArgsArray(objArr, dArr, i4 + 1, i3), callFrame.scope, i6);
        } else {
            i4 = i2 - (i3 + 1);
            objArr[i4] = ScriptRuntime.callSpecial(context, (Callable) objArr[i4], (Scriptable) objArr[i4 + 1], getArgsArray(objArr, dArr, i4 + 2, i3), callFrame.scope, callFrame.thisObj, i6, callFrame.idata.itsSourceFile, index);
        }
        callFrame.pc += 4;
        return i4;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int doCompare(org.mozilla.javascript.Interpreter.CallFrame r5, int r6, java.lang.Object[] r7, double[] r8, int r9) {
        /*
            int r0 = r9 + (-1)
            r1 = r7[r9]
            r2 = r7[r0]
            org.mozilla.javascript.UniqueTag r3 = org.mozilla.javascript.UniqueTag.DOUBLE_MARK
            if (r1 != r3) goto L11
            r1 = r8[r9]
            double r8 = stack_double(r5, r0)
            goto L1a
        L11:
            if (r2 != r3) goto L39
            double r1 = org.mozilla.javascript.ScriptRuntime.toNumber(r1)
            r3 = r8[r0]
            r8 = r3
        L1a:
            r5 = 0
            r3 = 1
            switch(r6) {
                case 14: goto L34;
                case 15: goto L2f;
                case 16: goto L2a;
                case 17: goto L24;
                default: goto L1f;
            }
        L1f:
            java.lang.RuntimeException r5 = org.mozilla.javascript.Kit.codeBug()
            throw r5
        L24:
            int r6 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
            if (r6 < 0) goto L54
        L28:
            r5 = r3
            goto L54
        L2a:
            int r6 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
            if (r6 <= 0) goto L54
            goto L28
        L2f:
            int r6 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
            if (r6 > 0) goto L54
            goto L28
        L34:
            int r6 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
            if (r6 >= 0) goto L54
            goto L28
        L39:
            switch(r6) {
                case 14: goto L50;
                case 15: goto L4b;
                case 16: goto L46;
                case 17: goto L41;
                default: goto L3c;
            }
        L3c:
            java.lang.RuntimeException r5 = org.mozilla.javascript.Kit.codeBug()
            throw r5
        L41:
            boolean r5 = org.mozilla.javascript.ScriptRuntime.cmp_LE(r1, r2)
            goto L54
        L46:
            boolean r5 = org.mozilla.javascript.ScriptRuntime.cmp_LT(r1, r2)
            goto L54
        L4b:
            boolean r5 = org.mozilla.javascript.ScriptRuntime.cmp_LE(r2, r1)
            goto L54
        L50:
            boolean r5 = org.mozilla.javascript.ScriptRuntime.cmp_LT(r2, r1)
        L54:
            java.lang.Boolean r5 = org.mozilla.javascript.ScriptRuntime.wrapBoolean(r5)
            r7[r0] = r5
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.Interpreter.doCompare(org.mozilla.javascript.Interpreter$CallFrame, int, java.lang.Object[], double[], int):int");
    }

    private static int doDelName(Context context, CallFrame callFrame, int i2, Object[] objArr, double[] dArr, int i3) {
        Object objWrapNumber = objArr[i3];
        UniqueTag uniqueTag = UniqueTag.DOUBLE_MARK;
        if (objWrapNumber == uniqueTag) {
            objWrapNumber = ScriptRuntime.wrapNumber(dArr[i3]);
        }
        int i4 = i3 - 1;
        Object objWrapNumber2 = objArr[i4];
        if (objWrapNumber2 == uniqueTag) {
            objWrapNumber2 = ScriptRuntime.wrapNumber(dArr[i4]);
        }
        objArr[i4] = ScriptRuntime.delete(objWrapNumber2, objWrapNumber, context, callFrame.scope, i2 == 0);
        return i4;
    }

    private static int doElemIncDec(Context context, CallFrame callFrame, byte[] bArr, Object[] objArr, double[] dArr, int i2) {
        Object objWrapNumber = objArr[i2];
        UniqueTag uniqueTag = UniqueTag.DOUBLE_MARK;
        if (objWrapNumber == uniqueTag) {
            objWrapNumber = ScriptRuntime.wrapNumber(dArr[i2]);
        }
        int i3 = i2 - 1;
        Object objWrapNumber2 = objArr[i3];
        if (objWrapNumber2 == uniqueTag) {
            objWrapNumber2 = ScriptRuntime.wrapNumber(dArr[i3]);
        }
        objArr[i3] = ScriptRuntime.elemIncrDecr(objWrapNumber2, objWrapNumber, context, callFrame.scope, bArr[callFrame.pc]);
        callFrame.pc++;
        return i3;
    }

    private static boolean doEquals(Object[] objArr, double[] dArr, int i2) {
        int i3 = i2 + 1;
        Object obj = objArr[i3];
        Object obj2 = objArr[i2];
        UniqueTag uniqueTag = UniqueTag.DOUBLE_MARK;
        return obj == uniqueTag ? obj2 == uniqueTag ? dArr[i2] == dArr[i3] : ScriptRuntime.eqNumber(dArr[i3], obj2) : obj2 == uniqueTag ? ScriptRuntime.eqNumber(dArr[i2], obj) : ScriptRuntime.eq(obj2, obj);
    }

    private static int doGetElem(Context context, CallFrame callFrame, Object[] objArr, double[] dArr, int i2) {
        int i3 = i2 - 1;
        Object objWrapNumber = objArr[i3];
        UniqueTag uniqueTag = UniqueTag.DOUBLE_MARK;
        if (objWrapNumber == uniqueTag) {
            objWrapNumber = ScriptRuntime.wrapNumber(dArr[i3]);
        }
        Object obj = objArr[i2];
        objArr[i3] = obj != uniqueTag ? ScriptRuntime.getObjectElem(objWrapNumber, obj, context, callFrame.scope) : ScriptRuntime.getObjectIndex(objWrapNumber, dArr[i2], context, callFrame.scope);
        return i3;
    }

    private static int doGetVar(CallFrame callFrame, Object[] objArr, double[] dArr, int i2, Object[] objArr2, double[] dArr2, int i3) {
        int i4 = i2 + 1;
        if (callFrame.useActivation) {
            String str = callFrame.idata.argNames[i3];
            Scriptable scriptable = callFrame.scope;
            objArr[i4] = scriptable.get(str, scriptable);
        } else {
            objArr[i4] = objArr2[i3];
            dArr[i4] = dArr2[i3];
        }
        return i4;
    }

    private static int doInOrInstanceof(Context context, int i2, Object[] objArr, double[] dArr, int i3) {
        Object objWrapNumber = objArr[i3];
        UniqueTag uniqueTag = UniqueTag.DOUBLE_MARK;
        if (objWrapNumber == uniqueTag) {
            objWrapNumber = ScriptRuntime.wrapNumber(dArr[i3]);
        }
        int i4 = i3 - 1;
        Object objWrapNumber2 = objArr[i4];
        if (objWrapNumber2 == uniqueTag) {
            objWrapNumber2 = ScriptRuntime.wrapNumber(dArr[i4]);
        }
        objArr[i4] = ScriptRuntime.wrapBoolean(i2 == 52 ? ScriptRuntime.in(objWrapNumber2, objWrapNumber, context) : ScriptRuntime.instanceOf(objWrapNumber2, objWrapNumber, context));
        return i4;
    }

    private static int doRefMember(Context context, Object[] objArr, double[] dArr, int i2, int i3) {
        Object objWrapNumber = objArr[i2];
        UniqueTag uniqueTag = UniqueTag.DOUBLE_MARK;
        if (objWrapNumber == uniqueTag) {
            objWrapNumber = ScriptRuntime.wrapNumber(dArr[i2]);
        }
        int i4 = i2 - 1;
        Object objWrapNumber2 = objArr[i4];
        if (objWrapNumber2 == uniqueTag) {
            objWrapNumber2 = ScriptRuntime.wrapNumber(dArr[i4]);
        }
        objArr[i4] = ScriptRuntime.memberRef(objWrapNumber2, objWrapNumber, context, i3);
        return i4;
    }

    private static int doRefNsMember(Context context, Object[] objArr, double[] dArr, int i2, int i3) {
        Object objWrapNumber = objArr[i2];
        UniqueTag uniqueTag = UniqueTag.DOUBLE_MARK;
        if (objWrapNumber == uniqueTag) {
            objWrapNumber = ScriptRuntime.wrapNumber(dArr[i2]);
        }
        int i4 = i2 - 1;
        Object objWrapNumber2 = objArr[i4];
        if (objWrapNumber2 == uniqueTag) {
            objWrapNumber2 = ScriptRuntime.wrapNumber(dArr[i4]);
        }
        int i5 = i2 - 2;
        Object objWrapNumber3 = objArr[i5];
        if (objWrapNumber3 == uniqueTag) {
            objWrapNumber3 = ScriptRuntime.wrapNumber(dArr[i5]);
        }
        objArr[i5] = ScriptRuntime.memberRef(objWrapNumber3, objWrapNumber2, objWrapNumber, context, i3);
        return i5;
    }

    private static int doRefNsName(Context context, CallFrame callFrame, Object[] objArr, double[] dArr, int i2, int i3) {
        Object objWrapNumber = objArr[i2];
        UniqueTag uniqueTag = UniqueTag.DOUBLE_MARK;
        if (objWrapNumber == uniqueTag) {
            objWrapNumber = ScriptRuntime.wrapNumber(dArr[i2]);
        }
        int i4 = i2 - 1;
        Object objWrapNumber2 = objArr[i4];
        if (objWrapNumber2 == uniqueTag) {
            objWrapNumber2 = ScriptRuntime.wrapNumber(dArr[i4]);
        }
        objArr[i4] = ScriptRuntime.nameRef(objWrapNumber2, objWrapNumber, context, callFrame.scope, i3);
        return i4;
    }

    private static int doSetConstVar(CallFrame callFrame, Object[] objArr, double[] dArr, int i2, Object[] objArr2, double[] dArr2, int[] iArr, int i3) {
        if (callFrame.useActivation) {
            Object objWrapNumber = objArr[i2];
            if (objWrapNumber == UniqueTag.DOUBLE_MARK) {
                objWrapNumber = ScriptRuntime.wrapNumber(dArr[i2]);
            }
            String str = callFrame.idata.argNames[i3];
            Scriptable scriptable = callFrame.scope;
            if (!(scriptable instanceof ConstProperties)) {
                throw Kit.codeBug();
            }
            ((ConstProperties) scriptable).putConst(str, scriptable, objWrapNumber);
        } else {
            int i4 = iArr[i3];
            if ((i4 & 1) == 0) {
                throw Context.reportRuntimeError1("msg.var.redecl", callFrame.idata.argNames[i3]);
            }
            if ((i4 & 8) != 0) {
                objArr2[i3] = objArr[i2];
                iArr[i3] = i4 & (-9);
                dArr2[i3] = dArr[i2];
            }
        }
        return i2;
    }

    private static int doSetElem(Context context, CallFrame callFrame, Object[] objArr, double[] dArr, int i2) {
        int i3 = i2 - 2;
        Object objWrapNumber = objArr[i2];
        UniqueTag uniqueTag = UniqueTag.DOUBLE_MARK;
        if (objWrapNumber == uniqueTag) {
            objWrapNumber = ScriptRuntime.wrapNumber(dArr[i2]);
        }
        Object obj = objWrapNumber;
        Object objWrapNumber2 = objArr[i3];
        if (objWrapNumber2 == uniqueTag) {
            objWrapNumber2 = ScriptRuntime.wrapNumber(dArr[i3]);
        }
        Object obj2 = objWrapNumber2;
        int i4 = i2 - 1;
        Object obj3 = objArr[i4];
        objArr[i3] = obj3 != uniqueTag ? ScriptRuntime.setObjectElem(obj2, obj3, obj, context, callFrame.scope) : ScriptRuntime.setObjectIndex(obj2, dArr[i4], obj, context, callFrame.scope);
        return i3;
    }

    private static int doSetVar(CallFrame callFrame, Object[] objArr, double[] dArr, int i2, Object[] objArr2, double[] dArr2, int[] iArr, int i3) {
        if (callFrame.useActivation) {
            Object objWrapNumber = objArr[i2];
            if (objWrapNumber == UniqueTag.DOUBLE_MARK) {
                objWrapNumber = ScriptRuntime.wrapNumber(dArr[i2]);
            }
            String str = callFrame.idata.argNames[i3];
            Scriptable scriptable = callFrame.scope;
            scriptable.put(str, scriptable, objWrapNumber);
        } else if ((iArr[i3] & 1) == 0) {
            objArr2[i3] = objArr[i2];
            dArr2[i3] = dArr[i2];
        }
        return i2;
    }

    private static boolean doShallowEquals(Object[] objArr, double[] dArr, int i2) {
        double dDoubleValue;
        double dDoubleValue2;
        int i3 = i2 + 1;
        Object obj = objArr[i3];
        Object obj2 = objArr[i2];
        UniqueTag uniqueTag = UniqueTag.DOUBLE_MARK;
        if (obj == uniqueTag) {
            dDoubleValue2 = dArr[i3];
            if (obj2 == uniqueTag) {
                dDoubleValue = dArr[i2];
            } else {
                if (!(obj2 instanceof Number)) {
                    return false;
                }
                dDoubleValue = ((Number) obj2).doubleValue();
            }
        } else {
            if (obj2 != uniqueTag) {
                return ScriptRuntime.shallowEq(obj2, obj);
            }
            dDoubleValue = dArr[i2];
            if (!(obj instanceof Number)) {
                return false;
            }
            dDoubleValue2 = ((Number) obj).doubleValue();
        }
        return dDoubleValue == dDoubleValue2;
    }

    private static int doVarIncDec(Context context, CallFrame callFrame, Object[] objArr, double[] dArr, int i2, Object[] objArr2, double[] dArr2, int[] iArr, int i3) {
        int i4 = i2 + 1;
        InterpreterData interpreterData = callFrame.idata;
        byte b2 = interpreterData.itsICode[callFrame.pc];
        if (callFrame.useActivation) {
            objArr[i4] = ScriptRuntime.nameIncrDecr(callFrame.scope, interpreterData.argNames[i3], context, b2);
        } else {
            Object obj = objArr2[i3];
            UniqueTag uniqueTag = UniqueTag.DOUBLE_MARK;
            double number = obj == uniqueTag ? dArr2[i3] : ScriptRuntime.toNumber(obj);
            double d2 = (b2 & 1) == 0 ? 1.0d + number : number - 1.0d;
            boolean z2 = (b2 & 2) != 0;
            if ((iArr[i3] & 1) == 0) {
                if (obj != uniqueTag) {
                    objArr2[i3] = uniqueTag;
                }
                dArr2[i3] = d2;
                objArr[i4] = uniqueTag;
                if (!z2) {
                    number = d2;
                }
                dArr[i4] = number;
            } else if (!z2 || obj == uniqueTag) {
                objArr[i4] = uniqueTag;
                if (!z2) {
                    number = d2;
                }
                dArr[i4] = number;
            } else {
                objArr[i4] = obj;
            }
        }
        callFrame.pc++;
        return i4;
    }

    static void dumpICode(InterpreterData interpreterData) {
    }

    private static void enterFrame(Context context, CallFrame callFrame, Object[] objArr, boolean z2) throws RuntimeException {
        CallFrame callFrame2;
        boolean z3 = callFrame.idata.itsNeedsActivation;
        boolean z4 = callFrame.debuggerFrame != null;
        if (z3 || z4) {
            Scriptable parentScope = callFrame.scope;
            if (parentScope == null) {
                Kit.codeBug();
            } else if (z2) {
                while (parentScope instanceof NativeWith) {
                    parentScope = parentScope.getParentScope();
                    if (parentScope == null || ((callFrame2 = callFrame.parentFrame) != null && callFrame2.scope == parentScope)) {
                        Kit.codeBug();
                        break;
                    }
                }
            }
            if (z4) {
                callFrame.debuggerFrame.onEnter(context, parentScope, callFrame.thisObj, objArr);
            }
            if (z3) {
                ScriptRuntime.enterActivationFunction(context, parentScope);
            }
        }
    }

    private static void exitFrame(Context context, CallFrame callFrame, Object obj) {
        if (callFrame.idata.itsNeedsActivation) {
            ScriptRuntime.exitActivationFunction(context);
        }
        DebugFrame debugFrame = callFrame.debuggerFrame;
        if (debugFrame != null) {
            try {
                if (obj instanceof Throwable) {
                    debugFrame.onExit(context, true, obj);
                    return;
                }
                ContinuationJump continuationJump = (ContinuationJump) obj;
                Object objWrapNumber = continuationJump == null ? callFrame.result : continuationJump.result;
                if (objWrapNumber == UniqueTag.DOUBLE_MARK) {
                    objWrapNumber = ScriptRuntime.wrapNumber(continuationJump == null ? callFrame.resultDbl : continuationJump.resultDbl);
                }
                callFrame.debuggerFrame.onExit(context, false, objWrapNumber);
            } catch (Throwable th) {
                PrintStream printStream = System.err;
                printStream.println("RHINO USAGE WARNING: onExit terminated with exception");
                th.printStackTrace(printStream);
            }
        }
    }

    private static Object freezeGenerator(Context context, CallFrame callFrame, int i2, GeneratorState generatorState) {
        if (generatorState.operation == 2) {
            throw ScriptRuntime.typeError0("msg.yield.closing");
        }
        callFrame.frozen = true;
        callFrame.result = callFrame.stack[i2];
        callFrame.resultDbl = callFrame.sDbl[i2];
        callFrame.savedStackTop = i2;
        callFrame.pc--;
        ScriptRuntime.exitActivationFunction(context);
        Object obj = callFrame.result;
        return obj != UniqueTag.DOUBLE_MARK ? obj : ScriptRuntime.wrapNumber(callFrame.resultDbl);
    }

    private static Object[] getArgsArray(Object[] objArr, double[] dArr, int i2, int i3) {
        if (i3 == 0) {
            return ScriptRuntime.emptyArgs;
        }
        Object[] objArr2 = new Object[i3];
        int i4 = 0;
        while (i4 != i3) {
            Object objWrapNumber = objArr[i2];
            if (objWrapNumber == UniqueTag.DOUBLE_MARK) {
                objWrapNumber = ScriptRuntime.wrapNumber(dArr[i2]);
            }
            objArr2[i4] = objWrapNumber;
            i4++;
            i2++;
        }
        return objArr2;
    }

    static String getEncodedSource(InterpreterData interpreterData) {
        String str = interpreterData.encodedSource;
        if (str == null) {
            return null;
        }
        return str.substring(interpreterData.encodedSourceStart, interpreterData.encodedSourceEnd);
    }

    private static int getExceptionHandler(CallFrame callFrame, boolean z2) throws RuntimeException {
        int[] iArr = callFrame.idata.itsExceptionTable;
        int i2 = -1;
        if (iArr == null) {
            return -1;
        }
        int i3 = callFrame.pc - 1;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 != iArr.length; i6 += 6) {
            int i7 = iArr[i6];
            int i8 = iArr[i6 + 1];
            if (i7 <= i3 && i3 < i8 && (!z2 || iArr[i6 + 3] == 1)) {
                if (i2 < 0) {
                    i2 = i6;
                    i5 = i7;
                    i4 = i8;
                } else if (i4 >= i8) {
                    if (i5 > i7) {
                        Kit.codeBug();
                    }
                    if (i4 == i8) {
                        Kit.codeBug();
                    }
                    i2 = i6;
                    i5 = i7;
                    i4 = i8;
                }
            }
        }
        return i2;
    }

    private static int getIndex(byte[] bArr, int i2) {
        return (bArr[i2 + 1] & 255) | ((bArr[i2] & 255) << 8);
    }

    private static int getInt(byte[] bArr, int i2) {
        return (bArr[i2 + 3] & 255) | (bArr[i2] << Ascii.CAN) | ((bArr[i2 + 1] & 255) << 16) | ((bArr[i2 + 2] & 255) << 8);
    }

    static int[] getLineNumbers(InterpreterData interpreterData) throws RuntimeException {
        UintMap uintMap = new UintMap();
        byte[] bArr = interpreterData.itsICode;
        int length = bArr.length;
        int i2 = 0;
        while (i2 != length) {
            byte b2 = bArr[i2];
            int iBytecodeSpan = bytecodeSpan(b2);
            if (b2 == -26) {
                if (iBytecodeSpan != 3) {
                    Kit.codeBug();
                }
                uintMap.put(getIndex(bArr, i2 + 1), 0);
            }
            i2 += iBytecodeSpan;
        }
        return uintMap.getKeys();
    }

    private static int getShort(byte[] bArr, int i2) {
        return (bArr[i2 + 1] & 255) | (bArr[i2] << 8);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x012b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void initFrame(org.mozilla.javascript.Context r18, org.mozilla.javascript.Scriptable r19, org.mozilla.javascript.Scriptable r20, java.lang.Object[] r21, double[] r22, int r23, int r24, org.mozilla.javascript.InterpretedFunction r25, org.mozilla.javascript.Interpreter.CallFrame r26, org.mozilla.javascript.Interpreter.CallFrame r27) throws java.lang.RuntimeException {
        /*
            Method dump skipped, instructions count: 306
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.Interpreter.initFrame(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[], double[], int, int, org.mozilla.javascript.InterpretedFunction, org.mozilla.javascript.Interpreter$CallFrame, org.mozilla.javascript.Interpreter$CallFrame):void");
    }

    private static CallFrame initFrameForApplyOrCall(Context context, CallFrame callFrame, int i2, Object[] objArr, double[] dArr, int i3, int i4, Scriptable scriptable, IdFunctionObject idFunctionObject, InterpretedFunction interpretedFunction) throws RuntimeException {
        Scriptable topCallScope;
        CallFrame callFrame2 = callFrame;
        if (i2 != 0) {
            int i5 = i3 + 2;
            Object objWrapNumber = objArr[i5];
            if (objWrapNumber == UniqueTag.DOUBLE_MARK) {
                objWrapNumber = ScriptRuntime.wrapNumber(dArr[i5]);
            }
            topCallScope = ScriptRuntime.toObjectOrNull(context, objWrapNumber, callFrame2.scope);
        } else {
            topCallScope = null;
        }
        if (topCallScope == null) {
            topCallScope = ScriptRuntime.getTopCallScope(context);
        }
        if (i4 == -55) {
            exitFrame(context, callFrame, null);
            callFrame2 = callFrame2.parentFrame;
        } else {
            callFrame2.savedStackTop = i3;
            callFrame2.savedCallOp = i4;
        }
        CallFrame callFrame3 = callFrame2;
        CallFrame callFrame4 = new CallFrame();
        if (BaseFunction.isApply(idFunctionObject)) {
            Object[] applyArguments = i2 < 2 ? ScriptRuntime.emptyArgs : ScriptRuntime.getApplyArguments(context, objArr[i3 + 3]);
            initFrame(context, scriptable, topCallScope, applyArguments, null, 0, applyArguments.length, interpretedFunction, callFrame3, callFrame4);
        } else {
            for (int i6 = 1; i6 < i2; i6++) {
                int i7 = i3 + 1 + i6;
                int i8 = i3 + 2 + i6;
                objArr[i7] = objArr[i8];
                dArr[i7] = dArr[i8];
            }
            initFrame(context, scriptable, topCallScope, objArr, dArr, i3 + 2, i2 < 2 ? 0 : i2 - 1, interpretedFunction, callFrame3, callFrame4);
        }
        return callFrame4;
    }

    private static CallFrame initFrameForNoSuchMethod(Context context, CallFrame callFrame, int i2, Object[] objArr, double[] dArr, int i3, int i4, Scriptable scriptable, Scriptable scriptable2, ScriptRuntime.NoSuchMethodShim noSuchMethodShim, InterpretedFunction interpretedFunction) throws RuntimeException {
        CallFrame callFrame2;
        int i5 = i3 + 2;
        Object[] objArr2 = new Object[i2];
        int i6 = 0;
        while (i6 < i2) {
            Object objWrapNumber = objArr[i5];
            if (objWrapNumber == UniqueTag.DOUBLE_MARK) {
                objWrapNumber = ScriptRuntime.wrapNumber(dArr[i5]);
            }
            objArr2[i6] = objWrapNumber;
            i6++;
            i5++;
        }
        Object[] objArr3 = {noSuchMethodShim.methodName, context.newArray(scriptable2, objArr2)};
        CallFrame callFrame3 = new CallFrame();
        if (i4 == -55) {
            CallFrame callFrame4 = callFrame.parentFrame;
            exitFrame(context, callFrame, null);
            callFrame2 = callFrame4;
        } else {
            callFrame2 = callFrame;
        }
        initFrame(context, scriptable2, scriptable, objArr3, null, 0, 2, interpretedFunction, callFrame2, callFrame3);
        if (i4 != -55) {
            callFrame.savedStackTop = i3;
            callFrame.savedCallOp = i4;
        }
        return callFrame3;
    }

    private static void initFunction(Context context, Scriptable scriptable, InterpretedFunction interpretedFunction, int i2) {
        InterpretedFunction interpretedFunctionCreateFunction = InterpretedFunction.createFunction(context, scriptable, interpretedFunction, i2);
        ScriptRuntime.initFunction(context, scriptable, interpretedFunctionCreateFunction, interpretedFunctionCreateFunction.idata.itsFunctionType, interpretedFunction.idata.evalScriptFlag);
    }

    static Object interpret(InterpretedFunction interpretedFunction, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) throws RuntimeException {
        if (!ScriptRuntime.hasTopCall(context)) {
            Kit.codeBug();
        }
        Object obj = context.interpreterSecurityDomain;
        Object obj2 = interpretedFunction.securityDomain;
        if (obj != obj2) {
            context.interpreterSecurityDomain = obj2;
            try {
                return interpretedFunction.securityController.callWithDomain(obj2, context, interpretedFunction, scriptable, scriptable2, objArr);
            } finally {
                context.interpreterSecurityDomain = obj;
            }
        }
        CallFrame callFrame = new CallFrame();
        initFrame(context, scriptable, scriptable2, objArr, null, 0, objArr.length, interpretedFunction, null, callFrame);
        callFrame.isContinuationsTopFrame = context.isContinuationsTopCall;
        context.isContinuationsTopCall = false;
        return interpretLoop(context, callFrame, null);
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type update terminated with stack overflow, arg: (r4v9 ?? I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]), method size: 7042
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    private static java.lang.Object interpretLoop(org.mozilla.javascript.Context r47, org.mozilla.javascript.Interpreter.CallFrame r48, java.lang.Object r49) throws java.lang.RuntimeException {
        /*
            Method dump skipped, instructions count: 7042
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.Interpreter.interpretLoop(org.mozilla.javascript.Context, org.mozilla.javascript.Interpreter$CallFrame, java.lang.Object):java.lang.Object");
    }

    private static boolean isFrameEnterExitRequired(CallFrame callFrame) {
        return callFrame.debuggerFrame != null || callFrame.idata.itsNeedsActivation;
    }

    private static CallFrame processThrowable(Context context, Object obj, CallFrame callFrame, int i2, boolean z2) throws RuntimeException {
        if (i2 >= 0) {
            if (callFrame.frozen) {
                callFrame = callFrame.cloneFrozen();
            }
            int[] iArr = callFrame.idata.itsExceptionTable;
            int i3 = iArr[i2 + 2];
            callFrame.pc = i3;
            if (z2) {
                callFrame.pcPrevBranch = i3;
            }
            callFrame.savedStackTop = callFrame.emptyStackTop;
            int i4 = callFrame.localShift;
            int i5 = iArr[i2 + 5] + i4;
            int i6 = i4 + iArr[i2 + 4];
            Object[] objArr = callFrame.stack;
            callFrame.scope = (Scriptable) objArr[i5];
            objArr[i6] = obj;
        } else {
            ContinuationJump continuationJump = (ContinuationJump) obj;
            if (continuationJump.branchFrame != callFrame) {
                Kit.codeBug();
            }
            if (continuationJump.capturedFrame == null) {
                Kit.codeBug();
            }
            CallFrame callFrame2 = continuationJump.capturedFrame;
            int i7 = callFrame2.frameIndex + 1;
            CallFrame callFrame3 = continuationJump.branchFrame;
            if (callFrame3 != null) {
                i7 -= callFrame3.frameIndex;
            }
            CallFrame[] callFrameArr = null;
            int i8 = 0;
            for (int i9 = 0; i9 != i7; i9++) {
                if (!callFrame2.frozen) {
                    Kit.codeBug();
                }
                if (isFrameEnterExitRequired(callFrame2)) {
                    if (callFrameArr == null) {
                        callFrameArr = new CallFrame[i7 - i9];
                    }
                    callFrameArr[i8] = callFrame2;
                    i8++;
                }
                callFrame2 = callFrame2.parentFrame;
            }
            while (i8 != 0) {
                i8--;
                enterFrame(context, callFrameArr[i8], ScriptRuntime.emptyArgs, true);
            }
            callFrame = continuationJump.capturedFrame.cloneFrozen();
            setCallResult(callFrame, continuationJump.result, continuationJump.resultDbl);
        }
        callFrame.throwable = null;
        return callFrame;
    }

    public static Object restartContinuation(NativeContinuation nativeContinuation, Context context, Scriptable scriptable, Object[] objArr) {
        if (!ScriptRuntime.hasTopCall(context)) {
            return ScriptRuntime.doTopCall(nativeContinuation, context, scriptable, null, objArr, context.isTopLevelStrict);
        }
        Object obj = objArr.length == 0 ? Undefined.instance : objArr[0];
        if (((CallFrame) nativeContinuation.getImplementation()) == null) {
            return obj;
        }
        ContinuationJump continuationJump = new ContinuationJump(nativeContinuation, null);
        continuationJump.result = obj;
        return interpretLoop(context, null, continuationJump);
    }

    public static Object resumeGenerator(Context context, Scriptable scriptable, int i2, Object obj, Object obj2) throws RuntimeException {
        CallFrame callFrame = (CallFrame) obj;
        GeneratorState generatorState = new GeneratorState(i2, obj2);
        if (i2 == 2) {
            try {
                return interpretLoop(context, callFrame, generatorState);
            } catch (RuntimeException e2) {
                if (e2 == obj2) {
                    return Undefined.instance;
                }
                throw e2;
            }
        }
        Object objInterpretLoop = interpretLoop(context, callFrame, generatorState);
        RuntimeException runtimeException = generatorState.returnedException;
        if (runtimeException == null) {
            return objInterpretLoop;
        }
        throw runtimeException;
    }

    private static void setCallResult(CallFrame callFrame, Object obj, double d2) throws RuntimeException {
        int i2 = callFrame.savedCallOp;
        if (i2 == 38) {
            Object[] objArr = callFrame.stack;
            int i3 = callFrame.savedStackTop;
            objArr[i3] = obj;
            callFrame.sDbl[i3] = d2;
        } else if (i2 != 30) {
            Kit.codeBug();
        } else if (obj instanceof Scriptable) {
            callFrame.stack[callFrame.savedStackTop] = obj;
        }
        callFrame.savedCallOp = 0;
    }

    private static boolean stack_boolean(CallFrame callFrame, int i2) {
        Object obj = callFrame.stack[i2];
        if (obj == Boolean.TRUE) {
            return true;
        }
        if (obj == Boolean.FALSE) {
            return false;
        }
        if (obj == UniqueTag.DOUBLE_MARK) {
            double d2 = callFrame.sDbl[i2];
            return d2 == d2 && d2 != 0.0d;
        }
        if (obj == null || obj == Undefined.instance) {
            return false;
        }
        if (!(obj instanceof Number)) {
            return obj instanceof Boolean ? ((Boolean) obj).booleanValue() : ScriptRuntime.toBoolean(obj);
        }
        double dDoubleValue = ((Number) obj).doubleValue();
        return dDoubleValue == dDoubleValue && dDoubleValue != 0.0d;
    }

    private static double stack_double(CallFrame callFrame, int i2) {
        Object obj = callFrame.stack[i2];
        return obj != UniqueTag.DOUBLE_MARK ? ScriptRuntime.toNumber(obj) : callFrame.sDbl[i2];
    }

    private static int stack_int32(CallFrame callFrame, int i2) {
        Object obj = callFrame.stack[i2];
        return obj == UniqueTag.DOUBLE_MARK ? ScriptRuntime.toInt32(callFrame.sDbl[i2]) : ScriptRuntime.toInt32(obj);
    }

    private static Object thawGenerator(CallFrame callFrame, int i2, GeneratorState generatorState, int i3) {
        callFrame.frozen = false;
        int index = getIndex(callFrame.idata.itsICode, callFrame.pc);
        callFrame.pc += 2;
        int i4 = generatorState.operation;
        if (i4 == 1) {
            return new JavaScriptException(generatorState.value, callFrame.idata.itsSourceFile, index);
        }
        if (i4 == 2) {
            return generatorState.value;
        }
        if (i4 != 0) {
            throw Kit.codeBug();
        }
        if (i3 == 73) {
            callFrame.stack[i2] = generatorState.value;
        }
        return Scriptable.NOT_FOUND;
    }

    @Override // org.mozilla.javascript.Evaluator
    public void captureStackInfo(RhinoException rhinoException) throws RuntimeException {
        CallFrame[] callFrameArr;
        Context currentContext = Context.getCurrentContext();
        if (currentContext == null || currentContext.lastInterpreterFrame == null) {
            rhinoException.interpreterStackInfo = null;
            rhinoException.interpreterLineData = null;
            return;
        }
        ObjArray objArray = currentContext.previousInterpreterInvocations;
        if (objArray == null || objArray.size() == 0) {
            callFrameArr = new CallFrame[1];
        } else {
            int size = currentContext.previousInterpreterInvocations.size();
            if (currentContext.previousInterpreterInvocations.peek() == currentContext.lastInterpreterFrame) {
                size--;
            }
            callFrameArr = new CallFrame[size + 1];
            currentContext.previousInterpreterInvocations.toArray(callFrameArr);
        }
        callFrameArr[callFrameArr.length - 1] = (CallFrame) currentContext.lastInterpreterFrame;
        int i2 = 0;
        for (int i3 = 0; i3 != callFrameArr.length; i3++) {
            i2 += callFrameArr[i3].frameIndex + 1;
        }
        int[] iArr = new int[i2];
        int length = callFrameArr.length;
        while (length != 0) {
            length--;
            for (CallFrame callFrame = callFrameArr[length]; callFrame != null; callFrame = callFrame.parentFrame) {
                i2--;
                iArr[i2] = callFrame.pcSourceLineStart;
            }
        }
        if (i2 != 0) {
            Kit.codeBug();
        }
        rhinoException.interpreterStackInfo = callFrameArr;
        rhinoException.interpreterLineData = iArr;
    }

    @Override // org.mozilla.javascript.Evaluator
    public Object compile(CompilerEnvirons compilerEnvirons, ScriptNode scriptNode, String str, boolean z2) {
        InterpreterData interpreterDataCompile = new CodeGenerator().compile(compilerEnvirons, scriptNode, str, z2);
        this.itsData = interpreterDataCompile;
        return interpreterDataCompile;
    }

    @Override // org.mozilla.javascript.Evaluator
    public Function createFunctionObject(Context context, Scriptable scriptable, Object obj, Object obj2) throws RuntimeException {
        if (obj != this.itsData) {
            Kit.codeBug();
        }
        return InterpretedFunction.createFunction(context, scriptable, this.itsData, obj2);
    }

    @Override // org.mozilla.javascript.Evaluator
    public Script createScriptObject(Object obj, Object obj2) throws RuntimeException {
        if (obj != this.itsData) {
            Kit.codeBug();
        }
        return InterpretedFunction.createScript(this.itsData, obj2);
    }

    @Override // org.mozilla.javascript.Evaluator
    public String getPatchedStack(RhinoException rhinoException, String str) throws RuntimeException {
        char cCharAt;
        StringBuilder sb = new StringBuilder(str.length() + 1000);
        String systemProperty = SecurityUtilities.getSystemProperty("line.separator");
        CallFrame[] callFrameArr = (CallFrame[]) rhinoException.interpreterStackInfo;
        int[] iArr = rhinoException.interpreterLineData;
        int length = callFrameArr.length;
        int length2 = iArr.length;
        int i2 = 0;
        while (length != 0) {
            length--;
            int iIndexOf = str.indexOf("org.mozilla.javascript.Interpreter.interpretLoop", i2);
            if (iIndexOf < 0) {
                break;
            }
            int i3 = iIndexOf + 48;
            while (i3 != str.length() && (cCharAt = str.charAt(i3)) != '\n' && cCharAt != '\r') {
                i3++;
            }
            sb.append(str.substring(i2, i3));
            for (CallFrame callFrame = callFrameArr[length]; callFrame != null; callFrame = callFrame.parentFrame) {
                if (length2 == 0) {
                    Kit.codeBug();
                }
                length2--;
                InterpreterData interpreterData = callFrame.idata;
                sb.append(systemProperty);
                sb.append("\tat script");
                String str2 = interpreterData.itsName;
                if (str2 != null && str2.length() != 0) {
                    sb.append('.');
                    sb.append(interpreterData.itsName);
                }
                sb.append('(');
                sb.append(interpreterData.itsSourceFile);
                int i4 = iArr[length2];
                if (i4 >= 0) {
                    sb.append(':');
                    sb.append(getIndex(interpreterData.itsICode, i4));
                }
                sb.append(')');
            }
            i2 = i3;
        }
        sb.append(str.substring(i2));
        return sb.toString();
    }

    @Override // org.mozilla.javascript.Evaluator
    public List<String> getScriptStack(RhinoException rhinoException) throws RuntimeException {
        ScriptStackElement[][] scriptStackElements = getScriptStackElements(rhinoException);
        ArrayList arrayList = new ArrayList(scriptStackElements.length);
        String systemProperty = SecurityUtilities.getSystemProperty("line.separator");
        for (ScriptStackElement[] scriptStackElementArr : scriptStackElements) {
            StringBuilder sb = new StringBuilder();
            for (ScriptStackElement scriptStackElement : scriptStackElementArr) {
                scriptStackElement.renderJavaStyle(sb);
                sb.append(systemProperty);
            }
            arrayList.add(sb.toString());
        }
        return arrayList;
    }

    public ScriptStackElement[][] getScriptStackElements(RhinoException rhinoException) throws RuntimeException {
        if (rhinoException.interpreterStackInfo == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        CallFrame[] callFrameArr = (CallFrame[]) rhinoException.interpreterStackInfo;
        int[] iArr = rhinoException.interpreterLineData;
        int length = callFrameArr.length;
        int length2 = iArr.length;
        while (length != 0) {
            length--;
            CallFrame callFrame = callFrameArr[length];
            ArrayList arrayList2 = new ArrayList();
            while (callFrame != null) {
                if (length2 == 0) {
                    Kit.codeBug();
                }
                length2--;
                InterpreterData interpreterData = callFrame.idata;
                String str = interpreterData.itsSourceFile;
                int i2 = iArr[length2];
                int index = i2 >= 0 ? getIndex(interpreterData.itsICode, i2) : -1;
                String str2 = interpreterData.itsName;
                String str3 = (str2 == null || str2.length() == 0) ? null : interpreterData.itsName;
                callFrame = callFrame.parentFrame;
                arrayList2.add(new ScriptStackElement(str, str3, index));
            }
            arrayList.add(arrayList2.toArray(new ScriptStackElement[arrayList2.size()]));
        }
        return (ScriptStackElement[][]) arrayList.toArray(new ScriptStackElement[arrayList.size()][]);
    }

    @Override // org.mozilla.javascript.Evaluator
    public String getSourcePositionFromStack(Context context, int[] iArr) {
        CallFrame callFrame = (CallFrame) context.lastInterpreterFrame;
        InterpreterData interpreterData = callFrame.idata;
        int i2 = callFrame.pcSourceLineStart;
        if (i2 >= 0) {
            iArr[0] = getIndex(interpreterData.itsICode, i2);
        } else {
            iArr[0] = 0;
        }
        return interpreterData.itsSourceFile;
    }

    @Override // org.mozilla.javascript.Evaluator
    public void setEvalScriptFlag(Script script) {
        ((InterpretedFunction) script).idata.evalScriptFlag = true;
    }

    private static NativeContinuation captureContinuation(Context context, CallFrame callFrame, boolean z2) throws RuntimeException {
        Object[] objArr;
        NativeContinuation nativeContinuation = new NativeContinuation();
        ScriptRuntime.setObjectProtoAndParent(nativeContinuation, ScriptRuntime.getTopCallScope(context));
        CallFrame callFrame2 = callFrame;
        CallFrame callFrame3 = callFrame2;
        while (callFrame2 != null && !callFrame2.frozen) {
            callFrame2.frozen = true;
            int i2 = callFrame2.savedStackTop + 1;
            while (true) {
                objArr = callFrame2.stack;
                if (i2 == objArr.length) {
                    break;
                }
                objArr[i2] = null;
                callFrame2.stackAttributes[i2] = 0;
                i2++;
            }
            int i3 = callFrame2.savedCallOp;
            if (i3 == 38) {
                objArr[callFrame2.savedStackTop] = null;
            } else if (i3 != 30) {
                Kit.codeBug();
            }
            callFrame3 = callFrame2;
            callFrame2 = callFrame2.parentFrame;
        }
        if (z2) {
            while (true) {
                CallFrame callFrame4 = callFrame3.parentFrame;
                if (callFrame4 == null) {
                    break;
                }
                callFrame3 = callFrame4;
            }
            if (!callFrame3.isContinuationsTopFrame) {
                throw new IllegalStateException("Cannot capture continuation from JavaScript code not called directly by executeScriptWithContinuations or callFunctionWithContinuations");
            }
        }
        nativeContinuation.initImplementation(callFrame);
        return nativeContinuation;
    }
}
