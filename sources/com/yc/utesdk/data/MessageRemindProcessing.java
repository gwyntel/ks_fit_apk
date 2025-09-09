package com.yc.utesdk.data;

import aisble.error.GattError;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import androidx.core.view.MotionEventCompat;
import com.yc.utesdk.bean.ReplySmsInfo;
import com.yc.utesdk.bean.SosCallInfo;
import com.yc.utesdk.ble.close.DeviceBusyLockUtils;
import com.yc.utesdk.ble.open.DeviceMode;
import com.yc.utesdk.ble.open.DevicePlatform;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.command.WriteCommandToBLE;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.GBUtils;
import com.yc.utesdk.utils.open.SPUtil;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class MessageRemindProcessing {
    private static MessageRemindProcessing instance;
    private Handler mHandler;
    private byte universalInterfaceCRC;
    private StringBuilder tempUniversalInterfaceData = new StringBuilder();
    private int mContactsCurrentCount = 0;
    private int mContactsTotalCount = 0;
    private int mContactsSegCount = 6;
    private int sendPhoneNumberType = 0;
    private List<byte[]> contentByteList = new ArrayList();
    private final int delayWriteTime50 = 20;
    private int mContentStatus = -1;
    private final int WRITE_PUSH_COMMAND_MSG = 0;
    private final int SMS_VIBRATION = 1;
    private final int CONTENT_SECTION = 2;
    private final int CONTENT_RESEND = 3;
    private final int SMS_NAME_FIRST_SECTION = 4;
    private final int SMS_NAME_SECTION = 5;
    private final int SEVEN_DAY_WEATHER_SECTION_DIALOG = 6;
    private final int UNIVERSAL_INTERFACE_SECTION_DIALOG = 7;
    private final int UNIVERSAL_INTERFACE_SECTION_DIALOG_NUMBER = 8;
    private final int UNIVERSAL_INTERFACE_SECTION_DIALOG_FD = 9;
    private final int UNIVERSAL_INTERFACE_SECTION_DIALOG_5 = 10;
    private final int UNIVERSAL_INTERFACE_SECTION_DIALOG_NUMBER_5 = 11;
    private final int UNIVERSAL_INTERFACE_SECTION_DIALOG_FD_5 = 12;
    private final int CONTACTS_SECTION_FIRST = 13;
    private final int CONTACTS_SECTION_SYNCING = 14;
    private final int SYNC_TIME_ZOME = 15;
    private final int SOS_CALL_SECTION_SYNCING = 16;
    private final int SYNC_CUSTOMIZE_SMS = 17;
    private final int SEND_PHONE_NUMBER_FINISH = 18;
    private byte[] sosTempData = new byte[0];
    private int sosContactCount = 0;
    private WriteCommandToBLE mWriteCommandToBLE = WriteCommandToBLE.getInstance();

    public class utendo extends Handler {
        public utendo(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) throws UnsupportedEncodingException {
            if (message.what != 0) {
                return;
            }
            if (!DeviceBusyLockUtils.getInstance().getDeviceBusy()) {
                MessageRemindProcessing.this.utendo(message.arg1, message.arg2);
                return;
            }
            LogUtils.i("设备忙，等20ms");
            Message message2 = new Message();
            message2.what = 0;
            message2.arg1 = message.arg1;
            message2.arg2 = message.arg2;
            MessageRemindProcessing.this.mHandler.sendMessageDelayed(message2, 20L);
        }
    }

    public MessageRemindProcessing() {
        utendo();
    }

    public static MessageRemindProcessing getInstance() {
        if (instance == null) {
            instance = new MessageRemindProcessing();
        }
        return instance;
    }

    public void dealWithCommonInterface(String str, byte[] bArr) {
        CommandTimeOutUtils commandTimeOutUtils;
        int i2;
        if (bArr == null || bArr.length < 2) {
            return;
        }
        int i3 = bArr[1] & 255;
        if (i3 == 253) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            if (this.tempUniversalInterfaceData.length() > 0) {
                LogUtils.e("universalInterfaceCRC = " + ((int) this.universalInterfaceCRC) + "," + ((int) bArr[2]));
                if (this.universalInterfaceCRC == bArr[2]) {
                    UteListenerManager.getInstance().onCommonInterfaceBleToSdk(3, GBUtils.getInstance().hexStringToBytes(this.tempUniversalInterfaceData.toString()));
                } else {
                    UteListenerManager.getInstance().onCommonInterfaceBleToSdk(4, null);
                }
            } else if (this.mWriteCommandToBLE.getUniversalInterfaceCRCResult(bArr[2])) {
                UteListenerManager.getInstance().onCommonInterfaceSdkToBle(1);
            } else {
                UteListenerManager.getInstance().onCommonInterfaceSdkToBle(2);
            }
            this.tempUniversalInterfaceData = new StringBuilder();
            return;
        }
        if (i3 == 0) {
            this.tempUniversalInterfaceData = new StringBuilder();
            this.universalInterfaceCRC = (byte) 0;
        }
        if (bArr.length == 2) {
            this.mWriteCommandToBLE.NOsectionUniversalInterface = (bArr[1] & 255) + 1;
            this.mContentStatus = 7;
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 82;
        } else {
            if (str.length() >= 4) {
                this.tempUniversalInterfaceData.append(str.substring(4));
            }
            for (int i4 = 1; i4 < bArr.length; i4++) {
                this.universalInterfaceCRC = (byte) (this.universalInterfaceCRC ^ bArr[i4]);
            }
            this.mWriteCommandToBLE.NOsectionUniversalInterface = bArr[1] & 255;
            this.mContentStatus = 8;
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 83;
        }
        commandTimeOutUtils.setCommandTimeOut(i2);
        utenif();
    }

    public void dealWithCommonInterface_5(String str, byte[] bArr) {
        CommandTimeOutUtils commandTimeOutUtils;
        int i2;
        if (bArr == null || bArr.length < 3) {
            return;
        }
        int i3 = ((bArr[1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[2] & 255);
        if (i3 == 65533) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            if (this.tempUniversalInterfaceData.length() > 0) {
                LogUtils.e("universalInterfaceCRC = " + ((int) this.universalInterfaceCRC) + "," + ((int) bArr[3]));
                if (this.universalInterfaceCRC == bArr[3]) {
                    UteListenerManager.getInstance().onCommonInterfaceBleToSdk(3, GBUtils.getInstance().hexStringToBytes(this.tempUniversalInterfaceData.toString()));
                } else {
                    UteListenerManager.getInstance().onCommonInterfaceBleToSdk(4, null);
                }
            } else if (this.mWriteCommandToBLE.getUniversalInterfaceCRCResult(bArr[3])) {
                UteListenerManager.getInstance().onCommonInterfaceSdkToBle(1);
            } else {
                UteListenerManager.getInstance().onCommonInterfaceSdkToBle(2);
            }
            this.tempUniversalInterfaceData = new StringBuilder();
            return;
        }
        if (i3 == 0) {
            this.tempUniversalInterfaceData = new StringBuilder();
            this.universalInterfaceCRC = (byte) 0;
        }
        if (bArr.length == 3) {
            this.mWriteCommandToBLE.NOsectionUniversalInterface = (((bArr[1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[2] & 255)) + 1;
            this.mContentStatus = 10;
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 82;
        } else {
            if (str.length() >= 6) {
                this.tempUniversalInterfaceData.append(str.substring(6));
            }
            for (int i4 = 1; i4 < bArr.length; i4++) {
                this.universalInterfaceCRC = (byte) (this.universalInterfaceCRC ^ bArr[i4]);
            }
            this.mWriteCommandToBLE.NOsectionUniversalInterface = ((bArr[1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[2] & 255);
            this.mContentStatus = 11;
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 83;
        }
        commandTimeOutUtils.setCommandTimeOut(i2);
        utenif();
    }

    public void dealWithContacts(byte[] bArr) {
        int i2 = bArr[1] & 255;
        if (i2 == 170) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onQuerySosContactsCount(true, bArr[2] & 255);
        }
        if (i2 == 186) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onContactsSyncStatus(4);
            return;
        }
        if (i2 == 189) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            int i3 = bArr[2] & 255;
            if (i3 != 253) {
                if (i3 == 0) {
                    this.sosContactCount = bArr[3] & 255;
                    int length = bArr.length - 4;
                    byte[] bArr2 = new byte[length];
                    this.sosTempData = bArr2;
                    System.arraycopy(bArr, 4, bArr2, 0, length);
                    return;
                }
                byte[] bArr3 = this.sosTempData;
                byte[] bArr4 = new byte[bArr3.length + (bArr.length - 3)];
                this.sosTempData = bArr4;
                System.arraycopy(bArr3, 0, bArr4, 0, bArr3.length);
                System.arraycopy(bArr, 3, this.sosTempData, bArr3.length, bArr.length - 3);
                return;
            }
            if (this.sosContactCount > 0) {
                ArrayList arrayList = new ArrayList();
                int i4 = 0;
                for (int i5 = 0; i5 < this.sosContactCount; i5++) {
                    byte[] bArr5 = this.sosTempData;
                    int i6 = bArr5[i4] & 255;
                    int i7 = bArr5[i4 + 1] & 255;
                    byte[] bArr6 = new byte[i6];
                    int i8 = i4 + 2;
                    System.arraycopy(bArr5, i8, bArr6, 0, i6);
                    String strUtf8ByteToString = GBUtils.getInstance().utf8ByteToString(bArr6);
                    byte[] bArr7 = new byte[i7];
                    System.arraycopy(this.sosTempData, i8 + i6, bArr7, 0, i7);
                    String strUtf8ByteToString2 = GBUtils.getInstance().utf8ByteToString(bArr7);
                    SosCallInfo sosCallInfo = new SosCallInfo();
                    sosCallInfo.setPhone(strUtf8ByteToString);
                    sosCallInfo.setName(strUtf8ByteToString2);
                    arrayList.add(sosCallInfo);
                    i4 += i6 + 2 + i7;
                }
                UteListenerManager.getInstance().onQuerySosContacts(true, arrayList);
                return;
            }
            return;
        }
        switch (i2) {
            case 172:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                CommandTimeOutUtils.getInstance().setCommandTimeOut(110);
                this.mContentStatus = 16;
                utenif();
                UteListenerManager.getInstance().onSosContactsSync(1);
                break;
            case 173:
            case 174:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onSosContactsSync(2);
                break;
            case 175:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                int i9 = bArr[2] & 255;
                if (i9 == 1) {
                    UteListenerManager.getInstance().onSosContactsStatus(true, 4);
                    break;
                } else if (i9 == 0) {
                    UteListenerManager.getInstance().onSosContactsStatus(true, 5);
                    break;
                }
                break;
            default:
                switch (i2) {
                    case 250:
                        int i10 = bArr[2] & 255;
                        this.mContactsTotalCount = i10;
                        if (i10 > 0) {
                            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                            CommandTimeOutUtils.getInstance().setCommandTimeOut(84);
                            this.mContentStatus = 13;
                            utenif();
                            this.mContactsCurrentCount = 0;
                            this.mContactsSegCount = (SPUtil.getInstance().getMaxCommunicationLength() - 3) / (DeviceMode.isHasFunction_6(524288) ? 59 : 39);
                            UteListenerManager.getInstance().onContactsSyncStatus(0);
                            break;
                        } else {
                            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                            UteListenerManager.getInstance().onContactsSyncStatus(3);
                            break;
                        }
                    case 251:
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        CommandTimeOutUtils.getInstance().setCommandTimeOut(84);
                        this.mContentStatus = 14;
                        utenif();
                        int i11 = this.mContactsCurrentCount + 1;
                        this.mContactsCurrentCount = i11;
                        int i12 = this.mContactsTotalCount;
                        int i13 = this.mContactsSegCount;
                        int i14 = ((i12 + i13) - 1) / i13;
                        UteListenerManager.getInstance().onContactsSyncProgress(i14 >= 1 ? (i11 * 100) / i14 : 100);
                        break;
                    case 252:
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        UteListenerManager.getInstance().onContactsSyncStatus(1);
                        this.mContactsCurrentCount = 0;
                        break;
                }
        }
    }

    public void dealWithCustomizeSMS(byte[] bArr) {
        switch (bArr[1] & 255) {
            case 250:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                CommandTimeOutUtils.getInstance().setCommandTimeOut(113);
                this.mContentStatus = 17;
                utenif();
                UteListenerManager.getInstance().onQuickReplySmsStatus(true, 3);
                break;
            case 251:
            case GattError.GATT_CCCD_CFG_ERROR /* 253 */:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onQuickReplySmsStatus(true, 4);
                break;
            case 252:
                if ((bArr[3] & 255) == 0) {
                    this.contentByteList = new ArrayList();
                }
                this.contentByteList.add(bArr);
                break;
            case 254:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                ArrayList arrayList = new ArrayList();
                int i2 = 0;
                int i3 = 0;
                while (i2 < this.contentByteList.size()) {
                    byte[] bArr2 = this.contentByteList.get(i2);
                    int i4 = bArr2[2] & 255;
                    int i5 = bArr2[4] & 255;
                    byte[] bArr3 = new byte[i5];
                    System.arraycopy(bArr2, 5, bArr3, 0, i5);
                    arrayList.add(new ReplySmsInfo(GBUtils.getInstance().utf8ByteToString(bArr3)));
                    i2++;
                    i3 = i4;
                }
                UteListenerManager.getInstance().onQuerySmsContent(true, i3, arrayList);
                break;
        }
    }

    public void dealWithEndCallReplySms(String str, byte[] bArr) {
        UteListenerManager uteListenerManager;
        int i2;
        if (bArr == null) {
            return;
        }
        if (bArr.length == 2) {
            int i3 = bArr[1] & 255;
            if (i3 == 254) {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 5;
            } else {
                if (i3 != 255) {
                    return;
                }
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 6;
            }
            uteListenerManager.onQuickReplySmsStatus(true, i2);
            return;
        }
        if (bArr.length == 3) {
            if ((bArr[2] & 255) == 253) {
                int i4 = bArr[1] & 255;
                if (i4 == 0) {
                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                    UteListenerManager.getInstance().onQuickReplySmsStatus(true, 2);
                    return;
                } else {
                    if (i4 == 1) {
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        UteListenerManager.getInstance().onQuickReplySmsStatus(true, 1);
                        return;
                    }
                    return;
                }
            }
            return;
        }
        if (bArr.length > 3) {
            int i5 = bArr[1] & 255;
            int i6 = bArr[i5 + 2] & 255;
            String strSubstring = str.substring(4, (i5 * 2) + 4);
            LogUtils.d("dealWithEndCallReplySms phoneNumber =" + strSubstring + ",numberLength =" + i5 + ",contentLength =" + i6);
            String strAsciiStringToString = GBUtils.getInstance().AsciiStringToString(strSubstring);
            byte[] bArr2 = new byte[i6];
            System.arraycopy(bArr, i5 + 3, bArr2, 0, i6);
            UteListenerManager.getInstance().onQuickReplySmsContent(strAsciiStringToString, GBUtils.getInstance().utf8ByteToString(bArr2));
        }
    }

    public void dealWithMessageRemind(String str, String str2, byte[] bArr) {
        int i2;
        if (str.equals("C5")) {
            if (str2.length() < 4) {
                return;
            }
            String strSubstring = str2.substring(2, 4);
            if (strSubstring.equals("FD")) {
                i2 = bArr[2] & 255;
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                if (i2 == 3 && ((DeviceMode.isHasFunction_7(2048) || DeviceMode.isHasFunction_7(8192) || DevicePlatform.getInstance().isJXPlatform()) && !TextUtils.isEmpty(SPUtil.getInstance().getSmsReceivedNameOrNumber()))) {
                    CommandTimeOutUtils.getInstance().setCommandTimeOut(98);
                    this.mContentStatus = 4;
                }
                this.mContentStatus = 1;
                utendo(i2);
                return;
            }
            if (strSubstring.equals("FF")) {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                CommandTimeOutUtils.getInstance().setCommandTimeOut(98);
                this.mContentStatus = 3;
            } else {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                CommandTimeOutUtils.getInstance().setCommandTimeOut(98);
                this.mContentStatus = 2;
            }
        } else {
            if (!str.equals("C6") || str2.length() < 4) {
                return;
            }
            String strSubstring2 = str2.substring(2, 4);
            if (strSubstring2.equals("FD")) {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                i2 = bArr[2] & 255;
                this.mContentStatus = 1;
                utendo(i2);
                return;
            }
            if (strSubstring2.equals("FF")) {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                CommandTimeOutUtils.getInstance().setCommandTimeOut(98);
                if ((bArr[2] & 255) != 3) {
                    return;
                }
                this.mContentStatus = 4;
            } else {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                CommandTimeOutUtils.getInstance().setCommandTimeOut(98);
                this.mContentStatus = 5;
            }
        }
        utenif();
    }

    public void dealWithPhoneNumber(byte[] bArr) {
        if ((bArr[1] & 255) != 250) {
            return;
        }
        this.mContentStatus = 18;
        utenif();
    }

    public void dealWithPushMessageDisplay(String str) {
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        if (str != null) {
            int length = str.length();
            if (length == 40) {
                int i2 = length - 6;
                String strSubstring = str.substring(i2, length);
                int i3 = length - 12;
                String strSubstring2 = str.substring(i3, i2);
                String strSubstring3 = str.substring(length - 18, i3);
                int iHexStringToAlgorism = GBUtils.getInstance().hexStringToAlgorism(strSubstring);
                int iHexStringToAlgorism2 = GBUtils.getInstance().hexStringToAlgorism(strSubstring2);
                int iHexStringToAlgorism3 = GBUtils.getInstance().hexStringToAlgorism(strSubstring3);
                SPUtil.getInstance().setPushMessageDisplay1(iHexStringToAlgorism);
                SPUtil.getInstance().setPushMessageDisplay2(iHexStringToAlgorism2);
                SPUtil.getInstance().setPushMessageDisplay3(iHexStringToAlgorism3);
            }
            UteListenerManager.getInstance().onQueryRemindDisplay(true);
        }
    }

    public void dealWithTimeZone(byte[] bArr) {
        UteListenerManager uteListenerManager;
        boolean z2 = true;
        int i2 = bArr[1] & 255;
        if (i2 == 250) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            CommandTimeOutUtils.getInstance().setCommandTimeOut(74);
            this.mContentStatus = 15;
            utenif();
            return;
        }
        if (i2 == 251) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onSimpleCallback(true, 27);
            return;
        }
        if (i2 == 253) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            uteListenerManager = UteListenerManager.getInstance();
        } else {
            if (i2 != 255) {
                return;
            }
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            uteListenerManager = UteListenerManager.getInstance();
            z2 = false;
        }
        uteListenerManager.onSimpleCallback(z2, 26);
    }

    public void dealWithWeather(String str, byte[] bArr) {
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        if (str.equals("CB")) {
            int i2 = bArr[1] & 255;
            if (i2 == 255) {
                UteListenerManager.getInstance().onSimpleCallback(true, 25);
                return;
            }
            if (!DeviceMode.isHasFunction_8(256)) {
                if (i2 >= 3 || i2 <= 0) {
                    if (i2 != 3) {
                        return;
                    }
                }
                CommandTimeOutUtils.getInstance().setCommandTimeOut(72);
                this.mWriteCommandToBLE.NOsectionForWeather = i2;
                this.mContentStatus = 6;
                utenif();
                return;
            }
            if (i2 != 253) {
                if (i2 == 254) {
                    UteListenerManager.getInstance().onSimpleCallback(false, 24);
                    return;
                }
                CommandTimeOutUtils.getInstance().setCommandTimeOut(72);
                this.mWriteCommandToBLE.NOsectionForWeather = i2;
                this.mContentStatus = 6;
                utenif();
                return;
            }
        } else if (!str.equals("CA")) {
            return;
        }
        UteListenerManager.getInstance().onSimpleCallback(true, 24);
    }

    public final void utenif() {
        Message message = new Message();
        message.what = 0;
        message.arg1 = this.mContentStatus;
        this.mHandler.sendMessage(message);
    }

    public final void utendo() {
        if (this.mHandler == null) {
            HandlerThread handlerThread = new HandlerThread("HandlerThread");
            handlerThread.start();
            this.mHandler = new utendo(handlerThread.getLooper());
        }
    }

    public final void utendo(int i2, int i3) throws UnsupportedEncodingException {
        switch (i2) {
            case 1:
                if (i3 == 0 || i3 == 3) {
                    String callSmsPhoneNumber = SPUtil.getInstance().getCallSmsPhoneNumber();
                    if (DeviceMode.isHasFunction_6(512) && !TextUtils.isEmpty(callSmsPhoneNumber)) {
                        LogUtils.i("发送电话号码");
                        this.sendPhoneNumberType = i3;
                        this.mWriteCommandToBLE.sendPhoneNumber(callSmsPhoneNumber);
                        SPUtil.getInstance().setCallSmsPhoneNumber("");
                        return;
                    }
                    if (i3 == 0) {
                        UteListenerManager.getInstance().onCallRemindStatus(true, i3);
                        return;
                    }
                }
                UteListenerManager.getInstance().onSmsAppRemindStatus(true, i3);
                return;
            case 2:
                WriteCommandToBLE writeCommandToBLE = this.mWriteCommandToBLE;
                int i4 = writeCommandToBLE.NOsection + 1;
                writeCommandToBLE.NOsection = i4;
                writeCommandToBLE.sendTextSectionKey(i4);
                return;
            case 3:
                WriteCommandToBLE writeCommandToBLE2 = this.mWriteCommandToBLE;
                writeCommandToBLE2.NOsection = 0;
                writeCommandToBLE2.isSendFD = false;
                writeCommandToBLE2.sendTextSectionKey(0);
                return;
            case 4:
                this.mWriteCommandToBLE.sendTextToBle6(SPUtil.getInstance().getSmsReceivedNameOrNumber(), 3);
                return;
            case 5:
                WriteCommandToBLE writeCommandToBLE3 = this.mWriteCommandToBLE;
                int i5 = writeCommandToBLE3.NOsection + 1;
                writeCommandToBLE3.NOsection = i5;
                writeCommandToBLE3.sendTextSectionKey6(i5);
                return;
            case 6:
                if (DeviceMode.isHasFunction_8(256)) {
                    WriteCommandToBLE writeCommandToBLE4 = this.mWriteCommandToBLE;
                    writeCommandToBLE4.setDeviceWeatherSegments(writeCommandToBLE4.NOsectionForWeather);
                    return;
                } else {
                    WriteCommandToBLE writeCommandToBLE5 = this.mWriteCommandToBLE;
                    writeCommandToBLE5.syncSevenDayWeatherSection(writeCommandToBLE5.NOsectionForWeather);
                    return;
                }
            case 7:
                WriteCommandToBLE writeCommandToBLE6 = this.mWriteCommandToBLE;
                writeCommandToBLE6.universalInterfaceOnSegments(writeCommandToBLE6.NOsectionUniversalInterface);
                return;
            case 8:
                WriteCommandToBLE writeCommandToBLE7 = this.mWriteCommandToBLE;
                writeCommandToBLE7.sendUniversalSerialNumber(writeCommandToBLE7.NOsectionUniversalInterface);
                return;
            case 9:
                this.mWriteCommandToBLE.sendUniversalFD(this.universalInterfaceCRC);
                break;
            case 10:
                WriteCommandToBLE writeCommandToBLE8 = this.mWriteCommandToBLE;
                writeCommandToBLE8.universalInterfaceOnSegments_5(writeCommandToBLE8.NOsectionUniversalInterface);
                return;
            case 11:
                WriteCommandToBLE writeCommandToBLE9 = this.mWriteCommandToBLE;
                writeCommandToBLE9.sendUniversalSerialNumber_5(writeCommandToBLE9.NOsectionUniversalInterface);
                return;
            case 12:
                this.mWriteCommandToBLE.sendUniversalFD_5(this.universalInterfaceCRC);
                break;
            case 13:
                this.mWriteCommandToBLE.sendContactsData();
                return;
            case 14:
                this.mWriteCommandToBLE.sendSectionContacts();
                return;
            case 15:
                this.mWriteCommandToBLE.sendTimeZoneSection();
                return;
            case 16:
                this.mWriteCommandToBLE.sendSectionSosCall();
                return;
            case 17:
                this.mWriteCommandToBLE.syncCustomizeSMSSection();
                return;
            case 18:
                int i6 = this.sendPhoneNumberType;
                if (i6 == 0) {
                    UteListenerManager.getInstance().onCallRemindStatus(true, 0);
                    return;
                } else {
                    if (i6 == 3) {
                        UteListenerManager.getInstance().onSmsAppRemindStatus(true, 3);
                        return;
                    }
                    return;
                }
            default:
                return;
        }
        this.universalInterfaceCRC = (byte) 0;
    }

    public final void utendo(int i2) {
        Message message = new Message();
        message.what = 0;
        message.arg1 = this.mContentStatus;
        message.arg2 = i2;
        this.mHandler.sendMessage(message);
    }
}
