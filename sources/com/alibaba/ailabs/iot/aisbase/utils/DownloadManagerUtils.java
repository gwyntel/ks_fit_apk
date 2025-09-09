package com.alibaba.ailabs.iot.aisbase.utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.ArrayMap;
import com.alibaba.ailabs.iot.aisbase.callback.OnDownLoadStateListener;
import com.alibaba.ailabs.tg.utils.FileUtils;
import com.alibaba.ailabs.tg.utils.LogUtils;
import com.google.android.gms.stats.CodePackage;
import java.io.File;
import java.net.URI;

/* loaded from: classes2.dex */
public class DownloadManagerUtils {

    /* renamed from: a, reason: collision with root package name */
    public static final String f8576a = "DownloadManagerUtils";

    /* renamed from: b, reason: collision with root package name */
    public static DownloadManagerUtils f8577b;

    /* renamed from: c, reason: collision with root package name */
    public DownloadManager f8578c;

    /* renamed from: d, reason: collision with root package name */
    public ArrayMap<Long, OnDownLoadStateListener> f8579d = new ArrayMap<>();

    /* renamed from: e, reason: collision with root package name */
    public Context f8580e;

    public static class DownloadTaskDetails {
        public int downloadedSize;
        public int totalSize;
    }

    class a extends BroadcastReceiver {
        public a() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            long longExtra = intent.getLongExtra("extra_download_id", -1L);
            OnDownLoadStateListener onDownLoadStateListener = (OnDownLoadStateListener) DownloadManagerUtils.this.f8579d.get(Long.valueOf(longExtra));
            if (onDownLoadStateListener != null) {
                Cursor cursorQuery = DownloadManagerUtils.this.f8578c.query(new DownloadManager.Query().setFilterById(longExtra));
                if (cursorQuery == null || !cursorQuery.moveToFirst()) {
                    return;
                }
                String string = cursorQuery.getString(cursorQuery.getColumnIndex("local_uri"));
                cursorQuery.close();
                LogUtils.d(DownloadManagerUtils.f8576a, "filePath = " + string);
                TextUtils.isEmpty(string);
                try {
                    File file = new File(new URI(string));
                    if (file.exists()) {
                        onDownLoadStateListener.downLoadStateCallback(file.getAbsolutePath());
                    }
                } catch (Exception e2) {
                    LogUtils.e(DownloadManagerUtils.f8576a, e2.toString());
                }
            }
        }
    }

    public DownloadManagerUtils(Context context) {
        this.f8580e = context;
        this.f8578c = (DownloadManager) context.getSystemService("download");
        this.f8580e.registerReceiver(new a(), new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
    }

    public static DownloadManagerUtils getInstance(Context context) {
        if (f8577b == null) {
            synchronized (DownloadManagerUtils.class) {
                try {
                    if (f8577b == null) {
                        f8577b = new DownloadManagerUtils(context);
                    }
                } finally {
                }
            }
        }
        return f8577b;
    }

    public int cancelDownload(long j2) {
        DownloadManager downloadManager = this.f8578c;
        if (downloadManager != null) {
            return downloadManager.remove(j2);
        }
        return 0;
    }

    public long downloadFile(String str, String str2, String str3, OnDownLoadStateListener onDownLoadStateListener) {
        if (TextUtils.isEmpty(str3)) {
            str3 = FileUtils.getExternalPath(this.f8580e, CodePackage.OTA);
        }
        String strCheckFileInDirWithMd5 = FileUtils.checkFileInDirWithMd5(str3, str2);
        String str4 = f8576a;
        LogUtils.i(str4, "ota url->" + str + "\nota dm5->" + str2 + "\nota file path->" + strCheckFileInDirWithMd5);
        if (!TextUtils.isEmpty(strCheckFileInDirWithMd5)) {
            if (onDownLoadStateListener == null) {
                return 0L;
            }
            onDownLoadStateListener.downLoadStateCallback(strCheckFileInDirWithMd5);
            return 0L;
        }
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(str));
        File file = new File(str3, System.currentTimeMillis() + ".bin");
        if (!FileUtils.createOrExistsFile(file)) {
            LogUtils.e(str4, "failed to create file");
            return -1L;
        }
        request.setDestinationUri(Uri.fromFile(file));
        long jEnqueue = this.f8578c.enqueue(request);
        if (onDownLoadStateListener != null) {
            this.f8579d.put(Long.valueOf(jEnqueue), onDownLoadStateListener);
        }
        LogUtils.d(str4, "download ID: " + jEnqueue);
        return jEnqueue;
    }

    public DownloadTaskDetails getDownloadDetails(long j2) {
        Cursor cursorQuery = this.f8578c.query(new DownloadManager.Query().setFilterById(j2));
        if (cursorQuery == null || !cursorQuery.moveToFirst()) {
            return null;
        }
        DownloadTaskDetails downloadTaskDetails = new DownloadTaskDetails();
        int i2 = cursorQuery.getInt(cursorQuery.getColumnIndex("bytes_so_far"));
        int i3 = cursorQuery.getInt(cursorQuery.getColumnIndex("total_size"));
        cursorQuery.close();
        LogUtils.d(f8576a, "download task details, downloadedSize: " + i2 + ", totalSize: " + i3);
        downloadTaskDetails.downloadedSize = i2;
        downloadTaskDetails.totalSize = i3;
        return downloadTaskDetails;
    }

    public int getTotalSize(long j2) {
        Cursor cursorQuery = this.f8578c.query(new DownloadManager.Query().setFilterById(j2));
        if (cursorQuery == null || !cursorQuery.moveToFirst()) {
            return 0;
        }
        return cursorQuery.getInt(cursorQuery.getColumnIndex("total_size"));
    }

    public int queryDownloadedBytes(long j2) {
        Cursor cursorQuery = this.f8578c.query(new DownloadManager.Query().setFilterById(j2));
        if (cursorQuery == null || !cursorQuery.moveToFirst()) {
            return 0;
        }
        int i2 = cursorQuery.getInt(cursorQuery.getColumnIndex("bytes_so_far"));
        cursorQuery.getInt(cursorQuery.getColumnIndex("total_size"));
        return i2;
    }

    public int validDownload(long j2) {
        Cursor cursorQuery = this.f8578c.query(new DownloadManager.Query().setFilterById(j2));
        try {
            if (!cursorQuery.moveToFirst()) {
                cursorQuery.close();
                return -1;
            }
            if (cursorQuery.getInt(cursorQuery.getColumnIndex("status")) == 8) {
                return 0;
            }
            return cursorQuery.getInt(cursorQuery.getColumnIndex("reason"));
        } finally {
            cursorQuery.close();
        }
    }
}
