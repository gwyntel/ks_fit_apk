package com.umeng.message.api;

import com.umeng.message.UmengMessageService;
import com.umeng.message.tag.TagManager;

/* loaded from: classes4.dex */
public interface UPushApi {
    void addAlias(String str, String str2, UPushAliasCallback uPushAliasCallback);

    void deleteAlias(String str, String str2, UPushAliasCallback uPushAliasCallback);

    void disable(UPushSettingCallback uPushSettingCallback);

    void enable(UPushSettingCallback uPushSettingCallback);

    int getDisplayNotificationNumber();

    String getMessageAppkey();

    String getMessageChannel();

    UPushMessageHandler getMessageHandler();

    UPushMessageNotifyApi getMessageNotifyApi();

    String getMessageSecret();

    int getMuteDurationSeconds();

    int getNoDisturbEndHour();

    int getNoDisturbEndMinute();

    int getNoDisturbStartHour();

    int getNoDisturbStartMinute();

    String getNotificationChannelName();

    UPushMessageHandler getNotificationClickHandler();

    boolean getNotificationOnForeground();

    int getNotificationPlayLights();

    int getNotificationPlaySound();

    int getNotificationPlayVibrate();

    String getNotificationSilenceChannelName();

    String getPushIntentServiceClass();

    UPushRegisterCallback getRegisterCallback();

    String getRegistrationId();

    String getResourcePackageName();

    UPushSettingCallback getSettingCallback();

    TagManager getTagManager();

    UPushThirdTokenCallback getThirdTokenCallback();

    boolean isPushCheck();

    void keepLowPowerMode(boolean z2);

    void onAppStart();

    void register(UPushRegisterCallback uPushRegisterCallback);

    void setAccsHeartbeatEnable(boolean z2);

    void setAlias(String str, String str2, UPushAliasCallback uPushAliasCallback);

    void setDisplayNotificationNumber(int i2);

    void setEnableAlarmHeartbeat(boolean z2);

    void setEnableForeground(boolean z2);

    void setEnableJobHeartbeat(boolean z2);

    void setMessageHandler(UPushMessageHandler uPushMessageHandler);

    void setMuteDurationSeconds(int i2);

    void setNoDisturbMode(int i2, int i3, int i4, int i5);

    void setNotificationChannelName(String str);

    void setNotificationClickHandler(UPushMessageHandler uPushMessageHandler);

    void setNotificationOnForeground(boolean z2);

    void setNotificationPlayLights(int i2);

    void setNotificationPlaySound(int i2);

    void setNotificationPlayVibrate(int i2);

    void setNotificationSilenceChannelName(String str);

    void setPullUpEnable(boolean z2);

    void setPushCheck(boolean z2);

    <U extends UmengMessageService> void setPushIntentServiceClass(Class<U> cls);

    void setRegisterCallback(UPushRegisterCallback uPushRegisterCallback);

    void setResourcePackageName(String str);

    void setSettingCallback(UPushSettingCallback uPushSettingCallback);

    void setThirdTokenCallback(UPushThirdTokenCallback uPushThirdTokenCallback);
}
