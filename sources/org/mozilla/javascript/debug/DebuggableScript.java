package org.mozilla.javascript.debug;

/* loaded from: classes5.dex */
public interface DebuggableScript {
    DebuggableScript getFunction(int i2);

    int getFunctionCount();

    String getFunctionName();

    int[] getLineNumbers();

    int getParamAndVarCount();

    int getParamCount();

    String getParamOrVarName(int i2);

    DebuggableScript getParent();

    String getSourceName();

    boolean isFunction();

    boolean isGeneratedScript();

    boolean isTopLevel();
}
