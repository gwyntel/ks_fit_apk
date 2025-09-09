package com.kingsmith.miot;

import android.util.Log;
import com.chgocn.miot.BuildConfig;
import com.chgocn.miot.KsmbKingSmith;
import com.chgocn.miot.KsmbWalkingPad;
import com.google.gson.JsonObject;
import com.kingsmith.plugins.Protos;
import com.miot.api.CompletionHandler;
import com.miot.api.DeviceManager;
import com.miot.api.DeviceManipulator;
import com.miot.api.MiotManager;
import com.miot.common.abstractdevice.AbstractDevice;
import com.miot.common.abstractdevice.AbstractService;
import com.miot.common.device.firmware.MiotFirmware;
import com.miot.common.device.invocation.ActionInfo;
import com.miot.common.device.invocation.ActionInfoFactory;
import com.miot.common.device.invocation.PropertyInfo;
import com.miot.common.device.invocation.PropertyInfoFactory;
import com.miot.common.exception.MiotException;
import com.miot.common.property.Property;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class PanelDevice {
    private static final String TAG = "PanelDevice";
    private AbstractDevice device;
    private List<String> motionList = new ArrayList();
    private List<String> propertyList = new ArrayList();
    private AbstractService service;

    public PanelDevice(AbstractDevice abstractDevice) {
        this.device = abstractDevice;
        addProperty();
        if (abstractDevice instanceof KsmbKingSmith) {
            this.service = ((KsmbKingSmith) abstractDevice).mKsService;
            this.motionList.add("spm");
            this.propertyList.add(KsProperty.Key);
            this.propertyList.add(KsProperty.Key);
            this.propertyList.add(KsProperty.Key);
            this.propertyList.add(KsProperty.Prog);
            this.propertyList.add(KsProperty.Prog);
            this.propertyList.add(KsProperty.Prog);
            return;
        }
        this.service = ((KsmbWalkingPad) abstractDevice).mWpService;
        this.motionList.add(KsProperty.Button_id);
        this.propertyList.add(KsProperty.Sensitivity);
        this.propertyList.add(KsProperty.StartSpeed);
        this.propertyList.add(KsProperty.Disp);
        this.propertyList.add("auto");
        this.propertyList.add("initial");
        this.propertyList.add(KsProperty.Goal);
        this.propertyList.add(KsProperty.Goal);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0056  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.gson.JsonObject _parsePropertyInfo(com.miot.common.device.invocation.PropertyInfo r14, java.lang.String r15) {
        /*
            Method dump skipped, instructions count: 664
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.kingsmith.miot.PanelDevice._parsePropertyInfo(com.miot.common.device.invocation.PropertyInfo, java.lang.String):com.google.gson.JsonObject");
    }

    private void addProperty() {
        this.motionList.add(KsProperty.Speed);
        this.motionList.add(KsProperty.Dist);
        this.motionList.add("step");
        this.motionList.add("time");
        this.motionList.add(KsProperty.Cal);
        this.motionList.add(KsProperty.Lock);
        this.motionList.add(KsProperty.Power);
        this.motionList.add("state");
        this.motionList.add("mode");
        this.propertyList.add(KsProperty.Max);
        this.propertyList.add(KsProperty.Lock);
    }

    boolean c() {
        return this.device instanceof KsmbWalkingPad;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.miot.common.exception.MiotException */
    public void getMotionData(final ICallback iCallback) throws MiotException {
        try {
            if (!this.device.isConnectionEstablished()) {
                throw new MiotException("device not configurated connection");
            }
            PropertyInfo propertyInfoCreate = PropertyInfoFactory.create(this.service.getService());
            Iterator<String> it = this.motionList.iterator();
            while (it.hasNext()) {
                propertyInfoCreate.addProperty(this.service.getService().getProperty(it.next()));
            }
            MiotManager.getDeviceManipulator().readProperty(propertyInfoCreate, new DeviceManipulator.ReadPropertyCompletionHandler() { // from class: com.kingsmith.miot.PanelDevice.3
                public void onFailed(int i2, String str) {
                    Log.e(PanelDevice.TAG, "getMotionData onFailed:" + i2 + "\t" + str);
                    iCallback.onError(new HttpError(i2, str));
                }

                public void onSucceed(PropertyInfo propertyInfo) {
                    JsonObject jsonObject_parsePropertyInfo = PanelDevice.this._parsePropertyInfo(propertyInfo, null);
                    Log.e(PanelDevice.TAG, "getMotionData:" + jsonObject_parsePropertyInfo.toString());
                    iCallback.onSuccess(jsonObject_parsePropertyInfo);
                }
            });
        } catch (MiotException e2) {
            if (BuildConfig.DEBUG) {
                e2.printStackTrace();
            }
            iCallback.onError(e2);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.miot.common.exception.MiotException */
    public void getProperties(final ICallback iCallback) throws MiotException {
        try {
            if (!this.device.isConnectionEstablished()) {
                throw new MiotException("device not configurated connection");
            }
            PropertyInfo propertyInfoCreate = PropertyInfoFactory.create(this.service.getService());
            for (String str : this.propertyList) {
                Log.e(TAG, "getProperties property name:" + str);
                propertyInfoCreate.addProperty(this.service.getService().getProperty(str));
            }
            MiotManager.getDeviceManipulator().readProperty(propertyInfoCreate, new DeviceManipulator.ReadPropertyCompletionHandler() { // from class: com.kingsmith.miot.PanelDevice.2
                public void onFailed(int i2, String str2) {
                    iCallback.onError(new HttpError(i2, str2));
                }

                public void onSucceed(PropertyInfo propertyInfo) {
                    JsonObject jsonObject_parsePropertyInfo = PanelDevice.this._parsePropertyInfo(propertyInfo, null);
                    jsonObject_parsePropertyInfo.addProperty("bssid", PanelDevice.this.device.getDevice().getConnectionInfo().getBssid());
                    Log.e(PanelDevice.TAG, "_parsePropertyInfo json:" + jsonObject_parsePropertyInfo);
                    iCallback.onSuccess(jsonObject_parsePropertyInfo);
                }
            });
        } catch (MiotException e2) {
            if (BuildConfig.DEBUG) {
                e2.printStackTrace();
            }
            iCallback.onError(e2);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.miot.common.exception.MiotException */
    public void invokeMethod(Protos.PAction pAction, final ICallback iCallback) throws MiotException {
        final String action = pAction.getAction();
        HashMap map = new HashMap();
        if (action.equals("set_max")) {
            map.put(KsProperty.Max, Double.valueOf(Double.parseDouble(pAction.getValueList().get(0))));
        } else if (action.equals("set_lock")) {
            if (!(this.device instanceof KsmbKingSmith)) {
                map.put(KsProperty.Lock, Integer.valueOf(Integer.parseInt(pAction.getValueList().get(0))));
            } else if (Integer.parseInt(pAction.getValueList().get(0)) == 0) {
                map.put(KsProperty.Lock, "off");
            } else {
                map.put(KsProperty.Lock, "on");
            }
        } else if (action.equals("set_prog")) {
            map.put("id", Integer.valueOf(Integer.parseInt(pAction.getValueList().get(0))));
            map.put(KsProperty.Prog, pAction.getValueList().get(1));
        } else if (action.equals("set_key")) {
            map.put(KsProperty.Key1, Integer.valueOf(Integer.parseInt(pAction.getValueList().get(0))));
            map.put(KsProperty.Key2, Integer.valueOf(Integer.parseInt(pAction.getValueList().get(1))));
            map.put(KsProperty.Key3, Integer.valueOf(Integer.parseInt(pAction.getValueList().get(2))));
        } else if (action.equals("set_mode")) {
            boolean zC = c();
            Object objValueOf = pAction.getValueList().get(0);
            if (zC) {
                objValueOf = Integer.valueOf(Integer.parseInt((String) objValueOf));
            }
            map.put("mode", objValueOf);
        } else if (action.equals("set_state")) {
            map.put("state", pAction.getValueList().get(0));
        } else if (action.equals("set_power")) {
            map.put(KsProperty.Power, pAction.getValueList().get(0));
        } else if (action.equals("set_cali")) {
            map.put(KsProperty.Correct, Integer.valueOf(Integer.parseInt(pAction.getValueList().get(0))));
        } else if (action.equals("tutorial")) {
            map.put(KsProperty.NewStep, Integer.valueOf(Integer.parseInt(pAction.getValueList().get(0))));
        } else if (action.equals("set_speed")) {
            map.put(KsProperty.Speed, Double.valueOf(Double.parseDouble(pAction.getValueList().get(0))));
        } else if (action.equals("set_disp")) {
            map.put(KsProperty.Disp, Integer.valueOf(Integer.parseInt(pAction.getValueList().get(0))));
        } else if (action.equals("set_start_speed")) {
            map.put(KsProperty.StartSpeed, Double.valueOf(Double.parseDouble(pAction.getValueList().get(0))));
        } else if (action.equals("set_sensitivity")) {
            map.put(KsProperty.Sensitivity, Integer.valueOf(Integer.parseInt(pAction.getValueList().get(0)) + 1));
        } else if (action.equals("get_prog")) {
            map.put("id", Integer.valueOf(Integer.parseInt(pAction.getValueList().get(0))));
        } else {
            Log.e("TAG", "undefined method: " + action);
        }
        try {
            if (!this.device.isConnectionEstablished()) {
                throw new MiotException("device not configurated connection");
            }
            ActionInfo actionInfoCreate = ActionInfoFactory.create(this.service.getService(), action);
            for (Map.Entry entry : map.entrySet()) {
                if (!actionInfoCreate.setArgumentValue((String) entry.getKey(), entry.getValue())) {
                    throw new MiotException("invalid value");
                }
            }
            MiotManager.getDeviceManipulator().invoke(actionInfoCreate, new DeviceManipulator.InvokeCompletionHandler() { // from class: com.kingsmith.miot.PanelDevice.1
                public void onFailed(int i2, String str) {
                    iCallback.onError(new HttpError(i2, str));
                }

                public void onSucceed(ActionInfo actionInfo) {
                    Log.e(PanelDevice.TAG, "invokeMethod ActionInfo:" + actionInfo);
                    if (!action.equals("get_prog")) {
                        iCallback.onSuccess(null);
                        return;
                    }
                    String str = "";
                    for (Property property : actionInfo.getArguments()) {
                        String stringValue = property.getStringValue();
                        Log.e(PanelDevice.TAG, "invokeMethod ActionInfo:" + property.getInternalName() + "," + property.getStringValue() + "," + property.getFriendlyName() + "," + property.getDefinition());
                        str = stringValue;
                    }
                    List<Property> results = actionInfo.getResults();
                    PropertyInfo propertyInfo = new PropertyInfo();
                    for (Property property2 : results) {
                        property2.getStringValue();
                        Log.e(PanelDevice.TAG, "invokeMethod ActionInfo:" + actionInfo);
                        propertyInfo.addProperty(property2);
                    }
                    JsonObject jsonObject_parsePropertyInfo = PanelDevice.this._parsePropertyInfo(propertyInfo, str);
                    Log.e(PanelDevice.TAG, "property internal value 模式返回参数:" + jsonObject_parsePropertyInfo);
                    iCallback.onSuccess(jsonObject_parsePropertyInfo);
                }
            });
        } catch (MiotException e2) {
            if (BuildConfig.DEBUG) {
                e2.printStackTrace();
            }
            iCallback.onError(e2);
        }
    }

    public void queryOtaStatus(boolean z2, final ICallback iCallback) {
        DeviceManager.QueryFirmwareHandler queryFirmwareHandler = new DeviceManager.QueryFirmwareHandler() { // from class: com.kingsmith.miot.PanelDevice.7
            public void onFailed(int i2, String str) {
                iCallback.onError(new HttpError(i2, str));
            }

            public void onSucceed(MiotFirmware miotFirmware) {
                iCallback.onSuccess(miotFirmware);
            }
        };
        try {
            if (z2) {
                MiotManager.getDeviceManager().queryFirmwareUpgradeInfo(this.device, queryFirmwareHandler);
            } else {
                MiotManager.getDeviceManager().queryFirmwareInfo(this.device, queryFirmwareHandler);
            }
        } catch (MiotException e2) {
            iCallback.onError(e2);
        }
    }

    public void startUpgrade(final ICallback iCallback) {
        try {
            MiotManager.getDeviceManager().startUpgradeFirmware(this.device, new CompletionHandler() { // from class: com.kingsmith.miot.PanelDevice.8
                public void onFailed(int i2, String str) {
                    iCallback.onError(new HttpError(i2, str));
                }

                public void onSucceed() {
                    iCallback.onSuccess(null);
                }
            });
        } catch (MiotException e2) {
            iCallback.onError(e2);
        }
    }

    public void subscribe(final ICallback iCallback, final IChange iChange) {
        PropertyInfo propertyInfoCreate = PropertyInfoFactory.create(this.service.getService());
        Iterator<String> it = this.motionList.iterator();
        while (it.hasNext()) {
            propertyInfoCreate.addProperty(this.service.getService().getProperty(it.next()));
        }
        DeviceManipulator deviceManipulator = MiotManager.getDeviceManipulator();
        try {
            deviceManipulator.readProperty(propertyInfoCreate, new DeviceManipulator.ReadPropertyCompletionHandler() { // from class: com.kingsmith.miot.PanelDevice.4
                public void onFailed(int i2, String str) {
                    Log.e(PanelDevice.TAG, "getMotionData onFailed:" + i2 + "\t" + str);
                    iCallback.onError(new HttpError(i2, str));
                }

                public void onSucceed(PropertyInfo propertyInfo) {
                    JsonObject jsonObject_parsePropertyInfo = PanelDevice.this._parsePropertyInfo(propertyInfo, null);
                    Log.e(PanelDevice.TAG, "getMotionData:" + jsonObject_parsePropertyInfo.toString());
                    iCallback.onSuccess(jsonObject_parsePropertyInfo);
                }
            });
            deviceManipulator.addPropertyChangedListener(propertyInfoCreate, new DeviceManipulator.CompletionHandler() { // from class: com.kingsmith.miot.PanelDevice.5
                public void onFailed(int i2, String str) {
                }

                public void onSucceed() {
                }
            }, new DeviceManipulator.PropertyChangedListener() { // from class: com.kingsmith.miot.PanelDevice.6
                public void onPropertyChanged(PropertyInfo propertyInfo, String str) {
                    JsonObject jsonObject_parsePropertyInfo = PanelDevice.this._parsePropertyInfo(propertyInfo, null);
                    Log.e(PanelDevice.TAG, "subscribe:" + jsonObject_parsePropertyInfo.toString());
                    iChange.onChange(jsonObject_parsePropertyInfo);
                }
            });
        } catch (MiotException e2) {
            e2.printStackTrace();
            iCallback.onError(e2);
        }
    }
}
