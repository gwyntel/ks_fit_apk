package com.bumptech.glide.provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.Encoder;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class EncoderRegistry {
    private final List<Entry<?>> encoders = new ArrayList();

    private static final class Entry<T> {

        /* renamed from: a, reason: collision with root package name */
        final Encoder f12420a;
        private final Class<T> dataClass;

        Entry(Class cls, Encoder encoder) {
            this.dataClass = cls;
            this.f12420a = encoder;
        }

        boolean a(Class cls) {
            return this.dataClass.isAssignableFrom(cls);
        }
    }

    public synchronized <T> void append(@NonNull Class<T> cls, @NonNull Encoder<T> encoder) {
        this.encoders.add(new Entry<>(cls, encoder));
    }

    @Nullable
    public synchronized <T> Encoder<T> getEncoder(@NonNull Class<T> cls) {
        for (Entry<?> entry : this.encoders) {
            if (entry.a(cls)) {
                return entry.f12420a;
            }
        }
        return null;
    }

    public synchronized <T> void prepend(@NonNull Class<T> cls, @NonNull Encoder<T> encoder) {
        this.encoders.add(0, new Entry<>(cls, encoder));
    }
}
