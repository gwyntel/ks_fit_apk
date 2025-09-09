package com.alibaba.ailabs.iot.aisbase.plugin.ota;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.SparseArray;
import com.alibaba.ailabs.iot.aisbase.Aa;
import com.alibaba.ailabs.iot.aisbase.Ba;
import com.alibaba.ailabs.iot.aisbase.C0389aa;
import com.alibaba.ailabs.iot.aisbase.C0391ba;
import com.alibaba.ailabs.iot.aisbase.C0393ca;
import com.alibaba.ailabs.iot.aisbase.C0401ga;
import com.alibaba.ailabs.iot.aisbase.C0403ha;
import com.alibaba.ailabs.iot.aisbase.C0405ia;
import com.alibaba.ailabs.iot.aisbase.C0407ja;
import com.alibaba.ailabs.iot.aisbase.C0409ka;
import com.alibaba.ailabs.iot.aisbase.C0411la;
import com.alibaba.ailabs.iot.aisbase.C0415na;
import com.alibaba.ailabs.iot.aisbase.C0417oa;
import com.alibaba.ailabs.iot.aisbase.C0421qa;
import com.alibaba.ailabs.iot.aisbase.C0423ra;
import com.alibaba.ailabs.iot.aisbase.C0427ta;
import com.alibaba.ailabs.iot.aisbase.C0431va;
import com.alibaba.ailabs.iot.aisbase.C0433wa;
import com.alibaba.ailabs.iot.aisbase.C0435xa;
import com.alibaba.ailabs.iot.aisbase.Ca;
import com.alibaba.ailabs.iot.aisbase.Constants;
import com.alibaba.ailabs.iot.aisbase.RequestManage;
import com.alibaba.ailabs.iot.aisbase.RunnableC0397ea;
import com.alibaba.ailabs.iot.aisbase.RunnableC0413ma;
import com.alibaba.ailabs.iot.aisbase.RunnableC0419pa;
import com.alibaba.ailabs.iot.aisbase.RunnableC0429ua;
import com.alibaba.ailabs.iot.aisbase.Utils;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.callback.ICastEventListener;
import com.alibaba.ailabs.iot.aisbase.callback.OtaActionListener;
import com.alibaba.ailabs.iot.aisbase.channel.ITransmissionLayer;
import com.alibaba.ailabs.iot.aisbase.channel.LayerState;
import com.alibaba.ailabs.iot.aisbase.dispatcher.CommandResponseDispatcher;
import com.alibaba.ailabs.iot.aisbase.plugin.PluginBase;
import com.alibaba.ailabs.iot.aisbase.plugin.auth.IAuthPlugin;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin;
import com.alibaba.ailabs.iot.aisbase.spec.AISCommand;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceSubtype;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;
import com.alibaba.ailabs.iot.aisbase.utils.DownloadManagerUtils;
import com.alibaba.ailabs.iot.aisbase.ya;
import com.alibaba.ailabs.iot.aisbase.za;
import com.alibaba.ailabs.tg.utils.ConvertUtils;
import com.alibaba.ailabs.tg.utils.LogUtils;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import datasource.implemention.data.DeviceVersionInfo;
import datasource.implemention.data.UpdateDeviceVersionRespData;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
public class OTAPluginProxy implements CommandResponseDispatcher.OnCommandReceivedListener, ICastEventListener {
    public IOTAPlugin.IFirmwareDownloadListener A;
    public Context M;
    public BroadcastReceiver Q;

    /* renamed from: c, reason: collision with root package name */
    public IOTAPlugin.IOTAActionListener f8487c;

    /* renamed from: d, reason: collision with root package name */
    public Handler f8488d;

    /* renamed from: e, reason: collision with root package name */
    public List<AISCommand> f8489e;

    /* renamed from: h, reason: collision with root package name */
    public byte[] f8492h;

    /* renamed from: i, reason: collision with root package name */
    public byte[] f8493i;

    /* renamed from: j, reason: collision with root package name */
    public byte[] f8494j;

    /* renamed from: k, reason: collision with root package name */
    public int f8495k;

    /* renamed from: l, reason: collision with root package name */
    public byte[] f8496l;

    /* renamed from: m, reason: collision with root package name */
    public byte f8497m;

    /* renamed from: n, reason: collision with root package name */
    public byte f8498n;

