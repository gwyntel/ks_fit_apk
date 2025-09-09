package org.mozilla.javascript;

import java.io.Serializable;
import org.mozilla.javascript.debug.DebuggableScript;

/* loaded from: classes5.dex */
final class InterpreterData implements Serializable, DebuggableScript {
    static final int INITIAL_MAX_ICODE_LENGTH = 1024;
    static final int INITIAL_NUMBERTABLE_SIZE = 64;
    static final int INITIAL_STRINGTABLE_SIZE = 64;
    static final long serialVersionUID = 5067677351589230234L;
    int argCount;
    boolean[] argIsConst;
    String[] argNames;
    String encodedSource;
    int encodedSourceEnd;
    int encodedSourceStart;
    boolean evalScriptFlag;
    int firstLinePC = -1;
    boolean isStrict;
    double[] itsDoubleTable;
    int[] itsExceptionTable;
    int itsFunctionType;
    byte[] itsICode;
    int itsMaxCalleeArgs;
    int itsMaxFrameArray;
    int itsMaxLocals;
    int itsMaxStack;
    int itsMaxVars;
    String itsName;
    boolean itsNeedsActivation;
    InterpreterData[] itsNestedFunctions;
    Object[] itsRegExpLiterals;
    String itsSourceFile;
    String[] itsStringTable;
    int languageVersion;
    Object[] literalIds;
    UintMap longJumps;
    InterpreterData parentData;
    boolean topLevel;

    InterpreterData(int i2, String str, String str2, boolean z2) {
        this.languageVersion = i2;
        this.itsSourceFile = str;
        this.encodedSource = str2;
        this.isStrict = z2;
        init();
    }

    private void init() {
        this.itsICode = new byte[1024];
        this.itsStringTable = new String[64];
    }

    @Override // org.mozilla.javascript.debug.DebuggableScript
    public DebuggableScript getFunction(int i2) {
        return this.itsNestedFunctions[i2];
    }

    @Override // org.mozilla.javascript.debug.DebuggableScript
    public int getFunctionCount() {
        InterpreterData[] interpreterDataArr = this.itsNestedFunctions;
        if (interpreterDataArr == null) {
            return 0;
        }
        return interpreterDataArr.length;
    }

    @Override // org.mozilla.javascript.debug.DebuggableScript
    public String getFunctionName() {
        return this.itsName;
    }

    @Override // org.mozilla.javascript.debug.DebuggableScript
    public int[] getLineNumbers() {
        return Interpreter.getLineNumbers(this);
    }

    @Override // org.mozilla.javascript.debug.DebuggableScript
    public int getParamAndVarCount() {
        return this.argNames.length;
    }

    @Override // org.mozilla.javascript.debug.DebuggableScript
    public int getParamCount() {
        return this.argCount;
    }

    public boolean getParamOrVarConst(int i2) {
        return this.argIsConst[i2];
    }

    @Override // org.mozilla.javascript.debug.DebuggableScript
    public String getParamOrVarName(int i2) {
        return this.argNames[i2];
    }

    @Override // org.mozilla.javascript.debug.DebuggableScript
    public DebuggableScript getParent() {
        return this.parentData;
    }

    @Override // org.mozilla.javascript.debug.DebuggableScript
    public String getSourceName() {
        return this.itsSourceFile;
    }

    @Override // org.mozilla.javascript.debug.DebuggableScript
    public boolean isFunction() {
        return this.itsFunctionType != 0;
    }

    @Override // org.mozilla.javascript.debug.DebuggableScript
    public boolean isGeneratedScript() {
        return ScriptRuntime.isGeneratedScript(this.itsSourceFile);
    }

    @Override // org.mozilla.javascript.debug.DebuggableScript
    public boolean isTopLevel() {
        return this.topLevel;
    }

    InterpreterData(InterpreterData interpreterData) {
        this.parentData = interpreterData;
        this.languageVersion = interpreterData.languageVersion;
        this.itsSourceFile = interpreterData.itsSourceFile;
        this.encodedSource = interpreterData.encodedSource;
        this.isStrict = interpreterData.isStrict;
        init();
    }
}
