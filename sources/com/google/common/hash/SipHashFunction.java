package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.nio.ByteBuffer;
import javax.annotation.CheckForNull;

@Immutable
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class SipHashFunction extends AbstractHashFunction implements Serializable {
    static final HashFunction SIP_HASH_24 = new SipHashFunction(2, 4, 506097522914230528L, 1084818905618843912L);
    private static final long serialVersionUID = 0;

    /* renamed from: c, reason: collision with root package name */
    private final int f14619c;

    /* renamed from: d, reason: collision with root package name */
    private final int f14620d;

    /* renamed from: k0, reason: collision with root package name */
    private final long f14621k0;
    private final long k1;

    private static final class SipHasher extends AbstractStreamingHasher {
        private static final int CHUNK_SIZE = 8;

        /* renamed from: b, reason: collision with root package name */
        private long f14622b;

        /* renamed from: c, reason: collision with root package name */
        private final int f14623c;

        /* renamed from: d, reason: collision with root package name */
        private final int f14624d;
        private long finalM;

        /* renamed from: v0, reason: collision with root package name */
        private long f14625v0;
        private long v1;
        private long v2;
        private long v3;

        SipHasher(int i2, int i3, long j2, long j3) {
            super(8);
            this.f14622b = 0L;
            this.finalM = 0L;
            this.f14623c = i2;
            this.f14624d = i3;
            this.f14625v0 = 8317987319222330741L ^ j2;
            this.v1 = 7237128888997146477L ^ j3;
            this.v2 = 7816392313619706465L ^ j2;
            this.v3 = 8387220255154660723L ^ j3;
        }

        private void processM(long j2) {
            this.v3 ^= j2;
            sipRound(this.f14623c);
            this.f14625v0 = j2 ^ this.f14625v0;
        }

        private void sipRound(int i2) {
            for (int i3 = 0; i3 < i2; i3++) {
                long j2 = this.f14625v0;
                long j3 = this.v1;
                this.f14625v0 = j2 + j3;
                this.v2 += this.v3;
                this.v1 = Long.rotateLeft(j3, 13);
                long jRotateLeft = Long.rotateLeft(this.v3, 16);
                long j4 = this.v1;
                long j5 = this.f14625v0;
                this.v1 = j4 ^ j5;
                this.v3 = jRotateLeft ^ this.v2;
                long jRotateLeft2 = Long.rotateLeft(j5, 32);
                long j6 = this.v2;
                long j7 = this.v1;
                this.v2 = j6 + j7;
                this.f14625v0 = jRotateLeft2 + this.v3;
                this.v1 = Long.rotateLeft(j7, 17);
                long jRotateLeft3 = Long.rotateLeft(this.v3, 21);
                long j8 = this.v1;
                long j9 = this.v2;
                this.v1 = j8 ^ j9;
                this.v3 = jRotateLeft3 ^ this.f14625v0;
                this.v2 = Long.rotateLeft(j9, 32);
            }
        }

        @Override // com.google.common.hash.AbstractStreamingHasher
        protected HashCode a() {
            long j2 = this.finalM ^ (this.f14622b << 56);
            this.finalM = j2;
            processM(j2);
            this.v2 ^= 255;
            sipRound(this.f14624d);
            return HashCode.fromLong(((this.f14625v0 ^ this.v1) ^ this.v2) ^ this.v3);
        }

        @Override // com.google.common.hash.AbstractStreamingHasher
        protected void b(ByteBuffer byteBuffer) {
            this.f14622b += 8;
            processM(byteBuffer.getLong());
        }

        @Override // com.google.common.hash.AbstractStreamingHasher
        protected void c(ByteBuffer byteBuffer) {
            this.f14622b += byteBuffer.remaining();
            int i2 = 0;
            while (byteBuffer.hasRemaining()) {
                this.finalM ^= (byteBuffer.get() & 255) << i2;
                i2 += 8;
            }
        }
    }

    SipHashFunction(int i2, int i3, long j2, long j3) {
        Preconditions.checkArgument(i2 > 0, "The number of SipRound iterations (c=%s) during Compression must be positive.", i2);
        Preconditions.checkArgument(i3 > 0, "The number of SipRound iterations (d=%s) during Finalization must be positive.", i3);
        this.f14619c = i2;
        this.f14620d = i3;
        this.f14621k0 = j2;
        this.k1 = j3;
    }

    @Override // com.google.common.hash.HashFunction
    public int bits() {
        return 64;
    }

    public boolean equals(@CheckForNull Object obj) {
        if (!(obj instanceof SipHashFunction)) {
            return false;
        }
        SipHashFunction sipHashFunction = (SipHashFunction) obj;
        return this.f14619c == sipHashFunction.f14619c && this.f14620d == sipHashFunction.f14620d && this.f14621k0 == sipHashFunction.f14621k0 && this.k1 == sipHashFunction.k1;
    }

    public int hashCode() {
        return (int) ((((SipHashFunction.class.hashCode() ^ this.f14619c) ^ this.f14620d) ^ this.f14621k0) ^ this.k1);
    }

    @Override // com.google.common.hash.HashFunction
    public Hasher newHasher() {
        return new SipHasher(this.f14619c, this.f14620d, this.f14621k0, this.k1);
    }

    public String toString() {
        return "Hashing.sipHash" + this.f14619c + "" + this.f14620d + "(" + this.f14621k0 + ", " + this.k1 + ")";
    }
}
