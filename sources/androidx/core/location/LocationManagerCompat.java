package androidx.core.location;

import android.annotation.SuppressLint;
import android.location.GnssMeasurementsEvent;
import android.location.GnssMeasurementsEvent$Callback;
import android.location.GnssStatus;
import android.location.GnssStatus$Callback;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import androidx.annotation.DoNotInline;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.collection.SimpleArrayMap;
import androidx.core.location.GnssStatusCompat;
import androidx.core.os.ExecutorCompat;
import androidx.core.util.Consumer;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes.dex */
public final class LocationManagerCompat {
    private static final long GET_CURRENT_LOCATION_TIMEOUT_MS = 30000;
    private static final long MAX_CURRENT_LOCATION_AGE_MS = 10000;
    private static final long PRE_N_LOOPER_TIMEOUT_S = 5;

    /* renamed from: a, reason: collision with root package name */
    static final WeakHashMap f3586a = new WeakHashMap();
    private static Field sContextField;
    private static Method sGnssRequestBuilderBuildMethod;
    private static Class<?> sGnssRequestBuilderClass;
    private static Method sRegisterGnssMeasurementsCallbackMethod;

    static class Api19Impl {
        private static Class<?> sLocationRequestClass;
        private static Method sRequestLocationUpdatesLooperMethod;

        private Api19Impl() {
        }

