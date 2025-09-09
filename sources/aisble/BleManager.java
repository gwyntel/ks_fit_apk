package aisble;

import aisble.BleManagerCallbacks;
import aisble.Request;
import aisble.callback.DataReceivedCallback;
import aisble.callback.SuccessCallback;
import aisble.data.Data;
import aisble.error.GattError;
import aisble.utils.ILogger;
import aisble.utils.ParserUtils;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v4.media.MediaDescriptionCompat;
import android.util.Log;
import androidx.annotation.IntRange;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.media3.exoplayer.DefaultLivePlaybackSpeedControl;
import androidx.media3.exoplayer.ExoPlayer;
import com.alibaba.ailabs.tg.utils.LogUtils;
import com.alipay.sdk.m.x.d;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

/* loaded from: classes.dex */
public abstract class BleManager<E extends BleManagerCallbacks> extends TimeoutHandler implements ILogger {
    public static final long CONNECTION_TIMEOUT_THRESHOLD = 20000;
    public static final int PAIRING_VARIANT_CONSENT = 3;
    public static final int PAIRING_VARIANT_DISPLAY_PASSKEY = 4;
    public static final int PAIRING_VARIANT_DISPLAY_PIN = 5;
    public static final int PAIRING_VARIANT_OOB_CONSENT = 6;
    public static final int PAIRING_VARIANT_PASSKEY = 1;
    public static final int PAIRING_VARIANT_PASSKEY_CONFIRMATION = 2;
    public static final int PAIRING_VARIANT_PIN = 0;
    public String TAG;

    @Deprecated
    public ValueChangedCallback mBatteryLevelNotificationCallback;

    @IntRange(from = -1, to = 100)
    @Deprecated
    public int mBatteryValue;
    public BluetoothDevice mBluetoothDevice;
    public BluetoothGatt mBluetoothGatt;
    public final BroadcastReceiver mBluetoothStateBroadcastReceiver;
    public BroadcastReceiver mBondingBroadcastReceiver;
    public E mCallbacks;
    public ConnectRequest mConnectRequest;
    public Runnable mConnectTimeoutTask;
    public boolean mConnected;
    public int mConnectionCount;
    public int mConnectionState;
    public long mConnectionTime;
    public final Context mContext;
    public boolean mDeviceNotSupported;
    public BleManager<E>.BleManagerGattCallback mGattCallback;
    public final Handler mHandler;
    public boolean mInitialConnection;
    public final Object mLock;
    public int mMtu;
    public final HashMap<BluetoothGattCharacteristic, ValueChangedCallback> mNotificationCallbacks;
    public final BroadcastReceiver mPairingRequestBroadcastReceiver;
    public boolean mReady;
    public boolean mReliableWriteInProgress;
    public Request mRequest;
    public RequestQueue mRequestQueue;
    public int mRetryDiscoveryServiceCount;
    public boolean mServiceDiscoveryRequested;
    public Runnable mServiceDiscoveryRunnable;
    public boolean mServicesDiscovered;
    public boolean mUserDisconnected;
    public WaitForValueChangedRequest mValueChangedRequest;
    public Runnable mWriteOpTimeoutRunable;
    public static final UUID CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static final UUID BATTERY_SERVICE = UUID.fromString("0000180F-0000-1000-8000-00805f9b34fb");
    public static final UUID BATTERY_LEVEL_CHARACTERISTIC = UUID.fromString("00002A19-0000-1000-8000-00805f9b34fb");
    public static final UUID GENERIC_ATTRIBUTE_SERVICE = UUID.fromString("00001801-0000-1000-8000-00805f9b34fb");
    public static final UUID SERVICE_CHANGED_CHARACTERISTIC = UUID.fromString("00002A05-0000-1000-8000-00805f9b34fb");

