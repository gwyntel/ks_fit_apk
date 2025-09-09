package com.alibaba.ailabs.tg.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/* loaded from: classes2.dex */
public final class CloneUtils {
    /* JADX WARN: Removed duplicated region for block: B:35:0x0034 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.Object bytes2Object(byte[] r3) throws java.lang.Throwable {
        /*
            r0 = 0
            if (r3 != 0) goto L4
            return r0
        L4:
            java.io.ObjectInputStream r1 = new java.io.ObjectInputStream     // Catch: java.lang.Throwable -> L20 java.lang.Exception -> L22
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream     // Catch: java.lang.Throwable -> L20 java.lang.Exception -> L22
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L20 java.lang.Exception -> L22
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L20 java.lang.Exception -> L22
            java.lang.Object r3 = r1.readObject()     // Catch: java.lang.Throwable -> L1b java.lang.Exception -> L1e
            r1.close()     // Catch: java.io.IOException -> L16
            goto L1a
        L16:
            r0 = move-exception
            r0.printStackTrace()
        L1a:
            return r3
        L1b:
            r3 = move-exception
            r0 = r1
            goto L32
        L1e:
            r3 = move-exception
            goto L24
        L20:
            r3 = move-exception
            goto L32
        L22:
            r3 = move-exception
            r1 = r0
        L24:
            r3.printStackTrace()     // Catch: java.lang.Throwable -> L1b
            if (r1 == 0) goto L31
            r1.close()     // Catch: java.io.IOException -> L2d
            goto L31
        L2d:
            r3 = move-exception
            r3.printStackTrace()
        L31:
            return r0
        L32:
            if (r0 == 0) goto L3c
            r0.close()     // Catch: java.io.IOException -> L38
            goto L3c
        L38:
            r0 = move-exception
            r0.printStackTrace()
        L3c:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.ailabs.tg.utils.CloneUtils.bytes2Object(byte[]):java.lang.Object");
    }

    public static <T> T deepClone(Serializable serializable) {
        if (serializable == null) {
            return null;
        }
        return (T) bytes2Object(serializable2Bytes(serializable));
    }

    /* JADX WARN: Not initialized variable reg: 1, insn: 0x001f: MOVE (r0 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]) (LINE:32), block:B:13:0x001f */
    private static byte[] serializable2Bytes(Serializable serializable) throws Throwable {
        ObjectOutputStream objectOutputStream;
        ObjectOutputStream objectOutputStream2;
        ByteArrayOutputStream byteArrayOutputStream;
        ObjectOutputStream objectOutputStream3 = null;
        try {
            if (serializable == null) {
                return null;
            }
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                objectOutputStream2 = new ObjectOutputStream(byteArrayOutputStream);
            } catch (Exception e2) {
                e = e2;
                objectOutputStream2 = null;
            } catch (Throwable th) {
                th = th;
                if (objectOutputStream3 != null) {
                    try {
                        objectOutputStream3.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                throw th;
            }
            try {
                objectOutputStream2.writeObject(serializable);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                try {
                    objectOutputStream2.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                return byteArray;
            } catch (Exception e5) {
                e = e5;
                e.printStackTrace();
                if (objectOutputStream2 != null) {
                    try {
                        objectOutputStream2.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                }
                return null;
            }
        } catch (Throwable th2) {
            th = th2;
            objectOutputStream3 = objectOutputStream;
        }
    }
}
