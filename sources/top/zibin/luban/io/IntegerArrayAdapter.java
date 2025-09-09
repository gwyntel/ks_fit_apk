package top.zibin.luban.io;

/* loaded from: classes5.dex */
public final class IntegerArrayAdapter implements ArrayAdapterInterface<int[]> {
    private static final String TAG = "IntegerArrayPool";

    @Override // top.zibin.luban.io.ArrayAdapterInterface
    public int getElementSizeInBytes() {
        return 4;
    }

    @Override // top.zibin.luban.io.ArrayAdapterInterface
    public String getTag() {
        return TAG;
    }

    @Override // top.zibin.luban.io.ArrayAdapterInterface
    public int getArrayLength(int[] iArr) {
        return iArr.length;
    }

    @Override // top.zibin.luban.io.ArrayAdapterInterface
    public int[] newArray(int i2) {
        return new int[i2];
    }
}
