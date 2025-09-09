package com.xiaomi.push;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.GZIPInputStream;

/* loaded from: classes4.dex */
public class gu {

    /* renamed from: a, reason: collision with root package name */
    public static final byte[] f23850a = {80, 85, 83, 72};

    /* renamed from: a, reason: collision with other field name */
    private byte f502a;

    /* renamed from: a, reason: collision with other field name */
    private int f503a;

    /* renamed from: a, reason: collision with other field name */
    private short f504a;

    /* renamed from: b, reason: collision with root package name */
    private byte[] f23851b;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public static final c f23852a = new c();

        /* renamed from: a, reason: collision with other field name */
        public static final d f505a = new d();

        public static byte[] a(byte[] bArr) {
            return a(bArr, f505a);
        }

        public static byte[] a(byte[] bArr, b bVar) {
            if (!gu.m458a(bArr)) {
                return bArr;
            }
            gu guVarA = gu.a(bArr);
            return (guVarA.f502a == 0 || guVarA.f502a != bVar.a()) ? guVarA.f23851b : bVar.a(guVarA.f23851b, guVarA.f503a);
        }
    }

    public interface b {
        byte a();

        byte[] a(byte[] bArr, int i2);
    }

    public static final class c {
    }

    public static final class d implements b {
        @Override // com.xiaomi.push.gu.b
        public byte a() {
            return (byte) 2;
        }

        @Override // com.xiaomi.push.gu.b
        public byte[] a(byte[] bArr, int i2) throws Throwable {
            GZIPInputStream gZIPInputStream;
            GZIPInputStream gZIPInputStream2 = null;
            try {
                gZIPInputStream = new GZIPInputStream(new ByteArrayInputStream(bArr), i2);
            } catch (IOException unused) {
            } catch (Throwable th) {
                th = th;
            }
            try {
                byte[] bArr2 = new byte[i2];
                gZIPInputStream.read(bArr2);
                try {
                    gZIPInputStream.close();
                } catch (IOException unused2) {
                }
                return bArr2;
            } catch (IOException unused3) {
                gZIPInputStream2 = gZIPInputStream;
                if (gZIPInputStream2 != null) {
                    try {
                        gZIPInputStream2.close();
                    } catch (IOException unused4) {
                    }
                }
                return bArr;
            } catch (Throwable th2) {
                th = th2;
                gZIPInputStream2 = gZIPInputStream;
                if (gZIPInputStream2 != null) {
                    try {
                        gZIPInputStream2.close();
                    } catch (IOException unused5) {
                    }
                }
                throw th;
            }
        }
    }

    protected gu(byte b2, int i2, byte[] bArr) {
        this((short) 1, b2, i2, bArr);
    }

    protected gu(short s2, byte b2, int i2, byte[] bArr) {
        this.f504a = s2;
        this.f502a = b2;
        this.f503a = i2;
        this.f23851b = bArr;
    }

    public static gu a(byte b2, int i2, byte[] bArr) {
        return new gu(b2, i2, bArr);
    }

    public static gu a(short s2, byte b2, int i2, byte[] bArr) {
        return new gu(s2, b2, i2, bArr);
    }

    public static gu a(byte[] bArr) {
        if (m458a(bArr)) {
            ByteBuffer byteBufferOrder = ByteBuffer.wrap(bArr).order(ByteOrder.BIG_ENDIAN);
            byteBufferOrder.getInt();
            short s2 = byteBufferOrder.getShort();
            byte b2 = byteBufferOrder.get();
            int i2 = byteBufferOrder.getInt();
            byte[] bArr2 = new byte[byteBufferOrder.getInt()];
            byteBufferOrder.get(bArr2);
            return a(s2, b2, i2, bArr2);
        }
        return a((byte) 0, bArr.length, bArr);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m458a(byte[] bArr) {
        byte[] bArr2 = f23850a;
        return a(bArr2, bArr, bArr2.length);
    }

    public static boolean a(byte[] bArr, byte[] bArr2, int i2) {
        if (bArr.length < i2 || bArr2.length < i2) {
            return false;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            if (bArr[i3] != bArr2[i3]) {
                return false;
            }
        }
        return true;
    }
}
