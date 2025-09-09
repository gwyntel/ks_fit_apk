package com.yc.utesdk.utils.open;

/* loaded from: classes4.dex */
public interface UteCalculator {
    public static final int SPORTS_TYPE_RIDE = 2;
    public static final int SPORTS_TYPE_RUN = 1;
    public static final int SPORTS_TYPE_WALK = 0;

    float calculateCalories(int i2, int i3);

    float calculateCaloriesForDistance(int i2, int i3);

    float calculateDistance(int i2, int i3);

    float calculateRideCalories(int i2);

    float kilogramToPound(float f2);

    float kilometerPaceToMilePace(float f2);

    float kilometerToMile(float f2);

    float milePaceToKilometerPace(float f2);

    float mileToKilometer(float f2);

    float poundToKilogram(float f2);
}
