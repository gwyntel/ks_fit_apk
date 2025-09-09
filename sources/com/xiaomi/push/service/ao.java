package com.xiaomi.push.service;

import android.os.Process;
import android.text.TextUtils;
import com.xiaomi.push.db;
import com.xiaomi.push.ew;
import com.xiaomi.push.gm;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class ao {

    /* renamed from: a, reason: collision with other field name */
    private static final Pattern f1012a = Pattern.compile("([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})");

    /* renamed from: a, reason: collision with root package name */
    private static long f24465a = 0;

    /* renamed from: a, reason: collision with other field name */
    private static ThreadPoolExecutor f1011a = new ThreadPoolExecutor(1, 1, 20, TimeUnit.SECONDS, new LinkedBlockingQueue());

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(String str) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            com.xiaomi.channel.commonutils.logger.b.m91a("ConnectivityTest: begin to connect to " + str);
            Socket socket = new Socket();
            socket.connect(db.m266a(str, 5222), 5000);
            socket.setTcpNoDelay(true);
            com.xiaomi.channel.commonutils.logger.b.m91a("ConnectivityTest: connect to " + str + " in " + (System.currentTimeMillis() - jCurrentTimeMillis));
            socket.close();
            return true;
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.d("ConnectivityTest: could not connect to:" + str + " exception: " + th.getClass().getSimpleName() + " description: " + th.getMessage());
            return false;
        }
    }

    public static void a() {
        ew.a aVarM771a;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if ((f1011a.getActiveCount() <= 0 || jCurrentTimeMillis - f24465a >= 1800000) && gm.m436a().m441a() && (aVarM771a = bw.a().m771a()) != null && aVarM771a.e() > 0) {
            f24465a = jCurrentTimeMillis;
            a(aVarM771a.m318a(), true);
        }
    }

    public static void a(List<String> list, boolean z2) {
        f1011a.execute(new ap(list, z2));
    }

    private static String a(String str) throws Throwable {
        BufferedReader bufferedReader;
        Throwable th;
        try {
            bufferedReader = new BufferedReader(new FileReader(new File(str)));
        } catch (Exception unused) {
            bufferedReader = null;
        } catch (Throwable th2) {
            bufferedReader = null;
            th = th2;
        }
        try {
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line != null) {
                    sb.append("\n");
                    sb.append(line);
                } else {
                    String string = sb.toString();
                    com.xiaomi.push.x.a(bufferedReader);
                    return string;
                }
            }
        } catch (Exception unused2) {
            com.xiaomi.push.x.a(bufferedReader);
            return null;
        } catch (Throwable th3) {
            th = th3;
            com.xiaomi.push.x.a(bufferedReader);
            throw th;
        }
    }

    public static void b() throws Throwable {
        String strA = a("/proc/self/net/tcp");
        if (!TextUtils.isEmpty(strA)) {
            com.xiaomi.channel.commonutils.logger.b.m91a("dump tcp for uid = " + Process.myUid());
            com.xiaomi.channel.commonutils.logger.b.m91a(strA);
        }
        String strA2 = a("/proc/self/net/tcp6");
        if (TextUtils.isEmpty(strA2)) {
            return;
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("dump tcp6 for uid = " + Process.myUid());
        com.xiaomi.channel.commonutils.logger.b.m91a(strA2);
    }
}
