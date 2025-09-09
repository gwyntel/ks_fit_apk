package com.yc.utesdk.ble.close;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.yc.utesdk.ble.close.NotifyUtils;
import com.yc.utesdk.ble.open.DeviceMode;
import com.yc.utesdk.ble.open.UteBleClient;
import com.yc.utesdk.command.UUIDUtils;
import com.yc.utesdk.command.WriteCommandToBLE;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.GBUtils;
import com.yc.utesdk.utils.open.SPUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes4.dex */
public class NotifyUtils {
    private static volatile NotifyUtils instance;

    /* renamed from: a, reason: collision with root package name */
    BluetoothGatt f24885a;
    private final int SET_NOTIFY_NONE_COMMAND = -1;
    private final int SET_NOTIFY_1 = 1;
    private final int SET_NOTIFY_2 = 2;
    private final int SET_NOTIFY_3 = 3;
    private final int SET_NOTIFY_4 = 4;
    private final int SET_NOTIFY_5 = 5;
    private final int SET_NOTIFY_6 = 6;
    private final int SET_NOTIFY_7 = 7;
    private final int READ_CHARACTERISTIC_1 = 11;
    private final int READ_CHARACTERISTIC_2 = 12;
    private final int READ_CHARACTERISTIC_3 = 13;
    public final int REQUEST_MTU_1 = 21;
    private int requestMtuCount = 0;
    public List<Integer> notifyCommandIndex = new CopyOnWriteArrayList();
    private int commandType = 0;
    private long startConnectTimeMillis = 0;
    private boolean isConnectNotify = false;
    private int NOTIFY_TIME_OUT_COUNT = 0;
    private final int WRITE_NOTIFY_COMMAND_MSG = 1;
    private final Handler mHandler = new utendo(Looper.getMainLooper());
    private final Runnable mSyncTimeRunnable = new utenif();
    private boolean isUpgrade = false;
    private boolean isSetWatchFace = false;
    private boolean isNeedPairing = true;
    private DeviceBusyLockUtils mDeviceBusyLockUtils = DeviceBusyLockUtils.getInstance();
    private utenfor mNotifyCountDown = new utenfor(3000, 1000);

