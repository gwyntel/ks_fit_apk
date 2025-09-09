package com.aliyun.alink.linksdk.tmp.timing;

import android.text.TextUtils;
import android.util.Pair;
import androidx.webkit.ProxyConfig;
import com.aliyun.alink.linksdk.tmp.timing.DeviceTimerAttributeModel;
import com.xiaomi.mipush.sdk.Constants;
import com.yc.utesdk.utils.open.CalendarUtils;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/* loaded from: classes2.dex */
public class MeshTimerModel implements Serializable {
    private String attributesTargets;
    private String days;
    private TimerEnableType enableType;
    private String endTime;
    private int runTime;
    private int sleepTime;
    private String time;
    private int timeZone;
    private String timerID;
    private TimerType timerType;

    /* renamed from: com.aliyun.alink.linksdk.tmp.timing.MeshTimerModel$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$aliyun$alink$linksdk$tmp$timing$TimerType;

        static {
            int[] iArr = new int[TimerType.values().length];
            $SwitchMap$com$aliyun$alink$linksdk$tmp$timing$TimerType = iArr;
            try {
                iArr[TimerType.TIMER_NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aliyun$alink$linksdk$tmp$timing$TimerType[TimerType.TIMER_NORMAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$aliyun$alink$linksdk$tmp$timing$TimerType[TimerType.TIMER_CIRCULATION.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$aliyun$alink$linksdk$tmp$timing$TimerType[TimerType.TIMER_COUNTDOWN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static class CirculationTimerBuilder extends BaseTimerBuilder<CirculationTimerBuilder> {
        private String mDays;
        private String mEndTime;
        private int mRunTime;
        private int mSleepTime;

        public CirculationTimerBuilder() {
            super(null);
        }

        public MeshTimerModel build() {
            return new MeshTimerModel(TimerType.TIMER_CIRCULATION, this.mTargets, this.mTime, this.mDays, this.mEnable, this.mTimeZone, this.mEndTime, this.mRunTime, this.mSleepTime, null);
        }

        public CirculationTimerBuilder setDays(String str) {
            this.mDays = str;
            return this;
        }

        public CirculationTimerBuilder setEndTime(String str) {
            this.mEndTime = str;
            return this;
        }

        public CirculationTimerBuilder setRunTime(int i2) {
            this.mRunTime = i2;
            return this;
        }

        public CirculationTimerBuilder setSleepTime(int i2) {
            this.mSleepTime = i2;
            return this;
        }
    }

    public static class CountDownTimerBuilder extends BaseTimerBuilder<CountDownTimerBuilder> {
        public CountDownTimerBuilder() {
            super(null);
        }

        public MeshTimerModel build() {
            return new MeshTimerModel(TimerType.TIMER_COUNTDOWN, this.mTargets, this.mTime, "", this.mEnable, this.mTimeZone, null);
        }
    }

    public static class NormalTimerBuilder extends BaseTimerBuilder<NormalTimerBuilder> {
        private String mDays;

        public NormalTimerBuilder() {
            super(null);
        }

        public MeshTimerModel build() {
            return new MeshTimerModel(TimerType.TIMER_NORMAL, this.mTargets, this.mTime, this.mDays, this.mEnable, this.mTimeZone, null);
        }

        public NormalTimerBuilder setDays(String str) {
            this.mDays = str;
            return this;
        }
    }

    /* synthetic */ MeshTimerModel(TimerType timerType, String str, String str2, String str3, boolean z2, int i2, AnonymousClass1 anonymousClass1) {
        this(timerType, str, str2, str3, z2, i2);
    }

    public static MeshTimerModel fromTimerAttributeValueItem(DeviceTimerAttributeModel.ValueItem valueItem) {
        if (valueItem == null) {
            return null;
        }
        MeshTimerModel meshTimerModel = new MeshTimerModel();
        meshTimerModel.timerType = TimerType.fromValue(valueItem.Y);
        meshTimerModel.enableType = TimerEnableType.fromTypeValue(valueItem.E);
        TimerType timerType = meshTimerModel.timerType;
        if (timerType == TimerType.TIMER_NONE) {
            return meshTimerModel;
        }
        meshTimerModel.attributesTargets = valueItem.A;
        Pair<String, String> timeAndDaysFromCronExpression = getTimeAndDaysFromCronExpression(timerType, valueItem.T);
        meshTimerModel.time = (String) timeAndDaysFromCronExpression.first;
        meshTimerModel.days = (String) timeAndDaysFromCronExpression.second;
        meshTimerModel.timeZone = valueItem.Z;
        if (meshTimerModel.timerType == TimerType.TIMER_CIRCULATION) {
            meshTimerModel.endTime = valueItem.N;
            meshTimerModel.runTime = valueItem.R;
            meshTimerModel.sleepTime = valueItem.S;
        }
        return meshTimerModel;
    }

