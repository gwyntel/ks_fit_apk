package org.spongycastle.util.encoders;

import c.a.d.a.a;
import c.a.d.a.b;
import c.a.d.l;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/* loaded from: classes5.dex */
public class Hex {
    public static final a encoder = new b();

    public static byte[] decode(byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            encoder.b(bArr, 0, bArr.length, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e2) {
            throw new DecoderException("exception decoding Hex data: " + e2.getMessage(), e2);
        }
    }

    public static byte[] encode(byte[] bArr) {
        return encode(bArr, 0, bArr.length);
    }

    public static String toHexString(byte[] bArr) {
        return toHexString(bArr, 0, bArr.length);
    }

    public static byte[] encode(byte[] bArr, int i2, int i3) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            encoder.a(bArr, i2, i3, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e2) {
            throw new EncoderException("exception encoding Hex string: " + e2.getMessage(), e2);
        }
    }

    public static String toHexString(byte[] bArr, int i2, int i3) {
        return l.b(encode(bArr, i2, i3));
    }

    public static byte[] decode(String str) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            encoder.a(str, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e2) {
            throw new DecoderException("exception decoding Hex string: " + e2.getMessage(), e2);
        }
    }

    public static int encode(byte[] bArr, OutputStream outputStream) {
        return encoder.a(bArr, 0, bArr.length, outputStream);
    }

    public static int encode(byte[] bArr, int i2, int i3, OutputStream outputStream) {
        return encoder.a(bArr, i2, i3, outputStream);
    }

    public static int decode(String str, OutputStream outputStream) {
        return encoder.a(str, outputStream);
    }
}
