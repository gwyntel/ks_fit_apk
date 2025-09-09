package com.yc.utesdk.listener;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Handler;
import android.os.Looper;
import com.yc.utesdk.bean.ActivityTimeInfo;
import com.yc.utesdk.bean.BloodPressureInfo;
import com.yc.utesdk.bean.BloodSugarSamplingInfo;
import com.yc.utesdk.bean.BodyInfo;
import com.yc.utesdk.bean.BreatheInfo;
import com.yc.utesdk.bean.CSBPDevicePmInfo;
import com.yc.utesdk.bean.CSBPHeartRateAndOxygenInfo;
import com.yc.utesdk.bean.CyweeSleepTimeInfo;
import com.yc.utesdk.bean.Device4GModuleInfo;
import com.yc.utesdk.bean.DeviceBt3StateInfo;
import com.yc.utesdk.bean.DeviceWidgetInfo;
import com.yc.utesdk.bean.EcgInfo;
import com.yc.utesdk.bean.ElHRVMiddleDataInfo;
import com.yc.utesdk.bean.ElbpBleMiddleInfo;
import com.yc.utesdk.bean.ElbpBlePpgInfo;
import com.yc.utesdk.bean.ElbpContinuousPpgDataInfo;
import com.yc.utesdk.bean.ElbpMiddleDataInfo;
import com.yc.utesdk.bean.ElbpPpgDataInfo;
import com.yc.utesdk.bean.ElbsPpgDataInfo;
import com.yc.utesdk.bean.HeartRateBestValueInfo;
import com.yc.utesdk.bean.HeartRateHourBestValueInfo;
import com.yc.utesdk.bean.HeartRateInfo;
import com.yc.utesdk.bean.HeartRateRestInfo;
import com.yc.utesdk.bean.LabelAlarmClockInfo;
import com.yc.utesdk.bean.LocalWatchFaceInfo;
import com.yc.utesdk.bean.MenstrualParam;
import com.yc.utesdk.bean.MessageReplyDataInfo;
import com.yc.utesdk.bean.MoodPressureFatigueInfo;
import com.yc.utesdk.bean.MoodSensorInterfaceInfo;
import com.yc.utesdk.bean.OxygenInfo;
import com.yc.utesdk.bean.PrayerInfo;
import com.yc.utesdk.bean.ReplySmsInfo;
import com.yc.utesdk.bean.SleepInfo;
import com.yc.utesdk.bean.SosCallInfo;
import com.yc.utesdk.bean.SportsDataInfo;
import com.yc.utesdk.bean.SportsModeInfo;
import com.yc.utesdk.bean.SportsRealDataInfo;
import com.yc.utesdk.bean.StandingTimeInfo;
import com.yc.utesdk.bean.StepOneDayAllInfo;
import com.yc.utesdk.bean.TemperatureInfo;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.watchface.open.WatchFaceUtil;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class UteListenerManager implements BleConnectStateListener, DeviceFirmwareVersionListener, DeviceUiVersionListener, DeviceDspVersionListener, SimpleCallbackListener, DeviceBatteryListener, StepChangeListener, SleepChangeListener, HeartRateChangeListener, BloodPressureChangeListener, OxygenChangeListener, TemperatureChangeListener, BodyFatChangeListener, EcgChangeListener, BreathingRateChangeListener, CallSmsAppRemindListener, DeviceCameraListener, DeviceAlarmListener, DeviceLabelAlarmListener, DeviceLanguageListener, DeviceShortcutSwitchListener, CommonInterfaceListener, ContactsSyncListener, DeviceMusicListener, WatchFaceOnlineListener, WatchFaceCustomListener, TodayTargetListener, MoodPressureListener, DeviceBt3Listener, MultiSportsListener, SosContactsListener, QuickReplySmsListener, MultiSportTargetListener, DeviceWidgetListener, CsbpChangeListener, ElbpListener, LocalWatchFaceListener, BluetoothDisconnectReminderListener, ElbsListener, ElHRVListener, ElComplexListener, SuloseCommandListener, DeviceQRCodeListener, DevicePrayerListener, BloodSugarListener, Device4GModuleListener, ContinuousPpgListener, ChatGPTListener, GPSListener, DeviceParamListener, MenstrualListener, MessageReplyListener, AIWatchListener {
    private static UteListenerManager instance;
    TodayTargetListener A;
    MoodPressureListener B;
    DeviceBt3Listener C;
    MultiSportsListener D;
    SosContactsListener E;
    QuickReplySmsListener F;
    DeviceLabelAlarmListener G;
    MultiSportTargetListener H;
    DeviceWidgetListener I;
    CsbpChangeListener J;
    ElbpListener K;
    LocalWatchFaceListener L;
    BluetoothDisconnectReminderListener M;
    ElbsListener N;
    ElHRVListener O;
    ElComplexListener P;
    SuloseCommandListener Q;
    DeviceQRCodeListener R;
    GattCallbackListener S;
    DevicePrayerListener T;
    BloodSugarListener U;
    Device4GModuleListener V;
    ContinuousPpgListener W;
    ChatGPTListener X;
    GPSListener Y;
    DeviceParamListener Z;

    /* renamed from: a0, reason: collision with root package name */
    MenstrualListener f24902a0;

    /* renamed from: b, reason: collision with root package name */
    BleConnectStateListener f24903b;

    /* renamed from: b0, reason: collision with root package name */
    MessageReplyListener f24904b0;

    /* renamed from: c, reason: collision with root package name */
    DeviceFirmwareVersionListener f24905c;

    /* renamed from: c0, reason: collision with root package name */
    AIWatchListener f24906c0;

    /* renamed from: d, reason: collision with root package name */
    DeviceUiVersionListener f24907d;

    /* renamed from: e, reason: collision with root package name */
    DeviceDspVersionListener f24908e;

    /* renamed from: f, reason: collision with root package name */
    SimpleCallbackListener f24909f;

    /* renamed from: g, reason: collision with root package name */
    DeviceBatteryListener f24910g;

    /* renamed from: h, reason: collision with root package name */
    StepChangeListener f24911h;

    /* renamed from: i, reason: collision with root package name */
    SleepChangeListener f24912i;

    /* renamed from: j, reason: collision with root package name */
    HeartRateChangeListener f24913j;

    /* renamed from: k, reason: collision with root package name */
    BloodPressureChangeListener f24914k;

    /* renamed from: l, reason: collision with root package name */
    OxygenChangeListener f24915l;

    /* renamed from: m, reason: collision with root package name */
    TemperatureChangeListener f24916m;

    /* renamed from: n, reason: collision with root package name */
    BodyFatChangeListener f24917n;

    /* renamed from: o, reason: collision with root package name */
    EcgChangeListener f24918o;

    /* renamed from: p, reason: collision with root package name */
    BreathingRateChangeListener f24919p;

    /* renamed from: q, reason: collision with root package name */
    CallSmsAppRemindListener f24920q;

    /* renamed from: r, reason: collision with root package name */
    DeviceCameraListener f24921r;

    /* renamed from: s, reason: collision with root package name */
    DeviceAlarmListener f24922s;

    /* renamed from: t, reason: collision with root package name */
    DeviceLanguageListener f24923t;

    /* renamed from: u, reason: collision with root package name */
    DeviceShortcutSwitchListener f24924u;

    /* renamed from: v, reason: collision with root package name */
    CommonInterfaceListener f24925v;

    /* renamed from: w, reason: collision with root package name */
    ContactsSyncListener f24926w;

    /* renamed from: x, reason: collision with root package name */
    DeviceMusicListener f24927x;

    /* renamed from: y, reason: collision with root package name */
    WatchFaceOnlineListener f24928y;

    /* renamed from: z, reason: collision with root package name */
    WatchFaceCustomListener f24929z;
    private int bleConnectState = 0;

    /* renamed from: a, reason: collision with root package name */
    Handler f24901a = new Handler(Looper.getMainLooper());
    private boolean isDelayCallBack = false;
    private int delayTime = 0;

    public static synchronized UteListenerManager getInstance() {
        try {
            if (instance == null) {
                instance = new UteListenerManager();
            }
        } catch (Throwable th) {
            throw th;
        }
        return instance;
    }

    public int getBleConnectState() {
        return this.bleConnectState;
    }

    public void notifyBluetoothOff() {
        this.bleConnectState = 0;
    }

    @Override // com.yc.utesdk.listener.LocalWatchFaceListener
    public void notifyCurrentWatchFaceIndex(final int i2) {
        LocalWatchFaceListener localWatchFaceListener = this.L;
        if (localWatchFaceListener == null) {
            LogUtils.initializeLog("UteBleConnection.notifyCurrentWatchFaceIndex");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.b1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25766a.utendo(i2);
                }
            }, this.delayTime);
        } else {
            localWatchFaceListener.notifyCurrentWatchFaceIndex(i2);
        }
    }

    @Override // com.yc.utesdk.listener.AIWatchListener
    public <T> void onAIWatchNotify(int i2, T t2) {
        AIWatchListener aIWatchListener = this.f24906c0;
        if (aIWatchListener == null) {
            LogUtils.initializeLog("UteBleConnection.setAIWatchListener");
        } else {
            aIWatchListener.onAIWatchNotify(i2, t2);
        }
    }

    @Override // com.yc.utesdk.listener.AIWatchListener
    public void onAIWatchStatus(boolean z2, int i2) {
        AIWatchListener aIWatchListener = this.f24906c0;
        if (aIWatchListener == null) {
            LogUtils.initializeLog("UteBleConnection.setAIWatchListener");
        } else {
            aIWatchListener.onAIWatchStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.TodayTargetListener
    public void onActivityTimeSyncFail() {
        TodayTargetListener todayTargetListener = this.A;
        if (todayTargetListener == null) {
            LogUtils.initializeLog("UteBleConnection.setTodayTargetListener");
        } else {
            todayTargetListener.onActivityTimeSyncFail();
        }
    }

    @Override // com.yc.utesdk.listener.TodayTargetListener
    public void onActivityTimeSyncSuccess(final List<ActivityTimeInfo> list) {
        TodayTargetListener todayTargetListener = this.A;
        if (todayTargetListener == null) {
            LogUtils.initializeLog("UteBleConnection.setTodayTargetListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.g2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25821a.utendo(list);
                }
            }, this.delayTime);
        } else {
            todayTargetListener.onActivityTimeSyncSuccess(list);
        }
    }

    @Override // com.yc.utesdk.listener.TodayTargetListener
    public void onActivityTimeSyncing() {
        TodayTargetListener todayTargetListener = this.A;
        if (todayTargetListener == null) {
            LogUtils.initializeLog("UteBleConnection.setTodayTargetListener");
        } else {
            todayTargetListener.onActivityTimeSyncing();
        }
    }

    @Override // com.yc.utesdk.listener.DeviceBatteryListener
    public void onBatteryChange(int i2, int i3) {
        DeviceBatteryListener deviceBatteryListener = this.f24910g;
        if (deviceBatteryListener == null) {
            LogUtils.initializeLog("UteBleConnection.setDeviceBatteryListener");
        } else {
            deviceBatteryListener.onBatteryChange(i2, i3);
        }
    }

    @Override // com.yc.utesdk.listener.BloodPressureChangeListener
    public void onBloodPressureRealTime(BloodPressureInfo bloodPressureInfo) {
        BloodPressureChangeListener bloodPressureChangeListener = this.f24914k;
        if (bloodPressureChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setBloodPressureChangeListener");
        } else {
            bloodPressureChangeListener.onBloodPressureRealTime(bloodPressureInfo);
        }
    }

    @Override // com.yc.utesdk.listener.BloodPressureChangeListener
    public void onBloodPressureStatus(final boolean z2, final int i2) {
        BloodPressureChangeListener bloodPressureChangeListener = this.f24914k;
        if (bloodPressureChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setBloodPressureChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.q1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25924a.utendo(z2, i2);
                }
            }, this.delayTime);
        } else {
            bloodPressureChangeListener.onBloodPressureStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.BloodPressureChangeListener
    public void onBloodPressureSyncFail() {
        BloodPressureChangeListener bloodPressureChangeListener = this.f24914k;
        if (bloodPressureChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setBloodPressureChangeListener");
        } else {
            bloodPressureChangeListener.onBloodPressureSyncFail();
        }
    }

    @Override // com.yc.utesdk.listener.BloodPressureChangeListener
    public void onBloodPressureSyncSuccess(final List<BloodPressureInfo> list) {
        BloodPressureChangeListener bloodPressureChangeListener = this.f24914k;
        if (bloodPressureChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setBloodPressureChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.i2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25838a.utenif(list);
                }
            }, this.delayTime);
        } else {
            bloodPressureChangeListener.onBloodPressureSyncSuccess(list);
        }
    }

    @Override // com.yc.utesdk.listener.BloodPressureChangeListener
    public void onBloodPressureSyncing() {
        BloodPressureChangeListener bloodPressureChangeListener = this.f24914k;
        if (bloodPressureChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setBloodPressureChangeListener");
        } else {
            bloodPressureChangeListener.onBloodPressureSyncing();
        }
    }

    @Override // com.yc.utesdk.listener.BloodSugarListener
    public void onBloodSugarSampling(final boolean z2, final List<BloodSugarSamplingInfo> list) {
        BloodSugarListener bloodSugarListener = this.U;
        if (bloodSugarListener == null) {
            LogUtils.initializeLog("UteBleConnection.setBloodSugarListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.r2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25935a.utendo(z2, list);
                }
            }, this.delayTime);
        } else {
            bloodSugarListener.onBloodSugarSampling(z2, list);
        }
    }

    @Override // com.yc.utesdk.listener.BloodSugarListener
    public void onBloodSugarStatus(final boolean z2, final int i2) {
        BloodSugarListener bloodSugarListener = this.U;
        if (bloodSugarListener == null) {
            LogUtils.initializeLog("UteBleConnection.setBloodSugarListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.p
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25908a.utenif(z2, i2);
                }
            }, this.delayTime);
        } else {
            bloodSugarListener.onBloodSugarStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.BluetoothDisconnectReminderListener
    public void onBlutoothDisconnectReminder(boolean z2, int i2) {
        BluetoothDisconnectReminderListener bluetoothDisconnectReminderListener = this.M;
        if (bluetoothDisconnectReminderListener == null) {
            LogUtils.initializeLog("UteBleConnection.setLocalWatchFaceListener");
        } else {
            bluetoothDisconnectReminderListener.onBlutoothDisconnectReminder(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.BodyFatChangeListener
    public void onBodyFatRealTime(final BodyInfo bodyInfo) {
        BodyFatChangeListener bodyFatChangeListener = this.f24917n;
        if (bodyFatChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setBodyFatChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.i1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25836a.utendo(bodyInfo);
                }
            }, this.delayTime);
        } else {
            bodyFatChangeListener.onBodyFatRealTime(bodyInfo);
        }
    }

    @Override // com.yc.utesdk.listener.BodyFatChangeListener
    public void onBodyFatStatus(final boolean z2, final int i2) {
        BodyFatChangeListener bodyFatChangeListener = this.f24917n;
        if (bodyFatChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setBodyFatChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.o1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25902a.utenfor(z2, i2);
                }
            }, this.delayTime);
        } else {
            bodyFatChangeListener.onBodyFatStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.BodyFatChangeListener
    public void onBodyFatSyncFail() {
        BodyFatChangeListener bodyFatChangeListener = this.f24917n;
        if (bodyFatChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setBodyFatChangeListener");
        } else {
            bodyFatChangeListener.onBodyFatSyncFail();
        }
    }

    @Override // com.yc.utesdk.listener.BodyFatChangeListener
    public void onBodyFatSyncSuccess(final List<BodyInfo> list) {
        BodyFatChangeListener bodyFatChangeListener = this.f24917n;
        if (bodyFatChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setBodyFatChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.p0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25911a.utenfor(list);
                }
            }, this.delayTime);
        } else {
            bodyFatChangeListener.onBodyFatSyncSuccess(list);
        }
    }

    @Override // com.yc.utesdk.listener.BodyFatChangeListener
    public void onBodyFatSyncing() {
        BodyFatChangeListener bodyFatChangeListener = this.f24917n;
        if (bodyFatChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setBodyFatChangeListener");
        } else {
            bodyFatChangeListener.onBodyFatSyncing();
        }
    }

    @Override // com.yc.utesdk.listener.BreathingRateChangeListener
    public void onBreathingRateRealTime(final BreatheInfo breatheInfo) {
        BreathingRateChangeListener breathingRateChangeListener = this.f24919p;
        if (breathingRateChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setBreathingRateChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.j2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25849a.utendo(breatheInfo);
                }
            }, this.delayTime);
        } else {
            breathingRateChangeListener.onBreathingRateRealTime(breatheInfo);
        }
    }

    @Override // com.yc.utesdk.listener.BreathingRateChangeListener
    public void onBreathingRateStatus(final boolean z2, final int i2) {
        BreathingRateChangeListener breathingRateChangeListener = this.f24919p;
        if (breathingRateChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setBreathingRateChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.e
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25793a.utenint(z2, i2);
                }
            }, this.delayTime);
        } else {
            breathingRateChangeListener.onBreathingRateStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.BreathingRateChangeListener
    public void onBreathingRateSyncFail() {
        BreathingRateChangeListener breathingRateChangeListener = this.f24919p;
        if (breathingRateChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setBreathingRateChangeListener");
        } else {
            breathingRateChangeListener.onBreathingRateSyncFail();
        }
    }

    @Override // com.yc.utesdk.listener.BreathingRateChangeListener
    public void onBreathingRateSyncSuccess(final List<BreatheInfo> list) {
        BreathingRateChangeListener breathingRateChangeListener = this.f24919p;
        if (breathingRateChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setBreathingRateChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.p2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25917a.utenint(list);
                }
            }, this.delayTime);
        } else {
            breathingRateChangeListener.onBreathingRateSyncSuccess(list);
        }
    }

    @Override // com.yc.utesdk.listener.BreathingRateChangeListener
    public void onBreathingRateSyncing() {
        BreathingRateChangeListener breathingRateChangeListener = this.f24919p;
        if (breathingRateChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setBreathingRateChangeListener");
        } else {
            breathingRateChangeListener.onBreathingRateSyncing();
        }
    }

    @Override // com.yc.utesdk.listener.CallSmsAppRemindListener
    public void onCallRemindStatus(final boolean z2, final int i2) {
        CallSmsAppRemindListener callSmsAppRemindListener = this.f24920q;
        if (callSmsAppRemindListener == null) {
            LogUtils.initializeLog("UteBleConnection.setCallSmsAppRemindListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.a3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25761a.utennew(z2, i2);
                }
            }, this.delayTime);
        } else {
            callSmsAppRemindListener.onCallRemindStatus(z2, i2);
        }
    }

    public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        GattCallbackListener gattCallbackListener = this.S;
        if (gattCallbackListener == null) {
            return;
        }
        gattCallbackListener.onCharacteristicChanged(bluetoothGatt, bluetoothGattCharacteristic);
    }

    public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i2) {
        GattCallbackListener gattCallbackListener = this.S;
        if (gattCallbackListener == null) {
            return;
        }
        gattCallbackListener.onCharacteristicWrite(bluetoothGatt, bluetoothGattCharacteristic, i2);
    }

    @Override // com.yc.utesdk.listener.ChatGPTListener
    public <T> void onChatGPTNotify(int i2, T t2) {
        ChatGPTListener chatGPTListener = this.X;
        if (chatGPTListener == null) {
            LogUtils.initializeLog("UteBleConnection.setChatGPTListener");
        } else {
            chatGPTListener.onChatGPTNotify(i2, t2);
        }
    }

    @Override // com.yc.utesdk.listener.ChatGPTListener
    public void onChatGPTStatus(boolean z2, int i2) {
        ChatGPTListener chatGPTListener = this.X;
        if (chatGPTListener == null) {
            LogUtils.initializeLog("UteBleConnection.setChatGPTListener");
        } else {
            chatGPTListener.onChatGPTStatus(z2, i2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x003b  */
    @Override // com.yc.utesdk.listener.CommonInterfaceListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onCommonInterfaceBleToSdk(final int r3, final byte[] r4) {
        /*
            r2 = this;
            com.yc.utesdk.listener.CommonInterfaceListener r0 = r2.f24925v
            if (r0 != 0) goto La
            java.lang.String r3 = "UteBleConnection.setCommonInterfaceListener"
            com.yc.utesdk.log.LogUtils.initializeLog(r3)
            return
        La:
            r0 = 3
            if (r3 != r0) goto L23
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "ble发送数据到sdk完成，并且校验成功，返回数据给客户 data.length ="
            r0.append(r1)
            int r1 = r4.length
            r0.append(r1)
            java.lang.String r0 = r0.toString()
        L1f:
            com.yc.utesdk.log.LogUtils.d(r0)
            goto L29
        L23:
            r0 = 4
            if (r3 != r0) goto L29
            java.lang.String r0 = "ble发送数据到sdk完成，但是校验失败，返回状态给客户,data == null "
            goto L1f
        L29:
            boolean r0 = r2.isDelayCallBack
            if (r0 == 0) goto L3b
            android.os.Handler r0 = r2.f24901a
            l0.v r1 = new l0.v
            r1.<init>()
            int r3 = r2.delayTime
            long r3 = (long) r3
            r0.postDelayed(r1, r3)
            goto L40
        L3b:
            com.yc.utesdk.listener.CommonInterfaceListener r0 = r2.f24925v
            r0.onCommonInterfaceBleToSdk(r3, r4)
        L40:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.listener.UteListenerManager.onCommonInterfaceBleToSdk(int, byte[]):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x002b  */
    @Override // com.yc.utesdk.listener.CommonInterfaceListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onCommonInterfaceSdkToBle(final int r5) {
        /*
            r4 = this;
            com.yc.utesdk.listener.CommonInterfaceListener r0 = r4.f24925v
            if (r0 != 0) goto La
            java.lang.String r5 = "UteBleConnection.setCommonInterfaceListener"
            com.yc.utesdk.log.LogUtils.initializeLog(r5)
            return
        La:
            r0 = 1
            if (r5 != r0) goto L13
            java.lang.String r0 = "sdk发送数据到ble完成，并且校验成功，返回状态给客户 "
        Lf:
            com.yc.utesdk.log.LogUtils.d(r0)
            goto L19
        L13:
            r0 = 2
            if (r5 != r0) goto L19
            java.lang.String r0 = "sdk发送数据到ble完成，但是校验失败，返回状态给客户 "
            goto Lf
        L19:
            boolean r0 = r4.isDelayCallBack
            if (r0 == 0) goto L2b
            android.os.Handler r0 = r4.f24901a
            l0.r0 r1 = new l0.r0
            r1.<init>()
            int r5 = r4.delayTime
            long r2 = (long) r5
            r0.postDelayed(r1, r2)
            goto L30
        L2b:
            com.yc.utesdk.listener.CommonInterfaceListener r0 = r4.f24925v
            r0.onCommonInterfaceSdkToBle(r5)
        L30:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.listener.UteListenerManager.onCommonInterfaceSdkToBle(int):void");
    }

    @Override // com.yc.utesdk.listener.BleConnectStateListener
    public void onConnectException(Exception exc) {
        BleConnectStateListener bleConnectStateListener = this.f24903b;
        if (bleConnectStateListener == null) {
            LogUtils.initializeLog("UteBleConnection.setConnectStateListener");
        } else {
            bleConnectStateListener.onConnectException(exc);
        }
    }

    @Override // com.yc.utesdk.listener.BleConnectStateListener
    public void onConnecteStateChange(int i2) {
        if (this.f24903b == null) {
            LogUtils.initializeLog("UteBleConnection.setConnectStateListener");
            return;
        }
        this.bleConnectState = i2;
        LogUtils.i("onConnectStateChange status =" + i2);
        this.f24903b.onConnecteStateChange(i2);
    }

    @Override // com.yc.utesdk.listener.ContactsSyncListener
    public void onContactsSyncProgress(int i2) {
        ContactsSyncListener contactsSyncListener = this.f24926w;
        if (contactsSyncListener == null) {
            LogUtils.initializeLog("UteBleConnection.setContactsSyncListener");
        } else {
            contactsSyncListener.onContactsSyncProgress(i2);
        }
    }

    @Override // com.yc.utesdk.listener.ContactsSyncListener
    public void onContactsSyncStatus(final int i2) {
        ContactsSyncListener contactsSyncListener = this.f24926w;
        if (contactsSyncListener == null) {
            LogUtils.initializeLog("UteBleConnection.setContactsSyncListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.c0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25776a.utenfor(i2);
                }
            }, this.delayTime);
        } else {
            contactsSyncListener.onContactsSyncStatus(i2);
        }
    }

    @Override // com.yc.utesdk.listener.ContinuousPpgListener
    public void onContinuousPpgStatus(final boolean z2, final int i2) {
        ContinuousPpgListener continuousPpgListener = this.W;
        if (continuousPpgListener == null) {
            LogUtils.initializeLog("UteBleConnection.setContinuousPpgListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.v0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25970a.utentry(z2, i2);
                }
            }, this.delayTime);
        } else {
            continuousPpgListener.onContinuousPpgStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.CsbpChangeListener
    public void onCsbpStatus(final boolean z2, final int i2) {
        CsbpChangeListener csbpChangeListener = this.J;
        if (csbpChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setCsbpChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.y2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f26008a.utenbyte(z2, i2);
                }
            }, this.delayTime);
        } else {
            csbpChangeListener.onCsbpStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.SleepChangeListener
    public void onCyweeSleepSyncSuccess(final List<CyweeSleepTimeInfo> list) {
        SleepChangeListener sleepChangeListener = this.f24912i;
        if (sleepChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setSleepChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.z1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f26016a.utennew(list);
                }
            }, this.delayTime);
        } else {
            sleepChangeListener.onCyweeSleepSyncSuccess(list);
        }
    }

    @Override // com.yc.utesdk.listener.WatchFaceOnlineListener
    public void onDeleteWatchFaceOnline(int i2) {
        if (this.f24928y == null) {
            LogUtils.initializeLog("UteBleConnection.setWatchFaceOnlineListener");
            return;
        }
        LogUtils.i("onDeleteWatchFaceOnline status =" + i2);
        this.f24928y.onDeleteWatchFaceOnline(i2);
    }

    @Override // com.yc.utesdk.listener.Device4GModuleListener
    public void onDevice4GModuleIMEI(final boolean z2, final Device4GModuleInfo device4GModuleInfo) {
        Device4GModuleListener device4GModuleListener = this.V;
        if (device4GModuleListener == null) {
            LogUtils.initializeLog("UteBleConnection.setDevice4GModuleListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.g1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25818a.utendo(z2, device4GModuleInfo);
                }
            }, this.delayTime);
        } else {
            device4GModuleListener.onDevice4GModuleIMEI(z2, device4GModuleInfo);
        }
    }

    @Override // com.yc.utesdk.listener.Device4GModuleListener
    public void onDevice4GModuleStatus(final boolean z2, final int i2) {
        Device4GModuleListener device4GModuleListener = this.V;
        if (device4GModuleListener == null) {
            LogUtils.initializeLog("UteBleConnection.setDevice4GModuleListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.d2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25790a.utencase(z2, i2);
                }
            }, this.delayTime);
        } else {
            device4GModuleListener.onDevice4GModuleStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.DeviceAlarmListener
    public void onDeviceAlarmStatus(final boolean z2, final int i2) {
        if (this.f24922s == null) {
            LogUtils.initializeLog("UteBleConnection.setDeviceAlarmListener");
            return;
        }
        LogUtils.i("onDeviceAlarmStatus result =" + z2 + ",alarmId =" + i2);
        if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.s1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25943a.utenchar(z2, i2);
                }
            }, this.delayTime);
        } else {
            this.f24922s.onDeviceAlarmStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.DeviceBt3Listener
    public void onDeviceBt3State(final boolean z2, final DeviceBt3StateInfo deviceBt3StateInfo) {
        DeviceBt3Listener deviceBt3Listener = this.C;
        if (deviceBt3Listener == null) {
            LogUtils.initializeLog("UteBleConnection.setDeviceBt3Listener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.c2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25781a.utendo(z2, deviceBt3StateInfo);
                }
            }, this.delayTime);
        } else {
            deviceBt3Listener.onDeviceBt3State(z2, deviceBt3StateInfo);
        }
    }

    @Override // com.yc.utesdk.listener.DeviceBt3Listener
    public void onDeviceBt3Switch(final boolean z2, final int i2) {
        DeviceBt3Listener deviceBt3Listener = this.C;
        if (deviceBt3Listener == null) {
            LogUtils.initializeLog("UteBleConnection.setDeviceBt3Listener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.h0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25825a.utenelse(z2, i2);
                }
            }, this.delayTime);
        } else {
            deviceBt3Listener.onDeviceBt3Switch(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.DeviceCameraListener
    public void onDeviceCameraStatus(final boolean z2, final int i2) {
        DeviceCameraListener deviceCameraListener = this.f24921r;
        if (deviceCameraListener == null) {
            LogUtils.initializeLog("UteBleConnection.setDeviceCameraListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.x
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25991a.utengoto(z2, i2);
                }
            }, this.delayTime);
        } else {
            deviceCameraListener.onDeviceCameraStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.DeviceLabelAlarmListener
    public void onDeviceLabelAlarmStatus(final boolean z2, final int i2) {
        DeviceLabelAlarmListener deviceLabelAlarmListener = this.G;
        if (deviceLabelAlarmListener == null) {
            LogUtils.initializeLog("UteBleConnection.setDeviceLabelAlarmListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.y1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f26005a.utenlong(z2, i2);
                }
            }, this.delayTime);
        } else {
            deviceLabelAlarmListener.onDeviceLabelAlarmStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.DeviceLanguageListener
    public void onDeviceLanguageStatus(final boolean z2, final int i2) {
        if (this.f24923t == null) {
            LogUtils.initializeLog("UteBleConnection.setDeviceLanguageListener");
            return;
        }
        LogUtils.i("onDeviceLanguageStatus result =" + z2 + ",language =" + i2);
        if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.w2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25988a.utenthis(z2, i2);
                }
            }, this.delayTime);
        } else {
            this.f24923t.onDeviceLanguageStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.DeviceMusicListener
    public void onDeviceMusicStatus(final boolean z2, final int i2) {
        DeviceMusicListener deviceMusicListener = this.f24927x;
        if (deviceMusicListener == null) {
            LogUtils.initializeLog("UteBleConnection.setDeviceMusicListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.w0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25981a.utenvoid(z2, i2);
                }
            }, this.delayTime);
        } else {
            deviceMusicListener.onDeviceMusicStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.CsbpChangeListener
    public void onDevicePmSuccess(final List<CSBPDevicePmInfo> list) {
        CsbpChangeListener csbpChangeListener = this.J;
        if (csbpChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setCsbpChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.a0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25755a.utentry(list);
                }
            }, this.delayTime);
        } else {
            csbpChangeListener.onDevicePmSuccess(list);
        }
    }

    @Override // com.yc.utesdk.listener.DevicePrayerListener
    public void onDevicePrayerInfo(List<PrayerInfo> list, int i2) {
        DevicePrayerListener devicePrayerListener = this.T;
        if (devicePrayerListener == null) {
            return;
        }
        devicePrayerListener.onDevicePrayerInfo(list, i2);
    }

    @Override // com.yc.utesdk.listener.DevicePrayerListener
    public void onDevicePrayerStatus(boolean z2, int i2) {
        DevicePrayerListener devicePrayerListener = this.T;
        if (devicePrayerListener == null) {
            return;
        }
        devicePrayerListener.onDevicePrayerStatus(z2, i2);
    }

    @Override // com.yc.utesdk.listener.DeviceQRCodeListener
    public void onDeviceQRCodeState(int i2) {
        DeviceQRCodeListener deviceQRCodeListener = this.R;
        if (deviceQRCodeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setDeviceQRCodeListener");
        } else {
            deviceQRCodeListener.onDeviceQRCodeState(i2);
        }
    }

    @Override // com.yc.utesdk.listener.DeviceShortcutSwitchListener
    public void onDeviceShortcutSwitch(final boolean z2, final byte[] bArr) {
        DeviceShortcutSwitchListener deviceShortcutSwitchListener = this.f24924u;
        if (deviceShortcutSwitchListener == null) {
            LogUtils.initializeLog("UteBleConnection.setDeviceShortcutSwitchListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.j0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25843a.utendo(z2, bArr);
                }
            }, this.delayTime);
        } else {
            deviceShortcutSwitchListener.onDeviceShortcutSwitch(z2, bArr);
        }
    }

    @Override // com.yc.utesdk.listener.DeviceShortcutSwitchListener
    public void onDeviceShortcutSwitchStatus(final boolean z2, final byte[] bArr) {
        DeviceShortcutSwitchListener deviceShortcutSwitchListener = this.f24924u;
        if (deviceShortcutSwitchListener == null) {
            LogUtils.initializeLog("UteBleConnection.setDeviceShortcutSwitchListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.z
                @Override // java.lang.Runnable
                public final void run() {
                    this.f26011a.utenif(z2, bArr);
                }
            }, this.delayTime);
        } else {
            deviceShortcutSwitchListener.onDeviceShortcutSwitchStatus(z2, bArr);
        }
    }

    @Override // com.yc.utesdk.listener.CsbpChangeListener
    public void onDeviceSnSuccess(final String str) {
        CsbpChangeListener csbpChangeListener = this.J;
        if (csbpChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setCsbpChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.t0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25951a.utendo(str);
                }
            }, this.delayTime);
        } else {
            csbpChangeListener.onDeviceSnSuccess(str);
        }
    }

    @Override // com.yc.utesdk.listener.DeviceWidgetListener
    public void onDeviceWidgetStatus(final boolean z2, final int i2) {
        DeviceWidgetListener deviceWidgetListener = this.I;
        if (deviceWidgetListener == null) {
            LogUtils.initializeLog("UteBleConnection.setDeviceWidgetListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.m1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25878a.utenbreak(z2, i2);
                }
            }, this.delayTime);
        } else {
            deviceWidgetListener.onDeviceWidgetStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.EcgChangeListener
    public void onEcgRealTime(final EcgInfo ecgInfo) {
        EcgChangeListener ecgChangeListener = this.f24918o;
        if (ecgChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setEcgChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.z0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f26014a.utendo(ecgInfo);
                }
            }, this.delayTime);
        } else {
            ecgChangeListener.onEcgRealTime(ecgInfo);
        }
    }

    @Override // com.yc.utesdk.listener.EcgChangeListener
    public void onEcgRealTimeData(final ArrayList<Double> arrayList) {
        EcgChangeListener ecgChangeListener = this.f24918o;
        if (ecgChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setEcgChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.w
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25979a.utendo(arrayList);
                }
            }, this.delayTime);
        } else {
            ecgChangeListener.onEcgRealTimeData(arrayList);
        }
    }

    @Override // com.yc.utesdk.listener.EcgChangeListener
    public void onEcgSamplingFrequency(int i2, int i3) {
        EcgChangeListener ecgChangeListener = this.f24918o;
        if (ecgChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setEcgChangeListener");
        } else {
            ecgChangeListener.onEcgSamplingFrequency(i2, i3);
        }
    }

    @Override // com.yc.utesdk.listener.EcgChangeListener
    public void onEcgStatus(final boolean z2, final int i2) {
        EcgChangeListener ecgChangeListener = this.f24918o;
        if (ecgChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setEcgChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.b3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25770a.utencatch(z2, i2);
                }
            }, this.delayTime);
        } else {
            ecgChangeListener.onEcgStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.EcgChangeListener
    public void onEcgSyncFail() {
        EcgChangeListener ecgChangeListener = this.f24918o;
        if (ecgChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setEcgChangeListener");
        } else {
            ecgChangeListener.onEcgSyncFail();
        }
    }

    @Override // com.yc.utesdk.listener.EcgChangeListener
    public void onEcgSyncSuccess(final boolean z2, final int i2, final List<EcgInfo> list) {
        EcgChangeListener ecgChangeListener = this.f24918o;
        if (ecgChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setEcgChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.p1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25913a.utendo(z2, i2, list);
                }
            }, this.delayTime);
        } else {
            ecgChangeListener.onEcgSyncSuccess(z2, i2, list);
        }
    }

    @Override // com.yc.utesdk.listener.EcgChangeListener
    public void onEcgSyncing() {
        EcgChangeListener ecgChangeListener = this.f24918o;
        if (ecgChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setEcgChangeListener");
        } else {
            ecgChangeListener.onEcgSyncing();
        }
    }

    @Override // com.yc.utesdk.listener.ElComplexListener
    public void onElComplexStatus(final boolean z2, final int i2) {
        ElComplexListener elComplexListener = this.P;
        if (elComplexListener == null) {
            LogUtils.initializeLog("UteBleConnection.setElComplexListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.t2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25954a.utenclass(z2, i2);
                }
            }, this.delayTime);
        } else {
            elComplexListener.onElComplexStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.ElHRVListener
    public void onElHRVPpgRealTime(ElHRVMiddleDataInfo elHRVMiddleDataInfo) {
        ElHRVListener elHRVListener = this.O;
        if (elHRVListener == null) {
            LogUtils.initializeLog("UteBleConnection.setElHRVListener");
        } else {
            elHRVListener.onElHRVPpgRealTime(elHRVMiddleDataInfo);
        }
    }

    @Override // com.yc.utesdk.listener.ElHRVListener
    public void onElHRVStatus(final boolean z2, final int i2) {
        ElHRVListener elHRVListener = this.O;
        if (elHRVListener == null) {
            LogUtils.initializeLog("UteBleConnection.setElHRVListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.g
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25813a.utenconst(z2, i2);
                }
            }, this.delayTime);
        } else {
            elHRVListener.onElHRVStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.ElbpListener
    public void onElbpAlgorithmVersion(final boolean z2, final String str) {
        ElbpListener elbpListener = this.K;
        if (elbpListener == null) {
            LogUtils.initializeLog("UteBleConnection.setElbpListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.l0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25865a.utendo(z2, str);
                }
            }, this.delayTime);
        } else {
            elbpListener.onElbpAlgorithmVersion(z2, str);
        }
    }

    @Override // com.yc.utesdk.listener.ContinuousPpgListener
    public void onElbpContinuousPpgRealTime(final ElbpContinuousPpgDataInfo elbpContinuousPpgDataInfo) {
        ContinuousPpgListener continuousPpgListener = this.W;
        if (continuousPpgListener == null) {
            LogUtils.initializeLog("UteBleConnection.setContinuousPpgListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.g0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25816a.utendo(elbpContinuousPpgDataInfo);
                }
            }, this.delayTime);
        } else {
            continuousPpgListener.onElbpContinuousPpgRealTime(elbpContinuousPpgDataInfo);
        }
    }

    @Override // com.yc.utesdk.listener.ElbpListener
    public void onElbpMiddleDataSyncFail() {
        ElbpListener elbpListener = this.K;
        if (elbpListener == null) {
            LogUtils.initializeLog("UteBleConnection.setElbpListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.a1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25757a.utendo();
                }
            }, this.delayTime);
        } else {
            elbpListener.onElbpMiddleDataSyncFail();
        }
    }

    @Override // com.yc.utesdk.listener.ElbpListener
    public void onElbpMiddleDataSyncSuccess(final List<ElbpBleMiddleInfo> list) {
        ElbpListener elbpListener = this.K;
        if (elbpListener == null) {
            LogUtils.initializeLog("UteBleConnection.setElbpListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.s
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25938a.utenbyte(list);
                }
            }, this.delayTime);
        } else {
            elbpListener.onElbpMiddleDataSyncSuccess(list);
        }
    }

    @Override // com.yc.utesdk.listener.ElbpListener
    public void onElbpMiddleDataSyncing() {
        ElbpListener elbpListener = this.K;
        if (elbpListener == null) {
            LogUtils.initializeLog("UteBleConnection.setElbpListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.e0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25796a.utenif();
                }
            }, this.delayTime);
        } else {
            elbpListener.onElbpMiddleDataSyncing();
        }
    }

    @Override // com.yc.utesdk.listener.ElbpListener
    public void onElbpMiddleRealTime(final List<ElbpMiddleDataInfo> list) {
        ElbpListener elbpListener = this.K;
        if (elbpListener == null) {
            LogUtils.initializeLog("UteBleConnection.setElbpListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.n
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25885a.utencase(list);
                }
            }, this.delayTime);
        } else {
            elbpListener.onElbpMiddleRealTime(list);
        }
    }

    @Override // com.yc.utesdk.listener.ElbpListener
    public void onElbpPpgDataSyncFail() {
        ElbpListener elbpListener = this.K;
        if (elbpListener == null) {
            LogUtils.initializeLog("UteBleConnection.setElbpListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.x2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25998a.utenfor();
                }
            }, this.delayTime);
        } else {
            elbpListener.onElbpPpgDataSyncFail();
        }
    }

    @Override // com.yc.utesdk.listener.ElbpListener
    public void onElbpPpgDataSyncSuccess(final List<ElbpBlePpgInfo> list) {
        ElbpListener elbpListener = this.K;
        if (elbpListener == null) {
            LogUtils.initializeLog("UteBleConnection.setElbpListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.e1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25797a.utenchar(list);
                }
            }, this.delayTime);
        } else {
            elbpListener.onElbpPpgDataSyncSuccess(list);
        }
    }

    @Override // com.yc.utesdk.listener.ElbpListener
    public void onElbpPpgDataSyncing() {
        ElbpListener elbpListener = this.K;
        if (elbpListener == null) {
            LogUtils.initializeLog("UteBleConnection.setElbpListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.h2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25831a.utenint();
                }
            }, this.delayTime);
        } else {
            elbpListener.onElbpPpgDataSyncing();
        }
    }

    @Override // com.yc.utesdk.listener.ElbpListener
    public void onElbpPpgRealTime(final ElbpPpgDataInfo elbpPpgDataInfo) {
        ElbpListener elbpListener = this.K;
        if (elbpListener == null) {
            LogUtils.initializeLog("UteBleConnection.setElbpListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.q2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25927a.utendo(elbpPpgDataInfo);
                }
            }, this.delayTime);
        } else {
            elbpListener.onElbpPpgRealTime(elbpPpgDataInfo);
        }
    }

    @Override // com.yc.utesdk.listener.ElbpListener
    public void onElbpStatus(final boolean z2, final int i2) {
        ElbpListener elbpListener = this.K;
        if (elbpListener == null) {
            LogUtils.initializeLog("UteBleConnection.setElbpListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.a2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25758a.utenfinal(z2, i2);
                }
            }, this.delayTime);
        } else {
            elbpListener.onElbpStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.ElbsListener
    public void onElbsPpgRealTime(ElbsPpgDataInfo elbsPpgDataInfo) {
        ElbsListener elbsListener = this.N;
        if (elbsListener == null) {
            LogUtils.initializeLog("UteBleConnection.setElbsListener");
        } else {
            elbsListener.onElbsPpgRealTime(elbsPpgDataInfo);
        }
    }

    @Override // com.yc.utesdk.listener.ElbsListener
    public void onElbsStatus(final boolean z2, final int i2) {
        ElbsListener elbsListener = this.N;
        if (elbsListener == null) {
            LogUtils.initializeLog("UteBleConnection.setElbsListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.s0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25940a.utenfloat(z2, i2);
                }
            }, this.delayTime);
        } else {
            elbsListener.onElbsStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.GPSListener
    public <T> void onGPSNotify(int i2, T t2) {
        GPSListener gPSListener = this.Y;
        if (gPSListener == null) {
            LogUtils.initializeLog("UteBleConnection.setGpsListener");
        } else {
            gPSListener.onGPSNotify(i2, t2);
        }
    }

    @Override // com.yc.utesdk.listener.GPSListener
    public void onGPSStatus(boolean z2, int i2) {
        GPSListener gPSListener = this.Y;
        if (gPSListener == null) {
            LogUtils.initializeLog("UteBleConnection.setGpsListener");
        } else {
            gPSListener.onGPSStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.HeartRateChangeListener
    public void onHeartRateBestValue(HeartRateBestValueInfo heartRateBestValueInfo) {
        HeartRateChangeListener heartRateChangeListener = this.f24913j;
        if (heartRateChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setHeartRateChangeListener");
        } else {
            heartRateChangeListener.onHeartRateBestValue(heartRateBestValueInfo);
        }
    }

    @Override // com.yc.utesdk.listener.HeartRateChangeListener
    public void onHeartRateHourRestBestValue(HeartRateHourBestValueInfo heartRateHourBestValueInfo) {
        HeartRateChangeListener heartRateChangeListener = this.f24913j;
        if (heartRateChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setHeartRateChangeListener");
        } else {
            heartRateChangeListener.onHeartRateHourRestBestValue(heartRateHourBestValueInfo);
        }
    }

    @Override // com.yc.utesdk.listener.HeartRateChangeListener
    public void onHeartRateRealTime(final HeartRateInfo heartRateInfo) {
        HeartRateChangeListener heartRateChangeListener = this.f24913j;
        if (heartRateChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setHeartRateChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.y
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25999a.utendo(heartRateInfo);
                }
            }, this.delayTime);
        } else {
            heartRateChangeListener.onHeartRateRealTime(heartRateInfo);
        }
    }

    @Override // com.yc.utesdk.listener.HeartRateChangeListener
    public void onHeartRateRest(HeartRateRestInfo heartRateRestInfo) {
        HeartRateChangeListener heartRateChangeListener = this.f24913j;
        if (heartRateChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setHeartRateChangeListener");
        } else {
            heartRateChangeListener.onHeartRateRest(heartRateRestInfo);
        }
    }

    @Override // com.yc.utesdk.listener.HeartRateChangeListener
    public void onHeartRateRestSyncFail() {
        HeartRateChangeListener heartRateChangeListener = this.f24913j;
        if (heartRateChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setHeartRateChangeListener");
        } else {
            heartRateChangeListener.onHeartRateRestSyncFail();
        }
    }

    @Override // com.yc.utesdk.listener.HeartRateChangeListener
    public void onHeartRateRestSyncSuccess(final List<HeartRateRestInfo> list) {
        HeartRateChangeListener heartRateChangeListener = this.f24913j;
        if (heartRateChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setHeartRateChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.f0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25804a.utenelse(list);
                }
            }, this.delayTime);
        } else {
            heartRateChangeListener.onHeartRateRestSyncSuccess(list);
        }
    }

    @Override // com.yc.utesdk.listener.HeartRateChangeListener
    public void onHeartRateRestSyncing() {
        HeartRateChangeListener heartRateChangeListener = this.f24913j;
        if (heartRateChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setHeartRateChangeListener");
        } else {
            heartRateChangeListener.onHeartRateRestSyncing();
        }
    }

    @Override // com.yc.utesdk.listener.HeartRateChangeListener
    public void onHeartRateStatus(final boolean z2, final int i2) {
        HeartRateChangeListener heartRateChangeListener = this.f24913j;
        if (heartRateChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setHeartRateChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.q
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25919a.utenshort(z2, i2);
                }
            }, this.delayTime);
        } else {
            heartRateChangeListener.onHeartRateStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.HeartRateChangeListener
    public void onHeartRateSyncFail() {
        HeartRateChangeListener heartRateChangeListener = this.f24913j;
        if (heartRateChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setHeartRateChangeListener");
        } else {
            heartRateChangeListener.onHeartRateSyncFail();
        }
    }

    @Override // com.yc.utesdk.listener.HeartRateChangeListener
    public void onHeartRateSyncSuccess(final List<HeartRateInfo> list) {
        HeartRateChangeListener heartRateChangeListener = this.f24913j;
        if (heartRateChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setHeartRateChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.l1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25868a.utengoto(list);
                }
            }, this.delayTime);
        } else {
            heartRateChangeListener.onHeartRateSyncSuccess(list);
        }
    }

    @Override // com.yc.utesdk.listener.HeartRateChangeListener
    public void onHeartRateSyncing() {
        HeartRateChangeListener heartRateChangeListener = this.f24913j;
        if (heartRateChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setHeartRateChangeListener");
        } else {
            heartRateChangeListener.onHeartRateSyncing();
        }
    }

    @Override // com.yc.utesdk.listener.MenstrualListener
    public void onMenstrualMonthData(boolean z2, List<Integer> list) {
        MenstrualListener menstrualListener = this.f24902a0;
        if (menstrualListener == null) {
            LogUtils.initializeLog("UteBleConnection.setMenstrualListener");
        } else {
            menstrualListener.onMenstrualMonthData(z2, list);
        }
    }

    @Override // com.yc.utesdk.listener.MenstrualListener
    public void onMenstrualParam(boolean z2, MenstrualParam menstrualParam) {
        MenstrualListener menstrualListener = this.f24902a0;
        if (menstrualListener == null) {
            LogUtils.initializeLog("UteBleConnection.setMenstrualListener");
        } else {
            menstrualListener.onMenstrualParam(z2, menstrualParam);
        }
    }

    @Override // com.yc.utesdk.listener.MessageReplyListener
    public void onMessageReplyData(MessageReplyDataInfo messageReplyDataInfo) {
        MessageReplyListener messageReplyListener = this.f24904b0;
        if (messageReplyListener == null) {
            LogUtils.initializeLog("UteBleConnection.setMessageReplyListener");
        } else {
            messageReplyListener.onMessageReplyData(messageReplyDataInfo);
        }
    }

    @Override // com.yc.utesdk.listener.MessageReplyListener
    public void onMessageReplyState(boolean z2, int i2) {
        MessageReplyListener messageReplyListener = this.f24904b0;
        if (messageReplyListener == null) {
            LogUtils.initializeLog("UteBleConnection.setMessageReplyListener");
        } else {
            messageReplyListener.onMessageReplyState(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.MoodPressureListener
    public void onMoodPressureRealTime(final MoodPressureFatigueInfo moodPressureFatigueInfo) {
        MoodPressureListener moodPressureListener = this.B;
        if (moodPressureListener == null) {
            LogUtils.initializeLog("UteBleConnection.setMoodPressureListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.m0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25876a.utendo(moodPressureFatigueInfo);
                }
            }, this.delayTime);
        } else {
            moodPressureListener.onMoodPressureRealTime(moodPressureFatigueInfo);
        }
    }

    @Override // com.yc.utesdk.listener.MoodPressureListener
    public void onMoodPressureSensor(final MoodSensorInterfaceInfo moodSensorInterfaceInfo) {
        MoodPressureListener moodPressureListener = this.B;
        if (moodPressureListener == null) {
            LogUtils.initializeLog("UteBleConnection.setMoodPressureListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.l
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25863a.utendo(moodSensorInterfaceInfo);
                }
            }, this.delayTime);
        } else {
            moodPressureListener.onMoodPressureSensor(moodSensorInterfaceInfo);
        }
    }

    @Override // com.yc.utesdk.listener.MoodPressureListener
    public void onMoodPressureStatus(final boolean z2, final int i2) {
        MoodPressureListener moodPressureListener = this.B;
        if (moodPressureListener == null) {
            LogUtils.initializeLog("UteBleConnection.setMoodPressureListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.j1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25846a.utensuper(z2, i2);
                }
            }, this.delayTime);
        } else {
            moodPressureListener.onMoodPressureStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.MoodPressureListener
    public void onMoodPressureSyncFail() {
        MoodPressureListener moodPressureListener = this.B;
        if (moodPressureListener == null) {
            LogUtils.initializeLog("UteBleConnection.setMoodPressureListener");
        } else {
            moodPressureListener.onMoodPressureSyncFail();
        }
    }

    @Override // com.yc.utesdk.listener.MoodPressureListener
    public void onMoodPressureSyncSuccess(final List<MoodPressureFatigueInfo> list) {
        MoodPressureListener moodPressureListener = this.B;
        if (moodPressureListener == null) {
            LogUtils.initializeLog("UteBleConnection.setMoodPressureListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.i0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25834a.utenlong(list);
                }
            }, this.delayTime);
        } else {
            moodPressureListener.onMoodPressureSyncSuccess(list);
        }
    }

    @Override // com.yc.utesdk.listener.MoodPressureListener
    public void onMoodPressureSyncing() {
        MoodPressureListener moodPressureListener = this.B;
        if (moodPressureListener == null) {
            LogUtils.initializeLog("UteBleConnection.setMoodPressureListener");
        } else {
            moodPressureListener.onMoodPressureSyncing();
        }
    }

    @Override // com.yc.utesdk.listener.MultiSportTargetListener
    public void onMultiSportTargetStatus(final boolean z2, final int i2) {
        MultiSportTargetListener multiSportTargetListener = this.H;
        if (multiSportTargetListener == null) {
            LogUtils.initializeLog("UteBleConnection.setMultiSportTargetListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.j
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25840a.utenthrow(z2, i2);
                }
            }, this.delayTime);
        } else {
            multiSportTargetListener.onMultiSportTargetStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.MultiSportsListener
    public void onMultiSportsRealData(final SportsRealDataInfo sportsRealDataInfo) {
        MultiSportsListener multiSportsListener = this.D;
        if (multiSportsListener == null) {
            LogUtils.initializeLog("UteBleConnection.setMultiSportsListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.x0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25994a.utendo(sportsRealDataInfo);
                }
            }, this.delayTime);
        } else {
            multiSportsListener.onMultiSportsRealData(sportsRealDataInfo);
        }
    }

    @Override // com.yc.utesdk.listener.MultiSportsListener
    public void onMultiSportsStatus(final boolean z2, final int i2, final int i3) {
        MultiSportsListener multiSportsListener = this.D;
        if (multiSportsListener == null) {
            LogUtils.initializeLog("UteBleConnection.setMultiSportsListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.m2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25881a.utendo(z2, i2, i3);
                }
            }, this.delayTime);
        } else {
            multiSportsListener.onMultiSportsStatus(z2, i2, i3);
        }
    }

    @Override // com.yc.utesdk.listener.MultiSportsListener
    public void onMultiSportsSyncFail() {
        MultiSportsListener multiSportsListener = this.D;
        if (multiSportsListener == null) {
            LogUtils.initializeLog("UteBleConnection.setMultiSportsListener");
        } else {
            multiSportsListener.onMultiSportsSyncFail();
        }
    }

    @Override // com.yc.utesdk.listener.MultiSportsListener
    public void onMultiSportsSyncSuccess(final List<SportsDataInfo> list) {
        MultiSportsListener multiSportsListener = this.D;
        if (multiSportsListener == null) {
            LogUtils.initializeLog("UteBleConnection.setMultiSportsListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.q0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25922a.utenthis(list);
                }
            }, this.delayTime);
        } else {
            multiSportsListener.onMultiSportsSyncSuccess(list);
        }
    }

    @Override // com.yc.utesdk.listener.MultiSportsListener
    public void onMultiSportsSyncing() {
        MultiSportsListener multiSportsListener = this.D;
        if (multiSportsListener == null) {
            LogUtils.initializeLog("UteBleConnection.setMultiSportsListener");
        } else {
            multiSportsListener.onMultiSportsSyncing();
        }
    }

    @Override // com.yc.utesdk.listener.OxygenChangeListener
    public void onOxygenRealTime(final OxygenInfo oxygenInfo) {
        OxygenChangeListener oxygenChangeListener = this.f24915l;
        if (oxygenChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setOxygenChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.r
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25929a.utendo(oxygenInfo);
                }
            }, this.delayTime);
        } else {
            oxygenChangeListener.onOxygenRealTime(oxygenInfo);
        }
    }

    @Override // com.yc.utesdk.listener.OxygenChangeListener
    public void onOxygenStatus(final boolean z2, final int i2) {
        OxygenChangeListener oxygenChangeListener = this.f24915l;
        if (oxygenChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setOxygenChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.t
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25948a.utenwhile(z2, i2);
                }
            }, this.delayTime);
        } else {
            oxygenChangeListener.onOxygenStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.OxygenChangeListener
    public void onOxygenSyncFail() {
        OxygenChangeListener oxygenChangeListener = this.f24915l;
        if (oxygenChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setOxygenChangeListener");
        } else {
            oxygenChangeListener.onOxygenSyncFail();
        }
    }

    @Override // com.yc.utesdk.listener.OxygenChangeListener
    public void onOxygenSyncSuccess(final List<OxygenInfo> list) {
        OxygenChangeListener oxygenChangeListener = this.f24915l;
        if (oxygenChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setOxygenChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.o0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25900a.utenvoid(list);
                }
            }, this.delayTime);
        } else {
            oxygenChangeListener.onOxygenSyncSuccess(list);
        }
    }

    @Override // com.yc.utesdk.listener.OxygenChangeListener
    public void onOxygenSyncing() {
        OxygenChangeListener oxygenChangeListener = this.f24915l;
        if (oxygenChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setOxygenChangeListener");
        } else {
            oxygenChangeListener.onOxygenSyncing();
        }
    }

    @Override // com.yc.utesdk.listener.DeviceLanguageListener
    public void onQueryCurrentLanguage(final boolean z2, final int i2) {
        if (this.f24923t == null) {
            LogUtils.initializeLog("UteBleConnection.setDeviceLanguageListener");
            return;
        }
        LogUtils.i("onQueryCurrentLanguage result =" + z2 + ",language =" + i2);
        if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.n1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25890a.utendouble(z2, i2);
                }
            }, this.delayTime);
        } else {
            this.f24923t.onQueryCurrentLanguage(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.CsbpChangeListener
    public void onQueryDeviceChipSuccess(final int i2, final int i3, final String str) {
        CsbpChangeListener csbpChangeListener = this.J;
        if (csbpChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setCsbpChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.l2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25870a.utendo(i2, i3, str);
                }
            }, this.delayTime);
        } else {
            csbpChangeListener.onQueryDeviceChipSuccess(i2, i3, str);
        }
    }

    @Override // com.yc.utesdk.listener.DeviceLabelAlarmListener
    public void onQueryDeviceLabelAlarmSuccess(final List<LabelAlarmClockInfo> list) {
        DeviceLabelAlarmListener deviceLabelAlarmListener = this.G;
        if (deviceLabelAlarmListener == null) {
            LogUtils.initializeLog("UteBleConnection.setDeviceLabelAlarmListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.o
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25898a.utenbreak(list);
                }
            }, this.delayTime);
        } else {
            deviceLabelAlarmListener.onQueryDeviceLabelAlarmSuccess(list);
        }
    }

    @Override // com.yc.utesdk.listener.CsbpChangeListener
    public void onQueryDeviceModuleIdSuccess(final int i2, final String str) {
        CsbpChangeListener csbpChangeListener = this.J;
        if (csbpChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setCsbpChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.c
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25773a.utendo(i2, str);
                }
            }, this.delayTime);
        } else {
            csbpChangeListener.onQueryDeviceModuleIdSuccess(i2, str);
        }
    }

    @Override // com.yc.utesdk.listener.DeviceParamListener
    public <T> void onQueryDeviceParam(boolean z2, int i2, T t2) {
        DeviceParamListener deviceParamListener = this.Z;
        if (deviceParamListener == null) {
            LogUtils.initializeLog("UteBleConnection.setDeviceParamListener");
        } else {
            deviceParamListener.onQueryDeviceParam(z2, i2, t2);
        }
    }

    @Override // com.yc.utesdk.listener.DeviceWidgetListener
    public void onQueryDeviceWidgetSuccess(final List<DeviceWidgetInfo> list, final List<DeviceWidgetInfo> list2, final List<DeviceWidgetInfo> list3) {
        DeviceWidgetListener deviceWidgetListener = this.I;
        if (deviceWidgetListener == null) {
            LogUtils.initializeLog("UteBleConnection.setDeviceWidgetListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.w1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25984a.utendo(list, list2, list3);
                }
            }, this.delayTime);
        } else {
            deviceWidgetListener.onQueryDeviceWidgetSuccess(list, list2, list3);
        }
    }

    @Override // com.yc.utesdk.listener.CallSmsAppRemindListener
    public void onQueryRemindDisplay(final boolean z2) {
        CallSmsAppRemindListener callSmsAppRemindListener = this.f24920q;
        if (callSmsAppRemindListener == null) {
            LogUtils.initializeLog("UteBleConnection.setCallSmsAppRemindListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.i
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25832a.utendo(z2);
                }
            }, this.delayTime);
        } else {
            callSmsAppRemindListener.onQueryRemindDisplay(z2);
        }
    }

    @Override // com.yc.utesdk.listener.QuickReplySmsListener
    public void onQuerySmsContent(final boolean z2, final int i2, final List<ReplySmsInfo> list) {
        QuickReplySmsListener quickReplySmsListener = this.F;
        if (quickReplySmsListener == null) {
            LogUtils.initializeLog("UteBleConnection.setQuickReplySmsListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.y0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f26001a.utenif(z2, i2, list);
                }
            }, this.delayTime);
        } else {
            quickReplySmsListener.onQuerySmsContent(z2, i2, list);
        }
    }

    @Override // com.yc.utesdk.listener.SosContactsListener
    public void onQuerySosContacts(final boolean z2, final List<SosCallInfo> list) {
        SosContactsListener sosContactsListener = this.E;
        if (sosContactsListener == null) {
            LogUtils.initializeLog("UteBleConnection.setSosContactsListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.f
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25801a.utenif(z2, list);
                }
            }, this.delayTime);
        } else {
            sosContactsListener.onQuerySosContacts(z2, list);
        }
    }

    @Override // com.yc.utesdk.listener.SosContactsListener
    public void onQuerySosContactsCount(final boolean z2, final int i2) {
        SosContactsListener sosContactsListener = this.E;
        if (sosContactsListener == null) {
            LogUtils.initializeLog("UteBleConnection.setSosContactsListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.v1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25973a.utenimport(z2, i2);
                }
            }, this.delayTime);
        } else {
            sosContactsListener.onQuerySosContactsCount(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.MultiSportsListener
    public void onQuerySportsMode(final boolean z2, final int i2, final int i3) {
        MultiSportsListener multiSportsListener = this.D;
        if (multiSportsListener == null) {
            LogUtils.initializeLog("UteBleConnection.setMultiSportsListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.k0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25853a.utenif(z2, i2, i3);
                }
            }, this.delayTime);
        } else {
            multiSportsListener.onQuerySportsMode(z2, i2, i3);
        }
    }

    @Override // com.yc.utesdk.listener.MultiSportsListener
    public void onQuerySportsModeList(final boolean z2, final int i2, final int i3, final List<SportsModeInfo> list) {
        MultiSportsListener multiSportsListener = this.D;
        if (multiSportsListener == null) {
            LogUtils.initializeLog("UteBleConnection.setMultiSportsListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.n2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25893a.utendo(z2, i2, i3, list);
                }
            }, this.delayTime);
        } else {
            multiSportsListener.onQuerySportsModeList(z2, i2, i3, list);
        }
    }

    @Override // com.yc.utesdk.listener.TodayTargetListener
    public void onQueryTodayTarget(final boolean z2, final int i2, final int i3) {
        TodayTargetListener todayTargetListener = this.A;
        if (todayTargetListener == null) {
            LogUtils.initializeLog("UteBleConnection.onQueryTodayTarget");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.f1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25806a.utenfor(z2, i2, i3);
                }
            }, this.delayTime);
        } else {
            todayTargetListener.onQueryTodayTarget(z2, i2, i3);
        }
    }

    @Override // com.yc.utesdk.listener.QuickReplySmsListener
    public void onQuickReplySmsContent(final String str, final String str2) {
        QuickReplySmsListener quickReplySmsListener = this.F;
        if (quickReplySmsListener == null) {
            LogUtils.initializeLog("UteBleConnection.setQuickReplySmsListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.u1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25962a.utendo(str, str2);
                }
            }, this.delayTime);
        } else {
            quickReplySmsListener.onQuickReplySmsContent(str, str2);
        }
    }

    @Override // com.yc.utesdk.listener.QuickReplySmsListener
    public void onQuickReplySmsStatus(final boolean z2, final int i2) {
        QuickReplySmsListener quickReplySmsListener = this.F;
        if (quickReplySmsListener == null) {
            LogUtils.initializeLog("UteBleConnection.setQuickReplySmsListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.v2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25976a.utennative(z2, i2);
                }
            }, this.delayTime);
        } else {
            quickReplySmsListener.onQuickReplySmsStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.MultiSportsListener
    public void onSetSportsModeList(final boolean z2) {
        MultiSportsListener multiSportsListener = this.D;
        if (multiSportsListener == null) {
            LogUtils.initializeLog("UteBleConnection.setMultiSportsListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.m
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25874a.utenif(z2);
                }
            }, this.delayTime);
        } else {
            multiSportsListener.onSetSportsModeList(z2);
        }
    }

    @Override // com.yc.utesdk.listener.SimpleCallbackListener
    public void onSimpleCallback(final boolean z2, final int i2) {
        SimpleCallbackListener simpleCallbackListener = this.f24909f;
        if (simpleCallbackListener == null) {
            LogUtils.initializeLog("UteBleConnection.setSimpleCallbackListener");
        } else if (!this.isDelayCallBack || i2 == 0) {
            simpleCallbackListener.onSimpleCallback(z2, i2);
        } else {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.n0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25887a.utenpublic(z2, i2);
                }
            }, this.delayTime);
        }
    }

    @Override // com.yc.utesdk.listener.SleepChangeListener
    public void onSleepSyncFail() {
        SleepChangeListener sleepChangeListener = this.f24912i;
        if (sleepChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setSleepChangeListener");
        } else {
            sleepChangeListener.onSleepSyncFail();
        }
    }

    @Override // com.yc.utesdk.listener.SleepChangeListener
    public void onSleepSyncSuccess(final List<SleepInfo> list) {
        SleepChangeListener sleepChangeListener = this.f24912i;
        if (sleepChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setSleepChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.d1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25788a.utencatch(list);
                }
            }, this.delayTime);
        } else {
            sleepChangeListener.onSleepSyncSuccess(list);
        }
    }

    @Override // com.yc.utesdk.listener.SleepChangeListener
    public void onSleepSyncing() {
        SleepChangeListener sleepChangeListener = this.f24912i;
        if (sleepChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setSleepChangeListener");
        } else {
            sleepChangeListener.onSleepSyncing();
        }
    }

    @Override // com.yc.utesdk.listener.CallSmsAppRemindListener
    public void onSmsAppRemindStatus(final boolean z2, final int i2) {
        CallSmsAppRemindListener callSmsAppRemindListener = this.f24920q;
        if (callSmsAppRemindListener == null) {
            LogUtils.initializeLog("UteBleConnection.setCallSmsAppRemindListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.u0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25959a.utenreturn(z2, i2);
                }
            }, this.delayTime);
        } else {
            callSmsAppRemindListener.onSmsAppRemindStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.SosContactsListener
    public void onSosContactsStatus(final boolean z2, final int i2) {
        SosContactsListener sosContactsListener = this.E;
        if (sosContactsListener == null) {
            LogUtils.initializeLog("UteBleConnection.setSosContactsListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.c1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25778a.utenstatic(z2, i2);
                }
            }, this.delayTime);
        } else {
            sosContactsListener.onSosContactsStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.SosContactsListener
    public void onSosContactsSync(final int i2) {
        SosContactsListener sosContactsListener = this.E;
        if (sosContactsListener == null) {
            LogUtils.initializeLog("UteBleConnection.setSosContactsListener");
        } else if (this.isDelayCallBack && i2 == 2) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.b0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25764a.utenint(i2);
                }
            }, this.delayTime);
        } else {
            sosContactsListener.onSosContactsSync(i2);
        }
    }

    @Override // com.yc.utesdk.listener.MultiSportsListener
    public void onSportStatusChange(final int i2, final int i3) {
        MultiSportsListener multiSportsListener = this.D;
        if (multiSportsListener == null) {
            LogUtils.initializeLog("UteBleConnection.setMultiSportsListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.f2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25810a.utendo(i2, i3);
                }
            }, this.delayTime);
        } else {
            multiSportsListener.onSportStatusChange(i2, i3);
        }
    }

    @Override // com.yc.utesdk.listener.MultiSportsListener
    public void onSportsLocation(final boolean z2, final int i2) {
        MultiSportsListener multiSportsListener = this.D;
        if (multiSportsListener == null) {
            LogUtils.initializeLog("UteBleConnection.setMultiSportsListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.h1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25828a.utenswitch(z2, i2);
                }
            }, this.delayTime);
        } else {
            multiSportsListener.onSportsLocation(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.TodayTargetListener
    public void onStandingTimeSyncFail() {
        TodayTargetListener todayTargetListener = this.A;
        if (todayTargetListener == null) {
            LogUtils.initializeLog("UteBleConnection.setTodayTargetListener");
        } else {
            todayTargetListener.onStandingTimeSyncFail();
        }
    }

    @Override // com.yc.utesdk.listener.TodayTargetListener
    public void onStandingTimeSyncSuccess(final List<StandingTimeInfo> list) {
        TodayTargetListener todayTargetListener = this.A;
        if (todayTargetListener == null) {
            LogUtils.initializeLog("UteBleConnection.setTodayTargetListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.u2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25965a.utenclass(list);
                }
            }, this.delayTime);
        } else {
            todayTargetListener.onStandingTimeSyncSuccess(list);
        }
    }

    @Override // com.yc.utesdk.listener.TodayTargetListener
    public void onStandingTimeSyncing() {
        TodayTargetListener todayTargetListener = this.A;
        if (todayTargetListener == null) {
            LogUtils.initializeLog("UteBleConnection.setTodayTargetListener");
        } else {
            todayTargetListener.onStandingTimeSyncing();
        }
    }

    @Override // com.yc.utesdk.listener.StepChangeListener
    public void onStepChange(StepOneDayAllInfo stepOneDayAllInfo) {
        StepChangeListener stepChangeListener = this.f24911h;
        if (stepChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setStepChangeListener");
        } else {
            stepChangeListener.onStepChange(stepOneDayAllInfo);
        }
    }

    @Override // com.yc.utesdk.listener.StepChangeListener
    public void onStepSyncFail() {
        StepChangeListener stepChangeListener = this.f24911h;
        if (stepChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setStepChangeListener");
        } else {
            stepChangeListener.onStepSyncFail();
        }
    }

    @Override // com.yc.utesdk.listener.StepChangeListener
    public void onStepSyncSuccess(final List<StepOneDayAllInfo> list) {
        StepChangeListener stepChangeListener = this.f24911h;
        if (stepChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setStepChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.b2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25768a.utenconst(list);
                }
            }, this.delayTime);
        } else {
            stepChangeListener.onStepSyncSuccess(list);
        }
    }

    @Override // com.yc.utesdk.listener.StepChangeListener
    public void onStepSyncing() {
        StepChangeListener stepChangeListener = this.f24911h;
        if (stepChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setStepChangeListener");
        } else {
            stepChangeListener.onStepSyncing();
        }
    }

    @Override // com.yc.utesdk.listener.SuloseCommandListener
    public void onSuloseChanged(int i2, byte[] bArr) {
        SuloseCommandListener suloseCommandListener = this.Q;
        if (suloseCommandListener == null) {
            LogUtils.initializeLog("UteBleConnection.setSuloseCommandListener");
        } else {
            suloseCommandListener.onSuloseChanged(i2, bArr);
        }
    }

    @Override // com.yc.utesdk.listener.SuloseCommandListener
    public void onSuloseWrite(int i2, int i3) {
        SuloseCommandListener suloseCommandListener = this.Q;
        if (suloseCommandListener == null) {
            LogUtils.initializeLog("UteBleConnection.setSuloseCommandListener");
        } else {
            suloseCommandListener.onSuloseWrite(i2, i3);
        }
    }

    @Override // com.yc.utesdk.listener.CsbpChangeListener
    public void onSyncHeartRateAndOxygenSuccess(final boolean z2, final List<CSBPHeartRateAndOxygenInfo> list) {
        CsbpChangeListener csbpChangeListener = this.J;
        if (csbpChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setCsbpChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.k2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25860a.utenfor(z2, list);
                }
            }, this.delayTime);
        } else {
            csbpChangeListener.onSyncHeartRateAndOxygenSuccess(z2, list);
        }
    }

    @Override // com.yc.utesdk.listener.TemperatureChangeListener
    public void onTemperatureRealTime(final TemperatureInfo temperatureInfo) {
        TemperatureChangeListener temperatureChangeListener = this.f24916m;
        if (temperatureChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setTemperatureChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.s2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25946a.utendo(temperatureInfo);
                }
            }, this.delayTime);
        } else {
            temperatureChangeListener.onTemperatureRealTime(temperatureInfo);
        }
    }

    @Override // com.yc.utesdk.listener.TemperatureChangeListener
    public void onTemperatureStatus(final boolean z2, final int i2) {
        TemperatureChangeListener temperatureChangeListener = this.f24916m;
        if (temperatureChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setTemperatureChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.o2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25905a.utenthrows(z2, i2);
                }
            }, this.delayTime);
        } else {
            temperatureChangeListener.onTemperatureStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.TemperatureChangeListener
    public void onTemperatureSyncFail() {
        TemperatureChangeListener temperatureChangeListener = this.f24916m;
        if (temperatureChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setTemperatureChangeListener");
        } else {
            temperatureChangeListener.onTemperatureSyncFail();
        }
    }

    @Override // com.yc.utesdk.listener.TemperatureChangeListener
    public void onTemperatureSyncSuccess(final List<TemperatureInfo> list) {
        TemperatureChangeListener temperatureChangeListener = this.f24916m;
        if (temperatureChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setTemperatureChangeListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.x1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25996a.utenfinal(list);
                }
            }, this.delayTime);
        } else {
            temperatureChangeListener.onTemperatureSyncSuccess(list);
        }
    }

    @Override // com.yc.utesdk.listener.TemperatureChangeListener
    public void onTemperatureSyncing() {
        TemperatureChangeListener temperatureChangeListener = this.f24916m;
        if (temperatureChangeListener == null) {
            LogUtils.initializeLog("UteBleConnection.setTemperatureChangeListener");
        } else {
            temperatureChangeListener.onTemperatureSyncing();
        }
    }

    @Override // com.yc.utesdk.listener.TodayTargetListener
    public void onTodayTargetStatus(final boolean z2, final int i2) {
        TodayTargetListener todayTargetListener = this.A;
        if (todayTargetListener == null) {
            LogUtils.initializeLog("UteBleConnection.setTodayTargetListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.k1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25857a.utenboolean(z2, i2);
                }
            }, this.delayTime);
        } else {
            todayTargetListener.onTodayTargetStatus(z2, i2);
        }
    }

    @Override // com.yc.utesdk.listener.WatchFaceCustomListener
    public void onWatchFaceCustomProgress(int i2) {
        if (this.f24929z == null) {
            LogUtils.initializeLog("UteBleConnection.setWatchFaceCustomListener");
            return;
        }
        LogUtils.i("onWatchFaceCustomProgress progress =" + i2);
        this.f24929z.onWatchFaceCustomProgress(i2);
    }

    @Override // com.yc.utesdk.listener.WatchFaceCustomListener
    public void onWatchFaceCustomStatus(final int i2) {
        if (this.f24929z == null) {
            LogUtils.initializeLog("UteBleConnection.setWatchFaceCustomListener");
            return;
        }
        LogUtils.i("onWatchFaceCustomStatus status =" + i2);
        if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.r1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25933a.utennew(i2);
                }
            }, this.delayTime);
        } else {
            this.f24929z.onWatchFaceCustomStatus(i2);
        }
    }

    @Override // com.yc.utesdk.listener.WatchFaceOnlineListener
    public void onWatchFaceOnlineMaxCount(int i2) {
        if (this.f24928y == null) {
            LogUtils.initializeLog("UteBleConnection.setWatchFaceOnlineListener");
            return;
        }
        LogUtils.i("onWatchFaceOnlineMaxCount maxCount =" + i2);
        this.f24928y.onWatchFaceOnlineMaxCount(i2);
    }

    @Override // com.yc.utesdk.listener.WatchFaceOnlineListener
    public void onWatchFaceOnlineProgress(int i2) {
        if (WatchFaceUtil.getInstance().getWatchFaceMode() != 0) {
            if (WatchFaceUtil.getInstance().getWatchFaceMode() == 1) {
                onWatchFaceCustomProgress(i2);
            }
        } else {
            if (this.f24928y == null) {
                LogUtils.initializeLog("UteBleConnection.setWatchFaceOnlineListener");
                return;
            }
            LogUtils.i("onWatchFaceOnlineProgress progress =" + i2);
            this.f24928y.onWatchFaceOnlineProgress(i2);
        }
    }

    @Override // com.yc.utesdk.listener.WatchFaceOnlineListener
    public void onWatchFaceOnlineStatus(final int i2) {
        if (WatchFaceUtil.getInstance().getWatchFaceMode() != 0) {
            if (WatchFaceUtil.getInstance().getWatchFaceMode() == 1) {
                onWatchFaceCustomStatus(i2);
            }
        } else {
            if (this.f24928y == null) {
                LogUtils.initializeLog("UteBleConnection.setWatchFaceOnlineListener");
                return;
            }
            LogUtils.i("onWatchFaceOnlineStatus status =" + i2);
            if (this.isDelayCallBack) {
                this.f24901a.postDelayed(new Runnable() { // from class: l0.h
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f25823a.utentry(i2);
                    }
                }, this.delayTime);
            } else {
                this.f24928y.onWatchFaceOnlineStatus(i2);
            }
        }
    }

    @Override // com.yc.utesdk.listener.WatchFaceOnlineListener, com.yc.utesdk.listener.WatchFaceCustomListener
    public <T> void onWatchFaceParams(int i2, T t2) {
        String str;
        if (WatchFaceUtil.getInstance().getWatchFaceMode() == 0) {
            if (this.f24928y != null) {
                LogUtils.i("onWatchFaceParams Online status =" + i2);
                this.f24928y.onWatchFaceParams(i2, t2);
                return;
            }
            str = "UteBleConnection.setWatchFaceOnlineListener";
        } else {
            if (WatchFaceUtil.getInstance().getWatchFaceMode() != 1) {
                return;
            }
            if (this.f24929z != null) {
                LogUtils.i("onWatchFaceParams  Custom status =" + i2);
                this.f24929z.onWatchFaceParams(i2, t2);
                return;
            }
            str = "UteBleConnection.setWatchFaceCustomListener";
        }
        LogUtils.initializeLog(str);
    }

    @Override // com.yc.utesdk.listener.DeviceBatteryListener
    public void queryDeviceBatteryFail() {
        DeviceBatteryListener deviceBatteryListener = this.f24910g;
        if (deviceBatteryListener == null) {
            LogUtils.initializeLog("UteBleConnection.setDeviceBatteryListener");
        } else {
            deviceBatteryListener.queryDeviceBatteryFail();
        }
    }

    @Override // com.yc.utesdk.listener.DeviceBatteryListener
    public void queryDeviceBatterySuccess(final int i2) {
        DeviceBatteryListener deviceBatteryListener = this.f24910g;
        if (deviceBatteryListener == null) {
            LogUtils.initializeLog("UteBleConnection.setDeviceBatteryListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.d
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25784a.utenbyte(i2);
                }
            }, this.delayTime);
        } else {
            deviceBatteryListener.queryDeviceBatterySuccess(i2);
        }
    }

    @Override // com.yc.utesdk.listener.DeviceDspVersionListener
    public void queryDeviceDspVersionFail() {
        DeviceDspVersionListener deviceDspVersionListener = this.f24908e;
        if (deviceDspVersionListener == null) {
            LogUtils.initializeLog("UteBleConnection.setDeviceDspVersionListener");
        } else {
            deviceDspVersionListener.queryDeviceDspVersionFail();
        }
    }

    @Override // com.yc.utesdk.listener.DeviceDspVersionListener
    public void queryDeviceDspVersionSuccess(final String str) {
        DeviceDspVersionListener deviceDspVersionListener = this.f24908e;
        if (deviceDspVersionListener == null) {
            LogUtils.initializeLog("UteBleConnection.setDeviceDspVersionListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.u
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25957a.utenif(str);
                }
            }, this.delayTime);
        } else {
            deviceDspVersionListener.queryDeviceDspVersionSuccess(str);
        }
    }

    @Override // com.yc.utesdk.listener.DeviceUiVersionListener
    public void queryDeviceUiVersionFail() {
        DeviceUiVersionListener deviceUiVersionListener = this.f24907d;
        if (deviceUiVersionListener == null) {
            LogUtils.initializeLog("UteBleConnection.setDeviceUiVersionListener");
        } else {
            deviceUiVersionListener.queryDeviceUiVersionFail();
        }
    }

    @Override // com.yc.utesdk.listener.DeviceUiVersionListener
    public void queryDeviceUiVersionSuccess(final String str) {
        DeviceUiVersionListener deviceUiVersionListener = this.f24907d;
        if (deviceUiVersionListener == null) {
            LogUtils.initializeLog("UteBleConnection.setDeviceUiVersionListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.d0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25786a.utenfor(str);
                }
            }, this.delayTime);
        } else {
            deviceUiVersionListener.queryDeviceUiVersionSuccess(str);
        }
    }

    @Override // com.yc.utesdk.listener.DeviceFirmwareVersionListener
    public void queryFirmwareVersionFail() {
        DeviceFirmwareVersionListener deviceFirmwareVersionListener = this.f24905c;
        if (deviceFirmwareVersionListener == null) {
            LogUtils.initializeLog("UteBleConnection.setDeviceFirmwareVersionListener");
        } else {
            deviceFirmwareVersionListener.queryFirmwareVersionFail();
        }
    }

    @Override // com.yc.utesdk.listener.DeviceFirmwareVersionListener
    public void queryFirmwareVersionSuccess(final String str) {
        DeviceFirmwareVersionListener deviceFirmwareVersionListener = this.f24905c;
        if (deviceFirmwareVersionListener == null) {
            LogUtils.initializeLog("UteBleConnection.setDeviceFirmwareVersionListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.e2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25799a.utenint(str);
                }
            }, this.delayTime);
        } else {
            deviceFirmwareVersionListener.queryFirmwareVersionSuccess(str);
        }
    }

    @Override // com.yc.utesdk.listener.LocalWatchFaceListener
    public void queryLocalWatchFaceFail() {
        LocalWatchFaceListener localWatchFaceListener = this.L;
        if (localWatchFaceListener == null) {
            LogUtils.initializeLog("UteBleConnection.setLocalWatchFaceListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.t1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25953a.utennew();
                }
            }, this.delayTime);
        } else {
            localWatchFaceListener.queryLocalWatchFaceFail();
        }
    }

    @Override // com.yc.utesdk.listener.LocalWatchFaceListener
    public void queryLocalWatchFaceSuccess(final LocalWatchFaceInfo localWatchFaceInfo) {
        LocalWatchFaceListener localWatchFaceListener = this.L;
        if (localWatchFaceListener == null) {
            LogUtils.initializeLog("UteBleConnection.setLocalWatchFaceListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.k
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25851a.utendo(localWatchFaceInfo);
                }
            }, this.delayTime);
        } else {
            localWatchFaceListener.queryLocalWatchFaceSuccess(localWatchFaceInfo);
        }
    }

    public void setAIWatchListener(AIWatchListener aIWatchListener) {
        this.f24906c0 = aIWatchListener;
    }

    public void setBloodPressureChangeListener(BloodPressureChangeListener bloodPressureChangeListener) {
        if (bloodPressureChangeListener != null) {
            this.f24914k = bloodPressureChangeListener;
        }
    }

    public void setBloodSugarListener(BloodSugarListener bloodSugarListener) {
        this.U = bloodSugarListener;
    }

    public void setBluetoothDisconnectReminderListener(BluetoothDisconnectReminderListener bluetoothDisconnectReminderListener) {
        if (bluetoothDisconnectReminderListener != null) {
            this.M = bluetoothDisconnectReminderListener;
        }
    }

    public void setBodyFatChangeListener(BodyFatChangeListener bodyFatChangeListener) {
        if (bodyFatChangeListener != null) {
            this.f24917n = bodyFatChangeListener;
        }
    }

    public void setBreathingRateChangeListener(BreathingRateChangeListener breathingRateChangeListener) {
        if (breathingRateChangeListener != null) {
            this.f24919p = breathingRateChangeListener;
        }
    }

    public void setCallSmsAppRemindListener(CallSmsAppRemindListener callSmsAppRemindListener) {
        if (callSmsAppRemindListener != null) {
            this.f24920q = callSmsAppRemindListener;
        }
    }

    public void setChatGPTListener(ChatGPTListener chatGPTListener) {
        this.X = chatGPTListener;
    }

    public void setCommonInterfaceListener(CommonInterfaceListener commonInterfaceListener) {
        if (commonInterfaceListener != null) {
            this.f24925v = commonInterfaceListener;
        }
    }

    public void setConnectStateListener(BleConnectStateListener bleConnectStateListener) {
        if (bleConnectStateListener != null) {
            this.f24903b = bleConnectStateListener;
        }
    }

    public void setContactsSyncListener(ContactsSyncListener contactsSyncListener) {
        if (contactsSyncListener != null) {
            this.f24926w = contactsSyncListener;
        }
    }

    public void setContinuousPpgListener(ContinuousPpgListener continuousPpgListener) {
        this.W = continuousPpgListener;
    }

    public void setCsbpChangeListener(CsbpChangeListener csbpChangeListener) {
        if (csbpChangeListener != null) {
            this.J = csbpChangeListener;
        }
    }

    public void setDelayCallBack(boolean z2, int i2) {
        this.isDelayCallBack = z2;
        this.delayTime = i2;
    }

    public void setDevice4GModuleListener(Device4GModuleListener device4GModuleListener) {
        this.V = device4GModuleListener;
    }

    public void setDeviceAlarmListener(DeviceAlarmListener deviceAlarmListener) {
        if (deviceAlarmListener != null) {
            this.f24922s = deviceAlarmListener;
        }
    }

    public void setDeviceBatteryListener(DeviceBatteryListener deviceBatteryListener) {
        if (deviceBatteryListener != null) {
            this.f24910g = deviceBatteryListener;
        }
    }

    public void setDeviceBt3Listener(DeviceBt3Listener deviceBt3Listener) {
        if (deviceBt3Listener != null) {
            this.C = deviceBt3Listener;
        }
    }

    public void setDeviceCameraListener(DeviceCameraListener deviceCameraListener) {
        if (deviceCameraListener != null) {
            this.f24921r = deviceCameraListener;
        }
    }

    public void setDeviceDspVersionListener(DeviceDspVersionListener deviceDspVersionListener) {
        if (deviceDspVersionListener != null) {
            this.f24908e = deviceDspVersionListener;
        }
    }

    public void setDeviceFirmwareVersionListener(DeviceFirmwareVersionListener deviceFirmwareVersionListener) {
        if (deviceFirmwareVersionListener != null) {
            this.f24905c = deviceFirmwareVersionListener;
        }
    }

    public void setDeviceLabelAlarmListener(DeviceLabelAlarmListener deviceLabelAlarmListener) {
        if (deviceLabelAlarmListener != null) {
            this.G = deviceLabelAlarmListener;
        }
    }

    public void setDeviceLanguageListener(DeviceLanguageListener deviceLanguageListener) {
        if (deviceLanguageListener != null) {
            this.f24923t = deviceLanguageListener;
        }
    }

    public void setDeviceMusicListener(DeviceMusicListener deviceMusicListener) {
        if (deviceMusicListener != null) {
            this.f24927x = deviceMusicListener;
        }
    }

    public void setDeviceParamListener(DeviceParamListener deviceParamListener) {
        this.Z = deviceParamListener;
    }

    public void setDeviceQRCodeListener(DeviceQRCodeListener deviceQRCodeListener) {
        if (deviceQRCodeListener != null) {
            this.R = deviceQRCodeListener;
        }
    }

    public void setDeviceShortcutSwitchListener(DeviceShortcutSwitchListener deviceShortcutSwitchListener) {
        if (deviceShortcutSwitchListener != null) {
            this.f24924u = deviceShortcutSwitchListener;
        }
    }

    public void setDeviceUiVersionListener(DeviceUiVersionListener deviceUiVersionListener) {
        if (deviceUiVersionListener != null) {
            this.f24907d = deviceUiVersionListener;
        }
    }

    public void setDeviceWidgetListener(DeviceWidgetListener deviceWidgetListener) {
        if (deviceWidgetListener != null) {
            this.I = deviceWidgetListener;
        }
    }

    public void setEcgChangeListener(EcgChangeListener ecgChangeListener) {
        if (ecgChangeListener != null) {
            this.f24918o = ecgChangeListener;
        }
    }

    public void setElComplexListener(ElComplexListener elComplexListener) {
        if (elComplexListener != null) {
            this.P = elComplexListener;
        }
    }

    public void setElHRVListener(ElHRVListener elHRVListener) {
        if (elHRVListener != null) {
            this.O = elHRVListener;
        }
    }

    public void setElbpListener(ElbpListener elbpListener) {
        if (elbpListener != null) {
            this.K = elbpListener;
        }
    }

    public void setElbsListener(ElbsListener elbsListener) {
        if (elbsListener != null) {
            this.N = elbsListener;
        }
    }

    public void setGpsListener(GPSListener gPSListener) {
        this.Y = gPSListener;
    }

    public void setHeartRateChangeListener(HeartRateChangeListener heartRateChangeListener) {
        if (heartRateChangeListener != null) {
            this.f24913j = heartRateChangeListener;
        }
    }

    public void setLocalWatchFaceListener(LocalWatchFaceListener localWatchFaceListener) {
        if (localWatchFaceListener != null) {
            this.L = localWatchFaceListener;
        }
    }

    @Override // com.yc.utesdk.listener.LocalWatchFaceListener
    public void setLocalWatchFaceStatus(final int i2, final int i3) {
        LocalWatchFaceListener localWatchFaceListener = this.L;
        if (localWatchFaceListener == null) {
            LogUtils.initializeLog("UteBleConnection.setLocalWatchFaceListener");
        } else if (this.isDelayCallBack) {
            this.f24901a.postDelayed(new Runnable() { // from class: l0.z2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f26018a.utenif(i2, i3);
                }
            }, this.delayTime);
        } else {
            localWatchFaceListener.setLocalWatchFaceStatus(i2, i3);
        }
    }

    public void setMenstrualListener(MenstrualListener menstrualListener) {
        this.f24902a0 = menstrualListener;
    }

    public void setMessageReplyListener(MessageReplyListener messageReplyListener) {
        this.f24904b0 = messageReplyListener;
    }

    public void setMoodPressureListener(MoodPressureListener moodPressureListener) {
        if (moodPressureListener != null) {
            this.B = moodPressureListener;
        }
    }

    public void setMultiSportTargetListener(MultiSportTargetListener multiSportTargetListener) {
        if (multiSportTargetListener != null) {
            this.H = multiSportTargetListener;
        }
    }

    public void setMultiSportsListener(MultiSportsListener multiSportsListener) {
        if (multiSportsListener != null) {
            this.D = multiSportsListener;
        }
    }

    public void setOnDevicePrayerListener(DevicePrayerListener devicePrayerListener) {
        this.T = devicePrayerListener;
    }

    public void setOnGattCallbackListener(GattCallbackListener gattCallbackListener) {
        this.S = gattCallbackListener;
    }

    public void setOxygenChangeListener(OxygenChangeListener oxygenChangeListener) {
        if (oxygenChangeListener != null) {
            this.f24915l = oxygenChangeListener;
        }
    }

    public void setQuickReplySmsListener(QuickReplySmsListener quickReplySmsListener) {
        if (quickReplySmsListener != null) {
            this.F = quickReplySmsListener;
        }
    }

    public void setSimpleCallbackListener(SimpleCallbackListener simpleCallbackListener) {
        if (simpleCallbackListener != null) {
            this.f24909f = simpleCallbackListener;
        }
    }

    public void setSleepChangeListener(SleepChangeListener sleepChangeListener) {
        if (sleepChangeListener != null) {
            this.f24912i = sleepChangeListener;
        }
    }

    public void setSosContactsListener(SosContactsListener sosContactsListener) {
        if (sosContactsListener != null) {
            this.E = sosContactsListener;
        }
    }

    public void setStepChangeListener(StepChangeListener stepChangeListener) {
        if (stepChangeListener != null) {
            this.f24911h = stepChangeListener;
        }
    }

    public void setSuloseCommandListener(SuloseCommandListener suloseCommandListener) {
        if (suloseCommandListener != null) {
            this.Q = suloseCommandListener;
        }
    }

    public void setTemperatureChangeListener(TemperatureChangeListener temperatureChangeListener) {
        if (temperatureChangeListener != null) {
            this.f24916m = temperatureChangeListener;
        }
    }

    public void setTodayTargetListener(TodayTargetListener todayTargetListener) {
        if (todayTargetListener != null) {
            this.A = todayTargetListener;
        }
    }

    public void setWatchFaceCustomListener(WatchFaceCustomListener watchFaceCustomListener) {
        if (watchFaceCustomListener != null) {
            this.f24929z = watchFaceCustomListener;
        }
    }

    public void setWatchFaceOnlineListener(WatchFaceOnlineListener watchFaceOnlineListener) {
        if (watchFaceOnlineListener != null) {
            this.f24928y = watchFaceOnlineListener;
        }
    }

    public final /* synthetic */ void utenboolean(boolean z2, int i2) {
        this.A.onTodayTargetStatus(z2, i2);
    }

    public final /* synthetic */ void utenbreak(boolean z2, int i2) {
        this.I.onDeviceWidgetStatus(z2, i2);
    }

    public final /* synthetic */ void utenbyte(boolean z2, int i2) {
        this.J.onCsbpStatus(z2, i2);
    }

    public final /* synthetic */ void utencase(boolean z2, int i2) {
        this.V.onDevice4GModuleStatus(z2, i2);
    }

    public final /* synthetic */ void utencatch(boolean z2, int i2) {
        this.f24918o.onEcgStatus(z2, i2);
    }

    public final /* synthetic */ void utenchar(boolean z2, int i2) {
        this.f24922s.onDeviceAlarmStatus(z2, i2);
    }

    public final /* synthetic */ void utenclass(boolean z2, int i2) {
        this.P.onElComplexStatus(z2, i2);
    }

    public final /* synthetic */ void utenconst(boolean z2, int i2) {
        this.O.onElHRVStatus(z2, i2);
    }

    public final /* synthetic */ void utendo(int i2) {
        this.L.notifyCurrentWatchFaceIndex(i2);
    }

    public final /* synthetic */ void utendouble(boolean z2, int i2) {
        this.f24923t.onQueryCurrentLanguage(z2, i2);
    }

    public final /* synthetic */ void utenelse(boolean z2, int i2) {
        this.C.onDeviceBt3Switch(z2, i2);
    }

    public final /* synthetic */ void utenfinal(boolean z2, int i2) {
        this.K.onElbpStatus(z2, i2);
    }

    public final /* synthetic */ void utenfloat(boolean z2, int i2) {
        this.N.onElbsStatus(z2, i2);
    }

    public final /* synthetic */ void utenfor(boolean z2, int i2) {
        this.f24917n.onBodyFatStatus(z2, i2);
    }

    public final /* synthetic */ void utengoto(boolean z2, int i2) {
        this.f24921r.onDeviceCameraStatus(z2, i2);
    }

    public final /* synthetic */ void utenif(List list) {
        this.f24914k.onBloodPressureSyncSuccess(list);
    }

    public final /* synthetic */ void utenimport(boolean z2, int i2) {
        this.E.onQuerySosContactsCount(z2, i2);
    }

    public final /* synthetic */ void utenint(boolean z2, int i2) {
        this.f24919p.onBreathingRateStatus(z2, i2);
    }

    public final /* synthetic */ void utenlong(boolean z2, int i2) {
        this.G.onDeviceLabelAlarmStatus(z2, i2);
    }

    public final /* synthetic */ void utennative(boolean z2, int i2) {
        this.F.onQuickReplySmsStatus(z2, i2);
    }

    public final /* synthetic */ void utennew(boolean z2, int i2) {
        this.f24920q.onCallRemindStatus(z2, i2);
    }

    public final /* synthetic */ void utenpublic(boolean z2, int i2) {
        this.f24909f.onSimpleCallback(z2, i2);
    }

    public final /* synthetic */ void utenreturn(boolean z2, int i2) {
        this.f24920q.onSmsAppRemindStatus(z2, i2);
    }

    public final /* synthetic */ void utenshort(boolean z2, int i2) {
        this.f24913j.onHeartRateStatus(z2, i2);
    }

    public final /* synthetic */ void utenstatic(boolean z2, int i2) {
        this.E.onSosContactsStatus(z2, i2);
    }

    public final /* synthetic */ void utensuper(boolean z2, int i2) {
        this.B.onMoodPressureStatus(z2, i2);
    }

    public final /* synthetic */ void utenswitch(boolean z2, int i2) {
        this.D.onSportsLocation(z2, i2);
    }

    public final /* synthetic */ void utenthis(boolean z2, int i2) {
        this.f24923t.onDeviceLanguageStatus(z2, i2);
    }

    public final /* synthetic */ void utenthrow(boolean z2, int i2) {
        this.H.onMultiSportTargetStatus(z2, i2);
    }

    public final /* synthetic */ void utenthrows(boolean z2, int i2) {
        this.f24916m.onTemperatureStatus(z2, i2);
    }

    public final /* synthetic */ void utentry(boolean z2, int i2) {
        this.W.onContinuousPpgStatus(z2, i2);
    }

    public final /* synthetic */ void utenvoid(boolean z2, int i2) {
        this.f24927x.onDeviceMusicStatus(z2, i2);
    }

    public final /* synthetic */ void utenwhile(boolean z2, int i2) {
        this.f24915l.onOxygenStatus(z2, i2);
    }

    public final /* synthetic */ void utenbreak(List list) {
        this.G.onQueryDeviceLabelAlarmSuccess(list);
    }

    public final /* synthetic */ void utenbyte(List list) {
        this.K.onElbpMiddleDataSyncSuccess(list);
    }

    public final /* synthetic */ void utencase(List list) {
        this.K.onElbpMiddleRealTime(list);
    }

    public final /* synthetic */ void utencatch(List list) {
        this.f24912i.onSleepSyncSuccess(list);
    }

    public final /* synthetic */ void utenchar(List list) {
        this.K.onElbpPpgDataSyncSuccess(list);
    }

    public final /* synthetic */ void utenclass(List list) {
        this.A.onStandingTimeSyncSuccess(list);
    }

    public final /* synthetic */ void utenconst(List list) {
        this.f24911h.onStepSyncSuccess(list);
    }

    public final /* synthetic */ void utendo(List list) {
        this.A.onActivityTimeSyncSuccess(list);
    }

    public final /* synthetic */ void utenelse(List list) {
        this.f24913j.onHeartRateRestSyncSuccess(list);
    }

    public final /* synthetic */ void utenfinal(List list) {
        this.f24916m.onTemperatureSyncSuccess(list);
    }

    public final /* synthetic */ void utenfor(List list) {
        this.f24917n.onBodyFatSyncSuccess(list);
    }

    public final /* synthetic */ void utengoto(List list) {
        this.f24913j.onHeartRateSyncSuccess(list);
    }

    public final /* synthetic */ void utenif(boolean z2, int i2) {
        this.U.onBloodSugarStatus(z2, i2);
    }

    public final /* synthetic */ void utenint(List list) {
        this.f24919p.onBreathingRateSyncSuccess(list);
    }

    public final /* synthetic */ void utenlong(List list) {
        this.B.onMoodPressureSyncSuccess(list);
    }

    public final /* synthetic */ void utennew(List list) {
        this.f24912i.onCyweeSleepSyncSuccess(list);
    }

    public final /* synthetic */ void utenthis(List list) {
        this.D.onMultiSportsSyncSuccess(list);
    }

    public final /* synthetic */ void utentry(List list) {
        this.J.onDevicePmSuccess(list);
    }

    public final /* synthetic */ void utenvoid(List list) {
        this.f24915l.onOxygenSyncSuccess(list);
    }

    public final /* synthetic */ void utenbyte(int i2) {
        this.f24910g.queryDeviceBatterySuccess(i2);
    }

    public final /* synthetic */ void utendo(boolean z2, int i2) {
        this.f24914k.onBloodPressureStatus(z2, i2);
    }

    public final /* synthetic */ void utenfor(int i2) {
        this.f24926w.onContactsSyncStatus(i2);
    }

    public final /* synthetic */ void utenif(int i2) {
        this.f24925v.onCommonInterfaceSdkToBle(i2);
    }

    public final /* synthetic */ void utenint() {
        this.K.onElbpPpgDataSyncing();
    }

    public final /* synthetic */ void utennew(int i2) {
        this.f24929z.onWatchFaceCustomStatus(i2);
    }

    public final /* synthetic */ void utentry(int i2) {
        this.f24928y.onWatchFaceOnlineStatus(i2);
    }

    public final /* synthetic */ void utendo(boolean z2, List list) {
        this.U.onBloodSugarSampling(z2, list);
    }

    public final /* synthetic */ void utenfor() {
        this.K.onElbpPpgDataSyncFail();
    }

    public final /* synthetic */ void utenif(boolean z2, byte[] bArr) {
        this.f24924u.onDeviceShortcutSwitchStatus(z2, bArr);
    }

    public final /* synthetic */ void utenint(int i2) {
        this.E.onSosContactsSync(i2);
    }

    public final /* synthetic */ void utennew() {
        this.L.queryLocalWatchFaceFail();
    }

    public final /* synthetic */ void utendo(BodyInfo bodyInfo) {
        this.f24917n.onBodyFatRealTime(bodyInfo);
    }

    public final /* synthetic */ void utenfor(boolean z2, int i2, int i3) {
        this.A.onQueryTodayTarget(z2, i2, i3);
    }

    public final /* synthetic */ void utenif() {
        this.K.onElbpMiddleDataSyncing();
    }

    public final /* synthetic */ void utenint(String str) {
        this.f24905c.queryFirmwareVersionSuccess(str);
    }

    public final /* synthetic */ void utendo(BreatheInfo breatheInfo) {
        this.f24919p.onBreathingRateRealTime(breatheInfo);
    }

    public final /* synthetic */ void utenfor(boolean z2, List list) {
        this.J.onSyncHeartRateAndOxygenSuccess(z2, list);
    }

    public final /* synthetic */ void utenif(boolean z2, int i2, List list) {
        this.F.onQuerySmsContent(z2, i2, list);
    }

    public final /* synthetic */ void utendo(int i2, byte[] bArr) {
        this.f24925v.onCommonInterfaceBleToSdk(i2, bArr);
    }

    public final /* synthetic */ void utenfor(String str) {
        this.f24907d.queryDeviceUiVersionSuccess(str);
    }

    public final /* synthetic */ void utenif(boolean z2, List list) {
        this.E.onQuerySosContacts(z2, list);
    }

    public final /* synthetic */ void utendo(boolean z2, Device4GModuleInfo device4GModuleInfo) {
        this.V.onDevice4GModuleIMEI(z2, device4GModuleInfo);
    }

    public final /* synthetic */ void utenif(boolean z2, int i2, int i3) {
        this.D.onQuerySportsMode(z2, i2, i3);
    }

    public final /* synthetic */ void utendo(boolean z2, DeviceBt3StateInfo deviceBt3StateInfo) {
        this.C.onDeviceBt3State(z2, deviceBt3StateInfo);
    }

    public final /* synthetic */ void utenif(boolean z2) {
        this.D.onSetSportsModeList(z2);
    }

    public final /* synthetic */ void utendo(boolean z2, byte[] bArr) {
        this.f24924u.onDeviceShortcutSwitch(z2, bArr);
    }

    public final /* synthetic */ void utenif(String str) {
        this.f24908e.queryDeviceDspVersionSuccess(str);
    }

    public final /* synthetic */ void utendo(String str) {
        this.J.onDeviceSnSuccess(str);
    }

    public final /* synthetic */ void utenif(int i2, int i3) {
        this.L.setLocalWatchFaceStatus(i2, i3);
    }

    public final /* synthetic */ void utendo(EcgInfo ecgInfo) {
        this.f24918o.onEcgRealTime(ecgInfo);
    }

    public final /* synthetic */ void utendo(ArrayList arrayList) {
        this.f24918o.onEcgRealTimeData(arrayList);
    }

    public final /* synthetic */ void utendo(boolean z2, int i2, List list) {
        this.f24918o.onEcgSyncSuccess(z2, i2, list);
    }

    public final /* synthetic */ void utendo(boolean z2, String str) {
        this.K.onElbpAlgorithmVersion(z2, str);
    }

    public final /* synthetic */ void utendo(ElbpContinuousPpgDataInfo elbpContinuousPpgDataInfo) {
        this.W.onElbpContinuousPpgRealTime(elbpContinuousPpgDataInfo);
    }

    public final /* synthetic */ void utendo() {
        this.K.onElbpMiddleDataSyncFail();
    }

    public final /* synthetic */ void utendo(ElbpPpgDataInfo elbpPpgDataInfo) {
        this.K.onElbpPpgRealTime(elbpPpgDataInfo);
    }

    public final /* synthetic */ void utendo(HeartRateInfo heartRateInfo) {
        this.f24913j.onHeartRateRealTime(heartRateInfo);
    }

    public final /* synthetic */ void utendo(MoodPressureFatigueInfo moodPressureFatigueInfo) {
        this.B.onMoodPressureRealTime(moodPressureFatigueInfo);
    }

    public final /* synthetic */ void utendo(MoodSensorInterfaceInfo moodSensorInterfaceInfo) {
        this.B.onMoodPressureSensor(moodSensorInterfaceInfo);
    }

    public final /* synthetic */ void utendo(SportsRealDataInfo sportsRealDataInfo) {
        this.D.onMultiSportsRealData(sportsRealDataInfo);
    }

    public final /* synthetic */ void utendo(boolean z2, int i2, int i3) {
        this.D.onMultiSportsStatus(z2, i2, i3);
    }

    public final /* synthetic */ void utendo(OxygenInfo oxygenInfo) {
        this.f24915l.onOxygenRealTime(oxygenInfo);
    }

    public final /* synthetic */ void utendo(int i2, int i3, String str) {
        this.J.onQueryDeviceChipSuccess(i2, i3, str);
    }

    public final /* synthetic */ void utendo(int i2, String str) {
        this.J.onQueryDeviceModuleIdSuccess(i2, str);
    }

    public final /* synthetic */ void utendo(List list, List list2, List list3) {
        this.I.onQueryDeviceWidgetSuccess(list, list2, list3);
    }

    public final /* synthetic */ void utendo(boolean z2) {
        this.f24920q.onQueryRemindDisplay(z2);
    }

    public final /* synthetic */ void utendo(boolean z2, int i2, int i3, List list) {
        this.D.onQuerySportsModeList(z2, i2, i3, list);
    }

    public final /* synthetic */ void utendo(String str, String str2) {
        this.F.onQuickReplySmsContent(str, str2);
    }

    public final /* synthetic */ void utendo(int i2, int i3) {
        this.D.onSportStatusChange(i2, i3);
    }

    public final /* synthetic */ void utendo(TemperatureInfo temperatureInfo) {
        this.f24916m.onTemperatureRealTime(temperatureInfo);
    }

    public final /* synthetic */ void utendo(LocalWatchFaceInfo localWatchFaceInfo) {
        this.L.queryLocalWatchFaceSuccess(localWatchFaceInfo);
    }
}