    private String getCronExpression() throws ParseException {
        String strValueOf;
        String strValueOf2;
        String str;
        String strValueOf3;
        String strValueOf4;
        String strValueOf5;
        GregorianCalendar gregorianCalendar;
        int i2 = AnonymousClass1.$SwitchMap$com$aliyun$alink$linksdk$tmp$timing$TimerType[this.timerType.ordinal()];
        String str2 = "?";
        if (i2 == 2 || i2 == 3) {
            String[] strArrSplit = this.time.split(":");
            strValueOf = strArrSplit[0];
            strValueOf2 = strArrSplit[1];
            str = this.days;
            if (TextUtils.isEmpty(str) || str.length() == 0) {
                Date date = new Date();
                GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
                gregorianCalendar2.setTime(date);
                String strValueOf6 = String.valueOf(gregorianCalendar2.get(5));
                String strValueOf7 = String.valueOf(gregorianCalendar2.get(2) + 1);
                String strValueOf8 = String.valueOf(gregorianCalendar2.get(1));
                try {
                    Date date2 = new SimpleDateFormat(CalendarUtils.yyyy_MM_dd_HH_mm, Locale.getDefault()).parse(strValueOf8 + Constants.ACCEPT_TIME_SEPARATOR_SERVER + strValueOf7 + Constants.ACCEPT_TIME_SEPARATOR_SERVER + strValueOf6 + " " + strValueOf + ":" + strValueOf2);
                    if (date.compareTo(date2) >= 0) {
                        gregorianCalendar2.setTime(new Date(date2.getTime() + 86400000));
                        strValueOf6 = String.valueOf(gregorianCalendar2.get(5));
                        strValueOf7 = String.valueOf(gregorianCalendar2.get(2) + 1);
                        strValueOf8 = String.valueOf(gregorianCalendar2.get(1));
                    }
                } catch (ParseException e2) {
                    e2.printStackTrace();
                }
                strValueOf3 = strValueOf8;
                str = "?";
                str2 = strValueOf6;
                strValueOf4 = strValueOf7;
            } else {
                strValueOf3 = ProxyConfig.MATCH_ALL_SCHEMES;
                strValueOf4 = ProxyConfig.MATCH_ALL_SCHEMES;
            }
        } else {
            strValueOf3 = "";
            if (i2 != 4) {
                return "";
            }
            try {
                Date date3 = new SimpleDateFormat(CalendarUtils.yyyy_MM_dd_HH_mm, Locale.getDefault()).parse(this.time);
                gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTime(date3);
                strValueOf2 = String.valueOf(gregorianCalendar.get(12));
            } catch (ParseException e3) {
                e = e3;
                strValueOf2 = "";
                strValueOf = strValueOf2;
            }
            try {
                strValueOf = String.valueOf(gregorianCalendar.get(11));
                try {
                    strValueOf5 = String.valueOf(gregorianCalendar.get(5));
                    try {
                        strValueOf4 = String.valueOf(gregorianCalendar.get(2) + 1);
                    } catch (ParseException e4) {
                        e = e4;
                        strValueOf4 = "";
                    }
                } catch (ParseException e5) {
                    e = e5;
                    strValueOf5 = "";
                    strValueOf4 = strValueOf5;
                    e.printStackTrace();
                    str = "?";
                    str2 = strValueOf5;
                    return String.format("%s %s %s %s %s %s", strValueOf2, strValueOf, str2, strValueOf4, str, strValueOf3);
                }
                try {
                    strValueOf3 = String.valueOf(gregorianCalendar.get(1));
                } catch (ParseException e6) {
                    e = e6;
                    e.printStackTrace();
                    str = "?";
                    str2 = strValueOf5;
                    return String.format("%s %s %s %s %s %s", strValueOf2, strValueOf, str2, strValueOf4, str, strValueOf3);
                }
            } catch (ParseException e7) {
                e = e7;
                strValueOf = "";
                strValueOf5 = strValueOf;
                strValueOf4 = strValueOf5;
                e.printStackTrace();
                str = "?";
                str2 = strValueOf5;
                return String.format("%s %s %s %s %s %s", strValueOf2, strValueOf, str2, strValueOf4, str, strValueOf3);
            }
            str = "?";
            str2 = strValueOf5;
        }
        return String.format("%s %s %s %s %s %s", strValueOf2, strValueOf, str2, strValueOf4, str, strValueOf3);
    }

