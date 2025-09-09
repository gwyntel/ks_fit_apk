package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.util.Clock;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes3.dex */
public final class zzjz extends zzid {
    zzjz(zzhc zzhcVar) {
        super(zzhcVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzid
    protected final boolean a() {
        return false;
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Context zza() {
        return super.zza();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Clock zzb() {
        return super.zzb();
    }

    public final boolean zzc() {
        NetworkInfo activeNetworkInfo;
        zzab();
        ConnectivityManager connectivityManager = (ConnectivityManager) zza().getSystemService("connectivity");
        if (connectivityManager != null) {
            try {
                activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            } catch (SecurityException unused) {
            }
        } else {
            activeNetworkInfo = null;
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ zzae zzd() {
        return super.zzd();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzaf zze() {
        return super.zze();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzba zzf() {
        return super.zzf();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzfn zzi() {
        return super.zzi();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ zzfs zzj() {
        return super.zzj();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzge zzk() {
        return super.zzk();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ zzgz zzl() {
        return super.zzl();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzne zzq() {
        return super.zzq();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzr() {
        super.zzr();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzs() {
        super.zzs();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzt() {
        super.zzt();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public static byte[] zza(HttpURLConnection httpURLConnection) throws IOException {
        InputStream inputStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            inputStream = httpURLConnection.getInputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int i2 = inputStream.read(bArr);
                if (i2 <= 0) {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    inputStream.close();
                    return byteArray;
                }
                byteArrayOutputStream.write(bArr, 0, i2);
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                inputStream.close();
            }
            throw th;
        }
    }
}
