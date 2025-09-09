package org.mozilla.javascript.regexp;

import org.mozilla.javascript.Context;

/* loaded from: classes5.dex */
class CompilerState {
    char[] cpbegin;
    int cpend;
    Context cx;
    int flags;
    int parenNesting;
    RENode result;
    int cp = 0;
    int backReferenceLimit = Integer.MAX_VALUE;
    int maxBackReference = 0;
    int parenCount = 0;
    int classCount = 0;
    int progLength = 0;

    CompilerState(Context context, char[] cArr, int i2, int i3) {
        this.cx = context;
        this.cpbegin = cArr;
        this.cpend = i2;
        this.flags = i3;
    }
}
