package com.yc.utesdk.data;

import com.yc.utesdk.bean.Device4GModuleInfo;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.utils.open.GBUtils;

/* loaded from: classes4.dex */
public class Device4GModuleProcessing {
    private static volatile Device4GModuleProcessing instance;

    public static Device4GModuleProcessing getInstance() {
        if (instance == null) {
            synchronized (Device4GModuleProcessing.class) {
                try {
                    if (instance == null) {
                        instance = new Device4GModuleProcessing();
                    }
                } finally {
                }
            }
        }
        return instance;
    }

    public void dealWithDevice4GModule(String str, byte[] bArr) {
        int i2 = bArr[1] & 255;
        if (i2 != 0) {
            if (i2 != 1) {
                return;
            }
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onDevice4GModuleStatus(true, 1);
            return;
        }
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        byte[] bArr2 = new byte[15];
        System.arraycopy(bArr, 2, bArr2, 0, 15);
        UteListenerManager.getInstance().onDevice4GModuleIMEI(true, new Device4GModuleInfo(GBUtils.getInstance().asciiByteToString(bArr2)));
    }
}
