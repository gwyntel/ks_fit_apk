package com.aliyun.alink.linksdk.alcs.coap;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPConstant;
import com.aliyun.alink.linksdk.alcs.coap.option.BlockOption;
import com.aliyun.alink.linksdk.alcs.coap.option.OptionSet;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;

/* loaded from: classes2.dex */
public abstract class AlcsCoAPMessage {
    public static final int MAX_MID = 65535;
    public static final int NONE = -1;
    private static final String TAG = "AlcsCoAPMessage";
    private volatile boolean acknowledged;
    private volatile byte[] bytes;
    private volatile boolean canceled;
    private InetAddress destination;
    private int destinationPort;
    private volatile boolean duplicate;
    private int multicast;
    private OptionSet options;
    private byte[] payload;
    private volatile boolean rejected;
    private volatile Throwable sendError;
    private volatile boolean sent;
    private InetAddress source;
    private int sourcePort;
    private volatile boolean timedOut;
    private volatile long timestamp;
    private AlcsCoAPConstant.Type type;
    private volatile int mid = -1;
    private volatile byte[] token = null;

    protected AlcsCoAPMessage() {
    }

    public void cancel() {
        setCanceled(true);
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public InetAddress getDestination() {
        return this.destination;
    }

    public int getDestinationPort() {
        return this.destinationPort;
    }

    public int getMID() {
        return this.mid;
    }

    public OptionSet getOptions() {
        if (this.options == null) {
            this.options = new OptionSet();
        }
        return this.options;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public int getPayloadSize() {
        byte[] bArr = this.payload;
        if (bArr == null) {
            return 0;
        }
        return bArr.length;
    }

    public String getPayloadString() {
        byte[] bArr = this.payload;
        return bArr == null ? "" : new String(bArr, AlcsCoAPConstant.UTF8_CHARSET);
    }

    public abstract int getRawCode();

    public int getRawType() {
        AlcsCoAPConstant.Type type = this.type;
        if (type != null) {
            return type.value;
        }
        return 0;
    }

    public Throwable getSendError() {
        return this.sendError;
    }

    public InetAddress getSource() {
        return this.source;
    }

    public int getSourcePort() {
        return this.sourcePort;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public byte[] getToken() {
        return this.token;
    }

    public String getTokenString() {
        return Utils.toHexString(getToken());
    }

    public AlcsCoAPConstant.Type getType() {
        return this.type;
    }

    public boolean hasBlock(BlockOption blockOption) {
        return getPayloadSize() > 0 && blockOption.getOffset() < getPayloadSize();
    }

    public boolean hasEmptyToken() {
        return this.token == null || this.token.length == 0;
    }

    public boolean hasMID() {
        return this.mid != -1;
    }

    public boolean isAcknowledged() {
        return this.acknowledged;
    }

    public boolean isCanceled() {
        return this.canceled;
    }

    public boolean isConfirmable() {
        return getType() == AlcsCoAPConstant.Type.CON;
    }

    public boolean isDuplicate() {
        return this.duplicate;
    }

    public int isMulticast() {
        return this.multicast;
    }

    public boolean isRejected() {
        return this.rejected;
    }

    public boolean isSent() {
        return this.sent;
    }

    public boolean isTimedOut() {
        return this.timedOut;
    }

    public void removeMID() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        setMID(-1);
    }

    public void setAcknowledged(boolean z2) {
        this.acknowledged = z2;
    }

    public void setBytes(byte[] bArr) {
        this.bytes = bArr;
    }

    public void setCanceled(boolean z2) {
        this.canceled = z2;
    }

    public AlcsCoAPMessage setConfirmable(boolean z2) {
        setType(z2 ? AlcsCoAPConstant.Type.CON : AlcsCoAPConstant.Type.NON);
        return this;
    }

    public AlcsCoAPMessage setDestination(InetAddress inetAddress) {
        this.destination = inetAddress;
        return this;
    }

    public AlcsCoAPMessage setDestinationPort(int i2) {
        this.destinationPort = i2;
        return this;
    }

    public void setDuplicate(boolean z2) {
        this.duplicate = z2;
    }

    public AlcsCoAPMessage setMID(int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (i2 <= 65535 && i2 >= -1) {
            this.mid = i2;
            return this;
        }
        ALog.e(TAG, "The MID must be an unsigned 16-bit number but was " + i2);
        return this;
    }

    public void setMulticast(int i2) {
        this.multicast = i2;
    }

    public AlcsCoAPMessage setOptions(OptionSet optionSet) {
        this.options = new OptionSet(optionSet);
        return this;
    }

    public AlcsCoAPMessage setPayload(String str) {
        if (str == null) {
            this.payload = null;
        } else {
            setPayload(str.getBytes(AlcsCoAPConstant.UTF8_CHARSET));
        }
        return this;
    }

    public void setRejected(boolean z2) {
        this.rejected = z2;
    }

    public void setSendError(Throwable th) {
        this.sendError = th;
    }

    public void setSent(boolean z2) {
        this.sent = z2;
    }

    public void setSource(InetAddress inetAddress) {
        this.source = inetAddress;
    }

    public void setSourcePort(int i2) {
        this.sourcePort = i2;
    }

    public void setTimedOut(boolean z2) {
        this.timedOut = z2;
    }

    public void setTimestamp(long j2) {
        this.timestamp = j2;
    }

    public void setToken(byte[] bArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (bArr == null || bArr.length <= 8) {
            this.token = bArr;
        } else {
            ALog.e(TAG, "Token length must be between 0 and 8 inclusive");
        }
    }

    public AlcsCoAPMessage setType(AlcsCoAPConstant.Type type) {
        this.type = type;
        return this;
    }

    public void setType(int i2) {
        this.type = AlcsCoAPConstant.Type.valueOf(i2);
    }

    public void setPayload(byte[] bArr) {
        this.payload = bArr;
    }

    public AlcsCoAPMessage(AlcsCoAPConstant.Type type) {
        this.type = type;
    }

    public void setToken(String str) {
        if (TextUtils.isEmpty(str) || str.length() > 8) {
            return;
        }
        this.token = str.getBytes();
    }
}
