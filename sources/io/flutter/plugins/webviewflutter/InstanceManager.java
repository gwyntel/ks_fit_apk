package io.flutter.plugins.webviewflutter;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.WeakHashMap;

/* loaded from: classes4.dex */
public class InstanceManager {
    private static final long CLEAR_FINALIZED_WEAK_REFERENCES_INTERVAL = 3000;
    private static final long MIN_HOST_CREATED_IDENTIFIER = 65536;
    private static final String TAG = "InstanceManager";
    private final FinalizationListener finalizationListener;
    private final Handler handler;
    private boolean hasFinalizationListenerStopped;
    private long nextIdentifier;
    private final WeakHashMap<Object, Long> identifiers = new WeakHashMap<>();
    private final HashMap<Long, WeakReference<Object>> weakInstances = new HashMap<>();
    private final HashMap<Long, Object> strongInstances = new HashMap<>();
    private final ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
    private final HashMap<WeakReference<Object>, Long> weakReferencesToIdentifiers = new HashMap<>();

    public interface FinalizationListener {
        void onFinalize(long j2);
    }

    private InstanceManager(FinalizationListener finalizationListener) {
        Handler handler = new Handler(Looper.getMainLooper());
        this.handler = handler;
        this.nextIdentifier = 65536L;
        this.hasFinalizationListenerStopped = false;
        this.finalizationListener = finalizationListener;
        handler.postDelayed(new m3(this), 3000L);
    }

    private void addInstance(Object obj, long j2) {
        if (j2 < 0) {
            throw new IllegalArgumentException(String.format("Identifier must be >= 0: %d", Long.valueOf(j2)));
        }
        if (this.weakInstances.containsKey(Long.valueOf(j2))) {
            throw new IllegalArgumentException(String.format("Identifier has already been added: %d", Long.valueOf(j2)));
        }
        WeakReference<Object> weakReference = new WeakReference<>(obj, this.referenceQueue);
        this.identifiers.put(obj, Long.valueOf(j2));
        this.weakInstances.put(Long.valueOf(j2), weakReference);
        this.weakReferencesToIdentifiers.put(weakReference, Long.valueOf(j2));
        this.strongInstances.put(Long.valueOf(j2), obj);
    }

    @NonNull
    public static InstanceManager create(@NonNull FinalizationListener finalizationListener) {
        return new InstanceManager(finalizationListener);
    }

    private void logWarningIfFinalizationListenerHasStopped() {
        if (hasFinalizationListenerStopped()) {
            Log.w(TAG, "The manager was used after calls to the FinalizationListener have been stopped.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseAllFinalizedInstances() {
        if (hasFinalizationListenerStopped()) {
            return;
        }
        while (true) {
            WeakReference weakReference = (WeakReference) this.referenceQueue.poll();
            if (weakReference == null) {
                this.handler.postDelayed(new m3(this), 3000L);
                return;
            }
            Long lRemove = this.weakReferencesToIdentifiers.remove(weakReference);
            if (lRemove != null) {
                this.weakInstances.remove(lRemove);
                this.strongInstances.remove(lRemove);
                this.finalizationListener.onFinalize(lRemove.longValue());
            }
        }
    }

    public void addDartCreatedInstance(@NonNull Object obj, long j2) {
        logWarningIfFinalizationListenerHasStopped();
        addInstance(obj, j2);
    }

    public long addHostCreatedInstance(@NonNull Object obj) {
        logWarningIfFinalizationListenerHasStopped();
        if (!containsInstance(obj)) {
            long j2 = this.nextIdentifier;
            this.nextIdentifier = 1 + j2;
            addInstance(obj, j2);
            return j2;
        }
        throw new IllegalArgumentException("Instance of " + obj.getClass() + " has already been added.");
    }

    public void clear() {
        this.identifiers.clear();
        this.weakInstances.clear();
        this.strongInstances.clear();
        this.weakReferencesToIdentifiers.clear();
    }

    public boolean containsInstance(@Nullable Object obj) {
        logWarningIfFinalizationListenerHasStopped();
        return this.identifiers.containsKey(obj);
    }

    @Nullable
    public Long getIdentifierForStrongReference(@Nullable Object obj) {
        logWarningIfFinalizationListenerHasStopped();
        Long l2 = this.identifiers.get(obj);
        if (l2 != null) {
            this.strongInstances.put(l2, obj);
        }
        return l2;
    }

    @Nullable
    public <T> T getInstance(long j2) {
        logWarningIfFinalizationListenerHasStopped();
        WeakReference<Object> weakReference = this.weakInstances.get(Long.valueOf(j2));
        if (weakReference != null) {
            return (T) weakReference.get();
        }
        return null;
    }

    public boolean hasFinalizationListenerStopped() {
        return this.hasFinalizationListenerStopped;
    }

    @Nullable
    public <T> T remove(long j2) {
        logWarningIfFinalizationListenerHasStopped();
        return (T) this.strongInstances.remove(Long.valueOf(j2));
    }

    public void stopFinalizationListener() {
        this.handler.removeCallbacks(new m3(this));
        this.hasFinalizationListenerStopped = true;
    }
}
