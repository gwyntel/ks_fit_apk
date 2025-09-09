package com.alibaba.ailabs.iot.bleadvertise.msg.control;

/* loaded from: classes2.dex */
public enum InexpensiveControlCmdType {
    SEND_CTRL_CMD((byte) 6),
    CTRL_CMD_ACK((byte) 7);

    public byte type;

    InexpensiveControlCmdType(byte b2) {
        this.type = b2;
    }

    public byte getType() {
        return this.type;
    }
}