    private static Pair<String, String> getTimeAndDaysFromCronExpression(TimerType timerType, String str) {
        String[] strArrSplit = str.split("\\s+");
        if (strArrSplit.length < 6) {
            return new Pair<>("", "");
        }
        Calendar calendar = Calendar.getInstance();
        if (TimerType.TIMER_COUNTDOWN == timerType) {
            calendar.set(Integer.parseInt(strArrSplit[5]), Integer.parseInt(strArrSplit[3]) - 1, Integer.parseInt(strArrSplit[2]), Integer.parseInt(strArrSplit[1]), Integer.parseInt(strArrSplit[0]));
            return new Pair<>(new SimpleDateFormat(CalendarUtils.yyyy_MM_dd_HH_mm).format(calendar.getTime()), "");
        }
        calendar.set(11, Integer.parseInt(strArrSplit[1]));
        calendar.set(12, Integer.parseInt(strArrSplit[0]));
        String str2 = new SimpleDateFormat("HH:mm").format(calendar.getTime());
        String str3 = strArrSplit[4];
        return new Pair<>(str2, "?".equals(str3) ? "" : str3);
    }

    public String getAttributesTargets() {
        return this.attributesTargets;
    }

    public String getDays() {
        return this.days;
    }

    public TimerEnableType getEnableType() {
        return this.enableType;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public long getRunTime() {
        return this.runTime;
    }

    public int getSleepTime() {
        return this.sleepTime;
    }

    public String getTime() {
        return this.time;
    }

    public int getTimeZone() {
        return this.timeZone;
    }

    public String getTimerID() {
        return this.timerID;
    }

    public TimerType getTimerType() {
        return this.timerType;
    }

    public void setAttributesTargets(String str) {
        this.attributesTargets = str;
    }

    public void setDays(String str) {
        this.days = str;
    }

    public void setEnableType(TimerEnableType timerEnableType) {
        this.enableType = timerEnableType;
    }

    public void setEndTime(String str) {
        this.endTime = str;
    }

    public void setRunTime(int i2) {
        this.runTime = i2;
    }

    public void setSleepTime(int i2) {
        this.sleepTime = i2;
    }

    public void setTime(String str) {
        this.time = str;
    }

    public void setTimeZone(int i2) {
        this.timeZone = i2;
    }

    public void setTimerID(String str) {
        this.timerID = str;
    }

    public DeviceTimerAttributeModel.ValueItem toAttributeModel() {
        DeviceTimerAttributeModel.ValueItem valueItem = new DeviceTimerAttributeModel.ValueItem();
        int i2 = AnonymousClass1.$SwitchMap$com$aliyun$alink$linksdk$tmp$timing$TimerType[this.timerType.ordinal()];
        if (i2 != 1) {
            if (i2 == 3) {
                valueItem.R = this.runTime;
                valueItem.S = this.sleepTime;
                valueItem.N = this.endTime;
            }
        } else if (TextUtils.isEmpty(this.time) || this.time.length() == 0) {
            valueItem.Y = 0;
            valueItem.E = 0;
            return valueItem;
        }
        valueItem.A = this.attributesTargets;
        valueItem.T = getCronExpression();
        valueItem.Y = this.timerType.getTypeValue();
        valueItem.Z = this.timeZone;
        valueItem.E = this.enableType != TimerEnableType.TIMER_ENABLE ? 0 : 1;
        return valueItem;
    }

    private static class BaseTimerBuilder<T> {
        protected boolean mEnable;
        protected String mTargets;
        protected String mTime;
        protected int mTimeZone;

        private BaseTimerBuilder() {
            this.mEnable = true;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T setTargets(String str) {
            this.mTargets = str;
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T setTime(String str) {
            this.mTime = str;
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T setTimeZone(int i2) {
            this.mTimeZone = i2;
            return this;
        }

        /* synthetic */ BaseTimerBuilder(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    /* synthetic */ MeshTimerModel(TimerType timerType, String str, String str2, String str3, boolean z2, int i2, String str4, int i3, int i4, AnonymousClass1 anonymousClass1) {
        this(timerType, str, str2, str3, z2, i2, str4, i3, i4);
    }

    private MeshTimerModel() {
    }

    private MeshTimerModel(TimerType timerType, String str, String str2, String str3, boolean z2, int i2) {
        this.timerType = timerType;
        this.attributesTargets = str;
        this.time = str2;
        this.days = str3;
        this.enableType = z2 ? TimerEnableType.TIMER_ENABLE : TimerEnableType.TIMER_ENABLE_NONE;
        this.timeZone = i2;
    }

    private MeshTimerModel(TimerType timerType, String str, String str2, String str3, boolean z2, int i2, String str4, int i3, int i4) {
        this.timerType = timerType;
        this.attributesTargets = str;
        this.time = str2;
        this.days = str3;
        this.enableType = z2 ? TimerEnableType.TIMER_ENABLE : TimerEnableType.TIMER_ENABLE_NONE;
        this.timeZone = i2;
        this.endTime = str4;
        this.runTime = i3;
        this.sleepTime = i4;
    }
}
