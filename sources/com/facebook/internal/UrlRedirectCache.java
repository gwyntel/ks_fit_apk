package com.facebook.internal;

import android.net.Uri;
import com.facebook.LoggingBehavior;
import com.facebook.internal.FileLruCache;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/* loaded from: classes3.dex */
class UrlRedirectCache {
    private static final String REDIRECT_CONTENT_TAG = UrlRedirectCache.class.getSimpleName() + "_Redirect";
    static final String TAG = "UrlRedirectCache";
    private static FileLruCache urlRedirectCache;

    UrlRedirectCache() {
    }

    static void cacheUriRedirect(Uri uri, Uri uri2) throws IOException {
        if (uri == null || uri2 == null) {
            return;
        }
        OutputStream outputStreamOpenPutStream = null;
        try {
            outputStreamOpenPutStream = getCache().openPutStream(uri.toString(), REDIRECT_CONTENT_TAG);
            outputStreamOpenPutStream.write(uri2.toString().getBytes());
        } catch (IOException unused) {
        } catch (Throwable th) {
            Utility.closeQuietly(outputStreamOpenPutStream);
            throw th;
        }
        Utility.closeQuietly(outputStreamOpenPutStream);
    }

    static void clearCache() {
        try {
            getCache().clearCache();
        } catch (IOException e2) {
            Logger.log(LoggingBehavior.CACHE, 5, TAG, "clearCache failed " + e2.getMessage());
        }
    }

    static synchronized FileLruCache getCache() throws IOException {
        try {
            if (urlRedirectCache == null) {
                urlRedirectCache = new FileLruCache(TAG, new FileLruCache.Limits());
            }
        } catch (Throwable th) {
            throw th;
        }
        return urlRedirectCache;
    }

    static Uri getRedirectedUri(Uri uri) throws Throwable {
        Throwable th;
        InputStreamReader inputStreamReader;
        InputStreamReader inputStreamReader2;
        FileLruCache cache;
        boolean z2;
        if (uri == null) {
            return null;
        }
        String string = uri.toString();
        try {
            cache = getCache();
            inputStreamReader2 = null;
            z2 = false;
        } catch (IOException unused) {
            inputStreamReader2 = null;
        } catch (Throwable th2) {
            th = th2;
            inputStreamReader = null;
        }
        while (true) {
            try {
                InputStream inputStream = cache.get(string, REDIRECT_CONTENT_TAG);
                if (inputStream == null) {
                    break;
                }
                inputStreamReader = new InputStreamReader(inputStream);
                try {
                    char[] cArr = new char[128];
                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        int i2 = inputStreamReader.read(cArr, 0, 128);
                        if (i2 <= 0) {
                            break;
                        }
                        sb.append(cArr, 0, i2);
                    }
                    Utility.closeQuietly(inputStreamReader);
                    inputStreamReader2 = inputStreamReader;
                    string = sb.toString();
                    z2 = true;
                } catch (IOException unused2) {
                    inputStreamReader2 = inputStreamReader;
                } catch (Throwable th3) {
                    th = th3;
                    Utility.closeQuietly(inputStreamReader);
                    throw th;
                }
            } catch (IOException unused3) {
            } catch (Throwable th4) {
                th = th4;
                inputStreamReader = inputStreamReader2;
            }
            Utility.closeQuietly(inputStreamReader2);
            return null;
        }
        if (z2) {
            Uri uri2 = Uri.parse(string);
            Utility.closeQuietly(inputStreamReader2);
            return uri2;
        }
        Utility.closeQuietly(inputStreamReader2);
        return null;
    }
}
