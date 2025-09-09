package com.xiaomi.infra.galaxy.fds.android.util;

import com.xiaomi.infra.galaxy.fds.model.HttpMethod;

/* loaded from: classes4.dex */
public class RequestFactory {

    /* renamed from: com.xiaomi.infra.galaxy.fds.android.util.RequestFactory$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$infra$galaxy$fds$model$HttpMethod;

        static {
            int[] iArr = new int[HttpMethod.values().length];
            $SwitchMap$com$xiaomi$infra$galaxy$fds$model$HttpMethod = iArr;
            try {
                iArr[HttpMethod.GET.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaomi$infra$galaxy$fds$model$HttpMethod[HttpMethod.PUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaomi$infra$galaxy$fds$model$HttpMethod[HttpMethod.POST.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaomi$infra$galaxy$fds$model$HttpMethod[HttpMethod.DELETE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaomi$infra$galaxy$fds$model$HttpMethod[HttpMethod.HEAD.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x003e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.apache.http.client.methods.HttpUriRequest createRequest(java.lang.String r1, com.xiaomi.infra.galaxy.fds.android.auth.GalaxyFDSCredential r2, com.xiaomi.infra.galaxy.fds.model.HttpMethod r3, java.util.Map<java.lang.String, java.lang.String> r4) throws com.xiaomi.infra.galaxy.fds.android.exception.GalaxyFDSClientException {
        /*
            java.lang.String r1 = r2.addParam(r1)
            int[] r0 = com.xiaomi.infra.galaxy.fds.android.util.RequestFactory.AnonymousClass1.$SwitchMap$com$xiaomi$infra$galaxy$fds$model$HttpMethod
            int r3 = r3.ordinal()
            r3 = r0[r3]
            r0 = 1
            if (r3 == r0) goto L36
            r0 = 2
            if (r3 == r0) goto L30
            r0 = 3
            if (r3 == r0) goto L2a
            r0 = 4
            if (r3 == r0) goto L24
            r0 = 5
            if (r3 == r0) goto L1d
            r1 = 0
            goto L3c
        L1d:
            org.apache.http.client.methods.HttpHead r3 = new org.apache.http.client.methods.HttpHead
            r3.<init>(r1)
        L22:
            r1 = r3
            goto L3c
        L24:
            org.apache.http.client.methods.HttpDelete r3 = new org.apache.http.client.methods.HttpDelete
            r3.<init>(r1)
            goto L22
        L2a:
            org.apache.http.client.methods.HttpPost r3 = new org.apache.http.client.methods.HttpPost
            r3.<init>(r1)
            goto L22
        L30:
            org.apache.http.client.methods.HttpPut r3 = new org.apache.http.client.methods.HttpPut
            r3.<init>(r1)
            goto L22
        L36:
            org.apache.http.client.methods.HttpGet r3 = new org.apache.http.client.methods.HttpGet
            r3.<init>(r1)
            goto L22
        L3c:
            if (r1 == 0) goto L81
            if (r4 == 0) goto L70
            java.lang.String r3 = "Content-Length"
            r4.remove(r3)
            java.lang.String r3 = r3.toLowerCase()
            r4.remove(r3)
            java.util.Set r3 = r4.entrySet()
            java.util.Iterator r3 = r3.iterator()
        L54:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L70
            java.lang.Object r4 = r3.next()
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4
            java.lang.Object r0 = r4.getKey()
            java.lang.String r0 = (java.lang.String) r0
            java.lang.Object r4 = r4.getValue()
            java.lang.String r4 = (java.lang.String) r4
            r1.addHeader(r0, r4)
            goto L54
        L70:
            java.util.Date r3 = new java.util.Date
            r3.<init>()
            java.lang.String r3 = com.xiaomi.infra.galaxy.fds.android.util.Util.formatDateString(r3)
            java.lang.String r4 = "Date"
            r1.addHeader(r4, r3)
            r2.addHeader(r1)
        L81:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.infra.galaxy.fds.android.util.RequestFactory.createRequest(java.lang.String, com.xiaomi.infra.galaxy.fds.android.auth.GalaxyFDSCredential, com.xiaomi.infra.galaxy.fds.model.HttpMethod, java.util.Map):org.apache.http.client.methods.HttpUriRequest");
    }
}
