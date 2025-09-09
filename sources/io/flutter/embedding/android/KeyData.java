package io.flutter.embedding.android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes4.dex */
public class KeyData {
    private static final int BYTES_PER_FIELD = 8;
    public static final String CHANNEL = "flutter/keydata";
    private static final int FIELD_COUNT = 6;
    private static final String TAG = "KeyData";

    @Nullable
    String character;
    DeviceType deviceType;
    long logicalKey;
    long physicalKey;
    boolean synthesized;
    long timestamp;
    Type type;

    public enum DeviceType {
        kKeyboard(0),
        kDirectionalPad(1),
        kGamepad(2),
        kJoystick(3),
        kHdmi(4);

        private final long value;

        DeviceType(long j2) {
            this.value = j2;
        }

        static DeviceType fromLong(long j2) {
            int i2 = (int) j2;
            if (i2 == 0) {
                return kKeyboard;
            }
            if (i2 == 1) {
                return kDirectionalPad;
            }
            if (i2 == 2) {
                return kGamepad;
            }
            if (i2 == 3) {
                return kJoystick;
            }
            if (i2 == 4) {
                return kHdmi;
            }
            throw new AssertionError("Unexpected DeviceType value");
        }

        public long getValue() {
            return this.value;
        }
    }

    public enum Type {
        kDown(0),
        kUp(1),
        kRepeat(2);

        private long value;

        Type(long j2) {
            this.value = j2;
        }

        static Type fromLong(long j2) {
            int i2 = (int) j2;
            if (i2 == 0) {
                return kDown;
            }
            if (i2 == 1) {
                return kUp;
            }
            if (i2 == 2) {
                return kRepeat;
            }
            throw new AssertionError("Unexpected Type value");
        }

        public long getValue() {
            return this.value;
        }
    }

    public KeyData() {
    }

    ByteBuffer toBytes() {
        try {
            String str = this.character;
            byte[] bytes = str == null ? null : str.getBytes("UTF-8");
            int length = bytes == null ? 0 : bytes.length;
            ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(length + 56);
            byteBufferAllocateDirect.order(ByteOrder.LITTLE_ENDIAN);
            byteBufferAllocateDirect.putLong(length);
            byteBufferAllocateDirect.putLong(this.timestamp);
            byteBufferAllocateDirect.putLong(this.type.getValue());
            byteBufferAllocateDirect.putLong(this.physicalKey);
            byteBufferAllocateDirect.putLong(this.logicalKey);
            byteBufferAllocateDirect.putLong(this.synthesized ? 1L : 0L);
            byteBufferAllocateDirect.putLong(this.deviceType.getValue());
            if (bytes != null) {
                byteBufferAllocateDirect.put(bytes);
            }
            return byteBufferAllocateDirect;
        } catch (UnsupportedEncodingException unused) {
            throw new AssertionError("UTF-8 not supported");
        }
    }

    public KeyData(@NonNull ByteBuffer byteBuffer) {
        long j2 = byteBuffer.getLong();
        this.timestamp = byteBuffer.getLong();
        this.type = Type.fromLong(byteBuffer.getLong());
        this.physicalKey = byteBuffer.getLong();
        this.logicalKey = byteBuffer.getLong();
        this.synthesized = byteBuffer.getLong() != 0;
        this.deviceType = DeviceType.fromLong(byteBuffer.getLong());
        if (byteBuffer.remaining() != j2) {
            throw new AssertionError(String.format("Unexpected char length: charSize is %d while buffer has position %d, capacity %d, limit %d", Long.valueOf(j2), Integer.valueOf(byteBuffer.position()), Integer.valueOf(byteBuffer.capacity()), Integer.valueOf(byteBuffer.limit())));
        }
        this.character = null;
        if (j2 != 0) {
            int i2 = (int) j2;
            byte[] bArr = new byte[i2];
            byteBuffer.get(bArr, 0, i2);
            try {
                this.character = new String(bArr, "UTF-8");
            } catch (UnsupportedEncodingException unused) {
                throw new AssertionError("UTF-8 unsupported");
            }
        }
    }
}
