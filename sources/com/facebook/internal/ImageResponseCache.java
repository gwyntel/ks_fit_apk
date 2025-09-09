package com.facebook.internal;

import android.content.Context;
import android.net.Uri;
import com.facebook.LoggingBehavior;
import com.facebook.internal.FileLruCache;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/* loaded from: classes3.dex */
class ImageResponseCache {
    static final String TAG = "ImageResponseCache";
    private static FileLruCache imageCache;

    private static class BufferedHttpInputStream extends BufferedInputStream {
        HttpURLConnection connection;

        BufferedHttpInputStream(InputStream inputStream, HttpURLConnection httpURLConnection) {
            super(inputStream, 8192);
            this.connection = httpURLConnection;
        }

        @Override // java.io.BufferedInputStream, java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            super.close();
            Utility.disconnectQuietly(this.connection);
        }
    }

    ImageResponseCache() {
    }

    static void clearCache(Context context) {
        try {
            getCache(context).clearCache();
        } catch (IOException e2) {
            Logger.log(LoggingBehavior.CACHE, 5, TAG, "clearCache failed " + e2.getMessage());
        }
    }

    static synchronized FileLruCache getCache(Context context) throws IOException {
        try {
            if (imageCache == null) {
                imageCache = new FileLruCache(TAG, new FileLruCache.Limits());
            }
        } catch (Throwable th) {
            throw th;
        }
        return imageCache;
    }

    static InputStream getCachedImageStream(Uri uri, Context context) {
        if (uri != null && isCDNURL(uri)) {
            try {
                return getCache(context).get(uri.toString());
            } catch (IOException e2) {
                Logger.log(LoggingBehavior.CACHE, 5, TAG, e2.toString());
            }
        }
        return null;
    }

    static InputStream interceptAndCacheImageStream(Context context, HttpURLConnection httpURLConnection) throws IOException {
        if (httpURLConnection.getResponseCode() != 200) {
            return null;
        }
        Uri uri = Uri.parse(httpURLConnection.getURL().toString());
        InputStream inputStream = httpURLConnection.getInputStream();
        try {
            return isCDNURL(uri) ? getCache(context).interceptAndPut(uri.toString(), new BufferedHttpInputStream(inputStream, httpURLConnection)) : inputStream;
        } catch (IOException unused) {
            return inputStream;
        }
    }

    private static boolean isCDNURL(Uri uri) {
        if (uri == null) {
            return false;
        }
        String host = uri.getHost();
        if (host.endsWith("fbcdn.net")) {
            return true;
        }
        return host.startsWith("fbcdn") && host.endsWith("akamaihd.net");
    }
}
