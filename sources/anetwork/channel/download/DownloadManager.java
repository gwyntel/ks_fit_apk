package anetwork.channel.download;

import android.content.Context;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.SparseArray;
import anet.channel.util.ALog;
import anet.channel.util.HttpHelper;
import anet.channel.util.StringUtils;
import anetwork.channel.Header;
import anetwork.channel.aidl.Connection;
import anetwork.channel.http.NetworkSdkSetting;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.io.IOUtils;

/* loaded from: classes2.dex */
public class DownloadManager {
    public static final int ERROR_DOWNLOAD_CANCELLED = -105;
    public static final int ERROR_EXCEPTION_HAPPEN = -104;
    public static final int ERROR_FILE_FOLDER_INVALID = -101;
    public static final int ERROR_FILE_RENAME_FAILED = -106;
    public static final int ERROR_IO_EXCEPTION = -103;
    public static final int ERROR_REQUEST_FAIL = -102;
    public static final int ERROR_URL_INVALID = -100;
    public static final String TAG = "anet.DownloadManager";

    /* renamed from: a, reason: collision with root package name */
    SparseArray<b> f7181a;

    /* renamed from: b, reason: collision with root package name */
    AtomicInteger f7182b;

    /* renamed from: c, reason: collision with root package name */
    ThreadPoolExecutor f7183c;

    /* renamed from: d, reason: collision with root package name */
    Context f7184d;

    public interface DownloadListener {
        void onFail(int i2, int i3, String str);

        void onProgress(int i2, long j2, long j3);

        void onSuccess(int i2, String str);
    }

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        static DownloadManager f7185a = new DownloadManager();

        private a() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String b(String str) {
        StringBuilder sb = new StringBuilder(32);
        sb.append(this.f7184d.getExternalFilesDir(null));
        sb.append("/");
        sb.append("downloads");
        sb.append("/");
        sb.append(str);
        return sb.toString();
    }

    public static DownloadManager getInstance() {
        return a.f7185a;
    }

