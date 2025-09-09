package com.alibaba.ailabs.iot.aisbase.plugin.ota;

import android.content.Context;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.plugin.IPlugin;
import datasource.implemention.data.DeviceVersionInfo;

/* loaded from: classes2.dex */
public interface IOTAPlugin extends IPlugin {

    public interface IFirmwareDownloadListener {
        void onComplete(String str);

        void onDownloadStart();

        void onFailed(int i2, String str);

        void onProgress(int i2, int i3);
    }

    public interface IOTAActionListener {
        void onFailed(int i2, String str);

        void onProgress(int i2, int i3);

        void onStateChanged(OTAState oTAState);

        void onSuccess(String str);
    }

    public enum OTAState {
        IDLE,
        REQUEST,
        WAIT_REQUEST_RESPONSE,
        OTA_PROGRESS,
        REQUEST_VERIFY,
        WAIT_VERIFY_RESPONSE,
        WAIT_RECONNECT,
        WAIT_RECHECK_VERSION,
        VERIFY_SUCCESS,
        ERROR,
        FINISH
    }

    void checkNewVersion(String str, String str2, String str3, IActionListener<DeviceVersionInfo> iActionListener);

    void enableReportProgress(boolean z2);

    void sendGetFirmwareVersionCommand(IActionListener iActionListener);

    void sendGetFirmwareVersionCommandV1(IActionListener iActionListener);

    void sendOTAFinishAndRequestVerifyCommand();

    void setSendCompleteFrameWithNoAckFlag(boolean z2);

    void startDownloadFirmware(Context context, DeviceVersionInfo deviceVersionInfo, String str, IFirmwareDownloadListener iFirmwareDownloadListener);

    void startOTA(String str, IOTAActionListener iOTAActionListener);

    void startOTA(byte[] bArr, byte[] bArr2, byte[] bArr3, byte b2, byte[] bArr4, byte b3, IOTAActionListener iOTAActionListener);

    void stopDownloadFirmware();

    void stopOTA();
}
