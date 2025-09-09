package com.alibaba.ailabs.iot.aisbase.spec;

import android.annotation.SuppressLint;
import android.util.Log;
import com.alibaba.ailabs.iot.aisbase.Utils;
import com.alibaba.ailabs.iot.aisbase.contant.GmaLogConst;
import com.alibaba.ailabs.iot.aisbase.exception.IllegalCommandException;
import com.alibaba.ailabs.iot.aisbase.exception.IncompletePayloadException;
import com.alibaba.ailabs.tg.utils.ConvertUtils;
import com.alibaba.ailabs.tg.utils.LogUtils;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class AISCommand {
    public static final int HEADER_LENGTH = 4;

    /* renamed from: a, reason: collision with root package name */
    public AISCommandHeader f8552a;

    /* renamed from: b, reason: collision with root package name */
    public byte[] f8553b;

    public static class AISCommandHeader {

        /* renamed from: a, reason: collision with root package name */
        public byte f8554a;

        /* renamed from: b, reason: collision with root package name */
        public byte f8555b;

        /* renamed from: c, reason: collision with root package name */
        public byte f8556c;

        /* renamed from: d, reason: collision with root package name */
        public byte f8557d;

        /* renamed from: e, reason: collision with root package name */
        public byte f8558e;

        /* renamed from: f, reason: collision with root package name */
        public byte f8559f;

        /* renamed from: g, reason: collision with root package name */
        public int f8560g;

        public AISCommandHeader(byte b2, byte b3, byte b4, byte b5, byte b6) {
            this.f8554a = b2;
            this.f8555b = (byte) 0;
            this.f8556c = (byte) 0;
            this.f8557d = b3;
            this.f8558e = b4;
            this.f8559f = b5;
            this.f8560g = b6;
        }

        public byte[] getBytes() {
            return new byte[]{(byte) (this.f8554a | (this.f8555b << 4) | (this.f8556c << 5)), this.f8557d, (byte) ((this.f8558e << 4) | this.f8559f), (byte) this.f8560g};
        }

        public byte getCommandType() {
            return this.f8557d;
        }

        public byte getEncryption() {
            return this.f8555b;
        }

        public byte getFrameSeq() {
            return this.f8559f;
        }

        public byte getMsgID() {
            return this.f8554a;
        }

        public int getPayloadLength() {
            return this.f8560g;
        }

        public byte getTotalFrame() {
            return this.f8558e;
        }

        public byte getVersion() {
            return this.f8556c;
        }

        public void setCommandType(byte b2) {
            this.f8557d = b2;
        }

        public void setEncryption(byte b2) {
            this.f8555b = b2;
        }

        public void setFrameSeq(byte b2) {
            this.f8559f = b2;
        }

        public void setPayloadLength(byte b2) {
            this.f8560g = b2;
        }

        public void setTotalFrame(byte b2) {
            this.f8558e = b2;
        }

        public void setVersion(byte b2) {
            this.f8556c = b2;
        }

        public void setsgID(byte b2) {
            this.f8554a = b2;
        }

        @SuppressLint({"DefaultLocale"})
        public String toString() {
            return String.format("Command(%s), flag(version: %d, encrypt: %d, message id: %d, total frame: %d, current frame sequence: %d,frame length: %d)", Utils.byte2String(this.f8557d, true), Byte.valueOf(this.f8556c), Byte.valueOf(this.f8555b), Byte.valueOf(this.f8554a), Byte.valueOf(this.f8558e), Byte.valueOf(this.f8559f), Integer.valueOf(this.f8560g));
        }

        public static AISCommandHeader a(byte[] bArr) {
            AISCommandHeader aISCommandHeader = new AISCommandHeader();
            byte b2 = bArr[0];
            aISCommandHeader.f8554a = (byte) (b2 & 15);
            aISCommandHeader.f8555b = (byte) ((b2 & 16) >> 4);
            aISCommandHeader.f8556c = (byte) ((b2 & 224) >> 5);
            aISCommandHeader.f8557d = bArr[1];
            byte b3 = bArr[2];
            aISCommandHeader.f8558e = (byte) ((b3 & 240) >> 4);
            aISCommandHeader.f8559f = (byte) (b3 & 15);
            aISCommandHeader.f8560g = bArr[3] & 255;
            return aISCommandHeader;
        }

        public AISCommandHeader() {
        }
    }

    public AISCommand() {
    }

    public static int getMessageSpec(byte b2, byte b3) {
        return b2;
    }

    @SuppressLint({"LongLogTag"})
    public static AISCommand parseFromByte(byte[] bArr) throws IllegalCommandException, IncompletePayloadException {
        if (bArr == null) {
            throw new IllegalCommandException();
        }
        Log.i(GmaLogConst.GMA_CONNECT_AUTH, "parseFromByte: " + ConvertUtils.bytes2HexString(bArr));
        if (bArr.length < 4) {
            throw new IncompletePayloadException("Header not complete", 0, 0);
        }
        AISCommand aISCommand = new AISCommand();
        AISCommandHeader aISCommandHeaderA = AISCommandHeader.a(Arrays.copyOfRange(bArr, 0, 4));
        aISCommand.f8552a = aISCommandHeaderA;
        if (aISCommandHeaderA.f8560g == 0) {
            aISCommand.f8553b = null;
        } else if (aISCommand.f8552a.f8560g > 0) {
            if (bArr.length < aISCommand.f8552a.f8560g + 4) {
                Log.e(GmaLogConst.GMA_CONNECT_AUTH, "***********receive error while processing with data: " + ConvertUtils.bytes2HexString(bArr));
                throw new IncompletePayloadException(String.format("Need %d payload, only %d in the payload in current packet", Integer.valueOf(aISCommand.f8552a.f8560g), Integer.valueOf(bArr.length - 4)), aISCommand.f8552a.f8560g, bArr.length - 4);
            }
            aISCommand.f8553b = Arrays.copyOfRange(bArr, 4, aISCommand.f8552a.f8560g + 4);
        }
        return aISCommand;
    }

    public byte[] getBytes() {
        byte[] bytes = this.f8552a.getBytes();
        byte[] bArr = this.f8553b;
        byte[] bArr2 = new byte[(bArr == null ? 0 : bArr.length) + 4];
        System.arraycopy(bytes, 0, bArr2, 0, 4);
        byte[] bArr3 = this.f8553b;
        if (bArr3 != null) {
            System.arraycopy(bArr3, 0, bArr2, 4, bArr3.length);
        }
        return bArr2;
    }

    public AISCommandHeader getHeader() {
        return this.f8552a;
    }

    public byte[] getPayload() {
        return this.f8553b;
    }

    public AISCommand setEnableEncrypt(boolean z2) {
        LogUtils.d("AISCommand", "set enable encrypt: " + z2);
        AISCommandHeader aISCommandHeader = this.f8552a;
        if (aISCommandHeader != null) {
            aISCommandHeader.setEncryption(z2 ? (byte) 1 : (byte) 0);
        }
        return this;
    }

    public void setHeader(AISCommandHeader aISCommandHeader) {
        this.f8552a = aISCommandHeader;
    }

    public void setPayload(byte[] bArr) {
        this.f8553b = bArr;
    }

    public AISCommand(byte b2, byte b3, byte b4, byte b5, byte b6, byte[] bArr) {
        this.f8552a = new AISCommandHeader(b2, b3, b4, b5, b6);
        this.f8553b = bArr;
    }

    public static int getMessageSpec(byte b2, byte b3, byte b4) {
        return b2 | (b3 << 8);
    }
}
