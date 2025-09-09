package com.kingsmith.miot;

import android.util.Log;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.google.gson.JsonObject;
import com.kingsmith.miot.Protos;
import com.kingsmith.plugins.Protos;
import com.miot.common.abstractdevice.AbstractDevice;
import com.miot.common.device.ConnectionType;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ProtoMaker {
    static Protos.ListOfDeviceMi a(List list) {
        Protos.ListOfDeviceMi.Builder builderNewBuilder = Protos.ListOfDeviceMi.newBuilder();
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                builderNewBuilder.addDevice(b((AbstractDevice) it.next()));
            }
            builderNewBuilder.setCode(0);
        } else {
            builderNewBuilder.setCode(-1);
        }
        return builderNewBuilder.build();
    }

    static Protos.PDeviceMi b(AbstractDevice abstractDevice) {
        Protos.PDeviceMi.Builder builderNewBuilder = Protos.PDeviceMi.newBuilder();
        if (abstractDevice != null) {
            builderNewBuilder.setName(abstractDevice.getName());
            builderNewBuilder.setModel(abstractDevice.getDeviceModel());
            builderNewBuilder.setOwned(1);
            builderNewBuilder.setStatus(abstractDevice.isOnline() ? 1 : 3);
            builderNewBuilder.setNetType(abstractDevice.getConnectionType() == ConnectionType.MIOT_WIFI ? "NET_WIFI" : "NET_OTHER");
            builderNewBuilder.setIotId(abstractDevice.getDeviceId());
            builderNewBuilder.setAddress(abstractDevice.getAddress());
        }
        return builderNewBuilder.build();
    }

    static Protos.PMotion c(JsonObject jsonObject, boolean z2) {
        Protos.PMotion.Builder builderNewBuilder = Protos.PMotion.newBuilder();
        if (jsonObject != null) {
            builderNewBuilder.setCal(jsonObject.get(KsProperty.Cal).getAsInt());
            builderNewBuilder.setDist(jsonObject.get(KsProperty.Dist).getAsInt());
            builderNewBuilder.setTime(jsonObject.get("time").getAsInt());
            builderNewBuilder.setStep(jsonObject.get("step").getAsInt());
            builderNewBuilder.setSpeed(jsonObject.get(KsProperty.Speed).getAsDouble());
            if (z2) {
                if (jsonObject.get(KsProperty.Button_id) != null) {
                    builderNewBuilder.setButtonId(jsonObject.get(KsProperty.Button_id).getAsInt());
                } else {
                    builderNewBuilder.setButtonId(0);
                }
            } else if (jsonObject.get("spm") != null) {
                builderNewBuilder.setSpm(jsonObject.get("spm").getAsInt());
            } else {
                builderNewBuilder.setSpm(0);
            }
            builderNewBuilder.setState(jsonObject.get("state").getAsString());
            builderNewBuilder.setMode(jsonObject.get("mode").getAsString());
            builderNewBuilder.setPower(jsonObject.get(KsProperty.Power).getAsString());
            builderNewBuilder.setChildLock(jsonObject.get(KsProperty.Lock).getAsString());
        }
        return builderNewBuilder.build();
    }

    static Protos.PPropertiesTr d(JsonObject jsonObject, int i2) {
        Log.e("TAG", jsonObject != null ? jsonObject.toString() : TmpConstant.GROUP_ROLE_UNKNOWN);
        Protos.PPropertiesTr.Builder builderNewBuilder = Protos.PPropertiesTr.newBuilder();
        Protos.PProperties.Builder builderNewBuilder2 = Protos.PProperties.newBuilder();
        if (jsonObject != null) {
            builderNewBuilder2.setMax(jsonObject.get(KsProperty.Max).getAsDouble());
            builderNewBuilder2.setChildLock(jsonObject.get(KsProperty.Lock).getAsString());
            builderNewBuilder2.setBssid(jsonObject.get("bssid").getAsString());
        }
        builderNewBuilder.setParent(builderNewBuilder2);
        if (jsonObject != null && jsonObject.get(KsProperty.Key1) != null) {
            builderNewBuilder.setKey1(jsonObject.get(KsProperty.Key1).getAsInt());
            builderNewBuilder.setKey2(jsonObject.get(KsProperty.Key2).getAsInt());
            builderNewBuilder.setKey3(jsonObject.get(KsProperty.Key3).getAsInt());
        }
        Log.e("TAG", builderNewBuilder2.toString());
        builderNewBuilder.setParent(builderNewBuilder2);
        return builderNewBuilder.build();
    }

    static Protos.PPropertiesWp e(JsonObject jsonObject) {
        Protos.PPropertiesWp.Builder builderNewBuilder = Protos.PPropertiesWp.newBuilder();
        Protos.PProperties.Builder builderNewBuilder2 = Protos.PProperties.newBuilder();
        if (jsonObject != null) {
            builderNewBuilder2.setMax(jsonObject.get(KsProperty.Max).getAsDouble());
            builderNewBuilder2.setChildLock(jsonObject.get(KsProperty.Lock).getAsString());
            builderNewBuilder2.setBssid(jsonObject.get("bssid").getAsString());
        }
        builderNewBuilder.setParent(builderNewBuilder2);
        if (jsonObject != null) {
            builderNewBuilder.setDisp(jsonObject.get(KsProperty.Disp).getAsInt());
            builderNewBuilder.setGoalType(jsonObject.get(KsProperty.GoalType).getAsInt());
            builderNewBuilder.setGoalValue(jsonObject.get(KsProperty.GoalValue).getAsInt());
            builderNewBuilder.setSensitivity(jsonObject.get(KsProperty.Sensitivity).getAsInt() - 1);
            builderNewBuilder.setStartSpeed(jsonObject.get(KsProperty.StartSpeed).getAsDouble());
            builderNewBuilder.setStartupType(jsonObject.get("auto").getAsInt());
            builderNewBuilder.setInitial(jsonObject.get("initial").getAsInt());
        } else {
            builderNewBuilder.setInitial(-1);
        }
        return builderNewBuilder.build();
    }
}
