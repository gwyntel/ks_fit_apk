package com.yc.utesdk.utils.close;

import androidx.media3.common.PlaybackException;
import com.yc.utesdk.bean.SigMaxMinInfo;
import java.util.ArrayList;
import java.util.Collections;

/* loaded from: classes4.dex */
public class EcgRealTimeHrAnalysis {
    private double Dy;

    /* renamed from: a, reason: collision with root package name */
    double f24935a;

    /* renamed from: b, reason: collision with root package name */
    double f24936b;

    /* renamed from: c, reason: collision with root package name */
    double f24937c;

    /* renamed from: d, reason: collision with root package name */
    double f24938d;

    /* renamed from: e, reason: collision with root package name */
    double f24939e;

    /* renamed from: f, reason: collision with root package name */
    double f24940f;
    private boolean isCalAll;
    private int pTh12;
    private int pTh23;
    private int pointCnt;
    private int STATE_SECOND = 2;
    private int STATE_THIRD = 3;
    private int STATE_FOURTH = 4;
    private int mEcgState = 2;
    private boolean isStatisRWave = false;
    private int RRPeriod = 0;
    private int NtSig = 5;
    private int NtRRThr = 6;
    private int NtDyn = 7;
    private int NtSigPK = 61;
    private ArrayList<Double> tSig_fifo = new ArrayList<>();
    private ArrayList<Double> Dyn = new ArrayList<>();
    private ArrayList<Double> tSigPK_fifo = new ArrayList<>();
    private double max_dSig = -1000000.0d;
    private int dSig_cnt = 0;
    private int sFs = 100;
    private boolean isDataReady = false;
    private int stua = 0;
    private int sFsX60 = PlaybackException.ERROR_CODE_DRM_UNSPECIFIED;
    private int th_RL = 42;
    private int th_RH = 200;
    private int th_RiH = 142;
    private int th_RiL = 30;
    private int NrecRR = 6;
    private ArrayList<Integer> recRR = new ArrayList<>();
    private ArrayList<Double> recRRY = new ArrayList<>();
    private ArrayList<Integer> RRPeriodArr = new ArrayList<>();
    private int cut_sec = 2;
    private int ini_p = 1;
    private int thresholdCnt = 0;
    private double diffMax = -1000000.0d;
    private double a1 = 0.2d;
    private double a2 = 0.3d;
    private double a3 = 0.125d;
    private int Npeak = 1;
    private boolean isMeanReady = false;
    private int readySum = 0;
    private int readCnt = 0;
    private double dMeans = 0.0d;
    private int cPoint1 = -10000;
    private int cPoint2 = -30000;
    private int cPoint3 = -20000;

    public EcgRealTimeHrAnalysis() {
        double d2 = this.sFs;
        this.pTh23 = (int) (0.13d * d2);
        this.pTh12 = (int) (d2 * 0.03d);
        this.pointCnt = 0;
        this.isCalAll = false;
    }

