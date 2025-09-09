package com.alibaba.ailabs.iot.aisbase.dispatcher;

import aisble.callback.profile.ProfileDataCallback;
import aisble.data.Data;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.util.Log;
import android.util.SparseArray;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.AESUtil;
import com.alibaba.ailabs.iot.aisbase.contant.GmaLogConst;
import com.alibaba.ailabs.iot.aisbase.exception.IllegalCommandException;
import com.alibaba.ailabs.iot.aisbase.exception.IncompletePayloadException;
import com.alibaba.ailabs.iot.aisbase.spec.AISCommand;
import com.alibaba.ailabs.tg.utils.ConvertUtils;
import com.alibaba.ailabs.tg.utils.LogUtils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes2.dex */
public class CommandResponseDispatcher implements ProfileDataCallback {

    /* renamed from: a, reason: collision with root package name */
    public static final String f8386a = "CommandResponseDispatcher";

    /* renamed from: e, reason: collision with root package name */
    public byte f8390e;

    /* renamed from: g, reason: collision with root package name */
    public OnCommandReceivedListener f8392g;

    /* renamed from: h, reason: collision with root package name */
    public byte[] f8393h;

    /* renamed from: j, reason: collision with root package name */
    public byte[] f8395j;

    /* renamed from: l, reason: collision with root package name */
    public AESUtil f8397l;

    /* renamed from: b, reason: collision with root package name */
    public SparseArray<byte[]> f8387b = new SparseArray<>();

    /* renamed from: c, reason: collision with root package name */
    public Set<Byte> f8388c = new HashSet();

    /* renamed from: d, reason: collision with root package name */
    public boolean f8389d = false;

    /* renamed from: f, reason: collision with root package name */
    public SparseArray<OnCommandReceivedListener> f8391f = new SparseArray<>();

    /* renamed from: i, reason: collision with root package name */
    public int f8394i = -1;

    /* renamed from: k, reason: collision with root package name */
    public boolean f8396k = false;

    public interface OnCommandReceivedListener {
        void onCommandReceived(byte b2, byte b3, byte[] bArr);
    }

