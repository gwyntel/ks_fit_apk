package com.xiaomi.clientreport.processor;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import androidx.core.app.NotificationCompat;
import com.xiaomi.clientreport.data.EventClientReport;
import com.xiaomi.push.ab;
import com.xiaomi.push.bp;
import com.xiaomi.push.cb;
import com.xiaomi.push.h;
import com.xiaomi.push.x;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes4.dex */
public class a implements IEventProcessor {

    /* renamed from: a, reason: collision with root package name */
    protected Context f23349a;

    /* renamed from: a, reason: collision with other field name */
    private HashMap<String, ArrayList<com.xiaomi.clientreport.data.a>> f96a;

    public a(Context context) {
        a(context);
    }

    public void a(Context context) {
        this.f23349a = context;
    }

    @Override // com.xiaomi.clientreport.processor.d
    public void b() throws Throwable {
        HashMap<String, ArrayList<com.xiaomi.clientreport.data.a>> map = this.f96a;
        if (map == null) {
            return;
        }
        if (map.size() > 0) {
            Iterator<String> it = this.f96a.keySet().iterator();
            while (it.hasNext()) {
                ArrayList<com.xiaomi.clientreport.data.a> arrayList = this.f96a.get(it.next());
                if (arrayList != null && arrayList.size() > 0) {
                    com.xiaomi.clientreport.data.a[] aVarArr = new com.xiaomi.clientreport.data.a[arrayList.size()];
                    arrayList.toArray(aVarArr);
                    m105a(aVarArr);
                }
            }
        }
        this.f96a.clear();
    }

    @Override // com.xiaomi.clientreport.processor.IEventProcessor
    public String bytesToString(byte[] bArr) {
        byte[] bArrA;
        if (bArr != null && bArr.length >= 1) {
            if (!com.xiaomi.clientreport.manager.a.a(this.f23349a).m101a().isEventEncrypted()) {
                return bp.b(bArr);
            }
            String strA = cb.a(this.f23349a);
            if (!TextUtils.isEmpty(strA) && (bArrA = cb.a(strA)) != null && bArrA.length > 0) {
                try {
                    return bp.b(Base64.decode(h.a(bArrA, bArr), 2));
                } catch (InvalidAlgorithmParameterException e2) {
                    com.xiaomi.channel.commonutils.logger.b.a(e2);
                } catch (InvalidKeyException e3) {
                    com.xiaomi.channel.commonutils.logger.b.a(e3);
                } catch (NoSuchAlgorithmException e4) {
                    com.xiaomi.channel.commonutils.logger.b.a(e4);
                } catch (BadPaddingException e5) {
                    com.xiaomi.channel.commonutils.logger.b.a(e5);
                } catch (IllegalBlockSizeException e6) {
                    com.xiaomi.channel.commonutils.logger.b.a(e6);
                } catch (NoSuchPaddingException e7) {
                    com.xiaomi.channel.commonutils.logger.b.a(e7);
                }
            }
        }
        return null;
    }

    @Override // com.xiaomi.clientreport.processor.IEventProcessor
    public void setEventMap(HashMap<String, ArrayList<com.xiaomi.clientreport.data.a>> map) {
        this.f96a = map;
    }

    @Override // com.xiaomi.clientreport.processor.IEventProcessor
    public byte[] stringToBytes(String str) {
        byte[] bArrA;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (!com.xiaomi.clientreport.manager.a.a(this.f23349a).m101a().isEventEncrypted()) {
            return bp.m221a(str);
        }
        String strA = cb.a(this.f23349a);
        byte[] bArrM221a = bp.m221a(str);
        if (!TextUtils.isEmpty(strA) && bArrM221a != null && bArrM221a.length > 1 && (bArrA = cb.a(strA)) != null) {
            try {
                if (bArrA.length > 1) {
                    return h.b(bArrA, Base64.encode(bArrM221a, 2));
                }
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
            }
        }
        return null;
    }

    public static String a(com.xiaomi.clientreport.data.a aVar) {
        return String.valueOf(aVar.production);
    }