    /* renamed from: t, reason: collision with root package name */
    public ITransmissionLayer f8504t;

    /* renamed from: u, reason: collision with root package name */
    public PluginBase f8505u;

    /* renamed from: v, reason: collision with root package name */
    public BluetoothDevice f8506v;

    /* renamed from: z, reason: collision with root package name */
    public DownloadManagerUtils f8510z;

    /* renamed from: a, reason: collision with root package name */
    public final String f8485a = OTAPluginProxy.class.getSimpleName();

    /* renamed from: b, reason: collision with root package name */
    public IOTAPlugin.OTAState f8486b = IOTAPlugin.OTAState.IDLE;

    /* renamed from: f, reason: collision with root package name */
    public SparseArray<IActionListener> f8490f = new SparseArray<>();

    /* renamed from: g, reason: collision with root package name */
    public SparseArray<Runnable> f8491g = new SparseArray<>();

    /* renamed from: o, reason: collision with root package name */
    public int f8499o = 0;

    /* renamed from: p, reason: collision with root package name */
    public int f8500p = 0;

    /* renamed from: q, reason: collision with root package name */
    public int f8501q = 0;

    /* renamed from: r, reason: collision with root package name */
    public int f8502r = 0;

    /* renamed from: s, reason: collision with root package name */
    public List<Byte> f8503s = new ArrayList();

    /* renamed from: w, reason: collision with root package name */
    public String f8507w = null;

    /* renamed from: x, reason: collision with root package name */
    public String f8508x = null;

    /* renamed from: y, reason: collision with root package name */
    public long f8509y = -1;
    public a B = null;
    public DeviceVersionInfo C = null;
    public int D = 1;
    public boolean E = false;
    public boolean F = false;
    public int G = 0;
    public final int H = 6;
    public int I = 0;
    public SparseArray<AISCommand> J = new SparseArray<>();
    public String K = null;
    public boolean L = false;
    public Runnable N = new RunnableC0413ma(this);
    public Runnable O = new RunnableC0429ua(this);
    public Runnable P = null;

    public OTAPluginProxy(CommandResponseDispatcher commandResponseDispatcher, ITransmissionLayer iTransmissionLayer, PluginBase pluginBase) {
        commandResponseDispatcher.subscribeMultiCommandReceivedListener(new byte[]{Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RESEX, Constants.CMD_TYPE.CMD_REQUEST_OTA_RES, Constants.CMD_TYPE.CMD_OTA_RES, Constants.CMD_TYPE.CMD_OTA_VERIFY_RES, 15}, this);
        this.f8504t = iTransmissionLayer;
        this.f8505u = pluginBase;
        this.f8488d = new Handler(Looper.getMainLooper());
        this.f8504t.registerCastEventListener(this);
        this.f8506v = this.f8504t.getBluetoothDevice();
    }

    public void checkNewVersion(String str, String str2, String str3, IActionListener<DeviceVersionInfo> iActionListener) {
        LogUtils.d(this.f8485a, String.format("check new version, productId: %s, macAddress: %s, appVersion: %s", str, str2, str3));
        if (!a()) {
            getFirmwareVersionCommand(new C0403ha(this));
        }
        if (TextUtils.isEmpty(this.f8507w)) {
            a(str, str2, new C0405ia(this, str3, iActionListener));
        } else {
            b(this.f8507w, str3, iActionListener);
        }
    }

    public void enableReportOtaProgress(boolean z2) {
        this.L = z2;
    }

    public void getFirmwareVersionCommand(IActionListener iActionListener) {
        AISCommand aISCommandSendCommandWithCallback = this.f8505u.sendCommandWithCallback((byte) 32, new byte[]{0}, new C0431va(this), new C0433wa(this, iActionListener));
        if (aISCommandSendCommandWithCallback == null) {
            return;
        }
        b(AISCommand.getMessageSpec(Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES, aISCommandSendCommandWithCallback.getHeader().getMsgID()), iActionListener, aISCommandSendCommandWithCallback);
    }

    public void getFirmwareVersionCommandV1(IActionListener iActionListener) {
        AISCommand aISCommandSendCommandWithCallback = this.f8505u.sendCommandWithCallback((byte) 32, new byte[]{0}, new C0435xa(this), new ya(this, iActionListener));
        if (aISCommandSendCommandWithCallback == null) {
            return;
        }
        b(AISCommand.getMessageSpec(Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RESEX, aISCommandSendCommandWithCallback.getHeader().getMsgID()), iActionListener, aISCommandSendCommandWithCallback);
    }

