package com.bumptech.glide.provider;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.ResourceDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class ResourceDecoderRegistry {
    private final List<String> bucketPriorityList = new ArrayList();
    private final Map<String, List<Entry<?, ?>>> decoders = new HashMap();

    private static class Entry<T, R> {

        /* renamed from: a, reason: collision with root package name */
        final Class f12421a;

        /* renamed from: b, reason: collision with root package name */
        final ResourceDecoder f12422b;
        private final Class<T> dataClass;

        public Entry(@NonNull Class<T> cls, @NonNull Class<R> cls2, ResourceDecoder<T, R> resourceDecoder) {
            this.dataClass = cls;
            this.f12421a = cls2;
            this.f12422b = resourceDecoder;
        }

        public boolean handles(@NonNull Class<?> cls, @NonNull Class<?> cls2) {
            return this.dataClass.isAssignableFrom(cls) && cls2.isAssignableFrom(this.f12421a);
        }
    }

    @NonNull
    private synchronized List<Entry<?, ?>> getOrAddEntryList(@NonNull String str) {
        List<Entry<?, ?>> arrayList;
        try {
            if (!this.bucketPriorityList.contains(str)) {
                this.bucketPriorityList.add(str);
            }
            arrayList = this.decoders.get(str);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.decoders.put(str, arrayList);
            }
        } catch (Throwable th) {
            throw th;
        }
        return arrayList;
    }

    public synchronized <T, R> void append(@NonNull String str, @NonNull ResourceDecoder<T, R> resourceDecoder, @NonNull Class<T> cls, @NonNull Class<R> cls2) {
        getOrAddEntryList(str).add(new Entry<>(cls, cls2, resourceDecoder));
    }

    @NonNull
    public synchronized <T, R> List<ResourceDecoder<T, R>> getDecoders(@NonNull Class<T> cls, @NonNull Class<R> cls2) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        Iterator<String> it = this.bucketPriorityList.iterator();
        while (it.hasNext()) {
            List<Entry<?, ?>> list = this.decoders.get(it.next());
            if (list != null) {
                for (Entry<?, ?> entry : list) {
                    if (entry.handles(cls, cls2)) {
                        arrayList.add(entry.f12422b);
                    }
                }
            }
        }
        return arrayList;
    }

    @NonNull
    public synchronized <T, R> List<Class<R>> getResourceClasses(@NonNull Class<T> cls, @NonNull Class<R> cls2) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        Iterator<String> it = this.bucketPriorityList.iterator();
        while (it.hasNext()) {
            List<Entry<?, ?>> list = this.decoders.get(it.next());
            if (list != null) {
                for (Entry<?, ?> entry : list) {
                    if (entry.handles(cls, cls2) && !arrayList.contains(entry.f12421a)) {
                        arrayList.add(entry.f12421a);
                    }
                }
            }
        }
        return arrayList;
    }

    public synchronized <T, R> void prepend(@NonNull String str, @NonNull ResourceDecoder<T, R> resourceDecoder, @NonNull Class<T> cls, @NonNull Class<R> cls2) {
        getOrAddEntryList(str).add(0, new Entry<>(cls, cls2, resourceDecoder));
    }

    public synchronized void setBucketPriorityList(@NonNull List<String> list) {
        try {
            ArrayList<String> arrayList = new ArrayList(this.bucketPriorityList);
            this.bucketPriorityList.clear();
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                this.bucketPriorityList.add(it.next());
            }
            for (String str : arrayList) {
                if (!list.contains(str)) {
                    this.bucketPriorityList.add(str);
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
