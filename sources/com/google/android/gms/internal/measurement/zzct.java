package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import java.util.HashMap;

/* loaded from: classes3.dex */
public abstract class zzct extends zzbx implements zzcu {
    public zzct() {
        super("com.google.android.gms.measurement.api.internal.IAppMeasurementDynamiteService");
    }

    public static zzcu asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.measurement.api.internal.IAppMeasurementDynamiteService");
        return iInterfaceQueryLocalInterface instanceof zzcu ? (zzcu) iInterfaceQueryLocalInterface : new zzcw(iBinder);
    }

    @Override // com.google.android.gms.internal.measurement.zzbx
    protected final boolean a(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
        zzcv zzcxVar;
        zzcv zzcxVar2 = null;
        zzcv zzcxVar3 = null;
        zzcv zzcxVar4 = null;
        zzcv zzcxVar5 = null;
        zzda zzdcVar = null;
        zzda zzdcVar2 = null;
        zzda zzdcVar3 = null;
        zzcv zzcxVar6 = null;
        zzcv zzcxVar7 = null;
        zzcv zzcxVar8 = null;
        zzcv zzcxVar9 = null;
        zzcv zzcxVar10 = null;
        zzcv zzcxVar11 = null;
        zzdb zzdeVar = null;
        zzcv zzcxVar12 = null;
        zzcv zzcxVar13 = null;
        zzcv zzcxVar14 = null;
        zzcv zzcxVar15 = null;
        switch (i2) {
            case 1:
                IObjectWrapper iObjectWrapperAsInterface = IObjectWrapper.Stub.asInterface(parcel.readStrongBinder());
                zzdd zzddVar = (zzdd) zzbw.zza(parcel, zzdd.CREATOR);
                long j2 = parcel.readLong();
                zzbw.zzb(parcel);
                initialize(iObjectWrapperAsInterface, zzddVar, j2);
                break;
            case 2:
                String string = parcel.readString();
                String string2 = parcel.readString();
                Bundle bundle = (Bundle) zzbw.zza(parcel, Bundle.CREATOR);
                boolean zZzc = zzbw.zzc(parcel);
                boolean zZzc2 = zzbw.zzc(parcel);
                long j3 = parcel.readLong();
                zzbw.zzb(parcel);
                logEvent(string, string2, bundle, zZzc, zZzc2, j3);
                break;
            case 3:
                String string3 = parcel.readString();
                String string4 = parcel.readString();
                Bundle bundle2 = (Bundle) zzbw.zza(parcel, Bundle.CREATOR);
                IBinder strongBinder = parcel.readStrongBinder();
                if (strongBinder == null) {
                    zzcxVar = null;
                } else {
                    IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzcxVar = iInterfaceQueryLocalInterface instanceof zzcv ? (zzcv) iInterfaceQueryLocalInterface : new zzcx(strongBinder);
                }
                long j4 = parcel.readLong();
                zzbw.zzb(parcel);
                logEventAndBundle(string3, string4, bundle2, zzcxVar, j4);
                break;
            case 4:
                String string5 = parcel.readString();
                String string6 = parcel.readString();
                IObjectWrapper iObjectWrapperAsInterface2 = IObjectWrapper.Stub.asInterface(parcel.readStrongBinder());
                boolean zZzc3 = zzbw.zzc(parcel);
                long j5 = parcel.readLong();
                zzbw.zzb(parcel);
                setUserProperty(string5, string6, iObjectWrapperAsInterface2, zZzc3, j5);
                break;
            case 5:
                String string7 = parcel.readString();
                String string8 = parcel.readString();
                boolean zZzc4 = zzbw.zzc(parcel);
                IBinder strongBinder2 = parcel.readStrongBinder();
                if (strongBinder2 != null) {
                    IInterface iInterfaceQueryLocalInterface2 = strongBinder2.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzcxVar2 = iInterfaceQueryLocalInterface2 instanceof zzcv ? (zzcv) iInterfaceQueryLocalInterface2 : new zzcx(strongBinder2);
                }
                zzbw.zzb(parcel);
                getUserProperties(string7, string8, zZzc4, zzcxVar2);
                break;
            case 6:
                String string9 = parcel.readString();
                IBinder strongBinder3 = parcel.readStrongBinder();
                if (strongBinder3 != null) {
                    IInterface iInterfaceQueryLocalInterface3 = strongBinder3.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzcxVar15 = iInterfaceQueryLocalInterface3 instanceof zzcv ? (zzcv) iInterfaceQueryLocalInterface3 : new zzcx(strongBinder3);
                }
                zzbw.zzb(parcel);
                getMaxUserProperties(string9, zzcxVar15);
                break;
            case 7:
                String string10 = parcel.readString();
                long j6 = parcel.readLong();
                zzbw.zzb(parcel);
                setUserId(string10, j6);
                break;
            case 8:
                Bundle bundle3 = (Bundle) zzbw.zza(parcel, Bundle.CREATOR);
                long j7 = parcel.readLong();
                zzbw.zzb(parcel);
                setConditionalUserProperty(bundle3, j7);
                break;
            case 9:
                String string11 = parcel.readString();
                String string12 = parcel.readString();
                Bundle bundle4 = (Bundle) zzbw.zza(parcel, Bundle.CREATOR);
                zzbw.zzb(parcel);
                clearConditionalUserProperty(string11, string12, bundle4);
                break;
            case 10:
                String string13 = parcel.readString();
                String string14 = parcel.readString();
                IBinder strongBinder4 = parcel.readStrongBinder();
                if (strongBinder4 != null) {
                    IInterface iInterfaceQueryLocalInterface4 = strongBinder4.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzcxVar14 = iInterfaceQueryLocalInterface4 instanceof zzcv ? (zzcv) iInterfaceQueryLocalInterface4 : new zzcx(strongBinder4);
                }
                zzbw.zzb(parcel);
                getConditionalUserProperties(string13, string14, zzcxVar14);
                break;
            case 11:
                boolean zZzc5 = zzbw.zzc(parcel);
                long j8 = parcel.readLong();
                zzbw.zzb(parcel);
                setMeasurementEnabled(zZzc5, j8);
                break;
            case 12:
                long j9 = parcel.readLong();
                zzbw.zzb(parcel);
                resetAnalyticsData(j9);
                break;
            case 13:
                long j10 = parcel.readLong();
                zzbw.zzb(parcel);
                setMinimumSessionDuration(j10);
                break;
            case 14:
                long j11 = parcel.readLong();
                zzbw.zzb(parcel);
                setSessionTimeoutDuration(j11);
                break;
            case 15:
                IObjectWrapper iObjectWrapperAsInterface3 = IObjectWrapper.Stub.asInterface(parcel.readStrongBinder());
                String string15 = parcel.readString();
                String string16 = parcel.readString();
                long j12 = parcel.readLong();
                zzbw.zzb(parcel);
                setCurrentScreen(iObjectWrapperAsInterface3, string15, string16, j12);
                break;
            case 16:
                IBinder strongBinder5 = parcel.readStrongBinder();
                if (strongBinder5 != null) {
                    IInterface iInterfaceQueryLocalInterface5 = strongBinder5.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzcxVar13 = iInterfaceQueryLocalInterface5 instanceof zzcv ? (zzcv) iInterfaceQueryLocalInterface5 : new zzcx(strongBinder5);
                }
                zzbw.zzb(parcel);
                getCurrentScreenName(zzcxVar13);
                break;
            case 17:
                IBinder strongBinder6 = parcel.readStrongBinder();
                if (strongBinder6 != null) {
                    IInterface iInterfaceQueryLocalInterface6 = strongBinder6.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzcxVar12 = iInterfaceQueryLocalInterface6 instanceof zzcv ? (zzcv) iInterfaceQueryLocalInterface6 : new zzcx(strongBinder6);
                }
                zzbw.zzb(parcel);
                getCurrentScreenClass(zzcxVar12);
                break;
            case 18:
                IBinder strongBinder7 = parcel.readStrongBinder();
                if (strongBinder7 != null) {
                    IInterface iInterfaceQueryLocalInterface7 = strongBinder7.queryLocalInterface("com.google.android.gms.measurement.api.internal.IStringProvider");
                    zzdeVar = iInterfaceQueryLocalInterface7 instanceof zzdb ? (zzdb) iInterfaceQueryLocalInterface7 : new zzde(strongBinder7);
                }
                zzbw.zzb(parcel);
                setInstanceIdProvider(zzdeVar);
                break;
            case 19:
                IBinder strongBinder8 = parcel.readStrongBinder();
                if (strongBinder8 != null) {
                    IInterface iInterfaceQueryLocalInterface8 = strongBinder8.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzcxVar11 = iInterfaceQueryLocalInterface8 instanceof zzcv ? (zzcv) iInterfaceQueryLocalInterface8 : new zzcx(strongBinder8);
                }
                zzbw.zzb(parcel);
                getCachedAppInstanceId(zzcxVar11);
                break;
            case 20:
                IBinder strongBinder9 = parcel.readStrongBinder();
                if (strongBinder9 != null) {
                    IInterface iInterfaceQueryLocalInterface9 = strongBinder9.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzcxVar10 = iInterfaceQueryLocalInterface9 instanceof zzcv ? (zzcv) iInterfaceQueryLocalInterface9 : new zzcx(strongBinder9);
                }
                zzbw.zzb(parcel);
                getAppInstanceId(zzcxVar10);
                break;
            case 21:
                IBinder strongBinder10 = parcel.readStrongBinder();
                if (strongBinder10 != null) {
                    IInterface iInterfaceQueryLocalInterface10 = strongBinder10.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzcxVar9 = iInterfaceQueryLocalInterface10 instanceof zzcv ? (zzcv) iInterfaceQueryLocalInterface10 : new zzcx(strongBinder10);
                }
                zzbw.zzb(parcel);
                getGmpAppId(zzcxVar9);
                break;
            case 22:
                IBinder strongBinder11 = parcel.readStrongBinder();
                if (strongBinder11 != null) {
                    IInterface iInterfaceQueryLocalInterface11 = strongBinder11.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzcxVar8 = iInterfaceQueryLocalInterface11 instanceof zzcv ? (zzcv) iInterfaceQueryLocalInterface11 : new zzcx(strongBinder11);
                }
                zzbw.zzb(parcel);
                generateEventId(zzcxVar8);
                break;
            case 23:
                String string17 = parcel.readString();
                long j13 = parcel.readLong();
                zzbw.zzb(parcel);
                beginAdUnitExposure(string17, j13);
                break;
            case 24:
                String string18 = parcel.readString();
                long j14 = parcel.readLong();
                zzbw.zzb(parcel);
                endAdUnitExposure(string18, j14);
                break;
            case 25:
                IObjectWrapper iObjectWrapperAsInterface4 = IObjectWrapper.Stub.asInterface(parcel.readStrongBinder());
                long j15 = parcel.readLong();
                zzbw.zzb(parcel);
                onActivityStarted(iObjectWrapperAsInterface4, j15);
                break;
            case 26:
                IObjectWrapper iObjectWrapperAsInterface5 = IObjectWrapper.Stub.asInterface(parcel.readStrongBinder());
                long j16 = parcel.readLong();
                zzbw.zzb(parcel);
                onActivityStopped(iObjectWrapperAsInterface5, j16);
                break;
            case 27:
                IObjectWrapper iObjectWrapperAsInterface6 = IObjectWrapper.Stub.asInterface(parcel.readStrongBinder());
                Bundle bundle5 = (Bundle) zzbw.zza(parcel, Bundle.CREATOR);
                long j17 = parcel.readLong();
                zzbw.zzb(parcel);
                onActivityCreated(iObjectWrapperAsInterface6, bundle5, j17);
                break;
            case 28:
                IObjectWrapper iObjectWrapperAsInterface7 = IObjectWrapper.Stub.asInterface(parcel.readStrongBinder());
                long j18 = parcel.readLong();
                zzbw.zzb(parcel);
                onActivityDestroyed(iObjectWrapperAsInterface7, j18);
                break;
            case 29:
                IObjectWrapper iObjectWrapperAsInterface8 = IObjectWrapper.Stub.asInterface(parcel.readStrongBinder());
                long j19 = parcel.readLong();
                zzbw.zzb(parcel);
                onActivityPaused(iObjectWrapperAsInterface8, j19);
                break;
            case 30:
                IObjectWrapper iObjectWrapperAsInterface9 = IObjectWrapper.Stub.asInterface(parcel.readStrongBinder());
                long j20 = parcel.readLong();
                zzbw.zzb(parcel);
                onActivityResumed(iObjectWrapperAsInterface9, j20);
                break;
            case 31:
                IObjectWrapper iObjectWrapperAsInterface10 = IObjectWrapper.Stub.asInterface(parcel.readStrongBinder());
                IBinder strongBinder12 = parcel.readStrongBinder();
                if (strongBinder12 != null) {
                    IInterface iInterfaceQueryLocalInterface12 = strongBinder12.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzcxVar7 = iInterfaceQueryLocalInterface12 instanceof zzcv ? (zzcv) iInterfaceQueryLocalInterface12 : new zzcx(strongBinder12);
                }
                long j21 = parcel.readLong();
                zzbw.zzb(parcel);
                onActivitySaveInstanceState(iObjectWrapperAsInterface10, zzcxVar7, j21);
                break;
            case 32:
                Bundle bundle6 = (Bundle) zzbw.zza(parcel, Bundle.CREATOR);
                IBinder strongBinder13 = parcel.readStrongBinder();
                if (strongBinder13 != null) {
                    IInterface iInterfaceQueryLocalInterface13 = strongBinder13.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzcxVar6 = iInterfaceQueryLocalInterface13 instanceof zzcv ? (zzcv) iInterfaceQueryLocalInterface13 : new zzcx(strongBinder13);
                }
                long j22 = parcel.readLong();
                zzbw.zzb(parcel);
                performAction(bundle6, zzcxVar6, j22);
                break;
            case 33:
                int i4 = parcel.readInt();
                String string19 = parcel.readString();
                IObjectWrapper iObjectWrapperAsInterface11 = IObjectWrapper.Stub.asInterface(parcel.readStrongBinder());
                IObjectWrapper iObjectWrapperAsInterface12 = IObjectWrapper.Stub.asInterface(parcel.readStrongBinder());
                IObjectWrapper iObjectWrapperAsInterface13 = IObjectWrapper.Stub.asInterface(parcel.readStrongBinder());
                zzbw.zzb(parcel);
                logHealthData(i4, string19, iObjectWrapperAsInterface11, iObjectWrapperAsInterface12, iObjectWrapperAsInterface13);
                break;
            case 34:
                IBinder strongBinder14 = parcel.readStrongBinder();
                if (strongBinder14 != null) {
                    IInterface iInterfaceQueryLocalInterface14 = strongBinder14.queryLocalInterface("com.google.android.gms.measurement.api.internal.IEventHandlerProxy");
                    zzdcVar3 = iInterfaceQueryLocalInterface14 instanceof zzda ? (zzda) iInterfaceQueryLocalInterface14 : new zzdc(strongBinder14);
                }
                zzbw.zzb(parcel);
                setEventInterceptor(zzdcVar3);
                break;
            case 35:
                IBinder strongBinder15 = parcel.readStrongBinder();
                if (strongBinder15 != null) {
                    IInterface iInterfaceQueryLocalInterface15 = strongBinder15.queryLocalInterface("com.google.android.gms.measurement.api.internal.IEventHandlerProxy");
                    zzdcVar2 = iInterfaceQueryLocalInterface15 instanceof zzda ? (zzda) iInterfaceQueryLocalInterface15 : new zzdc(strongBinder15);
                }
                zzbw.zzb(parcel);
                registerOnMeasurementEventListener(zzdcVar2);
                break;
            case 36:
                IBinder strongBinder16 = parcel.readStrongBinder();
                if (strongBinder16 != null) {
                    IInterface iInterfaceQueryLocalInterface16 = strongBinder16.queryLocalInterface("com.google.android.gms.measurement.api.internal.IEventHandlerProxy");
                    zzdcVar = iInterfaceQueryLocalInterface16 instanceof zzda ? (zzda) iInterfaceQueryLocalInterface16 : new zzdc(strongBinder16);
                }
                zzbw.zzb(parcel);
                unregisterOnMeasurementEventListener(zzdcVar);
                break;
            case 37:
                HashMap mapZza = zzbw.zza(parcel);
                zzbw.zzb(parcel);
                initForTests(mapZza);
                break;
            case 38:
                IBinder strongBinder17 = parcel.readStrongBinder();
                if (strongBinder17 != null) {
                    IInterface iInterfaceQueryLocalInterface17 = strongBinder17.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzcxVar5 = iInterfaceQueryLocalInterface17 instanceof zzcv ? (zzcv) iInterfaceQueryLocalInterface17 : new zzcx(strongBinder17);
                }
                int i5 = parcel.readInt();
                zzbw.zzb(parcel);
                getTestFlag(zzcxVar5, i5);
                break;
            case 39:
                boolean zZzc6 = zzbw.zzc(parcel);
                zzbw.zzb(parcel);
                setDataCollectionEnabled(zZzc6);
                break;
            case 40:
                IBinder strongBinder18 = parcel.readStrongBinder();
                if (strongBinder18 != null) {
                    IInterface iInterfaceQueryLocalInterface18 = strongBinder18.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzcxVar4 = iInterfaceQueryLocalInterface18 instanceof zzcv ? (zzcv) iInterfaceQueryLocalInterface18 : new zzcx(strongBinder18);
                }
                zzbw.zzb(parcel);
                isDataCollectionEnabled(zzcxVar4);
                break;
            case 41:
            default:
                return false;
            case 42:
                Bundle bundle7 = (Bundle) zzbw.zza(parcel, Bundle.CREATOR);
                zzbw.zzb(parcel);
                setDefaultEventParameters(bundle7);
                break;
            case 43:
                long j23 = parcel.readLong();
                zzbw.zzb(parcel);
                clearMeasurementEnabled(j23);
                break;
            case 44:
                Bundle bundle8 = (Bundle) zzbw.zza(parcel, Bundle.CREATOR);
                long j24 = parcel.readLong();
                zzbw.zzb(parcel);
                setConsent(bundle8, j24);
                break;
            case 45:
                Bundle bundle9 = (Bundle) zzbw.zza(parcel, Bundle.CREATOR);
                long j25 = parcel.readLong();
                zzbw.zzb(parcel);
                setConsentThirdParty(bundle9, j25);
                break;
            case 46:
                IBinder strongBinder19 = parcel.readStrongBinder();
                if (strongBinder19 != null) {
                    IInterface iInterfaceQueryLocalInterface19 = strongBinder19.queryLocalInterface("com.google.android.gms.measurement.api.internal.IBundleReceiver");
                    zzcxVar3 = iInterfaceQueryLocalInterface19 instanceof zzcv ? (zzcv) iInterfaceQueryLocalInterface19 : new zzcx(strongBinder19);
                }
                zzbw.zzb(parcel);
                getSessionId(zzcxVar3);
                break;
        }
        parcel2.writeNoException();
        return true;
    }
}
