package org.mozilla.javascript.optimizer;

import org.mozilla.javascript.Kit;
import org.mozilla.javascript.Node;
import org.mozilla.javascript.ast.FunctionNode;
import org.mozilla.javascript.ast.ScriptNode;

/* loaded from: classes5.dex */
public final class OptFunctionNode {
    private int directTargetIndex = -1;
    public final FunctionNode fnode;
    boolean itsContainsCalls0;
    boolean itsContainsCalls1;
    private boolean itsParameterNumberContext;
    private boolean[] numberVarFlags;

    OptFunctionNode(FunctionNode functionNode) {
        this.fnode = functionNode;
        functionNode.setCompilerData(this);
    }

    public static OptFunctionNode get(ScriptNode scriptNode, int i2) {
        return (OptFunctionNode) scriptNode.getFunctionNode(i2).getCompilerData();
    }

    public int getDirectTargetIndex() {
        return this.directTargetIndex;
    }

    public boolean getParameterNumberContext() {
        return this.itsParameterNumberContext;
    }

    public int getVarCount() {
        return this.fnode.getParamAndVarCount();
    }

    public int getVarIndex(Node node) throws RuntimeException {
        Node firstChild;
        int intProp = node.getIntProp(7, -1);
        if (intProp == -1) {
            int type = node.getType();
            if (type == 55) {
                firstChild = node;
            } else {
                if (type != 56 && type != 157) {
                    throw Kit.codeBug();
                }
                firstChild = node.getFirstChild();
            }
            intProp = this.fnode.getIndexForNameNode(firstChild);
            if (intProp < 0) {
                throw Kit.codeBug();
            }
            node.putIntProp(7, intProp);
        }
        return intProp;
    }

    public boolean isNumberVar(int i2) {
        boolean[] zArr;
        int paramCount = i2 - this.fnode.getParamCount();
        if (paramCount < 0 || (zArr = this.numberVarFlags) == null) {
            return false;
        }
        return zArr[paramCount];
    }

    public boolean isParameter(int i2) {
        return i2 < this.fnode.getParamCount();
    }

    public boolean isTargetOfDirectCall() {
        return this.directTargetIndex >= 0;
    }

    void setDirectTargetIndex(int i2) throws RuntimeException {
        if (i2 < 0 || this.directTargetIndex >= 0) {
            Kit.codeBug();
        }
        this.directTargetIndex = i2;
    }

    void setIsNumberVar(int i2) throws RuntimeException {
        int paramCount = i2 - this.fnode.getParamCount();
        if (paramCount < 0) {
            Kit.codeBug();
        }
        if (this.numberVarFlags == null) {
            this.numberVarFlags = new boolean[this.fnode.getParamAndVarCount() - this.fnode.getParamCount()];
        }
        this.numberVarFlags[paramCount] = true;
    }

    void setParameterNumberContext(boolean z2) {
        this.itsParameterNumberContext = z2;
    }

    public static OptFunctionNode get(ScriptNode scriptNode) {
        return (OptFunctionNode) scriptNode.getCompilerData();
    }
}
