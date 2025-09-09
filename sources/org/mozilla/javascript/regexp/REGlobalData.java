package org.mozilla.javascript.regexp;

/* loaded from: classes5.dex */
class REGlobalData {
    REBackTrackData backTrackStackTop;
    int cp;
    boolean multiline;
    long[] parens;
    RECompiled regexp;
    int skipped;
    REProgState stateStackTop;

    REGlobalData() {
    }

    int parensIndex(int i2) {
        return (int) this.parens[i2];
    }

    int parensLength(int i2) {
        return (int) (this.parens[i2] >>> 32);
    }

    void setParens(int i2, int i3, int i4) {
        REBackTrackData rEBackTrackData = this.backTrackStackTop;
        if (rEBackTrackData != null) {
            long[] jArr = rEBackTrackData.parens;
            long[] jArr2 = this.parens;
            if (jArr == jArr2) {
                this.parens = (long[]) jArr2.clone();
            }
        }
        this.parens[i2] = (i4 << 32) | (i3 & 4294967295L);
    }
}
