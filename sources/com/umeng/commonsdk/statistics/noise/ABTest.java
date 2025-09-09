package com.umeng.commonsdk.statistics.noise;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.statistics.common.MLog;
import com.umeng.commonsdk.statistics.idtracking.Envelope;
import com.umeng.commonsdk.statistics.idtracking.ImprintHandler;
import com.umeng.commonsdk.statistics.internal.d;

/* loaded from: classes4.dex */
public class ABTest implements d {
    private static ABTest instance;
    private Context context;
    private boolean isInTest = false;
    private int mPolicy = -1;
    private int mInterval = -1;
    private int mGroup = -1;
    private float mProb13 = 0.0f;
    private float mProb07 = 0.0f;
    private String mPoli = null;

    private ABTest(Context context, String str, int i2) {
        this.context = context;
        onExperimentChanged(str, i2);
    }

    public static synchronized ABTest getService(Context context) {
        try {
            if (instance == null) {
                instance = new ABTest(context, UMEnvelopeBuild.imprintProperty(context, "client_test", null), Integer.valueOf(UMEnvelopeBuild.imprintProperty(context, "test_report_interval", "0")).intValue());
            }
        } catch (Throwable th) {
            throw th;
        }
        return instance;
    }

    private void parseFIXED(String str) {
        int[] iArr;
        if (str == null) {
            return;
        }
        String[] strArrSplit = str.split("\\|");
        if (this.mProb13 > (strArrSplit[2].equals("SIG13") ? Float.valueOf(strArrSplit[3]).floatValue() : 0.0f)) {
            this.isInTest = false;
            return;
        }
        int iIntValue = strArrSplit[0].equals("FIXED") ? Integer.valueOf(strArrSplit[1]).intValue() : -1;
        if (strArrSplit[4].equals("RPT")) {
            this.mPoli = "RPT";
            String[] strArrSplit2 = strArrSplit[5].split(",");
            iArr = new int[strArrSplit2.length];
            for (int i2 = 0; i2 < strArrSplit2.length; i2++) {
                iArr[i2] = Integer.valueOf(strArrSplit2[i2]).intValue();
            }
        } else if (strArrSplit[4].equals("DOM")) {
            this.mPoli = "DOM";
            this.isInTest = true;
            try {
                String[] strArrSplit3 = strArrSplit[5].split(",");
                iArr = new int[strArrSplit3.length];
                for (int i3 = 0; i3 < strArrSplit3.length; i3++) {
                    try {
                        iArr[i3] = Integer.valueOf(strArrSplit3[i3]).intValue();
                    } catch (Exception unused) {
                    }
                }
            } catch (Exception unused2) {
            }
        } else {
            iArr = null;
        }
        if (iIntValue == -1) {
            this.isInTest = false;
            return;
        }
        this.isInTest = true;
        this.mGroup = iIntValue;
        if (iArr != null) {
            this.mPolicy = iArr[iIntValue - 1];
        }
    }

    private void parseSig7(String str) {
        float[] fArr;
        if (str == null) {
            return;
        }
        String[] strArrSplit = str.split("\\|");
        float f2 = 0.0f;
        if (this.mProb13 > (strArrSplit[2].equals("SIG13") ? Float.valueOf(strArrSplit[3]).floatValue() : 0.0f)) {
            this.isInTest = false;
            return;
        }
        int[] iArr = null;
        if (strArrSplit[0].equals("SIG7")) {
            String[] strArrSplit2 = strArrSplit[1].split(",");
            fArr = new float[strArrSplit2.length];
            for (int i2 = 0; i2 < strArrSplit2.length; i2++) {
                fArr[i2] = Float.valueOf(strArrSplit2[i2]).floatValue();
            }
        } else {
            fArr = null;
        }
        if (strArrSplit[4].equals("RPT")) {
            this.mPoli = "RPT";
            String[] strArrSplit3 = strArrSplit[5].split(",");
            iArr = new int[strArrSplit3.length];
            for (int i3 = 0; i3 < strArrSplit3.length; i3++) {
                iArr[i3] = Integer.valueOf(strArrSplit3[i3]).intValue();
            }
        } else if (strArrSplit[4].equals("DOM")) {
            this.isInTest = true;
            this.mPoli = "DOM";
            try {
                String[] strArrSplit4 = strArrSplit[5].split(",");
                iArr = new int[strArrSplit4.length];
                for (int i4 = 0; i4 < strArrSplit4.length; i4++) {
                    iArr[i4] = Integer.valueOf(strArrSplit4[i4]).intValue();
                }
            } catch (Exception unused) {
            }
        }
        int i5 = 0;
        while (true) {
            if (i5 >= fArr.length) {
                i5 = -1;
                break;
            }
            f2 += fArr[i5];
            if (this.mProb07 < f2) {
                break;
            } else {
                i5++;
            }
        }
        if (i5 == -1) {
            this.isInTest = false;
            return;
        }
        this.isInTest = true;
        this.mGroup = i5 + 1;
        if (iArr != null) {
            this.mPolicy = iArr[i5];
        }
    }

    private float prob(String str, int i2) {
        int i3 = i2 * 2;
        if (str == null) {
            return 0.0f;
        }
        return Integer.valueOf(str.substring(i3, i3 + 5), 16).intValue() / 1048576.0f;
    }

    public static boolean validate(String str) throws NumberFormatException {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String[] strArrSplit = str.split("\\|");
        if (strArrSplit.length != 6) {
            return false;
        }
        if (strArrSplit[0].startsWith("SIG7") && strArrSplit[1].split(",").length == strArrSplit[5].split(",").length) {
            return true;
        }
        if (strArrSplit[0].startsWith("FIXED")) {
            int length = strArrSplit[5].split(",").length;
            int i2 = Integer.parseInt(strArrSplit[1]);
            if (length >= i2 && i2 >= 1) {
                return true;
            }
        }
        return false;
    }

    public int getGroup() {
        return this.mGroup;
    }

    public String getGroupInfo() {
        return !this.isInTest ? "error" : String.valueOf(this.mGroup);
    }

    public int getTestInterval() {
        return this.mInterval;
    }

    public String getTestName() {
        return this.mPoli;
    }

    public int getTestPolicy() {
        return this.mPolicy;
    }

    public boolean isInTest() {
        return this.isInTest;
    }

    public void onExperimentChanged(String str, int i2) {
        this.mInterval = i2;
        String signature = Envelope.getSignature(this.context);
        if (TextUtils.isEmpty(signature) || TextUtils.isEmpty(str)) {
            this.isInTest = false;
            return;
        }
        try {
            this.mProb13 = prob(signature, 12);
            this.mProb07 = prob(signature, 6);
            if (str.startsWith("SIG7")) {
                parseSig7(str);
            } else if (str.startsWith("FIXED")) {
                parseFIXED(str);
            }
        } catch (Exception e2) {
            this.isInTest = false;
            MLog.e("v:" + str, e2);
        }
    }

    @Override // com.umeng.commonsdk.statistics.internal.d
    public void onImprintChanged(ImprintHandler.a aVar) {
        onExperimentChanged(aVar.a("client_test", null), Integer.valueOf(aVar.a("test_report_interval", "0")).intValue());
    }

    public String toString() {
        return " p13:" + this.mProb13 + " p07:" + this.mProb07 + " policy:" + this.mPolicy + " interval:" + this.mInterval;
    }
}
