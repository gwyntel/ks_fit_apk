package com.huawei.hms.framework.common;

import android.database.Cursor;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/* loaded from: classes4.dex */
public class IoUtils {
    private static final int BUFF_SIZE = 4096;
    private static final int MAX_SIZE = 16777216;

    private IoUtils() {
    }

    public static void close(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

    public static void closeSecure(Reader reader) throws IOException {
        closeSecure((Closeable) reader);
    }

    public static long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        String packageName = ContextHolder.getAppContext() != null ? ContextHolder.getAppContext().getPackageName() : "";
        byte[] bArr = new byte[4096];
        long j2 = 0;
        while (true) {
            int i2 = inputStream.read(bArr);
            if (-1 == i2) {
                return j2;
            }
            if (j2 > 16777216 && !"com.huawei.health".equals(packageName)) {
                throw new IOException("input data too large for byte.");
            }
            outputStream.write(bArr, 0, i2);
            j2 += i2;
        }
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        copy(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static void closeSecure(Writer writer) throws IOException {
        closeSecure((Closeable) writer);
    }

    public static void closeSecure(InputStream inputStream) throws IOException {
        closeSecure((Closeable) inputStream);
    }

    public static void closeSecure(OutputStream outputStream) throws IOException {
        closeSecure((Closeable) outputStream);
    }

    public static void closeSecure(Closeable closeable) throws IOException {
        if (closeable == null) {
            Logger.w("IOUtil", "closeable is null");
            return;
        }
        try {
            closeable.close();
        } catch (IOException e2) {
            Logger.w("IOUtil", "closeSecure IOException", e2);
        }
    }
}
