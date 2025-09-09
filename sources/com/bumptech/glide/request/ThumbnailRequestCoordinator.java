package com.bumptech.glide.request;

import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.RequestCoordinator;

/* loaded from: classes3.dex */
public class ThumbnailRequestCoordinator implements RequestCoordinator, Request {
    private volatile Request full;

    @GuardedBy("requestLock")
    private RequestCoordinator.RequestState fullState;

    @GuardedBy("requestLock")
    private boolean isRunningDuringBegin;

    @Nullable
    private final RequestCoordinator parent;
    private final Object requestLock;
    private volatile Request thumb;

    @GuardedBy("requestLock")
    private RequestCoordinator.RequestState thumbState;

    public ThumbnailRequestCoordinator(Object obj, @Nullable RequestCoordinator requestCoordinator) {
        RequestCoordinator.RequestState requestState = RequestCoordinator.RequestState.CLEARED;
        this.fullState = requestState;
        this.thumbState = requestState;
        this.requestLock = obj;
        this.parent = requestCoordinator;
    }

    @GuardedBy("requestLock")
    private boolean parentCanNotifyCleared() {
        RequestCoordinator requestCoordinator = this.parent;
        return requestCoordinator == null || requestCoordinator.canNotifyCleared(this);
    }

    @GuardedBy("requestLock")
    private boolean parentCanNotifyStatusChanged() {
        RequestCoordinator requestCoordinator = this.parent;
        return requestCoordinator == null || requestCoordinator.canNotifyStatusChanged(this);
    }

    @GuardedBy("requestLock")
    private boolean parentCanSetImage() {
        RequestCoordinator requestCoordinator = this.parent;
        return requestCoordinator == null || requestCoordinator.canSetImage(this);
    }

    @Override // com.bumptech.glide.request.Request
    public void begin() {
        synchronized (this.requestLock) {
            try {
                this.isRunningDuringBegin = true;
                try {
                    if (this.fullState != RequestCoordinator.RequestState.SUCCESS) {
                        RequestCoordinator.RequestState requestState = this.thumbState;
                        RequestCoordinator.RequestState requestState2 = RequestCoordinator.RequestState.RUNNING;
                        if (requestState != requestState2) {
                            this.thumbState = requestState2;
                            this.thumb.begin();
                        }
                    }
                    if (this.isRunningDuringBegin) {
                        RequestCoordinator.RequestState requestState3 = this.fullState;
                        RequestCoordinator.RequestState requestState4 = RequestCoordinator.RequestState.RUNNING;
                        if (requestState3 != requestState4) {
                            this.fullState = requestState4;
                            this.full.begin();
                        }
                    }
                    this.isRunningDuringBegin = false;
                } catch (Throwable th) {
                    this.isRunningDuringBegin = false;
                    throw th;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public boolean canNotifyCleared(Request request) {
        boolean z2;
        synchronized (this.requestLock) {
            try {
                z2 = parentCanNotifyCleared() && request.equals(this.full) && this.fullState != RequestCoordinator.RequestState.PAUSED;
            } finally {
            }
        }
        return z2;
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public boolean canNotifyStatusChanged(Request request) {
        boolean z2;
        synchronized (this.requestLock) {
            try {
                z2 = parentCanNotifyStatusChanged() && request.equals(this.full) && !isAnyResourceSet();
            } finally {
            }
        }
        return z2;
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public boolean canSetImage(Request request) {
        boolean z2;
        synchronized (this.requestLock) {
            try {
                z2 = parentCanSetImage() && (request.equals(this.full) || this.fullState != RequestCoordinator.RequestState.SUCCESS);
            } finally {
            }
        }
        return z2;
    }

    @Override // com.bumptech.glide.request.Request
    public void clear() {
        synchronized (this.requestLock) {
            this.isRunningDuringBegin = false;
            RequestCoordinator.RequestState requestState = RequestCoordinator.RequestState.CLEARED;
            this.fullState = requestState;
            this.thumbState = requestState;
            this.thumb.clear();
            this.full.clear();
        }
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public RequestCoordinator getRoot() {
        RequestCoordinator root;
        synchronized (this.requestLock) {
            try {
                RequestCoordinator requestCoordinator = this.parent;
                root = requestCoordinator != null ? requestCoordinator.getRoot() : this;
            } catch (Throwable th) {
                throw th;
            }
        }
        return root;
    }

    @Override // com.bumptech.glide.request.RequestCoordinator, com.bumptech.glide.request.Request
    public boolean isAnyResourceSet() {
        boolean z2;
        synchronized (this.requestLock) {
            try {
                z2 = this.thumb.isAnyResourceSet() || this.full.isAnyResourceSet();
            } finally {
            }
        }
        return z2;
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isCleared() {
        boolean z2;
        synchronized (this.requestLock) {
            z2 = this.fullState == RequestCoordinator.RequestState.CLEARED;
        }
        return z2;
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isComplete() {
        boolean z2;
        synchronized (this.requestLock) {
            z2 = this.fullState == RequestCoordinator.RequestState.SUCCESS;
        }
        return z2;
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isEquivalentTo(Request request) {
        if (!(request instanceof ThumbnailRequestCoordinator)) {
            return false;
        }
        ThumbnailRequestCoordinator thumbnailRequestCoordinator = (ThumbnailRequestCoordinator) request;
        if (this.full == null) {
            if (thumbnailRequestCoordinator.full != null) {
                return false;
            }
        } else if (!this.full.isEquivalentTo(thumbnailRequestCoordinator.full)) {
            return false;
        }
        if (this.thumb == null) {
            if (thumbnailRequestCoordinator.thumb != null) {
                return false;
            }
        } else if (!this.thumb.isEquivalentTo(thumbnailRequestCoordinator.thumb)) {
            return false;
        }
        return true;
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isRunning() {
        boolean z2;
        synchronized (this.requestLock) {
            z2 = this.fullState == RequestCoordinator.RequestState.RUNNING;
        }
        return z2;
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public void onRequestFailed(Request request) {
        synchronized (this.requestLock) {
            try {
                if (!request.equals(this.full)) {
                    this.thumbState = RequestCoordinator.RequestState.FAILED;
                    return;
                }
                this.fullState = RequestCoordinator.RequestState.FAILED;
                RequestCoordinator requestCoordinator = this.parent;
                if (requestCoordinator != null) {
                    requestCoordinator.onRequestFailed(this);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public void onRequestSuccess(Request request) {
        synchronized (this.requestLock) {
            try {
                if (request.equals(this.thumb)) {
                    this.thumbState = RequestCoordinator.RequestState.SUCCESS;
                    return;
                }
                this.fullState = RequestCoordinator.RequestState.SUCCESS;
                RequestCoordinator requestCoordinator = this.parent;
                if (requestCoordinator != null) {
                    requestCoordinator.onRequestSuccess(this);
                }
                if (!this.thumbState.isComplete()) {
                    this.thumb.clear();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.bumptech.glide.request.Request
    public void pause() {
        synchronized (this.requestLock) {
            try {
                if (!this.thumbState.isComplete()) {
                    this.thumbState = RequestCoordinator.RequestState.PAUSED;
                    this.thumb.pause();
                }
                if (!this.fullState.isComplete()) {
                    this.fullState = RequestCoordinator.RequestState.PAUSED;
                    this.full.pause();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setRequests(Request request, Request request2) {
        this.full = request;
        this.thumb = request2;
    }
}
