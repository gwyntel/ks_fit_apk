package com.google.common.hash;

import com.alibaba.ailabs.iot.aisbase.Constants;
import com.aliyun.alink.linksdk.securesigner.util.Utils;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import javax.annotation.CheckForNull;
import javax.crypto.spec.SecretKeySpec;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class Hashing {

    /* renamed from: a, reason: collision with root package name */
    static final int f14613a = (int) System.currentTimeMillis();

    @Immutable
    enum ChecksumType implements ImmutableSupplier<Checksum> {
        CRC_32("Hashing.crc32()") { // from class: com.google.common.hash.Hashing.ChecksumType.1
            @Override // com.google.common.base.Supplier
            public Checksum get() {
                return new CRC32();
            }
        },
        ADLER_32("Hashing.adler32()") { // from class: com.google.common.hash.Hashing.ChecksumType.2
            @Override // com.google.common.base.Supplier
            public Checksum get() {
                return new Adler32();
            }
        };

        public final HashFunction hashFunction;

        ChecksumType(String str) {
            this.hashFunction = new ChecksumHashFunction(this, 32, str);
        }
    }

    private static final class ConcatenatedHashFunction extends AbstractCompositeHashFunction {
        @Override // com.google.common.hash.AbstractCompositeHashFunction
        HashCode a(Hasher[] hasherArr) {
            byte[] bArr = new byte[bits() / 8];
            int iWriteBytesTo = 0;
            for (Hasher hasher : hasherArr) {
                HashCode hashCodeHash = hasher.hash();
                iWriteBytesTo += hashCodeHash.writeBytesTo(bArr, iWriteBytesTo, hashCodeHash.bits() / 8);
            }
            return HashCode.fromBytesNoCopy(bArr);
        }

        @Override // com.google.common.hash.HashFunction
        public int bits() {
            int iBits = 0;
            for (HashFunction hashFunction : this.f14600a) {
                iBits += hashFunction.bits();
            }
            return iBits;
        }

        public boolean equals(@CheckForNull Object obj) {
            if (obj instanceof ConcatenatedHashFunction) {
                return Arrays.equals(this.f14600a, ((ConcatenatedHashFunction) obj).f14600a);
            }
            return false;
        }

        public int hashCode() {
            return Arrays.hashCode(this.f14600a);
        }

        private ConcatenatedHashFunction(HashFunction... hashFunctionArr) {
            super(hashFunctionArr);
            for (HashFunction hashFunction : hashFunctionArr) {
                Preconditions.checkArgument(hashFunction.bits() % 8 == 0, "the number of bits (%s) in hashFunction (%s) must be divisible by 8", hashFunction.bits(), (Object) hashFunction);
            }
        }
    }

    private static final class LinearCongruentialGenerator {
        private long state;

        public LinearCongruentialGenerator(long j2) {
            this.state = j2;
        }

        public double nextDouble() {
            this.state = (this.state * 2862933555777941757L) + 1;
            return (((int) (r2 >>> 33)) + 1) / 2.147483648E9d;
        }
    }

    private static class Md5Holder {

        /* renamed from: a, reason: collision with root package name */
        static final HashFunction f14614a = new MessageDigestHashFunction(Utils.MD5, "Hashing.md5()");

        private Md5Holder() {
        }
    }

    private static class Sha1Holder {

        /* renamed from: a, reason: collision with root package name */
        static final HashFunction f14615a = new MessageDigestHashFunction("SHA-1", "Hashing.sha1()");

        private Sha1Holder() {
        }
    }

    private static class Sha256Holder {

        /* renamed from: a, reason: collision with root package name */
        static final HashFunction f14616a = new MessageDigestHashFunction("SHA-256", "Hashing.sha256()");

        private Sha256Holder() {
        }
    }

    private static class Sha384Holder {

        /* renamed from: a, reason: collision with root package name */
        static final HashFunction f14617a = new MessageDigestHashFunction("SHA-384", "Hashing.sha384()");

        private Sha384Holder() {
        }
    }

    private static class Sha512Holder {

        /* renamed from: a, reason: collision with root package name */
        static final HashFunction f14618a = new MessageDigestHashFunction("SHA-512", "Hashing.sha512()");

        private Sha512Holder() {
        }
    }

    private Hashing() {
    }

    static int a(int i2) {
        Preconditions.checkArgument(i2 > 0, "Number of bits must be positive");
        return (i2 + 31) & (-32);
    }

    public static HashFunction adler32() {
        return ChecksumType.ADLER_32.hashFunction;
    }

    public static HashCode combineOrdered(Iterable<HashCode> iterable) {
        Iterator<HashCode> it = iterable.iterator();
        Preconditions.checkArgument(it.hasNext(), "Must be at least 1 hash code to combine.");
        int iBits = it.next().bits() / 8;
        byte[] bArr = new byte[iBits];
        Iterator<HashCode> it2 = iterable.iterator();
        while (it2.hasNext()) {
            byte[] bArrAsBytes = it2.next().asBytes();
            Preconditions.checkArgument(bArrAsBytes.length == iBits, "All hashcodes must have the same bit length.");
            for (int i2 = 0; i2 < bArrAsBytes.length; i2++) {
                bArr[i2] = (byte) ((bArr[i2] * Constants.CMD_TYPE.CMD_OTA_REQUEST_VERIFY) ^ bArrAsBytes[i2]);
            }
        }
        return HashCode.fromBytesNoCopy(bArr);
    }

    public static HashCode combineUnordered(Iterable<HashCode> iterable) {
        Iterator<HashCode> it = iterable.iterator();
        Preconditions.checkArgument(it.hasNext(), "Must be at least 1 hash code to combine.");
        int iBits = it.next().bits() / 8;
        byte[] bArr = new byte[iBits];
        Iterator<HashCode> it2 = iterable.iterator();
        while (it2.hasNext()) {
            byte[] bArrAsBytes = it2.next().asBytes();
            Preconditions.checkArgument(bArrAsBytes.length == iBits, "All hashcodes must have the same bit length.");
            for (int i2 = 0; i2 < bArrAsBytes.length; i2++) {
                bArr[i2] = (byte) (bArr[i2] + bArrAsBytes[i2]);
            }
        }
        return HashCode.fromBytesNoCopy(bArr);
    }

    public static HashFunction concatenating(HashFunction hashFunction, HashFunction hashFunction2, HashFunction... hashFunctionArr) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(hashFunction);
        arrayList.add(hashFunction2);
        Collections.addAll(arrayList, hashFunctionArr);
        return new ConcatenatedHashFunction((HashFunction[]) arrayList.toArray(new HashFunction[0]));
    }

    public static int consistentHash(HashCode hashCode, int i2) {
        return consistentHash(hashCode.padToLong(), i2);
    }

    public static HashFunction crc32() {
        return ChecksumType.CRC_32.hashFunction;
    }

    public static HashFunction crc32c() {
        return Crc32cHashFunction.f14607a;
    }

    public static HashFunction farmHashFingerprint64() {
        return FarmHashFingerprint64.f14610a;
    }

    public static HashFunction fingerprint2011() {
        return Fingerprint2011.f14611a;
    }

    public static HashFunction goodFastHash(int i2) {
        int iA = a(i2);
        if (iA == 32) {
            return Murmur3_32HashFunction.GOOD_FAST_HASH_32;
        }
        if (iA <= 128) {
            return Murmur3_128HashFunction.GOOD_FAST_HASH_128;
        }
        int i3 = (iA + 127) / 128;
        HashFunction[] hashFunctionArr = new HashFunction[i3];
        hashFunctionArr[0] = Murmur3_128HashFunction.GOOD_FAST_HASH_128;
        int i4 = f14613a;
        for (int i5 = 1; i5 < i3; i5++) {
            i4 += 1500450271;
            hashFunctionArr[i5] = murmur3_128(i4);
        }
        return new ConcatenatedHashFunction(hashFunctionArr);
    }

    public static HashFunction hmacMd5(Key key) {
        return new MacHashFunction("HmacMD5", key, hmacToString("hmacMd5", key));
    }

    public static HashFunction hmacSha1(Key key) {
        return new MacHashFunction("HmacSHA1", key, hmacToString("hmacSha1", key));
    }

    public static HashFunction hmacSha256(Key key) {
        return new MacHashFunction("HmacSHA256", key, hmacToString("hmacSha256", key));
    }

    public static HashFunction hmacSha512(Key key) {
        return new MacHashFunction("HmacSHA512", key, hmacToString("hmacSha512", key));
    }

    private static String hmacToString(String str, Key key) {
        return "Hashing." + str + "(Key[algorithm=" + key.getAlgorithm() + ", format=" + key.getFormat() + "])";
    }

    @Deprecated
    public static HashFunction md5() {
        return Md5Holder.f14614a;
    }

    public static HashFunction murmur3_128(int i2) {
        return new Murmur3_128HashFunction(i2);
    }

    @Deprecated
    public static HashFunction murmur3_32(int i2) {
        return new Murmur3_32HashFunction(i2, false);
    }

    public static HashFunction murmur3_32_fixed(int i2) {
        return new Murmur3_32HashFunction(i2, true);
    }

    @Deprecated
    public static HashFunction sha1() {
        return Sha1Holder.f14615a;
    }

    public static HashFunction sha256() {
        return Sha256Holder.f14616a;
    }

    public static HashFunction sha384() {
        return Sha384Holder.f14617a;
    }

    public static HashFunction sha512() {
        return Sha512Holder.f14618a;
    }

    public static HashFunction sipHash24() {
        return SipHashFunction.SIP_HASH_24;
    }

    public static int consistentHash(long j2, int i2) {
        int i3 = 0;
        Preconditions.checkArgument(i2 > 0, "buckets must be positive: %s", i2);
        LinearCongruentialGenerator linearCongruentialGenerator = new LinearCongruentialGenerator(j2);
        while (true) {
            int iNextDouble = (int) ((i3 + 1) / linearCongruentialGenerator.nextDouble());
            if (iNextDouble < 0 || iNextDouble >= i2) {
                break;
            }
            i3 = iNextDouble;
        }
        return i3;
    }

    public static HashFunction hmacMd5(byte[] bArr) {
        return hmacMd5(new SecretKeySpec((byte[]) Preconditions.checkNotNull(bArr), "HmacMD5"));
    }

    public static HashFunction hmacSha1(byte[] bArr) {
        return hmacSha1(new SecretKeySpec((byte[]) Preconditions.checkNotNull(bArr), "HmacSHA1"));
    }

    public static HashFunction hmacSha256(byte[] bArr) {
        return hmacSha256(new SecretKeySpec((byte[]) Preconditions.checkNotNull(bArr), "HmacSHA256"));
    }

    public static HashFunction hmacSha512(byte[] bArr) {
        return hmacSha512(new SecretKeySpec((byte[]) Preconditions.checkNotNull(bArr), "HmacSHA512"));
    }

    public static HashFunction murmur3_128() {
        return Murmur3_128HashFunction.MURMUR3_128;
    }

    @Deprecated
    public static HashFunction murmur3_32() {
        return Murmur3_32HashFunction.MURMUR3_32;
    }

    public static HashFunction murmur3_32_fixed() {
        return Murmur3_32HashFunction.MURMUR3_32_FIXED;
    }

    public static HashFunction sipHash24(long j2, long j3) {
        return new SipHashFunction(2, 4, j2, j3);
    }

    public static HashFunction concatenating(Iterable<HashFunction> iterable) {
        Preconditions.checkNotNull(iterable);
        ArrayList arrayList = new ArrayList();
        Iterator<HashFunction> it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        Preconditions.checkArgument(!arrayList.isEmpty(), "number of hash functions (%s) must be > 0", arrayList.size());
        return new ConcatenatedHashFunction((HashFunction[]) arrayList.toArray(new HashFunction[0]));
    }
}
