package com.alibaba.fastjson.asm;

/* loaded from: classes2.dex */
public class MethodCollector {
    protected boolean debugInfoPresent;
    private final int ignoreCount;
    private final int paramCount;
    private final StringBuilder result = new StringBuilder();
    private int currentParameter = 0;

    protected MethodCollector(int i2, int i3) {
        this.ignoreCount = i2;
        this.paramCount = i3;
        this.debugInfoPresent = i3 == 0;
    }

    protected String getResult() {
        return this.result.length() != 0 ? this.result.substring(1) : "";
    }

    protected void visitLocalVariable(String str, int i2) {
        int i3 = this.ignoreCount;
        if (i2 < i3 || i2 >= i3 + this.paramCount) {
            return;
        }
        if (!str.equals("arg" + this.currentParameter)) {
            this.debugInfoPresent = true;
        }
        this.result.append(',');
        this.result.append(str);
        this.currentParameter++;
    }
}
