package okio;

import java.util.AbstractList;
import java.util.List;
import java.util.RandomAccess;

/* loaded from: classes5.dex */
public final class Options extends AbstractList<ByteString> implements RandomAccess {

    /* renamed from: a, reason: collision with root package name */
    final ByteString[] f26479a;

    /* renamed from: b, reason: collision with root package name */
    final int[] f26480b;

    private Options(ByteString[] byteStringArr, int[] iArr) {
        this.f26479a = byteStringArr;
        this.f26480b = iArr;
    }

    private static void buildTrieRecursive(long j2, Buffer buffer, int i2, List<ByteString> list, int i3, int i4, List<Integer> list2) {
        int iIntValue;
        int i5;
        int i6;
        int i7;
        int i8;
        Buffer buffer2;
        if (i3 >= i4) {
            throw new AssertionError();
        }
        for (int i9 = i3; i9 < i4; i9++) {
            if (list.get(i9).size() < i2) {
                throw new AssertionError();
            }
        }
        ByteString byteString = list.get(i3);
        ByteString byteString2 = list.get(i4 - 1);
        if (i2 == byteString.size()) {
            int i10 = i3 + 1;
            i5 = i10;
            iIntValue = list2.get(i3).intValue();
            byteString = list.get(i10);
        } else {
            iIntValue = -1;
            i5 = i3;
        }
        if (byteString.getByte(i2) == byteString2.getByte(i2)) {
            int iMin = Math.min(byteString.size(), byteString2.size());
            int i11 = 0;
            for (int i12 = i2; i12 < iMin && byteString.getByte(i12) == byteString2.getByte(i12); i12++) {
                i11++;
            }
            long jIntCount = 1 + j2 + intCount(buffer) + 2 + i11;
            buffer.writeInt(-i11);
            buffer.writeInt(iIntValue);
            int i13 = i2;
            while (true) {
                i6 = i2 + i11;
                if (i13 >= i6) {
                    break;
                }
                buffer.writeInt(byteString.getByte(i13) & 255);
                i13++;
            }
            if (i5 + 1 == i4) {
                if (i6 != list.get(i5).size()) {
                    throw new AssertionError();
                }
                buffer.writeInt(list2.get(i5).intValue());
                return;
            } else {
                Buffer buffer3 = new Buffer();
                buffer.writeInt((int) ((intCount(buffer3) + jIntCount) * (-1)));
                buildTrieRecursive(jIntCount, buffer3, i6, list, i5, i4, list2);
                buffer.write(buffer3, buffer3.size());
                return;
            }
        }
        int i14 = 1;
        for (int i15 = i5 + 1; i15 < i4; i15++) {
            if (list.get(i15 - 1).getByte(i2) != list.get(i15).getByte(i2)) {
                i14++;
            }
        }
        long jIntCount2 = j2 + intCount(buffer) + 2 + (i14 * 2);
        buffer.writeInt(i14);
        buffer.writeInt(iIntValue);
        for (int i16 = i5; i16 < i4; i16++) {
            byte b2 = list.get(i16).getByte(i2);
            if (i16 == i5 || b2 != list.get(i16 - 1).getByte(i2)) {
                buffer.writeInt(b2 & 255);
            }
        }
        Buffer buffer4 = new Buffer();
        int i17 = i5;
        while (i17 < i4) {
            byte b3 = list.get(i17).getByte(i2);
            int i18 = i17 + 1;
            int i19 = i18;
            while (true) {
                if (i19 >= i4) {
                    i7 = i4;
                    break;
                } else {
                    if (b3 != list.get(i19).getByte(i2)) {
                        i7 = i19;
                        break;
                    }
                    i19++;
                }
            }
            if (i18 == i7 && i2 + 1 == list.get(i17).size()) {
                buffer.writeInt(list2.get(i17).intValue());
                i8 = i7;
                buffer2 = buffer4;
            } else {
                buffer.writeInt((int) ((intCount(buffer4) + jIntCount2) * (-1)));
                i8 = i7;
                buffer2 = buffer4;
                buildTrieRecursive(jIntCount2, buffer4, i2 + 1, list, i17, i7, list2);
            }
            buffer4 = buffer2;
            i17 = i8;
        }
        Buffer buffer5 = buffer4;
        buffer.write(buffer5, buffer5.size());
    }

    private static int intCount(Buffer buffer) {
        return (int) (buffer.size() / 4);
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x00ba, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static okio.Options of(okio.ByteString... r11) {
        /*
            Method dump skipped, instructions count: 254
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Options.of(okio.ByteString[]):okio.Options");
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.f26479a.length;
    }

    @Override // java.util.AbstractList, java.util.List
    public ByteString get(int i2) {
        return this.f26479a[i2];
    }
}
