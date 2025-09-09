package com.umeng.message.proguard;

import android.app.Application;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import androidx.exifinterface.media.ExifInterface;
import androidx.media3.datasource.cache.CacheDataSink;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.message.MessageSharedPrefs;
import com.umeng.message.MsgConstant;
import com.umeng.message.common.UPLog;
import com.yc.utesdk.utils.open.CalendarUtils;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class p {

    /* renamed from: a, reason: collision with root package name */
    Boolean f22872a;

    /* renamed from: b, reason: collision with root package name */
    public boolean f22873b;

    /* renamed from: c, reason: collision with root package name */
    private Handler f22874c;

    static class a {

        /* renamed from: a, reason: collision with root package name */
        final String f22875a;

        /* renamed from: b, reason: collision with root package name */
        final String f22876b;

        /* renamed from: c, reason: collision with root package name */
        final long f22877c;

        /* renamed from: d, reason: collision with root package name */
        final int f22878d;

        /* renamed from: e, reason: collision with root package name */
        final int f22879e;

        /* renamed from: f, reason: collision with root package name */
        final long f22880f;

        public a(long j2, int i2, long j3, int i3, String str, String str2) {
            this.f22878d = i3;
            this.f22875a = str;
            this.f22876b = str2;
            this.f22877c = j2;
            this.f22879e = i2;
            this.f22880f = j3;
        }
    }

    class c implements Handler.Callback {

        /* renamed from: b, reason: collision with root package name */
        private long f22886b = 0;

        /* renamed from: c, reason: collision with root package name */
        private PrintWriter f22887c;

        /* renamed from: d, reason: collision with root package name */
        private final DateFormat f22888d;

        /* renamed from: e, reason: collision with root package name */
        private final DateFormat f22889e;

        /* renamed from: f, reason: collision with root package name */
        private final File f22890f;

        /* renamed from: g, reason: collision with root package name */
        private String f22891g;

        c() throws ClassNotFoundException {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CalendarUtils.yyyyMMdd, Locale.getDefault());
            this.f22888d = simpleDateFormat;
            simpleDateFormat.setTimeZone(TimeZone.getDefault());
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());
            this.f22889e = simpleDateFormat2;
            simpleDateFormat2.setTimeZone(TimeZone.getDefault());
            Application applicationA = x.a();
            this.f22890f = new File(applicationA.getFilesDir(), ".upush_log");
            String strA = f.a(applicationA);
            this.f22891g = strA;
            int iLastIndexOf = strA.lastIndexOf(":");
            if (iLastIndexOf < 0) {
                this.f22891g = "";
                return;
            }
            this.f22891g = this.f22891g.substring(iLastIndexOf + 1) + OpenAccountUIConstants.UNDER_LINE;
        }

        private void a() {
            PrintWriter printWriter = this.f22887c;
            if (printWriter != null) {
                try {
                    printWriter.close();
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
            this.f22887c = null;
        }

        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            int i2;
            try {
                i2 = message.what;
            } catch (Throwable th) {
                UPLog.d("Log", UPLog.getStackTrace(th));
            }
            if (i2 != 1) {
                if (i2 != 2) {
                    return true;
                }
                a();
                long jCurrentTimeMillis = (System.currentTimeMillis() / 86400000) * 86400000;
                if (Math.abs(jCurrentTimeMillis - this.f22886b) > 86400000) {
                    this.f22886b = jCurrentTimeMillis;
                    File[] fileArrListFiles = this.f22890f.listFiles();
                    if (fileArrListFiles != null) {
                        for (File file : fileArrListFiles) {
                            long jLastModified = file.lastModified() / 86400000;
                            Long.signum(jLastModified);
                            if (Math.abs(jCurrentTimeMillis - (jLastModified * 86400000)) > 86400000) {
                                file.delete();
                            }
                        }
                    }
                }
                return true;
            }
            try {
                if (!this.f22890f.exists()) {
                    this.f22890f.mkdirs();
                }
                a aVar = (a) message.obj;
                String str = String.format("%s%s", this.f22891g, this.f22888d.format(Long.valueOf(aVar.f22877c)));
                int i3 = aVar.f22878d;
                String str2 = String.format("%s %s-%s %s %s %s", this.f22889e.format(Long.valueOf(aVar.f22877c)), Integer.valueOf(aVar.f22879e), Long.valueOf(aVar.f22880f), i3 == 3 ? "D" : i3 == 4 ? "I" : i3 == 5 ? ExifInterface.LONGITUDE_WEST : i3 == 6 ? ExifInterface.LONGITUDE_EAST : "U", aVar.f22875a, aVar.f22876b);
                File file2 = new File(this.f22890f, str + ".log");
                if (file2.exists() && file2.length() + str2.length() > CacheDataSink.DEFAULT_FRAGMENT_SIZE) {
                    File file3 = new File(this.f22890f, str + ".bak");
                    if (file3.exists()) {
                        file3.delete();
                    }
                    file2.renameTo(file3);
                    file2.delete();
                }
                if (!file2.exists()) {
                    a();
                }
                if (this.f22887c == null) {
                    this.f22887c = new PrintWriter(new FileWriter(file2, true));
                }
                this.f22887c.println(str2);
                this.f22887c.flush();
                p.this.b().removeMessages(2);
                p.this.b().sendEmptyMessageDelayed(2, 5000L);
            } catch (Throwable th2) {
                UPLog.d("Log", UPLog.getStackTrace(th2));
                a();
            }
            return true;
        }
    }

    private void b(int i2, String str, String str2) {
        Handler handlerB = b();
        if (handlerB != null) {
            handlerB.obtainMessage(1, new a(System.currentTimeMillis(), Process.myPid(), Thread.currentThread().getId(), i2, str, str2)).sendToTarget();
        }
    }

    public final boolean a() {
        if (!f.f22836b) {
            return false;
        }
        if (this.f22872a == null) {
            this.f22872a = Boolean.valueOf(MessageSharedPrefs.getInstance(x.a()).l());
        }
        return this.f22872a.booleanValue();
    }

    public final void a(int i2, String str, String str2) {
        if (i2 >= 4 && str2 != null) {
            b(i2, str, str2);
        }
    }

    final Handler b() {
        Handler handler;
        Handler handler2 = this.f22874c;
        if (handler2 != null) {
            return handler2;
        }
        synchronized (this) {
            try {
                if (this.f22874c == null) {
                    try {
                        HandlerThread handlerThread = new HandlerThread("upush_log", 10);
                        handlerThread.start();
                        this.f22874c = new Handler(handlerThread.getLooper(), new c());
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
                handler = this.f22874c;
            } catch (Throwable th2) {
                throw th2;
            }
        }
        return handler;
    }

    public static class b {
        static JSONObject a(String str, String str2, String str3) {
            String zid;
            try {
                Application applicationA = x.a();
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("ts", System.currentTimeMillis());
                jSONObject.put("appkey", str);
                jSONObject.put(RemoteMessageConst.DEVICE_TOKEN, str2);
                jSONObject.put("android_id", str3);
                jSONObject.put("utdid", d.o(applicationA));
                jSONObject.put(com.umeng.analytics.pro.bc.f21408g, d.k(applicationA));
                try {
                    zid = UMUtils.getZid(applicationA);
                    try {
                        jSONObject.put("oaid", DeviceConfig.getOaid(applicationA));
                    } catch (Throwable unused) {
                    }
                } catch (Throwable unused2) {
                    zid = null;
                }
                jSONObject.put(com.umeng.analytics.pro.bc.al, zid);
                jSONObject.put("sdk_v", MsgConstant.SDK_VERSION);
                return g.a(jSONObject, "https://offmsg.umeng.com/log/switch", str, true);
            } catch (Throwable th) {
                UPLog.d("Log", UPLog.getStackTrace(th));
                return null;
            }
        }

        final void a(File file, File file2) {
            File[] fileArrListFiles;
            if (!file.exists() || (fileArrListFiles = file.listFiles(new FileFilter() { // from class: com.umeng.message.proguard.p.b.2
                @Override // java.io.FileFilter
                public final boolean accept(File file3) {
                    if (file3.isDirectory() || file3.length() > CacheDataSink.DEFAULT_FRAGMENT_SIZE) {
                        return false;
                    }
                    String name = file3.getName();
                    return name.endsWith(".log") || name.endsWith(".bak");
                }
            })) == null || fileArrListFiles.length == 0) {
                return;
            }
            if (fileArrListFiles.length > 8) {
                File[] fileArr = new File[8];
                System.arraycopy(fileArrListFiles, 0, fileArr, 0, 8);
                fileArrListFiles = fileArr;
            }
            ZipOutputStream zipOutputStream = null;
            try {
                ZipOutputStream zipOutputStream2 = new ZipOutputStream(new FileOutputStream(file2));
                try {
                    for (File file3 : fileArrListFiles) {
                        a(file3, zipOutputStream2);
                        file3.delete();
                    }
                    zipOutputStream2.finish();
                    f.a(zipOutputStream2);
                } catch (Throwable th) {
                    th = th;
                    zipOutputStream = zipOutputStream2;
                    try {
                        UPLog.d("Log", UPLog.getStackTrace(th));
                    } finally {
                        f.a(zipOutputStream);
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }

        private static void a(File file, ZipOutputStream zipOutputStream) {
            FileInputStream fileInputStream;
            if (!file.exists() || file.length() < 256) {
                return;
            }
            FileInputStream fileInputStream2 = null;
            try {
                fileInputStream = new FileInputStream(file);
            } catch (Throwable th) {
                th = th;
            }
            try {
                zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
                byte[] bArr = new byte[8192];
                while (true) {
                    int i2 = fileInputStream.read(bArr);
                    if (i2 != -1) {
                        zipOutputStream.write(bArr, 0, i2);
                    } else {
                        zipOutputStream.closeEntry();
                        f.a(fileInputStream);
                        return;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                fileInputStream2 = fileInputStream;
                try {
                    UPLog.d("Log", UPLog.getStackTrace(th));
                } finally {
                    f.a(fileInputStream2);
                }
            }
        }
    }
}
