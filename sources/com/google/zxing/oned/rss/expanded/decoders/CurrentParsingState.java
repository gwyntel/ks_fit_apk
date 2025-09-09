package com.google.zxing.oned.rss.expanded.decoders;

/* loaded from: classes3.dex */
final class CurrentParsingState {
    private int position = 0;
    private State encoding = State.NUMERIC;

    private enum State {
        NUMERIC,
        ALPHA,
        ISO_IEC_646
    }

    CurrentParsingState() {
    }

    int a() {
        return this.position;
    }

    void b(int i2) {
        this.position += i2;
    }

    boolean c() {
        return this.encoding == State.ALPHA;
    }

    boolean d() {
        return this.encoding == State.ISO_IEC_646;
    }

    void e() {
        this.encoding = State.ALPHA;
    }

    void f() {
        this.encoding = State.ISO_IEC_646;
    }

    void g() {
        this.encoding = State.NUMERIC;
    }

    void h(int i2) {
        this.position = i2;
    }
}
