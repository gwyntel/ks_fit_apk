package top.zibin.luban.io;

/* loaded from: classes5.dex */
interface ArrayAdapterInterface<T> {
    int getArrayLength(T t2);

    int getElementSizeInBytes();

    String getTag();

    T newArray(int i2);
}
