package anetwork.channel.cache;

import anet.channel.util.ALog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes2.dex */
public class CacheManager {

    /* renamed from: a, reason: collision with root package name */
    private static List<a> f7135a = new ArrayList();

    /* renamed from: b, reason: collision with root package name */
    private static final ReentrantReadWriteLock f7136b;

    /* renamed from: c, reason: collision with root package name */
    private static final ReentrantReadWriteLock.ReadLock f7137c;

    /* renamed from: d, reason: collision with root package name */
    private static final ReentrantReadWriteLock.WriteLock f7138d;

    private static class a implements Comparable<a> {

        /* renamed from: a, reason: collision with root package name */
        final Cache f7139a;

        /* renamed from: b, reason: collision with root package name */
        final CachePrediction f7140b;

        /* renamed from: c, reason: collision with root package name */
        final int f7141c;

        a(Cache cache, CachePrediction cachePrediction, int i2) {
            this.f7139a = cache;
            this.f7140b = cachePrediction;
            this.f7141c = i2;
        }

        @Override // java.lang.Comparable
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compareTo(a aVar) {
            return this.f7141c - aVar.f7141c;
        }
    }

    static {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        f7136b = reentrantReadWriteLock;
        f7137c = reentrantReadWriteLock.readLock();
        f7138d = reentrantReadWriteLock.writeLock();
    }

    public static void addCache(Cache cache, CachePrediction cachePrediction, int i2) {
        try {
            if (cache == null) {
                throw new IllegalArgumentException("cache is null");
            }
            if (cachePrediction == null) {
                throw new IllegalArgumentException("prediction is null");
            }
            ReentrantReadWriteLock.WriteLock writeLock = f7138d;
            writeLock.lock();
            f7135a.add(new a(cache, cachePrediction, i2));
            Collections.sort(f7135a);
            writeLock.unlock();
        } catch (Throwable th) {
            f7138d.unlock();
            throw th;
        }
    }

    public static void clearAllCache() {
        ALog.w("anet.CacheManager", "clearAllCache", null, new Object[0]);
        Iterator<a> it = f7135a.iterator();
        while (it.hasNext()) {
            try {
                it.next().f7139a.clear();
            } catch (Exception unused) {
            }
        }
    }

    public static Cache getCache(String str, Map<String, String> map) {
        try {
            f7137c.lock();
            for (a aVar : f7135a) {
                if (aVar.f7140b.handleCache(str, map)) {
                    return aVar.f7139a;
                }
            }
            f7137c.unlock();
            return null;
        } finally {
            f7137c.unlock();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x001b, code lost:
    
        r0.remove();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void removeCache(anetwork.channel.cache.Cache r2) {
        /*
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = anetwork.channel.cache.CacheManager.f7138d     // Catch: java.lang.Throwable -> L1f
            r0.lock()     // Catch: java.lang.Throwable -> L1f
            java.util.List<anetwork.channel.cache.CacheManager$a> r0 = anetwork.channel.cache.CacheManager.f7135a     // Catch: java.lang.Throwable -> L1f
            java.util.ListIterator r0 = r0.listIterator()     // Catch: java.lang.Throwable -> L1f
        Lb:
            boolean r1 = r0.hasNext()     // Catch: java.lang.Throwable -> L1f
            if (r1 == 0) goto L21
            java.lang.Object r1 = r0.next()     // Catch: java.lang.Throwable -> L1f
            anetwork.channel.cache.CacheManager$a r1 = (anetwork.channel.cache.CacheManager.a) r1     // Catch: java.lang.Throwable -> L1f
            anetwork.channel.cache.Cache r1 = r1.f7139a     // Catch: java.lang.Throwable -> L1f
            if (r1 != r2) goto Lb
            r0.remove()     // Catch: java.lang.Throwable -> L1f
            goto L21
        L1f:
            r2 = move-exception
            goto L27
        L21:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r2 = anetwork.channel.cache.CacheManager.f7138d
            r2.unlock()
            return
        L27:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = anetwork.channel.cache.CacheManager.f7138d
            r0.unlock()
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: anetwork.channel.cache.CacheManager.removeCache(anetwork.channel.cache.Cache):void");
    }
}
