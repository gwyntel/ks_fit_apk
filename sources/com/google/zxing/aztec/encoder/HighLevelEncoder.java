package com.google.zxing.aztec.encoder;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/* loaded from: classes3.dex */
public final class HighLevelEncoder {
    private static final int[][] CHAR_MAP;

    /* renamed from: a, reason: collision with root package name */
    static final String[] f15402a = {"UPPER", "LOWER", "DIGIT", "MIXED", "PUNCT"};

    /* renamed from: b, reason: collision with root package name */
    static final int[][] f15403b = {new int[]{0, 327708, 327710, 327709, 656318}, new int[]{590318, 0, 327710, 327709, 656318}, new int[]{262158, 590300, 0, 590301, 932798}, new int[]{327709, 327708, 656318, 0, 327710}, new int[]{327711, 656380, 656382, 656381, 0}};

    /* renamed from: c, reason: collision with root package name */
    static final int[][] f15404c;
    private final byte[] text;

    static {
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 5, 256);
        CHAR_MAP = iArr;
        iArr[0][32] = 1;
        for (int i2 = 65; i2 <= 90; i2++) {
            CHAR_MAP[0][i2] = i2 - 63;
        }
        CHAR_MAP[1][32] = 1;
        for (int i3 = 97; i3 <= 122; i3++) {
            CHAR_MAP[1][i3] = i3 - 95;
        }
        CHAR_MAP[2][32] = 1;
        for (int i4 = 48; i4 <= 57; i4++) {
            CHAR_MAP[2][i4] = i4 - 46;
        }
        int[] iArr2 = CHAR_MAP[2];
        iArr2[44] = 12;
        iArr2[46] = 13;
        int[] iArr3 = {0, 32, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 27, 28, 29, 30, 31, 64, 92, 94, 95, 96, 124, 126, 127};
        int i5 = 0;
        for (int i6 = 28; i5 < i6; i6 = 28) {
            CHAR_MAP[3][iArr3[i5]] = i5;
            i5++;
        }
        int[] iArr4 = {0, 13, 0, 0, 0, 0, 33, 39, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 58, 59, 60, 61, 62, 63, 91, 93, 123, 125};
        for (int i7 = 0; i7 < 31; i7++) {
            int i8 = iArr4[i7];
            if (i8 > 0) {
                CHAR_MAP[4][i8] = i7;
            }
        }
        int[][] iArr5 = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 6, 6);
        f15404c = iArr5;
        for (int[] iArr6 : iArr5) {
            Arrays.fill(iArr6, -1);
        }
        int[][] iArr7 = f15404c;
        iArr7[0][4] = 0;
        int[] iArr8 = iArr7[1];
        iArr8[4] = 0;
        iArr8[0] = 28;
        iArr7[3][4] = 0;
        int[] iArr9 = iArr7[2];
        iArr9[4] = 0;
        iArr9[0] = 15;
    }

    public HighLevelEncoder(byte[] bArr) {
        this.text = bArr;
    }

    private static Collection<State> simplifyStates(Iterable<State> iterable) {
        LinkedList linkedList = new LinkedList();
        for (State state : iterable) {
            Iterator it = linkedList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    linkedList.add(state);
                    break;
                }
                State state2 = (State) it.next();
                if (state2.f(state)) {
                    break;
                }
                if (state.f(state2)) {
                    it.remove();
                }
            }
        }
        return linkedList;
    }

    private void updateStateForChar(State state, int i2, Collection<State> collection) {
        char c2 = (char) (this.text[i2] & 255);
        boolean z2 = CHAR_MAP[state.e()][c2] > 0;
        State stateB = null;
        for (int i3 = 0; i3 <= 4; i3++) {
            int i4 = CHAR_MAP[i3][c2];
            if (i4 > 0) {
                if (stateB == null) {
                    stateB = state.b(i2);
                }
                if (!z2 || i3 == state.e() || i3 == 2) {
                    collection.add(stateB.g(i3, i4));
                }
                if (!z2 && f15404c[state.e()][i3] >= 0) {
                    collection.add(stateB.h(i3, i4));
                }
            }
        }
        if (state.c() > 0 || CHAR_MAP[state.e()][c2] == 0) {
            collection.add(state.a(i2));
        }
    }

    private static void updateStateForPair(State state, int i2, int i3, Collection<State> collection) {
        State stateB = state.b(i2);
        collection.add(stateB.g(4, i3));
        if (state.e() != 4) {
            collection.add(stateB.h(4, i3));
        }
        if (i3 == 3 || i3 == 4) {
            collection.add(stateB.g(2, 16 - i3).g(2, 1));
        }
        if (state.c() > 0) {
            collection.add(state.a(i2).a(i2 + 1));
        }
    }

    private Collection<State> updateStateListForChar(Iterable<State> iterable, int i2) {
        LinkedList linkedList = new LinkedList();
        Iterator<State> it = iterable.iterator();
        while (it.hasNext()) {
            updateStateForChar(it.next(), i2, linkedList);
        }
        return simplifyStates(linkedList);
    }

    private static Collection<State> updateStateListForPair(Iterable<State> iterable, int i2, int i3) {
        LinkedList linkedList = new LinkedList();
        Iterator<State> it = iterable.iterator();
        while (it.hasNext()) {
            updateStateForPair(it.next(), i2, i3, linkedList);
        }
        return simplifyStates(linkedList);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.zxing.common.BitArray encode() {
        /*
            r8 = this;
            com.google.zxing.aztec.encoder.State r0 = com.google.zxing.aztec.encoder.State.f15406a
            java.util.List r0 = java.util.Collections.singletonList(r0)
            r1 = 0
            r2 = r1
        L8:
            byte[] r3 = r8.text
            int r4 = r3.length
            if (r2 >= r4) goto L4c
            int r4 = r2 + 1
            int r5 = r3.length
            if (r4 >= r5) goto L15
            r5 = r3[r4]
            goto L16
        L15:
            r5 = r1
        L16:
            r3 = r3[r2]
            r6 = 13
            if (r3 == r6) goto L38
            r6 = 44
            r7 = 32
            if (r3 == r6) goto L34
            r6 = 46
            if (r3 == r6) goto L30
            r6 = 58
            if (r3 == r6) goto L2c
        L2a:
            r3 = r1
            goto L3d
        L2c:
            if (r5 != r7) goto L2a
            r3 = 5
            goto L3d
        L30:
            if (r5 != r7) goto L2a
            r3 = 3
            goto L3d
        L34:
            if (r5 != r7) goto L2a
            r3 = 4
            goto L3d
        L38:
            r3 = 10
            if (r5 != r3) goto L2a
            r3 = 2
        L3d:
            if (r3 <= 0) goto L45
            java.util.Collection r0 = updateStateListForPair(r0, r2, r3)
            r2 = r4
            goto L49
        L45:
            java.util.Collection r0 = r8.updateStateListForChar(r0, r2)
        L49:
            int r2 = r2 + 1
            goto L8
        L4c:
            com.google.zxing.aztec.encoder.HighLevelEncoder$1 r1 = new com.google.zxing.aztec.encoder.HighLevelEncoder$1
            r1.<init>()
            java.lang.Object r0 = java.util.Collections.min(r0, r1)
            com.google.zxing.aztec.encoder.State r0 = (com.google.zxing.aztec.encoder.State) r0
            byte[] r1 = r8.text
            com.google.zxing.common.BitArray r0 = r0.i(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.aztec.encoder.HighLevelEncoder.encode():com.google.zxing.common.BitArray");
    }
}
