package com.kingsmith.aliiot;

/* loaded from: classes4.dex */
public class Properties {
    private Property<Double> BurnCalories;
    private Property<Integer> ButtonId;
    private Property<Integer> ChildLockSwitch;
    private Property<Integer> ConSpMode;
    private Property<Integer> ControlMode;
    private Property<Double> CurrentSpeed;
    private Property<Integer> HeartRate;
    private Property<Double> Max;
    private Property<Double> MaxW;
    private Property<Integer> PanelDisplay;
    private Property<Integer> RunningDistance;
    private Property<Integer> RunningSteps;
    private Property<Integer> RunningTotalTime;
    private Property<Double> StartSpeed;
    private Property<Integer> VelocitySensitivity;
    private Property<String> WIFI_AP_BSSID;
    private Property<String> _sys_device_pid;
    private Property<String> goal;
    private Property<Integer> gradient;
    private Property<Integer> handrail;
    private Property<Integer> initial;
    private Property<String> mcu_version;
    private Property<String> record;
    private Property<Integer> runState;
    private Property<Integer> spm;
    private Property<Integer> tutorial;
    private Property<Integer> unit;

    public static class Property<T> {
        private long time;
        private T value;

        public long getTime() {
            return this.time;
        }

        public T getValue() {
            return this.value;
        }

        public void setTime(long j2) {
            this.time = j2;
        }

        public void setValue(T t2) {
            this.value = t2;
        }

        public String toString() {
            return "Property{time=" + this.time + ", value=" + this.value + '}';
        }
    }

    public Property<Double> getBurnCalories() {
        return this.BurnCalories;
    }

    public Property<Integer> getButtonId() {
        return this.ButtonId;
    }

    public Property<Integer> getChildLockSwitch() {
        return this.ChildLockSwitch;
    }

    public Property<Integer> getConSpMode() {
        return this.ConSpMode;
    }

    public Property<Integer> getControlMode() {
        return this.ControlMode;
    }

    public Property<Double> getCurrentSpeed() {
        return this.CurrentSpeed;
    }

    public Property<String> getGoal() {
        return this.goal;
    }

    public Property<Integer> getGradient() {
        return this.gradient;
    }

    public Property<Integer> getHandrail() {
        return this.handrail;
    }

    public Property<Integer> getHeartRate() {
        return this.HeartRate;
    }

    public Property<Integer> getInitial() {
        return this.initial;
    }

    public Property<Double> getMax() {
        return this.Max;
    }

    public Property<Double> getMaxW() {
        return this.MaxW;
    }

    public Property<String> getMcu_version() {
        return this.mcu_version;
    }

    public Property<Integer> getPanelDisplay() {
        return this.PanelDisplay;
    }

    public Property<String> getRecord() {
        return this.record;
    }

    public Property<Integer> getRunState() {
        return this.runState;
    }

    public Property<Integer> getRunningDistance() {
        return this.RunningDistance;
    }

    public Property<Integer> getRunningSteps() {
        return this.RunningSteps;
    }

    public Property<Integer> getRunningTotalTime() {
        return this.RunningTotalTime;
    }

    public Property<Integer> getSpm() {
        return this.spm;
    }

    public Property<Double> getStartSpeed() {
        return this.StartSpeed;
    }

    public Property<Integer> getTutorial() {
        return this.tutorial;
    }

    public Property<Integer> getUnit() {
        return this.unit;
    }

    public Property<Integer> getVelocitySensitivity() {
        return this.VelocitySensitivity;
    }

    public Property<String> getWIFI_AP_BSSID() {
        return this.WIFI_AP_BSSID;
    }

    public Property<String> get_sys_device_pid() {
        return this._sys_device_pid;
    }

    public void setBurnCalories(Property<Double> property) {
        this.BurnCalories = property;
    }

    public void setButtonId(Property<Integer> property) {
        this.ButtonId = property;
    }

    public void setChildLockSwitch(Property<Integer> property) {
        this.ChildLockSwitch = property;
    }

    public void setConSpMode(Property<Integer> property) {
        this.ConSpMode = property;
    }

    public void setControlMode(Property<Integer> property) {
        this.ControlMode = property;
    }

    public void setCurrentSpeed(Property<Double> property) {
        this.CurrentSpeed = property;
    }

    public void setGoal(Property<String> property) {
        this.goal = property;
    }

    public void setGradient(Property<Integer> property) {
        this.gradient = property;
    }

    public void setHandrail(Property<Integer> property) {
        this.handrail = property;
    }

    public void setHeartRate(Property<Integer> property) {
        this.HeartRate = property;
    }

    public void setInitial(Property<Integer> property) {
        this.initial = property;
    }

    public void setMax(Property<Double> property) {
        this.Max = property;
    }

    public void setMaxW(Property<Double> property) {
        this.MaxW = property;
    }

    public void setMcu_version(Property<String> property) {
        this.mcu_version = property;
    }

    public void setPanelDisplay(Property<Integer> property) {
        this.PanelDisplay = property;
    }

    public void setRecord(Property<String> property) {
        this.record = property;
    }

    public void setRunState(Property<Integer> property) {
        this.runState = property;
    }

    public void setRunningDistance(Property<Integer> property) {
        this.RunningDistance = property;
    }

    public void setRunningSteps(Property<Integer> property) {
        this.RunningSteps = property;
    }

    public void setRunningTotalTime(Property<Integer> property) {
        this.RunningTotalTime = property;
    }

    public void setSpm(Property<Integer> property) {
        this.spm = property;
    }

    public void setStartSpeed(Property<Double> property) {
        this.StartSpeed = property;
    }

    public void setTutorial(Property<Integer> property) {
        this.tutorial = property;
    }

    public void setUnit(Property<Integer> property) {
        this.unit = property;
    }

    public void setVelocitySensitivity(Property<Integer> property) {
        this.VelocitySensitivity = property;
    }

    public void setWIFI_AP_BSSID(Property<String> property) {
        this.WIFI_AP_BSSID = property;
    }

    public void set_sys_device_pid(Property<String> property) {
        this._sys_device_pid = property;
    }

    public String toString() {
        return "Properties{RunningSteps=" + this.RunningSteps + ", BurnCalories=" + this.BurnCalories + ", CurrentSpeed=" + this.CurrentSpeed + ", RunningDistance=" + this.RunningDistance + ", RunningTotalTime=" + this.RunningTotalTime + ", HeartRate=" + this.HeartRate + ", gradient=" + this.gradient + ", ChildLockSwitch=" + this.ChildLockSwitch + ", ConSpMode=" + this.ConSpMode + ", PanelDisplay=" + this.PanelDisplay + ", Max=" + this.Max + ", MaxW=" + this.MaxW + ", ControlMode=" + this.ControlMode + ", VelocitySensitivity=" + this.VelocitySensitivity + ", StartSpeed=" + this.StartSpeed + ", goal=" + this.goal + ", runState=" + this.runState + ", WIFI_AP_BSSID=" + this.WIFI_AP_BSSID + ", initial=" + this.initial + ", mcu_version=" + this.mcu_version + ", unit=" + this.unit + '}';
    }
}
