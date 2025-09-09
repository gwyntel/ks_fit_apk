package com.yc.utesdk.command;

import android.text.TextUtils;
import com.alibaba.ailabs.iot.aisbase.Constants;
import com.yc.utesdk.ble.open.DeviceMode;
import com.yc.utesdk.utils.open.CalendarUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes4.dex */
public class CommandUtil {
    public static final int COMMAND_TYPE_ACTIVITY_TIME = 13;
    public static final int COMMAND_TYPE_BALL = 8;
    public static final int COMMAND_TYPE_BODY_COMPOSITION = 10;
    public static final int COMMAND_TYPE_BP = 4;
    public static final int COMMAND_TYPE_BREATHE = 11;
    public static final int COMMAND_TYPE_MULTIPLE_SPORTS = 9;
    public static final int COMMAND_TYPE_OXYGEN = 12;
    public static final int COMMAND_TYPE_RATE = 3;
    public static final int COMMAND_TYPE_RIDE = 7;
    public static final int COMMAND_TYPE_SKIP = 6;
    public static final int COMMAND_TYPE_SLEEP = 2;
    public static final int COMMAND_TYPE_SLEEP_SW = 15;
    public static final int COMMAND_TYPE_STEP = 1;
    public static final int COMMAND_TYPE_STEP_SW = 14;
    public static final int COMMAND_TYPE_SWIM = 5;
    private static CommandUtil Instance;

    public static CommandUtil getInstance() {
        if (Instance == null) {
            Instance = new CommandUtil();
        }
        return Instance;
    }

    public byte[] getCommandType(int i2, String str) throws NumberFormatException, ParseException {
        byte[] bArr;
        byte[] bArrUtendo = utendo(i2, str);
        if (bArrUtendo == null || bArrUtendo.length != 6) {
            bArr = new byte[2];
        } else {
            bArr = new byte[8];
            System.arraycopy(bArrUtendo, 0, bArr, 2, bArrUtendo.length);
        }
        switch (i2) {
            case 1:
                bArr[0] = -78;
                bArr[1] = -6;
                break;
            case 2:
                if (!DeviceMode.isHasFunction_4(262144)) {
                    bArr[0] = -77;
                    bArr[1] = -6;
                    break;
                } else {
                    break;
                }
            case 3:
                if (DeviceMode.isHasFunction_2(16384)) {
                    bArr[0] = -9;
                } else {
                    bArr[0] = -26;
                }
                if (!DeviceMode.isHasFunction_9(4096)) {
                    bArr[1] = -6;
                    break;
                } else {
                    bArr[1] = -22;
                    break;
                }
            case 4:
                bArr[0] = -56;
                bArr[1] = -6;
                break;
            case 5:
                bArr[0] = -73;
                bArr[1] = -6;
                break;
            case 6:
                bArr[0] = -71;
                bArr[1] = -6;
                break;
            case 7:
                bArr[0] = -13;
                bArr[1] = -6;
                break;
            case 8:
                bArr[0] = -12;
                bArr[1] = -6;
                break;
            case 9:
                bArr[0] = -3;
                bArr[1] = -6;
                break;
            case 10:
                bArr[0] = -23;
                bArr[1] = -6;
                break;
            case 11:
                bArr[0] = 59;
                if (!DeviceMode.isHasFunction_9(4096)) {
                    bArr[1] = -6;
                    break;
                } else {
                    bArr[1] = -22;
                    break;
                }
            case 12:
                bArr[0] = Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE;
                if (!DeviceMode.isHasFunction_9(4096)) {
                    bArr[1] = -6;
                    break;
                } else {
                    bArr[1] = -22;
                    break;
                }
            case 13:
                bArr[0] = 88;
                bArr[1] = -6;
                break;
            case 15:
                bArr[0] = -20;
                bArr[1] = 1;
                break;
        }
        return bArr;
    }

    public final String utendo(String str) throws ParseException {
        Date date;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CalendarUtils.yyyyMMddHHmm, Locale.US);
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e2) {
            e2.printStackTrace();
            date = null;
        }
        calendar.setTime(date);
        calendar.add(5, -1);
        return simpleDateFormat.format(calendar.getTime());
    }

    public final byte[] utendo(int i2, String str) throws NumberFormatException, ParseException {
        byte[] bArr = new byte[0];
        if (TextUtils.isEmpty(str) || str.length() != 12 || !DeviceMode.isHasFunction_4(8192)) {
            return bArr;
        }
        int i3 = Integer.parseInt(str.substring(0, 4));
        int i4 = Integer.parseInt(str.substring(4, 6));
        int i5 = Integer.parseInt(str.substring(6, 8));
        int i6 = Integer.parseInt(str.substring(8, 10));
        int i7 = Integer.parseInt(str.substring(10, 12));
        if (i2 != 1 && i2 != 9) {
            if (i2 == 2) {
                String strUtendo = utendo(str);
                if (TextUtils.isEmpty(strUtendo) || strUtendo.length() != 12) {
                    i3 = 0;
                    i4 = 0;
                    i5 = 0;
                    i7 = 0;
                } else {
                    i3 = Integer.parseInt(strUtendo.substring(0, 4));
                    i4 = Integer.parseInt(strUtendo.substring(4, 6));
                    i5 = Integer.parseInt(strUtendo.substring(6, 8));
                    i6 = 18;
                    i7 = 0;
                }
            }
            return new byte[]{(byte) ((65280 & i3) >> 8), (byte) (i3 & 255), (byte) (i4 & 255), (byte) (i5 & 255), (byte) (i6 & 255), (byte) (i7 & 255)};
        }
        i7 = 0;
        i6 = i7;
        return new byte[]{(byte) ((65280 & i3) >> 8), (byte) (i3 & 255), (byte) (i4 & 255), (byte) (i5 & 255), (byte) (i6 & 255), (byte) (i7 & 255)};
    }
}
