package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public final class MetadataItem extends Table {

    public static final class Vector extends BaseVector {
        public Vector __assign(int i2, int i3, ByteBuffer byteBuffer) {
            b(i2, i3, byteBuffer);
            return this;
        }

        public MetadataItem get(int i2) {
            return get(new MetadataItem(), i2);
        }

        public MetadataItem get(MetadataItem metadataItem, int i2) {
            return metadataItem.__assign(Table.b(a(i2), this.f4040a), this.f4040a);
        }
    }

    public static void ValidateVersion() {
        Constants.FLATBUFFERS_1_12_0();
    }

    public static void addCodepoints(FlatBufferBuilder flatBufferBuilder, int i2) {
        flatBufferBuilder.addOffset(6, i2, 0);
    }

    public static void addCompatAdded(FlatBufferBuilder flatBufferBuilder, short s2) {
        flatBufferBuilder.addShort(3, s2, 0);
    }

    public static void addEmojiStyle(FlatBufferBuilder flatBufferBuilder, boolean z2) {
        flatBufferBuilder.addBoolean(1, z2, false);
    }

    public static void addHeight(FlatBufferBuilder flatBufferBuilder, short s2) {
        flatBufferBuilder.addShort(5, s2, 0);
    }

    public static void addId(FlatBufferBuilder flatBufferBuilder, int i2) {
        flatBufferBuilder.addInt(0, i2, 0);
    }

    public static void addSdkAdded(FlatBufferBuilder flatBufferBuilder, short s2) {
        flatBufferBuilder.addShort(2, s2, 0);
    }

    public static void addWidth(FlatBufferBuilder flatBufferBuilder, short s2) {
        flatBufferBuilder.addShort(4, s2, 0);
    }

    public static int createCodepointsVector(FlatBufferBuilder flatBufferBuilder, int[] iArr) {
        flatBufferBuilder.startVector(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.addInt(iArr[length]);
        }
        return flatBufferBuilder.endVector();
    }

    public static int createMetadataItem(FlatBufferBuilder flatBufferBuilder, int i2, boolean z2, short s2, short s3, short s4, short s5, int i3) {
        flatBufferBuilder.startTable(7);
        addCodepoints(flatBufferBuilder, i3);
        addId(flatBufferBuilder, i2);
        addHeight(flatBufferBuilder, s5);
        addWidth(flatBufferBuilder, s4);
        addCompatAdded(flatBufferBuilder, s3);
        addSdkAdded(flatBufferBuilder, s2);
        addEmojiStyle(flatBufferBuilder, z2);
        return endMetadataItem(flatBufferBuilder);
    }

    public static int endMetadataItem(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endTable();
    }

    public static MetadataItem getRootAsMetadataItem(ByteBuffer byteBuffer) {
        return getRootAsMetadataItem(byteBuffer, new MetadataItem());
    }

    public static void startCodepointsVector(FlatBufferBuilder flatBufferBuilder, int i2) {
        flatBufferBuilder.startVector(4, i2, 4);
    }

    public static void startMetadataItem(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startTable(7);
    }

    public MetadataItem __assign(int i2, ByteBuffer byteBuffer) {
        __init(i2, byteBuffer);
        return this;
    }

    public void __init(int i2, ByteBuffer byteBuffer) {
        d(i2, byteBuffer);
    }

    public int codepoints(int i2) {
        int iC = c(16);
        if (iC != 0) {
            return this.f4070b.getInt(h(iC) + (i2 * 4));
        }
        return 0;
    }

    public ByteBuffer codepointsAsByteBuffer() {
        return i(16, 4);
    }

    public ByteBuffer codepointsInByteBuffer(ByteBuffer byteBuffer) {
        return j(byteBuffer, 16, 4);
    }

    public int codepointsLength() {
        int iC = c(16);
        if (iC != 0) {
            return k(iC);
        }
        return 0;
    }

    public IntVector codepointsVector() {
        return codepointsVector(new IntVector());
    }

    public short compatAdded() {
        int iC = c(10);
        if (iC != 0) {
            return this.f4070b.getShort(iC + this.f4069a);
        }
        return (short) 0;
    }

    public boolean emojiStyle() {
        int iC = c(6);
        return (iC == 0 || this.f4070b.get(iC + this.f4069a) == 0) ? false : true;
    }

    public short height() {
        int iC = c(14);
        if (iC != 0) {
            return this.f4070b.getShort(iC + this.f4069a);
        }
        return (short) 0;
    }

    public int id() {
        int iC = c(4);
        if (iC != 0) {
            return this.f4070b.getInt(iC + this.f4069a);
        }
        return 0;
    }

    public short sdkAdded() {
        int iC = c(8);
        if (iC != 0) {
            return this.f4070b.getShort(iC + this.f4069a);
        }
        return (short) 0;
    }

    public short width() {
        int iC = c(12);
        if (iC != 0) {
            return this.f4070b.getShort(iC + this.f4069a);
        }
        return (short) 0;
    }

    public static MetadataItem getRootAsMetadataItem(ByteBuffer byteBuffer, MetadataItem metadataItem) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return metadataItem.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public IntVector codepointsVector(IntVector intVector) {
        int iC = c(16);
        if (iC != 0) {
            return intVector.__assign(h(iC), this.f4070b);
        }
        return null;
    }
}