    public int calRealTimeHR(short s2) {
        if (!this.isMeanReady) {
            int i2 = this.readySum + s2;
            this.readySum = i2;
            int i3 = this.readCnt + 1;
            this.readCnt = i3;
            int i4 = this.sFs * 2;
            if (i3 != i4) {
                return 0;
            }
            this.dMeans = (i2 * 1.0d) / i4;
            this.readySum = 0;
            this.readCnt = 0;
            this.isMeanReady = true;
            return 0;
        }
        double d2 = this.dMeans;
        double d3 = s2 - d2;
        int i5 = this.readySum + s2;
        this.readySum = i5;
        int i6 = this.readCnt + 1;
        this.readCnt = i6;
        int i7 = this.sFs * 2;
        if (i6 == i7) {
            this.dMeans = (d2 * 0.3d) + (((i5 * 1.0d) / i7) * 0.7d);
            this.readCnt = 0;
            this.readySum = 0;
        }
        if (this.isStatisRWave) {
            this.RRPeriod++;
        }
        int i8 = this.mEcgState;
        int i9 = this.STATE_FOURTH;
        if (i8 == i9) {
            if (i8 != i9) {
                return 0;
            }
            if (this.isDataReady) {
                this.tSigPK_fifo.remove(0);
                this.tSigPK_fifo.add(Double.valueOf(d3));
                return utencase(this.tSigPK_fifo);
            }
            this.tSigPK_fifo.add(Double.valueOf(d3));
            if (this.tSigPK_fifo.size() != this.NtSigPK) {
                return 0;
            }
            int iUtencase = utencase(this.tSigPK_fifo);
            this.isDataReady = true;
            return iUtencase;
        }
        if (i8 == this.STATE_SECOND) {
            this.tSig_fifo.add(Double.valueOf(d3));
            int size = this.tSig_fifo.size();
            int i10 = this.NtSig;
            if (size != i10) {
                return 0;
            }
            this.mEcgState = this.STATE_THIRD;
            double dUtendo = utendo((ArrayList) this.tSig_fifo, i10);
            if (dUtendo > this.max_dSig) {
                this.max_dSig = dUtendo;
            }
            this.dSig_cnt++;
            return 0;
        }
        this.tSig_fifo.remove(0);
        this.tSig_fifo.add(Double.valueOf(d3));
        double dUtendo2 = utendo((ArrayList) this.tSig_fifo, this.NtSig);
        if (dUtendo2 > this.max_dSig) {
            this.max_dSig = dUtendo2;
        }
        int i11 = this.dSig_cnt + 1;
        this.dSig_cnt = i11;
        if (i11 != this.cut_sec * this.sFs) {
            return 0;
        }
        this.dSig_cnt = 0;
        this.Dyn.add(Double.valueOf(this.max_dSig));
        if (this.Dyn.size() != this.ini_p) {
            this.mEcgState = this.STATE_SECOND;
            this.tSig_fifo.clear();
            this.max_dSig = -1000000.0d;
            return 0;
        }
        this.mEcgState = this.STATE_FOURTH;
        double d4 = this.max_dSig;
        this.Dy = d4;
        this.f24935a = this.a1 * d4;
        this.f24936b = (this.a2 * d4) + 2.0d;
        this.f24937c = this.a3 * d4;
        double d5 = d4 * 0.10000000149011612d;
        this.f24938d = d5;
        this.f24939e = 2.0d + d5;
        this.f24940f = d5;
        this.tSigPK_fifo.addAll(this.tSig_fifo);
        return 0;
    }

    public void clearParameter() {
        this.isMeanReady = false;
        this.readySum = 0;
        this.readCnt = 0;
        this.dMeans = 0.0d;
        this.mEcgState = this.STATE_SECOND;
        this.dSig_cnt = 0;
        this.max_dSig = -1.0E8d;
        this.Dyn.clear();
        this.tSig_fifo.clear();
        this.tSigPK_fifo.clear();
        this.isDataReady = false;
        this.stua = 0;
        this.isStatisRWave = false;
        this.RRPeriod = 0;
        this.recRR.clear();
        this.recRRY.clear();
        this.RRPeriodArr.clear();
        this.thresholdCnt = 0;
        this.diffMax = -1.0E8d;
        this.Npeak = 1;
    }

    public int getAverageHR() {
        return utendo(this.recRR);
    }

    public int getHRV() {
        return utenif(this.recRR);
    }