    public void a(List<String> list) {
        cb.a(this.f23349a, list);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0037 A[PHI: r2 r3 r4
      0x0037: PHI (r2v11 java.nio.channels.FileLock) = (r2v4 java.nio.channels.FileLock), (r2v2 java.nio.channels.FileLock), (r2v2 java.nio.channels.FileLock) binds: [B:68:0x010b, B:32:0x0092, B:19:0x0035] A[DONT_GENERATE, DONT_INLINE]
      0x0037: PHI (r3v15 java.io.RandomAccessFile) = (r3v4 java.io.RandomAccessFile), (r3v2 java.io.RandomAccessFile), (r3v2 java.io.RandomAccessFile) binds: [B:68:0x010b, B:32:0x0092, B:19:0x0035] A[DONT_GENERATE, DONT_INLINE]
      0x0037: PHI (r4v9 java.io.File) = (r4v3 java.io.File), (r4v1 java.io.File), (r4v1 java.io.File) binds: [B:68:0x010b, B:32:0x0092, B:19:0x0035] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.xiaomi.clientreport.processor.c
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a() throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 301
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.clientreport.processor.a.a():void");
    }

    private String b(com.xiaomi.clientreport.data.a aVar) {
        File externalFilesDir = this.f23349a.getExternalFilesDir(NotificationCompat.CATEGORY_EVENT);
        String strA = a(aVar);
        if (externalFilesDir == null) {
            return null;
        }
        String str = externalFilesDir.getAbsolutePath() + File.separator + strA;
        for (int i2 = 0; i2 < 100; i2++) {
            String str2 = str + i2;
            if (cb.m247a(this.f23349a, str2)) {
                return str2;
            }
        }
        return null;
    }

    private void a(String str, String str2) {
        EventClientReport eventClientReportA = com.xiaomi.clientreport.manager.a.a(this.f23349a).a(5001, "24:" + str + "," + str2);
        ArrayList arrayList = new ArrayList();
        arrayList.add(eventClientReportA.toJsonString());
        a(arrayList);
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x006f, code lost:
    
        com.xiaomi.channel.commonutils.logger.b.d("eventData read from cache file failed cause lengthBuffer < 1 || lengthBuffer > 4K");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.List<java.lang.String> a(java.lang.String r9) throws java.lang.Throwable {
        /*
            r8 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 4
            byte[] r2 = new byte[r1]
            byte[] r3 = new byte[r1]
            r4 = 0
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L78 java.lang.Exception -> L7a
            java.io.File r6 = new java.io.File     // Catch: java.lang.Throwable -> L78 java.lang.Exception -> L7a
            r6.<init>(r9)     // Catch: java.lang.Throwable -> L78 java.lang.Exception -> L7a
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L78 java.lang.Exception -> L7a
        L15:
            int r9 = r5.read(r2)     // Catch: java.lang.Throwable -> L25 java.lang.Exception -> L28
            r4 = -1
            if (r9 != r4) goto L1d
            goto L74
        L1d:
            java.lang.String r6 = "eventData read from cache file failed because magicNumber error"
            if (r9 == r1) goto L2b
            com.xiaomi.channel.commonutils.logger.b.d(r6)     // Catch: java.lang.Throwable -> L25 java.lang.Exception -> L28
            goto L74
        L25:
            r9 = move-exception
            r4 = r5
            goto L82
        L28:
            r9 = move-exception
            r4 = r5
            goto L7b
        L2b:
            int r9 = com.xiaomi.push.ab.a(r2)     // Catch: java.lang.Throwable -> L25 java.lang.Exception -> L28
            r7 = -573785174(0xffffffffddccbbaa, float:-1.8440715E18)
            if (r9 == r7) goto L38
            com.xiaomi.channel.commonutils.logger.b.d(r6)     // Catch: java.lang.Throwable -> L25 java.lang.Exception -> L28
            goto L74
        L38:
            int r9 = r5.read(r3)     // Catch: java.lang.Throwable -> L25 java.lang.Exception -> L28
            if (r9 != r4) goto L3f
            goto L74
        L3f:
            if (r9 == r1) goto L47
            java.lang.String r9 = "eventData read from cache file failed cause lengthBuffer error"
            com.xiaomi.channel.commonutils.logger.b.d(r9)     // Catch: java.lang.Throwable -> L25 java.lang.Exception -> L28
            goto L74
        L47:
            int r9 = com.xiaomi.push.ab.a(r3)     // Catch: java.lang.Throwable -> L25 java.lang.Exception -> L28
            r4 = 1
            if (r9 < r4) goto L6f
            r4 = 4096(0x1000, float:5.74E-42)
            if (r9 <= r4) goto L53
            goto L6f
        L53:
            byte[] r4 = new byte[r9]     // Catch: java.lang.Throwable -> L25 java.lang.Exception -> L28
            int r6 = r5.read(r4)     // Catch: java.lang.Throwable -> L25 java.lang.Exception -> L28
            if (r6 == r9) goto L61
            java.lang.String r9 = "eventData read from cache file failed cause buffer size not equal length"
            com.xiaomi.channel.commonutils.logger.b.d(r9)     // Catch: java.lang.Throwable -> L25 java.lang.Exception -> L28
            goto L74
        L61:
            java.lang.String r9 = r8.bytesToString(r4)     // Catch: java.lang.Throwable -> L25 java.lang.Exception -> L28
            boolean r4 = android.text.TextUtils.isEmpty(r9)     // Catch: java.lang.Throwable -> L25 java.lang.Exception -> L28
            if (r4 != 0) goto L15
            r0.add(r9)     // Catch: java.lang.Throwable -> L25 java.lang.Exception -> L28
            goto L15
        L6f:
            java.lang.String r9 = "eventData read from cache file failed cause lengthBuffer < 1 || lengthBuffer > 4K"
            com.xiaomi.channel.commonutils.logger.b.d(r9)     // Catch: java.lang.Throwable -> L25 java.lang.Exception -> L28
        L74:
            com.xiaomi.push.x.a(r5)
            goto L81
        L78:
            r9 = move-exception
            goto L82
        L7a:
            r9 = move-exception
        L7b:
            com.xiaomi.channel.commonutils.logger.b.a(r9)     // Catch: java.lang.Throwable -> L78
            com.xiaomi.push.x.a(r4)
        L81:
            return r0
        L82:
            com.xiaomi.push.x.a(r4)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.clientreport.processor.a.a(java.lang.String):java.util.List");
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m105a(com.xiaomi.clientreport.data.a[] aVarArr) throws Throwable {
        if (aVarArr == null || aVarArr.length == 0 || aVarArr[0] == null) {
            com.xiaomi.channel.commonutils.logger.b.m91a("event data write to cache file failed because data null");
            return;
        }
        do {
            aVarArr = a(aVarArr);
            if (aVarArr == null || aVarArr.length <= 0) {
                return;
            }
        } while (aVarArr[0] != null);
    }

    private com.xiaomi.clientreport.data.a[] a(com.xiaomi.clientreport.data.a[] aVarArr) throws Throwable {
        FileLock fileLockLock;
        RandomAccessFile randomAccessFile;
        BufferedOutputStream bufferedOutputStream;
        String strB = b(aVarArr[0]);
        BufferedOutputStream bufferedOutputStream2 = null;
        if (TextUtils.isEmpty(strB)) {
            return null;
        }
        try {
            File file = new File(strB + ".lock");
            x.m809a(file);
            randomAccessFile = new RandomAccessFile(file, "rw");
            try {
                fileLockLock = randomAccessFile.getChannel().lock();
            } catch (Exception e2) {
                e = e2;
                fileLockLock = null;
                bufferedOutputStream = null;
            } catch (Throwable th) {
                th = th;
                fileLockLock = null;
            }
        } catch (Exception e3) {
            e = e3;
            fileLockLock = null;
            randomAccessFile = null;
            bufferedOutputStream = null;
        } catch (Throwable th2) {
            th = th2;
            fileLockLock = null;
            randomAccessFile = null;
        }
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(strB), true));
        } catch (Exception e4) {
            e = e4;
            bufferedOutputStream = null;
        } catch (Throwable th3) {
            th = th3;
            x.a(bufferedOutputStream2);
            a(randomAccessFile, fileLockLock);
            throw th;
        }
        try {
            try {
                int i2 = 0;
                for (com.xiaomi.clientreport.data.a aVar : aVarArr) {
                    if (aVar != null) {
                        byte[] bArrStringToBytes = stringToBytes(aVar.toJsonString());
                        if (bArrStringToBytes != null && bArrStringToBytes.length >= 1 && bArrStringToBytes.length <= 4096) {
                            if (!cb.m247a(this.f23349a, strB)) {
                                int length = aVarArr.length - i2;
                                com.xiaomi.clientreport.data.a[] aVarArr2 = new com.xiaomi.clientreport.data.a[length];
                                System.arraycopy(aVarArr, i2, aVarArr2, 0, length);
                                x.a(bufferedOutputStream);
                                a(randomAccessFile, fileLockLock);
                                return aVarArr2;
                            }
                            bufferedOutputStream.write(ab.a(-573785174));
                            bufferedOutputStream.write(ab.a(bArrStringToBytes.length));
                            bufferedOutputStream.write(bArrStringToBytes);
                            bufferedOutputStream.flush();
                            i2++;
                        } else {
                            com.xiaomi.channel.commonutils.logger.b.d("event data throw a invalid item ");
                        }
                    }
                }
            } catch (Throwable th4) {
                th = th4;
                bufferedOutputStream2 = bufferedOutputStream;
                x.a(bufferedOutputStream2);
                a(randomAccessFile, fileLockLock);
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            com.xiaomi.channel.commonutils.logger.b.a("event data write to cache file failed cause exception", e);
            x.a(bufferedOutputStream);
            a(randomAccessFile, fileLockLock);
            return null;
        }
        x.a(bufferedOutputStream);
        a(randomAccessFile, fileLockLock);
        return null;
    }

    private void a(RandomAccessFile randomAccessFile, FileLock fileLock) throws IOException {
        if (fileLock != null && fileLock.isValid()) {
            try {
                fileLock.release();
            } catch (IOException e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
            }
        }
        x.a(randomAccessFile);
    }

    @Override // com.xiaomi.clientreport.processor.d
    /* renamed from: a, reason: collision with other method in class */
    public void mo104a(com.xiaomi.clientreport.data.a aVar) {
        if ((aVar instanceof EventClientReport) && this.f96a != null) {
            EventClientReport eventClientReport = (EventClientReport) aVar;
            String strA = a((com.xiaomi.clientreport.data.a) eventClientReport);
            ArrayList<com.xiaomi.clientreport.data.a> arrayList = this.f96a.get(strA);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
            }
            arrayList.add(eventClientReport);
            this.f96a.put(strA, arrayList);
        }
    }
}