    public void onBluetoothConnectionStateChanged(int i2) {
        LogUtils.d(this.f8485a, "onBluetoothConnectionStateChanged: " + i2);
        if (i2 != 0) {
            if (i2 == 2) {
                LogUtils.i(this.f8485a, "device connected, remove reconnect task");
                Runnable runnable = this.P;
                if (runnable != null) {
                    this.f8488d.removeCallbacks(runnable);
                }
                i();
                this.G = 0;
                this.f8506v = this.f8504t.getBluetoothDevice();
                BluetoothDeviceWrapper bluetoothDeviceWrapper = this.f8505u.getBluetoothDeviceWrapper();
                if (bluetoothDeviceWrapper == null || !bluetoothDeviceWrapper.isIsSafetyMode()) {
                    e();
                    return;
                }
                return;
            }
            return;
        }
        IOTAPlugin.OTAState oTAState = this.f8486b;
        if (oTAState != IOTAPlugin.OTAState.IDLE && oTAState != IOTAPlugin.OTAState.FINISH && oTAState != IOTAPlugin.OTAState.VERIFY_SUCCESS && oTAState != IOTAPlugin.OTAState.WAIT_RECONNECT && oTAState != IOTAPlugin.OTAState.ERROR) {
            b(6, "link loss in OTA process");
        }
        IOTAPlugin.OTAState oTAState2 = this.f8486b;
        IOTAPlugin.OTAState oTAState3 = IOTAPlugin.OTAState.VERIFY_SUCCESS;
        if (oTAState2 == oTAState3 || oTAState2 == IOTAPlugin.OTAState.WAIT_RECONNECT) {
            if (oTAState2 == oTAState3) {
                a(IOTAPlugin.OTAState.WAIT_RECONNECT);
            } else {
                this.G++;
            }
            if (this.G >= 6) {
                if (this.f8486b != oTAState3) {
                    b(6, "link loss in OTA process");
                    return;
                }
                IOTAPlugin.IOTAActionListener iOTAActionListener = this.f8487c;
                if (iOTAActionListener != null) {
                    iOTAActionListener.onSuccess("");
                    return;
                }
                return;
            }
            if (this.f8506v == null) {
                this.f8506v = this.f8504t.getBluetoothDevice();
            }
            if (this.f8506v == null) {
                b(6, "Maybe the user manually turned off Bluetooth");
                return;
            }
            Handler handler = this.f8488d;
            RunnableC0397ea runnableC0397ea = new RunnableC0397ea(this);
            this.P = runnableC0397ea;
            handler.postDelayed(runnableC0397ea, this.G == 0 ? b() : r1 * 500);
        }
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.ICastEventListener
    public void onCast(String str) {
        LogUtils.d(this.f8485a, "Handle cast message: " + str);
        if (str.equals(IAuthPlugin.EVENT_AUTH_SUCCESS) || str.equals(IAuthPlugin.EVENT_AUTH_FAILED)) {
            e();
        }
    }

    @Override // com.alibaba.ailabs.iot.aisbase.dispatcher.CommandResponseDispatcher.OnCommandReceivedListener
    @SuppressLint({"DefaultLocale"})
    public void onCommandReceived(byte b2, byte b3, byte[] bArr) {
        LogUtils.d(this.f8485a, String.format("Received whole command, command type: %d, payload: %s", Byte.valueOf(b2), ConvertUtils.bytes2HexString(bArr)));
        if (b2 == 15) {
            b(7, "Device reply illegal command");
            return;
        }
        if (b2 == 33) {
            if (bArr == null || bArr.length < 5) {
                return;
            }
            this.f8488d.removeCallbacks(this.N);
            int iByteArray2IntByLittleEndian = Utils.byteArray2IntByLittleEndian(Arrays.copyOfRange(bArr, 1, bArr.length));
            int messageSpec = AISCommand.getMessageSpec(b2, b3);
            IActionListener iActionListener = this.f8490f.get(messageSpec);
            if (iActionListener != null) {
                iActionListener.onSuccess(Integer.valueOf(iByteArray2IntByLittleEndian));
            }
            a(messageSpec);
            return;
        }
        if (b2 == 38) {
            if (bArr == null || bArr.length < 1) {
                return;
            }
            this.f8488d.removeCallbacks(this.N);
            if (bArr[0] != 1) {
                b(3, "Verify firmware failed");
                return;
            }
            a(IOTAPlugin.OTAState.VERIFY_SUCCESS);
            if (this.f8504t.getConnectionState() == LayerState.CONNECTED) {
                this.f8504t.disconnectDevice(null);
            }
            c();
            return;
        }
        if (b2 == 42) {
            if (bArr == null || bArr.length < 2) {
                return;
            }
            this.f8488d.removeCallbacks(this.N);
            int messageSpec2 = AISCommand.getMessageSpec(b2, b3);
            IActionListener iActionListener2 = this.f8490f.get(messageSpec2);
            if (iActionListener2 == null) {
                messageSpec2 = AISCommand.getMessageSpec(Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES, b3);
                iActionListener2 = this.f8490f.get(messageSpec2);
            }
            a(messageSpec2);
            try {
                String str = new String(Arrays.copyOfRange(bArr, 1, bArr.length), "UTF-8");
                if (iActionListener2 != null) {
                    iActionListener2.onSuccess(str);
                    return;
                }
                return;
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
                return;
            }
        }
        if (b2 == 35) {
            if (bArr == null || bArr.length < 5) {
                return;
            }
            this.f8488d.removeCallbacks(this.N);
            if (bArr[0] != 1) {
                b(2, "Device does not allow upgrade");
                return;
            }
            int iByteArray2IntByLittleEndian2 = Utils.byteArray2IntByLittleEndian(Arrays.copyOfRange(bArr, 1, 5));
            if (bArr.length >= 6) {
                this.E = true;
                this.I = bArr[5];
            }
            LogUtils.d(this.f8485a, "Allow ota, fast ota model: " + this.E);
            byte[] bArr2 = this.f8492h;
            if (bArr2 == null) {
                b(-201, "Null exception");
                return;
            }
            if (iByteArray2IntByLittleEndian2 == bArr2.length) {
                sendOTAFinishAndRequestVerifyCommand();
                return;
            }
            if (iByteArray2IntByLittleEndian2 > bArr2.length) {
                iByteArray2IntByLittleEndian2 = 0;
            }
            byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr2, iByteArray2IntByLittleEndian2, bArr2.length);
            if (!this.E && this.I == 0) {
                this.I = 15;
            }
            this.f8489e = this.f8505u.splitFirmwareBinToFixedQuantityAISCommands(this.I, 0, Constants.CMD_TYPE.CMD_OTA, bArrCopyOfRange, false);
            this.f8499o = 0;
            a(IOTAPlugin.OTAState.OTA_PROGRESS);
            this.f8488d.postDelayed(this.O, 1200000L);
            f();
            return;
        }
        if (b2 != 36) {
            LogUtils.w(this.f8485a, "Unknown command type: " + ((int) b2));
            return;
        }
        if (this.F) {
            LogUtils.w(this.f8485a, "In send loop");
            return;
        }
        if (bArr == null || bArr.length < 5) {
            return;
        }
        byte b4 = bArr[0];
        this.f8501q = (b4 & 240) >> 4;
        this.f8502r = b4 & 15;
        int iByteArray2IntByLittleEndian3 = Utils.byteArray2IntByLittleEndian(Arrays.copyOfRange(bArr, 1, bArr.length));
        IOTAPlugin.IOTAActionListener iOTAActionListener = this.f8487c;
        if (iOTAActionListener != null) {
            iOTAActionListener.onProgress(this.f8495k, iByteArray2IntByLittleEndian3);
        }
        LogUtils.v(this.f8485a, String.format("Device received %d bytes, SDK send %d bytes", Integer.valueOf(iByteArray2IntByLittleEndian3), Integer.valueOf(this.f8500p)));
        byte[] bArr3 = this.f8492h;
        if (bArr3 == null) {
            b(-201, "Null exception");
            return;
        }
        if (this.f8486b != IOTAPlugin.OTAState.OTA_PROGRESS) {
            return;
        }
        if (iByteArray2IntByLittleEndian3 != this.f8500p && iByteArray2IntByLittleEndian3 < bArr3.length) {
            LogUtils.w(this.f8485a, String.format("Packet loss in OTA progress, received package size: %d, next send package size: %d", Integer.valueOf(iByteArray2IntByLittleEndian3), Integer.valueOf(this.f8500p)));
            byte[] bArr4 = this.f8492h;
            this.f8489e = this.f8505u.splitFirmwareBinToFixedQuantityAISCommands(this.f8501q, this.f8502r + 1, Constants.CMD_TYPE.CMD_OTA, Arrays.copyOfRange(bArr4, iByteArray2IntByLittleEndian3, bArr4.length), false);
            this.f8499o = 0;
            this.f8500p = iByteArray2IntByLittleEndian3;
        }
        if (iByteArray2IntByLittleEndian3 == this.f8492h.length) {
            sendOTAFinishAndRequestVerifyCommand();
            return;
        }
        if (this.f8499o == this.f8489e.size()) {
            LogUtils.d(this.f8485a, "Conduct a new round of data transmission");
            byte[] bArr5 = this.f8492h;
            this.f8489e = this.f8505u.splitFirmwareBinToFixedQuantityAISCommands(this.f8501q, 0, Constants.CMD_TYPE.CMD_OTA, Arrays.copyOfRange(bArr5, iByteArray2IntByLittleEndian3, bArr5.length), false);
            this.f8499o = 0;
        }
        f();
    }

