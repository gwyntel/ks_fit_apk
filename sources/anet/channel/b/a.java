package anet.channel.b;

import androidx.media3.datasource.cache.CacheDataSink;
import anet.channel.util.ALog;
import anet.channel.util.StringUtils;
import anetwork.channel.cache.Cache;
import com.taobao.alivfssdk.cache.AVFSCache;
import com.taobao.alivfssdk.cache.AVFSCacheConfig;
import com.taobao.alivfssdk.cache.AVFSCacheManager;
import com.taobao.alivfssdk.cache.IAVFSCache;

/* loaded from: classes2.dex */
public class a implements Cache {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f6691a = true;

    /* renamed from: b, reason: collision with root package name */
    private static Object f6692b;

    /* renamed from: c, reason: collision with root package name */
    private static Object f6693c;

    /* renamed from: d, reason: collision with root package name */
    private static Object f6694d;

    static {
        try {
            Class.forName("com.taobao.alivfssdk.cache.AVFSCacheManager");
            f6692b = new b();
            f6693c = new c();
            f6694d = new d();
        } catch (ClassNotFoundException unused) {
            f6691a = false;
            ALog.w("anet.AVFSCacheImpl", "no alivfs sdk!", null, new Object[0]);
        }
    }

    private IAVFSCache b() {
        AVFSCache aVFSCacheCacheForModule = AVFSCacheManager.getInstance().cacheForModule("networksdk.httpcache");
        if (aVFSCacheCacheForModule != null) {
            return aVFSCacheCacheForModule.getFileCache();
        }
        return null;
    }

    public void a() {
        AVFSCache aVFSCacheCacheForModule;
        if (f6691a && (aVFSCacheCacheForModule = AVFSCacheManager.getInstance().cacheForModule("networksdk.httpcache")) != null) {
            AVFSCacheConfig aVFSCacheConfig = new AVFSCacheConfig();
            aVFSCacheConfig.limitSize = Long.valueOf(CacheDataSink.DEFAULT_FRAGMENT_SIZE);
            aVFSCacheConfig.fileMemMaxSize = 1048576L;
            aVFSCacheCacheForModule.moduleConfig(aVFSCacheConfig);
        }
    }

    @Override // anetwork.channel.cache.Cache
    public void clear() {
        if (f6691a) {
            try {
                IAVFSCache iAVFSCacheB = b();
                if (iAVFSCacheB != null) {
                    iAVFSCacheB.removeAllObject((IAVFSCache.OnAllObjectRemoveCallback) f6694d);
                }
            } catch (Exception e2) {
                ALog.e("anet.AVFSCacheImpl", "clear cache failed", null, e2, new Object[0]);
            }
        }
    }

    @Override // anetwork.channel.cache.Cache
    public Cache.Entry get(String str) {
        if (!f6691a) {
            return null;
        }
        try {
            IAVFSCache iAVFSCacheB = b();
            if (iAVFSCacheB != null) {
                return (Cache.Entry) iAVFSCacheB.objectForKey(StringUtils.md5ToHex(str));
            }
        } catch (Exception e2) {
            ALog.e("anet.AVFSCacheImpl", "get cache failed", null, e2, new Object[0]);
        }
        return null;
    }

    @Override // anetwork.channel.cache.Cache
    public void put(String str, Cache.Entry entry) {
        if (f6691a) {
            try {
                IAVFSCache iAVFSCacheB = b();
                if (iAVFSCacheB != null) {
                    iAVFSCacheB.setObjectForKey(StringUtils.md5ToHex(str), entry, (IAVFSCache.OnObjectSetCallback) f6692b);
                }
            } catch (Exception e2) {
                ALog.e("anet.AVFSCacheImpl", "put cache failed", null, e2, new Object[0]);
            }
        }
    }

    @Override // anetwork.channel.cache.Cache
    public void remove(String str) {
        if (f6691a) {
            try {
                IAVFSCache iAVFSCacheB = b();
                if (iAVFSCacheB != null) {
                    iAVFSCacheB.removeObjectForKey(StringUtils.md5ToHex(str), (IAVFSCache.OnObjectRemoveCallback) f6693c);
                }
            } catch (Exception e2) {
                ALog.e("anet.AVFSCacheImpl", "remove cache failed", null, e2, new Object[0]);
            }
        }
    }
}
