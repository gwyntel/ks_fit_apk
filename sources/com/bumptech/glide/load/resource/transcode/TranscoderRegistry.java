package com.bumptech.glide.load.resource.transcode;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class TranscoderRegistry {
    private final List<Entry<?, ?>> transcoders = new ArrayList();

    private static final class Entry<Z, R> {

        /* renamed from: a, reason: collision with root package name */
        final Class f12400a;

        /* renamed from: b, reason: collision with root package name */
        final Class f12401b;

        /* renamed from: c, reason: collision with root package name */
        final ResourceTranscoder f12402c;

        Entry(Class cls, Class cls2, ResourceTranscoder resourceTranscoder) {
            this.f12400a = cls;
            this.f12401b = cls2;
            this.f12402c = resourceTranscoder;
        }

        public boolean handles(@NonNull Class<?> cls, @NonNull Class<?> cls2) {
            return this.f12400a.isAssignableFrom(cls) && cls2.isAssignableFrom(this.f12401b);
        }
    }

    @NonNull
    public synchronized <Z, R> ResourceTranscoder<Z, R> get(@NonNull Class<Z> cls, @NonNull Class<R> cls2) {
        if (cls2.isAssignableFrom(cls)) {
            return UnitTranscoder.get();
        }
        for (Entry<?, ?> entry : this.transcoders) {
            if (entry.handles(cls, cls2)) {
                return entry.f12402c;
            }
        }
        throw new IllegalArgumentException("No transcoder registered to transcode from " + cls + " to " + cls2);
    }

    @NonNull
    public synchronized <Z, R> List<Class<R>> getTranscodeClasses(@NonNull Class<Z> cls, @NonNull Class<R> cls2) {
        ArrayList arrayList = new ArrayList();
        if (cls2.isAssignableFrom(cls)) {
            arrayList.add(cls2);
            return arrayList;
        }
        for (Entry<?, ?> entry : this.transcoders) {
            if (entry.handles(cls, cls2) && !arrayList.contains(entry.f12401b)) {
                arrayList.add(entry.f12401b);
            }
        }
        return arrayList;
    }

    public synchronized <Z, R> void register(@NonNull Class<Z> cls, @NonNull Class<R> cls2, @NonNull ResourceTranscoder<Z, R> resourceTranscoder) {
        this.transcoders.add(new Entry<>(cls, cls2, resourceTranscoder));
    }
}
