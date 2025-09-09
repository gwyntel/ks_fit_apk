package com.kingsmith.aliiot;

import com.kingsmith.aliiot.Properties;

/* loaded from: classes4.dex */
public class MotionData {
    private Properties.Property<Double> BurnCalories;
    private Properties.Property<Integer> ButtonId;
    private Properties.Property<Integer> ChildLockSwitch;
    private Properties.Property<Integer> ControlMode;
    private Properties.Property<Double> CurrentSpeed;
    private Properties.Property<Integer> RunningDistance;
    private Properties.Property<Integer> RunningSteps;
    private Properties.Property<Integer> RunningTotalTime;
    private Properties.Property<String> _sys_device_pid;
    private Properties.Property<Integer> handrail;
    private Properties.Property<String> record;
    private Properties.Property<Integer> runState;

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

    public Properties.Property<Double> getBurnCalories() {
        return this.BurnCalories;
    }

    public Properties.Property<Integer> getButtonId() {
        return this.ButtonId;
    }

    public Properties.Property<Integer> getChildLockSwitch() {
        return this.ChildLockSwitch;
    }

    public Properties.Property<Integer> getControlMode() {
        return this.ControlMode;
    }

    public Properties.Property<Double> getCurrentSpeed() {
        return this.CurrentSpeed;
    }

    public Properties.Property<Integer> getHandrail() {
        return this.handrail;
    }

    public Properties.Property<String> getRecord() {
        return this.record;
    }

    public Properties.Property<Integer> getRunState() {
        return this.runState;
    }

    public Properties.Property<Integer> getRunningDistance() {
        return this.RunningDistance;
    }

    public Properties.Property<Integer> getRunningSteps() {
        return this.RunningSteps;
    }

    public Properties.Property<Integer> getRunningTotalTime() {
        return this.RunningTotalTime;
    }

    public Properties.Property<String> get_sys_device_pid() {
        return this._sys_device_pid;
    }

    public void setBurnCalories(Properties.Property<Double> property) {
        this.BurnCalories = property;
    }

    public void setButtonId(Properties.Property<Integer> property) {
        this.ButtonId = property;
    }

    public void setChildLockSwitch(Properties.Property<Integer> property) {
        this.ChildLockSwitch = property;
    }

    public void setControlMode(Properties.Property<Integer> property) {
        this.ControlMode = property;
    }

    public void setCurrentSpeed(Properties.Property<Double> property) {
        this.CurrentSpeed = property;
    }

    public void setHandrail(Properties.Property<Integer> property) {
        this.handrail = property;
    }

    public void setRecord(Properties.Property<String> property) {
        this.record = property;
    }

    public void setRunState(Properties.Property<Integer> property) {
        this.runState = property;
    }

    public void setRunningDistance(Properties.Property<Integer> property) {
        this.RunningDistance = property;
    }

    public void setRunningSteps(Properties.Property<Integer> property) {
        this.RunningSteps = property;
    }

    public void setRunningTotalTime(Properties.Property<Integer> property) {
        this.RunningTotalTime = property;
    }

    public void set_sys_device_pid(Properties.Property<String> property) {
        this._sys_device_pid = property;
    }

    public String toString() {
        return "MotionData{RunningSteps=" + this.RunningSteps + ", BurnCalories=" + this.BurnCalories + ", CurrentSpeed=" + this.CurrentSpeed + ", RunningDistance=" + this.RunningDistance + ", RunningTotalTime=" + this.RunningTotalTime + ", ButtonId=" + this.ButtonId + ", runState=" + this.runState + ", ControlMode=" + this.ControlMode + ", ChildLockSwitch=" + this.ChildLockSwitch + ", handrail=" + this.handrail + ", _sys_device_pid=" + this._sys_device_pid + ", record=" + this.record + '}';
    }
}
