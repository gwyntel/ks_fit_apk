package com.squareup.okhttp.internal.http;

import com.google.common.net.HttpHeaders;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public final class CacheStrategy {
    public final Response cacheResponse;
    public final Request networkRequest;

    public static class Factory {

        /* renamed from: a, reason: collision with root package name */
        final long f19984a;
        private int ageSeconds;

        /* renamed from: b, reason: collision with root package name */
        final Request f19985b;

        /* renamed from: c, reason: collision with root package name */
        final Response f19986c;
        private String etag;
        private Date expires;
        private Date lastModified;
        private String lastModifiedString;
        private long receivedResponseMillis;
        private long sentRequestMillis;
        private Date servedDate;
        private String servedDateString;

        public Factory(long j2, Request request, Response response) {
            this.ageSeconds = -1;
            this.f19984a = j2;
            this.f19985b = request;
            this.f19986c = response;
            if (response != null) {
                Headers headers = response.headers();
                int size = headers.size();
                for (int i2 = 0; i2 < size; i2++) {
                    String strName = headers.name(i2);
                    String strValue = headers.value(i2);
                    if ("Date".equalsIgnoreCase(strName)) {
                        this.servedDate = HttpDate.parse(strValue);
                        this.servedDateString = strValue;
                    } else if ("Expires".equalsIgnoreCase(strName)) {
                        this.expires = HttpDate.parse(strValue);
                    } else if ("Last-Modified".equalsIgnoreCase(strName)) {
                        this.lastModified = HttpDate.parse(strValue);
                        this.lastModifiedString = strValue;
                    } else if ("ETag".equalsIgnoreCase(strName)) {
                        this.etag = strValue;
                    } else if (HttpHeaders.AGE.equalsIgnoreCase(strName)) {
                        this.ageSeconds = HeaderParser.parseSeconds(strValue, -1);
                    } else if (OkHeaders.SENT_MILLIS.equalsIgnoreCase(strName)) {
                        this.sentRequestMillis = Long.parseLong(strValue);
                    } else if (OkHeaders.RECEIVED_MILLIS.equalsIgnoreCase(strName)) {
                        this.receivedResponseMillis = Long.parseLong(strValue);
                    }
                }
            }
        }

        private long cacheResponseAge() {
            Date date = this.servedDate;
            long jMax = date != null ? Math.max(0L, this.receivedResponseMillis - date.getTime()) : 0L;
            int i2 = this.ageSeconds;
            if (i2 != -1) {
                jMax = Math.max(jMax, TimeUnit.SECONDS.toMillis(i2));
            }
            long j2 = this.receivedResponseMillis;
            return jMax + (j2 - this.sentRequestMillis) + (this.f19984a - j2);
        }

        private long computeFreshnessLifetime() throws NumberFormatException {
            if (this.f19986c.cacheControl().maxAgeSeconds() != -1) {
                return TimeUnit.SECONDS.toMillis(r0.maxAgeSeconds());
            }
            if (this.expires != null) {
                Date date = this.servedDate;
                long time = this.expires.getTime() - (date != null ? date.getTime() : this.receivedResponseMillis);
                if (time > 0) {
                    return time;
                }
                return 0L;
            }
            if (this.lastModified == null || this.f19986c.request().httpUrl().query() != null) {
                return 0L;
            }
            Date date2 = this.servedDate;
            long time2 = (date2 != null ? date2.getTime() : this.sentRequestMillis) - this.lastModified.getTime();
            if (time2 > 0) {
                return time2 / 10;
            }
            return 0L;
        }

        private CacheStrategy getCandidate() throws NumberFormatException {
            Response response = null;
            byte b2 = 0;
            byte b3 = 0;
            byte b4 = 0;
            byte b5 = 0;
            byte b6 = 0;
            byte b7 = 0;
            byte b8 = 0;
            byte b9 = 0;
            byte b10 = 0;
            byte b11 = 0;
            byte b12 = 0;
            byte b13 = 0;
            if (this.f19986c == null) {
                return new CacheStrategy(this.f19985b, response);
            }
            if (this.f19985b.isHttps() && this.f19986c.handshake() == null) {
                return new CacheStrategy(this.f19985b, b12 == true ? 1 : 0);
            }
            if (!CacheStrategy.isCacheable(this.f19986c, this.f19985b)) {
                return new CacheStrategy(this.f19985b, b10 == true ? 1 : 0);
            }
            CacheControl cacheControl = this.f19985b.cacheControl();
            if (cacheControl.noCache() || hasConditions(this.f19985b)) {
                return new CacheStrategy(this.f19985b, b3 == true ? 1 : 0);
            }
            long jCacheResponseAge = cacheResponseAge();
            long jComputeFreshnessLifetime = computeFreshnessLifetime();
            if (cacheControl.maxAgeSeconds() != -1) {
                jComputeFreshnessLifetime = Math.min(jComputeFreshnessLifetime, TimeUnit.SECONDS.toMillis(cacheControl.maxAgeSeconds()));
            }
            long millis = 0;
            long millis2 = cacheControl.minFreshSeconds() != -1 ? TimeUnit.SECONDS.toMillis(cacheControl.minFreshSeconds()) : 0L;
            CacheControl cacheControl2 = this.f19986c.cacheControl();
            if (!cacheControl2.mustRevalidate() && cacheControl.maxStaleSeconds() != -1) {
                millis = TimeUnit.SECONDS.toMillis(cacheControl.maxStaleSeconds());
            }
            if (!cacheControl2.noCache()) {
                long j2 = millis2 + jCacheResponseAge;
                if (j2 < millis + jComputeFreshnessLifetime) {
                    Response.Builder builderNewBuilder = this.f19986c.newBuilder();
                    if (j2 >= jComputeFreshnessLifetime) {
                        builderNewBuilder.addHeader(HttpHeaders.WARNING, "110 HttpURLConnection \"Response is stale\"");
                    }
                    if (jCacheResponseAge > 86400000 && isFreshnessLifetimeHeuristic()) {
                        builderNewBuilder.addHeader(HttpHeaders.WARNING, "113 HttpURLConnection \"Heuristic expiration\"");
                    }
                    return new CacheStrategy(b8 == true ? 1 : 0, builderNewBuilder.build());
                }
            }
            Request.Builder builderNewBuilder2 = this.f19985b.newBuilder();
            String str = this.etag;
            if (str != null) {
                builderNewBuilder2.header("If-None-Match", str);
            } else if (this.lastModified != null) {
                builderNewBuilder2.header("If-Modified-Since", this.lastModifiedString);
            } else if (this.servedDate != null) {
                builderNewBuilder2.header("If-Modified-Since", this.servedDateString);
            }
            Request requestBuild = builderNewBuilder2.build();
            return hasConditions(requestBuild) ? new CacheStrategy(requestBuild, this.f19986c) : new CacheStrategy(requestBuild, b5 == true ? 1 : 0);
        }

        private static boolean hasConditions(Request request) {
            return (request.header("If-Modified-Since") == null && request.header("If-None-Match") == null) ? false : true;
        }

        private boolean isFreshnessLifetimeHeuristic() {
            return this.f19986c.cacheControl().maxAgeSeconds() == -1 && this.expires == null;
        }

        public CacheStrategy get() throws NumberFormatException {
            CacheStrategy candidate = getCandidate();
            return (candidate.networkRequest == null || !this.f19985b.cacheControl().onlyIfCached()) ? candidate : new CacheStrategy(null, 0 == true ? 1 : 0);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0056, code lost:
    
        if (r3.cacheControl().isPrivate() == false) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isCacheable(com.squareup.okhttp.Response r3, com.squareup.okhttp.Request r4) {
        /*
            int r0 = r3.code()
            r1 = 200(0xc8, float:2.8E-43)
            r2 = 0
            if (r0 == r1) goto L5a
            r1 = 410(0x19a, float:5.75E-43)
            if (r0 == r1) goto L5a
            r1 = 414(0x19e, float:5.8E-43)
            if (r0 == r1) goto L5a
            r1 = 501(0x1f5, float:7.02E-43)
            if (r0 == r1) goto L5a
            r1 = 203(0xcb, float:2.84E-43)
            if (r0 == r1) goto L5a
            r1 = 204(0xcc, float:2.86E-43)
            if (r0 == r1) goto L5a
            r1 = 307(0x133, float:4.3E-43)
            if (r0 == r1) goto L31
            r1 = 308(0x134, float:4.32E-43)
            if (r0 == r1) goto L5a
            r1 = 404(0x194, float:5.66E-43)
            if (r0 == r1) goto L5a
            r1 = 405(0x195, float:5.68E-43)
            if (r0 == r1) goto L5a
            switch(r0) {
                case 300: goto L5a;
                case 301: goto L5a;
                case 302: goto L31;
                default: goto L30;
            }
        L30:
            goto L59
        L31:
            java.lang.String r0 = "Expires"
            java.lang.String r0 = r3.header(r0)
            if (r0 != 0) goto L5a
            com.squareup.okhttp.CacheControl r0 = r3.cacheControl()
            int r0 = r0.maxAgeSeconds()
            r1 = -1
            if (r0 != r1) goto L5a
            com.squareup.okhttp.CacheControl r0 = r3.cacheControl()
            boolean r0 = r0.isPublic()
            if (r0 != 0) goto L5a
            com.squareup.okhttp.CacheControl r0 = r3.cacheControl()
            boolean r0 = r0.isPrivate()
            if (r0 == 0) goto L59
            goto L5a
        L59:
            return r2
        L5a:
            com.squareup.okhttp.CacheControl r3 = r3.cacheControl()
            boolean r3 = r3.noStore()
            if (r3 != 0) goto L6f
            com.squareup.okhttp.CacheControl r3 = r4.cacheControl()
            boolean r3 = r3.noStore()
            if (r3 != 0) goto L6f
            r2 = 1
        L6f:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.http.CacheStrategy.isCacheable(com.squareup.okhttp.Response, com.squareup.okhttp.Request):boolean");
    }

    private CacheStrategy(Request request, Response response) {
        this.networkRequest = request;
        this.cacheResponse = response;
    }
}
