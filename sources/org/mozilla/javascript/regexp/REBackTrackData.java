package org.mozilla.javascript.regexp;

/* loaded from: classes5.dex */
class REBackTrackData {
    final int continuationOp;
    final int continuationPc;
    final int cp;
    final int op;
    final long[] parens;
    final int pc;
    final REBackTrackData previous;
    final REProgState stateStackTop;

    REBackTrackData(REGlobalData rEGlobalData, int i2, int i3, int i4, int i5, int i6) {
        this.previous = rEGlobalData.backTrackStackTop;
        this.op = i2;
        this.pc = i3;
        this.cp = i4;
        this.continuationOp = i5;
        this.continuationPc = i6;
        this.parens = rEGlobalData.parens;
        this.stateStackTop = rEGlobalData.stateStackTop;
    }
}
