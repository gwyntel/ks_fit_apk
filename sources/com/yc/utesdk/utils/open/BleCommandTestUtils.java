package com.yc.utesdk.utils.open;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.yc.utesdk.data.DataProcessing;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class BleCommandTestUtils {
    private static BleCommandTestUtils instance;
    private int currentCount = 0;
    private final int PPG_REAL_MSG = 1;
    private final Handler mHandler2 = new utendo(Looper.getMainLooper());
    private List<String> testPpgData = new ArrayList();

    /* renamed from: a, reason: collision with root package name */
    List f24945a = new ArrayList();

    public class utendo extends Handler {
        public utendo(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 1) {
                new StringBuilder((String) BleCommandTestUtils.this.testPpgData.get(BleCommandTestUtils.this.currentCount));
                String str = (String) BleCommandTestUtils.this.testPpgData.get(BleCommandTestUtils.this.currentCount);
                BleCommandTestUtils bleCommandTestUtils = BleCommandTestUtils.this;
                byte[] bArr = (byte[]) bleCommandTestUtils.f24945a.get(bleCommandTestUtils.currentCount);
                if (str.length() >= 4) {
                    str.substring(0, 4);
                }
                DataProcessing.getInstance().dealWithCyweeStep(str, bArr);
                if (BleCommandTestUtils.this.currentCount < BleCommandTestUtils.this.f24945a.size() - 1) {
                    BleCommandTestUtils.utenif(BleCommandTestUtils.this);
                    Message message2 = new Message();
                    message2.what = 1;
                    message2.arg1 = BleCommandTestUtils.this.currentCount;
                    BleCommandTestUtils.this.mHandler2.sendMessageDelayed(message2, 20L);
                }
            }
        }
    }

    public static BleCommandTestUtils getInstance() {
        if (instance == null) {
            instance = new BleCommandTestUtils();
        }
        return instance;
    }

    public static /* synthetic */ int utenif(BleCommandTestUtils bleCommandTestUtils) {
        int i2 = bleCommandTestUtils.currentCount;
        bleCommandTestUtils.currentCount = i2 + 1;
        return i2;
    }

    public void startTestBleCommand() {
        utendo();
        this.currentCount = 0;
        Message message = new Message();
        message.what = 1;
        message.arg1 = this.currentCount;
        this.mHandler2.sendMessageDelayed(message, 20L);
    }

    public final List utendo() {
        this.f24945a = new ArrayList();
        ArrayList arrayList = new ArrayList();
        this.testPpgData = arrayList;
        arrayList.add("EB0107E807030901A500B9003528");
        this.testPpgData.add("EB0107E807030901AF00BF0036D4");
        this.testPpgData.add("EB0107E807030901B500C30036D4");
        this.testPpgData.add("EB0107E807030901BF00C30036D4");
        this.testPpgData.add("EB0207E8070212007C003B00195A");
        this.testPpgData.add("EB0207E8070300000B00000003DD");
        this.testPpgData.add("EB0207E8070301003F001D0004A1");
        this.testPpgData.add("EB0207E8070305001200030000C3");
        this.testPpgData.add("EB0207E8070308019C00AE003B13");
        this.testPpgData.add("EB0207E807030901BF00C30036D4");
        this.testPpgData.add("EB02FD16");
        for (int i2 = 0; i2 < this.testPpgData.size(); i2++) {
            this.f24945a.add(GBUtils.getInstance().hexStringToBytes(this.testPpgData.get(i2)));
        }
        return this.f24945a;
    }
}
