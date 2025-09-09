package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Comparator;

/* loaded from: classes.dex */
public class Table {

    /* renamed from: a, reason: collision with root package name */
    protected int f4069a;

    /* renamed from: b, reason: collision with root package name */
    protected ByteBuffer f4070b;

    /* renamed from: c, reason: collision with root package name */
    Utf8 f4071c = Utf8.getDefault();
    private int vtable_size;
    private int vtable_start;

    protected static int b(int i2, ByteBuffer byteBuffer) {
        return i2 + byteBuffer.getInt(i2);
    }

    protected static String f(int i2, ByteBuffer byteBuffer, Utf8 utf8) {
        int i3 = i2 + byteBuffer.getInt(i2);
        return utf8.decodeUtf8(byteBuffer, i3 + 4, byteBuffer.getInt(i3));
    }

    protected static Table g(Table table, int i2, ByteBuffer byteBuffer) {
        table.d(b(i2, byteBuffer), byteBuffer);
        return table;
    }

    public void __reset() {
        d(0, null);
    }

    protected int a(int i2) {
        return i2 + this.f4070b.getInt(i2);
    }

    protected int c(int i2) {
        if (i2 < this.vtable_size) {
            return this.f4070b.getShort(this.vtable_start + i2);
        }
        return 0;
    }

    protected void d(int i2, ByteBuffer byteBuffer) {
        this.f4070b = byteBuffer;
        if (byteBuffer == null) {
            this.f4069a = 0;
            this.vtable_start = 0;
            this.vtable_size = 0;
        } else {
            this.f4069a = i2;
            int i3 = i2 - byteBuffer.getInt(i2);
            this.vtable_start = i3;
            this.vtable_size = this.f4070b.getShort(i3);
        }
    }

    protected String e(int i2) {
        return f(i2, this.f4070b, this.f4071c);
    }

    public ByteBuffer getByteBuffer() {
        return this.f4070b;
    }

    protected int h(int i2) {
        int i3 = i2 + this.f4069a;
        return i3 + this.f4070b.getInt(i3) + 4;
    }

    protected ByteBuffer i(int i2, int i3) {
        int iC = c(i2);
        if (iC == 0) {
            return null;
        }
        ByteBuffer byteBufferOrder = this.f4070b.duplicate().order(ByteOrder.LITTLE_ENDIAN);
        int iH = h(iC);
        byteBufferOrder.position(iH);
        byteBufferOrder.limit(iH + (k(iC) * i3));
        return byteBufferOrder;
    }

    protected ByteBuffer j(ByteBuffer byteBuffer, int i2, int i3) {
        int iC = c(i2);
        if (iC == 0) {
            return null;
        }
        int iH = h(iC);
        byteBuffer.rewind();
        byteBuffer.limit((k(iC) * i3) + iH);
        byteBuffer.position(iH);
        return byteBuffer;
    }

    protected int k(int i2) {
        int i3 = i2 + this.f4069a;
        return this.f4070b.getInt(i3 + this.f4070b.getInt(i3));
    }

    protected int l(Integer num, Integer num2, ByteBuffer byteBuffer) {
        return 0;
    }

    protected void m(int[] iArr, final ByteBuffer byteBuffer) {
        Integer[] numArr = new Integer[iArr.length];
        for (int i2 = 0; i2 < iArr.length; i2++) {
            numArr[i2] = Integer.valueOf(iArr[i2]);
        }
        Arrays.sort(numArr, new Comparator<Integer>() { // from class: androidx.emoji2.text.flatbuffer.Table.1
            @Override // java.util.Comparator
            public int compare(Integer num, Integer num2) {
                return Table.this.l(num, num2, byteBuffer);
            }
        });
        for (int i3 = 0; i3 < iArr.length; i3++) {
            iArr[i3] = numArr[i3].intValue();
        }
    }
}
