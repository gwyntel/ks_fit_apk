package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class DeviceWidgetInfo {
    public static final int DISPLAY_NO = 0;
    public static final int DISPLAY_YES = 1;
    public static final int WIDGET_ABOUT = 30;
    public static final int WIDGET_ALARM_CLOCK = 19;
    public static final int WIDGET_ALIPAY = 31;
    public static final int WIDGET_BO = 7;
    public static final int WIDGET_BODY_TEMPERATURE = 9;
    public static final int WIDGET_BP = 6;
    public static final int WIDGET_BREATH = 15;
    public static final int WIDGET_BREATHING_TRAINING = 14;
    public static final int WIDGET_CALL = 4;
    public static final int WIDGET_CAMERA = 20;
    public static final int WIDGET_COUNTDOWN = 17;
    public static final int WIDGET_DIAL_SWITCH = 24;
    public static final int WIDGET_FIND_PHONE = 18;
    public static final int WIDGET_FLASH_LIGHT = 21;
    public static final int WIDGET_HEART_RATE = 5;
    public static final int WIDGET_INFOS = 10;
    public static final int WIDGET_MORE = 23;
    public static final int WIDGET_MUSIC = 8;
    public static final int WIDGET_ONE_CLICK_MEASUREMENT = 33;
    public static final int WIDGET_PHYSIOLOGICAL = 25;
    public static final int WIDGET_PRESSURE = 11;
    public static final int WIDGET_REBOOT = 28;
    public static final int WIDGET_RESET = 29;
    public static final int WIDGET_SETTING = 22;
    public static final int WIDGET_SHUTDOWN = 27;
    public static final int WIDGET_SLEEP = 12;
    public static final int WIDGET_SPORTS = 2;
    public static final int WIDGET_SPORTS_RECORD = 3;
    public static final int WIDGET_STATUS = 1;
    public static final int WIDGET_STOP_WATCH = 16;
    public static final int WIDGET_THEME = 26;
    public static final int WIDGET_VOICE_ASSISTANT = 34;
    public static final int WIDGET_WEATHER = 13;
    public static final int WIDGET_WORLD_CLOCK = 32;
    private int widgetDisplayState;
    private int widgetId;
    private int widgetPosition;

    public DeviceWidgetInfo() {
        this.widgetDisplayState = 0;
    }

    public int getWidgetDisplayState() {
        return this.widgetDisplayState;
    }

    public int getWidgetId() {
        return this.widgetId;
    }

    public int getWidgetPosition() {
        return this.widgetPosition;
    }

    public void setWidgetDisplayState(int i2) {
        this.widgetDisplayState = i2;
    }

    public void setWidgetId(int i2) {
        this.widgetId = i2;
    }

    public void setWidgetPosition(int i2) {
        this.widgetPosition = i2;
    }

    public DeviceWidgetInfo(int i2, int i3) {
        this.widgetId = i2;
        this.widgetDisplayState = i3;
    }

    public DeviceWidgetInfo(int i2, int i3, int i4) {
        this.widgetId = i2;
        this.widgetDisplayState = i3;
        this.widgetPosition = i4;
    }
}
