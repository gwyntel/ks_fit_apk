package androidx.lifecycle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public abstract class ViewModel {

    @Nullable
    private final Map<String, Object> mBagOfTags;
    private volatile boolean mCleared;

    @Nullable
    private final Set<Closeable> mCloseables;

    public ViewModel() {
        this.mBagOfTags = new HashMap();
        this.mCloseables = new LinkedHashSet();
        this.mCleared = false;
    }

    private static void closeWithRuntimeException(Object obj) throws IOException {
        if (obj instanceof Closeable) {
            try {
                ((Closeable) obj).close();
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    final void a() {
        this.mCleared = true;
        Map<String, Object> map = this.mBagOfTags;
        if (map != null) {
            synchronized (map) {
                try {
                    Iterator<Object> it = this.mBagOfTags.values().iterator();
                    while (it.hasNext()) {
                        closeWithRuntimeException(it.next());
                    }
                } finally {
                }
            }
        }
        Set<Closeable> set = this.mCloseables;
        if (set != null) {
            synchronized (set) {
                try {
                    Iterator<Closeable> it2 = this.mCloseables.iterator();
                    while (it2.hasNext()) {
                        closeWithRuntimeException(it2.next());
                    }
                } finally {
                }
            }
        }
        c();
    }

    public void addCloseable(@NonNull Closeable closeable) {
        Set<Closeable> set = this.mCloseables;
        if (set != null) {
            synchronized (set) {
                this.mCloseables.add(closeable);
            }
        }
    }

    Object b(String str) {
        Object obj;
        Map<String, Object> map = this.mBagOfTags;
        if (map == null) {
            return null;
        }
        synchronized (map) {
            obj = this.mBagOfTags.get(str);
        }
        return obj;
    }

    protected void c() {
    }

    Object d(String str, Object obj) throws IOException {
        Object obj2;
        synchronized (this.mBagOfTags) {
            try {
                obj2 = this.mBagOfTags.get(str);
                if (obj2 == null) {
                    this.mBagOfTags.put(str, obj);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (obj2 != null) {
            obj = obj2;
        }
        if (this.mCleared) {
            closeWithRuntimeException(obj);
        }
        return obj;
    }

    public ViewModel(@NonNull Closeable... closeableArr) {
        this.mBagOfTags = new HashMap();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        this.mCloseables = linkedHashSet;
        this.mCleared = false;
        linkedHashSet.addAll(Arrays.asList(closeableArr));
    }
}
