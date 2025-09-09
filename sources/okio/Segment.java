package okio;

import javax.annotation.Nullable;

/* loaded from: classes5.dex */
final class Segment {

    /* renamed from: a, reason: collision with root package name */
    final byte[] f26493a;

    /* renamed from: b, reason: collision with root package name */
    int f26494b;

    /* renamed from: c, reason: collision with root package name */
    int f26495c;

    /* renamed from: d, reason: collision with root package name */
    boolean f26496d;

    /* renamed from: e, reason: collision with root package name */
    boolean f26497e;

    /* renamed from: f, reason: collision with root package name */
    Segment f26498f;

    /* renamed from: g, reason: collision with root package name */
    Segment f26499g;

    Segment() {
        this.f26493a = new byte[8192];
        this.f26497e = true;
        this.f26496d = false;
    }

    final Segment a() {
        this.f26496d = true;
        return new Segment(this.f26493a, this.f26494b, this.f26495c, true, false);
    }

    final Segment b() {
        return new Segment((byte[]) this.f26493a.clone(), this.f26494b, this.f26495c, false, true);
    }

    public final void compact() {
        Segment segment = this.f26499g;
        if (segment == this) {
            throw new IllegalStateException();
        }
        if (segment.f26497e) {
            int i2 = this.f26495c - this.f26494b;
            if (i2 > (8192 - segment.f26495c) + (segment.f26496d ? 0 : segment.f26494b)) {
                return;
            }
            writeTo(segment, i2);
            pop();
            SegmentPool.a(this);
        }
    }

    @Nullable
    public final Segment pop() {
        Segment segment = this.f26498f;
        Segment segment2 = segment != this ? segment : null;
        Segment segment3 = this.f26499g;
        segment3.f26498f = segment;
        this.f26498f.f26499g = segment3;
        this.f26498f = null;
        this.f26499g = null;
        return segment2;
    }

    public final Segment push(Segment segment) {
        segment.f26499g = this;
        segment.f26498f = this.f26498f;
        this.f26498f.f26499g = segment;
        this.f26498f = segment;
        return segment;
    }

    public final Segment split(int i2) {
        Segment segmentB;
        if (i2 <= 0 || i2 > this.f26495c - this.f26494b) {
            throw new IllegalArgumentException();
        }
        if (i2 >= 1024) {
            segmentB = a();
        } else {
            segmentB = SegmentPool.b();
            System.arraycopy(this.f26493a, this.f26494b, segmentB.f26493a, 0, i2);
        }
        segmentB.f26495c = segmentB.f26494b + i2;
        this.f26494b += i2;
        this.f26499g.push(segmentB);
        return segmentB;
    }

    public final void writeTo(Segment segment, int i2) {
        if (!segment.f26497e) {
            throw new IllegalArgumentException();
        }
        int i3 = segment.f26495c;
        if (i3 + i2 > 8192) {
            if (segment.f26496d) {
                throw new IllegalArgumentException();
            }
            int i4 = segment.f26494b;
            if ((i3 + i2) - i4 > 8192) {
                throw new IllegalArgumentException();
            }
            byte[] bArr = segment.f26493a;
            System.arraycopy(bArr, i4, bArr, 0, i3 - i4);
            segment.f26495c -= segment.f26494b;
            segment.f26494b = 0;
        }
        System.arraycopy(this.f26493a, this.f26494b, segment.f26493a, segment.f26495c, i2);
        segment.f26495c += i2;
        this.f26494b += i2;
    }

    Segment(byte[] bArr, int i2, int i3, boolean z2, boolean z3) {
        this.f26493a = bArr;
        this.f26494b = i2;
        this.f26495c = i3;
        this.f26496d = z2;
        this.f26497e = z3;
    }
}
