package com.alternadom.wifiiot;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.MacAddress;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager$LocalOnlyHotspotCallback;
import android.net.wifi.WifiNetworkSpecifier;
import android.net.wifi.WifiNetworkSuggestion;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.alibaba.ailabs.iot.mesh.TgMeshManager;
import com.alibaba.fastjson.parser.JSONLexer;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.taobao.accs.utl.BaseMonitor;
import info.whitebyte.hotspotmanager.ClientScanResult;
import info.whitebyte.hotspotmanager.FinishScanListener;
import info.whitebyte.hotspotmanager.WIFI_AP_STATE;
import info.whitebyte.hotspotmanager.WifiApManager;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.text.Typography;
import org.apache.commons.lang.CharUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class WifiIotPlugin implements FlutterPlugin, ActivityAware, MethodChannel.MethodCallHandler, EventChannel.StreamHandler, PluginRegistry.RequestPermissionsResultListener {
    private static final int PERMISSIONS_REQUEST_CODE_ACCESS_FINE_LOCATION_FIND_AND_CONNECT = 65655437;
    private static final int PERMISSIONS_REQUEST_CODE_ACCESS_FINE_LOCATION_LOAD_WIFI_LIST = 65655435;
    private static final int PERMISSIONS_REQUEST_CODE_ACCESS_FINE_LOCATION_ON_LISTEN = 65655436;
    private static final int PERMISSIONS_REQUEST_CODE_ACCESS_NETWORK_STATE_IS_CONNECTED = 65655438;
    private WifiManager.LocalOnlyHotspotReservation apReservation;
    private MethodChannel channel;
    private EventChannel eventChannel;
    private Network joinedNetwork;
    private Activity moActivity;
    private Context moContext;
    private WifiManager moWiFi;
    private WifiApManager moWiFiAPManager;
    private ConnectivityManager.NetworkCallback networkCallback;
    private List<WifiNetworkSuggestion> networkSuggestions;
    private BroadcastReceiver receiver;
    private WIFI_AP_STATE localOnlyHotspotState = WIFI_AP_STATE.WIFI_AP_STATE_DISABLED;
    private List<String> ssidsToBeRemovedOnExit = new ArrayList();
    private List<WifiNetworkSuggestion> suggestionsToBeRemovedOnExit = new ArrayList();
    private boolean requestingPermission = false;
    private MethodChannel.Result permissionRequestResultCallback = null;
    private ArrayList<Object> permissionRequestCookie = new ArrayList<>();

    private void _findAndConnect(final MethodCall methodCall, final MethodChannel.Result result) {
        new Thread() { // from class: com.alternadom.wifiiot.WifiIotPlugin.7
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                String str;
                String str2 = (String) methodCall.argument("ssid");
                String str3 = (String) methodCall.argument("bssid");
                String str4 = (String) methodCall.argument("password");
                Boolean bool = (Boolean) methodCall.argument("join_once");
                Boolean bool2 = (Boolean) methodCall.argument("with_internet");
                Integer num = (Integer) methodCall.argument("timeout_in_seconds");
                String str5 = str3;
                String securityType = null;
                for (ScanResult scanResult : WifiIotPlugin.this.moWiFi.getScanResults()) {
                    if (str2.equals("" + scanResult.SSID) && ((str = scanResult.BSSID) == null || str5 == null || str.equals(str5))) {
                        securityType = WifiIotPlugin.getSecurityType(scanResult);
                        if (str5 == null) {
                            str5 = scanResult.BSSID;
                        }
                    }
                }
                WifiIotPlugin.this.connectTo(result, str2, str5, str4, securityType, bool, bool2, Boolean.FALSE, num);
            }
        }.start();
    }

    private void _isConnected(MethodChannel.Result result) {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.moContext.getSystemService("connectivity");
        boolean z2 = false;
        if (connectivityManager != null) {
            Network[] allNetworks = connectivityManager.getAllNetworks();
            int length = allNetworks.length;
            int i2 = 0;
            while (true) {
                if (i2 < length) {
                    Network network = allNetworks[i2];
                    NetworkCapabilities networkCapabilities = network != null ? connectivityManager.getNetworkCapabilities(network) : null;
                    if (networkCapabilities != null && networkCapabilities.hasTransport(1)) {
                        z2 = true;
                        break;
                    }
                    i2++;
                } else {
                    break;
                }
            }
        }
        result.success(Boolean.valueOf(z2));
    }

    private void _loadWifiList(MethodChannel.Result result) {
        try {
            this.moWiFi.startScan();
            result.success(i().toString());
        } catch (Exception e2) {
            result.error("Exception", e2.getMessage(), null);
        }
    }

    private void _onListen(EventChannel.EventSink eventSink) {
        BroadcastReceiver broadcastReceiverCreateReceiver = createReceiver(eventSink);
        this.receiver = broadcastReceiverCreateReceiver;
        this.moContext.registerReceiver(broadcastReceiverCreateReceiver, new IntentFilter("android.net.wifi.SCAN_RESULTS"));
    }

    private void cleanup() {
        if (!this.ssidsToBeRemovedOnExit.isEmpty()) {
            List<WifiConfiguration> configuredNetworks = this.moWiFi.getConfiguredNetworks();
            for (String str : this.ssidsToBeRemovedOnExit) {
                for (WifiConfiguration wifiConfiguration : configuredNetworks) {
                    if (wifiConfiguration.SSID.equals(str)) {
                        this.moWiFi.removeNetwork(wifiConfiguration.networkId);
                    }
                }
            }
        }
        if (Build.VERSION.SDK_INT >= 29 && !this.suggestionsToBeRemovedOnExit.isEmpty()) {
            this.moWiFi.removeNetworkSuggestions(this.suggestionsToBeRemovedOnExit);
        }
        this.channel = null;
        this.eventChannel = null;
        this.moActivity = null;
        this.moContext = null;
        this.moWiFi = null;
        this.moWiFiAPManager = null;
    }

    private void connect(final MethodCall methodCall, final MethodChannel.Result result) {
        new Thread() { // from class: com.alternadom.wifiiot.WifiIotPlugin.6
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                WifiIotPlugin.this.connectTo(result, (String) methodCall.argument("ssid"), (String) methodCall.argument("bssid"), (String) methodCall.argument("password"), (String) methodCall.argument(AlinkConstants.KEY_SECURITY), (Boolean) methodCall.argument("join_once"), (Boolean) methodCall.argument("with_internet"), (Boolean) methodCall.argument("is_hidden"), (Integer) methodCall.argument("timeout_in_seconds"));
            }
        }.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectTo(final MethodChannel.Result result, String str, String str2, String str3, String str4, Boolean bool, Boolean bool2, Boolean bool3, Integer num) {
        Handler handler = new Handler(Looper.getMainLooper());
        if (Build.VERSION.SDK_INT < 29) {
            final boolean zBooleanValue = connectToDeprecated(str, str2, str3, str4, bool, bool3).booleanValue();
            handler.post(new Runnable() { // from class: com.alternadom.wifiiot.WifiIotPlugin.8
                @Override // java.lang.Runnable
                public void run() {
                    result.success(Boolean.valueOf(zBooleanValue));
                }
            });
            return;
        }
        if (str4 != null && str4.toUpperCase().equals("WEP")) {
            handler.post(new Runnable() { // from class: com.alternadom.wifiiot.WifiIotPlugin.9
                @Override // java.lang.Runnable
                public void run() {
                    result.error("Error", "WEP is not supported for Android SDK " + Build.VERSION.SDK_INT, "");
                }
            });
            return;
        }
        if (bool2 == null || !bool2.booleanValue()) {
            WifiNetworkSpecifier.Builder builderA = t.a();
            builderA.setSsid(str);
            builderA.setIsHiddenSsid(bool3 != null ? bool3.booleanValue() : false);
            if (str2 != null) {
                MacAddress macAddressMacAddressFromBssid = macAddressFromBssid(str2);
                if (macAddressMacAddressFromBssid == null) {
                    handler.post(new Runnable() { // from class: com.alternadom.wifiiot.WifiIotPlugin.12
                        @Override // java.lang.Runnable
                        public void run() {
                            result.error("Error", "Invalid BSSID representation", "");
                        }
                    });
                    return;
                }
                builderA.setBssid(macAddressMacAddressFromBssid);
            }
            if (str4 != null && str4.toUpperCase().equals("WPA")) {
                builderA.setWpa2Passphrase(str3);
            }
            NetworkRequest networkRequestBuild = new NetworkRequest.Builder().addTransportType(1).removeCapability(12).setNetworkSpecifier(builderA.build()).build();
            final ConnectivityManager connectivityManager = (ConnectivityManager) this.moContext.getSystemService("connectivity");
            ConnectivityManager.NetworkCallback networkCallback = this.networkCallback;
            if (networkCallback != null) {
                connectivityManager.unregisterNetworkCallback(networkCallback);
            }
            ConnectivityManager.NetworkCallback networkCallback2 = new ConnectivityManager.NetworkCallback() { // from class: com.alternadom.wifiiot.WifiIotPlugin.13

                /* renamed from: a, reason: collision with root package name */
                boolean f12157a = false;

                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onAvailable(@NonNull Network network) {
                    super.onAvailable(network);
                    if (this.f12157a) {
                        return;
                    }
                    WifiIotPlugin.this.joinedNetwork = network;
                    result.success(Boolean.TRUE);
                    this.f12157a = true;
                }

                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onLost(Network network) {
                    super.onLost(network);
                    if (Build.VERSION.SDK_INT == 29) {
                        connectivityManager.unregisterNetworkCallback(this);
                    }
                }

                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onUnavailable() {
                    super.onUnavailable();
                    if (Build.VERSION.SDK_INT == 29) {
                        connectivityManager.unregisterNetworkCallback(this);
                    }
                    if (this.f12157a) {
                        return;
                    }
                    result.success(Boolean.FALSE);
                    this.f12157a = true;
                }
            };
            this.networkCallback = networkCallback2;
            connectivityManager.requestNetwork(networkRequestBuild, networkCallback2, handler, num.intValue() * 1000);
            return;
        }
        WifiNetworkSuggestion.Builder builderA2 = s.a();
        builderA2.setSsid(str);
        builderA2.setIsHiddenSsid(bool3 != null ? bool3.booleanValue() : false);
        if (str2 != null) {
            MacAddress macAddressMacAddressFromBssid2 = macAddressFromBssid(str2);
            if (macAddressMacAddressFromBssid2 == null) {
                handler.post(new Runnable() { // from class: com.alternadom.wifiiot.WifiIotPlugin.10
                    @Override // java.lang.Runnable
                    public void run() {
                        result.error("Error", "Invalid BSSID representation", "");
                    }
                });
                return;
            }
            builderA2.setBssid(macAddressMacAddressFromBssid2);
        }
        if (str4 != null && str4.toUpperCase().equals("WPA")) {
            builderA2.setWpa2Passphrase(str3);
        }
        List<WifiNetworkSuggestion> list = this.networkSuggestions;
        if (list != null) {
            this.moWiFi.removeNetworkSuggestions(list);
        }
        WifiNetworkSuggestion wifiNetworkSuggestionBuild = builderA2.build();
        ArrayList arrayList = new ArrayList();
        this.networkSuggestions = arrayList;
        arrayList.add(wifiNetworkSuggestionBuild);
        if (bool != null && bool.booleanValue()) {
            this.suggestionsToBeRemovedOnExit.add(wifiNetworkSuggestionBuild);
        }
        final int iAddNetworkSuggestions = this.moWiFi.addNetworkSuggestions(this.networkSuggestions);
        Log.e(WifiIotPlugin.class.getSimpleName(), "status: " + iAddNetworkSuggestions);
        handler.post(new Runnable() { // from class: com.alternadom.wifiiot.WifiIotPlugin.11
            @Override // java.lang.Runnable
            public void run() {
                result.success(Boolean.valueOf(iAddNetworkSuggestions == 0));
            }
        });
    }

    private Boolean connectToDeprecated(String str, String str2, String str3, String str4, Boolean bool, Boolean bool2) throws InterruptedException {
        WifiConfiguration wifiConfigurationGenerateConfiguration = generateConfiguration(str, str2, str3, str4, bool2);
        int iRegisterWifiNetworkDeprecated = registerWifiNetworkDeprecated(wifiConfigurationGenerateConfiguration);
        if (iRegisterWifiNetworkDeprecated == -1) {
            return Boolean.FALSE;
        }
        if (bool != null && bool.booleanValue()) {
            this.ssidsToBeRemovedOnExit.add(wifiConfigurationGenerateConfiguration.SSID);
        }
        if (!this.moWiFi.disconnect()) {
            return Boolean.FALSE;
        }
        if (!this.moWiFi.enableNetwork(iRegisterWifiNetworkDeprecated, true)) {
            return Boolean.FALSE;
        }
        boolean z2 = false;
        int i2 = 0;
        while (true) {
            if (i2 >= 20) {
                break;
            }
            WifiInfo connectionInfo = this.moWiFi.getConnectionInfo();
            int networkId = connectionInfo.getNetworkId();
            SupplicantState supplicantState = connectionInfo.getSupplicantState();
            if (networkId == -1 || supplicantState != SupplicantState.COMPLETED) {
                try {
                    Thread.sleep(500L);
                    i2++;
                } catch (InterruptedException unused) {
                }
            } else {
                z2 = networkId == iRegisterWifiNetworkDeprecated;
            }
        }
        return Boolean.valueOf(z2);
    }

    private BroadcastReceiver createReceiver(final EventChannel.EventSink eventSink) {
        return new BroadcastReceiver() { // from class: com.alternadom.wifiiot.WifiIotPlugin.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                eventSink.success(WifiIotPlugin.this.i().toString());
            }
        };
    }

    private void disconnect(MethodChannel.Result result) {
        boolean zDisconnect;
        if (Build.VERSION.SDK_INT < 29) {
            zDisconnect = this.moWiFi.disconnect();
        } else {
            if (this.networkCallback != null) {
                ((ConnectivityManager) this.moContext.getSystemService("connectivity")).unregisterNetworkCallback(this.networkCallback);
                this.networkCallback = null;
                this.joinedNetwork = null;
            } else {
                List<WifiNetworkSuggestion> list = this.networkSuggestions;
                if (list == null) {
                    Log.e(WifiIotPlugin.class.getSimpleName(), "Can't disconnect from WiFi, networkCallback and networkSuggestions is null.");
                } else if (this.moWiFi.removeNetworkSuggestions(list) == 0) {
                }
                zDisconnect = false;
            }
            zDisconnect = true;
        }
        result.success(Boolean.valueOf(zDisconnect));
    }

    private void findAndConnect(MethodCall methodCall, MethodChannel.Result result) {
        if (this.moContext.checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0) {
            _findAndConnect(methodCall, result);
            return;
        }
        if (this.requestingPermission) {
            result.error("WifiIotPlugin.Permission", "Only one permission can be requested at a time", null);
            return;
        }
        this.requestingPermission = true;
        this.permissionRequestResultCallback = result;
        this.permissionRequestCookie.clear();
        this.permissionRequestCookie.add(methodCall);
        this.moActivity.requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"}, PERMISSIONS_REQUEST_CODE_ACCESS_FINE_LOCATION_FIND_AND_CONNECT);
    }

    private void forceWifiUsage(MethodCall methodCall, final MethodChannel.Result result) {
        boolean zSelectNetwork;
        boolean zBooleanValue = ((Boolean) methodCall.argument("useWifi")).booleanValue();
        final ConnectivityManager connectivityManager = (ConnectivityManager) this.moContext.getSystemService("connectivity");
        boolean z2 = true;
        if (connectivityManager == null) {
            zSelectNetwork = true;
        } else if (zBooleanValue) {
            Network network = this.joinedNetwork;
            if (network != null) {
                zSelectNetwork = selectNetwork(network, connectivityManager);
            } else {
                NetworkRequest.Builder builder = new NetworkRequest.Builder();
                builder.addTransportType(1);
                connectivityManager.requestNetwork(builder.build(), new ConnectivityManager.NetworkCallback() { // from class: com.alternadom.wifiiot.WifiIotPlugin.5
                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public void onAvailable(Network network2) {
                        super.onAvailable(network2);
                        connectivityManager.unregisterNetworkCallback(this);
                        WifiIotPlugin.this.onAvailableNetwork(connectivityManager, network2, result);
                    }
                });
                z2 = false;
                zSelectNetwork = true;
            }
        } else {
            zSelectNetwork = selectNetwork(null, connectivityManager);
        }
        if (z2) {
            result.success(Boolean.valueOf(zSelectNetwork));
        }
    }

    private WifiConfiguration generateConfiguration(String str, String str2, String str3, String str4, Boolean bool) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = "\"" + str + "\"";
        wifiConfiguration.hiddenSSID = bool != null ? bool.booleanValue() : false;
        if (str2 != null) {
            wifiConfiguration.BSSID = str2;
        }
        String upperCase = str4 != null ? str4.toUpperCase() : "NONE";
        if (upperCase.toUpperCase().equals("WPA")) {
            wifiConfiguration.preSharedKey = "\"" + str3 + "\"";
            wifiConfiguration.allowedProtocols.set(1);
            wifiConfiguration.allowedKeyManagement.set(1);
            wifiConfiguration.status = 2;
            wifiConfiguration.allowedGroupCiphers.set(2);
            wifiConfiguration.allowedGroupCiphers.set(3);
            wifiConfiguration.allowedKeyManagement.set(1);
            wifiConfiguration.allowedPairwiseCiphers.set(1);
            wifiConfiguration.allowedPairwiseCiphers.set(2);
            wifiConfiguration.allowedProtocols.set(1);
            wifiConfiguration.allowedProtocols.set(0);
        } else if (upperCase.equals("WEP")) {
            wifiConfiguration.wepKeys[0] = "\"" + str3 + "\"";
            wifiConfiguration.wepTxKeyIndex = 0;
            wifiConfiguration.allowedKeyManagement.set(0);
            wifiConfiguration.allowedGroupCiphers.set(0);
        } else {
            wifiConfiguration.allowedKeyManagement.set(0);
        }
        return wifiConfiguration;
    }

    private void getBSSID(MethodChannel.Result result) {
        try {
            result.success(this.moWiFi.getConnectionInfo().getBSSID().toUpperCase());
        } catch (Exception e2) {
            result.error("Exception", e2.getMessage(), null);
        }
    }

    private void getClientList(MethodCall methodCall, final MethodChannel.Result result) {
        final Boolean bool = Boolean.FALSE;
        if (methodCall.argument("onlyReachables") != null) {
            bool = (Boolean) methodCall.argument("onlyReachables");
        }
        Integer num = methodCall.argument("reachableTimeout") != null ? (Integer) methodCall.argument("reachableTimeout") : 300;
        FinishScanListener finishScanListener = new FinishScanListener() { // from class: com.alternadom.wifiiot.WifiIotPlugin.1
            @Override // info.whitebyte.hotspotmanager.FinishScanListener
            public void onFinishScan(ArrayList<ClientScanResult> arrayList) throws JSONException {
                try {
                    JSONArray jSONArray = new JSONArray();
                    Iterator<ClientScanResult> it = arrayList.iterator();
                    while (it.hasNext()) {
                        ClientScanResult next = it.next();
                        JSONObject jSONObject = new JSONObject();
                        boolean zIsReachable = next.isReachable();
                        Boolean bool2 = Boolean.TRUE;
                        if (bool.booleanValue() && !zIsReachable) {
                            bool2 = Boolean.FALSE;
                        }
                        if (bool2.booleanValue()) {
                            try {
                                jSONObject.put("IPAddr", next.getIpAddr());
                                jSONObject.put("HWAddr", next.getHWAddr());
                                jSONObject.put("Device", next.getDevice());
                                jSONObject.put("isReachable", next.isReachable());
                            } catch (JSONException e2) {
                                result.error("Exception", e2.getMessage(), null);
                            }
                            jSONArray.put(jSONObject);
                        }
                    }
                    result.success(jSONArray.toString());
                } catch (Exception e3) {
                    result.error("Exception", e3.getMessage(), null);
                }
            }
        };
        if (num != null) {
            this.moWiFiAPManager.getClientList(bool.booleanValue(), num.intValue(), finishScanListener);
        } else {
            this.moWiFiAPManager.getClientList(bool.booleanValue(), finishScanListener);
        }
    }

    private void getCurrentSignalStrength(MethodChannel.Result result) {
        result.success(Integer.valueOf(this.moWiFi.getConnectionInfo().getRssi()));
    }

    private void getFrequency(MethodChannel.Result result) {
        result.success(Integer.valueOf(this.moWiFi.getConnectionInfo().getFrequency()));
    }

    private void getIP(MethodChannel.Result result) {
        result.success(longToIP(this.moWiFi.getConnectionInfo().getIpAddress()));
    }

    private void getSSID(MethodChannel.Result result) {
        String ssid = this.moWiFi.getConnectionInfo().getSSID();
        if (ssid.startsWith("\"") && ssid.endsWith("\"")) {
            ssid = ssid.substring(1, ssid.length() - 1);
        }
        result.success(ssid);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getSecurityType(ScanResult scanResult) {
        String str = scanResult.capabilities;
        if (str.contains("WPA") || str.contains("WPA2") || str.contains("WPA/WPA2 PSK")) {
            return "WPA";
        }
        if (str.contains("WEP")) {
            return "WEP";
        }
        return null;
    }

    private void getWiFiAPPreSharedKey(MethodChannel.Result result) {
        String str;
        int i2 = Build.VERSION.SDK_INT;
        if (i2 < 26) {
            WifiConfiguration wifiApConfiguration = this.moWiFiAPManager.getWifiApConfiguration();
            if (wifiApConfiguration == null || (str = wifiApConfiguration.preSharedKey) == null) {
                result.error("Exception", "Wifi AP not Supported", null);
                return;
            } else {
                result.success(str);
                return;
            }
        }
        WifiManager.LocalOnlyHotspotReservation localOnlyHotspotReservation = this.apReservation;
        if (localOnlyHotspotReservation == null) {
            result.error("Exception [getWiFiAPPreSharedKey]", "Hotspot is not enabled.", null);
            return;
        }
        if (i2 >= 30) {
            result.success(localOnlyHotspotReservation.getSoftApConfiguration().getPassphrase());
            return;
        }
        WifiConfiguration wifiConfiguration = localOnlyHotspotReservation.getWifiConfiguration();
        if (wifiConfiguration != null) {
            result.success(wifiConfiguration.preSharedKey);
        } else {
            result.error("Exception [getWiFiAPPreSharedKey]", "Security type is not WifiConfiguration.KeyMgmt.None or WifiConfiguration.KeyMgmt.WPA2_PSK", null);
        }
    }

    private void getWiFiAPSSID(MethodChannel.Result result) {
        String str;
        int i2 = Build.VERSION.SDK_INT;
        if (i2 < 26) {
            WifiConfiguration wifiApConfiguration = this.moWiFiAPManager.getWifiApConfiguration();
            if (wifiApConfiguration == null || (str = wifiApConfiguration.SSID) == null) {
                result.error("Exception [getWiFiAPSSID]", "SSID not found", null);
                return;
            } else {
                result.success(str);
                return;
            }
        }
        WifiManager.LocalOnlyHotspotReservation localOnlyHotspotReservation = this.apReservation;
        if (localOnlyHotspotReservation == null) {
            result.error("Exception [getWiFiAPSSID]", "Hotspot is not enabled.", null);
            return;
        }
        if (i2 >= 30) {
            result.success(localOnlyHotspotReservation.getSoftApConfiguration().getSsid());
            return;
        }
        WifiConfiguration wifiConfiguration = localOnlyHotspotReservation.getWifiConfiguration();
        if (wifiConfiguration != null) {
            result.success(wifiConfiguration.SSID);
        } else {
            result.error("Exception [getWiFiAPSSID]", "Security type is not WifiConfiguration.KeyMgmt.None or WifiConfiguration.KeyMgmt.WPA2_PSK", null);
        }
    }

    private void getWiFiAPState(MethodChannel.Result result) {
        if (Build.VERSION.SDK_INT < 29) {
            result.success(Integer.valueOf(this.moWiFiAPManager.getWifiApState().ordinal()));
        } else {
            result.success(this.localOnlyHotspotState);
        }
    }

    private void initWithActivity(Activity activity) {
        this.moActivity = activity;
    }

    private void initWithContext(Context context) {
        this.moContext = context;
        this.moWiFi = (WifiManager) context.getApplicationContext().getSystemService("wifi");
        this.moWiFiAPManager = new WifiApManager(this.moContext.getApplicationContext());
    }

    private void isConnected(MethodChannel.Result result) {
        if (this.moContext.checkSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0) {
            _isConnected(result);
        } else {
            if (this.requestingPermission) {
                result.error("WifiIotPlugin.Permission", "Only one permission can be requested at a time", null);
                return;
            }
            this.requestingPermission = true;
            this.permissionRequestResultCallback = result;
            this.moActivity.requestPermissions(new String[]{"android.permission.ACCESS_NETWORK_STATE"}, PERMISSIONS_REQUEST_CODE_ACCESS_NETWORK_STATE_IS_CONNECTED);
        }
    }

    private void isConnectedDeprecated(MethodChannel.Result result) {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.moContext.getSystemService("connectivity");
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getNetworkInfo(1) : null;
        result.success(Boolean.valueOf(networkInfo != null && networkInfo.isConnected()));
    }

    private void isEnabled(MethodChannel.Result result) {
        result.success(Boolean.valueOf(this.moWiFi.isWifiEnabled()));
    }

    private void isRegisteredWifiNetwork(MethodCall methodCall, MethodChannel.Result result) {
        String str = (String) methodCall.argument("ssid");
        List<WifiConfiguration> configuredNetworks = this.moWiFi.getConfiguredNetworks();
        String str2 = Typography.quote + str + Typography.quote;
        if (configuredNetworks != null) {
            Iterator<WifiConfiguration> it = configuredNetworks.iterator();
            while (it.hasNext()) {
                if (it.next().SSID.equals(str2)) {
                    result.success(Boolean.TRUE);
                    return;
                }
            }
        }
        result.success(Boolean.FALSE);
    }

    private void isSSIDHidden(MethodChannel.Result result) {
        boolean z2;
        int i2 = Build.VERSION.SDK_INT;
        if (i2 < 26) {
            WifiConfiguration wifiApConfiguration = this.moWiFiAPManager.getWifiApConfiguration();
            if (wifiApConfiguration == null || !(z2 = wifiApConfiguration.hiddenSSID)) {
                result.error("Exception [isSSIDHidden]", "Wifi AP not Supported", null);
                return;
            } else {
                result.success(Boolean.valueOf(z2));
                return;
            }
        }
        WifiManager.LocalOnlyHotspotReservation localOnlyHotspotReservation = this.apReservation;
        if (localOnlyHotspotReservation == null) {
            result.error("Exception [isSSIDHidden]", "Hotspot is not enabled.", null);
            return;
        }
        if (i2 >= 30) {
            result.success(Boolean.valueOf(localOnlyHotspotReservation.getSoftApConfiguration().isHiddenSsid()));
            return;
        }
        WifiConfiguration wifiConfiguration = localOnlyHotspotReservation.getWifiConfiguration();
        if (wifiConfiguration != null) {
            result.success(Boolean.valueOf(wifiConfiguration.hiddenSSID));
        } else {
            result.error("Exception [isSSIDHidden]", "Security type is not WifiConfiguration.KeyMgmt.None or WifiConfiguration.KeyMgmt.WPA2_PSK", null);
        }
    }

    private void isWiFiAPEnabled(MethodChannel.Result result) {
        if (Build.VERSION.SDK_INT >= 29) {
            result.success(Boolean.valueOf(this.apReservation != null));
            return;
        }
        try {
            result.success(Boolean.valueOf(this.moWiFiAPManager.isWifiApEnabled()));
        } catch (SecurityException e2) {
            Log.e(WifiIotPlugin.class.getSimpleName(), e2.getMessage(), null);
            result.error("Exception [isWiFiAPEnabled]", e2.getMessage(), null);
        }
    }

    private void loadWifiList(MethodChannel.Result result) {
        if (this.moContext.checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0) {
            _loadWifiList(result);
        } else {
            if (this.requestingPermission) {
                result.error("WifiIotPlugin.Permission", "Only one permission can be requested at a time", null);
                return;
            }
            this.requestingPermission = true;
            this.permissionRequestResultCallback = result;
            this.moActivity.requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"}, PERMISSIONS_REQUEST_CODE_ACCESS_FINE_LOCATION_LOAD_WIFI_LIST);
        }
    }

    private static String longToIP(int i2) {
        StringBuilder sb = new StringBuilder("");
        String[] strArr = {strValueOf, String.valueOf((65535 & i2) >>> 8), String.valueOf((16777215 & i2) >>> 16), String.valueOf(i2 >>> 24)};
        String strValueOf = String.valueOf(i2 & 255);
        sb.append(strValueOf);
        sb.append(".");
        sb.append(strArr[1]);
        sb.append(".");
        sb.append(strArr[2]);
        sb.append(".");
        sb.append(strArr[3]);
        return sb.toString();
    }

    @RequiresApi(28)
    private static MacAddress macAddressFromBssid(String str) {
        if (str == null) {
            return null;
        }
        try {
            return MacAddress.fromString(str);
        } catch (IllegalArgumentException e2) {
            Log.e(WifiIotPlugin.class.getSimpleName(), "Mac address parsing failed for bssid: " + str, e2);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAvailableNetwork(ConnectivityManager connectivityManager, Network network, final MethodChannel.Result result) {
        final boolean zSelectNetwork = selectNetwork(network, connectivityManager);
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.alternadom.wifiiot.WifiIotPlugin.4
            @Override // java.lang.Runnable
            public void run() {
                result.success(Boolean.valueOf(zSelectNetwork));
            }
        });
    }

    private void registerWifiNetwork(MethodCall methodCall, MethodChannel.Result result) {
        String str = (String) methodCall.argument("ssid");
        String str2 = (String) methodCall.argument("bssid");
        String str3 = (String) methodCall.argument("password");
        String str4 = (String) methodCall.argument(AlinkConstants.KEY_SECURITY);
        Boolean bool = (Boolean) methodCall.argument("is_hidden");
        int i2 = Build.VERSION.SDK_INT;
        if (i2 < 30) {
            if (registerWifiNetworkDeprecated(generateConfiguration(str, str2, str3, str4, bool)) == -1) {
                result.error("Error", "Error updating network configuration", "");
                return;
            } else {
                result.success(null);
                return;
            }
        }
        WifiNetworkSuggestion.Builder builderA = s.a();
        builderA.setSsid(str);
        builderA.setIsHiddenSsid(bool != null ? bool.booleanValue() : false);
        if (str2 != null) {
            MacAddress macAddressMacAddressFromBssid = macAddressFromBssid(str2);
            if (macAddressMacAddressFromBssid == null) {
                result.error("Error", "Invalid BSSID representation", "");
                return;
            }
            builderA.setBssid(macAddressMacAddressFromBssid);
        }
        if (str4 != null && str4.toUpperCase().equals("WPA")) {
            builderA.setWpa2Passphrase(str3);
        } else if (str4 != null && str4.toUpperCase().equals("WEP")) {
            result.error("Error", "WEP is not supported for Android SDK " + i2, "");
            return;
        }
        ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
        arrayList.add(builderA.build());
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("android.provider.extra.WIFI_NETWORK_LIST", arrayList);
        Intent intent = new Intent("android.settings.WIFI_ADD_NETWORKS");
        intent.putExtras(bundle);
        intent.addFlags(268435456);
        this.moContext.startActivity(intent);
        result.success(null);
    }

    private int registerWifiNetworkDeprecated(WifiConfiguration wifiConfiguration) {
        int iAddNetwork;
        int i2;
        String str;
        String str2;
        List<WifiConfiguration> configuredNetworks = this.moWiFi.getConfiguredNetworks();
        if (configuredNetworks != null) {
            iAddNetwork = -1;
            i2 = -1;
            for (WifiConfiguration wifiConfiguration2 : configuredNetworks) {
                if (wifiConfiguration2.SSID.equals(wifiConfiguration.SSID) && ((str = wifiConfiguration2.BSSID) == null || (str2 = wifiConfiguration.BSSID) == null || str.equals(str2))) {
                    wifiConfiguration.networkId = wifiConfiguration2.networkId;
                    i2 = wifiConfiguration2.networkId;
                    iAddNetwork = this.moWiFi.updateNetwork(wifiConfiguration);
                }
            }
        } else {
            iAddNetwork = -1;
            i2 = -1;
        }
        if (iAddNetwork == -1) {
            iAddNetwork = this.moWiFi.addNetwork(wifiConfiguration);
            this.moWiFi.saveConfiguration();
        }
        return iAddNetwork == -1 ? i2 : iAddNetwork;
    }

    private void removeWifiNetwork(MethodCall methodCall, MethodChannel.Result result) {
        boolean z2;
        String str = (String) methodCall.argument("ssid");
        if (str.equals("")) {
            result.error("Error", "No prefix SSID was given!", null);
        }
        if (Build.VERSION.SDK_INT < 29) {
            for (WifiConfiguration wifiConfiguration : this.moWiFi.getConfiguredNetworks()) {
                if (wifiConfiguration.SSID.startsWith(Typography.quote + str)) {
                    this.moWiFi.removeNetwork(wifiConfiguration.networkId);
                    this.moWiFi.saveConfiguration();
                    z2 = true;
                    break;
                }
            }
            z2 = false;
        } else {
            z2 = false;
        }
        if (Build.VERSION.SDK_INT >= 30) {
            List networkSuggestions = this.moWiFi.getNetworkSuggestions();
            ArrayList arrayList = new ArrayList();
            int size = networkSuggestions.size();
            for (int i2 = 0; i2 < size; i2++) {
                WifiNetworkSuggestion wifiNetworkSuggestionA = l.a(networkSuggestions.get(i2));
                if (wifiNetworkSuggestionA.getSsid().startsWith(str)) {
                    arrayList.add(wifiNetworkSuggestionA);
                }
            }
            z2 = this.moWiFi.removeNetworkSuggestions(arrayList) == 0;
        }
        result.success(Boolean.valueOf(z2));
    }

    private boolean selectNetwork(Network network, ConnectivityManager connectivityManager) {
        return connectivityManager.bindProcessToNetwork(network);
    }

    private void setEnabled(MethodCall methodCall, MethodChannel.Result result) {
        Boolean bool = (Boolean) methodCall.argument("state");
        Boolean bool2 = (Boolean) methodCall.argument("shouldOpenSettings");
        if (Build.VERSION.SDK_INT < 29) {
            this.moWiFi.setWifiEnabled(bool.booleanValue());
        } else if (bool2 == null) {
            Log.e(WifiIotPlugin.class.getSimpleName(), "Error `setEnabled`: shouldOpenSettings is null.");
        } else if (bool2.booleanValue()) {
            Intent intent = new Intent("android.settings.WIFI_SETTINGS");
            intent.setFlags(268435456);
            this.moContext.startActivity(intent);
        } else {
            this.moWiFi.setWifiEnabled(bool.booleanValue());
        }
        result.success(null);
    }

    private void setSSIDHidden(MethodCall methodCall, MethodChannel.Result result) {
        if (Build.VERSION.SDK_INT >= 26) {
            result.error("Exception [setSSIDHidden]", "Setting SSID visibility is not supported on API level >= 26", null);
            return;
        }
        boolean zBooleanValue = ((Boolean) methodCall.argument("hidden")).booleanValue();
        WifiConfiguration wifiApConfiguration = this.moWiFiAPManager.getWifiApConfiguration();
        wifiApConfiguration.hiddenSSID = zBooleanValue;
        this.moWiFiAPManager.setWifiApConfiguration(wifiApConfiguration);
        result.success(null);
    }

    private void setWiFiAPEnabled(MethodCall methodCall, final MethodChannel.Result result) {
        boolean zBooleanValue = ((Boolean) methodCall.argument("state")).booleanValue();
        if (Build.VERSION.SDK_INT < 29) {
            result.success(Boolean.valueOf(this.moWiFiAPManager.setWifiApEnabled(null, zBooleanValue)));
            return;
        }
        if (zBooleanValue) {
            this.localOnlyHotspotState = WIFI_AP_STATE.WIFI_AP_STATE_ENABLING;
            this.moWiFi.startLocalOnlyHotspot(new WifiManager$LocalOnlyHotspotCallback() { // from class: com.alternadom.wifiiot.WifiIotPlugin.2
                public void onFailed(int i2) {
                    super.onFailed(i2);
                    if (WifiIotPlugin.this.apReservation != null) {
                        WifiIotPlugin.this.apReservation.close();
                    }
                    WifiIotPlugin.this.apReservation = null;
                    WifiIotPlugin.this.localOnlyHotspotState = WIFI_AP_STATE.WIFI_AP_STATE_FAILED;
                    Log.d(WifiIotPlugin.class.getSimpleName(), "LocalHotspot failed with code: " + String.valueOf(i2));
                    result.success(Boolean.FALSE);
                }

                public void onStarted(WifiManager.LocalOnlyHotspotReservation localOnlyHotspotReservation) {
                    super.onStarted(localOnlyHotspotReservation);
                    WifiIotPlugin.this.apReservation = localOnlyHotspotReservation;
                    WifiIotPlugin.this.localOnlyHotspotState = WIFI_AP_STATE.WIFI_AP_STATE_ENABLED;
                    result.success(Boolean.TRUE);
                }

                public void onStopped() {
                    super.onStopped();
                    if (WifiIotPlugin.this.apReservation != null) {
                        WifiIotPlugin.this.apReservation.close();
                    }
                    WifiIotPlugin.this.apReservation = null;
                    WifiIotPlugin.this.localOnlyHotspotState = WIFI_AP_STATE.WIFI_AP_STATE_DISABLED;
                    Log.d(WifiIotPlugin.class.getSimpleName(), "LocalHotspot Stopped.");
                }
            }, new Handler());
            return;
        }
        this.localOnlyHotspotState = WIFI_AP_STATE.WIFI_AP_STATE_DISABLING;
        WifiManager.LocalOnlyHotspotReservation localOnlyHotspotReservation = this.apReservation;
        if (localOnlyHotspotReservation != null) {
            localOnlyHotspotReservation.close();
            this.apReservation = null;
            result.success(Boolean.TRUE);
        } else {
            Log.e(WifiIotPlugin.class.getSimpleName(), "Can't disable WiFi AP, apReservation is null.");
            result.success(Boolean.FALSE);
        }
        this.localOnlyHotspotState = WIFI_AP_STATE.WIFI_AP_STATE_DISABLED;
    }

    private void setWiFiAPPreSharedKey(MethodCall methodCall, MethodChannel.Result result) {
        if (Build.VERSION.SDK_INT >= 26) {
            result.error("Exception [setWiFiAPPreSharedKey]", "Setting WiFi password is not supported on API level >= 26", null);
            return;
        }
        String str = (String) methodCall.argument("preSharedKey");
        WifiConfiguration wifiApConfiguration = this.moWiFiAPManager.getWifiApConfiguration();
        wifiApConfiguration.preSharedKey = str;
        this.moWiFiAPManager.setWifiApConfiguration(wifiApConfiguration);
        result.success(null);
    }

    private void setWiFiAPSSID(MethodCall methodCall, MethodChannel.Result result) {
        String str = (String) methodCall.argument("ssid");
        if (Build.VERSION.SDK_INT >= 26) {
            result.error("Exception [setWiFiAPSSID]", "Setting SSID name is not supported on API level >= 26", null);
            return;
        }
        WifiConfiguration wifiApConfiguration = this.moWiFiAPManager.getWifiApConfiguration();
        wifiApConfiguration.SSID = str;
        this.moWiFiAPManager.setWifiApConfiguration(wifiApConfiguration);
        result.success(null);
    }

    private void showWritePermissionSettings(MethodCall methodCall, MethodChannel.Result result) {
        this.moWiFiAPManager.showWritePermissionSettings(((Boolean) methodCall.argument("force")).booleanValue());
        result.success(null);
    }

    JSONArray i() {
        List<ScanResult> scanResults = this.moWiFi.getScanResults();
        JSONArray jSONArray = new JSONArray();
        try {
            try {
                for (ScanResult scanResult : scanResults) {
                    JSONObject jSONObject = new JSONObject();
                    if (!scanResult.SSID.equals("")) {
                        jSONObject.put("SSID", scanResult.SSID);
                        jSONObject.put(TgMeshManager.KEY_PROVISION_COMBO_MESH_WIFI_BSSID, scanResult.BSSID);
                        jSONObject.put("capabilities", scanResult.capabilities);
                        jSONObject.put("frequency", scanResult.frequency);
                        jSONObject.put(FirebaseAnalytics.Param.LEVEL, scanResult.level);
                        jSONObject.put(com.alipay.sdk.m.t.a.f9743k, scanResult.timestamp);
                        jSONArray.put(jSONObject);
                    }
                }
                return jSONArray;
            } catch (JSONException e2) {
                e2.printStackTrace();
                return jSONArray;
            }
        } catch (Throwable unused) {
            return jSONArray;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
        initWithActivity(activityPluginBinding.getActivity());
        activityPluginBinding.addRequestPermissionsResultListener(this);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "wifi_iot");
        this.eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), "plugins.wififlutter.io/wifi_scan");
        this.channel.setMethodCallHandler(this);
        this.eventChannel.setStreamHandler(this);
        initWithContext(flutterPluginBinding.getApplicationContext());
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onCancel(Object obj) {
        BroadcastReceiver broadcastReceiver = this.receiver;
        if (broadcastReceiver != null) {
            this.moContext.unregisterReceiver(broadcastReceiver);
            this.receiver = null;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        this.moActivity = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        this.moActivity = null;
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.channel.setMethodCallHandler(null);
        this.eventChannel.setStreamHandler(null);
        cleanup();
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onListen(Object obj, EventChannel.EventSink eventSink) {
        if (this.moContext.checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0) {
            _onListen(eventSink);
        } else {
            if (this.requestingPermission) {
                return;
            }
            this.requestingPermission = true;
            this.permissionRequestCookie.clear();
            this.permissionRequestCookie.add(eventSink);
            this.moActivity.requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"}, PERMISSIONS_REQUEST_CODE_ACCESS_FINE_LOCATION_ON_LISTEN);
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        String str = methodCall.method;
        str.hashCode();
        char c2 = 65535;
        switch (str.hashCode()) {
            case -1995843987:
                if (str.equals("isRegisteredWifiNetwork")) {
                    c2 = 0;
                    break;
                }
                break;
            case -1884106893:
                if (str.equals("isWiFiAPEnabled")) {
                    c2 = 1;
                    break;
                }
                break;
            case -1776439071:
                if (str.equals("forceWifiUsage")) {
                    c2 = 2;
                    break;
                }
                break;
            case -1743187564:
                if (str.equals("showWritePermissionSettings")) {
                    c2 = 3;
                    break;
                }
                break;
            case -1526131018:
                if (str.equals("registerWifiNetwork")) {
                    c2 = 4;
                    break;
                }
                break;
            case -1163299683:
                if (str.equals("setWiFiAPPreSharedKey")) {
                    c2 = 5;
                    break;
                }
                break;
            case -638600299:
                if (str.equals("getWiFiAPSSID")) {
                    c2 = 6;
                    break;
                }
                break;
            case -79628634:
                if (str.equals("getFrequency")) {
                    c2 = 7;
                    break;
                }
                break;
            case -75173935:
                if (str.equals("getSSID")) {
                    c2 = '\b';
                    break;
                }
                break;
            case -57072551:
                if (str.equals("loadWifiList")) {
                    c2 = '\t';
                    break;
                }
                break;
            case 98245373:
                if (str.equals("getIP")) {
                    c2 = '\n';
                    break;
                }
                break;
            case 135530913:
                if (str.equals("setWiFiAPSSID")) {
                    c2 = 11;
                    break;
                }
                break;
            case 175427372:
                if (str.equals("getCurrentSignalStrength")) {
                    c2 = '\f';
                    break;
                }
                break;
            case 415491815:
                if (str.equals("setSSIDHidden")) {
                    c2 = CharUtils.CR;
                    break;
                }
                break;
            case 530405532:
                if (str.equals("disconnect")) {
                    c2 = 14;
                    break;
                }
                break;
            case 595456657:
                if (str.equals("getWiFiAPPreSharedKey")) {
                    c2 = 15;
                    break;
                }
                break;
            case 599209215:
                if (str.equals("isConnected")) {
                    c2 = 16;
                    break;
                }
                break;
            case 804451071:
                if (str.equals("getClientList")) {
                    c2 = 17;
                    break;
                }
                break;
            case 901178796:
                if (str.equals("findAndConnect")) {
                    c2 = 18;
                    break;
                }
                break;
            case 951351530:
                if (str.equals(BaseMonitor.ALARM_POINT_CONNECT)) {
                    c2 = 19;
                    break;
                }
                break;
            case 1364071551:
                if (str.equals("setEnabled")) {
                    c2 = 20;
                    break;
                }
                break;
            case 1588173627:
                if (str.equals("setWiFiAPEnabled")) {
                    c2 = 21;
                    break;
                }
                break;
            case 1624573493:
                if (str.equals("removeWifiNetwork")) {
                    c2 = 22;
                    break;
                }
                break;
            case 1679234967:
                if (str.equals("getWiFiAPState")) {
                    c2 = 23;
                    break;
                }
                break;
            case 1948885287:
                if (str.equals("getBSSID")) {
                    c2 = 24;
                    break;
                }
                break;
            case 2105594551:
                if (str.equals("isEnabled")) {
                    c2 = 25;
                    break;
                }
                break;
            case 2110381487:
                if (str.equals("isSSIDHidden")) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                int i2 = Build.VERSION.SDK_INT;
                if (i2 >= 29) {
                    result.error("Error", "isRegisteredWifiNetwork not supported for Android SDK " + i2, null);
                    break;
                } else {
                    isRegisteredWifiNetwork(methodCall, result);
                    break;
                }
            case 1:
                isWiFiAPEnabled(result);
                break;
            case 2:
                forceWifiUsage(methodCall, result);
                break;
            case 3:
                showWritePermissionSettings(methodCall, result);
                break;
            case 4:
                registerWifiNetwork(methodCall, result);
                break;
            case 5:
                setWiFiAPPreSharedKey(methodCall, result);
                break;
            case 6:
                getWiFiAPSSID(result);
                break;
            case 7:
                getFrequency(result);
                break;
            case '\b':
                getSSID(result);
                break;
            case '\t':
                loadWifiList(result);
                break;
            case '\n':
                getIP(result);
                break;
            case 11:
                setWiFiAPSSID(methodCall, result);
                break;
            case '\f':
                getCurrentSignalStrength(result);
                break;
            case '\r':
                setSSIDHidden(methodCall, result);
                break;
            case 14:
                disconnect(result);
                break;
            case 15:
                getWiFiAPPreSharedKey(result);
                break;
            case 16:
                isConnected(result);
                break;
            case 17:
                getClientList(methodCall, result);
                break;
            case 18:
                findAndConnect(methodCall, result);
                break;
            case 19:
                connect(methodCall, result);
                break;
            case 20:
                setEnabled(methodCall, result);
                break;
            case 21:
                setWiFiAPEnabled(methodCall, result);
                break;
            case 22:
                removeWifiNetwork(methodCall, result);
                break;
            case 23:
                getWiFiAPState(result);
                break;
            case 24:
                getBSSID(result);
                break;
            case 25:
                isEnabled(result);
                break;
            case 26:
                isSSIDHidden(result);
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding activityPluginBinding) {
        initWithActivity(activityPluginBinding.getActivity());
        activityPluginBinding.addRequestPermissionsResultListener(this);
    }

    @Override // io.flutter.plugin.common.PluginRegistry.RequestPermissionsResultListener
    public boolean onRequestPermissionsResult(int i2, String[] strArr, int[] iArr) {
        boolean z2 = iArr.length > 0 && iArr[0] == 0;
        switch (i2) {
            case PERMISSIONS_REQUEST_CODE_ACCESS_FINE_LOCATION_LOAD_WIFI_LIST /* 65655435 */:
                if (z2) {
                    _loadWifiList(this.permissionRequestResultCallback);
                } else {
                    this.permissionRequestResultCallback.error("WifiIotPlugin.Permission", "Fine location permission denied", null);
                }
                this.requestingPermission = false;
                return true;
            case PERMISSIONS_REQUEST_CODE_ACCESS_FINE_LOCATION_ON_LISTEN /* 65655436 */:
                if (z2) {
                    _onListen((EventChannel.EventSink) this.permissionRequestCookie.get(0));
                }
                this.requestingPermission = false;
                return true;
            case PERMISSIONS_REQUEST_CODE_ACCESS_FINE_LOCATION_FIND_AND_CONNECT /* 65655437 */:
                if (z2) {
                    _findAndConnect((MethodCall) this.permissionRequestCookie.get(0), this.permissionRequestResultCallback);
                } else {
                    this.permissionRequestResultCallback.error("WifiIotPlugin.Permission", "Fine location permission denied", null);
                }
                this.requestingPermission = false;
                return true;
            case PERMISSIONS_REQUEST_CODE_ACCESS_NETWORK_STATE_IS_CONNECTED /* 65655438 */:
                if (z2) {
                    _isConnected(this.permissionRequestResultCallback);
                } else {
                    this.permissionRequestResultCallback.error("WifiIotPlugin.Permission", "Network state permission denied", null);
                }
                this.requestingPermission = false;
                return true;
            default:
                this.requestingPermission = false;
                return false;
        }
    }
}
