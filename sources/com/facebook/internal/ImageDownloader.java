package com.facebook.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.facebook.internal.ImageRequest;
import com.facebook.internal.WorkQueue;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class ImageDownloader {
    private static final int CACHE_READ_QUEUE_MAX_CONCURRENT = 2;
    private static final int DOWNLOAD_QUEUE_MAX_CONCURRENT = 8;
    private static Handler handler;
    private static WorkQueue downloadQueue = new WorkQueue(8);
    private static WorkQueue cacheReadQueue = new WorkQueue(2);
    private static final Map<RequestKey, DownloaderContext> pendingRequests = new HashMap();

    private static class CacheReadWorkItem implements Runnable {
        private boolean allowCachedRedirects;
        private Context context;
        private RequestKey key;

        CacheReadWorkItem(Context context, RequestKey requestKey, boolean z2) {
            this.context = context;
            this.key = requestKey;
            this.allowCachedRedirects = z2;
        }

        @Override // java.lang.Runnable
        public void run() throws IOException {
            ImageDownloader.readFromCache(this.key, this.context, this.allowCachedRedirects);
        }
    }

    private static class DownloadImageWorkItem implements Runnable {
        private Context context;
        private RequestKey key;

        DownloadImageWorkItem(Context context, RequestKey requestKey) {
            this.context = context;
            this.key = requestKey;
        }

        @Override // java.lang.Runnable
        public void run() throws Throwable {
            ImageDownloader.download(this.key, this.context);
        }
    }

    private static class DownloaderContext {
        boolean isCancelled;
        ImageRequest request;
        WorkQueue.WorkItem workItem;

        private DownloaderContext() {
        }
    }

    private static class RequestKey {
        private static final int HASH_MULTIPLIER = 37;
        private static final int HASH_SEED = 29;
        Object tag;
        Uri uri;

        RequestKey(Uri uri, Object obj) {
            this.uri = uri;
            this.tag = obj;
        }

        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof RequestKey)) {
                return false;
            }
            RequestKey requestKey = (RequestKey) obj;
            return requestKey.uri == this.uri && requestKey.tag == this.tag;
        }

        public int hashCode() {
            return ((1073 + this.uri.hashCode()) * 37) + this.tag.hashCode();
        }
    }

    public static boolean cancelRequest(ImageRequest imageRequest) {
        boolean z2;
        RequestKey requestKey = new RequestKey(imageRequest.getImageUri(), imageRequest.getCallerTag());
        Map<RequestKey, DownloaderContext> map = pendingRequests;
        synchronized (map) {
            try {
                DownloaderContext downloaderContext = map.get(requestKey);
                if (downloaderContext != null) {
                    z2 = true;
                    if (downloaderContext.workItem.cancel()) {
                        map.remove(requestKey);
                    } else {
                        downloaderContext.isCancelled = true;
                    }
                } else {
                    z2 = false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return z2;
    }

    public static void clearCache(Context context) {
        ImageResponseCache.clearCache(context);
        UrlRedirectCache.clearCache();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:62:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v12 */
    /* JADX WARN: Type inference failed for: r10v2, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r10v3 */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void download(com.facebook.internal.ImageDownloader.RequestKey r9, android.content.Context r10) throws java.lang.Throwable {
        /*
            r0 = 0
            r1 = 0
            r2 = 1
            java.net.URL r3 = new java.net.URL     // Catch: java.lang.Throwable -> Lab java.io.IOException -> Lae
            android.net.Uri r4 = r9.uri     // Catch: java.lang.Throwable -> Lab java.io.IOException -> Lae
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> Lab java.io.IOException -> Lae
            r3.<init>(r4)     // Catch: java.lang.Throwable -> Lab java.io.IOException -> Lae
            java.net.URLConnection r3 = r3.openConnection()     // Catch: java.lang.Throwable -> Lab java.io.IOException -> Lae
            java.net.HttpURLConnection r3 = (java.net.HttpURLConnection) r3     // Catch: java.lang.Throwable -> Lab java.io.IOException -> Lae
            r3.setInstanceFollowRedirects(r0)     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L63
            int r4 = r3.getResponseCode()     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L63
            r5 = 200(0xc8, float:2.8E-43)
            if (r4 == r5) goto L99
            r10 = 301(0x12d, float:4.22E-43)
            if (r4 == r10) goto L66
            r10 = 302(0x12e, float:4.23E-43)
            if (r4 == r10) goto L66
            java.io.InputStream r10 = r3.getErrorStream()     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L63
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L45 java.io.IOException -> L49
            r4.<init>()     // Catch: java.lang.Throwable -> L45 java.io.IOException -> L49
            if (r10 == 0) goto L50
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L45 java.io.IOException -> L49
            r5.<init>(r10)     // Catch: java.lang.Throwable -> L45 java.io.IOException -> L49
            r6 = 128(0x80, float:1.8E-43)
            char[] r7 = new char[r6]     // Catch: java.lang.Throwable -> L45 java.io.IOException -> L49
        L3b:
            int r8 = r5.read(r7, r0, r6)     // Catch: java.lang.Throwable -> L45 java.io.IOException -> L49
            if (r8 <= 0) goto L4c
            r4.append(r7, r0, r8)     // Catch: java.lang.Throwable -> L45 java.io.IOException -> L49
            goto L3b
        L45:
            r9 = move-exception
            r1 = r10
            goto Lb2
        L49:
            r4 = move-exception
            goto Lb9
        L4c:
            com.facebook.internal.Utility.closeQuietly(r5)     // Catch: java.lang.Throwable -> L45 java.io.IOException -> L49
            goto L55
        L50:
            java.lang.String r5 = "Unexpected error while downloading an image."
            r4.append(r5)     // Catch: java.lang.Throwable -> L45 java.io.IOException -> L49
        L55:
            com.facebook.FacebookException r5 = new com.facebook.FacebookException     // Catch: java.lang.Throwable -> L45 java.io.IOException -> L49
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L45 java.io.IOException -> L49
            r5.<init>(r4)     // Catch: java.lang.Throwable -> L45 java.io.IOException -> L49
            r4 = r1
        L5f:
            r1 = r10
            goto La3
        L61:
            r9 = move-exception
            goto Lb2
        L63:
            r4 = move-exception
        L64:
            r10 = r1
            goto Lb9
        L66:
            java.lang.String r10 = "location"
            java.lang.String r10 = r3.getHeaderField(r10)     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L92
            boolean r2 = com.facebook.internal.Utility.isNullOrEmpty(r10)     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L92
            if (r2 != 0) goto L95
            android.net.Uri r10 = android.net.Uri.parse(r10)     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L92
            android.net.Uri r2 = r9.uri     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L92
            com.facebook.internal.UrlRedirectCache.cacheUriRedirect(r2, r10)     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L92
            com.facebook.internal.ImageDownloader$DownloaderContext r2 = removePendingRequest(r9)     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L92
            if (r2 == 0) goto L95
            boolean r4 = r2.isCancelled     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L92
            if (r4 != 0) goto L95
            com.facebook.internal.ImageRequest r2 = r2.request     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L92
            com.facebook.internal.ImageDownloader$RequestKey r4 = new com.facebook.internal.ImageDownloader$RequestKey     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L92
            java.lang.Object r5 = r9.tag     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L92
            r4.<init>(r10, r5)     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L92
            enqueueCacheRead(r2, r4, r0)     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L92
            goto L95
        L92:
            r4 = move-exception
            r2 = r0
            goto L64
        L95:
            r2 = r0
            r4 = r1
            r5 = r4
            goto La3
        L99:
            java.io.InputStream r10 = com.facebook.internal.ImageResponseCache.interceptAndCacheImageStream(r10, r3)     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L63
            android.graphics.Bitmap r4 = android.graphics.BitmapFactory.decodeStream(r10)     // Catch: java.lang.Throwable -> L45 java.io.IOException -> L49
            r5 = r1
            goto L5f
        La3:
            com.facebook.internal.Utility.closeQuietly(r1)
            com.facebook.internal.Utility.disconnectQuietly(r3)
            r1 = r4
            goto Lc0
        Lab:
            r9 = move-exception
            r3 = r1
            goto Lb2
        Lae:
            r4 = move-exception
            r10 = r1
            r3 = r10
            goto Lb9
        Lb2:
            com.facebook.internal.Utility.closeQuietly(r1)
            com.facebook.internal.Utility.disconnectQuietly(r3)
            throw r9
        Lb9:
            com.facebook.internal.Utility.closeQuietly(r10)
            com.facebook.internal.Utility.disconnectQuietly(r3)
            r5 = r4
        Lc0:
            if (r2 == 0) goto Lc5
            issueResponse(r9, r5, r1, r0)
        Lc5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.ImageDownloader.download(com.facebook.internal.ImageDownloader$RequestKey, android.content.Context):void");
    }

    public static void downloadAsync(ImageRequest imageRequest) {
        if (imageRequest == null) {
            return;
        }
        RequestKey requestKey = new RequestKey(imageRequest.getImageUri(), imageRequest.getCallerTag());
        Map<RequestKey, DownloaderContext> map = pendingRequests;
        synchronized (map) {
            try {
                DownloaderContext downloaderContext = map.get(requestKey);
                if (downloaderContext != null) {
                    downloaderContext.request = imageRequest;
                    downloaderContext.isCancelled = false;
                    downloaderContext.workItem.moveToFront();
                } else {
                    enqueueCacheRead(imageRequest, requestKey, imageRequest.isCachedRedirectAllowed());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private static void enqueueCacheRead(ImageRequest imageRequest, RequestKey requestKey, boolean z2) {
        enqueueRequest(imageRequest, requestKey, cacheReadQueue, new CacheReadWorkItem(imageRequest.getContext(), requestKey, z2));
    }

    private static void enqueueDownload(ImageRequest imageRequest, RequestKey requestKey) {
        enqueueRequest(imageRequest, requestKey, downloadQueue, new DownloadImageWorkItem(imageRequest.getContext(), requestKey));
    }

    private static void enqueueRequest(ImageRequest imageRequest, RequestKey requestKey, WorkQueue workQueue, Runnable runnable) {
        Map<RequestKey, DownloaderContext> map = pendingRequests;
        synchronized (map) {
            DownloaderContext downloaderContext = new DownloaderContext();
            downloaderContext.request = imageRequest;
            map.put(requestKey, downloaderContext);
            downloaderContext.workItem = workQueue.addActiveWorkItem(runnable);
        }
    }

    private static synchronized Handler getHandler() {
        try {
            if (handler == null) {
                handler = new Handler(Looper.getMainLooper());
            }
        } catch (Throwable th) {
            throw th;
        }
        return handler;
    }

    private static void issueResponse(RequestKey requestKey, final Exception exc, final Bitmap bitmap, final boolean z2) {
        final ImageRequest imageRequest;
        final ImageRequest.Callback callback;
        DownloaderContext downloaderContextRemovePendingRequest = removePendingRequest(requestKey);
        if (downloaderContextRemovePendingRequest == null || downloaderContextRemovePendingRequest.isCancelled || (callback = (imageRequest = downloaderContextRemovePendingRequest.request).getCallback()) == null) {
            return;
        }
        getHandler().post(new Runnable() { // from class: com.facebook.internal.ImageDownloader.1
            @Override // java.lang.Runnable
            public void run() {
                callback.onCompleted(new ImageResponse(imageRequest, exc, z2, bitmap));
            }
        });
    }

    public static void prioritizeRequest(ImageRequest imageRequest) {
        RequestKey requestKey = new RequestKey(imageRequest.getImageUri(), imageRequest.getCallerTag());
        Map<RequestKey, DownloaderContext> map = pendingRequests;
        synchronized (map) {
            try {
                DownloaderContext downloaderContext = map.get(requestKey);
                if (downloaderContext != null) {
                    downloaderContext.workItem.moveToFront();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void readFromCache(RequestKey requestKey, Context context, boolean z2) throws IOException {
        InputStream cachedImageStream;
        Uri redirectedUri;
        boolean z3 = false;
        if (!z2 || (redirectedUri = UrlRedirectCache.getRedirectedUri(requestKey.uri)) == null) {
            cachedImageStream = null;
        } else {
            cachedImageStream = ImageResponseCache.getCachedImageStream(redirectedUri, context);
            if (cachedImageStream != null) {
                z3 = true;
            }
        }
        if (!z3) {
            cachedImageStream = ImageResponseCache.getCachedImageStream(requestKey.uri, context);
        }
        if (cachedImageStream != null) {
            Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(cachedImageStream);
            Utility.closeQuietly(cachedImageStream);
            issueResponse(requestKey, null, bitmapDecodeStream, z3);
        } else {
            DownloaderContext downloaderContextRemovePendingRequest = removePendingRequest(requestKey);
            if (downloaderContextRemovePendingRequest == null || downloaderContextRemovePendingRequest.isCancelled) {
                return;
            }
            enqueueDownload(downloaderContextRemovePendingRequest.request, requestKey);
        }
    }

    private static DownloaderContext removePendingRequest(RequestKey requestKey) {
        DownloaderContext downloaderContextRemove;
        Map<RequestKey, DownloaderContext> map = pendingRequests;
        synchronized (map) {
            downloaderContextRemove = map.remove(requestKey);
        }
        return downloaderContextRemove;
    }
}
