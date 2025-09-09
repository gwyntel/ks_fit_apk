package top.zibin.luban.io;

import android.content.ContentResolver;
import android.net.Uri;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes5.dex */
public class ArrayPoolProvide {
    private static ArrayPoolProvide mInstance;
    private final HashSet<String> keyCache = new HashSet<>();
    private final ConcurrentHashMap<String, BufferedInputStreamWrap> bufferedLruCache = new ConcurrentHashMap<>();
    private final LruArrayPool arrayPool = new LruArrayPool(4194304);

    public static void close(Closeable closeable) throws IOException {
        if (closeable instanceof Closeable) {
            try {
                closeable.close();
            } catch (Exception unused) {
            }
        }
    }

    public static ArrayPoolProvide getInstance() {
        if (mInstance == null) {
            synchronized (ArrayPoolProvide.class) {
                try {
                    if (mInstance == null) {
                        mInstance = new ArrayPoolProvide();
                    }
                } finally {
                }
            }
        }
        return mInstance;
    }

    private BufferedInputStreamWrap wrapInputStream(ContentResolver contentResolver, Uri uri) {
        BufferedInputStreamWrap bufferedInputStreamWrap = null;
        try {
            BufferedInputStreamWrap bufferedInputStreamWrap2 = new BufferedInputStreamWrap(contentResolver.openInputStream(uri));
            try {
                int iAvailable = bufferedInputStreamWrap2.available();
                if (iAvailable <= 0) {
                    iAvailable = 8388608;
                }
                bufferedInputStreamWrap2.mark(iAvailable);
                this.bufferedLruCache.put(uri.toString(), bufferedInputStreamWrap2);
                this.keyCache.add(uri.toString());
                return bufferedInputStreamWrap2;
            } catch (Exception e2) {
                e = e2;
                bufferedInputStreamWrap = bufferedInputStreamWrap2;
                e.printStackTrace();
                return bufferedInputStreamWrap;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    public void clearMemory() throws IOException {
        Iterator<String> it = this.keyCache.iterator();
        while (it.hasNext()) {
            String next = it.next();
            close(this.bufferedLruCache.get(next));
            this.bufferedLruCache.remove(next);
        }
        this.keyCache.clear();
        this.arrayPool.clearMemory();
    }

    public byte[] get(int i2) {
        return (byte[]) this.arrayPool.get(i2, byte[].class);
    }

    public InputStream openInputStream(ContentResolver contentResolver, Uri uri) {
        try {
            try {
                BufferedInputStreamWrap bufferedInputStreamWrapWrapInputStream = this.bufferedLruCache.get(uri.toString());
                if (bufferedInputStreamWrapWrapInputStream != null) {
                    bufferedInputStreamWrapWrapInputStream.reset();
                } else {
                    bufferedInputStreamWrapWrapInputStream = wrapInputStream(contentResolver, uri);
                }
                return bufferedInputStreamWrapWrapInputStream;
            } catch (Exception unused) {
                return contentResolver.openInputStream(uri);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return wrapInputStream(contentResolver, uri);
        }
    }

    public void put(byte[] bArr) {
        this.arrayPool.put(bArr);
    }

    private BufferedInputStreamWrap wrapInputStream(String str) {
        BufferedInputStreamWrap bufferedInputStreamWrap = null;
        try {
            BufferedInputStreamWrap bufferedInputStreamWrap2 = new BufferedInputStreamWrap(new FileInputStream(str));
            try {
                int iAvailable = bufferedInputStreamWrap2.available();
                if (iAvailable <= 0) {
                    iAvailable = 8388608;
                }
                bufferedInputStreamWrap2.mark(iAvailable);
                this.bufferedLruCache.put(str, bufferedInputStreamWrap2);
                this.keyCache.add(str);
                return bufferedInputStreamWrap2;
            } catch (Exception e2) {
                e = e2;
                bufferedInputStreamWrap = bufferedInputStreamWrap2;
                e.printStackTrace();
                return bufferedInputStreamWrap;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    public InputStream openInputStream(String str) {
        try {
            BufferedInputStreamWrap bufferedInputStreamWrapWrapInputStream = this.bufferedLruCache.get(str);
            if (bufferedInputStreamWrapWrapInputStream != null) {
                bufferedInputStreamWrapWrapInputStream.reset();
            } else {
                bufferedInputStreamWrapWrapInputStream = wrapInputStream(str);
            }
            return bufferedInputStreamWrapWrapInputStream;
        } catch (Exception unused) {
            return wrapInputStream(str);
        }
    }
}
