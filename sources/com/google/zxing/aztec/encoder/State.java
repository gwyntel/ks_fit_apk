package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;
import java.util.Iterator;
import java.util.LinkedList;

/* loaded from: classes3.dex */
final class State {

    /* renamed from: a, reason: collision with root package name */
    static final State f15406a = new State(Token.f15407a, 0, 0, 0);
    private final int binaryShiftByteCount;
    private final int bitCount;
    private final int mode;
    private final Token token;

    private State(Token token, int i2, int i3, int i4) {
        this.token = token;
        this.mode = i2;
        this.binaryShiftByteCount = i3;
        this.bitCount = i4;
    }

    State a(int i2) {
        Token tokenA = this.token;
        int i3 = this.mode;
        int i4 = this.bitCount;
        if (i3 == 4 || i3 == 2) {
            int i5 = HighLevelEncoder.f15403b[i3][0];
            int i6 = 65535 & i5;
            int i7 = i5 >> 16;
            tokenA = tokenA.a(i6, i7);
            i4 += i7;
            i3 = 0;
        }
        int i8 = this.binaryShiftByteCount;
        State state = new State(tokenA, i3, i8 + 1, i4 + ((i8 == 0 || i8 == 31) ? 18 : i8 == 62 ? 9 : 8));
        return state.binaryShiftByteCount == 2078 ? state.b(i2 + 1) : state;
    }

    State b(int i2) {
        int i3 = this.binaryShiftByteCount;
        return i3 == 0 ? this : new State(this.token.b(i2 - i3, i3), this.mode, 0, this.bitCount);
    }

    int c() {
        return this.binaryShiftByteCount;
    }

    int d() {
        return this.bitCount;
    }

    int e() {
        return this.mode;
    }

    boolean f(State state) {
        int i2;
        int i3 = this.bitCount + (HighLevelEncoder.f15403b[this.mode][state.mode] >> 16);
        int i4 = state.binaryShiftByteCount;
        if (i4 > 0 && ((i2 = this.binaryShiftByteCount) == 0 || i2 > i4)) {
            i3 += 10;
        }
        return i3 <= state.bitCount;
    }

    State g(int i2, int i3) {
        int i4 = this.bitCount;
        Token tokenA = this.token;
        int i5 = this.mode;
        if (i2 != i5) {
            int i6 = HighLevelEncoder.f15403b[i5][i2];
            int i7 = 65535 & i6;
            int i8 = i6 >> 16;
            tokenA = tokenA.a(i7, i8);
            i4 += i8;
        }
        int i9 = i2 == 2 ? 4 : 5;
        return new State(tokenA.a(i3, i9), i2, 0, i4 + i9);
    }

    State h(int i2, int i3) {
        Token token = this.token;
        int i4 = this.mode;
        int i5 = i4 == 2 ? 4 : 5;
        return new State(token.a(HighLevelEncoder.f15404c[i4][i2], i5).a(i3, 5), this.mode, 0, this.bitCount + i5 + 5);
    }

    BitArray i(byte[] bArr) {
        LinkedList linkedList = new LinkedList();
        for (Token tokenC = b(bArr.length).token; tokenC != null; tokenC = tokenC.c()) {
            linkedList.addFirst(tokenC);
        }
        BitArray bitArray = new BitArray();
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            ((Token) it.next()).appendTo(bitArray, bArr);
        }
        return bitArray;
    }

    public String toString() {
        return String.format("%s bits=%d bytes=%d", HighLevelEncoder.f15402a[this.mode], Integer.valueOf(this.bitCount), Integer.valueOf(this.binaryShiftByteCount));
    }
}
