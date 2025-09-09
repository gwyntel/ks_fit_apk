package com.aliyun.alink.linksdk.tmp.utils;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class ResHelper {
    protected static final String TAG = "[Tmp]ResHelper";

    /* JADX WARN: Removed duplicated region for block: B:44:0x0071 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getDataFileData(android.content.Context r5, java.lang.String r6) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "[Tmp]ResHelper"
            r1 = 0
            if (r5 != 0) goto Lb
            java.lang.String r5 = "getDataFileData context empty"
            com.aliyun.alink.linksdk.tools.ALog.d(r0, r5)
            return r1
        Lb:
            java.io.File r5 = r5.getExternalCacheDir()
            if (r5 != 0) goto L17
            java.lang.String r5 = "getDataFileData cacheDir empty"
            com.aliyun.alink.linksdk.tools.ALog.d(r0, r5)
            return r1
        L17:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "getDataFileData cacheDir:"
            r2.append(r3)
            java.lang.String r3 = r5.getAbsolutePath()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.aliyun.alink.linksdk.tools.ALog.d(r0, r2)
            java.io.File r0 = new java.io.File
            java.lang.String r5 = r5.getAbsolutePath()
            r0.<init>(r5, r6)
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L5d java.lang.Exception -> L5f
            r5.<init>(r0)     // Catch: java.lang.Throwable -> L5d java.lang.Exception -> L5f
            int r6 = r5.available()     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            byte[] r0 = new byte[r6]     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            r2 = 0
            r5.read(r0, r2, r6)     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            java.lang.String r3 = new java.lang.String     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            java.lang.String r4 = "UTF-8"
            r3.<init>(r0, r2, r6, r4)     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            r5.close()     // Catch: java.lang.Exception -> L52
            goto L56
        L52:
            r5 = move-exception
            r5.printStackTrace()
        L56:
            r1 = r3
            goto L6e
        L58:
            r6 = move-exception
            r1 = r5
            goto L6f
        L5b:
            r6 = move-exception
            goto L61
        L5d:
            r6 = move-exception
            goto L6f
        L5f:
            r6 = move-exception
            r5 = r1
        L61:
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L58
            if (r5 == 0) goto L6e
            r5.close()     // Catch: java.lang.Exception -> L6a
            goto L6e
        L6a:
            r5 = move-exception
            r5.printStackTrace()
        L6e:
            return r1
        L6f:
            if (r1 == 0) goto L79
            r1.close()     // Catch: java.lang.Exception -> L75
            goto L79
        L75:
            r5 = move-exception
            r5.printStackTrace()
        L79:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.linksdk.tmp.utils.ResHelper.getDataFileData(android.content.Context, java.lang.String):java.lang.String");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r0v7 */
    public static byte[] getFileByte(File file) throws Throwable {
        byte[] bArr;
        ?? r02 = 0;
        byte[] bArr2 = null;
        InputStream inputStream = null;
        try {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    int iAvailable = fileInputStream.available();
                    bArr2 = new byte[iAvailable];
                    fileInputStream.read(bArr2, 0, iAvailable);
                    try {
                        fileInputStream.close();
                        r02 = bArr2;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        r02 = bArr2;
                    }
                } catch (Exception e3) {
                    e = e3;
                    bArr = bArr2;
                    inputStream = fileInputStream;
                    e.printStackTrace();
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Exception e4) {
                            e4.printStackTrace();
                        }
                    }
                    r02 = bArr;
                    return r02;
                } catch (Throwable th) {
                    th = th;
                    r02 = fileInputStream;
                    if (r02 != 0) {
                        try {
                            r02.close();
                        } catch (Exception e5) {
                            e5.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Exception e6) {
                e = e6;
                bArr = null;
            }
            return r02;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static String getFileStr(File file) {
        try {
            return new String(getFileByte(file), "UTF-8");
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getRawDeviceModel(Context context, int i2) throws IOException {
        String str;
        InputStream inputStreamOpenRawResource = null;
        try {
            try {
                inputStreamOpenRawResource = context.getResources().openRawResource(i2);
                int iAvailable = inputStreamOpenRawResource.available();
                byte[] bArr = new byte[iAvailable];
                inputStreamOpenRawResource.read(bArr);
                str = new String(bArr, 0, iAvailable, "UTF-8");
                try {
                    inputStreamOpenRawResource.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } catch (Exception e3) {
                e3.printStackTrace();
                if (inputStreamOpenRawResource != null) {
                    try {
                        inputStreamOpenRawResource.close();
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                }
                str = "";
            }
            return str;
        } catch (Throwable th) {
            if (inputStreamOpenRawResource != null) {
                try {
                    inputStreamOpenRawResource.close();
                } catch (Exception e5) {
                    e5.printStackTrace();
                }
            }
            throw th;
        }
    }
}
