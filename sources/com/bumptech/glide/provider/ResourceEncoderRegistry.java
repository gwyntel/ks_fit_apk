package com.bumptech.glide.provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.ResourceEncoder;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class ResourceEncoderRegistry {
    private final List<Entry<?>> encoders = new ArrayList();

    private static final class Entry<T> {

        /* renamed from: a, reason: collision with root package name */
        final ResourceEncoder f12423a;
        private final Class<T> resourceClass;

        Entry(Class cls, ResourceEncoder resourceEncoder) {
            this.resourceClass = cls;
            this.f12423a = resourceEncoder;
        }

        boolean a(Class cls) {
            return this.resourceClass.isAssignableFrom(cls);
        }
    }

    public synchronized <Z> void append(@NonNull Class<Z> cls, @NonNull ResourceEncoder<Z> resourceEncoder) {
        this.encoders.add(new Entry<>(cls, resourceEncoder));
    }

    @Nullable
    public synchronized <Z> ResourceEncoder<Z> get(@NonNull Class<Z> cls) {
        int size = this.encoders.size();
        for (int i2 = 0; i2 < size; i2++) {
            Entry<?> entry = this.encoders.get(i2);
            if (entry.a(cls)) {
                return entry.f12423a;
            }
        }
        return null;
    }

    public synchronized <Z> void prepend(@NonNull Class<Z> cls, @NonNull ResourceEncoder<Z> resourceEncoder) {
        this.encoders.add(0, new Entry<>(cls, resourceEncoder));
    }
}
