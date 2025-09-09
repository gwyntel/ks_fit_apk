package com.bumptech.glide.load.resource;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.util.Preconditions;

/* loaded from: classes3.dex */
public class SimpleResource<T> implements Resource<T> {

    /* renamed from: a, reason: collision with root package name */
    protected final Object f12385a;

    public SimpleResource(@NonNull T t2) {
        this.f12385a = Preconditions.checkNotNull(t2);
    }

    @Override // com.bumptech.glide.load.engine.Resource
    @NonNull
    public final T get() {
        return (T) this.f12385a;
    }

    @Override // com.bumptech.glide.load.engine.Resource
    @NonNull
    public Class<T> getResourceClass() {
        return (Class<T>) this.f12385a.getClass();
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public final int getSize() {
        return 1;
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public void recycle() {
    }
}
