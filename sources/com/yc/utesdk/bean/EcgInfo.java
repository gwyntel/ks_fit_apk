package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class EcgInfo implements Comparable<EcgInfo> {
    private int bodyAge;
    private boolean bodyGender;
    private int bodyHeight;
    private float bodyWeight;
    private String calendar;
    private String calendarTime;
    private int ecgAverageRate;
    private int ecgFatigueIndex;
    private int ecgHRV;
    private String ecgLabel;
    private int ecgRhythm;
    private int ecgRiskLevel;
    private String ecgSamplingData;
    private int ecgStrength;
    private String realEcgValueArray;
    private int startTime;
    private int totalCount;
    private int whichPerson;

    public EcgInfo() {
    }

    @Override // java.lang.Comparable
    public int compareTo(EcgInfo ecgInfo) {
        return ecgInfo.getCalendar().compareTo(getCalendar());
    }

    public int getBodyAge() {
        return this.bodyAge;
    }

    public int getBodyHeight() {
        return this.bodyHeight;
    }

    public float getBodyWeight() {
        return this.bodyWeight;
    }

    public String getCalendar() {
        return this.calendar;
    }

    public String getCalendarTime() {
        return this.calendarTime;
    }

    public int getEcgAverageRate() {
        return this.ecgAverageRate;
    }

    public int getEcgFatigueIndex() {
        return this.ecgFatigueIndex;
    }

    public int getEcgHRV() {
        return this.ecgHRV;
    }

    public String getEcgLabel() {
        return this.ecgLabel;
    }

    public int getEcgRhythm() {
        return this.ecgRhythm;
    }

    public int getEcgRiskLevel() {
        return this.ecgRiskLevel;
    }

    public String getEcgSamplingData() {
        return this.ecgSamplingData;
    }

    public int getEcgStrength() {
        return this.ecgStrength;
    }

    public String getRealEcgValueArray() {
        return this.realEcgValueArray;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public int getWhichPerson() {
        return this.whichPerson;
    }

    public boolean isBodyGender() {
        return this.bodyGender;
    }

    public void setBodyAge(int i2) {
        this.bodyAge = i2;
    }

    public void setBodyGender(boolean z2) {
        this.bodyGender = z2;
    }

    public void setBodyHeight(int i2) {
        this.bodyHeight = i2;
    }

    public void setBodyWeight(float f2) {
        this.bodyWeight = f2;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setCalendarTime(String str) {
        this.calendarTime = str;
    }

    public void setEcgAverageRate(int i2) {
        this.ecgAverageRate = i2;
    }

    public void setEcgFatigueIndex(int i2) {
        this.ecgFatigueIndex = i2;
    }

    public void setEcgHRV(int i2) {
        this.ecgHRV = i2;
    }

    public void setEcgLabel(String str) {
        this.ecgLabel = str;
    }

    public void setEcgRhythm(int i2) {
        this.ecgRhythm = i2;
    }

    public void setEcgRiskLevel(int i2) {
        this.ecgRiskLevel = i2;
    }

    public void setEcgSamplingData(String str) {
        this.ecgSamplingData = str;
    }

    public void setEcgStrength(int i2) {
        this.ecgStrength = i2;
    }

    public void setRealEcgValueArray(String str) {
        this.realEcgValueArray = str;
    }

    public void setStartTime(int i2) {
        this.startTime = i2;
    }

    public void setTotalCount(int i2) {
        this.totalCount = i2;
    }

    public void setWhichPerson(int i2) {
        this.whichPerson = i2;
    }

    public EcgInfo(EcgInfo ecgInfo, String str) {
        if (ecgInfo != null) {
            setCalendar(ecgInfo.getCalendar());
            setCalendarTime(ecgInfo.getCalendarTime());
            setStartTime(ecgInfo.getStartTime());
            setEcgAverageRate(ecgInfo.getEcgAverageRate());
            setEcgHRV(ecgInfo.getEcgHRV());
            setEcgStrength(ecgInfo.getEcgStrength());
            setEcgRiskLevel(ecgInfo.getEcgRiskLevel());
            setEcgFatigueIndex(ecgInfo.getEcgFatigueIndex());
            setTotalCount(ecgInfo.getTotalCount());
            setWhichPerson(ecgInfo.getWhichPerson());
            setEcgLabel(ecgInfo.getEcgLabel());
            setEcgRhythm(ecgInfo.getEcgRhythm());
            setRealEcgValueArray(ecgInfo.getRealEcgValueArray());
        }
        setEcgSamplingData(str);
    }

    public EcgInfo(String str, String str2) {
        setCalendar(str);
        setCalendarTime(str2);
    }

    public EcgInfo(String str, String str2, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, String str3) {
        setCalendar(str);
        setCalendarTime(str2);
        setStartTime(i2);
        setEcgAverageRate(i3);
        setEcgHRV(i4);
        setEcgStrength(i5);
        setEcgRiskLevel(i6);
        setEcgFatigueIndex(i7);
        setTotalCount(i8);
        setEcgRhythm(i9);
        setRealEcgValueArray(str3);
    }

    public EcgInfo(String str, String str2, int i2, int i3, int i4, int i5, int i6, int i7, String str3, int i8, int i9, int i10, String str4, String str5) {
        setCalendar(str);
        setCalendarTime(str2);
        setStartTime(i2);
        setEcgAverageRate(i3);
        setEcgHRV(i4);
        setEcgStrength(i5);
        setEcgRiskLevel(i6);
        setEcgFatigueIndex(i7);
        setEcgSamplingData(str3);
        setTotalCount(i8);
        setWhichPerson(i9);
        setEcgLabel(str4);
        setEcgRhythm(i10);
        setRealEcgValueArray(str5);
    }
}