    public void sendOTAFinishAndRequestVerifyCommand() {
        this.f8488d.removeCallbacks(this.O);
        a(IOTAPlugin.OTAState.REQUEST_VERIFY);
        this.f8505u.sendCommandWithCallback(Constants.CMD_TYPE.CMD_OTA_REQUEST_VERIFY, new byte[]{1}, new Ba(this), new C0389aa(this));
        this.f8488d.postDelayed(this.N, 10000L);
    }

    public void sendOTARequestCommand(byte b2, byte[] bArr, byte[] bArr2, byte[] bArr3, byte b3) {
        int length = bArr.length + 2 + bArr2.length + bArr3.length;
        byte[] bArr4 = new byte[length];
        bArr4[0] = b2;
        System.arraycopy(bArr, 0, bArr4, 1, bArr.length);
        System.arraycopy(bArr2, 0, bArr4, bArr.length + 1, bArr2.length);
        System.arraycopy(bArr3, 0, bArr4, bArr.length + 1 + bArr2.length, bArr3.length);
        bArr4[length - 1] = b3;
        LogUtils.d(this.f8485a, "request ota: " + ConvertUtils.bytes2HexString(bArr4));
        a(IOTAPlugin.OTAState.REQUEST);
        this.f8505u.sendCommandWithCallback(Constants.CMD_TYPE.CMD_REQUEST_OTA, bArr4, new za(this), new Aa(this));
        this.f8488d.postDelayed(this.N, 10000L);
    }

