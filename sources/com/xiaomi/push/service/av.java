package com.xiaomi.push.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class av {

    /* renamed from: a, reason: collision with root package name */
    private static long f24472a;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        int f24473a;

        /* renamed from: a, reason: collision with other field name */
        byte[] f1017a;

        public a(byte[] bArr, int i2) {
            this.f1017a = bArr;
            this.f24473a = i2;
        }
    }

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        public long f24474a;

        /* renamed from: a, reason: collision with other field name */
        public Bitmap f1018a;

        public b(Bitmap bitmap, long j2) {
            this.f1018a = bitmap;
            this.f24474a = j2;
        }
    }

    public static b a(Context context, String str, boolean z2) throws Throwable {
        a aVarA;
        ByteArrayInputStream byteArrayInputStream = null;
        b bVar = new b(null, 0L);
        Bitmap bitmapB = b(context, str);
        try {
            if (bitmapB != null) {
                bVar.f1018a = bitmapB;
                return bVar;
            }
            try {
                aVarA = a(str, z2);
            } catch (Exception e2) {
                e = e2;
            }
            if (aVarA == null) {
                com.xiaomi.push.x.a((Closeable) null);
                return bVar;
            }
            bVar.f24474a = aVarA.f24473a;
            byte[] bArr = aVarA.f1017a;
            if (bArr != null) {
                if (z2) {
                    ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(bArr);
                    try {
                        int iA = a(context, byteArrayInputStream2);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = iA;
                        bVar.f1018a = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
                        byteArrayInputStream = byteArrayInputStream2;
                    } catch (Exception e3) {
                        e = e3;
                        byteArrayInputStream = byteArrayInputStream2;
                        com.xiaomi.channel.commonutils.logger.b.a(e);
                        com.xiaomi.push.x.a((Closeable) byteArrayInputStream);
                        return bVar;
                    } catch (Throwable th) {
                        th = th;
                        byteArrayInputStream = byteArrayInputStream2;
                        com.xiaomi.push.x.a((Closeable) byteArrayInputStream);
                        throw th;
                    }
                } else {
                    bVar.f1018a = BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
                }
            }
            a(context, aVarA.f1017a, str);
            com.xiaomi.push.x.a((Closeable) byteArrayInputStream);
            return bVar;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static Bitmap b(Context context, String str) throws Throwable {
        Throwable th;
        FileInputStream fileInputStream;
        Bitmap bitmap;
        File file = new File(context.getCacheDir().getPath() + File.separator + "mipush_icon", com.xiaomi.push.bp.a(str));
        FileInputStream fileInputStream2 = null;
        Bitmap bitmapDecodeStream = null;
        fileInputStream2 = null;
        if (!file.exists()) {
            return null;
        }
        try {
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    bitmapDecodeStream = BitmapFactory.decodeStream(fileInputStream);
                    file.setLastModified(System.currentTimeMillis());
                    com.xiaomi.push.x.a((Closeable) fileInputStream);
                    return bitmapDecodeStream;
                } catch (Exception e2) {
                    e = e2;
                    Bitmap bitmap2 = bitmapDecodeStream;
                    fileInputStream2 = fileInputStream;
                    bitmap = bitmap2;
                    com.xiaomi.channel.commonutils.logger.b.a(e);
                    com.xiaomi.push.x.a((Closeable) fileInputStream2);
                    return bitmap;
                } catch (Throwable th2) {
                    th = th2;
                    com.xiaomi.push.x.a((Closeable) fileInputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                FileInputStream fileInputStream3 = fileInputStream2;
                th = th3;
                fileInputStream = fileInputStream3;
            }
        } catch (Exception e3) {
            e = e3;
            bitmap = null;
        }
    }

    /* JADX WARN: Not initialized variable reg: 2, insn: 0x00a5: MOVE (r0 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:33:0x00a5 */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00df A[PHI: r1
      0x00df: PHI (r1v5 java.net.HttpURLConnection) = (r1v4 java.net.HttpURLConnection), (r1v6 java.net.HttpURLConnection) binds: [B:50:0x00dd, B:54:0x00fa] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.xiaomi.push.service.av.a a(java.lang.String r10, boolean r11) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 263
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.av.a(java.lang.String, boolean):com.xiaomi.push.service.av$a");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r0v3 */
    public static Bitmap a(Context context, String str) throws Throwable {
        InputStream inputStreamOpenInputStream;
        InputStream inputStreamOpenInputStream2;
        int iA;
        Uri uri = Uri.parse(str);
        ?? r02 = 0;
        r02 = 0;
        try {
            try {
                inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
            } catch (IOException e2) {
                e = e2;
                inputStreamOpenInputStream2 = null;
                inputStreamOpenInputStream = null;
            } catch (Throwable th) {
                th = th;
                inputStreamOpenInputStream = null;
            }
            try {
                iA = a(context, inputStreamOpenInputStream);
                inputStreamOpenInputStream2 = context.getContentResolver().openInputStream(uri);
            } catch (IOException e3) {
                e = e3;
                inputStreamOpenInputStream2 = null;
            } catch (Throwable th2) {
                th = th2;
                com.xiaomi.push.x.a((Closeable) r02);
                com.xiaomi.push.x.a((Closeable) inputStreamOpenInputStream);
                throw th;
            }
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = iA;
                Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpenInputStream2, null, options);
                com.xiaomi.push.x.a((Closeable) inputStreamOpenInputStream2);
                com.xiaomi.push.x.a((Closeable) inputStreamOpenInputStream);
                return bitmapDecodeStream;
            } catch (IOException e4) {
                e = e4;
                com.xiaomi.channel.commonutils.logger.b.a(e);
                com.xiaomi.push.x.a((Closeable) inputStreamOpenInputStream2);
                com.xiaomi.push.x.a((Closeable) inputStreamOpenInputStream);
                return null;
            }
        } catch (Throwable th3) {
            th = th3;
            r02 = context;
        }
    }

    private static int a(Context context, InputStream inputStream) {
        int i2;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        if (options.outWidth != -1 && options.outHeight != -1) {
            int iRound = Math.round((context.getResources().getDisplayMetrics().densityDpi / 160.0f) * 48.0f);
            int i3 = options.outWidth;
            if (i3 <= iRound || (i2 = options.outHeight) <= iRound) {
                return 1;
            }
            return Math.min(i3 / iRound, i2 / iRound);
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("decode dimension failed for bitmap.");
        return 1;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:43:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void a(android.content.Context r5, byte[] r6, java.lang.String r7) throws java.lang.Throwable {
        /*
            if (r6 != 0) goto L8
            java.lang.String r5 = "cannot save small icon cause bitmap is null"
            com.xiaomi.channel.commonutils.logger.b.m91a(r5)
            return
        L8:
            a(r5)
            java.io.File r0 = new java.io.File
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.io.File r2 = r5.getCacheDir()
            java.lang.String r2 = r2.getPath()
            r1.append(r2)
            java.lang.String r2 = java.io.File.separator
            r1.append(r2)
            java.lang.String r2 = "mipush_icon"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            boolean r1 = r0.exists()
            if (r1 != 0) goto L37
            r0.mkdirs()
        L37:
            java.io.File r1 = new java.io.File
            java.lang.String r7 = com.xiaomi.push.bp.a(r7)
            r1.<init>(r0, r7)
            r7 = 0
            boolean r0 = r1.exists()     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4e
            if (r0 != 0) goto L51
            r1.createNewFile()     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4e
            goto L51
        L4b:
            r5 = move-exception
            r0 = r7
            goto Lad
        L4e:
            r6 = move-exception
            r0 = r7
            goto L71
        L51:
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4e
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4e
            java.io.BufferedOutputStream r3 = new java.io.BufferedOutputStream     // Catch: java.lang.Throwable -> L6e java.lang.Exception -> L70
            r3.<init>(r0)     // Catch: java.lang.Throwable -> L6e java.lang.Exception -> L70
            r3.write(r6)     // Catch: java.lang.Throwable -> L68 java.lang.Exception -> L6b
            r3.flush()     // Catch: java.lang.Throwable -> L68 java.lang.Exception -> L6b
            com.xiaomi.push.x.a(r3)
        L64:
            com.xiaomi.push.x.a(r0)
            goto L78
        L68:
            r5 = move-exception
            r7 = r3
            goto Lad
        L6b:
            r6 = move-exception
            r7 = r3
            goto L71
        L6e:
            r5 = move-exception
            goto Lad
        L70:
            r6 = move-exception
        L71:
            com.xiaomi.channel.commonutils.logger.b.a(r6)     // Catch: java.lang.Throwable -> L6e
            com.xiaomi.push.x.a(r7)
            goto L64
        L78:
            long r6 = com.xiaomi.push.service.av.f24472a
            r3 = 0
            int r6 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r6 != 0) goto Lac
            java.io.File r6 = new java.io.File
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.io.File r5 = r5.getCacheDir()
            java.lang.String r5 = r5.getPath()
            r7.append(r5)
            java.lang.String r5 = java.io.File.separator
            r7.append(r5)
            r7.append(r2)
            java.lang.String r5 = r7.toString()
            r6.<init>(r5)
            long r5 = com.xiaomi.push.w.a(r6)
            long r0 = r1.length()
            long r5 = r5 + r0
            com.xiaomi.push.service.av.f24472a = r5
        Lac:
            return
        Lad:
            com.xiaomi.push.x.a(r7)
            com.xiaomi.push.x.a(r0)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.av.a(android.content.Context, byte[], java.lang.String):void");
    }

    private static void a(Context context) {
        File file = new File(context.getCacheDir().getPath() + File.separator + "mipush_icon");
        if (file.exists()) {
            if (f24472a == 0) {
                f24472a = com.xiaomi.push.w.a(file);
            }
            if (f24472a > 15728640) {
                try {
                    File[] fileArrListFiles = file.listFiles();
                    for (int i2 = 0; i2 < fileArrListFiles.length; i2++) {
                        if (!fileArrListFiles[i2].isDirectory() && Math.abs(System.currentTimeMillis() - fileArrListFiles[i2].lastModified()) > 1209600) {
                            fileArrListFiles[i2].delete();
                        }
                    }
                } catch (Exception e2) {
                    com.xiaomi.channel.commonutils.logger.b.a(e2);
                }
                f24472a = 0L;
            }
        }
    }
}
