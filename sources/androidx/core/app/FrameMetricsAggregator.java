package androidx.core.app;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.SparseIntArray;
import android.view.FrameMetrics;
import android.view.Window;
import android.view.Window$OnFrameMetricsAvailableListener;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class FrameMetricsAggregator {
    public static final int ANIMATION_DURATION = 256;
    public static final int ANIMATION_INDEX = 8;
    public static final int COMMAND_DURATION = 32;
    public static final int COMMAND_INDEX = 5;
    public static final int DELAY_DURATION = 128;
    public static final int DELAY_INDEX = 7;
    public static final int DRAW_DURATION = 8;
    public static final int DRAW_INDEX = 3;
    public static final int EVERY_DURATION = 511;
    public static final int INPUT_DURATION = 2;
    public static final int INPUT_INDEX = 1;
    private static final int LAST_INDEX = 8;
    public static final int LAYOUT_MEASURE_DURATION = 4;
    public static final int LAYOUT_MEASURE_INDEX = 2;
    public static final int SWAP_DURATION = 64;
    public static final int SWAP_INDEX = 6;
    public static final int SYNC_DURATION = 16;
    public static final int SYNC_INDEX = 4;
    public static final int TOTAL_DURATION = 1;
    public static final int TOTAL_INDEX = 0;
    private final FrameMetricsBaseImpl mInstance;

    @RequiresApi(24)
    private static class FrameMetricsApi24Impl extends FrameMetricsBaseImpl {
        private static final int NANOS_PER_MS = 1000000;
        private static final int NANOS_ROUNDING_VALUE = 500000;
        private static Handler sHandler;
        private static HandlerThread sHandlerThread;

        /* renamed from: a, reason: collision with root package name */
        int f3387a;

        /* renamed from: b, reason: collision with root package name */
        SparseIntArray[] f3388b = new SparseIntArray[9];
        private final ArrayList<WeakReference<Activity>> mActivities = new ArrayList<>();

        /* renamed from: c, reason: collision with root package name */
        Window$OnFrameMetricsAvailableListener f3389c = new Window$OnFrameMetricsAvailableListener() { // from class: androidx.core.app.FrameMetricsAggregator.FrameMetricsApi24Impl.1
            public void onFrameMetricsAvailable(Window window, FrameMetrics frameMetrics, int i2) {
                FrameMetricsApi24Impl frameMetricsApi24Impl = FrameMetricsApi24Impl.this;
                if ((frameMetricsApi24Impl.f3387a & 1) != 0) {
                    frameMetricsApi24Impl.a(frameMetricsApi24Impl.f3388b[0], frameMetrics.getMetric(8));
                }
                FrameMetricsApi24Impl frameMetricsApi24Impl2 = FrameMetricsApi24Impl.this;
                if ((frameMetricsApi24Impl2.f3387a & 2) != 0) {
                    frameMetricsApi24Impl2.a(frameMetricsApi24Impl2.f3388b[1], frameMetrics.getMetric(1));
                }
                FrameMetricsApi24Impl frameMetricsApi24Impl3 = FrameMetricsApi24Impl.this;
                if ((frameMetricsApi24Impl3.f3387a & 4) != 0) {
                    frameMetricsApi24Impl3.a(frameMetricsApi24Impl3.f3388b[2], frameMetrics.getMetric(3));
                }
                FrameMetricsApi24Impl frameMetricsApi24Impl4 = FrameMetricsApi24Impl.this;
                if ((frameMetricsApi24Impl4.f3387a & 8) != 0) {
                    frameMetricsApi24Impl4.a(frameMetricsApi24Impl4.f3388b[3], frameMetrics.getMetric(4));
                }
                FrameMetricsApi24Impl frameMetricsApi24Impl5 = FrameMetricsApi24Impl.this;
                if ((frameMetricsApi24Impl5.f3387a & 16) != 0) {
                    frameMetricsApi24Impl5.a(frameMetricsApi24Impl5.f3388b[4], frameMetrics.getMetric(5));
                }
                FrameMetricsApi24Impl frameMetricsApi24Impl6 = FrameMetricsApi24Impl.this;
                if ((frameMetricsApi24Impl6.f3387a & 64) != 0) {
                    frameMetricsApi24Impl6.a(frameMetricsApi24Impl6.f3388b[6], frameMetrics.getMetric(7));
                }
                FrameMetricsApi24Impl frameMetricsApi24Impl7 = FrameMetricsApi24Impl.this;
                if ((frameMetricsApi24Impl7.f3387a & 32) != 0) {
                    frameMetricsApi24Impl7.a(frameMetricsApi24Impl7.f3388b[5], frameMetrics.getMetric(6));
                }
                FrameMetricsApi24Impl frameMetricsApi24Impl8 = FrameMetricsApi24Impl.this;
                if ((frameMetricsApi24Impl8.f3387a & 128) != 0) {
                    frameMetricsApi24Impl8.a(frameMetricsApi24Impl8.f3388b[7], frameMetrics.getMetric(0));
                }
                FrameMetricsApi24Impl frameMetricsApi24Impl9 = FrameMetricsApi24Impl.this;
                if ((frameMetricsApi24Impl9.f3387a & 256) != 0) {
                    frameMetricsApi24Impl9.a(frameMetricsApi24Impl9.f3388b[8], frameMetrics.getMetric(2));
                }
            }
        };

        FrameMetricsApi24Impl(int i2) {
            this.f3387a = i2;
        }

        void a(SparseIntArray sparseIntArray, long j2) {
            if (sparseIntArray != null) {
                int i2 = (int) ((500000 + j2) / 1000000);
                if (j2 >= 0) {
                    sparseIntArray.put(i2, sparseIntArray.get(i2) + 1);
                }
            }
        }

        @Override // androidx.core.app.FrameMetricsAggregator.FrameMetricsBaseImpl
        public void add(Activity activity) {
            if (sHandlerThread == null) {
                HandlerThread handlerThread = new HandlerThread("FrameMetricsAggregator");
                sHandlerThread = handlerThread;
                handlerThread.start();
                sHandler = new Handler(sHandlerThread.getLooper());
            }
            for (int i2 = 0; i2 <= 8; i2++) {
                SparseIntArray[] sparseIntArrayArr = this.f3388b;
                if (sparseIntArrayArr[i2] == null && (this.f3387a & (1 << i2)) != 0) {
                    sparseIntArrayArr[i2] = new SparseIntArray();
                }
            }
            activity.getWindow().addOnFrameMetricsAvailableListener(this.f3389c, sHandler);
            this.mActivities.add(new WeakReference<>(activity));
        }

        @Override // androidx.core.app.FrameMetricsAggregator.FrameMetricsBaseImpl
        public SparseIntArray[] getMetrics() {
            return this.f3388b;
        }

        @Override // androidx.core.app.FrameMetricsAggregator.FrameMetricsBaseImpl
        public SparseIntArray[] remove(Activity activity) {
            Iterator<WeakReference<Activity>> it = this.mActivities.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                WeakReference<Activity> next = it.next();
                if (next.get() == activity) {
                    this.mActivities.remove(next);
                    break;
                }
            }
            activity.getWindow().removeOnFrameMetricsAvailableListener(this.f3389c);
            return this.f3388b;
        }

        @Override // androidx.core.app.FrameMetricsAggregator.FrameMetricsBaseImpl
        public SparseIntArray[] reset() {
            SparseIntArray[] sparseIntArrayArr = this.f3388b;
            this.f3388b = new SparseIntArray[9];
            return sparseIntArrayArr;
        }

        @Override // androidx.core.app.FrameMetricsAggregator.FrameMetricsBaseImpl
        public SparseIntArray[] stop() {
            for (int size = this.mActivities.size() - 1; size >= 0; size--) {
                WeakReference<Activity> weakReference = this.mActivities.get(size);
                Activity activity = weakReference.get();
                if (weakReference.get() != null) {
                    activity.getWindow().removeOnFrameMetricsAvailableListener(this.f3389c);
                    this.mActivities.remove(size);
                }
            }
            return this.f3388b;
        }
    }

    private static class FrameMetricsBaseImpl {
        FrameMetricsBaseImpl() {
        }

        public void add(Activity activity) {
        }

        public SparseIntArray[] getMetrics() {
            return null;
        }

        public SparseIntArray[] remove(Activity activity) {
            return null;
        }

        public SparseIntArray[] reset() {
            return null;
        }

        public SparseIntArray[] stop() {
            return null;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public @interface MetricType {
    }

    public FrameMetricsAggregator() {
        this(1);
    }

    public void add(@NonNull Activity activity) {
        this.mInstance.add(activity);
    }

    @Nullable
    public SparseIntArray[] getMetrics() {
        return this.mInstance.getMetrics();
    }

    @Nullable
    public SparseIntArray[] remove(@NonNull Activity activity) {
        return this.mInstance.remove(activity);
    }

    @Nullable
    public SparseIntArray[] reset() {
        return this.mInstance.reset();
    }

    @Nullable
    public SparseIntArray[] stop() {
        return this.mInstance.stop();
    }

    public FrameMetricsAggregator(int i2) {
        if (Build.VERSION.SDK_INT >= 24) {
            this.mInstance = new FrameMetricsApi24Impl(i2);
        } else {
            this.mInstance = new FrameMetricsBaseImpl();
        }
    }
}
