package com.xiaomi.push;

import android.content.Context;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes4.dex */
public final class t {

    /* renamed from: a, reason: collision with root package name */
    private static final Set<String> f24637a = Collections.synchronizedSet(new HashSet());

    /* renamed from: a, reason: collision with other field name */
    private Context f1118a;

    /* renamed from: a, reason: collision with other field name */
    private RandomAccessFile f1119a;

    /* renamed from: a, reason: collision with other field name */
    private String f1120a;

    /* renamed from: a, reason: collision with other field name */
    private FileLock f1121a;

    private t(Context context) {
        this.f1118a = context;
    }

    public static t a(Context context, File file) throws IOException {
        com.xiaomi.channel.commonutils.logger.b.c("Locking: " + file.getAbsolutePath());
        String str = file.getAbsolutePath() + ".LOCK";
        File file2 = new File(str);
        if (!file2.exists()) {
            file2.getParentFile().mkdirs();
            file2.createNewFile();
        }
        Set<String> set = f24637a;
        if (!set.add(str)) {
            throw new IOException("abtain lock failure");
        }
        t tVar = new t(context);
        tVar.f1120a = str;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file2, "rw");
            tVar.f1119a = randomAccessFile;
            tVar.f1121a = randomAccessFile.getChannel().lock();
            com.xiaomi.channel.commonutils.logger.b.c("Locked: " + str + " :" + tVar.f1121a);
            if (tVar.f1121a == null) {
                RandomAccessFile randomAccessFile2 = tVar.f1119a;
                if (randomAccessFile2 != null) {
                    x.a(randomAccessFile2);
                }
                set.remove(tVar.f1120a);
            }
            return tVar;
        } catch (Throwable th) {
            if (tVar.f1121a == null) {
                RandomAccessFile randomAccessFile3 = tVar.f1119a;
                if (randomAccessFile3 != null) {
                    x.a(randomAccessFile3);
                }
                f24637a.remove(tVar.f1120a);
            }
            throw th;
        }
    }

    public void a() throws IOException {
        com.xiaomi.channel.commonutils.logger.b.c("unLock: " + this.f1121a);
        FileLock fileLock = this.f1121a;
        if (fileLock != null && fileLock.isValid()) {
            try {
                this.f1121a.release();
            } catch (IOException unused) {
            }
            this.f1121a = null;
        }
        RandomAccessFile randomAccessFile = this.f1119a;
        if (randomAccessFile != null) {
            x.a(randomAccessFile);
        }
        f24637a.remove(this.f1120a);
    }
}
