package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class MessageReplyDataInfo {
    private int commandId;
    private int magic;
    private byte[] payLoad;

    public int getCommandId() {
        return this.commandId;
    }

    public int getMagic() {
        return this.magic;
    }

    public byte[] getPayLoad() {
        return this.payLoad;
    }

    public void setCommandId(int i2) {
        this.commandId = i2;
    }

    public void setMagic(int i2) {
        this.magic = i2;
    }

    public void setPayLoad(byte[] bArr) {
        this.payLoad = bArr;
    }
}
