package io.flutter.view;

import android.hardware.display.DisplayManager;
import android.view.Choreographer;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import io.flutter.embedding.engine.FlutterJNI;
import java.util.Objects;

/* loaded from: classes4.dex */
public class VsyncWaiter {
    private static VsyncWaiter instance;
    private static DisplayListener listener;
    private FlutterJNI flutterJNI;
    private long refreshPeriodNanos = -1;
    private FrameCallback frameCallback = new FrameCallback(0);
    private final FlutterJNI.AsyncWaitForVsyncDelegate asyncWaitForVsyncDelegate = new FlutterJNI.AsyncWaitForVsyncDelegate() { // from class: io.flutter.view.VsyncWaiter.1
        private Choreographer.FrameCallback obtainFrameCallback(long j2) {
            if (VsyncWaiter.this.frameCallback == null) {
                return VsyncWaiter.this.new FrameCallback(j2);
            }
            VsyncWaiter.this.frameCallback.cookie = j2;
            FrameCallback frameCallback = VsyncWaiter.this.frameCallback;
            VsyncWaiter.this.frameCallback = null;
            return frameCallback;
        }

        @Override // io.flutter.embedding.engine.FlutterJNI.AsyncWaitForVsyncDelegate
        public void asyncWaitForVsync(long j2) {
            Choreographer.getInstance().postFrameCallback(obtainFrameCallback(j2));
        }
    };

    class DisplayListener implements DisplayManager.DisplayListener {
        private DisplayManager displayManager;

        DisplayListener(DisplayManager displayManager) {
            this.displayManager = displayManager;
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i2) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i2) {
            if (i2 == 0) {
                float refreshRate = this.displayManager.getDisplay(0).getRefreshRate();
                VsyncWaiter.this.refreshPeriodNanos = (long) (1.0E9d / refreshRate);
                VsyncWaiter.this.flutterJNI.setRefreshRateFPS(refreshRate);
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i2) {
        }

        void register() {
            this.displayManager.registerDisplayListener(this, null);
        }
    }

    private class FrameCallback implements Choreographer.FrameCallback {
        private long cookie;

        FrameCallback(long j2) {
            this.cookie = j2;
        }

        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long j2) {
            long jNanoTime = System.nanoTime() - j2;
            VsyncWaiter.this.flutterJNI.onVsync(jNanoTime < 0 ? 0L : jNanoTime, VsyncWaiter.this.refreshPeriodNanos, this.cookie);
            VsyncWaiter.this.frameCallback = this;
        }
    }

    private VsyncWaiter(@NonNull FlutterJNI flutterJNI) {
        this.flutterJNI = flutterJNI;
    }

    @NonNull
    public static VsyncWaiter getInstance(float f2, @NonNull FlutterJNI flutterJNI) {
        if (instance == null) {
            instance = new VsyncWaiter(flutterJNI);
        }
        flutterJNI.setRefreshRateFPS(f2);
        VsyncWaiter vsyncWaiter = instance;
        vsyncWaiter.refreshPeriodNanos = (long) (1.0E9d / f2);
        return vsyncWaiter;
    }

    @VisibleForTesting
    public static void reset() {
        instance = null;
        listener = null;
    }

    public void init() {
        this.flutterJNI.setAsyncWaitForVsyncDelegate(this.asyncWaitForVsyncDelegate);
    }

    @NonNull
    public static VsyncWaiter getInstance(@NonNull DisplayManager displayManager, @NonNull FlutterJNI flutterJNI) {
        if (instance == null) {
            instance = new VsyncWaiter(flutterJNI);
        }
        if (listener == null) {
            VsyncWaiter vsyncWaiter = instance;
            Objects.requireNonNull(vsyncWaiter);
            DisplayListener displayListener = vsyncWaiter.new DisplayListener(displayManager);
            listener = displayListener;
            displayListener.register();
        }
        if (instance.refreshPeriodNanos == -1) {
            float refreshRate = displayManager.getDisplay(0).getRefreshRate();
            instance.refreshPeriodNanos = (long) (1.0E9d / refreshRate);
            flutterJNI.setRefreshRateFPS(refreshRate);
        }
        return instance;
    }
}
