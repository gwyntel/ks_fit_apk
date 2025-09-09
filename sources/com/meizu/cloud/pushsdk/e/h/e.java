package com.meizu.cloud.pushsdk.e.h;

import com.aliyun.alink.linksdk.securesigner.util.Utils;
import com.umeng.analytics.pro.bc;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class e implements Serializable, Comparable<e> {

    /* renamed from: a, reason: collision with root package name */
    static final char[] f19489a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /* renamed from: b, reason: collision with root package name */
    public static final e f19490b = a(new byte[0]);
    private static final long serialVersionUID = 1;

    /* renamed from: c, reason: collision with root package name */
    final byte[] f19491c;

    /* renamed from: d, reason: collision with root package name */
    transient int f19492d;

    /* renamed from: e, reason: collision with root package name */
    transient String f19493e;

    e(byte[] bArr) {
        this.f19491c = bArr;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IllegalAccessException, NoSuchFieldException, IOException, SecurityException, IllegalArgumentException {
        e eVarA = a(objectInputStream, objectInputStream.readInt());
        try {
            Field declaredField = e.class.getDeclaredField(bc.aL);
            declaredField.setAccessible(true);
            declaredField.set(this, eVarA.f19491c);
        } catch (IllegalAccessException unused) {
            throw new AssertionError();
        } catch (NoSuchFieldException unused2) {
            throw new AssertionError();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(this.f19491c.length);
        objectOutputStream.write(this.f19491c);
    }

    public byte a(int i2) {
        return this.f19491c[i2];
    }

    public e b() {
        return a(Utils.MD5);
    }

    public int c() {
        return this.f19491c.length;
    }

    public String d() {
        String str = this.f19493e;
        if (str != null) {
            return str;
        }
        String str2 = new String(this.f19491c, o.f19519a);
        this.f19493e = str2;
        return str2;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof e) {
            e eVar = (e) obj;
            int iC = eVar.c();
            byte[] bArr = this.f19491c;
            if (iC == bArr.length && eVar.a(0, bArr, 0, bArr.length)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int i2 = this.f19492d;
        if (i2 != 0) {
            return i2;
        }
        int iHashCode = Arrays.hashCode(this.f19491c);
        this.f19492d = iHashCode;
        return iHashCode;
    }

    public String toString() {
        byte[] bArr = this.f19491c;
        if (bArr.length == 0) {
            return "ByteString[size=0]";
        }
        int length = bArr.length;
        Integer numValueOf = Integer.valueOf(bArr.length);
        return length <= 16 ? String.format("ByteString[size=%s data=%s]", numValueOf, a()) : String.format("ByteString[size=%s md5=%s]", numValueOf, b().a());
    }

    public static e b(String str) {
        if (str == null) {
            throw new IllegalArgumentException("s == null");
        }
        e eVar = new e(str.getBytes(o.f19519a));
        eVar.f19493e = str;
        return eVar;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(e eVar) {
        int iC = c();
        int iC2 = eVar.c();
        int iMin = Math.min(iC, iC2);
        for (int i2 = 0; i2 < iMin; i2++) {
            int iA = a(i2) & 255;
            int iA2 = eVar.a(i2) & 255;
            if (iA != iA2) {
                return iA < iA2 ? -1 : 1;
            }
        }
        if (iC == iC2) {
            return 0;
        }
        return iC < iC2 ? -1 : 1;
    }

    public static e a(InputStream inputStream, int i2) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("in == null");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + i2);
        }
        byte[] bArr = new byte[i2];
        int i3 = 0;
        while (i3 < i2) {
            int i4 = inputStream.read(bArr, i3, i2 - i3);
            if (i4 == -1) {
                throw new EOFException();
            }
            i3 += i4;
        }
        return new e(bArr);
    }

    private e a(String str) {
        try {
            return a(MessageDigest.getInstance(str).digest(this.f19491c));
        } catch (NoSuchAlgorithmException e2) {
            throw new AssertionError(e2);
        }
    }

    public static e a(byte... bArr) {
        if (bArr != null) {
            return new e((byte[]) bArr.clone());
        }
        throw new IllegalArgumentException("data == null");
    }

    public String a() {
        byte[] bArr = this.f19491c;
        char[] cArr = new char[bArr.length * 2];
        int i2 = 0;
        for (byte b2 : bArr) {
            int i3 = i2 + 1;
            char[] cArr2 = f19489a;
            cArr[i2] = cArr2[(b2 >> 4) & 15];
            i2 += 2;
            cArr[i3] = cArr2[b2 & 15];
        }
        return new String(cArr);
    }

    void a(b bVar) {
        byte[] bArr = this.f19491c;
        bVar.a(bArr, 0, bArr.length);
    }

    public boolean a(int i2, byte[] bArr, int i3, int i4) {
        byte[] bArr2 = this.f19491c;
        return i2 <= bArr2.length - i4 && i3 <= bArr.length - i4 && o.a(bArr2, i2, bArr, i3, i4);
    }
}