    public class utendo extends Handler {
        public utendo(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 1) {
                if (NotifyUtils.this.mDeviceBusyLockUtils.getDeviceBusy()) {
                    NotifyUtils.this.mHandler.sendEmptyMessageDelayed(1, 50L);
                    return;
                }
                try {
                    NotifyUtils.this.utenint();
                } catch (Exception e2) {
                    LogUtils.e("Notify Exception =" + e2);
                }
            }
        }
    }

    public class utenfor extends CountDownTimer {
        public utenfor(long j2, long j3) {
            super(j2, j3);
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            LogUtils.i("通知命令超时了 commandType=" + NotifyUtils.this.commandType);
            NotifyUtils notifyUtils = NotifyUtils.this;
            notifyUtils.utendo(notifyUtils.commandType);
        }

        @Override // android.os.CountDownTimer
        public void onTick(long j2) {
        }
    }

    public class utenif implements Runnable {
        public utenif() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (NotifyUtils.this.f24885a != null) {
                boolean zIsHasFunction_5 = DeviceMode.isHasFunction_5(4);
                LogUtils.i("是否配对 isSup =" + zIsHasFunction_5 + ",isNeed =" + NotifyUtils.this.isNeedPairing);
                if (zIsHasFunction_5 && NotifyUtils.this.isNeedPairing) {
                    LogUtils.i("支持密码配对，SimpleCallbackListener.BIND_CONNECT_SEND_ACCOUNT_ID");
                    UteListenerManager.getInstance().onSimpleCallback(true, 1);
                } else {
                    UteListenerManager.getInstance().onConnecteStateChange(2);
                }
            }
            NotifyUtils.this.mHandler.removeCallbacks(NotifyUtils.this.mSyncTimeRunnable);
        }
    }

    public static NotifyUtils getInstance() {
        if (instance == null) {
            synchronized (NotifyUtils.class) {
                try {
                    if (instance == null) {
                        if (Looper.myLooper() == Looper.getMainLooper()) {
                            instance = new NotifyUtils();
                        } else {
                            final CountDownLatch countDownLatch = new CountDownLatch(1);
                            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: j0.a
                                @Override // java.lang.Runnable
                                public final void run() {
                                    NotifyUtils.utendo(countDownLatch);
                                }
                            });
                            try {
                                countDownLatch.await();
                            } catch (InterruptedException e2) {
                                Thread.currentThread().interrupt();
                                LogUtils.e("初始化中断", e2);
                            }
                        }
                    }
                } finally {
                }
            }
        }
        return instance;
    }

    public synchronized void addNotifyCommandIndex(int i2) {
        ArrayList arrayList = new ArrayList(this.notifyCommandIndex);
        arrayList.add(Integer.valueOf(i2));
        this.notifyCommandIndex = arrayList;
    }

    public void displayGattServices(BluetoothGatt bluetoothGatt) {
        int i2;
        if (bluetoothGatt == null) {
            return;
        }
        this.f24885a = bluetoothGatt;
        List<BluetoothGattService> services = bluetoothGatt.getServices();
        if (services == null) {
            return;
        }
        resetNotifyProcess();
        Iterator<BluetoothGattService> it = services.iterator();
        while (it.hasNext()) {
            Iterator<BluetoothGattCharacteristic> it2 = it.next().getCharacteristics().iterator();
            while (it2.hasNext()) {
                UUID uuid = it2.next().getUuid();
                if (UUID.fromString(UUIDUtils.ONLY_READ_UUID).equals(uuid)) {
                    addNotifyCommandIndex(1);
                } else {
                    if (UUID.fromString(UUIDUtils.ONLY_READ_UUID_5).equals(uuid)) {
                        i2 = 2;
                    } else if (UUIDUtils.SPOTA_SERV_STATUS_UUID.equals(uuid)) {
                        i2 = 3;
                    } else if (!UUIDUtils.ONLY_READ_UUID_60.equals(uuid) && !UUIDUtils.ONLY_READ_UUID_61.equals(uuid)) {
                        if (UUIDUtils.ONLY_READ_UUID_IFLY.equals(uuid)) {
                            i2 = 6;
                        } else if (UUIDUtils.UUID_FRK_READ_DATA.equals(uuid)) {
                            i2 = 7;
                        } else if (UUID.fromString(UUIDUtils.ONLY_WRITE_UUID).equals(uuid)) {
                            i2 = 11;
                        } else if (UUID.fromString(UUIDUtils.ONLY_WRITE_UUID_5).equals(uuid)) {
                            i2 = 12;
                        }
                    }
                    addNotifyCommandIndex(i2);
                }
            }
        }
        this.startConnectTimeMillis = System.currentTimeMillis();
        getNotifyCommandIndex().add(-1);
        this.isConnectNotify = true;
        this.requestMtuCount = 0;
        sendMessageForNotifyCommand(false);
    }

    public synchronized List<Integer> getNotifyCommandIndex() {
        return this.notifyCommandIndex;
    }

    public boolean isConnectNotify() {
        return this.isConnectNotify;
    }

    public void isNeedPairing(boolean z2) {
        this.isNeedPairing = z2;
    }

    public void resetNotifyProcess() {
        this.mHandler.removeMessages(1);
        utendo();
        utenfor();
    }

    public void sendMessageForNotifyCommand(boolean z2) {
        this.mHandler.sendEmptyMessage(1);
    }

    public void setUpgrade(boolean z2) {
        this.isUpgrade = z2;
    }

    public void setWatchFace(boolean z2) {
        this.isSetWatchFace = z2;
    }

    public void writeChara(byte[] bArr) {
        BluetoothGatt bluetoothGatt = this.f24885a;
        if (bluetoothGatt == null) {
            return;
        }
        List<BluetoothGattService> services = bluetoothGatt.getServices();
        if (services == null) {
            LogUtils.i("disconnect() writeChara bluetoothGattServices =null");
            UteBleClient.getUteBleClient().disconnect();
            return;
        }
        boolean z2 = false;
        for (int i2 = 0; i2 < services.size(); i2++) {
            BluetoothGattService bluetoothGattService = services.get(i2);
            int size = bluetoothGattService.getCharacteristics().size();
            int i3 = 0;
            while (true) {
                if (i3 >= size) {
                    break;
                }
                BluetoothGattCharacteristic bluetoothGattCharacteristic = bluetoothGattService.getCharacteristics().get(i3);
                if (bluetoothGattCharacteristic.getUuid().equals(UUID.fromString(UUIDUtils.ONLY_WRITE_UUID))) {
                    boolean zIsDeviceBusyLockTime = this.mDeviceBusyLockUtils.isDeviceBusyLockTime(3000L);
                    LogUtils.i("APK--->BLE isBusy = " + zIsDeviceBusyLockTime);
                    if (!zIsDeviceBusyLockTime) {
                        bluetoothGattCharacteristic.setValue(bArr);
                        utenif(bluetoothGattCharacteristic);
                        if (bArr != null && bArr.length > 0) {
                            LogUtils.i("APK--->BLE4 = " + GBUtils.getInstance().bytes2HexString(bArr));
                        }
                    }
                    z2 = true;
                } else {
                    i3++;
                }
            }
            if (z2) {
                return;
            }
        }
    }

    public void writeCharaBle5(byte[] bArr) {
        StringBuilder sb;
        BluetoothGatt bluetoothGatt = this.f24885a;
        if (bluetoothGatt == null) {
            return;
        }
        List<BluetoothGattService> services = bluetoothGatt.getServices();
        if (services == null) {
            LogUtils.i("disconnect() writeChara5 bluetoothGattServices =null");
            UteBleClient.getUteBleClient().disconnect();
            return;
        }
        boolean z2 = false;
        for (int i2 = 0; i2 < services.size(); i2++) {
            BluetoothGattService bluetoothGattService = services.get(i2);
            int size = bluetoothGattService.getCharacteristics().size();
            int i3 = 0;
            while (true) {
                if (i3 >= size) {
                    break;
                }
                BluetoothGattCharacteristic bluetoothGattCharacteristic = bluetoothGattService.getCharacteristics().get(i3);
                if (bluetoothGattCharacteristic.getUuid().equals(UUID.fromString(UUIDUtils.ONLY_WRITE_UUID_5))) {
                    boolean zIsDeviceBusyLockTime = this.mDeviceBusyLockUtils.isDeviceBusyLockTime(3000L);
                    LogUtils.i("APK--->BLE isBusy5 = " + zIsDeviceBusyLockTime);
                    if (zIsDeviceBusyLockTime) {
                        LogUtils.i("APK--->BLE5 = 没发 NOsectionOnline =" + WriteCommandToBLE.getInstance().NOsectionOnline);
                        WriteCommandToBLE writeCommandToBLE = WriteCommandToBLE.getInstance();
                        writeCommandToBLE.NOsectionOnline = writeCommandToBLE.NOsectionOnline - 1;
                    } else {
                        bluetoothGattCharacteristic.setValue(bArr);
                        boolean zUtenif = utenif(bluetoothGattCharacteristic);
                        if (!zUtenif) {
                            try {
                                Thread.sleep(20L);
                                LogUtils.i("APK--->BLE 写不成功，延时 20ms 重新写 isWrite2 =" + utenif(bluetoothGattCharacteristic));
                            } catch (InterruptedException e2) {
                                e2.printStackTrace();
                            }
                        }
                        if (bArr != null && bArr.length > 0) {
                            if (bArr[0] == 39) {
                                sb = new StringBuilder(3);
                                for (int i4 = 0; i4 < 3; i4++) {
                                    sb.append(String.format("%02X", Byte.valueOf(bArr[i4])));
                                }
                            } else {
                                sb = new StringBuilder(bArr.length);
                                for (byte b2 : bArr) {
                                    sb.append(String.format("%02X", Byte.valueOf(b2)));
                                }
                            }
                            LogUtils.i("APK--->BLE5 = " + ((Object) sb) + " ," + zUtenif);
                        }
                    }
                    z2 = true;
                } else {
                    i3++;
                }
            }
            if (z2) {
                return;
            }
        }
    }

    public void writeCharaBleUUid(byte[] bArr, UUID uuid) {
        StringBuilder sb;
        BluetoothGatt bluetoothGatt = this.f24885a;
        if (bluetoothGatt == null) {
            return;
        }
        List<BluetoothGattService> services = bluetoothGatt.getServices();
        if (services == null) {
            LogUtils.i("disconnect() writeCharaBleUUid bluetoothGattServices =null");
            UteBleClient.getUteBleClient().disconnect();
            return;
        }
        boolean z2 = false;
        for (int i2 = 0; i2 < services.size(); i2++) {
            BluetoothGattService bluetoothGattService = services.get(i2);
            int size = bluetoothGattService.getCharacteristics().size();
            int i3 = 0;
            while (true) {
                if (i3 >= size) {
                    break;
                }
                BluetoothGattCharacteristic bluetoothGattCharacteristic = bluetoothGattService.getCharacteristics().get(i3);
                if (bluetoothGattCharacteristic.getUuid().equals(uuid)) {
                    boolean zIsDeviceBusyLockTime = this.mDeviceBusyLockUtils.isDeviceBusyLockTime(3000L);
                    LogUtils.i("APK--->BLE isBusy6 = " + zIsDeviceBusyLockTime);
                    if (zIsDeviceBusyLockTime) {
                        LogUtils.i("APK--->BLE6 = 没发 NOsectionOnline =" + WriteCommandToBLE.getInstance().NOsectionOnline);
                        WriteCommandToBLE writeCommandToBLE = WriteCommandToBLE.getInstance();
                        writeCommandToBLE.NOsectionOnline = writeCommandToBLE.NOsectionOnline - 1;
                    } else {
                        bluetoothGattCharacteristic.setValue(bArr);
                        boolean zUtenif = utenif(bluetoothGattCharacteristic);
                        if (!zUtenif) {
                            try {
                                Thread.sleep(20L);
                                LogUtils.i("APK--->BLE6 写不成功，延时 20ms 重新写 isWrite2 =" + utenif(bluetoothGattCharacteristic));
                            } catch (InterruptedException e2) {
                                e2.printStackTrace();
                            }
                        }
                        if (bArr != null && bArr.length > 0) {
                            if (bArr[0] == 39) {
                                sb = new StringBuilder(3);
                                for (int i4 = 0; i4 < 3; i4++) {
                                    sb.append(String.format("%02X", Byte.valueOf(bArr[i4])));
                                }
                            } else {
                                sb = new StringBuilder(bArr.length);
                                for (byte b2 : bArr) {
                                    sb.append(String.format("%02X", Byte.valueOf(b2)));
                                }
                            }
                            LogUtils.i("APK--->BLE6 = " + ((Object) sb) + " ," + zUtenif);
                        }
                    }
                    z2 = true;
                } else {
                    i3++;
                }
            }
            if (z2) {
                return;
            }
        }
    }

    public final synchronized void utenfor() {
        this.notifyCommandIndex = new ArrayList();
    }

    public final synchronized int utenif() {
        List<Integer> list = this.notifyCommandIndex;
        if (list == null || list.size() <= 0) {
            LogUtils.i("没有通知命令可以移除");
            return -1;
        }
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList(this.notifyCommandIndex);
        int iIntValue = ((Integer) copyOnWriteArrayList.remove(0)).intValue();
        this.notifyCommandIndex = copyOnWriteArrayList;
        LogUtils.i("移除的通知命令 = " + iIntValue + ",还剩 =" + this.notifyCommandIndex.size() + "个命令");
        return iIntValue;
    }

    public final synchronized void utenint() {
        int iIntValue;
        utendo();
        ArrayList arrayList = new ArrayList(getNotifyCommandIndex());
        if (arrayList.size() > 0) {
            if (arrayList.size() == 1) {
                int maxCommunicationLength = SPUtil.getInstance().getMaxCommunicationLength();
                LogUtils.i("判断MTU phoneMtu =" + maxCommunicationLength + ",requestMtuCount =" + this.requestMtuCount);
                int i2 = this.requestMtuCount;
                if (i2 == 0 || (maxCommunicationLength <= 20 && i2 < 5)) {
                    this.requestMtuCount = i2 + 1;
                    arrayList.add(0, 21);
                    this.notifyCommandIndex = arrayList;
                }
            }
            try {
                iIntValue = ((Integer) arrayList.get(0)).intValue();
            } catch (Exception e2) {
                LogUtils.i("正在执行的通知命令 Exception = " + e2);
                iIntValue = 0;
            }
            LogUtils.i("正在执行的通知命令 = " + iIntValue);
            utenint(iIntValue);
            utenif();
        } else {
            LogUtils.i("没有待执行的通知指令");
        }
        List<Integer> notifyCommandIndex = getNotifyCommandIndex();
        if (notifyCommandIndex != null && notifyCommandIndex.size() == 0) {
            this.mHandler.postDelayed(this.mSyncTimeRunnable, 0);
            long jCurrentTimeMillis = System.currentTimeMillis() - this.startConnectTimeMillis;
            this.isConnectNotify = false;
            LogUtils.i("设置通知读特征值完成 ：" + jCurrentTimeMillis);
        }
    }

    public final void utendo() {
        LogUtils.i("关闭通知命令 commandType=" + this.commandType);
        utenfor utenforVar = this.mNotifyCountDown;
        if (utenforVar != null) {
            utenforVar.cancel();
        }
    }

    public final void utenfor(int i2) {
        LogUtils.i("开启通知命令 commandType=" + i2);
        this.commandType = i2;
        utenfor utenforVar = this.mNotifyCountDown;
        if (utenforVar != null) {
            utenforVar.start();
        }
    }

    public final void utenif(int i2) {
        LogUtils.i("requestMtu = " + i2);
        BluetoothGatt bluetoothGatt = this.f24885a;
        LogUtils.w("requestMtu =" + i2 + ",result =" + (bluetoothGatt != null ? bluetoothGatt.requestMtu(i2) : false));
    }

    public final synchronized void utenint(int i2) {
        BluetoothGattService service;
        boolean zUtendo;
        StringBuilder sb;
        String str;
        BluetoothGattService service2;
        BluetoothGatt bluetoothGatt;
        BluetoothGattService service3;
        BluetoothGattService service4;
        BluetoothGattService service5;
        BluetoothGattService service6;
        BluetoothGattService service7;
        BluetoothGattService service8;
        BluetoothGattService service9;
        if (i2 != -1) {
            try {
                utenfor(i2);
            } finally {
            }
        }
        if (i2 != 21) {
            switch (i2) {
                case 1:
                    BluetoothGatt bluetoothGatt2 = this.f24885a;
                    if (bluetoothGatt2 != null && (service = bluetoothGatt2.getService(UUID.fromString(UUIDUtils.SIMPLE_SERVICE_UUID))) != null) {
                        zUtendo = utendo(this.f24885a, service.getCharacteristic(UUID.fromString(UUIDUtils.ONLY_READ_UUID)), true);
                        sb = new StringBuilder();
                        str = "notifySet1 = ";
                        sb.append(str);
                        sb.append(zUtendo);
                        LogUtils.i(sb.toString());
                        break;
                    }
                    break;
                case 2:
                    BluetoothGatt bluetoothGatt3 = this.f24885a;
                    if (bluetoothGatt3 != null && (service2 = bluetoothGatt3.getService(UUID.fromString(UUIDUtils.SIMPLE_SERVICE_UUID_5))) != null) {
                        zUtendo = utendo(this.f24885a, service2.getCharacteristic(UUID.fromString(UUIDUtils.ONLY_READ_UUID_5)), true);
                        sb = new StringBuilder();
                        str = "notifySet2 = ";
                        sb.append(str);
                        sb.append(zUtendo);
                        LogUtils.i(sb.toString());
                        break;
                    }
                    break;
                case 3:
                    BluetoothGatt bluetoothGatt4 = this.f24885a;
                    if (bluetoothGatt4 != null) {
                        Iterator<BluetoothGattService> it = bluetoothGatt4.getServices().iterator();
                        while (it.hasNext()) {
                            for (BluetoothGattCharacteristic bluetoothGattCharacteristic : it.next().getCharacteristics()) {
                                if (bluetoothGattCharacteristic.getUuid().equals(UUIDUtils.SPOTA_SERV_STATUS_UUID) && (bluetoothGatt = this.f24885a) != null) {
                                    LogUtils.i("notifySet3 = " + utendo(bluetoothGatt, bluetoothGattCharacteristic, true));
                                }
                            }
                        }
                        break;
                    }
                    break;
                case 4:
                    BluetoothGatt bluetoothGatt5 = this.f24885a;
                    if (bluetoothGatt5 != null && (service3 = bluetoothGatt5.getService(UUIDUtils.SIMPLE_SERVICE_UUID_60)) != null) {
                        zUtendo = utendo(this.f24885a, service3.getCharacteristic(UUIDUtils.ONLY_READ_UUID_60), true);
                        sb = new StringBuilder();
                        str = "notifySet4 = ";
                        sb.append(str);
                        sb.append(zUtendo);
                        LogUtils.i(sb.toString());
                        break;
                    }
                    break;
                case 5:
                    BluetoothGatt bluetoothGatt6 = this.f24885a;
                    if (bluetoothGatt6 != null && (service4 = bluetoothGatt6.getService(UUIDUtils.SIMPLE_SERVICE_UUID_61)) != null) {
                        zUtendo = utendo(this.f24885a, service4.getCharacteristic(UUIDUtils.ONLY_READ_UUID_61), true);
                        sb = new StringBuilder();
                        str = "notifySet5 = ";
                        sb.append(str);
                        sb.append(zUtendo);
                        LogUtils.i(sb.toString());
                        break;
                    }
                    break;
                case 6:
                    BluetoothGatt bluetoothGatt7 = this.f24885a;
                    if (bluetoothGatt7 != null && (service5 = bluetoothGatt7.getService(UUIDUtils.SERVICE_IFLY)) != null) {
                        zUtendo = utendo(this.f24885a, service5.getCharacteristic(UUIDUtils.ONLY_READ_UUID_IFLY), true);
                        sb = new StringBuilder();
                        str = "notifySet6 = ";
                        sb.append(str);
                        sb.append(zUtendo);
                        LogUtils.i(sb.toString());
                        break;
                    }
                    break;
                case 7:
                    BluetoothGatt bluetoothGatt8 = this.f24885a;
                    if (bluetoothGatt8 != null && (service6 = bluetoothGatt8.getService(UUIDUtils.UUID_FRK_SERVICE_DATA)) != null) {
                        zUtendo = utendo(this.f24885a, service6.getCharacteristic(UUIDUtils.UUID_FRK_READ_DATA), true);
                        sb = new StringBuilder();
                        str = "notifySet7 = ";
                        sb.append(str);
                        sb.append(zUtendo);
                        LogUtils.i(sb.toString());
                        break;
                    }
                    break;
                default:
                    switch (i2) {
                        case 11:
                            BluetoothGatt bluetoothGatt9 = this.f24885a;
                            if (bluetoothGatt9 != null && (service7 = bluetoothGatt9.getService(UUID.fromString(UUIDUtils.SIMPLE_SERVICE_UUID))) != null) {
                                zUtendo = utendo(service7.getCharacteristic(UUID.fromString(UUIDUtils.ONLY_WRITE_UUID)));
                                sb = new StringBuilder();
                                str = "notifyRead1 = ";
                                sb.append(str);
                                sb.append(zUtendo);
                                LogUtils.i(sb.toString());
                                break;
                            }
                            break;
                        case 12:
                            BluetoothGatt bluetoothGatt10 = this.f24885a;
                            if (bluetoothGatt10 != null && (service8 = bluetoothGatt10.getService(UUID.fromString(UUIDUtils.SIMPLE_SERVICE_UUID_5))) != null) {
                                zUtendo = utendo(service8.getCharacteristic(UUID.fromString(UUIDUtils.ONLY_WRITE_UUID_5)));
                                sb = new StringBuilder();
                                str = "notifyRead2 = ";
                                sb.append(str);
                                sb.append(zUtendo);
                                LogUtils.i(sb.toString());
                                break;
                            }
                            break;
                        case 13:
                            BluetoothGatt bluetoothGatt11 = this.f24885a;
                            if (bluetoothGatt11 != null && (service9 = bluetoothGatt11.getService(UUIDUtils.OTA_SERVICE_UUID)) != null) {
                                zUtendo = utendo(service9.getCharacteristic(UUIDUtils.OTA_READ_PATCH_CHARACTERISTIC_UUID));
                                sb = new StringBuilder();
                                str = "notifyRead3 = ";
                                sb.append(str);
                                sb.append(zUtendo);
                                LogUtils.i(sb.toString());
                                break;
                            }
                            break;
                    }
            }
        } else {
            utenif(247);
        }
    }

    public static /* synthetic */ void utendo(CountDownLatch countDownLatch) {
        if (instance == null) {
            instance = new NotifyUtils();
        }
        countDownLatch.countDown();
    }

    public final boolean utenif(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        boolean z2 = false;
        if (bluetoothGattCharacteristic != null && this.f24885a != null) {
            synchronized (this.mDeviceBusyLockUtils.getDeviceBusyLock()) {
                try {
                    if (this.mDeviceBusyLockUtils.getDeviceBusy()) {
                        LogUtils.w("----------->writeCharacteristic result =false");
                        return false;
                    }
                    this.mDeviceBusyLockUtils.setDeviceBusy(true);
                    boolean zWriteCharacteristic = this.f24885a.writeCharacteristic(bluetoothGattCharacteristic);
                    if (!zWriteCharacteristic) {
                        this.mDeviceBusyLockUtils.setDeviceBusy(false);
                    }
                    z2 = zWriteCharacteristic;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        LogUtils.w("----------->writeCharacteristic result =" + z2);
        return z2;
    }

    public final void utendo(int i2) {
        this.NOTIFY_TIME_OUT_COUNT++;
        LogUtils.i("通知指令 " + i2 + " 超时了 " + this.NOTIFY_TIME_OUT_COUNT + " 次");
        if (this.NOTIFY_TIME_OUT_COUNT == 1) {
            utenint(i2);
            return;
        }
        this.NOTIFY_TIME_OUT_COUNT = 0;
        LogUtils.i("notifyCommandType = " + i2);
        sendMessageForNotifyCommand(false);
    }

    public final boolean utendo(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        Exception e2;
        boolean characteristic;
        LogUtils.i("readCharacteristic---读特征值");
        if (bluetoothGattCharacteristic == null || this.f24885a == null || this.mDeviceBusyLockUtils.isDeviceBusyLockTime(3000L)) {
            return false;
        }
        try {
        } catch (Exception e3) {
            e2 = e3;
            characteristic = false;
        }
        synchronized (this.mDeviceBusyLockUtils.getDeviceBusyLock()) {
            try {
                if (this.mDeviceBusyLockUtils.getDeviceBusy()) {
                    LogUtils.i("readCharacteristic cc =false");
                    return false;
                }
                this.mDeviceBusyLockUtils.setDeviceBusy(true);
                characteristic = this.f24885a.readCharacteristic(bluetoothGattCharacteristic);
                if (!characteristic) {
                    try {
                        this.mDeviceBusyLockUtils.setDeviceBusy(false);
                    } catch (Exception e4) {
                        e2 = e4;
                        LogUtils.i("readCharacteristic Exception =" + e2);
                        UteListenerManager.getInstance().onConnectException(e2);
                        utendo();
                        UteBleClient.getUteBleClient().disconnect();
                        return characteristic;
                    }
                }
                return characteristic;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x01cf A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean utendo(android.bluetooth.BluetoothGatt r11, android.bluetooth.BluetoothGattCharacteristic r12, boolean r13) {
        /*
            Method dump skipped, instructions count: 479
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.ble.close.NotifyUtils.utendo(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, boolean):boolean");
    }
}
