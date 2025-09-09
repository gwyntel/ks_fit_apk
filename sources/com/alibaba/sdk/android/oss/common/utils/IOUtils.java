package com.alibaba.sdk.android.oss.common.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;

/* loaded from: classes2.dex */
public class IOUtils {
    private static final int BUFFER_SIZE = 4096;

    public static byte[] readStreamAsBytesArray(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return new byte[0];
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[4096];
        while (true) {
            int i2 = inputStream.read(bArr);
            if (i2 <= -1) {
                byteArrayOutputStream.flush();
                safeClose(byteArrayOutputStream);
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr, 0, i2);
        }
    }

    public static String readStreamAsString(InputStream inputStream, String str) throws Throwable {
        if (inputStream == null) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        char[] cArr = new char[4096];
        BufferedReader bufferedReader = null;
        try {
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream, str));
            while (true) {
                try {
                    int i2 = bufferedReader2.read(cArr);
                    if (i2 <= 0) {
                        String string = stringWriter.toString();
                        safeClose(inputStream);
                        bufferedReader2.close();
                        stringWriter.close();
                        return string;
                    }
                    stringWriter.write(cArr, 0, i2);
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    safeClose(inputStream);
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    stringWriter.close();
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static void safeClose(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void safeClose(OutputStream outputStream) throws IOException {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    public static byte[] readStreamAsBytesArray(InputStream inputStream, int i2) throws IOException {
        int i3;
        if (inputStream == null) {
            return new byte[0];
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[4096];
        long j2 = 0;
        while (true) {
            long j3 = i2;
            if (j2 >= j3 || (i3 = inputStream.read(bArr, 0, Math.min(2048, (int) (j3 - j2)))) <= -1) {
                break;
            }
            byteArrayOutputStream.write(bArr, 0, i3);
            j2 += i3;
        }
        byteArrayOutputStream.flush();
        safeClose(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
