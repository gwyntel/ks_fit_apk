package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;

/* loaded from: classes3.dex */
abstract class Token {

    /* renamed from: a, reason: collision with root package name */
    static final Token f15407a = new SimpleToken(null, 0, 0);
    private final Token previous;

    Token(Token token) {
        this.previous = token;
    }

    final Token a(int i2, int i3) {
        return new SimpleToken(this, i2, i3);
    }

    abstract void appendTo(BitArray bitArray, byte[] bArr);

    final Token b(int i2, int i3) {
        return new BinaryShiftToken(this, i2, i3);
    }

    final Token c() {
        return this.previous;
    }
}
