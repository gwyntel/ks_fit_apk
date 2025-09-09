package com.huawei.hms.scankit.p;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class y4 {

    /* renamed from: a, reason: collision with root package name */
    private static byte[] f18029a;

    /* renamed from: b, reason: collision with root package name */
    private static byte[] f18030b;

    /* renamed from: c, reason: collision with root package name */
    private static byte[] f18031c;

    public static byte[] a() {
        return f18030b;
    }

    public static byte[] b() {
        return f18031c;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x0064 -> B:26:0x0067). Please report as a decompilation issue!!! */
    public static void c(Context context, String str) throws IOException {
        o4.d("MsModel", "load model...." + str);
        if (f18029a != null) {
            return;
        }
        InputStream inputStreamOpen = null;
        try {
            try {
                try {
                    inputStreamOpen = context.getAssets().open(str);
                    o4.d("MsModel", "inputStream" + inputStreamOpen);
                    byte[] bArr = new byte[inputStreamOpen.available()];
                    inputStreamOpen.read(bArr);
                    inputStreamOpen.close();
                    f18029a = bArr;
                    inputStreamOpen.close();
                } catch (IOException unused) {
                    o4.b("MsModel", "loadModel IOException");
                    if (inputStreamOpen != null) {
                        inputStreamOpen.close();
                    }
                } catch (Exception unused2) {
                    o4.b("MsModel", "loadModel Exception");
                    if (inputStreamOpen != null) {
                        inputStreamOpen.close();
                    }
                }
            } catch (IOException unused3) {
                o4.b("MsModel", "loadModel inputStream.close() IOException");
            }
        } catch (Throwable th) {
            if (inputStreamOpen != null) {
                try {
                    inputStreamOpen.close();
                } catch (IOException unused4) {
                    o4.b("MsModel", "loadModel inputStream.close() IOException");
                }
            }
            throw th;
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x0064 -> B:26:0x0067). Please report as a decompilation issue!!! */
    public static void a(Context context, String str) throws IOException {
        o4.d("MsModel", "load angle model...." + str);
        if (f18030b != null) {
            return;
        }
        InputStream inputStreamOpen = null;
        try {
            try {
                try {
                    inputStreamOpen = context.getAssets().open(str);
                    o4.d("MsModel", "3inputStream" + inputStreamOpen);
                    byte[] bArr = new byte[inputStreamOpen.available()];
                    inputStreamOpen.read(bArr);
                    inputStreamOpen.close();
                    f18030b = bArr;
                    inputStreamOpen.close();
                } catch (IOException unused) {
                    o4.b("MsModel", "loadAngleModel IOException");
                    if (inputStreamOpen != null) {
                        inputStreamOpen.close();
                    }
                } catch (Exception unused2) {
                    o4.b("MsModel", "loadAngleModel Exception");
                    if (inputStreamOpen != null) {
                        inputStreamOpen.close();
                    }
                }
            } catch (IOException unused3) {
                o4.b("MsModel", "loadAngleModel inputStream.close() IOException");
            }
        } catch (Throwable th) {
            if (inputStreamOpen != null) {
                try {
                    inputStreamOpen.close();
                } catch (IOException unused4) {
                    o4.b("MsModel", "loadAngleModel inputStream.close() IOException");
                }
            }
            throw th;
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x0064 -> B:26:0x0067). Please report as a decompilation issue!!! */
    public static void b(Context context, String str) throws IOException {
        o4.d("MsModel", "load corner model...." + str);
        if (f18031c != null) {
            return;
        }
        InputStream inputStreamOpen = null;
        try {
            try {
                try {
                    inputStreamOpen = context.getAssets().open(str);
                    o4.d("MsModel", "4inputStream" + inputStreamOpen);
                    byte[] bArr = new byte[inputStreamOpen.available()];
                    inputStreamOpen.read(bArr);
                    inputStreamOpen.close();
                    f18031c = bArr;
                    inputStreamOpen.close();
                } catch (IOException unused) {
                    o4.b("MsModel", "loadConerModel IOException");
                    if (inputStreamOpen != null) {
                        inputStreamOpen.close();
                    }
                } catch (Exception unused2) {
                    o4.b("MsModel", "loadConerModel Exception");
                    if (inputStreamOpen != null) {
                        inputStreamOpen.close();
                    }
                }
            } catch (IOException unused3) {
                o4.b("MsModel", "loadConerModel inputStream.close() IOException");
            }
        } catch (Throwable th) {
            if (inputStreamOpen != null) {
                try {
                    inputStreamOpen.close();
                } catch (IOException unused4) {
                    o4.b("MsModel", "loadConerModel inputStream.close() IOException");
                }
            }
            throw th;
        }
    }

    public static byte[] c() {
        return f18029a;
    }
}
