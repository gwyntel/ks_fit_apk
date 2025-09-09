package com.meizu.cloud.pushsdk.notification.g;

import com.meizu.cloud.pushinternal.DebugLogger;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes4.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    private final File f19766a;

    /* renamed from: b, reason: collision with root package name */
    private final File f19767b;

    /* renamed from: c, reason: collision with root package name */
    private final String f19768c;

    public e(String str, String str2) {
        File file = new File(str);
        this.f19766a = file;
        File file2 = new File(str2);
        this.f19767b = file2;
        this.f19768c = file2.getAbsolutePath();
        DebugLogger.i("ZipExtractTask", "Extract mInput file = " + file.toString());
        DebugLogger.i("ZipExtractTask", "Extract mOutput file = " + file2.toString());
    }

    private int a(InputStream inputStream, OutputStream outputStream) throws IOException {
        StringBuilder sb;
        byte[] bArr = new byte[8192];
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 8192);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, 8192);
        int i2 = 0;
        while (true) {
            try {
                try {
                    int i3 = bufferedInputStream.read(bArr, 0, 8192);
                    if (i3 == -1) {
                        break;
                    }
                    bufferedOutputStream.write(bArr, 0, i3);
                    i2 += i3;
                } catch (IOException e2) {
                    DebugLogger.e("ZipExtractTask", "Extracted IOException:" + e2.toString());
                    try {
                        bufferedOutputStream.close();
                    } catch (IOException e3) {
                        DebugLogger.e("ZipExtractTask", "out.close() IOException e=" + e3.toString());
                    }
                    try {
                        bufferedInputStream.close();
                    } catch (IOException e4) {
                        e = e4;
                        sb = new StringBuilder();
                        sb.append("in.close() IOException e=");
                        sb.append(e.toString());
                        DebugLogger.e("ZipExtractTask", sb.toString());
                        return i2;
                    }
                }
            } finally {
            }
        }
        bufferedOutputStream.flush();
        try {
            bufferedOutputStream.close();
        } catch (IOException e5) {
            DebugLogger.e("ZipExtractTask", "out.close() IOException e=" + e5.toString());
        }
        try {
            bufferedInputStream.close();
        } catch (IOException e6) {
            e = e6;
            sb = new StringBuilder();
            sb.append("in.close() IOException e=");
            sb.append(e.toString());
            DebugLogger.e("ZipExtractTask", sb.toString());
            return i2;
        }
        return i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:83:0x01e5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private long c() throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 545
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.notification.g.e.c():long");
    }

    public boolean b() {
        return c() > 0;
    }

    private void a() {
        StringBuilder sb;
        String str;
        File file = this.f19766a;
        if (file == null || !file.exists()) {
            return;
        }
        if (this.f19766a.delete()) {
            sb = new StringBuilder();
            str = "Delete file:";
        } else {
            sb = new StringBuilder();
            str = "Can't delete file:";
        }
        sb.append(str);
        sb.append(this.f19766a.toString());
        sb.append(" after extracted.");
        DebugLogger.i("ZipExtractTask", sb.toString());
    }
}
