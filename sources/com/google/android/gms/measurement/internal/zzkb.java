package com.google.android.gms.measurement.internal;

import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

@WorkerThread
/* loaded from: classes3.dex */
final class zzkb implements Runnable {
    private final URL zza;
    private final byte[] zzb;
    private final zzkc zzc;
    private final String zzd;
    private final Map<String, String> zze;
    private final /* synthetic */ zzjz zzf;

    public zzkb(zzjz zzjzVar, String str, URL url, byte[] bArr, Map<String, String> map, zzkc zzkcVar) {
        this.zzf = zzjzVar;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(url);
        Preconditions.checkNotNull(zzkcVar);
        this.zza = url;
        this.zzb = null;
        this.zzc = zzkcVar;
        this.zzd = str;
        this.zze = null;
    }

    private final void zzb(final int i2, final Exception exc, final byte[] bArr, final Map<String, List<String>> map) throws IllegalStateException {
        this.zzf.zzl().zzb(new Runnable() { // from class: com.google.android.gms.measurement.internal.zzke
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.a(i2, exc, bArr, map);
            }
        });
    }

    final /* synthetic */ void a(int i2, Exception exc, byte[] bArr, Map map) {
        this.zzc.zza(this.zzd, i2, exc, bArr, map);
    }

    @Override // java.lang.Runnable
    public final void run() throws Throwable {
        HttpURLConnection httpURLConnection;
        Map<String, List<String>> headerFields;
        this.zzf.zzr();
        int responseCode = 0;
        try {
            URLConnection uRLConnectionZza = com.google.android.gms.internal.measurement.zzcd.zza().zza(this.zza, "client-measurement");
            if (!(uRLConnectionZza instanceof HttpURLConnection)) {
                throw new IOException("Failed to obtain HTTP connection");
            }
            httpURLConnection = (HttpURLConnection) uRLConnectionZza;
            httpURLConnection.setDefaultUseCaches(false);
            httpURLConnection.setConnectTimeout(60000);
            httpURLConnection.setReadTimeout(61000);
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setDoInput(true);
            try {
                responseCode = httpURLConnection.getResponseCode();
                headerFields = httpURLConnection.getHeaderFields();
            } catch (IOException e2) {
                e = e2;
                headerFields = null;
            } catch (Throwable th) {
                th = th;
                headerFields = null;
            }
            try {
                zzjz zzjzVar = this.zzf;
                byte[] bArrZza = zzjz.zza(httpURLConnection);
                httpURLConnection.disconnect();
                zzb(responseCode, null, bArrZza, headerFields);
            } catch (IOException e3) {
                e = e3;
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                zzb(responseCode, e, null, headerFields);
            } catch (Throwable th2) {
                th = th2;
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                zzb(responseCode, null, null, headerFields);
                throw th;
            }
        } catch (IOException e4) {
            e = e4;
            httpURLConnection = null;
            headerFields = null;
        } catch (Throwable th3) {
            th = th3;
            httpURLConnection = null;
            headerFields = null;
        }
    }
}
