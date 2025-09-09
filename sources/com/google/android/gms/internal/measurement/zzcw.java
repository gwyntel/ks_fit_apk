package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import java.util.Map;

/* loaded from: classes3.dex */
public final class zzcw extends zzbu implements zzcu {
    zzcw(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.measurement.api.internal.IAppMeasurementDynamiteService");
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void beginAdUnitExposure(String str, long j2) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeString(str);
        parcelD.writeLong(j2);
        f(23, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void clearConditionalUserProperty(String str, String str2, Bundle bundle) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeString(str);
        parcelD.writeString(str2);
        zzbw.zza(parcelD, bundle);
        f(9, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void clearMeasurementEnabled(long j2) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeLong(j2);
        f(43, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void endAdUnitExposure(String str, long j2) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeString(str);
        parcelD.writeLong(j2);
        f(24, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void generateEventId(zzcv zzcvVar) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, zzcvVar);
        f(22, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void getAppInstanceId(zzcv zzcvVar) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, zzcvVar);
        f(20, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void getCachedAppInstanceId(zzcv zzcvVar) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, zzcvVar);
        f(19, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void getConditionalUserProperties(String str, String str2, zzcv zzcvVar) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeString(str);
        parcelD.writeString(str2);
        zzbw.zza(parcelD, zzcvVar);
        f(10, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void getCurrentScreenClass(zzcv zzcvVar) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, zzcvVar);
        f(17, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void getCurrentScreenName(zzcv zzcvVar) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, zzcvVar);
        f(16, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void getGmpAppId(zzcv zzcvVar) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, zzcvVar);
        f(21, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void getMaxUserProperties(String str, zzcv zzcvVar) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeString(str);
        zzbw.zza(parcelD, zzcvVar);
        f(6, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void getSessionId(zzcv zzcvVar) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, zzcvVar);
        f(46, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void getTestFlag(zzcv zzcvVar, int i2) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, zzcvVar);
        parcelD.writeInt(i2);
        f(38, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void getUserProperties(String str, String str2, boolean z2, zzcv zzcvVar) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeString(str);
        parcelD.writeString(str2);
        zzbw.zza(parcelD, z2);
        zzbw.zza(parcelD, zzcvVar);
        f(5, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void initForTests(Map map) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeMap(map);
        f(37, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void initialize(IObjectWrapper iObjectWrapper, zzdd zzddVar, long j2) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, iObjectWrapper);
        zzbw.zza(parcelD, zzddVar);
        parcelD.writeLong(j2);
        f(1, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void isDataCollectionEnabled(zzcv zzcvVar) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, zzcvVar);
        f(40, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void logEvent(String str, String str2, Bundle bundle, boolean z2, boolean z3, long j2) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeString(str);
        parcelD.writeString(str2);
        zzbw.zza(parcelD, bundle);
        zzbw.zza(parcelD, z2);
        zzbw.zza(parcelD, z3);
        parcelD.writeLong(j2);
        f(2, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void logEventAndBundle(String str, String str2, Bundle bundle, zzcv zzcvVar, long j2) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeString(str);
        parcelD.writeString(str2);
        zzbw.zza(parcelD, bundle);
        zzbw.zza(parcelD, zzcvVar);
        parcelD.writeLong(j2);
        f(3, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void logHealthData(int i2, String str, IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, IObjectWrapper iObjectWrapper3) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeInt(i2);
        parcelD.writeString(str);
        zzbw.zza(parcelD, iObjectWrapper);
        zzbw.zza(parcelD, iObjectWrapper2);
        zzbw.zza(parcelD, iObjectWrapper3);
        f(33, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void onActivityCreated(IObjectWrapper iObjectWrapper, Bundle bundle, long j2) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, iObjectWrapper);
        zzbw.zza(parcelD, bundle);
        parcelD.writeLong(j2);
        f(27, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void onActivityDestroyed(IObjectWrapper iObjectWrapper, long j2) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, iObjectWrapper);
        parcelD.writeLong(j2);
        f(28, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void onActivityPaused(IObjectWrapper iObjectWrapper, long j2) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, iObjectWrapper);
        parcelD.writeLong(j2);
        f(29, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void onActivityResumed(IObjectWrapper iObjectWrapper, long j2) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, iObjectWrapper);
        parcelD.writeLong(j2);
        f(30, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void onActivitySaveInstanceState(IObjectWrapper iObjectWrapper, zzcv zzcvVar, long j2) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, iObjectWrapper);
        zzbw.zza(parcelD, zzcvVar);
        parcelD.writeLong(j2);
        f(31, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void onActivityStarted(IObjectWrapper iObjectWrapper, long j2) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, iObjectWrapper);
        parcelD.writeLong(j2);
        f(25, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void onActivityStopped(IObjectWrapper iObjectWrapper, long j2) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, iObjectWrapper);
        parcelD.writeLong(j2);
        f(26, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void performAction(Bundle bundle, zzcv zzcvVar, long j2) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, bundle);
        zzbw.zza(parcelD, zzcvVar);
        parcelD.writeLong(j2);
        f(32, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void registerOnMeasurementEventListener(zzda zzdaVar) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, zzdaVar);
        f(35, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void resetAnalyticsData(long j2) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeLong(j2);
        f(12, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void setConditionalUserProperty(Bundle bundle, long j2) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, bundle);
        parcelD.writeLong(j2);
        f(8, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void setConsent(Bundle bundle, long j2) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, bundle);
        parcelD.writeLong(j2);
        f(44, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void setConsentThirdParty(Bundle bundle, long j2) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, bundle);
        parcelD.writeLong(j2);
        f(45, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void setCurrentScreen(IObjectWrapper iObjectWrapper, String str, String str2, long j2) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, iObjectWrapper);
        parcelD.writeString(str);
        parcelD.writeString(str2);
        parcelD.writeLong(j2);
        f(15, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void setDataCollectionEnabled(boolean z2) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, z2);
        f(39, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void setDefaultEventParameters(Bundle bundle) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, bundle);
        f(42, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void setEventInterceptor(zzda zzdaVar) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, zzdaVar);
        f(34, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void setInstanceIdProvider(zzdb zzdbVar) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, zzdbVar);
        f(18, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void setMeasurementEnabled(boolean z2, long j2) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, z2);
        parcelD.writeLong(j2);
        f(11, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void setMinimumSessionDuration(long j2) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeLong(j2);
        f(13, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void setSessionTimeoutDuration(long j2) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeLong(j2);
        f(14, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void setUserId(String str, long j2) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeString(str);
        parcelD.writeLong(j2);
        f(7, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void setUserProperty(String str, String str2, IObjectWrapper iObjectWrapper, boolean z2, long j2) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeString(str);
        parcelD.writeString(str2);
        zzbw.zza(parcelD, iObjectWrapper);
        zzbw.zza(parcelD, z2);
        parcelD.writeLong(j2);
        f(4, parcelD);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public final void unregisterOnMeasurementEventListener(zzda zzdaVar) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, zzdaVar);
        f(36, parcelD);
    }
}
