package top.zibin.luban.io;

/* loaded from: classes5.dex */
public final class ByteArrayAdapter implements ArrayAdapterInterface<byte[]> {
    private static final String TAG = "ByteArrayPool";

    @Override // top.zibin.luban.io.ArrayAdapterInterface
    public int getElementSizeInBytes() {
        return 1;
    }

    @Override // top.zibin.luban.io.ArrayAdapterInterface
    public String getTag() {
        return TAG;
    }

    @Override // top.zibin.luban.io.ArrayAdapterInterface
    public int getArrayLength(byte[] bArr) {
        return bArr.length;
    }

    @Override // top.zibin.luban.io.ArrayAdapterInterface
    public byte[] newArray(int i2) {
        return new byte[i2];
    }
}
