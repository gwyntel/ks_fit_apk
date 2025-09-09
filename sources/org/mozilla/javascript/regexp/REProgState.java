package org.mozilla.javascript.regexp;

/* loaded from: classes5.dex */
class REProgState {
    final REBackTrackData backTrack;
    final int continuationOp;
    final int continuationPc;
    final int index;
    final int max;
    final int min;
    final REProgState previous;

    REProgState(REProgState rEProgState, int i2, int i3, int i4, REBackTrackData rEBackTrackData, int i5, int i6) {
        this.previous = rEProgState;
        this.min = i2;
        this.max = i3;
        this.index = i4;
        this.continuationOp = i5;
        this.continuationPc = i6;
        this.backTrack = rEBackTrackData;
    }
}