        @DoNotInline
        @SuppressLint({"BanUncheckedReflection"})
        static boolean a(LocationManager locationManager, String str, LocationRequestCompat locationRequestCompat, LocationListenerCompat locationListenerCompat, Looper looper) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            try {
                if (sLocationRequestClass == null) {
                    sLocationRequestClass = Class.forName("android.location.LocationRequest");
                }
                if (sRequestLocationUpdatesLooperMethod == null) {
                    Method declaredMethod = LocationManager.class.getDeclaredMethod("requestLocationUpdates", sLocationRequestClass, LocationListener.class, Looper.class);
                    sRequestLocationUpdatesLooperMethod = declaredMethod;
                    declaredMethod.setAccessible(true);
                }
                LocationRequest locationRequest = locationRequestCompat.toLocationRequest(str);
                if (locationRequest != null) {
                    sRequestLocationUpdatesLooperMethod.invoke(locationManager, locationRequest, locationListenerCompat, looper);
                    return true;
                }
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | UnsupportedOperationException | InvocationTargetException unused) {
            }
            return false;
        }

        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        @DoNotInline
        @SuppressLint({"BanUncheckedReflection"})
        static boolean b(LocationManager locationManager, String str, LocationRequestCompat locationRequestCompat, LocationListenerTransport locationListenerTransport) throws NoSuchMethodException, SecurityException {
            try {
                if (sLocationRequestClass == null) {
                    sLocationRequestClass = Class.forName("android.location.LocationRequest");
                }
                if (sRequestLocationUpdatesLooperMethod == null) {
                    Method declaredMethod = LocationManager.class.getDeclaredMethod("requestLocationUpdates", sLocationRequestClass, LocationListener.class, Looper.class);
                    sRequestLocationUpdatesLooperMethod = declaredMethod;
                    declaredMethod.setAccessible(true);
                }
                LocationRequest locationRequest = locationRequestCompat.toLocationRequest(str);
                if (locationRequest != null) {
                    synchronized (LocationManagerCompat.f3586a) {
                        sRequestLocationUpdatesLooperMethod.invoke(locationManager, locationRequest, locationListenerTransport, Looper.getMainLooper());
                        LocationManagerCompat.c(locationManager, locationListenerTransport);
                    }
                    return true;
                }
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | UnsupportedOperationException | InvocationTargetException unused) {
            }
            return false;
        }
    }

    @RequiresApi(24)
    static class Api24Impl {
        private Api24Impl() {
        }

        @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
        @DoNotInline
        static boolean a(@NonNull LocationManager locationManager, @NonNull GnssMeasurementsEvent$Callback gnssMeasurementsEvent$Callback) {
            return locationManager.registerGnssMeasurementsCallback(gnssMeasurementsEvent$Callback);
        }

        @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
        @DoNotInline
        static boolean b(@NonNull LocationManager locationManager, @NonNull GnssMeasurementsEvent$Callback gnssMeasurementsEvent$Callback, @NonNull Handler handler) {
            return locationManager.registerGnssMeasurementsCallback(gnssMeasurementsEvent$Callback, handler);
        }

        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        @DoNotInline
        static boolean c(LocationManager locationManager, Handler handler, Executor executor, GnssStatusCompat.Callback callback) {
            Preconditions.checkArgument(handler != null);
            SimpleArrayMap simpleArrayMap = GnssListenersHolder.f3588a;
            synchronized (simpleArrayMap) {
                try {
                    PreRGnssStatusTransport preRGnssStatusTransport = (PreRGnssStatusTransport) simpleArrayMap.get(callback);
                    if (preRGnssStatusTransport == null) {
                        preRGnssStatusTransport = new PreRGnssStatusTransport(callback);
                    } else {
                        preRGnssStatusTransport.unregister();
                    }
                    preRGnssStatusTransport.register(executor);
                    if (!locationManager.registerGnssStatusCallback(preRGnssStatusTransport, handler)) {
                        return false;
                    }
                    simpleArrayMap.put(callback, preRGnssStatusTransport);
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @DoNotInline
        static void d(@NonNull LocationManager locationManager, @NonNull GnssMeasurementsEvent$Callback gnssMeasurementsEvent$Callback) {
            locationManager.unregisterGnssMeasurementsCallback(gnssMeasurementsEvent$Callback);
        }

        @DoNotInline
        static void e(LocationManager locationManager, Object obj) {
            if (obj instanceof PreRGnssStatusTransport) {
                ((PreRGnssStatusTransport) obj).unregister();
            }
            locationManager.unregisterGnssStatusCallback((GnssStatus$Callback) obj);
        }
    }

    @RequiresApi(28)
    private static class Api28Impl {
        private Api28Impl() {
        }

        @DoNotInline
        static String a(LocationManager locationManager) {
            return locationManager.getGnssHardwareModelName();
        }

        @DoNotInline
        static int b(LocationManager locationManager) {
            return locationManager.getGnssYearOfHardware();
        }

        @DoNotInline
        static boolean c(LocationManager locationManager) {
            return locationManager.isLocationEnabled();
        }
    }

    @RequiresApi(30)
    private static class Api30Impl {
        private static Class<?> sLocationRequestClass;
        private static Method sRequestLocationUpdatesExecutorMethod;

        private Api30Impl() {
        }

        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        @DoNotInline
        static void a(LocationManager locationManager, @NonNull String str, @Nullable CancellationSignal cancellationSignal, @NonNull Executor executor, @NonNull final Consumer<Location> consumer) {
            Objects.requireNonNull(consumer);
            locationManager.getCurrentLocation(str, cancellationSignal, executor, new java.util.function.Consumer() { // from class: androidx.core.location.s
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    consumer.accept((Location) obj);
                }
            });
        }

        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        @DoNotInline
        public static boolean registerGnssStatusCallback(LocationManager locationManager, Handler handler, Executor executor, GnssStatusCompat.Callback callback) {
            SimpleArrayMap simpleArrayMap = GnssListenersHolder.f3588a;
            synchronized (simpleArrayMap) {
                try {
                    GnssStatusTransport gnssStatusTransport = (GnssStatusTransport) simpleArrayMap.get(callback);
                    if (gnssStatusTransport == null) {
                        gnssStatusTransport = new GnssStatusTransport(callback);
                    }
                    if (!locationManager.registerGnssStatusCallback(executor, gnssStatusTransport)) {
                        return false;
                    }
                    simpleArrayMap.put(callback, gnssStatusTransport);
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @DoNotInline
        public static boolean tryRequestLocationUpdates(LocationManager locationManager, String str, LocationRequestCompat locationRequestCompat, Executor executor, LocationListenerCompat locationListenerCompat) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            if (Build.VERSION.SDK_INT >= 30) {
                try {
                    if (sLocationRequestClass == null) {
                        sLocationRequestClass = Class.forName("android.location.LocationRequest");
                    }
                    if (sRequestLocationUpdatesExecutorMethod == null) {
                        Method declaredMethod = LocationManager.class.getDeclaredMethod("requestLocationUpdates", sLocationRequestClass, Executor.class, LocationListener.class);
                        sRequestLocationUpdatesExecutorMethod = declaredMethod;
                        declaredMethod.setAccessible(true);
                    }
                    LocationRequest locationRequest = locationRequestCompat.toLocationRequest(str);
                    if (locationRequest != null) {
                        sRequestLocationUpdatesExecutorMethod.invoke(locationManager, locationRequest, executor, locationListenerCompat);
                        return true;
                    }
                } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | UnsupportedOperationException | InvocationTargetException unused) {
                }
            }
            return false;
        }
    }

    @RequiresApi(31)
    private static class Api31Impl {
        private Api31Impl() {
        }

        @DoNotInline
        static boolean a(LocationManager locationManager, @NonNull String str) {
            return locationManager.hasProvider(str);
        }

        @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
        @DoNotInline
        static boolean b(@NonNull LocationManager locationManager, @NonNull Executor executor, @NonNull GnssMeasurementsEvent$Callback gnssMeasurementsEvent$Callback) {
            return locationManager.registerGnssMeasurementsCallback(executor, gnssMeasurementsEvent$Callback);
        }

        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        @DoNotInline
        static void c(LocationManager locationManager, @NonNull String str, @NonNull LocationRequest locationRequest, @NonNull Executor executor, @NonNull LocationListener locationListener) {
            locationManager.requestLocationUpdates(str, locationRequest, executor, locationListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class CancellableLocationListener implements LocationListener {

        /* renamed from: a, reason: collision with root package name */
        Runnable f3587a;
        private Consumer<Location> mConsumer;
        private final Executor mExecutor;
        private final LocationManager mLocationManager;
        private final Handler mTimeoutHandler = new Handler(Looper.getMainLooper());

        @GuardedBy("this")
        private boolean mTriggered;

        CancellableLocationListener(LocationManager locationManager, Executor executor, Consumer consumer) {
            this.mLocationManager = locationManager;
            this.mExecutor = executor;
            this.mConsumer = consumer;
        }

        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        private void cleanup() {
            this.mConsumer = null;
            this.mLocationManager.removeUpdates(this);
            Runnable runnable = this.f3587a;
            if (runnable != null) {
                this.mTimeoutHandler.removeCallbacks(runnable);
                this.f3587a = null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startTimeout$0() {
            this.f3587a = null;
            onLocationChanged((Location) null);
        }

        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        public void cancel() {
            synchronized (this) {
                try {
                    if (this.mTriggered) {
                        return;
                    }
                    this.mTriggered = true;
                    cleanup();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.location.LocationListener
        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        public void onLocationChanged(@Nullable final Location location) {
            synchronized (this) {
                try {
                    if (this.mTriggered) {
                        return;
                    }
                    this.mTriggered = true;
                    final Consumer<Location> consumer = this.mConsumer;
                    this.mExecutor.execute(new Runnable() { // from class: androidx.core.location.u
                        @Override // java.lang.Runnable
                        public final void run() {
                            consumer.accept(location);
                        }
                    });
                    cleanup();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.location.LocationListener
        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        public void onProviderDisabled(@NonNull String str) {
            onLocationChanged((Location) null);
        }

        @Override // android.location.LocationListener
        public void onProviderEnabled(@NonNull String str) {
        }

        @Override // android.location.LocationListener
        public void onStatusChanged(String str, int i2, Bundle bundle) {
        }

        @SuppressLint({"MissingPermission"})
        public void startTimeout(long j2) {
            synchronized (this) {
                try {
                    if (this.mTriggered) {
                        return;
                    }
                    Runnable runnable = new Runnable() { // from class: androidx.core.location.t
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f3646a.lambda$startTimeout$0();
                        }
                    };
                    this.f3587a = runnable;
                    this.mTimeoutHandler.postDelayed(runnable, j2);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    private static class GnssListenersHolder {

        /* renamed from: a, reason: collision with root package name */
        static final SimpleArrayMap f3588a = new SimpleArrayMap();

        /* renamed from: b, reason: collision with root package name */
        static final SimpleArrayMap f3589b = new SimpleArrayMap();

        private GnssListenersHolder() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(24)
    static class GnssMeasurementsTransport extends GnssMeasurementsEvent$Callback {

        /* renamed from: a, reason: collision with root package name */
        final GnssMeasurementsEvent$Callback f3590a;

        /* renamed from: b, reason: collision with root package name */
        volatile Executor f3591b;

        GnssMeasurementsTransport(GnssMeasurementsEvent$Callback gnssMeasurementsEvent$Callback, Executor executor) {
            this.f3590a = gnssMeasurementsEvent$Callback;
            this.f3591b = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onGnssMeasurementsReceived$0(Executor executor, GnssMeasurementsEvent gnssMeasurementsEvent) {
            if (this.f3591b != executor) {
                return;
            }
            this.f3590a.onGnssMeasurementsReceived(gnssMeasurementsEvent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStatusChanged$1(Executor executor, int i2) {
            if (this.f3591b != executor) {
                return;
            }
            this.f3590a.onStatusChanged(i2);
        }

        public void onGnssMeasurementsReceived(final GnssMeasurementsEvent gnssMeasurementsEvent) {
            final Executor executor = this.f3591b;
            if (executor == null) {
                return;
            }
            executor.execute(new Runnable() { // from class: androidx.core.location.x
                @Override // java.lang.Runnable
                public final void run() {
                    this.f3649a.lambda$onGnssMeasurementsReceived$0(executor, gnssMeasurementsEvent);
                }
            });
        }

        public void onStatusChanged(final int i2) {
            final Executor executor = this.f3591b;
            if (executor == null) {
                return;
            }
            executor.execute(new Runnable() { // from class: androidx.core.location.y
                @Override // java.lang.Runnable
                public final void run() {
                    this.f3652a.lambda$onStatusChanged$1(executor, i2);
                }
            });
        }

        public void unregister() {
            this.f3591b = null;
        }
    }

    @RequiresApi(30)
    private static class GnssStatusTransport extends GnssStatus$Callback {

        /* renamed from: a, reason: collision with root package name */
        final GnssStatusCompat.Callback f3592a;

        GnssStatusTransport(GnssStatusCompat.Callback callback) {
            Preconditions.checkArgument(callback != null, "invalid null callback");
            this.f3592a = callback;
        }

        public void onFirstFix(int i2) {
            this.f3592a.onFirstFix(i2);
        }

        public void onSatelliteStatusChanged(GnssStatus gnssStatus) {
            this.f3592a.onSatelliteStatusChanged(GnssStatusCompat.wrap(gnssStatus));
        }

        public void onStarted() {
            this.f3592a.onStarted();
        }

        public void onStopped() {
            this.f3592a.onStopped();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class GpsStatusTransport implements GpsStatus.Listener {

        /* renamed from: a, reason: collision with root package name */
        final GnssStatusCompat.Callback f3593a;

        /* renamed from: b, reason: collision with root package name */
        volatile Executor f3594b;
        private final LocationManager mLocationManager;

        GpsStatusTransport(LocationManager locationManager, GnssStatusCompat.Callback callback) {
            Preconditions.checkArgument(callback != null, "invalid null callback");
            this.mLocationManager = locationManager;
            this.f3593a = callback;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onGpsStatusChanged$0(Executor executor) {
            if (this.f3594b != executor) {
                return;
            }
            this.f3593a.onStarted();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onGpsStatusChanged$1(Executor executor) {
            if (this.f3594b != executor) {
                return;
            }
            this.f3593a.onStopped();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onGpsStatusChanged$2(Executor executor, int i2) {
            if (this.f3594b != executor) {
                return;
            }
            this.f3593a.onFirstFix(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onGpsStatusChanged$3(Executor executor, GnssStatusCompat gnssStatusCompat) {
            if (this.f3594b != executor) {
                return;
            }
            this.f3593a.onSatelliteStatusChanged(gnssStatusCompat);
        }

        @Override // android.location.GpsStatus.Listener
        @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
        public void onGpsStatusChanged(int i2) {
            GpsStatus gpsStatus;
            final Executor executor = this.f3594b;
            if (executor == null) {
                return;
            }
            if (i2 == 1) {
                executor.execute(new Runnable() { // from class: androidx.core.location.z
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f3655a.lambda$onGpsStatusChanged$0(executor);
                    }
                });
                return;
            }
            if (i2 == 2) {
                executor.execute(new Runnable() { // from class: androidx.core.location.a0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f3608a.lambda$onGpsStatusChanged$1(executor);
                    }
                });
                return;
            }
            if (i2 != 3) {
                if (i2 == 4 && (gpsStatus = this.mLocationManager.getGpsStatus(null)) != null) {
                    final GnssStatusCompat gnssStatusCompatWrap = GnssStatusCompat.wrap(gpsStatus);
                    executor.execute(new Runnable() { // from class: androidx.core.location.c0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f3613a.lambda$onGpsStatusChanged$3(executor, gnssStatusCompatWrap);
                        }
                    });
                    return;
                }
                return;
            }
            GpsStatus gpsStatus2 = this.mLocationManager.getGpsStatus(null);
            if (gpsStatus2 != null) {
                final int timeToFirstFix = gpsStatus2.getTimeToFirstFix();
                executor.execute(new Runnable() { // from class: androidx.core.location.b0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f3610a.lambda$onGpsStatusChanged$2(executor, timeToFirstFix);
                    }
                });
            }
        }

        public void register(Executor executor) {
            Preconditions.checkState(this.f3594b == null);
            this.f3594b = executor;
        }

        public void unregister() {
            this.f3594b = null;
        }
    }

    private static final class InlineHandlerExecutor implements Executor {
        private final Handler mHandler;

        InlineHandlerExecutor(Handler handler) {
            this.mHandler = (Handler) Preconditions.checkNotNull(handler);
        }

        @Override // java.util.concurrent.Executor
        public void execute(@NonNull Runnable runnable) {
            if (Looper.myLooper() == this.mHandler.getLooper()) {
                runnable.run();
            } else {
                if (this.mHandler.post((Runnable) Preconditions.checkNotNull(runnable))) {
                    return;
                }
                throw new RejectedExecutionException(this.mHandler + " is shutting down");
            }
        }
    }

    private static class LocationListenerKey {

        /* renamed from: a, reason: collision with root package name */
        final String f3595a;

        /* renamed from: b, reason: collision with root package name */
        final LocationListenerCompat f3596b;

        LocationListenerKey(String str, LocationListenerCompat locationListenerCompat) {
            this.f3595a = (String) ObjectsCompat.requireNonNull(str, "invalid null provider");
            this.f3596b = (LocationListenerCompat) ObjectsCompat.requireNonNull(locationListenerCompat, "invalid null listener");
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof LocationListenerKey)) {
                return false;
            }
            LocationListenerKey locationListenerKey = (LocationListenerKey) obj;
            return this.f3595a.equals(locationListenerKey.f3595a) && this.f3596b.equals(locationListenerKey.f3596b);
        }

        public int hashCode() {
            return ObjectsCompat.hash(this.f3595a, this.f3596b);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(24)
    static class PreRGnssStatusTransport extends GnssStatus$Callback {

        /* renamed from: a, reason: collision with root package name */
        final GnssStatusCompat.Callback f3599a;

        /* renamed from: b, reason: collision with root package name */
        volatile Executor f3600b;

        PreRGnssStatusTransport(GnssStatusCompat.Callback callback) {
            Preconditions.checkArgument(callback != null, "invalid null callback");
            this.f3599a = callback;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFirstFix$2(Executor executor, int i2) {
            if (this.f3600b != executor) {
                return;
            }
            this.f3599a.onFirstFix(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSatelliteStatusChanged$3(Executor executor, GnssStatus gnssStatus) {
            if (this.f3600b != executor) {
                return;
            }
            this.f3599a.onSatelliteStatusChanged(GnssStatusCompat.wrap(gnssStatus));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStarted$0(Executor executor) {
            if (this.f3600b != executor) {
                return;
            }
            this.f3599a.onStarted();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStopped$1(Executor executor) {
            if (this.f3600b != executor) {
                return;
            }
            this.f3599a.onStopped();
        }

        public void onFirstFix(final int i2) {
            final Executor executor = this.f3600b;
            if (executor == null) {
                return;
            }
            executor.execute(new Runnable() { // from class: androidx.core.location.j0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f3630a.lambda$onFirstFix$2(executor, i2);
                }
            });
        }

        public void onSatelliteStatusChanged(final GnssStatus gnssStatus) {
            final Executor executor = this.f3600b;
            if (executor == null) {
                return;
            }
            executor.execute(new Runnable() { // from class: androidx.core.location.k0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f3633a.lambda$onSatelliteStatusChanged$3(executor, gnssStatus);
                }
            });
        }

        public void onStarted() {
            final Executor executor = this.f3600b;
            if (executor == null) {
                return;
            }
            executor.execute(new Runnable() { // from class: androidx.core.location.m0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f3638a.lambda$onStarted$0(executor);
                }
            });
        }

        public void onStopped() {
            final Executor executor = this.f3600b;
            if (executor == null) {
                return;
            }
            executor.execute(new Runnable() { // from class: androidx.core.location.l0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f3636a.lambda$onStopped$1(executor);
                }
            });
        }

        public void register(Executor executor) {
            Preconditions.checkArgument(executor != null, "invalid null executor");
            Preconditions.checkState(this.f3600b == null);
            this.f3600b = executor;
        }

        public void unregister() {
            this.f3600b = null;
        }
    }

    private LocationManagerCompat() {
    }

    static void c(LocationManager locationManager, LocationListenerTransport locationListenerTransport) {
        WeakReference weakReference = (WeakReference) f3586a.put(locationListenerTransport.getKey(), new WeakReference(locationListenerTransport));
        LocationListenerTransport locationListenerTransport2 = weakReference != null ? (LocationListenerTransport) weakReference.get() : null;
        if (locationListenerTransport2 != null) {
            locationListenerTransport2.unregister();
            locationManager.removeUpdates(locationListenerTransport2);
        }
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @Deprecated
    public static void getCurrentLocation(@NonNull LocationManager locationManager, @NonNull String str, @Nullable androidx.core.os.CancellationSignal cancellationSignal, @NonNull Executor executor, @NonNull Consumer<Location> consumer) {
        getCurrentLocation(locationManager, str, cancellationSignal != null ? (CancellationSignal) cancellationSignal.getCancellationSignalObject() : null, executor, consumer);
    }

    @Nullable
    public static String getGnssHardwareModelName(@NonNull LocationManager locationManager) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.a(locationManager);
        }
        return null;
    }

    public static int getGnssYearOfHardware(@NonNull LocationManager locationManager) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.b(locationManager);
        }
        return 0;
    }

    public static boolean hasProvider(@NonNull LocationManager locationManager, @NonNull String str) {
        if (Build.VERSION.SDK_INT >= 31) {
            return Api31Impl.a(locationManager, str);
        }
        if (locationManager.getAllProviders().contains(str)) {
            return true;
        }
        try {
            return locationManager.getProvider(str) != null;
        } catch (SecurityException unused) {
            return false;
        }
    }

    public static boolean isLocationEnabled(@NonNull LocationManager locationManager) {
        return Build.VERSION.SDK_INT >= 28 ? Api28Impl.c(locationManager) : locationManager.isProviderEnabled("network") || locationManager.isProviderEnabled("gps");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Boolean lambda$registerGnssStatusCallback$1(LocationManager locationManager, GpsStatusTransport gpsStatusTransport) throws Exception {
        return Boolean.valueOf(locationManager.addGpsStatusListener(gpsStatusTransport));
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    @RequiresApi(24)
    public static boolean registerGnssMeasurementsCallback(@NonNull LocationManager locationManager, @NonNull GnssMeasurementsEvent$Callback gnssMeasurementsEvent$Callback, @NonNull Handler handler) {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 > 30) {
            return Api24Impl.b(locationManager, gnssMeasurementsEvent$Callback, handler);
        }
        if (i2 == 30) {
            return registerGnssMeasurementsCallbackOnR(locationManager, ExecutorCompat.create(handler), gnssMeasurementsEvent$Callback);
        }
        SimpleArrayMap simpleArrayMap = GnssListenersHolder.f3589b;
        synchronized (simpleArrayMap) {
            try {
                unregisterGnssMeasurementsCallback(locationManager, gnssMeasurementsEvent$Callback);
                if (!Api24Impl.b(locationManager, gnssMeasurementsEvent$Callback, handler)) {
                    return false;
                }
                simpleArrayMap.put(gnssMeasurementsEvent$Callback, gnssMeasurementsEvent$Callback);
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @RequiresApi(30)
    private static boolean registerGnssMeasurementsCallbackOnR(@NonNull LocationManager locationManager, @NonNull Executor executor, @NonNull GnssMeasurementsEvent$Callback gnssMeasurementsEvent$Callback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (Build.VERSION.SDK_INT != 30) {
            throw new IllegalStateException();
        }
        try {
            if (sGnssRequestBuilderClass == null) {
                sGnssRequestBuilderClass = Class.forName("android.location.GnssRequest$Builder");
            }
            if (sGnssRequestBuilderBuildMethod == null) {
                Method declaredMethod = sGnssRequestBuilderClass.getDeclaredMethod("build", null);
                sGnssRequestBuilderBuildMethod = declaredMethod;
                declaredMethod.setAccessible(true);
            }
            if (sRegisterGnssMeasurementsCallbackMethod == null) {
                Method declaredMethod2 = LocationManager.class.getDeclaredMethod("registerGnssMeasurementsCallback", Class.forName("android.location.GnssRequest"), Executor.class, o.a());
                sRegisterGnssMeasurementsCallbackMethod = declaredMethod2;
                declaredMethod2.setAccessible(true);
            }
            Object objInvoke = sRegisterGnssMeasurementsCallbackMethod.invoke(locationManager, sGnssRequestBuilderBuildMethod.invoke(sGnssRequestBuilderClass.getDeclaredConstructor(null).newInstance(null), null), executor, gnssMeasurementsEvent$Callback);
            if (objInvoke != null) {
                if (((Boolean) objInvoke).booleanValue()) {
                    return true;
                }
            }
            return false;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException unused) {
            return false;
        }
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public static boolean registerGnssStatusCallback(@NonNull LocationManager locationManager, @NonNull GnssStatusCompat.Callback callback, @NonNull Handler handler) {
        return Build.VERSION.SDK_INT >= 30 ? registerGnssStatusCallback(locationManager, ExecutorCompat.create(handler), callback) : registerGnssStatusCallback(locationManager, new InlineHandlerExecutor(handler), callback);
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public static void removeUpdates(@NonNull LocationManager locationManager, @NonNull LocationListenerCompat locationListenerCompat) {
        WeakHashMap weakHashMap = f3586a;
        synchronized (weakHashMap) {
            try {
                Iterator it = weakHashMap.values().iterator();
                ArrayList arrayList = null;
                while (it.hasNext()) {
                    LocationListenerTransport locationListenerTransport = (LocationListenerTransport) ((WeakReference) it.next()).get();
                    if (locationListenerTransport != null) {
                        LocationListenerKey key = locationListenerTransport.getKey();
                        if (key.f3596b == locationListenerCompat) {
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                            }
                            arrayList.add(key);
                            locationListenerTransport.unregister();
                            locationManager.removeUpdates(locationListenerTransport);
                        }
                    }
                }
                if (arrayList != null) {
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        f3586a.remove((LocationListenerKey) it2.next());
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        locationManager.removeUpdates(locationListenerCompat);
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public static void requestLocationUpdates(@NonNull LocationManager locationManager, @NonNull String str, @NonNull LocationRequestCompat locationRequestCompat, @NonNull Executor executor, @NonNull LocationListenerCompat locationListenerCompat) {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 31) {
            Api31Impl.c(locationManager, str, locationRequestCompat.toLocationRequest(), executor, locationListenerCompat);
            return;
        }
        if (i2 < 30 || !Api30Impl.tryRequestLocationUpdates(locationManager, str, locationRequestCompat, executor, locationListenerCompat)) {
            LocationListenerTransport locationListenerTransport = new LocationListenerTransport(new LocationListenerKey(str, locationListenerCompat), executor);
            if (Api19Impl.b(locationManager, str, locationRequestCompat, locationListenerTransport)) {
                return;
            }
            synchronized (f3586a) {
                locationManager.requestLocationUpdates(str, locationRequestCompat.getIntervalMillis(), locationRequestCompat.getMinUpdateDistanceMeters(), locationListenerTransport, Looper.getMainLooper());
                c(locationManager, locationListenerTransport);
            }
        }
    }

    @RequiresApi(24)
    public static void unregisterGnssMeasurementsCallback(@NonNull LocationManager locationManager, @NonNull GnssMeasurementsEvent$Callback gnssMeasurementsEvent$Callback) {
        if (Build.VERSION.SDK_INT >= 30) {
            Api24Impl.d(locationManager, gnssMeasurementsEvent$Callback);
            return;
        }
        SimpleArrayMap simpleArrayMap = GnssListenersHolder.f3589b;
        synchronized (simpleArrayMap) {
            try {
                GnssMeasurementsEvent$Callback gnssMeasurementsEvent$CallbackA = n.a(simpleArrayMap.remove(gnssMeasurementsEvent$Callback));
                if (gnssMeasurementsEvent$CallbackA != null) {
                    if (gnssMeasurementsEvent$CallbackA instanceof GnssMeasurementsTransport) {
                        ((GnssMeasurementsTransport) gnssMeasurementsEvent$CallbackA).unregister();
                    }
                    Api24Impl.d(locationManager, gnssMeasurementsEvent$CallbackA);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void unregisterGnssStatusCallback(@NonNull LocationManager locationManager, @NonNull GnssStatusCompat.Callback callback) {
        if (Build.VERSION.SDK_INT >= 24) {
            SimpleArrayMap simpleArrayMap = GnssListenersHolder.f3588a;
            synchronized (simpleArrayMap) {
                try {
                    Object objRemove = simpleArrayMap.remove(callback);
                    if (objRemove != null) {
                        Api24Impl.e(locationManager, objRemove);
                    }
                } finally {
                }
            }
            return;
        }
        SimpleArrayMap simpleArrayMap2 = GnssListenersHolder.f3588a;
        synchronized (simpleArrayMap2) {
            try {
                GpsStatusTransport gpsStatusTransport = (GpsStatusTransport) simpleArrayMap2.remove(callback);
                if (gpsStatusTransport != null) {
                    gpsStatusTransport.unregister();
                    locationManager.removeGpsStatusListener(gpsStatusTransport);
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class LocationListenerTransport implements LocationListener {

        /* renamed from: a, reason: collision with root package name */
        volatile LocationListenerKey f3597a;

        /* renamed from: b, reason: collision with root package name */
        final Executor f3598b;

        LocationListenerTransport(LocationListenerKey locationListenerKey, Executor executor) {
            this.f3597a = locationListenerKey;
            this.f3598b = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFlushComplete$2(int i2) {
            LocationListenerKey locationListenerKey = this.f3597a;
            if (locationListenerKey == null) {
                return;
            }
            locationListenerKey.f3596b.onFlushComplete(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLocationChanged$0(Location location) {
            LocationListenerKey locationListenerKey = this.f3597a;
            if (locationListenerKey == null) {
                return;
            }
            locationListenerKey.f3596b.onLocationChanged(location);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLocationChanged$1(List list) {
            LocationListenerKey locationListenerKey = this.f3597a;
            if (locationListenerKey == null) {
                return;
            }
            locationListenerKey.f3596b.onLocationChanged((List<Location>) list);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProviderDisabled$5(String str) {
            LocationListenerKey locationListenerKey = this.f3597a;
            if (locationListenerKey == null) {
                return;
            }
            locationListenerKey.f3596b.onProviderDisabled(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProviderEnabled$4(String str) {
            LocationListenerKey locationListenerKey = this.f3597a;
            if (locationListenerKey == null) {
                return;
            }
            locationListenerKey.f3596b.onProviderEnabled(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStatusChanged$3(String str, int i2, Bundle bundle) {
            LocationListenerKey locationListenerKey = this.f3597a;
            if (locationListenerKey == null) {
                return;
            }
            locationListenerKey.f3596b.onStatusChanged(str, i2, bundle);
        }

        public LocationListenerKey getKey() {
            return (LocationListenerKey) ObjectsCompat.requireNonNull(this.f3597a);
        }

        @Override // android.location.LocationListener
        public void onFlushComplete(final int i2) {
            if (this.f3597a == null) {
                return;
            }
            this.f3598b.execute(new Runnable() { // from class: androidx.core.location.h0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f3624a.lambda$onFlushComplete$2(i2);
                }
            });
        }

        @Override // android.location.LocationListener
        public void onLocationChanged(@NonNull final Location location) {
            if (this.f3597a == null) {
                return;
            }
            this.f3598b.execute(new Runnable() { // from class: androidx.core.location.g0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f3622a.lambda$onLocationChanged$0(location);
                }
            });
        }

        @Override // android.location.LocationListener
        public void onProviderDisabled(@NonNull final String str) {
            if (this.f3597a == null) {
                return;
            }
            this.f3598b.execute(new Runnable() { // from class: androidx.core.location.e0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f3618a.lambda$onProviderDisabled$5(str);
                }
            });
        }

        @Override // android.location.LocationListener
        public void onProviderEnabled(@NonNull final String str) {
            if (this.f3597a == null) {
                return;
            }
            this.f3598b.execute(new Runnable() { // from class: androidx.core.location.d0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f3616a.lambda$onProviderEnabled$4(str);
                }
            });
        }

        @Override // android.location.LocationListener
        public void onStatusChanged(final String str, final int i2, final Bundle bundle) {
            if (this.f3597a == null) {
                return;
            }
            this.f3598b.execute(new Runnable() { // from class: androidx.core.location.i0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f3626a.lambda$onStatusChanged$3(str, i2, bundle);
                }
            });
        }

        public void unregister() {
            this.f3597a = null;
        }

        @Override // android.location.LocationListener
        public void onLocationChanged(@NonNull final List<Location> list) {
            if (this.f3597a == null) {
                return;
            }
            this.f3598b.execute(new Runnable() { // from class: androidx.core.location.f0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f3620a.lambda$onLocationChanged$1(list);
                }
            });
        }
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public static void getCurrentLocation(@NonNull LocationManager locationManager, @NonNull String str, @Nullable CancellationSignal cancellationSignal, @NonNull Executor executor, @NonNull final Consumer<Location> consumer) {
        if (Build.VERSION.SDK_INT >= 30) {
            Api30Impl.a(locationManager, str, cancellationSignal, executor, consumer);
            return;
        }
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        final Location lastKnownLocation = locationManager.getLastKnownLocation(str);
        if (lastKnownLocation != null && SystemClock.elapsedRealtime() - LocationCompat.getElapsedRealtimeMillis(lastKnownLocation) < 10000) {
            executor.execute(new Runnable() { // from class: androidx.core.location.p
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(lastKnownLocation);
                }
            });
            return;
        }
        final CancellableLocationListener cancellableLocationListener = new CancellableLocationListener(locationManager, executor, consumer);
        locationManager.requestLocationUpdates(str, 0L, 0.0f, cancellableLocationListener, Looper.getMainLooper());
        if (cancellationSignal != null) {
            cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.core.location.q
                @Override // android.os.CancellationSignal.OnCancelListener
                public final void onCancel() {
                    cancellableLocationListener.cancel();
                }
            });
        }
        cancellableLocationListener.startTimeout(30000L);
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public static boolean registerGnssStatusCallback(@NonNull LocationManager locationManager, @NonNull Executor executor, @NonNull GnssStatusCompat.Callback callback) {
        if (Build.VERSION.SDK_INT >= 30) {
            return registerGnssStatusCallback(locationManager, null, executor, callback);
        }
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper == null) {
            looperMyLooper = Looper.getMainLooper();
        }
        return registerGnssStatusCallback(locationManager, new Handler(looperMyLooper), executor, callback);
    }

    /* JADX WARN: Removed duplicated region for block: B:64:0x00cd A[Catch: all -> 0x00a8, TryCatch #6 {all -> 0x00a8, blocks: (B:27:0x0056, B:60:0x00ae, B:61:0x00c4, B:62:0x00c5, B:64:0x00cd, B:66:0x00d5, B:67:0x00db, B:68:0x00dc, B:69:0x00e1, B:70:0x00e2, B:71:0x00e8), top: B:79:0x0056 }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00e2 A[Catch: all -> 0x00a8, TryCatch #6 {all -> 0x00a8, blocks: (B:27:0x0056, B:60:0x00ae, B:61:0x00c4, B:62:0x00c5, B:64:0x00cd, B:66:0x00d5, B:67:0x00db, B:68:0x00dc, B:69:0x00e1, B:70:0x00e2, B:71:0x00e8), top: B:79:0x0056 }] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00eb A[Catch: all -> 0x002f, TRY_ENTER, TryCatch #10 {all -> 0x002f, blocks: (B:16:0x0021, B:18:0x0029, B:22:0x0035, B:24:0x004c, B:33:0x0079, B:34:0x0080, B:43:0x008e, B:44:0x0095, B:73:0x00eb, B:74:0x00f2, B:25:0x0050, B:75:0x00f3, B:76:0x0109, B:21:0x0032), top: B:80:0x0021 }] */
    @androidx.annotation.RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean registerGnssStatusCallback(final android.location.LocationManager r9, android.os.Handler r10, java.util.concurrent.Executor r11, androidx.core.location.GnssStatusCompat.Callback r12) {
        /*
            Method dump skipped, instructions count: 268
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.location.LocationManagerCompat.registerGnssStatusCallback(android.location.LocationManager, android.os.Handler, java.util.concurrent.Executor, androidx.core.location.GnssStatusCompat$Callback):boolean");
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    @RequiresApi(24)
    public static boolean registerGnssMeasurementsCallback(@NonNull LocationManager locationManager, @NonNull Executor executor, @NonNull GnssMeasurementsEvent$Callback gnssMeasurementsEvent$Callback) {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 > 30) {
            return Api31Impl.b(locationManager, executor, gnssMeasurementsEvent$Callback);
        }
        if (i2 == 30) {
            return registerGnssMeasurementsCallbackOnR(locationManager, executor, gnssMeasurementsEvent$Callback);
        }
        SimpleArrayMap simpleArrayMap = GnssListenersHolder.f3589b;
        synchronized (simpleArrayMap) {
            try {
                GnssMeasurementsTransport gnssMeasurementsTransport = new GnssMeasurementsTransport(gnssMeasurementsEvent$Callback, executor);
                unregisterGnssMeasurementsCallback(locationManager, gnssMeasurementsEvent$Callback);
                if (!Api24Impl.a(locationManager, gnssMeasurementsTransport)) {
                    return false;
                }
                simpleArrayMap.put(gnssMeasurementsEvent$Callback, gnssMeasurementsTransport);
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public static void requestLocationUpdates(@NonNull LocationManager locationManager, @NonNull String str, @NonNull LocationRequestCompat locationRequestCompat, @NonNull LocationListenerCompat locationListenerCompat, @NonNull Looper looper) {
        if (Build.VERSION.SDK_INT >= 31) {
            Api31Impl.c(locationManager, str, locationRequestCompat.toLocationRequest(), ExecutorCompat.create(new Handler(looper)), locationListenerCompat);
        } else {
            if (Api19Impl.a(locationManager, str, locationRequestCompat, locationListenerCompat, looper)) {
                return;
            }
            locationManager.requestLocationUpdates(str, locationRequestCompat.getIntervalMillis(), locationRequestCompat.getMinUpdateDistanceMeters(), locationListenerCompat, looper);
        }
    }
}
