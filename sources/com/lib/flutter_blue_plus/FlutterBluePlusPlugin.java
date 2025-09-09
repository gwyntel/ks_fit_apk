package com.lib.flutter_blue_plus;

import android.annotation.TargetApi;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.facebook.internal.NativeProtocol;
import com.taobao.accs.utl.BaseMonitor;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import org.android.agoo.common.AgooConstants;

/* loaded from: classes4.dex */
public class FlutterBluePlusPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler, PluginRegistry.RequestPermissionsResultListener, PluginRegistry.ActivityResultListener, ActivityAware {
    private static final String CCCD = "2902";
    private static final String NAMESPACE = "flutter_blue_plus";
    private static final String TAG = "[FBP-Android]";
    private ActivityPluginBinding activityBinding;
    private Context context;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothManager mBluetoothManager;
    private MethodChannel methodChannel;
    private FlutterPlugin.FlutterPluginBinding pluginBinding;
    private ScanCallback scanCallback;
    private LogLevel logLevel = LogLevel.DEBUG;
    private boolean mIsScanning = false;
    private final Semaphore mMethodCallMutex = new Semaphore(1);
    private final Map<String, BluetoothGatt> mConnectedDevices = new ConcurrentHashMap();
    private final Map<String, BluetoothGatt> mCurrentlyConnectingDevices = new ConcurrentHashMap();
    private final Map<String, BluetoothDevice> mBondingDevices = new ConcurrentHashMap();
    private final Map<String, Integer> mMtu = new ConcurrentHashMap();
    private final Map<String, BluetoothGatt> mAutoConnected = new ConcurrentHashMap();
    private final Map<String, String> mWriteChr = new ConcurrentHashMap();
    private final Map<String, String> mWriteDesc = new ConcurrentHashMap();
    private final Map<String, String> mAdvSeen = new ConcurrentHashMap();
    private final Map<String, Integer> mScanCounts = new ConcurrentHashMap();
    private HashMap<String, Object> mScanFilters = new HashMap<>();
    private final Map<Integer, OperationOnPermission> operationsOnPermission = new HashMap();
    private int lastEventId = 1452;
    private final int enableBluetoothRequestCode = 1879842617;
    private final BroadcastReceiver mBluetoothAdapterStateReceiver = new BroadcastReceiver() { // from class: com.lib.flutter_blue_plus.FlutterBluePlusPlugin.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            BluetoothLeScanner bluetoothLeScanner;
            String action = intent.getAction();
            if (action == null || !"android.bluetooth.adapter.action.STATE_CHANGED".equals(action)) {
                return;
            }
            int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
            FlutterBluePlusPlugin flutterBluePlusPlugin = FlutterBluePlusPlugin.this;
            LogLevel logLevel = LogLevel.DEBUG;
            flutterBluePlusPlugin.log(logLevel, "OnAdapterStateChanged: " + FlutterBluePlusPlugin.adapterStateString(intExtra));
            if (intExtra == 12 && FlutterBluePlusPlugin.this.mBluetoothAdapter != null && FlutterBluePlusPlugin.this.mIsScanning && (bluetoothLeScanner = FlutterBluePlusPlugin.this.mBluetoothAdapter.getBluetoothLeScanner()) != null) {
                FlutterBluePlusPlugin.this.log(logLevel, "calling stopScan (Bluetooth Restarted)");
                bluetoothLeScanner.stopScan(FlutterBluePlusPlugin.this.getScanCallback());
                FlutterBluePlusPlugin.this.mIsScanning = false;
            }
            HashMap map = new HashMap();
            map.put("adapter_state", Integer.valueOf(FlutterBluePlusPlugin.I(intExtra)));
            FlutterBluePlusPlugin.this.invokeMethodUIThread("OnAdapterStateChanged", map);
            if (intExtra == 13 || intExtra == 10) {
                FlutterBluePlusPlugin.this.disconnectAllDevices("adapterTurnOff");
            }
        }
    };
    private final BroadcastReceiver mBluetoothBondStateReceiver = new BroadcastReceiver() { // from class: com.lib.flutter_blue_plus.FlutterBluePlusPlugin.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null || !action.equals("android.bluetooth.device.action.BOND_STATE_CHANGED")) {
                return;
            }
            BluetoothDevice bluetoothDevice = Build.VERSION.SDK_INT >= 33 ? (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE", BluetoothDevice.class) : (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            int intExtra = intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", Integer.MIN_VALUE);
            int intExtra2 = intent.getIntExtra("android.bluetooth.device.extra.PREVIOUS_BOND_STATE", -1);
            FlutterBluePlusPlugin.this.log(LogLevel.DEBUG, "OnBondStateChanged: " + FlutterBluePlusPlugin.bondStateString(intExtra) + " prev: " + FlutterBluePlusPlugin.bondStateString(intExtra2));
            String address = bluetoothDevice.getAddress();
            if (intExtra == 11) {
                FlutterBluePlusPlugin.this.mBondingDevices.put(address, bluetoothDevice);
            } else {
                FlutterBluePlusPlugin.this.mBondingDevices.remove(address);
            }
            HashMap map = new HashMap();
            map.put("remote_id", address);
            map.put("bond_state", Integer.valueOf(FlutterBluePlusPlugin.N(intExtra)));
            map.put("prev_state", Integer.valueOf(FlutterBluePlusPlugin.N(intExtra2)));
            FlutterBluePlusPlugin.this.invokeMethodUIThread("OnBondStateChanged", map);
        }
    };
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() { // from class: com.lib.flutter_blue_plus.FlutterBluePlusPlugin.4
        private boolean handleUnexpectedConnectionEvents(BluetoothGatt bluetoothGatt, int i2, String str) {
            if (i2 == 2) {
                if (FlutterBluePlusPlugin.this.mCurrentlyConnectingDevices.get(str) == null && FlutterBluePlusPlugin.this.mAutoConnected.get(str) == null) {
                    FlutterBluePlusPlugin.this.log(LogLevel.DEBUG, "[unexpected connection] disconnecting now");
                    FlutterBluePlusPlugin.this.mConnectedDevices.remove(str);
                    FlutterBluePlusPlugin.this.mBondingDevices.remove(str);
                    bluetoothGatt.disconnect();
                    bluetoothGatt.close();
                    return true;
                }
            } else if (i2 == 0 && FlutterBluePlusPlugin.this.mCurrentlyConnectingDevices.get(str) == null && FlutterBluePlusPlugin.this.mConnectedDevices.get(str) == null && FlutterBluePlusPlugin.this.mAutoConnected.get(str) == null) {
                FlutterBluePlusPlugin.this.log(LogLevel.DEBUG, "[unexpected connection] disconnect complete");
                FlutterBluePlusPlugin.this.mBondingDevices.remove(str);
                bluetoothGatt.close();
                return true;
            }
            return false;
        }

        @Override // android.bluetooth.BluetoothGattCallback
        @TargetApi(33)
        public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, byte[] bArr) {
            LogLevel logLevel = LogLevel.DEBUG;
            FlutterBluePlusPlugin.this.log(logLevel, "onCharacteristicChanged:");
            FlutterBluePlusPlugin.this.log(logLevel, "  chr: " + FlutterBluePlusPlugin.this.uuidStr(bluetoothGattCharacteristic.getUuid()));
            onCharacteristicReceived(bluetoothGatt, bluetoothGattCharacteristic, bArr, 0);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        @TargetApi(33)
        public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, byte[] bArr, int i2) {
            LogLevel logLevel = i2 == 0 ? LogLevel.DEBUG : LogLevel.ERROR;
            FlutterBluePlusPlugin.this.log(logLevel, "onCharacteristicRead:");
            FlutterBluePlusPlugin.this.log(logLevel, "  chr: " + FlutterBluePlusPlugin.this.uuidStr(bluetoothGattCharacteristic.getUuid()));
            FlutterBluePlusPlugin.this.log(logLevel, "  status: " + FlutterBluePlusPlugin.gattErrorString(i2) + " (" + i2 + ")");
            onCharacteristicReceived(bluetoothGatt, bluetoothGattCharacteristic, bArr, i2);
        }

        public void onCharacteristicReceived(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, byte[] bArr, int i2) {
            if (FlutterBluePlusPlugin.this.uuidStr(bluetoothGattCharacteristic.getService().getUuid()) == "1800" && FlutterBluePlusPlugin.this.uuidStr(bluetoothGattCharacteristic.getUuid()) == "2A05") {
                FlutterBluePlusPlugin.this.invokeMethodUIThread("OnServicesReset", FlutterBluePlusPlugin.this.L(bluetoothGatt.getDevice()));
            }
            BluetoothGattService bluetoothGattServiceU = FlutterBluePlusPlugin.U(bluetoothGatt, bluetoothGattCharacteristic);
            HashMap map = new HashMap();
            map.put("remote_id", bluetoothGatt.getDevice().getAddress());
            map.put("service_uuid", FlutterBluePlusPlugin.this.uuidStr(bluetoothGattCharacteristic.getService().getUuid()));
            map.put("characteristic_uuid", FlutterBluePlusPlugin.this.uuidStr(bluetoothGattCharacteristic.getUuid()));
            map.put("value", FlutterBluePlusPlugin.bytesToHex(bArr));
            map.put("success", Integer.valueOf(i2 == 0 ? 1 : 0));
            map.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, Integer.valueOf(i2));
            map.put("error_string", FlutterBluePlusPlugin.gattErrorString(i2));
            if (bluetoothGattServiceU != null) {
                map.put("primary_service_uuid", FlutterBluePlusPlugin.this.uuidStr(bluetoothGattServiceU.getUuid()));
            }
            FlutterBluePlusPlugin.this.invokeMethodUIThread("OnCharacteristicReceived", map);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i2) {
            LogLevel logLevel = i2 == 0 ? LogLevel.DEBUG : LogLevel.ERROR;
            FlutterBluePlusPlugin.this.log(logLevel, "onCharacteristicWrite:");
            FlutterBluePlusPlugin.this.log(logLevel, "  chr: " + FlutterBluePlusPlugin.this.uuidStr(bluetoothGattCharacteristic.getUuid()));
            FlutterBluePlusPlugin.this.log(logLevel, "  status: " + FlutterBluePlusPlugin.gattErrorString(i2) + " (" + i2 + ")");
            BluetoothGattService bluetoothGattServiceU = FlutterBluePlusPlugin.U(bluetoothGatt, bluetoothGattCharacteristic);
            String address = bluetoothGatt.getDevice().getAddress();
            String strUuidStr = FlutterBluePlusPlugin.this.uuidStr(bluetoothGattCharacteristic.getService().getUuid());
            String strUuidStr2 = FlutterBluePlusPlugin.this.uuidStr(bluetoothGattCharacteristic.getUuid());
            String str = address + ":" + strUuidStr + ":" + strUuidStr2 + ":" + (bluetoothGattServiceU != null ? FlutterBluePlusPlugin.this.uuidStr(bluetoothGattServiceU.getUuid()) : "");
            String str2 = FlutterBluePlusPlugin.this.mWriteChr.get(str) != null ? (String) FlutterBluePlusPlugin.this.mWriteChr.get(str) : "";
            FlutterBluePlusPlugin.this.mWriteChr.remove(str);
            HashMap map = new HashMap();
            map.put("remote_id", address);
            map.put("service_uuid", strUuidStr);
            map.put("characteristic_uuid", strUuidStr2);
            map.put("value", str2);
            map.put("success", Integer.valueOf(i2 == 0 ? 1 : 0));
            map.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, Integer.valueOf(i2));
            map.put("error_string", FlutterBluePlusPlugin.gattErrorString(i2));
            if (bluetoothGattServiceU != null) {
                map.put("primary_service_uuid", FlutterBluePlusPlugin.this.uuidStr(bluetoothGattServiceU.getUuid()));
            }
            FlutterBluePlusPlugin.this.invokeMethodUIThread("OnCharacteristicWritten", map);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i2, int i3) {
            try {
                FlutterBluePlusPlugin flutterBluePlusPlugin = FlutterBluePlusPlugin.this;
                flutterBluePlusPlugin.acquireMutex(flutterBluePlusPlugin.mMethodCallMutex);
                FlutterBluePlusPlugin flutterBluePlusPlugin2 = FlutterBluePlusPlugin.this;
                LogLevel logLevel = LogLevel.DEBUG;
                flutterBluePlusPlugin2.log(logLevel, "onConnectionStateChange:" + FlutterBluePlusPlugin.connectionStateString(i3));
                FlutterBluePlusPlugin.this.log(logLevel, "  status: " + FlutterBluePlusPlugin.hciStatusString(i2));
                if (i3 == 2 || i3 == 0) {
                    String address = bluetoothGatt.getDevice().getAddress();
                    if (!handleUnexpectedConnectionEvents(bluetoothGatt, i3, address)) {
                        if (i3 == 2) {
                            FlutterBluePlusPlugin.this.mConnectedDevices.put(address, bluetoothGatt);
                            FlutterBluePlusPlugin.this.mCurrentlyConnectingDevices.remove(address);
                            FlutterBluePlusPlugin.this.mMtu.put(address, 23);
                        }
                        if (i3 == 0) {
                            FlutterBluePlusPlugin.this.mConnectedDevices.remove(address);
                            FlutterBluePlusPlugin.this.mCurrentlyConnectingDevices.remove(address);
                            FlutterBluePlusPlugin.this.mBondingDevices.remove(address);
                            if (FlutterBluePlusPlugin.this.mAutoConnected.containsKey(address)) {
                                FlutterBluePlusPlugin.this.log(logLevel, "autoconnect is true. skipping gatt.close()");
                            } else {
                                bluetoothGatt.close();
                            }
                        }
                        HashMap map = new HashMap();
                        map.put("remote_id", address);
                        map.put("connection_state", Integer.valueOf(FlutterBluePlusPlugin.Q(i3)));
                        map.put("disconnect_reason_code", Integer.valueOf(i2));
                        map.put("disconnect_reason_string", FlutterBluePlusPlugin.hciStatusString(i2));
                        FlutterBluePlusPlugin.this.invokeMethodUIThread("OnConnectionStateChanged", map);
                        FlutterBluePlusPlugin.this.mMethodCallMutex.release();
                    }
                }
            } finally {
                FlutterBluePlusPlugin.this.mMethodCallMutex.release();
            }
        }

        @Override // android.bluetooth.BluetoothGattCallback
        @TargetApi(33)
        public void onDescriptorRead(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i2, byte[] bArr) {
            LogLevel logLevel = i2 == 0 ? LogLevel.DEBUG : LogLevel.ERROR;
            FlutterBluePlusPlugin.this.log(logLevel, "onDescriptorRead:");
            FlutterBluePlusPlugin.this.log(logLevel, "  chr: " + FlutterBluePlusPlugin.this.uuidStr(bluetoothGattDescriptor.getCharacteristic().getUuid()));
            FlutterBluePlusPlugin.this.log(logLevel, "  desc: " + FlutterBluePlusPlugin.this.uuidStr(bluetoothGattDescriptor.getUuid()));
            FlutterBluePlusPlugin.this.log(logLevel, "  status: " + FlutterBluePlusPlugin.gattErrorString(i2) + " (" + i2 + ")");
            BluetoothGattService bluetoothGattServiceU = FlutterBluePlusPlugin.U(bluetoothGatt, bluetoothGattDescriptor.getCharacteristic());
            HashMap map = new HashMap();
            map.put("remote_id", bluetoothGatt.getDevice().getAddress());
            map.put("service_uuid", FlutterBluePlusPlugin.this.uuidStr(bluetoothGattDescriptor.getCharacteristic().getService().getUuid()));
            map.put("characteristic_uuid", FlutterBluePlusPlugin.this.uuidStr(bluetoothGattDescriptor.getCharacteristic().getUuid()));
            map.put("descriptor_uuid", FlutterBluePlusPlugin.this.uuidStr(bluetoothGattDescriptor.getUuid()));
            map.put("value", FlutterBluePlusPlugin.bytesToHex(bArr));
            map.put("success", Integer.valueOf(i2 == 0 ? 1 : 0));
            map.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, Integer.valueOf(i2));
            map.put("error_string", FlutterBluePlusPlugin.gattErrorString(i2));
            if (bluetoothGattServiceU != null) {
                map.put("primary_service_uuid", FlutterBluePlusPlugin.this.uuidStr(bluetoothGattServiceU.getUuid()));
            }
            FlutterBluePlusPlugin.this.invokeMethodUIThread("OnDescriptorRead", map);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i2) {
            LogLevel logLevel = i2 == 0 ? LogLevel.DEBUG : LogLevel.ERROR;
            FlutterBluePlusPlugin.this.log(logLevel, "onDescriptorWrite:");
            FlutterBluePlusPlugin.this.log(logLevel, "  chr: " + FlutterBluePlusPlugin.this.uuidStr(bluetoothGattDescriptor.getCharacteristic().getUuid()));
            FlutterBluePlusPlugin.this.log(logLevel, "  desc: " + FlutterBluePlusPlugin.this.uuidStr(bluetoothGattDescriptor.getUuid()));
            FlutterBluePlusPlugin.this.log(logLevel, "  status: " + FlutterBluePlusPlugin.gattErrorString(i2) + " (" + i2 + ")");
            BluetoothGattService bluetoothGattServiceU = FlutterBluePlusPlugin.U(bluetoothGatt, bluetoothGattDescriptor.getCharacteristic());
            String address = bluetoothGatt.getDevice().getAddress();
            String strUuidStr = FlutterBluePlusPlugin.this.uuidStr(bluetoothGattDescriptor.getCharacteristic().getService().getUuid());
            String strUuidStr2 = FlutterBluePlusPlugin.this.uuidStr(bluetoothGattDescriptor.getCharacteristic().getUuid());
            String strUuidStr3 = FlutterBluePlusPlugin.this.uuidStr(bluetoothGattDescriptor.getUuid());
            String str = address + ":" + strUuidStr + ":" + strUuidStr2 + ":" + strUuidStr3 + ":" + (bluetoothGattServiceU != null ? FlutterBluePlusPlugin.this.uuidStr(bluetoothGattServiceU.getUuid()) : "");
            String str2 = FlutterBluePlusPlugin.this.mWriteDesc.get(str) != null ? (String) FlutterBluePlusPlugin.this.mWriteDesc.get(str) : "";
            FlutterBluePlusPlugin.this.mWriteDesc.remove(str);
            HashMap map = new HashMap();
            map.put("remote_id", address);
            map.put("service_uuid", strUuidStr);
            map.put("characteristic_uuid", strUuidStr2);
            map.put("descriptor_uuid", strUuidStr3);
            map.put("value", str2);
            map.put("success", Integer.valueOf(i2 == 0 ? 1 : 0));
            map.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, Integer.valueOf(i2));
            map.put("error_string", FlutterBluePlusPlugin.gattErrorString(i2));
            if (bluetoothGattServiceU != null) {
                map.put("primary_service_uuid", FlutterBluePlusPlugin.this.uuidStr(bluetoothGattServiceU.getUuid()));
            }
            FlutterBluePlusPlugin.this.invokeMethodUIThread("OnDescriptorWritten", map);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onMtuChanged(BluetoothGatt bluetoothGatt, int i2, int i3) {
            LogLevel logLevel = i3 == 0 ? LogLevel.DEBUG : LogLevel.ERROR;
            FlutterBluePlusPlugin.this.log(logLevel, "onMtuChanged:");
            FlutterBluePlusPlugin.this.log(logLevel, "  mtu: " + i2);
            FlutterBluePlusPlugin.this.log(logLevel, "  status: " + FlutterBluePlusPlugin.gattErrorString(i3) + " (" + i3 + ")");
            String address = bluetoothGatt.getDevice().getAddress();
            FlutterBluePlusPlugin.this.mMtu.put(address, Integer.valueOf(i2));
            HashMap map = new HashMap();
            map.put("remote_id", address);
            map.put("mtu", Integer.valueOf(i2));
            map.put("success", Integer.valueOf(i3 == 0 ? 1 : 0));
            map.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, Integer.valueOf(i3));
            map.put("error_string", FlutterBluePlusPlugin.gattErrorString(i3));
            FlutterBluePlusPlugin.this.invokeMethodUIThread("OnMtuChanged", map);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onReadRemoteRssi(BluetoothGatt bluetoothGatt, int i2, int i3) {
            LogLevel logLevel = i3 == 0 ? LogLevel.DEBUG : LogLevel.ERROR;
            FlutterBluePlusPlugin.this.log(logLevel, "onReadRemoteRssi:");
            FlutterBluePlusPlugin.this.log(logLevel, "  rssi: " + i2);
            FlutterBluePlusPlugin.this.log(logLevel, "  status: " + FlutterBluePlusPlugin.gattErrorString(i3) + " (" + i3 + ")");
            HashMap map = new HashMap();
            map.put("remote_id", bluetoothGatt.getDevice().getAddress());
            map.put("rssi", Integer.valueOf(i2));
            map.put("success", Integer.valueOf(i3 == 0 ? 1 : 0));
            map.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, Integer.valueOf(i3));
            map.put("error_string", FlutterBluePlusPlugin.gattErrorString(i3));
            FlutterBluePlusPlugin.this.invokeMethodUIThread("OnReadRssi", map);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onReliableWriteCompleted(BluetoothGatt bluetoothGatt, int i2) {
            LogLevel logLevel = i2 == 0 ? LogLevel.DEBUG : LogLevel.ERROR;
            FlutterBluePlusPlugin.this.log(logLevel, "onReliableWriteCompleted:");
            FlutterBluePlusPlugin.this.log(logLevel, "  status: " + FlutterBluePlusPlugin.gattErrorString(i2) + " (" + i2 + ")");
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i2) {
            LogLevel logLevel = i2 == 0 ? LogLevel.DEBUG : LogLevel.ERROR;
            FlutterBluePlusPlugin.this.log(logLevel, "onServicesDiscovered:");
            FlutterBluePlusPlugin.this.log(logLevel, "  count: " + bluetoothGatt.getServices().size());
            FlutterBluePlusPlugin.this.log(logLevel, "  status: " + i2 + FlutterBluePlusPlugin.gattErrorString(i2));
            ArrayList arrayList = new ArrayList();
            for (BluetoothGattService bluetoothGattService : bluetoothGatt.getServices()) {
                arrayList.add(FlutterBluePlusPlugin.this.M(bluetoothGatt.getDevice(), bluetoothGattService, null, bluetoothGatt));
                Iterator<BluetoothGattService> it = bluetoothGattService.getIncludedServices().iterator();
                while (it.hasNext()) {
                    arrayList.add(FlutterBluePlusPlugin.this.M(bluetoothGatt.getDevice(), it.next(), bluetoothGattService, bluetoothGatt));
                }
            }
            HashMap map = new HashMap();
            map.put("remote_id", bluetoothGatt.getDevice().getAddress());
            map.put(TmpConstant.DEVICE_MODEL_SERVICES, arrayList);
            map.put("success", Integer.valueOf(i2 == 0 ? 1 : 0));
            map.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, Integer.valueOf(i2));
            map.put("error_string", FlutterBluePlusPlugin.gattErrorString(i2));
            FlutterBluePlusPlugin.this.invokeMethodUIThread("OnDiscoveredServices", map);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            onCharacteristicChanged(bluetoothGatt, bluetoothGattCharacteristic, bluetoothGattCharacteristic.getValue());
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i2) {
            onCharacteristicRead(bluetoothGatt, bluetoothGattCharacteristic, bluetoothGattCharacteristic.getValue(), i2);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onDescriptorRead(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i2) {
            onDescriptorRead(bluetoothGatt, bluetoothGattDescriptor, i2, bluetoothGattDescriptor.getValue());
        }
    };

    /* renamed from: com.lib.flutter_blue_plus.FlutterBluePlusPlugin$5, reason: invalid class name */
    static /* synthetic */ class AnonymousClass5 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f18826a;

        static {
            int[] iArr = new int[LogLevel.values().length];
            f18826a = iArr;
            try {
                iArr[LogLevel.DEBUG.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f18826a[LogLevel.WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f18826a[LogLevel.ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    class ChrFound {
        public BluetoothGattCharacteristic characteristic;
        public String error;

        public ChrFound(BluetoothGattCharacteristic bluetoothGattCharacteristic, String str) {
            this.characteristic = bluetoothGattCharacteristic;
            this.error = str;
        }
    }

    enum LogLevel {
        NONE,
        ERROR,
        WARNING,
        INFO,
        DEBUG,
        VERBOSE
    }

    /* JADX INFO: Access modifiers changed from: private */
    interface OperationOnPermission {
        void op(boolean z2, String str);
    }

    static int I(int i2) {
        switch (i2) {
            case 10:
                return 6;
            case 11:
                return 3;
            case 12:
                return 4;
            case 13:
                return 5;
            default:
                return 0;
        }
    }

    static int N(int i2) {
        if (i2 != 11) {
            return i2 != 12 ? 0 : 2;
        }
        return 1;
    }

    static int P(int i2) {
        if (i2 != 0) {
            return i2 != 1 ? 2 : 1;
        }
        return 0;
    }

    static int Q(int i2) {
        return i2 != 2 ? 0 : 1;
    }

    static BluetoothGattService U(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        BluetoothGattService service = bluetoothGattCharacteristic.getService();
        if (service.getType() == 0) {
            return null;
        }
        for (BluetoothGattService bluetoothGattService : bluetoothGatt.getServices()) {
            Iterator<BluetoothGattService> it = bluetoothGattService.getIncludedServices().iterator();
            while (it.hasNext()) {
                if (it.next().getUuid().equals(service.getUuid())) {
                    return bluetoothGattService;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acquireMutex(@NonNull Semaphore semaphore) throws InterruptedException {
        boolean z2 = false;
        while (!z2) {
            try {
                semaphore.acquire();
                z2 = true;
            } catch (InterruptedException unused) {
                log(LogLevel.ERROR, "failed to acquire mutex, retrying");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String adapterStateString(int i2) {
        switch (i2) {
            case 10:
                return "off";
            case 11:
                return "turningOn";
            case 12:
                return "on";
            case 13:
                return "turningOff";
            default:
                return "UNKNOWN_ADAPTER_STATE (" + i2 + ")";
        }
    }

    private void askPermission(List<String> list, OperationOnPermission operationOnPermission) {
        if (list.isEmpty()) {
            operationOnPermission.op(true, null);
            return;
        }
        this.operationsOnPermission.put(Integer.valueOf(this.lastEventId), operationOnPermission);
        ActivityCompat.requestPermissions(this.activityBinding.getActivity(), (String[]) list.toArray(new String[0]), this.lastEventId);
        this.lastEventId++;
    }

    private static String bluetoothStatusString(int i2) {
        if (i2 == 0) {
            return "SUCCESS";
        }
        if (i2 == 1) {
            return "ERROR_BLUETOOTH_NOT_ENABLED";
        }
        if (i2 == 2) {
            return "ERROR_BLUETOOTH_NOT_ALLOWED";
        }
        if (i2 == 3) {
            return "ERROR_DEVICE_NOT_BONDED";
        }
        if (i2 == 6) {
            return "ERROR_MISSING_BLUETOOTH_CONNECT_PERMISSION";
        }
        if (i2 == Integer.MAX_VALUE) {
            return "ERROR_UNKNOWN";
        }
        if (i2 == 200) {
            return "ERROR_GATT_WRITE_NOT_ALLOWED";
        }
        if (i2 == 201) {
            return "ERROR_GATT_WRITE_REQUEST_BUSY";
        }
        switch (i2) {
            case 9:
                return "ERROR_PROFILE_SERVICE_NOT_BOUND";
            case 10:
                return "FEATURE_SUPPORTED";
            case 11:
                return "FEATURE_NOT_SUPPORTED";
            default:
                return "UNKNOWN_BLE_ERROR (" + i2 + ")";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String bondStateString(int i2) {
        switch (i2) {
            case 10:
                return "bond-none";
            case 11:
                return "bonding";
            case 12:
                return "bonded";
            default:
                return "UNKNOWN_BOND_STATE (" + i2 + ")";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String bytesToHex(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b2 : bArr) {
            sb.append(Character.forDigit((b2 >> 4) & 15, 16));
            sb.append(Character.forDigit(b2 & 15, 16));
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String connectionStateString(int i2) {
        if (i2 == 0) {
            return "disconnected";
        }
        if (i2 == 1) {
            return "connecting";
        }
        if (i2 == 2) {
            return "connected";
        }
        if (i2 == 3) {
            return "disconnecting";
        }
        return "UNKNOWN_CONNECTION_STATE (" + i2 + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disconnectAllDevices(String str) {
        log(LogLevel.DEBUG, "disconnectAllDevices(" + str + ")");
        for (BluetoothGatt bluetoothGatt : this.mConnectedDevices.values()) {
            if (str == "adapterTurnOff") {
                this.mGattCallback.onConnectionStateChange(bluetoothGatt, 0, 0);
            } else {
                String address = bluetoothGatt.getDevice().getAddress();
                LogLevel logLevel = LogLevel.DEBUG;
                log(logLevel, "calling disconnect: " + address);
                bluetoothGatt.disconnect();
                log(logLevel, "calling close: " + address);
                bluetoothGatt.close();
            }
        }
        this.mConnectedDevices.clear();
        this.mCurrentlyConnectingDevices.clear();
        this.mBondingDevices.clear();
        this.mMtu.clear();
        this.mWriteChr.clear();
        this.mWriteDesc.clear();
        this.mAutoConnected.clear();
    }

    private void ensurePermissions(List<String> list, OperationOnPermission operationOnPermission) {
        ArrayList arrayList = new ArrayList();
        for (String str : list) {
            if (str != null && ContextCompat.checkSelfPermission(this.context, str) != 0) {
                arrayList.add(str);
            }
        }
        if (arrayList.isEmpty()) {
            operationOnPermission.op(true, null);
        } else {
            askPermission(arrayList, operationOnPermission);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean filterKeywords(List<String> list, String str) {
        if (list.isEmpty()) {
            return true;
        }
        if (str == null) {
            return false;
        }
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            if (str.contains(it.next())) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String gattErrorString(int i2) {
        if (i2 == 257) {
            return "GATT_FAILURE";
        }
        switch (i2) {
            case 0:
                return "GATT_SUCCESS";
            case 1:
                return "GATT_INVALID_HANDLE";
            case 2:
                return "GATT_READ_NOT_PERMITTED";
            case 3:
                return "GATT_WRITE_NOT_PERMITTED";
            case 4:
                return "GATT_INVALID_PDU";
            case 5:
                return "GATT_INSUFFICIENT_AUTHENTICATION";
            case 6:
                return "GATT_REQUEST_NOT_SUPPORTED";
            case 7:
                return "GATT_INVALID_OFFSET";
            case 8:
                return "GATT_INSUFFICIENT_AUTHORIZATION";
            case 9:
                return "GATT_PREPARE_QUEUE_FULL";
            case 10:
                return "GATT_ATTR_NOT_FOUND";
            case 11:
                return "GATT_ATTR_NOT_LONG";
            case 12:
                return "GATT_INSUFFICIENT_KEY_SIZE";
            case 13:
                return "GATT_INVALID_ATTRIBUTE_LENGTH";
            case 14:
                return "GATT_UNLIKELY";
            case 15:
                return "GATT_INSUFFICIENT_ENCRYPTION";
            case 16:
                return "GATT_UNSUPPORTED_GROUP";
            case 17:
                return "GATT_INSUFFICIENT_RESOURCES";
            default:
                switch (i2) {
                    case 128:
                        return "GATT_NO_RESOURCES";
                    case 129:
                        return "GATT_INTERNAL_ERROR";
                    case 130:
                        return "GATT_WRONG_STATE";
                    case 131:
                        return "GATT_DB_FULL";
                    case 132:
                        return "GATT_BUSY";
                    case 133:
                        return "GATT_ERROR";
                    case 134:
                        return "GATT_CMD_STARTED";
                    case 135:
                        return "GATT_ILLEGAL_PARAMETER";
                    case 136:
                        return "GATT_PENDING";
                    case 137:
                        return "GATT_AUTH_FAIL";
                    case 138:
                        return "GATT_MORE";
                    case 139:
                        return "GATT_INVALID_CFG";
                    case 140:
                        return "GATT_SERVICE_STARTED";
                    case 141:
                        return "GATT_ENCRYPTED_NO_MITM";
                    case 142:
                        return "GATT_NOT_ENCRYPTED";
                    case 143:
                        return "GATT_CONNECTION_CONGESTED";
                    default:
                        return "UNKNOWN_GATT_ERROR (" + i2 + ")";
                }
        }
    }

    private BluetoothGattCharacteristic getCharacteristicFromArray(String str, List<BluetoothGattCharacteristic> list) {
        for (BluetoothGattCharacteristic bluetoothGattCharacteristic : list) {
            if (uuid128(bluetoothGattCharacteristic.getUuid()).equals(uuid128(str))) {
                return bluetoothGattCharacteristic;
            }
        }
        return null;
    }

    private BluetoothGattDescriptor getDescriptorFromArray(String str, List<BluetoothGattDescriptor> list) {
        for (BluetoothGattDescriptor bluetoothGattDescriptor : list) {
            if (uuid128(bluetoothGattDescriptor.getUuid()).equals(uuid128(str))) {
                return bluetoothGattDescriptor;
            }
        }
        return null;
    }

    private int getMaxPayload(String str, int i2, boolean z2) {
        if (i2 != 1 && z2) {
            return 512;
        }
        Integer num = this.mMtu.get(str);
        if (num == null) {
            num = 23;
        }
        return Math.min(num.intValue() - 3, 512);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ScanCallback getScanCallback() {
        if (this.scanCallback == null) {
            this.scanCallback = new ScanCallback() { // from class: com.lib.flutter_blue_plus.FlutterBluePlusPlugin.3
                @Override // android.bluetooth.le.ScanCallback
                public void onBatchScanResults(List<ScanResult> list) {
                    super.onBatchScanResults(list);
                }

                @Override // android.bluetooth.le.ScanCallback
                public void onScanFailed(int i2) {
                    FlutterBluePlusPlugin.this.log(LogLevel.ERROR, "onScanFailed: " + FlutterBluePlusPlugin.scanFailedString(i2));
                    super.onScanFailed(i2);
                    HashMap map = new HashMap();
                    map.put("advertisements", new ArrayList());
                    map.put("success", 0);
                    map.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, Integer.valueOf(i2));
                    map.put("error_string", FlutterBluePlusPlugin.scanFailedString(i2));
                    FlutterBluePlusPlugin.this.invokeMethodUIThread("OnScanResponse", map);
                }

                @Override // android.bluetooth.le.ScanCallback
                public void onScanResult(int i2, ScanResult scanResult) {
                    FlutterBluePlusPlugin.this.log(LogLevel.VERBOSE, "onScanResult");
                    super.onScanResult(i2, scanResult);
                    BluetoothDevice device = scanResult.getDevice();
                    String address = device.getAddress();
                    ScanRecord scanRecord = scanResult.getScanRecord();
                    String strBytesToHex = scanRecord != null ? FlutterBluePlusPlugin.bytesToHex(scanRecord.getBytes()) : "";
                    if (!((Boolean) FlutterBluePlusPlugin.this.mScanFilters.get("continuous_updates")).booleanValue()) {
                        boolean z2 = FlutterBluePlusPlugin.this.mAdvSeen.containsKey(address) && ((String) FlutterBluePlusPlugin.this.mAdvSeen.get(address)).equals(strBytesToHex);
                        FlutterBluePlusPlugin.this.mAdvSeen.put(address, strBytesToHex);
                        if (z2) {
                            return;
                        }
                    }
                    if (FlutterBluePlusPlugin.this.filterKeywords((List) FlutterBluePlusPlugin.this.mScanFilters.get("with_keywords"), scanRecord != null ? scanRecord.getDeviceName() : "")) {
                        if (!((Boolean) FlutterBluePlusPlugin.this.mScanFilters.get("continuous_updates")).booleanValue() || FlutterBluePlusPlugin.this.scanCountIncrement(address) % ((Integer) FlutterBluePlusPlugin.this.mScanFilters.get("continuous_divisor")).intValue() == 0) {
                            HashMap map = new HashMap();
                            map.put("advertisements", Arrays.asList(FlutterBluePlusPlugin.this.R(device, scanResult)));
                            FlutterBluePlusPlugin.this.invokeMethodUIThread("OnScanResponse", map);
                        }
                    }
                }
            };
        }
        return this.scanCallback;
    }

    private BluetoothGattService getServiceFromArray(String str, List<BluetoothGattService> list) {
        for (BluetoothGattService bluetoothGattService : list) {
            if (uuid128(bluetoothGattService.getUuid()).equals(uuid128(str))) {
                return bluetoothGattService;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String hciStatusString(int i2) {
        if (i2 == 133) {
            return "ANDROID_SPECIFIC_ERROR";
        }
        if (i2 == 257) {
            return "FAILURE_REGISTERING_CLIENT";
        }
        switch (i2) {
            case 0:
                return "SUCCESS";
            case 1:
                return "UNKNOWN_COMMAND";
            case 2:
                return "UNKNOWN_CONNECTION_IDENTIFIER";
            case 3:
                return "HARDWARE_FAILURE";
            case 4:
                return "PAGE_TIMEOUT";
            case 5:
                return "AUTHENTICATION_FAILURE";
            case 6:
                return "PIN_OR_KEY_MISSING";
            case 7:
                return "MEMORY_FULL";
            case 8:
                return "LINK_SUPERVISION_TIMEOUT";
            case 9:
                return "CONNECTION_LIMIT_EXCEEDED";
            case 10:
                return "MAX_NUM_OF_CONNECTIONS_EXCEEDED";
            case 11:
                return "CONNECTION_ALREADY_EXISTS";
            case 12:
                return "COMMAND_DISALLOWED";
            case 13:
                return "CONNECTION_REJECTED_LIMITED_RESOURCES";
            case 14:
                return "CONNECTION_REJECTED_SECURITY_REASONS";
            case 15:
                return "CONNECTION_REJECTED_UNACCEPTABLE_MAC_ADDRESS";
            case 16:
                return "CONNECTION_ACCEPT_TIMEOUT_EXCEEDED";
            case 17:
                return "UNSUPPORTED_PARAMETER_VALUE";
            case 18:
                return "INVALID_COMMAND_PARAMETERS";
            case 19:
                return "REMOTE_USER_TERMINATED_CONNECTION";
            case 20:
                return "REMOTE_DEVICE_TERMINATED_CONNECTION_LOW_RESOURCES";
            case 21:
                return "REMOTE_DEVICE_TERMINATED_CONNECTION_POWER_OFF";
            case 22:
                return "CONNECTION_TERMINATED_BY_LOCAL_HOST";
            case 23:
                return "REPEATED_ATTEMPTS";
            case 24:
                return "PAIRING_NOT_ALLOWED";
            case 25:
                return "UNKNOWN_LMP_PDU";
            case 26:
                return "UNSUPPORTED_REMOTE_FEATURE";
            case 27:
                return "SCO_OFFSET_REJECTED";
            case 28:
                return "SCO_INTERVAL_REJECTED";
            case 29:
                return "SCO_AIR_MODE_REJECTED";
            case 30:
                return "INVALID_LMP_OR_LL_PARAMETERS";
            case 31:
                return "UNSPECIFIED";
            case 32:
                return "UNSUPPORTED_LMP_OR_LL_PARAMETER_VALUE";
            case 33:
                return "ROLE_CHANGE_NOT_ALLOWED";
            case 34:
                return "LMP_OR_LL_RESPONSE_TIMEOUT";
            case 35:
                return "LMP_OR_LL_ERROR_TRANS_COLLISION";
            case 36:
                return "LMP_PDU_NOT_ALLOWED";
            case 37:
                return "ENCRYPTION_MODE_NOT_ACCEPTABLE";
            case 38:
                return "LINK_KEY_CANNOT_BE_EXCHANGED";
            case 39:
                return "REQUESTED_QOS_NOT_SUPPORTED";
            case 40:
                return "INSTANT_PASSED";
            case 41:
                return "PAIRING_WITH_UNIT_KEY_NOT_SUPPORTED";
            case 42:
                return "DIFFERENT_TRANSACTION_COLLISION";
            case 43:
                return "UNDEFINED_0x2B";
            case 44:
                return "QOS_UNACCEPTABLE_PARAMETER";
            case 45:
                return "QOS_REJECTED";
            case 46:
                return "CHANNEL_CLASSIFICATION_NOT_SUPPORTED";
            case 47:
                return "INSUFFICIENT_SECURITY";
            case 48:
                return "PARAMETER_OUT_OF_RANGE";
            case 49:
                return "UNDEFINED_0x31";
            case 50:
                return "ROLE_SWITCH_PENDING";
            case 51:
                return "UNDEFINED_0x33";
            case 52:
                return "RESERVED_SLOT_VIOLATION";
            case 53:
                return "ROLE_SWITCH_FAILED";
            case 54:
                return "INQUIRY_RESPONSE_TOO_LARGE";
            case 55:
                return "SECURE_SIMPLE_PAIRING_NOT_SUPPORTED";
            case 56:
                return "HOST_BUSY_PAIRING";
            case 57:
                return "CONNECTION_REJECTED_NO_SUITABLE_CHANNEL";
            case 58:
                return "CONTROLLER_BUSY";
            case 59:
                return "UNACCEPTABLE_CONNECTION_PARAMETERS";
            case 60:
                return "ADVERTISING_TIMEOUT";
            case 61:
                return "CONNECTION_TERMINATED_MIC_FAILURE";
            case 62:
                return "CONNECTION_FAILED_ESTABLISHMENT";
            case 63:
                return "MAC_CONNECTION_FAILED";
            case 64:
                return "COARSE_CLOCK_ADJUSTMENT_REJECTED";
            case 65:
                return "TYPE0_SUBMAP_NOT_DEFINED";
            case 66:
                return "UNKNOWN_ADVERTISING_IDENTIFIER";
            case 67:
                return "LIMIT_REACHED";
            case 68:
                return "OPERATION_CANCELLED_BY_HOST";
            case 69:
                return "PACKET_TOO_LONG";
            default:
                return "UNKNOWN_HCI_ERROR (" + i2 + ")";
        }
    }

    private static byte[] hexToBytes(String str) {
        if (str == null) {
            return new byte[0];
        }
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i2 = 0; i2 < length; i2 += 2) {
            bArr[i2 / 2] = (byte) ((Character.digit(str.charAt(i2), 16) << 4) + Character.digit(str.charAt(i2 + 1), 16));
        }
        return bArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invokeMethodUIThread(final String str, final HashMap<String, Object> map) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.lib.flutter_blue_plus.i
            @Override // java.lang.Runnable
            public final void run() {
                this.f18834a.lambda$invokeMethodUIThread$6(str, map);
            }
        });
    }

    private boolean isAdapterOn() {
        try {
            return this.mBluetoothAdapter.getState() == 12;
        } catch (Exception unused) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$invokeMethodUIThread$6(String str, HashMap map) {
        MethodChannel methodChannel = this.methodChannel;
        if (methodChannel != null) {
            methodChannel.invokeMethod(str, map);
            return;
        }
        log(LogLevel.WARNING, "invokeMethodUIThread: tried to call method on closed channel: " + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMethodCall$0(MethodChannel.Result result, boolean z2, String str) {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        String name = bluetoothAdapter != null ? bluetoothAdapter.getName() : "N/A";
        if (name == null) {
            name = "";
        }
        result.success(name);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMethodCall$1(MethodChannel.Result result, boolean z2, String str) {
        if (!z2) {
            result.error("turnOn", String.format("FlutterBluePlus requires %s permission", str), null);
        } else if (this.mBluetoothAdapter.isEnabled()) {
            result.success(Boolean.FALSE);
        } else {
            this.activityBinding.getActivity().startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 1879842617);
            result.success(Boolean.TRUE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMethodCall$2(MethodChannel.Result result, boolean z2, String str) {
        if (!z2) {
            result.error("turnOff", String.format("FlutterBluePlus requires %s permission", str), null);
        } else if (this.mBluetoothAdapter.isEnabled()) {
            result.success(Boolean.valueOf(this.mBluetoothAdapter.disable()));
        } else {
            result.success(Boolean.TRUE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMethodCall$3(MethodChannel.Result result, int i2, boolean z2, List list, List list2, List list3, List list4, List list5, List list6, HashMap map, boolean z3, String str) {
        if (!z3) {
            result.error("startScan", String.format("FlutterBluePlus requires %s permission", str), null);
            return;
        }
        if (!isAdapterOn()) {
            result.error("startScan", String.format("bluetooth must be turned on", new Object[0]), null);
            return;
        }
        BluetoothLeScanner bluetoothLeScanner = this.mBluetoothAdapter.getBluetoothLeScanner();
        if (bluetoothLeScanner == null) {
            result.error("startScan", String.format("getBluetoothLeScanner() is null. Is the Adapter on?", new Object[0]), null);
            return;
        }
        ScanSettings.Builder builder = new ScanSettings.Builder();
        builder.setScanMode(i2);
        if (Build.VERSION.SDK_INT >= 26) {
            builder.setPhy(255);
            builder.setLegacy(z2);
        }
        ScanSettings scanSettingsBuild = builder.build();
        ArrayList arrayList = new ArrayList();
        for (int i3 = 0; i3 < list.size(); i3++) {
            arrayList.add(new ScanFilter.Builder().setServiceUuid(ParcelUuid.fromString(uuid128(list.get(i3)))).build());
        }
        for (int i4 = 0; i4 < list2.size(); i4++) {
            arrayList.add(new ScanFilter.Builder().setDeviceAddress((String) list2.get(i4)).build());
        }
        for (int i5 = 0; i5 < list3.size(); i5++) {
            arrayList.add(new ScanFilter.Builder().setDeviceName((String) list3.get(i5)).build());
        }
        if (Build.VERSION.SDK_INT >= 33 && list4.size() > 0) {
            ScanFilter scanFilterBuild = new ScanFilter.Builder().setAdvertisingDataType(8).build();
            ScanFilter scanFilterBuild2 = new ScanFilter.Builder().setAdvertisingDataType(9).build();
            arrayList.add(scanFilterBuild);
            arrayList.add(scanFilterBuild2);
        }
        for (int i6 = 0; i6 < list5.size(); i6++) {
            HashMap map2 = (HashMap) list5.get(i6);
            int iIntValue = ((Integer) map2.get("manufacturer_id")).intValue();
            byte[] bArrHexToBytes = hexToBytes((String) map2.get("data"));
            byte[] bArrHexToBytes2 = hexToBytes((String) map2.get("mask"));
            arrayList.add(bArrHexToBytes2.length == 0 ? new ScanFilter.Builder().setManufacturerData(iIntValue, bArrHexToBytes).build() : new ScanFilter.Builder().setManufacturerData(iIntValue, bArrHexToBytes, bArrHexToBytes2).build());
        }
        for (int i7 = 0; i7 < list6.size(); i7++) {
            HashMap map3 = (HashMap) list6.get(i7);
            ParcelUuid parcelUuidFromString = ParcelUuid.fromString(uuid128((String) map3.get("service")));
            byte[] bArrHexToBytes3 = hexToBytes((String) map3.get("data"));
            byte[] bArrHexToBytes4 = hexToBytes((String) map3.get("mask"));
            arrayList.add(bArrHexToBytes4.length == 0 ? new ScanFilter.Builder().setServiceData(parcelUuidFromString, bArrHexToBytes3).build() : new ScanFilter.Builder().setServiceData(parcelUuidFromString, bArrHexToBytes3, bArrHexToBytes4).build());
        }
        this.mScanFilters = map;
        this.mAdvSeen.clear();
        this.mScanCounts.clear();
        bluetoothLeScanner.startScan(arrayList, scanSettingsBuild, getScanCallback());
        this.mIsScanning = true;
        result.success(Boolean.TRUE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMethodCall$4(MethodChannel.Result result, boolean z2, String str) {
        if (!z2) {
            result.error("getSystemDevices", String.format("FlutterBluePlus requires %s permission", str), null);
            return;
        }
        List<BluetoothDevice> connectedDevices = this.mBluetoothManager.getConnectedDevices(7);
        ArrayList arrayList = new ArrayList();
        Iterator<BluetoothDevice> it = connectedDevices.iterator();
        while (it.hasNext()) {
            arrayList.add(L(it.next()));
        }
        HashMap map = new HashMap();
        map.put(TmpConstant.DEVICES, arrayList);
        result.success(map);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMethodCall$5(MethodChannel.Result result, String str, boolean z2, boolean z3, String str2) throws InterruptedException {
        if (!z3) {
            result.error(BaseMonitor.ALARM_POINT_CONNECT, String.format("FlutterBluePlus requires %s for new connection", str2), null);
            return;
        }
        if (!isAdapterOn()) {
            result.error(BaseMonitor.ALARM_POINT_CONNECT, String.format("bluetooth must be turned on", new Object[0]), null);
            return;
        }
        if (this.mCurrentlyConnectingDevices.get(str) != null) {
            log(LogLevel.DEBUG, "already connecting");
            result.success(Boolean.TRUE);
            return;
        }
        if (this.mConnectedDevices.get(str) != null) {
            log(LogLevel.DEBUG, "already connected");
            result.success(Boolean.FALSE);
            return;
        }
        waitIfBonding();
        BluetoothGatt bluetoothGattConnectGatt = this.mBluetoothAdapter.getRemoteDevice(str).connectGatt(this.context, z2, this.mGattCallback, 2);
        if (bluetoothGattConnectGatt == null) {
            result.error(BaseMonitor.ALARM_POINT_CONNECT, String.format("device.connectGatt returned null", new Object[0]), null);
            return;
        }
        this.mCurrentlyConnectingDevices.put(str, bluetoothGattConnectGatt);
        if (z2) {
            this.mAutoConnected.put(str, bluetoothGattConnectGatt);
        } else {
            this.mAutoConnected.remove(str);
        }
        result.success(Boolean.TRUE);
    }

    private ChrFound locateCharacteristic(BluetoothGatt bluetoothGatt, String str, String str2, String str3) {
        BluetoothGattService serviceFromArray;
        boolean z2 = str3 != null;
        if (str3 == null) {
            str3 = str;
        }
        BluetoothGattService serviceFromArray2 = getServiceFromArray(str3, bluetoothGatt.getServices());
        if (serviceFromArray2 == null) {
            return new ChrFound(null, "primary service not found '" + str3 + "'");
        }
        if (z2) {
            serviceFromArray = getServiceFromArray(str, serviceFromArray2.getIncludedServices());
            if (serviceFromArray == null) {
                return new ChrFound(null, "secondary service not found '" + str + "' (primary service '" + str3 + "')");
            }
        } else {
            serviceFromArray = null;
        }
        if (serviceFromArray != null) {
            serviceFromArray2 = serviceFromArray;
        }
        BluetoothGattCharacteristic characteristicFromArray = getCharacteristicFromArray(str2, serviceFromArray2.getCharacteristics());
        if (characteristicFromArray != null) {
            return new ChrFound(characteristicFromArray, null);
        }
        return new ChrFound(null, "characteristic not found in service (chr: '" + str2 + "' svc: '" + str + "')");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void log(LogLevel logLevel, String str) {
        if (logLevel.ordinal() > this.logLevel.ordinal()) {
            return;
        }
        int i2 = AnonymousClass5.f18826a[logLevel.ordinal()];
        if (i2 == 1) {
            Log.d(TAG, "[FBP] " + str);
            return;
        }
        if (i2 == 2) {
            Log.w(TAG, "[FBP] " + str);
            return;
        }
        if (i2 != 3) {
            Log.d(TAG, "[FBP] " + str);
            return;
        }
        Log.e(TAG, "[FBP] " + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int scanCountIncrement(String str) {
        if (this.mScanCounts.get(str) == null) {
            this.mScanCounts.put(str, 0);
        }
        int iIntValue = this.mScanCounts.get(str).intValue();
        this.mScanCounts.put(str, Integer.valueOf(iIntValue + 1));
        return iIntValue;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String scanFailedString(int i2) {
        switch (i2) {
            case 1:
                return "SCAN_FAILED_ALREADY_STARTED";
            case 2:
                return "SCAN_FAILED_APPLICATION_REGISTRATION_FAILED";
            case 3:
                return "SCAN_FAILED_INTERNAL_ERROR";
            case 4:
                return "SCAN_FAILED_FEATURE_UNSUPPORTED";
            case 5:
                return "SCAN_FAILED_OUT_OF_HARDWARE_RESOURCES";
            case 6:
                return "SCAN_FAILED_SCANNING_TOO_FREQUENTLY";
            default:
                return "UNKNOWN_SCAN_ERROR (" + i2 + ")";
        }
    }

    private void waitIfBonding() throws InterruptedException {
        if (this.mBondingDevices.isEmpty()) {
            return;
        }
        log(LogLevel.DEBUG, "[FBP] waiting for bonding to complete...");
        try {
            Thread.sleep(50L);
        } catch (Exception unused) {
        }
        log(LogLevel.DEBUG, "[FBP] bonding completed");
    }

    HashMap J(BluetoothDevice bluetoothDevice, BluetoothGattCharacteristic bluetoothGattCharacteristic, BluetoothGatt bluetoothGatt) {
        ArrayList arrayList = new ArrayList();
        Iterator<BluetoothGattDescriptor> it = bluetoothGattCharacteristic.getDescriptors().iterator();
        while (it.hasNext()) {
            arrayList.add(K(bluetoothDevice, it.next(), bluetoothGatt));
        }
        BluetoothGattService bluetoothGattServiceU = U(bluetoothGatt, bluetoothGattCharacteristic);
        HashMap map = new HashMap();
        map.put("remote_id", bluetoothDevice.getAddress());
        map.put("service_uuid", uuidStr(bluetoothGattCharacteristic.getService().getUuid()));
        map.put("characteristic_uuid", uuidStr(bluetoothGattCharacteristic.getUuid()));
        map.put("descriptors", arrayList);
        map.put(TmpConstant.DEVICE_MODEL_PROPERTIES, O(bluetoothGattCharacteristic.getProperties()));
        if (bluetoothGattServiceU != null) {
            map.put("primary_service_uuid", uuidStr(bluetoothGattServiceU.getUuid()));
        }
        return map;
    }

    HashMap K(BluetoothDevice bluetoothDevice, BluetoothGattDescriptor bluetoothGattDescriptor, BluetoothGatt bluetoothGatt) {
        BluetoothGattService bluetoothGattServiceU = U(bluetoothGatt, bluetoothGattDescriptor.getCharacteristic());
        HashMap map = new HashMap();
        map.put("remote_id", bluetoothDevice.getAddress());
        map.put("descriptor_uuid", uuidStr(bluetoothGattDescriptor.getUuid()));
        map.put("characteristic_uuid", uuidStr(bluetoothGattDescriptor.getCharacteristic().getUuid()));
        map.put("service_uuid", uuidStr(bluetoothGattDescriptor.getCharacteristic().getService().getUuid()));
        if (bluetoothGattServiceU != null) {
            map.put("primary_service_uuid", uuidStr(bluetoothGattServiceU.getUuid()));
        }
        return map;
    }

    HashMap L(BluetoothDevice bluetoothDevice) {
        HashMap map = new HashMap();
        map.put("remote_id", bluetoothDevice.getAddress());
        map.put("platform_name", bluetoothDevice.getName());
        return map;
    }

    HashMap M(BluetoothDevice bluetoothDevice, BluetoothGattService bluetoothGattService, BluetoothGattService bluetoothGattService2, BluetoothGatt bluetoothGatt) {
        ArrayList arrayList = new ArrayList();
        Iterator<BluetoothGattCharacteristic> it = bluetoothGattService.getCharacteristics().iterator();
        while (it.hasNext()) {
            arrayList.add(J(bluetoothDevice, it.next(), bluetoothGatt));
        }
        HashMap map = new HashMap();
        map.put("remote_id", bluetoothDevice.getAddress());
        map.put("service_uuid", uuidStr(bluetoothGattService.getUuid()));
        map.put("characteristics", arrayList);
        if (bluetoothGattService2 != null) {
            map.put("primary_service_uuid", uuidStr(bluetoothGattService2.getUuid()));
        }
        return map;
    }

    HashMap O(int i2) {
        HashMap map = new HashMap();
        map.put(AlinkConstants.KEY_BROADCAST, Integer.valueOf((i2 & 1) != 0 ? 1 : 0));
        map.put("read", Integer.valueOf((i2 & 2) != 0 ? 1 : 0));
        map.put("write_without_response", Integer.valueOf((i2 & 4) != 0 ? 1 : 0));
        map.put("write", Integer.valueOf((i2 & 8) != 0 ? 1 : 0));
        map.put(AgooConstants.MESSAGE_NOTIFICATION, Integer.valueOf((i2 & 16) != 0 ? 1 : 0));
        map.put("indicate", Integer.valueOf((i2 & 32) != 0 ? 1 : 0));
        map.put("authenticated_signed_writes", Integer.valueOf((i2 & 64) != 0 ? 1 : 0));
        map.put("extended_properties", Integer.valueOf((i2 & 128) != 0 ? 1 : 0));
        map.put("notify_encryption_required", Integer.valueOf((i2 & 256) != 0 ? 1 : 0));
        map.put("indicate_encryption_required", Integer.valueOf((i2 & 512) != 0 ? 1 : 0));
        return map;
    }

    HashMap R(BluetoothDevice bluetoothDevice, ScanResult scanResult) {
        ScanRecord scanRecord = scanResult.getScanRecord();
        boolean zIsConnectable = Build.VERSION.SDK_INT >= 26 ? scanResult.isConnectable() : true;
        String deviceName = scanRecord != null ? scanRecord.getDeviceName() : null;
        int txPowerLevel = scanRecord != null ? scanRecord.getTxPowerLevel() : Integer.MIN_VALUE;
        int iS = scanRecord != null ? S(scanRecord) : 0;
        Map mapT = scanRecord != null ? T(scanRecord) : null;
        List<ParcelUuid> serviceUuids = scanRecord != null ? scanRecord.getServiceUuids() : null;
        Map<ParcelUuid, byte[]> serviceData = scanRecord != null ? scanRecord.getServiceData() : null;
        HashMap map = new HashMap();
        if (mapT != null) {
            for (Map.Entry entry : mapT.entrySet()) {
                map.put((Integer) entry.getKey(), bytesToHex((byte[]) entry.getValue()));
            }
        }
        HashMap map2 = new HashMap();
        if (serviceData != null) {
            for (Map.Entry<ParcelUuid, byte[]> entry2 : serviceData.entrySet()) {
                map2.put(uuidStr(entry2.getKey().getUuid()), bytesToHex(entry2.getValue()));
            }
        }
        ArrayList arrayList = new ArrayList();
        if (serviceUuids != null) {
            Iterator<ParcelUuid> it = serviceUuids.iterator();
            while (it.hasNext()) {
                arrayList.add(uuidStr(it.next().getUuid()));
            }
        }
        HashMap map3 = new HashMap();
        if (bluetoothDevice.getAddress() != null) {
            map3.put("remote_id", bluetoothDevice.getAddress());
        }
        if (bluetoothDevice.getName() != null) {
            map3.put("platform_name", bluetoothDevice.getName());
        }
        if (zIsConnectable) {
            map3.put("connectable", 1);
        }
        if (deviceName != null) {
            map3.put("adv_name", deviceName);
        }
        if (txPowerLevel != Integer.MIN_VALUE) {
            map3.put("tx_power_level", Integer.valueOf(txPowerLevel));
        }
        if (iS != 0) {
            map3.put("appearance", Integer.valueOf(iS));
        }
        if (mapT != null) {
            map3.put("manufacturer_data", map);
        }
        if (serviceData != null) {
            map3.put("service_data", map2);
        }
        if (serviceUuids != null) {
            map3.put("service_uuids", arrayList);
        }
        if (scanResult.getRssi() != 0) {
            map3.put("rssi", Integer.valueOf(scanResult.getRssi()));
        }
        return map3;
    }

    int S(ScanRecord scanRecord) {
        int i2;
        int i3;
        if (Build.VERSION.SDK_INT >= 33) {
            Map advertisingDataMap = scanRecord.getAdvertisingDataMap();
            if (advertisingDataMap.containsKey(25)) {
                byte[] bArr = (byte[]) advertisingDataMap.get(25);
                if (bArr.length == 2) {
                    return ((bArr[1] & 255) * 256) + (bArr[0] & 255);
                }
            }
            return 0;
        }
        byte[] bytes = scanRecord.getBytes();
        int i4 = 0;
        while (i4 < bytes.length && (i2 = bytes[i4] & 255) > 0 && i4 + i2 < bytes.length && (i3 = bytes[i4 + 1] & 255) != 0) {
            if (i3 == 25 && i2 == 3) {
                return (bytes[i4 + 2] & 255) | ((bytes[i4 + 3] & 255) << 8);
            }
            i4 += i2 + 1;
        }
        return 0;
    }

    Map T(ScanRecord scanRecord) {
        int i2;
        byte[] bytes = scanRecord.getBytes();
        HashMap map = new HashMap();
        int i3 = 0;
        while (i3 < bytes.length && (i2 = bytes[i3] & 255) > 0 && i3 + i2 < bytes.length) {
            if ((bytes[i3 + 1] & 255) == 255 && i2 >= 3) {
                int i4 = (255 & bytes[i3 + 2]) | ((bytes[i3 + 3] & 255) << 8);
                int i5 = i2 - 3;
                int i6 = i3 + 4;
                if (map.containsKey(Integer.valueOf(i4))) {
                    byte[] bArr = (byte[]) map.get(Integer.valueOf(i4));
                    byte[] bArr2 = new byte[bArr.length + i5];
                    System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                    System.arraycopy(bytes, i6, bArr2, bArr.length, i5);
                    map.put(Integer.valueOf(i4), bArr2);
                } else {
                    byte[] bArr3 = new byte[i5];
                    System.arraycopy(bytes, i6, bArr3, 0, i5);
                    map.put(Integer.valueOf(i4), bArr3);
                }
            }
            i3 += i2 + 1;
        }
        return map;
    }

    @Override // io.flutter.plugin.common.PluginRegistry.ActivityResultListener
    public boolean onActivityResult(int i2, int i3, Intent intent) {
        if (i2 != 1879842617) {
            return false;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_accepted", Boolean.valueOf(i3 == -1));
        invokeMethodUIThread("OnTurnOnResponse", map);
        return true;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
        log(LogLevel.DEBUG, "onAttachedToActivity");
        this.activityBinding = activityPluginBinding;
        activityPluginBinding.addRequestPermissionsResultListener(this);
        this.activityBinding.addActivityResultListener(this);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        log(LogLevel.DEBUG, "onAttachedToEngine");
        this.pluginBinding = flutterPluginBinding;
        this.context = (Application) flutterPluginBinding.getApplicationContext();
        MethodChannel methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_blue_plus/methods");
        this.methodChannel = methodChannel;
        methodChannel.setMethodCallHandler(this);
        this.context.registerReceiver(this.mBluetoothAdapterStateReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        this.context.registerReceiver(this.mBluetoothBondStateReceiver, new IntentFilter("android.bluetooth.device.action.BOND_STATE_CHANGED"));
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        log(LogLevel.DEBUG, "onDetachedFromActivity");
        this.activityBinding.removeRequestPermissionsResultListener(this);
        this.activityBinding = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        log(LogLevel.DEBUG, "onDetachedFromActivityForConfigChanges");
        onDetachedFromActivity();
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        BluetoothLeScanner bluetoothLeScanner;
        LogLevel logLevel = LogLevel.DEBUG;
        log(logLevel, "onDetachedFromEngine");
        invokeMethodUIThread("OnDetachedFromEngine", new HashMap<>());
        this.pluginBinding = null;
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter != null && this.mIsScanning && (bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner()) != null) {
            log(logLevel, "calling stopScan (OnDetachedFromEngine)");
            bluetoothLeScanner.stopScan(getScanCallback());
            this.mIsScanning = false;
        }
        disconnectAllDevices("onDetachedFromEngine");
        this.context.unregisterReceiver(this.mBluetoothBondStateReceiver);
        this.context.unregisterReceiver(this.mBluetoothAdapterStateReceiver);
        this.context = null;
        this.methodChannel.setMethodCallHandler(null);
        this.methodChannel = null;
        this.mBluetoothAdapter = null;
        this.mBluetoothManager = null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:127:0x01f8  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0216 A[Catch: all -> 0x0040, Exception -> 0x0044, TRY_ENTER, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:131:0x021b A[Catch: all -> 0x0040, Exception -> 0x0044, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:138:0x024d A[Catch: all -> 0x0040, Exception -> 0x0044, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0294 A[Catch: all -> 0x0040, Exception -> 0x0044, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:158:0x02f3 A[Catch: all -> 0x0040, Exception -> 0x0044, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0321 A[Catch: all -> 0x0040, Exception -> 0x0044, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0353 A[Catch: all -> 0x0040, Exception -> 0x0044, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:171:0x03c1 A[Catch: all -> 0x0040, Exception -> 0x0044, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0408 A[Catch: all -> 0x0040, Exception -> 0x0044, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0450 A[Catch: all -> 0x0040, Exception -> 0x0044, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:189:0x0480 A[Catch: all -> 0x0040, Exception -> 0x0044, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:196:0x04c4 A[Catch: all -> 0x0040, Exception -> 0x0044, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:244:0x0631 A[Catch: all -> 0x0040, Exception -> 0x0044, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:245:0x0638 A[Catch: all -> 0x0040, Exception -> 0x0044, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:271:0x0777 A[Catch: all -> 0x0040, Exception -> 0x0044, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:284:0x0803 A[Catch: all -> 0x0040, Exception -> 0x0044, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:305:0x0893 A[Catch: all -> 0x0040, Exception -> 0x0044, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:330:0x0968 A[Catch: all -> 0x0040, Exception -> 0x0044, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:343:0x09d2 A[Catch: all -> 0x0040, Exception -> 0x0044, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:350:0x0a02 A[Catch: all -> 0x0040, Exception -> 0x0044, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:366:0x0aa2 A[Catch: all -> 0x0040, Exception -> 0x0044, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:374:0x0ad9 A[Catch: all -> 0x0040, Exception -> 0x0044, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:378:0x0af3 A[Catch: all -> 0x0040, Exception -> 0x0044, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:383:0x0b0c  */
    /* JADX WARN: Removed duplicated region for block: B:397:0x0bb3 A[Catch: all -> 0x0040, Exception -> 0x0b92, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:408:0x0bda A[Catch: all -> 0x0040, Exception -> 0x0bd8, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:416:0x0bfe  */
    /* JADX WARN: Removed duplicated region for block: B:421:0x0c1e A[Catch: all -> 0x0040, Exception -> 0x0bd8, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:428:0x0c42 A[Catch: all -> 0x0040, Exception -> 0x0bd8, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:433:0x0c53 A[Catch: all -> 0x0040, Exception -> 0x0bd8, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:434:0x0c6b A[Catch: all -> 0x0040, Exception -> 0x0bd8, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:438:0x0ca3 A[Catch: all -> 0x0040, Exception -> 0x0bd8, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:439:0x0caa A[Catch: all -> 0x0040, Exception -> 0x0bd8, TryCatch #0 {all -> 0x0040, blocks: (B:3:0x0006, B:6:0x0028, B:8:0x003b, B:14:0x004a, B:15:0x004c, B:18:0x0052, B:20:0x005a, B:22:0x0064, B:24:0x006e, B:26:0x0078, B:28:0x0082, B:30:0x008c, B:33:0x0099, B:37:0x00b2, B:130:0x0216, B:131:0x021b, B:133:0x0229, B:134:0x022f, B:136:0x023c, B:137:0x0243, B:138:0x024d, B:140:0x025f, B:141:0x026d, B:143:0x0284, B:144:0x028d, B:145:0x0294, B:147:0x02a2, B:148:0x02aa, B:150:0x02b8, B:151:0x02c6, B:153:0x02ce, B:154:0x02dc, B:156:0x02e2, B:157:0x02ec, B:158:0x02f3, B:159:0x0321, B:160:0x0330, B:162:0x0336, B:163:0x0344, B:164:0x0353, B:166:0x0359, B:167:0x0372, B:169:0x03ac, B:170:0x03b4, B:171:0x03c1, B:173:0x03c7, B:174:0x03e0, B:175:0x0408, B:177:0x042a, B:178:0x0432, B:180:0x043f, B:181:0x0449, B:182:0x0450, B:184:0x045e, B:185:0x0466, B:187:0x046f, B:188:0x0479, B:189:0x0480, B:191:0x04a2, B:192:0x04aa, B:194:0x04b3, B:195:0x04bd, B:196:0x04c4, B:198:0x0504, B:199:0x050a, B:201:0x0515, B:202:0x051b, B:204:0x0523, B:205:0x053f, B:207:0x054b, B:209:0x0572, B:213:0x057d, B:219:0x058e, B:222:0x059a, B:224:0x05a4, B:227:0x05aa, B:229:0x05ae, B:233:0x05b7, B:235:0x05ec, B:237:0x05f2, B:244:0x0631, B:238:0x0615, B:240:0x061b, B:241:0x0623, B:243:0x0629, B:230:0x05b1, B:245:0x0638, B:247:0x0670, B:248:0x0676, B:250:0x0681, B:251:0x0687, B:253:0x0693, B:254:0x06b7, B:256:0x06cc, B:259:0x06f4, B:261:0x0723, B:263:0x072d, B:270:0x0770, B:264:0x0750, B:266:0x075a, B:267:0x0762, B:269:0x0768, B:271:0x0777, B:273:0x07a7, B:274:0x07ad, B:276:0x07b8, B:277:0x07be, B:279:0x07ca, B:280:0x07ee, B:282:0x07f4, B:283:0x07fc, B:284:0x0803, B:291:0x084b, B:293:0x0855, B:294:0x085b, B:296:0x0866, B:297:0x086c, B:299:0x0873, B:301:0x087b, B:305:0x0893, B:315:0x08b1, B:318:0x08e4, B:320:0x090d, B:322:0x0919, B:329:0x0961, B:323:0x093c, B:325:0x0948, B:326:0x0950, B:328:0x0959, B:302:0x0883, B:304:0x088b, B:330:0x0968, B:332:0x0990, B:333:0x0998, B:335:0x09a5, B:336:0x09ab, B:338:0x09b5, B:339:0x09bd, B:341:0x09c3, B:342:0x09cb, B:343:0x09d2, B:345:0x09e0, B:346:0x09e8, B:348:0x09f3, B:349:0x09fb, B:350:0x0a02, B:352:0x0a10, B:355:0x0a1c, B:357:0x0a26, B:359:0x0a30, B:361:0x0a49, B:362:0x0a55, B:364:0x0a65, B:365:0x0a9b, B:366:0x0aa2, B:370:0x0abf, B:372:0x0aca, B:373:0x0acf, B:374:0x0ad9, B:376:0x0ae6, B:377:0x0ae9, B:378:0x0af3, B:380:0x0afb, B:381:0x0b05, B:384:0x0b0e, B:386:0x0b85, B:388:0x0b8c, B:392:0x0b98, B:395:0x0b9f, B:396:0x0ba4, B:452:0x0d09, B:397:0x0bb3, B:399:0x0bc0, B:402:0x0bc7, B:403:0x0bcc, B:405:0x0bd0, B:408:0x0bda, B:410:0x0be8, B:413:0x0bef, B:414:0x0bf4, B:417:0x0bff, B:420:0x0c07, B:421:0x0c1e, B:423:0x0c2c, B:426:0x0c33, B:427:0x0c38, B:428:0x0c42, B:432:0x0c4a, B:433:0x0c53, B:434:0x0c6b, B:436:0x0c90, B:437:0x0c95, B:438:0x0ca3, B:439:0x0caa, B:441:0x0cb1, B:442:0x0cba, B:444:0x0cc0, B:446:0x0cc4, B:447:0x0cd3, B:40:0x00bc, B:43:0x00c7, B:46:0x00d3, B:49:0x00de, B:52:0x00e8, B:55:0x00f4, B:58:0x00ff, B:61:0x010b, B:64:0x0117, B:67:0x0123, B:70:0x012f, B:73:0x013b, B:76:0x0145, B:79:0x0151, B:82:0x015b, B:85:0x0167, B:88:0x0172, B:91:0x017e, B:94:0x0189, B:97:0x0194, B:100:0x019f, B:103:0x01a8, B:106:0x01b3, B:109:0x01bc, B:112:0x01c5, B:115:0x01cd, B:118:0x01d8, B:121:0x01e2, B:124:0x01ed), top: B:456:0x0006 }] */
    /* JADX WARN: Type inference failed for: r23v0, types: [com.lib.flutter_blue_plus.FlutterBluePlusPlugin] */
    /* JADX WARN: Type inference failed for: r25v0, types: [io.flutter.plugin.common.MethodChannel$Result] */
    /* JADX WARN: Type inference failed for: r5v6, types: [java.lang.String] */
    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onMethodCall(@androidx.annotation.NonNull io.flutter.plugin.common.MethodCall r24, @androidx.annotation.NonNull final io.flutter.plugin.common.MethodChannel.Result r25) {
        /*
            Method dump skipped, instructions count: 3558
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lib.flutter_blue_plus.FlutterBluePlusPlugin.onMethodCall(io.flutter.plugin.common.MethodCall, io.flutter.plugin.common.MethodChannel$Result):void");
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding activityPluginBinding) {
        log(LogLevel.DEBUG, "onReattachedToActivityForConfigChanges");
        onAttachedToActivity(activityPluginBinding);
    }

    @Override // io.flutter.plugin.common.PluginRegistry.RequestPermissionsResultListener
    public boolean onRequestPermissionsResult(int i2, String[] strArr, int[] iArr) {
        OperationOnPermission operationOnPermission = this.operationsOnPermission.get(Integer.valueOf(i2));
        this.operationsOnPermission.remove(Integer.valueOf(i2));
        if (operationOnPermission == null || iArr.length <= 0) {
            return false;
        }
        for (int i3 = 0; i3 < iArr.length; i3++) {
            if (iArr[i3] != 0) {
                operationOnPermission.op(false, strArr[i3]);
                return true;
            }
        }
        operationOnPermission.op(true, null);
        return true;
    }

    public String uuid128(Object obj) {
        if (!(obj instanceof UUID) && !(obj instanceof String)) {
            throw new IllegalArgumentException("input must be UUID or String");
        }
        String string = obj.toString();
        return string.length() == 4 ? String.format("0000%s-0000-1000-8000-00805f9b34fb", string).toLowerCase() : string.length() == 8 ? String.format("%s-0000-1000-8000-00805f9b34fb", string).toLowerCase() : string.toLowerCase();
    }

    public String uuidStr(Object obj) {
        String strUuid128 = uuid128(obj);
        boolean zStartsWith = strUuid128.startsWith("0000");
        boolean zEndsWith = strUuid128.endsWith("-0000-1000-8000-00805f9b34fb");
        return (zStartsWith && zEndsWith) ? strUuid128.substring(4, 8) : zEndsWith ? strUuid128.substring(0, 8) : strUuid128;
    }
}