    public void setOnOTAActionListener(IOTAPlugin.IOTAActionListener iOTAActionListener) {
        this.f8487c = iOTAActionListener;
    }

    public void setSendCompleteFrameWithNoAckFlag(boolean z2) {
        this.E = z2;
    }

    public void startDownloadFirmware(Context context, DeviceVersionInfo deviceVersionInfo, String str, IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener) throws InterruptedException {
        this.M = context;
        this.A = iFirmwareDownloadListener;
        if (this.f8509y != -1) {
            a(-400, "There is currently a download task with id" + this.f8509y);
            return;
        }
        if (this.f8510z == null) {
            this.f8510z = DownloadManagerUtils.getInstance(context);
        }
        IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener2 = this.A;
        if (iFirmwareDownloadListener2 != null) {
            iFirmwareDownloadListener2.onDownloadStart();
        }
        if (this.L) {
            ReportProgressUtil.reportOtaProgress(this.K, deviceVersionInfo.getModel().getVersion(), this.f8507w, this.f8508x, ReportProgressUtil.TAG_START, ReportProgressUtil.CODE_OK, "");
        }
        long jDownloadFile = this.f8510z.downloadFile(deviceVersionInfo.getModel().getOtaUrl(), deviceVersionInfo.getModel().getMd5(), str, new C0415na(this, deviceVersionInfo));
        this.f8509y = jDownloadFile;
        if (jDownloadFile < 0) {
            a(-401, "no write permission or insufficient disk");
            return;
        }
        if (jDownloadFile > 0) {
            g();
        }
        if (this.f8509y == 0) {
            this.f8509y = -1L;
        }
    }

