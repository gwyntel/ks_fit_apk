package com.alibaba.ailabs.iot.aisbase.plugin;

import com.alibaba.ailabs.iot.aisbase.AESUtil;
import com.alibaba.ailabs.iot.aisbase.channel.ITransmissionLayer;
import com.alibaba.ailabs.iot.aisbase.dispatcher.CommandResponseDispatcher;
import com.alibaba.ailabs.iot.aisbase.exception.UnsupportedLayerException;
import com.alibaba.ailabs.iot.aisbase.spec.AISCommand;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;
import com.alibaba.ailabs.tg.utils.LogUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class PluginBase implements IPlugin {

    /* renamed from: a, reason: collision with root package name */
    public static final String f8454a = "PluginBase";

    /* renamed from: b, reason: collision with root package name */
    public AESUtil f8455b;
    public byte[] mAesKey;
    public ITransmissionLayer mTransmissionLayer;
    public boolean mEnableAesEncryption = false;

    /* renamed from: c, reason: collision with root package name */
    public BluetoothDeviceWrapper f8456c = null;

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.IPlugin
    public void enableAESEncryption(byte[] bArr) {
        boolean z2 = bArr != null;
        this.mEnableAesEncryption = z2;
        if (z2) {
            AESUtil aESUtil = AESUtil.getInstance();
            this.f8455b = aESUtil;
            aESUtil.setKey(bArr);
        }
        this.mAesKey = bArr;
        if (this.mTransmissionLayer == null) {
            return;
        }
        for (String str : getChannelUUIDs()) {
            CommandResponseDispatcher commandResponseDispatcher = this.mTransmissionLayer.getCommandResponseDispatcher(str);
            if (commandResponseDispatcher != null) {
                commandResponseDispatcher.enableAESEncryption(bArr);
            }
        }
    }

    public byte[] encryptPayload(byte[] bArr) {
        return (!this.mEnableAesEncryption || bArr == null) ? bArr : this.f8455b.encrypt(AESUtil.PKCS7PADDING_CIPHER_ALGORITHM, bArr);
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.IPlugin
    public BluetoothDeviceWrapper getBluetoothDeviceWrapper() {
        return this.f8456c;
    }

    public abstract String[] getChannelUUIDs();

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.IPlugin
    public void init(ITransmissionLayer iTransmissionLayer) throws UnsupportedLayerException {
        this.mTransmissionLayer = iTransmissionLayer;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.IPlugin
    public void setBluetoothDeviceWrapper(BluetoothDeviceWrapper bluetoothDeviceWrapper) {
        this.f8456c = bluetoothDeviceWrapper;
    }

    public List<AISCommand> splitDataToCommands(int i2, int i3, byte b2, byte[] bArr, boolean z2) {
        byte[] bArr2;
        byte[] bArr3;
        byte[] bArrEncryptPayload = z2 ? encryptPayload(bArr) : bArr;
        ArrayList arrayList = new ArrayList();
        int i4 = 0;
        int i5 = i3;
        if (i5 > 16) {
            i5 = 0;
        }
        if (bArrEncryptPayload == null) {
            AISCommand aISCommand = new AISCommand(this.mTransmissionLayer.generateMessageID(), b2, (byte) 0, (byte) i5, (byte) 0, null);
            aISCommand.setEnableEncrypt(this.mEnableAesEncryption);
            arrayList.add(aISCommand);
            return arrayList;
        }
        int mtu = this.mTransmissionLayer.getMtu();
        LogUtils.d(f8454a, "transmission mtu: " + mtu);
        int i6 = mtu + (-7);
        int length = bArrEncryptPayload.length / i6;
        if (bArrEncryptPayload.length % i6 == 0) {
            length--;
        }
        int i7 = 0;
        if (i5 != 0) {
            while (i5 <= i2) {
                int iMin = Math.min(bArrEncryptPayload.length - i7, i6);
                if (iMin != 0) {
                    byte[] bArr4 = new byte[iMin];
                    System.arraycopy(bArrEncryptPayload, i7, bArr4, 0, iMin);
                    i7 += iMin;
                    bArr3 = bArr4;
                } else {
                    bArr3 = null;
                }
                AISCommand aISCommand2 = new AISCommand(this.mTransmissionLayer.generateMessageID(), b2, (byte) i2, (byte) (i5 % (i2 + 1)), (byte) iMin, bArr3);
                aISCommand2.setEnableEncrypt(this.mEnableAesEncryption);
                arrayList.add(aISCommand2);
                length--;
                i5++;
            }
        }
        if (i7 < bArrEncryptPayload.length) {
            int i8 = 0;
            while (i8 <= length) {
                int iMin2 = Math.min(bArrEncryptPayload.length - i7, i6);
                if (iMin2 != 0) {
                    byte[] bArr5 = new byte[iMin2];
                    System.arraycopy(bArrEncryptPayload, i7, bArr5, i4, iMin2);
                    i7 += iMin2;
                    bArr2 = bArr5;
                } else {
                    bArr2 = null;
                }
                AISCommand aISCommand3 = new AISCommand(this.mTransmissionLayer.generateMessageID(), b2, (byte) (((i8 / 16) + 1) * 16 > length ? length % 16 : 15), (byte) (i8 % 16), (byte) iMin2, bArr2);
                aISCommand3.setEnableEncrypt(this.mEnableAesEncryption);
                arrayList.add(aISCommand3);
                i8++;
                i4 = 0;
            }
        }
        return arrayList;
    }

    public List<AISCommand> splitFirmwareBinToFixedQuantityAISCommands(int i2, int i3, byte b2, byte[] bArr, boolean z2) {
        byte[] bArr2;
        byte[] bArrEncryptPayload = z2 ? encryptPayload(bArr) : bArr;
        ArrayList arrayList = new ArrayList();
        int i4 = 0;
        z = false;
        boolean z3 = false;
        int i5 = i3;
        if (i5 > i2) {
            i5 = 0;
        }
        if (bArrEncryptPayload == null) {
            AISCommand aISCommand = new AISCommand(this.mTransmissionLayer.generateMessageID(), b2, (byte) 0, (byte) i5, (byte) 0, null);
            if (z2 && this.mEnableAesEncryption) {
                z3 = true;
            }
            aISCommand.setEnableEncrypt(z3);
            arrayList.add(aISCommand);
            return arrayList;
        }
        int length = bArrEncryptPayload.length;
        int mtu = this.mTransmissionLayer.getMtu();
        LogUtils.d(f8454a, "transmission mtu: " + mtu);
        int i6 = 0;
        while (i5 <= i2) {
            int iMin = Math.min(bArrEncryptPayload.length - i6, mtu - 7);
            int i7 = i6 + iMin;
            if (i7 > length) {
                break;
            }
            if (iMin != 0) {
                bArr2 = new byte[iMin];
                System.arraycopy(bArrEncryptPayload, i6, bArr2, i4, iMin);
                i6 = i7;
            } else {
                bArr2 = null;
            }
            AISCommand aISCommand2 = new AISCommand(this.mTransmissionLayer.generateMessageID(), b2, (byte) i2, (byte) (i5 % (i2 + 1)), (byte) iMin, bArr2);
            aISCommand2.setEnableEncrypt(z2 && this.mEnableAesEncryption);
            arrayList.add(aISCommand2);
            i5++;
            i4 = 0;
        }
        return arrayList;
    }

    public List<AISCommand> splitDataToCommands(byte b2, byte[] bArr, boolean z2) {
        byte[] bArr2;
        byte[] bArrEncryptPayload = z2 ? encryptPayload(bArr) : bArr;
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        if (bArrEncryptPayload == null) {
            AISCommand aISCommand = new AISCommand(this.mTransmissionLayer.generateMessageID(), b2, (byte) 0, (byte) 0, (byte) 0, null);
            aISCommand.setEnableEncrypt(z2 && this.mEnableAesEncryption);
            arrayList.add(aISCommand);
            return arrayList;
        }
        int mtu = this.mTransmissionLayer.getMtu();
        LogUtils.d(f8454a, "transmission mtu: " + mtu);
        int i3 = mtu + (-7);
        int length = bArrEncryptPayload.length / i3;
        if (bArrEncryptPayload.length % i3 == 0) {
            length--;
        }
        int i4 = 0;
        int i5 = 0;
        while (i4 <= length) {
            int iMin = Math.min(bArrEncryptPayload.length - i5, i3);
            if (iMin != 0) {
                bArr2 = new byte[iMin];
                System.arraycopy(bArrEncryptPayload, i5, bArr2, 0, iMin);
                i5 += iMin;
            } else {
                bArr2 = null;
            }
            AISCommand aISCommand2 = new AISCommand(this.mTransmissionLayer.generateMessageID(), b2, (byte) (((i4 / 16) + i2) * 16 > length ? length % 16 : 15), (byte) (i4 % 16), (byte) iMin, bArr2);
            aISCommand2.setEnableEncrypt(z2 && this.mEnableAesEncryption);
            arrayList.add(aISCommand2);
            i4++;
            i2 = 1;
        }
        return arrayList;
    }
}