    @SuppressLint({"DefaultLocale", "LongLogTag"})
    public final void a(@NonNull BluetoothDevice bluetoothDevice, @NonNull Data data, boolean z2) {
        byte[] value;
        if (this.f8393h != null) {
            int length = data.getValue().length;
            byte[] bArr = this.f8393h;
            value = new byte[length + bArr.length];
            System.arraycopy(bArr, 0, value, 0, bArr.length);
            if (data.getValue() != null) {
                System.arraycopy(data.getValue(), 0, value, this.f8393h.length, data.getValue().length);
            }
            this.f8393h = null;
        } else {
            value = data.getValue();
        }
        try {
            try {
                AISCommand fromByte = AISCommand.parseFromByte(value);
                if (this.f8394i == -1) {
                    this.f8394i = fromByte.getHeader().getMsgID();
                } else {
                    byte msgID = fromByte.getHeader().getMsgID();
                    int i2 = this.f8394i;
                    if ((i2 != 15 || msgID != 0) && ((i2 == 15 && msgID != 0) || i2 + 1 != msgID)) {
                        LogUtils.w(f8386a, String.format("Packet loss: %d: %d", Integer.valueOf(i2), Byte.valueOf(msgID)));
                    }
                    this.f8394i = msgID;
                }
                byte totalFrame = fromByte.getHeader().getTotalFrame();
                byte frameSeq = fromByte.getHeader().getFrameSeq();
                byte commandType = fromByte.getHeader().getCommandType();
                this.f8389d = true;
                this.f8390e = commandType;
                if (totalFrame == 0 && frameSeq == 0) {
                    this.f8387b.put(commandType, fromByte.getPayload());
                } else {
                    byte[] payload = fromByte.getPayload();
                    if (payload != null) {
                        byte[] bArr2 = this.f8387b.get(commandType);
                        if (bArr2 == null) {
                            this.f8387b.put(commandType, payload);
                        } else {
                            byte[] bArr3 = new byte[payload.length + bArr2.length];
                            System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
                            System.arraycopy(payload, 0, bArr3, bArr2.length, payload.length);
                            this.f8387b.put(commandType, bArr3);
                        }
                    }
                }
                boolean zContains = this.f8388c.contains(Byte.valueOf(commandType));
                if (totalFrame == frameSeq || zContains) {
                    this.f8389d = false;
                    byte[] bArrDecryptPayload = this.f8387b.get(commandType);
                    if (fromByte.getHeader().getEncryption() == 1) {
                        bArrDecryptPayload = decryptPayload(bArrDecryptPayload);
                    }
                    OnCommandReceivedListener onCommandReceivedListener = this.f8392g;
                    if (onCommandReceivedListener != null) {
                        onCommandReceivedListener.onCommandReceived(commandType, fromByte.getHeader().getMsgID(), bArrDecryptPayload);
                    }
                    LogUtils.w(f8386a, String.format("cmdType: %d", Integer.valueOf(commandType)));
                    for (int i3 = 0; i3 < this.f8391f.size(); i3++) {
                        String str = f8386a;
                        StringBuilder sb = new StringBuilder();
                        sb.append("subscribe=");
                        sb.append(this.f8391f.valueAt(i3));
                        LogUtils.w(str, String.format(sb.toString(), new Object[0]));
                    }
                    OnCommandReceivedListener onCommandReceivedListener2 = this.f8391f.get(commandType);
                    if (onCommandReceivedListener2 != null) {
                        onCommandReceivedListener2.onCommandReceived(commandType, fromByte.getHeader().getMsgID(), bArrDecryptPayload);
                    }
                    this.f8387b.remove(commandType);
                }
                if (fromByte.getHeader().getPayloadLength() < value.length - 4) {
                    a(bluetoothDevice, new Data(Arrays.copyOfRange(value, fromByte.getHeader().getPayloadLength() + 4, value.length)), true);
                }
            } catch (IncompletePayloadException e2) {
                Log.e(GmaLogConst.GMA_CONNECT_AUTH, e2.toString());
                this.f8393h = value;
            }
        } catch (IllegalCommandException e3) {
            LogUtils.e(f8386a, e3.toString());
            this.f8389d = false;
        }
    }

    public byte[] decryptPayload(byte[] bArr) {
        if (!this.f8396k || bArr == null) {
            return bArr;
        }
        if (this.f8397l == null) {
            AESUtil aESUtil = AESUtil.getInstance();
            this.f8397l = aESUtil;
            aESUtil.setKey(this.f8395j);
        }
        return this.f8397l.decrypt(bArr);
    }

    public void enableAESEncryption(byte[] bArr) {
        LogUtils.d(f8386a, "enableAESEncryption: " + ConvertUtils.bytes2HexString(bArr));
        this.f8396k = bArr != null;
        this.f8395j = bArr;
        if (bArr != null) {
            AESUtil aESUtil = AESUtil.getInstance();
            this.f8397l = aESUtil;
            aESUtil.setKey(bArr);
        }
    }

    @Override // aisble.callback.DataReceivedCallback
    public void onDataReceived(@NonNull BluetoothDevice bluetoothDevice, @NonNull Data data) {
        a(bluetoothDevice, data, false);
    }

    @Override // aisble.callback.profile.ProfileDataCallback
    public void onInvalidDataReceived(@NonNull BluetoothDevice bluetoothDevice, @NonNull Data data) {
    }

    public void reset() {
        LogUtils.d(f8386a, "Reset...");
        this.f8393h = null;
        this.f8394i = -1;
    }

    public void setCommandReassembleByFrameSeq(byte b2, boolean z2) {
        if (z2) {
            this.f8388c.add(Byte.valueOf(b2));
        } else {
            this.f8388c.remove(Byte.valueOf(b2));
        }
    }

    public void subscribeMultiCommandReceivedListener(byte[] bArr, OnCommandReceivedListener onCommandReceivedListener) {
        for (byte b2 : bArr) {
            this.f8391f.put(b2, onCommandReceivedListener);
        }
    }

    public void unsubscribeMultiCommandReceivedListener(byte[] bArr) {
        for (byte b2 : bArr) {
            this.f8391f.remove(b2);
        }
    }
}
