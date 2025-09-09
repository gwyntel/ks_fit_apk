package top.zibin.luban.io;

/* loaded from: classes5.dex */
public interface ArrayPool {
    void clearMemory();

    <T> T get(int i2, Class<T> cls);

    <T> void put(T t2);

    @Deprecated
    <T> void put(T t2, Class<T> cls);
}