    /* renamed from: aisble.BleManager$10, reason: invalid class name */
    static /* synthetic */ class AnonymousClass10 {
        public static final /* synthetic */ int[] $SwitchMap$aisble$Request$Type;

        static {
            int[] iArr = new int[Request.Type.values().length];
            $SwitchMap$aisble$Request$Type = iArr;
            try {
                iArr[Request.Type.WAIT_FOR_INDICATION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.WAIT_FOR_NOTIFICATION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.CONNECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.DISCONNECT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.CREATE_BOND.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.REMOVE_BOND.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.SET.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.READ.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.WRITE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.READ_DESCRIPTOR.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.WRITE_DESCRIPTOR.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.BEGIN_RELIABLE_WRITE.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.EXECUTE_RELIABLE_WRITE.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.ABORT_RELIABLE_WRITE.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.ENABLE_NOTIFICATIONS.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.ENABLE_INDICATIONS.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.DISABLE_NOTIFICATIONS.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.DISABLE_INDICATIONS.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.READ_BATTERY_LEVEL.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.ENABLE_BATTERY_LEVEL_NOTIFICATIONS.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.DISABLE_BATTERY_LEVEL_NOTIFICATIONS.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.ENABLE_SERVICE_CHANGED_INDICATIONS.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.REQUEST_MTU.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.REQUEST_CONNECTION_PRIORITY.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.SET_PREFERRED_PHY.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.READ_PHY.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.READ_RSSI.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.REFRESH_CACHE.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$aisble$Request$Type[Request.Type.SLEEP.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract class BleManagerGattCallback extends MainThreadBluetoothGattCallback {
        public static final String ERROR_AUTH_ERROR_WHILE_BONDED = "Phone has lost bonding information";
        public static final String ERROR_CONNECTION_PRIORITY_REQUEST = "Error on connection priority request";
        public static final String ERROR_CONNECTION_STATE_CHANGE = "Error on connection state change";
        public static final String ERROR_DISCOVERY_SERVICE = "Error on discovering services";
        public static final String ERROR_MTU_REQUEST = "Error on mtu request";
        public static final String ERROR_PHY_UPDATE = "Error on PHY update";
        public static final String ERROR_READ_CHARACTERISTIC = "Error on reading characteristic";
        public static final String ERROR_READ_DESCRIPTOR = "Error on reading descriptor";
        public static final String ERROR_READ_PHY = "Error on PHY read";
        public static final String ERROR_READ_RSSI = "Error on RSSI read";
        public static final String ERROR_RELIABLE_WRITE = "Error on Execute Reliable Write";
        public static final String ERROR_WRITE_CHARACTERISTIC = "Error on writing characteristic";
        public static final String ERROR_WRITE_DESCRIPTOR = "Error on writing descriptor";
        public boolean mInitInProgress;
        public Deque<Request> mInitQueue;
        public boolean mOperationInProgress;
        public final Deque<Request> mTaskQueue = new LinkedList();
        public boolean mConnectionPriorityOperationInProgress = false;

        public BleManagerGattCallback() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void enqueue(@NonNull Request request) {
            (this.mInitInProgress ? this.mInitQueue : this.mTaskQueue).add(request);
            request.enqueued = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void enqueueFirst(@NonNull Request request) {
            (this.mInitInProgress ? this.mInitQueue : this.mTaskQueue).addFirst(request);
            request.enqueued = true;
        }

        @Deprecated
        private boolean isBatteryLevelCharacteristic(@Nullable BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            return bluetoothGattCharacteristic != null && BleManager.BATTERY_LEVEL_CHARACTERISTIC.equals(bluetoothGattCharacteristic.getUuid());
        }

        private boolean isCCCD(BluetoothGattDescriptor bluetoothGattDescriptor) {
            return bluetoothGattDescriptor != null && BleManager.CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID.equals(bluetoothGattDescriptor.getUuid());
        }

        private boolean isServiceChangedCCCD(@Nullable BluetoothGattDescriptor bluetoothGattDescriptor) {
            return bluetoothGattDescriptor != null && BleManager.SERVICE_CHANGED_CHARACTERISTIC.equals(bluetoothGattDescriptor.getCharacteristic().getUuid());
        }

        private boolean isServiceChangedCharacteristic(@Nullable BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            return bluetoothGattCharacteristic != null && BleManager.SERVICE_CHANGED_CHARACTERISTIC.equals(bluetoothGattCharacteristic.getUuid());
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:104:0x023e A[Catch: all -> 0x001d, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:112:0x0279 A[Catch: all -> 0x001d, TRY_ENTER, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:113:0x0281 A[Catch: all -> 0x001d, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:114:0x0289 A[Catch: all -> 0x001d, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:115:0x0291 A[Catch: all -> 0x001d, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:116:0x0299 A[Catch: all -> 0x001d, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:117:0x02a3 A[Catch: all -> 0x001d, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:118:0x02ad A[Catch: all -> 0x001d, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:119:0x02b7 A[Catch: all -> 0x001d, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:120:0x02c1 A[Catch: all -> 0x001d, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:121:0x02c9 A[Catch: all -> 0x001d, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:122:0x02d1 A[Catch: all -> 0x001d, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:127:0x02ed A[Catch: all -> 0x001d, TRY_ENTER, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:131:0x0307 A[Catch: all -> 0x001d, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:132:0x0311 A[Catch: all -> 0x001d, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:136:0x0343 A[Catch: all -> 0x001d, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:137:0x034d A[Catch: all -> 0x001d, TRY_LEAVE, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:140:0x0359 A[Catch: all -> 0x001d, TRY_ENTER, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:141:0x0361 A[Catch: all -> 0x001d, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:142:0x0369 A[Catch: all -> 0x001d, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:143:0x0371 A[Catch: all -> 0x001d, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:145:0x038b A[Catch: all -> 0x001d, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:29:0x0063 A[Catch: all -> 0x001d, Exception -> 0x006e, TryCatch #0 {Exception -> 0x006e, blocks: (B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:26:0x004c), top: B:161:0x0027 }] */
        /* JADX WARN: Removed duplicated region for block: B:67:0x0116 A[Catch: all -> 0x001d, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:68:0x0121 A[Catch: all -> 0x001d, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:73:0x013f A[DONT_GENERATE] */
        /* JADX WARN: Removed duplicated region for block: B:74:0x0141 A[Catch: all -> 0x001d, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:77:0x017e A[Catch: all -> 0x001d, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:80:0x019c A[Catch: all -> 0x001d, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:82:0x01a5 A[Catch: all -> 0x001d, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:90:0x01d1 A[Catch: all -> 0x001d, TRY_ENTER, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:98:0x0209 A[Catch: all -> 0x001d, TRY_ENTER, TryCatch #2 {all -> 0x001d, blocks: (B:3:0x0001, B:9:0x000f, B:13:0x001a, B:16:0x0020, B:21:0x0027, B:23:0x002f, B:25:0x003b, B:29:0x0063, B:31:0x0067, B:35:0x0071, B:37:0x0075, B:39:0x0088, B:40:0x00a0, B:42:0x00a9, B:45:0x00b5, B:65:0x0110, B:67:0x0116, B:71:0x0132, B:72:0x013c, B:145:0x038b, B:152:0x03af, B:148:0x03a1, B:74:0x0141, B:76:0x0149, B:77:0x017e, B:79:0x0186, B:80:0x019c, B:82:0x01a5, B:84:0x01ab, B:85:0x01b2, B:87:0x01ba, B:90:0x01d1, B:92:0x01d7, B:93:0x01ea, B:95:0x01f2, B:98:0x0209, B:100:0x020f, B:101:0x021c, B:103:0x0228, B:104:0x023e, B:106:0x024a, B:107:0x0256, B:109:0x025e, B:112:0x0279, B:113:0x0281, B:114:0x0289, B:115:0x0291, B:116:0x0299, B:117:0x02a3, B:118:0x02ad, B:119:0x02b7, B:120:0x02c1, B:121:0x02c9, B:122:0x02d1, B:124:0x02d9, B:127:0x02ed, B:129:0x02f4, B:130:0x02ff, B:131:0x0307, B:132:0x0311, B:134:0x0318, B:135:0x032a, B:136:0x0343, B:137:0x034d, B:140:0x0359, B:141:0x0361, B:142:0x0369, B:143:0x0371, B:68:0x0121, B:70:0x0129, B:155:0x03be, B:52:0x00d2, B:54:0x00dd, B:56:0x00e5, B:61:0x00f2, B:64:0x0107, B:26:0x004c), top: B:165:0x0001, inners: #1 }] */
        @androidx.annotation.MainThread
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public synchronized void nextRequest(boolean r9) {
            /*
                Method dump skipped, instructions count: 1032
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: aisble.BleManager.BleManagerGattCallback.nextRequest(boolean):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyDeviceDisconnected(@NonNull BluetoothDevice bluetoothDevice) {
            boolean z2 = BleManager.this.mConnected;
            BleManager.this.mConnected = false;
            BleManager bleManager = BleManager.this;
            bleManager.mServicesDiscovered = false;
            bleManager.mServiceDiscoveryRequested = false;
            this.mInitInProgress = false;
            BleManager.this.mConnectionState = 0;
            if (!z2) {
                BleManager.this.log(5, "Connection attempt timed out");
                BleManager.this.close();
                BleManager.this.mCallbacks.onDeviceDisconnected(bluetoothDevice);
            } else if (BleManager.this.mUserDisconnected) {
                BleManager.this.log(4, "Disconnected");
                BleManager.this.close();
                Request request = BleManager.this.mRequest;
                BleManager.this.mCallbacks.onDeviceDisconnected(bluetoothDevice);
                if (request != null && request.type == Request.Type.DISCONNECT) {
                    request.notifySuccess(bluetoothDevice);
                }
            } else {
                BleManager.this.log(5, "Connection lost");
                BleManager.this.mCallbacks.onLinkLossOccurred(bluetoothDevice);
            }
            if (BleManager.this.mUserDisconnected) {
                BleManager.this.mHandler.postDelayed(new Runnable() { // from class: aisble.BleManager.BleManagerGattCallback.1
                    @Override // java.lang.Runnable
                    public void run() {
                        BleManagerGattCallback.this.onDeviceDisconnected();
                    }
                }, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
            } else {
                onDeviceDisconnected();
            }
        }

        private void onError(BluetoothDevice bluetoothDevice, String str, int i2) {
            BleManager.this.log(6, "Error (0x" + Integer.toHexString(i2) + "): " + GattError.parse(i2));
            BleManager.this.mCallbacks.onError(bluetoothDevice, str, i2);
        }

        public void cancelQueue() {
            this.mTaskQueue.clear();
        }

        @Deprecated
        public Deque<Request> initGatt(@NonNull BluetoothGatt bluetoothGatt) {
            return null;
        }

        public void initialize() {
        }

        public boolean isOptionalServiceSupported(@NonNull BluetoothGatt bluetoothGatt) {
            return false;
        }

        public abstract boolean isRequiredServiceSupported(@NonNull BluetoothGatt bluetoothGatt);

        @Deprecated
        public void onBatteryValueReceived(@NonNull BluetoothGatt bluetoothGatt, @IntRange(from = 0, to = 100) int i2) {
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x004c  */
        @Override // aisble.MainThreadBluetoothGattCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onCharacteristicChangedSafe(@androidx.annotation.NonNull android.bluetooth.BluetoothGatt r9, @androidx.annotation.NonNull android.bluetooth.BluetoothGattCharacteristic r10, @androidx.annotation.Nullable byte[] r11) {
            /*
                Method dump skipped, instructions count: 269
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: aisble.BleManager.BleManagerGattCallback.onCharacteristicChangedSafe(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, byte[]):void");
        }

        @Deprecated
        public void onCharacteristicIndicated(@NonNull BluetoothGatt bluetoothGatt, @NonNull BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        }

        @Deprecated
        public void onCharacteristicNotified(@NonNull BluetoothGatt bluetoothGatt, @NonNull BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        }

        @Deprecated
        public void onCharacteristicRead(@NonNull BluetoothGatt bluetoothGatt, @NonNull BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        }

        @Override // aisble.MainThreadBluetoothGattCallback
        public final void onCharacteristicReadSafe(@NonNull BluetoothGatt bluetoothGatt, @NonNull BluetoothGattCharacteristic bluetoothGattCharacteristic, @Nullable byte[] bArr, int i2) {
            if (i2 == 0) {
                BleManager.this.log(4, "Read Response received from " + bluetoothGattCharacteristic.getUuid() + ", value: " + ParserUtils.parse(bArr));
                onCharacteristicRead(bluetoothGatt, bluetoothGattCharacteristic);
                if (BleManager.this.mRequest instanceof ReadRequest) {
                    ReadRequest readRequest = (ReadRequest) BleManager.this.mRequest;
                    boolean zMatches = readRequest.matches(bArr);
                    if (zMatches) {
                        readRequest.notifyValueChanged(bluetoothGatt.getDevice(), bArr);
                    }
                    if (!zMatches || readRequest.hasMore()) {
                        enqueueFirst(readRequest);
                    } else {
                        readRequest.notifySuccess(bluetoothGatt.getDevice());
                    }
                }
            } else {
                if (i2 == 5 || i2 == 8 || i2 == 137) {
                    BleManager.this.log(5, "Authentication required (" + i2 + ")");
                    if (bluetoothGatt.getDevice().getBondState() != 10) {
                        Log.w(BleManager.this.TAG, ERROR_AUTH_ERROR_WHILE_BONDED);
                        BleManager.this.mCallbacks.onError(bluetoothGatt.getDevice(), ERROR_AUTH_ERROR_WHILE_BONDED, i2);
                        return;
                    }
                    return;
                }
                Log.e(BleManager.this.TAG, "onCharacteristicRead error " + i2);
                if (BleManager.this.mRequest instanceof ReadRequest) {
                    BleManager.this.mRequest.notifyFail(bluetoothGatt.getDevice(), i2);
                }
                BleManager.this.mValueChangedRequest = null;
                onError(bluetoothGatt.getDevice(), ERROR_READ_CHARACTERISTIC, i2);
            }
            nextRequest(true);
        }

        @Deprecated
        public void onCharacteristicWrite(@NonNull BluetoothGatt bluetoothGatt, @NonNull BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        }

        @Override // aisble.MainThreadBluetoothGattCallback
        public final void onCharacteristicWriteSafe(@NonNull BluetoothGatt bluetoothGatt, @NonNull BluetoothGattCharacteristic bluetoothGattCharacteristic, @Nullable byte[] bArr, int i2) {
            if (BleManager.this.mRequest instanceof WriteRequest) {
                if (BleManager.this.mWriteOpTimeoutRunable != null) {
                    BleManager bleManager = BleManager.this;
                    bleManager.mHandler.removeCallbacks(bleManager.mWriteOpTimeoutRunable);
                }
                ((WriteRequest) BleManager.this.mRequest).onWriteFinished();
            }
            if (i2 == 0) {
                BleManager.this.log(4, "Data written to " + bluetoothGattCharacteristic.getUuid() + ", value: " + ParserUtils.parse(bArr));
                onCharacteristicWrite(bluetoothGatt, bluetoothGattCharacteristic);
                if (BleManager.this.mRequest instanceof WriteRequest) {
                    WriteRequest writeRequest = (WriteRequest) BleManager.this.mRequest;
                    if (!writeRequest.notifyPacketSent(bluetoothGatt.getDevice(), bArr) && (BleManager.this.mRequestQueue instanceof ReliableWriteRequest)) {
                        writeRequest.notifyFail(bluetoothGatt.getDevice(), -6);
                        BleManager.this.mRequestQueue.cancelQueue();
                    } else if (writeRequest.hasMore()) {
                        enqueueFirst(writeRequest);
                    } else {
                        writeRequest.notifySuccess(bluetoothGatt.getDevice());
                    }
                }
            } else {
                if (i2 == 5 || i2 == 8 || i2 == 137) {
                    BleManager.this.log(5, "Authentication required (" + i2 + ")");
                    if (bluetoothGatt.getDevice().getBondState() != 10) {
                        Log.w(BleManager.this.TAG, ERROR_AUTH_ERROR_WHILE_BONDED);
                        BleManager.this.mCallbacks.onError(bluetoothGatt.getDevice(), ERROR_AUTH_ERROR_WHILE_BONDED, i2);
                        return;
                    }
                    return;
                }
                Log.e(BleManager.this.TAG, "onCharacteristicWrite error " + i2);
                if (BleManager.this.mRequest instanceof WriteRequest) {
                    BleManager.this.mRequest.notifyFail(bluetoothGatt.getDevice(), i2);
                    if (BleManager.this.mRequestQueue instanceof ReliableWriteRequest) {
                        BleManager.this.mRequestQueue.cancelQueue();
                    }
                }
                BleManager.this.mValueChangedRequest = null;
                onError(bluetoothGatt.getDevice(), ERROR_WRITE_CHARACTERISTIC, i2);
            }
            nextRequest(true);
        }

        @Override // aisble.MainThreadBluetoothGattCallback
        public final void onConnectionStateChangeSafe(@NonNull final BluetoothGatt bluetoothGatt, int i2, int i3) {
            BleManager.this.log(3, "[Callback] Connection state changed with status: " + i2 + " and new state: " + i3 + " (" + BleManager.this.stateToString(i3) + ")");
            BleManager.this.cancelConnectTimeoutTask();
            if (i2 == 0 && i3 == 2) {
                if (BleManager.this.mBluetoothDevice == null) {
                    Log.e(BleManager.this.TAG, "Device received notification after disconnection.");
                    BleManager.this.log(3, "gatt.close()");
                    bluetoothGatt.close();
                    return;
                }
                BleManager.this.log(4, "Connected to " + bluetoothGatt.getDevice().getAddress());
                BleManager.this.mConnected = true;
                BleManager.this.mConnectionState = 2;
                BleManager.this.mCallbacks.onDeviceConnected(bluetoothGatt.getDevice());
                if (BleManager.this.mServiceDiscoveryRequested) {
                    return;
                }
                int serviceDiscoveryDelay = BleManager.this.getServiceDiscoveryDelay(bluetoothGatt.getDevice().getBondState() == 12);
                if (serviceDiscoveryDelay > 0) {
                    BleManager.this.log(3, "wait(" + serviceDiscoveryDelay + ")");
                }
                final int iAccess$1804 = BleManager.access$1804(BleManager.this);
                BleManager bleManager = BleManager.this;
                bleManager.mHandler.postDelayed(bleManager.mServiceDiscoveryRunnable = new Runnable() { // from class: aisble.BleManager.BleManagerGattCallback.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (iAccess$1804 == BleManager.this.mConnectionCount && BleManager.this.mConnected && bluetoothGatt.getDevice().getBondState() != 11) {
                            BleManager.this.mServiceDiscoveryRequested = true;
                            BleManager bleManager2 = BleManager.this;
                            bleManager2.mRetryDiscoveryServiceCount = 0;
                            bleManager2.log(2, "Discovering services...");
                            BleManager.this.log(3, "gatt.discoverServices()");
                            bluetoothGatt.discoverServices();
                        }
                    }
                }, serviceDiscoveryDelay);
                return;
            }
            if (i3 == 0) {
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                boolean z2 = BleManager.this.mConnectionTime > 0;
                boolean z3 = z2 && jElapsedRealtime > BleManager.this.mConnectionTime + 20000;
                BleManager.this.mConnectionState = 0;
                if (i2 != 0) {
                    BleManager.this.log(5, "Error: (0x" + Integer.toHexString(i2) + "): " + GattError.parseConnectionError(i2));
                }
                BleManager.this.log(3, "canTimeout: " + z2 + ", timeout: " + z3 + ", mConnectRequest: " + BleManager.this.mConnectRequest);
                BleManager bleManager2 = BleManager.this;
                bleManager2.mHandler.removeCallbacks(bleManager2.mServiceDiscoveryRunnable);
                BleManager bleManager3 = BleManager.this;
                if (!bleManager3.mDeviceNotSupported && z2 && !z3 && bleManager3.mConnectRequest != null && BleManager.this.mConnectRequest.canRetry()) {
                    int retryDelay = BleManager.this.mConnectRequest.getRetryDelay();
                    if (retryDelay > 0) {
                        BleManager.this.log(3, "wait(" + retryDelay + ")");
                    }
                    BleManager.this.mHandler.postDelayed(new Runnable() { // from class: aisble.BleManager.BleManagerGattCallback.3
                        @Override // java.lang.Runnable
                        public void run() {
                            BleManager bleManager4 = BleManager.this;
                            if (bleManager4.mBluetoothGatt == null) {
                                bluetoothGatt.close();
                                return;
                            }
                            try {
                                bleManager4.internalReconnect(bluetoothGatt.getDevice(), BleManager.this.mConnectRequest);
                            } catch (Exception e2) {
                                Log.e(BleManager.this.TAG, e2.toString());
                            }
                        }
                    }, retryDelay);
                    return;
                }
                this.mOperationInProgress = true;
                cancelQueue();
                this.mInitQueue = null;
                BleManager bleManager4 = BleManager.this;
                bleManager4.mReady = false;
                boolean z4 = bleManager4.mConnected;
                notifyDeviceDisconnected(bluetoothGatt.getDevice());
                int i4 = -1;
                if (BleManager.this.mRequest != null && BleManager.this.mRequest.type != Request.Type.DISCONNECT && BleManager.this.mRequest.type != Request.Type.REMOVE_BOND) {
                    BleManager.this.mRequest.notifyFail(bluetoothGatt.getDevice(), i2 == 0 ? -1 : i2);
                    BleManager.this.mRequest = null;
                }
                if (BleManager.this.mValueChangedRequest != null) {
                    BleManager.this.mValueChangedRequest.notifyFail(BleManager.this.mBluetoothDevice, -1);
                    BleManager.this.mValueChangedRequest = null;
                }
                if (BleManager.this.mConnectRequest != null) {
                    BleManager bleManager5 = BleManager.this;
                    if (bleManager5.mDeviceNotSupported) {
                        i4 = -2;
                    } else if (i2 != 0) {
                        i4 = (i2 == 133 && z3) ? -5 : i2;
                    }
                    bleManager5.mConnectRequest.notifyFail(bluetoothGatt.getDevice(), i4);
                    BleManager.this.mConnectRequest = null;
                }
                this.mOperationInProgress = false;
                if (z4 && BleManager.this.mInitialConnection) {
                    BleManager.this.internalConnect(bluetoothGatt.getDevice(), null);
                } else {
                    BleManager.this.mInitialConnection = false;
                    nextRequest(false);
                }
                if (z4 || i2 == 0) {
                    return;
                }
            } else if (i2 != 0) {
                BleManager.this.log(6, "Error (0x" + Integer.toHexString(i2) + "): " + GattError.parseConnectionError(i2));
            }
            BleManager.this.mCallbacks.onError(bluetoothGatt.getDevice(), ERROR_CONNECTION_STATE_CHANGE, i2);
        }

        @TargetApi(26)
        @Deprecated
        public void onConnectionUpdated(@NonNull BluetoothGatt bluetoothGatt, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_YEARS, to = 3200) int i2, @IntRange(from = 0, to = 499) int i3, @IntRange(from = 10, to = 3200) int i4) {
        }

        @Override // aisble.MainThreadBluetoothGattCallback
        @RequiresApi(api = 26)
        public final void onConnectionUpdatedSafe(@NonNull BluetoothGatt bluetoothGatt, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_YEARS, to = 3200) int i2, @IntRange(from = 0, to = 499) int i3, @IntRange(from = 10, to = 3200) int i4, int i5) {
            if (i5 == 0) {
                BleManager.this.log(4, "Connection parameters updated (interval: " + (i2 * 1.25d) + "ms, latency: " + i3 + ", timeout: " + (i4 * 10) + "ms)");
                onConnectionUpdated(bluetoothGatt, i2, i3, i4);
                if (BleManager.this.mRequest instanceof ConnectionPriorityRequest) {
                    ((ConnectionPriorityRequest) BleManager.this.mRequest).notifyConnectionPriorityChanged(bluetoothGatt.getDevice(), i2, i3, i4);
                    BleManager.this.mRequest.notifySuccess(bluetoothGatt.getDevice());
                }
            } else if (i5 == 59) {
                Log.e(BleManager.this.TAG, "onConnectionUpdated received status: Unacceptable connection interval, interval: " + i2 + ", latency: " + i3 + ", timeout: " + i4);
                BleManager.this.log(5, "Connection parameters update failed with status: UNACCEPT CONN INTERVAL (0x3b) (interval: " + (((double) i2) * 1.25d) + "ms, latency: " + i3 + ", timeout: " + (i4 * 10) + "ms)");
                if (BleManager.this.mRequest instanceof ConnectionPriorityRequest) {
                    BleManager.this.mRequest.notifyFail(bluetoothGatt.getDevice(), i5);
                    BleManager.this.mValueChangedRequest = null;
                }
            } else {
                Log.e(BleManager.this.TAG, "onConnectionUpdated received status: " + i5 + ", interval: " + i2 + ", latency: " + i3 + ", timeout: " + i4);
                BleManager.this.log(5, "Connection parameters update failed with status " + i5 + " (interval: " + (((double) i2) * 1.25d) + "ms, latency: " + i3 + ", timeout: " + (i4 * 10) + "ms)");
                if (BleManager.this.mRequest instanceof ConnectionPriorityRequest) {
                    BleManager.this.mRequest.notifyFail(bluetoothGatt.getDevice(), i5);
                    BleManager.this.mValueChangedRequest = null;
                }
            }
            if (this.mConnectionPriorityOperationInProgress) {
                this.mConnectionPriorityOperationInProgress = false;
                nextRequest(true);
            }
        }

        @Deprecated
        public void onDescriptorRead(@NonNull BluetoothGatt bluetoothGatt, @NonNull BluetoothGattDescriptor bluetoothGattDescriptor) {
        }

        @Override // aisble.MainThreadBluetoothGattCallback
        public void onDescriptorReadSafe(@NonNull BluetoothGatt bluetoothGatt, @NonNull BluetoothGattDescriptor bluetoothGattDescriptor, @Nullable byte[] bArr, int i2) {
            if (i2 == 0) {
                BleManager.this.log(4, "Read Response received from descr. " + bluetoothGattDescriptor.getUuid() + ", value: " + ParserUtils.parse(bArr));
                onDescriptorRead(bluetoothGatt, bluetoothGattDescriptor);
                if (BleManager.this.mRequest instanceof ReadRequest) {
                    ReadRequest readRequest = (ReadRequest) BleManager.this.mRequest;
                    readRequest.notifyValueChanged(bluetoothGatt.getDevice(), bArr);
                    if (readRequest.hasMore()) {
                        enqueueFirst(readRequest);
                    } else {
                        readRequest.notifySuccess(bluetoothGatt.getDevice());
                    }
                }
            } else {
                if (i2 == 5 || i2 == 8 || i2 == 137) {
                    BleManager.this.log(5, "Authentication required (" + i2 + ")");
                    if (bluetoothGatt.getDevice().getBondState() != 10) {
                        Log.w(BleManager.this.TAG, ERROR_AUTH_ERROR_WHILE_BONDED);
                        BleManager.this.mCallbacks.onError(bluetoothGatt.getDevice(), ERROR_AUTH_ERROR_WHILE_BONDED, i2);
                        return;
                    }
                    return;
                }
                Log.e(BleManager.this.TAG, "onDescriptorRead error " + i2);
                if (BleManager.this.mRequest instanceof ReadRequest) {
                    BleManager.this.mRequest.notifyFail(bluetoothGatt.getDevice(), i2);
                }
                BleManager.this.mValueChangedRequest = null;
                onError(bluetoothGatt.getDevice(), ERROR_READ_DESCRIPTOR, i2);
            }
            nextRequest(true);
        }

        @Deprecated
        public void onDescriptorWrite(@NonNull BluetoothGatt bluetoothGatt, @NonNull BluetoothGattDescriptor bluetoothGattDescriptor) {
        }

        @Override // aisble.MainThreadBluetoothGattCallback
        public final void onDescriptorWriteSafe(@NonNull BluetoothGatt bluetoothGatt, @NonNull BluetoothGattDescriptor bluetoothGattDescriptor, @Nullable byte[] bArr, int i2) {
            if (i2 == 0) {
                BleManager.this.log(4, "Data written to descr. " + bluetoothGattDescriptor.getUuid() + ", value: " + ParserUtils.parse(bArr));
                if (isServiceChangedCCCD(bluetoothGattDescriptor)) {
                    BleManager.this.log(4, "Service Changed notifications enabled");
                } else if (!isCCCD(bluetoothGattDescriptor)) {
                    onDescriptorWrite(bluetoothGatt, bluetoothGattDescriptor);
                } else if (bArr != null && bArr.length == 2 && bArr[1] == 0) {
                    byte b2 = bArr[0];
                    if (b2 == 0) {
                        BleManager.this.mNotificationCallbacks.remove(bluetoothGattDescriptor.getCharacteristic());
                        BleManager.this.log(4, "Notifications and indications disabled");
                    } else if (b2 == 1) {
                        BleManager.this.log(4, "Notifications enabled");
                    } else if (b2 == 2) {
                        BleManager.this.log(4, "Indications enabled");
                    }
                    onDescriptorWrite(bluetoothGatt, bluetoothGattDescriptor);
                }
                if (BleManager.this.mRequest instanceof WriteRequest) {
                    WriteRequest writeRequest = (WriteRequest) BleManager.this.mRequest;
                    if (!writeRequest.notifyPacketSent(bluetoothGatt.getDevice(), bArr) && (BleManager.this.mRequestQueue instanceof ReliableWriteRequest)) {
                        writeRequest.notifyFail(bluetoothGatt.getDevice(), -6);
                        BleManager.this.mRequestQueue.cancelQueue();
                    } else if (writeRequest.hasMore()) {
                        enqueueFirst(writeRequest);
                    } else {
                        writeRequest.notifySuccess(bluetoothGatt.getDevice());
                    }
                }
            } else {
                if (i2 == 5 || i2 == 8 || i2 == 137) {
                    BleManager.this.log(5, "Authentication required (" + i2 + ")");
                    if (bluetoothGatt.getDevice().getBondState() != 10) {
                        Log.w(BleManager.this.TAG, ERROR_AUTH_ERROR_WHILE_BONDED);
                        BleManager.this.mCallbacks.onError(bluetoothGatt.getDevice(), ERROR_AUTH_ERROR_WHILE_BONDED, i2);
                        return;
                    }
                    return;
                }
                Log.e(BleManager.this.TAG, "onDescriptorWrite error " + i2);
                if (BleManager.this.mRequest instanceof WriteRequest) {
                    BleManager.this.mRequest.notifyFail(bluetoothGatt.getDevice(), i2);
                    if (BleManager.this.mRequestQueue instanceof ReliableWriteRequest) {
                        BleManager.this.mRequestQueue.cancelQueue();
                    }
                }
                BleManager.this.mValueChangedRequest = null;
                onError(bluetoothGatt.getDevice(), ERROR_WRITE_DESCRIPTOR, i2);
            }
            nextRequest(true);
        }

        public abstract void onDeviceDisconnected();

        public void onDeviceReady() {
            BleManager bleManager = BleManager.this;
            BluetoothGatt bluetoothGatt = bleManager.mBluetoothGatt;
            if (bluetoothGatt == null) {
                return;
            }
            bleManager.mCallbacks.onDeviceReady(bluetoothGatt.getDevice());
        }

        public void onManagerReady() {
        }

        @Deprecated
        public void onMtuChanged(@NonNull BluetoothGatt bluetoothGatt, @IntRange(from = 23, to = 517) int i2) {
        }

        @Override // aisble.MainThreadBluetoothGattCallback
        @RequiresApi(api = 21)
        public final void onMtuChangedSafe(@NonNull BluetoothGatt bluetoothGatt, @IntRange(from = 23, to = 517) int i2, int i3) {
            if (i3 == 0) {
                BleManager.this.log(4, "MTU changed to: " + i2);
                BleManager.this.mMtu = i2;
                onMtuChanged(bluetoothGatt, i2);
                if (BleManager.this.mRequest instanceof MtuRequest) {
                    ((MtuRequest) BleManager.this.mRequest).notifyMtuChanged(bluetoothGatt.getDevice(), i2);
                    BleManager.this.mRequest.notifySuccess(bluetoothGatt.getDevice());
                }
            } else {
                Log.e(BleManager.this.TAG, "onMtuChanged error: " + i3 + ", mtu: " + i2);
                if (BleManager.this.mRequest instanceof MtuRequest) {
                    BleManager.this.mRequest.notifyFail(bluetoothGatt.getDevice(), i3);
                    BleManager.this.mValueChangedRequest = null;
                }
            }
            nextRequest(true);
        }

        @Override // aisble.MainThreadBluetoothGattCallback
        @RequiresApi(api = 26)
        public final void onPhyReadSafe(@NonNull BluetoothGatt bluetoothGatt, int i2, int i3, int i4) {
            if (i4 == 0) {
                BleManager.this.log(4, "PHY read (TX: " + BleManager.this.phyToString(i2) + ", RX: " + BleManager.this.phyToString(i3) + ")");
                if (BleManager.this.mRequest instanceof PhyRequest) {
                    ((PhyRequest) BleManager.this.mRequest).notifyPhyChanged(bluetoothGatt.getDevice(), i2, i3);
                    BleManager.this.mRequest.notifySuccess(bluetoothGatt.getDevice());
                }
            } else {
                BleManager.this.log(5, "PHY read failed with status " + i4);
                if (BleManager.this.mRequest instanceof PhyRequest) {
                    BleManager.this.mRequest.notifyFail(bluetoothGatt.getDevice(), i4);
                }
                BleManager.this.mValueChangedRequest = null;
                BleManager.this.mCallbacks.onError(bluetoothGatt.getDevice(), ERROR_READ_PHY, i4);
            }
            nextRequest(true);
        }

        @Override // aisble.MainThreadBluetoothGattCallback
        @RequiresApi(api = 26)
        public final void onPhyUpdateSafe(@NonNull BluetoothGatt bluetoothGatt, int i2, int i3, int i4) {
            if (i4 == 0) {
                BleManager.this.log(4, "PHY updated (TX: " + BleManager.this.phyToString(i2) + ", RX: " + BleManager.this.phyToString(i3) + ")");
                if (BleManager.this.mRequest instanceof PhyRequest) {
                    ((PhyRequest) BleManager.this.mRequest).notifyPhyChanged(bluetoothGatt.getDevice(), i2, i3);
                    BleManager.this.mRequest.notifySuccess(bluetoothGatt.getDevice());
                }
            } else {
                BleManager.this.log(5, "PHY updated failed with status " + i4);
                if (BleManager.this.mRequest instanceof PhyRequest) {
                    BleManager.this.mRequest.notifyFail(bluetoothGatt.getDevice(), i4);
                    BleManager.this.mValueChangedRequest = null;
                }
                BleManager.this.mCallbacks.onError(bluetoothGatt.getDevice(), ERROR_PHY_UPDATE, i4);
            }
            if (BleManager.this.mRequest instanceof PhyRequest) {
                nextRequest(true);
            }
        }

        @Override // aisble.MainThreadBluetoothGattCallback
        public final void onReadRemoteRssiSafe(@NonNull BluetoothGatt bluetoothGatt, @IntRange(from = -128, to = DefaultLivePlaybackSpeedControl.DEFAULT_MAX_LIVE_OFFSET_ERROR_MS_FOR_UNIT_SPEED) int i2, int i3) {
            if (i3 == 0) {
                BleManager.this.log(4, "Remote RSSI received: " + i2 + " dBm");
                if (BleManager.this.mRequest instanceof ReadRssiRequest) {
                    ((ReadRssiRequest) BleManager.this.mRequest).notifyRssiRead(bluetoothGatt.getDevice(), i2);
                    BleManager.this.mRequest.notifySuccess(bluetoothGatt.getDevice());
                }
            } else {
                BleManager.this.log(5, "Reading remote RSSI failed with status " + i3);
                if (BleManager.this.mRequest instanceof ReadRssiRequest) {
                    BleManager.this.mRequest.notifyFail(bluetoothGatt.getDevice(), i3);
                }
                BleManager.this.mValueChangedRequest = null;
                BleManager.this.mCallbacks.onError(bluetoothGatt.getDevice(), ERROR_READ_RSSI, i3);
            }
            nextRequest(true);
        }

        @Override // aisble.MainThreadBluetoothGattCallback
        public final void onReliableWriteCompletedSafe(@NonNull BluetoothGatt bluetoothGatt, int i2) {
            boolean z2 = BleManager.this.mRequest.type == Request.Type.EXECUTE_RELIABLE_WRITE;
            BleManager.this.mReliableWriteInProgress = false;
            if (i2 != 0) {
                Log.e(BleManager.this.TAG, "onReliableWriteCompleted execute " + z2 + ", error " + i2);
                BleManager.this.mRequest.notifyFail(bluetoothGatt.getDevice(), i2);
                onError(bluetoothGatt.getDevice(), ERROR_RELIABLE_WRITE, i2);
            } else if (z2) {
                BleManager.this.log(4, "Reliable Write executed");
                BleManager.this.mRequest.notifySuccess(bluetoothGatt.getDevice());
            } else {
                BleManager.this.log(5, "Reliable Write aborted");
                BleManager.this.mRequest.notifySuccess(bluetoothGatt.getDevice());
                BleManager.this.mRequestQueue.notifyFail(bluetoothGatt.getDevice(), -4);
            }
            nextRequest(true);
        }

        @Override // aisble.MainThreadBluetoothGattCallback
        public final void onServicesDiscoveredSafe(@NonNull final BluetoothGatt bluetoothGatt, int i2) {
            BleManager.this.mServiceDiscoveryRequested = false;
            if (i2 != 0) {
                Log.e(BleManager.this.TAG, "onServicesDiscovered error " + i2);
                onError(bluetoothGatt.getDevice(), ERROR_DISCOVERY_SERVICE, i2);
                if (BleManager.this.mConnectRequest != null) {
                    BleManager.this.mConnectRequest.notifyFail(bluetoothGatt.getDevice(), -4);
                    BleManager.this.mConnectRequest = null;
                }
                BleManager.this.internalDisconnect();
                return;
            }
            BleManager.this.log(4, "Services discovered");
            BleManager.this.mServicesDiscovered = true;
            if (!isRequiredServiceSupported(bluetoothGatt)) {
                BleManager.this.log(5, "Device is not supported");
                BleManager bleManager = BleManager.this;
                int i3 = bleManager.mRetryDiscoveryServiceCount + 1;
                bleManager.mRetryDiscoveryServiceCount = i3;
                if (!bleManager.shouldRetryDiscoveryServiceWhenDeviceNotSupported(i3)) {
                    BleManager bleManager2 = BleManager.this;
                    bleManager2.mDeviceNotSupported = true;
                    bleManager2.internalDisconnect();
                    return;
                }
                int serviceDiscoveryDelay = BleManager.this.getServiceDiscoveryDelay(bluetoothGatt.getDevice().getBondState() == 12);
                if (serviceDiscoveryDelay > 0) {
                    BleManager.this.log(3, "wait(" + serviceDiscoveryDelay + ") try again");
                }
                BleManager.this.mHandler.postDelayed(new Runnable() { // from class: aisble.BleManager.BleManagerGattCallback.4
                    @Override // java.lang.Runnable
                    public void run() {
                        if (!BleManager.this.mConnected || bluetoothGatt.getDevice().getBondState() == 11) {
                            return;
                        }
                        BleManager.this.mServiceDiscoveryRequested = true;
                        BleManager.this.log(2, "Retry discovering services...");
                        BleManager.this.log(3, "gatt.discoverServices()");
                        BleManager.this.internalRefreshDeviceCache();
                        bluetoothGatt.discoverServices();
                    }
                }, serviceDiscoveryDelay);
                return;
            }
            BleManager.this.log(2, "Primary service found");
            boolean zIsOptionalServiceSupported = isOptionalServiceSupported(bluetoothGatt);
            if (zIsOptionalServiceSupported) {
                BleManager.this.log(2, "Secondary service found");
            }
            BleManager.this.mCallbacks.onServicesDiscovered(bluetoothGatt.getDevice(), zIsOptionalServiceSupported);
            this.mInitInProgress = true;
            this.mOperationInProgress = true;
            Deque<Request> dequeInitGatt = initGatt(bluetoothGatt);
            this.mInitQueue = dequeInitGatt;
            boolean z2 = dequeInitGatt != null;
            if (z2) {
                Iterator<Request> it = dequeInitGatt.iterator();
                while (it.hasNext()) {
                    it.next().enqueued = true;
                }
            }
            if (this.mInitQueue == null) {
                this.mInitQueue = new LinkedList();
            }
            int i4 = Build.VERSION.SDK_INT;
            if (i4 == 26 || i4 == 27 || i4 == 28) {
                enqueueFirst(Request.newEnableServiceChangedIndicationsRequest().setManager(BleManager.this));
            }
            if (z2) {
                BleManager.this.readBatteryLevel();
                if (BleManager.this.mCallbacks.shouldEnableBatteryLevelNotifications(bluetoothGatt.getDevice())) {
                    BleManager.this.enableBatteryLevelNotifications();
                }
            }
            initialize();
            this.mInitInProgress = false;
            nextRequest(true);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PairingVariant {
    }

    public BleManager(@NonNull Context context, @NonNull String str) {
        this(context);
        this.TAG += str;
    }

    public static /* synthetic */ int access$1804(BleManager bleManager) {
        int i2 = bleManager.mConnectionCount + 1;
        bleManager.mConnectionCount = i2;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelConnectTimeoutTask() {
        Runnable runnable = this.mConnectTimeoutTask;
        if (runnable != null) {
            this.mHandler.removeCallbacks(runnable);
            this.mConnectTimeoutTask = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean ensureServiceChangedEnabled() {
        BluetoothGattService service;
        BluetoothGattCharacteristic characteristic;
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt == null || !this.mConnected || bluetoothGatt.getDevice().getBondState() != 12 || (service = bluetoothGatt.getService(GENERIC_ATTRIBUTE_SERVICE)) == null || (characteristic = service.getCharacteristic(SERVICE_CHANGED_CHARACTERISTIC)) == null) {
            return false;
        }
        log(4, "Service Changed characteristic found on a bonded device");
        return internalEnableIndications(characteristic);
    }

    public static BluetoothGattDescriptor getCccd(@Nullable BluetoothGattCharacteristic bluetoothGattCharacteristic, int i2) {
        if (bluetoothGattCharacteristic == null || (i2 & bluetoothGattCharacteristic.getProperties()) == 0) {
            return null;
        }
        return bluetoothGattCharacteristic.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID);
    }

    private void installConnectTimeoutTask(long j2) {
        Handler handler = this.mHandler;
        Runnable runnable = new Runnable() { // from class: aisble.BleManager.9
            @Override // java.lang.Runnable
            public void run() {
                if (BleManager.this.mConnected) {
                    return;
                }
                BleManager.this.close();
            }
        };
        this.mConnectTimeoutTask = runnable;
        handler.postDelayed(runnable, j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    public boolean internalAbortReliableWrite() {
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt == null || !this.mConnected || !this.mReliableWriteInProgress) {
            return false;
        }
        log(2, "Aborting reliable write...");
        log(3, "gatt.abortReliableWrite()");
        bluetoothGatt.abortReliableWrite();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    public boolean internalBeginReliableWrite() {
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt == null || !this.mConnected) {
            return false;
        }
        if (this.mReliableWriteInProgress) {
            return true;
        }
        log(2, "Beginning reliable write...");
        log(3, "gatt.beginReliableWrite()");
        boolean zBeginReliableWrite = bluetoothGatt.beginReliableWrite();
        this.mReliableWriteInProgress = zBeginReliableWrite;
        return zBeginReliableWrite;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    public boolean internalConnect(@NonNull BluetoothDevice bluetoothDevice, @Nullable ConnectRequest connectRequest) {
        this.mDeviceNotSupported = false;
        this.mServiceDiscoveryRequested = false;
        boolean zIsEnabled = BluetoothAdapter.getDefaultAdapter().isEnabled();
        if (this.mConnected || !zIsEnabled) {
            BluetoothDevice bluetoothDevice2 = this.mBluetoothDevice;
            if (zIsEnabled && bluetoothDevice2 != null && bluetoothDevice2.equals(bluetoothDevice)) {
                this.mConnectRequest.notifySuccess(bluetoothDevice);
            } else {
                ConnectRequest connectRequest2 = this.mConnectRequest;
                if (connectRequest2 != null) {
                    connectRequest2.notifyFail(bluetoothDevice, zIsEnabled ? -4 : -100);
                }
            }
            this.mConnectRequest = null;
            BleManager<E>.BleManagerGattCallback bleManagerGattCallback = this.mGattCallback;
            if (bleManagerGattCallback != null) {
                bleManagerGattCallback.nextRequest(true);
            }
            return true;
        }
        synchronized (this.mLock) {
            if (this.mBluetoothGatt == null) {
                this.mContext.registerReceiver(this.mBluetoothStateBroadcastReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
                this.mContext.registerReceiver(this.mBondingBroadcastReceiver, new IntentFilter("android.bluetooth.device.action.BOND_STATE_CHANGED"));
                this.mContext.registerReceiver(this.mPairingRequestBroadcastReceiver, new IntentFilter("android.bluetooth.device.action.PAIRING_REQUEST"));
            } else {
                if (this.mInitialConnection) {
                    this.mInitialConnection = false;
                    this.mConnectionTime = 0L;
                    this.mConnectionState = 1;
                    log(2, "Connecting...");
                    this.mCallbacks.onDeviceConnecting(bluetoothDevice);
                    log(3, "gatt.connect()");
                    this.mBluetoothGatt.connect();
                    return true;
                }
                log(3, "gatt.close()");
                this.mBluetoothGatt.close();
                this.mBluetoothGatt = null;
                try {
                    log(3, "wait(200)");
                    Thread.sleep(200L);
                } catch (InterruptedException unused) {
                }
            }
            if (connectRequest == null) {
                return false;
            }
            boolean zShouldAutoConnect = connectRequest.shouldAutoConnect();
            this.mUserDisconnected = !zShouldAutoConnect;
            if (zShouldAutoConnect) {
                this.mInitialConnection = true;
            }
            this.mBluetoothDevice = bluetoothDevice;
            this.mGattCallback.setHandler(this.mHandler);
            log(2, connectRequest.isFirstAttempt() ? "Connecting..." : "Retrying...");
            this.mConnectionState = 1;
            this.mCallbacks.onDeviceConnecting(bluetoothDevice);
            this.mConnectionTime = SystemClock.elapsedRealtime();
            String str = Build.MODEL;
            if (Build.VERSION.SDK_INT >= 26) {
                int preferredPhy = connectRequest.getPreferredPhy();
                log(3, "gatt = device.connectGatt(autoConnect = false, TRANSPORT_LE, " + phyMaskToString(preferredPhy) + ")");
                this.mBluetoothGatt = bluetoothDevice.connectGatt(this.mContext, false, this.mGattCallback, 2, preferredPhy);
            } else {
                log(3, "gatt = device.connectGatt(autoConnect = false, TRANSPORT_LE)");
                this.mBluetoothGatt = bluetoothDevice.connectGatt(this.mContext, false, this.mGattCallback, 2);
            }
            long connectionTimeout = getConnectionTimeout();
            if (connectionTimeout > 0) {
                installConnectTimeoutTask(connectionTimeout);
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    public boolean internalCreateBond() {
        BluetoothDevice bluetoothDevice = this.mBluetoothDevice;
        if (bluetoothDevice == null) {
            return false;
        }
        log(2, "Starting pairing...");
        if (bluetoothDevice.getBondState() != 12) {
            log(3, "device.createBond()");
            return bluetoothDevice.createBond();
        }
        log(5, "Device already bonded");
        this.mRequest.notifySuccess(bluetoothDevice);
        this.mGattCallback.nextRequest(true);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    public boolean internalDisableIndications(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return internalDisableNotifications(bluetoothGattCharacteristic);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    public boolean internalDisableNotifications(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        BluetoothGattDescriptor cccd;
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt == null || bluetoothGattCharacteristic == null || !this.mConnected || (cccd = getCccd(bluetoothGattCharacteristic, 16)) == null) {
            return false;
        }
        log(3, "gatt.setCharacteristicNotification(" + bluetoothGattCharacteristic.getUuid() + ", false)");
        bluetoothGatt.setCharacteristicNotification(bluetoothGattCharacteristic, false);
        cccd.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
        log(2, "Disabling notifications and indications for " + bluetoothGattCharacteristic.getUuid());
        log(3, "gatt.writeDescriptor(" + CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID + ", value=0x00-00)");
        return internalWriteDescriptorWorkaround(cccd);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    public boolean internalDisconnect() {
        this.mUserDisconnected = true;
        this.mInitialConnection = false;
        this.mReady = false;
        if (this.mBluetoothGatt != null) {
            this.mConnectionState = 3;
            log(2, this.mConnected ? "Disconnecting..." : "Cancelling connection...");
            this.mCallbacks.onDeviceDisconnecting(this.mBluetoothGatt.getDevice());
            boolean z2 = this.mConnected;
            log(3, "gatt.disconnect()");
            this.mBluetoothGatt.disconnect();
            if (z2) {
                return true;
            }
            this.mConnectionState = 0;
            log(4, "Disconnected");
            this.mCallbacks.onDeviceDisconnected(this.mBluetoothGatt.getDevice());
        }
        Request request = this.mRequest;
        if (request != null && request.type == Request.Type.DISCONNECT) {
            BluetoothDevice bluetoothDevice = this.mBluetoothDevice;
            if (bluetoothDevice != null) {
                request.notifySuccess(bluetoothDevice);
            } else {
                request.notifyInvalidRequest();
            }
        }
        BleManager<E>.BleManagerGattCallback bleManagerGattCallback = this.mGattCallback;
        if (bleManagerGattCallback != null) {
            bleManagerGattCallback.nextRequest(true);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    public boolean internalEnableIndications(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt != null && bluetoothGattCharacteristic != null && this.mConnected) {
            bluetoothGatt.setCharacteristicNotification(bluetoothGattCharacteristic, true);
            BluetoothGattDescriptor cccd = getCccd(bluetoothGattCharacteristic, 32);
            if (cccd != null) {
                log(3, "gatt.setCharacteristicNotification(" + bluetoothGattCharacteristic.getUuid() + ", true)");
                cccd.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
                log(2, "Enabling indications for " + bluetoothGattCharacteristic.getUuid());
                log(3, "gatt.writeDescriptor(" + CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID + ", value=0x02-00)");
                return internalWriteDescriptorWorkaround(cccd);
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    public boolean internalEnableNotifications(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt != null && bluetoothGattCharacteristic != null && this.mConnected) {
            bluetoothGatt.setCharacteristicNotification(bluetoothGattCharacteristic, true);
            BluetoothGattDescriptor cccd = getCccd(bluetoothGattCharacteristic, 16);
            if (cccd != null) {
                log(3, "gatt.setCharacteristicNotification(" + bluetoothGattCharacteristic.getUuid() + ", true)");
                cccd.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                log(2, "Enabling notifications for " + bluetoothGattCharacteristic.getUuid());
                log(3, "gatt.writeDescriptor(" + CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID + ", value=0x01-00)");
                return internalWriteDescriptorWorkaround(cccd);
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    public boolean internalExecuteReliableWrite() {
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt == null || !this.mConnected || !this.mReliableWriteInProgress) {
            return false;
        }
        log(2, "Executing reliable write...");
        log(3, "gatt.executeReliableWrite()");
        return bluetoothGatt.executeReliableWrite();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    @Deprecated
    public boolean internalReadBatteryLevel() {
        BluetoothGattService service;
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt == null || !this.mConnected || (service = bluetoothGatt.getService(BATTERY_SERVICE)) == null) {
            return false;
        }
        return internalReadCharacteristic(service.getCharacteristic(BATTERY_LEVEL_CHARACTERISTIC));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    public boolean internalReadCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt == null || bluetoothGattCharacteristic == null || !this.mConnected) {
            return false;
        }
        if ((bluetoothGattCharacteristic.getProperties() & 2) == 0) {
            log(5, "Reading characteristic failed");
            return false;
        }
        log(2, "Reading characteristic " + bluetoothGattCharacteristic.getUuid());
        log(3, "gatt.readCharacteristic(" + bluetoothGattCharacteristic.getUuid() + ")");
        return bluetoothGatt.readCharacteristic(bluetoothGattCharacteristic);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    public boolean internalReadDescriptor(BluetoothGattDescriptor bluetoothGattDescriptor) {
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt == null || bluetoothGattDescriptor == null || !this.mConnected) {
            return false;
        }
        log(2, "Reading descriptor " + bluetoothGattDescriptor.getUuid());
        log(3, "gatt.readDescriptor(" + bluetoothGattDescriptor.getUuid() + ")");
        return bluetoothGatt.readDescriptor(bluetoothGattDescriptor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(api = 26)
    @MainThread
    public boolean internalReadPhy() {
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt == null || !this.mConnected) {
            return false;
        }
        log(2, "Reading PHY...");
        log(3, "gatt.readPhy()");
        bluetoothGatt.readPhy();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    public boolean internalReadRssi() {
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt == null || !this.mConnected) {
            return false;
        }
        log(2, "Reading remote RSSI...");
        log(3, "gatt.readRemoteRssi()");
        return bluetoothGatt.readRemoteRssi();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    public boolean internalReconnect(@NonNull BluetoothDevice bluetoothDevice, @Nullable ConnectRequest connectRequest) {
        this.mDeviceNotSupported = false;
        this.mServiceDiscoveryRequested = false;
        if (this.mGattCallback == null) {
            return true;
        }
        boolean zIsEnabled = BluetoothAdapter.getDefaultAdapter().isEnabled();
        if (!zIsEnabled) {
            ConnectRequest connectRequest2 = this.mConnectRequest;
            if (connectRequest2 != null) {
                connectRequest2.notifyFail(bluetoothDevice, zIsEnabled ? -4 : -100);
            }
            this.mConnectRequest = null;
            BleManager<E>.BleManagerGattCallback bleManagerGattCallback = this.mGattCallback;
            if (bleManagerGattCallback != null) {
                bleManagerGattCallback.nextRequest(true);
            }
            return true;
        }
        synchronized (this.mLock) {
            if (this.mBluetoothGatt == null) {
                this.mContext.registerReceiver(this.mBluetoothStateBroadcastReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
                this.mContext.registerReceiver(this.mBondingBroadcastReceiver, new IntentFilter("android.bluetooth.device.action.BOND_STATE_CHANGED"));
                this.mContext.registerReceiver(this.mPairingRequestBroadcastReceiver, new IntentFilter("android.bluetooth.device.action.PAIRING_REQUEST"));
            } else {
                if (this.mInitialConnection) {
                    this.mInitialConnection = false;
                    this.mConnectionTime = 0L;
                    this.mConnectionState = 1;
                    log(2, "Connecting...");
                    this.mCallbacks.onDeviceConnecting(bluetoothDevice);
                    log(3, "gatt.connect()");
                    this.mBluetoothGatt.connect();
                    return true;
                }
                log(3, "gatt.close()");
                this.mBluetoothGatt.close();
                this.mBluetoothGatt = null;
                try {
                    log(3, "wait(200)");
                    Thread.sleep(200L);
                } catch (InterruptedException unused) {
                }
            }
            if (connectRequest == null || this.mGattCallback == null) {
                return false;
            }
            boolean zShouldAutoConnect = connectRequest.shouldAutoConnect();
            this.mUserDisconnected = !zShouldAutoConnect;
            if (zShouldAutoConnect) {
                this.mInitialConnection = true;
            }
            this.mBluetoothDevice = bluetoothDevice;
            this.mGattCallback.setHandler(this.mHandler);
            log(2, connectRequest.isFirstAttempt() ? "Connecting..." : "Retrying...");
            this.mConnectionState = 1;
            this.mCallbacks.onDeviceConnecting(bluetoothDevice);
            this.mConnectionTime = SystemClock.elapsedRealtime();
            if (Build.VERSION.SDK_INT >= 26) {
                int preferredPhy = connectRequest.getPreferredPhy();
                log(3, "gatt = device.connectGatt(autoConnect = false, TRANSPORT_LE, " + phyMaskToString(preferredPhy) + ")");
                this.mBluetoothGatt = bluetoothDevice.connectGatt(this.mContext, false, this.mGattCallback, 2, preferredPhy);
            } else {
                log(3, "gatt = device.connectGatt(autoConnect = false, TRANSPORT_LE)");
                this.mBluetoothGatt = bluetoothDevice.connectGatt(this.mContext, false, this.mGattCallback, 2);
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    public boolean internalRefreshDeviceCache() {
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt == null) {
            return false;
        }
        log(2, "Refreshing device cache...");
        log(3, "gatt.refresh() (hidden)");
        try {
            return ((Boolean) bluetoothGatt.getClass().getMethod(d.f9880w, null).invoke(bluetoothGatt, null)).booleanValue();
        } catch (Exception e2) {
            Log.w(this.TAG, "An exception occurred while refreshing device", e2);
            log(5, "gatt.refresh() method not found");
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    public boolean internalRemoveBond() throws NoSuchMethodException, SecurityException {
        BluetoothDevice bluetoothDevice = this.mBluetoothDevice;
        if (bluetoothDevice == null) {
            return false;
        }
        log(2, "Removing bond information...");
        if (bluetoothDevice.getBondState() == 10) {
            log(5, "Device is not bonded");
            this.mRequest.notifySuccess(bluetoothDevice);
            this.mGattCallback.nextRequest(true);
            return true;
        }
        try {
            Method method = bluetoothDevice.getClass().getMethod("removeBond", null);
            log(3, "device.removeBond() (hidden)");
            return ((Boolean) method.invoke(bluetoothDevice, null)).booleanValue();
        } catch (Exception e2) {
            Log.w(this.TAG, "An exception occurred while removing bond", e2);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(api = 21)
    @MainThread
    public boolean internalRequestConnectionPriority(int i2) {
        String str;
        String str2;
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt == null || !this.mConnected) {
            return false;
        }
        if (i2 == 1) {
            str = "HIGH (11.25–15ms, 0, 20s)";
            str2 = "HIGH";
        } else if (i2 != 2) {
            str = "BALANCED (30–50ms, 0, 20s)";
            str2 = "BALANCED";
        } else {
            str = "LOW POWER (100–125ms, 2, 20s)";
            str2 = "LOW POWER";
        }
        log(2, "Requesting connection priority: " + str + "...");
        log(3, "gatt.requestConnectionPriority(" + str2 + ")");
        return bluetoothGatt.requestConnectionPriority(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(api = 21)
    @MainThread
    public boolean internalRequestMtu(@IntRange(from = 23, to = 517) int i2) {
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt == null || !this.mConnected) {
            return false;
        }
        log(2, "Requesting new MTU...");
        log(3, "gatt.requestMtu(" + i2 + ")");
        return bluetoothGatt.requestMtu(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    @Deprecated
    public boolean internalSetBatteryNotifications(boolean z2) {
        BluetoothGattService service;
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt == null || !this.mConnected || (service = bluetoothGatt.getService(BATTERY_SERVICE)) == null) {
            return false;
        }
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(BATTERY_LEVEL_CHARACTERISTIC);
        return z2 ? internalEnableNotifications(characteristic) : internalDisableNotifications(characteristic);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(api = 26)
    @MainThread
    public boolean internalSetPreferredPhy(int i2, int i3, int i4) {
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt == null || !this.mConnected) {
            return false;
        }
        log(2, "Requesting preferred PHYs...");
        log(3, "gatt.setPreferredPhy(" + phyMaskToString(i2) + ", " + phyMaskToString(i3) + ", coding option = " + phyCodedOptionToString(i4) + ")");
        bluetoothGatt.setPreferredPhy(i2, i3, i4);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    public boolean internalWriteCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt == null || bluetoothGattCharacteristic == null || !this.mConnected || (bluetoothGattCharacteristic.getProperties() & 12) == 0) {
            return false;
        }
        log(2, "Writing characteristic " + bluetoothGattCharacteristic.getUuid() + " (" + writeTypeToString(bluetoothGattCharacteristic.getWriteType()) + ")");
        StringBuilder sb = new StringBuilder();
        sb.append("gatt.writeCharacteristic(");
        sb.append(bluetoothGattCharacteristic.getUuid());
        sb.append(")");
        log(3, sb.toString());
        return bluetoothGatt.writeCharacteristic(bluetoothGattCharacteristic);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    public boolean internalWriteDescriptor(BluetoothGattDescriptor bluetoothGattDescriptor) {
        if (this.mBluetoothGatt == null || bluetoothGattDescriptor == null || !this.mConnected) {
            return false;
        }
        log(2, "Writing descriptor " + bluetoothGattDescriptor.getUuid());
        log(3, "gatt.writeDescriptor(" + bluetoothGattDescriptor.getUuid() + ")");
        return internalWriteDescriptorWorkaround(bluetoothGattDescriptor);
    }

    @MainThread
    private boolean internalWriteDescriptorWorkaround(BluetoothGattDescriptor bluetoothGattDescriptor) {
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt == null || bluetoothGattDescriptor == null || !this.mConnected) {
            return false;
        }
        BluetoothGattCharacteristic characteristic = bluetoothGattDescriptor.getCharacteristic();
        int writeType = characteristic.getWriteType();
        characteristic.setWriteType(2);
        boolean zWriteDescriptor = bluetoothGatt.writeDescriptor(bluetoothGattDescriptor);
        characteristic.setWriteType(writeType);
        return zWriteDescriptor;
    }

    @RequiresApi(26)
    private String phyCodedOptionToString(int i2) {
        if (i2 == 0) {
            return "No preferred";
        }
        if (i2 == 1) {
            return "S2";
        }
        if (i2 == 2) {
            return "S8";
        }
        return "UNKNOWN (" + i2 + ")";
    }

    @RequiresApi(26)
    private String phyMaskToString(int i2) {
        switch (i2) {
            case 1:
                return "LE 1M";
            case 2:
                return "LE 2M";
            case 3:
                return "LE 1M or LE 2M";
            case 4:
                return "LE Coded";
            case 5:
                return "LE 1M or LE Coded";
            case 6:
                return "LE 2M or LE Coded";
            case 7:
                return "LE 1M, LE 2M or LE Coded";
            default:
                return "UNKNOWN (" + i2 + ")";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(26)
    public String phyToString(int i2) {
        if (i2 == 1) {
            return "LE 1M";
        }
        if (i2 == 2) {
            return "LE 2M";
        }
        if (i2 == 3) {
            return "LE Coded";
        }
        return "UNKNOWN (" + i2 + ")";
    }

    private void runOnUiThread(Runnable runnable) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            this.mHandler.post(runnable);
        } else {
            runnable.run();
        }
    }

    @NonNull
    public RequestQueue beginAtomicRequestQueue() {
        return new RequestQueue().setManager((BleManager) this);
    }

    @NonNull
    public ReliableWriteRequest beginReliableWrite() {
        return Request.newReliableWriteRequest().setManager((BleManager) this);
    }

    public String bondStateToString(int i2) {
        switch (i2) {
            case 10:
                return "BOND_NONE";
            case 11:
                return "BOND_BONDING";
            case 12:
                return "BOND_BONDED";
            default:
                return "UNKNOWN";
        }
    }

    public void clearQueue() {
        BleManager<E>.BleManagerGattCallback bleManagerGattCallback = this.mGattCallback;
        if (bleManagerGattCallback != null) {
            bleManagerGattCallback.cancelQueue();
        }
    }

    public void close() {
        try {
            this.mContext.unregisterReceiver(this.mBluetoothStateBroadcastReceiver);
            this.mContext.unregisterReceiver(this.mBondingBroadcastReceiver);
            this.mContext.unregisterReceiver(this.mPairingRequestBroadcastReceiver);
        } catch (Exception unused) {
        }
        synchronized (this.mLock) {
            if (this.mBluetoothGatt != null) {
                if (shouldClearCacheWhenDisconnected()) {
                    if (internalRefreshDeviceCache()) {
                        log(4, "Cache refreshed");
                    } else {
                        log(5, "Refreshing failed");
                    }
                }
                log(3, "gatt.close()");
                this.mBluetoothGatt.close();
                this.mBluetoothGatt = null;
            }
            this.mConnected = false;
            this.mInitialConnection = false;
            this.mReliableWriteInProgress = false;
            this.mNotificationCallbacks.clear();
            this.mConnectionState = 0;
            BleManager<E>.BleManagerGattCallback bleManagerGattCallback = this.mGattCallback;
            if (bleManagerGattCallback != null) {
                bleManagerGattCallback.cancelQueue();
                this.mGattCallback.mInitQueue = null;
            }
            this.mGattCallback = null;
            this.mBluetoothDevice = null;
        }
    }

    @NonNull
    public final ConnectRequest connect(@NonNull BluetoothDevice bluetoothDevice) {
        if (this.mCallbacks == null) {
            throw new NullPointerException("Set callbacks using setGattCallbacks(E callbacks) before connecting");
        }
        if (bluetoothDevice != null) {
            return Request.connect(bluetoothDevice).useAutoConnect(shouldAutoConnect()).setManager((BleManager) this);
        }
        throw new NullPointerException("Bluetooth device not specified");
    }

    @NonNull
    public Request createBond() {
        return Request.createBond().setManager(this);
    }

    @Deprecated
    public void disableBatteryLevelNotifications() {
        Request.newDisableBatteryLevelNotificationsRequest().setManager((BleManager) this).done(new SuccessCallback() { // from class: aisble.BleManager.7
            @Override // aisble.callback.SuccessCallback
            public void onRequestCompleted(@NonNull BluetoothDevice bluetoothDevice) {
                BleManager.this.log(4, "Battery Level notifications disabled");
            }
        }).enqueue();
    }

    @NonNull
    public WriteRequest disableIndications(@Nullable BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return Request.newDisableIndicationsRequest(bluetoothGattCharacteristic).setManager((BleManager) this);
    }

    @NonNull
    public WriteRequest disableNotifications(@Nullable BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return Request.newDisableNotificationsRequest(bluetoothGattCharacteristic).setManager((BleManager) this);
    }

    @NonNull
    public final DisconnectRequest disconnect() {
        return Request.disconnect().setManager((BleManager) this);
    }

    public final void disconnectImmediately() {
        internalDisconnect();
    }

    @Deprecated
    public void enableBatteryLevelNotifications() {
        if (this.mBatteryLevelNotificationCallback == null) {
            this.mBatteryLevelNotificationCallback = new ValueChangedCallback().with(new DataReceivedCallback() { // from class: aisble.BleManager.5
                @Override // aisble.callback.DataReceivedCallback
                public void onDataReceived(@NonNull BluetoothDevice bluetoothDevice, @NonNull Data data) {
                    if (data.size() == 1) {
                        int iIntValue = data.getIntValue(17, 0).intValue();
                        BleManager.this.mBatteryValue = iIntValue;
                        BleManagerGattCallback bleManagerGattCallback = BleManager.this.mGattCallback;
                        if (bleManagerGattCallback != null) {
                            bleManagerGattCallback.onBatteryValueReceived(BleManager.this.mBluetoothGatt, iIntValue);
                        }
                        BleManager.this.mCallbacks.onBatteryValueReceived(bluetoothDevice, iIntValue);
                    }
                }
            });
        }
        Request.newEnableBatteryLevelNotificationsRequest().setManager((BleManager) this).done(new SuccessCallback() { // from class: aisble.BleManager.6
            @Override // aisble.callback.SuccessCallback
            public void onRequestCompleted(@NonNull BluetoothDevice bluetoothDevice) {
                BleManager.this.log(4, "Battery Level notifications enabled");
            }
        }).enqueue();
    }

    @NonNull
    public WriteRequest enableIndications(@Nullable BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return Request.newEnableIndicationsRequest(bluetoothGattCharacteristic).setManager((BleManager) this);
    }

    @NonNull
    public WriteRequest enableNotifications(@Nullable BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return Request.newEnableNotificationsRequest(bluetoothGattCharacteristic).setManager((BleManager) this);
    }

    @Deprecated
    public final void enqueue(@NonNull Request request) {
        final BleManager<E>.BleManagerGattCallback gattCallback = this.mGattCallback;
        if (gattCallback == null) {
            gattCallback = getGattCallback();
            this.mGattCallback = gattCallback;
        }
        gattCallback.enqueue(request);
        runOnUiThread(new Runnable() { // from class: aisble.BleManager.8
            @Override // java.lang.Runnable
            public void run() {
                gattCallback.nextRequest(false);
            }
        });
    }

    @IntRange(from = -1, to = 100)
    @Deprecated
    public final int getBatteryValue() {
        return this.mBatteryValue;
    }

    @Nullable
    public BluetoothDevice getBluetoothDevice() {
        return this.mBluetoothDevice;
    }

    public final int getConnectState() {
        return this.mConnectionState;
    }

    public long getConnectionTimeout() {
        return -1L;
    }

    @NonNull
    public final Context getContext() {
        return this.mContext;
    }

    @NonNull
    public abstract BleManager<E>.BleManagerGattCallback getGattCallback();

    @IntRange(from = 23, to = 517)
    public int getMtu() {
        return this.mMtu;
    }

    @IntRange(from = 0)
    public int getServiceDiscoveryDelay(boolean z2) {
        return z2 ? 1600 : 100;
    }

    public final boolean isBonded() {
        BluetoothDevice bluetoothDevice = this.mBluetoothDevice;
        return bluetoothDevice != null && bluetoothDevice.getBondState() == 12;
    }

    public final boolean isConnected() {
        return this.mConnected;
    }

    public final boolean isReady() {
        return this.mReady;
    }

    public final boolean isReliableWriteInProgress() {
        return this.mReliableWriteInProgress;
    }

    @Override // aisble.utils.ILogger
    public void log(int i2, @NonNull String str) {
        if (i2 == 2) {
            LogUtils.v(this.TAG, str);
            return;
        }
        if (i2 == 3) {
            LogUtils.d(this.TAG, str);
            return;
        }
        if (i2 == 4) {
            LogUtils.i(this.TAG, str);
        } else if (i2 == 5) {
            LogUtils.w(this.TAG, str);
        } else {
            if (i2 != 6) {
                return;
            }
            LogUtils.e(this.TAG, str);
        }
    }

    public void onPairingRequestReceived(@NonNull BluetoothDevice bluetoothDevice, int i2) {
    }

    @Override // aisble.TimeoutHandler
    @MainThread
    public void onRequestTimeout(@NonNull TimeoutableRequest timeoutableRequest) {
        this.mRequest = null;
        this.mValueChangedRequest = null;
        Request.Type type = timeoutableRequest.type;
        if (type == Request.Type.CONNECT) {
            this.mConnectRequest = null;
            internalDisconnect();
        } else {
            if (type == Request.Type.DISCONNECT) {
                close();
                return;
            }
            BleManager<E>.BleManagerGattCallback bleManagerGattCallback = this.mGattCallback;
            if (bleManagerGattCallback != null) {
                bleManagerGattCallback.nextRequest(true);
            }
        }
    }

    public void overrideMtu(@IntRange(from = 23, to = 517) int i2) {
        this.mMtu = i2;
    }

    public String pairingVariantToString(int i2) {
        switch (i2) {
            case 0:
                return "PAIRING_VARIANT_PIN";
            case 1:
                return "PAIRING_VARIANT_PASSKEY";
            case 2:
                return "PAIRING_VARIANT_PASSKEY_CONFIRMATION";
            case 3:
                return "PAIRING_VARIANT_CONSENT";
            case 4:
                return "PAIRING_VARIANT_DISPLAY_PASSKEY";
            case 5:
                return "PAIRING_VARIANT_DISPLAY_PIN";
            case 6:
                return "PAIRING_VARIANT_OOB_CONSENT";
            default:
                return "UNKNOWN";
        }
    }

    @Deprecated
    public void readBatteryLevel() {
        Request.newReadBatteryLevelRequest().setManager((BleManager) this).with(new DataReceivedCallback() { // from class: aisble.BleManager.4
            @Override // aisble.callback.DataReceivedCallback
            public void onDataReceived(@NonNull BluetoothDevice bluetoothDevice, @NonNull Data data) {
                if (data.size() == 1) {
                    int iIntValue = data.getIntValue(17, 0).intValue();
                    BleManager.this.log(4, "Battery Level received: " + iIntValue + "%");
                    BleManager.this.mBatteryValue = iIntValue;
                    BleManagerGattCallback bleManagerGattCallback = BleManager.this.mGattCallback;
                    if (bleManagerGattCallback != null) {
                        bleManagerGattCallback.onBatteryValueReceived(BleManager.this.mBluetoothGatt, iIntValue);
                    }
                    BleManager.this.mCallbacks.onBatteryValueReceived(bluetoothDevice, iIntValue);
                }
            }
        }).enqueue();
    }

    @NonNull
    public ReadRequest readCharacteristic(@Nullable BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return Request.newReadRequest(bluetoothGattCharacteristic).setManager((BleManager) this);
    }

    @NonNull
    public ReadRequest readDescriptor(@Nullable BluetoothGattDescriptor bluetoothGattDescriptor) {
        return Request.newReadRequest(bluetoothGattDescriptor).setManager((BleManager) this);
    }

    public PhyRequest readPhy() {
        return Request.newReadPhyRequest().setManager((BleManager) this);
    }

    public ReadRssiRequest readRssi() {
        return Request.newReadRssiRequest().setManager((BleManager) this);
    }

    public Request refreshDeviceCache() {
        return Request.newRefreshCacheRequest().setManager(this);
    }

    @NonNull
    public Request removeBond() {
        return Request.removeBond().setManager(this);
    }

    @RequiresApi(api = 21)
    public ConnectionPriorityRequest requestConnectionPriority(int i2) {
        return Request.newConnectionPriorityRequest(i2).setManager((BleManager) this);
    }

    public MtuRequest requestMtu(@IntRange(from = 23, to = 517) int i2) {
        return Request.newMtuRequest(i2).setManager((BleManager) this);
    }

    public void setGattCallbacks(@NonNull E e2) {
        this.mCallbacks = e2;
    }

    @NonNull
    public ValueChangedCallback setIndicationCallback(@Nullable BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return setNotificationCallback(bluetoothGattCharacteristic);
    }

    @NonNull
    @MainThread
    public ValueChangedCallback setNotificationCallback(@Nullable BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        ValueChangedCallback valueChangedCallback = this.mNotificationCallbacks.get(bluetoothGattCharacteristic);
        if (valueChangedCallback == null) {
            valueChangedCallback = new ValueChangedCallback();
            if (bluetoothGattCharacteristic != null) {
                this.mNotificationCallbacks.put(bluetoothGattCharacteristic, valueChangedCallback);
            }
        }
        return valueChangedCallback.free();
    }

    public PhyRequest setPreferredPhy(int i2, int i3, int i4) {
        return Request.newSetPreferredPhyRequest(i2, i3, i4).setManager((BleManager) this);
    }

    @Deprecated
    public boolean shouldAutoConnect() {
        return false;
    }

    public boolean shouldClearCacheWhenDisconnected() {
        return false;
    }

    public boolean shouldRetryDiscoveryServiceWhenDeviceNotSupported(int i2) {
        return false;
    }

    public SleepRequest sleep(@IntRange(from = 0) long j2) {
        return Request.newSleepRequest(j2).setManager((BleManager) this);
    }

    public String stateToString(int i2) {
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? "DISCONNECTED" : "DISCONNECTING" : "CONNECTED" : "CONNECTING";
    }

    @NonNull
    public WaitForValueChangedRequest waitForIndication(@NonNull BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return Request.newWaitForIndicationRequest(bluetoothGattCharacteristic).setManager((BleManager) this);
    }

    @NonNull
    public WaitForValueChangedRequest waitForNotification(@NonNull BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return Request.newWaitForNotificationRequest(bluetoothGattCharacteristic).setManager((BleManager) this);
    }

    @NonNull
    public WriteRequest writeCharacteristic(@Nullable BluetoothGattCharacteristic bluetoothGattCharacteristic, @Nullable Data data) {
        return Request.newWriteRequest(bluetoothGattCharacteristic, data != null ? data.getValue() : null).setManager((BleManager) this);
    }

    @NonNull
    public WriteRequest writeDescriptor(@Nullable BluetoothGattDescriptor bluetoothGattDescriptor, @Nullable Data data) {
        return Request.newWriteRequest(bluetoothGattDescriptor, data != null ? data.getValue() : null).setManager((BleManager) this);
    }

    public String writeTypeToString(int i2) {
        if (i2 == 1) {
            return "WRITE COMMAND";
        }
        if (i2 == 2) {
            return "WRITE REQUEST";
        }
        if (i2 == 4) {
            return "WRITE SIGNED";
        }
        return "UNKNOWN: " + i2;
    }

    public BleManager(@NonNull Context context) {
        this.TAG = "BleManager";
        this.mLock = new Object();
        this.mConnectionCount = 0;
        this.mConnectionState = 0;
        this.mBatteryValue = -1;
        this.mMtu = 23;
        this.mNotificationCallbacks = new HashMap<>();
        this.mDeviceNotSupported = false;
        this.mRetryDiscoveryServiceCount = 0;
        this.mConnectTimeoutTask = null;
        this.mServiceDiscoveryRunnable = null;
        this.mWriteOpTimeoutRunable = null;
        this.mBluetoothStateBroadcastReceiver = new BroadcastReceiver() { // from class: aisble.BleManager.1
            private String state2String(int i2) {
                switch (i2) {
                    case 10:
                        return "OFF";
                    case 11:
                        return "TURNING ON";
                    case 12:
                        return "ON";
                    case 13:
                        return "TURNING OFF";
                    default:
                        return "UNKNOWN (" + i2 + ")";
                }
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
                int intExtra2 = intent.getIntExtra("android.bluetooth.adapter.extra.PREVIOUS_STATE", 10);
                BleManager.this.log(3, "[Broadcast] Action received: android.bluetooth.adapter.action.STATE_CHANGED, state changed to " + state2String(intExtra));
                if (intExtra == 10 || intExtra == 13) {
                    if (intExtra2 == 13 || intExtra2 == 10) {
                        BleManager.this.close();
                        return;
                    }
                    BleManagerGattCallback bleManagerGattCallback = BleManager.this.mGattCallback;
                    if (bleManagerGattCallback != null) {
                        bleManagerGattCallback.mOperationInProgress = true;
                        bleManagerGattCallback.cancelQueue();
                        bleManagerGattCallback.mInitQueue = null;
                    }
                    BluetoothDevice bluetoothDevice = BleManager.this.mBluetoothDevice;
                    if (bluetoothDevice != null) {
                        if (BleManager.this.mRequest != null && BleManager.this.mRequest.type != Request.Type.DISCONNECT) {
                            BleManager.this.mRequest.notifyFail(bluetoothDevice, -100);
                            BleManager.this.mRequest = null;
                        }
                        if (BleManager.this.mValueChangedRequest != null) {
                            BleManager.this.mValueChangedRequest.notifyFail(bluetoothDevice, -100);
                            BleManager.this.mValueChangedRequest = null;
                        }
                        if (BleManager.this.mConnectRequest != null) {
                            BleManager.this.mConnectRequest.notifyFail(bluetoothDevice, -100);
                            BleManager.this.mConnectRequest = null;
                        }
                    }
                    BleManager.this.mUserDisconnected = true;
                    if (bleManagerGattCallback != null) {
                        bleManagerGattCallback.mOperationInProgress = false;
                        if (bluetoothDevice != null) {
                            bleManagerGattCallback.notifyDeviceDisconnected(bluetoothDevice);
                        }
                    }
                }
            }
        };
        this.mBondingBroadcastReceiver = new BroadcastReceiver() { // from class: aisble.BleManager.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                int intExtra = intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", -1);
                int intExtra2 = intent.getIntExtra("android.bluetooth.device.extra.PREVIOUS_BOND_STATE", -1);
                if (BleManager.this.mBluetoothDevice == null || !bluetoothDevice.getAddress().equals(BleManager.this.mBluetoothDevice.getAddress())) {
                    return;
                }
                BleManager.this.log(3, "[Broadcast] Action received: android.bluetooth.device.action.BOND_STATE_CHANGED, bond state changed to: " + BleManager.this.bondStateToString(intExtra) + " (" + intExtra + ")");
                switch (intExtra) {
                    case 10:
                        if (intExtra2 != 11) {
                            if (intExtra2 == 12 && BleManager.this.mRequest != null && BleManager.this.mRequest.type == Request.Type.REMOVE_BOND) {
                                BleManager.this.log(4, "Bond information removed");
                                BleManager.this.mRequest.notifySuccess(bluetoothDevice);
                                BleManager.this.mRequest = null;
                                break;
                            }
                        } else {
                            BleManager.this.mCallbacks.onBondingFailed(bluetoothDevice);
                            BleManager.this.log(5, "Bonding failed");
                            if (BleManager.this.mRequest != null) {
                                BleManager.this.mRequest.notifyFail(bluetoothDevice, -4);
                                BleManager.this.mRequest = null;
                                break;
                            }
                        }
                        break;
                    case 11:
                        BleManager.this.mCallbacks.onBondingRequired(bluetoothDevice);
                        return;
                    case 12:
                        BleManager.this.log(4, "Device bonded");
                        BleManager.this.mCallbacks.onBonded(bluetoothDevice);
                        if (BleManager.this.mRequest != null && BleManager.this.mRequest.type == Request.Type.CREATE_BOND) {
                            BleManager.this.mRequest.notifySuccess(bluetoothDevice);
                            BleManager.this.mRequest = null;
                            break;
                        } else {
                            BleManager bleManager = BleManager.this;
                            if (!bleManager.mServicesDiscovered && !bleManager.mServiceDiscoveryRequested) {
                                BleManager.this.mServiceDiscoveryRequested = true;
                                BleManager.this.mHandler.post(new Runnable() { // from class: aisble.BleManager.2.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        BleManager.this.log(2, "Discovering services...");
                                        BleManager.this.log(3, "gatt.discoverServices()");
                                        BleManager.this.mBluetoothGatt.discoverServices();
                                    }
                                });
                                return;
                            } else if (Build.VERSION.SDK_INT < 26 && BleManager.this.mRequest != null && BleManager.this.mRequest.type != Request.Type.CREATE_BOND) {
                                BleManager.this.mGattCallback.enqueueFirst(BleManager.this.mRequest);
                                break;
                            } else {
                                return;
                            }
                        }
                        break;
                }
                BleManager.this.mGattCallback.nextRequest(true);
            }
        };
        this.mPairingRequestBroadcastReceiver = new BroadcastReceiver() { // from class: aisble.BleManager.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (BleManager.this.mBluetoothDevice == null || bluetoothDevice == null || !bluetoothDevice.getAddress().equals(BleManager.this.mBluetoothDevice.getAddress())) {
                    return;
                }
                int intExtra = intent.getIntExtra("android.bluetooth.device.extra.PAIRING_VARIANT", 0);
                BleManager.this.log(3, "[Broadcast] Action received: android.bluetooth.device.action.PAIRING_REQUEST, pairing variant: " + BleManager.this.pairingVariantToString(intExtra) + " (" + intExtra + ")");
                BleManager.this.onPairingRequestReceived(bluetoothDevice, intExtra);
            }
        };
        this.mContext = context.getApplicationContext();
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    @NonNull
    public WriteRequest writeCharacteristic(@Nullable BluetoothGattCharacteristic bluetoothGattCharacteristic, @Nullable byte[] bArr) {
        return Request.newWriteRequest(bluetoothGattCharacteristic, bArr).setManager((BleManager) this);
    }

    @NonNull
    public WriteRequest writeDescriptor(@Nullable BluetoothGattDescriptor bluetoothGattDescriptor, @Nullable byte[] bArr) {
        return Request.newWriteRequest(bluetoothGattDescriptor, bArr).setManager((BleManager) this);
    }

    @NonNull
    public WriteRequest writeCharacteristic(@Nullable BluetoothGattCharacteristic bluetoothGattCharacteristic, @Nullable byte[] bArr, int i2, int i3) {
        return Request.newWriteRequest(bluetoothGattCharacteristic, bArr, i2, i3).setManager((BleManager) this);
    }

    @NonNull
    public WriteRequest writeDescriptor(@Nullable BluetoothGattDescriptor bluetoothGattDescriptor, @Nullable byte[] bArr, int i2, int i3) {
        return Request.newWriteRequest(bluetoothGattDescriptor, bArr, i2, i3).setManager((BleManager) this);
    }

    @Override // aisble.utils.ILogger
    public void log(int i2, @StringRes int i3, @Nullable Object... objArr) {
        log(i2, this.mContext.getString(i3, objArr));
    }

    @NonNull
    @Deprecated
    public final ConnectRequest connect(@NonNull BluetoothDevice bluetoothDevice, int i2) {
        if (this.mCallbacks == null) {
            throw new NullPointerException("Set callbacks using setGattCallbacks(E callbacks) before connecting");
        }
        if (bluetoothDevice != null) {
            return Request.connect(bluetoothDevice).usePreferredPhy(i2).useAutoConnect(shouldAutoConnect()).setManager((BleManager) this);
        }
        throw new NullPointerException("Bluetooth device not specified");
    }
}
