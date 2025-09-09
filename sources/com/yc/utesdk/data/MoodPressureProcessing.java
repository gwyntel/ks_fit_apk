package com.yc.utesdk.data;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.google.common.base.Ascii;
import com.google.gson.Gson;
import com.yc.utesdk.bean.ActivationResult;
import com.yc.utesdk.bean.BaseResult;
import com.yc.utesdk.bean.ElHRVMiddleDataInfo;
import com.yc.utesdk.bean.ElbpBleMiddleInfo;
import com.yc.utesdk.bean.ElbpBlePpgInfo;
import com.yc.utesdk.bean.ElbpContinuousPpgDataInfo;
import com.yc.utesdk.bean.ElbpMiddleDataInfo;
import com.yc.utesdk.bean.ElbpPpgDataInfo;
import com.yc.utesdk.bean.ElbsPpgDataInfo;
import com.yc.utesdk.bean.MoodPressureFatigueInfo;
import com.yc.utesdk.bean.MoodSensorInterfaceInfo;
import com.yc.utesdk.ble.close.DeviceBusyLockUtils;
import com.yc.utesdk.ble.open.DeviceMode;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.command.WriteCommandToBLE;
import com.yc.utesdk.listener.NetBaseListener;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.close.MoodHttpPostUtils;
import com.yc.utesdk.utils.close.MoodServerListener;
import com.yc.utesdk.utils.open.ByteDataUtil;
import com.yc.utesdk.utils.open.CalendarUtils;
import com.yc.utesdk.utils.open.GBUtils;
import com.yc.utesdk.utils.open.SPUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import l0.b;

/* loaded from: classes4.dex */
public class MoodPressureProcessing {
    private static MoodPressureProcessing instance;
    private String appStartDate;
    private String bleMiddleStartDate;
    private String blePpgStartDate;
    private Handler mHandler;
    private int moodCRC;
    private int moodCRCCount = 0;
    private List<MoodPressureFatigueInfo> moodInfoList = new ArrayList();
    private String moodTestMedian = "";
    private String startDateMood = "";
    private String endDateMood = "";
    private String startDateCalendar = "";
    private int startDateMunite = 0;
    private int moodTestMedianCount = 0;
    private int appPpgCrc = 0;
    private int appMiddleCrc = 0;

    /* renamed from: a, reason: collision with root package name */
    List f24893a = new ArrayList();

    /* renamed from: b, reason: collision with root package name */
    int f24894b = -1;
    private byte[] blePpgDataByte = new byte[0];

    /* renamed from: c, reason: collision with root package name */
    List f24895c = new ArrayList();

    /* renamed from: d, reason: collision with root package name */
    int f24896d = -1;
    private byte[] bleMiddleDataByte = new byte[0];

    /* renamed from: e, reason: collision with root package name */
    List f24897e = new ArrayList();
    private final int delayWriteTime50 = 20;
    private final int WRITE_PUSH_COMMAND_MSG = 0;
    private final int ELBP_PPG_DATA_CRC_OK = 1;
    private final int ELBP_PPG_DATA_CRC_FAIL = 2;
    private final int ELBP_MIDDLE_DATA_CRC_OK = 3;
    private final int ELBP_MIDDLE_DATA_CRC_FAIL = 4;

    /* renamed from: f, reason: collision with root package name */
    double f24898f = Math.pow(2.0d, 24.0d);

    public class utendo implements MoodServerListener {
        public utendo() {
        }

        @Override // com.yc.utesdk.utils.close.MoodServerListener
        public void onMoodServerStatus(MoodPressureFatigueInfo moodPressureFatigueInfo) {
            LogUtils.i("已获取到情绪压力值 info =" + new Gson().toJson(moodPressureFatigueInfo));
            SPUtil.getInstance().setMoodTestResultStatusSp(moodPressureFatigueInfo.getTestResultStatus());
            WriteCommandToBLE.getInstance().stopTestMoodPressureFatigue(moodPressureFatigueInfo);
        }
    }

    public class utenfor implements NetBaseListener {
        public utenfor() {
        }

        @Override // com.yc.utesdk.listener.NetBaseListener
        public void failed(BaseResult baseResult) {
            b.a(this, baseResult);
            UteListenerManager.getInstance().onElbpStatus(true, 18);
        }

        @Override // com.yc.utesdk.listener.NetBaseListener
        public void success(ActivationResult activationResult) {
            if (!activationResult.isStatus() || activationResult.getData() == null) {
                UteListenerManager.getInstance().onElbpStatus(true, 18);
                return;
            }
            String activation_code = activationResult.getData().getActivation_code();
            SPUtil.getInstance().setMoodActivationCodeSp(GBUtils.StringToAsciiString(activation_code));
            LogUtils.i("恒爱接口 2.2.1 activation_code = " + activation_code);
            WriteCommandToBLE.getInstance().syncElbpActivationCode(GBUtils.getInstance().hexStringToBytes(SPUtil.getInstance().getMoodActivationCodeSp()));
        }
    }

    public class utenif implements MoodServerListener {
        public utenif() {
        }

        @Override // com.yc.utesdk.utils.close.MoodServerListener
        public void onMoodServerStatus(MoodPressureFatigueInfo moodPressureFatigueInfo) {
            int testResultStatus = moodPressureFatigueInfo.getTestResultStatus();
            LogUtils.i("已获取到申请码 activeStatus =" + testResultStatus);
            if (testResultStatus == 0) {
                CommandTimeOutUtils.getInstance().setCommandTimeOut(107);
                WriteCommandToBLE.getInstance().syncMoodActivationCode(GBUtils.getInstance().hexStringToBytes(SPUtil.getInstance().getMoodActivationCodeSp()));
            }
        }
    }

