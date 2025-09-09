package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import java.util.Map;

/* loaded from: classes3.dex */
public interface zzcu extends IInterface {
    void beginAdUnitExposure(String str, long j2) throws RemoteException;

    void clearConditionalUserProperty(String str, String str2, Bundle bundle) throws RemoteException;

    void clearMeasurementEnabled(long j2) throws RemoteException;

    void endAdUnitExposure(String str, long j2) throws RemoteException;

    void generateEventId(zzcv zzcvVar) throws RemoteException;

    void getAppInstanceId(zzcv zzcvVar) throws RemoteException;

    void getCachedAppInstanceId(zzcv zzcvVar) throws RemoteException;

    void getConditionalUserProperties(String str, String str2, zzcv zzcvVar) throws RemoteException;

    void getCurrentScreenClass(zzcv zzcvVar) throws RemoteException;

    void getCurrentScreenName(zzcv zzcvVar) throws RemoteException;

    void getGmpAppId(zzcv zzcvVar) throws RemoteException;

    void getMaxUserProperties(String str, zzcv zzcvVar) throws RemoteException;

    void getSessionId(zzcv zzcvVar) throws RemoteException;

    void getTestFlag(zzcv zzcvVar, int i2) throws RemoteException;

    void getUserProperties(String str, String str2, boolean z2, zzcv zzcvVar) throws RemoteException;

    void initForTests(Map map) throws RemoteException;

    void initialize(IObjectWrapper iObjectWrapper, zzdd zzddVar, long j2) throws RemoteException;

    void isDataCollectionEnabled(zzcv zzcvVar) throws RemoteException;

    void logEvent(String str, String str2, Bundle bundle, boolean z2, boolean z3, long j2) throws RemoteException;

    void logEventAndBundle(String str, String str2, Bundle bundle, zzcv zzcvVar, long j2) throws RemoteException;

    void logHealthData(int i2, String str, IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, IObjectWrapper iObjectWrapper3) throws RemoteException;

    void onActivityCreated(IObjectWrapper iObjectWrapper, Bundle bundle, long j2) throws RemoteException;

    void onActivityDestroyed(IObjectWrapper iObjectWrapper, long j2) throws RemoteException;

    void onActivityPaused(IObjectWrapper iObjectWrapper, long j2) throws RemoteException;

    void onActivityResumed(IObjectWrapper iObjectWrapper, long j2) throws RemoteException;

    void onActivitySaveInstanceState(IObjectWrapper iObjectWrapper, zzcv zzcvVar, long j2) throws RemoteException;

    void onActivityStarted(IObjectWrapper iObjectWrapper, long j2) throws RemoteException;

    void onActivityStopped(IObjectWrapper iObjectWrapper, long j2) throws RemoteException;

    void performAction(Bundle bundle, zzcv zzcvVar, long j2) throws RemoteException;

    void registerOnMeasurementEventListener(zzda zzdaVar) throws RemoteException;

    void resetAnalyticsData(long j2) throws RemoteException;

    void setConditionalUserProperty(Bundle bundle, long j2) throws RemoteException;

    void setConsent(Bundle bundle, long j2) throws RemoteException;

    void setConsentThirdParty(Bundle bundle, long j2) throws RemoteException;

    void setCurrentScreen(IObjectWrapper iObjectWrapper, String str, String str2, long j2) throws RemoteException;

    void setDataCollectionEnabled(boolean z2) throws RemoteException;

    void setDefaultEventParameters(Bundle bundle) throws RemoteException;

    void setEventInterceptor(zzda zzdaVar) throws RemoteException;

    void setInstanceIdProvider(zzdb zzdbVar) throws RemoteException;

    void setMeasurementEnabled(boolean z2, long j2) throws RemoteException;

    void setMinimumSessionDuration(long j2) throws RemoteException;

    void setSessionTimeoutDuration(long j2) throws RemoteException;

    void setUserId(String str, long j2) throws RemoteException;

    void setUserProperty(String str, String str2, IObjectWrapper iObjectWrapper, boolean z2, long j2) throws RemoteException;

    void unregisterOnMeasurementEventListener(zzda zzdaVar) throws RemoteException;
}
