package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.SecureRandom;

/* loaded from: classes5.dex */
public class WebSocketFrame {
    public static final int frameLengthOverhead = 6;
    private boolean closeFlag;
    private boolean fin;
    private byte opcode;
    private byte[] payload;

    public WebSocketFrame(byte b2, boolean z2, byte[] bArr) {
        this.closeFlag = false;
        this.opcode = b2;
        this.fin = z2;
        this.payload = bArr;
    }

    public static void appendFinAndOpCode(ByteBuffer byteBuffer, byte b2, boolean z2) {
        byteBuffer.put((byte) ((b2 & 15) | (z2 ? (byte) 128 : (byte) 0)));
    }

    private static void appendLength(ByteBuffer byteBuffer, int i2, boolean z2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("Length cannot be negative");
        }
        int i3 = z2 ? -128 : 0;
        if (i2 <= 65535) {
            if (i2 < 126) {
                byteBuffer.put((byte) (i2 | i3));
                return;
            }
            byteBuffer.put((byte) (i3 | 126));
            byteBuffer.put((byte) (i2 >> 8));
            byteBuffer.put((byte) (i2 & 255));
            return;
        }
        byteBuffer.put((byte) (i3 | 127));
        byteBuffer.put((byte) 0);
        byteBuffer.put((byte) 0);
        byteBuffer.put((byte) 0);
        byteBuffer.put((byte) 0);
        byteBuffer.put((byte) ((i2 >> 24) & 255));
        byteBuffer.put((byte) ((i2 >> 16) & 255));
        byteBuffer.put((byte) ((i2 >> 8) & 255));
        byteBuffer.put((byte) (i2 & 255));
    }

    public static void appendLengthAndMask(ByteBuffer byteBuffer, int i2, byte[] bArr) {
        if (bArr == null) {
            appendLength(byteBuffer, i2, false);
        } else {
            appendLength(byteBuffer, i2, true);
            byteBuffer.put(bArr);
        }
    }

    public static byte[] generateMaskingKey() {
        SecureRandom secureRandom = new SecureRandom();
        return new byte[]{(byte) secureRandom.nextInt(255), (byte) secureRandom.nextInt(255), (byte) secureRandom.nextInt(255), (byte) secureRandom.nextInt(255)};
    }

    private void setFinAndOpCode(byte b2) {
        this.fin = (b2 & 128) != 0;
        this.opcode = (byte) (b2 & 15);
    }

    public byte[] encodeFrame() {
        byte[] bArr = this.payload;
        int length = bArr.length;
        int i2 = length + 6;
        if (bArr.length > 65535) {
            i2 = length + 14;
        } else if (bArr.length >= 126) {
            i2 = length + 8;
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(i2);
        appendFinAndOpCode(byteBufferAllocate, this.opcode, this.fin);
        byte[] bArrGenerateMaskingKey = generateMaskingKey();
        appendLengthAndMask(byteBufferAllocate, this.payload.length, bArrGenerateMaskingKey);
        int i3 = 0;
        while (true) {
            byte[] bArr2 = this.payload;
            if (i3 >= bArr2.length) {
                byteBufferAllocate.flip();
                return byteBufferAllocate.array();
            }
            byte b2 = (byte) (bArr2[i3] ^ bArrGenerateMaskingKey[i3 % 4]);
            bArr2[i3] = b2;
            byteBufferAllocate.put(b2);
            i3++;
        }
    }

    public byte getOpcode() {
        return this.opcode;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public boolean isCloseFlag() {
        return this.closeFlag;
    }

    public boolean isFin() {
        return this.fin;
    }

    public WebSocketFrame(byte[] bArr) {
        byte[] bArr2;
        int i2 = 0;
        this.closeFlag = false;
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        setFinAndOpCode(byteBufferWrap.get());
        byte b2 = byteBufferWrap.get();
        boolean z2 = (b2 & 128) != 0;
        int i3 = (byte) (b2 & Byte.MAX_VALUE);
        int i4 = i3 == 127 ? 8 : i3 == 126 ? 2 : 0;
        while (true) {
            i4--;
            if (i4 <= 0) {
                break;
            } else {
                i3 |= (byteBufferWrap.get() & 255) << (i4 * 8);
            }
        }
        if (z2) {
            bArr2 = new byte[4];
            byteBufferWrap.get(bArr2, 0, 4);
        } else {
            bArr2 = null;
        }
        byte[] bArr3 = new byte[i3];
        this.payload = bArr3;
        byteBufferWrap.get(bArr3, 0, i3);
        if (!z2) {
            return;
        }
        while (true) {
            byte[] bArr4 = this.payload;
            if (i2 >= bArr4.length) {
                return;
            }
            bArr4[i2] = (byte) (bArr4[i2] ^ bArr2[i2 % 4]);
            i2++;
        }
    }

    public WebSocketFrame(InputStream inputStream) throws IOException {
        byte[] bArr;
        int i2 = 0;
        this.closeFlag = false;
        setFinAndOpCode((byte) inputStream.read());
        byte b2 = this.opcode;
        if (b2 != 2) {
            if (b2 == 8) {
                this.closeFlag = true;
                return;
            }
            throw new IOException("Invalid Frame: Opcode: " + ((int) this.opcode));
        }
        byte b3 = (byte) inputStream.read();
        boolean z2 = (b3 & 128) != 0;
        int i3 = (byte) (b3 & Byte.MAX_VALUE);
        int i4 = i3 != 127 ? i3 == 126 ? 2 : 0 : 8;
        i3 = i4 > 0 ? 0 : i3;
        while (true) {
            i4--;
            if (i4 < 0) {
                break;
            } else {
                i3 |= (((byte) inputStream.read()) & 255) << (i4 * 8);
            }
        }
        if (z2) {
            bArr = new byte[4];
            inputStream.read(bArr, 0, 4);
        } else {
            bArr = null;
        }
        this.payload = new byte[i3];
        int i5 = 0;
        int i6 = i3;
        while (i5 != i3) {
            int i7 = inputStream.read(this.payload, i5, i6);
            i5 += i7;
            i6 -= i7;
        }
        if (!z2) {
            return;
        }
        while (true) {
            byte[] bArr2 = this.payload;
            if (i2 >= bArr2.length) {
                return;
            }
            bArr2[i2] = (byte) (bArr2[i2] ^ bArr[i2 % 4]);
            i2++;
        }
    }
}
