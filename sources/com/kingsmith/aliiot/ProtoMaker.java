package com.kingsmith.aliiot;

import com.aliyun.iot.aep.sdk.log.ALog;
import com.aliyun.iot.aep.sdk.login.data.UserInfo;
import com.kingsmith.aliiot.Properties;
import com.kingsmith.aliiot.Protos;
import com.kingsmith.aliiot.bean.OTADeviceDetailInfo;
import com.kingsmith.aliiot.bean.OTAStatusInfo;
import com.kingsmith.plugin.Protos;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ProtoMaker {
    private static long buttonIdTimeStamp = 0;
    private static long devicePidTimeStamp = 0;
    private static String preRecord = "";
    private static int preRecordId = -1;
    private static long statusTimeStamp;

    static Protos.UserInfo from(UserInfo userInfo) {
        Protos.UserInfo.Builder builderNewBuilder = Protos.UserInfo.newBuilder();
        if (userInfo != null) {
            String str = userInfo.userId;
            if (str != null) {
                builderNewBuilder.setUserId(str);
            }
            String str2 = userInfo.openId;
            if (str2 != null) {
                builderNewBuilder.setOpenId(str2);
            }
            String str3 = userInfo.userNick;
            if (str3 != null) {
                builderNewBuilder.setUserNick(str3);
            }
            String str4 = userInfo.mobileLocationCode;
            if (str4 != null) {
                builderNewBuilder.setMobileLocationCode(str4);
            }
            String str5 = userInfo.userPhone;
            if (str5 != null) {
                builderNewBuilder.setUserPhone(str5);
            }
            String str6 = userInfo.userAvatarUrl;
            if (str6 != null) {
                builderNewBuilder.setUserAvatarUrl(str6);
            }
            String str7 = userInfo.userEmail;
            if (str7 != null) {
                builderNewBuilder.setUserEmail(str7);
            }
        }
        return builderNewBuilder.build();
    }

    private static boolean isValid(long j2) {
        return j2 >= devicePidTimeStamp && j2 >= statusTimeStamp;
    }

    static Protos.DeviceInfo from(DeviceInfoBean deviceInfoBean) {
        Protos.DeviceInfo.Builder builderNewBuilder = Protos.DeviceInfo.newBuilder();
        if (deviceInfoBean != null) {
            builderNewBuilder.setGmtModified(deviceInfoBean.getGmtModified());
            if (deviceInfoBean.getCategoryImage() != null) {
                builderNewBuilder.setCategoryImage(deviceInfoBean.getCategoryImage());
            }
            if (deviceInfoBean.getNodeType() != null) {
                builderNewBuilder.setNodeType(deviceInfoBean.getNodeType());
            }
            if (deviceInfoBean.getNetType() != null) {
                builderNewBuilder.setNetType(deviceInfoBean.getNetType());
            }
            if (deviceInfoBean.getProductKey() != null) {
                builderNewBuilder.setProductKey(deviceInfoBean.getProductKey());
            }
            if (deviceInfoBean.getDeviceName() != null) {
                builderNewBuilder.setDeviceName(deviceInfoBean.getDeviceName());
            }
            if (deviceInfoBean.getIdentityAlias() != null) {
                builderNewBuilder.setIdentityAlias(deviceInfoBean.getIdentityAlias());
            }
            if (deviceInfoBean.getIotId() != null) {
                builderNewBuilder.setIotId(deviceInfoBean.getIotId());
            }
            if (deviceInfoBean.getIdentityId() != null) {
                builderNewBuilder.setIdentityId(deviceInfoBean.getIdentityId());
            }
            if (deviceInfoBean.getThingType() != null) {
                builderNewBuilder.setThingType(deviceInfoBean.getThingType());
            }
            builderNewBuilder.setOwned(deviceInfoBean.getOwned());
            builderNewBuilder.setStatus(deviceInfoBean.getStatus());
            if (deviceInfoBean.getNickName() != null) {
                builderNewBuilder.setNickName(deviceInfoBean.getNickName());
            }
        }
        return builderNewBuilder.build();
    }

    static Protos.ListOfDeviceAli from(List<DeviceInfoBean> list) {
        Protos.ListOfDeviceAli.Builder builderNewBuilder = Protos.ListOfDeviceAli.newBuilder();
        if (list != null) {
            Iterator<DeviceInfoBean> it = list.iterator();
            while (it.hasNext()) {
                builderNewBuilder.addDeviceList(from(it.next()));
            }
            builderNewBuilder.setCode(0);
        } else {
            builderNewBuilder.setCode(-1);
        }
        return builderNewBuilder.build();
    }

    static Protos.Property from(Properties.Property property) {
        Protos.Property.Builder builderNewBuilder = Protos.Property.newBuilder();
        builderNewBuilder.setTime(property.getTime());
        if (property.getValue() != null) {
            builderNewBuilder.setValue(property.getValue().toString());
        }
        return builderNewBuilder.build();
    }

    static Protos.AProperties from(Properties properties, int i2) {
        Protos.AProperties.Builder builderNewBuilder = Protos.AProperties.newBuilder();
        builderNewBuilder.setCode(i2);
        if (properties != null) {
            if (properties.getRunningSteps() != null) {
                builderNewBuilder.setRunningSteps(from(properties.getRunningSteps()));
            }
            if (properties.getBurnCalories() != null) {
                builderNewBuilder.setBurnCalories(from(properties.getBurnCalories()));
            }
            if (properties.getCurrentSpeed() != null) {
                builderNewBuilder.setCurrentSpeed(from(properties.getCurrentSpeed()));
            }
            if (properties.getRunningDistance() != null) {
                builderNewBuilder.setRunningDistance(from(properties.getRunningDistance()));
            }
            if (properties.getRunningTotalTime() != null) {
                builderNewBuilder.setRunningTotalTime(from(properties.getRunningTotalTime()));
            }
            if (properties.getHeartRate() != null) {
                builderNewBuilder.setHeartRate(from(properties.getHeartRate()));
            }
            ALog.d("getGradient", "" + properties.getGradient());
            if (properties.getGradient() != null) {
                builderNewBuilder.setGradient(from(properties.getGradient()));
            }
            if (properties.getWIFI_AP_BSSID() != null) {
                builderNewBuilder.setWifiApBassid(from(properties.getWIFI_AP_BSSID()));
            }
            if (properties.getChildLockSwitch() != null) {
                builderNewBuilder.setChildLockSwitch(from(properties.getChildLockSwitch()));
            }
            if (properties.getConSpMode() != null) {
                builderNewBuilder.setConSpMode(from(properties.getConSpMode()));
            }
            if (properties.getButtonId() != null) {
                builderNewBuilder.setButtonId(from(properties.getButtonId()));
            }
            if (properties.getPanelDisplay() != null) {
                builderNewBuilder.setPanelDisplay(from(properties.getPanelDisplay()));
            }
            if (properties.getMax() != null) {
                builderNewBuilder.setMax(from(properties.getMax()));
            }
            if (properties.getControlMode() != null) {
                builderNewBuilder.setControlMode(from(properties.getControlMode()));
            }
            if (properties.getVelocitySensitivity() != null) {
                builderNewBuilder.setVelocitySensitivity(from(properties.getVelocitySensitivity()));
            }
            if (properties.getStartSpeed() != null) {
                builderNewBuilder.setStartSpeed(from(properties.getStartSpeed()));
            }
            if (properties.getGoal() != null) {
                builderNewBuilder.setGoal(from(properties.getGoal()));
            }
            if (properties.getRunState() != null) {
                builderNewBuilder.setRunState(from(properties.getRunState()));
            }
            if (properties.getMcu_version() != null) {
                builderNewBuilder.setMcuVersion(from(properties.getMcu_version()));
            }
            if (properties.getRecord() != null) {
                builderNewBuilder.setRecord(from(properties.getRecord()));
            }
            if (properties.getHandrail() != null) {
                builderNewBuilder.setHandrail(from(properties.getHandrail()));
            }
            if (properties.getInitial() != null) {
                builderNewBuilder.setInitial(from(properties.getInitial()));
            }
            if (properties.getUnit() != null) {
                builderNewBuilder.setUnit(from(properties.getUnit()));
            }
            if (properties.getSpm() != null) {
                builderNewBuilder.setSpm(from(properties.getSpm()));
            }
            if (properties.getMaxW() != null) {
                builderNewBuilder.setMaxW(from(properties.getMaxW()));
            }
            if (properties.get_sys_device_pid() != null) {
                builderNewBuilder.setSysDevicePid(from(properties.get_sys_device_pid()));
            }
        }
        return builderNewBuilder.build();
    }

    static Protos.POTAFirmware from(OTADeviceDetailInfo oTADeviceDetailInfo) {
        Protos.POTAFirmware.Builder builderNewBuilder = Protos.POTAFirmware.newBuilder();
        String str = oTADeviceDetailInfo.currentVersion;
        if (str != null) {
            builderNewBuilder.setCurrentVersion(str);
        }
        String str2 = oTADeviceDetailInfo.version;
        if (str2 != null) {
            builderNewBuilder.setNewVersion(str2);
        }
        String str3 = oTADeviceDetailInfo.desc;
        if (str3 != null) {
            builderNewBuilder.setDesc(str3);
        }
        return builderNewBuilder.build();
    }

    static Protos.POTAStatus from(OTAStatusInfo oTAStatusInfo) {
        Protos.POTAStatus.Builder builderNewBuilder = Protos.POTAStatus.newBuilder();
        String str = oTAStatusInfo.step;
        if (str != null) {
            builderNewBuilder.setProgress(Integer.parseInt(str));
        }
        String str2 = oTAStatusInfo.upgradeStatus;
        if (str2 != null) {
            builderNewBuilder.setStatus(str2);
        }
        return builderNewBuilder.build();
    }
}