    public void startDownloadIlopFirmware(Context context, DeviceVersionInfo.DeviceInfoModel deviceInfoModel, String str, IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener) throws InterruptedException {
        this.M = context;
        this.A = iFirmwareDownloadListener;
        if (this.f8509y != -1) {
            a(-400, "There is currently a download task with id" + this.f8509y);
            return;
        }
        if (this.f8510z == null) {
            this.f8510z = DownloadManagerUtils.getInstance(context);
        }
        IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener2 = this.A;
        if (iFirmwareDownloadListener2 != null) {
            iFirmwareDownloadListener2.onDownloadStart();
        }
        if (this.L) {
            ReportProgressUtil.reportOtaProgress(this.K, deviceInfoModel.getVersion(), this.f8507w, this.f8508x, ReportProgressUtil.TAG_START, ReportProgressUtil.CODE_OK, "");
        }
        long jDownloadFile = this.f8510z.downloadFile(deviceInfoModel.getOtaUrl(), deviceInfoModel.getMd5(), str, new C0417oa(this, deviceInfoModel));
        this.f8509y = jDownloadFile;
        if (jDownloadFile < 0) {
            a(-401, "no write permission or insufficient disk");
            return;
        }
        if (jDownloadFile > 0) {
            g();
        }
        if (this.f8509y == 0) {
            this.f8509y = -1L;
        }
    }

    public void startOTA(byte[] bArr, byte[] bArr2, byte[] bArr3, byte b2, byte[] bArr4, byte b3, IOTAPlugin.IOTAActionListener iOTAActionListener) {
        this.F = false;
        this.f8500p = 0;
        this.G = 0;
        this.f8487c = iOTAActionListener;
        IOTAPlugin.OTAState oTAState = this.f8486b;
        if (oTAState != IOTAPlugin.OTAState.IDLE && oTAState != IOTAPlugin.OTAState.FINISH && oTAState != IOTAPlugin.OTAState.ERROR) {
            b(4, "Waiting for the current upgrade to complete");
        }
        this.f8492h = bArr;
        this.f8493i = bArr2;
        this.f8494j = bArr3;
        this.f8497m = b2;
        this.f8496l = bArr4;
        this.f8498n = b3;
        this.f8495k = Utils.byteArray2IntByLittleEndian(bArr3);
        sendOTARequestCommand(this.f8497m, this.f8493i, this.f8494j, this.f8496l, this.f8498n);
    }

    public void stopDownloadFirmware() {
        DownloadManagerUtils downloadManagerUtils;
        long j2 = this.f8509y;
        if (j2 != -1 || (downloadManagerUtils = this.f8510z) == null) {
            return;
        }
        downloadManagerUtils.cancelDownload(j2);
    }

    public void stopOTA() {
        IOTAPlugin.OTAState oTAState = this.f8486b;
        IOTAPlugin.OTAState oTAState2 = IOTAPlugin.OTAState.IDLE;
        if (oTAState == oTAState2 || oTAState == IOTAPlugin.OTAState.FINISH) {
            return;
        }
        b(8, "User terminated the OTA process");
        this.f8486b = oTAState2;
        this.f8488d.removeCallbacks(this.N);
        this.f8488d.removeCallbacks(this.P);
    }

