package com.yc.utesdk.data;

import androidx.core.view.MotionEventCompat;
import com.yc.utesdk.bean.BloodSugarSamplingInfo;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.command.WriteCommandToBLE;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.ByteDataUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class BloodSugarProcessing {
    private static volatile BloodSugarProcessing instance;
    private byte[] mBloodSugarRawData = new byte[0];
    private int mLastDataSegNumber = 0;
    private List<byte[]> mBloodSugarSamplingRawList = new ArrayList();
    private int appCrc = 0;

    public static BloodSugarProcessing getInstance() {
        if (instance == null) {
            synchronized (BloodSugarProcessing.class) {
                try {
                    if (instance == null) {
                        instance = new BloodSugarProcessing();
                    }
                } finally {
                }
            }
        }
        return instance;
    }

    public void dealWithBloodSugar(String str, byte[] bArr) {
        switch (bArr[1] & 255) {
            case 1:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onBloodSugarStatus(true, 1);
                break;
            case 2:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                int i2 = bArr[2] & 255;
                if (i2 == 1) {
                    LogUtils.i("查询舒糖代谢算法开关 开");
                    UteListenerManager.getInstance().onBloodSugarStatus(true, 3);
                    break;
                } else if (i2 == 0) {
                    LogUtils.i("查询舒糖代谢算法开关 关");
                    UteListenerManager.getInstance().onBloodSugarStatus(true, 4);
                    break;
                } else {
                    UteListenerManager.getInstance().onBloodSugarStatus(false, 2);
                    break;
                }
            case 3:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                int i3 = bArr[2] & 255;
                if (i3 != 250) {
                    if (i3 != 253) {
                        if (i3 != 255) {
                        }
                    } else if (bArr.length == 4) {
                        int i4 = bArr[3] & 255;
                        LogUtils.i("bleCrc =" + i4 + ",appCrc = " + (this.appCrc & 255));
                        if (i4 == (this.appCrc & 255)) {
                            LogUtils.i("获取采样数据,检验成功");
                            byte[] bArr2 = this.mBloodSugarRawData;
                            if (bArr2 != null && bArr2.length > 0) {
                                this.mBloodSugarSamplingRawList.add(bArr2);
                            }
                            UteListenerManager.getInstance().onBloodSugarSampling(true, utendo(this.mBloodSugarSamplingRawList));
                        } else {
                            LogUtils.i("获取采样数据,检验失败");
                            UteListenerManager.getInstance().onBloodSugarSampling(false, null);
                        }
                        this.mLastDataSegNumber = 0;
                        this.appCrc = 0;
                        this.mBloodSugarSamplingRawList = new ArrayList();
                        break;
                    } else if (bArr.length != 3) {
                    }
                    UteListenerManager.getInstance().onBloodSugarStatus(true, 7);
                    break;
                } else {
                    for (int i5 = 2; i5 < bArr.length; i5++) {
                        this.appCrc ^= bArr[i5];
                    }
                    CommandTimeOutUtils.getInstance().setCommandTimeOut(207);
                    int i6 = ((bArr[5] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[6] & 255);
                    if (i6 != this.mLastDataSegNumber) {
                        byte[] bArr3 = this.mBloodSugarRawData;
                        if (bArr3 != null && bArr3.length > 0) {
                            this.mBloodSugarSamplingRawList.add(bArr3);
                        }
                        int length = bArr.length - 7;
                        byte[] bArr4 = new byte[length];
                        System.arraycopy(bArr, 7, bArr4, 0, length);
                        this.mBloodSugarRawData = bArr4;
                    } else {
                        int length2 = bArr.length - 7;
                        byte[] bArr5 = new byte[length2];
                        System.arraycopy(bArr, 7, bArr5, 0, length2);
                        this.mBloodSugarRawData = ByteDataUtil.getInstance().copyTwoArrays(this.mBloodSugarRawData, bArr5);
                    }
                    this.mLastDataSegNumber = i6;
                    break;
                }
                break;
            case 4:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onBloodSugarStatus(true, 5);
                break;
            case 5:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                int i7 = bArr[2] & 255;
                if (i7 == 250) {
                    int i8 = bArr[4] & 255;
                    LogUtils.i("APP下发当天的血糖值 ble返回序号 =" + i8);
                    WriteCommandToBLE.getInstance().sendBloodSugarValueSegments(i8 + 1);
                    break;
                } else if (i7 == 253) {
                    if ((bArr[3] & 255) == 0) {
                        LogUtils.i("APP下发当天的血糖值 完成,校验成功 ");
                        UteListenerManager.getInstance().onBloodSugarStatus(true, 9);
                        break;
                    } else {
                        LogUtils.i("APP下发当天的血糖值 完成,校验失败 ");
                        UteListenerManager.getInstance().onBloodSugarStatus(false, 9);
                        break;
                    }
                }
                break;
            case 6:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                int i9 = bArr[2] & 255;
                if (i9 == 250) {
                    int i10 = bArr[4] & 255;
                    LogUtils.i("APP下发当天的血糖等级 ble返回序号 =" + i10);
                    WriteCommandToBLE.getInstance().sendBloodSugarLevelSegments(i10 + 1);
                    break;
                } else if (i9 == 253) {
                    if ((bArr[3] & 255) == 0) {
                        LogUtils.i("APP下发当天的血糖等级 完成,校验成功 ");
                        UteListenerManager.getInstance().onBloodSugarStatus(true, 8);
                        break;
                    } else {
                        LogUtils.i("APP下发当天的血糖等级 完成,校验失败 ");
                        UteListenerManager.getInstance().onBloodSugarStatus(false, 8);
                        break;
                    }
                }
                break;
            case 7:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onBloodSugarStatus(true, 6);
                break;
        }
    }

    public final List utendo(List list) {
        int i2 = 3;
        ArrayList arrayList = new ArrayList();
        if (list != null && !list.isEmpty()) {
            int i3 = 0;
            while (i3 < list.size()) {
                byte[] bArr = (byte[]) list.get(i3);
                int i4 = ((bArr[0] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[1] & 255);
                int i5 = bArr[2] & 255;
                int i6 = bArr[i2] & 255;
                int i7 = bArr[4] & 255;
                int i8 = bArr[5] & 255;
                Locale locale = Locale.US;
                Integer numValueOf = Integer.valueOf(i4);
                Integer numValueOf2 = Integer.valueOf(i5);
                Integer numValueOf3 = Integer.valueOf(i6);
                Object[] objArr = new Object[i2];
                objArr[0] = numValueOf;
                objArr[1] = numValueOf2;
                objArr[2] = numValueOf3;
                String str = String.format(locale, "%1$04d%2$02d%3$02d", objArr);
                String str2 = str + String.format(locale, "%1$02d%2$02d", Integer.valueOf(i7), Integer.valueOf(i8));
                int i9 = (i7 * 60) + i8;
                int i10 = ((bArr[6] & 255) << 24) | ((bArr[7] & 255) << 16) | ((bArr[8] & 255) << 8) | (bArr[9] & 255);
                int i11 = ((bArr[10] & 255) << 24) | ((bArr[11] & 255) << 16) | ((bArr[12] & 255) << 8) | (bArr[13] & 255);
                int i12 = bArr[14] & 255;
                int i13 = bArr[15] & 255;
                float f2 = ((bArr[16] << 8) | bArr[17]) / 100.0f;
                float f3 = ((bArr[18] << 8) | bArr[19]) / 100.0f;
                ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = new ArrayList();
                for (int i14 = 20; i14 < bArr.length; i14++) {
                    if (i14 < 80) {
                        arrayList2.add(Integer.valueOf(bArr[i14] & 255));
                    } else if (i14 < 140) {
                        arrayList3.add(Integer.valueOf(bArr[i14] & 255));
                    }
                }
                arrayList.add(new BloodSugarSamplingInfo(str, str2, i9, i10, i11, i12, i13, f2, f3, arrayList2, arrayList3));
                i3++;
                i2 = 3;
            }
        }
        return arrayList;
    }
}
