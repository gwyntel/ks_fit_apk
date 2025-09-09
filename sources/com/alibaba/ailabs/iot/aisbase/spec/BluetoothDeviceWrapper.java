package com.alibaba.ailabs.iot.aisbase.spec;

import aisscanner.ScanRecord;
import aisscanner.ScanResult;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.alibaba.ailabs.iot.aisbase.Ia;
import com.alibaba.ailabs.iot.aisbase.Ja;
import com.alibaba.ailabs.iot.aisbase.Ka;
import com.alibaba.ailabs.iot.aisbase.OTAUTLogDecorator;
import com.alibaba.ailabs.iot.aisbase.UTLogUtils;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.callback.ICommandSendListener;
import com.alibaba.ailabs.iot.aisbase.channel.LayerState;
import com.alibaba.ailabs.iot.aisbase.channel.TransmissionLayer;
import com.alibaba.ailabs.iot.aisbase.channel.TransmissionLayerManagerBase;
import com.alibaba.ailabs.iot.aisbase.plugin.IPlugin;
import com.alibaba.ailabs.iot.aisbase.plugin.basic.IBasicPlugin;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin;
import com.alibaba.ailabs.tg.utils.LogUtils;
import datasource.implemention.data.DeviceVersionInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class BluetoothDeviceWrapper implements Parcelable {
    public static final Parcelable.Creator<BluetoothDeviceWrapper> CREATOR = new Ia();
    public static final String TAG = "BluetoothDeviceWrapper";
    public AISManufacturerADData mAisManufactureDataADV;
    public IBasicPlugin mBasicPlugin;
    public BluetoothVersion mBleVersion;
    public BluetoothDevice mBluetoothDevice;
    public volatile TransmissionLayerManagerBase mChannelManager;
    public DeviceAbility mDeviceAbility;
    public boolean mFromLeScan;
    public List<IPlugin> mInstalledPlugins;
    public boolean mIsAdvMode;
    public boolean mIsSafetyMode;
    public String mMac;
    public String mName;
    public boolean mNetConfigFlag;
    public IOTAPlugin mOtaPlugin;
    public int mRssi;
    public ScanRecord mScanRecord;
    public SecretType mSecretType;
    public BluetoothDeviceSubtype mSubtype;
    public boolean mSupportOTA;

    enum BluetoothVersion {
        BLE_4_0,
        BLE_4_2,
        BLE_5_0,
        BLE_5_X
    }

    public enum EncodeFormat {
        UNKNOWN,
        PCM,
        ADPCM,
        Ogg,
        Opus,
        Optimized_ADPCM;

        public static EncodeFormat parseFromInt(int i2) {
            return i2 != 16 ? i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? UNKNOWN : Optimized_ADPCM : Opus : ADPCM : PCM : Ogg;
        }
    }

    enum SecretType {
        ONE_SECRET_ONE_TYPE,
        ONE_SECRET_ONE_DEVICES
    }

    public BluetoothDeviceWrapper() {
        this.mSubtype = BluetoothDeviceSubtype.UNKNOWN;
        this.mRssi = 0;
        this.mFromLeScan = false;
        this.mInstalledPlugins = new ArrayList();
        this.mNetConfigFlag = false;
    }

    public void checkDeviceNewVersion(IActionListener<DeviceVersionInfo> iActionListener) {
        if (makeSurePluginIsInitialized(this.mOtaPlugin, iActionListener)) {
            this.mOtaPlugin.checkNewVersion(getAisManufactureDataADV().getPidStr(), getAddress(), "", iActionListener);
        }
    }

    public BluetoothDeviceWrapper connect(Context context, IActionListener<BluetoothDevice> iActionListener) {
        return this;
    }

    public TransmissionLayerManagerBase createChannelManager(Context context, TransmissionLayer transmissionLayer) {
        return null;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void disconnect(IActionListener<BluetoothDevice> iActionListener) {
        if (this.mChannelManager == null) {
            LogUtils.w(TAG, String.format("TransmissionManager is null, disconnect on device(%s) failed", this.mBluetoothDevice.getAddress()));
            iActionListener.onFailure(-201, String.format("TransmissionManager is null, disconnect on device(%s) failed", this.mBluetoothDevice.getAddress()));
        } else {
            UTLogUtils.updateBusInfo("disconnection", UTLogUtils.buildDeviceInfo(this), UTLogUtils.buildDisconnectionBusInfo(this.mChannelManager.getLayerType()));
            if (iActionListener == null) {
                iActionListener = new Ja(this);
            }
            this.mChannelManager.getTransmissionLayer().disconnectDevice(iActionListener);
        }
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public String getAddress() {
        return this.mMac;
    }

    public AISManufacturerADData getAisManufactureDataADV() {
        return this.mAisManufactureDataADV;
    }

    public BluetoothVersion getBleVersion() {
        return this.mBleVersion;
    }

    public BluetoothDevice getBluetoothDevice() {
        return this.mBluetoothDevice;
    }

    public LayerState getConnectionState() {
        return this.mChannelManager == null ? LayerState.NONE : this.mChannelManager.getTransmissionLayer().getConnectionState();
    }

    public DeviceAbility getDeviceAbility() {
        return this.mDeviceAbility;
    }

    public TransmissionLayer getLayerType() {
        return this.mChannelManager == null ? TransmissionLayer.NONE : this.mChannelManager.getLayerType();
    }

    public String getName() {
        return this.mName;
    }

    public int getRssi() {
        return this.mRssi;
    }

    public ScanRecord getScanRecord() {
        return this.mScanRecord;
    }

    public SecretType getSecretType() {
        return this.mSecretType;
    }

    public BluetoothDeviceSubtype getSubtype() {
        return this.mSubtype;
    }

    public void initTransmissionManager(Context context, TransmissionLayer transmissionLayer) {
        if (this.mChannelManager == null) {
            this.mChannelManager = createChannelManager(context, transmissionLayer);
        }
        if (this.mChannelManager.getLayerType() != transmissionLayer) {
            this.mChannelManager.switchTransmissionLayer(transmissionLayer);
        }
        installPlugins(transmissionLayer);
    }

    public void installPlugins(TransmissionLayer transmissionLayer) {
    }

    public boolean isIsSafetyMode() {
        return this.mIsSafetyMode;
    }

    public boolean isNetConfigFlag() {
        return this.mNetConfigFlag;
    }

    public boolean isSupportOTA() {
        return true;
    }

    public boolean ismFromLeScan() {
        return this.mFromLeScan;
    }

    public boolean ismIsAdvMode() {
        return this.mIsAdvMode;
    }

    public boolean makeSurePluginIsInitialized(IPlugin iPlugin, IActionListener iActionListener) {
        if (this.mChannelManager == null || this.mChannelManager.getTransmissionLayer() == null || this.mChannelManager.getTransmissionLayer().getConnectionState() != LayerState.CONNECTED) {
            if (iActionListener != null) {
                iActionListener.onFailure(-1, "Not connected");
            }
            return false;
        }
        if (iPlugin != null) {
            return true;
        }
        if (iActionListener != null) {
            iActionListener.onFailure(-202, "Not supported");
        }
        return false;
    }

    public void sendAISCommand(byte[] bArr, ICommandSendListener iCommandSendListener) {
        IBasicPlugin iBasicPlugin = this.mBasicPlugin;
        if (iBasicPlugin == null && iCommandSendListener != null) {
            iCommandSendListener.onFailure(-202, "Please connect first");
        } else if (iBasicPlugin != null) {
            iBasicPlugin.sendCommand(bArr, iCommandSendListener);
        }
    }

    public void setAisManufactureDataADV(AISManufacturerADData aISManufacturerADData) {
        if (aISManufacturerADData == null) {
            return;
        }
        this.mAisManufactureDataADV = aISManufacturerADData;
        byte fMask = aISManufacturerADData.getFMask();
        int i2 = fMask & 3;
        if (i2 == 0) {
            this.mBleVersion = BluetoothVersion.BLE_4_0;
        } else if (i2 == 1) {
            this.mBleVersion = BluetoothVersion.BLE_4_2;
        } else if (i2 == 16) {
            this.mBleVersion = BluetoothVersion.BLE_5_0;
        } else if (i2 == 17) {
            this.mBleVersion = BluetoothVersion.BLE_5_X;
        }
        this.mSupportOTA = ((fMask >> 2) & 1) == 1;
        this.mIsSafetyMode = ((fMask >> 3) & 1) == 1;
        int i3 = (fMask >> 4) & 1;
        this.mSecretType = i3 == 1 ? SecretType.ONE_SECRET_ONE_DEVICES : SecretType.ONE_SECRET_ONE_TYPE;
        this.mIsAdvMode = i3 == 0;
        this.mSubtype = BluetoothDeviceSubtype.parseFromByte(this.mAisManufactureDataADV.getBluetoothSubtype());
        this.mNetConfigFlag = ((fMask >> 5) & 1) == 1;
    }

    public void setBleVersion(BluetoothVersion bluetoothVersion) {
        this.mBleVersion = bluetoothVersion;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.mBluetoothDevice = bluetoothDevice;
        this.mName = bluetoothDevice.getName();
        this.mMac = bluetoothDevice.getAddress();
        setSubtype(BluetoothDeviceSubtype.UNKNOWN);
    }

    public void setFromLeScan(boolean z2) {
        this.mFromLeScan = z2;
    }

    public void setIsAdvMode(boolean z2) {
        this.mIsAdvMode = z2;
    }

    public void setIsSafetyMode(boolean z2) {
        this.mIsSafetyMode = z2;
    }

    public void setName(String str) {
        this.mName = str;
    }

    public void setNetConfigFlag(boolean z2) {
        this.mNetConfigFlag = z2;
    }

    public void setRssi(int i2) {
        this.mRssi = i2;
    }

    public void setScanRecord(ScanRecord scanRecord) {
        this.mScanRecord = scanRecord;
    }

    public void setScanResult(ScanResult scanResult) {
        this.mBluetoothDevice = scanResult.getDevice();
        this.mRssi = scanResult.getRssi();
        this.mMac = this.mBluetoothDevice.getAddress();
        ScanRecord scanRecord = scanResult.getScanRecord();
        this.mScanRecord = scanRecord;
        if (scanRecord != null) {
            String deviceName = scanRecord.getDeviceName();
            this.mName = deviceName;
            if (TextUtils.isEmpty(deviceName)) {
                this.mName = this.mBluetoothDevice.getName();
            }
        }
    }

    public void setSecretType(SecretType secretType) {
        this.mSecretType = secretType;
    }

    public void setSubtype(BluetoothDeviceSubtype bluetoothDeviceSubtype) {
        this.mSubtype = bluetoothDeviceSubtype;
    }

    public void setSupportOTA(boolean z2) {
        this.mSupportOTA = z2;
    }

    public void startDownloadDeviceFirmware(Context context, DeviceVersionInfo deviceVersionInfo, String str, IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener) {
        Ka ka = new Ka(this, iFirmwareDownloadListener);
        UTLogUtils.updateBusInfo("ota", UTLogUtils.buildDeviceInfo(this), UTLogUtils.buildOtaBusInfo("start", (this.mChannelManager == null || this.mChannelManager.getTransmissionLayer() == null) ? 0 : this.mChannelManager.getTransmissionLayer().getMtu(), 0, ""));
        IOTAPlugin iOTAPlugin = this.mOtaPlugin;
        if (iOTAPlugin == null) {
            ka.onFailed(-202, "The device does not support ota or is not connected");
        } else {
            iOTAPlugin.startDownloadFirmware(context, deviceVersionInfo, str, ka);
        }
    }

    public void startOTA(String str, IOTAPlugin.IOTAActionListener iOTAActionListener) {
        OTAUTLogDecorator oTAUTLogDecorator = new OTAUTLogDecorator(iOTAActionListener, this);
        IOTAPlugin iOTAPlugin = this.mOtaPlugin;
        if (iOTAPlugin == null) {
            oTAUTLogDecorator.onFailed(-202, "The device does not support ota or is not connected");
        } else {
            iOTAPlugin.startOTA(str, oTAUTLogDecorator);
        }
    }

    public void stopDownloadDeviceFirmware() {
        IOTAPlugin iOTAPlugin = this.mOtaPlugin;
        if (iOTAPlugin == null) {
            LogUtils.w(TAG, "The device does not support ota or is not connected");
        } else {
            iOTAPlugin.stopDownloadFirmware();
        }
    }

    public void stopOTA() {
        this.mOtaPlugin.stopOTA();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeParcelable(this.mBluetoothDevice, i2);
        parcel.writeString(this.mName);
        parcel.writeString(this.mMac);
        parcel.writeParcelable(this.mDeviceAbility, i2);
        parcel.writeByte(this.mSupportOTA ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mIsSafetyMode ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mIsAdvMode ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.mRssi);
        parcel.writeByte(this.mFromLeScan ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mNetConfigFlag ? (byte) 1 : (byte) 0);
        parcel.writeParcelable(this.mAisManufactureDataADV, i2);
    }

    public BluetoothDeviceWrapper connect(Context context, TransmissionLayer transmissionLayer, boolean z2, IActionListener<BluetoothDevice> iActionListener) {
        return this;
    }

    public BluetoothDeviceWrapper(Parcel parcel) {
        this.mSubtype = BluetoothDeviceSubtype.UNKNOWN;
        this.mRssi = 0;
        this.mFromLeScan = false;
        this.mInstalledPlugins = new ArrayList();
        this.mNetConfigFlag = false;
        this.mBluetoothDevice = (BluetoothDevice) parcel.readParcelable(BluetoothDevice.class.getClassLoader());
        this.mName = parcel.readString();
        this.mMac = parcel.readString();
        this.mDeviceAbility = (DeviceAbility) parcel.readParcelable(DeviceAbility.class.getClassLoader());
        this.mSupportOTA = parcel.readByte() != 0;
        this.mIsSafetyMode = parcel.readByte() != 0;
        this.mIsAdvMode = parcel.readByte() != 0;
        this.mRssi = parcel.readInt();
        this.mFromLeScan = parcel.readByte() != 0;
        this.mNetConfigFlag = parcel.readByte() != 0;
        AISManufacturerADData aISManufacturerADData = (AISManufacturerADData) parcel.readParcelable(AISManufacturerADData.class.getClassLoader());
        this.mAisManufactureDataADV = aISManufacturerADData;
        setAisManufactureDataADV(aISManufacturerADData);
    }
}