    public void updateDeviceVersion(String str, String str2, IActionListener<UpdateDeviceVersionRespData> iActionListener) {
        RequestManage.getInstance().updateDeviceVersion(str, str2, new C0411la(this, iActionListener));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public class a extends Thread {

        /* renamed from: a, reason: collision with root package name */
        public boolean f8511a;

        public a() {
            this.f8511a = true;
        }

        public void a() {
            this.f8511a = false;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() throws InterruptedException {
            if (OTAPluginProxy.this.f8510z != null) {
                while (this.f8511a) {
                    int iValidDownload = OTAPluginProxy.this.f8510z.validDownload(OTAPluginProxy.this.f8509y);
                    if (iValidDownload != 0) {
                        OTAPluginProxy.this.a(iValidDownload, "Download failed");
                        OTAPluginProxy.this.f8509y = -1L;
                        return;
                    }
                    DownloadManagerUtils.DownloadTaskDetails downloadDetails = OTAPluginProxy.this.f8510z.getDownloadDetails(OTAPluginProxy.this.f8509y);
                    if (downloadDetails == null) {
                        this.f8511a = false;
                        return;
                    }
                    if (OTAPluginProxy.this.A != null && downloadDetails.totalSize > 0) {
                        OTAPluginProxy.this.f8488d.post(new Ca(this, downloadDetails));
                    }
                    try {
                        Thread.sleep(200L);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }

        public /* synthetic */ a(OTAPluginProxy oTAPluginProxy, RunnableC0413ma runnableC0413ma) {
            this();
        }
    }

    public final boolean d() {
        BluetoothDeviceWrapper bluetoothDeviceWrapper = this.f8505u.getBluetoothDeviceWrapper();
        return bluetoothDeviceWrapper != null && bluetoothDeviceWrapper.getSubtype() == BluetoothDeviceSubtype.GMA;
    }

    public final void e() {
        LogUtils.d(this.f8485a, "recheckVersion...");
        if (this.f8486b == IOTAPlugin.OTAState.WAIT_RECONNECT) {
            a(IOTAPlugin.OTAState.WAIT_RECHECK_VERSION);
            getFirmwareVersionCommand(new C0401ga(this));
        }
    }

    public final void f() {
        IOTAPlugin.OTAState oTAState = this.f8486b;
        if (oTAState == IOTAPlugin.OTAState.ERROR || oTAState == IOTAPlugin.OTAState.IDLE) {
            LogUtils.w(this.f8485a, "Current OTA state is ERROR, ignore");
            this.F = false;
            return;
        }
        if (this.f8499o == this.f8489e.size()) {
            this.F = false;
            return;
        }
        LogUtils.v(this.f8485a, "Start send pdu(" + this.f8499o + ")");
        this.F = true;
        byte[] bytes = this.f8489e.get(this.f8499o).getBytes();
        this.D = 1;
        this.f8505u.sendRawDataWithCallback(bytes, new C0391ba(this), new C0393ca(this));
    }

    public final void g() throws InterruptedException {
        h();
        a aVar = new a(this, null);
        this.B = aVar;
        aVar.start();
    }

    public final void h() throws InterruptedException {
        a aVar = this.B;
        if (aVar != null) {
            aVar.a();
            try {
                this.B.join();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
    }

    public final void i() {
        Context context;
        LogUtils.d(this.f8485a, String.format("unregisterBluetoothReceiver called, receiver: %s, context: %s", this.Q, this.M));
        BroadcastReceiver broadcastReceiver = this.Q;
        if (broadcastReceiver == null || (context = this.M) == null) {
            return;
        }
        context.unregisterReceiver(broadcastReceiver);
    }

    public final void c() {
        if (d() && this.Q == null) {
            LogUtils.i(this.f8485a, "Register system bluetoothA2dp receiver");
            this.Q = new C0427ta(this);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
            Context context = this.M;
            if (context != null) {
                context.registerReceiver(this.Q, intentFilter);
            }
        }
    }

    public final void b(int i2, String str) {
        LogUtils.e(this.f8485a, "ota error (" + i2 + ":" + str + ")");
        this.F = false;
        this.f8488d.removeCallbacks(this.N);
        this.f8488d.removeCallbacks(this.O);
        i();
        this.f8486b = IOTAPlugin.OTAState.ERROR;
        IOTAPlugin.IOTAActionListener iOTAActionListener = this.f8487c;
        if (iOTAActionListener != null) {
            iOTAActionListener.onFailed(i2, str);
        }
    }

    public final void a(IOTAPlugin.OTAState oTAState) {
        this.f8486b = oTAState;
        IOTAPlugin.IOTAActionListener iOTAActionListener = this.f8487c;
        if (iOTAActionListener != null) {
            iOTAActionListener.onStateChanged(oTAState);
        }
    }

    public final void b(String str, String str2, IActionListener<DeviceVersionInfo> iActionListener) {
        LogUtils.d(this.f8485a, String.format("Query ota info, uuid: %s, appVersion: %s", str, str2));
        RequestManage.getInstance().queryOtaInfo(str, str2, new C0409ka(this, iActionListener));
    }

    public final void a(String str, String str2, IActionListener<String> iActionListener) {
        RequestManage.getInstance().getDeviceUUIDViaProductId(str, str2, new C0407ja(this, iActionListener));
    }

    public final void a(int i2, IActionListener iActionListener, AISCommand aISCommand) {
        LogUtils.d(this.f8485a, "ReTransmission command, Key: " + i2 + ", Command type: " + Utils.byte2String(aISCommand.getHeader().getCommandType(), true));
        this.f8505u.sendRawDataWithCallback(aISCommand.getBytes(), new C0421qa(this), new C0423ra(this, iActionListener));
        b(i2, iActionListener, (AISCommand) null);
    }

    public final void b(int i2, IActionListener iActionListener, AISCommand aISCommand) {
        LogUtils.d(this.f8485a, "Save listener and set timeout task for key : " + i2);
        this.f8490f.put(i2, iActionListener);
        if (aISCommand != null) {
            this.J.put(i2, aISCommand);
        }
        RunnableC0419pa runnableC0419pa = new RunnableC0419pa(this, i2, iActionListener);
        this.f8491g.put(i2, runnableC0419pa);
        this.f8488d.postDelayed(runnableC0419pa, 3000L);
    }

    public void startOTA(String str, IOTAPlugin.IOTAActionListener iOTAActionListener) throws IOException {
        IOTAPlugin.IOTAActionListener otaActionListener;
        if (this.L) {
            DeviceVersionInfo deviceVersionInfo = this.C;
            otaActionListener = new OtaActionListener(iOTAActionListener, this.f8505u.getBluetoothDeviceWrapper(), this.f8507w, this.f8508x, this.K, deviceVersionInfo == null ? "" : deviceVersionInfo.getModel().getVersion());
        } else {
            otaActionListener = iOTAActionListener;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(str));
            int iAvailable = fileInputStream.available();
            byte[] bArr = new byte[iAvailable];
            fileInputStream.read(bArr);
            fileInputStream.close();
            byte[] bArrInt2ByteArrayByLittleEndian = {-1, -1, -1, -1};
            DeviceVersionInfo deviceVersionInfo2 = this.C;
            if (deviceVersionInfo2 != null) {
                bArrInt2ByteArrayByLittleEndian = Utils.int2ByteArrayByLittleEndian(Utils.adapterToAisVersion(deviceVersionInfo2.getModel().getVersion()));
                String str2 = this.f8485a;
                StringBuilder sb = new StringBuilder();
                sb.append("firmware version: ");
                sb.append(ConvertUtils.bytes2HexString(bArrInt2ByteArrayByLittleEndian));
                LogUtils.d(str2, sb.toString());
            }
            startOTA(bArr, bArrInt2ByteArrayByLittleEndian, Utils.int2ByteArrayByLittleEndian(iAvailable), (byte) 0, Arrays.copyOfRange(Utils.int2ByteArrayByLittleEndian(Utils.genCrc16CCITT(bArr, 0, iAvailable)), 0, 2), (byte) 0, otaActionListener);
        } catch (IOException e2) {
            LogUtils.e(this.f8485a, e2.toString());
            if (otaActionListener != null) {
                otaActionListener.onFailed(-200, "Failed to open firmware file");
            }
        }
    }

    public final void a(int i2) {
        LogUtils.d(this.f8485a, "Remove listener and cancel timeout task for key : " + i2);
        this.f8490f.remove(i2);
        this.f8488d.removeCallbacks(this.f8491g.get(i2));
    }

    public final void a(int i2, String str) {
        IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener = this.A;
        if (iFirmwareDownloadListener != null) {
            iFirmwareDownloadListener.onFailed(i2, str);
        }
        if (this.L) {
            DeviceVersionInfo deviceVersionInfo = this.C;
            String version = deviceVersionInfo == null ? "" : deviceVersionInfo.getModel().getVersion();
            ReportProgressUtil.reportOtaProgress(this.K, version, this.f8507w, this.f8508x, "FINISH", ReportProgressUtil.CODE_ERR, str + OpenAccountUIConstants.UNDER_LINE + i2);
        }
    }

    public final int b() {
        BluetoothDeviceWrapper bluetoothDeviceWrapper = this.f8505u.getBluetoothDeviceWrapper();
        return (bluetoothDeviceWrapper == null || bluetoothDeviceWrapper.getSubtype() != BluetoothDeviceSubtype.GMA) ? 8000 : 20000;
    }

    public final boolean a() {
        IOTAPlugin.OTAState oTAState = this.f8486b;
        return (oTAState == IOTAPlugin.OTAState.IDLE || oTAState == IOTAPlugin.OTAState.FINISH || oTAState == IOTAPlugin.OTAState.ERROR) ? false : true;
    }
}
