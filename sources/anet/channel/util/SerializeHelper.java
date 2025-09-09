package anet.channel.util;

import android.content.Context;
import anet.channel.GlobalAppRuntimeInfo;
import java.io.File;
import java.io.Serializable;

/* loaded from: classes2.dex */
public class SerializeHelper {
    private static final String TAG = "awcn.SerializeHelper";
    private static File cacheDir;

    public static File getCacheFiles(String str) {
        Context context;
        if (cacheDir == null && (context = GlobalAppRuntimeInfo.getContext()) != null) {
            cacheDir = context.getCacheDir();
        }
        return new File(cacheDir, str);
    }

    public static synchronized void persist(Serializable serializable, File file) {
        persist(serializable, file, null);
    }

    public static synchronized <T> T restore(File file) {
        return (T) restore(file, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x001c A[Catch: all -> 0x0036, TryCatch #2 {all -> 0x0036, blocks: (B:10:0x0016, B:12:0x001c, B:14:0x0022, B:21:0x003e, B:22:0x0040), top: B:55:0x0016 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized <T> T restore(java.io.File r17, anet.channel.statist.StrategyStatObject r18) {
        /*
            r1 = r18
            r0 = 2
            r2 = 3
            r3 = 1
            r4 = 0
            java.lang.Class<anet.channel.util.SerializeHelper> r5 = anet.channel.util.SerializeHelper.class
            monitor-enter(r5)
            if (r1 == 0) goto L15
            java.lang.String r6 = java.lang.String.valueOf(r17)     // Catch: java.lang.Throwable -> L12
            r1.readStrategyFilePath = r6     // Catch: java.lang.Throwable -> L12
            goto L15
        L12:
            r0 = move-exception
            goto Lc2
        L15:
            r6 = 0
            boolean r7 = r17.exists()     // Catch: java.lang.Throwable -> L36
            if (r7 != 0) goto L3c
            boolean r7 = anet.channel.util.ALog.isPrintLog(r2)     // Catch: java.lang.Throwable -> L36
            if (r7 == 0) goto L3a
            java.lang.String r7 = "awcn.SerializeHelper"
            java.lang.String r8 = "file not exist."
            java.lang.String r9 = r17.getName()     // Catch: java.lang.Throwable -> L36
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch: java.lang.Throwable -> L36
            java.lang.String r10 = "file"
            r0[r4] = r10     // Catch: java.lang.Throwable -> L36
            r0[r3] = r9     // Catch: java.lang.Throwable -> L36
            anet.channel.util.ALog.w(r7, r8, r6, r0)     // Catch: java.lang.Throwable -> L36
            goto L3a
        L36:
            r0 = move-exception
            r9 = r6
            r12 = r9
            goto L9e
        L3a:
            monitor-exit(r5)
            return r6
        L3c:
            if (r1 == 0) goto L40
            r1.isFileExists = r3     // Catch: java.lang.Throwable -> L36
        L40:
            long r7 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> L36
            java.io.FileInputStream r9 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L36
            r10 = r17
            r9.<init>(r10)     // Catch: java.lang.Throwable -> L36
            java.io.ObjectInputStream r11 = new java.io.ObjectInputStream     // Catch: java.lang.Throwable -> L9c
            java.io.BufferedInputStream r12 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L9c
            r12.<init>(r9)     // Catch: java.lang.Throwable -> L9c
            r11.<init>(r12)     // Catch: java.lang.Throwable -> L9c
            java.lang.Object r12 = r11.readObject()     // Catch: java.lang.Throwable -> L9c
            r11.close()     // Catch: java.lang.Throwable -> L68
            long r13 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> L68
            long r13 = r13 - r7
            if (r1 == 0) goto L6a
            r1.isReadObjectSucceed = r3     // Catch: java.lang.Throwable -> L68
            r1.readCostTime = r13     // Catch: java.lang.Throwable -> L68
            goto L6a
        L68:
            r0 = move-exception
            goto L9e
        L6a:
            java.lang.String r7 = "awcn.SerializeHelper"
            java.lang.String r8 = "restore end."
            java.io.File r11 = r17.getAbsoluteFile()     // Catch: java.lang.Throwable -> L68
            long r15 = r17.length()     // Catch: java.lang.Throwable -> L68
            java.lang.Long r10 = java.lang.Long.valueOf(r15)     // Catch: java.lang.Throwable -> L68
            java.lang.Long r13 = java.lang.Long.valueOf(r13)     // Catch: java.lang.Throwable -> L68
            r14 = 6
            java.lang.Object[] r14 = new java.lang.Object[r14]     // Catch: java.lang.Throwable -> L68
            java.lang.String r15 = "file"
            r14[r4] = r15     // Catch: java.lang.Throwable -> L68
            r14[r3] = r11     // Catch: java.lang.Throwable -> L68
            java.lang.String r3 = "size"
            r14[r0] = r3     // Catch: java.lang.Throwable -> L68
            r14[r2] = r10     // Catch: java.lang.Throwable -> L68
            java.lang.String r0 = "cost"
            r3 = 4
            r14[r3] = r0     // Catch: java.lang.Throwable -> L68
            r0 = 5
            r14[r0] = r13     // Catch: java.lang.Throwable -> L68
            anet.channel.util.ALog.i(r7, r8, r6, r14)     // Catch: java.lang.Throwable -> L68
        L98:
            r9.close()     // Catch: java.lang.Throwable -> L12 java.io.IOException -> Lba
            goto Lba
        L9c:
            r0 = move-exception
            r12 = r6
        L9e:
            boolean r2 = anet.channel.util.ALog.isPrintLog(r2)     // Catch: java.lang.Throwable -> Lae
            if (r2 == 0) goto Lb0
            java.lang.String r2 = "awcn.SerializeHelper"
            java.lang.String r3 = "restore file fail."
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch: java.lang.Throwable -> Lae
            anet.channel.util.ALog.w(r2, r3, r6, r0, r4)     // Catch: java.lang.Throwable -> Lae
            goto Lb0
        Lae:
            r0 = move-exception
            goto Lbc
        Lb0:
            if (r1 == 0) goto Lb7
            java.lang.String r2 = "SerializeHelper.restore()"
            r1.appendErrorTrace(r2, r0)     // Catch: java.lang.Throwable -> Lae
        Lb7:
            if (r9 == 0) goto Lba
            goto L98
        Lba:
            monitor-exit(r5)
            return r12
        Lbc:
            if (r9 == 0) goto Lc1
            r9.close()     // Catch: java.lang.Throwable -> L12 java.io.IOException -> Lc1
        Lc1:
            throw r0     // Catch: java.lang.Throwable -> L12
        Lc2:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L12
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.util.SerializeHelper.restore(java.io.File, anet.channel.statist.StrategyStatObject):java.lang.Object");
    }

    /* JADX WARN: Not initialized variable reg: 11, insn: 0x0053: MOVE (r7 I:??[OBJECT, ARRAY]) = (r11 I:??[OBJECT, ARRAY]), block:B:18:0x0053 */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0088 A[Catch: all -> 0x004d, TryCatch #9 {, blocks: (B:8:0x0013, B:12:0x0049, B:33:0x0081, B:35:0x0088, B:37:0x009a, B:39:0x00a0, B:42:0x00db, B:47:0x00ec, B:49:0x00f0, B:40:0x00d0, B:53:0x00fd, B:54:0x0100, B:31:0x007d, B:55:0x0101), top: B:78:0x000d, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x009a A[Catch: all -> 0x004d, TryCatch #9 {, blocks: (B:8:0x0013, B:12:0x0049, B:33:0x0081, B:35:0x0088, B:37:0x009a, B:39:0x00a0, B:42:0x00db, B:47:0x00ec, B:49:0x00f0, B:40:0x00d0, B:53:0x00fd, B:54:0x0100, B:31:0x007d, B:55:0x0101), top: B:78:0x000d, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00e7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized void persist(java.io.Serializable r17, java.io.File r18, anet.channel.statist.StrategyStatObject r19) {
        /*
            Method dump skipped, instructions count: 270
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.util.SerializeHelper.persist(java.io.Serializable, java.io.File, anet.channel.statist.StrategyStatObject):void");
    }
}
