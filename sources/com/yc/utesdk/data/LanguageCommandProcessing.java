package com.yc.utesdk.data;

import com.yc.utesdk.bean.DeviceBt3StateInfo;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.utils.open.GBUtils;
import com.yc.utesdk.utils.open.SPUtil;

/* loaded from: classes4.dex */
public class LanguageCommandProcessing {
    private static LanguageCommandProcessing instance;

    public static LanguageCommandProcessing getInstance() {
        if (instance == null) {
            instance = new LanguageCommandProcessing();
        }
        return instance;
    }

    public void dealWithBt3Agreement(byte[] bArr) {
        int i2 = bArr[1] & 255;
        if (i2 != 1) {
            if (i2 != 2) {
                return;
            }
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            int i3 = bArr[2] & 255;
            if (i3 == 0) {
                UteListenerManager.getInstance().onDeviceBt3Switch(true, 0);
                return;
            } else {
                if (i3 == 1) {
                    UteListenerManager.getInstance().onDeviceBt3Switch(true, 1);
                    return;
                }
                return;
            }
        }
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        if (bArr.length < 31) {
            return;
        }
        byte[] bArr2 = new byte[20];
        byte[] bArr3 = new byte[6];
        System.arraycopy(bArr, 2, bArr2, 0, 20);
        System.arraycopy(bArr, 22, bArr3, 0, 6);
        int i4 = 0;
        int i5 = 0;
        while (i4 < 20 && (bArr2[i4] & 255) != 0) {
            i5 = i4 + 1;
            i4 = i5;
        }
        byte[] bArr4 = new byte[i5];
        System.arraycopy(bArr2, 0, bArr4, 0, i5);
        UteListenerManager.getInstance().onDeviceBt3State(true, new DeviceBt3StateInfo(GBUtils.getInstance().AsciiStringToString(GBUtils.getInstance().bytes2HexStringUpperCase(bArr4)), GBUtils.getInstance().bytes2HexStringUpperCaseMac(bArr3), bArr[28] & 255, bArr[29] & 255, bArr[30] & 255));
    }

    public void dealWithLanguage(byte[] bArr, String str, String str2) {
        if (!str.equals("AFAA")) {
            if (str.equals("AFAB")) {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onDeviceLanguageStatus(true, bArr[2] & 255);
                return;
            }
            return;
        }
        int length = str2.length();
        if (length == 40) {
            int i2 = length - 6;
            String strSubstring = str2.substring(i2, length);
            int i3 = length - 12;
            String strSubstring2 = str2.substring(i3, i2);
            int i4 = length - 18;
            String strSubstring3 = str2.substring(i4, i3);
            int i5 = length - 24;
            String strSubstring4 = str2.substring(i5, i4);
            String strSubstring5 = str2.substring(length - 30, i5);
            int iHexStringToAlgorism = GBUtils.getInstance().hexStringToAlgorism(strSubstring);
            int iHexStringToAlgorism2 = GBUtils.getInstance().hexStringToAlgorism(strSubstring2);
            int iHexStringToAlgorism3 = GBUtils.getInstance().hexStringToAlgorism(strSubstring3);
            int iHexStringToAlgorism4 = GBUtils.getInstance().hexStringToAlgorism(strSubstring4);
            int iHexStringToAlgorism5 = GBUtils.getInstance().hexStringToAlgorism(strSubstring5);
            SPUtil.getInstance().setBandLanguageDisplay1(iHexStringToAlgorism);
            SPUtil.getInstance().setBandLanguageDisplay2(iHexStringToAlgorism2);
            SPUtil.getInstance().setBandLanguageDisplay3(iHexStringToAlgorism3);
            SPUtil.getInstance().setBandLanguageDisplay4(iHexStringToAlgorism4);
            SPUtil.getInstance().setBandLanguageDisplay5(iHexStringToAlgorism5);
        }
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        UteListenerManager.getInstance().onQueryCurrentLanguage(true, bArr[2] & 255);
    }

    public void dealWithQuickSwitch(byte[] bArr) {
        int i2 = bArr[1] & 255;
        if (i2 == 1) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            byte[] bArr2 = new byte[bArr.length - 2];
            System.arraycopy(bArr, 2, bArr2, 0, bArr.length - 2);
            UteListenerManager.getInstance().onDeviceShortcutSwitch(true, bArr2);
            return;
        }
        if (i2 != 2) {
            return;
        }
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        byte[] bArr3 = new byte[bArr.length - 2];
        System.arraycopy(bArr, 2, bArr3, 0, bArr.length - 2);
        UteListenerManager.getInstance().onDeviceShortcutSwitchStatus(true, bArr3);
    }
}
