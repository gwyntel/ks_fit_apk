package com.yc.utesdk.utils.close;

import com.yc.utesdk.ble.open.DeviceMode;
import com.yc.utesdk.utils.open.SPUtil;
import com.yc.utesdk.utils.open.UteCalculator;

/* loaded from: classes4.dex */
public class UteCalculatorImp implements UteCalculator {
    private static UteCalculatorImp instance;
    private final double METRIC_RUNNING_FACTOR = 1.036d;
    private final double METRIC_WALKING_FACTOR = 0.708d;
    private final double METRIC_RUNNING_MAN_FACTOR = 0.9288487d;
    private final double METRIC_RUNNING_WOMAN_FACTOR = 1.23493d;
    private final double METRIC_WALKING_MAN_FACTOR = 0.5907296d;
    private final double METRIC_WALKING_WOMAN_FACTOR = 0.7918928d;
    private final double METRIC_RIDING_FACTOR = 1.05d;
    private final float DISTANCE_PARA = 0.6213712f;
    private final float WEIGHT_PARA = 2.2046f;

    public static UteCalculatorImp getInstance() {
        if (instance == null) {
            instance = new UteCalculatorImp();
        }
        return instance;
    }

    @Override // com.yc.utesdk.utils.open.UteCalculator
    public float calculateCalories(int i2, int i3) {
        float personageStepLength;
        double personageWeight;
        double d2;
        double d3;
        if (!DeviceMode.isHasFunction_2(4096)) {
            personageStepLength = SPUtil.getInstance().getPersonageStepLength();
            personageWeight = SPUtil.getInstance().getPersonageWeight();
            d2 = 0.708d;
        } else if (i3 == 1) {
            float personageHeight = SPUtil.getInstance().getPersonageHeight();
            float personageWeight2 = SPUtil.getInstance().getPersonageWeight();
            if (SPUtil.getInstance().getPersonageGender() == 1) {
                personageStepLength = personageHeight * 0.5461055f;
                personageWeight = personageWeight2;
                d2 = 0.9288487d;
            } else {
                personageStepLength = personageHeight * 0.5047813f;
                personageWeight = personageWeight2;
                d2 = 1.23493d;
            }
        } else {
            float personageHeight2 = SPUtil.getInstance().getPersonageHeight();
            float personageWeight3 = SPUtil.getInstance().getPersonageWeight();
            int personageGender = SPUtil.getInstance().getPersonageGender();
            if (DeviceMode.isHasFunction_8(32768)) {
                d3 = ((((i2 * 5.2f) * personageHeight2) / 800.0f) * personageWeight3) / 1800.0f;
                return (float) d3;
            }
            if (personageGender == 1) {
                personageStepLength = personageHeight2 * 0.410267f;
                personageWeight = personageWeight3;
                d2 = 0.5907296d;
            } else {
                personageStepLength = personageHeight2 * 0.4151582f;
                personageWeight = personageWeight3;
                d2 = 0.7918928d;
            }
        }
        d3 = (((personageWeight * d2) * personageStepLength) * i2) / 100000.0d;
        return (float) d3;
    }

    @Override // com.yc.utesdk.utils.open.UteCalculator
    public float calculateCaloriesForDistance(int i2, int i3) {
        double d2;
        double d3;
        double d4;
        float personageWeight = SPUtil.getInstance().getPersonageWeight();
        if (i3 == 0) {
            d2 = personageWeight;
            d3 = 0.708d;
        } else {
            if (i3 != 1) {
                d4 = i3 != 2 ? 0.0d : (((personageWeight * 1.05d) * i2) / 1000.0d) / 4.0d;
                return (float) d4;
            }
            d2 = personageWeight;
            d3 = 1.036d;
        }
        d4 = ((d2 * d3) * i2) / 1000.0d;
        return (float) d4;
    }

    @Override // com.yc.utesdk.utils.open.UteCalculator
    public float calculateDistance(int i2, int i3) {
        float personageStepLength;
        float personageHeight;
        float f2;
        if (DeviceMode.isHasFunction_2(4096)) {
            if (i3 == 1) {
                personageHeight = SPUtil.getInstance().getPersonageHeight();
                f2 = SPUtil.getInstance().getPersonageGender() == 1 ? 0.5461055f : 0.5047813f;
            } else {
                personageHeight = SPUtil.getInstance().getPersonageHeight();
                f2 = SPUtil.getInstance().getPersonageGender() == 1 ? 0.410267f : 0.4151582f;
            }
            personageStepLength = personageHeight * f2;
        } else {
            personageStepLength = SPUtil.getInstance().getPersonageStepLength();
        }
        return (i2 * personageStepLength) / 100000.0f;
    }

    @Override // com.yc.utesdk.utils.open.UteCalculator
    public float calculateRideCalories(int i2) {
        return (i2 / 60.0f) * 9.72f;
    }

    @Override // com.yc.utesdk.utils.open.UteCalculator
    public float kilogramToPound(float f2) {
        return f2 * 2.2046f;
    }

    @Override // com.yc.utesdk.utils.open.UteCalculator
    public float kilometerPaceToMilePace(float f2) {
        return f2 / 0.6213712f;
    }

    @Override // com.yc.utesdk.utils.open.UteCalculator
    public float kilometerToMile(float f2) {
        return f2 * 0.6213712f;
    }

    @Override // com.yc.utesdk.utils.open.UteCalculator
    public float milePaceToKilometerPace(float f2) {
        return f2 * 0.6213712f;
    }

    @Override // com.yc.utesdk.utils.open.UteCalculator
    public float mileToKilometer(float f2) {
        return f2 / 0.6213712f;
    }

    @Override // com.yc.utesdk.utils.open.UteCalculator
    public float poundToKilogram(float f2) {
        return f2 / 2.2046f;
    }
}
