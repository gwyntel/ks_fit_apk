package com.xiaomi.push;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Constants;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class ej implements LoggerInterface {

    /* renamed from: a, reason: collision with root package name */
    private static volatile ej f23658a;

    /* renamed from: a, reason: collision with other field name */
    private Context f329a;

    /* renamed from: a, reason: collision with other field name */
    private Handler f330a;

    /* renamed from: b, reason: collision with root package name */
    private String f23659b;

    /* renamed from: c, reason: collision with root package name */
    private String f23660c = "";

    /* renamed from: a, reason: collision with other field name */
    private static final SimpleDateFormat f327a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aaa");

    /* renamed from: a, reason: collision with other field name */
    public static String f326a = "/MiPushLog";

    /* renamed from: a, reason: collision with other field name */
    private static List<Pair<String, Throwable>> f328a = Collections.synchronizedList(new ArrayList());

    private ej(Context context) {
        this.f329a = context;
        if (context.getApplicationContext() != null) {
            this.f329a = context.getApplicationContext();
        }
        this.f23659b = this.f329a.getPackageName() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + Process.myPid();
        HandlerThread handlerThread = new HandlerThread("Log2FileHandlerThread");
        handlerThread.start();
        this.f330a = new Handler(handlerThread.getLooper());
    }

    @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
    public final void log(String str) {
        log(str, null);
    }

    @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
    public final void setTag(String str) {
        this.f23659b = str;
    }

    @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
    public final void log(String str, Throwable th) {
        this.f330a.post(new ek(this, str, th));
    }

    public static ej a(Context context) {
        if (f23658a == null) {
            synchronized (ej.class) {
                try {
                    if (f23658a == null) {
                        f23658a = new ej(context);
                    }
                } finally {
                }
            }
        }
        return f23658a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:62:0x0149 -> B:108:0x017c). Please report as a decompilation issue!!! */
    /* renamed from: a, reason: collision with other method in class */
    public void m310a() throws Throwable {
        FileLock fileLockLock;
        RandomAccessFile randomAccessFile;
        File file;
        File externalFilesDir;
        BufferedWriter bufferedWriter = null;
        try {
            try {
                if (TextUtils.isEmpty(this.f23660c) && (externalFilesDir = this.f329a.getExternalFilesDir(null)) != null) {
                    this.f23660c = externalFilesDir.getAbsolutePath() + "";
                }
                file = new File(this.f23660c + f326a);
            } catch (Exception e2) {
                e = e2;
                fileLockLock = null;
                randomAccessFile = null;
            } catch (Throwable th) {
                th = th;
                fileLockLock = null;
                randomAccessFile = null;
            }
        } catch (IOException e3) {
            Log.e(this.f23659b, "", e3);
        }
        if ((!file.exists() || !file.isDirectory()) && !file.mkdirs()) {
            Log.w(this.f23659b, "Create mipushlog directory fail.");
            return;
        }
        File file2 = new File(file, "log.lock");
        if (!file2.exists() || file2.isDirectory()) {
            file2.createNewFile();
        }
        randomAccessFile = new RandomAccessFile(file2, "rw");
        try {
            fileLockLock = randomAccessFile.getChannel().lock();
            try {
                try {
                    BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(file, "log1.txt"), true)));
                    while (!f328a.isEmpty()) {
                        try {
                            Pair<String, Throwable> pairRemove = f328a.remove(0);
                            String str = (String) pairRemove.first;
                            if (pairRemove.second != null) {
                                str = (str + "\n") + Log.getStackTraceString((Throwable) pairRemove.second);
                            }
                            bufferedWriter2.write(str + "\n");
                        } catch (Exception e4) {
                            e = e4;
                            bufferedWriter = bufferedWriter2;
                            Log.e(this.f23659b, "", e);
                            if (bufferedWriter != null) {
                                try {
                                    bufferedWriter.close();
                                } catch (IOException e5) {
                                    Log.e(this.f23659b, "", e5);
                                }
                            }
                            if (fileLockLock != null && fileLockLock.isValid()) {
                                try {
                                    fileLockLock.release();
                                } catch (IOException e6) {
                                    Log.e(this.f23659b, "", e6);
                                }
                            }
                            if (randomAccessFile != null) {
                                randomAccessFile.close();
                            }
                            return;
                        } catch (Throwable th2) {
                            th = th2;
                            bufferedWriter = bufferedWriter2;
                            if (bufferedWriter != null) {
                                try {
                                    bufferedWriter.close();
                                } catch (IOException e7) {
                                    Log.e(this.f23659b, "", e7);
                                }
                            }
                            if (fileLockLock != null && fileLockLock.isValid()) {
                                try {
                                    fileLockLock.release();
                                } catch (IOException e8) {
                                    Log.e(this.f23659b, "", e8);
                                }
                            }
                            if (randomAccessFile != null) {
                                try {
                                    randomAccessFile.close();
                                    throw th;
                                } catch (IOException e9) {
                                    Log.e(this.f23659b, "", e9);
                                    throw th;
                                }
                            }
                            throw th;
                        }
                    }
                    bufferedWriter2.flush();
                    bufferedWriter2.close();
                    File file3 = new File(file, "log1.txt");
                    if (file3.length() >= 1048576) {
                        File file4 = new File(file, "log0.txt");
                        if (file4.exists() && file4.isFile()) {
                            file4.delete();
                        }
                        file3.renameTo(file4);
                    }
                    if (fileLockLock != null && fileLockLock.isValid()) {
                        try {
                            fileLockLock.release();
                        } catch (IOException e10) {
                            Log.e(this.f23659b, "", e10);
                        }
                    }
                    randomAccessFile.close();
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (Exception e11) {
                e = e11;
            }
        } catch (Exception e12) {
            e = e12;
            fileLockLock = null;
        } catch (Throwable th4) {
            th = th4;
            fileLockLock = null;
        }
    }
}
