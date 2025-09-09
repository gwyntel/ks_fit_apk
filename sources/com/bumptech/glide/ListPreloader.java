package com.bumptech.glide;

import android.graphics.drawable.Drawable;
import android.widget.AbsListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.util.Util;
import java.util.List;
import java.util.Queue;

/* loaded from: classes3.dex */
public class ListPreloader<T> implements AbsListView.OnScrollListener {
    private int lastEnd;
    private int lastStart;
    private final int maxPreload;
    private final PreloadSizeProvider<T> preloadDimensionProvider;
    private final PreloadModelProvider<T> preloadModelProvider;
    private final PreloadTargetQueue preloadTargetQueue;
    private final RequestManager requestManager;
    private int totalItemCount;
    private int lastFirstVisible = -1;
    private boolean isIncreasing = true;

    public interface PreloadModelProvider<U> {
        @NonNull
        List<U> getPreloadItems(int i2);

        @Nullable
        RequestBuilder<?> getPreloadRequestBuilder(@NonNull U u2);
    }

    public interface PreloadSizeProvider<T> {
        @Nullable
        int[] getPreloadSize(@NonNull T t2, int i2, int i3);
    }

    private static final class PreloadTarget implements Target<Object> {

        /* renamed from: a, reason: collision with root package name */
        int f12248a;

        /* renamed from: b, reason: collision with root package name */
        int f12249b;

        @Nullable
        private Request request;

        PreloadTarget() {
        }

        @Override // com.bumptech.glide.request.target.Target
        @Nullable
        public Request getRequest() {
            return this.request;
        }

        @Override // com.bumptech.glide.request.target.Target
        public void getSize(@NonNull SizeReadyCallback sizeReadyCallback) {
            sizeReadyCallback.onSizeReady(this.f12249b, this.f12248a);
        }

        @Override // com.bumptech.glide.manager.LifecycleListener
        public void onDestroy() {
        }

        @Override // com.bumptech.glide.request.target.Target
        public void onLoadCleared(@Nullable Drawable drawable) {
        }

        @Override // com.bumptech.glide.request.target.Target
        public void onLoadFailed(@Nullable Drawable drawable) {
        }

        @Override // com.bumptech.glide.request.target.Target
        public void onLoadStarted(@Nullable Drawable drawable) {
        }

        @Override // com.bumptech.glide.request.target.Target
        public void onResourceReady(@NonNull Object obj, @Nullable Transition<? super Object> transition) {
        }

        @Override // com.bumptech.glide.manager.LifecycleListener
        public void onStart() {
        }

        @Override // com.bumptech.glide.manager.LifecycleListener
        public void onStop() {
        }

        @Override // com.bumptech.glide.request.target.Target
        public void removeCallback(@NonNull SizeReadyCallback sizeReadyCallback) {
        }

        @Override // com.bumptech.glide.request.target.Target
        public void setRequest(@Nullable Request request) {
            this.request = request;
        }
    }

    private static final class PreloadTargetQueue {

        /* renamed from: a, reason: collision with root package name */
        final Queue f12250a;

        PreloadTargetQueue(int i2) {
            this.f12250a = Util.createQueue(i2);
            for (int i3 = 0; i3 < i2; i3++) {
                this.f12250a.offer(new PreloadTarget());
            }
        }

        public PreloadTarget next(int i2, int i3) {
            PreloadTarget preloadTarget = (PreloadTarget) this.f12250a.poll();
            this.f12250a.offer(preloadTarget);
            preloadTarget.f12249b = i2;
            preloadTarget.f12248a = i3;
            return preloadTarget;
        }
    }

    public ListPreloader(@NonNull RequestManager requestManager, @NonNull PreloadModelProvider<T> preloadModelProvider, @NonNull PreloadSizeProvider<T> preloadSizeProvider, int i2) {
        this.requestManager = requestManager;
        this.preloadModelProvider = preloadModelProvider;
        this.preloadDimensionProvider = preloadSizeProvider;
        this.maxPreload = i2;
        this.preloadTargetQueue = new PreloadTargetQueue(i2 + 1);
    }

    private void cancelAll() {
        for (int i2 = 0; i2 < this.preloadTargetQueue.f12250a.size(); i2++) {
            this.requestManager.clear(this.preloadTargetQueue.next(0, 0));
        }
    }

    private void preload(int i2, boolean z2) {
        if (this.isIncreasing != z2) {
            this.isIncreasing = z2;
            cancelAll();
        }
        preload(i2, (z2 ? this.maxPreload : -this.maxPreload) + i2);
    }

    private void preloadAdapterPosition(List<T> list, int i2, boolean z2) {
        int size = list.size();
        if (z2) {
            for (int i3 = 0; i3 < size; i3++) {
                preloadItem(list.get(i3), i2, i3);
            }
            return;
        }
        for (int i4 = size - 1; i4 >= 0; i4--) {
            preloadItem(list.get(i4), i2, i4);
        }
    }

    private void preloadItem(@Nullable T t2, int i2, int i3) {
        int[] preloadSize;
        RequestBuilder<?> preloadRequestBuilder;
        if (t2 == null || (preloadSize = this.preloadDimensionProvider.getPreloadSize(t2, i2, i3)) == null || (preloadRequestBuilder = this.preloadModelProvider.getPreloadRequestBuilder(t2)) == null) {
            return;
        }
        preloadRequestBuilder.into((RequestBuilder<?>) this.preloadTargetQueue.next(preloadSize[0], preloadSize[1]));
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public void onScroll(AbsListView absListView, int i2, int i3, int i4) {
        this.totalItemCount = i4;
        int i5 = this.lastFirstVisible;
        if (i2 > i5) {
            preload(i3 + i2, true);
        } else if (i2 < i5) {
            preload(i2, false);
        }
        this.lastFirstVisible = i2;
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public void onScrollStateChanged(AbsListView absListView, int i2) {
    }

    private void preload(int i2, int i3) {
        int iMin;
        int iMax;
        if (i2 < i3) {
            iMax = Math.max(this.lastEnd, i2);
            iMin = i3;
        } else {
            iMin = Math.min(this.lastStart, i2);
            iMax = i3;
        }
        int iMin2 = Math.min(this.totalItemCount, iMin);
        int iMin3 = Math.min(this.totalItemCount, Math.max(0, iMax));
        if (i2 < i3) {
            for (int i4 = iMin3; i4 < iMin2; i4++) {
                preloadAdapterPosition(this.preloadModelProvider.getPreloadItems(i4), i4, true);
            }
        } else {
            for (int i5 = iMin2 - 1; i5 >= iMin3; i5--) {
                preloadAdapterPosition(this.preloadModelProvider.getPreloadItems(i5), i5, false);
            }
        }
        this.lastStart = iMin3;
        this.lastEnd = iMin2;
    }
}