    public void cancel(int i2) {
        synchronized (this.f7181a) {
            try {
                b bVar = this.f7181a.get(i2);
                if (bVar != null) {
                    if (ALog.isPrintLog(2)) {
                        ALog.i(TAG, "try cancel task" + i2 + " url=" + bVar.f7187b.toString(), null, new Object[0]);
                    }
                    this.f7181a.remove(i2);
                    bVar.a();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public int enqueue(String str, String str2, DownloadListener downloadListener) {
        return enqueue(str, null, str2, downloadListener);
    }

    class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final int f7186a;

        /* renamed from: b, reason: collision with root package name */
        final URL f7187b;

        /* renamed from: d, reason: collision with root package name */
        private final String f7189d;

        /* renamed from: e, reason: collision with root package name */
        private final CopyOnWriteArrayList<DownloadListener> f7190e;

        /* renamed from: f, reason: collision with root package name */
        private final AtomicBoolean f7191f = new AtomicBoolean(false);

        /* renamed from: g, reason: collision with root package name */
        private final AtomicBoolean f7192g = new AtomicBoolean(false);

        /* renamed from: h, reason: collision with root package name */
        private volatile Connection f7193h = null;

        /* renamed from: i, reason: collision with root package name */
        private boolean f7194i;

        b(URL url, String str, String str2, DownloadListener downloadListener) {
            this.f7194i = true;
            this.f7186a = DownloadManager.this.f7182b.getAndIncrement();
            this.f7187b = url;
            str2 = TextUtils.isEmpty(str2) ? a(url) : str2;
            if (TextUtils.isEmpty(str)) {
                this.f7189d = DownloadManager.this.b(str2);
            } else {
                if (str.endsWith("/")) {
                    this.f7189d = str + str2;
                } else {
                    this.f7189d = str + IOUtils.DIR_SEPARATOR_UNIX + str2;
                }
                if (str.startsWith("/data/user") || str.startsWith("/data/data")) {
                    this.f7194i = false;
                }
            }
            CopyOnWriteArrayList<DownloadListener> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
            this.f7190e = copyOnWriteArrayList;
            copyOnWriteArrayList.add(downloadListener);
        }

        public boolean a(DownloadListener downloadListener) {
            if (this.f7192g.get()) {
                return false;
            }
            this.f7190e.add(downloadListener);
            return true;
        }

        /* JADX WARN: Removed duplicated region for block: B:186:0x026c A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:192:0x0267 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:216:0x0262 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:218:0x0274 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:222:0x024a A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:236:0x0245 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:238:0x0252 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:240:0x0240 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() throws java.lang.Throwable {
            /*
                Method dump skipped, instructions count: 642
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: anetwork.channel.download.DownloadManager.b.run():void");
        }

        public void a() {
            this.f7191f.set(true);
            a(-105, "download canceled.");
            if (this.f7193h != null) {
                try {
                    this.f7193h.cancel();
                } catch (RemoteException unused) {
                }
            }
        }

        private void a(String str) {
            if (this.f7192g.compareAndSet(false, true)) {
                Iterator<DownloadListener> it = this.f7190e.iterator();
                while (it.hasNext()) {
                    it.next().onSuccess(this.f7186a, str);
                }
            }
        }

        private void a(int i2, String str) {
            if (this.f7192g.compareAndSet(false, true)) {
                Iterator<DownloadListener> it = this.f7190e.iterator();
                while (it.hasNext()) {
                    it.next().onFail(this.f7186a, i2, str);
                }
            }
        }

        private void a(long j2, long j3) {
            if (this.f7192g.get()) {
                return;
            }
            Iterator<DownloadListener> it = this.f7190e.iterator();
            while (it.hasNext()) {
                it.next().onProgress(this.f7186a, j2, j3);
            }
        }

        private long a(int i2, Map<String, List<String>> map, long j2) {
            int iLastIndexOf;
            try {
                if (i2 == 200) {
                    return Long.parseLong(HttpHelper.getSingleHeaderFieldByKey(map, "Content-Length"));
                }
                if (i2 != 206) {
                    return 0L;
                }
                String singleHeaderFieldByKey = HttpHelper.getSingleHeaderFieldByKey(map, "Content-Range");
                long j3 = (singleHeaderFieldByKey == null || (iLastIndexOf = singleHeaderFieldByKey.lastIndexOf(47)) == -1) ? 0L : Long.parseLong(singleHeaderFieldByKey.substring(iLastIndexOf + 1));
                if (j3 == 0) {
                    try {
                        return Long.parseLong(HttpHelper.getSingleHeaderFieldByKey(map, "Content-Length")) + j2;
                    } catch (Exception unused) {
                    }
                }
                return j3;
            } catch (Exception unused2) {
                return 0L;
            }
        }

        private void a(List<Header> list) {
            if (list != null) {
                ListIterator<Header> listIterator = list.listIterator();
                while (listIterator.hasNext()) {
                    if ("Range".equalsIgnoreCase(listIterator.next().getName())) {
                        listIterator.remove();
                        return;
                    }
                }
            }
        }

        private String a(URL url) {
            String path = url.getPath();
            int iLastIndexOf = path.lastIndexOf(47);
            String strSubstring = iLastIndexOf != -1 ? path.substring(iLastIndexOf + 1, path.length()) : null;
            if (!TextUtils.isEmpty(strSubstring)) {
                return strSubstring;
            }
            String strMd5ToHex = StringUtils.md5ToHex(url.toString());
            return strMd5ToHex == null ? url.getFile() : strMd5ToHex;
        }
    }

    private DownloadManager() {
        this.f7181a = new SparseArray<>(6);
        this.f7182b = new AtomicInteger(0);
        this.f7183c = new ThreadPoolExecutor(2, 2, 30L, TimeUnit.SECONDS, new LinkedBlockingDeque());
        this.f7184d = null;
        this.f7184d = NetworkSdkSetting.getContext();
        this.f7183c.allowCoreThreadTimeOut(true);
        a();
    }

    public int enqueue(String str, String str2, String str3, DownloadListener downloadListener) {
        int i2 = 0;
        if (ALog.isPrintLog(2)) {
            ALog.i(TAG, "enqueue", null, "folder", str2, "filename", str3, "url", str);
        }
        if (this.f7184d == null) {
            ALog.e(TAG, "network sdk not initialized.", null, new Object[0]);
            return -1;
        }
        try {
            URL url = new URL(str);
            if (!TextUtils.isEmpty(str2) && !a(str2)) {
                ALog.e(TAG, "file folder invalid.", null, new Object[0]);
                if (downloadListener != null) {
                    downloadListener.onFail(-1, -101, "file folder path invalid");
                }
                return -1;
            }
            synchronized (this.f7181a) {
                try {
                    int size = this.f7181a.size();
                    while (true) {
                        if (i2 >= size) {
                            break;
                        }
                        b bVarValueAt = this.f7181a.valueAt(i2);
                        if (!url.equals(bVarValueAt.f7187b)) {
                            i2++;
                        } else if (bVarValueAt.a(downloadListener)) {
                            return bVarValueAt.f7186a;
                        }
                    }
                    b bVar = new b(url, str2, str3, downloadListener);
                    this.f7181a.put(bVar.f7186a, bVar);
                    this.f7183c.submit(bVar);
                    return bVar.f7186a;
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (MalformedURLException e2) {
            ALog.e(TAG, "url invalid.", null, e2, new Object[0]);
            if (downloadListener != null) {
                downloadListener.onFail(-1, -100, "url invalid");
            }
            return -1;
        }
    }

    private void a() {
        if (this.f7184d != null) {
            File file = new File(this.f7184d.getExternalFilesDir(null), "downloads");
            if (file.exists()) {
                return;
            }
            file.mkdir();
        }
    }

    private boolean a(String str) {
        if (this.f7184d != null) {
            try {
                File file = new File(str);
                if (file.exists()) {
                    return true;
                }
                return file.mkdir();
            } catch (Exception unused) {
                ALog.e(TAG, "create folder failed", null, "folder", str);
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public File a(String str, boolean z2) {
        String strMd5ToHex = StringUtils.md5ToHex(str);
        if (strMd5ToHex != null) {
            str = strMd5ToHex;
        }
        if (z2) {
            return new File(this.f7184d.getExternalCacheDir(), str);
        }
        return new File(this.f7184d.getCacheDir(), str);
    }
}
