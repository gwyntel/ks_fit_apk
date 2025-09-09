package com.aliyun.downloader;

import android.content.Context;

/* loaded from: classes2.dex */
public class AliDownloaderFactory {
    public static AliMediaDownloader create(Context context) {
        return new ApsaraDownloader(context.getApplicationContext());
    }

    public static int deleteFile(String str, String str2, String str3, int i2) {
        return AliMediaDownloader.deleteFile(str, str2, str3, i2);
    }
}
