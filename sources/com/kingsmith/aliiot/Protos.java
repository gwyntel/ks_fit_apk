package com.kingsmith.aliiot;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public final class Protos {

    /* renamed from: com.kingsmith.aliiot.Protos$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static final class AProperties extends GeneratedMessageLite<AProperties, Builder> implements APropertiesOrBuilder {
        public static final int BURN_CALORIES_FIELD_NUMBER = 2;
        public static final int BUTTON_ID_FIELD_NUMBER = 18;
        public static final int CHILD_LOCK_SWITCH_FIELD_NUMBER = 26;
        public static final int CODE_FIELD_NUMBER = 27;
        public static final int CONTROL_MODE_FIELD_NUMBER = 9;
        public static final int CON_SP_MODE_FIELD_NUMBER = 6;
        public static final int CURRENT_SPEED_FIELD_NUMBER = 3;
        private static final AProperties DEFAULT_INSTANCE;
        public static final int GOAL_FIELD_NUMBER = 12;
        public static final int GRADIENT_FIELD_NUMBER = 28;
        public static final int HANDRAIL_FIELD_NUMBER = 22;
        public static final int HEART_RATE_FIELD_NUMBER = 16;
        public static final int INITIAL_FIELD_NUMBER = 23;
        public static final int MAX_FIELD_NUMBER = 8;
        public static final int MAX_W_FIELD_NUMBER = 19;
        public static final int MCU_VERSION_FIELD_NUMBER = 17;
        public static final int PANEL_DISPLAY_FIELD_NUMBER = 7;
        private static volatile Parser<AProperties> PARSER = null;
        public static final int QUICK_SPEED_FIELD_NUMBER = 29;
        public static final int RECORD_FIELD_NUMBER = 20;
        public static final int RUNNING_DISTANCE_FIELD_NUMBER = 4;
        public static final int RUNNING_STEPS_FIELD_NUMBER = 1;
        public static final int RUNNING_TOTAL_TIME_FIELD_NUMBER = 5;
        public static final int RUN_STATE_FIELD_NUMBER = 13;
        public static final int SPM_FIELD_NUMBER = 14;
        public static final int START_SPEED_FIELD_NUMBER = 11;
        public static final int SYS_DEVICE_PID_FIELD_NUMBER = 25;
        public static final int TUTORIAL_FIELD_NUMBER = 21;
        public static final int UNIT_FIELD_NUMBER = 24;
        public static final int VELOCITY_SENSITIVITY_FIELD_NUMBER = 10;
        public static final int WIFI_AP_BASSID_FIELD_NUMBER = 15;
        private Property burnCalories_;
        private Property buttonId_;
        private Property childLockSwitch_;
        private int code_;
        private Property conSpMode_;
        private Property controlMode_;
        private Property currentSpeed_;
        private Property goal_;
        private Property gradient_;
        private Property handrail_;
        private Property heartRate_;
        private Property initial_;
        private Property maxW_;
        private Property max_;
        private Property mcuVersion_;
        private Property panelDisplay_;
        private Property quickSpeed_;
        private Property record_;
        private Property runState_;
        private Property runningDistance_;
        private Property runningSteps_;
        private Property runningTotalTime_;
        private Property spm_;
        private Property startSpeed_;
        private Property sysDevicePid_;
        private Property tutorial_;
        private Property unit_;
        private Property velocitySensitivity_;
        private Property wifiApBassid_;

        public static final class Builder extends GeneratedMessageLite.Builder<AProperties, Builder> implements APropertiesOrBuilder {
            public Builder clearBurnCalories() {
                copyOnWrite();
                ((AProperties) this.instance).clearBurnCalories();
                return this;
            }

            public Builder clearButtonId() {
                copyOnWrite();
                ((AProperties) this.instance).clearButtonId();
                return this;
            }

            public Builder clearChildLockSwitch() {
                copyOnWrite();
                ((AProperties) this.instance).clearChildLockSwitch();
                return this;
            }

            public Builder clearCode() {
                copyOnWrite();
                ((AProperties) this.instance).clearCode();
                return this;
            }

            public Builder clearConSpMode() {
                copyOnWrite();
                ((AProperties) this.instance).clearConSpMode();
                return this;
            }

            public Builder clearControlMode() {
                copyOnWrite();
                ((AProperties) this.instance).clearControlMode();
                return this;
            }

            public Builder clearCurrentSpeed() {
                copyOnWrite();
                ((AProperties) this.instance).clearCurrentSpeed();
                return this;
            }

            public Builder clearGoal() {
                copyOnWrite();
                ((AProperties) this.instance).clearGoal();
                return this;
            }

            public Builder clearGradient() {
                copyOnWrite();
                ((AProperties) this.instance).clearGradient();
                return this;
            }

            public Builder clearHandrail() {
                copyOnWrite();
                ((AProperties) this.instance).clearHandrail();
                return this;
            }

            public Builder clearHeartRate() {
                copyOnWrite();
                ((AProperties) this.instance).clearHeartRate();
                return this;
            }

            public Builder clearInitial() {
                copyOnWrite();
                ((AProperties) this.instance).clearInitial();
                return this;
            }

            public Builder clearMax() {
                copyOnWrite();
                ((AProperties) this.instance).clearMax();
                return this;
            }

            public Builder clearMaxW() {
                copyOnWrite();
                ((AProperties) this.instance).clearMaxW();
                return this;
            }

            public Builder clearMcuVersion() {
                copyOnWrite();
                ((AProperties) this.instance).clearMcuVersion();
                return this;
            }

            public Builder clearPanelDisplay() {
                copyOnWrite();
                ((AProperties) this.instance).clearPanelDisplay();
                return this;
            }

            public Builder clearQuickSpeed() {
                copyOnWrite();
                ((AProperties) this.instance).clearQuickSpeed();
                return this;
            }

            public Builder clearRecord() {
                copyOnWrite();
                ((AProperties) this.instance).clearRecord();
                return this;
            }

            public Builder clearRunState() {
                copyOnWrite();
                ((AProperties) this.instance).clearRunState();
                return this;
            }

            public Builder clearRunningDistance() {
                copyOnWrite();
                ((AProperties) this.instance).clearRunningDistance();
                return this;
            }

            public Builder clearRunningSteps() {
                copyOnWrite();
                ((AProperties) this.instance).clearRunningSteps();
                return this;
            }

            public Builder clearRunningTotalTime() {
                copyOnWrite();
                ((AProperties) this.instance).clearRunningTotalTime();
                return this;
            }

            public Builder clearSpm() {
                copyOnWrite();
                ((AProperties) this.instance).clearSpm();
                return this;
            }

            public Builder clearStartSpeed() {
                copyOnWrite();
                ((AProperties) this.instance).clearStartSpeed();
                return this;
            }

            public Builder clearSysDevicePid() {
                copyOnWrite();
                ((AProperties) this.instance).clearSysDevicePid();
                return this;
            }

            public Builder clearTutorial() {
                copyOnWrite();
                ((AProperties) this.instance).clearTutorial();
                return this;
            }

            public Builder clearUnit() {
                copyOnWrite();
                ((AProperties) this.instance).clearUnit();
                return this;
            }

            public Builder clearVelocitySensitivity() {
                copyOnWrite();
                ((AProperties) this.instance).clearVelocitySensitivity();
                return this;
            }

            public Builder clearWifiApBassid() {
                copyOnWrite();
                ((AProperties) this.instance).clearWifiApBassid();
                return this;
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getBurnCalories() {
                return ((AProperties) this.instance).getBurnCalories();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getButtonId() {
                return ((AProperties) this.instance).getButtonId();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getChildLockSwitch() {
                return ((AProperties) this.instance).getChildLockSwitch();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public int getCode() {
                return ((AProperties) this.instance).getCode();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getConSpMode() {
                return ((AProperties) this.instance).getConSpMode();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getControlMode() {
                return ((AProperties) this.instance).getControlMode();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getCurrentSpeed() {
                return ((AProperties) this.instance).getCurrentSpeed();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getGoal() {
                return ((AProperties) this.instance).getGoal();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getGradient() {
                return ((AProperties) this.instance).getGradient();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getHandrail() {
                return ((AProperties) this.instance).getHandrail();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getHeartRate() {
                return ((AProperties) this.instance).getHeartRate();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getInitial() {
                return ((AProperties) this.instance).getInitial();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getMax() {
                return ((AProperties) this.instance).getMax();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getMaxW() {
                return ((AProperties) this.instance).getMaxW();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getMcuVersion() {
                return ((AProperties) this.instance).getMcuVersion();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getPanelDisplay() {
                return ((AProperties) this.instance).getPanelDisplay();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getQuickSpeed() {
                return ((AProperties) this.instance).getQuickSpeed();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getRecord() {
                return ((AProperties) this.instance).getRecord();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getRunState() {
                return ((AProperties) this.instance).getRunState();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getRunningDistance() {
                return ((AProperties) this.instance).getRunningDistance();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getRunningSteps() {
                return ((AProperties) this.instance).getRunningSteps();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getRunningTotalTime() {
                return ((AProperties) this.instance).getRunningTotalTime();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getSpm() {
                return ((AProperties) this.instance).getSpm();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getStartSpeed() {
                return ((AProperties) this.instance).getStartSpeed();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getSysDevicePid() {
                return ((AProperties) this.instance).getSysDevicePid();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getTutorial() {
                return ((AProperties) this.instance).getTutorial();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getUnit() {
                return ((AProperties) this.instance).getUnit();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getVelocitySensitivity() {
                return ((AProperties) this.instance).getVelocitySensitivity();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public Property getWifiApBassid() {
                return ((AProperties) this.instance).getWifiApBassid();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasBurnCalories() {
                return ((AProperties) this.instance).hasBurnCalories();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasButtonId() {
                return ((AProperties) this.instance).hasButtonId();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasChildLockSwitch() {
                return ((AProperties) this.instance).hasChildLockSwitch();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasConSpMode() {
                return ((AProperties) this.instance).hasConSpMode();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasControlMode() {
                return ((AProperties) this.instance).hasControlMode();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasCurrentSpeed() {
                return ((AProperties) this.instance).hasCurrentSpeed();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasGoal() {
                return ((AProperties) this.instance).hasGoal();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasGradient() {
                return ((AProperties) this.instance).hasGradient();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasHandrail() {
                return ((AProperties) this.instance).hasHandrail();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasHeartRate() {
                return ((AProperties) this.instance).hasHeartRate();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasInitial() {
                return ((AProperties) this.instance).hasInitial();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasMax() {
                return ((AProperties) this.instance).hasMax();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasMaxW() {
                return ((AProperties) this.instance).hasMaxW();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasMcuVersion() {
                return ((AProperties) this.instance).hasMcuVersion();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasPanelDisplay() {
                return ((AProperties) this.instance).hasPanelDisplay();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasQuickSpeed() {
                return ((AProperties) this.instance).hasQuickSpeed();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasRecord() {
                return ((AProperties) this.instance).hasRecord();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasRunState() {
                return ((AProperties) this.instance).hasRunState();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasRunningDistance() {
                return ((AProperties) this.instance).hasRunningDistance();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasRunningSteps() {
                return ((AProperties) this.instance).hasRunningSteps();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasRunningTotalTime() {
                return ((AProperties) this.instance).hasRunningTotalTime();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasSpm() {
                return ((AProperties) this.instance).hasSpm();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasStartSpeed() {
                return ((AProperties) this.instance).hasStartSpeed();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasSysDevicePid() {
                return ((AProperties) this.instance).hasSysDevicePid();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasTutorial() {
                return ((AProperties) this.instance).hasTutorial();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasUnit() {
                return ((AProperties) this.instance).hasUnit();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasVelocitySensitivity() {
                return ((AProperties) this.instance).hasVelocitySensitivity();
            }

            @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
            public boolean hasWifiApBassid() {
                return ((AProperties) this.instance).hasWifiApBassid();
            }

            public Builder mergeBurnCalories(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeBurnCalories(property);
                return this;
            }

            public Builder mergeButtonId(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeButtonId(property);
                return this;
            }

            public Builder mergeChildLockSwitch(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeChildLockSwitch(property);
                return this;
            }

            public Builder mergeConSpMode(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeConSpMode(property);
                return this;
            }

            public Builder mergeControlMode(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeControlMode(property);
                return this;
            }

            public Builder mergeCurrentSpeed(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeCurrentSpeed(property);
                return this;
            }

            public Builder mergeGoal(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeGoal(property);
                return this;
            }

            public Builder mergeGradient(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeGradient(property);
                return this;
            }

            public Builder mergeHandrail(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeHandrail(property);
                return this;
            }

            public Builder mergeHeartRate(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeHeartRate(property);
                return this;
            }

            public Builder mergeInitial(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeInitial(property);
                return this;
            }

            public Builder mergeMax(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeMax(property);
                return this;
            }

            public Builder mergeMaxW(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeMaxW(property);
                return this;
            }

            public Builder mergeMcuVersion(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeMcuVersion(property);
                return this;
            }

            public Builder mergePanelDisplay(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergePanelDisplay(property);
                return this;
            }

            public Builder mergeQuickSpeed(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeQuickSpeed(property);
                return this;
            }

            public Builder mergeRecord(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeRecord(property);
                return this;
            }

            public Builder mergeRunState(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeRunState(property);
                return this;
            }

            public Builder mergeRunningDistance(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeRunningDistance(property);
                return this;
            }

            public Builder mergeRunningSteps(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeRunningSteps(property);
                return this;
            }

            public Builder mergeRunningTotalTime(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeRunningTotalTime(property);
                return this;
            }

            public Builder mergeSpm(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeSpm(property);
                return this;
            }

            public Builder mergeStartSpeed(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeStartSpeed(property);
                return this;
            }

            public Builder mergeSysDevicePid(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeSysDevicePid(property);
                return this;
            }

            public Builder mergeTutorial(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeTutorial(property);
                return this;
            }

            public Builder mergeUnit(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeUnit(property);
                return this;
            }

            public Builder mergeVelocitySensitivity(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeVelocitySensitivity(property);
                return this;
            }

            public Builder mergeWifiApBassid(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).mergeWifiApBassid(property);
                return this;
            }

            public Builder setBurnCalories(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setBurnCalories(property);
                return this;
            }

            public Builder setButtonId(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setButtonId(property);
                return this;
            }

            public Builder setChildLockSwitch(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setChildLockSwitch(property);
                return this;
            }

            public Builder setCode(int i2) {
                copyOnWrite();
                ((AProperties) this.instance).setCode(i2);
                return this;
            }

            public Builder setConSpMode(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setConSpMode(property);
                return this;
            }

            public Builder setControlMode(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setControlMode(property);
                return this;
            }

            public Builder setCurrentSpeed(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setCurrentSpeed(property);
                return this;
            }

            public Builder setGoal(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setGoal(property);
                return this;
            }

            public Builder setGradient(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setGradient(property);
                return this;
            }

            public Builder setHandrail(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setHandrail(property);
                return this;
            }

            public Builder setHeartRate(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setHeartRate(property);
                return this;
            }

            public Builder setInitial(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setInitial(property);
                return this;
            }

            public Builder setMax(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setMax(property);
                return this;
            }

            public Builder setMaxW(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setMaxW(property);
                return this;
            }

            public Builder setMcuVersion(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setMcuVersion(property);
                return this;
            }

            public Builder setPanelDisplay(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setPanelDisplay(property);
                return this;
            }

            public Builder setQuickSpeed(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setQuickSpeed(property);
                return this;
            }

            public Builder setRecord(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setRecord(property);
                return this;
            }

            public Builder setRunState(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setRunState(property);
                return this;
            }

            public Builder setRunningDistance(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setRunningDistance(property);
                return this;
            }

            public Builder setRunningSteps(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setRunningSteps(property);
                return this;
            }

            public Builder setRunningTotalTime(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setRunningTotalTime(property);
                return this;
            }

            public Builder setSpm(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setSpm(property);
                return this;
            }

            public Builder setStartSpeed(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setStartSpeed(property);
                return this;
            }

            public Builder setSysDevicePid(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setSysDevicePid(property);
                return this;
            }

            public Builder setTutorial(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setTutorial(property);
                return this;
            }

            public Builder setUnit(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setUnit(property);
                return this;
            }

            public Builder setVelocitySensitivity(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setVelocitySensitivity(property);
                return this;
            }

            public Builder setWifiApBassid(Property property) {
                copyOnWrite();
                ((AProperties) this.instance).setWifiApBassid(property);
                return this;
            }

            private Builder() {
                super(AProperties.DEFAULT_INSTANCE);
            }

            public Builder setBurnCalories(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setBurnCalories(builder.build());
                return this;
            }

            public Builder setButtonId(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setButtonId(builder.build());
                return this;
            }

            public Builder setChildLockSwitch(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setChildLockSwitch(builder.build());
                return this;
            }

            public Builder setConSpMode(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setConSpMode(builder.build());
                return this;
            }

            public Builder setControlMode(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setControlMode(builder.build());
                return this;
            }

            public Builder setCurrentSpeed(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setCurrentSpeed(builder.build());
                return this;
            }

            public Builder setGoal(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setGoal(builder.build());
                return this;
            }

            public Builder setGradient(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setGradient(builder.build());
                return this;
            }

            public Builder setHandrail(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setHandrail(builder.build());
                return this;
            }

            public Builder setHeartRate(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setHeartRate(builder.build());
                return this;
            }

            public Builder setInitial(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setInitial(builder.build());
                return this;
            }

            public Builder setMax(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setMax(builder.build());
                return this;
            }

            public Builder setMaxW(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setMaxW(builder.build());
                return this;
            }

            public Builder setMcuVersion(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setMcuVersion(builder.build());
                return this;
            }

            public Builder setPanelDisplay(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setPanelDisplay(builder.build());
                return this;
            }

            public Builder setQuickSpeed(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setQuickSpeed(builder.build());
                return this;
            }

            public Builder setRecord(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setRecord(builder.build());
                return this;
            }

            public Builder setRunState(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setRunState(builder.build());
                return this;
            }

            public Builder setRunningDistance(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setRunningDistance(builder.build());
                return this;
            }

            public Builder setRunningSteps(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setRunningSteps(builder.build());
                return this;
            }

            public Builder setRunningTotalTime(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setRunningTotalTime(builder.build());
                return this;
            }

            public Builder setSpm(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setSpm(builder.build());
                return this;
            }

            public Builder setStartSpeed(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setStartSpeed(builder.build());
                return this;
            }

            public Builder setSysDevicePid(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setSysDevicePid(builder.build());
                return this;
            }

            public Builder setTutorial(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setTutorial(builder.build());
                return this;
            }

            public Builder setUnit(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setUnit(builder.build());
                return this;
            }

            public Builder setVelocitySensitivity(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setVelocitySensitivity(builder.build());
                return this;
            }

            public Builder setWifiApBassid(Property.Builder builder) {
                copyOnWrite();
                ((AProperties) this.instance).setWifiApBassid(builder.build());
                return this;
            }
        }

        static {
            AProperties aProperties = new AProperties();
            DEFAULT_INSTANCE = aProperties;
            GeneratedMessageLite.registerDefaultInstance(AProperties.class, aProperties);
        }

        private AProperties() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearBurnCalories() {
            this.burnCalories_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearButtonId() {
            this.buttonId_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearChildLockSwitch() {
            this.childLockSwitch_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearCode() {
            this.code_ = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearConSpMode() {
            this.conSpMode_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearControlMode() {
            this.controlMode_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearCurrentSpeed() {
            this.currentSpeed_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearGoal() {
            this.goal_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearGradient() {
            this.gradient_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearHandrail() {
            this.handrail_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearHeartRate() {
            this.heartRate_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearInitial() {
            this.initial_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearMax() {
            this.max_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearMaxW() {
            this.maxW_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearMcuVersion() {
            this.mcuVersion_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearPanelDisplay() {
            this.panelDisplay_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearQuickSpeed() {
            this.quickSpeed_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearRecord() {
            this.record_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearRunState() {
            this.runState_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearRunningDistance() {
            this.runningDistance_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearRunningSteps() {
            this.runningSteps_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearRunningTotalTime() {
            this.runningTotalTime_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSpm() {
            this.spm_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearStartSpeed() {
            this.startSpeed_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSysDevicePid() {
            this.sysDevicePid_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearTutorial() {
            this.tutorial_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearUnit() {
            this.unit_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearVelocitySensitivity() {
            this.velocitySensitivity_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearWifiApBassid() {
            this.wifiApBassid_ = null;
        }

        public static AProperties getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeBurnCalories(Property property) {
            property.getClass();
            Property property2 = this.burnCalories_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.burnCalories_ = property;
            } else {
                this.burnCalories_ = Property.newBuilder(this.burnCalories_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeButtonId(Property property) {
            property.getClass();
            Property property2 = this.buttonId_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.buttonId_ = property;
            } else {
                this.buttonId_ = Property.newBuilder(this.buttonId_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeChildLockSwitch(Property property) {
            property.getClass();
            Property property2 = this.childLockSwitch_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.childLockSwitch_ = property;
            } else {
                this.childLockSwitch_ = Property.newBuilder(this.childLockSwitch_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeConSpMode(Property property) {
            property.getClass();
            Property property2 = this.conSpMode_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.conSpMode_ = property;
            } else {
                this.conSpMode_ = Property.newBuilder(this.conSpMode_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeControlMode(Property property) {
            property.getClass();
            Property property2 = this.controlMode_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.controlMode_ = property;
            } else {
                this.controlMode_ = Property.newBuilder(this.controlMode_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeCurrentSpeed(Property property) {
            property.getClass();
            Property property2 = this.currentSpeed_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.currentSpeed_ = property;
            } else {
                this.currentSpeed_ = Property.newBuilder(this.currentSpeed_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeGoal(Property property) {
            property.getClass();
            Property property2 = this.goal_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.goal_ = property;
            } else {
                this.goal_ = Property.newBuilder(this.goal_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeGradient(Property property) {
            property.getClass();
            Property property2 = this.gradient_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.gradient_ = property;
            } else {
                this.gradient_ = Property.newBuilder(this.gradient_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeHandrail(Property property) {
            property.getClass();
            Property property2 = this.handrail_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.handrail_ = property;
            } else {
                this.handrail_ = Property.newBuilder(this.handrail_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeHeartRate(Property property) {
            property.getClass();
            Property property2 = this.heartRate_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.heartRate_ = property;
            } else {
                this.heartRate_ = Property.newBuilder(this.heartRate_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeInitial(Property property) {
            property.getClass();
            Property property2 = this.initial_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.initial_ = property;
            } else {
                this.initial_ = Property.newBuilder(this.initial_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeMax(Property property) {
            property.getClass();
            Property property2 = this.max_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.max_ = property;
            } else {
                this.max_ = Property.newBuilder(this.max_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeMaxW(Property property) {
            property.getClass();
            Property property2 = this.maxW_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.maxW_ = property;
            } else {
                this.maxW_ = Property.newBuilder(this.maxW_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeMcuVersion(Property property) {
            property.getClass();
            Property property2 = this.mcuVersion_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.mcuVersion_ = property;
            } else {
                this.mcuVersion_ = Property.newBuilder(this.mcuVersion_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergePanelDisplay(Property property) {
            property.getClass();
            Property property2 = this.panelDisplay_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.panelDisplay_ = property;
            } else {
                this.panelDisplay_ = Property.newBuilder(this.panelDisplay_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeQuickSpeed(Property property) {
            property.getClass();
            Property property2 = this.quickSpeed_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.quickSpeed_ = property;
            } else {
                this.quickSpeed_ = Property.newBuilder(this.quickSpeed_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeRecord(Property property) {
            property.getClass();
            Property property2 = this.record_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.record_ = property;
            } else {
                this.record_ = Property.newBuilder(this.record_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeRunState(Property property) {
            property.getClass();
            Property property2 = this.runState_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.runState_ = property;
            } else {
                this.runState_ = Property.newBuilder(this.runState_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeRunningDistance(Property property) {
            property.getClass();
            Property property2 = this.runningDistance_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.runningDistance_ = property;
            } else {
                this.runningDistance_ = Property.newBuilder(this.runningDistance_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeRunningSteps(Property property) {
            property.getClass();
            Property property2 = this.runningSteps_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.runningSteps_ = property;
            } else {
                this.runningSteps_ = Property.newBuilder(this.runningSteps_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeRunningTotalTime(Property property) {
            property.getClass();
            Property property2 = this.runningTotalTime_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.runningTotalTime_ = property;
            } else {
                this.runningTotalTime_ = Property.newBuilder(this.runningTotalTime_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeSpm(Property property) {
            property.getClass();
            Property property2 = this.spm_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.spm_ = property;
            } else {
                this.spm_ = Property.newBuilder(this.spm_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeStartSpeed(Property property) {
            property.getClass();
            Property property2 = this.startSpeed_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.startSpeed_ = property;
            } else {
                this.startSpeed_ = Property.newBuilder(this.startSpeed_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeSysDevicePid(Property property) {
            property.getClass();
            Property property2 = this.sysDevicePid_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.sysDevicePid_ = property;
            } else {
                this.sysDevicePid_ = Property.newBuilder(this.sysDevicePid_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeTutorial(Property property) {
            property.getClass();
            Property property2 = this.tutorial_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.tutorial_ = property;
            } else {
                this.tutorial_ = Property.newBuilder(this.tutorial_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeUnit(Property property) {
            property.getClass();
            Property property2 = this.unit_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.unit_ = property;
            } else {
                this.unit_ = Property.newBuilder(this.unit_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeVelocitySensitivity(Property property) {
            property.getClass();
            Property property2 = this.velocitySensitivity_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.velocitySensitivity_ = property;
            } else {
                this.velocitySensitivity_ = Property.newBuilder(this.velocitySensitivity_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeWifiApBassid(Property property) {
            property.getClass();
            Property property2 = this.wifiApBassid_;
            if (property2 == null || property2 == Property.getDefaultInstance()) {
                this.wifiApBassid_ = property;
            } else {
                this.wifiApBassid_ = Property.newBuilder(this.wifiApBassid_).mergeFrom((Property.Builder) property).buildPartial();
            }
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static AProperties parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (AProperties) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static AProperties parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (AProperties) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<AProperties> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setBurnCalories(Property property) {
            property.getClass();
            this.burnCalories_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setButtonId(Property property) {
            property.getClass();
            this.buttonId_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setChildLockSwitch(Property property) {
            property.getClass();
            this.childLockSwitch_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setCode(int i2) {
            this.code_ = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setConSpMode(Property property) {
            property.getClass();
            this.conSpMode_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setControlMode(Property property) {
            property.getClass();
            this.controlMode_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setCurrentSpeed(Property property) {
            property.getClass();
            this.currentSpeed_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setGoal(Property property) {
            property.getClass();
            this.goal_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setGradient(Property property) {
            property.getClass();
            this.gradient_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setHandrail(Property property) {
            property.getClass();
            this.handrail_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setHeartRate(Property property) {
            property.getClass();
            this.heartRate_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setInitial(Property property) {
            property.getClass();
            this.initial_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMax(Property property) {
            property.getClass();
            this.max_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMaxW(Property property) {
            property.getClass();
            this.maxW_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMcuVersion(Property property) {
            property.getClass();
            this.mcuVersion_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPanelDisplay(Property property) {
            property.getClass();
            this.panelDisplay_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setQuickSpeed(Property property) {
            property.getClass();
            this.quickSpeed_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setRecord(Property property) {
            property.getClass();
            this.record_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setRunState(Property property) {
            property.getClass();
            this.runState_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setRunningDistance(Property property) {
            property.getClass();
            this.runningDistance_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setRunningSteps(Property property) {
            property.getClass();
            this.runningSteps_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setRunningTotalTime(Property property) {
            property.getClass();
            this.runningTotalTime_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSpm(Property property) {
            property.getClass();
            this.spm_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setStartSpeed(Property property) {
            property.getClass();
            this.startSpeed_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSysDevicePid(Property property) {
            property.getClass();
            this.sysDevicePid_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTutorial(Property property) {
            property.getClass();
            this.tutorial_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setUnit(Property property) {
            property.getClass();
            this.unit_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setVelocitySensitivity(Property property) {
            property.getClass();
            this.velocitySensitivity_ = property;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setWifiApBassid(Property property) {
            property.getClass();
            this.wifiApBassid_ = property;
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new AProperties();
                case 2:
                    return new Builder();
                case 3:
                    return GeneratedMessageLite.newMessageInfo(DEFAULT_INSTANCE, "\u0000\u001d\u0000\u0000\u0001\u001d\u001d\u0000\u0000\u0000\u0001\t\u0002\t\u0003\t\u0004\t\u0005\t\u0006\t\u0007\t\b\t\t\t\n\t\u000b\t\f\t\r\t\u000e\t\u000f\t\u0010\t\u0011\t\u0012\t\u0013\t\u0014\t\u0015\t\u0016\t\u0017\t\u0018\t\u0019\t\u001a\t\u001b\u0004\u001c\t\u001d\t", new Object[]{"runningSteps_", "burnCalories_", "currentSpeed_", "runningDistance_", "runningTotalTime_", "conSpMode_", "panelDisplay_", "max_", "controlMode_", "velocitySensitivity_", "startSpeed_", "goal_", "runState_", "spm_", "wifiApBassid_", "heartRate_", "mcuVersion_", "buttonId_", "maxW_", "record_", "tutorial_", "handrail_", "initial_", "unit_", "sysDevicePid_", "childLockSwitch_", "code_", "gradient_", "quickSpeed_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<AProperties> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (AProperties.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getBurnCalories() {
            Property property = this.burnCalories_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getButtonId() {
            Property property = this.buttonId_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getChildLockSwitch() {
            Property property = this.childLockSwitch_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public int getCode() {
            return this.code_;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getConSpMode() {
            Property property = this.conSpMode_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getControlMode() {
            Property property = this.controlMode_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getCurrentSpeed() {
            Property property = this.currentSpeed_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getGoal() {
            Property property = this.goal_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getGradient() {
            Property property = this.gradient_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getHandrail() {
            Property property = this.handrail_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getHeartRate() {
            Property property = this.heartRate_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getInitial() {
            Property property = this.initial_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getMax() {
            Property property = this.max_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getMaxW() {
            Property property = this.maxW_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getMcuVersion() {
            Property property = this.mcuVersion_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getPanelDisplay() {
            Property property = this.panelDisplay_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getQuickSpeed() {
            Property property = this.quickSpeed_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getRecord() {
            Property property = this.record_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getRunState() {
            Property property = this.runState_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getRunningDistance() {
            Property property = this.runningDistance_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getRunningSteps() {
            Property property = this.runningSteps_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getRunningTotalTime() {
            Property property = this.runningTotalTime_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getSpm() {
            Property property = this.spm_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getStartSpeed() {
            Property property = this.startSpeed_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getSysDevicePid() {
            Property property = this.sysDevicePid_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getTutorial() {
            Property property = this.tutorial_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getUnit() {
            Property property = this.unit_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getVelocitySensitivity() {
            Property property = this.velocitySensitivity_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public Property getWifiApBassid() {
            Property property = this.wifiApBassid_;
            return property == null ? Property.getDefaultInstance() : property;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasBurnCalories() {
            return this.burnCalories_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasButtonId() {
            return this.buttonId_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasChildLockSwitch() {
            return this.childLockSwitch_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasConSpMode() {
            return this.conSpMode_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasControlMode() {
            return this.controlMode_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasCurrentSpeed() {
            return this.currentSpeed_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasGoal() {
            return this.goal_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasGradient() {
            return this.gradient_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasHandrail() {
            return this.handrail_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasHeartRate() {
            return this.heartRate_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasInitial() {
            return this.initial_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasMax() {
            return this.max_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasMaxW() {
            return this.maxW_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasMcuVersion() {
            return this.mcuVersion_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasPanelDisplay() {
            return this.panelDisplay_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasQuickSpeed() {
            return this.quickSpeed_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasRecord() {
            return this.record_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasRunState() {
            return this.runState_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasRunningDistance() {
            return this.runningDistance_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasRunningSteps() {
            return this.runningSteps_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasRunningTotalTime() {
            return this.runningTotalTime_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasSpm() {
            return this.spm_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasStartSpeed() {
            return this.startSpeed_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasSysDevicePid() {
            return this.sysDevicePid_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasTutorial() {
            return this.tutorial_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasUnit() {
            return this.unit_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasVelocitySensitivity() {
            return this.velocitySensitivity_ != null;
        }

        @Override // com.kingsmith.aliiot.Protos.APropertiesOrBuilder
        public boolean hasWifiApBassid() {
            return this.wifiApBassid_ != null;
        }

        public static Builder newBuilder(AProperties aProperties) {
            return DEFAULT_INSTANCE.createBuilder(aProperties);
        }

        public static AProperties parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AProperties) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static AProperties parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AProperties) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static AProperties parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (AProperties) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static AProperties parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AProperties) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static AProperties parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (AProperties) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static AProperties parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AProperties) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static AProperties parseFrom(InputStream inputStream) throws IOException {
            return (AProperties) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static AProperties parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AProperties) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static AProperties parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (AProperties) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static AProperties parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AProperties) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface APropertiesOrBuilder extends MessageLiteOrBuilder {
        Property getBurnCalories();

        Property getButtonId();

        Property getChildLockSwitch();

        int getCode();

        Property getConSpMode();

        Property getControlMode();

        Property getCurrentSpeed();

        Property getGoal();

        Property getGradient();

        Property getHandrail();

        Property getHeartRate();

        Property getInitial();

        Property getMax();

        Property getMaxW();

        Property getMcuVersion();

        Property getPanelDisplay();

        Property getQuickSpeed();

        Property getRecord();

        Property getRunState();

        Property getRunningDistance();

        Property getRunningSteps();

        Property getRunningTotalTime();

        Property getSpm();

        Property getStartSpeed();

        Property getSysDevicePid();

        Property getTutorial();

        Property getUnit();

        Property getVelocitySensitivity();

        Property getWifiApBassid();

        boolean hasBurnCalories();

        boolean hasButtonId();

        boolean hasChildLockSwitch();

        boolean hasConSpMode();

        boolean hasControlMode();

        boolean hasCurrentSpeed();

        boolean hasGoal();

        boolean hasGradient();

        boolean hasHandrail();

        boolean hasHeartRate();

        boolean hasInitial();

        boolean hasMax();

        boolean hasMaxW();

        boolean hasMcuVersion();

        boolean hasPanelDisplay();

        boolean hasQuickSpeed();

        boolean hasRecord();

        boolean hasRunState();

        boolean hasRunningDistance();

        boolean hasRunningSteps();

        boolean hasRunningTotalTime();

        boolean hasSpm();

        boolean hasStartSpeed();

        boolean hasSysDevicePid();

        boolean hasTutorial();

        boolean hasUnit();

        boolean hasVelocitySensitivity();

        boolean hasWifiApBassid();
    }

    public static final class DeviceInfo extends GeneratedMessageLite<DeviceInfo, Builder> implements DeviceInfoOrBuilder {
        public static final int CATEGORY_IMAGE_FIELD_NUMBER = 2;
        private static final DeviceInfo DEFAULT_INSTANCE;
        public static final int DEVICE_NAME_FIELD_NUMBER = 6;
        public static final int GMT_MODIFIED_FIELD_NUMBER = 1;
        public static final int IDENTITY_ALIAS_FIELD_NUMBER = 7;
        public static final int IDENTITY_ID_FIELD_NUMBER = 10;
        public static final int IOT_ID_FIELD_NUMBER = 8;
        public static final int NET_TYPE_FIELD_NUMBER = 3;
        public static final int NICK_NAME_FIELD_NUMBER = 16;
        public static final int NODE_TYPE_FIELD_NUMBER = 4;
        public static final int OWNED_FIELD_NUMBER = 9;
        private static volatile Parser<DeviceInfo> PARSER = null;
        public static final int PRODUCT_IMAGE_FIELD_NUMBER = 14;
        public static final int PRODUCT_KEY_FIELD_NUMBER = 5;
        public static final int PRODUCT_MODEL_FIELD_NUMBER = 15;
        public static final int PRODUCT_NAME_FIELD_NUMBER = 13;
        public static final int STATUS_FIELD_NUMBER = 12;
        public static final int THING_TYPE_FIELD_NUMBER = 11;
        private long gmtModified_;
        private int owned_;
        private int status_;
        private String categoryImage_ = "";
        private String netType_ = "";
        private String nodeType_ = "";
        private String productKey_ = "";
        private String deviceName_ = "";
        private String identityAlias_ = "";
        private String iotId_ = "";
        private String identityId_ = "";
        private String thingType_ = "";
        private String productName_ = "";
        private String productImage_ = "";
        private String productModel_ = "";
        private String nickName_ = "";

        public static final class Builder extends GeneratedMessageLite.Builder<DeviceInfo, Builder> implements DeviceInfoOrBuilder {
            public Builder clearCategoryImage() {
                copyOnWrite();
                ((DeviceInfo) this.instance).clearCategoryImage();
                return this;
            }

            public Builder clearDeviceName() {
                copyOnWrite();
                ((DeviceInfo) this.instance).clearDeviceName();
                return this;
            }

            public Builder clearGmtModified() {
                copyOnWrite();
                ((DeviceInfo) this.instance).clearGmtModified();
                return this;
            }

            public Builder clearIdentityAlias() {
                copyOnWrite();
                ((DeviceInfo) this.instance).clearIdentityAlias();
                return this;
            }

            public Builder clearIdentityId() {
                copyOnWrite();
                ((DeviceInfo) this.instance).clearIdentityId();
                return this;
            }

            public Builder clearIotId() {
                copyOnWrite();
                ((DeviceInfo) this.instance).clearIotId();
                return this;
            }

            public Builder clearNetType() {
                copyOnWrite();
                ((DeviceInfo) this.instance).clearNetType();
                return this;
            }

            public Builder clearNickName() {
                copyOnWrite();
                ((DeviceInfo) this.instance).clearNickName();
                return this;
            }

            public Builder clearNodeType() {
                copyOnWrite();
                ((DeviceInfo) this.instance).clearNodeType();
                return this;
            }

            public Builder clearOwned() {
                copyOnWrite();
                ((DeviceInfo) this.instance).clearOwned();
                return this;
            }

            public Builder clearProductImage() {
                copyOnWrite();
                ((DeviceInfo) this.instance).clearProductImage();
                return this;
            }

            public Builder clearProductKey() {
                copyOnWrite();
                ((DeviceInfo) this.instance).clearProductKey();
                return this;
            }

            public Builder clearProductModel() {
                copyOnWrite();
                ((DeviceInfo) this.instance).clearProductModel();
                return this;
            }

            public Builder clearProductName() {
                copyOnWrite();
                ((DeviceInfo) this.instance).clearProductName();
                return this;
            }

            public Builder clearStatus() {
                copyOnWrite();
                ((DeviceInfo) this.instance).clearStatus();
                return this;
            }

            public Builder clearThingType() {
                copyOnWrite();
                ((DeviceInfo) this.instance).clearThingType();
                return this;
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public String getCategoryImage() {
                return ((DeviceInfo) this.instance).getCategoryImage();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public ByteString getCategoryImageBytes() {
                return ((DeviceInfo) this.instance).getCategoryImageBytes();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public String getDeviceName() {
                return ((DeviceInfo) this.instance).getDeviceName();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public ByteString getDeviceNameBytes() {
                return ((DeviceInfo) this.instance).getDeviceNameBytes();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public long getGmtModified() {
                return ((DeviceInfo) this.instance).getGmtModified();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public String getIdentityAlias() {
                return ((DeviceInfo) this.instance).getIdentityAlias();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public ByteString getIdentityAliasBytes() {
                return ((DeviceInfo) this.instance).getIdentityAliasBytes();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public String getIdentityId() {
                return ((DeviceInfo) this.instance).getIdentityId();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public ByteString getIdentityIdBytes() {
                return ((DeviceInfo) this.instance).getIdentityIdBytes();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public String getIotId() {
                return ((DeviceInfo) this.instance).getIotId();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public ByteString getIotIdBytes() {
                return ((DeviceInfo) this.instance).getIotIdBytes();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public String getNetType() {
                return ((DeviceInfo) this.instance).getNetType();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public ByteString getNetTypeBytes() {
                return ((DeviceInfo) this.instance).getNetTypeBytes();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public String getNickName() {
                return ((DeviceInfo) this.instance).getNickName();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public ByteString getNickNameBytes() {
                return ((DeviceInfo) this.instance).getNickNameBytes();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public String getNodeType() {
                return ((DeviceInfo) this.instance).getNodeType();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public ByteString getNodeTypeBytes() {
                return ((DeviceInfo) this.instance).getNodeTypeBytes();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public int getOwned() {
                return ((DeviceInfo) this.instance).getOwned();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public String getProductImage() {
                return ((DeviceInfo) this.instance).getProductImage();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public ByteString getProductImageBytes() {
                return ((DeviceInfo) this.instance).getProductImageBytes();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public String getProductKey() {
                return ((DeviceInfo) this.instance).getProductKey();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public ByteString getProductKeyBytes() {
                return ((DeviceInfo) this.instance).getProductKeyBytes();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public String getProductModel() {
                return ((DeviceInfo) this.instance).getProductModel();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public ByteString getProductModelBytes() {
                return ((DeviceInfo) this.instance).getProductModelBytes();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public String getProductName() {
                return ((DeviceInfo) this.instance).getProductName();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public ByteString getProductNameBytes() {
                return ((DeviceInfo) this.instance).getProductNameBytes();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public int getStatus() {
                return ((DeviceInfo) this.instance).getStatus();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public String getThingType() {
                return ((DeviceInfo) this.instance).getThingType();
            }

            @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
            public ByteString getThingTypeBytes() {
                return ((DeviceInfo) this.instance).getThingTypeBytes();
            }

            public Builder setCategoryImage(String str) {
                copyOnWrite();
                ((DeviceInfo) this.instance).setCategoryImage(str);
                return this;
            }

            public Builder setCategoryImageBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((DeviceInfo) this.instance).setCategoryImageBytes(byteString);
                return this;
            }

            public Builder setDeviceName(String str) {
                copyOnWrite();
                ((DeviceInfo) this.instance).setDeviceName(str);
                return this;
            }

            public Builder setDeviceNameBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((DeviceInfo) this.instance).setDeviceNameBytes(byteString);
                return this;
            }

            public Builder setGmtModified(long j2) {
                copyOnWrite();
                ((DeviceInfo) this.instance).setGmtModified(j2);
                return this;
            }

            public Builder setIdentityAlias(String str) {
                copyOnWrite();
                ((DeviceInfo) this.instance).setIdentityAlias(str);
                return this;
            }

            public Builder setIdentityAliasBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((DeviceInfo) this.instance).setIdentityAliasBytes(byteString);
                return this;
            }

            public Builder setIdentityId(String str) {
                copyOnWrite();
                ((DeviceInfo) this.instance).setIdentityId(str);
                return this;
            }

            public Builder setIdentityIdBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((DeviceInfo) this.instance).setIdentityIdBytes(byteString);
                return this;
            }

            public Builder setIotId(String str) {
                copyOnWrite();
                ((DeviceInfo) this.instance).setIotId(str);
                return this;
            }

            public Builder setIotIdBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((DeviceInfo) this.instance).setIotIdBytes(byteString);
                return this;
            }

            public Builder setNetType(String str) {
                copyOnWrite();
                ((DeviceInfo) this.instance).setNetType(str);
                return this;
            }

            public Builder setNetTypeBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((DeviceInfo) this.instance).setNetTypeBytes(byteString);
                return this;
            }

            public Builder setNickName(String str) {
                copyOnWrite();
                ((DeviceInfo) this.instance).setNickName(str);
                return this;
            }

            public Builder setNickNameBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((DeviceInfo) this.instance).setNickNameBytes(byteString);
                return this;
            }

            public Builder setNodeType(String str) {
                copyOnWrite();
                ((DeviceInfo) this.instance).setNodeType(str);
                return this;
            }

            public Builder setNodeTypeBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((DeviceInfo) this.instance).setNodeTypeBytes(byteString);
                return this;
            }

            public Builder setOwned(int i2) {
                copyOnWrite();
                ((DeviceInfo) this.instance).setOwned(i2);
                return this;
            }

            public Builder setProductImage(String str) {
                copyOnWrite();
                ((DeviceInfo) this.instance).setProductImage(str);
                return this;
            }

            public Builder setProductImageBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((DeviceInfo) this.instance).setProductImageBytes(byteString);
                return this;
            }

            public Builder setProductKey(String str) {
                copyOnWrite();
                ((DeviceInfo) this.instance).setProductKey(str);
                return this;
            }

            public Builder setProductKeyBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((DeviceInfo) this.instance).setProductKeyBytes(byteString);
                return this;
            }

            public Builder setProductModel(String str) {
                copyOnWrite();
                ((DeviceInfo) this.instance).setProductModel(str);
                return this;
            }

            public Builder setProductModelBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((DeviceInfo) this.instance).setProductModelBytes(byteString);
                return this;
            }

            public Builder setProductName(String str) {
                copyOnWrite();
                ((DeviceInfo) this.instance).setProductName(str);
                return this;
            }

            public Builder setProductNameBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((DeviceInfo) this.instance).setProductNameBytes(byteString);
                return this;
            }

            public Builder setStatus(int i2) {
                copyOnWrite();
                ((DeviceInfo) this.instance).setStatus(i2);
                return this;
            }

            public Builder setThingType(String str) {
                copyOnWrite();
                ((DeviceInfo) this.instance).setThingType(str);
                return this;
            }

            public Builder setThingTypeBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((DeviceInfo) this.instance).setThingTypeBytes(byteString);
                return this;
            }

            private Builder() {
                super(DeviceInfo.DEFAULT_INSTANCE);
            }
        }

        static {
            DeviceInfo deviceInfo = new DeviceInfo();
            DEFAULT_INSTANCE = deviceInfo;
            GeneratedMessageLite.registerDefaultInstance(DeviceInfo.class, deviceInfo);
        }

        private DeviceInfo() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearCategoryImage() {
            this.categoryImage_ = getDefaultInstance().getCategoryImage();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDeviceName() {
            this.deviceName_ = getDefaultInstance().getDeviceName();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearGmtModified() {
            this.gmtModified_ = 0L;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearIdentityAlias() {
            this.identityAlias_ = getDefaultInstance().getIdentityAlias();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearIdentityId() {
            this.identityId_ = getDefaultInstance().getIdentityId();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearIotId() {
            this.iotId_ = getDefaultInstance().getIotId();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearNetType() {
            this.netType_ = getDefaultInstance().getNetType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearNickName() {
            this.nickName_ = getDefaultInstance().getNickName();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearNodeType() {
            this.nodeType_ = getDefaultInstance().getNodeType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearOwned() {
            this.owned_ = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearProductImage() {
            this.productImage_ = getDefaultInstance().getProductImage();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearProductKey() {
            this.productKey_ = getDefaultInstance().getProductKey();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearProductModel() {
            this.productModel_ = getDefaultInstance().getProductModel();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearProductName() {
            this.productName_ = getDefaultInstance().getProductName();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearStatus() {
            this.status_ = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearThingType() {
            this.thingType_ = getDefaultInstance().getThingType();
        }

        public static DeviceInfo getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static DeviceInfo parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (DeviceInfo) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static DeviceInfo parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<DeviceInfo> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setCategoryImage(String str) {
            str.getClass();
            this.categoryImage_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setCategoryImageBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.categoryImage_ = byteString.toStringUtf8();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDeviceName(String str) {
            str.getClass();
            this.deviceName_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDeviceNameBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.deviceName_ = byteString.toStringUtf8();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setGmtModified(long j2) {
            this.gmtModified_ = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setIdentityAlias(String str) {
            str.getClass();
            this.identityAlias_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setIdentityAliasBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.identityAlias_ = byteString.toStringUtf8();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setIdentityId(String str) {
            str.getClass();
            this.identityId_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setIdentityIdBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.identityId_ = byteString.toStringUtf8();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setIotId(String str) {
            str.getClass();
            this.iotId_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setIotIdBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.iotId_ = byteString.toStringUtf8();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNetType(String str) {
            str.getClass();
            this.netType_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNetTypeBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.netType_ = byteString.toStringUtf8();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNickName(String str) {
            str.getClass();
            this.nickName_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNickNameBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.nickName_ = byteString.toStringUtf8();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNodeType(String str) {
            str.getClass();
            this.nodeType_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNodeTypeBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.nodeType_ = byteString.toStringUtf8();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setOwned(int i2) {
            this.owned_ = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setProductImage(String str) {
            str.getClass();
            this.productImage_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setProductImageBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.productImage_ = byteString.toStringUtf8();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setProductKey(String str) {
            str.getClass();
            this.productKey_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setProductKeyBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.productKey_ = byteString.toStringUtf8();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setProductModel(String str) {
            str.getClass();
            this.productModel_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setProductModelBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.productModel_ = byteString.toStringUtf8();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setProductName(String str) {
            str.getClass();
            this.productName_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setProductNameBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.productName_ = byteString.toStringUtf8();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setStatus(int i2) {
            this.status_ = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setThingType(String str) {
            str.getClass();
            this.thingType_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setThingTypeBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.thingType_ = byteString.toStringUtf8();
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new DeviceInfo();
                case 2:
                    return new Builder();
                case 3:
                    return GeneratedMessageLite.newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0010\u0000\u0000\u0001\u0010\u0010\u0000\u0000\u0000\u0001\u0002\u0002Ȉ\u0003Ȉ\u0004Ȉ\u0005Ȉ\u0006Ȉ\u0007Ȉ\bȈ\t\u0004\nȈ\u000bȈ\f\u0004\rȈ\u000eȈ\u000fȈ\u0010Ȉ", new Object[]{"gmtModified_", "categoryImage_", "netType_", "nodeType_", "productKey_", "deviceName_", "identityAlias_", "iotId_", "owned_", "identityId_", "thingType_", "status_", "productName_", "productImage_", "productModel_", "nickName_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<DeviceInfo> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (DeviceInfo.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public String getCategoryImage() {
            return this.categoryImage_;
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public ByteString getCategoryImageBytes() {
            return ByteString.copyFromUtf8(this.categoryImage_);
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public String getDeviceName() {
            return this.deviceName_;
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public ByteString getDeviceNameBytes() {
            return ByteString.copyFromUtf8(this.deviceName_);
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public long getGmtModified() {
            return this.gmtModified_;
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public String getIdentityAlias() {
            return this.identityAlias_;
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public ByteString getIdentityAliasBytes() {
            return ByteString.copyFromUtf8(this.identityAlias_);
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public String getIdentityId() {
            return this.identityId_;
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public ByteString getIdentityIdBytes() {
            return ByteString.copyFromUtf8(this.identityId_);
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public String getIotId() {
            return this.iotId_;
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public ByteString getIotIdBytes() {
            return ByteString.copyFromUtf8(this.iotId_);
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public String getNetType() {
            return this.netType_;
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public ByteString getNetTypeBytes() {
            return ByteString.copyFromUtf8(this.netType_);
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public String getNickName() {
            return this.nickName_;
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public ByteString getNickNameBytes() {
            return ByteString.copyFromUtf8(this.nickName_);
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public String getNodeType() {
            return this.nodeType_;
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public ByteString getNodeTypeBytes() {
            return ByteString.copyFromUtf8(this.nodeType_);
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public int getOwned() {
            return this.owned_;
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public String getProductImage() {
            return this.productImage_;
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public ByteString getProductImageBytes() {
            return ByteString.copyFromUtf8(this.productImage_);
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public String getProductKey() {
            return this.productKey_;
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public ByteString getProductKeyBytes() {
            return ByteString.copyFromUtf8(this.productKey_);
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public String getProductModel() {
            return this.productModel_;
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public ByteString getProductModelBytes() {
            return ByteString.copyFromUtf8(this.productModel_);
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public String getProductName() {
            return this.productName_;
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public ByteString getProductNameBytes() {
            return ByteString.copyFromUtf8(this.productName_);
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public int getStatus() {
            return this.status_;
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public String getThingType() {
            return this.thingType_;
        }

        @Override // com.kingsmith.aliiot.Protos.DeviceInfoOrBuilder
        public ByteString getThingTypeBytes() {
            return ByteString.copyFromUtf8(this.thingType_);
        }

        public static Builder newBuilder(DeviceInfo deviceInfo) {
            return DEFAULT_INSTANCE.createBuilder(deviceInfo);
        }

        public static DeviceInfo parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DeviceInfo) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static DeviceInfo parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static DeviceInfo parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static DeviceInfo parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static DeviceInfo parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static DeviceInfo parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static DeviceInfo parseFrom(InputStream inputStream) throws IOException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static DeviceInfo parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static DeviceInfo parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static DeviceInfo parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface DeviceInfoOrBuilder extends MessageLiteOrBuilder {
        String getCategoryImage();

        ByteString getCategoryImageBytes();

        String getDeviceName();

        ByteString getDeviceNameBytes();

        long getGmtModified();

        String getIdentityAlias();

        ByteString getIdentityAliasBytes();

        String getIdentityId();

        ByteString getIdentityIdBytes();

        String getIotId();

        ByteString getIotIdBytes();

        String getNetType();

        ByteString getNetTypeBytes();

        String getNickName();

        ByteString getNickNameBytes();

        String getNodeType();

        ByteString getNodeTypeBytes();

        int getOwned();

        String getProductImage();

        ByteString getProductImageBytes();

        String getProductKey();

        ByteString getProductKeyBytes();

        String getProductModel();

        ByteString getProductModelBytes();

        String getProductName();

        ByteString getProductNameBytes();

        int getStatus();

        String getThingType();

        ByteString getThingTypeBytes();
    }

    public static final class ListOfDeviceAli extends GeneratedMessageLite<ListOfDeviceAli, Builder> implements ListOfDeviceAliOrBuilder {
        public static final int CODE_FIELD_NUMBER = 2;
        private static final ListOfDeviceAli DEFAULT_INSTANCE;
        public static final int DEVICELIST_FIELD_NUMBER = 1;
        private static volatile Parser<ListOfDeviceAli> PARSER;
        private int code_;
        private Internal.ProtobufList<DeviceInfo> deviceList_ = GeneratedMessageLite.emptyProtobufList();

        public static final class Builder extends GeneratedMessageLite.Builder<ListOfDeviceAli, Builder> implements ListOfDeviceAliOrBuilder {
            public Builder addAllDeviceList(Iterable<? extends DeviceInfo> iterable) {
                copyOnWrite();
                ((ListOfDeviceAli) this.instance).addAllDeviceList(iterable);
                return this;
            }

            public Builder addDeviceList(DeviceInfo deviceInfo) {
                copyOnWrite();
                ((ListOfDeviceAli) this.instance).addDeviceList(deviceInfo);
                return this;
            }

            public Builder clearCode() {
                copyOnWrite();
                ((ListOfDeviceAli) this.instance).clearCode();
                return this;
            }

            public Builder clearDeviceList() {
                copyOnWrite();
                ((ListOfDeviceAli) this.instance).clearDeviceList();
                return this;
            }

            @Override // com.kingsmith.aliiot.Protos.ListOfDeviceAliOrBuilder
            public int getCode() {
                return ((ListOfDeviceAli) this.instance).getCode();
            }

            @Override // com.kingsmith.aliiot.Protos.ListOfDeviceAliOrBuilder
            public DeviceInfo getDeviceList(int i2) {
                return ((ListOfDeviceAli) this.instance).getDeviceList(i2);
            }

            @Override // com.kingsmith.aliiot.Protos.ListOfDeviceAliOrBuilder
            public int getDeviceListCount() {
                return ((ListOfDeviceAli) this.instance).getDeviceListCount();
            }

            @Override // com.kingsmith.aliiot.Protos.ListOfDeviceAliOrBuilder
            public List<DeviceInfo> getDeviceListList() {
                return Collections.unmodifiableList(((ListOfDeviceAli) this.instance).getDeviceListList());
            }

            public Builder removeDeviceList(int i2) {
                copyOnWrite();
                ((ListOfDeviceAli) this.instance).removeDeviceList(i2);
                return this;
            }

            public Builder setCode(int i2) {
                copyOnWrite();
                ((ListOfDeviceAli) this.instance).setCode(i2);
                return this;
            }

            public Builder setDeviceList(int i2, DeviceInfo deviceInfo) {
                copyOnWrite();
                ((ListOfDeviceAli) this.instance).setDeviceList(i2, deviceInfo);
                return this;
            }

            private Builder() {
                super(ListOfDeviceAli.DEFAULT_INSTANCE);
            }

            public Builder addDeviceList(int i2, DeviceInfo deviceInfo) {
                copyOnWrite();
                ((ListOfDeviceAli) this.instance).addDeviceList(i2, deviceInfo);
                return this;
            }

            public Builder setDeviceList(int i2, DeviceInfo.Builder builder) {
                copyOnWrite();
                ((ListOfDeviceAli) this.instance).setDeviceList(i2, builder.build());
                return this;
            }

            public Builder addDeviceList(DeviceInfo.Builder builder) {
                copyOnWrite();
                ((ListOfDeviceAli) this.instance).addDeviceList(builder.build());
                return this;
            }

            public Builder addDeviceList(int i2, DeviceInfo.Builder builder) {
                copyOnWrite();
                ((ListOfDeviceAli) this.instance).addDeviceList(i2, builder.build());
                return this;
            }
        }

        static {
            ListOfDeviceAli listOfDeviceAli = new ListOfDeviceAli();
            DEFAULT_INSTANCE = listOfDeviceAli;
            GeneratedMessageLite.registerDefaultInstance(ListOfDeviceAli.class, listOfDeviceAli);
        }

        private ListOfDeviceAli() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllDeviceList(Iterable<? extends DeviceInfo> iterable) {
            ensureDeviceListIsMutable();
            AbstractMessageLite.addAll((Iterable) iterable, (List) this.deviceList_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDeviceList(DeviceInfo deviceInfo) {
            deviceInfo.getClass();
            ensureDeviceListIsMutable();
            this.deviceList_.add(deviceInfo);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearCode() {
            this.code_ = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDeviceList() {
            this.deviceList_ = GeneratedMessageLite.emptyProtobufList();
        }

        private void ensureDeviceListIsMutable() {
            Internal.ProtobufList<DeviceInfo> protobufList = this.deviceList_;
            if (protobufList.isModifiable()) {
                return;
            }
            this.deviceList_ = GeneratedMessageLite.mutableCopy(protobufList);
        }

        public static ListOfDeviceAli getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static ListOfDeviceAli parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ListOfDeviceAli) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static ListOfDeviceAli parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (ListOfDeviceAli) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<ListOfDeviceAli> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeDeviceList(int i2) {
            ensureDeviceListIsMutable();
            this.deviceList_.remove(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setCode(int i2) {
            this.code_ = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDeviceList(int i2, DeviceInfo deviceInfo) {
            deviceInfo.getClass();
            ensureDeviceListIsMutable();
            this.deviceList_.set(i2, deviceInfo);
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new ListOfDeviceAli();
                case 2:
                    return new Builder();
                case 3:
                    return GeneratedMessageLite.newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0001\u0000\u0001\u001b\u0002\u0004", new Object[]{"deviceList_", DeviceInfo.class, "code_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<ListOfDeviceAli> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (ListOfDeviceAli.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        @Override // com.kingsmith.aliiot.Protos.ListOfDeviceAliOrBuilder
        public int getCode() {
            return this.code_;
        }

        @Override // com.kingsmith.aliiot.Protos.ListOfDeviceAliOrBuilder
        public DeviceInfo getDeviceList(int i2) {
            return this.deviceList_.get(i2);
        }

        @Override // com.kingsmith.aliiot.Protos.ListOfDeviceAliOrBuilder
        public int getDeviceListCount() {
            return this.deviceList_.size();
        }

        @Override // com.kingsmith.aliiot.Protos.ListOfDeviceAliOrBuilder
        public List<DeviceInfo> getDeviceListList() {
            return this.deviceList_;
        }

        public DeviceInfoOrBuilder getDeviceListOrBuilder(int i2) {
            return this.deviceList_.get(i2);
        }

        public List<? extends DeviceInfoOrBuilder> getDeviceListOrBuilderList() {
            return this.deviceList_;
        }

        public static Builder newBuilder(ListOfDeviceAli listOfDeviceAli) {
            return DEFAULT_INSTANCE.createBuilder(listOfDeviceAli);
        }

        public static ListOfDeviceAli parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ListOfDeviceAli) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ListOfDeviceAli parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ListOfDeviceAli) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static ListOfDeviceAli parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ListOfDeviceAli) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDeviceList(int i2, DeviceInfo deviceInfo) {
            deviceInfo.getClass();
            ensureDeviceListIsMutable();
            this.deviceList_.add(i2, deviceInfo);
        }

        public static ListOfDeviceAli parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ListOfDeviceAli) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static ListOfDeviceAli parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ListOfDeviceAli) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static ListOfDeviceAli parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ListOfDeviceAli) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static ListOfDeviceAli parseFrom(InputStream inputStream) throws IOException {
            return (ListOfDeviceAli) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static ListOfDeviceAli parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ListOfDeviceAli) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ListOfDeviceAli parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ListOfDeviceAli) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static ListOfDeviceAli parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ListOfDeviceAli) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface ListOfDeviceAliOrBuilder extends MessageLiteOrBuilder {
        int getCode();

        DeviceInfo getDeviceList(int i2);

        int getDeviceListCount();

        List<DeviceInfo> getDeviceListList();
    }

    public static final class Property extends GeneratedMessageLite<Property, Builder> implements PropertyOrBuilder {
        private static final Property DEFAULT_INSTANCE;
        private static volatile Parser<Property> PARSER = null;
        public static final int TIME_FIELD_NUMBER = 1;
        public static final int VALUE_FIELD_NUMBER = 2;
        private long time_;
        private String value_ = "";

        public static final class Builder extends GeneratedMessageLite.Builder<Property, Builder> implements PropertyOrBuilder {
            public Builder clearTime() {
                copyOnWrite();
                ((Property) this.instance).clearTime();
                return this;
            }

            public Builder clearValue() {
                copyOnWrite();
                ((Property) this.instance).clearValue();
                return this;
            }

            @Override // com.kingsmith.aliiot.Protos.PropertyOrBuilder
            public long getTime() {
                return ((Property) this.instance).getTime();
            }

            @Override // com.kingsmith.aliiot.Protos.PropertyOrBuilder
            public String getValue() {
                return ((Property) this.instance).getValue();
            }

            @Override // com.kingsmith.aliiot.Protos.PropertyOrBuilder
            public ByteString getValueBytes() {
                return ((Property) this.instance).getValueBytes();
            }

            public Builder setTime(long j2) {
                copyOnWrite();
                ((Property) this.instance).setTime(j2);
                return this;
            }

            public Builder setValue(String str) {
                copyOnWrite();
                ((Property) this.instance).setValue(str);
                return this;
            }

            public Builder setValueBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((Property) this.instance).setValueBytes(byteString);
                return this;
            }

            private Builder() {
                super(Property.DEFAULT_INSTANCE);
            }
        }

        static {
            Property property = new Property();
            DEFAULT_INSTANCE = property;
            GeneratedMessageLite.registerDefaultInstance(Property.class, property);
        }

        private Property() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearTime() {
            this.time_ = 0L;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearValue() {
            this.value_ = getDefaultInstance().getValue();
        }

        public static Property getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static Property parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Property) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static Property parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (Property) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<Property> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTime(long j2) {
            this.time_ = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setValue(String str) {
            str.getClass();
            this.value_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setValueBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.value_ = byteString.toStringUtf8();
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new Property();
                case 2:
                    return new Builder();
                case 3:
                    return GeneratedMessageLite.newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0002\u0002Ȉ", new Object[]{"time_", "value_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<Property> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (Property.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        @Override // com.kingsmith.aliiot.Protos.PropertyOrBuilder
        public long getTime() {
            return this.time_;
        }

        @Override // com.kingsmith.aliiot.Protos.PropertyOrBuilder
        public String getValue() {
            return this.value_;
        }

        @Override // com.kingsmith.aliiot.Protos.PropertyOrBuilder
        public ByteString getValueBytes() {
            return ByteString.copyFromUtf8(this.value_);
        }

        public static Builder newBuilder(Property property) {
            return DEFAULT_INSTANCE.createBuilder(property);
        }

        public static Property parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Property) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Property parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Property) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static Property parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (Property) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static Property parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Property) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static Property parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (Property) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static Property parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Property) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static Property parseFrom(InputStream inputStream) throws IOException {
            return (Property) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static Property parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Property) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Property parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Property) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static Property parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Property) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface PropertyOrBuilder extends MessageLiteOrBuilder {
        long getTime();

        String getValue();

        ByteString getValueBytes();
    }

    public static final class UserInfo extends GeneratedMessageLite<UserInfo, Builder> implements UserInfoOrBuilder {
        private static final UserInfo DEFAULT_INSTANCE;
        public static final int MOBILE_LOCATION_CODE_FIELD_NUMBER = 5;
        public static final int OPEN_ID_FIELD_NUMBER = 2;
        private static volatile Parser<UserInfo> PARSER = null;
        public static final int USER_AVATAR_URL_FIELD_NUMBER = 4;
        public static final int USER_EMAIL_FIELD_NUMBER = 7;
        public static final int USER_ID_FIELD_NUMBER = 1;
        public static final int USER_NICK_FIELD_NUMBER = 3;
        public static final int USER_PHONE_FIELD_NUMBER = 6;
        private String userId_ = "";
        private String openId_ = "";
        private String userNick_ = "";
        private String userAvatarUrl_ = "";
        private String mobileLocationCode_ = "";
        private String userPhone_ = "";
        private String userEmail_ = "";

        public static final class Builder extends GeneratedMessageLite.Builder<UserInfo, Builder> implements UserInfoOrBuilder {
            public Builder clearMobileLocationCode() {
                copyOnWrite();
                ((UserInfo) this.instance).clearMobileLocationCode();
                return this;
            }

            public Builder clearOpenId() {
                copyOnWrite();
                ((UserInfo) this.instance).clearOpenId();
                return this;
            }

            public Builder clearUserAvatarUrl() {
                copyOnWrite();
                ((UserInfo) this.instance).clearUserAvatarUrl();
                return this;
            }

            public Builder clearUserEmail() {
                copyOnWrite();
                ((UserInfo) this.instance).clearUserEmail();
                return this;
            }

            public Builder clearUserId() {
                copyOnWrite();
                ((UserInfo) this.instance).clearUserId();
                return this;
            }

            public Builder clearUserNick() {
                copyOnWrite();
                ((UserInfo) this.instance).clearUserNick();
                return this;
            }

            public Builder clearUserPhone() {
                copyOnWrite();
                ((UserInfo) this.instance).clearUserPhone();
                return this;
            }

            @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
            public String getMobileLocationCode() {
                return ((UserInfo) this.instance).getMobileLocationCode();
            }

            @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
            public ByteString getMobileLocationCodeBytes() {
                return ((UserInfo) this.instance).getMobileLocationCodeBytes();
            }

            @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
            public String getOpenId() {
                return ((UserInfo) this.instance).getOpenId();
            }

            @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
            public ByteString getOpenIdBytes() {
                return ((UserInfo) this.instance).getOpenIdBytes();
            }

            @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
            public String getUserAvatarUrl() {
                return ((UserInfo) this.instance).getUserAvatarUrl();
            }

            @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
            public ByteString getUserAvatarUrlBytes() {
                return ((UserInfo) this.instance).getUserAvatarUrlBytes();
            }

            @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
            public String getUserEmail() {
                return ((UserInfo) this.instance).getUserEmail();
            }

            @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
            public ByteString getUserEmailBytes() {
                return ((UserInfo) this.instance).getUserEmailBytes();
            }

            @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
            public String getUserId() {
                return ((UserInfo) this.instance).getUserId();
            }

            @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
            public ByteString getUserIdBytes() {
                return ((UserInfo) this.instance).getUserIdBytes();
            }

            @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
            public String getUserNick() {
                return ((UserInfo) this.instance).getUserNick();
            }

            @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
            public ByteString getUserNickBytes() {
                return ((UserInfo) this.instance).getUserNickBytes();
            }

            @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
            public String getUserPhone() {
                return ((UserInfo) this.instance).getUserPhone();
            }

            @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
            public ByteString getUserPhoneBytes() {
                return ((UserInfo) this.instance).getUserPhoneBytes();
            }

            public Builder setMobileLocationCode(String str) {
                copyOnWrite();
                ((UserInfo) this.instance).setMobileLocationCode(str);
                return this;
            }

            public Builder setMobileLocationCodeBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((UserInfo) this.instance).setMobileLocationCodeBytes(byteString);
                return this;
            }

            public Builder setOpenId(String str) {
                copyOnWrite();
                ((UserInfo) this.instance).setOpenId(str);
                return this;
            }

            public Builder setOpenIdBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((UserInfo) this.instance).setOpenIdBytes(byteString);
                return this;
            }

            public Builder setUserAvatarUrl(String str) {
                copyOnWrite();
                ((UserInfo) this.instance).setUserAvatarUrl(str);
                return this;
            }

            public Builder setUserAvatarUrlBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((UserInfo) this.instance).setUserAvatarUrlBytes(byteString);
                return this;
            }

            public Builder setUserEmail(String str) {
                copyOnWrite();
                ((UserInfo) this.instance).setUserEmail(str);
                return this;
            }

            public Builder setUserEmailBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((UserInfo) this.instance).setUserEmailBytes(byteString);
                return this;
            }

            public Builder setUserId(String str) {
                copyOnWrite();
                ((UserInfo) this.instance).setUserId(str);
                return this;
            }

            public Builder setUserIdBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((UserInfo) this.instance).setUserIdBytes(byteString);
                return this;
            }

            public Builder setUserNick(String str) {
                copyOnWrite();
                ((UserInfo) this.instance).setUserNick(str);
                return this;
            }

            public Builder setUserNickBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((UserInfo) this.instance).setUserNickBytes(byteString);
                return this;
            }

            public Builder setUserPhone(String str) {
                copyOnWrite();
                ((UserInfo) this.instance).setUserPhone(str);
                return this;
            }

            public Builder setUserPhoneBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((UserInfo) this.instance).setUserPhoneBytes(byteString);
                return this;
            }

            private Builder() {
                super(UserInfo.DEFAULT_INSTANCE);
            }
        }

        static {
            UserInfo userInfo = new UserInfo();
            DEFAULT_INSTANCE = userInfo;
            GeneratedMessageLite.registerDefaultInstance(UserInfo.class, userInfo);
        }

        private UserInfo() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearMobileLocationCode() {
            this.mobileLocationCode_ = getDefaultInstance().getMobileLocationCode();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearOpenId() {
            this.openId_ = getDefaultInstance().getOpenId();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearUserAvatarUrl() {
            this.userAvatarUrl_ = getDefaultInstance().getUserAvatarUrl();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearUserEmail() {
            this.userEmail_ = getDefaultInstance().getUserEmail();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearUserId() {
            this.userId_ = getDefaultInstance().getUserId();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearUserNick() {
            this.userNick_ = getDefaultInstance().getUserNick();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearUserPhone() {
            this.userPhone_ = getDefaultInstance().getUserPhone();
        }

        public static UserInfo getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static UserInfo parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (UserInfo) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static UserInfo parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (UserInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<UserInfo> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMobileLocationCode(String str) {
            str.getClass();
            this.mobileLocationCode_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMobileLocationCodeBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.mobileLocationCode_ = byteString.toStringUtf8();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setOpenId(String str) {
            str.getClass();
            this.openId_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setOpenIdBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.openId_ = byteString.toStringUtf8();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setUserAvatarUrl(String str) {
            str.getClass();
            this.userAvatarUrl_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setUserAvatarUrlBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.userAvatarUrl_ = byteString.toStringUtf8();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setUserEmail(String str) {
            str.getClass();
            this.userEmail_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setUserEmailBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.userEmail_ = byteString.toStringUtf8();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setUserId(String str) {
            str.getClass();
            this.userId_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setUserIdBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.userId_ = byteString.toStringUtf8();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setUserNick(String str) {
            str.getClass();
            this.userNick_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setUserNickBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.userNick_ = byteString.toStringUtf8();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setUserPhone(String str) {
            str.getClass();
            this.userPhone_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setUserPhoneBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.userPhone_ = byteString.toStringUtf8();
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new UserInfo();
                case 2:
                    return new Builder();
                case 3:
                    return GeneratedMessageLite.newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0007\u0000\u0000\u0001\u0007\u0007\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ\u0003Ȉ\u0004Ȉ\u0005Ȉ\u0006Ȉ\u0007Ȉ", new Object[]{"userId_", "openId_", "userNick_", "userAvatarUrl_", "mobileLocationCode_", "userPhone_", "userEmail_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<UserInfo> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (UserInfo.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
        public String getMobileLocationCode() {
            return this.mobileLocationCode_;
        }

        @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
        public ByteString getMobileLocationCodeBytes() {
            return ByteString.copyFromUtf8(this.mobileLocationCode_);
        }

        @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
        public String getOpenId() {
            return this.openId_;
        }

        @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
        public ByteString getOpenIdBytes() {
            return ByteString.copyFromUtf8(this.openId_);
        }

        @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
        public String getUserAvatarUrl() {
            return this.userAvatarUrl_;
        }

        @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
        public ByteString getUserAvatarUrlBytes() {
            return ByteString.copyFromUtf8(this.userAvatarUrl_);
        }

        @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
        public String getUserEmail() {
            return this.userEmail_;
        }

        @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
        public ByteString getUserEmailBytes() {
            return ByteString.copyFromUtf8(this.userEmail_);
        }

        @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
        public String getUserId() {
            return this.userId_;
        }

        @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
        public ByteString getUserIdBytes() {
            return ByteString.copyFromUtf8(this.userId_);
        }

        @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
        public String getUserNick() {
            return this.userNick_;
        }

        @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
        public ByteString getUserNickBytes() {
            return ByteString.copyFromUtf8(this.userNick_);
        }

        @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
        public String getUserPhone() {
            return this.userPhone_;
        }

        @Override // com.kingsmith.aliiot.Protos.UserInfoOrBuilder
        public ByteString getUserPhoneBytes() {
            return ByteString.copyFromUtf8(this.userPhone_);
        }

        public static Builder newBuilder(UserInfo userInfo) {
            return DEFAULT_INSTANCE.createBuilder(userInfo);
        }

        public static UserInfo parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (UserInfo) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static UserInfo parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (UserInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static UserInfo parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (UserInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static UserInfo parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (UserInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static UserInfo parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (UserInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static UserInfo parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (UserInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static UserInfo parseFrom(InputStream inputStream) throws IOException {
            return (UserInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static UserInfo parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (UserInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static UserInfo parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (UserInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static UserInfo parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (UserInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface UserInfoOrBuilder extends MessageLiteOrBuilder {
        String getMobileLocationCode();

        ByteString getMobileLocationCodeBytes();

        String getOpenId();

        ByteString getOpenIdBytes();

        String getUserAvatarUrl();

        ByteString getUserAvatarUrlBytes();

        String getUserEmail();

        ByteString getUserEmailBytes();

        String getUserId();

        ByteString getUserIdBytes();

        String getUserNick();

        ByteString getUserNickBytes();

        String getUserPhone();

        ByteString getUserPhoneBytes();
    }

    private Protos() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }
}