    public final double utenbyte(ArrayList arrayList) {
        double dAbs = 0.0d;
        if (arrayList.size() < this.NrecRR) {
            double dAbs2 = 0.0d;
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                dAbs2 += Math.abs(((Double) arrayList.get(i2)).doubleValue());
            }
            if (arrayList.size() > 0) {
                return (dAbs2 * 1.0d) / arrayList.size();
            }
            return 0.0d;
        }
        int size = arrayList.size();
        while (true) {
            size--;
            int size2 = arrayList.size();
            int i3 = this.NrecRR;
            if (size < size2 - i3) {
                return (dAbs * 1.0d) / i3;
            }
            dAbs += Math.abs(((Double) arrayList.get(size)).doubleValue());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x007d, code lost:
    
        if (java.lang.Math.abs(r30.cPoint2 - r9) < r30.pTh23) goto L25;
     */
    /* JADX WARN: Removed duplicated region for block: B:69:0x027f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int utencase(java.util.ArrayList r31) {
        /*
            Method dump skipped, instructions count: 1055
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.utils.close.EcgRealTimeHrAnalysis.utencase(java.util.ArrayList):int");
    }

    public final int utendo(ArrayList arrayList) {
        int iIntValue = 0;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            iIntValue += ((Integer) arrayList.get(i2)).intValue();
        }
        if (arrayList.size() <= 0) {
            return 0;
        }
        return (this.sFs * 60) / (iIntValue / arrayList.size());
    }

    public final int utenfor(ArrayList arrayList) {
        int i2 = 0;
        double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
        for (int i3 = 1; i3 < arrayList.size(); i3++) {
            if (((Double) arrayList.get(i3)).doubleValue() > dDoubleValue) {
                dDoubleValue = ((Double) arrayList.get(i3)).doubleValue();
                i2 = i3;
            }
        }
        return i2;
    }

    public final int utenif(ArrayList arrayList) {
        int i2 = 0;
        for (int i3 = 1; i3 < arrayList.size(); i3++) {
            int iIntValue = ((Integer) arrayList.get(i3)).intValue() - ((Integer) arrayList.get(i3 - 1)).intValue();
            i2 += iIntValue * iIntValue;
        }
        if (arrayList.size() <= 1) {
            return 0;
        }
        int iSqrt = (((int) Math.sqrt(i2 / (arrayList.size() - 1))) * 1000) / this.sFs;
        return iSqrt > 110 ? ((((int) Math.sqrt(i2)) / (arrayList.size() - 1)) * 1000) / this.sFs : iSqrt;
    }

    public final double utenint(ArrayList arrayList) {
        double dDoubleValue = 0.0d;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            dDoubleValue += ((Double) arrayList.get(i2)).doubleValue();
        }
        return dDoubleValue;
    }

    public final double utennew(ArrayList arrayList) {
        if (arrayList.size() > 0) {
            return utenint(arrayList) / arrayList.size();
        }
        return 0.0d;
    }

    public final double utentry(ArrayList arrayList) {
        int i2;
        double d2;
        double size;
        int iIntValue = 0;
        if (arrayList.size() < this.NrecRR) {
            int iIntValue2 = 0;
            while (iIntValue < arrayList.size()) {
                iIntValue2 += ((Integer) arrayList.get(iIntValue)).intValue();
                iIntValue++;
            }
            if (arrayList.size() <= 0) {
                return 0.0d;
            }
            d2 = iIntValue2 * 1.0d;
            size = arrayList.size();
        } else {
            int size2 = arrayList.size();
            while (true) {
                size2--;
                int size3 = arrayList.size();
                i2 = this.NrecRR;
                if (size2 < size3 - i2) {
                    break;
                }
                iIntValue += ((Integer) arrayList.get(size2)).intValue();
            }
            d2 = iIntValue * 1.0d;
            size = i2;
        }
        return d2 / size;
    }

    public final ArrayList utendo(ArrayList arrayList, double d2, double d3) {
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            arrayList2.add(Double.valueOf(Math.exp((-((((Double) arrayList.get(i2)).doubleValue() - d2) * (((Double) arrayList.get(i2)).doubleValue() - d2))) / (2.0d * d3))));
        }
        return arrayList2;
    }

    public final double utenfor(ArrayList arrayList, double d2) {
        double dAbs = 0.0d;
        if (arrayList.size() < this.NrecRR) {
            double dAbs2 = 0.0d;
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                dAbs2 += (Math.abs(((Double) arrayList.get(i2)).doubleValue()) - d2) * (Math.abs(((Double) arrayList.get(i2)).doubleValue()) - d2);
            }
            if (arrayList.size() > 0) {
                return dAbs2 / arrayList.size();
            }
            return 0.0d;
        }
        int size = arrayList.size();
        while (true) {
            size--;
            int size2 = arrayList.size();
            int i3 = this.NrecRR;
            if (size < size2 - i3) {
                return dAbs / i3;
            }
            dAbs += (Math.abs(((Double) arrayList.get(size)).doubleValue()) - d2) * (Math.abs(((Double) arrayList.get(size)).doubleValue()) - d2);
        }
    }

    public final SigMaxMinInfo utenif(ArrayList arrayList, int i2, int i3) {
        double dPow = Math.pow(10.0d, 7.0d);
        int i4 = i2 + 1;
        int i5 = -1;
        while (i4 <= i3 - 1) {
            double dDoubleValue = ((Double) arrayList.get(i4)).doubleValue() - ((Double) arrayList.get(i4 - 1)).doubleValue();
            int i6 = i4 + 1;
            double dDoubleValue2 = ((Double) arrayList.get(i6)).doubleValue() - ((Double) arrayList.get(i4)).doubleValue();
            if (dDoubleValue <= 0.0d && dDoubleValue2 >= 0.0d && ((Double) arrayList.get(i4)).doubleValue() < dPow) {
                dPow = ((Double) arrayList.get(i4)).doubleValue();
                i5 = i4;
            }
            i4 = i6;
        }
        return new SigMaxMinInfo(dPow, i5);
    }

    public final ArrayList utendo(ArrayList arrayList, ArrayList arrayList2) {
        ArrayList arrayList3 = new ArrayList();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            arrayList3.add(Double.valueOf(((Double) arrayList.get(i2)).doubleValue() + ((Double) arrayList2.get(i2)).doubleValue()));
        }
        return arrayList3;
    }

    public final int utenif(ArrayList arrayList, boolean z2) {
        if (arrayList.size() <= 0) {
            return 0;
        }
        ArrayList arrayList2 = (!z2 && arrayList.size() > this.NtRRThr) ? new ArrayList(arrayList.subList(arrayList.size() - this.NtRRThr, arrayList.size())) : new ArrayList(arrayList);
        Collections.sort(arrayList2);
        int size = arrayList2.size();
        if (size % 2 == 1) {
            return ((Integer) arrayList2.get((size - 1) / 2)).intValue();
        }
        int i2 = size / 2;
        return (((Integer) arrayList2.get(i2 - 1)).intValue() + ((Integer) arrayList2.get(i2)).intValue()) / 2;
    }

    public final double utendo(ArrayList arrayList, int i2) {
        double dDoubleValue;
        int i3;
        if (i2 == this.NtSig) {
            dDoubleValue = ((((Double) arrayList.get(0)).doubleValue() * (-2.0d)) - ((Double) arrayList.get(1)).doubleValue()) + ((Double) arrayList.get(3)).doubleValue();
            i3 = 4;
        } else {
            if (i2 != this.NtSigPK) {
                return 0.0d;
            }
            dDoubleValue = ((((Double) arrayList.get(28)).doubleValue() * (-2.0d)) - ((Double) arrayList.get(29)).doubleValue()) + ((Double) arrayList.get(31)).doubleValue();
            i3 = 32;
        }
        return dDoubleValue + (((Double) arrayList.get(i3)).doubleValue() * 2.0d);
    }

    public final double utenif(ArrayList arrayList, double d2) {
        double dDoubleValue = 0.0d;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            dDoubleValue += (((Double) arrayList.get(i2)).doubleValue() - d2) * (((Double) arrayList.get(i2)).doubleValue() - d2);
        }
        if (arrayList.size() > 0) {
            return dDoubleValue / arrayList.size();
        }
        return 0.0d;
    }

    public final void utendo(double d2) {
        this.Dyn.add(0, Double.valueOf(d2));
        if (this.Dyn.size() > this.NtDyn) {
            this.Dyn.remove(r2.size() - 1);
        }
    }

    public final SigMaxMinInfo utendo(ArrayList arrayList, int i2, int i3) {
        double dDoubleValue = -Math.pow(10.0d, 7.0d);
        int i4 = i2 + 1;
        int i5 = -1;
        while (i4 <= i3 - 1) {
            double dDoubleValue2 = ((Double) arrayList.get(i4)).doubleValue() - ((Double) arrayList.get(i4 - 1)).doubleValue();
            int i6 = i4 + 1;
            double dDoubleValue3 = ((Double) arrayList.get(i6)).doubleValue() - ((Double) arrayList.get(i4)).doubleValue();
            if (dDoubleValue2 >= 0.0d && dDoubleValue3 <= 0.0d && ((Double) arrayList.get(i4)).doubleValue() > dDoubleValue) {
                dDoubleValue = ((Double) arrayList.get(i4)).doubleValue();
                i5 = i4;
            }
            i4 = i6;
        }
        return new SigMaxMinInfo(dDoubleValue, i5);
    }

    public final double utendo(ArrayList arrayList, boolean z2) {
        if (arrayList.size() <= 0) {
            return 0.0d;
        }
        ArrayList arrayList2 = (!z2 && arrayList.size() > this.NtRRThr) ? new ArrayList(arrayList.subList(arrayList.size() - this.NtRRThr, arrayList.size())) : new ArrayList(arrayList);
        Collections.sort(arrayList2);
        int size = arrayList2.size();
        if (size % 2 == 1) {
            return ((Double) arrayList2.get((size - 1) / 2)).doubleValue();
        }
        int i2 = size / 2;
        return ((((Double) arrayList2.get(i2 - 1)).doubleValue() + ((Double) arrayList2.get(i2)).doubleValue()) + 0.0d) / 2.0d;
    }

    public final double utendo(ArrayList arrayList, double d2) {
        int iIntValue = 0;
        if (arrayList.size() < this.NrecRR) {
            int iIntValue2 = 0;
            while (iIntValue < arrayList.size()) {
                iIntValue2 = (int) (iIntValue2 + ((((Integer) arrayList.get(iIntValue)).intValue() - d2) * (((Integer) arrayList.get(iIntValue)).intValue() - d2)));
                iIntValue++;
            }
            if (arrayList.size() > 0) {
                return (iIntValue2 * 1.0d) / arrayList.size();
            }
            return 0.0d;
        }
        int size = arrayList.size();
        while (true) {
            size--;
            int size2 = arrayList.size();
            int i2 = this.NrecRR;
            if (size < size2 - i2) {
                return (iIntValue * 1.0d) / i2;
            }
            iIntValue = (int) (iIntValue + ((((Integer) arrayList.get(size)).intValue() - d2) * (((Integer) arrayList.get(size)).intValue() - d2)));
        }
    }
}