    public class utenint extends Handler {
        public utenint(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 0) {
                return;
            }
            if (!DeviceBusyLockUtils.getInstance().getDeviceBusy()) {
                MoodPressureProcessing.this.utendo(message.arg1);
                return;
            }
            LogUtils.i("设备忙，等20ms");
            Message message2 = new Message();
            message2.what = 0;
            message2.arg1 = message.arg1;
            MoodPressureProcessing.this.mHandler.sendMessageDelayed(message2, 20L);
        }
    }

    public MoodPressureProcessing() {
        utendo();
    }

    public static MoodPressureProcessing getInstance() {
        if (instance == null) {
            instance = new MoodPressureProcessing();
        }
        return instance;
    }

    public void dealwithElComplex(String str, byte[] bArr) {
        UteListenerManager uteListenerManager;
        int i2;
        int i3 = bArr[1] & 255;
        if (i3 == 0) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onElComplexStatus(true, 2);
            return;
        }
        if (i3 == 1) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onElComplexStatus(true, 1);
            return;
        }
        if (i3 != 170) {
            return;
        }
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        int i4 = bArr[2] & 255;
        if (i4 == 1) {
            uteListenerManager = UteListenerManager.getInstance();
            i2 = 4;
        } else {
            if (i4 != 0) {
                return;
            }
            uteListenerManager = UteListenerManager.getInstance();
            i2 = 5;
        }
        uteListenerManager.onElComplexStatus(true, i2);
    }

    public void dealwithElHRV(String str, byte[] bArr) {
        int i2 = bArr[1] & 255;
        if (i2 == 0) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            int i3 = bArr[2] & 255;
            if (i3 == 17) {
                UteListenerManager.getInstance().onElHRVStatus(true, 2);
                return;
            } else {
                if (i3 == 255) {
                    UteListenerManager.getInstance().onElHRVStatus(true, 3);
                    return;
                }
                return;
            }
        }
        if (i2 != 1) {
            if (i2 != 170) {
                return;
            }
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            int i4 = bArr[2] & 255;
            if (i4 == 1) {
                UteListenerManager.getInstance().onElHRVStatus(true, 4);
                return;
            } else {
                if (i4 == 0) {
                    UteListenerManager.getInstance().onElHRVStatus(true, 5);
                    return;
                }
                return;
            }
        }
        if (bArr.length == 2) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onElHRVStatus(true, 1);
            return;
        }
        if (bArr.length > 5) {
            int i5 = ((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[3] & 255);
            int i6 = bArr[4] & 255;
            LogUtils.i("ElHRV 实时中间值数据 serialNum =" + i5 + ",curDataLength =" + i6);
            int length = bArr.length - 5;
            byte[] bArr2 = new byte[length];
            System.arraycopy(bArr, 5, bArr2, 0, length);
            UteListenerManager.getInstance().onElHRVPpgRealTime(new ElHRVMiddleDataInfo(i5, i6, utendo(bArr2), bArr2));
        }
    }

    /* JADX WARN: Type inference failed for: r4v43 */
    /* JADX WARN: Type inference failed for: r4v44 */
    public void dealwithElbp(String str, byte[] bArr) {
        UteListenerManager uteListenerManager;
        int i2;
        UteListenerManager uteListenerManager2;
        int i3;
        int i4;
        String str2;
        String str3;
        String str4;
        boolean z2 = true;
        z2 = true;
        z2 = true;
        z2 = true;
        z2 = true;
        z2 = true;
        z2 = true;
        z2 = true;
        z2 = true;
        z2 = true;
        z2 = true;
        z2 = true;
        z2 = true;
        int i5 = bArr[1] & 255;
        int i6 = 2;
        if (i5 != 17) {
            if (i5 == 170) {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                int i7 = bArr[2] & 255;
                if (i7 == 17) {
                    LogUtils.i("Elbp 当前正在采样");
                    uteListenerManager = UteListenerManager.getInstance();
                    i2 = 1;
                } else {
                    if (i7 != 0) {
                        return;
                    }
                    LogUtils.i("Elbp 当前没有在采样");
                    uteListenerManager = UteListenerManager.getInstance();
                    i2 = 2;
                }
            } else {
                if (i5 != 238) {
                    switch (i5 == true ? 1 : 0) {
                        case 0:
                            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                            int i8 = bArr[2] & 255;
                            LogUtils.i("Elbp 停止测试 stopType =" + i8);
                            if (i8 == 0) {
                                UteListenerManager.getInstance().onElbpMiddleRealTime(this.f24893a);
                                UteListenerManager.getInstance().onElbpStatus(true, 6);
                            } else {
                                if (i8 == 1) {
                                    uteListenerManager2 = UteListenerManager.getInstance();
                                    i3 = 8;
                                } else if (i8 == 2) {
                                    uteListenerManager2 = UteListenerManager.getInstance();
                                    i3 = 9;
                                } else if (i8 == 3) {
                                    UteListenerManager.getInstance().onElbpStatus(true, 10);
                                } else if (i8 == 253) {
                                    uteListenerManager2 = UteListenerManager.getInstance();
                                    i3 = 7;
                                }
                                uteListenerManager2.onElbpStatus(true, i3);
                            }
                            this.f24893a = new ArrayList();
                            break;
                        case 1:
                            int i9 = ((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[3] & 255);
                            int i10 = bArr[4] & 255;
                            LogUtils.i("Elbp 实时PPG数据 serialNum =" + i9 + ",curDataLength =" + i10);
                            int length = bArr.length - 5;
                            byte[] bArr2 = new byte[length];
                            System.arraycopy(bArr, 5, bArr2, 0, length);
                            UteListenerManager.getInstance().onElbpPpgRealTime(new ElbpPpgDataInfo(i9, i10, utenif(bArr2), bArr2));
                            break;
                        case 2:
                            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                            if (bArr.length != 4 || (bArr[2] & 255) != 253) {
                                if (bArr.length != 3) {
                                    if (bArr.length > 4) {
                                        CommandTimeOutUtils.getInstance().setCommandTimeOut(138);
                                        UteListenerManager.getInstance().onElbpPpgDataSyncing();
                                        int i11 = bArr[2] & 255;
                                        int i12 = bArr[3] & 255;
                                        LogUtils.i("Elbp ble储存的PPG数据 serialNum =" + i12 + ",totalCount =" + i11);
                                        while (i6 < bArr.length) {
                                            this.appPpgCrc ^= bArr[i6];
                                            i6++;
                                        }
                                        int i13 = this.f24894b;
                                        if (i13 != -1) {
                                            if (i13 == i12) {
                                                int length2 = bArr.length - 4;
                                                byte[] bArr3 = new byte[length2];
                                                System.arraycopy(bArr, 4, bArr3, 0, length2);
                                                this.blePpgDataByte = ByteDataUtil.getInstance().copyTwoArrays(this.blePpgDataByte, bArr3);
                                                break;
                                            } else {
                                                this.f24894b = i12;
                                                LogUtils.i("Elbp PPG数据 下一个数据 blePpgDataByte = " + this.blePpgDataByte.length);
                                                this.f24895c.add(new ElbpBlePpgInfo(this.blePpgStartDate, this.blePpgDataByte));
                                                this.blePpgStartDate = getBleStartDate(4, bArr);
                                                int length3 = bArr.length - 10;
                                                byte[] bArr4 = new byte[length3];
                                                System.arraycopy(bArr, 10, bArr4, 0, length3);
                                                this.blePpgDataByte = bArr4;
                                                break;
                                            }
                                        } else {
                                            this.f24894b = i12;
                                            this.f24895c = new ArrayList();
                                            this.blePpgStartDate = getBleStartDate(4, bArr);
                                            int length4 = bArr.length - 10;
                                            byte[] bArr5 = new byte[length4];
                                            System.arraycopy(bArr, 10, bArr5, 0, length4);
                                            this.blePpgDataByte = bArr5;
                                            break;
                                        }
                                    }
                                } else {
                                    int i14 = bArr[2] & 255;
                                    if (i14 != 253) {
                                        if (i14 == 255) {
                                            LogUtils.i("Elbp PPG数据 APP不校验通过,通知设备完成");
                                            UteListenerManager.getInstance().onElbpPpgDataSyncFail();
                                            break;
                                        }
                                    } else {
                                        LogUtils.i("Elbp PPG数据 APP校验通过,通知设备完成");
                                        LogUtils.i("Elbp PPG数据 APP校验通过,通知设备完成 blePpgDataByte = " + this.blePpgDataByte.length);
                                        this.f24895c.add(new ElbpBlePpgInfo(this.blePpgStartDate, this.blePpgDataByte));
                                        utenif(this.f24895c);
                                        this.f24895c = new ArrayList();
                                        break;
                                    }
                                }
                            } else {
                                byte b2 = bArr[3];
                                boolean z3 = b2 == this.appPpgCrc;
                                LogUtils.i("Elbp ble储存的PPG数据  ppgCrcResult=" + z3 + ",appPpgCrc =" + this.appPpgCrc + ",blePpgCrc =" + ((int) b2));
                                LogUtils.i(z3 ? "Elbp PPG数据 APP校验通过,准备通知设备端" : "Elbp PPG数据 APP不校验通过,准备通知设备端");
                                utenif(z3 ? 1 : 2);
                                UteListenerManager.getInstance().onElbpPpgDataSyncing();
                                this.f24894b = -1;
                                this.appPpgCrc = 0;
                                break;
                            }
                            break;
                        case 3:
                            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                            if (bArr.length != 4 || (bArr[2] & 255) != 253) {
                                if (bArr.length != 3) {
                                    if (bArr.length > 4) {
                                        CommandTimeOutUtils.getInstance().setCommandTimeOut(139);
                                        UteListenerManager.getInstance().onElbpMiddleDataSyncing();
                                        int i15 = bArr[2] & 255;
                                        int i16 = bArr[3] & 255;
                                        LogUtils.i("Elbp ble储存的中间值数据 serialNum =" + i16 + ",totalCount =" + i15);
                                        while (i6 < bArr.length) {
                                            this.appMiddleCrc ^= bArr[i6];
                                            i6++;
                                        }
                                        int i17 = this.f24896d;
                                        if (i17 != -1) {
                                            if (i17 == i16) {
                                                int length5 = bArr.length - 4;
                                                byte[] bArr6 = new byte[length5];
                                                System.arraycopy(bArr, 4, bArr6, 0, length5);
                                                this.bleMiddleDataByte = ByteDataUtil.getInstance().copyTwoArrays(this.bleMiddleDataByte, bArr6);
                                                break;
                                            } else {
                                                this.f24896d = i16;
                                                this.f24897e.add(new ElbpBleMiddleInfo(this.bleMiddleStartDate, this.bleMiddleDataByte));
                                                this.bleMiddleStartDate = getBleStartDate(4, bArr);
                                                int length6 = bArr.length - 10;
                                                byte[] bArr7 = new byte[length6];
                                                System.arraycopy(bArr, 10, bArr7, 0, length6);
                                                this.bleMiddleDataByte = bArr7;
                                                break;
                                            }
                                        } else {
                                            this.f24896d = i16;
                                            this.f24897e = new ArrayList();
                                            this.bleMiddleStartDate = getBleStartDate(4, bArr);
                                            int length7 = bArr.length - 10;
                                            byte[] bArr8 = new byte[length7];
                                            System.arraycopy(bArr, 10, bArr8, 0, length7);
                                            this.bleMiddleDataByte = bArr8;
                                            break;
                                        }
                                    }
                                } else {
                                    int i18 = bArr[2] & 255;
                                    if (i18 != 253) {
                                        if (i18 == 255) {
                                            LogUtils.i("Elbp 中间值 APP校验不通过,通知设备完成");
                                            UteListenerManager.getInstance().onElbpMiddleDataSyncFail();
                                            break;
                                        }
                                    } else {
                                        LogUtils.i("Elbp 中间值APP校验通过,通知设备完成");
                                        this.f24897e.add(new ElbpBleMiddleInfo(this.bleMiddleStartDate, this.bleMiddleDataByte));
                                        utendo(this.f24897e);
                                        this.f24897e = new ArrayList();
                                        break;
                                    }
                                }
                            } else {
                                byte b3 = bArr[3];
                                boolean z4 = b3 == this.appMiddleCrc;
                                LogUtils.i("Elbp ble储存的中间值数据  middleCrcResult=" + z4 + ",appMiddleCrc =" + this.appMiddleCrc + ",bleMiddleCrc =" + ((int) b3));
                                LogUtils.i(z4 ? "Elbp 中间值APP校验通过,准备通知设备端" : "Elbp 中间值APP不校验通过,准备通知设备端");
                                utenif(z4 ? 3 : 4);
                                UteListenerManager.getInstance().onElbpMiddleDataSyncing();
                                this.f24896d = -1;
                                this.appMiddleCrc = 0;
                                break;
                            }
                            break;
                        case 4:
                            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                            uteListenerManager = UteListenerManager.getInstance();
                            i2 = 13;
                            break;
                        case 5:
                            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                            uteListenerManager = UteListenerManager.getInstance();
                            i2 = 14;
                            break;
                        case 6:
                            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                            String strAsciiStringToString = GBUtils.getInstance().AsciiStringToString(str.substring(6, ((bArr[2] & 255) * 2) + 6));
                            LogUtils.i("algorithmVersion =" + strAsciiStringToString);
                            UteListenerManager.getInstance().onElbpAlgorithmVersion(true, strAsciiStringToString);
                            break;
                        case 7:
                            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                            int i19 = bArr[2] & 255;
                            if (i19 == 250) {
                                int i20 = bArr[3] & 255;
                                LogUtils.i("APP下发血压标定模型 ble返回序号 =" + i20);
                                WriteCommandToBLE.getInstance().sendElbsCalibrationModelDataSegments(i20 + 1);
                                break;
                            } else if (i19 == 253) {
                                i4 = 19;
                                if (WriteCommandToBLE.getInstance().getElbsCalibrationModelCrc() != (bArr[3] & 255)) {
                                    str2 = "APP下发血压标定模型 完成,校验失败 ";
                                    LogUtils.i(str2);
                                    uteListenerManager = UteListenerManager.getInstance();
                                    i2 = i4;
                                    z2 = false;
                                    break;
                                } else {
                                    str3 = "APP下发血压标定模型 完成,校验成功 ";
                                    LogUtils.i(str3);
                                    uteListenerManager = UteListenerManager.getInstance();
                                    i2 = i4;
                                    break;
                                }
                            }
                            break;
                        default:
                            switch (i5 == true ? 1 : 0) {
                                case 13:
                                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                                    int i21 = bArr[2] & 255;
                                    if (i21 != 17) {
                                        if (i21 == 255) {
                                            LogUtils.i("恒爱血压算法未激活,需要激活");
                                            uteListenerManager = UteListenerManager.getInstance();
                                            i2 = 16;
                                            break;
                                        }
                                    } else {
                                        LogUtils.i("恒爱血压算法已激活，不需要再激活");
                                        uteListenerManager = UteListenerManager.getInstance();
                                        i2 = 15;
                                        break;
                                    }
                                    break;
                                case 14:
                                    if (bArr.length == 5) {
                                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                                        if ("550E5811FD".equals(str)) {
                                            LogUtils.i("发送激活码到Ble的回调,恒爱血压算法激活成功");
                                            uteListenerManager = UteListenerManager.getInstance();
                                            i2 = 17;
                                            break;
                                        } else {
                                            str4 = "发送激活码到Ble的回调,恒爱血压算法激活失败返回";
                                        }
                                    } else {
                                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                                        if ((bArr[2] & 255) == 64) {
                                            String strAsciiStringToString2 = GBUtils.getInstance().AsciiStringToString(str.substring(6));
                                            LogUtils.i("已获取到恒爱血压申请码");
                                            MoodHttpPostUtils.getInstance().activationElbpIo(strAsciiStringToString2, new utenfor());
                                            break;
                                        } else {
                                            str4 = "获取恒爱血压申请码失败";
                                        }
                                    }
                                    LogUtils.i(str4);
                                    uteListenerManager = UteListenerManager.getInstance();
                                    i2 = 18;
                                    break;
                                case 15:
                                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                                    int i22 = bArr[2] & 255;
                                    if (i22 == 250) {
                                        int i23 = bArr[3] & 255;
                                        LogUtils.i("APP下发4G登录恒爱服务器需要的账号等信息 ble返回序号 =" + i23);
                                        WriteCommandToBLE.getInstance().sendLoginElServerInfoSegments(i23 + 1);
                                        break;
                                    } else if (i22 == 253) {
                                        i4 = 20;
                                        if (WriteCommandToBLE.getInstance().getLoginElServerInfoFD() != (bArr[3] & 255)) {
                                            str2 = "APP下发4G登录恒爱服务器需要的账号等信息 完成,校验失败 ";
                                            LogUtils.i(str2);
                                            uteListenerManager = UteListenerManager.getInstance();
                                            i2 = i4;
                                            z2 = false;
                                            break;
                                        } else {
                                            str3 = "APP下发4G登录恒爱服务器需要的账号等信息 完成,校验成功 ";
                                            LogUtils.i(str3);
                                            uteListenerManager = UteListenerManager.getInstance();
                                            i2 = i4;
                                            break;
                                        }
                                    }
                                    break;
                            }
                    }
                }
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                int i24 = bArr[2] & 255;
                if (i24 == 17) {
                    LogUtils.i("Elbp 打开调试模式（ble不断返回ppg和中间值");
                    uteListenerManager = UteListenerManager.getInstance();
                    i2 = 11;
                } else {
                    if (i24 != 0) {
                        return;
                    }
                    LogUtils.i("Elbp 关闭调试模式");
                    uteListenerManager = UteListenerManager.getInstance();
                    i2 = 12;
                }
            }
        } else {
            if (bArr.length > 5) {
                int i25 = bArr[2] & 255;
                int i26 = bArr[3] & 255;
                LogUtils.i("Elbp 中间值数据 serialNum =" + i26 + ",dataState =" + i25);
                if (i26 == 1) {
                    this.f24893a = new ArrayList();
                }
                int length8 = bArr.length - 4;
                byte[] bArr9 = new byte[length8];
                System.arraycopy(bArr, 4, bArr9, 0, length8);
                this.f24893a.add(new ElbpMiddleDataInfo(i26, i25, utendo(bArr9), bArr9));
                return;
            }
            int i27 = bArr[2] & 255;
            LogUtils.i("Elbp 开始测试 startType =" + i27);
            this.appStartDate = CalendarUtils.getCalendarAndTime2();
            if (i27 == 0) {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 4;
            } else {
                if (i27 != 1) {
                    return;
                }
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 5;
            }
        }
        uteListenerManager.onElbpStatus(z2, i2);
    }

    public void dealwithElbs(String str, byte[] bArr) {
        int i2 = bArr[1] & 255;
        if (i2 == 0) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            int i3 = bArr[2] & 255;
            if (i3 == 17) {
                UteListenerManager.getInstance().onElbsStatus(true, 2);
                return;
            } else {
                if (i3 == 255) {
                    UteListenerManager.getInstance().onElbsStatus(true, 3);
                    return;
                }
                return;
            }
        }
        if (i2 != 1) {
            if (i2 != 170) {
                return;
            }
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            int i4 = bArr[2] & 255;
            if (i4 == 1) {
                UteListenerManager.getInstance().onElbsStatus(true, 4);
                return;
            } else {
                if (i4 == 0) {
                    UteListenerManager.getInstance().onElbsStatus(true, 5);
                    return;
                }
                return;
            }
        }
        if (bArr.length == 2) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onElbsStatus(true, 1);
            return;
        }
        if (bArr.length > 5) {
            int i5 = ((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[3] & 255);
            int i6 = bArr[4] & 255;
            LogUtils.i("Elbs 实时PPG数据 serialNum =" + i5 + ",curDataLength =" + i6);
            int length = bArr.length - 5;
            byte[] bArr2 = new byte[length];
            System.arraycopy(bArr, 5, bArr2, 0, length);
            UteListenerManager.getInstance().onElbsPpgRealTime(new ElbsPpgDataInfo(i5, i6, utenif(bArr2), bArr2));
        }
    }

    public void dealwithMoodPressureFatigue(String str, byte[] bArr) throws ParseException {
        int i2;
        int i3;
        UteListenerManager uteListenerManager;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        int i4 = bArr[1] & 255;
        if (i4 == 0) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            int i5 = bArr[2] & 255;
            int i6 = bArr[3] & 255;
            int i7 = bArr[4] & 255;
            int i8 = bArr[5] & 255;
            LogUtils.i("停止测试情绪压力 endingReason =" + i8 + ",moodValue =" + i5 + ",pressureValue =" + i6 + ",fatigueValue =" + i7);
            if (i8 == 0) {
                MoodPressureFatigueInfo moodPressureFatigueInfo = new MoodPressureFatigueInfo();
                moodPressureFatigueInfo.setMoodValue(i5);
                moodPressureFatigueInfo.setPressureValue(i6);
                moodPressureFatigueInfo.setFatigueValue(i7);
                this.startDateCalendar = CalendarUtils.getCalendar();
                this.startDateMunite = CalendarUtils.getPhoneCurrentMinute();
                if (!TextUtils.isEmpty(this.startDateCalendar)) {
                    String calendarTime = CalendarUtils.getCalendarTime(this.startDateCalendar, this.startDateMunite);
                    moodPressureFatigueInfo.setCalendar(this.startDateCalendar);
                    moodPressureFatigueInfo.setStartDate(calendarTime);
                    moodPressureFatigueInfo.setTime(this.startDateMunite);
                }
                UteListenerManager.getInstance().onMoodPressureRealTime(moodPressureFatigueInfo);
                uteListenerManager = UteListenerManager.getInstance();
                i3 = 0;
            } else {
                int i9 = bArr[5] & 255;
                if (i9 == 241) {
                    LogUtils.i("服务器解析异常");
                    uteListenerManager = UteListenerManager.getInstance();
                    i3 = 241;
                } else if (i9 == 242) {
                    LogUtils.i("测试超时 (超过2分钟本地测试未出值或服务器未返回值)");
                    uteListenerManager = UteListenerManager.getInstance();
                    i3 = 242;
                } else if (i9 == 243) {
                    LogUtils.i("脱手");
                    uteListenerManager = UteListenerManager.getInstance();
                    i3 = 243;
                } else {
                    i2 = 244;
                    if (i9 != 244) {
                        i2 = 245;
                        if (i9 != 245) {
                            i2 = 246;
                            if (i9 != 246) {
                                i3 = 247;
                                if (i9 != 247) {
                                    return;
                                } else {
                                    uteListenerManager = UteListenerManager.getInstance();
                                }
                            }
                        }
                        str2 = "其他原因 (用户切走页面等)";
                        LogUtils.i(str2);
                    }
                    str2 = "运动中";
                    LogUtils.i(str2);
                }
            }
            uteListenerManager.onMoodPressureStatus(true, i3);
        }
        if (i4 == 17) {
            if (bArr.length == 2) {
                LogUtils.i("开始测试情绪压力");
                this.startDateMood = CalendarUtils.getCalendarAndTime();
                this.startDateCalendar = CalendarUtils.getCalendar();
                this.startDateMunite = CalendarUtils.getPhoneCurrentMinute();
                this.moodTestMedian = "";
                this.moodTestMedianCount = 0;
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                uteListenerManager = UteListenerManager.getInstance();
                i3 = 100;
                uteListenerManager.onMoodPressureStatus(true, i3);
            }
            if (bArr.length != 3) {
                if (bArr.length <= 4 || (bArr[2] & 255) != 0) {
                    return;
                }
                this.moodTestMedian += str.substring(8);
                return;
            }
            int i10 = bArr[2] & 255;
            if (i10 == 253) {
                this.moodTestMedianCount = 0;
                if (TextUtils.isEmpty(this.startDateMood)) {
                    this.startDateMood = CalendarUtils.getCalendarAndTime();
                }
                this.endDateMood = CalendarUtils.getCalendarAndTime();
                LogUtils.i("停止测试情绪压力 start_time_mood =" + this.startDateMood + ",end_time_mood =" + this.endDateMood);
                MoodHttpPostUtils.getInstance().getMoodPressureData(this.startDateMood, this.endDateMood, this.moodTestMedian, new utendo());
                this.moodTestMedian = "";
                return;
            }
            if (i10 == 17) {
                str3 = "正常出值";
                LogUtils.i(str3);
                return;
            }
            if (i10 != 241) {
                if (i10 != 242) {
                    if (i10 != 243) {
                        i2 = 244;
                        if (i10 != 244) {
                            i2 = 245;
                            if (i10 != 245) {
                                i2 = 246;
                                if (i10 != 246) {
                                    return;
                                }
                            }
                            str2 = "其他原因 (用户切走页面等)";
                            LogUtils.i(str2);
                        }
                        str2 = "运动中";
                        LogUtils.i(str2);
                    }
                    LogUtils.i("脱手");
                    uteListenerManager = UteListenerManager.getInstance();
                    i3 = 243;
                    uteListenerManager.onMoodPressureStatus(true, i3);
                }
                LogUtils.i("测试超时 (超过2分钟本地测试未出值或服务器未返回值)");
                uteListenerManager = UteListenerManager.getInstance();
                i3 = 242;
                uteListenerManager.onMoodPressureStatus(true, i3);
            }
            LogUtils.i("服务器解析异常");
            uteListenerManager = UteListenerManager.getInstance();
            i3 = 241;
            uteListenerManager.onMoodPressureStatus(true, i3);
        }
        if (i4 == 170) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            int i11 = bArr[2] & 255;
            if (i11 == 17) {
                LogUtils.i("情绪压力疲劳度正在测试");
                uteListenerManager = UteListenerManager.getInstance();
                i3 = 105;
            } else {
                if (i11 != 255) {
                    return;
                }
                LogUtils.i("情绪压力疲劳度没有在测试");
                uteListenerManager = UteListenerManager.getInstance();
                i3 = 106;
            }
        } else {
            if (i4 == 250) {
                if (bArr.length == 4 && str.startsWith("44FAFD")) {
                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                    int i12 = bArr[3] & 255;
                    LogUtils.i("情绪压力同步完成，并给出检验结果 bleCrc =" + i12 + ",moodCRC =" + (this.moodCRC & 255));
                    if (i12 == (this.moodCRC & 255)) {
                        this.moodCRCCount = 0;
                        UteListenerManager.getInstance().onMoodPressureSyncSuccess(this.moodInfoList);
                        this.moodInfoList = new ArrayList();
                        this.moodCRC = 0;
                        str3 = "情绪压力同步完成,检验成功";
                        LogUtils.i(str3);
                        return;
                    }
                    this.moodCRCCount++;
                    LogUtils.i("情绪压力同步完成,检验失败");
                    this.moodInfoList = new ArrayList();
                    this.moodCRC = 0;
                    this.moodCRCCount = 0;
                    UteListenerManager.getInstance().onMoodPressureSyncFail();
                    return;
                }
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                CommandTimeOutUtils.getInstance().setCommandTimeOut(99);
                for (int i13 = 2; i13 < bArr.length; i13++) {
                    this.moodCRC ^= bArr[i13];
                }
                String calendar = getCalendar(bArr);
                int hour = getHour(bArr);
                if (hour == 0) {
                    calendar = utendo(calendar);
                    hour = 24;
                }
                int i14 = hour * 60;
                int length = (bArr.length - 8) / 3;
                int i15 = DeviceMode.isHasFunction_9(262144) ? 5 : 10;
                for (int i16 = 0; i16 < length; i16++) {
                    int i17 = i16 * 3;
                    int i18 = bArr[i17 + 8] & 255;
                    if (i18 != 255 || (bArr[i17 + 9] & 255) != 255 || (bArr[i17 + 10] & 255) != 255) {
                        int i19 = i14 - ((11 - i16) * i15);
                        this.moodInfoList.add(new MoodPressureFatigueInfo(calendar, CalendarUtils.getCalendarTime(calendar, i19), i19, i18, bArr[i17 + 9] & 255, bArr[i17 + 10] & 255));
                    }
                }
                UteListenerManager.getInstance().onMoodPressureSyncing();
                return;
            }
            if (i4 == 3) {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                int i20 = bArr[2] & 255;
                if (i20 != 1) {
                    str4 = i20 == 0 ? "设置自动测试关" : "设置自动测试开";
                    uteListenerManager = UteListenerManager.getInstance();
                    i3 = 102;
                }
                LogUtils.i(str4);
                uteListenerManager = UteListenerManager.getInstance();
                i3 = 102;
            } else if (i4 != 4) {
                switch (i4) {
                    case 12:
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        uteListenerManager = UteListenerManager.getInstance();
                        i3 = 111;
                        break;
                    case 13:
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        int i21 = bArr[2] & 255;
                        if (i21 == 17) {
                            LogUtils.i("算法已激活，不需要再激活");
                            uteListenerManager = UteListenerManager.getInstance();
                            i3 = 107;
                            break;
                        } else if (i21 == 255) {
                            LogUtils.i("算法未激活,需要激活");
                            uteListenerManager = UteListenerManager.getInstance();
                            i3 = 108;
                            break;
                        } else {
                            return;
                        }
                    case 14:
                        if (bArr.length == 5) {
                            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                            if ("440E5811FD".equals(str)) {
                                LogUtils.i("发送激活码到Ble的回调,算法激活成功");
                                uteListenerManager = UteListenerManager.getInstance();
                                i3 = 109;
                                break;
                            } else {
                                str6 = "发送激活码到Ble的回调,算法激活失败返回";
                            }
                        } else {
                            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                            if ((bArr[2] & 255) == 64) {
                                String strAsciiStringToString = GBUtils.getInstance().AsciiStringToString(str.substring(6));
                                LogUtils.i("已获取到申请码");
                                MoodHttpPostUtils.getInstance().activeMoodAlgorithm(strAsciiStringToString, new utenif());
                                return;
                            }
                            str6 = "获取到申请码失败";
                        }
                        LogUtils.i(str6);
                        uteListenerManager = UteListenerManager.getInstance();
                        i3 = 110;
                        break;
                    case 15:
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        int i22 = bArr[2] & 255;
                        int i23 = bArr[3] & 255;
                        int i24 = bArr[4] & 255;
                        int i25 = 255 & bArr[5];
                        byte[] bArr2 = new byte[i25];
                        System.arraycopy(bArr, 6, bArr2, 0, i25);
                        String strUtf8ByteToString = GBUtils.getInstance().utf8ByteToString(bArr2);
                        SPUtil.getInstance().setMoodSensorType(strUtf8ByteToString);
                        SPUtil.getInstance().setMoodInterfaceSwitch(i22);
                        SPUtil.getInstance().setPressureInterfaceSwitch(i23);
                        SPUtil.getInstance().setFatigueInterfaceSwitch(i24);
                        LogUtils.i("sensorType =" + strUtf8ByteToString + ",moodInterfaceSwitch =" + i22 + ",pressureInterfaceSwitch =" + i23 + ",fatigueInterfaceSwitch =" + i24 + ",sensorLen =" + i25);
                        UteListenerManager.getInstance().onMoodPressureSensor(new MoodSensorInterfaceInfo(strUtf8ByteToString, i22, i23, i24));
                        return;
                    default:
                        switch (i4) {
                            case 32:
                                int i26 = bArr[2] & 255;
                                if (i26 == 0) {
                                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                                    UteListenerManager.getInstance().onContinuousPpgStatus(true, 1);
                                    break;
                                } else if (i26 == 1) {
                                    UteListenerManager.getInstance().onContinuousPpgStatus(true, 2);
                                    break;
                                }
                                break;
                            case 33:
                                int i27 = bArr[2] & 255;
                                if (i27 == 1) {
                                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                                    UteListenerManager.getInstance().onContinuousPpgStatus(true, 4);
                                    break;
                                } else if (i27 == 2) {
                                    UteListenerManager.getInstance().onContinuousPpgStatus(true, 5);
                                    break;
                                } else if (i27 == 3) {
                                    UteListenerManager.getInstance().onContinuousPpgStatus(true, 6);
                                    break;
                                }
                                break;
                            case 34:
                                int i28 = ((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[3] & 255);
                                int i29 = bArr[4] & 255;
                                int length2 = bArr.length - 5;
                                byte[] bArr3 = new byte[length2];
                                System.arraycopy(bArr, 5, bArr3, 0, length2);
                                UteListenerManager.getInstance().onElbpContinuousPpgRealTime(new ElbpContinuousPpgDataInfo(i28, i29, bArr3));
                                break;
                        }
                }
            } else {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                int i30 = bArr[2] & 255;
                if (i30 != 1) {
                    str5 = i30 == 0 ? "设置自动测试时间段关" : "设置自动测试时间段开";
                    uteListenerManager = UteListenerManager.getInstance();
                    i3 = 103;
                }
                LogUtils.i(str5);
                uteListenerManager = UteListenerManager.getInstance();
                i3 = 103;
            }
        }
        uteListenerManager.onMoodPressureStatus(true, i3);
        uteListenerManager = UteListenerManager.getInstance();
        i3 = i2;
        uteListenerManager.onMoodPressureStatus(true, i3);
    }

    public String getBleStartDate(int i2, byte[] bArr) {
        int i3 = (bArr[i2 + 1] & 255) | ((bArr[i2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
        int i4 = bArr[i2 + 2] & 255;
        int i5 = bArr[i2 + 3] & 255;
        int i6 = bArr[i2 + 4] & 255;
        int i7 = bArr[i2 + 5] & 255;
        try {
            return String.format(Locale.US, "%1$04d%2$02d%3$02d%4$02d%5$02d", Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6), Integer.valueOf(i7));
        } catch (Exception e2) {
            LogUtils.i("getBleStartDate Exception =" + e2);
            String strValueOf = String.valueOf(i4);
            String strValueOf2 = String.valueOf(i5);
            String strValueOf3 = String.valueOf(i6);
            String strValueOf4 = String.valueOf(i7);
            if (i4 < 10) {
                strValueOf = "0" + i4;
            }
            if (i5 < 10) {
                strValueOf2 = "0" + i5;
            }
            if (i6 < 10) {
                strValueOf3 = "0" + i6;
            }
            if (i7 < 10) {
                strValueOf4 = "0" + i7;
            }
            return i3 + strValueOf + strValueOf2 + strValueOf3 + strValueOf4;
        }
    }

    public String getCalendar(byte[] bArr) {
        int i2 = bArr[5] & 255;
        int i3 = bArr[4] & 255;
        int i4 = (bArr[3] & 255) | ((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
        String strValueOf = String.valueOf(i2);
        String strValueOf2 = String.valueOf(i3);
        if (i2 < 10) {
            strValueOf = "0" + i2;
        }
        if (i3 < 10) {
            strValueOf2 = "0" + i3;
        }
        return i4 + strValueOf2 + strValueOf;
    }

    public float getFloat(byte[] bArr, int i2) {
        return Float.intBitsToFloat(getInt(bArr, i2));
    }

    public int getHour(byte[] bArr) {
        return bArr[6] & 255;
    }

    public int getInt(byte[] bArr, int i2) {
        return (bArr[i2 + 3] & 255) | ((bArr[i2] << Ascii.CAN) & ViewCompat.MEASURED_STATE_MASK) | ((bArr[i2 + 1] << 16) & 16711680) | ((bArr[i2 + 2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    }

    public final void utenif(List list) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            ElbpBlePpgInfo elbpBlePpgInfo = (ElbpBlePpgInfo) list.get(i2);
            byte[] ppgData = elbpBlePpgInfo.getPpgData();
            int length = ppgData.length / 48;
            ArrayList arrayList2 = new ArrayList();
            for (int i3 = 0; i3 < length; i3++) {
                byte[] bArr = new byte[48];
                System.arraycopy(ppgData, i3 * 48, bArr, 0, 48);
                arrayList2.add(utenif(bArr));
            }
            elbpBlePpgInfo.setPpgDataList(arrayList2);
            LogUtils.i("doElbpBlePpgData getStartDate =" + elbpBlePpgInfo.getStartDate() + "，ppgData.length =" + ppgData.length + "," + elbpBlePpgInfo.getPpgDataList().size());
            arrayList.add(elbpBlePpgInfo);
        }
        UteListenerManager.getInstance().onElbpPpgDataSyncSuccess(arrayList);
    }

    public final synchronized List utenif(byte[] bArr) {
        ArrayList arrayList;
        int length = bArr.length;
        arrayList = new ArrayList();
        for (int i2 = 0; i2 < length; i2 += 3) {
            int i3 = ((bArr[i2] & 255) << 16) + ((bArr[i2 + 1] & 255) << 8) + (bArr[i2 + 2] & 255);
            double d2 = i3;
            double d3 = this.f24898f;
            if (d2 >= d3 / 2.0d) {
                i3 = (int) (d2 - d3);
            }
            arrayList.add(Integer.valueOf(i3));
        }
        return arrayList;
    }

    public final void utendo(List list) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            ElbpBleMiddleInfo elbpBleMiddleInfo = (ElbpBleMiddleInfo) list.get(i2);
            byte[] middleData = elbpBleMiddleInfo.getMiddleData();
            int length = middleData.length / 32;
            ArrayList arrayList2 = new ArrayList();
            for (int i3 = 0; i3 < length; i3++) {
                byte[] bArr = new byte[32];
                System.arraycopy(middleData, i3 * 32, bArr, 0, 32);
                arrayList2.add(utendo(bArr));
            }
            elbpBleMiddleInfo.setMiddleDataList(arrayList2);
            LogUtils.i("doElbpBleMiddleData getStartDate =" + elbpBleMiddleInfo.getStartDate() + "，middleData.length =" + middleData.length + "," + elbpBleMiddleInfo.getMiddleDataList().size());
            arrayList.add(elbpBleMiddleInfo);
        }
        UteListenerManager.getInstance().onElbpMiddleDataSyncSuccess(arrayList);
    }

    public final void utenif(int i2) {
        Message message = new Message();
        message.what = 0;
        message.arg1 = i2;
        this.mHandler.sendMessage(message);
    }

    public final String utendo(String str) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        try {
            date = new SimpleDateFormat(CalendarUtils.yyyyMMdd, Locale.US).parse(str);
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        calendar.setTime(date);
        calendar.set(5, calendar.get(5) - 1);
        return new SimpleDateFormat(CalendarUtils.yyyyMMdd, Locale.US).format(calendar.getTime());
    }

    public final void utendo() {
        if (this.mHandler == null) {
            HandlerThread handlerThread = new HandlerThread("HandlerThread");
            handlerThread.start();
            this.mHandler = new utenint(handlerThread.getLooper());
        }
    }

    public final List utendo(byte[] bArr) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < 8; i2++) {
            arrayList.add(Double.valueOf(getFloat(bArr, i2 * 4)));
        }
        return arrayList;
    }

    public final void utendo(int i2) {
        if (i2 == 1) {
            WriteCommandToBLE.getInstance().sendDeviceElbpPpgDataCrcResults(true);
            return;
        }
        if (i2 == 2) {
            WriteCommandToBLE.getInstance().sendDeviceElbpPpgDataCrcResults(false);
        } else if (i2 == 3) {
            WriteCommandToBLE.getInstance().sendDeviceElbpMiddleDataCrcResults(true);
        } else {
            if (i2 != 4) {
                return;
            }
            WriteCommandToBLE.getInstance().sendDeviceElbpMiddleDataCrcResults(false);
        }
    }
}
