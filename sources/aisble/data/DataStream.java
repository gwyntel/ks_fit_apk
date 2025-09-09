package aisble.data;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.ByteArrayOutputStream;

/* loaded from: classes.dex */
public class DataStream {
    public final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    @IntRange(from = 0)
    public int size() {
        return this.buffer.size();
    }

    @NonNull
    public byte[] toByteArray() {
        return this.buffer.toByteArray();
    }

    @NonNull
    public Data toData() {
        return new Data(this.buffer.toByteArray());
    }

    public boolean write(@Nullable byte[] bArr) {
        if (bArr == null) {
            return false;
        }
        return write(bArr, 0, bArr.length);
    }

    public boolean write(@Nullable byte[] bArr, @IntRange(from = 0) int i2, @IntRange(from = 0) int i3) {
        if (bArr == null || bArr.length < i2) {
            return false;
        }
        this.buffer.write(bArr, i2, Math.min(bArr.length - i2, i3));
        return true;
    }

    public boolean write(@Nullable Data data) {
        return data != null && write(data.getValue());
    }
}
